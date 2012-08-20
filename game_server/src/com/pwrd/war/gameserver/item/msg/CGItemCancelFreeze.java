package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 取消装备绑定
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGItemCancelFreeze extends CGMessage{
	
	/** 装备uuid */
	private String itemUUID;
	
	public CGItemCancelFreeze (){
	}
	
	public CGItemCancelFreeze (
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
		return MessageType.CG_ITEM_CANCEl_FREEZE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ITEM_CANCEl_FREEZE";
	}

	public String getItemUUID(){
		return itemUUID;
	}
		
	public void setItemUUID(String itemUUID){
		this.itemUUID = itemUUID;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleItemCancelFreeze(this.getSession().getPlayer(), this);
	}
}