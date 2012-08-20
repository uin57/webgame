package com.pwrd.war.gameserver.promptButton.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.promptButton.handler.PromptButtonHandlerFactory;

/**
 * 清除提示按钮
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDeletePromptButton extends CGMessage{
	
	/** 实例id */
	private String id;
	
	public CGDeletePromptButton (){
	}
	
	public CGDeletePromptButton (
			String id ){
			this.id = id;
	}
	
	@Override
	protected boolean readImpl() {
		id = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(id);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_DELETE_PROMPT_BUTTON;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DELETE_PROMPT_BUTTON";
	}

	public String getId(){
		return id;
	}
		
	public void setId(String id){
		this.id = id;
	}

	@Override
	public void execute() {
		PromptButtonHandlerFactory.getHandler().handleDeletePromptButton(this.getSession().getPlayer(), this);
	}
}