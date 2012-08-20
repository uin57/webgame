package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 取消订阅某个频道，这样就可以获取该频道的所有消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGUnjoinChannel extends CGMessage{
	
	/** 类型(EQUIP_EHANCE，强化装备页面) */
	private String channel;
	
	public CGUnjoinChannel (){
	}
	
	public CGUnjoinChannel (
			String channel ){
			this.channel = channel;
	}
	
	@Override
	protected boolean readImpl() {
		channel = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(channel);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_UNJOIN_CHANNEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UNJOIN_CHANNEL";
	}

	public String getChannel(){
		return channel;
	}
		
	public void setChannel(String channel){
		this.channel = channel;
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleUnjoinChannel(this.getSession().getPlayer(), this);
	}
}