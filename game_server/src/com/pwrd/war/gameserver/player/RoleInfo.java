package com.pwrd.war.gameserver.player;

import java.sql.Timestamp;

import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.common.Globals;

public class RoleInfo {
	
	/** 账户id */
	private String passportId;
	/** 角色ID */
	private String roleUUID;
	/** 名字 */
	private String name;
	/** 所属阵营 Id */
	private int camp;
	/** 职业 **/
	private int vocation;
	/** 性别 **/
	private int sex;
	
	/** level **/
	private int level;
	/** exp **/
	private int curExp;
	/** maxExp  **/
	private int maxExp;
	/** hp **/
	private int curHp;
	/** maxHp **/
	private int maxHp;

	/** 是否为首次登陆 */
	private boolean firstLogin;
	
	public RoleInfo()
	{
		
	}
	
	public RoleInfo(String roleUUID, String name) {
		super();
		this.roleUUID = roleUUID;
		this.name = name;
		
	}
	
	public String getPassportId() {
		return passportId;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
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

	 
	public boolean getFirstLogin() {
		return this.firstLogin;
	}

	public void setFirstLogin(boolean value) {
		this.firstLogin = value;
	}

	public HumanEntity toEntity() {
		HumanEntity entity = new HumanEntity();
		entity.setPassportId(this.passportId);
		entity.setId(this.roleUUID);
		entity.setName(this.name);
		entity.setCamp(this.camp);
		entity.setSex(this.sex);
		entity.setVocation(this.vocation);
		entity.setCreateTime(Globals.getTimeService().now());
		return entity;
	}
	
	/**
	 * 创建角色时选择的自定义武将信息
	 *
	 */
	public static class PetSelection {
	
		/** 选择的模板Id */
		private int templateId;
		
		public int getTemplateId() {
			return templateId;
		}
		public void setTemplateId(int templateId) {
			this.templateId = templateId;
		}		
		
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
 
	public int getMaxExp() {
		return maxExp;
	}

	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}

	 

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getCurExp() {
		return curExp;
	}

	public void setCurExp(int curExp) {
		this.curExp = curExp;
	}

	public int getCurHp() {
		return curHp;
	}

	public void setCurHp(int curHp) {
		this.curHp = curHp;
	}

 

}
