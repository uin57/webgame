package com.pwrd.war.gameserver.quest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.Lists;
import com.pwrd.war.common.model.quest.QuestBonusItem;
import com.pwrd.war.common.model.quest.QuestDestInfo;
import com.pwrd.war.common.model.quest.QuestInfo;
import com.pwrd.war.gameserver.behavior.BehaviorTypeEnum;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.quest.IQuest.QuestType;
import com.pwrd.war.gameserver.quest.condition.IQuestCondition;
import com.pwrd.war.gameserver.quest.msg.GCQuestUpdate;
import com.pwrd.war.gameserver.quest.template.QuestTemplate;

public class QuestMessageBuilder {
	
//	public final static Comparator<QuestInfo> questsorter = new QuestInfoSorter();
	
	
	/**
	 * 
	 * 发送成长任务列表消息
	 * 
	 * @param questList
	 * @param owner
	 */
	public void sendQuestListMsg(Human owner) {
		List<QuestInfo> questInfos = Lists.newArrayList();
		
//		questInfos.addAll(owner.getQuestDiary().getCandoQuestInfoList());
//		questInfos.addAll(owner.getQuestDiary().getCommonDoingQuestInfoList());
		
//		Collections.sort(questInfos,questsorter);
	
//		GCCommonQuestList gcCommonQuestList = new GCCommonQuestList();
//		gcCommonQuestList.setQuestInfos(questInfos.toArray(new QuestInfo[0]));
//		owner.sendMessage(gcCommonQuestList);
	}
	
	public void sendQuestUpdateMsg(Human owner,DoingQuest doingQuest)
	{
		QuestInfo questInfo = getQuestInfo(owner,doingQuest);
		GCQuestUpdate gcQuestUpdate = new GCQuestUpdate();
		gcQuestUpdate.setQuestInfo(questInfo);
		owner.sendMessage(gcQuestUpdate);
	}
	
	/**
	 * 根据一个任务
	 * 
	 * @param human
	 * @param commonQuest
	 * @return
	 */
	public static QuestInfo getQuestInfo(Human human,CommonQuest commonQuest)
	{
		QuestInfo result = new QuestInfo();
		
//		QuestTemplate template = commonQuest.getTemplate();
//		
//		result.setQuestId(template.getId());
//		result.setQuestTitle(template.getQuestTitle());
//		result.setDesc(template.getQuestDesc());
//		result.setContent(template.getQuestContent());
//		result.setMoneyType((short)template.getMoneyBonusType());
//		result.setMoneyAmount(template.getMoneyBonusCount());
//
//		result.setDestInfo(getCandoQuestDestInfo(template));
//		result.setItemBonus(getQuestBonusItem(template));
//		
//		boolean canAccept = commonQuest.canAccept(human, false);
//		boolean canFinish = commonQuest.canFinish(human, false);
//		
//		if(canFinish)
//		{
//			result.setFinishBtnEnable(true);
//			result.setAcceptBtnEnable(false);
//			result.setCancelBtnEnable(false);
//		}
//		else
//		{
//			if(canAccept)
//			{
//				result.setAcceptBtnEnable(true);
//				result.setFinishBtnEnable(false);				
//				result.setCancelBtnEnable(false);
//			}
//			else
//			{
//				result.setAcceptBtnEnable(false);
//				result.setFinishBtnEnable(false);				
//				result.setCancelBtnEnable(false);
//			}
//		}
		
		return result;
	}
		
