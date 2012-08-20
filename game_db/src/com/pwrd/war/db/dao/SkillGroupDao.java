package com.pwrd.war.db.dao;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.SkillGroupEntity;

public class SkillGroupDao extends BaseDao<SkillGroupEntity> {

	public SkillGroupDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<SkillGroupEntity> getEntityClass() {
		return SkillGroupEntity.class;
	}

}
