package com.pwrd.war.gameserver.item.container;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.LogReasons.ItemLogReason;
import com.pwrd.war.common.constants.RoleConstants;
import com.pwrd.war.common.model.mall.RedeemInfo;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.mall.msg.GCRedeemList;

public class RedeemBag extends ShoulderBag {

	private Map<String, Long> redeemMap = new TreeMap<String, Long>();

	public RedeemBag(Human owner, BagType bagType) {
		// TODO
		// super(owner, bagType, RoleConstants.PRIM_BAG_CAPACITY_PER_PAGE *
		// owner.getBagPageSize());
//		super(owner, bagType, RoleConstants.PRIM_BAG_CAPACITY_PER_PAGE * 1);
		super(owner, bagType, 60);
	}
 

	/**
	 * 将背包中一个物品移动到另一个背包 用于主背包物品移入
	 * 
	 * @author xf
	 */
	public Item moveIn(Item item, AbstractItemBag oBag,
			AbstractItemBag toBag, int num) {
//		//有无此道具
//		Item[] toItems = toBag.getBySn(item.getTemplate().getItemSn());
//		if(toItems.length > 0){
//			//
//			for(Item toItem : toItems){
//				if(toItem.getOverlap() < toItem.getMaxOverlap()){
//					
//				}
//			}
//		}
		
		// TODO 注意逻辑
		// 若目标背包没有空位
		if (toBag.getEmptySlotCount() == 0) {
			String uuid = getLastItemUuid();
			Item lastItem = toBag.getByUUID(uuid);
			int lastIndex = lastItem.getIndex();
			// 装备不能叠加、不能和最晚一个装备叠加、删除最后一个格子
			if (!item.canOverlap()
					|| !StringUtils.equals(lastItem.getTemplate().getItemSn(),
							item.getTemplate().getItemSn())
					|| (StringUtils.equals(lastItem.getTemplate().getItemSn(),
							item.getTemplate().getItemSn()) && lastItem
							.getOverlap() == lastItem.getMaxOverlap())) {

				//给前端发送删除物品消息
				RedeemInfo[] redeemList = new RedeemInfo[1];
				redeemList[0] = new RedeemInfo();
				redeemList[0].setFlag(-1);
				redeemList[0].setItemSn(lastItem.getTemplate().getItemSn());
				redeemList[0].setNumber(0);
				redeemList[0].setCurrencyType(lastItem.getTemplate().getCurrency());
				redeemList[0].setPrice(lastItem.getTemplate().getSellPrice() * 2);
				redeemList[0].setUuid(lastItem.getUUID());
				redeemList[0].setSellTime(System.currentTimeMillis()
						/ TimeUtils.SECOND);
				GCRedeemList dmsg = new GCRedeemList();
				dmsg.setRedeemInfo(redeemList);
				owner.sendMessage(dmsg);

				// 变成空道具
				toBag.items[lastIndex] = Item.newEmptyOwneredInstance(owner,
						toBag.getBagType(), lastIndex);

				// 移除map表里的时间
				redeemMap.remove(uuid);
			}
		}
		Item cpitem = Item.newCloneInstance(item);
		// TODO
		//当前数量减一
//		item.changeOverlap(item.getOverlap() - 1, null, null, null, false);
//		int oindex = item.getIndex();
		// 变成空道具
//		oBag.items[oindex] = Item.newEmptyOwneredInstance(owner,
//				oBag.getBagType(), oindex);
		
		Item empty = toBag.getEmptySlot();// 空的，占位的
		cpitem.setIndex(empty.getIndex());
		cpitem.setBagType(toBag.getBagType());
		cpitem.changeOverlap(num, ItemLogReason.SELL_COST, "出售的个数" + num, "出售",
				true);
		toBag.putItem(cpitem);

		// 把道具卖出的时间加入到map
		Long sellTime = System.currentTimeMillis();
		redeemMap.put(cpitem.getUUID(), sellTime);

		item.changeOverlap(item.getOverlap() - num, ItemLogReason.SELL_COST,
				"出售的个数" + num, "出售", true);
		return cpitem;
	}

	/**
	 * 回购背包移出
	 * 
	 * @author xf
	 */
	public Item moveOut(Item item, AbstractItemBag oBag,
			AbstractItemBag toBag, int num) {
		
		if (toBag.getEmptySlotCount() == 0) {
			return null;
		}
		if (oBag.owner != toBag.owner) {
			return null;
		}
		
		//复制item
		Item cpitem = Item.newCloneInstance(item);
		cpitem.getLifeCycle().activate();
		int oindex = item.getIndex();

		// 变成空道具
		oBag.items[oindex] = Item.newEmptyOwneredInstance(oBag.owner,
				oBag.getBagType(), oindex);
		
		

		Item empty = toBag.getEmptySlot();// 空的，占位的
		cpitem.setIndex(empty.getIndex());
		cpitem.setBagType(toBag.getBagType());
		cpitem.changeOverlap(num, ItemLogReason.REDEEM_BUY, "回购买入", "回购买入", true);
		toBag.putItem(cpitem);
		
		
		// 移除回购背包存的卖出时间
		if(item.getOverlap() == num){
			redeemMap.remove(item.getUUID());
		}
		item.changeOverlap(item.getOverlap() - num, ItemLogReason.SELL_COST,
				"出售的个数" + num, "出售", true);

		return cpitem;
	}

	public String getLastItemUuid() {
		String result = "";
		Long time = Long.MAX_VALUE;
		for (Map.Entry<String, Long> e : redeemMap.entrySet()) {
			if (e.getValue() <= time) {
				time = e.getValue();
				result = e.getKey();
			}
		}
		return result;
	}

	public long getSellTime(String uuid) {
		return redeemMap.get(uuid);
	}
}
