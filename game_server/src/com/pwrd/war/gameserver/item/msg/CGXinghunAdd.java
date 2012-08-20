package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 星魂镶嵌
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGXinghunAdd extends CGMessage{
	
	/** 星魂背包ID */
	private int xhbagId;
	/** 星魂index */
	private int xhIndex;
	/** 装备背包id */
	private int eqbagId;
	/** 装备index */
	private int eqbagIndex;
	/** 佩戴者uuid */
	private String uuid;
	
	public CGXinghunAdd (){
	}
	
	public CGXinghunAdd (
			int xhbagId,
			int xhIndex,
			int eqbagId,
			int eqbagIndex,
			String uuid ){
			this.xhbagId = xhbagId;
			this.xhIndex = xhIndex;
			this.eqbagId = eqbagId;
			this.eqbagIndex = eqbagIndex;
			this.uuid = uuid;
	}
	
	@Override
	protected boolean readImpl() {
		xhbagId = readInteger();
		xhIndex = readInteger();
		eqbagId = readInteger();
		eqbagIndex = readInteger();
		uuid = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(xhbagId);
		writeInteger(xhIndex);
		writeInteger(eqbagId);
		writeInteger(eqbagIndex);
		writeString(uuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_XINGHUN_ADD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_XINGHUN_ADD";
	}

	public int getXhbagId(){
		return xhbagId;
	}
		
	public void setXhbagId(int xhbagId){
		this.xhbagId = xhbagId;
	}

	public int getXhIndex(){
		return xhIndex;
	}
		
	public void setXhIndex(int xhIndex){
		this.xhIndex = xhIndex;
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
		ItemHandlerFactory.getHandler().handleXinghunAdd(this.getSession().getPlayer(), this);
	}
}