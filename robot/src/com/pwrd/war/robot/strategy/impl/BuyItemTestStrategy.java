package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.common.model.mall.RedeemInfo;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.mall.msg.CGMallList;
import com.pwrd.war.gameserver.mall.msg.CGMallSell;
import com.pwrd.war.gameserver.mall.msg.CGRedeemBuy;
import com.pwrd.war.gameserver.mall.msg.CGRedeemList;
import com.pwrd.war.gameserver.mall.msg.GCRedeemList;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.LoopExecuteStrategy;

public class BuyItemTestStrategy extends LoopExecuteStrategy {
	/** 类参数构造器 */
	public BuyItemTestStrategy(Robot robot, int delay, int s) {
		super(robot, delay, s);
	}

	@Override
	public void doAction() {
//		CGMallList msgList = new CGMallList();

//		msgList.setIsNpc(true);
//		msgList.setId("1111");
//
//		sendMessage(msgList);
//		this.logInfo("malllist");
//		
//		System.out.println("################################## MallBuy ####################################");
//		CGMallBuy msgBuy = new CGMallBuy();
//		msgBuy.setItemSn("12345");
//		msgBuy.setNumber(1);
//		sendMessage(msgBuy);
//		this.logInfo("mallbuy");
		
		System.out.println("################################## MallSell ####################################");
		CGMallSell msgSell = new CGMallSell();
		msgSell.setUuid("17a0068136044096a299c771b7665102");
		msgSell.setItemSn("12345");
		msgSell.setNumber(5);		
		sendMessage(msgSell);
		this.logInfo("msgSell");
		
		System.out.println("################################## RedeemList ####################################");
		CGRedeemList redeemList = new CGRedeemList();
		sendMessage(redeemList);
		this.logInfo("redeemlist");
		
		//取得回购物品的uuid
		
	}


	@Override
	public void onResponse(IMessage message) {		
		if (message instanceof GCRedeemList)
		{
			RedeemInfo[] redeemInfo;
			redeemInfo =((GCRedeemList)message).getRedeemInfo();
			if(redeemInfo.length > 0){
				String uuid = redeemInfo[0].getUuid();
				String itemSn = redeemInfo[0].getItemSn();
				CGRedeemBuy msg = new CGRedeemBuy();
				msg.setUuid(uuid);
				msg.setItemSn(itemSn);
				msg.setNumber(1);
				sendMessage(msg);
			}
			
			Robot.robotLogger.info(getRobot().getPassportId() + "测试，我是机器人");
		}
		
	}
}
