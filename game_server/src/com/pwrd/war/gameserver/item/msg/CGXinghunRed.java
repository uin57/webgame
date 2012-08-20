package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 取消镶嵌
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGXinghunRed extends CGMessage{
	
	/** 星魂背包ID */
	private int bagId;
	/** 星魂index */
	private int Index;
	/** 佩戴者uuid */
	private String uuid;
	/** 装备背包id */
	private int eqbagId;
	/** 装备index */
	private int eqbagIndex;
	
	public CGXinghunRed (){
	}
	
	public CGXinghunRed (
			int bagId,
			int Index,
			String uuid,
			int eqbagId,
			int eqbagIndex ){
			this.bagId = bagId;
			this.Index = Index;
			this.uuid = uuid;
			this.eqbagId = eqbagId;
			this.eqbagIndex = eqbagIndex;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readInteger();
		Index = readInteger();
		uuid = readString();
		eqbagId = readInteger();
		eqbagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bagId);
		writeInteger(Index);
		writeString(uuid);
		writeInteger(eqbagId);
		writeInteger(eqbagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_XINGHUN_RED;
	}
	
	@Override
	public String getTypeName() {
		return "CG_XINGHUN_RED";
	}

	public int getBagId(){
		return bagId;
	}
		
	public void setBagId(int bagId){
		this.bagId = bagId;
	}

	public int getIndex(){
		return Index;
	}
		
	public void setIndex(int Index){
		this.Index = Index;
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
		ItemHandlerFactory.getHandler().handleXinghunRed(this.getSession().getPlayer(), this);
	}
}