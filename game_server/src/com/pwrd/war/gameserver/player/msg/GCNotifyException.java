package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 通知客户端
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCNotifyException extends GCMessage{
	
	/** 错误码 */
	private int code;
	/** 错误信息，如果为空就显示默认的 */
	private String msg;

	public GCNotifyException (){
	}
	
	public GCNotifyException (
			int code,
			String msg ){
			this.code = code;
			this.msg = msg;
	}

	@Override
	protected boolean readImpl() {
		code = readInteger();
		msg = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(code);
		writeString(msg);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_NOTIFY_EXCEPTION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_NOTIFY_EXCEPTION";
	}

	public int getCode(){
		return code;
	}
		
	public void setCode(int code){
		this.code = code;
	}

	public String getMsg(){
		return msg;
	}
		
	public void setMsg(String msg){
		this.msg = msg;
	}
}