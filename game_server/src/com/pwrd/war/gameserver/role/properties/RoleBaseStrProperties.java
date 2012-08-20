package com.pwrd.war.gameserver.role.properties;

import com.pwrd.war.core.annotation.Comment;
import com.pwrd.war.core.annotation.Type;

/**
 * 基础属性(人物角色，宠物公用)：Object类型，存放Long及非数值类型的属性
 * 
 * 
 */
public class RoleBaseStrProperties extends PropertyObject {

	/** 基础对象型属性索引开始值 */
	private static int _BEGIN = 0;
	
	/** 基础对象型属性索引结束值 */
	public static int _END = _BEGIN;

	/* 公用 */
	

	
	
	/* 人物角色用 */
	@Comment(content = "名称")
	@Type(String.class)
	public static final int NAME = ++_END; 

	
	@Comment(content = "所在地图 Id")
	@Type(String.class)
	public static final int SCENEID = ++_END;
	
	@Comment(content = "骨骼ID")
	@Type(String.class)
	public static final int SKELETONID = ++_END;	
	
	@Comment(content = "弹道动画")
	@Type(String.class)
	public static final int CAST_ANIM = ++_END;
	
	@Comment(content = "被击光效")
	@Type(String.class)
	public static final int ATTACKED_ANIM = ++_END;
	
	@Comment(content = "职业特性弹道动画")
	@Type(String.class)
	public static final int VOACTION_ANIM = ++_END;
	
	@Comment(content = "角色兵法装备信息json数组格式[{name,level,quantity,prop({key:value})}]" +
			"key包括atk,def,skillatk,skilldef,hp,shanbi(%),mingzhong(%),cri(%),renxing(%),addjitui,redjitui")
	@Type(String.class)
	public static final int WARCRAFT_PROPS = ++_END;
	
	/** 基础整型属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;

	public static final PropertyType TYPE = PropertyType.BASE_ROLE_PROPS_STR;

	public RoleBaseStrProperties() {
		super(_SIZE, TYPE);
	}

}
