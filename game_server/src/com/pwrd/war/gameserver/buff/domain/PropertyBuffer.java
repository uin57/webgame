package com.pwrd.war.gameserver.buff.domain;

import java.util.Map;

import com.pwrd.war.db.model.BufferEntity;
import com.pwrd.war.gameserver.buff.enums.BuffBigType;
import com.pwrd.war.gameserver.buff.enums.ModifyType;
import com.pwrd.war.gameserver.buff.enums.ValueType;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

public abstract class PropertyBuffer extends Buffer {
	public boolean addBuffer(Human human) {
		Map<BuffBigType, Buffer> buffers = human.getBuffers();
		if (isContain(buffers)) {
			human.sendErrorMessage("已经有相同buff");
			return false;
		} else {
			BufferEntity entity = this.toEntity();
			if (getValueType() == ValueType.numerical.getId()) {
				modifyProperties(human, getValue());
			} else if (getValueType() == ValueType.ratio.getId()) {
				modifyPropertiesRatio(human, getValue());
			}
			Globals.getDaoService().getBufferDao().save(entity);
			this.setId(entity.getId());
			this.setInDb(true);
			buffers.put(this.getBuffBigType(), this);
			human.sendMessage(generatorMessage(human, ModifyType.add));
			return true;
		}
	}

	protected abstract void modifyProperties(Human human, double value);
	protected abstract void modifyPropertiesThisRole(Role role, double value);

	protected abstract void modifyPropertiesRatio(Human human, double value);
	
	protected abstract void modifyPropertiesRatioThisRole(Role role, double value);
	
	@Override
	public void update(Human human) {
		if (getValueType() == ValueType.numerical.getId()) {
			modifyProperties(human, getValue());
		} else if (getValueType() == ValueType.ratio.getId()) {
			modifyPropertiesRatio(human, getValue());
		}
	}
	public void updateThisRole(Role role) {
		if (getValueType() == ValueType.numerical.getId()) {
			modifyPropertiesThisRole(role, getValue());
		} else if (getValueType() == ValueType.ratio.getId()) {
			modifyPropertiesRatioThisRole(role, getValue());
		}
	}
	@Override
	protected void remove(Human human) {
		if (getValueType() == ValueType.numerical.getId()) {
			modifyProperties(human, getValue() * -1);
		} else if (getValueType() == ValueType.ratio.getId()) {
			modifyPropertiesRatio(human, getValue() * -1);
		}
	}

	/**
	 * 
	 * @param human
	 * @param number
	 * @param time
	 * @return
	 */
	@Override
	public double modify(Role role, int number) {
		Human human = null;
		if (role instanceof Human) {
			human = (Human) role;
		} else if (role instanceof Pet) {
			human = ((Pet) role).getOwner();
		} else {
			return number;
		}
		if (getEndTime() < Globals.getTimeService().now()) {
			if (getValueType() == ValueType.numerical.getId()) {
				modifyProperties(human, getValue() * -1);
			} else if (getValueType() == ValueType.ratio.getId()) {
				modifyProperties(human, getValue() * -1);
			}
			human.sendMessage(generatorMessage(human, ModifyType.delete));
		}
		return 0;
	}
}
