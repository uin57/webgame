package com.pwrd.war.gameserver.role.properties;

import com.pwrd.war.core.annotation.Comment;
import com.pwrd.war.core.annotation.Type;

public class SkillBaseIntProperties extends PropertyObject {
	/** 基础整型属性索引开始值 */
	public static int _BEGIN = 0;

	/** 基础整型属性索引结束值 */
	public static int _END = _BEGIN;

	@Comment(content = "武将技等级")
	@Type(Integer.class)
	public static final int PET_SKILL_LEVEL = ++_END;
	
	@Comment(content = "普通技1等级")
	@Type(String.class)
	public static final int SKILL_LEVEL1 = ++_END;

	@Comment(content = "普通技2等级")
	@Type(String.class)
	public static final int SKILL_LEVEL2 = ++_END;

	@Comment(content = "普通技3等级")
	@Type(String.class)
	public static final int SKILL_LEVEL3 = ++_END;

	@Comment(content = "被动技能1等级")
	@Type(String.class)
	public static final int PASS_SKILL_LEVEL1 = ++_END;
	@Comment(content = "被动技能2等级")
	@Type(String.class)
	public static final int PASS_SKILL_LEVEL2 = ++_END;
	@Comment(content = "被动技能3等级")
	@Type(String.class)
	public static final int PASS_SKILL_LEVEL3 = ++_END;
	@Comment(content = "被动技能4等级")
	@Type(String.class)
	public static final int PASS_SKILL_LEVEL4 = ++_END;
	@Comment(content = "被动技能5等级")
	@Type(String.class)
	public static final int PASS_SKILL_LEVEL5 = ++_END;
	@Comment(content = "被动技能6等级")
	@Type(String.class)
	public static final int PASS_SKILL_LEVEL6 = ++_END;

	/** 基础整型属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;

	public static final PropertyType TYPE = PropertyType.SKILL_ROLE_PROPS_INT;

	public SkillBaseIntProperties() {
		super(_SIZE, TYPE);
	}

}