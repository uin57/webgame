package com.pwrd.war.gameserver.player;

import net.sf.json.JSONObject;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.WallowConstants;
import com.pwrd.war.core.enums.AccountState;
import com.pwrd.war.core.util.JsonUtils;
import com.pwrd.war.db.model.UserInfo;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.player.async.CreateRoleOperation;
import com.pwrd.war.gameserver.player.async.LoadPlayerRoleOperation;
import com.pwrd.war.gameserver.player.async.PlayerRolesLoad;
import com.pwrd.war.gameserver.player.auth.UserAuth;
import com.pwrd.war.gameserver.player.auth.UserAuth.AuthResult;
import com.pwrd.war.gameserver.player.msg.GCNotifyException;
import com.pwrd.war.gameserver.player.msg.GCRoleInfo;

/**
 * 处理玩家登录，创建角色，选择角色等相关逻辑
 * 
 */
public class LoginLogicalProcessor {

	private UserAuth userAuth;

	public LoginLogicalProcessor(UserAuth userAuth) {
		this.userAuth = userAuth;
	}
	
 
	
	/**
	 * 处理使用用户名和密码登录
	 * 
	 * @param player
	 * @param account
	 * @param password
	 */
	public void playerLogin(Player player, String account, String password) {
		boolean authResult = authUserWithUserPass(player, account, password);
		if (!authResult) {
			player.disconnect();
		}
	}

	/**
	 * 处理使用cookie登录
	 * 
	 * @param player
	 * @param account
	 * @param password
	 */
	public void playerCookieLogin(Player player, String cookieValue) {
		boolean authResult = authUserWithCookie(player, cookieValue);
		if (!authResult) {
			player.disconnect();
		}
	}

	/**
	 * @param player
	 * @param account
	 * @param password
	 * @param loginType
	 */
	private boolean authUserWithUserPass(Player player, String account, String password) {
		if (!checkSession(player)) {
			return false;
		}
		// 获取玩家IP
		final String ip = player.getSession().getIp();
		AuthResult result = this.userAuth.auth(account.trim(), password, ip);
		return handleAuthResult(player, result);
	}
	
	/**
	 * @param player
	 * @param cookieValue
	 * @return
	 */
	private boolean authUserWithCookie(Player player, String cookieValue) {
		if (!checkSession(player)) {
			return false;
		}
		// 获取玩家IP
		final String ip = player.getSession().getIp();
		AuthResult result = this.userAuth.auth(cookieValue, ip);
		return handleAuthResult(player, result);
	}

	private boolean checkSession(Player player) {
		player.setState(PlayerState.connected);
		// 判断session : 验证过程中可能掉线
		if (player.getSession() == null) {
			return false;
		}
		return true;
	}
	
	private boolean handleAuthResult(Player player, final AuthResult result) {
		if (result == null) {
			// 登录失败
			player.sendErrorMessage(
					Globals.getLangService().read(PlayerLangConstants_30000.LOGIN_VALIDATE_ERROR)
					);
			return false;
		}
		if (!result.success) {
			// 登录失败
			//player.sendErrorMessage(result.message);
			player.sendMessage(new GCNotifyException(PlayerLangConstants_30000.DC_COOKIE_VALID_FAIL, result.message));
			player.exitReason = PlayerExitReason.LOGOUT;
			player.sendErrorPromptMessage(PlayerLangConstants_30000.DC_COOKIE_VALID_FAIL);
			return false;
		}

		final UserInfo userInfo = result.userInfo;
		
		final long localAccOnlineTime = result.localAccOnlineTime;

		// 登陆墙不允许普通玩家登陆
		if (userInfo.getRole() == 0
				&& Globals.getServerConfig().isLoginWallEnabled()) {
			player.sendErrorPromptMessage(Globals.getLangService()
					.read(PlayerLangConstants_30000.LOGIN_ERROR_WALL_CLOSED));
			return false;
		}

		if (Globals.getServerConfig().isWallowControlled()) {
			
			if (userInfo.getWallowFlag() == WallowConstants.WALLOW_FLAG_ON) {		
				// 登陆
				if(localAccOnlineTime >= 3 * 12 * WallowConstants.TIME_EXTEND_UNIT)
				{			
					player.sendErrorPromptMessage(PlayerLangConstants_30000.WALLOW_CANNOT_LOGIN_STATUS);
					player.sendMessage(new GCNotifyException(PlayerLangConstants_30000.WALLOW_CANNOT_LOGIN_STATUS,
							Globals.getLangService().read(PlayerLangConstants_30000.WALLOW_CANNOT_LOGIN_STATUS)));
					player.exitReason = PlayerExitReason.WALLOW_KICK;
					return false;
				}
			}
		}
		
		// 检查是否处于锁定等非法状态
		if (!checkState(player, userInfo)) {
			return false;
		}
		//设置Player的passwordId
		player.setPassportId(userInfo.getId());
		player.setWallowFlag(userInfo.getWallowFlag());
		
		Player existPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(userInfo.getId());
		
		// 玩家已在线
		if (existPlayer != null) {
			// 踢掉当前在线玩家，通知当前登录玩家稍后重试
			if (existPlayer.getState() != PlayerState.logouting) {		
				existPlayer.sendMessage(new GCNotifyException(PlayerLangConstants_30000.LOGIN_ONLINE_ERROR, 
						Globals.getLangService().read(PlayerLangConstants_30000.LOGIN_ONLINE_ERROR)));
				existPlayer.exitReason = PlayerExitReason.MULTI_LOGIN;
				existPlayer.disconnect();
				
//				player.setPassportId(userInfo.getId());
//				player.sendMessage(new GCNotifyException(NotifyCode.DC_LOGIN_ON_ANOTHER_CLIENT.getCode(),langService.read(LangConstants.LOGIN_ONLINE_ERROR)));
//				player.exitReason =  PlayerExitReason.MULTI_LOGIN;
//				return false;
			}
			Loggers.msgLogger.info(existPlayer.getPassportName()+"被顶下线!");
			Globals.getOnlinePlayerService().addWillAuthPlayer(player);
			return true;
		}
		
		
		player.setState(PlayerState.auth);  
		Globals.getLoginLogicalProcessor().loadCharacters(player, false, "");
		
		return true;
	}
	/**
	 * 被顶下线的玩家告诉现在的可以进行登录了
	 * @author xf
	 */
	public void letWillAuthPlayerAuth(Player player){
		player.setState(PlayerState.auth);  
		Globals.getLoginLogicalProcessor().loadCharacters(player, false, "");
	}
	
