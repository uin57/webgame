package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.db.model.PetEntity;

/**
 * PetInfo 数据库管理操作类
 * 
 * 
 */
public class PetDao extends BaseDao<PetEntity> {

	/** 按照charId获取宠物：HQL */
	private static final String GET_PETS_BY_CHARID = "queryPlayerPets";
	/** 按照charId获取宠物：参数 */
	private static final String[] GET_PETS_BY_CHARID_PARAMS = new String[] { "charId" };

	public PetDao(DBService dbService) {
		super(dbService);
	}

	@SuppressWarnings("unchecked")
	public List<PetEntity> getPetsByCharId(String charId) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_PETS_BY_CHARID,
				GET_PETS_BY_CHARID_PARAMS, new Object[] { charId });
	}

	/**
	 * 软删除一个实体
	 * 
	 * @param id
	 * @exception DataAccessException
	 */
	public void deleteSoft(PetEntity petInfo) {
		this.dbService.softDelete(petInfo);
	}

	@Override
	protected Class<PetEntity> getEntityClass() {
		return PetEntity.class;
	}
}
