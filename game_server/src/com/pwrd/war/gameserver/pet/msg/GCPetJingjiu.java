package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 武将敬酒
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPetJingjiu extends GCMessage{
	
	/** 结果 */
	private boolean result;
	/** 原因 */
	private String desc;

	public GCPetJingjiu (){
	}
	
	public GCPetJingjiu (
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
		return MessageType.GC_PET_JINGJIU;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PET_JINGJIU";
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