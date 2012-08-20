package com.pwrd.war.gameserver.dayTask.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回任务完成信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCDayTaskComplete extends GCMessage{
	
	/** 任务id */
	private String taskId;

	public GCDayTaskComplete (){
	}
	
	public GCDayTaskComplete (
			String taskId ){
			this.taskId = taskId;
	}

	@Override
	protected boolean readImpl() {
		taskId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(taskId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DAY_TASK_COMPLETE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DAY_TASK_COMPLETE";
	}

	public String getTaskId(){
		return taskId;
	}
		
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}
}