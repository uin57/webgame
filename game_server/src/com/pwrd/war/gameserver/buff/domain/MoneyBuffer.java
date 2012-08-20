package com.pwrd.war.gameserver.buff.domain;

import com.pwrd.war.gameserver.buff.enums.ModifyType;
import com.pwrd.war.gameserver.buff.enums.ValueType;
import com.pwrd.war.gameserver.buff.msg.GCBuffChangeMessage;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

public class MoneyBuffer extends Buffer {
	@Override
	public double modify(Role role, int number) {
		if (number == 0) {
			return 0;
		}
		Human human = null;
		if (role instanceof Human) {
			human = (Human) role;
		} else if (role instanceof Pet) {
			human = ((Pet) role).getOwner();
		} else {
			return number;
		}
		GCBuffChangeMessage msg = null;
		// 剩余的次数
		int moneyNumber = getUsedNumber() - number;
		if (moneyNumber <= 0) {
			setUsedNumber(0);
			msg = generatorMessage(human, ModifyType.delete);
		} else {
			setUsedNumber(moneyNumber);
			msg = generatorMessage(human, ModifyType.change);
		}
		human.sendMessage(msg);
		return moneyNumber < 0 ? 0 : moneyNumber;
	}
	@Override
	public double getValue(double value) {
		if (getValueType() == ValueType.numerical.getId()) {
			return getValue();
		} else if (getValueType() == ValueType.ratio.getId()) {
			return value * getValue();
		}
		return 0;
	}

}
