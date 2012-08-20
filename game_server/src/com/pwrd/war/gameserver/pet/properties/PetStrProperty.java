package com.pwrd.war.gameserver.pet.properties;

import com.pwrd.war.core.annotation.Comment;
import com.pwrd.war.gameserver.role.properties.PropertyObject;
import com.pwrd.war.gameserver.role.properties.PropertyType;

/**
 * 武将二级属性索引定义
 * 
 * 
 */
@Comment(content="武将字符型属性")
public final class PetStrProperty extends PropertyObject {
	
	/** 二级属性索引开始值 */
	public static int _BEGIN = 0;

	/** 二级属性索引结束值 */
	public static int _END = _BEGIN;
	
 
	 
	
	/** 二级属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;
	
	public static final PropertyType TYPE = PropertyType.PET_PROP_STR; 
	
	/**
	 * 是否是合法的索引
	 * 
	 * @param index
	 * @return
	 */
	public static final boolean isValidIndex(int index){
		return index>=0&&index<PetStrProperty._SIZE;
	}
	
	public PetStrProperty() {
		super(_SIZE,TYPE);
	}
	
}
