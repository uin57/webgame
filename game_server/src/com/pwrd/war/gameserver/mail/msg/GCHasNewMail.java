package com.pwrd.war.gameserver.mail.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 新邮件提示
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHasNewMail extends GCMessage{
	

	public GCHasNewMail (){
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
		return MessageType.GC_HAS_NEW_MAIL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HAS_NEW_MAIL";
	}
}