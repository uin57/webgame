package com.pwrd.war.gameserver.player.async;

import java.util.Map;

import com.pwrd.op.LogOp;
import com.pwrd.op.LogOpChannel;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.common.service.DirtFilterService.WordCheckType;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.human.template.HumanInitPropTemplate;
import com.pwrd.war.gameserver.human.template.LevelUpExpTemplate;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerState;
import com.pwrd.war.gameserver.player.RoleInfo;
import com.pwrd.war.gameserver.role.Role;
import com.pwrd.war.gameserver.role.template.RoleToSkillTemplate;

/**
 * 异步IO操作： 保存一个新角色
 * 
 * @author Fancy
 * @version 2009-7-31 下午07:04:40
 */
public class CreateRoleOperation implements IIoOperation {

	/** 玩家 */
	private Player player;

	/** 角色 */
	private RoleInfo roleInfo;

	/** 是否创建成功 */
	private boolean isCreateSucc = false;

	/**
	 * @param player
	 */
	public CreateRoleOperation(Player player, RoleInfo roleInfo) {
		this.player = player;
		this.roleInfo = roleInfo;
	}

	@Override
	public int doIo() {
		do {
			String name = roleInfo.getName();
			if (name == null || name.equals("")) {
				player.sendErrorPromptMessage(Globals.getLangService().read(PlayerLangConstants_30000.EMPTY_ROLE_NAME));
				return STAGE_STOP_DONE;
			}

			// 判断姓名是否合法
			int _checkInputError = Globals.getDirtFilterService().checkInput(
					WordCheckType.NAME, name,
					CommonLangConstants_10000.GAME_INPUT_TYPE_CHARACTER_NAME,
					SharedConstants.MIN_NAME_LENGTH_ENG,
					SharedConstants.MAX_NAME_LENGTH_ENG, false);

			if (_checkInputError != 0) { 
				switch (_checkInputError) {
					case -1:
						player.sendErrorPromptMessage(CommonLangConstants_10000.GAME_INPUT_TOO_SHORT,
								Globals.getLangService().read(CommonLangConstants_10000.GAME_INPUT_TYPE_CHARACTER_NAME),
								SharedConstants.MIN_NAME_LENGTH_ENG);
						break;
					case -2:
						player.sendErrorPromptMessage(CommonLangConstants_10000.GAME_INPUT_TOO_SHORT,
								Globals.getLangService().read(CommonLangConstants_10000.GAME_INPUT_TYPE_CHARACTER_NAME),
								SharedConstants.MIN_NAME_LENGTH_ENG);
						break;
					case -3:
						player.sendErrorPromptMessage(CommonLangConstants_10000.GAME_INPUT_TOO_LONG,
								Globals.getLangService().read(CommonLangConstants_10000.GAME_INPUT_TYPE_CHARACTER_NAME),
								SharedConstants.MAX_NAME_LENGTH_ENG,SharedConstants.MAX_NAME_LENGTH_ZHCN);
						break;
					default:
						player.sendErrorPromptMessage(CommonLangConstants_10000.NAME_UNLAWFUL);
						break;
				}
				
				return STAGE_STOP_DONE;
			}

			// 判断玩家姓名是否重复
			if (Globals.getOnlinePlayerService().loadPlayerByName(name) != null) {
//				GCNotifyException exp = new GCNotifyException();
//				exp.setCode(NotifyCode.DUPLICATE_ROLE_NAME.getCode());
//				exp.setMsg(Globals.getLangService().read(PlayerLangConstants_30000.DUPLICATE_ROLE_NAME));
////				player.sendErrorMessage(Globals.getLangService().read(PlayerLangConstants_30000.DUPLICATE_ROLE_NAME));
//				player.sendMessage(exp);
				player.sendErrorPromptMessage(PlayerLangConstants_30000.DUPLICATE_ROLE_NAME);
				return STAGE_STOP_DONE;
			}

			// 所有判断都在状态更新之前,否则状态混乱
			player.setState(PlayerState.creatingrole);
			
			if (!player.isOnline()) {
				break;
			}
			// 保存到数据库
			isCreateSucc = this.createRole(player, roleInfo);
			
			//GM平台
			long now = Globals.getTimeService().now();
			String ipAddr = player.getClientIp();
			String[] ip = ipAddr.split(":");
			LogOp.log(LogOpChannel.REGISTER, roleInfo.getRoleUUID(), roleInfo.getName(), now, ip[0], TimeUtils.formatYMDTime(now), 0);
			
		} while (false);
		return STAGE_IO_DONE;
	}

	@Override
	public int doStart() {
		return STAGE_START_DONE;
	}

