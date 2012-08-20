package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回摇钱信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTreeShake extends GCMessage{
	
	/** 返回结果 */
	private boolean result;
	/** 返回结果描述 */
	private String desc;

	public GCTreeShake (){
	}
	
	public GCTreeShake (
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
		return MessageType.GC_TREE_SHAKE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TREE_SHAKE";
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