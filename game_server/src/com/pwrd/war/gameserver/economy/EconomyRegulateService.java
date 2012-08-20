package com.pwrd.war.gameserver.economy;

import com.pwrd.war.gameserver.economy.EconomyDef.EconomyConfigType;

/**
 * 经济调控服务
 * 用以在全局调节游戏内各系统的收益
 * @author yue.yan
 *
 */
public class EconomyRegulateService {

	private int[] economyConfigs;
	
	public EconomyRegulateService() {
		economyConfigs = new int[EconomyConfigType.values().length];
	}
	
	/**
	 * 获得特定经济配置的值
	 * @param economyConfigType
	 * @return
	 */
	public int getEconomyConfig(EconomyConfigType economyConfigType) {
		return economyConfigs[economyConfigType.getIndex()];
	}
	
	/**
	 * 改变特定经济配置的值
	 * @param economyConfigType
	 * @param value
	 */
	public void changeEconomyConfig(EconomyConfigType economyConfigType, int value) {
		economyConfigs[economyConfigType.getIndex()] += value;
	}
	
	/**
	 * 重置所有经济配置的值
	 */
	public void resetEconomyConfigs() {
		for(int i = 0; i < economyConfigs.length; i++) {
			economyConfigs[i] = 0;
		}
	}
}
