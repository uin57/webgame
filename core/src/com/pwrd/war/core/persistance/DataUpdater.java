package com.pwrd.war.core.persistance;


/**
 * 数据更新接口
 *
 */
public interface DataUpdater<T> {	
	
	public boolean addUpdate(final T lifeCycle);
	
	public boolean addDelete(final T lifeCycle);
	
	public void update();

}
