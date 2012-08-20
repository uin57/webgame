package com.pwrd.war.gameserver.common.listener;

import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.PlayerOffLineEvent;

/**
 * 离线会收到该事件，可以进行一些数据操作，不能切换场景
 */
public class PlayerOffLineEventListener implements IEventListener<PlayerOffLineEvent> {

	@Override
	public void fireEvent(PlayerOffLineEvent event) { 
		try {
			//玩家离线
			Globals.getActivityService().unjoin(event.getInfo().getHuman(),
					null);
			Globals.getHumanService().getHumanXiulianService().onPlayerOffline(event.getInfo());
			Globals.getAgainstRepService().onPlayerOffline(event.getInfo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
