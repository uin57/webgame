package com.pwrd.war.gameserver.quest.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 任务更新消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCQuestUpdate extends GCMessage{
	
	/** 单个任务 */
	private com.pwrd.war.common.model.quest.QuestInfo questInfo;

	public GCQuestUpdate (){
	}
	
	public GCQuestUpdate (
			com.pwrd.war.common.model.quest.QuestInfo questInfo ){
			this.questInfo = questInfo;
	}

	@Override
	protected boolean readImpl() {
		questInfo = new com.pwrd.war.common.model.quest.QuestInfo();
					questInfo.setQuestId(readInteger());
						{
			int subCount = readShort();
							com.pwrd.war.common.model.quest.QuestDestInfo[] subList = new com.pwrd.war.common.model.quest.QuestDestInfo[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = new com.pwrd.war.common.model.quest.QuestDestInfo();
									subList[j].setDestType(readShort());
									subList[j].setParam(readString());
									subList[j].setDestReqNum(readShort());
									subList[j].setDestGotNum(readShort());
															}
			questInfo.setDestInfo(subList);
		}
							questInfo.setStatus(readShort());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(questInfo.getQuestId());
		com.pwrd.war.common.model.quest.QuestDestInfo[] destInfo=questInfo.getDestInfo();
		writeShort(destInfo.length);
		for(int i=0; i<destInfo.length; i++){	
			writeShort(destInfo[i].getDestType());
			writeString(destInfo[i].getParam());
			writeShort(destInfo[i].getDestReqNum());
			writeShort(destInfo[i].getDestGotNum());
		}
		writeShort(questInfo.getStatus());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_QUEST_UPDATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QUEST_UPDATE";
	}

	public com.pwrd.war.common.model.quest.QuestInfo getQuestInfo(){
		return questInfo;
	}
		
	public void setQuestInfo(com.pwrd.war.common.model.quest.QuestInfo questInfo){
		this.questInfo = questInfo;
	}
}