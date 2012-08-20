package com.pwrd.war.gameserver.pet.effector;

import com.pwrd.war.gameserver.role.Role;
import com.pwrd.war.gameserver.role.properties.FloatNumberPropertyObject;

public interface SoldierPropertyEffector<P extends FloatNumberPropertyObject, R extends Role> {
	
	/**
	 * 计算该效应提供的属性值
	 * 
	 * @param 角色
	 * @return 返回计算后的属性值
	 */
	void effect(P property,R role);

}
