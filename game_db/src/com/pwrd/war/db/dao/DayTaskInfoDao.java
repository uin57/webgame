package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.DayTaskInfoEntity;

public class DayTaskInfoDao extends BaseDao<DayTaskInfoEntity>{
	
	private static final String GET_DAY_TASK_INFO_BY_CHARID = "queryPlayerDayTaskInfo";
	private static final String GEL_DAY_TASK_INFO_BY_CHARID = "deletePlayerDayTaskInfo";
	private static final String UPDATE_DAY_TASK_INFO_BY_CHARID = "updatePlayerDayTaskInfo";
	private static final String UPDATE_DAY_TASK_FLAG_AND_ID_BY_CHARID = "updatePlayerDayTaskFlagAndId";
	private static final String UPDATE_DAY_TASK_TIMES_BY_CHARID = "updatePlayerDayTaskTimes";
	private static final String UPDATE_DAY_TASK_FLAG_BY_CHARID = "updatePlayerDayTaskFlag";

	
	private static final String[] GET_DAY_TASK_INFO_BY_CHARID_PARAMS = new String[] { "charId" };
	private static final String[] COMPLETE_ONE_TASK = new String[] { "charId", "taskTimes" };
	private static final String[] COMPLETE_ALL_TASK = new String[] { "charId", "dayTimes" };
	private static final String[] UPDATE_DAY_TASK_INFO_BY_CHARID_PARAMS = new String[] { "charId" ,"dayTimes", "taskTimes", "bullet", "taskFlag", "taskId"};
	private static final String[] UPDATE_DAY_TASK_FLAG_AND_ID_BY_CHARID_PARAMS = new String[] { "charId" , "taskFlag", "taskId"};
	
	
	public DayTaskInfoDao(DBService dbService) {
		super(dbService);
	}
	
	/**
	 * 获取每日任务列表
	 * @param characterId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DayTaskInfoEntity> getDayTaskInfoByCharId(String characterId) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_DAY_TASK_INFO_BY_CHARID, GET_DAY_TASK_INFO_BY_CHARID_PARAMS,
				new Object[] { characterId });
	}
	
	/**
	 * 删除每日任务
	 * @param characterId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<DayTaskInfoEntity> deleteDayTaskInfoByCharId(String characterId) {
		return this.dbService.findByNamedQueryAndNamedParam(GEL_DAY_TASK_INFO_BY_CHARID, GET_DAY_TASK_INFO_BY_CHARID_PARAMS,
				new Object[] { characterId });
	}
	
	/**
	 * 更新每日任务
	 * @param characterId
	 * @return
	 */
	public void updateDayTaskInfoByCharId(String characterId, int dayTimes, int taskTimes, int bullet, boolean taskFlag, String taskId) {
		this.dbService.queryForUpdate(UPDATE_DAY_TASK_INFO_BY_CHARID, UPDATE_DAY_TASK_INFO_BY_CHARID_PARAMS,
				new Object[] { characterId, dayTimes, taskTimes, bullet, taskFlag, taskId});
	}
	
	/**
	 * 放弃每日任务，taskid清空 然后完成标志设置为已完成
	 * @param characterId
	 * @return
	 */

	public void updateDayTaskFlagAndIdByCharId(String characterId, boolean taskFlag, String taskId) {
		this.dbService.queryForUpdate(UPDATE_DAY_TASK_FLAG_AND_ID_BY_CHARID, UPDATE_DAY_TASK_FLAG_AND_ID_BY_CHARID_PARAMS,
				new Object[] { characterId, taskFlag, taskId });
	}
	
	public void updateDayTaskTimesByCharId(String characterId, int taskTimes) {
		this.dbService.queryForUpdate(UPDATE_DAY_TASK_TIMES_BY_CHARID, COMPLETE_ONE_TASK,
				new Object[] { characterId, taskTimes });
	}
	
	public void updateDayTaskFlagByCharId(String characterId, int dayTimes) {
		this.dbService.queryForUpdate(UPDATE_DAY_TASK_FLAG_BY_CHARID, COMPLETE_ALL_TASK,
				new Object[] { characterId, dayTimes });
	}
	
	@Override
	protected Class<DayTaskInfoEntity> getEntityClass() {
		return DayTaskInfoEntity.class;
	}

}
