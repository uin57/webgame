package com.pwrd.war.gameserver.dayTask;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.i18n.constants.QuestLangConstants_40000;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.dayTask.msg.GCDayTaskComplete;
import com.pwrd.war.gameserver.dayTask.template.DayTaskTemplate;
import com.pwrd.war.gameserver.dayTask.template.TaskProbabilityTemplate;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;

public class DayTaskService {
	private Map<Integer, DayTaskTemplate>  dayMap = new HashMap<Integer, DayTaskTemplate>();
	private Map<Integer, TaskProbabilityTemplate>  probabilyMap = new HashMap<Integer, TaskProbabilityTemplate>();
	
	
	public DayTaskService(){
		dayMap = Globals.getTemplateService().getAll(DayTaskTemplate.class);
		probabilyMap = Globals.getTemplateService().getAll(TaskProbabilityTemplate.class);
	}
	
	/**
	 * 根据用户等级得到用户的15个任务集
	 * @param level
	 * @return
	 */
	public Map<Integer, TaskProbabilityTemplate> getTaskProbabilityTemplateByLevel(int level){
		Map<Integer, TaskProbabilityTemplate> map = new HashMap<Integer, TaskProbabilityTemplate>();
		for(Map.Entry<Integer, TaskProbabilityTemplate> t : probabilyMap.entrySet()){
			if(t.getValue().getLowLevel() <= level && t.getValue().getHighLevel() >= level){
				map.put(t.getValue().getTimes(), t.getValue());
			}
		}
		return map;
	}
	
	/**
	 * 根据次数随机任务概率模板
	 * @param human
	 * @param times
	 * @return
	 */
	public Map<Integer, Integer> getTaskSetByDayTime(Human human, int times){
		int level = human.getLevel();
		Map<Integer, TaskProbabilityTemplate> map = this.getTaskProbabilityTemplateByLevel(level);
		TaskProbabilityTemplate taskProbability = map.get(times);
		
		if(taskProbability == null ){
			return null;
		}
		
		Map<Integer, Integer> taskMap = new HashMap<Integer, Integer>();
		
		//把模板里的6个任务放到map里以备随机任务用，我也不知道为啥必须用这个这么坑爹的6个分支
		if(taskProbability.getTask1() != 0){
			taskMap.put(taskProbability.getTask1(), dayMap.get(taskProbability.getTask1()).getWeight());
		}
		if(taskProbability.getTask2() != 0){
			taskMap.put(taskProbability.getTask2(), dayMap.get(taskProbability.getTask2()).getWeight());
		}
		if(taskProbability.getTask3() != 0){
			taskMap.put(taskProbability.getTask3(), dayMap.get(taskProbability.getTask3()).getWeight());
		}
		if(taskProbability.getTask4() != 0){
			taskMap.put(taskProbability.getTask4(), dayMap.get(taskProbability.getTask4()).getWeight());
		}
		if(taskProbability.getTask5() != 0){
			taskMap.put(taskProbability.getTask5(), dayMap.get(taskProbability.getTask5()).getWeight());
		}
		if(taskProbability.getTask6() != 0){
			taskMap.put(taskProbability.getTask6(), dayMap.get(taskProbability.getTask6()).getWeight());
		}
		
		return taskMap;
	}
	
