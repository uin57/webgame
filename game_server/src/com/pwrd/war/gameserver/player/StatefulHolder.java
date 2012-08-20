package com.pwrd.war.gameserver.player;

/**
 * 状态保存接口, 用于临时在服务端保存玩家的操作, 由玩家的后续操作触发
 * {@link IStatefulHandler#exec(com.mop.lzr.gameserver.character.model.Player, String)}
 * 执行 执行完成后由{@link #clear()}清除状态
 * 
 * @author haijiang.jin
 * 
 */
public class StatefulHolder {
	/** 状态标识, 惟一标识一个新的状态 */
	private String tag;
	/** 处理具体的选择实现 */
	private IStatefulHandler handler;

	/**
	 * 清除状态
	 * 
	 */
	public void clear() {
		this.tag = null;
		this.handler = null;
	}

	/**
	 * 获取状态标识
	 * 
	 * @return
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * 设置状态标识
	 * 
	 * @param tag
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 获取状态处理器
	 * 
	 * @return
	 */
	public IStatefulHandler getHandler() {
		return handler;
	}

	/**
	 * 设置状态处理器
	 * 
	 * @param handler
	 */
	public void setHandler(IStatefulHandler handler) {
		this.handler = handler;
	}
}
