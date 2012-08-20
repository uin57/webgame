package com.pwrd.war.robot.startup;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageParseException;
import com.pwrd.war.core.msg.recognizer.BaseMessageRecognizer;
import com.pwrd.war.robot.msg.RobotMsgRegister;





public class RobotClientMsgRecognizer extends BaseMessageRecognizer{
	 
	@Override
	public IMessage createMessageImpl(short type) throws MessageParseException {
		Class<?> clazz = RobotMsgRegister.getMsgs().get(type);
		if (clazz == null) {
			throw new MessageParseException("Unknow msgType:" + type);
		} else {
			try {
				IMessage msg = (IMessage) clazz.newInstance();
				return msg;
			} catch (Exception e) {
				throw new MessageParseException(
						"Message Newinstance Failed，msgType:" + type, e);
			}
		}
	}

}
