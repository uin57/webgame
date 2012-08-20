package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 服务器响应的物品信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCResItemInfo extends GCMessage{
	
	/** 单个道具信息 */
	private com.pwrd.war.common.model.item.CommonItem item;

	public GCResItemInfo (){
	}
	
	public GCResItemInfo (
			com.pwrd.war.common.model.item.CommonItem item ){
			this.item = item;
	}

	@Override
	protected boolean readImpl() {
		item = new com.pwrd.war.common.model.item.CommonItem();
					item.setUuid(readString());
							item.setItemSn(readString());
							item.setBagId(readInteger());
							item.setIndex(readInteger());
							item.setBind(readInteger());
							item.setWearerUuid(readString());
							item.setCreateTime(readInteger());
							item.setNum(readInteger());
							item.setIdentity(readInteger());
							item.setFeature(readString());
							item.setBattleProps(readString());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(item.getUuid());
		writeString(item.getItemSn());
		writeInteger(item.getBagId());
		writeInteger(item.getIndex());
		writeInteger(item.getBind());
		writeString(item.getWearerUuid());
		writeInteger(item.getCreateTime());
		writeInteger(item.getNum());
		writeInteger(item.getIdentity());
		writeString(item.getFeature());
		writeString(item.getBattleProps());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RES_ITEM_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RES_ITEM_INFO";
	}

	public com.pwrd.war.common.model.item.CommonItem getItem(){
		return item;
	}
		
	public void setItem(com.pwrd.war.common.model.item.CommonItem item){
		this.item = item;
	}
}