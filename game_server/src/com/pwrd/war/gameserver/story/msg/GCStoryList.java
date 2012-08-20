package com.pwrd.war.gameserver.story.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回剧情列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStoryList extends GCMessage{
	
	/** 可播放剧情id列表 */
	private String[] canShowList;

	public GCStoryList (){
	}
	
	public GCStoryList (
			String[] canShowList ){
			this.canShowList = canShowList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		canShowList = new String[count];
		for(int i=0; i<count; i++){
			canShowList[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(canShowList.length);
		for(int i=0; i<canShowList.length; i++){
			writeString(canShowList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STORY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STORY_LIST";
	}

	public String[] getCanShowList(){
		return canShowList;
	}

	public void setCanShowList(String[] canShowList){
		this.canShowList = canShowList;
	}	
}