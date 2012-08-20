package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.tree.handler.TreeHandlerFactory;

/**
 * 给好友浇水
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGWaterFriend extends CGMessage{
	
	/** 好友名称 */
	private String friendName;
	/** 好友UUID */
	private String friendUUID;
	
	public CGWaterFriend (){
	}
	
	public CGWaterFriend (
			String friendName,
			String friendUUID ){
			this.friendName = friendName;
			this.friendUUID = friendUUID;
	}
	
	@Override
	protected boolean readImpl() {
		friendName = readString();
		friendUUID = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(friendName);
		writeString(friendUUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_WATER_FRIEND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_WATER_FRIEND";
	}

	public String getFriendName(){
		return friendName;
	}
		
	public void setFriendName(String friendName){
		this.friendName = friendName;
	}

	public String getFriendUUID(){
		return friendUUID;
	}
		
	public void setFriendUUID(String friendUUID){
		this.friendUUID = friendUUID;
	}

	@Override
	public void execute() {
		TreeHandlerFactory.getHandler().handleWaterFriend(this.getSession().getPlayer(), this);
	}
}