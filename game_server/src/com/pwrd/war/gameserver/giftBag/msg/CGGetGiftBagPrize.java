package com.pwrd.war.gameserver.giftBag.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.giftBag.handler.GiftBagHandlerFactory;

/**
 * 领取礼包奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetGiftBagPrize extends CGMessage{
	
	/** 礼包实例id */
	private String id;
	/** 礼包模版id */
	private String giftId;
	
	public CGGetGiftBagPrize (){
	}
	
	public CGGetGiftBagPrize (
			String id,
			String giftId ){
			this.id = id;
			this.giftId = giftId;
	}
	
	@Override
	protected boolean readImpl() {
		id = readString();
		giftId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(id);
		writeString(giftId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_GIFT_BAG_PRIZE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_GIFT_BAG_PRIZE";
	}

	public String getId(){
		return id;
	}
		
	public void setId(String id){
		this.id = id;
	}

	public String getGiftId(){
		return giftId;
	}
		
	public void setGiftId(String giftId){
		this.giftId = giftId;
	}

	@Override
	public void execute() {
		GiftBagHandlerFactory.getHandler().handleGetGiftBagPrize(this.getSession().getPlayer(), this);
	}
}