package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 向服务器请求其他玩家角色信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGQueryOtherRoleInfo extends CGMessage{
	
	/** 请求的角色UUID */
	private String UUID;
	
	public CGQueryOtherRoleInfo (){
	}
	
	public CGQueryOtherRoleInfo (
			String UUID ){
			this.UUID = UUID;
	}
	
	@Override
	protected boolean readImpl() {
		UUID = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(UUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_QUERY_OTHER_ROLE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_QUERY_OTHER_ROLE_INFO";
	}

	public String getUUID(){
		return UUID;
	}
		
	public void setUUID(String UUID){
		this.UUID = UUID;
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleQueryOtherRoleInfo(this.getSession().getPlayer(), this);
	}
}