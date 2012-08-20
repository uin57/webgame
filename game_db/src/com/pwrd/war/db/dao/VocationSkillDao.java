package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.VocationSkillEntity;

public class VocationSkillDao extends BaseDao<VocationSkillEntity> {

	
	private static final String GET_VOCATION_BY_HUMANSN = "queryVocation";
	
	private static final String[] GET_VOCATION_BY_HUMANSN_PARAMS = new String[] { "humanSn" };
	
	@SuppressWarnings("unchecked")
	public List<VocationSkillEntity> getVacationByHumanId(String humanSn) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_VOCATION_BY_HUMANSN, GET_VOCATION_BY_HUMANSN_PARAMS,
				new Object[] { humanSn });
	}
	
	public VocationSkillDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<VocationSkillEntity> getEntityClass() {
		return VocationSkillEntity.class;
	}

}
