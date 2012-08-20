package com.pwrd.war.gameserver.promptButton.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 提示按钮
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPromptButton extends GCMessage{
	
	/** 实例id */
	private String id;
	/** 消息内容 */
	private String content;
	/** 功能id */
	private int functionId;

	public GCPromptButton (){
	}
	
	public GCPromptButton (
			String id,
			String content,
			int functionId ){
			this.id = id;
			this.content = content;
			this.functionId = functionId;
	}

	@Override
	protected boolean readImpl() {
		id = readString();
		content = readString();
		functionId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(id);
		writeString(content);
		writeInteger(functionId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PROMPT_BUTTON;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PROMPT_BUTTON";
	}

	public String getId(){
		return id;
	}
		
	public void setId(String id){
		this.id = id;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public int getFunctionId(){
		return functionId;
	}
		
	public void setFunctionId(int functionId){
		this.functionId = functionId;
	}
}