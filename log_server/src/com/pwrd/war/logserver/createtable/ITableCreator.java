package com.pwrd.war.logserver.createtable;

import java.util.Collection;

/**
 * 日志表创建接口
 * 
 */
public interface ITableCreator {
	/**
	 * 创建日志表
	 */
	public void buildTable();

	/**
	 * 取得基本表名列表
	 * 
	 * @return
	 */
	public Collection<String> getBaseTableNames();

	/**
	 * 设置基本表名列表
	 * 
	 * @param baseTableNames
	 */
	public void setBaseTableNames(Collection<String> baseTableNames);
}
