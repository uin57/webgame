package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.role.Role;

public class FormChangeEvent implements IEvent<Role>{
	
	private Role role; 
	
	public FormChangeEvent(Role role ){ 
		this.role = role;
	}

	@Override
	public Role getInfo() {
		return role;
	}

}
