package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * GM补偿实体类:GM补偿实体
 * 
 */
@Entity
@Table(name = "t_user_prize")
public class UserPrize implements BaseEntity<Integer> {
	
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
	private Integer id;

	/** 玩家账号ID */
	private String passportId;

	/** GM补偿名称 */
	private String userPrizeName;

	/** 奖励金钱 */
	private String coin;

	/** 奖励物品 */
	private String item;

	/** 补偿类型 */
	private int type;

	/** 领取状态(1表示已经领取,0代表未领取) */
	private int status;

	/** 记录创建时间 */
	private Timestamp createTime;

	/** 记录更新时间 */
	private Timestamp updateTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column
	public String getPassportId() {
		return passportId;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}
	
	@Column
	public String getUserPrizeName() {
		return userPrizeName;
	}

	public void setUserPrizeName(String userPrizeName) {
		this.userPrizeName = userPrizeName;
	}

	@Column
	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	@Column
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
