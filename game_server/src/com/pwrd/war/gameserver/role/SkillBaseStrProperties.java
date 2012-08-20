package com.pwrd.war.gameserver.role;

import com.pwrd.war.core.annotation.Comment;
import com.pwrd.war.core.annotation.Type;
import com.pwrd.war.gameserver.role.properties.PropertyObject;
import com.pwrd.war.gameserver.role.properties.PropertyType;

public class SkillBaseStrProperties extends PropertyObject {

	/** 基础对象型属性索引开始值 */
	private static int _BEGIN = 0;
	
	/** 基础对象型属性索引结束值 */
	public static int _END = _BEGIN;
	
	@Comment(content = "武将技")
	@Type(String.class)
	public static final int PET_SKILL = ++_END;
	
	@Comment(content = "普通技1")
	@Type(String.class)
	public static final int SKILL1 = ++_END;
	
	@Comment(content = "普通技2")
	@Type(String.class)
	public static final int SKILL2 = ++_END;
	
	@Comment(content = "普通技3")
	@Type(String.class)
	public static final int SKILL3 = ++_END;
	
	@Comment(content = "被动技能1")
	@Type(String.class)
	public static final int PASS_SKILL1 = ++_END;
	@Comment(content = "被动技能2")
	@Type(String.class)
	public static final int PASS_SKILL2 = ++_END;
	@Comment(content = "被动技能3")
	@Type(String.class)
	public static final int PASS_SKILL3 = ++_END;
	@Comment(content = "被动技能4")
	@Type(String.class)
	public static final int PASS_SKILL4 = ++_END;
	@Comment(content = "被动技能5")
	@Type(String.class)
	public static final int PASS_SKILL5 = ++_END;
	@Comment(content = "被动技能6")
	@Type(String.class)
	public static final int PASS_SKILL6 = ++_END;
	
	/** 基础整型属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;

	public static final PropertyType TYPE = PropertyType.SKILL_ROLE_PROPS_STR;

	public SkillBaseStrProperties() {
		super(_SIZE, TYPE);
	}
}