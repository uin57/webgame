package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.gameserver.scene.Scene;

/**
 * 场景信息
 *  
 *
 */
public class SceneInfo {
	/** 场景ID **/
	private int scened;
	/** 名称 **/
	private String name;
	/** 类型 **/
	private int type;
	/** 需求等级 **/
	private int needLevel;
	/** 最大等级 **/
	private int maxLevel;
	/** 原始势力 **/
	private int orignCamp;
	/** 当前势力 **/
	private int nowCamp;
	/** 复活地图 **/
	private String reliveSceneId;
	/** 本图复活点x坐标  **/
	private int reliveX;
	/** 本图复活点y坐标 **/
	private int reliveY;
	/** 切换地图下一个地图id **/
	private String nextSceneId; 
	/** 所属区域id **/
	private int regionId;

	/**
	 * 类默认构造器
	 * 
	 */
	public SceneInfo() {
	}

	/**
	 * 类参数构造器
	 * 
	 * @param s
	 */
	public SceneInfo(Scene s) {
		if (s != null) {
//			this.id = s.getId();
//			this.typeId = s.getTypeId();
//			this.regionId = s.getRegionId();
//			this.name = s.getName();
//			this.x = s.getX();
//			this.y = s.getY();
//			this.image = s.getImage();
//			this.allianceId = s.getAllianceId();
		}
	}

	public int getScened() {
		return scened;
	}

	public void setScened(int scened) {
		this.scened = scened;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNeedLevel() {
		return needLevel;
	}

	public void setNeedLevel(int needLevel) {
		this.needLevel = needLevel;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public int getOrignCamp() {
		return orignCamp;
	}

	public void setOrignCamp(int orignCamp) {
		this.orignCamp = orignCamp;
	}

	public int getNowCamp() {
		return nowCamp;
	}

	public void setNowCamp(int nowCamp) {
		this.nowCamp = nowCamp;
	}

	public String getReliveSceneId() {
		return reliveSceneId;
	}

	public void setReliveSceneId(String reliveSceneId) {
		this.reliveSceneId = reliveSceneId;
	}

	public int getReliveX() {
		return reliveX;
	}

	public void setReliveX(int reliveX) {
		this.reliveX = reliveX;
	}

	public int getReliveY() {
		return reliveY;
	}

	public void setReliveY(int reliveY) {
		this.reliveY = reliveY;
	}

	public String getNextSceneId() {
		return nextSceneId;
	}

	public void setNextSceneId(String nextSceneId) {
		this.nextSceneId = nextSceneId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
 
}
