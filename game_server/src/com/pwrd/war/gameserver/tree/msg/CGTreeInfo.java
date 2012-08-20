package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.tree.handler.TreeHandlerFactory;

/**
 * 摇钱树信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTreeInfo extends CGMessage{
	
	
	public CGTreeInfo (){
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
		return MessageType.CG_TREE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_TREE_INFO";
	}

	@Override
	public void execute() {
		TreeHandlerFactory.getHandler().handleTreeInfo(this.getSession().getPlayer(), this);
	}
}