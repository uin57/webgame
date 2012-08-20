package com.pwrd.war.gameserver.item;

import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatTask;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.container.AbstractItemBag;

/**
 * 用于扫描道具是否过期，并做相应处理
 * 
 * 
 */
public class ItemExpireProcesser implements HeartbeatTask {

	/** 检查道具是否过期的时间间隔，40分钟 */
	private static final long CHECK_EXPIRED_SPAN = 40 * TimeUtils.MIN; 
	private boolean isCanceled;
	private Inventory inventory;
	private Human owner;

	public ItemExpireProcesser(Inventory inventory) {
		this.inventory = inventory;
		this.owner = inventory.getOwner();
		this.isCanceled = false;
	}

	@Override
	public void run() {
		if (isCanceled) {
			return;
		}
		scan(inventory.getPrimBag());
	}

	/**
	 * @param bag
	 */
	private void scan(AbstractItemBag bag) {
//		Collection<Item> items = bag.getAll();
//		for (Item item : items) {
//			if (!item.isExpired()) {
//				continue;
//			}
//			if (item.isLocked()) {
//				// 道具被锁定，可能时处于交易、打造、摆摊等特殊状态，为防止出现为止问题，延迟删除
//				if (Loggers.itemLogger.isDebugEnabled()) {
//					Loggers.itemLogger.debug("道具被锁定，延迟删除 item=" + item);
//				}
//				continue;
//			}
//			// 删除道具
//			String name = item.getName();
//			item.delete(ItemLogReason.EXPIRED, "");
//			bag.onChanged();
//			owner.sendMessage(item.getUpdateMsgAndResetModify());
//			owner.sendSystemMessage(LangConstants.ITEM_DELETE_SINCE_EXPIRED, name);
//		}
	}

	@Override
	public long getRunTimeSpan() {
		return CHECK_EXPIRED_SPAN;
	}

	@Override
	public void cancel() {
		isCanceled = true;
	}
}
