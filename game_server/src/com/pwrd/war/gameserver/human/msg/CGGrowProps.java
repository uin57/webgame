package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 取得成长变化对应属性增加
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGrowProps extends CGMessage{
	
	/** 目标UUID */
	private String targetUUID;
	
	public CGGrowProps (){
	}
	
	public CGGrowProps (
			String targetUUID ){
			this.targetUUID = targetUUID;
	}
	
	@Override
	protected boolean readImpl() {
		targetUUID = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(targetUUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GROW_PROPS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GROW_PROPS";
	}

	public String getTargetUUID(){
		return targetUUID;
	}
		
	public void setTargetUUID(String targetUUID){
		this.targetUUID = targetUUID;
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleGrowProps(this.getSession().getPlayer(), this);
	}
}