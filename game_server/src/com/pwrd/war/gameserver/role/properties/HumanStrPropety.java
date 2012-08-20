package com.pwrd.war.gameserver.role.properties;

import com.pwrd.war.core.annotation.Comment;
import com.pwrd.war.core.annotation.Type;

public class HumanStrPropety extends PropertyObject {

	/** 基础对象型属性索引开始值 */
	private static int _BEGIN = 0;
	
	/** 基础对象型属性索引结束值 */
	public static int _END = _BEGIN;
	
	@Comment(content = "玩家之前场景id")
	@Type(String.class)
	public static final int BEFORESCENEID =  ++_END;

	@Comment(content = "个性签名")
	@Type(String.class)
	public static final int PERSONSIGN =  ++_END;
	
	@Comment(content = "玩家家族名称")
	@Type(String.class)
	public static final int FAMILY_NAME =  ++_END;
	
	@Comment(content = "玩家家族id")
	@Type(String.class)
	public static final int FAMILY_ID =  ++_END;

	@Comment(content = "开启的功能")
	@Type(String.class)
	public static final int OPEN_FUNCTION =  ++_END;

	
	/** 基础整型属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;

	public static final PropertyType TYPE = PropertyType.HUMAN_PROP_STR;

	public HumanStrPropety() {
		super(_SIZE, TYPE);
	} 

}
