package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 取得成就列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGArchiveList extends CGMessage{
	
	
	public CGArchiveList (){
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
		return MessageType.CG_ARCHIVE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ARCHIVE_LIST";
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleArchiveList(this.getSession().getPlayer(), this);
	}
}