package com.pwrd.war.gameserver.common.db.operation;

import org.slf4j.Logger;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.orm.BaseEntity;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.db.dao.BaseDao;

/**
 * 保存主键是UUID的对象数据到数据库中
 * 
 */
public class SaveObjectOperation<E extends BaseEntity<?>, P extends PersistanceObject<?, E>>
		implements BindUUIDIoOperation {
	private static final Logger logger = Loggers.asyncLogger;

	/** 业务对象 */
	protected final P persistObject;

	/** 实体对象 */
	private E entity;

	/** 是否执行插入操作,如果为false,则执行更新操作 */
	protected boolean save = true;

	private final BaseDao<E> dao;

	/**
	 * @param player
	 */
	public SaveObjectOperation(P persistObject, BaseDao<E> dao) {
		this.persistObject = persistObject;
		this.dao = dao;
	}

	@Override
	public int doStart() {
		if (persistObject.getDbId() == null) {
			// _dbId为空时,程序中有bug?停止保存
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.DB_NO_ID,
						"#GS.SaveObjectOperation.doStart",
						"The db id must be set before save or update."),
						new IllegalStateException());
			}
			return STAGE_STOP_DONE;
		}
		save = !(persistObject.isInDb());
		persistObject.setInDb(true);
		entity = persistObject.toEntity();
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		// 保存到数据库
		try {
			if (save) {
				dao.save(entity);
			} else {
				dao.update(entity);
			}
		} catch (DataAccessException e) {
			// 如果是保存操作,说明该对象未保存成功,设置inDb为false,尝试下次保存
			if (save) {
				persistObject.setInDb(false);
			}
			if (logger.isErrorEnabled()) {
//				logger.error(ErrorsUtil.error(
//						CommonErrorLogInfo.DB_OPERATE_FAIL,
//						"#GS.SaveObjectOperation.doIo", null), e);
				logger.error("Error in db with :" + entity+", dao="+this.dao+", emsg="+e.getMessage());
			}
			return STAGE_STOP_DONE;
		}
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		return STAGE_STOP_DONE;
	}

	@Override
	public String getBindUUID() {
		return this.persistObject.getCharId();
	}
}
