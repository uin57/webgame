package com.pwrd.war.gameserver.human.manager;

import java.net.URLEncoder;
import java.util.List;

import net.sf.json.JSONObject;

import com.pwrd.war.common.LogReasons.LoginLogReason;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.common.constants.WallowConstants;
import com.pwrd.war.core.util.MD5Util;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.db.model.FormEntity;
import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.Camp;
import com.pwrd.war.gameserver.common.enums.Sex;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.common.msg.GCCommonAskAndAnswerUrl;
import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.HumanPropAttr;
import com.pwrd.war.gameserver.item.Inventory;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerState;
import com.pwrd.war.gameserver.player.msg.GCSceneInfo;
import com.pwrd.war.gameserver.role.properties.HumanNormalProperty;
import com.pwrd.war.gameserver.scene.vo.SceneInfoVO;
import com.pwrd.war.gameserver.wallow.msg.GCWallowInfo;

public class HumanInitManager {

	private Human human;

	public HumanInitManager(Human human) {
		super();
		this.human = human;
	}

	/**
	 * 加载玩家角色基本信息
	 * 
	 * @param entity
	 */
	public void loadHuman(HumanEntity entity) {
		human.setDbId(entity.getId());
		human.setInDb(true);

		human.setName(entity.getName());
		human.setSex(Sex.getByCode(entity.getSex()));
		human.setVocationType(VocationType.getByCode(entity.getVocation()));
		human.setLevel(entity.getLevel());
		human.setGrow(entity.getGrow());

		human.setCurHp(entity.getCurHp());
		human.setMaxHp(entity.getMaxHp()); //需要计算的就不用设置了
		human.setAtk(entity.getAtk());
		human.setDef(entity.getDef());
		human.setMaxExp(entity.getMaxExp());
		human.setCurExp(entity.getCurExp());
		human.setCri(entity.getCri());
		human.setCriShanghai(entity.getCriShanghai());
		human.setSpd(entity.getSpd());
		human.setShanbi(entity.getShanbi());
		human.setShanghai(entity.getShanghai());
		human.setMianshang(entity.getMianshang());
		human.setFanji(entity.getFanji());
		human.setMingzhong(entity.getMingzhong());
		human.setLianji(entity.getLianji());
		human.setRenXing(entity.getRenxing());
		human.setZhandouli(entity.getZhandouli());
		
		human.setMaxHpLevel(entity.getMaxHpLevel());
		human.setDefLevel(entity.getDefLevel());
		human.setAtkLevel(entity.getAtkLevel());
		human.setRemoteAtkLevel(entity.getRemoteAtkLevel());
		human.setRemoteDefLevel(entity.getRemoteDefLevel());
		human.setShortAtkLevel(entity.getShortAtkLevel());
		human.setShortDefLevel(entity.getShortDefLevel());
		
		human.setSkeletonId(entity.getSkeletonId());
		human.setCamp(Camp.getByCode(entity.getCamp()));
		human.setPetSkill(entity.getPetSkill());
		human.setSkill1(entity.getSkill1());
		human.setSkill2(entity.getSkill2());
		human.setSkill3(entity.getSkill3());
		human.setPassiveSkill1(entity.getPassSkill1());
		human.setPassiveSkill2(entity.getPassSkill2());
		human.setPassiveSkill3(entity.getPassSkill3());
		human.setPassiveSkill4(entity.getPassSkill4());
		human.setPassiveSkill5(entity.getPassSkill5());
		human.setPassiveSkill6(entity.getPassSkill6());
		human.setPassiveSkillLevel1(entity.getPassSkillLevel1());
		human.setPassiveSkillLevel2(entity.getPassSkillLevel2());
		human.setPassiveSkillLevel3(entity.getPassSkillLevel3());
		human.setPassiveSkillLevel4(entity.getPassSkillLevel4());
		human.setPassiveSkillLevel5(entity.getPassSkillLevel5());
		human.setPassiveSkillLevel6(entity.getPassSkillLevel6());
		human.setMaxGrow(entity.getMaxGrow());
		human.setTransferLevel(entity.getTransferLevel());
		human.setTransferstar(entity.getTransferstar());
		human.setTransferexp(entity.getTransferExp());
		
		human.getPropertyManager().setMerit(entity.getMerit());
		human.getPropertyManager().setTitle(entity.getTitle());
		human.getPropertyManager().setVitality(entity.getVitality());
		human.getPropertyManager().setMaxVitality(entity.getMaxVitality());
		human.getPropertyManager().setBuyVitTimes(entity.getBuyVitTimes());
		human.getPropertyManager().setBuyVitalityDate(entity.getBuyVitalityDate());
		human.getPropertyManager().setCharm(entity.getCharm());
		human.getPropertyManager().setProtectFlower(entity.getProtectFlower());
		human.getPropertyManager().setOffical(entity.getOffical());
		human.getPropertyManager().setFamilyId(entity.getFamilyId());
		human.getPropertyManager().setFamilyRole(entity.getFamilyRole());
		human.getPropertyManager().setHonour(entity.getHonour());
		human.getPropertyManager().setMassacre(entity.getMassacre());
		human.getPropertyManager().setBattleAchieve(entity.getBattleAchieve());
		human.getPropertyManager().setSee(entity.getSee());
		human.getPropertyManager().setVip(entity.getVip());
		human.getPropertyManager().setBuffVip(entity.getBuffVip());
		human.getPropertyManager().setPopularity(entity.getPopularity());

		human.getPropertyManager().setCoins(entity.getCoins());
//		human.getPropertyManager().setSliver(entity.getSliver());
		human.getPropertyManager().setGold(entity.getGold());
		human.getPropertyManager().setCoupon(entity.getCoupon());
		human.getPropertyManager().setHunshiNum(entity.getHunshi());
		 
		human.getPropertyManager().setMuanBattle(entity.isMuanBattle());
		human.getPropertyManager().setIsOpenForm(entity.isOpenForm());
		human.getPropertyManager().setEquipEnhanceNum(entity.getEquipEnhanceNum());
		human.getPropertyManager().setIsInBattle(entity.isInBattle());
		
		human.getPropertyManager().setBagSize(entity.getBagSize());
		human.getPropertyManager().setMaxBagSize(entity.getMaxBagSize());
		human.getPropertyManager().setMaxDepotSize(entity.getMaxDepotSize());
		human.getPropertyManager().setDepotSize(entity.getDepotSize());
		human.getPropertyManager().getPropertyNormal().setBuffAtk(entity.getBuffAtk());
		human.getPropertyManager().getPropertyNormal().setBuffDef(entity.getBuffDef());
		human.getPropertyManager().getPropertyNormal().setBuffMaxHp(entity.getBuffMaxHp());
		human.getPropertyManager().setOpenFunc(entity.getOpenFunctions());
		
		human.setSceneId(entity.getSceneId());
		human.setX(entity.getX());
		human.setY(entity.getY());

		human.getPropertyManager().setBeforeSceneId(entity.getBeforeSenceId());
		human.getPropertyManager().setBeforeX(entity.getBeforeX());
		human.getPropertyManager().setBeforeY(entity.getBeforeY());
		human.setLastLoginTime(entity.getLastLoginTime());
		human.setLastLoginIp(entity.getLastLoginIp());
		human.setLastLogoutTime(entity.getLastLogoutTime());
		human.setTotalMinute(entity.getTotalMinute());
		human.setTodayCharge(entity.getTodayCharge());
		human.setTotalCharge(entity.getTotalCharge());
		human.setLastChargeTime(entity.getLastChargeTime());
		human.setLastVipTime(entity.getLastVipTime());
		human.setTransferLevel(entity.getTransferLevel());
		
		human.getPropertyManager().getPropertyNormal().setTotalDays(entity.getTotalDays());
		human.getPropertyManager().getPropertyNormal().setLoginGifts(entity.getLoginGifts());		

				
		HumanNormalProperty prop = human.getPropertyManager().getPropertyNormal();
		prop.setNewPrize(entity.getNewPrize());
		prop.setNewPrizeSTime(entity.getNewPrizeSTime());
		prop.setCanChat(entity.isCanChat());
		
		//将星云路自动挑战
		prop.setInAutoTower(entity.isInAutoTower());
		prop.setAutoTowerLastTime(entity.getAutoTowerLastTime());
		prop.setAutoTowerNum(entity.getAutoTowerNum());
		prop.setAutoTowerStartTime(entity.getAutoTowerStartTime());
		

		
		human.getPropertyManager().setPersonSign(entity.getPersonSign());
		
		human.getPropertyManager().getPropertyNormal().setSecretShopTime(entity.getLastSecretShopTime());
		prop.setDayTaskTime(entity.getLastDayTaskTime());
		prop.setTowerRefreshTime(entity.getLastTowerRefreshTime());
		prop.setLastFightTime(entity.getLastFightTime());
		prop.setLastRepTime(entity.getLastRepTime());
		
		human.getPropertyManager().setChargeAmount(entity.getChargeAmount());
		
		//敬酒相关
		prop.setJingjiu1(entity.getJingjiu1());
		prop.setJingjiu2(entity.getJingjiu2());
		prop.setJingjiu3(entity.getJingjiu3());
		prop.setLastJingjiuTime(entity.getLastJingjiuTime());
		
		//加载阵型
		List<FormEntity> forms = Globals.getDaoService().getFormDao().getFormByHumanSn(human.getUUID());
		if (forms.size() > 0) {
			BattleForm form = BattleForm.buildBattleForm(human, forms.get(0));
			form.setInDb(true);
			human.setBattleForm(form);
		} else {
			human.setBattleForm(null);
		}

		if ((entity.getProps() != null) && !entity.getProps().equals("")) {
			JSONObject json = JSONObject.fromObject(entity.getProps());
			for (HumanPropAttr humanPropAttr : HumanPropAttr.values()) {
				if (json.has(humanPropAttr.toString())) {
					String entryStr = json.getString(humanPropAttr.toString());
					switch (humanPropAttr) {
					case BEHAVIOR:
						humanPropAttr.resolve(human.getBehaviorManager(),
								entryStr);
						break;
					case QUEST_DIARY:
						humanPropAttr.resolve(human.getQuestDiary(), entryStr);
						break;
					case BAG_PAGE_SIZE:
						// human.setBagPageSize(NumberUtils.toInt(entryStr,
						// RoleConstants.PRIM_BAG_INIT_PAGE));
						break;
					}
				}
			}
		} else {
			// HumanAssistantService初始化太置后,需要提前初始化背包页大小
			// human.setBagPageSize(RoleConstants.PRIM_BAG_INIT_PAGE);
		}

	}



