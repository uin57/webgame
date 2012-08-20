package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 领取登陆奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGLoginPrize extends CGMessage{
	
	/** 领取第几天的 */
	private int day;
	
	public CGLoginPrize (){
	}
	
	public CGLoginPrize (
			int day ){
			this.day = day;
	}
	
	@Override
	protected boolean readImpl() {
		day = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(day);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_LOGIN_PRIZE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LOGIN_PRIZE";
	}

	public int getDay(){
		return day;
	}
		
	public void setDay(int day){
		this.day = day;
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleLoginPrize(this.getSession().getPlayer(), this);
	}
}