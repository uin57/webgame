package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.SceneEntity;

/**
 * 场景数据表操作类
 * 
 * @author haijiang.jin
 *
 */
public class SceneDao extends BaseDao<SceneEntity> {
	/** 获取场景实体 SQL */
	private static final String GET_SCENE_BY_TPL_ID_SQL = "getSceneByTemplateId";
	/** 获取场景实体参数 */
	private static final String[] GET_SCENE_BY_TPL_ID_PARAMS = { "templateId" };

	/**
	 * 类参数构造器
	 * 
	 * @param dbServcie
	 */
	public SceneDao(DBService dbServcie) {
		super(dbServcie);
	}

	@Override
	protected Class<SceneEntity> getEntityClass() {
		return SceneEntity.class;
	}

	/**
	 * 获取场景实体
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SceneEntity getSceneByTemplateId(int templateId) {
		// 获取场景实体列表
		List<SceneEntity> objs = this.dbService.findByNamedQueryAndNamedParam(
			GET_SCENE_BY_TPL_ID_SQL, 
			GET_SCENE_BY_TPL_ID_PARAMS, 
			new Object[] { templateId });

		if (objs == null || objs.size() <= 0) {
			return null;
		}

		return objs.get(0);
	}
}
