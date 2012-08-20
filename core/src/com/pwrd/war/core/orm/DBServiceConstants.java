package com.pwrd.war.core.orm;

/**
 * 定义DBService所用得常量
 * 
 * 
 */
public class DBServiceConstants {
	/** 数据库初始化类型,Hibernate {@value} */
	public static final int DB_TYPE_HIBERNATE = 0;

	/** 数据库初始化类型,IBatis{@value} */
	public static final int DB_TYPE_IBATIS = 1;
	
	/** 数据库初始化类型,DBServer{@value} */
	public static final int DAO_PROCESS_TYPE_DBS = 2;

}
