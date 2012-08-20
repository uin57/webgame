package com.pwrd.war.core.log;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.ConnectFuture;
import org.apache.mina.common.IoConnector;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.IoSessionRecycler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.DatagramConnector;
import org.apache.mina.transport.socket.nio.DatagramConnectorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.core.codec.GameCodecFactory;
import com.pwrd.war.core.msg.BaseMessage;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageParseException;
import com.pwrd.war.core.msg.recognizer.IMessageRecognizer;

/**
 * 实现UdpLoggerClient
 * 
 */
public class UdpLoggerClient {
	private static final Logger logger = LoggerFactory
			.getLogger(UdpLoggerClient.class);
	private String ip;
	private int port;
	private int regionId;
	private int serverId;
	private IoSession session;

	private static UdpLoggerClient _inst=new UdpLoggerClient();

	private UdpLoggerClient() {

	}

	public synchronized static UdpLoggerClient getInstance() {
		if (_inst == null) {
			_inst = new UdpLoggerClient();
		}
		return _inst;
	}

	/**
	 * 初始化
	 * 
	 * @param ip
	 * @param port
	 */
	public void initialize(String ip, int port) {
		this.ip = ip;
		this.port = port;
		connect(ip, port);
	}

	/**
	 * 关闭
	 */
	public void close() {
		if (session != null) {
			session.close();
			this.session = null;
		}
	}

	/**
	 * 连接已经被关闭
	 */
	public void closed() {
		this.session = null;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public synchronized boolean isEnable() {
		return session != null;
	}

	public void sendMessage(IMessage msg) {
		try {
			if (session != null && session.isConnected()) {
				session.write(msg);
			}
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger
						.warn("[#GS.UdpLoggerClient.reconnect] [send log message exception]");
			}
		}
	}

	/**
	 * 重新建立连接
	 */
	public void reconnect() {
		this.connect(this.ip, this.port);
	}

	private synchronized void connect(String ip, int port) {
		if (this.session != null && this.session.isConnected()) {
			return;
		}

		DatagramConnectorConfig cfg = new DatagramConnectorConfig();
		cfg.setSessionRecycler(IoSessionRecycler.NOOP);
		cfg.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new GameCodecFactory(
						new IMessageRecognizer() {
							@Override
							public IMessage recognize(ByteBuffer buf)
									throws MessageParseException {
								return new FooBar();
							}

							@Override
							public int recognizePacketLen(ByteBuffer buff) {
								return -1;
							}
						})));

		SocketAddress addr = new InetSocketAddress(ip, port);
		IoConnector connector = new DatagramConnector();
		ConnectFuture _future = connector.connect(addr, new LogClientIoHandler(
				this), cfg);
		_future.join();
		if (_future.isConnected()) {
			session = _future.getSession();
		} else {
			throw new IllegalStateException(
					"Can't connect to the remote server.");
		}
	}

	private static class FooBar extends BaseMessage {

		@Override
		protected boolean readImpl() {
			return true;
		}

		@Override
		protected boolean writeImpl() {
			return true;
		}

		@Override
		public short getType() {
			return 0;
		}

		@Override
		public String getTypeName() {
			return null;
		}

		@Override
		public void execute() {
		}
	}

}
