package com.pwrd.war.core.persistance;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.util.ErrorsUtil;


/**
 * 数据更新接口的默认实现,用于更新或者被删除的{@link LifeCycle}类型的对象
 * 
 *
 */
public abstract class AbstractDataUpdater implements DataUpdater<LifeCycle>{
	
	protected final static Logger logger = Loggers.updateLogger;
	

	/** 保存需要被更新的,Key:实体的ID,Value:将要被更新的实体 */
	private Map<Object, UpdateEntry<LifeCycle>> changedObjects = new LinkedHashMap<Object, UpdateEntry<LifeCycle>>();
	
	/** 是否正在执行Update操作 */
	private volatile boolean isUpdating = false;
	
	
	public AbstractDataUpdater()
	{		
	}
	
	/**
	 * 增加要被更新对象
	 * 
	 * @param lifeCycle
	 * @return
	 */
	@Override
	public boolean addUpdate(LifeCycle lifeCycle) {
		if (lifeCycle == null) {
			throw new IllegalArgumentException("The value must not be null.");
		}
		if (isUpdating) {
			
//			throw new IllegalStateException(
//					"The update operation is running,can't add new update");
			logger.error("The update operation is running,can't add new update");
			return false;
		}
		final Serializable _key = lifeCycle.getKey();
		UpdateEntry<LifeCycle> _pre = changedObjects.get(_key);
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
		UpdateEntry<LifeCycle> _now = new UpdateEntry<LifeCycle>(UpdateEntry.OPER_UPDATE, lifeCycle);
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
	public boolean addDelete(LifeCycle lifeCycle) {
		if (lifeCycle == null) {
			throw new IllegalArgumentException("The value must not be null.");
		}
		if (isUpdating) {
			throw new IllegalStateException(
					"The delete operation is running,can't add new update");
		}
		final Serializable _key = lifeCycle.getKey();
		UpdateEntry<LifeCycle> _now = new UpdateEntry<LifeCycle>(UpdateEntry.OPER_DEL, lifeCycle);
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
			for (UpdateEntry<LifeCycle> _entry : this.changedObjects.values()) {
				LifeCycle _obj = _entry.obj;
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
											+ _obj.getKey()), e);
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
	public Map<Object, UpdateEntry<LifeCycle>> getChangedObjects() {
		return Collections.unmodifiableMap(changedObjects);
	}
	
	abstract protected void doUpdate(LifeCycle lc);
	
	abstract protected void doDel(LifeCycle lc);

	public boolean isUpdating() {
		return isUpdating;
	}
	
}
