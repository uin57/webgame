package com.pwrd.war.gameserver.skill.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.skill.handler.SkillHandlerFactory;

/**
 * 技能冷却加速
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSkillCooldown extends CGMessage{
	
	/** cdSn */
	private String cdSn;
	/** 加速的时间 */
	private String accelerateTime;
	/** 货币类型 0为元宝 */
	private int currencyType;
	/** 货币数量 */
	private int currencyNumber;
	
	public CGSkillCooldown (){
	}
	
	public CGSkillCooldown (
			String cdSn,
			String accelerateTime,
			int currencyType,
			int currencyNumber ){
			this.cdSn = cdSn;
			this.accelerateTime = accelerateTime;
			this.currencyType = currencyType;
			this.currencyNumber = currencyNumber;
	}
	
	@Override
	protected boolean readImpl() {
		cdSn = readString();
		accelerateTime = readString();
		currencyType = readInteger();
		currencyNumber = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(cdSn);
		writeString(accelerateTime);
		writeInteger(currencyType);
		writeInteger(currencyNumber);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_Skill_Cooldown;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Skill_Cooldown";
	}

	public String getCdSn(){
		return cdSn;
	}
		
	public void setCdSn(String cdSn){
		this.cdSn = cdSn;
	}

	public String getAccelerateTime(){
		return accelerateTime;
	}
		
	public void setAccelerateTime(String accelerateTime){
		this.accelerateTime = accelerateTime;
	}

	public int getCurrencyType(){
		return currencyType;
	}
		
	public void setCurrencyType(int currencyType){
		this.currencyType = currencyType;
	}

	public int getCurrencyNumber(){
		return currencyNumber;
	}
		
	public void setCurrencyNumber(int currencyNumber){
		this.currencyNumber = currencyNumber;
	}

	@Override
	public void execute() {
		SkillHandlerFactory.getHandler().handleSkillCooldown(this.getSession().getPlayer(), this);
	}
}