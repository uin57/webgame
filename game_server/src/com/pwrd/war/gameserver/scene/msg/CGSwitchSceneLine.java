package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;

/**
 * 切换场景，有分线
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSwitchSceneLine extends CGMessage{
	
	/** 当前场景 Id */
	private String sceneId;
	/** 分线号 */
	private int lineNo;
	
	public CGSwitchSceneLine (){
	}
	
	public CGSwitchSceneLine (
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
		return MessageType.CG_SWITCH_SCENE_LINE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SWITCH_SCENE_LINE";
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

	@Override
	public void execute() {
		SceneHandlerFactory.getHandler().handleSwitchSceneLine(this.getSession().getPlayer(), this);
	}
}