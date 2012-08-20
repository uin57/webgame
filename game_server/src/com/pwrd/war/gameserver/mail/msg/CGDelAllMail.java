package com.pwrd.war.gameserver.mail.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.mail.handler.MailHandlerFactory;

/**
 * 删除所有邮件
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDelAllMail extends CGMessage{
	
	/** 要删除的所有邮件uuid */
	private String[] uuidlist;
	
	public CGDelAllMail (){
	}
	
	public CGDelAllMail (
			String[] uuidlist ){
			this.uuidlist = uuidlist;
	}
	
	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
				uuidlist = new String[count];
				for(int i=0; i<count; i++){
			uuidlist[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(uuidlist.length);
		for(int i=0; i<uuidlist.length; i++){
			writeString(uuidlist[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_DEL_ALL_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DEL_ALL_MAIL";
	}

	public String[] getUuidlist(){
		return uuidlist;
	}

	public void setUuidlist(String[] uuidlist){
		this.uuidlist = uuidlist;
	}	

	@Override
	public void execute() {
		MailHandlerFactory.getHandler().handleDelAllMail(this.getSession().getPlayer(), this);
	}
}