package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 进程数据库实体对象
 * 
 */
@Entity
@Table(name = "t_process_event")
public class ProcessEventEntity implements BaseEntity<Long> , CharSubEntity{
	
	private static final long serialVersionUID = -2989952994121948568L;

	/** 主键 UUID */
	private long id;
	
	/** 所属角色 */
	private String charId;
	
	/** 进程类型 */
	private int type;
	
	/** 进程信息 */
	private String props;
	
	/** 创建时间 */
	private Timestamp createTime;
	
	/** 结束时间 */
	private Timestamp endTime;
	

	@Id
	@Override
	@Column(length = 36)
	public Long getId() {
		return id;
	}

	@Column
	public String getCharId() {
		return charId;
	}
	
	@Column
	public int getType() {
		return type;
	}
	
	@Column(columnDefinition = "text", nullable = true)
	public String getProps() {
		return props;
	}

	@Column
	public Timestamp getCreateTime() {
		return createTime;
	}

	@Column
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCharId(String charId) {
		this.charId = charId;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setProps(String props) {
		this.props = props;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
	

}
