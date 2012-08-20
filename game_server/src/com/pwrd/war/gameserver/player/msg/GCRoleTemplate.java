package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 角色模板数据
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoleTemplate extends GCMessage{
	

	public GCRoleTemplate (){
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
		return MessageType.GC_ROLE_TEMPLATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROLE_TEMPLATE";
	}
}