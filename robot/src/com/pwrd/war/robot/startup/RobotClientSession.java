package com.pwrd.war.robot.startup;

import org.apache.mina.common.IoSession;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.session.MinaSession;
import com.pwrd.war.robot.Robot;

public class RobotClientSession extends MinaSession implements IRobotClientSession {
	
	/** 当前会话绑定的robot，登录验证成功之后实例化 */
	private Robot robot;

	public RobotClientSession(IoSession session) {
		super(session);
	}

	@Override
	public Robot getRobot() {
		return robot;
	}

	@Override
	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	@Override
	public void write(IMessage msg) {
		if (session == null || !session.isConnected()) {
			return;
		}
		Robot.robotLogger.debug("【Send】" + msg);
		session.write(msg);
	}
	
}
