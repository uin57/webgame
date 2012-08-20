package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 告诉该场景有多少条线
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSceneLine extends GCMessage{
	
	/** 场景 Id */
	private String sceneId;
	/** 总共多少条分线，从1开始 */
	private int line;

	public GCSceneLine (){
	}
	
	public GCSceneLine (
			String sceneId,
			int line ){
			this.sceneId = sceneId;
			this.line = line;
	}

	@Override
	protected boolean readImpl() {
		sceneId = readString();
		line = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sceneId);
		writeInteger(line);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SCENE_LINE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SCENE_LINE";
	}

	public String getSceneId(){
		return sceneId;
	}
		
	public void setSceneId(String sceneId){
		this.sceneId = sceneId;
	}

	public int getLine(){
		return line;
	}
		
	public void setLine(int line){
		this.line = line;
	}
}