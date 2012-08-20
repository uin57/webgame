package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;

/**
 * 取得该场景有多少条线
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSceneLine extends CGMessage{
	
	/** 场景 Id */
	private String sceneId;
	
	public CGSceneLine (){
	}
	
	public CGSceneLine (
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
		return MessageType.CG_SCENE_LINE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SCENE_LINE";
	}

	public String getSceneId(){
		return sceneId;
	}
		
	public void setSceneId(String sceneId){
		this.sceneId = sceneId;
	}

	@Override
	public void execute() {
		SceneHandlerFactory.getHandler().handleSceneLine(this.getSession().getPlayer(), this);
	}
}