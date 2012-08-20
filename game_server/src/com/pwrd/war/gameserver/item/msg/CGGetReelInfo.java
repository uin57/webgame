package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 取得合成后应该有的属性
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetReelInfo extends CGMessage{
	
	/** 背包ID */
	private int bagId;
	/** 背包内位置索引 */
	private int index;
	/** 佩戴者uuid */
	private String uuid;
	/** 卷轴SN */
	private String reelSN;
	
	public CGGetReelInfo (){
	}
	
	public CGGetReelInfo (
			int bagId,
			int index,
			String uuid,
			String reelSN ){
			this.bagId = bagId;
			this.index = index;
			this.uuid = uuid;
			this.reelSN = reelSN;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readInteger();
		index = readInteger();
		uuid = readString();
		reelSN = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bagId);
		writeInteger(index);
		writeString(uuid);
		writeString(reelSN);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_REEL_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_REEL_INFO";
	}

	public int getBagId(){
		return bagId;
	}
		
	public void setBagId(int bagId){
		this.bagId = bagId;
	}

	public int getIndex(){
		return index;
	}
		
	public void setIndex(int index){
		this.index = index;
	}

	public String getUuid(){
		return uuid;
	}
		
	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	public String getReelSN(){
		return reelSN;
	}
		
	public void setReelSN(String reelSN){
		this.reelSN = reelSN;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleGetReelInfo(this.getSession().getPlayer(), this);
	}
}