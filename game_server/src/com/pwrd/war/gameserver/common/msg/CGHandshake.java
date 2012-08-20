package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.BaseMinaMessage;
import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.MinaGameClientSession;

/**
 * 客户端用于时间同步的消息
 * 
 * 不是生成的
 */
public class CGHandshake extends BaseMinaMessage<MinaGameClientSession>{
	
	
	public CGHandshake (){
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
		return MessageType.CG_HANDSHAKE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HANDSHAKE";
	}

	@Override
	public void execute() {
		Player connectClient = new Player(this.getSession());
		connectClient.setClientIp(this.getSession().getIp());
		Globals.getOnlinePlayerService().putPlayer(this.getSession(), connectClient);
		connectClient.sendMessage(new GCHandshake());		
	}
}