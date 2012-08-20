package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.role.Role;

public class RolePropsChangedEvent implements IEvent<Role> {
	private Role role;
	
	public RolePropsChangedEvent(Role role){
		this.role = role;
	}
	
	@Override
	public Role getInfo() { 
		return role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
