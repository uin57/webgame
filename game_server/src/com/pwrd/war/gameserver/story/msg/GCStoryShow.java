package com.pwrd.war.gameserver.story.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 播放剧情
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStoryShow extends GCMessage{
	
	/** 剧情Id */
	private String storyId;

	public GCStoryShow (){
	}
	
	public GCStoryShow (
			String storyId ){
			this.storyId = storyId;
	}

	@Override
	protected boolean readImpl() {
		storyId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(storyId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STORY_SHOW;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STORY_SHOW";
	}

	public String getStoryId(){
		return storyId;
	}
		
	public void setStoryId(String storyId){
		this.storyId = storyId;
	}
}