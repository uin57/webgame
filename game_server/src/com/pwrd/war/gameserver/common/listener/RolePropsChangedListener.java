package com.pwrd.war.gameserver.common.listener;

import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.gameserver.common.event.RolePropsChangedEvent;

public class RolePropsChangedListener implements IEventListener<RolePropsChangedEvent> {

	@Override
	public void fireEvent(RolePropsChangedEvent event) { 
		event.getRole().calcProps();
	}

}
