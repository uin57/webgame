package com.pwrd.war.gameserver.player.auth;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.db.dao.UserInfoDao;
import com.pwrd.war.db.model.UserInfo;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;

/**
 * 使用数据库进行的用户验证,用于开发测试阶段
 * 
 * 
 */
public class DBUserAuthImpl implements UserAuth {
	private final UserInfoDao userInfoDao;

	public DBUserAuthImpl(UserInfoDao userInfoDao) {
		if (userInfoDao == null) {
			throw new IllegalArgumentException(
					"The userInfoDao must not be null.");
		}
		this.userInfoDao = userInfoDao;
	}

	@Override
	public AuthResult auth(String userName, String password, String ip) {
		AuthResult _result = new AuthResult();
		try {

			final UserInfo _userInfo = this.userInfoDao.getUserByName(userName,
					password);
			if (_userInfo != null) {
				_result.success = true;
				_result.userInfo = _userInfo;
			} else {
				_result.success = false;
				_result.message = Globals.getLangService().read(
						PlayerLangConstants_30000.LOGIN_VALIDATE_ERROR);
			}
		} catch (Exception e) {	
			Loggers.loginLogger
					.error(String
							.format("Login DBSServer Network break! User %s from %s cannot login!",
									userName, ip), e);
		}
		return _result;
	}

	@Override
	public AuthResult auth(String cookieValue, String ip) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @author xf
	 */
	public void register(String account ,String password){
		UserInfo info = new UserInfo();
//		info.setId(System.currentTimeMillis());
		info.setName(account);
		info.setPassword(password);
		userInfoDao.save(info);
	}
}
