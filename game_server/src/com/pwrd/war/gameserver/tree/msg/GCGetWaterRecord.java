package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 获取浇水记录
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGetWaterRecord extends GCMessage{
	
	/** 好友信息列表 */
	private com.pwrd.war.gameserver.tree.HumanTreeWaterInfo[] humanTreeWaterInfo;

	public GCGetWaterRecord (){
	}
	
	public GCGetWaterRecord (
			com.pwrd.war.gameserver.tree.HumanTreeWaterInfo[] humanTreeWaterInfo ){
			this.humanTreeWaterInfo = humanTreeWaterInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		humanTreeWaterInfo = new com.pwrd.war.gameserver.tree.HumanTreeWaterInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.tree.HumanTreeWaterInfo obj = new com.pwrd.war.gameserver.tree.HumanTreeWaterInfo();
			obj.setCharId(readString());
			obj.setCharName(readString());
			obj.setWaterTime(readLong());
			humanTreeWaterInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(humanTreeWaterInfo.length);
		for(int i=0; i<humanTreeWaterInfo.length; i++){
			writeString(humanTreeWaterInfo[i].getCharId());
			writeString(humanTreeWaterInfo[i].getCharName());
			writeLong(humanTreeWaterInfo[i].getWaterTime());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_WATER_RECORD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_WATER_RECORD";
	}

	public com.pwrd.war.gameserver.tree.HumanTreeWaterInfo[] getHumanTreeWaterInfo(){
		return humanTreeWaterInfo;
	}

	public void setHumanTreeWaterInfo(com.pwrd.war.gameserver.tree.HumanTreeWaterInfo[] humanTreeWaterInfo){
		this.humanTreeWaterInfo = humanTreeWaterInfo;
	}	
}