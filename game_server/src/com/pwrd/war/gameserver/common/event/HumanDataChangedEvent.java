package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.core.object.LifeCycle;

/**
 * 玩家的数据变更事件
 * 
 * 
 */
public class HumanDataChangedEvent implements IEvent<LifeCycle> {
	private final LifeCycle charObj;

	public HumanDataChangedEvent(LifeCycle charObj) {
		this.charObj = charObj;
	}

	@Override
	public LifeCycle getInfo() {
		return charObj;
	}
}