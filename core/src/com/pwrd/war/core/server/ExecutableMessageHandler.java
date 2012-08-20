package com.pwrd.war.core.server;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.msg.IMessage;

/**
 * 可自执行的消息处理器
 * 
 * 
 */
public class ExecutableMessageHandler implements IMessageHandler<IMessage> {
	@Override
	public void execute(IMessage message) {
		try {
			message.execute();
		} catch (Exception e) {
			Loggers.msgLogger.error(CommonErrorLogInfo.MSG_PRO_ERR_EXP, e);
		}
	}

	@Override
	public short[] getTypes() {
		return null;
	}
}
