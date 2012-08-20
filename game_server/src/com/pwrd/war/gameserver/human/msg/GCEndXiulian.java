package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 结束挂机修炼
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCEndXiulian extends GCMessage{
	
	/** 累计经验 */
	private int allExp;

	public GCEndXiulian (){
	}
	
	public GCEndXiulian (
			int allExp ){
			this.allExp = allExp;
	}

	@Override
	protected boolean readImpl() {
		allExp = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(allExp);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_END_XIULIAN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_END_XIULIAN";
	}

	public int getAllExp(){
		return allExp;
	}
		
	public void setAllExp(int allExp){
		this.allExp = allExp;
	}
}