package com.pwrd.war.core.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.apache.mina.util.NewThreadExecutor;
import org.slf4j.Logger;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.codec.GameCodecFactory;
import com.pwrd.war.core.msg.filter.FloodByteAttackFilter;
import com.pwrd.war.core.msg.filter.FloodCmdAttackFilter;
import com.pwrd.war.core.msg.recognizer.IMessageRecognizer;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.core.util.StringUtils;

/**
 * Server进程,每个Server由Mina IoAcceptor,MessageProcessor组成
 * 
 * 
 */
public class ServerProcess {
	private static final Logger logger =Loggers.serverLogger;
	/** 系统的可用cpu个数 */
	public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
	private final String name;
	private final String ports;
	private final String ip;
	private final IMessageRecognizer messageRecognizer;
	private final BaseIoHandler messageIoHandler;
	private final IMessageProcessor messageProcessor;
	private final List<IoAcceptor> acceptors = new ArrayList<IoAcceptor>();
	private final int perPortIoProcessor;
	/** MIS (后台)系统 IP 地址 */
	private final String misIps;

	/**
	 * 构造一个ServerProcess,每个监听端口使用一个IO处理线程
	 * 
	 * @param name
	 *            server process的名称
	 * @param ports
	 *            监听的端口
	 * @param messageRecognizer
	 *            消息识别器
	 * @param ioHandler
	 *            Mina IO消息处理器
	 * @param messageProcessor
	 *            业务消息处理器
	 * @exception IllegalArgumentException
	 */
	public ServerProcess(String ip, String name, String ports, IMessageRecognizer messageRecognizer,
			AbstractIoHandler<? extends ISession> ioHandler, IMessageProcessor messageProcessor, 
			String misIps) {
		this(ip, name, ports, messageRecognizer, ioHandler, messageProcessor, 1, misIps);
	}

	/**
	 * 构造一个ServerProcess,每个监听端口使用由ioProcessorPerPort指定的个数进行IO处理
	 * 
	 * @param name
	 *            server process的名称
	 * @param ports
	 *            监听的端口
	 * @param messageRecognizer
	 *            消息哀识别器
	 * @param ioHandler
	 *            Mina IO消息处理器
	 * @param messageProcessor
	 *            业务消息处理器
	 * @param ioProcessorPerPort
	 *            每个监听端口启用的IO处理线程数,通常不建议此参数的值大于系统可用的CPU个数,参见
	 *            <tt>AVAILABLE_PROCESSORS</tt>
	 * @exception IllegalArgumentException
	 */
	public ServerProcess(String ip, String name, String ports, IMessageRecognizer messageRecognizer,
			BaseIoHandler  ioHandler, IMessageProcessor messageProcessor, int ioProcessorPerPort, 
			String misIps) {
		if (name == null || ports == null || messageRecognizer == null || ioHandler == null || messageProcessor == null) {
			throw new IllegalArgumentException(CommonErrorLogInfo.ARG_NOT_NULL_EXCEPT);
		}
		if (ioProcessorPerPort <= 0) {
			throw new IllegalArgumentException(CommonErrorLogInfo.ARG_POSITIVE_NUMBER_EXCEPT);
		}
		String _ip = null;
		if (ip != null && (ip = ip.trim()).length() > 0) {
			_ip = ip;
		}
		this.ip = _ip;
		this.name = name;
		this.ports = ports;
		this.messageRecognizer = messageRecognizer;
		this.messageIoHandler = ioHandler;
		this.messageProcessor = messageProcessor;
		this.messageIoHandler.setMessageProcessor(this.messageProcessor);
		this.perPortIoProcessor = ioProcessorPerPort;
		this.misIps = misIps;
	}

	/**
	 * 取得与ServerProcess绑定的消息处理器
	 * 
	 * @return the messageProcessor
	 */
	public IMessageProcessor getMessageProcessor() {
		return messageProcessor;
	}

	/**
	 * 启动处理
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		log("Message processor thread starting...");
		messageProcessor.start();
		int[] portList = StringUtils.getIntArray(this.ports, ",");
		for (int i = 0; i < portList.length; i++) {
			SocketAcceptorConfig cfg = new SocketAcceptorConfig();
			cfg.setThreadModel(SingleThreadModel.getInstance());
			cfg.setReuseAddress(true);

			// 添加防洪水攻击过滤器
			cfg.getFilterChain().addFirst("floodCheck_byte", new FloodByteAttackFilter(this.misIps));
			cfg.getFilterChain().addFirst("floodCheck_cmd", new FloodCmdAttackFilter(this.misIps));

			cfg.getFilterChain().addLast("codec", new ProtocolCodecFilter(new GameCodecFactory(messageRecognizer)));
			int port = portList[i];
			InetSocketAddress _address = this.ip == null ? new InetSocketAddress(port)
					: new InetSocketAddress(ip, port);
			IoAcceptor acceptor = new SocketAcceptor(this.perPortIoProcessor, new NewThreadExecutor());
			try {
				acceptor.bind(_address, messageIoHandler, cfg);
			} catch (Exception e) {
				throw new RuntimeException("Bind address [" + _address.toString() + "] fail", e);
			}
			this.acceptors.add(acceptor);

			log("Listening on " + _address + ", io prcessors:" + this.perPortIoProcessor);
		}
	}

	/**
	 * 停止处理
	 */
	public void stop() {
		log("Message processor stoping...");
		messageProcessor.stop();
		log("Message processor stopped");
		for (IoAcceptor acceptor : this.acceptors) {
			log("Unbind acceprot " + acceptor);
			acceptor.unbindAll();
		}
		this.acceptors.clear();
		log("Server process stopped");
	}

	/**
	 * 获取服务的名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Server process name=" + this.name + " bind address=" + this.ip + " ports=" + this.ports;
	}

	private void log(String msg) {
		if (logger.isInfoEnabled()) {
			logger.info("Server process [" + this.name + "]:" + msg);
		}
	}
}
