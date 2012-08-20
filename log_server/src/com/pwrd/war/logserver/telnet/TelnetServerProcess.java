package com.pwrd.war.logserver.telnet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用Telnet协议进行交互的服务进程
 * 
 * 
 */
public class TelnetServerProcess {
	private static final Logger logger = LoggerFactory.getLogger(TelnetServerProcess.class);
	/** 服务名称 */
	private final String name;
	/** 服务绑定的ip */
	private final String ip;
	/** 服务绑定的端口 */
	private final String port;
	private final TelnetIoHandler ioHandler;
	/** 启动的IoAcceptor接口 */
	private final List<IoAcceptor> acceptors = new ArrayList<IoAcceptor>();

	public TelnetServerProcess(String name, String ip, String port, TelnetIoHandler ioHandler) {
		this.name = name;
		String _ip = null;
		if (ip != null && (ip = ip.trim()).length() > 0) {
			_ip = ip;
		}
		this.ip = _ip;
		this.port = port;
		this.ioHandler = ioHandler;
	}

	public void start() throws IOException {
		SocketAcceptorConfig cfg = new SocketAcceptorConfig();
		cfg.setReuseAddress(true);
		TextLineCodecFactory _factory = new TextLineCodecFactory(Charset.forName("UTF-8"));
		_factory.setDecoderMaxLineLength(8096);
		cfg.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(_factory));
		int _port = Integer.parseInt(this.port);
		InetSocketAddress _address = this.ip == null ? new InetSocketAddress(_port) : new InetSocketAddress(ip, _port);
		IoAcceptor acceptor = new SocketAcceptor();
		acceptor.bind(_address, ioHandler, cfg);
		this.acceptors.add(acceptor);
		log("Listening on " + _address);
	}

	public void stop() {
		for (IoAcceptor acceptor : this.acceptors) {
			log("Unbind acceprot " + acceptor);
			acceptor.unbindAll();
		}
		this.acceptors.clear();
	}

	private void log(String msg) {
		if (logger.isInfoEnabled()) {
			logger.info("Server process [" + this.name + "]:" + msg);
		}
	}
}
