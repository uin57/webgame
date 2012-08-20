package com.pwrd.war.gameserver.buff.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 没气血包时,提示购买气血包的消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCNotHpBagMessage extends GCMessage{
	
	/** 使用血气包是否成功，不成功一般是元宝不够 */
	private boolean result;

	public GCNotHpBagMessage (){
	}
	
	public GCNotHpBagMessage (
			boolean result ){
			this.result = result;
	}

	@Override
	protected boolean readImpl() {
		result = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(result);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Not_Hp_Bag_Message;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Not_Hp_Bag_Message";
	}

	public boolean getResult(){
		return result;
	}
		
	public void setResult(boolean result){
		this.result = result;
	}
}