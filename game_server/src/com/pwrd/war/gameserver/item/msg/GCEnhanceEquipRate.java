package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回当前装备强化成功概率
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCEnhanceEquipRate extends GCMessage{
	
	/** 当前成功概率 */
	private int rate;
	/** 用元宝强化需要多少元宝 */
	private int goldNum;
	/** 下次概率是上升还是下降趋势，true为上升趋势 */
	private boolean bUp;

	public GCEnhanceEquipRate (){
	}
	
	public GCEnhanceEquipRate (
			int rate,
			int goldNum,
			boolean bUp ){
			this.rate = rate;
			this.goldNum = goldNum;
			this.bUp = bUp;
	}

	@Override
	protected boolean readImpl() {
		rate = readInteger();
		goldNum = readInteger();
		bUp = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rate);
		writeInteger(goldNum);
		writeBoolean(bUp);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ENHANCE_EQUIP_RATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ENHANCE_EQUIP_RATE";
	}

	public int getRate(){
		return rate;
	}
		
	public void setRate(int rate){
		this.rate = rate;
	}

	public int getGoldNum(){
		return goldNum;
	}
		
	public void setGoldNum(int goldNum){
		this.goldNum = goldNum;
	}

	public boolean getBUp(){
		return bUp;
	}
		
	public void setBUp(boolean bUp){
		this.bUp = bUp;
	}
}