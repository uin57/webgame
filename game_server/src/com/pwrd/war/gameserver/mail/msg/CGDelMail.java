package com.pwrd.war.gameserver.mail.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.mail.handler.MailHandlerFactory;

/**
 * 删除邮件
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDelMail extends CGMessage{
	
	/** 要删除的邮件uuid */
	private String uuid;
	
	public CGDelMail (){
	}
	
	public CGDelMail (
			String uuid ){
			this.uuid = uuid;
	}
	
	@Override
	protected boolean readImpl() {
		uuid = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(uuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_DEL_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DEL_MAIL";
	}

	public String getUuid(){
		return uuid;
	}
		
	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	@Override
	public void execute() {
		MailHandlerFactory.getHandler().handleDelMail(this.getSession().getPlayer(), this);
	}
}