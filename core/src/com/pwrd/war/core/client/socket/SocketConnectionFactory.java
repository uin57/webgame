package com.pwrd.war.core.client.socket;

import java.net.Socket;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * {@link SocketConnectionImpl}的创建工厂
 * 
 */
public class SocketConnectionFactory implements PoolableObjectFactory {
	private final SocketFactory socketFactory;

	public SocketConnectionFactory(SocketFactory socketFactory) {
		if (socketFactory == null) {
			throw new IllegalArgumentException("The socketFactory must not be null.");
		}
		this.socketFactory = socketFactory;
	}

	@Override
	public Object makeObject() throws Exception {
		Socket _socket = socketFactory.openSocket();
		return new SocketConnectionImpl(_socket);
	}

	@Override
	public void destroyObject(Object obj) throws Exception {
		ISocketConnection _connection = (ISocketConnection) obj;
		_connection.close();
	}

	@Override
	public boolean validateObject(Object obj) {
		ISocketConnection _connection = (ISocketConnection) obj;
		return _connection.isConnected();
	}

	@Override
	public void passivateObject(Object obj) throws Exception {
		//什么也不做
	}

	@Override
	public void activateObject(Object obj) throws Exception {
		//什么也不做
	}
	
}
