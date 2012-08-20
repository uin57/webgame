package com.pwrd.war.gameserver.common.log;

import com.pwrd.war.common.LogReasons.BasicPlayerLogReason;
import com.pwrd.war.common.LogReasons.SnapLogReason;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.PlayerExitReason;
import com.pwrd.war.gameserver.scene.SceneService;

public class PlayerLogService {
	
	
	
	private SceneService sceneService;
	
	private TemplateService templateService;
	
	
	public PlayerLogService(SceneService sceneService,TemplateService tmplService)
	{
		this.sceneService = sceneService;
		this.templateService = tmplService;
	}
	
	
	
	public void sendBasicPlayerLog(Human human,BasicPlayerLogReason playerLogReason,String detailReason)
	{
		try {
//			int currRank = human.getRank();
//			int currSceneId = human.getSceneId();
//			int currMissionStageId = human.getMissionManager().getCurrentStageId();
//			
//			Scene currScene = sceneService.getScene(currSceneId);
//			String currSceneName = SharedConstants.NOT_EXIST_NAME;
//			if(currScene != null)
//			{
//				currSceneName = currScene.getName();
//			}
//			
//			MissionStageTemplate mst = templateService.get(currMissionStageId, MissionStageTemplate.class);
//			String missionName = SharedConstants.NOT_EXIST_NAME;
//			
//			if (mst != null) {
//				missionName = mst.getName();
//			}
//
//			
//			Globals.getLogService().sendBasicPlayerLog(human, playerLogReason, 
//					detailReason, human.getLastLoginIp(), 0, currRank, 
//					rankService.getRankName(currRank), currSceneId, 
//					currSceneName, 
//					currMissionStageId, missionName);
		} catch (Exception e) {
			Loggers.playerLogger.error(LogUtils.buildLogInfoStr(human.getUUID(),
			"记录角色基本日志时出错"), e);
		}
	}
	
	
	/**
	 * 记录角色信息快照
	 * @param human
	 * @param reason
	 */
	public void sendHumanSnapLog(Human human,SnapLogReason reason)
	{
		try {
			String snapString = SnapStringBuilder.buildHumanSnap(human);
			Globals.getLogService().sendSnapLog(human,
					reason, "", snapString);
		} catch (Exception e) {
			Loggers.playerLogger.error(LogUtils.buildLogInfoStr(
					human.getUUID(), "error when sending login snap log"), e);
		}
	}
	
	/**
	 * 记录玩家背包快照
	 * @param human
	 * @param reason
	 */
	public void sendInventorySnapLog(Human human,SnapLogReason reason)
	{
		
		try {
			String snapString = SnapStringBuilder.buildInventorySnap(human.getInventory());
			Globals.getLogService().sendSnapLog(human,
					reason, "",
					snapString);
		} catch (Exception e) {
			Loggers.itemLogger.error(LogUtils.buildLogInfoStr(human.getUUID(),
					"记录角色登陆时背包快照时出错"), e);
		}
	}
	
	/**
	 * 记录玩家武将快照
	 * @param human
	 * @param reason
	 */
	public void sendPetSnapLog(Human human,SnapLogReason reason)
	{
		try{			
			Globals.getLogService().sendSnapLog(human, reason, "",
					SnapStringBuilder.buildPetSnap(human.getPetManager()));
		} catch (Exception e) {
			Loggers.playerLogger.error(
					LogUtils.buildLogInfoStr(human.getUUID(), "玩家下线记录快照时发生错误"),
					e);
		}
	}
	
	/**
	 * 记录玩家副本信息快照
	 * @param human
	 * @param reason
	 */
	public void sendHumanRepInfoSnapLog(Human human,SnapLogReason reason)
	{
		try{			
			Globals.getLogService().sendSnapLog(human, reason, "",
					SnapStringBuilder.buildHumanRepInfoSnap(human.getHumanRepInfoManager()));
		} catch (Exception e) {
			Loggers.playerLogger.error(
					LogUtils.buildLogInfoStr(human.getUUID(), "玩家下线记录快照时发生错误"),
					e);
		}
	}
	
	public void sendLoginLog(Human human)
	{
		sendBasicPlayerLog(human,BasicPlayerLogReason.REASON_NORMAL_LOGIN,"");
		sendLoginSnapLog(human);
	}
	
	public void sendExitLog(Human human, PlayerExitReason reason)
	{
		BasicPlayerLogReason basicPlayerLogReason = null;
		switch (reason) {
			case SERVER_ERROR:
			case LOGOUT:
				basicPlayerLogReason = BasicPlayerLogReason.REASON_NORMAL_LOGOUT;
				break;
			case SERVER_SHUTDOWN:
				basicPlayerLogReason = BasicPlayerLogReason.REASON_LOGOUT_SERVER_SHUTDOWN;
				break;
			case GM_KICK:
				basicPlayerLogReason = BasicPlayerLogReason.GM_KICK;
				break;
			case MULTI_LOGIN:
				basicPlayerLogReason = BasicPlayerLogReason.MULTI_LOGIN;
				break;
			default:
				return;
		}
		sendBasicPlayerLog(human,basicPlayerLogReason,"");
		sendExitSnapLog(human,reason);
	}
	
	/**
	 * 
	 * 登录时,在此处只记录玩家自身信息的快照,物品和武将的日志在各自管理器自行发送,
	 * {@link com.pwrd.war.gameserver.item.Inventory},
	 * {@link com.pwrd.war.gameserver.human.manager.HumanPetManager}
	 * 
	 * @param human
	 */
	private void sendLoginSnapLog(Human human)
	{
		sendHumanSnapLog(human,SnapLogReason.REASON_SNAP_LOGIN_STATUS);

	}
	
	private void sendExitSnapLog(Human human, final PlayerExitReason reason) {
		SnapLogReason snapReasonStatus = null;
		SnapLogReason snapReasonItem = null;
		SnapLogReason snapReasonPet = null;
		
		switch (reason) {
			case LOGOUT:
				snapReasonStatus = SnapLogReason.REASON_SNAP_LOGOUT_STATUS;
				snapReasonItem = SnapLogReason.REASON_SNAP_LOGOUT_ITEM;
				snapReasonPet = SnapLogReason.REASON_SNAP_LOGOUT_PET;
				break;
			case SERVER_ERROR:
			case GM_KICK:
				snapReasonStatus = SnapLogReason.REASON_SNAP_KICK_STATUS;
				snapReasonItem = SnapLogReason.REASON_SNAP_KICK_ITEM;
				snapReasonPet = SnapLogReason.REASON_SNAP_KICK_PET;
				break;
			// 切换服务器时暂时不记录快照
			default:
				return;
		}
		sendHumanSnapLog(human,snapReasonStatus);
		sendInventorySnapLog(human,snapReasonItem);
		sendPetSnapLog(human,snapReasonPet);
	}

}
