package com.pwrd.war.robot.msg;

import com.pwrd.war.core.msg.sys.SessionClosedMessage;
import com.pwrd.war.robot.startup.IRobotClientSession;

public class RobotClientSessionClosedMsg extends SessionClosedMessage<IRobotClientSession>{

	public RobotClientSessionClosedMsg(IRobotClientSession sender) {
		super(sender);
	}
	
	@Override
	public void execute() {		
		
	}

}
