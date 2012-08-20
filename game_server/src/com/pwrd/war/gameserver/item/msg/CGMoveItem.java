package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 移动物品，用于拖拽动作
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGMoveItem extends CGMessage{
	
	/** 来源包id */
	private short fromBagId;
	/** 道具在来源背包内位置索引 */
	private short fromIndex;
	/** 目的包id */
	private short toBagId;
	/** 道具在目的包内位置索引 */
	private short toIndex;
	/** 物品佩戴者的UUID,即当前操作的武将宠物id */
	private String wearerId;
	
	public CGMoveItem (){
	}
	
	public CGMoveItem (
			short fromBagId,
			short fromIndex,
			short toBagId,
			short toIndex,
			String wearerId ){
			this.fromBagId = fromBagId;
			this.fromIndex = fromIndex;
			this.toBagId = toBagId;
			this.toIndex = toIndex;
			this.wearerId = wearerId;
	}
	
	@Override
	protected boolean readImpl() {
		fromBagId = readShort();
		fromIndex = readShort();
		toBagId = readShort();
		toIndex = readShort();
		wearerId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(fromBagId);
		writeShort(fromIndex);
		writeShort(toBagId);
		writeShort(toIndex);
		writeString(wearerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_MOVE_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MOVE_ITEM";
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

	public String getWearerId(){
		return wearerId;
	}
		
	public void setWearerId(String wearerId){
		this.wearerId = wearerId;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleMoveItem(this.getSession().getPlayer(), this);
	}
}