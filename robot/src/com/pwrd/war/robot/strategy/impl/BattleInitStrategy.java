package com.pwrd.war.robot.strategy.impl;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.fight.msg.CGBattleBeginMessage;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class BattleInitStrategy extends OnceExecuteStrategy {
	/**
	 * 类参数构造器
	 * 
	 * @param robot
	 * @param delay 
	 * 
	 */
	public BattleInitStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {
		List<String> list = this.getRobot().getMonsterSnList();
		if(list != null && list.size() > 0){
			int num = RandomUtils.nextInt(list.size());
			String monsterSn = list.get(num);
			CGBattleBeginMessage msg = new CGBattleBeginMessage(false, monsterSn, 0);
			this.getRobot().sendMessage(msg);
		}
	}

	@Override
	public void onResponse(IMessage message) {
//		if(message instanceof GCBattleInitMessage){
//			GCBattleInitMessage msg =(GCBattleInitMessage) message; 
//			System.out.println("________________________________"+msg.getAttackSn());
//		}
//		if(message instanceof GCMonsterList){
//			GCMonsterList list=(GCMonsterList) message;
//			VisibleMonsterInfo[] infos=list.getMonsterInfo();
//			CGBattleBeginMessage msg=new CGBattleBeginMessage();
//			msg.setBattleType(0);
//			msg.setTargetSn(infos[0].getMonsterUUID());
//			msg.setIsPVP(false);
//			sendMessage(msg);
//		}
	}
}