	/**
	 * <pre>
	 * 异步加载玩家角色列表之后调用此方法
	 * 此方法在主线程中调用
	 * 
	 * <pre>
	 * 
	 */
	public void humanLogin() {
		boolean isFirstLogin = human.getLastLoginTime() == 0 ? true : false;
		human.onLogin(isFirstLogin);

		Player player = human.getPlayer(); // 更新玩家状态

		if (isFirstLogin) { // 如果玩家是首次登陆

			// 那么给玩家分配一个城市 Id
			String sceneId = Globals.getSceneService().getFirstCityId(human);
			player.setTargetSceneId(sceneId);

			human.setSceneId(sceneId);
			SceneInfoVO s = Globals.getSceneService().getLoadSceneService()
					.getSceneById(sceneId);
			human.setX(s.getStartX());
			human.setY(s.getStartY());

			// human.setGamingState(GamingStateIndex.IN_NOMAL);

		} else { // 如果玩家已经登陆过
			String lastSceneId = human.getSceneId();

			if (StringUtils.isEmpty(lastSceneId)) {
				lastSceneId = Globals.getSceneService().getFirstCityId(human);
			}
			//检查场景id是否正确，不准确则修正
			//如果目标地图属于非普通地图，返回默认地图
			SceneInfoVO vo = Globals.getSceneService().getLoadSceneService().getSceneById(lastSceneId); 
			if(vo != null && vo.getSceneType() != SceneInfoVO.SceneType.NORMAL){
				lastSceneId = Globals.getSceneService().getFirstCityId(human);
				human.setSceneId(lastSceneId); 
				human.setX(vo.getStartX());
				human.setY(vo.getStartY());
			}

			// 那么进入玩家上一次所在场景
			player.setTargetSceneId(lastSceneId);

		}
		player.setLastSetPosTime(System.currentTimeMillis());

		player.setState(PlayerState.incoming);

		// 同步Cache数据

		// 激活此角色
		human.getLifeCycle().activate();
		human.setSkillLevel1();
		human.setSkillLevel2();
		human.setSkillLevel3();
		// 通知消息
		noticeHuman();
		
		//log日志
		Globals.getLogService().sendLoginLog(human, LoginLogReason.LOGIN, null, Globals.getTimeService().now(), human.getLastLoginIp(), human.getSceneId(), human.getTotalMinute(), LoginLogReason.LOGIN.getReason());

	}
	
	

