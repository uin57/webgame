package com.pwrd.war.gameserver.item.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pwrd.war.core.util.Assert;
import com.pwrd.war.db.model.ItemEntity;
import com.pwrd.war.gameserver.common.container.Bag;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.Item;

/**
 * 固定容量的道具容器
 * 
 * 
 */
public abstract class AbstractItemBag implements Bag<Item, Human> {
	/** 容器中的所有道具实例 */
	protected Item[] items;
	/** 容器的所有者 */
	protected final Human owner;
	/** 容器ID */
	private final BagType bagType;


	/**
	 * 初始化容器,依据capacity生成同等数量的道具实例,此时道具都是空的
	 * 
	 * @param owner
	 *            所有者
	 * @param bagId
	 *            包的id
	 * @param capacity
	 *            初始容量
	 */
	protected AbstractItemBag(Human owner, BagType bagType, int capacity) {
		this.bagType = bagType;
		this.owner = owner;
		Assert.notNull(owner);
		Assert.isTrue(capacity >= 0, "容量不能为负 :" + capacity);
		items = new Item[capacity];
		for (int i = 0; i < capacity; i++) {
			// 填满空的item
			Item item = Item.newEmptyOwneredInstance(owner, getBagType(), i);
			items[i] = item;
		}
	}

	/**
	 * 添加道具实例，注意，此方法同样不关心业务逻辑
	 * 
	 * @param item
	 */
	public final void putItem(Item item) {
		Assert.notNull(item);
		if (checkIndex(item.getIndex())) {
			items[item.getIndex()] = item;
		}
	}

	/**
	 * 将包中所有Item转成ItemEntity并返回列表
	 * 
	 * @return
	 */
	public List<ItemEntity> toItemEntitys() {
		List<ItemEntity> entities = new ArrayList<ItemEntity>();
		for (Item item : items) {
			if (!Item.isEmpty(item)) {
				entities.add(item.toEntity());
			} else if (item != null && item.isInDb()
					&& item.getLifeCycle().isDestroyed()) {
				entities.add(item.toEntity());
			}
		}
		return entities;
	}

	@Override
	public final Item getByIndex(int index) {
		if (checkIndex(index)) {
			return items[index];
		} else {
			return null;
		}
	}

	/**
	 * 检查一个索引是否在本背包的索引允许范围内
	 * 
	 * @param index
	 * @return 如果合法，返回true，否则返回false
	 */
	public final boolean checkIndex(int index) {
		return index < items.length && index >= 0;
	}

	@Override
	public final int getCapacity() {
		return items.length;
	}

	@Override
	public void onChanged() {		
	}

	@Override
	public void onLoad() {
	}

	/**
	 * 根据道具模板的ID找到所有的道具
	 * 
	 * @param templateId
	 * @return
	 */
	public final List<Item> getAllIgnoreBind(String itemSN) {
		List<Item> resultList = new ArrayList<Item>();
		findByTmplId(itemSN, resultList);
		return resultList;
	}

	/**
	 * 根据templateId和绑定状态查询所有item
	 * 
	 * @param tempalteId
	 * @param bind
	 * @return
	 */
	public final List<Item> getAllConsiderBind(String itemSN, BindStatus bind) {
		List<Item> resultList = new ArrayList<Item>();
		for (Item item : items) {
			if (Item.isSameTemplate(itemSN, item)
					&& item.getBindStatus() == bind) {
				resultList.add(item);
			}
		}
		return resultList;
	}

	/**
	 * 根据道具模板的ID找所有的道具,并将找到的结果填入到指定的结果集中<code>resultList</code>，不考虑绑定
	 * 
	 * @param templateId
	 * @param result
	 */
	public final void getAllByTemplateId(String itemSN, Collection<Item> result) {
		Assert.notNull(result);
		findByTmplId(itemSN, result);
	}

	/**
	 * 通过UUID查询道具实例
	 * 
	 * @param UUID
	 * @return
	 */
	public final Item getByUUID(String UUID) {
		for (Item item : items) {
			// 格子中没有物品
			if (Item.isEmpty(item)) {
				continue;
			} 
			if (item.getUUID().equals(UUID)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 根据sn取得所有item对象
	 * @author xf
	 */
	public final Item[] getBySn(String itemSN) {
		List<Item> lsit = new ArrayList<Item>();
		for (Item item : items) {
			// 格子中没有物品
			if (Item.isEmpty(item)) {
				continue;
			} 
			if (item.getTemplate().getItemSn().equals(itemSN)) {
				lsit.add(item);
			}
		}
		return lsit.toArray(new Item[0]);
	}
	
	@Override
	public final Human getOwner() {
		return owner;
	}

	@Override
	public final BagType getBagType() {
		return bagType;
	}

	/**
	 * 返回所有非空道具的列表
	 * 
	 * @return
	 */
	public final Collection<Item> getAll() {
		List<Item> itemList = new ArrayList<Item>();
		for (Item item : items) {
			if (!Item.isEmpty(item)) {
				itemList.add(item);
			}
		}
		return itemList;
	}

	/**
	 * 取得指定道具在背包中的数量,不区分绑定状态
	 * 
	 * @param templateId
	 * @return
	 */
	public final int getCountIgnoreBind(String itemSN) {
		int count = 0;
		for (Item item : items) {
			if (Item.isSameTemplate(itemSN, item)) {
				count += item.getOverlap();
			}
		}
		return count;
	}

	/**
	 * 获得相同绑定状态和模板ID的道具数量,区分绑定状态
	 * 
	 * @param templateId
	 * @param bindStatus
	 * @return
	 */
	public final int getCountConsiderBind(String itemSN,
			Bindable.BindStatus bindStatus) {
		int count = 0;
		for (Item item : items) {
			if (Item.isSameTemplate(itemSN, item)
					&& item.getBindStatus() == bindStatus) {
				count += item.getOverlap();
			}
		}
		return count;
	}
	
	/**
	 * 取得指定道具在背包中的数量,不区分绑定状态
	 * @author xf
	 */
	public final int getCountByItemSN(String itemSN){
		int count = 0;
		for (Item item : items) {
			if (Item.isSameTemplate(itemSN, item) ) {
				count += item.getOverlap();
			}
		}
		return count;
	}
	
	/**
	 * 根据道具模板ID查找道具,并将找到的结果加入到resultList中
	 * 
	 * @param templateId
	 * @param resultList
	 */
	private void findByTmplId(String itemSN, Collection<Item> resultList) {
		for (Item item : items) {
			if (Item.isSameTemplate (itemSN, item)) {
				resultList.add(item);
			}
		}
	}

	public void activeAll() {
		for (Item item : items) {
			item.getLifeCycle().activate();
		}
	}

	/**
	 * 判断一个背包是不是主背包
	 * 
	 * @param bag
	 *            背包Id
	 * @return
	 */
	public static boolean isPrimBag(BagType bag) {
		if (bag == BagType.PRIM) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断是否为一个无效包
	 * 
	 * @param bag
	 * @return
	 */
	public static boolean isNullBag(BagType bag) {
		if (bag == null || bag == BagType.NULL) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 取得背包中一个可用的空格
	 * 
	 * @return 如果找不到return null
	 */
	public Item getEmptySlot() {
		for (Item item : items) {
			if (item.isEmpty()) {
				return item;
			}
		}
		return null;
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
	

}
