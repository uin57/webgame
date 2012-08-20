package com.pwrd.war.gameserver.dayTask.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.dayTask.handler.DayTaskHandlerFactory;

/**
 * 放弃任务
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDayTaskDrop extends CGMessage{
	
	/** 任务id */
	private String taskId;
	
	public CGDayTaskDrop (){
	}
	
	public CGDayTaskDrop (
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
		return MessageType.CG_DAY_TASK_DROP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DAY_TASK_DROP";
	}

	public String getTaskId(){
		return taskId;
	}
		
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}

	@Override
	public void execute() {
		DayTaskHandlerFactory.getHandler().handleDayTaskDrop(this.getSession().getPlayer(), this);
	}
}