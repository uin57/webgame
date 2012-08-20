package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 重新设置背包的容量，只可能是主道具包、材料包、仓库三者之一
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCResetCapacity extends GCMessage{
	
	/** 包Id */
	private short bagId;
	/** 新的容量 */
	private short capacity;

	public GCResetCapacity (){
	}
	
	public GCResetCapacity (
			short bagId,
			short capacity ){
			this.bagId = bagId;
			this.capacity = capacity;
	}

	@Override
	protected boolean readImpl() {
		bagId = readShort();
		capacity = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagId);
		writeShort(capacity);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RESET_CAPACITY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RESET_CAPACITY";
	}

	public short getBagId(){
		return bagId;
	}
		
	public void setBagId(short bagId){
		this.bagId = bagId;
	}

	public short getCapacity(){
		return capacity;
	}
		
	public void setCapacity(short capacity){
		this.capacity = capacity;
	}
}