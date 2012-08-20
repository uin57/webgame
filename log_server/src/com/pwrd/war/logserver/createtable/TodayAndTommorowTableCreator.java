package com.pwrd.war.logserver.createtable;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import com.pwrd.war.logserver.dao.LogDao;

/**
 * 创建今天和明天两天的表
 * 
 */
public class TodayAndTommorowTableCreator implements ITableCreator {
	private Collection<String> baseTableNames;

	public TodayAndTommorowTableCreator() {
	}

	public Collection<String> getBaseTableNames() {
		return baseTableNames;
	}

	public void setBaseTableNames(Collection<String> baseTableNames) {
		this.baseTableNames = baseTableNames;
	}

	@Override
	public void buildTable() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
		String timeStamp = format.format(new Date());
		String tomStamp = format.format((new Date().getTime() + 86400000));
		for (String _tableName : this.baseTableNames) {
			LogDao.create(_tableName, timeStamp);
			LogDao.create(_tableName, tomStamp);
		}
	}
}
