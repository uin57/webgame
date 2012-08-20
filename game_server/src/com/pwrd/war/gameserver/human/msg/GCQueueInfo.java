package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 冷却队列信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCQueueInfo extends GCMessage{
	
	/** 队列类型 */
	private int cdType;
	/** 该类型的索引 */
	private int index;
	/** 剩余时间，单位秒 */
	private int leftTime;
	/** 是否显示 */
	private short isShow;

	public GCQueueInfo (){
	}
	
	public GCQueueInfo (
			int cdType,
			int index,
			int leftTime,
			short isShow ){
			this.cdType = cdType;
			this.index = index;
			this.leftTime = leftTime;
			this.isShow = isShow;
	}

	@Override
	protected boolean readImpl() {
		cdType = readInteger();
		index = readInteger();
		leftTime = readInteger();
		isShow = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(cdType);
		writeInteger(index);
		writeInteger(leftTime);
		writeShort(isShow);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_QUEUE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QUEUE_INFO";
	}

	public int getCdType(){
		return cdType;
	}
		
	public void setCdType(int cdType){
		this.cdType = cdType;
	}

	public int getIndex(){
		return index;
	}
		
	public void setIndex(int index){
		this.index = index;
	}

	public int getLeftTime(){
		return leftTime;
	}
		
	public void setLeftTime(int leftTime){
		this.leftTime = leftTime;
	}

	public short getIsShow(){
		return isShow;
	}
		
	public void setIsShow(short isShow){
		this.isShow = isShow;
	}
}