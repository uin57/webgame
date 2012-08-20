package com.pwrd.war.common.model.quest;

public class QuestInfo {
	
	private int questId;
	
	private QuestDestInfo[] destInfo;
	
	private short status;

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public QuestDestInfo[] getDestInfo() {
		return destInfo;
	}

	public void setDestInfo(QuestDestInfo[] destInfo) {
		this.destInfo = destInfo;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	
}
