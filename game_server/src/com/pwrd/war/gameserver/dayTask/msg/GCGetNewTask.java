package com.pwrd.war.gameserver.dayTask.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回任务信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGetNewTask extends GCMessage{
	
	/** 是否领取成功 */
	private boolean success;
	/** 失败原因描述 */
	private String desc;
	/** 剩余弹药数 */
	private int number;
	/** 任务id */
	private String taskId;
	/** 当前次数 */
	private int curTimes;

	public GCGetNewTask (){
	}
	
	public GCGetNewTask (
			boolean success,
			String desc,
			int number,
			String taskId,
			int curTimes ){
			this.success = success;
			this.desc = desc;
			this.number = number;
			this.taskId = taskId;
			this.curTimes = curTimes;
	}

	@Override
	protected boolean readImpl() {
		success = readBoolean();
		desc = readString();
		number = readInteger();
		taskId = readString();
		curTimes = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(success);
		writeString(desc);
		writeInteger(number);
		writeString(taskId);
		writeInteger(curTimes);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_NEW_TASK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_NEW_TASK";
	}

	public boolean getSuccess(){
		return success;
	}
		
	public void setSuccess(boolean success){
		this.success = success;
	}

	public String getDesc(){
		return desc;
	}
		
	public void setDesc(String desc){
		this.desc = desc;
	}

	public int getNumber(){
		return number;
	}
		
	public void setNumber(int number){
		this.number = number;
	}

	public String getTaskId(){
		return taskId;
	}
		
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}

	public int getCurTimes(){
		return curTimes;
	}
		
	public void setCurTimes(int curTimes){
		this.curTimes = curTimes;
	}
}