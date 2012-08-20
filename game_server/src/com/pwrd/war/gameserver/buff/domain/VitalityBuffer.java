package com.pwrd.war.gameserver.buff.domain;

import com.pwrd.war.gameserver.buff.enums.ModifyType;
import com.pwrd.war.gameserver.buff.msg.GCBuffChangeMessage;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

/**
 * 修改体力
 * 
 * @author zhutao
 * 
 */
public class VitalityBuffer extends Buffer {
	@Override
	public double modify(Role role , int number) {
		if(number==0){
			return 0;
		}
		Human human=null;
		if(role instanceof Human){
			human=(Human) role;
		}else if(role instanceof Pet){
			human=((Pet)role).getOwner();
		}else{
			return number;
		}
		GCBuffChangeMessage msg = null;
		// 剩余的次数
		double numberVal = getValue() - number;
		double val=0;
		if (numberVal <= 0) {
			setValue(0);
			msg = generatorMessage(human,ModifyType.delete);
		} else {
			setValue(numberVal);
			msg = generatorMessage(human,ModifyType.change);
			val=number-getValue();
		}
		human.sendMessage(msg);
		return val < 0 ? 0 : val;
	}
}
