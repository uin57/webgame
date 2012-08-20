package com.pwrd.war.gameserver.player.handler;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.WallowConstants;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.db.model.PlayerGuideEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.PlayerOffLineEvent;
import com.pwrd.war.gameserver.common.i18n.LangService;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.common.i18n.constants.SceneLangConstants_60000;
import com.pwrd.war.gameserver.human.manager.HumanInitManager;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerExitReason;
import com.pwrd.war.gameserver.player.PlayerState;
import com.pwrd.war.gameserver.player.RoleInfo;
import com.pwrd.war.gameserver.player.msg.CGCreateRole;
import com.pwrd.war.gameserver.player.msg.CGEnterScene;
import com.pwrd.war.gameserver.player.msg.CGPlayerChargeDiamond;
import com.pwrd.war.gameserver.player.msg.CGPlayerCookieLogin;
import com.pwrd.war.gameserver.player.msg.CGPlayerEnter;
import com.pwrd.war.gameserver.player.msg.CGPlayerGuide;
import com.pwrd.war.gameserver.player.msg.CGPlayerGuideList;
import com.pwrd.war.gameserver.player.msg.CGPlayerLogin;
import com.pwrd.war.gameserver.player.msg.CGPlayerQueryAccount;
import com.pwrd.war.gameserver.player.msg.CGRoleTemplate;
import com.pwrd.war.gameserver.player.msg.GCNotifyException;
import com.pwrd.war.gameserver.player.msg.GCPlayerGuideList;
import com.pwrd.war.gameserver.player.msg.GCRoleTemplate;

/**
 * 玩家消息处理器，处理玩家登录、换线、换地图等消息，主线程中调用
 * 
 */
public class PlayerMessageHandler {
	
	
	protected OnlinePlayerService onlinePlayerService;

	protected LangService langService;
	

	protected PlayerMessageHandler(OnlinePlayerService playerManager,LangService langService) {
		this.onlinePlayerService = playerManager;
		this.langService = langService;
	}

	/**
	 * 玩家主动下线时调用此方法
	 * 
	 * @param player
	 */
	public void handlePlayerCloseSession(final Player player) {
		if (player != null) {
			final IMessage offlineMsg = new SysInternalMessage() {
				@Override
				public void execute() {
					if (player.getState() != PlayerState.logouting) {
						if (player.getWallowFlag() == WallowConstants.WALLOW_FLAG_ON) {
							Globals.getWallowService().onPlayerExit(player.getPassportId());
						}
						PlayerOffLineEvent e = new PlayerOffLineEvent(player);
						Globals.getEventService().fireEvent(e);
						
						Globals.getOnlinePlayerService().offlinePlayer(player,player.exitReason == null ? PlayerExitReason.LOGOUT : player.exitReason);
						
					}
				}
			};
			Globals.getMessageProcessor().put(offlineMsg);
		}
	}


	public void handlePlayerLogin(Player player, CGPlayerLogin cgPlayerLogin) 
	{
		String account = cgPlayerLogin.getAccount();
		String password = cgPlayerLogin.getPassword();
		
		// 验证用户名和密码不能为空
		if (account == null || account.equals("")) {
			return;
		}
		if (password == null || password.equals("")) {
			return;
		}
		
		if (Globals.getOnlinePlayerService().isFull()) {
			// 服务器人满了
			player.sendMessage(new GCNotifyException(PlayerLangConstants_30000.LOGIN_ERROR_SERVER_FULL,
					langService.read( PlayerLangConstants_30000.LOGIN_ERROR_SERVER_FULL)));
			player.exitReason = PlayerExitReason.SERVER_IS_FULL;
			player.disconnect();
			return;
		}
		
		Globals.getLoginLogicalProcessor().playerLogin(player, account,	password);	
	}

	public void handlePlayerCookieLogin(Player player,CGPlayerCookieLogin cgPlayerCookieLogin) 
	{
		String cookieValue = cgPlayerCookieLogin.getCookieValue();
		// 验证cookie不能为空
		if (cookieValue == null || cookieValue.equals("")) {
			return;
		}
		
		if (Globals.getOnlinePlayerService().isFull()) {
			// 服务器人满了
			player.sendMessage(new GCNotifyException(PlayerLangConstants_30000.LOGIN_ERROR_SERVER_FULL,
					langService.read( PlayerLangConstants_30000.LOGIN_ERROR_SERVER_FULL)));
			player.exitReason = PlayerExitReason.SERVER_IS_FULL;
			player.disconnect();
			return;
		}

		Globals.getLoginLogicalProcessor().playerCookieLogin(player, cookieValue);		
	}
	
	/**
	 * 客户段请求角色模板
	 * 简单的消息，不涉及逻辑，就在这里直接处理了
	 * @param player
	 * @param cgRoleTemplate
	 */
	public void handleRoleTemplate(Player player, CGRoleTemplate cgRoleTemplate) 
	{
		GCRoleTemplate gcRoleTemplate = new GCRoleTemplate();
		player.sendMessage(gcRoleTemplate);
	}
	
	 

	
	public void handleCreateRole(Player player, CGCreateRole msg) 
	{
		String roleName = msg.getName();
		RoleInfo role = new RoleInfo();
		role.setName(roleName);
		role.setPassportId(player.getPassportId());
		role.setSex(msg.getSex());
		role.setVocation(msg.getVocation());
		role.setCamp(msg.getCamp());
		
//		PetSelection selection = new PetSelection();
//		//TODO 
//		selection.setTemplateId(41011001);
////		role.setSelection(selection);
		
		
		Globals.getLoginLogicalProcessor().createRole(player, role);
	}
	


