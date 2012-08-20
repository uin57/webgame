package com.pwrd.war.gameserver.dayTask.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.dayTask.handler.DayTaskHandlerFactory;

/**
 * 领取新任务
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetNewTask extends CGMessage{
	
	
	public CGGetNewTask (){
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
		return MessageType.CG_GET_NEW_TASK;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_NEW_TASK";
	}

	@Override
	public void execute() {
		DayTaskHandlerFactory.getHandler().handleGetNewTask(this.getSession().getPlayer(), this);
	}
}