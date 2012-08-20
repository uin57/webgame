package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 装备绑定
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGItemFreeze extends CGMessage{
	
	/** 装备uuid */
	private String itemUUID;
	
	public CGItemFreeze (){
	}
	
	public CGItemFreeze (
			String itemUUID ){
			this.itemUUID = itemUUID;
	}
	
	@Override
	protected boolean readImpl() {
		itemUUID = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(itemUUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ITEM_FREEZE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ITEM_FREEZE";
	}

	public String getItemUUID(){
		return itemUUID;
	}
		
	public void setItemUUID(String itemUUID){
		this.itemUUID = itemUUID;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleItemFreeze(this.getSession().getPlayer(), this);
	}
}