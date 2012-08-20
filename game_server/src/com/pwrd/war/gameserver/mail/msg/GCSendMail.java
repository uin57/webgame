package com.pwrd.war.gameserver.mail.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 发送邮件结果
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSendMail extends GCMessage{
	
	/** 发送结果 */
	private byte resultCode;
	/** 发送结果提示 */
	private String resultMsg;

	public GCSendMail (){
	}
	
	public GCSendMail (
			byte resultCode,
			String resultMsg ){
			this.resultCode = resultCode;
			this.resultMsg = resultMsg;
	}

	@Override
	protected boolean readImpl() {
		resultCode = readByte();
		resultMsg = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeByte(resultCode);
		writeString(resultMsg);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SEND_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SEND_MAIL";
	}

	public byte getResultCode(){
		return resultCode;
	}
		
	public void setResultCode(byte resultCode){
		this.resultCode = resultCode;
	}

	public String getResultMsg(){
		return resultMsg;
	}
		
	public void setResultMsg(String resultMsg){
		this.resultMsg = resultMsg;
	}
}