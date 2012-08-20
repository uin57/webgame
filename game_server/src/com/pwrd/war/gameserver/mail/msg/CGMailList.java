package com.pwrd.war.gameserver.mail.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.mail.handler.MailHandlerFactory;

/**
 * 查询邮件列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGMailList extends CGMessage{
	
	/** 查询的页面索引 */
	private short queryIndex;
	/** 邮箱类型1-inbox,2-system,3-private,4-battle,5-savebox,6-delbox */
	private short boxType;
	
	public CGMailList (){
	}
	
	public CGMailList (
			short queryIndex,
			short boxType ){
			this.queryIndex = queryIndex;
			this.boxType = boxType;
	}
	
	@Override
	protected boolean readImpl() {
		queryIndex = readShort();
		boxType = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(queryIndex);
		writeShort(boxType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_MAIL_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MAIL_LIST";
	}

	public short getQueryIndex(){
		return queryIndex;
	}
		
	public void setQueryIndex(short queryIndex){
		this.queryIndex = queryIndex;
	}

	public short getBoxType(){
		return boxType;
	}
		
	public void setBoxType(short boxType){
		this.boxType = boxType;
	}

	@Override
	public void execute() {
		MailHandlerFactory.getHandler().handleMailList(this.getSession().getPlayer(), this);
	}
}