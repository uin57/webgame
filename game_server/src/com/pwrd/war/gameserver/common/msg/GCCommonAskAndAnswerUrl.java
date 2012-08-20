package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 在线答疑URL
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCCommonAskAndAnswerUrl extends GCMessage{
	
	/** URL值 */
	private String url;

	public GCCommonAskAndAnswerUrl (){
	}
	
	public GCCommonAskAndAnswerUrl (
			String url ){
			this.url = url;
	}

	@Override
	protected boolean readImpl() {
		url = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(url);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_COMMON_ASK_AND_ANSWER_URL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_COMMON_ASK_AND_ANSWER_URL";
	}

	public String getUrl(){
		return url;
	}
		
	public void setUrl(String url){
		this.url = url;
	}
}