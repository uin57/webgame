package com.pwrd.war.core.orm;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 
 * @param <ID>
 */
public interface SoftDeleteEntity<ID extends Serializable> extends BaseEntity<ID> {
	/** 未删除 */
	public static final int UN_DELETED = 0;
	/** 已删除 */
	public static final int DELETED = 1;

	/**
	 * 取得实体是否已经被删除,参见{@link #UN_DELETED},{@link #DELETED}
	 * 
	 * @return
	 */
	public int getDeleted();

	/**
	 * 取得实体被删除的时间
	 * 
	 * @return
	 */
	public Timestamp getDeleteDate();
}
