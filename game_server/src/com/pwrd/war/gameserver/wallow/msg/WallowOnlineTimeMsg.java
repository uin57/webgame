package com.pwrd.war.gameserver.wallow.msg;

import java.util.List;

import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.gameserver.common.Globals;

/**
 * local返回的防沉迷玩家列表在线累计时长
 * 
 * @author wenpin.qian
 * @author lixiao.fan
 */
public class WallowOnlineTimeMsg extends SysInternalMessage {
	/** 玩家列表 */
	private List<String> players;
	/** 玩家在线时长列表 */
	private List<Long> seconds;

	public void setSeconds(List<Long> seconds) {
		this.seconds = seconds;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public List<Long> getSeconds() {
		return seconds;
	}

	@Override
	public void execute() {
		Globals.getWallowService().onPlayerOnlineTimeSynced(this);
	}

	@Override
	public String getTypeName() {
		return "WallowOnlineTimeMsg";
	}

}
