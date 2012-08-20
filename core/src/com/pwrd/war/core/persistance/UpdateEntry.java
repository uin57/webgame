package com.pwrd.war.core.persistance;

/**
 * 更新元素
 *
 * @param <T> 数据Holder
 */
public class UpdateEntry<T> {
	
	public static final int OPER_UPDATE = 1;
	public static final int OPER_DEL = 2;
	/** 操作类型 */
	public final int operation;
	/** 待更新的业务对象 */
	public final T obj;

	public UpdateEntry(int operation, T obj) {
		this.operation = operation;
		this.obj = obj;
	}

}
