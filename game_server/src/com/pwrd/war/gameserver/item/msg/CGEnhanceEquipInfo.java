package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 取得强化装备一些信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGEnhanceEquipInfo extends CGMessage{
	
	
	public CGEnhanceEquipInfo (){
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
		return MessageType.CG_ENHANCE_EQUIP_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENHANCE_EQUIP_INFO";
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleEnhanceEquipInfo(this.getSession().getPlayer(), this);
	}
}