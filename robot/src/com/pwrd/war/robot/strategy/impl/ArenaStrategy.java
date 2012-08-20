/**
 * 
 */
package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.arena.ArenaRoleInfo;
import com.pwrd.war.gameserver.arena.msg.CGArenaFight;
import com.pwrd.war.gameserver.arena.msg.CGArenaList;
import com.pwrd.war.gameserver.arena.msg.GCArenaList;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * 
 * 竞技场策略
 * @author dengdan
 *
 */
public class ArenaStrategy extends OnceExecuteStrategy {
	
	public ArenaStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	/**
	 * 请求竞技场信息
	 */
	@Override
	public void doAction() {
		CGArenaList msg = new CGArenaList();
		this.getRobot().sendMessage(msg);
	}

	/**
	 * 处理返回的竞技场信息并自动挑战玩家
	 */
	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCArenaList){
			GCArenaList msg = (GCArenaList)message;
			ArenaRoleInfo[] infos = msg.getTargets();
			if(infos == null || infos.length == 0){
				return;
			}
			int challenge = msg.getChallenge();
			if(challenge > 0){
				CGArenaFight cgMsg = new CGArenaFight();
				cgMsg.setTargetPos(infos[0].getRank());
				this.getRobot().sendMessage(cgMsg);
			}
		}

	}

}
