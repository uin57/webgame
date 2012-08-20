package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 已完成的新手引导
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPlayerGuideList extends GCMessage{
	
	/**  */
	private String guideIds;

	public GCPlayerGuideList (){
	}
	
	public GCPlayerGuideList (
			String guideIds ){
			this.guideIds = guideIds;
	}

	@Override
	protected boolean readImpl() {
		guideIds = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(guideIds);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PLAYER_GUIDE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PLAYER_GUIDE_LIST";
	}

	public String getGuideIds(){
		return guideIds;
	}
		
	public void setGuideIds(String guideIds){
		this.guideIds = guideIds;
	}
}