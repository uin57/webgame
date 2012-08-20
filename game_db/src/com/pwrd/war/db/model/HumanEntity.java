package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 数据库实体类：角色信息，暂时先放在这儿
 * 
 */
@Entity
@Table(name = "t_character_info")
public class HumanEntity extends RoleEntity implements BaseEntity<String> {
	/** */
	private static final long serialVersionUID = 1L;

	/** 玩家角色ID 主键 */
	private String id;
	/** 玩家账号ID */
	private String passportId;
	/** 创建时间 */
	private long createTime;
	/** 是否已经被删除 */
	private int deleted;
	/** 删除日期 */
	private long deleteTime;
	/** 上次登陆时间 */
	private long lastLoginTime;
	/** 上次登出时间 */
	private long lastLogoutTime;
	/** 上次登陆IP */
	private String lastLoginIp;
	/** 累计在线时长(分钟) */
	private int totalMinute;
	/** 累计登陆天数 **/
	private int totalDays;
	/** 在线状态 */
	private int onlineStatus;
	/** 空闲时间 */
	private int idleTime;
 
	/** 当日充值数额 */
	private int todayCharge;
	/** 总充值数额 */
	private int totalCharge;
	/** 最后充值时间 */
	private long lastChargeTime;
	/** 最后成为某级别vip的时间 */
	private long lastVipTime;
	/** 神秘商店刷新时间 */
	private long lastSecretShopTime;
	/** 每日任务刷新时间 */
	private long lastDayTaskTime;
	/** 将星云路刷新时间 */
	private long lastTowerRefreshTime;
	/** 上次战斗时间 */
	private long lastFightTime;
	/** 上次副本时间*/
	private long lastRepTime;
	
	
	
	/**
	 * 基本属性 
	 */ 
	/** X坐标 */
	private int x;
	/** Y坐标 */
	private int y; 
	/** 当前地图ID **/
	private String sceneId;

	
	
	/** 称号 */
	private int title;
	/** 体力 */
	private int vitality;
	/** 最大体力 */
	private int maxVitality;
	/** 体力购买次数*/
	private int buyVitTimes;
	
	/**上次购买体力的时间*/
	private long buyVitalityDate;
	
	/** 魅力 */
	private int charm;
	/** 护花值 */
	private int protectFlower;
	/** 官职 */
	private int offical;
	/** 家族id **/
	private String familyId;
	/** 家族职位 **/
	private int familyRole;
	/** 荣誉 */
	private int honour;
	/** 杀戮 */
	private int massacre;	
	/** 战功值 */
	private int battleAchieve;	 
	/** 阅历 **/
	private int see;	
	/** 声望 **/
	private int popularity;
	/** 游戏状态 **/
	private int gamingStatus;
	/** 0:表示没有vip 1~7表示vip等级 **/
	private int vip;
	/** 体验vip等级 */
	private int buffVip;
	
	/**
	 * 货币属性
	 */
	/** 金币数量[元宝] */
	private int gold; 
	/** 礼券数量 */
	private int coupon;	
//	/** 银两数量*/
//	private int sliver;
	/** 碎银数量 **/
	private int coins;
	
	/** 魂石 */
	private int hunshi;
	

 
	
	/**  自动战斗状态 **/
	private boolean muanBattle;
	
	/** 是否开启阵法 **/
	private boolean isOpenForm;
	
 
	
	/** 已开启强化队列数目 **/
	private int equipEnhanceNum;
	
	/** 是否在战斗中 **/
	private boolean isInBattle;
	
	/** 背包大小 **/
	private int bagSize; 
	/** 背包最大格子大小 **/
	private int maxBagSize;
	
	/** 仓库大小 **/
	private int depotSize;
	/** 最大仓库有大小 **/
	private int maxDepotSize;
	
	
	private int merit;	//功勋值
	
	/**
	 * 以下是联合属性
	 */
	/** 属性信息 */
	private String props;

	
	/** 进入副本前地图信息*/
	private String beforeSenceId;
	private int beforeX;
	private int beforeY;
	

	private String personSign;//个性签名
	private String openFunctions;//开启的功能
	private int chargeAmount;//已充值金额
	private String loginGifts;//已经领取的登陆奖励
	/*******将星云路自动挑战***********/
	private boolean isInAutoTower;	//是否在副本扫荡
	private long autoTowerStartTime;	//开始时间,
	private long autoTowerLastTime;	//上次扫荡时间,
	private int autoTowerNum;	//扫荡的副本
	
	/** 敬酒相关***********************/
	private int jingjiu1;	//敬酒1的次数
	private int jingjiu2;
	private int jingjiu3;
	private long lastJingjiuTime;//上次敬酒时间
	
