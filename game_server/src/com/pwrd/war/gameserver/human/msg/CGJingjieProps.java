package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 请求境界变化信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGJingjieProps extends CGMessage{
	
	/** 目标UUID */
	private String targetUUID;
	
	public CGJingjieProps (){
	}
	
	public CGJingjieProps (
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
		return MessageType.CG_JingJie_Props;
	}
	
	@Override
	public String getTypeName() {
		return "CG_JingJie_Props";
	}

	public String getTargetUUID(){
		return targetUUID;
	}
		
	public void setTargetUUID(String targetUUID){
		this.targetUUID = targetUUID;
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleJingjieProps(this.getSession().getPlayer(), this);
	}
}