package com.pwrd.war.core.orm;

import com.pwrd.war.core.event.IEvent;

/**
 * 数据访问失败接口
 * 
 * 
 */
public class DBAccessEvent implements IEvent<String> {
	
	/** 
	 * DB的事件类型定义 
	 */
	public static enum Type {
		/**
		 * 访问数据库是发生的错误事件
		 */
		ERROR,
		/**
		 * 访问数据库发生的警告式事件
		 */
		WARN;
	}

	/**
	 * 事件的类型
	 */
	private final Type type;
	/**
	 * 事件的文字描述
	 */
	private final String message;

	public DBAccessEvent(Type type, String message) {
		if (type == null) {
			throw new IllegalArgumentException("The type must not be null");
		}
		this.type = type;
		this.message = message;
	}

	/**
	 * @return the type
	 */
	public final Type getType() {
		return type;
	}

	/**
	 * @return the message
	 */
	public final String getMessage() {
		return message;
	}

	@Override
	public String getInfo() {
		return "DBAccess:" + this.type + ":[" + this.message + "]";
	}
}
