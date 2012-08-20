package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 玩家已经进入场景
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCEnterScene extends GCMessage{
	

	public GCEnterScene (){
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
		return MessageType.GC_ENTER_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ENTER_SCENE";
	}
}