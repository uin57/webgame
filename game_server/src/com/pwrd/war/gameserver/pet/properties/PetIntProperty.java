package com.pwrd.war.gameserver.pet.properties;


import com.pwrd.war.core.annotation.Comment;
import com.pwrd.war.core.annotation.Type;
import com.pwrd.war.gameserver.role.properties.PropertyObject;
import com.pwrd.war.gameserver.role.properties.PropertyType;

/**
 * 武将一级属性数据对象
 * 
 * 
 */
@Comment(content = "武将整型属性")
public final class PetIntProperty  extends PropertyObject {

	/** 一级属性索引开始值 */
	public static int _BEGIN = 0;
	
	/** 一级属性索引结束值 */
	public static int _END = _BEGIN;

	/** 星级 */
	@Comment(content = "星级")
	@Type(Integer.class)
	public static final int STAR = ++_END;

	/** 成长等级 */
	@Comment(content = "成长等级")
	@Type(Integer.class)
	public static final int GROW_TYPE = ++_END;

	/** 成长等级对应基础成长 */
	@Comment(content = "成长等级对应基础成长")
	@Type(Integer.class)
	public static final int BASE_GROW = ++_END;
	
	/** 武将特有成长 */
	@Comment(content = "武将特有成长")
	@Type(Integer.class)
	public static final int SPECIAL_GROW = ++_END;
	
	@Comment(content = "是否出战")
	@Type(Integer.class)
	public static final int ISIN_BATTLE = ++_END;
	
	@Comment(content = "心情")
	@Type(Integer.class)
	public static final int MOOD = ++_END;
	


	/** 一级属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;
	
	/**
	 * 是否是合法的索引
	 * 
	 * @param index
	 * @return
	 */
	public static final boolean isValidIndex(int index){
		return index>=0&&index<PetIntProperty._SIZE;
	}
	
	public static final PropertyType TYPE = PropertyType.PET_PROP_INT;
	

	public PetIntProperty() {
		super(_SIZE, TYPE);
	}

}
