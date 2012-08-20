package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 丢弃道具
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDropItem extends CGMessage{
	
	/** 包ID */
	private short bagId;
	/** 包内位置索引 */
	private short index;
	
	public CGDropItem (){
	}
	
	public CGDropItem (
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
		return MessageType.CG_DROP_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DROP_ITEM";
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
		ItemHandlerFactory.getHandler().handleDropItem(this.getSession().getPlayer(), this);
	}
}