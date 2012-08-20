package com.pwrd.war.gameserver.fight.domain;


/**
 * 战斗中角色初始和结束信息
 * @author zy
 *
 */
public class FightRoleInfo {
	private String roleSn;		//角色编号
	private String roleName;	//角色名称
	private String skeletonSn;	//角色骨骼
	private int initMorale;		//初始士气
	private int line;			//角色所在线
	private int initPos;		//角色初始位置
	private int formPos;		//角色阵型位置
	private int initHp;			//角色初始血量
	private int finalHp;		//角色战斗后血量
	private int maxHp;			//角色最大血量
	private boolean isAttack;	//是否攻击方
	private boolean isRemote;	//是否为远程职业
	private int index;			//角色战场编号
	private int roleType;		//角色类型
	private int exp;			//结束后经验奖励
	private int money;			//结束后金钱奖励
	private int level;			//角色等级
	private int star;			//角色星级
	private int gender;			//角色性别
	private String attackedAnim;	//受击动画
	private String castAnim;	//弹道动画
	private String vocationAnim;	//职业特性弹道动画
	private boolean isVisible;	//初始时是否可见
	private int maxMorale;		//最大士气
	private int skillMorale;	//技能士气
	private int belongs;		//所属角色编号，对障碍、召唤的单元有效，其他单元编号为自己

	public FightRoleInfo() {
	}
	
	public FightRoleInfo(String roleSn, String roleName, String skeletonSn, int initMorale, int line, int initPos,
			int formPos, int initHp, int finalHp, int maxHp, boolean isAttack, boolean isRemote, int index,
			int roleType, int level, int star, int gender, String attackedAnim, String castAnim, String vocationAnim,
			boolean isVisible, int maxMorale, int skillMorale, int belongs) {
		this.roleSn = roleSn;
		this.roleName = roleName;
		this.skeletonSn = skeletonSn;
		this.initMorale = initMorale;
		this.line = line;
		this.initPos = initPos;
		this.formPos = formPos;
		this.initHp = initHp;
		this.finalHp = finalHp;
		this.maxHp = maxHp;
		this.isAttack = isAttack;
		this.isRemote = isRemote;
		this.index = index;
		this.roleType = roleType;
		this.exp = 0;
		this.money = 0;
		this.level = level;
		this.star = star;
		this.gender = gender;
		this.attackedAnim = attackedAnim;
		this.castAnim = castAnim;
		this.vocationAnim = vocationAnim;
		this.isVisible = isVisible;
		this.maxMorale = maxMorale;
		this.skillMorale = skillMorale;
		this.belongs = belongs;
	}
	
	public String getRoleSn() {
		return roleSn;
	}

	public void setRoleSn(String roleSn) {
		this.roleSn = roleSn;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSkeletonSn() {
		return skeletonSn;
	}

	public void setSkeletonSn(String skeletonSn) {
		this.skeletonSn = skeletonSn;
	}

	public int getInitMorale() {
		return initMorale;
	}

	public void setInitMorale(int initMorale) {
		this.initMorale = initMorale;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getInitPos() {
		return initPos;
	}

	public void setInitPos(int initPos) {
		this.initPos = initPos;
	}

	public int getFormPos() {
		return formPos;
	}

	public void setFormPos(int formPos) {
		this.formPos = formPos;
	}

	public int getInitHp() {
		return initHp;
	}

	public void setInitHp(int initHp) {
		this.initHp = initHp;
	}

	public int getFinalHp() {
		return finalHp;
	}

	public void setFinalHp(int finalHp) {
		this.finalHp = finalHp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public boolean getIsAttack() {
		return isAttack;
	}

	public void setIsAttack(boolean isAttack) {
		this.isAttack = isAttack;
	}
	
	public boolean getIsRemote() {
		return isRemote;
	}

	public void setIsRemote(boolean isRemote) {
		this.isRemote = isRemote;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getAttackedAnim() {
		return attackedAnim;
	}

	public void setAttackedAnim(String attackedAnim) {
		this.attackedAnim = attackedAnim;
	}

	public String getCastAnim() {
		return castAnim;
	}

	public void setCastAnim(String castAnim) {
		this.castAnim = castAnim;
	}

	public String getVocationAnim() {
		return vocationAnim;
	}

	public void setVocationAnim(String vocationAnim) {
		this.vocationAnim = vocationAnim;
	}

	public boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public int getMaxMorale() {
		return maxMorale;
	}

	public void setMaxMorale(int maxMorale) {
		this.maxMorale = maxMorale;
	}

	public int getSkillMorale() {
		return skillMorale;
	}

	public void setSkillMorale(int skillMorale) {
		this.skillMorale = skillMorale;
	}

	public int getBelongs() {
		return belongs;
	}

	public void setBelongs(int belongs) {
		this.belongs = belongs;
	}
	
	
}
