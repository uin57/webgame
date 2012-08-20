package com.pwrd.war.gameserver.role.properties;



/**
 * 一般属性
 * 不需要发给客户端的
 */
public class HumanNormalProperty {
	private int totalDays;//总登陆天数
	private long buyVitalityDate;
	
	//已开启背包数目
	private     int bagSize; 
	//背包最大数目
	private     int maxBagSize; 
	//已开启仓库数目
	private  int depotSize; 
	//仓库最大数目
	private   int maxDepotSize;
	
	/** buff增加攻击 **/
	private double buffAtk;
	/** buff增加防御 **/
	private double buffDef;
	/** buff增加最大血量 **/
	private double buffMaxHp;

	/*******副本扫荡信息***********/
	private long lastRepTime;


	
	/***********修炼信息**************/


	////////神秘商店
	private long secretShopTime;		//上次神秘商店刷新的时间
	private long dayTaskTime;			//上次每日任务的时间
	private long towerRefreshTime;		//上次将星云路刷新时间
	


	
	/*******将星云路自动挑战***********/
	private boolean isInAutoTower;	//是否在副本扫荡
	private long autoTowerStartTime;	//开始时间,
	private long autoTowerLastTime;	//上次扫荡时间,
	private int autoTowerNum;	//扫荡的副本
	
	private long lastFightTime;	//上次战斗时间
	
	
	/** 敬酒相关***********************/
	private int jingjiu1;	//敬酒1的次数
	private int jingjiu2;
	private int jingjiu3;
	private long lastJingjiuTime;//上次敬酒时间
	
	private short newPrize;//领取在线礼包到第几步了
	private long newPrizeSTime;//开始计算在线礼包时间
	private boolean canChat;//是否禁言
	
	private String loginGifts;//已经领取的登陆奖励
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

	public long getSecretShopTime() {
		return secretShopTime;
	}

	public void setSecretShopTime(long secretShopTime) {
		this.secretShopTime = secretShopTime;
	}

//	public long getRobberyLastSelectTime() {
//		return robberyLastSelectTime;
//	}
//
//	public void setRobberyLastSelectTime(long robberyLastSelectTime) {
//		this.robberyLastSelectTime = robberyLastSelectTime;
//	}




	public long getDayTaskTime() {
		return dayTaskTime;
	}

	public void setDayTaskTime(long dayTaskTime) {
		this.dayTaskTime = dayTaskTime;
	}

	public long getTowerRefreshTime() {
		return towerRefreshTime;
	}

	public void setTowerRefreshTime(long towerRefreshTime) {
		this.towerRefreshTime = towerRefreshTime;
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


	public long getLastFightTime() {
		return lastFightTime;
	}

	public void setLastFightTime(long lastFightTime) {
		this.lastFightTime = lastFightTime;
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
