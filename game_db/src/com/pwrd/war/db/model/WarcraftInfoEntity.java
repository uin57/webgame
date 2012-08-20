/**
 * 
 */
package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * @author dendgan
 * 
 * 玩家兵法信息实体类
 *
 */
@Entity
@Table(name = "t_warcraft_info")
public class WarcraftInfoEntity implements BaseEntity<String> {

	private static final long serialVersionUID = -913937977164256354L;
	/** 玩家兵法信息实例id */
	private String id;
	/** 玩家id */
	private String charId;
	/** 玩家兵法阶段 */
	private int stage;
	/** 玩家兵法领悟度 */
	private int comprehend;
	/** 领悟兵法累计消耗铜钱 */
	private int totalCost;
	/** 兵法碎片 */
	private int warcraftCoin;
	/** 是否处于召唤申通状态 0不是1是*/
	private int specialGrasp;
	/** 上次领悟更新时间 */
	private long lastGraspUpdateTime;
	/** 已领取的奖励兵法id集合 */
	private String havePrizeIds;
	
	@Id
	@Column
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column
	public String getCharId() {
		return charId;
	}
	public void setCharId(String charId) {
		this.charId = charId;
	}
	@Column
	public int getStage() {
		return stage;
	}
	public void setStage(int stage) {
		this.stage = stage;
	}
	@Column
	public int getComprehend() {
		return comprehend;
	}
	public void setComprehend(int comprehend) {
		this.comprehend = comprehend;
	}
	@Column
	public int getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	@Column
	public int getWarcraftCoin() {
		return warcraftCoin;
	}
	public void setWarcraftCoin(int warcraftCoin) {
		this.warcraftCoin = warcraftCoin;
	}
	@Column
	public long getLastGraspUpdateTime() {
		return lastGraspUpdateTime;
	}
	public void setLastGraspUpdateTime(long lastGraspUpdateTime) {
		this.lastGraspUpdateTime = lastGraspUpdateTime;
	}
	@Column
	public String getHavePrizeIds() {
		return havePrizeIds;
	}
	public void setHavePrizeIds(String havePrizeIds) {
		this.havePrizeIds = havePrizeIds;
	}
	@Column
	public int getSpecialGrasp() {
		return specialGrasp;
	}
	public void setSpecialGrasp(int specialGrasp) {
		this.specialGrasp = specialGrasp;
	}
	
}
