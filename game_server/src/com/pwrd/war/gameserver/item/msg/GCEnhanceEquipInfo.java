package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回当前装备强化成功概率
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCEnhanceEquipInfo extends GCMessage{
	
	/** 是否双倍强化 */
	private boolean isDouble;
	/** 双倍强化时间段描述 */
	private String doubleTimeDesc;
	/** 是否免费强化 */
	private boolean isFree;
	/** 免费强化时间段描述 */
	private String freeTimeDesc;
	/** 免费强化需要元宝 */
	private int freeGold;
	/** 强化暴击需要元宝 */
	private int doubleGold;
	/** 下一个描述 */
	private int nextDesc;

	public GCEnhanceEquipInfo (){
	}
	
	public GCEnhanceEquipInfo (
			boolean isDouble,
			String doubleTimeDesc,
			boolean isFree,
			String freeTimeDesc,
			int freeGold,
			int doubleGold,
			int nextDesc ){
			this.isDouble = isDouble;
			this.doubleTimeDesc = doubleTimeDesc;
			this.isFree = isFree;
			this.freeTimeDesc = freeTimeDesc;
			this.freeGold = freeGold;
			this.doubleGold = doubleGold;
			this.nextDesc = nextDesc;
	}

	@Override
	protected boolean readImpl() {
		isDouble = readBoolean();
		doubleTimeDesc = readString();
		isFree = readBoolean();
		freeTimeDesc = readString();
		freeGold = readInteger();
		doubleGold = readInteger();
		nextDesc = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(isDouble);
		writeString(doubleTimeDesc);
		writeBoolean(isFree);
		writeString(freeTimeDesc);
		writeInteger(freeGold);
		writeInteger(doubleGold);
		writeInteger(nextDesc);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ENHANCE_EQUIP_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ENHANCE_EQUIP_INFO";
	}

	public boolean getIsDouble(){
		return isDouble;
	}
		
	public void setIsDouble(boolean isDouble){
		this.isDouble = isDouble;
	}

	public String getDoubleTimeDesc(){
		return doubleTimeDesc;
	}
		
	public void setDoubleTimeDesc(String doubleTimeDesc){
		this.doubleTimeDesc = doubleTimeDesc;
	}

	public boolean getIsFree(){
		return isFree;
	}
		
	public void setIsFree(boolean isFree){
		this.isFree = isFree;
	}

	public String getFreeTimeDesc(){
		return freeTimeDesc;
	}
		
	public void setFreeTimeDesc(String freeTimeDesc){
		this.freeTimeDesc = freeTimeDesc;
	}

	public int getFreeGold(){
		return freeGold;
	}
		
	public void setFreeGold(int freeGold){
		this.freeGold = freeGold;
	}

	public int getDoubleGold(){
		return doubleGold;
	}
		
	public void setDoubleGold(int doubleGold){
		this.doubleGold = doubleGold;
	}

	public int getNextDesc(){
		return nextDesc;
	}
		
	public void setNextDesc(int nextDesc){
		this.nextDesc = nextDesc;
	}
}