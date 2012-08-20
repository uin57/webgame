package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回某装备的升级信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCEquipEnhanceInfo extends GCMessage{
	
	/** 某道具的升级信息 */
	private com.pwrd.war.common.model.item.EnhanceItem enhanceItem;

	public GCEquipEnhanceInfo (){
	}
	
	public GCEquipEnhanceInfo (
			com.pwrd.war.common.model.item.EnhanceItem enhanceItem ){
			this.enhanceItem = enhanceItem;
	}

	@Override
	protected boolean readImpl() {
		enhanceItem = new com.pwrd.war.common.model.item.EnhanceItem();
					enhanceItem.setUuid(readString());
							enhanceItem.setName(readString());
							enhanceItem.setRarity(readShort());
							enhanceItem.setLevel(readShort());
							enhanceItem.setEnhanceLevel(readShort());
							enhanceItem.setAfterEnhanceLevel(readShort());
							enhanceItem.setLevelUpCost(readInteger());
							enhanceItem.setFailedBackMoney(readInteger());
							enhanceItem.setCanLevelUp(readBoolean());
							enhanceItem.setLevelUpDesc(readString());
							enhanceItem.setLevelDownDesc(readString());
							enhanceItem.setCanLevelDown(readBoolean());
						{
			int subCount = readShort();
							com.pwrd.war.common.model.item.AttrDescBase[] subList = new com.pwrd.war.common.model.item.AttrDescBase[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = new com.pwrd.war.common.model.item.AttrDescBase();
													subList[j].setKey(readShort());
													subList[j].setMainValue(readString());
															}
			enhanceItem.setBaseAttrs(subList);
		}
						{
			int subCount = readShort();
							com.pwrd.war.common.model.item.AttrDescBase[] subList = new com.pwrd.war.common.model.item.AttrDescBase[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = new com.pwrd.war.common.model.item.AttrDescBase();
													subList[j].setKey(readShort());
													subList[j].setMainValue(readString());
															}
			enhanceItem.setAfterBaseAttrs(subList);
		}
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(enhanceItem.getUuid());
		writeString(enhanceItem.getName());
		writeShort(enhanceItem.getRarity());
		writeShort(enhanceItem.getLevel());
		writeShort(enhanceItem.getEnhanceLevel());
		writeShort(enhanceItem.getAfterEnhanceLevel());
		writeInteger(enhanceItem.getLevelUpCost());
		writeInteger(enhanceItem.getFailedBackMoney());
		writeBoolean(enhanceItem.getCanLevelUp());
		writeString(enhanceItem.getLevelUpDesc());
		writeString(enhanceItem.getLevelDownDesc());
		writeBoolean(enhanceItem.getCanLevelDown());
		com.pwrd.war.common.model.item.AttrDescBase[] baseAttrs=enhanceItem.getBaseAttrs();
		writeShort(baseAttrs.length);
		for(int i=0; i<baseAttrs.length; i++){	
				writeShort(baseAttrs[i].getKey());
				writeString(baseAttrs[i].getMainValue());
		}
		com.pwrd.war.common.model.item.AttrDescBase[] afterBaseAttrs=enhanceItem.getAfterBaseAttrs();
		writeShort(afterBaseAttrs.length);
		for(int i=0; i<afterBaseAttrs.length; i++){	
				writeShort(afterBaseAttrs[i].getKey());
				writeString(afterBaseAttrs[i].getMainValue());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EQUIP_ENHANCE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EQUIP_ENHANCE_INFO";
	}

	public com.pwrd.war.common.model.item.EnhanceItem getEnhanceItem(){
		return enhanceItem;
	}
		
	public void setEnhanceItem(com.pwrd.war.common.model.item.EnhanceItem enhanceItem){
		this.enhanceItem = enhanceItem;
	}
}