package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 显示装备强化面板
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCShowEquipEnhancePanel extends GCMessage{
	
	/** 装备强化成功率 */
	private int enhanceRate;
	/** 锁定成功率需要话费的金钱 */
	private int goldCost;
	/** 是否为上升趋势 */
	private boolean isUp;

	public GCShowEquipEnhancePanel (){
	}
	
	public GCShowEquipEnhancePanel (
			int enhanceRate,
			int goldCost,
			boolean isUp ){
			this.enhanceRate = enhanceRate;
			this.goldCost = goldCost;
			this.isUp = isUp;
	}

	@Override
	protected boolean readImpl() {
		enhanceRate = readInteger();
		goldCost = readInteger();
		isUp = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(enhanceRate);
		writeInteger(goldCost);
		writeBoolean(isUp);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_EQUIP_ENHANCE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_EQUIP_ENHANCE_PANEL";
	}

	public int getEnhanceRate(){
		return enhanceRate;
	}
		
	public void setEnhanceRate(int enhanceRate){
		this.enhanceRate = enhanceRate;
	}

	public int getGoldCost(){
		return goldCost;
	}
		
	public void setGoldCost(int goldCost){
		this.goldCost = goldCost;
	}

	public boolean getIsUp(){
		return isUp;
	}
		
	public void setIsUp(boolean isUp){
		this.isUp = isUp;
	}
}