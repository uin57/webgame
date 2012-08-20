package com.pwrd.war.gameserver.buff.domain;

import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.role.Role;

public class AttackBuffer extends PropertyBuffer {

	@Override
	protected void modifyProperties(Human human, double value) {
		BattleForm form = human.getDefaultForm();
		for (Role role : form.getAllBattlePets()) {
			this.modifyPropertiesThisRole(role, value);
		}
	}
	protected void modifyPropertiesThisRole(Role role, double value){
		role.setBuffAtk(role.getBuffAtk() + value); 
		role.snapChangedProperty(true);
	}
	@Override
	protected void modifyPropertiesRatio(Human human, double value) {
		BattleForm form = human.getDefaultForm();
		for (Role role : form.getAllBattlePets()) {
			this.modifyPropertiesRatioThisRole(role, value);
		}
	}
	protected void modifyPropertiesRatioThisRole(Role role, double value){ 
		double addAtk = (role.getAtk() - role.getBuffAtk() ) * value;
		role.setBuffAtk(role.getBuffAtk() + addAtk); 
		role.snapChangedProperty(true);
	}
}
