package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.util.MathUtils;
import com.pwrd.war.gameserver.mail.msg.CGSendMail;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * 邮件测试
 * 
 * @author haijiang.jin
 *
 */
public class MailTestStrategy extends OnceExecuteStrategy {
	/** CG 消息 */
	private CGSendMail _cgmsg = new CGSendMail();
	
	/**
	 * 类参数构造器
	 * 
	 * @param robot
	 * @param delay 
	 * 
	 */
	public MailTestStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {
		int robotId = MathUtils.random(10000, 15000);
		this._cgmsg.setRecName("robot" + robotId);
		
		int contentId = MathUtils.random(0, 100);

		if (contentId <= 10) {
			this._cgmsg.setContent("你有病啊？");
        } else if (contentId <= 20) {
        	this._cgmsg.setContent("你有药啊？");
        } else if (contentId <= 30) {
        	this._cgmsg.setContent("你吃多少药？");
        } else if (contentId <= 40) {
        	this._cgmsg.setContent("你有多少药？");
        } else if (contentId <= 50) {
        	this._cgmsg.setContent("你吃多少药，我就有多少药？");
        } else if (contentId <= 60) {
        	this._cgmsg.setContent("你有多少药，我就吃多少药！");
        } else {
        	this._cgmsg.setContent("死猪，看我不揍死你！");
        }

		this._cgmsg.setTitle(this._cgmsg.getContent());
		this.getRobot().sendMessage(this._cgmsg);
		this.logInfo("mail");
	}

	@Override
	public void onResponse(IMessage message) {
	}
}
