package com.pwrd.war.gameserver.vocation.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.vocation.handler.VocationHandlerFactory;

/**
 * 设置默认技能组
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGDefaultSkillGroup extends CGMessage{
	
	/** 职业类型 */
	private int vocationType;
	/** 默认技能组编号 */
	private int defaultSkillGroupOrder;
	
	public CGDefaultSkillGroup (){
	}
	
	public CGDefaultSkillGroup (
			int vocationType,
			int defaultSkillGroupOrder ){
			this.vocationType = vocationType;
			this.defaultSkillGroupOrder = defaultSkillGroupOrder;
	}
	
	@Override
	protected boolean readImpl() {
		vocationType = readInteger();
		defaultSkillGroupOrder = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(vocationType);
		writeInteger(defaultSkillGroupOrder);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_Default_Skill_Group;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Default_Skill_Group";
	}

	public int getVocationType(){
		return vocationType;
	}
		
	public void setVocationType(int vocationType){
		this.vocationType = vocationType;
	}

	public int getDefaultSkillGroupOrder(){
		return defaultSkillGroupOrder;
	}
		
	public void setDefaultSkillGroupOrder(int defaultSkillGroupOrder){
		this.defaultSkillGroupOrder = defaultSkillGroupOrder;
	}

	@Override
	public void execute() {
		VocationHandlerFactory.getHandler().handleDefaultSkillGroup(this.getSession().getPlayer(), this);
	}
}