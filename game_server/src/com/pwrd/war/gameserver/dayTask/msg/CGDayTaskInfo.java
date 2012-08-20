package com.pwrd.war.gameserver.dayTask.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.dayTask.handler.DayTaskHandlerFactory;

/**
 * 查询任务列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDayTaskInfo extends CGMessage{
	
	
	public CGDayTaskInfo (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_DAY_TASK_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DAY_TASK_INFO";
	}

	@Override
	public void execute() {
		DayTaskHandlerFactory.getHandler().handleDayTaskInfo(this.getSession().getPlayer(), this);
	}
}