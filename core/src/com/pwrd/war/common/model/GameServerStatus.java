package com.pwrd.war.common.model;

import net.sf.json.JSONObject;

import com.pwrd.war.common.constants.ServerTypes;
import com.pwrd.war.common.constants.SharedConstants;

/**
 * Game Server信息状态消息
 * 
 * 
 */
public class GameServerStatus extends ServerStatus {

	/** 当前在线人数 */
	private int onlinePlayerCount;
	
	/** 登陆墙是否开启 */
	private boolean loginWallEnabled;
	/** 是否开启防沉迷控制 */
	private boolean wallowControlled;

	public GameServerStatus() {
		this.serverType = ServerTypes.GAME;
	}

	public int getOnlinePlayerCount() {
		return onlinePlayerCount;
	}

	public void setOnlinePlayerCount(int count) {
		this.onlinePlayerCount = count;
	}
	
	public boolean getLoginWallEnabled() {
		return loginWallEnabled;
	}

	public void setLoginWallEnabled(boolean loginWallEnabled) {
		this.loginWallEnabled = loginWallEnabled;
	}
	
	public boolean isWallowControlled() {
		return wallowControlled;
	}
	
	public void setWallowControlled(boolean wallowControlled) {
		this.wallowControlled = wallowControlled;
	}


	/**
	 * 根据timestamp及在线人数返回服务器状态
	 * 
	 * @return
	 */
	public int getState() {
		if (System.currentTimeMillis() - timestamp > SharedConstants.MAX_GAMESERVER_REPORT_PERIOR * 1000 * 5) {
			// 如果上次更新时间超过汇报频率的5倍时间，认定其为维护状态，恢复状态数据为默认值，返回维护状态
			return SharedConstants.GS_STATUS_MAINTAIN;
		} else if (onlinePlayerCount >= SharedConstants.GS_STATUS_FULL_LIMIT) {
			// 如果在线人数超过"拥挤"定义的人数，返回拥挤状态
			return SharedConstants.GS_STATUS_FULL;
		} else {
			// 返回正常状态
			return SharedConstants.GS_STATUS_NORMAL;
		}
	}

	@Override
	public JSONObject getStatusJSON() {
		JSONObject _status = super.getStatusJSON();
		_status.put("state", getState());
		_status.put("onlinePlayerCount", getOnlinePlayerCount());
		_status.put("loginWallEnabled", getLoginWallEnabled());
		_status.put("wallowControlled", isWallowControlled());
		return _status;
	}

	@Override
	public String toString() {
		return this.getStatusJSON().toString();
	}
}
