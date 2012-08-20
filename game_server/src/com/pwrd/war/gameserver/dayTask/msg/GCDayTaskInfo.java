package com.pwrd.war.gameserver.dayTask.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回任务列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCDayTaskInfo extends GCMessage{
	
	/** 剩余弹药数 */
	private int number;
	/** 是否在任务中 */
	private boolean isInTask;
	/** 任务id */
	private String taskId;
	/** 当前次数 */
	private int curTimes;

	public GCDayTaskInfo (){
	}
	
	public GCDayTaskInfo (
			int number,
			boolean isInTask,
			String taskId,
			int curTimes ){
			this.number = number;
			this.isInTask = isInTask;
			this.taskId = taskId;
			this.curTimes = curTimes;
	}

	@Override
	protected boolean readImpl() {
		number = readInteger();
		isInTask = readBoolean();
		taskId = readString();
		curTimes = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(number);
		writeBoolean(isInTask);
		writeString(taskId);
		writeInteger(curTimes);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DAY_TASK_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DAY_TASK_INFO";
	}

	public int getNumber(){
		return number;
	}
		
	public void setNumber(int number){
		this.number = number;
	}

	public boolean getIsInTask(){
		return isInTask;
	}
		
	public void setIsInTask(boolean isInTask){
		this.isInTask = isInTask;
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