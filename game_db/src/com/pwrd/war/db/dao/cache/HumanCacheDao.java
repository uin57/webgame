package com.pwrd.war.db.dao.cache;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.cache.HumanCacheEntity;

/**
 * HumanInfoCache的数据操作类
 * @author yue.yan
 *
 */
public class HumanCacheDao extends BaseCacheDao<HumanCacheEntity>  {
	
	/** 查询语句名称 ： 根据姓名获取 RoleInfo */
	public static final String QUERY_GET_ROLE_BY_NAME = "queryRoleByName";
	
	/** 更新 buffs */
	public static final String UPDATE_BUFFS = "updateBuffs";

	public HumanCacheDao(DBService dbServcie) {
		super(dbServcie);
	}

	@Override
	protected Class<HumanCacheEntity> getEntityClass() {
		return HumanCacheEntity.class;
	}
	
	/**
	 * 根据角色名获取第一个HumanEntity
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HumanCacheEntity loadHumanCacheEntityByName(String name) {
		List _charList = cacheService.findByNamedQueryAndNamedParam(
				QUERY_GET_ROLE_BY_NAME, new String[] { "name" },
				new Object[] { name });
		if (_charList.size() > 0) {
			return (HumanCacheEntity) _charList.get(0);
		}
		return null;
	}
	

	/**
	 * 更新 buffs 
	 * 
	 * @param humanUUId
	 * @param buffs
	 * @return
	 */
	public int updateBuffs(long humanUUId, String buffs) {
		return cacheService.queryForUpdate(UPDATE_BUFFS, 
			new String[] { "humanUUId", "buffs" }, 
			new Object[] {  humanUUId ,  buffs  });
	}
}


