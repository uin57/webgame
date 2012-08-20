package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 提示消息:1:重要提示(个人提示)2:错误提示3:按钮提示4:头顶提示5:特殊提示6:正确消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSystemPrompt extends GCMessage{
	
	/** 消息类型 */
	private short showType;
	/** 消息内容 */
	private String content;
	/** 消息字体颜色 */
	private int color;

	public GCSystemPrompt (){
	}
	
	public GCSystemPrompt (
			short showType,
			String content,
			int color ){
			this.showType = showType;
			this.content = content;
			this.color = color;
	}

	@Override
	protected boolean readImpl() {
		showType = readShort();
		content = readString();
		color = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(showType);
		writeString(content);
		writeInteger(color);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SYSTEM_PROMPT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SYSTEM_PROMPT";
	}

	public short getShowType(){
		return showType;
	}
		
	public void setShowType(short showType){
		this.showType = showType;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public int getColor(){
		return color;
	}
		
	public void setColor(int color){
		this.color = color;
	}
}