package com.pwrd.war.gameserver.giftBag.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.giftBag.handler.GiftBagHandlerFactory;

/**
 * 请求礼包信息列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGiftBagInfoList extends CGMessage{
	
	
	public CGGiftBagInfoList (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GIFT_BAG_INFO_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GIFT_BAG_INFO_LIST";
	}

	@Override
	public void execute() {
		GiftBagHandlerFactory.getHandler().handleGiftBagInfoList(this.getSession().getPlayer(), this);
	}
}