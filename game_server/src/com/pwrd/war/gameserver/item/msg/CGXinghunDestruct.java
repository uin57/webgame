package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 星魂销毁
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGXinghunDestruct extends CGMessage{
	
	/** 背包ID */
	private int bagId;
	/** 背包内位置索引 */
	private int index;
	/** 佩戴者uuid */
	private String uuid;
	/** 装备背包id */
	private int eqbagId;
	/** 装备index */
	private int eqbagIndex;
	
	public CGXinghunDestruct (){
	}
	
	public CGXinghunDestruct (
			int bagId,
			int index,
			String uuid,
			int eqbagId,
			int eqbagIndex ){
			this.bagId = bagId;
			this.index = index;
			this.uuid = uuid;
			this.eqbagId = eqbagId;
			this.eqbagIndex = eqbagIndex;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readInteger();
		index = readInteger();
		uuid = readString();
		eqbagId = readInteger();
		eqbagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bagId);
		writeInteger(index);
		writeString(uuid);
		writeInteger(eqbagId);
		writeInteger(eqbagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_XINGHUN_DESTRUCT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_XINGHUN_DESTRUCT";
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

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleXinghunDestruct(this.getSession().getPlayer(), this);
	}
}