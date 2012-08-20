package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.UserInfo;

/**
 * UserInfo 数据库管理操作类
 * 
 * 
 */
public class UserInfoDao extends BaseDao<UserInfo> {

	/** 查询语句名称 ： 根据 用户名，密码查询 User */
	public static final String QUERY_GET_USER_BY_NAMEANDPWD = "queryUserByNameAndPwd";

	public UserInfoDao(DBService dbService) {
		super(dbService);
	}

	/**
	 * 根据 用户名，密码 获取用户信息
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserInfo getUserByName(String name , String password) {
		List _queryList = dbService.findByNamedQueryAndNamedParam(QUERY_GET_USER_BY_NAMEANDPWD,
				new String[] { "name" , "password" }, new Object[] { name , password});
		if (_queryList != null && _queryList.size() >= 1) {
			return (UserInfo) _queryList.get(0);
		}
		return null;
	}

	@Override
	protected Class<UserInfo> getEntityClass() {
		return UserInfo.class;
	}

}
