package com.pwrd.war.gameserver.player.async;

import java.util.List;

import org.slf4j.Logger;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerExitReason;
import com.pwrd.war.gameserver.player.RoleInfo;
import com.pwrd.war.gameserver.player.msg.GCNotifyException;

/**
 * 加载玩家的角色列表
 * 
 */
public class PlayerRolesLoad implements IIoOperation {

	private static final Logger logger = Loggers.asyncDbLogger;
	private final Player player;
	private final String passportId;
	private boolean isOperateSucc = false;

	/** 是否加载完毕后使用指定charId直接进入游戏 */
	private boolean isForwardEnter;
	/** 需要直接进入游戏的charId */
	private String charId;

	/**
	 * 
	 * @param player
	 *            玩家对象
	 * @param passportId
	 *            玩家的passportId
	 */
	public PlayerRolesLoad(Player player, boolean isForwardEnter,
			String charId) {
		this.player = player;
		this.passportId = player.getPassportId();
		this.isForwardEnter = isForwardEnter;
		this.charId = charId;
	}

	@Override
	public int doIo() {
		do {
			try {
				// 只有在玩家处理于连接状态的时候才加载他的角色列表
				if (!player.isOnline()) {
					break;
				}
				RoleInfo role = this.loadPlayerRoleList(passportId);
				player.setRole(role);
				isOperateSucc = true;
			} catch (Exception e) {
				isOperateSucc = false;
				if (logger.isErrorEnabled()) {
					Loggers.playerLogger.error(LogUtils.buildLogInfoStr(
							passportId, "load character error"), e);
				}
			}
		} while (false);

		return STAGE_IO_DONE;
	}

	@Override
	public int doStart() {
		return STAGE_START_DONE;
	}

	@Override
	public int doStop() {
		if (isOperateSucc) {
			Globals.getLoginLogicalProcessor().onCharsLoad(this.player,
					this.isForwardEnter, this.charId);
		} else {
			player.sendMessage(new GCNotifyException(
					CommonLangConstants_10000.LOAD_PLAYER_ROLES, 
					Globals.getLangService().read( CommonLangConstants_10000.LOAD_PLAYER_ROLES)));
			player.exitReason = PlayerExitReason.SERVER_ERROR;			
			player.disconnect();
		}
		return STAGE_STOP_DONE;
	}

	
	/**
	 * @param passportId
	 * @return
	 */
	public RoleInfo loadPlayerRoleList(String passportId) {
		try {
			// 从数据库中读取
			List<HumanEntity> humans = Globals.getDaoService().getHumanDao().loadHumans(passportId);
			
			RoleInfo roleInfo =  null;
			// 进行转换 
			if(humans.size() > 0){
				HumanEntity human = humans.get(0); 
				roleInfo = new RoleInfo();
				roleInfo.setRoleUUID(human.getId());
				roleInfo.setName(human.getName());
				roleInfo.setPassportId(human.getPassportId());
				roleInfo.setSex(human.getSex());
				roleInfo.setVocation(human.getVocation());
				roleInfo.setCamp(human.getCamp());
				
				
				roleInfo.setFirstLogin(StringUtils.isEmpty(human.getSceneId()));
				if (roleInfo.getFirstLogin()) {
					// 
					// 如果是第一次登陆, 则获取玩家武将列表!
					// 主要目的是用于引导步骤第一步的战报播放, 战报会根据兵种的不同而不同!
					// XXX 注意, 新注册的玩家此时只有一个主(武)将
					//TODO  
//					List<PetEntity> petEntities = Globals.getDaoService().getPetDao().getPetsByCharId(human.getId());
//					if(petEntities.size()  > 0){
//						PetEntity petEntity = petEntities.get(0);
//						PetSelection petSel = new PetSelection();
//						petSel.setTemplateId(petEntity.getTemplateId());
//						roleInfo.setSelection(petSel);
//					} 
				}
				
			} 
			return roleInfo;
		} catch (DataAccessException e) {
			Loggers.playerLogger.error(LogUtils.buildLogInfoStr(passportId,
					"#GS.PlayerManagerImpl.loadPlayersByPid"), e);
			return null;
		}
	}
	
}
