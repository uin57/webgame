package com.pwrd.war.gameserver.player.async;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pwrd.op.LogOp;
import com.pwrd.op.LogOpChannel;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.db.dao.HumanDao;
import com.pwrd.war.db.model.ChargeInfo;
import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerExitReason;
import com.pwrd.war.gameserver.player.PlayerState;
import com.pwrd.war.gameserver.player.msg.GCNotifyException;

/**
 * 加载角色所有数据的异步操作
 * 
 * 
 */
public class LoadPlayerRoleOperation implements IIoOperation {

	private final Player player;
	private String roleUUID;

	/** 是否数据库操作成功 */
	private boolean isOperateSucc = false;

	/**
	 * 
	 * @param player
	 *            玩家对象
	 * @param roleUUID
	 *            玩家角色
	 */
	public LoadPlayerRoleOperation(Player player, String roleUUID) {
		this.player = player;
		this.roleUUID = roleUUID;
	}

	@Override
	public int doIo() {
		do {
			try {
				// 加载帐户信息 ：
				boolean rs = this.loadAccountInfo();
				if(!rs){
					//账户错误
					player.exitReason = PlayerExitReason.SERVER_ERROR;
					player.disconnect();
					Loggers.msgLogger.warn("加载账户信息错误："+player.getPassportName());
					return STAGE_STOP_DONE;
				}
				HumanDao _roleInfoDao = Globals.getDaoService().getHumanDao();
				HumanEntity entity = _roleInfoDao.get(roleUUID);
				if(entity == null || !StringUtils.equals(entity.getPassportId(), player.getPassportId())){
					//角色错误
					player.exitReason = PlayerExitReason.SERVER_ERROR;
					player.disconnect();
					Loggers.msgLogger.warn("加载账户和角色信息错误："+player.getPassportName()+","+roleUUID);
					return STAGE_STOP_DONE;
				}
				
				player.init();//初始化必要管理器
				Human human = new Human();
				human.fromEntity(entity);
				human.initInventory();

				String passportId = entity.getPassportId();
				player.setPassportId(passportId);

				// 首先设置player,因为在之后的执行中可能会用到player
				player.setHuman(human);
				human.setPlayer(player);
				// 加载武将
				human.getPetManager().load();
				// 初始化宠物装备包裹
				human.getInventory().initPetBags();				
				// 加载物品
				human.getInventory().load();
				// 加载任务
				human.getQuestDiary().load();
				// 加载剧情
				human.getStoryManager().load();
				// 加载邮件
				human.getMailbox().load();
				// 加载家族信息
				human.getFamilyManager().load();
				// 加载礼包信息
				human.getGiftBagContianer().load();
				//加载buff信息
				human.loadBuff();
				//加载兵法信息
				human.getWarcraftInventory().load();			
				//加载将星云路信息
				human.getTowerInfoManager().load();
				//加载伪随机信息
				human.getHumanProbInfoManager().load();
				
				human.getTreeInfoManager().load();
				human.getTreeWaterManager().load();
				//加载修炼信息
				Globals.getHumanService().getHumanXiulianService().load(player);
				Globals.getAgainstRepService().load(player);
				Globals.getRobberyService().load(human);
				
				if(Globals.getServerConfig().getOperationCom().equals(SharedConstants.OPERATION_COM_HITHERE))
				{
					if(Globals.getServerConfig().getFuncSwitches().isChargeEnabled())
					{						
						List<ChargeInfo> chargeInfos = Globals.getDaoService().getChargeInfoDao().loadChargeInfos(human.getPassportId());						
						for(ChargeInfo chargeInfo : chargeInfos)
						{
							human.addDirectChargeOrderId(chargeInfo.getId());
						}				
					}
				}
			
				// 因为涉及的到数据量可能较大,在加载完成后执行进入游戏的预处理,将相关的对象设置为Live
				human.checkAfterRoleLoad();
				// 设置成活动
				
				// 数据加载完成之后初始化
				human.onInit(Globals.getTemplateService(),Globals.getLangService());
				isOperateSucc = true;
				
				//GM平台
				long now = Globals.getTimeService().now();
				String ipAddr = player.getClientIp();
				String[] ip = ipAddr.split(":");
				LogOp.log(LogOpChannel.LOGIN, roleUUID, now, ip[0], TimeUtils.formatYMDTime(now));
				
			} catch (Exception e) {
				isOperateSucc = false;
				Loggers.playerLogger.error(LogUtils.buildLogInfoStr(player
						.getRoleUUID(), "#GS.CharacterLoad.doIo"), e);
			}
		} while (false);
		return STAGE_IO_DONE;
	}

	/**
	 * 加载帐户信息
	 */
	private boolean loadAccountInfo() {
		// 获取并设置权限
		List<?> _roles = Globals.getDaoService().getDBService()
				.findByNamedQueryAndNamedParam("queryPlayerRole",
						new String[] { "id" },
						new Object[] { player.getPassportId() });
		if (_roles != null && _roles.size() > 0) {
			Object[] _userInfo = (Object[]) _roles.get(0);
			if (_userInfo != null && _userInfo.length > 1) {
				player.setPassportName((String) _userInfo[0]);
				player.setPermission((Integer) _userInfo[1]);
				player.setTodayOnlineTime((Integer) _userInfo[2]);
				player.setLastLogoutTime((Long) _userInfo[3]);
				return true;
			}
		}
		return false;
	}

	@Override
	public int doStart() {		
		return STAGE_START_DONE;
	}

	@Override
	public int doStop() {
		final Human human = player.getHuman();
		try {
			if (player.getState() == PlayerState.logouting || !isOperateSucc || human == null) {				
				player.sendMessage(new GCNotifyException(
						CommonLangConstants_10000.LOAD_PLAYER_SELECTED_ROLE,
						Globals.getLangService().read(CommonLangConstants_10000.LOAD_PLAYER_SELECTED_ROLE)));
				player.exitReason = PlayerExitReason.SERVER_ERROR;
				player.disconnect();
			} else {
				human.getInitManager().humanLogin();
			}
		} catch (Exception e) {
			Loggers.playerLogger.error(LogUtils.buildLogInfoStr(
					player.getRoleUUID(), "#GS.CharacterLoad.doIo"), e);
			player.sendMessage(new GCNotifyException(
					CommonLangConstants_10000.LOAD_PLAYER_SELECTED_ROLE,
					Globals.getLangService().read(CommonLangConstants_10000.LOAD_PLAYER_SELECTED_ROLE)));
			player.exitReason = PlayerExitReason.SERVER_ERROR;
			player.disconnect();
		
		}
		return STAGE_STOP_DONE;
	}
}
