package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.FormEntity;

public class FormDao extends BaseDao<FormEntity>{
	
	private static final String GET_FORM_BY_FORMSN = "queryForm";
	
	private static final String[] GET_FORM_BY_FORMSN_PARAMS = new String[] { "humanSn" };
	
	@SuppressWarnings("unchecked")
	public List<FormEntity> getFormByHumanSn(String humanSn) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_FORM_BY_FORMSN, GET_FORM_BY_FORMSN_PARAMS,
				new Object[] { humanSn });
	}


	public FormDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<FormEntity> getEntityClass() {
		return FormEntity.class;
	}

}
