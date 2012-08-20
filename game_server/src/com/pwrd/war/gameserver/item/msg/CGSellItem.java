package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 卖出道具
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSellItem extends CGMessage{
	
	/** 背包ID */
	private short bagId;
	/** 背包内位置索引 */
	private short index;
	
	public CGSellItem (){
	}
	
	public CGSellItem (
			short bagId,
			short index ){
			this.bagId = bagId;
			this.index = index;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readShort();
		index = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagId);
		writeShort(index);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SELL_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SELL_ITEM";
	}

	public short getBagId(){
		return bagId;
	}
		
	public void setBagId(short bagId){
		this.bagId = bagId;
	}

	public short getIndex(){
		return index;
	}
		
	public void setIndex(short index){
		this.index = index;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleSellItem(this.getSession().getPlayer(), this);
	}
}