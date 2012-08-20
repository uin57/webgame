package com.pwrd.war.gameserver.vocation.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 职业
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCVocation extends GCMessage{
	
	/** ${field.comment} */
	private com.pwrd.war.gameserver.vocation.VocationSkill[] vocationSkills;

	public GCVocation (){
	}
	
	public GCVocation (
			com.pwrd.war.gameserver.vocation.VocationSkill[] vocationSkills ){
			this.vocationSkills = vocationSkills;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		vocationSkills = new com.pwrd.war.gameserver.vocation.VocationSkill[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.vocation.VocationSkill obj = new com.pwrd.war.gameserver.vocation.VocationSkill();
			obj.setVocationType(readInteger());
			{
				int subCount = readShort();
							com.pwrd.war.gameserver.vocation.SkillGroup[] subList = new com.pwrd.war.gameserver.vocation.SkillGroup[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = new com.pwrd.war.gameserver.vocation.SkillGroup();
									subList[j].setNumber(readInteger());
									subList[j].setChoose(readBoolean());
									subList[j].setName(readString());
									subList[j].setSkillSn1(readString());
									subList[j].setSkillRank1(readInteger());
									subList[j].setSkillSn2(readString());
									subList[j].setSkillRank2(readInteger());
									subList[j].setSkillSn3(readString());
									subList[j].setSkillRank3(readInteger());
									subList[j].setSkillSn4(readString());
									subList[j].setSkillRank4(readInteger());
															}
				obj.setSkillGroups(subList);
			}
			{
				int subCount = readShort();
							com.pwrd.war.gameserver.vocation.SkillUnit[] subList = new com.pwrd.war.gameserver.vocation.SkillUnit[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = new com.pwrd.war.gameserver.vocation.SkillUnit();
									subList[j].setSkillSn(readString());
									subList[j].setSkillRank(readInteger());
															}
				obj.setSkillUnits(subList);
			}
			vocationSkills[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(vocationSkills.length);
		for(int i=0; i<vocationSkills.length; i++){
			writeInteger(vocationSkills[i].getVocationType());
			com.pwrd.war.gameserver.vocation.SkillGroup[] skillGroups=vocationSkills[i].getSkillGroups();
			writeShort(skillGroups.length);
			for(int j=0; j<skillGroups.length; j++){
			writeInteger(skillGroups[j].getNumber());
			writeBoolean(skillGroups[j].getChoose());
			writeString(skillGroups[j].getName());
			writeString(skillGroups[j].getSkillSn1());
			writeInteger(skillGroups[j].getSkillRank1());
			writeString(skillGroups[j].getSkillSn2());
			writeInteger(skillGroups[j].getSkillRank2());
			writeString(skillGroups[j].getSkillSn3());
			writeInteger(skillGroups[j].getSkillRank3());
			writeString(skillGroups[j].getSkillSn4());
			writeInteger(skillGroups[j].getSkillRank4());
			}
			com.pwrd.war.gameserver.vocation.SkillUnit[] skillUnits=vocationSkills[i].getSkillUnits();
			writeShort(skillUnits.length);
			for(int j=0; j<skillUnits.length; j++){
			writeString(skillUnits[j].getSkillSn());
			writeInteger(skillUnits[j].getSkillRank());
			}
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Vocation;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Vocation";
	}

	public com.pwrd.war.gameserver.vocation.VocationSkill[] getVocationSkills(){
		return vocationSkills;
	}

	public void setVocationSkills(com.pwrd.war.gameserver.vocation.VocationSkill[] vocationSkills){
		this.vocationSkills = vocationSkills;
	}	
}