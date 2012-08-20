package com.pwrd.war.gameserver.item.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.common.LogReasons.ItemLogReason;
import com.pwrd.war.common.LogReasons.ReasonDesc;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemService;
import com.pwrd.war.gameserver.item.template.ItemTemplate;

/**
 * human的背包,主道具包，材料包，任务道具，仓库包都属于这种类型
 * 
 * 
 */
public class ShoulderBag extends CommonBag {
 

	public ShoulderBag(Human owner, BagType bagType, int bagSize) {
		//TODO 
//		super(owner, bagType, RoleConstants.PRIM_BAG_CAPACITY_PER_PAGE * owner.getBagPageSize());
		super(owner, bagType, bagSize);
	}

	
	/**
	 * 获得包裹中还可以添加的指定道具的个数,已经考虑了道具叠加的情况和道具的绑定情况
	 * 
	 * @param template
	 *            道具模板
	 * @param bindStatus
	 *            道具的绑定状态
	 * @return
	 */
	public int getMaxCanAdd(ItemTemplate template,
			Bindable.BindStatus bindStatus) {
		int count = 0;
		final int overlap = template.getMaxOverlap();
		for (Item item : items) {
			if (Item.isEmpty(item)) {
				count += overlap;
			} else if (ItemService.canOverlapOn(item, template.getId(), bindStatus)) {
				count += (overlap - item.getOverlap());
			}
		}
		return count;
	}

	/**
	 * 取得指定的道具还可以叠加的总数,考虑道具的绑定状态
	 * 
	 * @param templateId
	 *            道具模板ID
	 * @param bindStatus
	 *            添加道具的绑定状态
	 * @return
	 */
	private int getCanOverlapCount(int templateId, BindStatus bindStatus) {
		int left = 0;
		for (Item item : this.items) {
			if (ItemService.canOverlapOn(item, templateId, bindStatus)) {
				left += (item.getTemplate().getMaxOverlap() - item.getOverlap());
			}
		}
		return left;
	}

	/**
	 * 检查要放下count个模板为template的道具需要多少个槽位
	 * 
	 * @param template
	 * @param count
	 * @param bindStatus
	 * @return
	 */
	public int getNeedSlot(ItemTemplate template, int count,
			BindStatus bindStatus) {
		// 还可以叠加几个，这些不占新槽位
		int canOverlapCount = getCanOverlapCount(template.getId(), bindStatus);
		if (count <= canOverlapCount) {
			return 0;
		} else {
			int leftCount = count - canOverlapCount;
			int maxovlp = template.getMaxOverlap();
			return (leftCount + maxovlp - 1) / maxovlp;
		}
	}

