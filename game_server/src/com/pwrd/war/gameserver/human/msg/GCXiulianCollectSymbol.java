package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 采集修炼标志
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCXiulianCollectSymbol extends GCMessage{
	
	/** 获得经验 */
	private int getExp;
	/** 好友加成经验 */
	private int friendAddExp;
	/** 随机效果ID */
	private String randomSymbolId;

	public GCXiulianCollectSymbol (){
	}
	
	public GCXiulianCollectSymbol (
			int getExp,
			int friendAddExp,
			String randomSymbolId ){
			this.getExp = getExp;
			this.friendAddExp = friendAddExp;
			this.randomSymbolId = randomSymbolId;
	}

	@Override
	protected boolean readImpl() {
		getExp = readInteger();
		friendAddExp = readInteger();
		randomSymbolId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(getExp);
		writeInteger(friendAddExp);
		writeString(randomSymbolId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_XIULIAN_COLLECT_SYMBOL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_XIULIAN_COLLECT_SYMBOL";
	}

	public int getGetExp(){
		return getExp;
	}
		
	public void setGetExp(int getExp){
		this.getExp = getExp;
	}

	public int getFriendAddExp(){
		return friendAddExp;
	}
		
	public void setFriendAddExp(int friendAddExp){
		this.friendAddExp = friendAddExp;
	}

	public String getRandomSymbolId(){
		return randomSymbolId;
	}
		
	public void setRandomSymbolId(String randomSymbolId){
		this.randomSymbolId = randomSymbolId;
	}
}