package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 整理背包
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTidyBag extends CGMessage{
	
	/** 包ID */
	private short bagId;
	
	public CGTidyBag (){
	}
	
	public CGTidyBag (
			short bagId ){
			this.bagId = bagId;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_TIDY_BAG;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TIDY_BAG";
	}

	public short getBagId(){
		return bagId;
	}
		
	public void setBagId(short bagId){
		this.bagId = bagId;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleTidyBag(this.getSession().getPlayer(), this);
	}
}