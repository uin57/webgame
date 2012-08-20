/**
 * 
 */
package com.pwrd.war.gameserver.buff.domain;

import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.role.Role;

/**
 * vip体验buff
 * 
 * @author dengdan
 *
 */
public class VipBuffer extends PropertyBuffer {

	@Override
	protected void modifyProperties(Human human, double value) {
		if(value < 0){
			human.getPropertyManager().setBuffVip(0);
			human.snapChangedProperty(true);
		}else{
			human.getPropertyManager().setBuffVip((int)value);
			human.snapChangedProperty(true);
		}
	}

	@Override
	protected void modifyPropertiesThisRole(Role role, double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void modifyPropertiesRatio(Human human, double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void modifyPropertiesRatioThisRole(Role role, double value) {
		// TODO Auto-generated method stub
		
	}

}