	/**
	 * 获取背包中的空格个数
	 * 
	 * @return
	 */
	public int getEmptySlotCount() {
		int count = 0;
		for (Item item : items) {
			if (item.isEmpty()) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 向包中添加一定数量的某种道具，尽可能叠加，留下更多空位<br />
	 * 要么全放下，要么一个也不放
	 * 
	 * @param template
	 *            添加的道具模板
	 * @param count
	 *            添加的道具数
	 * @param bind
	 *            绑定状态
	 * @param reason
	 *            添加的原因
	 * @param detailReason
	 *            添加原因的详细说明
	 * @return
	 */
	public Collection<Item> add(ItemTemplate template, int count,
			BindStatus bind, ItemGenLogReason reason, String detailReason) {
		if (count == 0) {
			return Collections.emptyList();
		}
		// 不能全放下，一个也不放
		if (getMaxCanAdd(template, bind) < count) {
			return Collections.emptyList();
		}

		boolean needSendLog = true;
		String genKey = "";
		try {
			do {				
				// 记录道具产生日志
				genKey = KeyUtil.UUIDKey();
				Globals.getLogService().sendItemGenLog(owner, reason, detailReason,
						template.getId(), template.getName(), count, bind.index, 0, "", genKey);
				// 增加物品增加原因到reasonDetail
				String countChangeReason = " [genReason:" + reason.getClass()
				.getField(reason.name()).getAnnotation(ReasonDesc.class).value()
						+ "] ";
				detailReason = detailReason == null ? countChangeReason
						: detailReason + countChangeReason;
			} while (false);
		} catch (Exception e) {
			Loggers.itemLogger.error(LogUtils.buildLogInfoStr(owner.getUUID(),
					"记录向包中添加一定数量物品日志时出错"), e);
		}
		List<Item> updateList = new ArrayList<Item>();
		int left = count;
		// 在可以叠加的情况下,优先叠加
		if (template.canOverlap()) {
			left = addToOverlapSlot(template, left, bind,
					ItemLogReason.COUNT_ADD, detailReason, genKey, updateList,
					needSendLog);
		}
		// 还没全放下,就添加到空格中
		if (left > 0) {
			left = addToEmptySlot(template, left, bind,
					ItemLogReason.COUNT_ADD, detailReason, genKey, updateList,
					needSendLog);
		}
		// 还没全放下，肯定出错了
		if (left != 0) {
			Loggers.itemLogger.error(LogUtils.buildLogInfoStr(owner.getUUID(),
					String.format("出现添加道具不完全的异常， itemId=%d count=%d left=%d",
							template.getId(), count, left)));
		}
		return updateList;
	}

	/**
	 * 向非空格中叠加道具，忽略空格，考虑道具的绑定状态，尽力放置，如果放不下返回剩下的个数
	 * 
	 * @param updateList
	 *            需要跟新的Item的list
	 * @return 放不下剩下的个数
	 */
	private int addToOverlapSlot(ItemTemplate template, int count,
			BindStatus bindStatus, ItemLogReason reason, String reasonDetail,
			String genKey, List<Item> updateList, boolean needSendLog) {
		if (count == 0) {
			return 0;
		}
		int left = count;
		for (Item item : items) {
			if (left == 0) {
				break;
			}
			if (ItemService.canOverlapOn(item, template.getId(), bindStatus)) {
				// 找到已经存在的位置, 绑定状态和添加的物品状态一致，不需要重新设置绑定状态
				if (template.getMaxOverlap() > item.getOverlap()) {
					int capacity = template.getMaxOverlap() - item.getOverlap();
					if (capacity >= left) {
						// 可以全部放入
						item.changeOverlap(item.getOverlap() + left, reason,
								reasonDetail, genKey, needSendLog);
						left = 0;
						updateList.add(item);
						break;
					} else {
						// 只能放一部分
						item.changeOverlap(template.getMaxOverlap(), reason,
								reasonDetail, genKey, needSendLog);
						left -= capacity;
						updateList.add(item);
					}
				}
			}
		}
		return left;
	}

	/**
	 * 向空格中增加道具，不叠加，考虑道具的绑定状态，尽力放置，如果放不下返回剩下的个数
	 * 
	 * @param updateList
	 *            需要跟新的Item的list
	 * @return 放不下剩下的个数
	 */
	private int addToEmptySlot(ItemTemplate template, int count,
			BindStatus bindStatus, ItemLogReason reason, String reasonDetail,
			String genKey, List<Item> updateList, boolean needSendLog) {
		int left = count;
		if (count == 0) {
			return 0;
		}
		for (Item item : items) {
			if (left == 0) {
				break;
			}
			if (item.isEmpty()) { // 只检查空的格子
				// 产生一个新的激活的物品实例
				Item newItem = Globals.getItemService().newActivatedInstance(owner,
						template, getBagType(), item.getIndex());
				newItem.setModified();
				// 设置物品的绑定状态
				newItem.setBindStatus(bindStatus);
				this.items[newItem.getIndex()] = newItem;
				if (template.getMaxOverlap() >= left) {
					// 可以全部放入
					newItem.changeOverlap(left, reason, reasonDetail, genKey,
							needSendLog);
					left = 0;
					updateList.add(newItem);
					break;
				} else {
					// 只能放一部分
					newItem.changeOverlap(template.getMaxOverlap(), reason,
							reasonDetail, genKey, needSendLog);
					left -= template.getMaxOverlap();
					updateList.add(newItem);
				}
			}
		}
		return left;
	}

	/**
	 * 移除一定数量的某种道具，区分绑定状态，优先删除叠加数少的道具，尽可能使删除完成后产生更多的空槽位 如果 count 为0则抛出
	 * {@link IllegalArgumentException}
	 * 
	 * @param <T>
	 * @param templateId
	 *            模板Id
	 * @param count
	 *            要移除的数量
	 * @param reason
	 *            移除的原因
	 * @param bind
	 *            绑定状态
	 * @return 移除了的Item
	 */
	public Collection<Item> removeItemConsiderBind(String itemSN, int count,
			BindStatus bind, ItemLogReason reason, String detailReason) {
		if (count <= 0) {
			throw new IllegalArgumentException("count must not be 0!");
		}
		int hasCount = getCountConsiderBind(itemSN, bind);
		// 看看够不够删，要么全删，要么不删
		if (count > hasCount) {
			return Collections.emptyList();
		}
		// 筛选出所有符合要求的item
		List<Item> matchItems = getAllConsiderBind(itemSN, bind);
		return directRemove(matchItems, count, reason, detailReason);
	}

	/**
	 * 移除一定数量的某种道具，要么全删，要么不删，不区分绑定状态，优先删除叠加数少的，尽可能使删除完成后产生更多的空槽位 如果 count 为0则抛出
	 * {@link IllegalArgumentException}
	 * 
	 * @param <T>
	 * @param templateId
	 *            模板Id
	 * @param count
	 *            要移除的数量
	 * @param reason
	 *            移除的原因
	 * @param bind
	 *            绑定状态
	 * @return 移除了的Item
	 */
	public Collection<Item> removeItemIgnoreBind(String itemSN, int count,
			ItemLogReason reason, String detailReason) {
		if (count <= 0) {
			throw new IllegalArgumentException("count must not be 0!");
		}
		int hasCount = getCountIgnoreBind(itemSN);
		// 看看够不够删，要么全删，要么不删
		if (count > hasCount) {
			return Collections.emptyList();
		}
		// 筛选出所有符合要求的item
		List<Item> matchItems = getAllIgnoreBind(itemSN);
		return directRemove(matchItems, count, reason, detailReason);
	}

	/**
	 * 从items中删除count个，优先删除叠加数少的，尽可能使删除完成后产生更多的空槽位
	 * 
	 * @return 删除的所有item
	 */
	private Collection<Item> directRemove(List<Item> items, int count,
			ItemLogReason reason, String detailReason) {
		// 按叠加个数从小到大排列，先从叠加小的上面删
		Collections.sort(items, overlapComp);
		List<Item> updateList = new ArrayList<Item>();
		int toDelete = count;
		for (Item item : items) {
			if (toDelete == 0) {
				break;
			}
			if (item.getOverlap() >= toDelete) {
				item.changeOverlap(item.getOverlap() - toDelete, reason, detailReason,
						"", true);
				toDelete = 0;
				updateList.add(item);
			} else {
				toDelete -= item.getOverlap();
				item.delete(reason, "");
				updateList.add(item);
			}
		}
		return updateList;
	}

	/** 叠加数比较器，按叠加从小到大排列 */
	private final Comparator<Item> overlapComp = new Comparator<Item>() {

		@Override
		public int compare(Item itemA, Item itemB) {
			int ovlpA = itemA.getOverlap();
			int ovlpB = itemB.getOverlap();
			if (ovlpA > ovlpB) {
				return 1;
			} else if (ovlpA < ovlpB) {
				return -1;
			} else {
				return 0;
			}
		}
	};

	/**
	 * 拆分道具
	 * 
	 * @param index
	 *            索引
	 * @param count
	 *            拆出来的数量
	 * @return 需要跟新的道具
	 */
	public List<Item> split(int index, final int count) {
		Item item = getByIndex(index);
		if (Item.isEmpty(item)) {
			return Collections.emptyList();
		}
		// 不可以叠加
		if (!item.canOverlap()) {
			return Collections.emptyList();
		}
		// 检查拆出来的个数
		if (count >= item.getOverlap() || count < 1) {
			return Collections.emptyList();
		}
		Item emptyItem = getEmptySlot();
		// 没有地方拆分
		if (emptyItem == null) {
			return Collections.emptyList();
		}
		List<Item> updateList = new ArrayList<Item>();
		// 减少原来的叠加数
		item.changeOverlap(item.getOverlap() - count, ItemLogReason.SPLITTED,
				"", "", true);
		updateList.add(item);

		// 记录道具产出日志
		String genKey = KeyUtil.UUIDKey();
		try {
			Globals.getLogService().sendItemGenLog(owner, ItemGenLogReason.SPLIT, "",
					item.getTemplateId(), item.getName(), count, item.getBindStatus().index, 0, "",
					genKey);
		} catch (Exception e) {
			Loggers.itemLogger.error(LogUtils.buildLogInfoStr(owner.getUUID(),
					"记录道 具产出日志时出错"), e);
		}

		String countChangeReason = " [genReason:" + ItemGenLogReason.SPLIT
				+ "] ";
		// 产生一个新的激活的物品实例
		Item newItem = Globals.getItemService().newActivatedInstance(owner, item
				.getTemplate(), getBagType(), emptyItem.getIndex());
		newItem.setModified();
		// 设置物品的绑定状态
		newItem.setBindStatus(item.getBindStatus());
		this.items[newItem.getIndex()] = newItem;
		newItem.changeOverlap(count, ItemLogReason.COUNT_ADD,
				countChangeReason, genKey, true);
		// 添加到新拆分的物品
		updateList.add(newItem);
		return updateList;
	}


	@Override
	public void onChanged() { 
		super.onChanged();
	}
	
	
}
