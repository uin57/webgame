package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.tree.handler.TreeHandlerFactory;

/**
 * 浇水和果实信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFriendWaterInfo extends CGMessage{
	
	/** 好友uuid */
	private String friendUuid;
	/** 好友名称 */
	private String friendName;
	
	public CGFriendWaterInfo (){
	}
	
	public CGFriendWaterInfo (
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
		return MessageType.CG_FRIEND_WATER_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FRIEND_WATER_INFO";
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
		TreeHandlerFactory.getHandler().handleFriendWaterInfo(this.getSession().getPlayer(), this);
	}
}