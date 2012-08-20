package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 打开背包某页的花费
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGOpenBagPageCost extends CGMessage{
	
	
	public CGOpenBagPageCost (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_BAG_PAGE_COST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_BAG_PAGE_COST";
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleOpenBagPageCost(this.getSession().getPlayer(), this);
	}
}