	private short newPrize;//领取在线礼包到第几步了
	private long newPrizeSTime;//开始计算在线礼包时间
	private boolean canChat;//是否禁言
	@Column
	public String getPassportId() {
		return passportId;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}

 

 
	@Column(columnDefinition = " int default 0", nullable = false)
	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	 
	 
	@Column
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getTotalMinute() {
		return totalMinute;
	}

	public void setTotalMinute(int totalMinute) {
		this.totalMinute = totalMinute;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(int idleTime) {
		this.idleTime = idleTime;
	}

	 
	@Column(columnDefinition = " int default 0", nullable = false)
	public int getTodayCharge() {
		return todayCharge;
	}

	public void setTodayCharge(int todayCharge) {
		this.todayCharge = todayCharge;
	}
		
	@Column(columnDefinition = " int default 0", nullable = false)
	public int getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(int totalCharge) {
		this.totalCharge = totalCharge;
	}
 
	public String getSceneId() {
		return sceneId;
	}

	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}

	@Column(columnDefinition = " int default 0", nullable = false)
	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
 
	@Column(columnDefinition = " int default 0", nullable = false)
	public int getCoupon() {
		return coupon;
	}

	public void setCoupon(int coupon) {
		this.coupon = coupon;
	}



	@Column(columnDefinition = "text", nullable = true)
	public String getProps() {
		return this.props;
	}

	public void setProps(String value) {
		this.props = value;
	}

 
	 
