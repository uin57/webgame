package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 扩充背包需要多少元宝
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSpandBagGold extends GCMessage{
	
	/** 数量 */
	private int number;
	/** 元宝数目 */
	private int gold;
	/** 背包1，仓库5 */
	private int bagType;

	public GCSpandBagGold (){
	}
	
	public GCSpandBagGold (
			int number,
			int gold,
			int bagType ){
			this.number = number;
			this.gold = gold;
			this.bagType = bagType;
	}

	@Override
	protected boolean readImpl() {
		number = readInteger();
		gold = readInteger();
		bagType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(number);
		writeInteger(gold);
		writeInteger(bagType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SPAND_BAG_GOLD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SPAND_BAG_GOLD";
	}

	public int getNumber(){
		return number;
	}
		
	public void setNumber(int number){
		this.number = number;
	}

	public int getGold(){
		return gold;
	}
		
	public void setGold(int gold){
		this.gold = gold;
	}

	public int getBagType(){
		return bagType;
	}
		
	public void setBagType(int bagType){
		this.bagType = bagType;
	}
}