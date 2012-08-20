package com.pwrd.war.gameserver.mail.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.mail.handler.MailHandlerFactory;

/**
 * 发送军团邮件
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSendGuildMail extends CGMessage{
	
	/** 邮件标题 */
	private String title;
	/** 邮件内容 */
	private String content;
	
	public CGSendGuildMail (){
	}
	
	public CGSendGuildMail (
			String title,
			String content ){
			this.title = title;
			this.content = content;
	}
	
	@Override
	protected boolean readImpl() {
		title = readString();
		content = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(title);
		writeString(content);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SEND_GUILD_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SEND_GUILD_MAIL";
	}

	public String getTitle(){
		return title;
	}
		
	public void setTitle(String title){
		this.title = title;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	@Override
	public void execute() {
		MailHandlerFactory.getHandler().handleSendGuildMail(this.getSession().getPlayer(), this);
	}
}