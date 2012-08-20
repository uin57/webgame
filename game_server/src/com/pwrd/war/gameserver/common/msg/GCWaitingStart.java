package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 等待开始
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCWaitingStart extends GCMessage{
	
	/** 等待类型,如创建副本等待 */
	private int waitingType;
	/** 超时时间（秒） */
	private int overTime;
	/** 等待标题 */
	private String title;
	/** 等待提示语 */
	private String tip;

	public GCWaitingStart (){
	}
	
	public GCWaitingStart (
			int waitingType,
			int overTime,
			String title,
			String tip ){
			this.waitingType = waitingType;
			this.overTime = overTime;
			this.title = title;
			this.tip = tip;
	}

	@Override
	protected boolean readImpl() {
		waitingType = readInteger();
		overTime = readInteger();
		title = readString();
		tip = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(waitingType);
		writeInteger(overTime);
		writeString(title);
		writeString(tip);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_WAITING_START;
	}
	
	@Override
	public String getTypeName() {
		return "GC_WAITING_START";
	}

	public int getWaitingType(){
		return waitingType;
	}
		
	public void setWaitingType(int waitingType){
		this.waitingType = waitingType;
	}

	public int getOverTime(){
		return overTime;
	}
		
	public void setOverTime(int overTime){
		this.overTime = overTime;
	}

	public String getTitle(){
		return title;
	}
		
	public void setTitle(String title){
		this.title = title;
	}

	public String getTip(){
		return tip;
	}
		
	public void setTip(String tip){
		this.tip = tip;
	}
}