package com.pwrd.war.gameserver.common.listener;

import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.RoleLevelUpEvent;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

/**
 * 监听角色升级时的处理器
 * @author zy
 *
 */
public class RoleLevelUpListener implements IEventListener<RoleLevelUpEvent> {

	@Override
	public void fireEvent(RoleLevelUpEvent event) {
		initLevel(event.getInfo());
//		modifyBufferValue(event.getInfo());
		
		//升级后发送阵法信息，第一次登录时忽略
		if (event.getLevel() > 1) {
			updateBattleForm(event.getInfo());
		}
	}

	private void modifyBufferValue(Role role) {
		Human human = null;
		if (role instanceof Human) {
			human = (Human) role;
		}else if( role instanceof Pet){
			human = ((Pet)role).getOwner();
		}
		if(human != null){
//			human.reCalcBuffValues();
		}
	}
	
	/**
	 * 重新计算角色技能等级
	 * @param role
	 */
	private void initLevel(Role role){
		role.setSkillLevel1();
		role.setSkillLevel2();
		role.setSkillLevel3();
		role.snapChangedProperty(true);
	}

	/**
	 * 升级后重新发送阵法信息
	 * @param role
	 */
	private void updateBattleForm(Role role) {
		//角色升级后发送阵法变化，武将升级忽略
		if (role instanceof Human) {
			Human human = (Human)role;
			Globals.getFormService().getForm(human.getPlayer());
		}
	}
}
