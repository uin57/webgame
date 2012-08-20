package com.pwrd.war.core.client.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 * Socket连接创建工厂
 * 
 * 
 */
public class SocketFactory {
	/** 服务器的地址 */
	private String serverHost;
	/** 服务器监听的端口 */
	private int serverPort;
	/** Socket的配置 */
	private final SocketConfig socketConfig;

	/**
	 * 构建到指定地址和端口的Socket连接工厂,使用默认的SocketConfig配置
	 * 
	 * @param serverHost
	 * @param serverPort
	 */
	public SocketFactory(String serverHost, int serverPort) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.socketConfig = new SocketConfig();
	}

	/**
	 * 构建到指定地址和端口的Socket连接工厂,使用指定的SocketConfig配置
	 * 
	 * @param serverHost
	 * @param serverPort
	 * @param socketConfig
	 */
	public SocketFactory(String serverHost, int serverPort, SocketConfig socketConfig) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.socketConfig = new SocketConfig(socketConfig);
	}

	/**
	 * 打开一个Socket连接,并使用{@link SocketFactory}的配置进行配置
	 * 
	 * @return
	 * @throws IOException
	 */
	public Socket openSocket() throws IOException {
		InetAddress _addr = InetAddress.getByName(serverHost);
		SocketAddress _serverAddress = new InetSocketAddress(_addr, this.serverPort);
		Socket _socket = new Socket();
		configureSocket(_socket);
		_socket.connect(_serverAddress, socketConfig.connectionTimeOut);
		return _socket;
	}

	/**
	 * 使用{@link SocketFactory}的配置对{@link Socket}进行配置
	 * 
	 * @param socket
	 * @throws SocketException
	 */
	private void configureSocket(final Socket socket) throws SocketException {
		socket.setKeepAlive(socketConfig.keepAlive);
		socket.setTcpNoDelay(socketConfig.tcpNoDelay);
		socket.setReuseAddress(true);
		socket.setSoTimeout(socketConfig.readTimeOut);
		socket.setReceiveBufferSize(socketConfig.receiveBufSize);
		socket.setSendBufferSize(socketConfig.sendBufSize);
	}

	/**
	 * Socket的配置
	 *
	 * 
	 */
	public static class SocketConfig {
		/** 连接超时默认值{@value} */
		private static final int DEFAULT_CONNECTION_TIMEOUT = 30 * 1000;
		/** 读取超时默认值{@value } */
		private static final int DEFAULT_READ_TIMEOUT = 10 * 1000;
		/** Keep Alive默认值{@value} */
		private static final boolean DEFAULT_KEEPALIVE = true;
		/** Tcp nodelay默认值{@value} */
		private static final boolean DEFAULT_TCPNODELAY = true;
		/** 接收缓存区默认大小{@value} 字节 */
		private static final int DEFAULT_RECEIVE_BUFSIZE = 512;
		/** 发送缓存区黑夜大小{@value} 字节 */
		private static final int DEFAULT_SEND_BUFSIZE = 512;

		/** 连接超时,单位毫秒,默认值{@value #DEFAULT_CONNECTION_TIMEOUT} */
		public int connectionTimeOut = DEFAULT_CONNECTION_TIMEOUT;
		/** 读取超时,单位毫秒,默认值{@value #DEFAULT_READ_TIMEOUT} */
		public int readTimeOut = DEFAULT_READ_TIMEOUT;
		/** 设置SO_KEEPALIVE选项,默认值{@value #DEFAULT_KEEPALIVE} */
		public boolean keepAlive = DEFAULT_KEEPALIVE;
		/** 设置TCP_NODELAY选项,默认值{@value #DEFAULT_TCPNODELAY} */
		public boolean tcpNoDelay = DEFAULT_TCPNODELAY;
		/** 设置接收缓存区大小,默认值{@value #DEFAULT_RECEIVE_BUFSIZE}字节 */
		public int receiveBufSize = DEFAULT_RECEIVE_BUFSIZE;
		/** 设置发送缓存区大小,默认值{@value #DEFAULT_SEND_BUFSIZE}字节 */
		public int sendBufSize = DEFAULT_SEND_BUFSIZE;

		/**
		 * 使用默认配置
		 */
		public SocketConfig() {
		}

		/**
		 * 从<code>config</code>中拷贝配置
		 * 
		 * @param config
		 */
		public SocketConfig(SocketConfig config) {
			this.connectionTimeOut = config.connectionTimeOut;
			this.readTimeOut = config.readTimeOut;
			this.keepAlive = config.keepAlive;
			this.tcpNoDelay = config.tcpNoDelay;
			this.receiveBufSize = config.receiveBufSize;
			this.sendBufSize = config.sendBufSize;
		}
	}

}
