package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.tree.handler.TreeHandlerFactory;

/**
 * 获取果实
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetFruit extends CGMessage{
	
	/** 获取果实等级 */
	private int fruitLevel;
	
	public CGGetFruit (){
	}
	
	public CGGetFruit (
			int fruitLevel ){
			this.fruitLevel = fruitLevel;
	}
	
	@Override
	protected boolean readImpl() {
		fruitLevel = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(fruitLevel);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_FRUIT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_FRUIT";
	}

	public int getFruitLevel(){
		return fruitLevel;
	}
		
	public void setFruitLevel(int fruitLevel){
		this.fruitLevel = fruitLevel;
	}

	@Override
	public void execute() {
		TreeHandlerFactory.getHandler().handleGetFruit(this.getSession().getPlayer(), this);
	}
}