package com.pwrd.war.gameserver.item;

import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatTask;
import com.pwrd.war.gameserver.item.container.AbstractItemBag;

/**
 * 用于扫描道具是否取消绑定，并做相应处理
 */
public class ItemFreezeProcesser implements HeartbeatTask {

	/** 检查道具是否取消绑定的时间间隔，5分钟 */
	private static final long CHECK_FREEZE_SPAN = 5 * TimeUtils.MIN;
	private boolean isCanceled;
	private Inventory inventory;

	public ItemFreezeProcesser(Inventory inventory) {
		this.inventory = inventory;
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
		inventory.synBagFreezeItem(bag);
	}

	@Override
	public long getRunTimeSpan() {
		return CHECK_FREEZE_SPAN;
	}

	@Override
	public void cancel() {
		isCanceled = true;
	}
}
