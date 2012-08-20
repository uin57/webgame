package com.pwrd.war.gameserver.buff.domain;

import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.role.Role;

public class DefenseBuffer extends PropertyBuffer {

	@Override
	protected void modifyProperties(Human human, double value) {
		BattleForm form = human.getDefaultForm();
		for (Role role : form.getAllBattlePets()) {
			this.modifyPropertiesThisRole(role, value);
		}
	}

	@Override
	protected void modifyPropertiesRatio(Human human, double value) {
		BattleForm form = human.getDefaultForm();
		for (Role role : form.getAllBattlePets()) {
			this.modifyPropertiesRatioThisRole(role, value);
		}
	}

	@Override
	protected void modifyPropertiesThisRole(Role role, double value) {
		role.setBuffDef(role.getBuffDef() + value);
		role.snapChangedProperty(true);
	}

	@Override
	protected void modifyPropertiesRatioThisRole(Role role, double value) {
		double addDef = (role.getDef() - role.getBuffDef()) * value;
		role.setBuffDef(role.getBuffDef() + addDef);
		role.snapChangedProperty(true);
	}

}
