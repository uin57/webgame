package com.pwrd.war.gameserver.player;

/**
 * 状态处理器
 * 
 * @author haijiang.jin
 * 
 */
public interface IStatefulHandler {
	/**
	 * 执行玩家的操作
	 * 
	 * @param player
	 * @param value
	 */
	public void exec(Player player, String value);
}
