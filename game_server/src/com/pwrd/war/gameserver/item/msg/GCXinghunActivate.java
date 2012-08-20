package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 激活结果
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCXinghunActivate extends GCMessage{
	
	/** 结果 */
	private boolean result;
	/** 结果描述 */
	private String desc;

	public GCXinghunActivate (){
	}
	
	public GCXinghunActivate (
			boolean result,
			String desc ){
			this.result = result;
			this.desc = desc;
	}

	@Override
	protected boolean readImpl() {
		result = readBoolean();
		desc = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(result);
		writeString(desc);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_XINGHUN_ACTIVATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_XINGHUN_ACTIVATE";
	}

	public boolean getResult(){
		return result;
	}
		
	public void setResult(boolean result){
		this.result = result;
	}

	public String getDesc(){
		return desc;
	}
		
	public void setDesc(String desc){
		this.desc = desc;
	}
}