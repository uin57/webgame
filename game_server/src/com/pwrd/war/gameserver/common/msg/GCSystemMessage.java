package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 系统提示消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSystemMessage extends GCMessage{
	
	/** 消息内容 */
	private String content;
	/** 消息字体颜色 */
	private int color;
	/** 消息显示类型 */
	private short showType;

	public GCSystemMessage (){
	}
	
	public GCSystemMessage (
			String content,
			int color,
			short showType ){
			this.content = content;
			this.color = color;
			this.showType = showType;
	}

	@Override
	protected boolean readImpl() {
		content = readString();
		color = readInteger();
		showType = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(content);
		writeInteger(color);
		writeShort(showType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SYSTEM_MESSAGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SYSTEM_MESSAGE";
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

	public short getShowType(){
		return showType;
	}
		
	public void setShowType(short showType){
		this.showType = showType;
	}
}