package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 采集修炼标志
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGXiulianCollectSymbol extends CGMessage{
	
	/** 目标UUID */
	private String targetUUID;
	
	public CGXiulianCollectSymbol (){
	}
	
	public CGXiulianCollectSymbol (
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
		return MessageType.CG_XIULIAN_COLLECT_SYMBOL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_XIULIAN_COLLECT_SYMBOL";
	}

	public String getTargetUUID(){
		return targetUUID;
	}
		
	public void setTargetUUID(String targetUUID){
		this.targetUUID = targetUUID;
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleXiulianCollectSymbol(this.getSession().getPlayer(), this);
	}
}