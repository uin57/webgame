package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.HumanEntity;

/**
 * 人物角色数据管理操作类
 * 
 * 
 */
public class HumanDao extends BaseDao<HumanEntity> {
	
	// 基础查询 
	
	/**查询所有玩家 */
	public static final String QUERY_ALL_ROLES = "queryAllRole";

	/** 查询语句名称 ： 根据账号ID获取所有 roleInfo */
	public static final String QUERY_GET_ROLES_BY_PID = "queryRolesByPid";
	
	/** 查询：根据角色ID查询 role */
	public static final String QUERY_ROLE_BY_UUID = "queryRoleByUUID";

	/** 查询语句名称 ： 根据姓名获取 roleInfo */
	public static final String QUERY_GET_ROLE_BY_NAME = "queryRoleByName";	

	/** 更新玩家当日累计在线时间 */
	public static final String UPDATE_USER_ONLINE_TIME = "updateUserOnlineTime";
	
	/** 根据场景 Id 查询玩家角色列表 */
	public static final String QUERY_HUMANS_BY_SCENE_ID = "queryHumansBySceneId";
	
	/** 更新玩家 buffs */
	public static final String UPDATE_BUFFS = "updateBuffs";
	
	/** 增加玩家体力*/
	public static final String UPDATE_VITS = "updateVits";
	
	/** 根基家族id查询角色列表 */
	public static final String QUERY_ROLES_BY_FAMILYID = "queryRolesByFamilyId";
	
	// 功能需要
	
	
	
	
	public HumanDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<HumanEntity> getEntityClass() {
		return HumanEntity.class;
	}

	/**
	 * 根据角色名获取第一个HumanEntity
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HumanEntity loadHuman(String name) {
		List<HumanEntity> _charList = dbService.findByNamedQueryAndNamedParam(
				QUERY_GET_ROLE_BY_NAME, new String[] { "name" },
				new Object[] { name });
		if (_charList.size() > 0) {
			return (HumanEntity) _charList.get(0);
		}
		return null;
	}
	

	/**
	 * 根据账号ID从数据库获取所有角色
	 * 
	 * @param passportId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HumanEntity> loadHumans(String passportId) {
		return dbService.findByNamedQueryAndNamedParam(
				QUERY_GET_ROLES_BY_PID, new String[] { "passportId" },
				new Object[] { passportId });
	}

	/**
	 * 更新玩家当日在线累计时间
	 * 
	 * @param playerId
	 * @param onlineTime
	 */
	public void updatePlayerOnlineTime(String passportId,long lastLoginTime , int onlineTime) {
		dbService.queryForUpdate(UPDATE_USER_ONLINE_TIME,
				new String[] { "passportId", "lastLoginTime", "onlineTime" }, new Object[] {
						passportId,lastLoginTime , onlineTime });
	}

	/**
	 * 根据UUID从数据库中查询相应角色
	 * 
	 * @param UUID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HumanEntity> queryHumanByUUID(long UUID) {
		return dbService.findByNamedQueryAndNamedParam(QUERY_ROLE_BY_UUID,
				new String[] { "id" }, new Object[] { UUID });
	}

	/**
	 * 根据场景 Id 加载玩家角色列表
	 * 
	 * @param sceneId 场景 Id, 场景配置模版中的 Id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HumanEntity> queryHumansBySceneId(String sceneId) {
		return dbService.findByNamedQueryAndNamedParam(
			QUERY_HUMANS_BY_SCENE_ID,
			new String[] { "sceneId" }, new Object[] { sceneId }
		);
	}

	/**
	 * 更新玩家 buff
	 * 
	 * @param humanUUId
	 * @param buffs
	 * @return
	 */
	public int updateBuffs(long humanUUId, String buffs) {
		return dbService.queryForUpdate(
			UPDATE_BUFFS, 
			new String[] { "humanUUId", "buffs" }, 
			new Object[] {  humanUUId ,  buffs  }
		);
	}

	@SuppressWarnings("unchecked")
	public List<HumanEntity> queryAllHuman() {
		return dbService.findByNamedQuery(QUERY_ALL_ROLES);
	}
	
	/**
	 * 更新玩家体力 每次num点 但是不能超过上限
	 * @param humanUUID
	 * @param num
	 * @return
	 */
	public int updateVits(int num){
		return dbService.queryForUpdate(
				UPDATE_VITS, 
				new String[] { "num" }, 
				new Object[] {  num  }
			);
	}
	
	/**
	 * 查询某个家族的玩家列表
	 * @param familyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<HumanEntity> queryHumansByFamilyId(String familyId){
		return dbService.findByNamedQueryAndNamedParam(
				QUERY_ROLES_BY_FAMILYID,
				new String[] { "familyId" }, new Object[] { familyId }
			);
	}
	
	public int getMaxLevel(){
		Object o = dbService.findByNamedQuery("getMaxLevel").get(0);
		if(o == null)return 1;
		return Integer.valueOf(""+o);
	}
	@SuppressWarnings("unchecked")
	public List<HumanEntity> queryTopHumanList(int camp){
		return dbService.findByNamedQueryAndNamedParam("queryTopHumanList", new String[]{"camp"},
				new Object[]{camp});
	}
	
	public int getTotalPlayer(){
		return Integer.valueOf(""+dbService.findByNamedQuery("getTotalPlayer").get(0));
	}
}
