package com.pwrd.war.core.object;

import java.io.Serializable;

/**
 * 对象的生命周期状态
 * 
 * 
 */
public class LifeCycleImpl implements LifeCycle {
	private LifeCycleStateType state;
	private PersistanceObject<?, ?> po;

	public LifeCycleImpl(PersistanceObject<?, ?> po) {
		this.po = po;
		this.state = LifeCycleStateType.DEACTIVED;
	}

	/**
	 * 检查生命期是否是活动状态
	 * 
	 * @return true,活动状态;false,非活动状态
	 */
	public boolean isActive() {
		return this.state == LifeCycleStateType.ACTIVE;
	}

	/**
	 * 将生命期设置为活动
	 * 
	 * @exception IllegalStateException
	 *                如果当前状态是{@link LifeCycleStateType#DESTROYED}会抛出此异常
	 */
	public void activate() {
		if (this.state == LifeCycleStateType.DESTROYED) {
			throw new IllegalStateException(
					"The object has been dead,can't set it state.");
		}
		this.state = LifeCycleStateType.ACTIVE;
	}

	/**
	 * 将生命期设置为非活动
	 * 
	 * @exception IllegalStateException
	 *                如果当前状态是{@link LifeCycleStateType#DESTROYED}会抛出此异常
	 */
	public void deactivate() {
		if (this.state == LifeCycleStateType.DESTROYED) {
			throw new IllegalStateException(
					"The object has been dead,can't set it state.");
		}
		this.state = LifeCycleStateType.DEACTIVED;
	}

	/**
	 * 检查当前的状态是否是可以修改的
	 * 
	 * @exception IllegalStateException
	 *                如果是不可修改的,则抛出此异常
	 */
	public void checkModifiable() {
		if (this.state == LifeCycleStateType.DESTROYED) {
			throw new IllegalStateException(
					"The object has been dead,can't modify");
		}
	}

	/**
	 * 将生命期设置为死亡,对象一旦进入到此状态,对象应该不可以再进行修改
	 */
	public void destroy() {
		this.state = LifeCycleStateType.DESTROYED;
	}

	/**
	 * 检查当前的状态是否是死亡的
	 * 
	 * @return true,死亡状态;false,非死亡状态
	 */
	public boolean isDestroyed() {
		return this.state == LifeCycleStateType.DESTROYED;
	}

	@Override
	public Serializable getKey() {
		return po.getGUID();
	}

	@Override
	public PersistanceObject<?, ?> getPO() {
		return po;
	}
}