	@Id
	@Column
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	public String  getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public int getVitality() {
		return vitality;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public int getMaxVitality() {
		return maxVitality;
	}

	public void setMaxVitality(int maxVitality) {
		this.maxVitality = maxVitality;
	}
	
	@Column
	public int getBuyVitTimes() {
		return buyVitTimes;
	}

	public void setBuyVitTimes(int buyVitTimes) {
		this.buyVitTimes = buyVitTimes;
	}
	
	public int getCharm() {
		return charm;
	}

	public void setCharm(int charm) {
		this.charm = charm;
	}

	public int getProtectFlower() {
		return protectFlower;
	}

	public void setProtectFlower(int protectFlower) {
		this.protectFlower = protectFlower;
	}

	public int getOffical() {
		return offical;
	}

	public void setOffical(int offical) {
		this.offical = offical;
	}

	@Column
	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public int getHonour() {
		return honour;
	}

	public void setHonour(int honour) {
		this.honour = honour;
	}

	public int getMassacre() {
		return massacre;
	}

	public void setMassacre(int massacre) {
		this.massacre = massacre;
	}

	public int getBattleAchieve() {
		return battleAchieve;
	}

	public void setBattleAchieve(int battleAchieve) {
		this.battleAchieve = battleAchieve;
	}

	public int getSee() {
		return see;
	}

	public void setSee(int see) {
		this.see = see;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
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


	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	 

	public boolean isMuanBattle() {
		return muanBattle;
	}

	public void setMuanBattle(boolean muanBattle) {
		this.muanBattle = muanBattle;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	public int getGamingStatus() {
		return gamingStatus;
	}

	public void setGamingStatus(int gamingStatus) {
		this.gamingStatus = gamingStatus;
	}

	public boolean isOpenForm() {
		return isOpenForm;
	}

	public void setOpenForm(boolean isOpenForm) {
		this.isOpenForm = isOpenForm;
	}
	
	 

	public int getEquipEnhanceNum() {
		return equipEnhanceNum;
	}

	public void setEquipEnhanceNum(int equipEnhanceNum) {
		this.equipEnhanceNum = equipEnhanceNum;
	}
	
	public String getBeforeSenceId() {
		return beforeSenceId;
	}

	public void setBeforeSenceId(String beforeSenceId) {
		this.beforeSenceId = beforeSenceId;
	}

	public int getBeforeX() {
		return beforeX;
	}

	public void setBeforeX(int beforeX) {
		this.beforeX = beforeX;
	}

	public int getBeforeY() {
		return beforeY;
	}

	public void setBeforeY(int beforeY) {
		this.beforeY = beforeY;
	}

	public boolean isInBattle() {
		return isInBattle;
	}

	public void setInBattle(boolean isInBattle) {
		this.isInBattle = isInBattle;
	}

	@Column
	public long getBuyVitalityDate() {
		return buyVitalityDate;
	}

	public void setBuyVitalityDate(long buyVitalityDate) {
		this.buyVitalityDate = buyVitalityDate;
	}

	public int getBagSize() {
		return bagSize;
	}

	public void setBagSize(int bagSize) {
		this.bagSize = bagSize;
	}

	public int getMaxBagSize() {
		return maxBagSize;
	}

	public void setMaxBagSize(int maxBagSize) {
		this.maxBagSize = maxBagSize;
	}

	public int getDepotSize() {
		return depotSize;
	}

	public void setDepotSize(int depotSize) {
		this.depotSize = depotSize;
	}

	public int getMaxDepotSize() {
		return maxDepotSize;
	}

	public void setMaxDepotSize(int maxDepotSize) {
		this.maxDepotSize = maxDepotSize;
	}

	public int getMerit() {
		return merit;
	}

	public void setMerit(int merit) {
		this.merit = merit;
	}


	public String getPersonSign() {
		return personSign;
	}

	public void setPersonSign(String personSign) {
		this.personSign = personSign;
	}

	public long getLastSecretShopTime() {
		return lastSecretShopTime;
	}

	public void setLastSecretShopTime(long lastSecretShopTime) {
		this.lastSecretShopTime = lastSecretShopTime;
	}

	public int getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(int chargeAmount) {
		this.chargeAmount = chargeAmount;
	}


	public long getLastDayTaskTime() {
		return lastDayTaskTime;
	}

	public void setLastDayTaskTime(long lastDayTaskTime) {
		this.lastDayTaskTime = lastDayTaskTime;
	}

	public long getLastTowerRefreshTime() {
		return lastTowerRefreshTime;
	}

	public void setLastTowerRefreshTime(long lastTowerRefreshTime) {
		this.lastTowerRefreshTime = lastTowerRefreshTime;
	}

	@Column
	public long getLastFightTime() {
		return lastFightTime;
	}

	public void setLastFightTime(long lastFightTime) {
		this.lastFightTime = lastFightTime;
	}

	@Column
	public int getFamilyRole() {
		return familyRole;
	}

	public void setFamilyRole(int familyRole) {
		this.familyRole = familyRole;
	}

	public boolean isInAutoTower() {
		return isInAutoTower;
	}

	public void setInAutoTower(boolean isInAutoTower) {
		this.isInAutoTower = isInAutoTower;
	}

	public long getAutoTowerStartTime() {
		return autoTowerStartTime;
	}

	public void setAutoTowerStartTime(long autoTowerStartTime) {
		this.autoTowerStartTime = autoTowerStartTime;
	}

	public long getAutoTowerLastTime() {
		return autoTowerLastTime;
	}

	public void setAutoTowerLastTime(long autoTowerLastTime) {
		this.autoTowerLastTime = autoTowerLastTime;
	}

	public int getAutoTowerNum() {
		return autoTowerNum;
	}

	public void setAutoTowerNum(int autoTowerNum) {
		this.autoTowerNum = autoTowerNum;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(long deleteTime) {
		this.deleteTime = deleteTime;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public long getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(long lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

	public long getLastChargeTime() {
		return lastChargeTime;
	}

	public void setLastChargeTime(long lastChargeTime) {
		this.lastChargeTime = lastChargeTime;
	}

	public long getLastVipTime() {
		return lastVipTime;
	}

	public void setLastVipTime(long lastVipTime) {
		this.lastVipTime = lastVipTime;
	}

	public long getLastRepTime() {
		return lastRepTime;
	}

	public void setLastRepTime(long lastRepTime) {
		this.lastRepTime = lastRepTime;
	}

	public int getJingjiu1() {
		return jingjiu1;
	}

	public void setJingjiu1(int jingjiu1) {
		this.jingjiu1 = jingjiu1;
	}

	public int getJingjiu2() {
		return jingjiu2;
	}

	public void setJingjiu2(int jingjiu2) {
		this.jingjiu2 = jingjiu2;
	}

	public int getJingjiu3() {
		return jingjiu3;
	}

	public void setJingjiu3(int jingjiu3) {
		this.jingjiu3 = jingjiu3;
	}

	public long getLastJingjiuTime() {
		return lastJingjiuTime;
	}

	public void setLastJingjiuTime(long lastJingjiuTime) {
		this.lastJingjiuTime = lastJingjiuTime;
	}

	public int getHunshi() {
		return hunshi;
	}

	public void setHunshi(int hunshi) {
		this.hunshi = hunshi;
	}

	@Column
	public int getBuffVip() {
		return buffVip;
	}

	public void setBuffVip(int buffVip) {
		this.buffVip = buffVip;
	}

	public String getOpenFunctions() {
		return openFunctions;
	}

	public void setOpenFunctions(String openFunctions) {
		this.openFunctions = openFunctions;
	}

	public short getNewPrize() {
		return newPrize;
	}

	public void setNewPrize(short newPrize) {
		this.newPrize = newPrize;
	}

	public long getNewPrizeSTime() {
		return newPrizeSTime;
	}

	public void setNewPrizeSTime(long newPrizeSTime) {
		this.newPrizeSTime = newPrizeSTime;
	}

	public boolean isCanChat() {
		return canChat;
	}

	public void setCanChat(boolean canChat) {
		this.canChat = canChat;
	}

	public int getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}

	public String getLoginGifts() {
		return loginGifts;
	}

	public void setLoginGifts(String loginGifts) {
		this.loginGifts = loginGifts;
	}
	
}
