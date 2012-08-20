package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 进入场景结果，队员和队长都是此消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSwitchScene extends GCMessage{
	
	/** 目标场景ID */
	private String sceneId;
	/** 目标场景切换点id */
	private String switchId;
	/** 分线号 */
	private int lineNo;

	public GCSwitchScene (){
	}
	
	public GCSwitchScene (
			String sceneId,
			String switchId,
			int lineNo ){
			this.sceneId = sceneId;
			this.switchId = switchId;
			this.lineNo = lineNo;
	}

	@Override
	protected boolean readImpl() {
		sceneId = readString();
		switchId = readString();
		lineNo = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sceneId);
		writeString(switchId);
		writeInteger(lineNo);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SWITCH_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SWITCH_SCENE";
	}

	public String getSceneId(){
		return sceneId;
	}
		
	public void setSceneId(String sceneId){
		this.sceneId = sceneId;
	}

	public String getSwitchId(){
		return switchId;
	}
		
	public void setSwitchId(String switchId){
		this.switchId = switchId;
	}

	public int getLineNo(){
		return lineNo;
	}
		
	public void setLineNo(int lineNo){
		this.lineNo = lineNo;
	}
}