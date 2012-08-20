package com.pwrd.war.gameserver.buff.domain;

import com.pwrd.war.gameserver.buff.enums.ModifyType;
import com.pwrd.war.gameserver.buff.msg.GCBuffChangeMessage;
import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

public class MedicinalBuffer extends Buffer {
	// public boolean addBuffer(Human human) {
	// List<Buffer> buffers = human.getBuffers();
	// if (isContain(buffers)) {
	// for (Buffer buffer : buffers) {
	// if (buffer.getBufferType() == getBufferType()) {
	// buffer.setValue(buffer.getValue() + getValue());
	// buffer.setModified();
	// GCBuffChangeMessage msg = generatorMessage(human,ModifyType.change);
	// msg.setUsedNumber((int) buffer.getValue());
	// human.sendMessage(msg);
	// }
	// }
	// } else {
	// BufferEntity entity = this.toEntity();
	// Globals.getDaoService().getBufferDao().save(entity);
	// this.setId(entity.getId());
	// this.setInDb(true);
	// buffers.add(this);
	// GCBuffChangeMessage msg = generatorMessage(human,ModifyType.add);
	// msg.setUsedNumber((int) getValue());
	// human.sendMessage(msg);
	// }
	// return true;
	// }

	/**
	 * 补血buff中根据用户的血量来判断是否补血，number和time无用
	 */
	@Override
	public double modify(Role role, int number) {
		double lackHp = 0;
		double initValue = getValue();
		Human human = null;
		if (role instanceof Human) {
			human = (Human) role;
			BattleForm defaultForm = human.getDefaultForm();
			if (initValue != 0) {
				for (Role r : defaultForm.getAllBattlePets()) {
					lackHp += modifyRole(r);
				}
				/** 判断当前血气包是否清除 */
				if (initValue != getValue()) {
					if (getValue() == 0) {
						GCBuffChangeMessage msg = generatorMessage(human,ModifyType.delete);
						human.sendMessage(msg);
					} else {
						GCBuffChangeMessage msg = generatorMessage(human,ModifyType.change);
						human.sendMessage(msg);
					}
					human.snapChangedProperty(true);
				}
			}
			return lackHp;
		} else if (role instanceof Pet) {
			human = ((Pet) role).getOwner();
			if (initValue != 0) {
				lackHp += modifyRole(role);
				/** 判断当前血气包是否清除 */
				if (initValue != getValue()) {
					if (getValue() == 0) {
						GCBuffChangeMessage msg = generatorMessage(human,
								ModifyType.delete);
						human.sendMessage(msg);
					} else {
						GCBuffChangeMessage msg = generatorMessage(human,
								ModifyType.change);
						human.sendMessage(msg);
					}
					human.snapChangedProperty(true);
				}
			}
			return lackHp;
		} else {
			return lackHp;
		}

	}

	private double modifyRole(Role role) {
		// 缺少血量
		double lackHp = 0;
		// 还需血量
		double needHp = role.getMaxHp() - role.getCurHp();
		if (needHp != 0) {
			if (needHp < getValue()) {
				role.setCurHp(role.getMaxHp());
				role.snapChangedProperty(true);
				setValue(getValue() - needHp);
			} else {
				lackHp = needHp - getValue();
				role.setCurHp(role.getCurHp() + getValue());
				role.snapChangedProperty(true);
				setValue(0);
			}
		}
		return lackHp;
	}
}
