package com.pwrd.war.robot.msg;

import com.pwrd.war.core.msg.sys.SessionOpenedMessage;
import com.pwrd.war.robot.startup.IRobotClientSession;

public class RobotClientSessionOpenedMsg extends SessionOpenedMessage<IRobotClientSession>{

	public RobotClientSessionOpenedMsg(IRobotClientSession session) {
		super(session);
	}

	@Override
	public void execute() {	
		
	}
}
