package com.pwrd.war.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_xiulian")
public class HumanXiulianEntity implements BaseEntity<String>  {
	/**  **/
	private static final long serialVersionUID = 1900847765485963745L;

	//这个id就是角色的id
	private String id;	
	
	private boolean isInXiulian;		//是否在修炼
	private long xiulianStartTime;	//修炼开始时间
	private long xiulianEndTime;	//修炼结束时间
	private long xiulianLastTime;	//最后一次修炼时间
	private int  xiulianAllExp;		//修炼获得累计经验
	
	private long xiulianCollectTimesLastTime;//上次给予采集次数的时间点
	private int xiulianTodayCollectTimes;	//今日采集次数
	private int xiulianTodayBeCollectTimes;	//今日被采集次数
	
	private long xiulianLastCollectTime;	//上次采集时间
	private int xiulianLevel;		//当前修炼境界等级
	private long xiulianLevelExpireTime;	//修炼境界等级过期时间
	
	private long xiulianLastSymbolTime;//上次取得修炼标志的时间
	private int xiulianSymbolId;		//当前的修炼标志ID
	private int xiulianHasCollectTimes;//当前标志,已经被采集的次数
	@Id
	@Override
	public String getId() { 
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;		
	}

	public boolean isInXiulian() {
		return isInXiulian;
	}

	public void setInXiulian(boolean isInXiulian) {
		this.isInXiulian = isInXiulian;
	}

	public long getXiulianStartTime() {
		return xiulianStartTime;
	}

	public void setXiulianStartTime(long xiulianStartTime) {
		this.xiulianStartTime = xiulianStartTime;
	}

	public long getXiulianEndTime() {
		return xiulianEndTime;
	}

	public void setXiulianEndTime(long xiulianEndTime) {
		this.xiulianEndTime = xiulianEndTime;
	}

	public long getXiulianLastTime() {
		return xiulianLastTime;
	}

	public void setXiulianLastTime(long xiulianLastTime) {
		this.xiulianLastTime = xiulianLastTime;
	}

	public int getXiulianAllExp() {
		return xiulianAllExp;
	}

	public void setXiulianAllExp(int xiulianAllExp) {
		this.xiulianAllExp = xiulianAllExp;
	}

	public long getXiulianCollectTimesLastTime() {
		return xiulianCollectTimesLastTime;
	}

	public void setXiulianCollectTimesLastTime(long xiulianCollectTimesLastTime) {
		this.xiulianCollectTimesLastTime = xiulianCollectTimesLastTime;
	}

	public int getXiulianTodayCollectTimes() {
		return xiulianTodayCollectTimes;
	}

	public void setXiulianTodayCollectTimes(int xiulianTodayCollectTimes) {
		this.xiulianTodayCollectTimes = xiulianTodayCollectTimes;
	}

	public int getXiulianTodayBeCollectTimes() {
		return xiulianTodayBeCollectTimes;
	}

	public void setXiulianTodayBeCollectTimes(int xiulianTodayBeCollectTimes) {
		this.xiulianTodayBeCollectTimes = xiulianTodayBeCollectTimes;
	}

	public long getXiulianLastCollectTime() {
		return xiulianLastCollectTime;
	}

	public void setXiulianLastCollectTime(long xiulianLastCollectTime) {
		this.xiulianLastCollectTime = xiulianLastCollectTime;
	}

	public int getXiulianLevel() {
		return xiulianLevel;
	}

	public void setXiulianLevel(int xiulianLevel) {
		this.xiulianLevel = xiulianLevel;
	}

	public long getXiulianLevelExpireTime() {
		return xiulianLevelExpireTime;
	}

	public void setXiulianLevelExpireTime(long xiulianLevelExpireTime) {
		this.xiulianLevelExpireTime = xiulianLevelExpireTime;
	}

	public long getXiulianLastSymbolTime() {
		return xiulianLastSymbolTime;
	}

	public void setXiulianLastSymbolTime(long xiulianLastSymbolTime) {
		this.xiulianLastSymbolTime = xiulianLastSymbolTime;
	}

	public int getXiulianSymbolId() {
		return xiulianSymbolId;
	}

	public void setXiulianSymbolId(int xiulianSymbolId) {
		this.xiulianSymbolId = xiulianSymbolId;
	}

	public int getXiulianHasCollectTimes() {
		return xiulianHasCollectTimes;
	}

	public void setXiulianHasCollectTimes(int xiulianHasCollectTimes) {
		this.xiulianHasCollectTimes = xiulianHasCollectTimes;
	}
}
