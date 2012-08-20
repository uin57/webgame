package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 更新单个道具信息，客户端受到此消息就替换原来此位置的道具
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCItemUpdate extends GCMessage{
	
	/** 道具信息数组 */
	private com.pwrd.war.common.model.item.CommonItem[] item;

	public GCItemUpdate (){
	}
	
	public GCItemUpdate (
			com.pwrd.war.common.model.item.CommonItem[] item ){
			this.item = item;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		item = new com.pwrd.war.common.model.item.CommonItem[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.common.model.item.CommonItem obj = new com.pwrd.war.common.model.item.CommonItem();
			obj.setUuid(readString());
			obj.setItemSn(readString());
			obj.setBagId(readInteger());
			obj.setIndex(readInteger());
			obj.setBind(readInteger());
			obj.setWearerUuid(readString());
			obj.setCreateTime(readInteger());
			obj.setNum(readInteger());
			obj.setIdentity(readInteger());
			obj.setFeature(readString());
			obj.setBattleProps(readString());
			item[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(item.length);
		for(int i=0; i<item.length; i++){
			writeString(item[i].getUuid());
			writeString(item[i].getItemSn());
			writeInteger(item[i].getBagId());
			writeInteger(item[i].getIndex());
			writeInteger(item[i].getBind());
			writeString(item[i].getWearerUuid());
			writeInteger(item[i].getCreateTime());
			writeInteger(item[i].getNum());
			writeInteger(item[i].getIdentity());
			writeString(item[i].getFeature());
			writeString(item[i].getBattleProps());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ITEM_UPDATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ITEM_UPDATE";
	}

	public com.pwrd.war.common.model.item.CommonItem[] getItem(){
		return item;
	}

	public void setItem(com.pwrd.war.common.model.item.CommonItem[] item){
		this.item = item;
	}	
}