	public static QuestInfo getQuestInfo(Human owner,DoingQuest doingQuest)
	{
		QuestInfo result = new QuestInfo();
//		QuestTemplate template = doingQuest.getTemplate();
//		
//		result.setQuestId(template.getId());
//		result.setQuestTitle(template.getQuestTitle());
//		result.setDesc(template.getQuestDesc());
//		result.setContent(template.getQuestContent());
//		result.setMoneyType((short)template.getMoneyBonusType());
//		result.setMoneyAmount(template.getMoneyBonusCount());
//		
//		result.setDestInfo(getDoingQuestDestInfo(template,owner));
//		result.setItemBonus(getQuestBonusItem(template));
//		
//		boolean isQuestFinish = owner.getQuestDiary().isQuestFinish(template.getId());
//		if(isQuestFinish)
//		{
//			result.setFinishBtnEnable(true);
//			result.setAcceptBtnEnable(false);
//			result.setCancelBtnEnable(false);
//		}
//		else
//		{
//			result.setFinishBtnEnable(false);
//			result.setAcceptBtnEnable(false);
//			result.setCancelBtnEnable(true);
//		}
		
		return result;		
	}	
	
	/**
	 * 得到物品消息数组
	 * 
	 * @param itemsHolders
	 * @return
	 */
	public static QuestBonusItem[] getQuestBonusItem(QuestTemplate questTemplate) {
//		List<ItemParam> itemsParams = questTemplate.getItemBonusList();		
		
		List<QuestBonusItem> items = new ArrayList<QuestBonusItem>();
//		for (ItemParam item : itemsParams) {
//			// 如果没有则不做为奖励
//			if (item.getTemplateId() == 0) {
//				continue;
//			}
//
//			ItemTemplate tmpl = Globals.getTemplateService().get(item.getTemplateId(), ItemTemplate.class);
//
//			QuestBonusItem qbi = new QuestBonusItem();
//			qbi.setName(tmpl.getName());
//			qbi.setCount((short)item.getCount());
//			qbi.setRarity((short)tmpl.getRarity().getIndex());	
//			
//			items.add(qbi);
//		}
		return items.toArray(new QuestBonusItem[items.size()]);
	}
	
	/**
	 * 得到正在做的任务目标消息数组
	 * 
	 * @param dests
	 * @param human
	 * @param questId
	 * @return
	 */
	public static QuestDestInfo[] getDoingQuestDestInfo(QuestTemplate questTemplate, Human human) {
		
//		int questId = questTemplate.getId();		
//		List<IQuestCondition> dests = questTemplate.getFinishConditionList();
		
		List<QuestDestInfo> destInfos = new ArrayList<QuestDestInfo>();
		//TODO
//		for (IQuestCondition iq : dests) {
//			short _num = (short) human.getQuestDiary().getFinishedCount(questId, iq.getDestType(), iq.getInstKey());
//
//			// 如果接任务就完成，则设置接任务为完成的数量为所需数量
//			if (iq.evaluate(human,null)) {
//				_num = (short) iq.getRequiredNum();
//			}
//
//			QuestDestInfo dest = new QuestDestInfo((short) (iq.getDestType().getType()), "", 
//					(short) iq.getRequiredNum(), _num);
//
//			destInfos.add(dest);
//		}
		return destInfos.toArray(new QuestDestInfo[destInfos.size()]);
	}
	
	/**
	 * 得到可接任务目标消息数组
	 * 
	 * @param dests
	 * @return
	 */
	public static QuestDestInfo[] getCandoQuestDestInfo(QuestTemplate questTemplate) {
		//TODO
//		List<IQuestDestination> dests = questTemplate.getQuestDestList();
		List<QuestDestInfo> destInfos = new ArrayList<QuestDestInfo>();
//		for (IQuestDestination iq : dests) {
//			QuestDestInfo dest = new QuestDestInfo((short) (iq.getDestType().getType()), "", 
//					(short) iq.getRequiredNum(), (short) 0);
//			destInfos.add(dest);
//		}
		return destInfos.toArray(new QuestDestInfo[destInfos.size()]);
	}
	
	

	
//	private static class QuestInfoSorter implements Comparator<QuestInfo> {
//		@Override
//		public int compare(QuestInfo o1, QuestInfo o2) {
//			int questId1 = o1.getQuestId();
//			int questId2 = o2.getQuestId();
//			return questId1 - questId2;
//		}		
//	}
	

}
