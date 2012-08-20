package com.pwrd.war.gameserver.item.operation.impl;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.ItemLangConstants_20000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemUseInfo;
import com.pwrd.war.gameserver.item.operation.AbstractUseItemOperation;
import com.pwrd.war.gameserver.item.template.ItemPetCardTemplate;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.role.Role;

/**
 * 武将卡使用处理
 * @author zy
 *
 */
public class UsePetCard  extends AbstractUseItemOperation {

	@Override
	public boolean consume(Human user, Item item, Role target) {
		return consumeOne(user, item);
	}

	@Override
	public Role getTarget(Human user, String targetUUID, Role target) { 
		return user;
	}

	@Override
	public boolean isSuitable(Human user, Item item, Role target) {
		//只有武将卡才能使用
		if(item.getTemplate() instanceof ItemPetCardTemplate){
			return true;
		}
		return false; 
	}

	@Override
	public ItemUseInfo collectItemUseInfo(Item item) {
		return collectItemAndTemplate(item);
	}

	@Override
	protected boolean canUseImpl(Human user, Item item, Role target) {
		// 检查一般条件
		if (!checkCommonConditions(user, item)) {
			return false;
		}
		
		// 检查物品模板中的武将是否已经存在，如果存在则无法使用
		return canUseCard(user, item.getTemplate());
	}
	
	/**
	 * 根据武将卡模板判断目标武将是否已经被招募
	 * @param human
	 * @param template
	 * @return
	 */
	private boolean canUseCard(Human human, ItemTemplate template) {
		if (template instanceof ItemPetCardTemplate) {
			ItemPetCardTemplate cardTemplate = (ItemPetCardTemplate)template;
			String petSn = cardTemplate.getPetSn();
			if (Globals.getPetService().getPetTemplate(petSn) != null) {
				if (human.getPetManager().containsPetSn(petSn)) {
					human.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.PET_CARD_USED);
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 使用武将卡
	 */
	@Override
	protected boolean useImpl(Human user, ItemUseInfo itemInfo, Role target) {
		Loggers.msgLogger.info("Use pet card " + itemInfo);
		
		//可以使用，根据模板中配置的武将编号给予武将
		ItemPetCardTemplate cardTemplate = (ItemPetCardTemplate)itemInfo.getTemplate();
		Globals.getPetService().givePetToHuman(user, cardTemplate.getPetSn());
		user.getPlayer().sendSystemMessage(ItemLangConstants_20000.PET_CARD_OK);
		
		this.consume(user, itemInfo.getItemToUse(), target);
		return true;
	}

}
