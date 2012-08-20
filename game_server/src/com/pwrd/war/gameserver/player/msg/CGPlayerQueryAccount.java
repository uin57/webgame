package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 查询账户余额
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerQueryAccount extends CGMessage{
	
	
	public CGPlayerQueryAccount (){
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
		return MessageType.CG_PLAYER_QUERY_ACCOUNT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_QUERY_ACCOUNT";
	}

	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handlePlayerQueryAccount(this.getSession().getPlayer(), this);
	}
}