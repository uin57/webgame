package com.pwrd.war.gameserver.quest.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回任务列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCQuestList extends GCMessage{
	
	/** 任务列表 */
	private com.pwrd.war.common.model.quest.QuestInfo[] questInfos;

	public GCQuestList (){
	}
	
	public GCQuestList (
			com.pwrd.war.common.model.quest.QuestInfo[] questInfos ){
			this.questInfos = questInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		questInfos = new com.pwrd.war.common.model.quest.QuestInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.common.model.quest.QuestInfo obj = new com.pwrd.war.common.model.quest.QuestInfo();
			obj.setQuestId(readInteger());
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
				obj.setDestInfo(subList);
			}
			obj.setStatus(readShort());
			questInfos[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(questInfos.length);
		for(int i=0; i<questInfos.length; i++){
			writeInteger(questInfos[i].getQuestId());
			com.pwrd.war.common.model.quest.QuestDestInfo[] destInfo=questInfos[i].getDestInfo();
			writeShort(destInfo.length);
			for(int j=0; j<destInfo.length; j++){
			writeShort(destInfo[j].getDestType());
			writeString(destInfo[j].getParam());
			writeShort(destInfo[j].getDestReqNum());
			writeShort(destInfo[j].getDestGotNum());
			}
			writeShort(questInfos[i].getStatus());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_QUEST_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QUEST_LIST";
	}

	public com.pwrd.war.common.model.quest.QuestInfo[] getQuestInfos(){
		return questInfos;
	}

	public void setQuestInfos(com.pwrd.war.common.model.quest.QuestInfo[] questInfos){
		this.questInfos = questInfos;
	}	
}