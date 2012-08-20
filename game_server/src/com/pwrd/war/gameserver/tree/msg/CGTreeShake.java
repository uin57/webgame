package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.tree.handler.TreeHandlerFactory;

/**
 * 摇钱
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTreeShake extends CGMessage{
	
	
	public CGTreeShake (){
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
		return MessageType.CG_TREE_SHAKE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TREE_SHAKE";
	}

	@Override
	public void execute() {
		TreeHandlerFactory.getHandler().handleTreeShake(this.getSession().getPlayer(), this);
	}
}