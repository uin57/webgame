package com.pwrd.war.common.model.pet;



/**
 * 武将招募信息
 *
 */
public class PetHireInfo { 
	
	/** 是否可招募 **/
	private boolean canHire;
	/** 武将id */
	private String petSn; 
	
	
	protected int hireLevel; 
	/** 招募声望 */
	protected int hirePopularity; 
	/** 招募钱币id */
	protected int hireCoin; 
	/** 招募钱币数目 */
	protected int hireGold; 
	/** 招募道具1sn */
	protected String hireItem1Sn;  
	/** 招募道具1数量 */
	protected int hireItem1Num; 
	/** 招募道具2sn */
	protected String hireItem2Sn; 
	/** 招募道具2数量 */
	protected int hireItem2Num;

	public String getPetSn() {
		return petSn;
	}

	public void setPetSn(String petSn) {
		this.petSn = petSn;
	}

	public int getHireLevel() {
		return hireLevel;
	}

	public void setHireLevel(int hireLevel) {
		this.hireLevel = hireLevel;
	}

	public int getHirePopularity() {
		return hirePopularity;
	}

	public void setHirePopularity(int hirePopularity) {
		this.hirePopularity = hirePopularity;
	}

	public int getHireCoin() {
		return hireCoin;
	}

	public void setHireCoin(int hireCoin) {
		this.hireCoin = hireCoin;
	}

	public int getHireGold() {
		return hireGold;
	}

	public void setHireGold(int hireGold) {
		this.hireGold = hireGold;
	}

	public String getHireItem1Sn() {
		return hireItem1Sn;
	}

	public void setHireItem1Sn(String hireItem1Sn) {
		this.hireItem1Sn = hireItem1Sn;
	}

	public int getHireItem1Num() {
		return hireItem1Num;
	}

	public void setHireItem1Num(int hireItem1Num) {
		this.hireItem1Num = hireItem1Num;
	}

	public String getHireItem2Sn() {
		return hireItem2Sn;
	}

	public void setHireItem2Sn(String hireItem2Sn) {
		this.hireItem2Sn = hireItem2Sn;
	}

	public int getHireItem2Num() {
		return hireItem2Num;
	}

	public void setHireItem2Num(int hireItem2Num) {
		this.hireItem2Num = hireItem2Num;
	}

	

  

	public boolean getCanHire() {
		return canHire;
	}

	public void setCanHire(boolean canHire) {
		this.canHire = canHire;
	}

	
}
