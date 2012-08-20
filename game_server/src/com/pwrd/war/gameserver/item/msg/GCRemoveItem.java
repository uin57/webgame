package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 移除一个道具
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRemoveItem extends GCMessage{
	
	/** 包id */
	private short bagId;
	/** 道具在包内位置索引 */
	private short index;
	/** 佩戴者uuid */
	private String wearerId;

	public GCRemoveItem (){
	}
	
	public GCRemoveItem (
			short bagId,
			short index,
			String wearerId ){
			this.bagId = bagId;
			this.index = index;
			this.wearerId = wearerId;
	}

	@Override
	protected boolean readImpl() {
		bagId = readShort();
		index = readShort();
		wearerId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagId);
		writeShort(index);
		writeString(wearerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REMOVE_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REMOVE_ITEM";
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

	public String getWearerId(){
		return wearerId;
	}
		
	public void setWearerId(String wearerId){
		this.wearerId = wearerId;
	}
}