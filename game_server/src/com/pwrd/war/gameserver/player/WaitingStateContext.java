package com.pwrd.war.gameserver.player;

/**
 * 等待状态上下文
 * 
 * @author zhengyisi
 * @since 2010-6-21
 */
public class WaitingStateContext implements PlayerStateContext {
	/** 等待类型 */
	private static int TYPE_BASE = 0;
	/** 进入副本等待 */
	public static final int TYPE_RAID_ENTRY = ++TYPE_BASE;
	/** 退出等待 */
	public static final int TYPE_LOGOUT = ++TYPE_BASE;

	/** 等待类型 */
	private int type;
	/** 等待面板标题 */
	private String title;
	/** 等待面板提示 */
	private String tip;
	/** */
	private PlayerStateExitCallback exitCallback;
	
	public WaitingStateContext(int type,String title,String tip){
		this.type = type;
		this.title = title;
		this.tip = tip;
	}

	int getType() {
		return type;
	}

	void setType(int type) {
		this.type = type;
	}

	String getTitle() {
		return title;
	}

	void setTitle(String title) {
		this.title = title;
	}

	String getTip() {
		return tip;
	}

	void setTip(String tip) {
		this.tip = tip;
	}

	public void setExitCallback(PlayerStateExitCallback exitCallback) {
		this.exitCallback = exitCallback;
	}

	public PlayerStateExitCallback getExitCallback() {
		return exitCallback;
	}

}
