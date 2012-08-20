package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.util.MathUtils;
import com.pwrd.war.gameserver.monster.VisibleMonsterInfo;
import com.pwrd.war.gameserver.monster.msg.GCMonsterList;
import com.pwrd.war.gameserver.player.msg.CGCreateRole;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * 创建角色测试
 * 
 * @author haijiang.jin
 *
 */
public class CreateRoleTestStrategy extends OnceExecuteStrategy {
	/**
	 * 类参数构造器
	 * 
	 * @param robot
	 * @param delay 
	 * 
	 */
	public CreateRoleTestStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {
		String robotName = "robot" + this.getRobot().getPid();
		int templateId = 100004;
		int hair = 1422004;
		int feature = 1522003;
		int job = 3;

		int r = MathUtils.random(0, 300);
		short allianceId = (short)0;

		if (r < 100) {
			allianceId = 1;
		} else if (r < 200) {
			allianceId = 2;
		} else if (r < 300) {
			allianceId = 4;
		}
		// 创建角色
		CGCreateRole cgmsg = new CGCreateRole(robotName, 0, 0, 0);
		this.getRobot().sendMessage(cgmsg);
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCMonsterList){
			VisibleMonsterInfo[] infos = (VisibleMonsterInfo[])((GCMonsterList) message).getMonsterInfo();
			if(infos != null && infos.length >0){
				for(VisibleMonsterInfo info : infos){
					System.out.println(info.getName());
				}
			}
		}
	}
}
