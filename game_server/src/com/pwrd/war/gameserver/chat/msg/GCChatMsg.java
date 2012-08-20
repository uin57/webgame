package com.pwrd.war.gameserver.chat.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 服务器向客户端发送过滤后的聊天信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCChatMsg extends GCMessage{
	
	/** 聊天范围 */
	private int scope;
	/** 聊天消息发送者角色UUID */
	private String fromRoleUUID;
	/** 聊天消息发送者角色名称 */
	private String fromRoleName;
	/** 聊天消息发送者阵营 */
	private int fromRoleCamp;
	/** 聊天消息发送者性别 */
	private int fromRoleSex;
	/** 经过过滤后的聊天消息 */
	private String content;
	/** 聊天消息接受者角色UUID */
	private String toRoleUUID;
	/** 聊天消息接受者角色名称 */
	private String toRoleName;
	/** 聊天文字颜色 */
	private int color;

	public GCChatMsg (){
	}
	
	public GCChatMsg (
			int scope,
			String fromRoleUUID,
			String fromRoleName,
			int fromRoleCamp,
			int fromRoleSex,
			String content,
			String toRoleUUID,
			String toRoleName,
			int color ){
			this.scope = scope;
			this.fromRoleUUID = fromRoleUUID;
			this.fromRoleName = fromRoleName;
			this.fromRoleCamp = fromRoleCamp;
			this.fromRoleSex = fromRoleSex;
			this.content = content;
			this.toRoleUUID = toRoleUUID;
			this.toRoleName = toRoleName;
			this.color = color;
	}

	@Override
	protected boolean readImpl() {
		scope = readInteger();
		fromRoleUUID = readString();
		fromRoleName = readString();
		fromRoleCamp = readInteger();
		fromRoleSex = readInteger();
		content = readString();
		toRoleUUID = readString();
		toRoleName = readString();
		color = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(scope);
		writeString(fromRoleUUID);
		writeString(fromRoleName);
		writeInteger(fromRoleCamp);
		writeInteger(fromRoleSex);
		writeString(content);
		writeString(toRoleUUID);
		writeString(toRoleName);
		writeInteger(color);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHAT_MSG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHAT_MSG";
	}

	public int getScope(){
		return scope;
	}
		
	public void setScope(int scope){
		this.scope = scope;
	}

	public String getFromRoleUUID(){
		return fromRoleUUID;
	}
		
	public void setFromRoleUUID(String fromRoleUUID){
		this.fromRoleUUID = fromRoleUUID;
	}

	public String getFromRoleName(){
		return fromRoleName;
	}
		
	public void setFromRoleName(String fromRoleName){
		this.fromRoleName = fromRoleName;
	}

	public int getFromRoleCamp(){
		return fromRoleCamp;
	}
		
	public void setFromRoleCamp(int fromRoleCamp){
		this.fromRoleCamp = fromRoleCamp;
	}

	public int getFromRoleSex(){
		return fromRoleSex;
	}
		
	public void setFromRoleSex(int fromRoleSex){
		this.fromRoleSex = fromRoleSex;
	}

	public String getContent(){
		return content;
	}
		
	public void setContent(String content){
		this.content = content;
	}

	public String getToRoleUUID(){
		return toRoleUUID;
	}
		
	public void setToRoleUUID(String toRoleUUID){
		this.toRoleUUID = toRoleUUID;
	}

	public String getToRoleName(){
		return toRoleName;
	}
		
	public void setToRoleName(String toRoleName){
		this.toRoleName = toRoleName;
	}

	public int getColor(){
		return color;
	}
		
	public void setColor(int color){
		this.color = color;
	}
}