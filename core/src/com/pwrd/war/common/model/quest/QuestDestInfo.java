package com.pwrd.war.common.model.quest;


/**
 * 
 * 任务目标信息
 * 
 * 
 */
public class QuestDestInfo {

	/** 任务目标类型 */
	private short destType;
	/** 任务目标参数 */
	private String param;
	/** 需求数量 */
	private short destReqNum;
	/** 已完成数量 */
	private short destGotNum;
	
	public QuestDestInfo()
	{
		super();
	}

	public QuestDestInfo(short destType, String param, short destReqNum, short destGotNum) {
		super();
		this.destType = destType;
		this.param = param;
		this.destReqNum = destReqNum;
		this.destGotNum = destGotNum;
	}

	public short getDestType() {
		return destType;
	}

	public void setDestType(short destType) {
		this.destType = destType;
	}
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public short getDestReqNum() {
		return destReqNum;
	}

	public void setDestReqNum(short destReqNum) {
		this.destReqNum = destReqNum;
	}

	public short getDestGotNum() {
		return destGotNum;
	}

	public void setDestGotNum(short destGotNum) {
		this.destGotNum = destGotNum;
	}


	@Override
	public String toString() {
		return "QuestDestInfo [destGotNum=" + destGotNum + ", destReqNum="
				+ destReqNum + ", destType=" + destType + "]";
	}

}
