package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 夺宝护送信息
 * @author xf
 */
@Entity
@Table(name = "t_robbery")
public class RobberyEntity implements BaseEntity<String> {
 
	private static final long serialVersionUID = 6299025578649208724L;
	private String id;
	
	//夺宝者UUID
	private String playerUUID;
	//名字
	private String playerName;
	//等级
	private int playerLevel;
	//所属阵营
	private	int camp; 
	//运送的宝物类型1-5
	private short robberyType;
 
	//被拦截次数
	private short beRobTimes;
	//拦截者UUID jsonarray数组
	private String robers;
	//护送者UUID
	private String protecterUUID;
	//护送者姓名
	private String protecterName;
 
	//护送者获得声望
	private int protPopu;
	//获得铜钱奖励
	private int getMoney;
	//获得声望奖励
	private int getPopularity;
	//原始获得铜钱
	private int omoney;
	//原始获得声望
	private int opopularity;
	//开始夺宝时间
	private long startTime;
	//结束夺宝时间
	private long endTime;
	
	@Id
	@Column(length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@Override
	public String getId() { 
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(String playerUUID) {
		this.playerUUID = playerUUID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}

	public int getCamp() {
		return camp;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}

	public short getRobberyType() {
		return robberyType;
	}

	public void setRobberyType(short robberyType) {
		this.robberyType = robberyType;
	}

	 

	public short getBeRobTimes() {
		return beRobTimes;
	}

	public void setBeRobTimes(short beRobTimes) {
		this.beRobTimes = beRobTimes;
	}

	public String getRobers() {
		return robers;
	}

	public void setRobers(String robers) {
		this.robers = robers;
	}

	public String getProtecterUUID() {
		return protecterUUID;
	}

	public void setProtecterUUID(String protecterUUID) {
		this.protecterUUID = protecterUUID;
	}

	public int getGetMoney() {
		return getMoney;
	}

	public void setGetMoney(int getMoney) {
		this.getMoney = getMoney;
	}

	public int getGetPopularity() {
		return getPopularity;
	}

	public void setGetPopularity(int getPopularity) {
		this.getPopularity = getPopularity;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getProtecterName() {
		return protecterName;
	}

	public void setProtecterName(String protecterName) {
		this.protecterName = protecterName;
	}

 

	public int getProtPopu() {
		return protPopu;
	}

	public void setProtPopu(int protPopu) {
		this.protPopu = protPopu;
	}

	public int getOmoney() {
		return omoney;
	}

	public void setOmoney(int omoney) {
		this.omoney = omoney;
	}

	public int getOpopularity() {
		return opopularity;
	}

	public void setOpopularity(int opopularity) {
		this.opopularity = opopularity;
	}

}
