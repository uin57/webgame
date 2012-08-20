package com.pwrd.war.gameserver.chat;

/**
 * 自定义聊天频道
 * 
 * 
 */
public class ChatChannel {
	/** 自定义标签的名称 */
	private String name;
	/** 接受的频道 */
	private int scope;

	public ChatChannel() {
	}

	public ChatChannel(String name, int scope) {
		this.name = name;
		this.scope = scope;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

}
