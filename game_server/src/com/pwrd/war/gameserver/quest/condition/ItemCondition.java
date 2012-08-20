package com.pwrd.war.gameserver.quest.condition;

import com.pwrd.war.common.model.quest.QuestDestInfo;
import com.pwrd.war.gameserver.common.i18n.constants.QuestLangConstants_40000;
import com.pwrd.war.gameserver.human.Human;

/**
 * 物品数量条件
 * @author zy
 */
public class ItemCondition implements IQuestCondition {
	private String itemSn;
	private int count;
	
	public ItemCondition(String itemSn, int count) {
		this.itemSn = itemSn;
		this.count = count;
	}

	@Override
	public boolean isMeet(Human human, int questId, boolean showError) {
		//直接从角色身上获取物品数量进行判断
		int amount = human.getInventory().getPrimBag().getCountByItemSN(itemSn);
		boolean result = (amount >= count);
		if (showError && !result) {
			human.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_NO_ENOUGH_ITEM);
		}
		return result;
	}

	@Override
	public void onAccept(Human human) {
	}

	@Override
	public void onFinish(Human human) {
	}

	@Override
	public int initParam(Human human) {
		return 0;
	}

	@Override
	public QuestConditionType getType() {
		return QuestConditionType.ITEM;
	}

	@Override
	public String getParam() {
		return itemSn;
	}

	@Override
	public QuestDestInfo getDestInfo(Human human, int questId) {
		int amount = human.getInventory().getPrimBag().getCountByItemSN(itemSn);
		return new QuestDestInfo((short)QuestConditionType.ITEM.getIndex(), itemSn, (short)count, (short)amount);
	}

}
