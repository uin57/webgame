package com.pwrd.war.gameserver.form.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.form.handler.FormHandlerFactory;

/**
 * 客户端发送新的武将位置
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFormPosition extends CGMessage{
	
	/** 武将技序列 */
	private com.pwrd.war.gameserver.form.FormPosition[] petPositions;
	
	public CGFormPosition (){
	}
	
	public CGFormPosition (
			com.pwrd.war.gameserver.form.FormPosition[] petPositions ){
			this.petPositions = petPositions;
	}
	
	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
				petPositions = new com.pwrd.war.gameserver.form.FormPosition[count];
				for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.form.FormPosition obj  =new com.pwrd.war.gameserver.form.FormPosition();
			obj.setPetSn(readString());
			obj.setPosition(readInteger());
			petPositions[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(petPositions.length);
		for(int i=0; i<petPositions.length; i++){
			writeString(petPositions[i].getPetSn());
			writeInteger(petPositions[i].getPosition());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_Form_Position;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Form_Position";
	}

	public com.pwrd.war.gameserver.form.FormPosition[] getPetPositions(){
		return petPositions;
	}

	public void setPetPositions(com.pwrd.war.gameserver.form.FormPosition[] petPositions){
		this.petPositions = petPositions;
	}	

	@Override
	public void execute() {
		FormHandlerFactory.getHandler().handleFormPosition(this.getSession().getPlayer(), this);
	}
}