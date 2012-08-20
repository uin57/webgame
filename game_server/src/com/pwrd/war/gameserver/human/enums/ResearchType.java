package com.pwrd.war.gameserver.human.enums;

import com.pwrd.war.gameserver.role.properties.RoleBaseIntProperties;

/**
 * 研究类型
 * @author xf
 */
public enum ResearchType { 
	NONE(0, 0),
	HP(1, RoleBaseIntProperties.MAXHP_LEVEL),	//生命上限（强体术）
	ATK(2, RoleBaseIntProperties.ATK_LEVEL),	//攻击（兵器冶炼）
	DEF(3, RoleBaseIntProperties.DEF_LEVEL),			//防御（护甲术）
	SHORT_ATK(4, RoleBaseIntProperties.SHORT_ATK_LEVEL),		//近战攻击（近战学）
	SHORT_DEF(5, RoleBaseIntProperties.SHORT_DEF_LEVEL),		//近战防御（重甲冶炼）
	REMOTE_ATK(6, RoleBaseIntProperties.REMOTE_ATK_LEVEL),		//远程攻击（弹药学）
	REMOTE_DEF(7, RoleBaseIntProperties.REMOTE_DEF_LEVEL),		//远程防御（轻甲冶炼）
	;
	
	private int id;
	private int propIndex;

	ResearchType(int id, int propIndex) {
		this.id = id;
		this.propIndex = propIndex;
	}

	public static ResearchType getById(int id) {
		for (ResearchType coolType : ResearchType.values()) {
			if (coolType.getId() == id) {
				return coolType;
			}
		}
		return ResearchType.NONE;
	}

	public int getId() {
		return id;
	}

	public int getPropIndex() {
		return propIndex;
	}

}
