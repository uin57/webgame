package com.pwrd.war.gameserver.fight.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.fight.handler.FightHandlerFactory;

/**
 * 触发战斗消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBattleBeginMessage extends CGMessage{
	
	/** 是否是明雷 */
	private boolean isPVP;
	/** 目标Sn */
	private String targetSn;
	/** 战斗类型 */
	private int battleType;
	
	public CGBattleBeginMessage (){
	}
	
	public CGBattleBeginMessage (
			boolean isPVP,
			String targetSn,
			int battleType ){
			this.isPVP = isPVP;
			this.targetSn = targetSn;
			this.battleType = battleType;
	}
	
	@Override
	protected boolean readImpl() {
		isPVP = readBoolean();
		targetSn = readString();
		battleType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(isPVP);
		writeString(targetSn);
		writeInteger(battleType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_Battle_Begin_Message;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Battle_Begin_Message";
	}

	public boolean getIsPVP(){
		return isPVP;
	}
		
	public void setIsPVP(boolean isPVP){
		this.isPVP = isPVP;
	}

	public String getTargetSn(){
		return targetSn;
	}
		
	public void setTargetSn(String targetSn){
		this.targetSn = targetSn;
	}

	public int getBattleType(){
		return battleType;
	}
		
	public void setBattleType(int battleType){
		this.battleType = battleType;
	}

	@Override
	public void execute() {
		FightHandlerFactory.getHandler().handleBattleBeginMessage(this.getSession().getPlayer(), this);
	}
}