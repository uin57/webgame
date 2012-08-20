package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 强化装备结果返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCEnhanceEquip extends GCMessage{
	
	/** 是否免费 */
	private boolean isFree;
	/** 是否强化暴击 */
	private boolean isDouble;

	public GCEnhanceEquip (){
	}
	
	public GCEnhanceEquip (
			boolean isFree,
			boolean isDouble ){
			this.isFree = isFree;
			this.isDouble = isDouble;
	}

	@Override
	protected boolean readImpl() {
		isFree = readBoolean();
		isDouble = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(isFree);
		writeBoolean(isDouble);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ENHANCE_EQUIP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ENHANCE_EQUIP";
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
}