package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 用户登录
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerLogin extends CGMessage{
	
	/** 玩家的账户 */
	private String account;
	/** 玩家的密码  */
	private String password;
	
	public CGPlayerLogin (){
	}
	
	public CGPlayerLogin (
			String account,
			String password ){
			this.account = account;
			this.password = password;
	}
	
	@Override
	protected boolean readImpl() {
		account = readString();
		password = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(account);
		writeString(password);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PLAYER_LOGIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_LOGIN";
	}

	public String getAccount(){
		return account;
	}
		
	public void setAccount(String account){
		this.account = account;
	}

	public String getPassword(){
		return password;
	}
		
	public void setPassword(String password){
		this.password = password;
	}

	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handlePlayerLogin(this.getSession().getPlayer(), this);
	}
}