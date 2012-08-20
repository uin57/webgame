package com.pwrd.war.db.model.cache;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.SoftDeleteEntity;

@Entity
@Table(name = "t_h2_test")
public class H2TestEntity implements SoftDeleteEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/**  id **/
	private long id;
	/** 类型，用于条件查询测试*/
	private int type;
	/** 值 */
	private String value;
	/** 删除标志 */
	private int deleted;

	/** 删除日期 */
	private Timestamp deleteDate;
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@Override
	public Long getId() {
		return id;
	}

	
	public void setValue(String value) {
		this.value = value;
	}

	@Column
	public String getValue() {
		return value;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	@Column
	public Timestamp getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Timestamp deleteDate) {
		this.deleteDate = deleteDate;
	}
}
