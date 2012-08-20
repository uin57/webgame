package com.pwrd.war.robot.startup;

import org.apache.mina.common.IoSession;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.server.AbstractIoHandler;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.msg.RobotClientSessionClosedMsg;
import com.pwrd.war.robot.msg.RobotClientSessionOpenedMsg;

public class RobotClientIoHandler extends AbstractIoHandler<RobotClientSession>{

	
	@Override
	public void sessionOpened(IoSession session) {
		if (Robot.robotLogger.isDebugEnabled()) {
			Robot.robotLogger.debug("Session opened");
		}
		curSessionCount.incrementAndGet();
		RobotClientSession s = this.createSession(session);
		session.setAttachment(s);
		IMessage msg = new RobotClientSessionOpenedMsg(s);
		msgProcessor.put(msg);
	}
	
	@Override
	public void sessionClosed(IoSession session) {
		if (Robot.robotLogger.isDebugEnabled()) {
			Robot.robotLogger.debug("Session closed");
		}
		curSessionCount.decrementAndGet();
		RobotClientSession ms = (RobotClientSession) session.getAttachment();
		if (ms == null) {
			return;
		}
		session.setAttachment(null);
		IMessage msg = new RobotClientSessionClosedMsg(ms);
		msgProcessor.put(msg);
	}


	@Override
	protected RobotClientSession createSession(IoSession session) {		
		return new RobotClientSession(session);
	}
	

}
