package com.pwrd.war.db.model.cache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.CacheEntity;

/**
 * 
 * HumanInfo的缓存实体,主要是城市的基本信息,便于其他玩家查看.不包含战斗相关信息
 * @author yue.yan
 *
 */
@Entity
@Table(name = "t_character_info_cache")
public class HumanCacheEntity implements CacheEntity<Long>{

	/** */
	private static final long serialVersionUID = 1L;

	/** 玩家角色ID 主键 */
	private long id;
	
	/** 姓名 */
	private String name;
	/** 级别 */
	private short level;
	/** 头像 */
	private short photo;	
	/** 排名 */
	private int rank;
	/** 阵营 */
	private short alliance;
	/** 个人签名 */
	private String signature;	

	/**
	 * 货币属性
	 */
	/** 金币数量[元宝] */
	private int gold; 
	/** 礼券数量 */
	private int coupon;	
	/** 铜币数量*/
	private int copper;
	
	/**
	 * 资源属性
	 */
	/** 粮食数量 */
	private int food;
	/** 石料数量 */
	private int stone;
	/** 木材数量 */
	private int wood;
	/** 青铜数量 */
	private int bronze;
	
	@Id
	@Override
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}	
	
	public void setId(long id) {
		this.id = id;
	}	
	
	@Column(length = 36)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Column
	public short getLevel() {
		return level;
	}
	
	public void setLevel(short level) {
		this.level = level;
	}

	@Column
	public short getAlliance() {
		return alliance;
	}

	public void setAlliance(short alliance) {
		this.alliance = alliance;
	}

	@Column
	public short getPhoto() {
		return photo;
	}
	
	public void setPhoto(short photo) {
		this.photo = photo;
	}

	@Column
	public int getRank() {
		return rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}

	
	@Column
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Column
	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	@Column
	public int getCoupon() {
		return coupon;
	}

	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}

	@Column
	public int getCopper() {
		return copper;
	}

	public void setCopper(int copper) {
		this.copper = copper;
	}

	@Column
	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	@Column
	public int getStone() {
		return stone;
	}

	public void setStone(int stone) {
		this.stone = stone;
	}

	@Column
	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	@Column
	public int getBronze() {
		return bronze;
	}

	public void setBronze(int bronze) {
		this.bronze = bronze;
	}

}
