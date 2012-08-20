package com.pwrd.war.gameserver.story.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.story.handler.StoryHandlerFactory;

/**
 * 请求剧情列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGStoryList extends CGMessage{
	
	
	public CGStoryList (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_STORY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_STORY_LIST";
	}

	@Override
	public void execute() {
		StoryHandlerFactory.getHandler().handleStoryList(this.getSession().getPlayer(), this);
	}
}