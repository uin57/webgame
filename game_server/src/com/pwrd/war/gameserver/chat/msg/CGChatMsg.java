package com.pwrd.war.gameserver.chat.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.chat.handler.ChatHandlerFactory;

/**
 * 客户端向服务器端发送的聊天消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGChatMsg extends CGMessage{
	
	/** 聊天范围 */
	private int scope;
	/** 聊天消息接收的玩家 */
	private String destRoleName;
	/** 消息接收玩家的UUID */
	private String destRoleUUID;
	/** 聊天消息 */
	private String content;
	/** 是否阻止向接收者发送消息，如在玩家黑名单中，true为不发 */
	private boolean block;
	/** 聊天文字颜色 */
	private int color;
	
	public CGChatMsg (){
	}
	
	public CGChatMsg (
			int scope,
			String destRoleName,
			String destRoleUUID,
			String content,
			boolean block,
			int color ){
			this.scope = scope;
			this.destRoleName = destRoleName;
			this.destRoleUUID = destRoleUUID;
			this.content = content;
			this.block = block;
			this.color = color;
	}
	
	@Override
	protected boolean readImpl() {
		scope = readInteger();
		destRoleName = readString();
		destRoleUUID = readString();
		content = readString();
		block = readBoolean();
		color = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(scope);
		writeString(destRoleName);
		writeString(destRoleUUID);
		writeString(content);
		writeBoolean(block);
		writeInteger(color);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CHAT_MSG;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CHAT_MSG";
	}

	public int getScope(){
		return scope;
	}
		
	public void setScope(int scope){
		this.scope = scope;
	}

	public String getDestRoleName(){
		return destRoleName;
	}
		
	public void setDestRoleName(String destRoleName){
		this.destRoleName = destRoleName;
	}

	public String getDestRoleUUID(){
		return destRoleUUID;
	}
		
	public void setDestRoleUUID(String destRoleUUID){
		this.destRoleUUID = destRoleUUID;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public boolean getBlock(){
		return block;
	}
		
	public void setBlock(boolean block){
		this.block = block;
	}

	public int getColor(){
		return color;
	}
		
	public void setColor(int color){
		this.color = color;
	}

	@Override
	public void execute() {
		ChatHandlerFactory.getHandler().handleChatMsg(this.getSession().getPlayer(), this);
	}
}