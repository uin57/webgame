package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 切换场景，有分线
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSwitchSceneLine extends GCMessage{
	
	/** 当前场景 Id */
	private String sceneId;

	public GCSwitchSceneLine (){
	}
	
	public GCSwitchSceneLine (
			String sceneId ){
			this.sceneId = sceneId;
	}

	@Override
	protected boolean readImpl() {
		sceneId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sceneId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SWITCH_SCENE_LINE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SWITCH_SCENE_LINE";
	}

	public String getSceneId(){
		return sceneId;
	}
		
	public void setSceneId(String sceneId){
		this.sceneId = sceneId;
	}
}