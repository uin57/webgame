package com.pwrd.war.gameserver.common.db.operation;

import org.slf4j.Logger;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.orm.BaseEntity;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.db.dao.BaseDao;

/**
 * 直接操作实体对象,注意该实现在做IO操作前不会对原对象进行拷贝操作
 * 
 * 
 */
public class DeleteEntityOperation<T extends BaseEntity<?>> implements BindUUIDIoOperation {
	private static final Logger logger = Loggers.asyncLogger;
	private final T entity;
	private final String roleUUID;
	private final BaseDao<T> dao;

	/**
	 * 
	 * @param entity 
	 * @param roleUUID 推荐是角色id,根据需要可以换成其他id
	 * @param dao
	 */
	public DeleteEntityOperation(T entity, String roleUUID, BaseDao<T> dao) {
		this.entity = entity;
		this.roleUUID = roleUUID;
		this.dao = dao;
	}

	@Override
	public int doIo() {
		try {
			this.dao.delete(entity);
		} catch (DataAccessException e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.DB_OPERATE_FAIL,
						"#GS.DeleteEntityOperation.doIo", null), e);
			}
			return STAGE_STOP_DONE;
		}
		return STAGE_IO_DONE;
	}

	@Override
	public int doStart() {
		return STAGE_START_DONE;
	}

	@Override
	public int doStop() {
		return STAGE_STOP_DONE;
	}

	@Override
	public String getBindUUID() {
		return this.roleUUID;
	}
}
