package com.pwrd.war.gameserver.skill.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 修改技能组顺序
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSkillOrder extends GCMessage{
	
	/** 职业类型 */
	private int vocationType;
	/** 技能组编号 */
	private int skillGroupOrder;
	/** 技能sn序列 */
	private String[] skillSns;

	public GCSkillOrder (){
	}
	
	public GCSkillOrder (
			int vocationType,
			int skillGroupOrder,
			String[] skillSns ){
			this.vocationType = vocationType;
			this.skillGroupOrder = skillGroupOrder;
			this.skillSns = skillSns;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		vocationType = readInteger();
		skillGroupOrder = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		skillSns = new String[count];
		for(int i=0; i<count; i++){
			skillSns[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(vocationType);
		writeInteger(skillGroupOrder);
		writeShort(skillSns.length);
		for(int i=0; i<skillSns.length; i++){
			writeString(skillSns[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Skill_Order;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Skill_Order";
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

	public String[] getSkillSns(){
		return skillSns;
	}

	public void setSkillSns(String[] skillSns){
		this.skillSns = skillSns;
	}	
}