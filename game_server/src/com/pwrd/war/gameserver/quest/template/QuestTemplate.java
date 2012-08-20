package com.pwrd.war.gameserver.quest.template;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.quest.IQuest.QuestSubType;
import com.pwrd.war.gameserver.quest.IQuest.QuestType;
import com.pwrd.war.gameserver.quest.bonus.IQuestBonus;
import com.pwrd.war.gameserver.quest.condition.IQuestCondition;

/**
 * 任务模板，数据来源于配置表，表示一个任务所有相关的数据
 * 
 * 
 */
@ExcelRowBinding
public class QuestTemplate extends QuestTemplateVO {

	/** 金钱奖励类型 */
	private Currency currency;
	
	/** 任务类型 */
	private QuestType questTypeEnum;
	
	/** 任务子类型 */
	private QuestSubType questSubTypeEnum;

	/** 任务目标列表 */
	private List<IQuestCondition> finishConditionList = new ArrayList<IQuestCondition>();

	/** 接任务条件列表 */
	private List<IQuestCondition> acceptConditionList = new ArrayList<IQuestCondition>();

	/** 任务物品奖励 */
	private List<ItemParam> itemBonusList = new ArrayList<ItemParam>();
	
	/** 任务特殊奖励 */
	private List<IQuestBonus> specialBonusList = new ArrayList<IQuestBonus>();

	@Override
	public void check() throws TemplateConfigException {
		// 验证金钱类型
		currency = Currency.valueOf(moneyBonusType);
		if (moneyBonusCount > 0 && (currency == null || currency == Currency.NULL)) {
			throw new TemplateConfigException("主任务表", id, "奖励金钱类型错误："
					+ moneyBonusType);
		}
		
		questTypeEnum = QuestType.indexOf(super.questType);
		if (questTypeEnum == null) {
			throw new TemplateConfigException("主任务表", id, "任务类型类型错误："
					+ super.questType);
		}
		
		questSubTypeEnum = QuestSubType.indexOf(super.questSubType);
		if (questSubTypeEnum == null)
		{
			throw new TemplateConfigException("主任务表", id, "任务子类型类型错误："
					+ super.questSubType);
		}

	}

	@Override
	public void patchUp() {
		//组装任务接受条件，并检查参数是否正确
		try {
			for (SpecialCondition cond : acceptCondition) {
				IQuestCondition questCondition = cond.buildQuestCondition(id);
				if (questCondition != null) {
					acceptConditionList.add(questCondition);
				}
			}
		} catch (Exception ex) {
			throw new TemplateConfigException("主任务表", id, ex.toString());
		}

		//组装任务目标，并检查参数是否正确
		try {
			for (SpecialCondition cond : finishCondition) {
				IQuestCondition questCondition = cond.buildQuestCondition(id);
				if (questCondition != null) {
					finishConditionList.add(questCondition);
				}
			}
		} catch (Exception ex) {
			throw new TemplateConfigException("主任务表", id, ex.toString());
		}

		//组装任务物品奖励奖励，并检查参数是否正确，格式为"物品id:数量,..."
		try {
			if (StringUtils.isNotEmpty(itemBonus)) {
				String[] items = itemBonus.split(",");
				for (String item : items) {
					if (StringUtils.isNotEmpty(item)) {
						String[] infos = item.split(":");
						if (infos.length > 1) {
							int count = Integer.parseInt(infos[1]);
							if (count > 0) {
								itemBonusList.add(new ItemParam(infos[0], count, BindStatus.BIND_YET));
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new TemplateConfigException("主任务表", id, ex.toString());
		}
		
		//组装任务特殊奖励，并检查参数是否正确
		try {
			for (SpecialBonus bonus : specialBonus) {
				IQuestBonus questBonus = bonus.buildQuestBonus(id);
				if (questBonus != null) {
					specialBonusList.add(questBonus);
				}
			}
		} catch (Exception ex) {
			throw new TemplateConfigException("主任务表", id, ex.toString());
		}
	}

	public Currency getCurrency() {
		return currency;
	}

	public List<IQuestCondition> getFinishConditionList() {
		return finishConditionList;
	}

	public List<IQuestCondition> getAcceptConditionList() {
		return acceptConditionList;
	}

	public List<IQuestBonus> getSpecialBonusList() {
		return specialBonusList;
	}
	
	public List<ItemParam> getItemBonusList() {
		return itemBonusList;
	}

	public QuestType getQuestTypeEnum() {
		return questTypeEnum;
	}

	public QuestSubType getQuestSubTypeEnum() {
		return questSubTypeEnum;
	}

	@Override
	public String toString() {
		return "QuestTemplate [id=" + id + ", title=" + questTitle + "]";
	}


}