	/**
	 * 选择角色,由于默认肯定是一个角色,所以命名为PlayerEnter,实际就是SelectRole
	 * @param player
	 * @param cgPlayerEnter
	 */
	public void handlePlayerEnter(final Player player, CGPlayerEnter cgPlayerEnter) 
	{
		String roleUUID = cgPlayerEnter.getRoleUUID();
		//人数已经满,不能进入服务器,断开连接
		if (!Globals.getOnlinePlayerService().onPlayerEnterServer(player, roleUUID)) {
			Loggers.gameLogger.warn("player " + player.getPassportName() + " can't enter server");
			player.sendMessage(new GCNotifyException(PlayerLangConstants_30000.LOGIN_ERROR_SERVER_FULL,
					Globals.getLangService().read(PlayerLangConstants_30000.LOGIN_ERROR_SERVER_FULL)));
			player.exitReason = PlayerExitReason.SERVER_ERROR;
			player.disconnect();
			return ;
		}		
		//需要判断合法性
		Globals.getLoginLogicalProcessor().selectRole(player, roleUUID);
	}
	
	/**
	 * 
	 * 进入地图
	 * 
	 * @see HumanInitManager#noticeHuman
	 * @param player
	 * @param cgEnterScene
	 */
	public void handleEnterScene(final Player player, CGEnterScene cgEnterScene) 
	{
		if(player.getState() == PlayerState.incoming){//只有在第一次进入场景的时候需要设置为entering
			player.setState(PlayerState.entering);
		}
		//人数出现错误,不能进入场景,断开连接
		if (!Globals.getSceneService().onPlayerEnterScene(player, cgEnterScene.getLine())) {
			Loggers.gameLogger.warn("player " + player.getPassportName() + " can't enter scene, targetSceneId :" + player.getTargetSceneId());
			player.sendMessage(new GCNotifyException(SceneLangConstants_60000.ENTER_SCENE_FAIL,
					Globals.getLangService().read(SceneLangConstants_60000.ENTER_SCENE_FAIL)));
			player.exitReason = PlayerExitReason.SERVER_ERROR;
			player.disconnect();
			return;
		}
	}

	/**
	 * 玩家充值消息
	 * @param player
	 * @param cgPlayerChargeGold
	 */
	public void handlePlayerChargeDiamond(Player player,CGPlayerChargeDiamond cgPlayerChargeDiamond) {
		int mmCost = cgPlayerChargeDiamond.getMmCost();
		Globals.getChargeLogicalProcessor().chargeGold(player, mmCost);
	}

	/**
	 * 玩家账户查询
	 * @param player
	 * @param cgPlayerQueryAccount
	 */
	public void handlePlayerQueryAccount(Player player,CGPlayerQueryAccount cgPlayerQueryAccount) {
		Globals.getChargeLogicalProcessor().queryPlayerAccount(player);
	}
	//取新手引导列表
	public void handlePlayerGuideList(Player player, CGPlayerGuideList msg){
		class GetList implements IIoOperation{
			public Player player;
			@Override
			public int doStop() {
				return STAGE_STOP_DONE;
			}
			@Override
			public int doStart() {
				return STAGE_START_DONE;
			}
			@Override
			public int doIo() {
				PlayerGuideEntity ent = Globals.getDaoService().getPlayerGuideDAO().getByPlayerUUID(player.getRoleUUID());
				if(ent != null){
					GCPlayerGuideList rmsg = new GCPlayerGuideList(ent.getIds());
					player.sendMessage(rmsg);
				}else{
					GCPlayerGuideList rmsg = new GCPlayerGuideList("");
					player.sendMessage(rmsg);
				}
				return STAGE_IO_DONE;
			} 
		};
		GetList op = new GetList();
		op.player = player;
		Globals.getAsyncService().createOperationAndExecuteAtOnce(op);		
	}
	//新手引导保存
	public void handlePlayerGuide(Player player, CGPlayerGuide msg){
		class Save implements IIoOperation{
			public String playerUUID;
			public String guideId;
			@Override
			public int doStop() {
				return STAGE_STOP_DONE;
			}
			@Override
			public int doStart() {
				return STAGE_START_DONE;
			}
			@Override
			public int doIo() {
				PlayerGuideEntity ent = Globals.getDaoService().getPlayerGuideDAO().getByPlayerUUID(playerUUID);
				if(ent == null){
					ent = new PlayerGuideEntity();
					ent.setPlayerUUID(playerUUID);
					ent.setIds(guideId);
					Globals.getDaoService().getPlayerGuideDAO().save(ent);
				}else{
					ent.setIds(ent.getIds()+","+guideId);
					Globals.getDaoService().getPlayerGuideDAO().saveOrUpdate(ent);
				}
				return STAGE_IO_DONE;
			} 
		};
		Save op = new Save();
		op.playerUUID = player.getRoleUUID();
		op.guideId = msg.getGuideId();
		Globals.getAsyncService().createOperationAndExecuteAtOnce(op);		
	}
}
