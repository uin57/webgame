package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_charge")
public class ChargeInfo implements BaseEntity<Long> {
	
	/** */
	private static final long serialVersionUID = 1L;
	
	/** OrderID 主键(流水号18位) */
	private long id;
	
	/** 登陆标识 */
	private String userId;
	
	/** 充值金额($) */
	private int money;
	
	/** 任务开始时间 */
	private Timestamp time;
	
	/** 领取状态(1表示已经领取,0代表未领取) */
	private int status;
	
	/** 记录创建时间 */
	private Timestamp createTime;
	
	/** 记录更新时间 */
	private Timestamp updateTime;
	
	@Id
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(columnDefinition = " int default 0", nullable = false)
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	@Column
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	@Column
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}	

}
