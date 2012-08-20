package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 玩家进入场景的信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSceneInfo extends GCMessage{
	

	public GCSceneInfo (){
	}
	

	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SCENE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SCENE_INFO";
	}
}