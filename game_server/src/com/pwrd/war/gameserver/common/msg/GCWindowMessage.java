package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 特殊窗口提示消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCWindowMessage extends GCMessage{
	
	/** 窗口id */
	private int windowId;
	/** 消息内容 */
	private String content;

	public GCWindowMessage (){
	}
	
	public GCWindowMessage (
			int windowId,
			String content ){
			this.windowId = windowId;
			this.content = content;
	}

	@Override
	protected boolean readImpl() {
		windowId = readInteger();
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(windowId);
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_WINDOW_MESSAGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_WINDOW_MESSAGE";
	}

	public int getWindowId(){
		return windowId;
	}
		
	public void setWindowId(int windowId){
		this.windowId = windowId;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}
}