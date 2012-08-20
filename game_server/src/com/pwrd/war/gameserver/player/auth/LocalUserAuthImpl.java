package com.pwrd.war.gameserver.player.auth;

import java.io.IOException;

import net.sf.json.JSONObject;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.common.constants.WallowConstants;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.HttpUtil;
import com.pwrd.war.db.dao.UserInfoDao;
import com.pwrd.war.db.model.UserInfo;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;


/**
 * 使用Mop Local平台进行用户登录校验
 * 
 * 
 */
public class LocalUserAuthImpl implements UserAuth {

	private UserInfoDao userInfoDao;
	
//	private static final String VALID_URL = "http://219.238.234.185:9080/rest/";
	private static final String VALID_URL = "http://10.2.4.124/rest/";
	
	public LocalUserAuthImpl(UserInfoDao userInfoDao ) {
		Assert.notNull(userInfoDao);
		this.userInfoDao = userInfoDao;
	}
	
	@Override
	public AuthResult auth(String userName, String password, String ip) {
		return auth(userName, password, ip, LoginType.UserPasswordLogin);
	}
	
	@Override
	public AuthResult auth(String cookieValue, String ip) {
		return auth(cookieValue, "", ip, LoginType.UserCookieLogin);
	}
	
	private AuthResult auth(String userName, String password, String ip, LoginType loginType) {
		AuthResult result = new AuthResult();
		if (!Globals.getServerConfig().isTurnOnLocalInterface()) {
			result.success = false;
			result.message = Globals.getLangService().read(CommonLangConstants_10000.LOCAL_TURN_OFF);
			return result;
		}
		try {
			String res = HttpUtil.getUrl(VALID_URL+"valid?key="+userName);
			System.out.println(res);
			JSONObject json = JSONObject.fromObject(res);
			if("true".equals(json.getString("success"))){
				//取得是否开启需要防沉迷
				boolean bw = false;
				if(Globals.getServerConfig().isWallowControlled() ){
					bw = Globals.getWallowService().needWallow(json.optString("id"));
				}
				UserInfo userInfo = getUserInfoByPassportId(json.optString("id"),
						json.getString("name"), ip, bw);
				result.success = true;
				result.userInfo = userInfo;
				if(bw){
					//取累计在线时长 
					result.localAccOnlineTime = Globals.getWallowService().getWallowTime(json.optString("id"), 0);
				}else{
					result.localAccOnlineTime = 0;
				}
			}else{
				result.success = false;
				result.message = Globals.getLangService().read(PlayerLangConstants_30000.LOGIN_VALIDATE_ERROR);
			}
		} catch (IOException e) { 
			e.printStackTrace();
			result.success = false;
			result.message = Globals.getLangService().read(PlayerLangConstants_30000.LOGIN_CANT_LOGIN);
		}
//		// 请求local接口
//		LoginResponse response = Globals.getSynLocalService().validateUser(userName,
//				password, ip, loginType);
//		
//		
//		if (response.isSuccess()) {
//			UserInfo userInfo = getUserInfoByPassportId(response.getUserId(),
//					response.getUserName(), ip, response.isAntiIndulge());
//			result.success = true;
//			result.userInfo = userInfo;
//			result.localAccOnlineTime = Globals.getSynLocalService().queryOnlineTime(response.getUserId());			
//			
//		} else {
//			result.success = false;
//			result.message = getErrorInfo(response.getErrorCode());
//		}
		return result;
	}

	private UserInfo getUserInfoByPassportId(String passportId, String name,
			String ip, boolean wallow) {
		try {
			int wallowFlag = wallow ? WallowConstants.WALLOW_FLAG_ON
					: WallowConstants.WALLOW_FLAG_OFF;
			UserInfo userInfo = userInfoDao.get(passportId);
			if (userInfo == null) {
				userInfo = UserInfo.getDefaultUserInfo();
				userInfo.setId(passportId);
				userInfo.setName(name);
				userInfo.setLastLoginIp(ip);
				//TODO 默认都是GM
				userInfo.setRole(SharedConstants.ACCOUNT_ROLE_GM);
				userInfo.setWallowFlag(wallowFlag);
				userInfoDao.save(userInfo);
			}
			userInfo.setWallowFlag(wallowFlag);
			return userInfo;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 解析平台返回的错误代码
	 * 
	 * @param errorCode 平台返回的错误代码
	 * @return 解析出的错误信息
	 */
	private String getErrorInfo(int errorCode) {
		Integer _langKey = null;
		switch (errorCode) {
		case 1:
			_langKey = CommonLangConstants_10000.LOCAL_LOGIN_SIGN_AUTH_FAIL;
			break;
		case 2:
			_langKey = CommonLangConstants_10000.LOCAL_LOGIN_TIMESTAMP_EXPIRSE;
			break;
		case 3:
			_langKey = CommonLangConstants_10000.LOCAL_LOGIN_PARAM_FORMAT_ERROR;
			break;
		case 4:
			_langKey = CommonLangConstants_10000.LOCAL_LOGIN_PASS_ERR;
			break;
		case 5:
			_langKey = CommonLangConstants_10000.LOCAL_LOGIN_ACCOUNT_BLOCK;
			break;
		case 6:
			_langKey = CommonLangConstants_10000.LOCAL_LOGIN_PASS_PROTECT_ERR;
			break;
		case 7:
			_langKey = CommonLangConstants_10000.LOCAL_LOGIN_COOKIE_AUTH_FAIL;
			break;
		case 8:
			_langKey = CommonLangConstants_10000.LOCAL_LOGIN_TOKEN_AUTH_FAIL;
			break;
		case 9:
			_langKey = CommonLangConstants_10000.LOCAL_LOGIN_REGION_AUTH_FAIL;
			break;
		default:
			_langKey = CommonLangConstants_10000.LOCAL_UNKNOW_ERROR;
			break;
		}
		return Globals.getLangService().read(_langKey);
	}
	
}
