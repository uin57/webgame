package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 强化装备
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGEnhanceEquip extends CGMessage{
	
	/** 背包ID */
	private int bagId;
	/** 背包内位置索引 */
	private int index;
	/** 佩带者 */
	private String wearerId;
	/** 是否免费强化，使用元宝 */
	private boolean isFree;
	/** 是否强化暴击，使用元宝 */
	private boolean isDouble;
	
	public CGEnhanceEquip (){
	}
	
	public CGEnhanceEquip (
			int bagId,
			int index,
			String wearerId,
			boolean isFree,
			boolean isDouble ){
			this.bagId = bagId;
			this.index = index;
			this.wearerId = wearerId;
			this.isFree = isFree;
			this.isDouble = isDouble;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readInteger();
		index = readInteger();
		wearerId = readString();
		isFree = readBoolean();
		isDouble = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bagId);
		writeInteger(index);
		writeString(wearerId);
		writeBoolean(isFree);
		writeBoolean(isDouble);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ENHANCE_EQUIP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENHANCE_EQUIP";
	}

	public int getBagId(){
		return bagId;
	}
		
	public void setBagId(int bagId){
		this.bagId = bagId;
	}

	public int getIndex(){
		return index;
	}
		
	public void setIndex(int index){
		this.index = index;
	}

	public String getWearerId(){
		return wearerId;
	}
		
	public void setWearerId(String wearerId){
		this.wearerId = wearerId;
	}

	public boolean getIsFree(){
		return isFree;
	}
		
	public void setIsFree(boolean isFree){
		this.isFree = isFree;
	}

	public boolean getIsDouble(){
		return isDouble;
	}
		
	public void setIsDouble(boolean isDouble){
		this.isDouble = isDouble;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleEnhanceEquip(this.getSession().getPlayer(), this);
	}
}