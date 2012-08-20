package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回充值面板所需信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCShowChargePanel extends GCMessage{
	
	/** 累计充值金额 */
	private int totalCharge;
	/** 当天充值奖励描述 */
	private String chargePrizeInfo;
	/** 当前的vip等级 */
	private int vipLevel;
	/** vip下一个等级 */
	private int nextVipLevel;
	/** 下级vip充值总钻数 */
	private int nextVipTotalCharge;
	/** 再充值多少钻到下一级别 */
	private int diffCharge;
	/** 下级vip能享受的功能描述列表 */
	private String[] nextVipDesc;

	public GCShowChargePanel (){
	}
	
	public GCShowChargePanel (
			int totalCharge,
			String chargePrizeInfo,
			int vipLevel,
			int nextVipLevel,
			int nextVipTotalCharge,
			int diffCharge,
			String[] nextVipDesc ){
			this.totalCharge = totalCharge;
			this.chargePrizeInfo = chargePrizeInfo;
			this.vipLevel = vipLevel;
			this.nextVipLevel = nextVipLevel;
			this.nextVipTotalCharge = nextVipTotalCharge;
			this.diffCharge = diffCharge;
			this.nextVipDesc = nextVipDesc;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		totalCharge = readInteger();
		chargePrizeInfo = readString();
		vipLevel = readInteger();
		nextVipLevel = readInteger();
		nextVipTotalCharge = readInteger();
		diffCharge = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		nextVipDesc = new String[count];
		for(int i=0; i<count; i++){
			nextVipDesc[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(totalCharge);
		writeString(chargePrizeInfo);
		writeInteger(vipLevel);
		writeInteger(nextVipLevel);
		writeInteger(nextVipTotalCharge);
		writeInteger(diffCharge);
		writeShort(nextVipDesc.length);
		for(int i=0; i<nextVipDesc.length; i++){
			writeString(nextVipDesc[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_CHARGE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_CHARGE_PANEL";
	}

	public int getTotalCharge(){
		return totalCharge;
	}
		
	public void setTotalCharge(int totalCharge){
		this.totalCharge = totalCharge;
	}

	public String getChargePrizeInfo(){
		return chargePrizeInfo;
	}
		
	public void setChargePrizeInfo(String chargePrizeInfo){
		this.chargePrizeInfo = chargePrizeInfo;
	}

	public int getVipLevel(){
		return vipLevel;
	}
		
	public void setVipLevel(int vipLevel){
		this.vipLevel = vipLevel;
	}

	public int getNextVipLevel(){
		return nextVipLevel;
	}
		
	public void setNextVipLevel(int nextVipLevel){
		this.nextVipLevel = nextVipLevel;
	}

	public int getNextVipTotalCharge(){
		return nextVipTotalCharge;
	}
		
	public void setNextVipTotalCharge(int nextVipTotalCharge){
		this.nextVipTotalCharge = nextVipTotalCharge;
	}

	public int getDiffCharge(){
		return diffCharge;
	}
		
	public void setDiffCharge(int diffCharge){
		this.diffCharge = diffCharge;
	}

	public String[] getNextVipDesc(){
		return nextVipDesc;
	}

	public void setNextVipDesc(String[] nextVipDesc){
		this.nextVipDesc = nextVipDesc;
	}	
}