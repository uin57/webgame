package com.pwrd.war.gameserver.wallow.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.wallow.handler.WallowHandlerFactory;

/**
 * 填写防沉迷认证信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGWallowAddInfo extends CGMessage{
	
	/** 真实姓名 */
	private String name;
	/** 身份证号 */
	private String idCard;
	
	public CGWallowAddInfo (){
	}
	
	public CGWallowAddInfo (
			String name,
			String idCard ){
			this.name = name;
			this.idCard = idCard;
	}
	
	@Override
	protected boolean readImpl() {
		name = readString();
		idCard = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(name);
		writeString(idCard);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_WALLOW_ADD_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_WALLOW_ADD_INFO";
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}

	public String getIdCard(){
		return idCard;
	}
		
	public void setIdCard(String idCard){
		this.idCard = idCard;
	}

	@Override
	public void execute() {
		WallowHandlerFactory.getHandler().handleWallowAddInfo(this.getSession().getPlayer(), this);
	}
}