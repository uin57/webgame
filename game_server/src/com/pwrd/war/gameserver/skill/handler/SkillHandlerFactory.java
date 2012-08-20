package com.pwrd.war.gameserver.skill.handler;

/**
 * 
 * 物品使用处理器工厂类
 * 
 * @author Yvon
 * @since 2010-3-26
 */
public class SkillHandlerFactory {

	private static final SkillMessageHandler handler = new SkillMessageHandler();

	public static SkillMessageHandler getHandler() {
		return handler;
	}

}
