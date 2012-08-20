package com.pwrd.war.gameserver.dayTask.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回放弃任务信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCDayTaskDrop extends GCMessage{
	
	/** 结果 */
	private boolean result;
	/** 描述 */
	private String description;

	public GCDayTaskDrop (){
	}
	
	public GCDayTaskDrop (
			boolean result,
			String description ){
			this.result = result;
			this.description = description;
	}

	@Override
	protected boolean readImpl() {
		result = readBoolean();
		description = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(result);
		writeString(description);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DAY_TASK_DROP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DAY_TASK_DROP";
	}

	public boolean getResult(){
		return result;
	}
		
	public void setResult(boolean result){
		this.result = result;
	}

	public String getDescription(){
		return description;
	}
		
	public void setDescription(String description){
		this.description = description;
	}
}