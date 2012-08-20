package com.pwrd.war.gameserver.item.operation.impl;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.ItemLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.i18n.constants.ItemLangConstants_20000;
import com.pwrd.war.gameserver.common.msg.GCSystemMessage;
import com.pwrd.war.gameserver.currency.CurrencyCostType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.EquipmentFeature;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemFeature;
import com.pwrd.war.gameserver.item.ItemUseInfo;
import com.pwrd.war.gameserver.item.container.AbstractItemBag;
import com.pwrd.war.gameserver.item.container.HumanEquipBag;
import com.pwrd.war.gameserver.item.container.PetBodyEquipBag;
import com.pwrd.war.gameserver.item.msg.GCUseReel;
import com.pwrd.war.gameserver.item.operation.AbstractUseItemOperation;
import com.pwrd.war.gameserver.item.template.EquipItemTemplate;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.item.template.MaterialTemplate;
import com.pwrd.war.gameserver.item.template.ReelTemplate;
import com.pwrd.war.gameserver.role.Role;

public class UseReel  extends AbstractUseItemOperation {

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
		//只有是卷轴的时候才可以
		if(item.getTemplate() instanceof ReelTemplate){
			return true;
		}
		return false; 
	}

	@Override
	public ItemUseInfo collectItemUseInfo(Item item) {
		return collectItemOnly(item);
	}

	@Override
	protected boolean canUseImpl(Human user, Item item, Role target) {
		// 检查一般条件
		if (!checkCommonConditions(user, item)) {
			return false;
		} 
		return true;
	}
 
	private boolean checkHasItems(Human user, Map<String, Integer> needs, boolean notify){
		//判断数量是否足够
		for(Map.Entry<String, Integer> e : needs.entrySet()){
			//取得该sn的所有物品
			//先看背包
			Item[] items = user.getInventory().getPrimBag().getBySn(e.getKey());
			int count = 0;
			for(Item item : items){
				count += item.getOverlap();
			}
			if(count < e.getValue()){
				//看看仓库里可有...
				Item[] itemsDepot = user.getInventory().getDepotBag().getBySn(e.getKey());
				for(Item item : itemsDepot){
					count += item.getOverlap();
				}
			}
			if(count < e.getValue()){
				if(notify){
					user.sendErrorMessage(
							ItemLangConstants_20000.ITEM_NOT_ENOUGH,
							Globals.getItemService().getTemplateByItemSn(e.getKey()).getName(),
							e.getValue());
					
				}
				return false;
			}
		}
		return true;
	} 
	private void sendResultMessag(Human user, boolean ok){
		user.sendMessage(new GCUseReel(ok?1:0));
	}
	@Override
	protected boolean useImpl(Human user, ItemUseInfo itemInfo, Role target) {
		Loggers.msgLogger.info("使用卷轴 " + itemInfo);
		boolean bUseGold = false;
		String toEquip = null;
		if(!StringUtils.isEmpty(itemInfo.getParams())){
			String[] p = itemInfo.getParams().split(",");
			bUseGold = Boolean.valueOf(p[0]);
			if(p.length > 1){
				toEquip = p[1];
			}
		}
		//检查每个道具是否足够
		ReelTemplate tmp = (ReelTemplate) itemInfo.getItemToUse().getTemplate();
		Map<String, Integer> needs = tmp.getNeedItems();
		//判断是装备卷轴还是丹药卷轴
		
		Item equip  = null;
		if(StringUtils.isEmpty(toEquip)){
			Item[] items = user.getInventory().getPrimBag().getBySn(tmp.getNeedEquipSn());
			//找到index最小的
			int index = Integer.MAX_VALUE;
			for(Item item : items){
				if(item.getIndex() < index){
					index = item.getIndex();
					equip = item;
				}
			} 
		}else{
			//直接取该装备
			equip = user.getInventory().getItemByUUID(toEquip); 
		}
		if(equip == null && !bUseGold){//装备不存在也不用元宝
			user.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_NOT_EXIST);
			this.sendResultMessag(user, false);
			return false;
		}
		if(equip != null && !StringUtils.equals(tmp.getNeedEquipSn(), equip.getTemplate().getItemSn())){
			user.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_NOT_EXIST);
			this.sendResultMessag(user, false);
			return false;
		}
		boolean has = this.checkHasItems(user, needs, !bUseGold);
		if(!has && !bUseGold){//材料不够也不适用元宝
			this.sendResultMessag(user, false);
			return false;
		}
		boolean bUserDepot = false;
		if(bUseGold){
			//检查元宝是否足够
			int needGold = 0;
			if(equip == null){
				ItemTemplate itemTmp = Globals.getItemService().getItemTempByTempId(tmp.getNeedEquipSn());
				if(itemTmp instanceof EquipItemTemplate){
					needGold +=((EquipItemTemplate)itemTmp).getItem1Gold();
				}else{
					needGold = ((MaterialTemplate)itemTmp).getGold() * tmp.getNeedEquipNum();
				}
			}
			//扣除道具先
			for(Map.Entry<String, Integer> e : needs.entrySet()){
				//取得该sn的所有物品
				//先看背包
				Item[] items = user.getInventory().getPrimBag().getBySn(e.getKey());
				int count = e.getValue();
				for(Item item : items){
					if(count > item.getOverlap()){
						count -= item.getOverlap();
					}else {
						count = 0;
					}
				}
				if(count > 0){
					//看看仓库里可有...
					Item[] itemsDepot = user.getInventory().getDepotBag().getBySn(e.getKey());
					for(Item item : itemsDepot){
						if(count > item.getOverlap()){
							count -= item.getOverlap();
						}else {
							count = 0;
						}
					}
				}
				if(count > 0){
					//剩下的用元宝了
					
					needGold += ((MaterialTemplate)Globals.getItemService().getItemTempByTempId(e.getKey())).getGold() * count;
				}
			}
			if(!user.hasEnoughGold(needGold, true)){
				this.sendResultMessag(user, false);
				return false;
			}
			// 扣除元宝和物品
			if (!user.costGold(needGold, true, null, CurrencyLogReason.GGZJ,
					CurrencyLogReason.GGZJ.getReasonText(), 0)) {
				this.sendResultMessag(user, false);
				return false;
			}	
		}
		//扣除道具先
		for(Map.Entry<String, Integer> e : needs.entrySet()){
			//取得该sn的所有物品
			//先看背包
			Item[] items = user.getInventory().getPrimBag().getBySn(e.getKey());
			int count = e.getValue();
			for(Item item : items){
				if(count > item.getOverlap()){
					item.changeOverlap(0, null, "扣除用来合成", "", false);
					count -= item.getOverlap();
				}else {
					item.changeOverlap(item.getOverlap() - count, null, "扣除用来合成", "", false);
					count = 0;
				}
			}
			if(count > 0){
				//看看仓库里可有...
				Item[] itemsDepot = user.getInventory().getDepotBag().getBySn(e.getKey());
				for(Item item : itemsDepot){
					if(count > item.getOverlap()){
						item.changeOverlap(0, null, "扣除用来合成", "", false);
						count -= item.getOverlap();
					}else {
						item.changeOverlap(item.getOverlap() - count, null, "扣除用来合成", "", false);
						count = 0;
					}
					bUserDepot = true;
				}
			} 
		} 
		
		//装备变成另一个装备，同时等级降低5级
		ItemTemplate newTmp = Globals.getItemService().getTemplateByItemSn(tmp.getGetItemSn());
		Item newItem = null;
		if(newTmp.isEquipment()){
			if(equip != null){
				newItem = Item.newActivatedInstance(user, newTmp, equip.getBagType(), equip.getIndex());
				newItem.setWearerId(equip.getWearerId()); 
			}else{
				Item empItem = user.getInventory().getPrimBag().getEmptySlot();
				newItem = Item.newActivatedInstance(user, newTmp, empItem.getBagType(), empItem.getIndex());
				newItem.setWearerId(empItem.getWearerId()); 
			}
//			user.sendSystemMessage(CommonLangConstants_10000.GET_SOMETHING, 1, newTmp.getName());
//			GCSystemMessage msg = new GCSystemMessage("十年磨一剑终于打造出了",
//					GameColor.SYSMSG_SYS.getRgb(), SysMsgShowTypes.generic);
			GCSystemMessage msg = user.getPlayer().getSystemMessage(ItemLangConstants_20000.USE_RELL_SUCC,
					user.getPlayer().getSystemPlayerMsgPart(user.getPlayer()),
					user.getPlayer().getSystemItemMsgPart(newItem.getTemplate()));
			Globals.getOnlinePlayerService().broadcastMessage(msg);
			
		}else{
			//生成新的放到背包
			Collection<Item> coll = user.getInventory().addItem(newTmp.getItemSn(), 1, newTmp.getBindStatusAfterOper(BindStatus.NOT_BIND,
					Bindable.Oper.GET), null, null, true);
			if(coll.size() > 0){
				newItem = coll.iterator().next();
			}else{
				user.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.BAG_FULL,
						Globals.getLangService().read(newTmp.getBagType().getNameLangId())
						);
				this.sendResultMessag(user, false);
				return false;
			}
		}
  
		
		
		ItemFeature of = null;
		if(equip != null){
			of = equip.getFeature();
		}
		ItemFeature nf = newItem.getFeature();
		if(of != null && nf != null){
			//复制feature
			of.cloneTo(nf);
		}
		
		
		if(newTmp.isEquipment()){
			//是装备，看看当前强化等级
			EquipmentFeature nef = (EquipmentFeature) nf;
			int level = nef.getEnhanceLevel();
//			if(level > 5){
//				level -= 5; 
//				//改变属性
//				Globals.getItemService().buildPropsByEnhanceLevel(newItem,  level);
//			}else{
//				level = 1;
//				Globals.getItemService().buildPropsByEnhanceLevel(newItem,  level); 
//			}
			if(level < 1) level = 1;
			Globals.getItemService().buildPropsByEnhanceLevel(newItem,  level); 
		}  
		//如果是装备需要从装备中删除
		if(equip != null && equip.isEquipment() && 
				(equip.getBagType() == BagType.HUMAN_EQUIP || equip.getBagType() == BagType.PET_EQUIP)){
			if(equip.getBagType() == BagType.HUMAN_EQUIP){
				HumanEquipBag heb = user.getInventory().getEquipBag();
				heb.addOrRemovePosition(equip.getPosition(), newItem);
			}
			else if(equip.getBagType() == BagType.PET_EQUIP){
				PetBodyEquipBag heb = user.getInventory().getBagByPet(equip.getWearerId());
				heb.addOrRemovePosition(equip.getPosition(), newItem);
			}
		}
		if(equip != null){
			equip.changeOverlap(equip.getOverlap() - tmp.getNeedEquipNum(),
					ItemLogReason.USED, "已经合成或者升级了了", "", false);
		}
		if(newTmp.isEquipment()){ 
			newItem.changeOverlap(1, ItemLogReason.REEL, "打造卷轴得到的东西", "", false);			
		}  
		
		AbstractItemBag bag = user.getInventory().getBagByType(newItem.getBagType(), newItem.getWearerId());
		if(newTmp.isEquipment()){
			//直接替换的
			bag.putItem(newItem);
		} 
		
		 
		
		//发送道具变化信息
		user.sendMessage(user.getInventory().getPrimBagInfoMsg());
		if(bUserDepot){//仓库发生变化
			user.sendMessage(user.getInventory().getDepotBagInfoMsg());
		}
		if(bag instanceof HumanEquipBag){//人物武器包
			user.sendMessage(user.getInventory().getEquipBagInfoMsg());
		}else if(bag instanceof PetBodyEquipBag){//武将武器包
			user.sendMessage(user.getInventory().getPetEquipBagInfoMsg(newItem.getWearerId()));
		}
		 
		
		//重新计算属性
		user.calcProps();
		
		this.sendResultMessag(user, true);
		
		this.consume(user, itemInfo.getItemToUse(), target);
		return true;
	}

}
