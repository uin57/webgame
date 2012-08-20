package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 请求查看物品信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGReqItemInfo extends CGMessage{
	
	/** 查询目标的UUID */
	private String targetUUID;
	/** 物品所有者的UUID */
	private String ownerUUID;
	/** 查询类别 */
	private short queryType;
	
	public CGReqItemInfo (){
	}
	
	public CGReqItemInfo (
			String targetUUID,
			String ownerUUID,
			short queryType ){
			this.targetUUID = targetUUID;
			this.ownerUUID = ownerUUID;
			this.queryType = queryType;
	}
	
	@Override
	protected boolean readImpl() {
		targetUUID = readString();
		ownerUUID = readString();
		queryType = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(targetUUID);
		writeString(ownerUUID);
		writeShort(queryType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_REQ_ITEM_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REQ_ITEM_INFO";
	}

	public String getTargetUUID(){
		return targetUUID;
	}
		
	public void setTargetUUID(String targetUUID){
		this.targetUUID = targetUUID;
	}

	public String getOwnerUUID(){
		return ownerUUID;
	}
		
	public void setOwnerUUID(String ownerUUID){
		this.ownerUUID = ownerUUID;
	}

	public short getQueryType(){
		return queryType;
	}
		
	public void setQueryType(short queryType){
		this.queryType = queryType;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleReqItemInfo(this.getSession().getPlayer(), this);
	}
}