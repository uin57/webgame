package com.pwrd.war.gameserver.dayTask.handler;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.DayTaskEvent;
import com.pwrd.war.gameserver.common.i18n.constants.QuestLangConstants_40000;
import com.pwrd.war.gameserver.dayTask.DayTaskInfoManager;
import com.pwrd.war.gameserver.dayTask.DayTaskType;
import com.pwrd.war.gameserver.dayTask.HumanDayTaskInfo;
import com.pwrd.war.gameserver.dayTask.msg.CGDayTaskDrop;
import com.pwrd.war.gameserver.dayTask.msg.CGDayTaskInfo;
import com.pwrd.war.gameserver.dayTask.msg.CGGetNewTask;
import com.pwrd.war.gameserver.dayTask.msg.GCDayTaskDrop;
import com.pwrd.war.gameserver.dayTask.msg.GCDayTaskInfo;
import com.pwrd.war.gameserver.dayTask.msg.GCGetNewTask;
import com.pwrd.war.gameserver.dayTask.template.DayTaskTemplate;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;


public class DayTaskMessageHandler {
	private static int BULLET = 7;	//弹药数最多为7个
	
	public void handleDayTaskInfo(Player player, CGDayTaskInfo cgDayTaskInfo) {
		GCDayTaskInfo msg = new GCDayTaskInfo();
		
		Human human = player.getHuman();
		
		
		// 获得用户上次每日任务的时间
		long lastDayTaskTime = human.getPropertyManager().getPropertyNormal()
				.getDayTaskTime();

		// 取得当天的0点时间 判断是否到刷新时间
		long nowTime = Globals.getTimeService().now();
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeInMillis(nowTime);
		_calendar.set(Calendar.HOUR_OF_DAY, 0);
		_calendar.set(Calendar.MINUTE, 0);
		_calendar.set(Calendar.SECOND, 0);
		_calendar.set(Calendar.MILLISECOND, 0);
		long dayBegin = _calendar.getTimeInMillis();

		// 获得每日任务
		DayTaskInfoManager dtim = new DayTaskInfoManager(human);
		HumanDayTaskInfo taskInfo = dtim.getHumanDayTaskInfo();

		//构造消息
		msg.setIsInTask(false);
		msg.setNumber(BULLET);
		
		// 如果数据库里有任务
		if(StringUtils.isNotBlank(taskInfo.getCharId())){
			msg.setNumber(taskInfo.getBullet());
			if(taskInfo.isTaskFlag() == false){
				msg.setIsInTask(true);
				msg.setCurTimes(taskInfo.getTaskTimes());
				msg.setTaskId(taskInfo.getTaskId());
				msg.setNumber(taskInfo.getBullet());
			}
			
		}

		// 0点时间大于上次时间 则进行更新弹药
		if (dayBegin > lastDayTaskTime) {
			msg.setNumber(BULLET);
		} 
		
		player.sendMessage(msg);
	}

	public void handleGetNewTask(Player player, CGGetNewTask cgGetNewTask) {
		GCGetNewTask msg = new GCGetNewTask();
		
		Human human = player.getHuman();
		boolean isInDb = true;
		
		// 获得每日任务
		DayTaskInfoManager dtim = new DayTaskInfoManager(human);
		HumanDayTaskInfo taskInfo = dtim.getHumanDayTaskInfo();
		
		if(StringUtils.isEmpty(taskInfo.getCharId())){
			isInDb = false;
		}
		
		//如果有任务
		if(StringUtils.isNotBlank(taskInfo.getCharId()) && taskInfo.isTaskFlag() == false){
			msg.setSuccess(false);
			msg.setDesc(Globals.getLangService().read(QuestLangConstants_40000.QUEST_IN_PROCESS, taskInfo.getTaskId()));
			player.sendMessage(msg);
			return;
		}
		
		// 获得用户上次每日任务的时间
		long lastDayTaskTime = human.getPropertyManager().getPropertyNormal()
				.getDayTaskTime();

		// 取得当天的0点时间 判断是否到刷新时间
		long nowTime = Globals.getTimeService().now();
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeInMillis(nowTime);
		_calendar.set(Calendar.HOUR_OF_DAY, 0);
		_calendar.set(Calendar.MINUTE, 0);
		_calendar.set(Calendar.SECOND, 0);
		_calendar.set(Calendar.MILLISECOND, 0);
		long dayBegin = _calendar.getTimeInMillis();
		
		//获得可用弹药 
		int bullet = 0;
		int dayTimes = 0;
		if(dayBegin >= lastDayTaskTime || taskInfo == null){
			bullet = BULLET;
			dayTimes = 1;
		}else{
			bullet = taskInfo.getBullet();
			dayTimes = taskInfo.getDayTimes();
		}
		
		if(bullet < 1){
			msg.setSuccess(false);
			msg.setDesc(Globals.getLangService().read(QuestLangConstants_40000.DAILY_QUEST_ACCEPT_ERR_TODAY_FULL));
			player.sendMessage(msg);
			return;
		}
		
		//生成新的任务
		Map<Integer, Integer> taskMap = Globals.getDayTaskService().getTaskSetByDayTime(human, dayTimes);
		
		//任务map为空 造成原因基本是用户等级不够
		if(taskMap.isEmpty()){
			msg.setSuccess(false);
			msg.setDesc(Globals.getLangService().read(QuestLangConstants_40000.QUEST_LEVEL_NOT_REACH));
			player.sendMessage(msg);
			return;
		}
		
		DayTaskTemplate task = Globals.getDayTaskService().getRandomTask(taskMap);
		if(task != null){
			msg.setCurTimes(0);
			msg.setSuccess(true);
			msg.setDesc(null);
			msg.setNumber(bullet -1);
			msg.setTaskId(task.getTaskId());
			
			//加入数据库
			human.getPropertyManager().getPropertyNormal().setDayTaskTime(nowTime);
		
			HumanDayTaskInfo info = new HumanDayTaskInfo();
			info.setBullet(bullet - 1);
			info.setCharId(human.getUUID());
			info.setDayTimes(dayTimes + 1);
			info.setOwner(human);
			info.setTaskFlag(false);
			info.setTaskId(task.getTaskId());
			info.setTaskTimes(0);
			
			//不存在记录插入 存在更新
			if(isInDb == false){	
				dtim.addDayTaskInfoToDb(info);
				
			}else{
				//存在记录则更新
				dtim.updateDayTaskInfoToDb(info);
			}
		}
		player.sendMessage(msg);
		
		if(task.getTaskId().equals(DayTaskType.NONE.getValue())){
			//发送每日任务的事件
			DayTaskEvent event = new DayTaskEvent(player.getHuman(), DayTaskType.NONE.getValue());
			Globals.getEventService().fireEvent(event);
		}
		return;
	}
	
	public void handleDayTaskDrop(Player player, CGDayTaskDrop cgDayTaskDrop) {

		GCDayTaskDrop msg = new GCDayTaskDrop();
		
		Human human = player.getHuman();
		
		// 获得每日任务
		DayTaskInfoManager dtim = new DayTaskInfoManager(human);
		HumanDayTaskInfo taskInfo = dtim.getHumanDayTaskInfo();
		
		if(StringUtils.isEmpty(taskInfo.getCharId()) || taskInfo.isTaskFlag() == true){
			msg.setResult(false);
			msg.setDescription(Globals.getLangService().read(QuestLangConstants_40000.QUEST_NOT_FOUND));
		}else{
			//更新数据库中taskid为空  taskflag为true
			dtim.dropDayTask(taskInfo);
			msg.setResult(true);
		}
		
		player.sendMessage(msg);
	}
}
