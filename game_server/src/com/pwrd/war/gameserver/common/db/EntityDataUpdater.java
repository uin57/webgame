package com.pwrd.war.gameserver.common.db;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.orm.BaseEntity;
import com.pwrd.war.core.persistance.DataUpdater;
import com.pwrd.war.core.persistance.UpdateEntry;
import com.pwrd.war.core.util.ErrorsUtil;

/**
 * 
 * 主要用于没有业务对象对应的数据更新操作
 * 
 */
public class EntityDataUpdater implements DataUpdater<BaseEntity<?>>{
	
	protected final static Logger logger = Loggers.updateLogger;
	
	/**  */
	private static Map<Class<? extends BaseEntity<?>>, EntityUpdater> operationDbMap = new LinkedHashMap<Class<? extends BaseEntity<?>>, EntityUpdater>();
		
	/** 保存需要被更新的,Key:实体的ID,Value:将要被更新的实体 */
	private Map<Object, UpdateEntry<BaseEntity<?>>> changedObjects = new LinkedHashMap<Object, UpdateEntry<BaseEntity<?>>>();	
	
	/** 是否正在执行Update操作 */
	private volatile boolean isUpdating = false;
	
	static {
		
		
	}

	public EntityDataUpdater()
	{		
	}
	

	
	/**
	 * 增加要被更新对象
	 * 
	 * @param lifeCycle
	 * @return
	 */
	@Override
	public boolean addUpdate(BaseEntity<?> lifeCycle) {
		if (lifeCycle == null) {
			throw new IllegalArgumentException("The value must not be null.");
		}
		if (isUpdating) {
			throw new IllegalStateException(
					"The update operation is running,can't add new update");
		}
		final Serializable _key = lifeCycle.getId();
		UpdateEntry<BaseEntity<?>> _pre = changedObjects.get(_key);
		if (_pre != null) {
			if (_pre.obj != lifeCycle) {
				if (logger.isWarnEnabled()) {
					logger
							.warn("[#GS.AbstractDataUpdater.addUpdate] [The object to be updated not the same instance,is this a bug?]");
				}
				return false;
			} else {
				return true;
			}
		}
		UpdateEntry<BaseEntity<?>> _now = new UpdateEntry<BaseEntity<?>>(UpdateEntry.OPER_UPDATE, lifeCycle);
		changedObjects.put(_key, _now);
		return true;
	}
	
	/**
	 * 增加要被删除的对象
	 * 
	 * @param lifeCycle
	 * @return
	 */
	@Override
	public boolean addDelete(BaseEntity<?> lifeCycle) {
		if (lifeCycle == null) {
			throw new IllegalArgumentException("The value must not be null.");
		}
		if (isUpdating) {
			throw new IllegalStateException(
					"The delete operation is running,can't add new update");
		}
		final Serializable _key = lifeCycle.getId();
		UpdateEntry<BaseEntity<?>> _now = new UpdateEntry<BaseEntity<?>>(UpdateEntry.OPER_DEL, lifeCycle);
		changedObjects.put(_key, _now);
		return true;
	}
	
	/**
	 * 执行update操作
	 * 
	 */
	@Override
	public void update() {
		try {
			this.isUpdating = true;
			long _s = System.nanoTime();
			final int _beginSize = this.changedObjects.size();
			if (_beginSize > 0) {
				if (logger.isDebugEnabled()) {
					logger
							.debug("[#GS.AbstractDataUpdater.update] [Begin to sync objects size:"
									+ this.changedObjects.size() + "]");
				}
			}
			for (UpdateEntry<BaseEntity<?>> _entry : this.changedObjects.values()) {
				BaseEntity<?> _obj = _entry.obj;
				try {
					if (_entry.operation == UpdateEntry.OPER_UPDATE) {
						// 执行更新操作
						this.doUpdate(_obj);
					} else if (_entry.operation == UpdateEntry.OPER_DEL) {
						// 执行删除操作
						this.doDel(_obj);
					}
				} catch (Exception e) {
					logger.error(
							ErrorsUtil.error(
									CommonErrorLogInfo.DB_OPERATE_FAIL,
									"#GS.AbstractDataUpdater.update", "key:"
											+ _obj.getId()), e);
				}
			}
			final int _finishSize = this.changedObjects.size();
			if (_finishSize > 0) {
				if (logger.isDebugEnabled()) {
					logger
							.debug("[#GS.AbstractDataUpdater.update] [Finish sync objects size:"
									+ this.changedObjects.size() + "]");
				}
			}
			if (_beginSize != _finishSize) {
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.INVALID_STATE,
						"#GS.AbstractDataUpdater.update", "The begin size is ["
								+ _beginSize + "] but the finish size is ["
								+ _finishSize + "]"),
						new IllegalStateException());
			}
			long _time = (System.nanoTime() - _s) / (1000 * 1000);
			if (_time > 0 && logger.isDebugEnabled()) {
				logger.debug("[#GS.AbstractDataUpdater.update] [Update Time:" + (_time)
						+ "ms]");
			}
		} finally {
			this.changedObjects.clear();
			this.isUpdating = false;
		}
	}
	
	/**
	 * 返回改变的对象
	 * 
	 * @return
	 */
	public Map<Object, UpdateEntry<BaseEntity<?>>> getChangedObjects() {
		return Collections.unmodifiableMap(changedObjects);
	}


	protected void doUpdate(BaseEntity<?> lc) {
		EntityUpdater poUpdater = operationDbMap.get(lc.getClass());
		poUpdater.save(lc);
	}

	protected void doDel(BaseEntity<?> lc) {
		EntityUpdater poUpdater = operationDbMap.get(lc.getClass());
		poUpdater.delete(lc);
	}

}
