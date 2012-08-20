package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.item.msg.CGUseItem;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class UseItem extends OnceExecuteStrategy {
	Robot robot;
	public UseItem(Robot robot, int delay) {
		super(robot, delay);
		this.robot = robot;
	}

	@Override
	public void doAction() { 
		
		CGUseItem msg = new CGUseItem();
//		msg.setBagId((short) BagType.PRIM.getIndex());
		msg.setBagId((short) BagType.HUMAN_EQUIP.getIndex());
//		msg.setBagId((short) BagType.PET_EQUIP.getIndex());
		msg.setIndex((short) 0);
		msg.setTargetUuid("77beefc5-649b-45a7-b014-ccff079bff45");//8a828424331087ba01331087ced80001
		msg.setWearId("");
		msg.setParams("");
		sendMessage(msg);
		
//		CGMoveItem msg = new CGMoveItem();
//		msg.setFromBagId((short) BagType.HUMAN_EQUIP.getIndex());
//		msg.setFromIndex((short)0);
//		msg.setToBagId((short) BagType.PRIM.getIndex());
//		msg.setToIndex((short)10);
//		msg.setWearerId("");
//		sendMessage(msg);
		
		
	}

	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub 
//			System.out.println("收到:"+message);
//		}
	}

}
