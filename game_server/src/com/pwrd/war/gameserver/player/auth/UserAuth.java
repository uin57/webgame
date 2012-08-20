package com.pwrd.war.gameserver.player.auth;

import com.pwrd.war.db.model.UserInfo;

/**
 * 用户认证接口
 * 
 */
public interface UserAuth {
	/**
	 * 验证用户名和密码
	 * 
	 * @param userName
	 * @param password
	 * @param ip
	 * @param loginType
	 * @return
	 */
	public AuthResult auth(String userName, String password, String ip);
	
	/**
	 * 用cookie进行验证
	 * 
	 * @param cookieValue
	 * @param ip
	 * @return
	 */
	public AuthResult auth(String cookieValue, String ip);

	/**
	 * 验证用户名和密码的结果
	 * 
	 * @author <a href="mailto:dongyong.wang@opi-corp.com">wang dong yong<a>
	 * 
	 */
	public static final class AuthResult {
		/** 验证是否成功 */
		public boolean success;
		/** 验证失败是的提示消息 */
		public String message;
		/** 验证成功后取得的对应UserInfo对象 */
		public UserInfo userInfo;
		/** 玩家在线累计时长，秒数，Local接口累计 */
		public long localAccOnlineTime;
	}
}
