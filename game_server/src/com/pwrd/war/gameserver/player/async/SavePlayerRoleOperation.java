package com.pwrd.war.gameserver.player.async;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.operation.BindUUIDIoOperation;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerConstants;
import com.pwrd.war.gameserver.player.PlayerState;

/**
 * 异步保存角色信息
 * 
 * 
 */
public class SavePlayerRoleOperation implements BindUUIDIoOperation {

	/** 玩家 */
	private Player player;
	/** 保存信息 Mask */
	private int mask;
	/** 是否为退出保存 */
	private boolean isLogoutSaving;
	/** 要保存的 */
	private Human human;
	/** 角色基础信息 */
	private HumanEntity humanEntity = null;


	public SavePlayerRoleOperation(Player player, int mask,
			boolean isLogoutSaving) {
		this.player = player;
		this.mask = mask;
		this.isLogoutSaving = isLogoutSaving;
		Loggers.msgLogger.info("create save Player Oper:"+player.getPassportName()+","+isLogoutSaving);
	}

	@Override
	public int doStart() {
		this.human = player.getHuman();
		if (human == null) {
			// 如果玩家没有角色绑定,则忽略保存,直接返回STATE_IO_DONE,进入下一个阶段
			return STAGE_IO_DONE;
		}
		try {
			if (isLogoutSaving) {
				// 必须在调用 human.toEntity() 之前调用,logLogout会设置在线时长且没有setModified(),toEntity()直接保存到实体
				human.logLogout();
			}
			if ((mask & PlayerConstants.CHARACTER_INFO_MASK_BASE) != 0) {
				this.humanEntity = human.toEntity();
			}
			if ((mask & PlayerConstants.CHARACTER_INFO_MASK_BATTLE_SNAP) != 0) {
//				this.battleSnapEntity = human.getBattleManager().buildBattleSnapEntity();
			}
		} catch (Exception e) {
			Loggers.playerLogger.error(LogUtils.buildLogInfoStr(player
					.getRoleUUID(), String.format(
					"Conventer error. pid=%s,cid=%s", humanEntity != null ? humanEntity
							.getPassportId() : "", humanEntity != null ? humanEntity.getId() : 0)), e);
		}
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		do {
			// 保存玩家的基本数据到数据库
			try {
				if(isLogoutSaving){
				//先保存player里的updata数据，这个时候已经停止心跳了，有些数据可能未保存
					human.getPlayer().getDataUpdater().update();
				}
				
				if (this.humanEntity != null) {
					Globals.getDaoService().getHumanDao().update(humanEntity);
//					Globals.getHumanService().syncCacheEntity(humanEntity);
				}
				
//				if (this.battleSnapEntity != null) {
//					Globals.getDaoService().getBattleSnapDao().saveOrUpdate(battleSnapEntity);
//				}
				
				if (isLogoutSaving) {
					
					long lastLoginTime = human.getLastLoginTime();
					
					if(lastLoginTime != 0)
					{
						// 如果跨天，对管理开始时间清零，当日历史累计在线时长清零，重新计算提醒时间
						long now = Globals.getTimeService().now();
						if (!TimeUtils.isSameDay(human.getLastLoginTime(), now)) {
							int time = (int) (now - TimeUtils.getTodayBegin(Globals.getTimeService())) / 1000 / 60;
							human.getPlayer().setTodayOnlineTime(time);
						} else {
							human.getPlayer().setTodayOnlineTime((int) (now - human.getLastLoginTime()) / 1000 / 60);
						}
											
						// 更新UserInfo的当日累计在线时间
						Globals.getDaoService().getHumanDao().updatePlayerOnlineTime(
										human.getPassportId(),
										human.getPlayer().getLastLoginTime(),
										human.getPlayer().getTodayOnlineTime());		
					}
				}
			} catch (Exception e) {
				Loggers.playerLogger.error(LogUtils.buildLogInfoStr(player
						.getRoleUUID(), "Save character base info error."), e);
			}

		} while (false);
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		if (isLogoutSaving) {
			player.setState(PlayerState.logouting);
			Globals.getOnlinePlayerService().removeSession(player.getSession());
			Globals.getOnlinePlayerService().removePlayer(player);
		}
		return STAGE_STOP_DONE;
	}

	@Override
	public String getBindUUID() {
		return this.player.getRoleUUID();
	}
}
