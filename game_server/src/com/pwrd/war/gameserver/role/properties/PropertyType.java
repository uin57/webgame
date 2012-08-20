package com.pwrd.war.gameserver.role.properties;

import com.pwrd.war.gameserver.pet.properties.PetIntProperty;
import com.pwrd.war.gameserver.pet.properties.PetStrProperty;


/**
 * 定义属性对象的类型
 * 
 */
public enum PropertyType {
	DUMY(1),
	/** 人物的一级属性 */
	HUMAN_PROP_A(2),
	
	/** 人物的字符串属性 **/
	HUMAN_PROP_STR(11),
	
	/** w武将的整型属性 */
	PET_PROP_INT(3),

	/** 武将的字符型属性 */
	PET_PROP_STR(4),
	
	/** 角色一级属性 **/
	BASE_ROLE_PROPS_INT_A(12),
	
	/** 基础属性（角色，宠物）： 数值类型  二级 int */
	BASE_ROLE_PROPS_INT(5),
	
	/** 基础属性（角色，宠物）： 非数值类型  String */
	BASE_ROLE_PROPS_STR(6),
	
	/** 技能相关属性： 数值类型  二级 int */
	SKILL_ROLE_PROPS_INT(100),
	
	/** 技能相关属性（角色，宠物）： 非数值类型  String */
	SKILL_ROLE_PROPS_STR(101),
	
	/** 角色属性：Object类型，存放在游戏过程中不改变，或者无需告知客户端的各种类型属性 */
	ROLE_PROPS_FINAL(7),
	
	/** 兵的一级属性*/
	SOLDIER_PROP_A(8),
		
	/** 兵的二级属性*/
	SOLDIER_PROP_B(9);
	; 
	
	private int type;
	private PropertyType(int type){
		this.type = type;
	}
	public int getType(){
		return type;
	}
	
	/**
	 * 产生属性的KEY值，用于服务器之间，服务器和客户端之间数据发送接受
	 * 
	 * @param index
	 *           属性在Property类中的索引
	 * @param propertyType
	 *           Property类的类型
	 * @return
	 */
	public  int genPropertyKey(int index){
		return this.type * 100 + index;
	}
	
	public static void assertPropertyType(PropertyType propType) {
		if (propType != PetIntProperty.TYPE && propType != PetStrProperty.TYPE) {
			throw new IllegalArgumentException("Not a valid PropLevel key ["
					+ propType + "]");
		}
	}

}
