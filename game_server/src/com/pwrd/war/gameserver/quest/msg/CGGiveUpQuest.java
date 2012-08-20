package com.pwrd.war.gameserver.quest.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.quest.handler.QuestHandlerFactory;

/**
 * 放弃已接任务
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGiveUpQuest extends CGMessage{
	
	/** 任务Id */
	private int questId;
	
	public CGGiveUpQuest (){
	}
	
	public CGGiveUpQuest (
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
		return MessageType.CG_GIVE_UP_QUEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GIVE_UP_QUEST";
	}

	public int getQuestId(){
		return questId;
	}
		
	public void setQuestId(int questId){
		this.questId = questId;
	}

	@Override
	public void execute() {
		QuestHandlerFactory.getHandler().handleGiveUpQuest(this.getSession().getPlayer(), this);
	}
}