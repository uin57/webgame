package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.common.model.item.CommonItem;
import com.pwrd.war.common.model.pet.PetInfo;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.util.MathUtils;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.item.msg.CGMoveItem;
import com.pwrd.war.gameserver.item.msg.GCRemoveItem;
import com.pwrd.war.gameserver.item.msg.GCSwapItem;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class MoveItemStrategy extends OnceExecuteStrategy{
	/** CG 消息 */
	private CGMoveItem _cgmsg = new CGMoveItem();
	
	public MoveItemStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {
		int itemCount = getRobot().getBagManager().getItemCount();
		int itemIndex = MathUtils.random(0, itemCount - 1);
		int petCount = getRobot().getPetManager().getPetCount();
		int petIndex = MathUtils.random(0, petCount - 1);
		PetInfo pet = getRobot().getPetManager().getPetByIndex(petIndex);
		
		if(itemCount > 0 && pet != null)
		{
			int ranIdx = MathUtils.random(0, 9);
			CommonItem commonItem = getRobot().getBagManager().getItemByIndex(itemIndex);

			this._cgmsg.setFromBagId((short)BagType.PRIM.index);
			this._cgmsg.setFromIndex((short) commonItem.getIndex());
			this._cgmsg.setToBagId((short)BagType.PET_EQUIP.index);
			this._cgmsg.setToIndex((short)ranIdx);
			this._cgmsg.setWearerId(pet.getUuid());
			getRobot().sendMessage(this._cgmsg);
			this.logInfo("move item");
		}
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCSwapItem)
		{
			int fromIndex = ((GCSwapItem)message).getFromIndex();
			int toIndex = ((GCSwapItem)message).getToIndex();
			
			CommonItem commonItem = getRobot().getBagManager().getItemByIndex(fromIndex);
			commonItem.setIndex((short)toIndex);
		}
		else if(message instanceof GCRemoveItem)
		{
			getRobot().getBagManager().removeItem((GCRemoveItem)message);
		}
	}

}
