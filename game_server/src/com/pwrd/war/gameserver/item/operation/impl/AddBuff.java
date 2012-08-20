package com.pwrd.war.gameserver.item.operation.impl;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.HPReduceEvent;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef;
import com.pwrd.war.gameserver.item.ItemUseInfo;
import com.pwrd.war.gameserver.item.operation.AbstractUseItemOperation;
import com.pwrd.war.gameserver.item.template.ConsumeItemTemplate;
import com.pwrd.war.gameserver.role.Role;

/**
 * 使用后自身获得buff
 * 
 * XXX 此类只是一个演示测试类 物品可以通过添加自己的使用函数来进行消耗或者扩展出其他行为
 * 
 */
public class AddBuff extends AbstractUseItemOperation {

	@Override
	protected boolean canUseImpl(Human user, Item item, Role target) {
		// 检查一般条件
		if (!checkCommonConditions(user, item)) {
			return false;
		}

		return true;
	}

	@Override
	public ItemUseInfo collectItemUseInfo(Item item) {
		return collectItemAndTemplate(item);
	}

	@Override
	public boolean isSuitable(Human user, Item item, Role target) {
		return hasConsumableFunc(item, ItemDef.ConsumableFunc.ADD_BUFF); 
	}

	@Override
	protected boolean useImpl(Human user, ItemUseInfo itemInfo, Role target) {	
		if(target == null){
			user.sendErrorMessage(CommonLangConstants_10000.OBJECT_NOT_EXIST);
			return false;
		}
		//血包血量 
		//TODO 添加该buff 
		boolean rs = user.addBuffer(((ConsumeItemTemplate)itemInfo.getTemplate()).getParam1());
		if(!rs){
			return false;
		}
		
		HPReduceEvent e = new HPReduceEvent(user);
		Globals.getEventService().fireEvent(e);
		
		this.consume(user, itemInfo.getItemToUse(), target);
		return true;
	}

	@Override
	public boolean consume(Human user, Item item, Role target) {
		return consumeOne(user, item);
	}
	
	@Override
	public Role getTarget(Human user, String targetUUID, Role target) {
		return user;
	}

}
