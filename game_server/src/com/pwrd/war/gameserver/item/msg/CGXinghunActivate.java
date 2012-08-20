package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 星魂激活
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGXinghunActivate extends CGMessage{
	
	/** 背包ID */
	private int bagId;
	/** 背包内位置索引 */
	private int index;
	/** 装备背包id */
	private int eqbagId;
	/** 装备index */
	private int eqbagIndex;
	/** 佩戴者uuid */
	private String uuid;
	
	public CGXinghunActivate (){
	}
	
	public CGXinghunActivate (
			int bagId,
			int index,
			int eqbagId,
			int eqbagIndex,
			String uuid ){
			this.bagId = bagId;
			this.index = index;
			this.eqbagId = eqbagId;
			this.eqbagIndex = eqbagIndex;
			this.uuid = uuid;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readInteger();
		index = readInteger();
		eqbagId = readInteger();
		eqbagIndex = readInteger();
		uuid = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bagId);
		writeInteger(index);
		writeInteger(eqbagId);
		writeInteger(eqbagIndex);
		writeString(uuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_XINGHUN_ACTIVATE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_XINGHUN_ACTIVATE";
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

	public int getEqbagId(){
		return eqbagId;
	}
		
	public void setEqbagId(int eqbagId){
		this.eqbagId = eqbagId;
	}

	public int getEqbagIndex(){
		return eqbagIndex;
	}
		
	public void setEqbagIndex(int eqbagIndex){
		this.eqbagIndex = eqbagIndex;
	}

	public String getUuid(){
		return uuid;
	}
		
	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleXinghunActivate(this.getSession().getPlayer(), this);
	}
}