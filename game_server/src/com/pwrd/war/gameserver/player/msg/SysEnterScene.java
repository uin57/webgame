package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;

public class SysEnterScene extends SysInternalMessage{
	
	private int playerId;
	
	private String sceneId;
	
	private int line;

	public SysEnterScene(int playerId, String sceneId, int line) {
		this.playerId = playerId;
		this.sceneId = sceneId;
		this.line = line;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	@Override
	public String getTypeName() {
		return "SysEnterSceneMsg";
	}

	@Override
	public void execute() {
		boolean result = SceneHandlerFactory.getHandler().handleEnterScene(playerId, sceneId, line);
		SysEnterSceneResult resultMsg = new SysEnterSceneResult(result, playerId);
		Globals.getMessageProcessor().put(resultMsg);
	}

	
}
