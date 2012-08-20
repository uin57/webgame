package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 在线奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCOnlinePrize extends GCMessage{
	
	/** 步骤 */
	private int buzhou;
	/** 剩余可领取时间，秒 */
	private int leftTime;
	/** 奖励提示 */
	private String prizeTip;
	/** 提示 */
	private String tip;

	public GCOnlinePrize (){
	}
	
	public GCOnlinePrize (
			int buzhou,
			int leftTime,
			String prizeTip,
			String tip ){
			this.buzhou = buzhou;
			this.leftTime = leftTime;
			this.prizeTip = prizeTip;
			this.tip = tip;
	}

	@Override
	protected boolean readImpl() {
		buzhou = readInteger();
		leftTime = readInteger();
		prizeTip = readString();
		tip = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buzhou);
		writeInteger(leftTime);
		writeString(prizeTip);
		writeString(tip);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ONLINE_PRIZE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ONLINE_PRIZE";
	}

	public int getBuzhou(){
		return buzhou;
	}
		
	public void setBuzhou(int buzhou){
		this.buzhou = buzhou;
	}

	public int getLeftTime(){
		return leftTime;
	}
		
	public void setLeftTime(int leftTime){
		this.leftTime = leftTime;
	}

	public String getPrizeTip(){
		return prizeTip;
	}
		
	public void setPrizeTip(String prizeTip){
		this.prizeTip = prizeTip;
	}

	public String getTip(){
		return tip;
	}
		
	public void setTip(String tip){
		this.tip = tip;
	}
}