	@Override
	public int doStop() {
		if (player.getState() == PlayerState.creatingrole) {
			if (isCreateSucc) {
				// 创建完角色后，如果是第一次创建，characters为空，所以重新加载角色列表，然后直接进入游戏
				Globals.getLoginLogicalProcessor().loadCharacters(player, true,
						roleInfo.getRoleUUID());
			} else {
				player.setState(PlayerState.waitingselectrole);
			}
		}
		return STAGE_STOP_DONE;
	}
	
	
	public boolean createRole(Player player, RoleInfo roleInfo) {
		try {
			// 插入角色对象
			// 获得UUID
//			roleInfo.setRoleUUID(Globals.getUUIDService().getNextUUID(UUIDType.HUMAN));
			HumanEntity he = roleInfo.toEntity();
			this.initHumanInfo(he);
			
			Globals.getDaoService().getHumanDao().save(he);
			roleInfo.setRoleUUID(he.getId());
			return true;
		} catch (DataAccessException e) {
			Loggers.playerLogger.error(LogUtils.buildLogInfoStr(player
					.getPassportId(), "PlayerManagerImpl.createCharacter"), e);
			player.sendErrorMessage(Globals.getLangService().read(PlayerLangConstants_30000.LOGIN_UNKOWN_ERROR));
			return false;
		}
	}

	
	
	
	/**
	 * 初始化角色信息，在新创建角色的时候，主要是从配置等文件读取信息
	 * 注册初始化
	 * @author xf
	 */
	private void initHumanInfo(HumanEntity human){
		LevelUpExpTemplate leveltmp = Globals.getTemplateService().get(1, LevelUpExpTemplate.class);
		Map<Integer, HumanInitPropTemplate> tmps = Globals.getTemplateService().getAll(HumanInitPropTemplate.class);
		HumanInitPropTemplate tmp = tmps.get(human.getVocation());
		human.setLevel(tmp.getInitLevel());
		human.setVitality(1);

		human.setCurExp(0);
		human.setMaxExp(leveltmp.getNeedExp());
		human.setGrow(tmp.getInitGrow()); 
		human.setAtk(tmp.getInitAtk());
		human.setDef(tmp.getInitDef());
		human.setCurHp(tmp.getInitHp());
		human.setMaxHp(tmp.getInitHp());
		human.setSpd(tmp.getInitSpd());
		 
		human.setCri(tmp.getInitCri());
		human.setShanbi(tmp.getInitShanbi());
		human.setShanghai(tmp.getInitShanghai());
		human.setMianshang(tmp.getInitMianshang());
		human.setFanji(tmp.getInitFanji());
		human.setMingzhong(tmp.getInitMingzhong());
		human.setLianji(tmp.getInitLianji());
		human.setZhandouli(0);//待读取时才重新初始化计算
		
		human.setSkeletonId("01");
		
		human.setTitle(1);
		human.setVitality(0);
		human.setCharm(122);
		human.setProtectFlower(144);
		human.setOffical(133);
		human.setFamilyId(null);
		human.setFamilyRole(0);
		human.setHonour(1111);
		human.setMassacre(1222);
		human.setBattleAchieve(1333);
		human.setSee(0);
		human.setVip(0);
		human.setCoins(0);
		human.setGold(0);
		human.setCoupon(0); 
		human.setMerit(100000);
		
		human.setOpenFunctions("");
		
		human.setMaxGrow(Globals.getHumanService().getMaxGrow());
		human.setGamingStatus(GamingStateIndex.IN_NOMAL.getValue());
		human.setEquipEnhanceNum(1);//默认1个
		human.setMaxVitality(200);
		human.setBagSize(24);
		human.setMaxBagSize(216);
		human.setDepotSize(36);
		human.setMaxDepotSize(216);
		human.setSkill1("");
		human.setSkill2("");
		human.setSkill3("");
		human.setTransferLevel(Role.DEFAULT_TRANSFER_LEVEL);
		human.setTransferExp(0);
		human.setTransferstar(0);
		human.setPassSkillLevel1(-1);
		human.setPassSkillLevel2(-1);
		human.setPassSkillLevel3(-1);
		human.setPassSkillLevel4(-1);
		human.setPassSkillLevel5(-1);
		human.setPassSkillLevel6(-1);
		RoleToSkillTemplate hrst =  Globals.getHumanService().getRoleToSkillTemplate(
				VocationType.getByCode(human.getVocation()));
		human.setPetSkill(hrst.getPetSkill());
		human.setPassSkill1(hrst.getPassiveSkill1());
		human.setPassSkill2(hrst.getPassiveSkill2());
		human.setPassSkill3(hrst.getPassiveSkill3());
		human.setPassSkill4(hrst.getPassiveSkill4());
		human.setPassSkill5(hrst.getPassiveSkill5());
		human.setPassSkill6(hrst.getPassiveSkill6());
		
		human.setNewPrize((short)0);
		human.setNewPrizeSTime(0);
		human.setCanChat(true);
	}
}
