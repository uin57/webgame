package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 服务器端响应的时间同步的消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPing extends GCMessage{
	
	/** 服务器当前时间戳 */
	private long timestamp;

	public GCPing (){
	}
	
	public GCPing (
			long timestamp ){
			this.timestamp = timestamp;
	}

	@Override
	protected boolean readImpl() {
		timestamp = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(timestamp);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PING";
	}

	public long getTimestamp(){
		return timestamp;
	}
		
	public void setTimestamp(long timestamp){
		this.timestamp = timestamp;
	}
}