package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 打造结果
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCUseReel extends GCMessage{
	
	/** 1ok,0false */
	private int result;

	public GCUseReel (){
	}
	
	public GCUseReel (
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
		return MessageType.GC_USE_REEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_USE_REEL";
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}
}