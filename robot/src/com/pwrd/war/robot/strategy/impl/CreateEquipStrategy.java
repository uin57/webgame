package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.common.model.item.CommonItem;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.chat.msg.CGChatMsg;
import com.pwrd.war.gameserver.item.msg.GCItemUpdate;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class CreateEquipStrategy extends OnceExecuteStrategy{

	public CreateEquipStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {		
		int itemCount = getRobot().getBagManager().getItemCount();
		if(itemCount == 0)
		{
			CGChatMsg chatMsg = new CGChatMsg();			
			chatMsg.setScope(SharedConstants.CHAT_SCOPE_WORLD);
			chatMsg.setContent("!giveitem 11110001 1");
			getRobot().sendMessage(chatMsg);
		}
		
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCItemUpdate)
		{
			CommonItem[] items = ((GCItemUpdate)message).getItem();
			for(CommonItem item : items){
				getRobot().getBagManager().updateItem(item);
			}
		}			
	}

}
