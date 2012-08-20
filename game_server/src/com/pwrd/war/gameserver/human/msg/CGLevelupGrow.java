package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 提升成长
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGLevelupGrow extends CGMessage{
	
	/** 提升类型 */
	private int growType;
	/** 目标UUID */
	private String targetUUID;
	/** 是否立即消除冷却队列 */
	private boolean coolDown;
	
	public CGLevelupGrow (){
	}
	
	public CGLevelupGrow (
			int growType,
			String targetUUID,
			boolean coolDown ){
			this.growType = growType;
			this.targetUUID = targetUUID;
			this.coolDown = coolDown;
	}
	
	@Override
	protected boolean readImpl() {
		growType = readInteger();
		targetUUID = readString();
		coolDown = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(growType);
		writeString(targetUUID);
		writeBoolean(coolDown);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_LEVELUP_GROW;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LEVELUP_GROW";
	}

	public int getGrowType(){
		return growType;
	}
		
	public void setGrowType(int growType){
		this.growType = growType;
	}

	public String getTargetUUID(){
		return targetUUID;
	}
		
	public void setTargetUUID(String targetUUID){
		this.targetUUID = targetUUID;
	}

	public boolean getCoolDown(){
		return coolDown;
	}
		
	public void setCoolDown(boolean coolDown){
		this.coolDown = coolDown;
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleLevelupGrow(this.getSession().getPlayer(), this);
	}
}