	/**
	 * 检查玩家状态
	 * 
	 * @return
	 */
	private boolean checkState(final Player player, final UserInfo userInfo) {
		AccountState _state = AccountState.indexOf(userInfo.getLockStatus());
		if (_state == AccountState.NORMAL) {
			return true;
		} else if (_state == AccountState.LOCKED) {
			// 获取锁定原因
			String _reason = null;
			if (userInfo.getProps() != null) {
				JSONObject _json = JSONObject.fromObject(userInfo.getProps());
				JSONObject _lockJson = JsonUtils.getJSONObject(_json, "lock");
				if (_lockJson != null) {
					_reason = JsonUtils.getString(_lockJson, "reason");
				}
			}
			player.sendErrorPromptMessage(Globals.getLangService().read( PlayerLangConstants_30000.LOGIN_ERROR_ACCOUNT_LOCKED,
					_reason == null ? " " : _reason));
		} else {
			player.sendErrorPromptMessage(Globals.getLangService()
					.read(PlayerLangConstants_30000.LOGIN_ERROR_ACCOUNT_STATE));
		}
		return false;
	}

	/**
	 * 玩家选择角色
	 * 
	 * @param player
	 * @param roleUUID
	 */
	public void selectRole(Player player, String roleUUID) {		
		// 正常登录，设置为加载角色列表 状态
		player.setState(PlayerState.loading);
		// 异步加载角色列表
		LoadPlayerRoleOperation _loadTask = new LoadPlayerRoleOperation(player,roleUUID);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_loadTask);		
	}

	/**
	 * 处理玩家创建角色
	 * 
	 * @param player
	 * @param role
	 */
	public void createRole(Player player, RoleInfo role) {
		// 检查是否达到角色数上限
		RoleInfo roles = player.getRole();
//		if (roles != null && roles.size() >= SharedConstants.MAX_ROLE_PER_PLAYER) {
//			player.sendErrorMessage(langService
//					.read(LangConstants.ROLE_CREATE_ERROR_MAX));
//			return;
//		}
		if(roles != null){//已经建立角色了
			player.sendErrorMessage(Globals.getLangService().read(PlayerLangConstants_30000.ROLE_CREATE_ERROR_MAX));
			return;
		}

		
		
		
		// 异步判断和创建
		CreateRoleOperation _createTask = new CreateRoleOperation(player, role);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_createTask);
	}

	/**
	 * 玩家登录时的接口方法
	 * 
	 * @param player
	 * @return
	 */
	public boolean loadCharacters(Player player, boolean isForwardEnter, String charId) {
		// 正常登录，设置为加载角色列表 状态
		player.setState(PlayerState.loadingrolelist);
		// 异步加载角色列表
		PlayerRolesLoad _loadTask = new PlayerRolesLoad(player,isForwardEnter, charId);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_loadTask);
		return true;
	}

	/**
	 * 结束异步加载角色列表流程 ： 加载完成后续处理
	 * 
	 * . 如果是第一次创建角色，则直接进入游戏 . 如果不是，则发送列表给客户端
	 * 
	 * @param player
	 */
	public void onCharsLoad(Player player, boolean isForwardEnter,String charId) {
//		RoleInfo[] roleInfos = player.getRoles().toArray(new RoleInfo[0]);
//		int defaultIndex = 0;
////		if (charId > 0) {
//		if (!charId.equals("")) {
//			for (RoleInfo roleInfo : roleInfos) {
////				if (roleInfo.getRoleUUID() == charId) {
//				if (roleInfo.getRoleUUID().equals(charId)) {
//					break;
//				}
//				defaultIndex++;
//			}
//		}
		RoleInfo role = player.getRole();
		if(role == null)role = new RoleInfo();
		player.setState(PlayerState.waitingselectrole);
		GCRoleInfo roleMsg = new GCRoleInfo();
		roleMsg.setCamp(role.getCamp());
		roleMsg.setName(role.getName());
		roleMsg.setRoleUUID(role.getRoleUUID());
		roleMsg.setSex(role.getSex());
		roleMsg.setVocation(role.getVocation());
		player.sendMessage(roleMsg);		
	}

	
}
