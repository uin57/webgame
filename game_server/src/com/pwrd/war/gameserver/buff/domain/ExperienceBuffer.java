package com.pwrd.war.gameserver.buff.domain;

import com.pwrd.war.gameserver.buff.enums.ModifyType;
import com.pwrd.war.gameserver.buff.enums.ValueType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

public class ExperienceBuffer extends Buffer {

	@Override
	public double modify(Role role, int number) {
		Human human=null;
		if(role instanceof Human){
			human=(Human) role;
		}else if(role instanceof Pet){
			human=((Pet)role).getOwner();
		}else{
			return number;
		}
		if (getEndTime() < System.currentTimeMillis()) {
			human.sendMessage(generatorMessage(human,ModifyType.delete));
		}
		return 0;
	}
	

	@Override
	public double getValue(double value) {
		if(getValueType()==ValueType.numerical.getId()){
			return getValue();
		}else if(getValueType()==ValueType.ratio.getId()){
			return value*getValue();
		}
		return 0;
	}

}
