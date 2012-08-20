package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.tree.handler.TreeHandlerFactory;

/**
 * 获取浇水记录
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetFriendWaterRecord extends CGMessage{
	
	/** 好友uuid */
	private String friendUuid;
	/** 好友名称 */
	private String friendName;
	
	public CGGetFriendWaterRecord (){
	}
	
	public CGGetFriendWaterRecord (
			String friendUuid,
			String friendName ){
			this.friendUuid = friendUuid;
			this.friendName = friendName;
	}
	
	@Override
	protected boolean readImpl() {
		friendUuid = readString();
		friendName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(friendUuid);
		writeString(friendName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_FRIEND_WATER_RECORD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_FRIEND_WATER_RECORD";
	}

	public String getFriendUuid(){
		return friendUuid;
	}
		
	public void setFriendUuid(String friendUuid){
		this.friendUuid = friendUuid;
	}

	public String getFriendName(){
		return friendName;
	}
		
	public void setFriendName(String friendName){
		this.friendName = friendName;
	}

	@Override
	public void execute() {
		TreeHandlerFactory.getHandler().handleGetFriendWaterRecord(this.getSession().getPlayer(), this);
	}
}