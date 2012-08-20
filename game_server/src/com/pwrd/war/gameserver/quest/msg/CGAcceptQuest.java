package com.pwrd.war.gameserver.quest.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.quest.handler.QuestHandlerFactory;

/**
 * 接受任务
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGAcceptQuest extends CGMessage{
	
	/** 任务Id */
	private int questId;
	
	public CGAcceptQuest (){
	}
	
	public CGAcceptQuest (
			int questId ){
			this.questId = questId;
	}
	
	@Override
	protected boolean readImpl() {
		questId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(questId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ACCEPT_QUEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ACCEPT_QUEST";
	}

	public int getQuestId(){
		return questId;
	}
		
	public void setQuestId(int questId){
		this.questId = questId;
	}

	@Override
	public void execute() {
		QuestHandlerFactory.getHandler().handleAcceptQuest(this.getSession().getPlayer(), this);
	}
}