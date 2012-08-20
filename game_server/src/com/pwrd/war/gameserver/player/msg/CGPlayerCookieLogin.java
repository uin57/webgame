package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 用户cookie登录
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerCookieLogin extends CGMessage{
	
	/** cookie值 */
	private String cookieValue;
	
	public CGPlayerCookieLogin (){
	}
	
	public CGPlayerCookieLogin (
			String cookieValue ){
			this.cookieValue = cookieValue;
	}
	
	@Override
	protected boolean readImpl() {
		cookieValue = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(cookieValue);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PLAYER_COOKIE_LOGIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_COOKIE_LOGIN";
	}

	public String getCookieValue(){
		return cookieValue;
	}
		
	public void setCookieValue(String cookieValue){
		this.cookieValue = cookieValue;
	}

	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handlePlayerCookieLogin(this.getSession().getPlayer(), this);
	}
}