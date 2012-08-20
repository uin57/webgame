package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.ArenaAchievementEntity;

public class ArenaAchievementDao extends BaseDao<ArenaAchievementEntity> {
	private static final String GET_CHAR_ACHIEVEMENTS = "findAllArenaAchievements";
	private static final String[] GET_CHAR_ACHIEVEMENTS_PARAMS = new String[] { "charId" };
	
	@SuppressWarnings("unchecked")
	public List<ArenaAchievementEntity> getAllArenaAchievements(String charId) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_CHAR_ACHIEVEMENTS,
				GET_CHAR_ACHIEVEMENTS_PARAMS, new Object[] { charId });
	}

	public ArenaAchievementDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<ArenaAchievementEntity> getEntityClass() {
		return ArenaAchievementEntity.class;
	}

}
