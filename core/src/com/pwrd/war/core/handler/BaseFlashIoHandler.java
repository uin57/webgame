package com.pwrd.war.core.handler;

import org.apache.mina.common.IoSession;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.core.msg.special.PolicyMessage;
import com.pwrd.war.core.server.AbstractIoHandler;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.core.util.ErrorsUtil;

/**
 * 接收从Flash发送的网络消息处理器
 * 
  *
 * 
 */
public abstract class BaseFlashIoHandler<T extends ISession> extends
		AbstractIoHandler<T> {
	/** Flash客户端建立socket连接时发送的policy请求的响应 */
	private final String flashSocketPolicy;

	/**
	 * 
	 * @param flashSocketPolicy
	 *            Flash客户端建立socket连接时发送的policy请求的响应
	 */
	public BaseFlashIoHandler(String flashSocketPolicy) {
		if (flashSocketPolicy == null
				|| (flashSocketPolicy = flashSocketPolicy.trim()).length() == 0) {
			throw new IllegalArgumentException(ErrorsUtil.error(
					com.pwrd.war.common.constants.CommonErrorLogInfo.ARG_NOT_NULL_EXCEPT, "",
					"flashSocketPolicy"));
		}
		this.flashSocketPolicy = flashSocketPolicy;
	}

	/**
	 * 在此对Flash socket policy请求做特殊的处理
	 */
	@Override
	public void messageReceived(IoSession session, Object obj) {
		if (!(obj instanceof IMessage)) {
			return;
		}
		IMessage msg = (IMessage) obj;
		if (msg.getType() == MessageType.FLASH_POLICY) {
			// 特殊的处理,响应Flash socket policy请求
			PolicyMessage _pm = (PolicyMessage) msg;
			_pm.setPolicy(flashSocketPolicy);
			session.write(msg);
			session.close();
			return;
		}
		super.messageReceived(session, obj);
	}
}
