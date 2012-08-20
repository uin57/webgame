package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * vip公开的功能配置
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCVipConfig extends GCMessage{
	
	/** 训练空位的配置 */
	private int trainingSlotConfig;
	/** 建筑空位的配置 */
	private int buildingSlotConfig;
	/** 训练时间模式的配置[24小时,72小时] */
	private int trainingTimeConfig;
	/** 训练模式的配置[高级,白金,金钻,至尊] */
	private int trainingModelConfig;
	/** 日常任务刷新的配置 */
	private int dailyQuestConfig;
	/** 通商的配置 */
	private int businessConfig;
	/** 洗属性模式的配置 */
	private int resetPropModelConfig;
	/** 强化装备的配置 */
	private int enhanceEquipConfig;
	/** 强制攻击精英npc的配置 */
	private int fightEliteNpcConfig;
	/** 军令购买模式的配置 */
	private int buyTokenModelConfig;
	/** 金币突飞的配置 */
	private int rapidTrainingConfig;
	/** 钻石鼓舞的配置 */
	private int diamondEncourageConfig;

	public GCVipConfig (){
	}
	
	public GCVipConfig (
			int trainingSlotConfig,
			int buildingSlotConfig,
			int trainingTimeConfig,
			int trainingModelConfig,
			int dailyQuestConfig,
			int businessConfig,
			int resetPropModelConfig,
			int enhanceEquipConfig,
			int fightEliteNpcConfig,
			int buyTokenModelConfig,
			int rapidTrainingConfig,
			int diamondEncourageConfig ){
			this.trainingSlotConfig = trainingSlotConfig;
			this.buildingSlotConfig = buildingSlotConfig;
			this.trainingTimeConfig = trainingTimeConfig;
			this.trainingModelConfig = trainingModelConfig;
			this.dailyQuestConfig = dailyQuestConfig;
			this.businessConfig = businessConfig;
			this.resetPropModelConfig = resetPropModelConfig;
			this.enhanceEquipConfig = enhanceEquipConfig;
			this.fightEliteNpcConfig = fightEliteNpcConfig;
			this.buyTokenModelConfig = buyTokenModelConfig;
			this.rapidTrainingConfig = rapidTrainingConfig;
			this.diamondEncourageConfig = diamondEncourageConfig;
	}

	@Override
	protected boolean readImpl() {
		trainingSlotConfig = readInteger();
		buildingSlotConfig = readInteger();
		trainingTimeConfig = readInteger();
		trainingModelConfig = readInteger();
		dailyQuestConfig = readInteger();
		businessConfig = readInteger();
		resetPropModelConfig = readInteger();
		enhanceEquipConfig = readInteger();
		fightEliteNpcConfig = readInteger();
		buyTokenModelConfig = readInteger();
		rapidTrainingConfig = readInteger();
		diamondEncourageConfig = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(trainingSlotConfig);
		writeInteger(buildingSlotConfig);
		writeInteger(trainingTimeConfig);
		writeInteger(trainingModelConfig);
		writeInteger(dailyQuestConfig);
		writeInteger(businessConfig);
		writeInteger(resetPropModelConfig);
		writeInteger(enhanceEquipConfig);
		writeInteger(fightEliteNpcConfig);
		writeInteger(buyTokenModelConfig);
		writeInteger(rapidTrainingConfig);
		writeInteger(diamondEncourageConfig);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_VIP_CONFIG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_VIP_CONFIG";
	}

	public int getTrainingSlotConfig(){
		return trainingSlotConfig;
	}
		
	public void setTrainingSlotConfig(int trainingSlotConfig){
		this.trainingSlotConfig = trainingSlotConfig;
	}

	public int getBuildingSlotConfig(){
		return buildingSlotConfig;
	}
		
	public void setBuildingSlotConfig(int buildingSlotConfig){
		this.buildingSlotConfig = buildingSlotConfig;
	}

	public int getTrainingTimeConfig(){
		return trainingTimeConfig;
	}
		
	public void setTrainingTimeConfig(int trainingTimeConfig){
		this.trainingTimeConfig = trainingTimeConfig;
	}

	public int getTrainingModelConfig(){
		return trainingModelConfig;
	}
		
	public void setTrainingModelConfig(int trainingModelConfig){
		this.trainingModelConfig = trainingModelConfig;
	}

	public int getDailyQuestConfig(){
		return dailyQuestConfig;
	}
		
	public void setDailyQuestConfig(int dailyQuestConfig){
		this.dailyQuestConfig = dailyQuestConfig;
	}

	public int getBusinessConfig(){
		return businessConfig;
	}
		
	public void setBusinessConfig(int businessConfig){
		this.businessConfig = businessConfig;
	}

	public int getResetPropModelConfig(){
		return resetPropModelConfig;
	}
		
	public void setResetPropModelConfig(int resetPropModelConfig){
		this.resetPropModelConfig = resetPropModelConfig;
	}

	public int getEnhanceEquipConfig(){
		return enhanceEquipConfig;
	}
		
	public void setEnhanceEquipConfig(int enhanceEquipConfig){
		this.enhanceEquipConfig = enhanceEquipConfig;
	}

	public int getFightEliteNpcConfig(){
		return fightEliteNpcConfig;
	}
		
	public void setFightEliteNpcConfig(int fightEliteNpcConfig){
		this.fightEliteNpcConfig = fightEliteNpcConfig;
	}

	public int getBuyTokenModelConfig(){
		return buyTokenModelConfig;
	}
		
	public void setBuyTokenModelConfig(int buyTokenModelConfig){
		this.buyTokenModelConfig = buyTokenModelConfig;
	}

	public int getRapidTrainingConfig(){
		return rapidTrainingConfig;
	}
		
	public void setRapidTrainingConfig(int rapidTrainingConfig){
		this.rapidTrainingConfig = rapidTrainingConfig;
	}

	public int getDiamondEncourageConfig(){
		return diamondEncourageConfig;
	}
		
	public void setDiamondEncourageConfig(int diamondEncourageConfig){
		this.diamondEncourageConfig = diamondEncourageConfig;
	}
}