package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 交换道具
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSwapItem extends GCMessage{
	
	/** 来源包id */
	private short fromBagId;
	/** 道具在来源背包内位置索引 */
	private short fromIndex;
	/** 目的包id */
	private short toBagId;
	/** 道具在目的包内位置索引 */
	private short toIndex;

	public GCSwapItem (){
	}
	
	public GCSwapItem (
			short fromBagId,
			short fromIndex,
			short toBagId,
			short toIndex ){
			this.fromBagId = fromBagId;
			this.fromIndex = fromIndex;
			this.toBagId = toBagId;
			this.toIndex = toIndex;
	}

	@Override
	protected boolean readImpl() {
		fromBagId = readShort();
		fromIndex = readShort();
		toBagId = readShort();
		toIndex = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(fromBagId);
		writeShort(fromIndex);
		writeShort(toBagId);
		writeShort(toIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SWAP_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SWAP_ITEM";
	}

	public short getFromBagId(){
		return fromBagId;
	}
		
	public void setFromBagId(short fromBagId){
		this.fromBagId = fromBagId;
	}

	public short getFromIndex(){
		return fromIndex;
	}
		
	public void setFromIndex(short fromIndex){
		this.fromIndex = fromIndex;
	}

	public short getToBagId(){
		return toBagId;
	}
		
	public void setToBagId(short toBagId){
		this.toBagId = toBagId;
	}

	public short getToIndex(){
		return toIndex;
	}
		
	public void setToIndex(short toIndex){
		this.toIndex = toIndex;
	}
}