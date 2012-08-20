package com.pwrd.war.gameserver.skill.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 技能冷却加速
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSkillCooldown extends GCMessage{
	
	/** cdSn */
	private String cdSn;
	/** cd类型 0 表示增加冷却 1表示加速修改冷却 2表示冷却时间到了结束冷却 */
	private int cdType;
	/** 冷却对象类型，如：0表示技能冷却 */
	private int targetType;
	/** 冷却对象sn */
	private String targetSn;
	/** 剩余冷却时间 */
	private String cooldownTime;
	/** 货币类型 0为元宝 */
	private int currencyType;
	/** 货币数量 */
	private int currencyNumber;

	public GCSkillCooldown (){
	}
	
	public GCSkillCooldown (
			String cdSn,
			int cdType,
			int targetType,
			String targetSn,
			String cooldownTime,
			int currencyType,
			int currencyNumber ){
			this.cdSn = cdSn;
			this.cdType = cdType;
			this.targetType = targetType;
			this.targetSn = targetSn;
			this.cooldownTime = cooldownTime;
			this.currencyType = currencyType;
			this.currencyNumber = currencyNumber;
	}

	@Override
	protected boolean readImpl() {
		cdSn = readString();
		cdType = readInteger();
		targetType = readInteger();
		targetSn = readString();
		cooldownTime = readString();
		currencyType = readInteger();
		currencyNumber = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(cdSn);
		writeInteger(cdType);
		writeInteger(targetType);
		writeString(targetSn);
		writeString(cooldownTime);
		writeInteger(currencyType);
		writeInteger(currencyNumber);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Skill_Cooldown;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Skill_Cooldown";
	}

	public String getCdSn(){
		return cdSn;
	}
		
	public void setCdSn(String cdSn){
		this.cdSn = cdSn;
	}

	public int getCdType(){
		return cdType;
	}
		
	public void setCdType(int cdType){
		this.cdType = cdType;
	}

	public int getTargetType(){
		return targetType;
	}
		
	public void setTargetType(int targetType){
		this.targetType = targetType;
	}

	public String getTargetSn(){
		return targetSn;
	}
		
	public void setTargetSn(String targetSn){
		this.targetSn = targetSn;
	}

	public String getCooldownTime(){
		return cooldownTime;
	}
		
	public void setCooldownTime(String cooldownTime){
		this.cooldownTime = cooldownTime;
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
}