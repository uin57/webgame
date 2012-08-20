package com.pwrd.war.gameserver.item.operation.impl;

import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.buff.domain.ChangeBuffer;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef;
import com.pwrd.war.gameserver.item.ItemUseInfo;
import com.pwrd.war.gameserver.item.operation.AbstractUseItemOperation;
import com.pwrd.war.gameserver.item.template.ConsumeItemTemplate;
import com.pwrd.war.gameserver.role.Role;

/**
 * 变身
 * @author xf
 */
public class UseChangeBody extends AbstractUseItemOperation {

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
		return hasConsumableFunc(item, ItemDef.ConsumableFunc.CHANGE_BODY); 
	}

	@Override
	protected boolean useImpl(Human user, ItemUseInfo itemInfo, Role target) {	
		//8是变身buff
		Buffer buffer = Globals.getBufferService().getInstanceBuffer("8", Globals.getTimeService().now());
		if(buffer != null){
			ChangeBuffer cb = (ChangeBuffer) buffer;
			cb.setOwner(user);
			boolean rs = cb.addBuffer(user, ((ConsumeItemTemplate)itemInfo.getTemplate()));
			if(!rs)return rs;
			
			user.getPlayer().sendInfoChangeMessageToScene();
		}else{
			return false;
		}
		
		return this.consume(user, itemInfo.getItemToUse(), target); 
	}

	@Override
	public boolean consume(Human user, Item item, Role target) {
		return consumeOne(user, item);
	}
	
	@Override
	public Role getTarget(Human user, String targetUUID, Role target) {
		return target;
	}


}
