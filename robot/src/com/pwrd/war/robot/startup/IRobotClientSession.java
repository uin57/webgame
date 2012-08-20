package com.pwrd.war.robot.startup;

import org.apache.mina.common.IoSession;

import com.pwrd.war.core.session.ISession;
import com.pwrd.war.robot.Robot;

public interface IRobotClientSession extends ISession{
	
	void setRobot(Robot player);

	Robot getRobot();
	
	IoSession getIoSession();

}
