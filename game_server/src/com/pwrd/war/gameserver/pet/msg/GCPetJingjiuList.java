package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 敬酒信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPetJingjiuList extends GCMessage{
	
	/** 敬酒信息列表 */
	private com.pwrd.war.common.model.pet.JingjiuInfo[] jingjiuInfo;

	public GCPetJingjiuList (){
	}
	
	public GCPetJingjiuList (
			com.pwrd.war.common.model.pet.JingjiuInfo[] jingjiuInfo ){
			this.jingjiuInfo = jingjiuInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		jingjiuInfo = new com.pwrd.war.common.model.pet.JingjiuInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.common.model.pet.JingjiuInfo obj = new com.pwrd.war.common.model.pet.JingjiuInfo();
			obj.setId(readInteger());
			obj.setName(readString());
			obj.setVipLevel(readInteger());
			obj.setCoin(readInteger());
			obj.setGold(readInteger());
			obj.setPopularity(readInteger());
			obj.setTimes(readInteger());
			jingjiuInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(jingjiuInfo.length);
		for(int i=0; i<jingjiuInfo.length; i++){
			writeInteger(jingjiuInfo[i].getId());
			writeString(jingjiuInfo[i].getName());
			writeInteger(jingjiuInfo[i].getVipLevel());
			writeInteger(jingjiuInfo[i].getCoin());
			writeInteger(jingjiuInfo[i].getGold());
			writeInteger(jingjiuInfo[i].getPopularity());
			writeInteger(jingjiuInfo[i].getTimes());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PET_JINGJIU_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PET_JINGJIU_LIST";
	}

	public com.pwrd.war.common.model.pet.JingjiuInfo[] getJingjiuInfo(){
		return jingjiuInfo;
	}

	public void setJingjiuInfo(com.pwrd.war.common.model.pet.JingjiuInfo[] jingjiuInfo){
		this.jingjiuInfo = jingjiuInfo;
	}	
}