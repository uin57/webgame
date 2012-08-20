package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.HeroWarActivityEntity;

public class HeroWarActivityDao extends BaseDao<HeroWarActivityEntity> {

	public HeroWarActivityDao(DBService dbService) {
		super(dbService); 
	}

	@Override
	protected Class<HeroWarActivityEntity> getEntityClass() { 
		return HeroWarActivityEntity.class;
	}
	@SuppressWarnings("unchecked")
	public List<HeroWarActivityEntity> getAll() {
		return this.dbService.findByNamedQuery("findAllHeroWarActivityEntity");
	}
}