	/**
	 * 发送初始的消息接口
	 */
	public void noticeHuman() {
		Player player = human.getPlayer();
		// 建立玩家角色名称与Player的查询关系
		Globals.getOnlinePlayerService().putPlayer(human.getName(), player);
		// //发送角色详细信息
		// human.sendMessage(new GCHumanDetailInfo(
		// Globals.getHumanService().getHumanInfo(human),
		// human.getCdManager().getCdQueueInfos()));

		
				
		// 通知属性信息
		human.snapChangedProperty(true);
		
		// 发送可以进入场景信息
		GCSceneInfo sceneInfo = new GCSceneInfo();
		human.sendMessage(sceneInfo);
		
		Inventory inv = human.getInventory();
		
//		human.sendMessage(inv.getPrimBagInfoMsg());	
		
//		human.sendMessage(ItemMessageBuilder.buildGCBagUpdate(inv.getBagByType(BagType.TEMP, null)));

		human.sendMessage(inv.getPrimBagInfoMsg());

		human.sendMessage(inv.getEquipBagInfoMsg());
		human.sendMessage(inv.getDepotBagInfoMsg());
		human.sendMessage(inv.getXinghunBagInfoMsg());
		
		human.sendMessage(inv.getXiangqianBagInfoMsg());

		// 发送宠物列表
		human.sendMessage(human.getPetManager().getPetListMsg());
		// 发送宠物属性信息
		human.getPetManager().sendPetPropsMsg(null);
		// 宠物背包
		for (Pet pet : human.getPetManager().getPets()) {
			human.sendMessage(inv.getPetEquipBagInfoMsg(pet.getUUID()));
		}

//		human.sendMessage(new GCVocation(human.getVocationSkills()));

		//发送阵型
		Globals.getFormService().getForm(player);
		
		//发送cd队列信息
		human.getCooldownManager().sendAllQueueMsg();
		//发送好友离线留言消息
		human.getFriendManager().sendLoginNewMessage();
		
		// 记录登陆时基本信息log
		Globals.getPlayerLogService().sendLoginLog(human);

		// 发送在线答疑URL
		human.sendMessage(new GCCommonAskAndAnswerUrl(getAskAndAnswerUrl(human)));

		

		//发送buff消息
		human.sendBufferMessage();
		
		
		modifyBattleMessage(human);
		// 发送防沉迷信息
		if (player.getWallowFlag() == WallowConstants.WALLOW_FLAG_ON) {
			player.setAccOnlineTime(0);
			Globals.getWallowService().onPlayerEnter(player);
			human.sendSystemMessage(PlayerLangConstants_30000.WALLOW_FILL_INFOR);
		}else{
			player.sendMessage(new GCWallowInfo(-1, ""));
		}
		//发送登陆信息
		String ip = human.getLastLastLoginIp();
		String time = TimeUtils.formatYMDHMTime(human.getLastLastLoginTime());
		if(StringUtils.isEmpty(ip)){
			ip = human.getLastLoginIp();
			time = TimeUtils.formatYMDHMTime(human.getLastLoginTime());
		}
		human.sendSystemMessage(PlayerLangConstants_30000.LAST_LOGIN_INFO, ip.split(":")[0], time);
		human.getPlayer().setLastAccTime(Globals.getTimeService().now());
		
		
		//发送任务列表信息
		human.getQuestDiary().sendQuestListMsg();
		
		//发送当前已播放剧情列表信息
		human.getStoryManager().sendStoriesList();
		
		//在线奖励
		human.checkOnlinePrize(true);
		//登陆奖励
		human.checkLoginGifts();
		
		//扫荡
		Globals.getAgainstRepService().onPlayerLogin(player);
		//恢复体力
		Globals.getVitService().onPlayerLogin(player);
		//修炼
		Globals.getHumanService().getHumanXiulianService().onPlayerLogin(player);
		//发送兵法属性
		Globals.getWarcraftService().sendWarcraftEquip(human.getPlayer());
		Globals.getWarcraftService().sendPetWarcraftEquip(human.getPlayer());
	}



