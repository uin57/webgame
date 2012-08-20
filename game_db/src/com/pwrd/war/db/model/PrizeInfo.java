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
 * 奖励信息
 * 
 * 
 */
@Entity
@Table(name = "t_prize")
public class PrizeInfo implements BaseEntity<Integer> {
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Integer Id;
	/** 奖励Id */
	private int prizeId;
	/** 奖励名称 */
	private String prizeName;
	/** 奖励金钱 */
	private String coin;
	/** 奖励物品 */
	private String item;
	/** 奖励宠物 */
	private String pet;
	/** 创建时间 */
	private Timestamp createTime;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.Id;
	}

	@Override
	public void setId(Integer id) {
		this.Id = id;
	}

	@Column
	public int getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(int prizeId) {
		this.prizeId = prizeId;
	}

	@Column
	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
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
	public String getPet() {
		return pet;
	}

	public void setPet(String pet) {
		this.pet = pet;
	}

	@Column
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}
