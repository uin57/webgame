package com.pwrd.war.gameserver.common.listener;

import com.pwrd.war.gameserver.human.Human;

public interface EquipEnhanceListener {
	
	/**
	 * 装备强化成功
	 * @param human
	 */
	void onEnhanceEquipSuccess(Human human);
	
	/**
	 * 装备强化成功的详细接口
	 * @param human
	 * @param equipTmplId 强化装备的模版id
	 * @param enhanceLevel 强化后的级别
	 */
	void onEnhanceEquipSuccess(Human human,int equipTmplId,int enhanceLevel);

}
