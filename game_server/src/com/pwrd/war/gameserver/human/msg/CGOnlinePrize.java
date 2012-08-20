package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 领取奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGOnlinePrize extends CGMessage{
	
	/** 步骤 */
	private int buzhou;
	
	public CGOnlinePrize (){
	}
	
	public CGOnlinePrize (
			int buzhou ){
			this.buzhou = buzhou;
	}
	
	@Override
	protected boolean readImpl() {
		buzhou = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buzhou);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ONLINE_PRIZE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ONLINE_PRIZE";
	}

	public int getBuzhou(){
		return buzhou;
	}
		
	public void setBuzhou(int buzhou){
		this.buzhou = buzhou;
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleOnlinePrize(this.getSession().getPlayer(), this);
	}
}