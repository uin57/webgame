package com.pwrd.war.gameserver.common.listener;

import com.pwrd.war.gameserver.human.Human;

public interface LevyListener {
	

	/**
	 * 完成征收
	 * @param human
	 */
	void onLevy(Human human);
	
	

}
