package com.pwrd.war.core.session;

import com.pwrd.war.core.msg.IMessage;

/**
 * 封装会话的业务逻辑
 * 
 * 
 */
public interface ISession {

	/**
	 * 判断当前会话是否处于连接状态
	 * 
	 * @return
	 */
	public boolean isConnected();

	/**
	 * @param msg
	 */
	public void write(IMessage msg);

	/**
	 * 
	 */
	public void close();

	/**
	 * 出现异常时是否关闭连接
	 * 
	 * @return
	 */
	public boolean closeOnException();
}
