package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.ProcessEventEntity;

public class ProcessEventDao extends BaseDao<ProcessEventEntity> {
	
	/** 查询可以运行的进程事件 */
	public static final String QUERY_RUNNABLE_EVENTS = "queryRunnableEvents";
	
	/** 根据角色ID和事件类型查询进程事件 */
	public static final String QUERY_ROLE_EVENT_BY_TYPE = "queryRoleEventByType";
	
	/** 根据角色ID查询进程事件 */
	public static final String QUERY_EVENT_BY_ROLE_ID = "queryEventByRoleId";
	
	/** 角色ID */
	private static final String[] QUERY_EVENT_BY_ROLE_ID_PARAMS = new String[] { "charId" };
	
	/** 角色ID和队列事件id */
	private static final String[] QUERY_ROLE_EVENT_BY_TYPE_PARAMS = new String[] { "charId", "type" };
	
	/** 结束时间和取出多少和最后一个取出的进程事件id */
	private static final String[] QUERY_RUNNABLE_EVENTS_PARAMS = new String[] { "endTime", "top" , "lastId"};
	

	public ProcessEventDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<ProcessEventEntity> getEntityClass() {
		return ProcessEventEntity.class;
	}
	
	/**
	 * 得到某角色的进程事件
	 * @param characterId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessEventEntity> getProcessEventsByCharId(long characterId) {
		return this.dbService.findByNamedQueryAndNamedParam(QUERY_EVENT_BY_ROLE_ID, QUERY_EVENT_BY_ROLE_ID_PARAMS,
				new Object[] { characterId });
	}
	
	/**
	 * 得到某时刻之前所有可运行的进程事件
	 * @param endTime
	 * @param top
	 * @param lastId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessEventEntity> getRunnableEvents(long endTime, int top, long lastId)
	{
		return this.dbService.findByNamedQueryAndNamedParam(QUERY_RUNNABLE_EVENTS,QUERY_RUNNABLE_EVENTS_PARAMS,
				new Object[]{endTime, top, lastId} );		
	}
	
	/**
	 * 得到某角色某类型的进程事件
	 * @param characterId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessEventEntity> getRoleProcessEventsByType(long characterId, int type)
	{
		return this.dbService.findByNamedQueryAndNamedParam(QUERY_ROLE_EVENT_BY_TYPE,QUERY_ROLE_EVENT_BY_TYPE_PARAMS,
				new Object[]{characterId, type} );		
	}
	
	
}
