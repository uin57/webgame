package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 人物使用道具消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGUseItem extends CGMessage{
	
	/** 背包ID */
	private short bagId;
	/** 背包内位置索引 */
	private short index;
	/** 使用对象的UUID,人物或者武将uuid */
	private String targetUuid;
	/** 使用者UUID,人物或者武将uuid */
	private String wearId;
	/** 额外参数 */
	private String params;
	
	public CGUseItem (){
	}
	
	public CGUseItem (
			short bagId,
			short index,
			String targetUuid,
			String wearId,
			String params ){
			this.bagId = bagId;
			this.index = index;
			this.targetUuid = targetUuid;
			this.wearId = wearId;
			this.params = params;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readShort();
		index = readShort();
		targetUuid = readString();
		wearId = readString();
		params = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagId);
		writeShort(index);
		writeString(targetUuid);
		writeString(wearId);
		writeString(params);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_USE_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_USE_ITEM";
	}

	public short getBagId(){
		return bagId;
	}
		
	public void setBagId(short bagId){
		this.bagId = bagId;
	}

	public short getIndex(){
		return index;
	}
		
	public void setIndex(short index){
		this.index = index;
	}

	public String getTargetUuid(){
		return targetUuid;
	}
		
	public void setTargetUuid(String targetUuid){
		this.targetUuid = targetUuid;
	}

	public String getWearId(){
		return wearId;
	}
		
	public void setWearId(String wearId){
		this.wearId = wearId;
	}

	public String getParams(){
		return params;
	}
		
	public void setParams(String params){
		this.params = params;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleUseItem(this.getSession().getPlayer(), this);
	}
}