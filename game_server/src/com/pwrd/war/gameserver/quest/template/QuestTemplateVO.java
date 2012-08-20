package com.pwrd.war.gameserver.quest.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import java.util.List;
import com.pwrd.war.core.template.ExcelCollectionMapping;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 任务模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class QuestTemplateVO extends TemplateObject {

	/** 任务id */
	@ExcelCellBinding(offset = 1)
	protected int questId;

	/** 任务名 */
	@ExcelCellBinding(offset = 2)
	protected String questTitle;

	/** 任务概述 */
	@ExcelCellBinding(offset = 3)
	protected String questDesc;

	/** 任务内容 */
	@ExcelCellBinding(offset = 4)
	protected String questContent;

	/** 任务主类型（主线为1，支线为2，日常为5，周常为6，帮会为10，国家为11） */
	@ExcelCellBinding(offset = 5)
	protected int questType;

	/** 任务子类型 */
	@ExcelCellBinding(offset = 6)
	protected int questSubType;

	/** 任务区域 */
	@ExcelCellBinding(offset = 7)
	protected int questRegion;

	/** 是否可见 */
	@ExcelCellBinding(offset = 8)
	protected boolean canVisible;

	/** 能否放弃 */
	@ExcelCellBinding(offset = 9)
	protected boolean canGiveup;

	/** 前置任务编号列表（多个任务用逗号分开） */
	@ExcelCellBinding(offset = 10)
	protected String preQuestIds;

	/** 接受任务NPC编号 */
	@ExcelCellBinding(offset = 11)
	protected int acceptNpc;

	/** 完成任务NPC编号 */
	@ExcelCellBinding(offset = 12)
	protected int finishNpc;

	/** 接受任务对白 */
	@ExcelCellBinding(offset = 13)
	protected String acceptTalk;

	/** 接受任务最小等级 */
	@ExcelCellBinding(offset = 14)
	protected int acceptMinLevel;

	/** 接受任务特殊条件（最多3个） */
	@ExcelCollectionMapping(clazz = com.pwrd.war.gameserver.quest.template.SpecialCondition.class, collectionNumber = "15,16,17;18,19,20;21,22,23")
	protected List<com.pwrd.war.gameserver.quest.template.SpecialCondition> acceptCondition;

	/** 完成任务对白 */
	@ExcelCellBinding(offset = 24)
	protected String finishTalk;

	/** 完成任务描述 */
	@ExcelCellBinding(offset = 25)
	protected String finishDesc;

	/** 完成任务最小等级 */
	@ExcelCellBinding(offset = 26)
	protected int finishMinLevel;

	/** 完成任务特殊条件（最多3个） */
	@ExcelCollectionMapping(clazz = com.pwrd.war.gameserver.quest.template.SpecialCondition.class, collectionNumber = "27,28,29;30,31,32;33,34,35")
	protected List<com.pwrd.war.gameserver.quest.template.SpecialCondition> finishCondition;

	/** 金钱奖励类型 */
	@ExcelCellBinding(offset = 36)
	protected int moneyBonusType;

	/** 金钱奖励数量 */
	@ExcelCellBinding(offset = 37)
	protected int moneyBonusCount;

	/** 经验奖励数量 */
	@ExcelCellBinding(offset = 38)
	protected int expBonusCount;

	/** 物品奖励（物品id:数量,...） */
	@ExcelCellBinding(offset = 39)
	protected String itemBonus;

	/** 特殊奖励（最多3个） */
	@ExcelCollectionMapping(clazz = com.pwrd.war.gameserver.quest.template.SpecialBonus.class, collectionNumber = "40,41,42;43,44,45;46,47,48")
	protected List<com.pwrd.war.gameserver.quest.template.SpecialBonus> specialBonus;

	/** 副本id */
	@ExcelCellBinding(offset = 52)
	protected String repId;


	public int getQuestId() {
		return this.questId;
	}

	public void setQuestId(int questId) {
		if (questId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[任务id]questId不可以为0");
		}
		this.questId = questId;
	}
	
	public String getQuestTitle() {
		return this.questTitle;
	}

	public void setQuestTitle(String questTitle) {
		if (StringUtils.isEmpty(questTitle)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[任务名]questTitle不可以为空");
		}
		this.questTitle = questTitle;
	}
	
	public String getQuestDesc() {
		return this.questDesc;
	}

	public void setQuestDesc(String questDesc) {
		if (StringUtils.isEmpty(questDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[任务概述]questDesc不可以为空");
		}
		this.questDesc = questDesc;
	}
	
	public String getQuestContent() {
		return this.questContent;
	}

	public void setQuestContent(String questContent) {
		if (StringUtils.isEmpty(questContent)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[任务内容]questContent不可以为空");
		}
		this.questContent = questContent;
	}
	
	public int getQuestType() {
		return this.questType;
	}

	public void setQuestType(int questType) {
		if (questType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[任务主类型（主线为1，支线为2，日常为5，周常为6，帮会为10，国家为11）]questType不可以为0");
		}
		if (questType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[任务主类型（主线为1，支线为2，日常为5，周常为6，帮会为10，国家为11）]questType的值不得小于1");
		}
		this.questType = questType;
	}
	
	public int getQuestSubType() {
		return this.questSubType;
	}

	public void setQuestSubType(int questSubType) {
		this.questSubType = questSubType;
	}
	
	public int getQuestRegion() {
		return this.questRegion;
	}

	public void setQuestRegion(int questRegion) {
		this.questRegion = questRegion;
	}
	
	public boolean isCanVisible() {
		return this.canVisible;
	}

	public void setCanVisible(boolean canVisible) {
		this.canVisible = canVisible;
	}
	
	public boolean isCanGiveup() {
		return this.canGiveup;
	}

	public void setCanGiveup(boolean canGiveup) {
		this.canGiveup = canGiveup;
	}
	
	public String getPreQuestIds() {
		return this.preQuestIds;
	}

	public void setPreQuestIds(String preQuestIds) {
		this.preQuestIds = preQuestIds;
	}
	
	public int getAcceptNpc() {
		return this.acceptNpc;
	}

	public void setAcceptNpc(int acceptNpc) {
		this.acceptNpc = acceptNpc;
	}
	
	public int getFinishNpc() {
		return this.finishNpc;
	}

	public void setFinishNpc(int finishNpc) {
		this.finishNpc = finishNpc;
	}
	
	public String getAcceptTalk() {
		return this.acceptTalk;
	}

	public void setAcceptTalk(String acceptTalk) {
		this.acceptTalk = acceptTalk;
	}
	
	public int getAcceptMinLevel() {
		return this.acceptMinLevel;
	}

	public void setAcceptMinLevel(int acceptMinLevel) {
		this.acceptMinLevel = acceptMinLevel;
	}
	
	public List<com.pwrd.war.gameserver.quest.template.SpecialCondition> getAcceptCondition() {
		return this.acceptCondition;
	}

	public void setAcceptCondition(List<com.pwrd.war.gameserver.quest.template.SpecialCondition> acceptCondition) {
		if (acceptCondition == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[接受任务特殊条件（最多3个）]acceptCondition不可以为空");
		}	
		this.acceptCondition = acceptCondition;
	}
	
	public String getFinishTalk() {
		return this.finishTalk;
	}

	public void setFinishTalk(String finishTalk) {
		this.finishTalk = finishTalk;
	}
	
	public String getFinishDesc() {
		return this.finishDesc;
	}

	public void setFinishDesc(String finishDesc) {
		this.finishDesc = finishDesc;
	}
	
	public int getFinishMinLevel() {
		return this.finishMinLevel;
	}

	public void setFinishMinLevel(int finishMinLevel) {
		this.finishMinLevel = finishMinLevel;
	}
	
	public List<com.pwrd.war.gameserver.quest.template.SpecialCondition> getFinishCondition() {
		return this.finishCondition;
	}

	public void setFinishCondition(List<com.pwrd.war.gameserver.quest.template.SpecialCondition> finishCondition) {
		if (finishCondition == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					28, "[完成任务特殊条件（最多3个）]finishCondition不可以为空");
		}	
		this.finishCondition = finishCondition;
	}
	
	public int getMoneyBonusType() {
		return this.moneyBonusType;
	}

	public void setMoneyBonusType(int moneyBonusType) {
		this.moneyBonusType = moneyBonusType;
	}
	
	public int getMoneyBonusCount() {
		return this.moneyBonusCount;
	}

	public void setMoneyBonusCount(int moneyBonusCount) {
		this.moneyBonusCount = moneyBonusCount;
	}
	
	public int getExpBonusCount() {
		return this.expBonusCount;
	}

	public void setExpBonusCount(int expBonusCount) {
		this.expBonusCount = expBonusCount;
	}
	
	public String getItemBonus() {
		return this.itemBonus;
	}

	public void setItemBonus(String itemBonus) {
		this.itemBonus = itemBonus;
	}
	
	public List<com.pwrd.war.gameserver.quest.template.SpecialBonus> getSpecialBonus() {
		return this.specialBonus;
	}

	public void setSpecialBonus(List<com.pwrd.war.gameserver.quest.template.SpecialBonus> specialBonus) {
		if (specialBonus == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					41, "[特殊奖励（最多3个）]specialBonus不可以为空");
		}	
		this.specialBonus = specialBonus;
	}
	
	public String getRepId() {
		return this.repId;
	}

	public void setRepId(String repId) {
		this.repId = repId;
	}
	

	@Override
	public String toString() {
		return "QuestTemplateVO[questId=" + questId + ",questTitle=" + questTitle + ",questDesc=" + questDesc + ",questContent=" + questContent + ",questType=" + questType + ",questSubType=" + questSubType + ",questRegion=" + questRegion + ",canVisible=" + canVisible + ",canGiveup=" + canGiveup + ",preQuestIds=" + preQuestIds + ",acceptNpc=" + acceptNpc + ",finishNpc=" + finishNpc + ",acceptTalk=" + acceptTalk + ",acceptMinLevel=" + acceptMinLevel + ",acceptCondition=" + acceptCondition + ",finishTalk=" + finishTalk + ",finishDesc=" + finishDesc + ",finishMinLevel=" + finishMinLevel + ",finishCondition=" + finishCondition + ",moneyBonusType=" + moneyBonusType + ",moneyBonusCount=" + moneyBonusCount + ",expBonusCount=" + expBonusCount + ",itemBonus=" + itemBonus + ",specialBonus=" + specialBonus + ",repId=" + repId + ",]";

	}
}