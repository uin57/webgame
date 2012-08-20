package com.pwrd.war.common.model.pet;

/**
 * 武将管理界面的整体信息
 * @author yue.yan
 *
 */
public class PetManagePanelInfo {

	/** 当前拥有的武将数量 */
	private int petsCount;
	/** 最大可拥有的武将数量 */
	private int maxPetsCount;
	
	public int getPetsCount() {
		return petsCount;
	}
	
	public void setPetsCount(int petsCount) {
		this.petsCount = petsCount;
	}
	
	public int getMaxPetsCount() {
		return maxPetsCount;
	}
	
	public void setMaxPetsCount(int maxPetsCount) {
		this.maxPetsCount = maxPetsCount;
	}
}
