package com.pwrd.war.gameserver.buff.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.buff.handler.BuffHandlerFactory;

/**
 * 去除buff
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRomoveBuffer extends CGMessage{
	
	/** buffer 大类型 */
	private int bigType;
	
	public CGRomoveBuffer (){
	}
	
	public CGRomoveBuffer (
			int bigType ){
			this.bigType = bigType;
	}
	
	@Override
	protected boolean readImpl() {
		bigType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bigType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_Romove_Buffer;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Romove_Buffer";
	}

	public int getBigType(){
		return bigType;
	}
		
	public void setBigType(int bigType){
		this.bigType = bigType;
	}

	@Override
	public void execute() {
		BuffHandlerFactory.getHandler().handleRomoveBuffer(this.getSession().getPlayer(), this);
	}
}