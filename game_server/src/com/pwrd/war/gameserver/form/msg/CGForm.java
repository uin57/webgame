package com.pwrd.war.gameserver.form.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.form.handler.FormHandlerFactory;

/**
 * 客户端请求阵型初始化消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGForm extends CGMessage{
	
	
	public CGForm (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_Form;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Form";
	}

	@Override
	public void execute() {
		FormHandlerFactory.getHandler().handleForm(this.getSession().getPlayer(), this);
	}
}