package com.pwrd.war.gameserver.dayTask;

import java.util.List;

import com.pwrd.war.db.dao.DayTaskInfoDao;
import com.pwrd.war.db.dao.HumanDao;
import com.pwrd.war.db.model.DayTaskInfoEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;

/**
 * 每日任务管理器
 * @author xf
 */
public class DayTaskInfoManager {
	private HumanDao humanDao;
	private DayTaskInfoDao dayTaskInfoDao;
	private Human human;
	private HumanDayTaskInfo humanDayTaskInfo;
	public DayTaskInfoManager(Human human) { 
		this.human = human;
		humanDayTaskInfo = new HumanDayTaskInfo();
		humanDao = Globals.getDaoService().getHumanDao();
		dayTaskInfoDao = Globals.getDaoService().getDayTaskInfoDao();
		this.init(); 
	}	
	
	//初始化,从数据库读取每日任务信息列表
	public void init(){		
		List<DayTaskInfoEntity>  list = dayTaskInfoDao.getDayTaskInfoByCharId(human.getCharId());
		if(!list.isEmpty()){
			DayTaskInfoEntity entity = list.get(0);
			addEntityToList(entity);
		}
	}
	
	/**
	 * 将entity保存到内存
	 * @author xf
	 */
	public void addEntityToList(DayTaskInfoEntity entity){
		HumanDayTaskInfo info = new HumanDayTaskInfo(human);
		info.fromEntity(entity);		
		info.setInDb(true);
		info.getLifeCycle().activate();
		this.humanDayTaskInfo = info;
	}
	
	public void addDayTaskInfoToDb(HumanDayTaskInfo info){
		DayTaskInfoEntity entity = new DayTaskInfoEntity();
		entity.setCharId(this.human.getCharId());
		entity.setBullet(info.getBullet());
		entity.setDayTimes(info.getDayTimes());
		entity.setTaskFlag(info.isTaskFlag());
		entity.setTaskId(info.getTaskId());
		entity.setTaskTimes(info.getTaskTimes());
		
		this.dayTaskInfoDao.save(entity);
	}
	
	public void updateDayTaskInfoToDb(HumanDayTaskInfo info){
		String charId = info.getCharId();
		int dayTimes = info.getDayTimes();
		int taskTimes = info.getTaskTimes();
		int bullet = info.getBullet();
		boolean taskFlag = false;
		String taskId = info.getTaskId();
		
		this.dayTaskInfoDao.updateDayTaskInfoByCharId(charId, dayTimes, taskTimes, bullet, taskFlag, taskId);
	}
	
//	public void deleteDayTaskInfo(DayTaskInfoEntity entity){
//		this.dayTaskInfoDao.delete(entity);
//	}
//	
	public DayTaskInfoEntity getDayTaskInfoEntity(){
		List<DayTaskInfoEntity>  list = dayTaskInfoDao.getDayTaskInfoByCharId(human.getCharId());
		if(!list.isEmpty()){
			DayTaskInfoEntity entity = list.get(0);
			return entity;
		}else{
			return null;
		}
	}
	/**
	 * 放弃每日任务
	 * @param info
	 */
	public void dropDayTask(HumanDayTaskInfo info){
		String charId = info.getCharId();
		this.dayTaskInfoDao.updateDayTaskFlagAndIdByCharId(charId, true, null);
	}
	
	/**
	 * 完成一环每日任务
	 * @param human
	 */
	public void completeOneDayTask(Human human, int taskTimes){
		String charId = human.getCharId();
		this.dayTaskInfoDao.updateDayTaskTimesByCharId(charId, taskTimes);
	}
	
	/**
	 * 完成每日任务
	 * @param human
	 */
	public void completeDayTask(Human human, int dayTimes){
		String charId = human.getCharId();
		this.dayTaskInfoDao.updateDayTaskFlagByCharId(charId, dayTimes);
	}
	
	public HumanDao getHumanDao() {
		return humanDao;
	}
	public Human getHuman() {
		return human;
	}

	public HumanDayTaskInfo getHumanDayTaskInfo() {
		return humanDayTaskInfo;
	}

	public void setHumanDayTaskInfo(HumanDayTaskInfo humanDayTaskInfo) {
		this.humanDayTaskInfo = humanDayTaskInfo;
	}
}
