package com.pwrd.war.gameserver.chat.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 客户端自定义聊天频道列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCChatChannelList extends GCMessage{
	
	/** 自定义频道列表  */
	private com.pwrd.war.gameserver.chat.ChatChannel[] chatChannels;

	public GCChatChannelList (){
	}
	
	public GCChatChannelList (
			com.pwrd.war.gameserver.chat.ChatChannel[] chatChannels ){
			this.chatChannels = chatChannels;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		chatChannels = new com.pwrd.war.gameserver.chat.ChatChannel[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.chat.ChatChannel obj = new com.pwrd.war.gameserver.chat.ChatChannel();
			obj.setName(readString());
			obj.setScope(readInteger());
			chatChannels[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(chatChannels.length);
		for(int i=0; i<chatChannels.length; i++){
			writeString(chatChannels[i].getName());
			writeInteger(chatChannels[i].getScope());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHAT_CHANNEL_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHAT_CHANNEL_LIST";
	}

	public com.pwrd.war.gameserver.chat.ChatChannel[] getChatChannels(){
		return chatChannels;
	}

	public void setChatChannels(com.pwrd.war.gameserver.chat.ChatChannel[] chatChannels){
		this.chatChannels = chatChannels;
	}	
}