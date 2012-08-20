package com.pwrd.war.gameserver.buff.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * buff改变消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBuffChangeMessage extends GCMessage{
	
	/** -1 表示去除，0 表示修改，1表示添加 */
	private int modifyType;
	/** ${field.comment} */
	private com.pwrd.war.gameserver.buff.domain.Buffer buff;

	public GCBuffChangeMessage (){
	}
	
	public GCBuffChangeMessage (
			int modifyType,
			com.pwrd.war.gameserver.buff.domain.Buffer buff ){
			this.modifyType = modifyType;
			this.buff = buff;
	}

	@Override
	protected boolean readImpl() {
		modifyType = readInteger();
		buff = new com.pwrd.war.gameserver.buff.domain.Buffer();
					buff.setBufferSn(readString());
							buff.setBufferName(readString());
							buff.setBufferType(readInteger());
							buff.setUsedNumber(readInteger());
							buff.setBufferTime(readLong());
							buff.setBufferIcon(readString());
							buff.setBufferDescription(readString());
							buff.setValue(readDouble());
							buff.setExt(readString());
							buff.setBigType(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(modifyType);
		writeString(buff.getBufferSn());
		writeString(buff.getBufferName());
		writeInteger(buff.getBufferType());
		writeInteger(buff.getUsedNumber());
		writeLong(buff.getBufferTime());
		writeString(buff.getBufferIcon());
		writeString(buff.getBufferDescription());
		writeDouble(buff.getValue());
		writeString(buff.getExt());
		writeInteger(buff.getBigType());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Buff_Change_Message;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Buff_Change_Message";
	}

	public int getModifyType(){
		return modifyType;
	}
		
	public void setModifyType(int modifyType){
		this.modifyType = modifyType;
	}

	public com.pwrd.war.gameserver.buff.domain.Buffer getBuff(){
		return buff;
	}
		
	public void setBuff(com.pwrd.war.gameserver.buff.domain.Buffer buff){
		this.buff = buff;
	}
}