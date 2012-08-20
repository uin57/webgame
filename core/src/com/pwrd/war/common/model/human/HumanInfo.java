package com.pwrd.war.common.model.human;

import net.sf.json.JSONObject;

/**
 * 玩家角色信息
 *
 */
public class HumanInfo {
	
	/** 玩家角色ID 主键 */
	private String roleUUID;
	/** 姓名 */
	private String name;
	/**阵营 */
	private int camp; 
	/** 性别 */
	private int sex;
	/** 职业 */
	private int vocation;
	/** 级别 */
	private int level; 
	/** 所属国家势力对应的Scene */
	private String sceneId; 
	/** 分线号 **/
	private int lineNo;
	/** 整个地图的x */
	private int x;
	/** 整个地图的y */
	private int y;
	
	/** 目标点x **/
	private int toX;
	/** 目标点y **/
	private int toY;
	
	/** 玩家状态 **/
	private int humanStatus;
	
	/** 当前武器sn **/
	private String weaponSn;
	
	/** 变身后的骨骼sn，如果为空表示默认 **/
	private String bodySn;
	//修炼标志ID
	private int xiulianSymbolId;
	//已经采集次数
	private int xiulianCollectTimes;
	//总可采集次数
	private int xiulianAllCollectTimes;
	
	public HumanInfo() {
	}

	public String getRoleUUID() {
		return roleUUID;
	}

	public void setRoleUUID(String roleUUID) {
		this.roleUUID = roleUUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

 

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
 

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	 

	public int getCamp() {
		return camp;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}

	public int getVocation() {
		return vocation;
	}

	public void setVocation(int vocation) {
		this.vocation = vocation;
	}

	 

	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() { 
		return JSONObject.fromObject(this).toString();
	}

	public int getToX() {
		return toX;
	}

	public void setToX(int toX) {
		this.toX = toX;
	}

	public int getToY() {
		return toY;
	}

	public void setToY(int toY) {
		this.toY = toY;
	}

	public int getHumanStatus() {
		return humanStatus;
	}

	public void setHumanStatus(int humanStatus) {
		this.humanStatus = humanStatus;
	}

	public String getWeaponSn() {
		return weaponSn;
	}

	public void setWeaponSn(String weaponSn) {
		this.weaponSn = weaponSn;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public int getXiulianSymbolId() {
		return xiulianSymbolId;
	}

	public void setXiulianSymbolId(int xiulianSymbolId) {
		this.xiulianSymbolId = xiulianSymbolId;
	}

	public int getXiulianCollectTimes() {
		return xiulianCollectTimes;
	}

	public void setXiulianCollectTimes(int xiulianCollectTimes) {
		this.xiulianCollectTimes = xiulianCollectTimes;
	}

	public int getXiulianAllCollectTimes() {
		return xiulianAllCollectTimes;
	}

	public void setXiulianAllCollectTimes(int xiulianAllCollectTimes) {
		this.xiulianAllCollectTimes = xiulianAllCollectTimes;
	}

	public String getBodySn() {
		return bodySn;
	}

	public void setBodySn(String bodySn) {
		this.bodySn = bodySn;
	}
	
	
	
	
}
