package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;

/**
 * 切换场景，自动分线
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSwitchScene extends CGMessage{
	
	/** 当前切换点 Id */
	private String sceneId;
	
	public CGSwitchScene (){
	}
	
	public CGSwitchScene (
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
		return MessageType.CG_SWITCH_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SWITCH_SCENE";
	}

	public String getSceneId(){
		return sceneId;
	}
		
	public void setSceneId(String sceneId){
		this.sceneId = sceneId;
	}

	@Override
	public void execute() {
		SceneHandlerFactory.getHandler().handleSwitchScene(this.getSession().getPlayer(), this);
	}
}