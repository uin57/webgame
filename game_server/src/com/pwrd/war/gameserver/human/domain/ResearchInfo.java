package com.pwrd.war.gameserver.human.domain;

import net.sf.json.JSONObject;

public class ResearchInfo {
	private int researchType; //研究类型
	private String researchName; //研究名称
	private int level; //当前等级
	private int needLevel; //升级需求等级
	private int needSee; //需要阅历
	private int coolTime; //冷却时间
	private String levelAddDesc;	//每级增加值
	private String icon;
	
	 
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getNeedLevel() {
		return needLevel;
	}
	public void setNeedLevel(int needLevel) {
		this.needLevel = needLevel;
	}
	public int getNeedSee() {
		return needSee;
	}
	public void setNeedSee(int needSee) {
		this.needSee = needSee;
	}
	public int getCoolTime() {
		return coolTime;
	}
	public void setCoolTime(int coolTime) {
		this.coolTime = coolTime;
	}
	public String getLevelAddDesc() {
		return levelAddDesc;
	}
	public void setLevelAddDesc(String levelAddDesc) {
		this.levelAddDesc = levelAddDesc;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getResearchType() {
		return researchType;
	}
	public void setResearchType(int researchType) {
		this.researchType = researchType;
	}
	public String getResearchName() {
		return researchName;
	}
	public void setResearchName(String researchName) {
		this.researchName = researchName;
	}
	 
	public String toString(){
		return JSONObject.fromObject(this).toString();
	}
	
}
