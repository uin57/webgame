package com.pwrd.war.logserver.telnet;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.TransportType;
import org.apache.mina.transport.socket.nio.SocketSessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.logserver.telnet.command.AbstractTelnetCommand;

/**
 * Telnet协议处理
 * 
 * 
 */
public class TelnetIoHandler extends IoHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger("telnet");

	/** 注册Telnet命令 */
	private final Map<String, AbstractTelnetCommand> commands = new HashMap<String, AbstractTelnetCommand>();

	public TelnetIoHandler() {
	}

	/**
	 * 注册命令
	 * 
	 * @param command
	 */
	public void register(AbstractTelnetCommand command) {
		if (this.commands.containsKey(command.getCommandName())) {
			if (logger.isWarnEnabled()) {
				logger.warn("The command [" + command.getCommandName()
						+ "] has been already registed,which will be replaced.");
			}
		}
		this.commands.put(command.getCommandName().toUpperCase(), command);
	}

	@Override
	public void messageReceived(IoSession session, Object msgObject) throws Exception {
		final String _message = msgObject.toString().trim();
		if (_message.length() == 0) {
			return;
		}
		final String[] msgArray = _message.split(" ");
		final String _cmd = msgArray[0].toUpperCase();
		final AbstractTelnetCommand _command = this.commands.get(_cmd);
		if (_command == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("No registed command [" + _cmd + "]");
			}
			session.write("Unknown command:" + _cmd + "\r");
			return;
		}
		Map<String, String> _cmdParamMap = parseParamMap(msgArray);
		_command.exec(_message, _cmdParamMap, session);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		session.setAttachment(null);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		if (session.getTransportType() == TransportType.SOCKET) {
			((SocketSessionConfig) session.getConfig()).setReceiveBufferSize(2048);
		}
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (logger.isErrorEnabled()) {
			logger.error("Exception", cause);
		}
		session.close();
	}

	/**
	 * 解析命令中的参数,将所有格式为[param]=[value]格式的内容,以param为key,以value为值放到到map中
	 * 
	 * @param commandParams
	 * @return
	 */
	private Map<String, String> parseParamMap(String[] commandParams) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 1; i < commandParams.length; i++) {
			String _param = commandParams[i];
			String[] kv = _param.split("=");
			if (kv != null) {
				if (kv.length == 2) {
					map.put(kv[0], kv[1]);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("Skip param [" + _param + "]");
					}
				}
			}
		}
		return map;
	}
}
