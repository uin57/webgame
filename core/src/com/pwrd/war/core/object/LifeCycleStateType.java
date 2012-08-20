package com.pwrd.war.core.object;

/**
 * 对象实例的生命状态
 * 
 */
public enum LifeCycleStateType {
	/** 活动状态,如果一个对象已经进入到了游戏世界中,必须处于该状态 */
	ACTIVE,
	/** 非活动状态,如果一个对象还不存在于游戏世界中,即还未参与到游戏的逻辑中,必须处于该状态 */
	DEACTIVED,
	/** 已销毁,如果一个对象将要从游戏世界中销毁,必须处于该状态 */
	DESTROYED
}