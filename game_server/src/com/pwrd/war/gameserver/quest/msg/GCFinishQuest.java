package com.pwrd.war.gameserver.quest.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 完成任务结果
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFinishQuest extends GCMessage{
	
	/** 任务Id */
	private int questId;

	public GCFinishQuest (){
	}
	
	public GCFinishQuest (
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
		return MessageType.GC_FINISH_QUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FINISH_QUEST";
	}

	public int getQuestId(){
		return questId;
	}
		
	public void setQuestId(int questId){
		this.questId = questId;
	}
}