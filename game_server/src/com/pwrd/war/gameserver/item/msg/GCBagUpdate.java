package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 刷新整个背包
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBagUpdate extends GCMessage{
	
	/** 所属者uuid */
	private String uuid;
	/** 佩戴者uuid */
	private String wearerId;
	/** 背包ID */
	private short bagId;
	/** 背包的可用位置数量 */
	private short capacity;
	/** 单个道具信息 */
	private com.pwrd.war.common.model.item.CommonItem[] item;

	public GCBagUpdate (){
	}
	
	public GCBagUpdate (
			String uuid,
			String wearerId,
			short bagId,
			short capacity,
			com.pwrd.war.common.model.item.CommonItem[] item ){
			this.uuid = uuid;
			this.wearerId = wearerId;
			this.bagId = bagId;
			this.capacity = capacity;
			this.item = item;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		uuid = readString();
		wearerId = readString();
		bagId = readShort();
		capacity = readShort();
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
		writeString(uuid);
		writeString(wearerId);
		writeShort(bagId);
		writeShort(capacity);
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
		return MessageType.GC_BAG_UPDATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAG_UPDATE";
	}

	public String getUuid(){
		return uuid;
	}
		
	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	public String getWearerId(){
		return wearerId;
	}
		
	public void setWearerId(String wearerId){
		this.wearerId = wearerId;
	}

	public short getBagId(){
		return bagId;
	}
		
	public void setBagId(short bagId){
		this.bagId = bagId;
	}

	public short getCapacity(){
		return capacity;
	}
		
	public void setCapacity(short capacity){
		this.capacity = capacity;
	}

	public com.pwrd.war.common.model.item.CommonItem[] getItem(){
		return item;
	}

	public void setItem(com.pwrd.war.common.model.item.CommonItem[] item){
		this.item = item;
	}	
}