package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 星魂洗练
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGXinghunXilian extends CGMessage{
	
	/** 背包ID */
	private int bagId;
	/** 背包内位置索引 */
	private int index;
	/** -1未激活，0锁定，1激活 */
	private int prop1;
	/** -1未激活，0锁定，1激活 */
	private int prop2;
	/** -1未激活，0锁定，1激活 */
	private int prop3;
	/** 装备背包id */
	private int eqbagId;
	/** 装备index */
	private int eqbagIndex;
	/** 佩戴者uuid */
	private String uuid;
	
	public CGXinghunXilian (){
	}
	
	public CGXinghunXilian (
			int bagId,
			int index,
			int prop1,
			int prop2,
			int prop3,
			int eqbagId,
			int eqbagIndex,
			String uuid ){
			this.bagId = bagId;
			this.index = index;
			this.prop1 = prop1;
			this.prop2 = prop2;
			this.prop3 = prop3;
			this.eqbagId = eqbagId;
			this.eqbagIndex = eqbagIndex;
			this.uuid = uuid;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readInteger();
		index = readInteger();
		prop1 = readInteger();
		prop2 = readInteger();
		prop3 = readInteger();
		eqbagId = readInteger();
		eqbagIndex = readInteger();
		uuid = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bagId);
		writeInteger(index);
		writeInteger(prop1);
		writeInteger(prop2);
		writeInteger(prop3);
		writeInteger(eqbagId);
		writeInteger(eqbagIndex);
		writeString(uuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_XINGHUN_XILIAN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_XINGHUN_XILIAN";
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

	public int getProp1(){
		return prop1;
	}
		
	public void setProp1(int prop1){
		this.prop1 = prop1;
	}

	public int getProp2(){
		return prop2;
	}
		
	public void setProp2(int prop2){
		this.prop2 = prop2;
	}

	public int getProp3(){
		return prop3;
	}
		
	public void setProp3(int prop3){
		this.prop3 = prop3;
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
		ItemHandlerFactory.getHandler().handleXinghunXilian(this.getSession().getPlayer(), this);
	}
}