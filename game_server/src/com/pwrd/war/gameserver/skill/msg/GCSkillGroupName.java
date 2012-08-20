package com.pwrd.war.gameserver.skill.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 修改技能组名称
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSkillGroupName extends GCMessage{
	
	/** 职业类型 */
	private int vocationType;
	/** 技能组编号 */
	private int skillGroupOrder;
	/** 技能组名称 */
	private String skillGroupName;

	public GCSkillGroupName (){
	}
	
	public GCSkillGroupName (
			int vocationType,
			int skillGroupOrder,
			String skillGroupName ){
			this.vocationType = vocationType;
			this.skillGroupOrder = skillGroupOrder;
			this.skillGroupName = skillGroupName;
	}

	@Override
	protected boolean readImpl() {
		vocationType = readInteger();
		skillGroupOrder = readInteger();
		skillGroupName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(vocationType);
		writeInteger(skillGroupOrder);
		writeString(skillGroupName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Skill_Group_Name;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Skill_Group_Name";
	}

	public int getVocationType(){
		return vocationType;
	}
		
	public void setVocationType(int vocationType){
		this.vocationType = vocationType;
	}

	public int getSkillGroupOrder(){
		return skillGroupOrder;
	}
		
	public void setSkillGroupOrder(int skillGroupOrder){
		this.skillGroupOrder = skillGroupOrder;
	}

	public String getSkillGroupName(){
		return skillGroupName;
	}
		
	public void setSkillGroupName(String skillGroupName){
		this.skillGroupName = skillGroupName;
	}
}