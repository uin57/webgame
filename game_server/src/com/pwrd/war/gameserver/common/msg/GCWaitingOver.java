package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 等待结束
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCWaitingOver extends GCMessage{
	
	/** 等待类型 */
	private int waitingType;
	/** 标签,该等待的一个标识 */
	private int tag;

	public GCWaitingOver (){
	}
	
	public GCWaitingOver (
			int waitingType,
			int tag ){
			this.waitingType = waitingType;
			this.tag = tag;
	}

	@Override
	protected boolean readImpl() {
		waitingType = readInteger();
		tag = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(waitingType);
		writeInteger(tag);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_WAITING_OVER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_WAITING_OVER";
	}

	public int getWaitingType(){
		return waitingType;
	}
		
	public void setWaitingType(int waitingType){
		this.waitingType = waitingType;
	}

	public int getTag(){
		return tag;
	}
		
	public void setTag(int tag){
		this.tag = tag;
	}
}