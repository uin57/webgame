package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public class RoleEntity {

	private int level = 1;				//等级
	private String name;				//名称
	private int vocation ;			//职业 (战士，术师，侠隐，刺客，圣手)
	private int sex ;				//性别 (男、女)
	

	private int grow;				//特有成长度
	//
	private double curHp = 0;			//当前血量
	private int curExp = 0;			//当前经验
	private int maxExp = 0;			//升级经验  
	private double maxHp = 0;			//血量上限
	//一级属性
	private double atk = 0;		//攻击
	private double def = 0;		//防御
	private double cri = 0;		//暴击
	private double spd = 0;		//速度
	//特殊属性
	private double shanbi;			//闪避
	private double shanghai;		//伤害
	private double mianshang;		//免伤
	private double fanji;			//反击
	private double mingzhong;		//命中
	private double lianji;			//连击
	private double zhandouli;		//战斗力
	private double renxing;	        //韧性

	private String skeletonId;	//骨骼
//	private String avatar;		//头像
	/** 阵营 */
	private int camp; 
	
	private int maxGrow;	//最大成长
	private int transferLevel;	//转职等级
	private int transferstar;//星等级
	private int transferExp;//经验
	
	private String skill1;
	private String skill2;
	private String skill3;
	private String petSkill;	
	
	private String passSkill1;
	private String passSkill2;
	private String passSkill3;
	private String passSkill4;
	private String passSkill5;
	private String passSkill6;
	
	private int passSkillLevel1;
	private int passSkillLevel2;
	private int passSkillLevel3;
	private int passSkillLevel4;
	private int passSkillLevel5;
	private int passSkillLevel6;
	/*******************************/
	/** buff攻击 **/
	private double buffAtk;
	/** buff防御 **/
	private double buffDef;
	/** buff最大血量 **/
	private double buffMaxHp;
	
	/**************************/
	private int maxHpLevel;
	//攻击研究等级") 
	private int atkLevel; 
	//防御研究等级") 
	private int defLevel; 
	//近程攻击研究等级") 
	private int shortAtkLevel; 
	//近程防御研究等级") 
	private int shortDefLevel;
	//远程攻击研究等级") 
	private int remoteAtkLevel; 
	//远程防御研究等级") 
	private int remoteDefLevel;
	//近程攻击
	private double shortAtk;
	//近程防御
	private double shortDef;
	//远程攻击
	private double remoteAtk;
	//远程防御
	private double remoteDef;
	//暴击伤害
	private double criShanghai;
	
	
	
	@Column(columnDefinition = " int default 1", nullable = false)
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
 
	 
	public double getCurHp() {
		return curHp;
	}
	public void setCurHp(double curHp) {
		this.curHp = curHp;
	}
	public double getMaxHp() {
		return maxHp;
	}
	public void setMaxHp(double maxHp) {
		this.maxHp = maxHp;
	}
	public int getCurExp() {
		return curExp;
	}
	public void setCurExp(int curExp) {
		this.curExp = curExp;
	}
	public int getMaxExp() {
		return maxExp;
	}
	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}
	
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public void setDef(int def) {
		this.def = def;
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
	 
	 
	public int getCamp() {
		return camp;
	}
	public void setCamp(int camp) {
		this.camp = camp;
	}
 
	 
	public String getSkeletonId() {
		return skeletonId;
	}
	public void setSkeletonId(String skeletonId) {
		this.skeletonId = skeletonId;
	}
	public double getAtk() {
		return atk;
	}
	public void setAtk(double atk) {
		this.atk = atk;
	}
	public double getDef() {
		return def;
	}
	public void setDef(double def) {
		this.def = def;
	}
	public double getCri() {
		return cri;
	}
	public void setCri(double cri) {
		this.cri = cri;
	}
	public double getSpd() {
		return spd;
	}
	public void setSpd(double spd) {
		this.spd = spd;
	}
	public double getShanbi() {
		return shanbi;
	}
	public void setShanbi(double shanbi) {
		this.shanbi = shanbi;
	}
	public double getShanghai() {
		return shanghai;
	}
	public void setShanghai(double shanghai) {
		this.shanghai = shanghai;
	}
	public double getMianshang() {
		return mianshang;
	}
	public void setMianshang(double mianshang) {
		this.mianshang = mianshang;
	}
	public double getFanji() {
		return fanji;
	}
	public void setFanji(double fanji) {
		this.fanji = fanji;
	}
	public double getMingzhong() {
		return mingzhong;
	}
	public void setMingzhong(double mingzhong) {
		this.mingzhong = mingzhong;
	}
	public double getLianji() {
		return lianji;
	}
	public void setLianji(double lianji) {
		this.lianji = lianji;
	}
	public double getZhandouli() {
		return zhandouli;
	}
	public void setZhandouli(double zhandouli) {
		this.zhandouli = zhandouli;
	}
	public int getGrow() {
		return grow;
	}
	public void setGrow(int grow) {
		this.grow = grow;
	}
	public String getSkill1() {
		return skill1;
	}
	public void setSkill1(String skill1) {
		this.skill1 = skill1;
	}
	public String getSkill2() {
		return skill2;
	}
	public void setSkill2(String skill2) {
		this.skill2 = skill2;
	}
	public String getSkill3() {
		return skill3;
	}
	public void setSkill3(String skill3) {
		this.skill3 = skill3;
	}
	public String getPetSkill() {
		return petSkill;
	}
	public void setPetSkill(String petSkill) {
		this.petSkill = petSkill;
	}
	public double getBuffAtk() {
		return buffAtk;
	}
	public void setBuffAtk(double buffAtk) {
		this.buffAtk = buffAtk;
	}
	public double getBuffDef() {
		return buffDef;
	}
	public void setBuffDef(double buffDef) {
		this.buffDef = buffDef;
	}
	public double getBuffMaxHp() {
		return buffMaxHp;
	}
	public void setBuffMaxHp(double buffMaxHp) {
		this.buffMaxHp = buffMaxHp;
	}
	public int getMaxGrow() {
		return maxGrow;
	}
	public void setMaxGrow(int maxGrow) {
		this.maxGrow = maxGrow;
	}
	public int getTransferLevel() {
		return transferLevel;
	}
	public void setTransferLevel(int transferLevel) {
		this.transferLevel = transferLevel;
	}
	public String getPassSkill1() {
		return passSkill1;
	}
	public void setPassSkill1(String passSkill1) {
		this.passSkill1 = passSkill1;
	}
	public String getPassSkill2() {
		return passSkill2;
	}
	public void setPassSkill2(String passSkill2) {
		this.passSkill2 = passSkill2;
	}
	public String getPassSkill3() {
		return passSkill3;
	}
	public void setPassSkill3(String passSkill3) {
		this.passSkill3 = passSkill3;
	}
	public String getPassSkill4() {
		return passSkill4;
	}
	public void setPassSkill4(String passSkill4) {
		this.passSkill4 = passSkill4;
	}
	public int getAtkLevel() {
		return atkLevel;
	}
	public void setAtkLevel(int atkLevel) {
		this.atkLevel = atkLevel;
	}
	public int getDefLevel() {
		return defLevel;
	}
	public void setDefLevel(int defLevel) {
		this.defLevel = defLevel;
	}
	public int getShortAtkLevel() {
		return shortAtkLevel;
	}
	public void setShortAtkLevel(int shortAtkLevel) {
		this.shortAtkLevel = shortAtkLevel;
	}
	public int getShortDefLevel() {
		return shortDefLevel;
	}
	public void setShortDefLevel(int shortDefLevel) {
		this.shortDefLevel = shortDefLevel;
	}
	public int getRemoteAtkLevel() {
		return remoteAtkLevel;
	}
	public void setRemoteAtkLevel(int remoteAtkLevel) {
		this.remoteAtkLevel = remoteAtkLevel;
	}
	public int getRemoteDefLevel() {
		return remoteDefLevel;
	}
	public void setRemoteDefLevel(int remoteDefLevel) {
		this.remoteDefLevel = remoteDefLevel;
	}
	public double getShortAtk() {
		return shortAtk;
	}
	public void setShortAtk(double shortAtk) {
		this.shortAtk = shortAtk;
	}
	public double getShortDef() {
		return shortDef;
	}
	public void setShortDef(double shortDef) {
		this.shortDef = shortDef;
	}
	public double getRemoteAtk() {
		return remoteAtk;
	}
	public void setRemoteAtk(double remoteAtk) {
		this.remoteAtk = remoteAtk;
	}
	public double getRemoteDef() {
		return remoteDef;
	}
	public void setRemoteDef(double remoteDef) {
		this.remoteDef = remoteDef;
	}
	public int getMaxHpLevel() {
		return maxHpLevel;
	}
	public void setMaxHpLevel(int maxHpLevel) {
		this.maxHpLevel = maxHpLevel;
	}
	public String getPassSkill5() {
		return passSkill5;
	}
	public void setPassSkill5(String passSkill5) {
		this.passSkill5 = passSkill5;
	}
	public String getPassSkill6() {
		return passSkill6;
	}
	public void setPassSkill6(String passSkill6) {
		this.passSkill6 = passSkill6;
	}
	public int getTransferstar() {
		return transferstar;
	}
	public void setTransferstar(int transferstar) {
		this.transferstar = transferstar;
	}
	public int getTransferExp() {
		return transferExp;
	}
	public void setTransferExp(int transferExp) {
		this.transferExp = transferExp;
	}
	public int getPassSkillLevel1() {
		return passSkillLevel1;
	}
	public void setPassSkillLevel1(int passSkillLevel1) {
		this.passSkillLevel1 = passSkillLevel1;
	}
	public int getPassSkillLevel2() {
		return passSkillLevel2;
	}
	public void setPassSkillLevel2(int passSkillLevel2) {
		this.passSkillLevel2 = passSkillLevel2;
	}
	public int getPassSkillLevel3() {
		return passSkillLevel3;
	}
	public void setPassSkillLevel3(int passSkillLevel3) {
		this.passSkillLevel3 = passSkillLevel3;
	}
	public int getPassSkillLevel4() {
		return passSkillLevel4;
	}
	public void setPassSkillLevel4(int passSkillLevel4) {
		this.passSkillLevel4 = passSkillLevel4;
	}
	public int getPassSkillLevel5() {
		return passSkillLevel5;
	}
	public void setPassSkillLevel5(int passSkillLevel5) {
		this.passSkillLevel5 = passSkillLevel5;
	}
	public int getPassSkillLevel6() {
		return passSkillLevel6;
	}
	public void setPassSkillLevel6(int passSkillLevel6) {
		this.passSkillLevel6 = passSkillLevel6;
	}
	public double getRenxing() {
		return renxing;
	}
	public void setRenxing(double renxing) {
		this.renxing = renxing;
	}
	public double getCriShanghai() {
		return criShanghai;
	}
	public void setCriShanghai(double criShanghai) {
		this.criShanghai = criShanghai;
	}
	
}
