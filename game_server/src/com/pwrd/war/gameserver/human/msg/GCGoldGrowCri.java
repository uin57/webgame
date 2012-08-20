package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返还元宝成长暴击
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGoldGrowCri extends GCMessage{
	
	/** 元宝成长结果1:双倍成长2:五倍成长(暴击) */
	private int result;

	public GCGoldGrowCri (){
	}
	
	public GCGoldGrowCri (
			int result ){
			this.result = result;
	}

	@Override
	protected boolean readImpl() {
		result = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(result);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GOLD_GROW_CRI;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GOLD_GROW_CRI";
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}
}