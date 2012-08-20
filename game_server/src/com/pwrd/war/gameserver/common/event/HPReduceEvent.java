package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.role.Role;

/**
 * 血量减少
 * @author xf
 */
public class HPReduceEvent implements IEvent<Role> {

	private Role role; 
	public HPReduceEvent(Role role ){ 
		this.role = role;
	}
	@Override
	public Role getInfo() { 
		return role;
	}  

}
