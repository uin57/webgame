package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 修改个性签名
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGModifyPersonsign extends CGMessage{
	
	/** 个性签名 */
	private String sign;
	
	public CGModifyPersonsign (){
	}
	
	public CGModifyPersonsign (
			String sign ){
			this.sign = sign;
	}
	
	@Override
	protected boolean readImpl() {
		sign = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sign);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_MODIFY_PERSONSIGN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MODIFY_PERSONSIGN";
	}

	public String getSign(){
		return sign;
	}
		
	public void setSign(String sign){
		this.sign = sign;
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleModifyPersonsign(this.getSession().getPlayer(), this);
	}
}