package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.role.Role;

public class RoleLevelUpEvent implements IEvent<Role>{
	
	/** 角色 */
	private final Role human;
	
	/** 到达的级别 */
	private int level;
	
	public RoleLevelUpEvent(Role human,int level)
	{
		this.human = human;
		this.level = level;
	}
	

	@Override
	public Role getInfo() {
		return human;
	}
	

	public int getLevel() {
		return level;
	}

}
