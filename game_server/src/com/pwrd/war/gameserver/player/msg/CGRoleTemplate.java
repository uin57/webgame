package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 向服务器端请求角色模板数据
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRoleTemplate extends CGMessage{
	
	
	public CGRoleTemplate (){
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
		return MessageType.CG_ROLE_TEMPLATE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROLE_TEMPLATE";
	}

	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handleRoleTemplate(this.getSession().getPlayer(), this);
	}
}