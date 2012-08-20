package com.pwrd.war.gameserver.skill.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.skill.handler.SkillHandlerFactory;

/**
 * 技能升级消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSkillUpgrade extends CGMessage{
	
	/** 技能sn */
	private String skillSn;
	/** 等级 */
	private int rank;
	
	public CGSkillUpgrade (){
	}
	
	public CGSkillUpgrade (
			String skillSn,
			int rank ){
			this.skillSn = skillSn;
			this.rank = rank;
	}
	
	@Override
	protected boolean readImpl() {
		skillSn = readString();
		rank = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(skillSn);
		writeInteger(rank);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_Skill_Upgrade;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Skill_Upgrade";
	}

	public String getSkillSn(){
		return skillSn;
	}
		
	public void setSkillSn(String skillSn){
		this.skillSn = skillSn;
	}

	public int getRank(){
		return rank;
	}
		
	public void setRank(int rank){
		this.rank = rank;
	}

	@Override
	public void execute() {
		SkillHandlerFactory.getHandler().handleSkillUpgrade(this.getSession().getPlayer(), this);
	}
}