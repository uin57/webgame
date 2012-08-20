package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.BufferEntity;

public class BufferDao extends BaseDao<BufferEntity> {
	
	private static final String GET_BUFFER_BY_HUMANSN = "queryBuffer";
	
	private static final String[] GET_BUFFER_BY_HUMANSN_PARAMS = new String[] { "humanSn" };
	
	@SuppressWarnings("unchecked")
	public List<BufferEntity> getBufferByHumanId(String humanSn) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_BUFFER_BY_HUMANSN, GET_BUFFER_BY_HUMANSN_PARAMS,
				new Object[] { humanSn });
	}

	public BufferDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<BufferEntity> getEntityClass() {
		return BufferEntity.class;
	}

	public void deleteByHumanAndBuffer(String humanSn, int bufferSn){
		this.dbService.queryForUpdate("deleteBuffer", new String[]{"humanSn","bufferSn"}, new Object[]{humanSn, bufferSn+""});
	}
}
