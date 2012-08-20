package com.pwrd.war.gameserver.mail.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 单个邮件更新
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCMailUpdate extends GCMessage{
	
	/** 邮件 */
	private com.pwrd.war.common.model.mail.MailInfo mail;

	public GCMailUpdate (){
	}
	
	public GCMailUpdate (
			com.pwrd.war.common.model.mail.MailInfo mail ){
			this.mail = mail;
	}

	@Override
	protected boolean readImpl() {
		mail = new com.pwrd.war.common.model.mail.MailInfo();
					mail.setUuid(readString());
							mail.setTitle(readString());
							mail.setSendName(readString());
							mail.setContent(readString());
							mail.setMailType(readInteger());
							mail.setMailStatus(readInteger());
							mail.setCreateTime(readString());
							mail.setUpdateTime(readLong());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(mail.getUuid());
		writeString(mail.getTitle());
		writeString(mail.getSendName());
		writeString(mail.getContent());
		writeInteger(mail.getMailType());
		writeInteger(mail.getMailStatus());
		writeString(mail.getCreateTime());
		writeLong(mail.getUpdateTime());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MAIL_UPDATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MAIL_UPDATE";
	}

	public com.pwrd.war.common.model.mail.MailInfo getMail(){
		return mail;
	}
		
	public void setMail(com.pwrd.war.common.model.mail.MailInfo mail){
		this.mail = mail;
	}
}