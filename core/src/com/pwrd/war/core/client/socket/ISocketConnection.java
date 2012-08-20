package com.pwrd.war.core.client.socket;

import java.io.IOException;

import org.apache.mina.common.ByteBuffer;

/**
 * Socket的客户端连接
 * 
 * 
 */
public interface ISocketConnection {
	/**
	 * 发送消息
	 * 
	 * @param buffer
	 * @throws IOException
	 */
	public abstract void send(ByteBuffer buffer) throws IOException;

	/**
	 * 接收消息
	 * 
	 * @throws IOException
	 */
	public abstract ByteBuffer receive() throws IOException;

	/**
	 * 关闭连接
	 * 
	 * @throws IOException
	 */
	public abstract void close() throws IOException;
	
	/**
	 * 强制关闭真实的连接,不管其是否在连接池中,直接关闭
	 * @throws IOException
	 */
	public abstract void closeNative() throws IOException;

	/**
	 * 检查连接是否处于连接状态
	 * 
	 * @return true,连接;false,未连接
	 */
	boolean isConnected();
}