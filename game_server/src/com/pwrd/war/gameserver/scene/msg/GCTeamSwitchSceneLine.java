package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 队员开始切换场景，有分线
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTeamSwitchSceneLine extends GCMessage{
	
	/** 当前场景 Id */
	private String sceneId;
	/** 分线号 */
	private int lineNo;

	public GCTeamSwitchSceneLine (){
	}
	
	public GCTeamSwitchSceneLine (
			String sceneId,
			int lineNo ){
			this.sceneId = sceneId;
			this.lineNo = lineNo;
	}

	@Override
	protected boolean readImpl() {
		sceneId = readString();
		lineNo = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sceneId);
		writeInteger(lineNo);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TEAM_SWITCH_SCENE_LINE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TEAM_SWITCH_SCENE_LINE";
	}

	public String getSceneId(){
		return sceneId;
	}
		
	public void setSceneId(String sceneId){
		this.sceneId = sceneId;
	}

	public int getLineNo(){
		return lineNo;
	}
		
	public void setLineNo(int lineNo){
		this.lineNo = lineNo;
	}
}