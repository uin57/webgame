package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 客户端主动请求更新背包
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGReqBaginfo extends CGMessage{
	
	/** 来源包id */
	private short bagId;
	/** 佩戴者uuid */
	private String wearerId;
	
	public CGReqBaginfo (){
	}
	
	public CGReqBaginfo (
			short bagId,
			String wearerId ){
			this.bagId = bagId;
			this.wearerId = wearerId;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readShort();
		wearerId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagId);
		writeString(wearerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_REQ_BAGINFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REQ_BAGINFO";
	}

	public short getBagId(){
		return bagId;
	}
		
	public void setBagId(short bagId){
		this.bagId = bagId;
	}

	public String getWearerId(){
		return wearerId;
	}
		
	public void setWearerId(String wearerId){
		this.wearerId = wearerId;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleReqBaginfo(this.getSession().getPlayer(), this);
	}
}