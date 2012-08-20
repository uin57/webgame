package com.pwrd.war.gameserver.wallow.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 打开防沉迷填写面板
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCWallowOpenPanel extends GCMessage{
	

	public GCWallowOpenPanel (){
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
		return MessageType.GC_WALLOW_OPEN_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_WALLOW_OPEN_PANEL";
	}
}