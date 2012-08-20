package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.TimeNoticeInfo;

public class TimeNoticeDAO extends BaseDao<TimeNoticeInfo> {

	public TimeNoticeDAO(DBService dbService) {
		super(dbService); 
	}

	@Override
	protected Class<TimeNoticeInfo> getEntityClass() { 
		return TimeNoticeInfo.class;
	}
	
	public List<TimeNoticeInfo> findAll(){
		return this.dbService.findByNamedQuery("findAllTimeNotice");
	}
}