	private void modifyBattleMessage(Human human) {
		if (human.getGamingState() == GamingStateIndex.IN_BATTLE.getValue()) {
			human.setGamingState(GamingStateIndex.IN_NOMAL);
		}
	}

	/**
	 * 获取在线答疑后面的URL
	 * 
	 * @return
	 */
	private String getAskAndAnswerUrl(Human human) {

		// 用户名
		String un = human.getPassportName();
		try {
			un = URLEncoder.encode(un, "UTF-8");
		} catch (Exception e) {
			Loggers.playerLogger.error("进行URL encode时出错", e);
			un = human.getPassportName();
		}
		// 角色名
		String rn = human.getName();
		try {
			rn = URLEncoder.encode(rn, "UTF-8");
		} catch (Exception e) {
			Loggers.playerLogger.error("进行URL encode时出错", e);
			rn = human.getName();
		}
		// 角色的UUID
		String rid = human.getUUID();
		// 服务器ID
		String sid = Globals.getServerConfig().getLocalHostId();
		try {
			sid = URLEncoder.encode(sid, "UTF-8");
		} catch (Exception ex) {
			Loggers.playerLogger.error("进行URL encode时出错", ex);
			sid = Globals.getServerConfig().getLocalHostId();
		}

		// 服务器域名
		String domain = Globals.getServerConfig().getServerDomain();
		try {
			domain = URLEncoder.encode(domain, "UTF-8");
		} catch (Exception ex) {
			Loggers.playerLogger.error("进行URL encode时出错", ex);
			domain = Globals.getServerConfig().getServerDomain();
		}

		// key
		String key = SharedConstants.LOCAL_MD5_KEY;
		try {
			key = URLEncoder.encode(key, "UTF-8");
		} catch (Exception ex) {
			Loggers.playerLogger.error("进行URL encode时出错", ex);
			key = SharedConstants.LOCAL_MD5_KEY;
		}

		StringBuilder url = new StringBuilder("?");
		url.append("un=").append(un).append("&rn=").append(rn).append("&rid=")
				.append(rid).append("&sid=").append(sid).append("&domain=")
				.append(domain).append("&sign=");

		StringBuilder sign = new StringBuilder(un).append(rn).append(rid)
				.append(sid).append(domain).append(key);

		url.append(MD5Util.createMD5String(sign.toString()));

		return url.toString();
	}
}
