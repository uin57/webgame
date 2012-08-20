package com.pwrd.war.gameserver.giftBag.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回礼包信息列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGiftBagInfoList extends GCMessage{
	
	/** 礼包信息列表 */
	private com.pwrd.war.gameserver.giftBag.model.vo.GiftBagInfo[] GiftBagInfoList;

	public GCGiftBagInfoList (){
	}
	
	public GCGiftBagInfoList (
			com.pwrd.war.gameserver.giftBag.model.vo.GiftBagInfo[] GiftBagInfoList ){
			this.GiftBagInfoList = GiftBagInfoList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		GiftBagInfoList = new com.pwrd.war.gameserver.giftBag.model.vo.GiftBagInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.giftBag.model.vo.GiftBagInfo obj = new com.pwrd.war.gameserver.giftBag.model.vo.GiftBagInfo();
			obj.setId(readString());
			obj.setGiftId(readString());
			obj.setName(readString());
			obj.setImg(readString());
			obj.setDesc(readString());
			GiftBagInfoList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(GiftBagInfoList.length);
		for(int i=0; i<GiftBagInfoList.length; i++){
			writeString(GiftBagInfoList[i].getId());
			writeString(GiftBagInfoList[i].getGiftId());
			writeString(GiftBagInfoList[i].getName());
			writeString(GiftBagInfoList[i].getImg());
			writeString(GiftBagInfoList[i].getDesc());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GIFT_BAG_INFO_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GIFT_BAG_INFO_LIST";
	}

	public com.pwrd.war.gameserver.giftBag.model.vo.GiftBagInfo[] getGiftBagInfoList(){
		return GiftBagInfoList;
	}

	public void setGiftBagInfoList(com.pwrd.war.gameserver.giftBag.model.vo.GiftBagInfo[] GiftBagInfoList){
		this.GiftBagInfoList = GiftBagInfoList;
	}	
}