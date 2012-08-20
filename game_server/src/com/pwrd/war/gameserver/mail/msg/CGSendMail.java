package com.pwrd.war.gameserver.mail.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.mail.handler.MailHandlerFactory;

/**
 * 发送邮件
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSendMail extends CGMessage{
	
	/** 收件人名称 */
	private String recName;
	/** 邮件标题 */
	private String title;
	/** 邮件内容 */
	private String content;
	/** 是否为军团邮件 */
	private boolean isGuildMail;
	
	public CGSendMail (){
	}
	
	public CGSendMail (
			String recName,
			String title,
			String content,
			boolean isGuildMail ){
			this.recName = recName;
			this.title = title;
			this.content = content;
			this.isGuildMail = isGuildMail;
	}
	
	@Override
	protected boolean readImpl() {
		recName = readString();
		title = readString();
		content = readString();
		isGuildMail = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(recName);
		writeString(title);
		writeString(content);
		writeBoolean(isGuildMail);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SEND_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SEND_MAIL";
	}

	public String getRecName(){
		return recName;
	}
		
	public void setRecName(String recName){
		this.recName = recName;
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

	public boolean getIsGuildMail(){
		return isGuildMail;
	}
		
	public void setIsGuildMail(boolean isGuildMail){
		this.isGuildMail = isGuildMail;
	}

	@Override
	public void execute() {
		MailHandlerFactory.getHandler().handleSendMail(this.getSession().getPlayer(), this);
	}
}