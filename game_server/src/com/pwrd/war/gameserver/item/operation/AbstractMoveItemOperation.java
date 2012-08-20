package com.pwrd.war.gameserver.item.operation;

import com.pwrd.war.core.util.Assert;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.container.AbstractItemBag;
import com.pwrd.war.gameserver.pet.Pet;

/**
 * MoveItemOperation的骨架实现
 * 
 * 
 */
public abstract class AbstractMoveItemOperation implements MoveItemOperation {
	
	@Override
	public final boolean move(Human user, Item from, Item to) {
		Assert.notNull(user);
		if (Item.isEmpty(from) || from.getBagType() == null || from.getBagType() == BagType.NULL) {
			// 目标可以为空，但源不能为空，也不能不属于任何包裹，否则移动什么
			return false;
		}
		if (to == null) {
			return false;
		}
		if (isAtSameSlot(from, to)) {
			// 根本没有移动
			return false;
		}
//		if (from.isLocked() || to.isLocked()) {
//			// 任一个被锁定，不可以移动
//			return false;
//		}
		BagType fromBag = from.getBagType();
		BagType toBag = to.getBagType();
		// 不能从NULL背包中移出东西，也不能往NULL背包中塞东西
		if (fromBag == BagType.NULL || toBag == BagType.NULL) {
			return false;
		}
		if (isSuiltable(fromBag, toBag)) {
			return moveImpl(user, from, to);
		} else {
			return false;
		}
	}
	
	@Override
	public boolean move(Human user, Pet wearer, Item from, Item to) {
		Assert.notNull(user);
		Assert.notNull(wearer);
		if (Item.isEmpty(from) || from.getBagType() == null || from.getBagType() == BagType.NULL) {
			// 目标可以为空，但源不能为空，也不能不属于任何包裹，否则移动什么
			return false;
		}
		if (to == null) {
			return false;
		}
		if (isAtSameSlot(from, to)) {
			// 根本没有移动
			return false;
		}
//		if (from.isLocked() || to.isLocked()) {
//			// 任一个被锁定，不可以移动
//			return false;
//		}
		BagType fromBag = from.getBagType();
		BagType toBag = to.getBagType();
		// 不能从NULL背包中移出东西，也不能往NULL背包中塞东西
		if (fromBag == BagType.NULL || toBag == BagType.NULL) {
			return false;
		}
		if (isSuiltable(fromBag, toBag)) {
			return moveImpl(user, wearer, from, to);
		} else {
			return false;
		}
	}



	private boolean isAtSameSlot(Item fromItem, Item toItem) {
		return fromItem.getBagType() == toItem.getBagType() && fromItem.getIndex() == toItem.getIndex();
	}
	
	/**
	 * 子类实现，用于检测本service是否适用于从fromBag移动到toBag
	 * 
	 * @param user
	 * @param from
	 * @param to
	 * @return
	 */
	protected abstract boolean isSuiltable(BagType fromBag, BagType toBag);

	/**
	 * 子类实现具体的移动逻辑，父类保证传入的源道具一定是正常的，不会是null，目标道具可能是只有背包Id的空道具，但不会为null
	 * 
	 * @param user
	 * @param fromItem
	 * @param toItem
	 * @return
	 */
	protected abstract boolean moveImpl(Human user, Item fromItem, Item toItem);
	
	/**
	 * 子类实现具体的移动逻辑，父类保证传入的源道具一定是正常的，不会是null，目标道具可能是只有背包Id的空道具，但不会为null
	 * 
	 * @param user
	 * @param fromItem
	 * @param toItem
	 * @return
	 */
	protected abstract boolean moveImpl(Human user,Pet wearer, Item fromItem, Item toItem);
	

	/**
	 * 交换两个Item，用来辅助各种移动操作
	 * Human所属背包之间的道具交换的基本实现
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	protected boolean swapItem(Item from, Item to) {
		Human fromOwner = from.getOwner();
		AbstractItemBag fromBag = fromOwner.getInventory().getBagByType(from.getBagType(),from.getWearerId());//应该强制wearerId都是0l
		Human toOwner = to.getOwner();
		AbstractItemBag toBag = toOwner.getInventory().getBagByType(to.getBagType(),to.getWearerId());
		if (fromBag == null || toBag == null) {
			return false;
		}
		BagType fromBagType = fromBag.getBagType();
		int fromIndex = from.getIndex();
		BagType toBagType = toBag.getBagType();
		int toIndex = to.getIndex();
		if (fromBagType == toBagType && fromIndex == toIndex) {
			// 根本没动，不需要交换
			return false;
		}
		if (fromOwner == toOwner) {
			// 自己跟自己交换
			from.setBagType(toBagType);
			from.setIndex(toIndex);
			toBag.putItem(from);

			to.setBagType(fromBagType);
			to.setIndex(fromIndex);
			fromBag.putItem(to);
			// 通知两个包裹改变
			fromBag.onChanged();
			
			if (toBag != fromBag) {
				toBag.onChanged();
			}
			return true;
		} else {
			// 跟别人交换，交易的情况，不归这里管
			return false;
		}
	}
	
	protected boolean swapItem(Item from, Item to,AbstractItemBag fromBag, AbstractItemBag toBag) {
		Human fromOwner = from.getOwner();
		Human toOwner = to.getOwner();
		if (fromBag == null || toBag == null) {
			return false;
		}
		BagType fromBagType = fromBag.getBagType();
		int fromIndex = from.getIndex();

		BagType toBagType = toBag.getBagType();
		int toIndex = to.getIndex();
		
		if (fromBagType == toBagType && fromIndex == toIndex) {
			// 根本没动，不需要交换
			return false;
		}
		if (fromOwner == toOwner) {
			// 自己跟自己交换
			from.setBagType(toBagType);
			from.setIndex(toIndex);
			toBag.putItem(from);

			to.setBagType(fromBagType);
			to.setIndex(fromIndex);
			fromBag.putItem(to);
			// 通知两个包裹改变
			fromBag.onChanged();
			if (toBag != fromBag) {
				toBag.onChanged();
			}
			return true;
		} else {
			// 跟别人交换，交易的情况，不归这里管
			return false;
		}
	}
	
}