	/**
	 * 根据次数获得奖励
	 * @param human
	 * @param times
	 * @return
	 */
	public void getRandomPrizeByDayTime(Human human, int times){
		int level = human.getLevel();
		Map<Integer, TaskProbabilityTemplate> map = this.getTaskProbabilityTemplateByLevel(level);
		TaskProbabilityTemplate taskProbability = map.get(times);
		
		//取得随机数
		double random = RandomUtils.nextDouble();
		
		// 增加道具
		if (random <= taskProbability.getItemProbability()) {
			if (!taskProbability.getItemPrize().equalsIgnoreCase("0")) {
				human.getInventory().addItem(taskProbability.getItemPrize(), 1,
						BindStatus.BIND_YET, ItemGenLogReason.QUEST_BONUS, "",
						true);
			}
			// 重要信息提示
			String content = Globals.getLangService()
					.read(CommonLangConstants_10000.GET_SOMETHING,
							1,
							Globals.getItemService()
									.getTemplateByItemSn(
											taskProbability.getItemPrize())
									.getName());
			human.getPlayer().sendImportPromptMessage(content);
		}

		// 增加体力
		random = RandomUtils.nextDouble();
		if (random <= taskProbability.getVitProbability()) {
			human.getPropertyManager().setVitality(
					human.getPropertyManager().getVitality()
							+ taskProbability.getVitPrize());
			// 重要信息提示
			String content = Globals.getLangService().read(
					CommonLangConstants_10000.ADDPROP,
					taskProbability.getVitPrize(),
					Globals.getLangService().read(CommonLangConstants_10000.VIT));
			human.getPlayer().sendImportPromptMessage(content);
		}
		
		//增加声望
		random = RandomUtils.nextDouble();
		if(random <= taskProbability.getPopularityProbability()){
			human.addPopularity(taskProbability.getPopularityPrize());
			// 重要信息提示
			String content = Globals.getLangService().read(
					CommonLangConstants_10000.ADDPROP,
					taskProbability.getPopularityPrize(),
					Globals.getLangService().read(CommonLangConstants_10000.SHENGWANG));
			human.getPlayer().sendImportPromptMessage(content);
		}

		// 增加阅历铜钱
		human.getPropertyManager().setSee(
				human.getPropertyManager().getSee() + taskProbability.getSee());
		human.giveMoney(taskProbability.getCopper(), Currency.COINS, true,
				CurrencyLogReason.DAYTASK,
				CurrencyLogReason.DAYTASK.getReasonText());
		// 重要信息提示
		String content = Globals.getLangService().read(
				CommonLangConstants_10000.ADDPROP,
				taskProbability.getSee(),
				Globals.getLangService().read(CommonLangConstants_10000.SEE));
		human.getPlayer().sendImportPromptMessage(content);
		
		// 重要信息提示
		String content1 = Globals.getLangService().read(
				CommonLangConstants_10000.ADDPROP, taskProbability.getCopper(),
				Globals.getLangService().read(CommonLangConstants_10000.CURRENCY_NAME_COINS));
		human.getPlayer().sendImportPromptMessage(content1);
	}
	
	/**
	 * 随机任务
	 * @param taskMap
	 * @return
	 */
	public DayTaskTemplate getRandomTask(Map<Integer, Integer> taskMap){
		double allWeight = 0;
		double rate = 0;
		
		//得到总权重
		for(Map.Entry<Integer, Integer> t : taskMap.entrySet()){
			allWeight = allWeight + t.getValue();
		}
		
		//取得随机数
		double random = RandomUtils.nextDouble();
		
		//轮盘法
		for(Map.Entry<Integer, Integer> t : taskMap.entrySet()){
			rate = (double) t.getValue()/allWeight;
			
			random = random - rate;
			
			//失败
			if(random > 0){
				continue;
			}
			
			return dayMap.get(t.getKey());
		}

		return null;		
	}
	
	public void udateDayTask(Human human, String taskId){
		
		/*根据taskid判断是否为日常任务 如果是
		 *  判断当天得完成次数 如果次数已经达到完成标
		 *  准 发完成任务消息给客户端，否则只更新次数
		 */

		for(Map.Entry<Integer, DayTaskTemplate> d : dayMap.entrySet()){
			GCDayTaskComplete msg = new GCDayTaskComplete();
			if(taskId.equalsIgnoreCase(d.getValue().getTaskId())){
				DayTaskInfoManager dtim = new DayTaskInfoManager(human);
				HumanDayTaskInfo taskInfo = dtim.getHumanDayTaskInfo();
				if(taskInfo != null && taskInfo.isTaskFlag() == false){
					taskInfo.setTaskTimes(taskInfo.getTaskTimes()+1);
					dtim.completeOneDayTask(human, taskInfo.getTaskTimes());
					if(taskInfo.getTaskTimes() == d.getValue().getTimes()){
						taskInfo.setDayTimes(taskInfo.getDayTimes()+1);
						dtim.completeDayTask(human, taskInfo.getDayTimes());
						this.getRandomPrizeByDayTime(human, taskInfo.getDayTimes());
						
						msg.setTaskId(taskId);
						human.sendMessage(msg);
						human.getPlayer().sendRightMessage(QuestLangConstants_40000.QUEST_IS_FINISHED, d.getValue().getTaskName());
						return;
					}
					msg.setTaskId(taskId);
					human.sendMessage(msg);
					human.getPlayer().sendRightMessage(QuestLangConstants_40000.QUEST_IS_FINISHED, d.getValue().getTaskName());
					return;
				}
			}
		}
	}
}
