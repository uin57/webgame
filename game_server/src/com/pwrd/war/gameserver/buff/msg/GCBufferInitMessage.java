package com.pwrd.war.gameserver.buff.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 触发战斗消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBufferInitMessage extends GCMessage{
	
	/** ${field.comment} */
	private com.pwrd.war.gameserver.buff.domain.Buffer[] buffers;

	public GCBufferInitMessage (){
	}
	
	public GCBufferInitMessage (
			com.pwrd.war.gameserver.buff.domain.Buffer[] buffers ){
			this.buffers = buffers;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		buffers = new com.pwrd.war.gameserver.buff.domain.Buffer[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.buff.domain.Buffer obj = new com.pwrd.war.gameserver.buff.domain.Buffer();
			obj.setBufferSn(readString());
			obj.setBufferName(readString());
			obj.setBufferType(readInteger());
			obj.setUsedNumber(readInteger());
			obj.setBufferTime(readLong());
			obj.setBufferIcon(readString());
			obj.setBufferDescription(readString());
			obj.setValue(readDouble());
			obj.setExt(readString());
			obj.setBigType(readInteger());
			buffers[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(buffers.length);
		for(int i=0; i<buffers.length; i++){
			writeString(buffers[i].getBufferSn());
			writeString(buffers[i].getBufferName());
			writeInteger(buffers[i].getBufferType());
			writeInteger(buffers[i].getUsedNumber());
			writeLong(buffers[i].getBufferTime());
			writeString(buffers[i].getBufferIcon());
			writeString(buffers[i].getBufferDescription());
			writeDouble(buffers[i].getValue());
			writeString(buffers[i].getExt());
			writeInteger(buffers[i].getBigType());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Buffer_Init_Message;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Buffer_Init_Message";
	}

	public com.pwrd.war.gameserver.buff.domain.Buffer[] getBuffers(){
		return buffers;
	}

	public void setBuffers(com.pwrd.war.gameserver.buff.domain.Buffer[] buffers){
		this.buffers = buffers;
	}	
}