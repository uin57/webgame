package com.pwrd.war.gameserver.quest.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.quest.handler.QuestHandlerFactory;

/**
 * 请求任务列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGQuestList extends CGMessage{
	
	
	public CGQuestList (){
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
		return MessageType.CG_QUEST_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_QUEST_LIST";
	}

	@Override
	public void execute() {
		QuestHandlerFactory.getHandler().handleQuestList(this.getSession().getPlayer(), this);
	}
}