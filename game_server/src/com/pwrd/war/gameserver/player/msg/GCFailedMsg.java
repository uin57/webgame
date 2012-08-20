package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * GS向CLIENT发送操作失败消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFailedMsg extends GCMessage{
	
	/** 错误号 */
	private short errorNo;
	/** 错误提示信息 */
	private String msg;

	public GCFailedMsg (){
	}
	
	public GCFailedMsg (
			short errorNo,
			String msg ){
			this.errorNo = errorNo;
			this.msg = msg;
	}

	@Override
	protected boolean readImpl() {
		errorNo = readShort();
		msg = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(errorNo);
		writeString(msg);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FAILED_MSG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FAILED_MSG";
	}

	public short getErrorNo(){
		return errorNo;
	}
		
	public void setErrorNo(short errorNo){
		this.errorNo = errorNo;
	}

	public String getMsg(){
		return msg;
	}
		
	public void setMsg(String msg){
		this.msg = msg;
	}
}