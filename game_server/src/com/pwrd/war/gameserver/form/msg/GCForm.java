package com.pwrd.war.gameserver.form.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 阵型初始化消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCForm extends GCMessage{
	
	/** 阵型位置 */
	private com.pwrd.war.gameserver.form.FormPosition[] formPositions;
	/** 最大可上阵人数 */
	private int maxPos;

	public GCForm (){
	}
	
	public GCForm (
			com.pwrd.war.gameserver.form.FormPosition[] formPositions,
			int maxPos ){
			this.formPositions = formPositions;
			this.maxPos = maxPos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		formPositions = new com.pwrd.war.gameserver.form.FormPosition[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.form.FormPosition obj = new com.pwrd.war.gameserver.form.FormPosition();
			obj.setPetSn(readString());
			obj.setPosition(readInteger());
			formPositions[i] = obj;
		}
		maxPos = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(formPositions.length);
		for(int i=0; i<formPositions.length; i++){
			writeString(formPositions[i].getPetSn());
			writeInteger(formPositions[i].getPosition());
		}
		writeInteger(maxPos);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Form;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Form";
	}

	public com.pwrd.war.gameserver.form.FormPosition[] getFormPositions(){
		return formPositions;
	}

	public void setFormPositions(com.pwrd.war.gameserver.form.FormPosition[] formPositions){
		this.formPositions = formPositions;
	}	

	public int getMaxPos(){
		return maxPos;
	}
		
	public void setMaxPos(int maxPos){
		this.maxPos = maxPos;
	}
}