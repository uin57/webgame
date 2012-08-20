/**
 * 
 */
package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.monster.VisibleMonsterInfo;
import com.pwrd.war.gameserver.monster.msg.GCMonsterList;
import com.pwrd.war.gameserver.monster.msg.GCMonsterMove;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * 
 * 战斗策略
 * @author dengdan
 *
 */
public class BattleStrategy extends OnceExecuteStrategy {
	
	public BattleStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction(){
		
	}

	/**
	 * 接收到场景的怪物列表自动战斗
	 */
	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCMonsterList){
			GCMonsterList msg = (GCMonsterList)message;
			VisibleMonsterInfo[] infos = (VisibleMonsterInfo[])msg.getMonsterInfo();
			if(infos != null && infos.length >0){
				for(VisibleMonsterInfo info : infos){
					int x = info.getNowX();
					int y = info.getNowY();
					this.getRobot().handlerBattle(x,y);
					break;
				}
			}
		}
		if(message instanceof GCMonsterMove){
			GCMonsterMove gcMsg = (GCMonsterMove)message;
			int toX = gcMsg.getTox();
			int toY = gcMsg.getToy();
			this.getRobot().handlerBattle(toX,toY);
		}

	}

}
