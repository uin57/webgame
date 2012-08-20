package com.pwrd.war.gameserver.quest;

import com.pwrd.war.gameserver.common.listener.BattleListener;
import com.pwrd.war.gameserver.common.listener.BuildingListener;
import com.pwrd.war.gameserver.common.listener.EquipEnhanceListener;
import com.pwrd.war.gameserver.common.listener.LevyListener;
import com.pwrd.war.gameserver.common.listener.MarketListener;
import com.pwrd.war.gameserver.human.Human;

public interface QuestEventListener extends BattleListener,LevyListener,BuildingListener,MarketListener,EquipEnhanceListener {

	/**
	 * 消费金币
	 * @param human
	 */
	void onCostGold(Human human);
	
	/**
	 * 切换到征战视图
	 * @param human
	 */
	void onSwitchFightView(Human human);
	
	/**
	 * 切换到地区视图
	 * @param human
	 */
	void onSwitchRegionView(Human human);
	
	/**
	 * 切换到农田视图
	 * @param human
	 */
	void onSwitchFarmView(Human human);
	
	/**
	 * 切换到世界视图
	 * @param human
	 */
	void onSwitchWorldView(Human human);	
	
	/**
	 * 切换到属臣视图
	 * @param human
	 */
	void onSwitchSlaveView(Human human);
	
	/**
	 * 个人留言
	 * @param human
	 */
	void onChangeLeaveMessage(Human human);
	
	/**
	 * 在世界频道里面说话
	 * @param human
	 */
	void onChatInWorldChannel(Human human);
	
	/**
	 * 征义兵
	 * @param human
	 */
	void onGetFreeSolider(Human human);
	
	/**
	 * 从商店购买装备
	 * @param human
	 */
	void onBuyEquipFromShop(Human human);
	
	/**
	 * 购买粮食
	 * @param human
	 * @param price
	 */
	void onBuyGrain(Human human,float price);

	/**
	 * 卖出粮食
	 * @param human
	 * @param price
	 */
	void onSellGrain(Human human,float price);
	
	/**
	 * 抢夺其他玩家的农田资源
	 * @param human
	 */
	void onSnatchFarmResource(Human human);
	
	/**
	 * 抢夺其他玩家的银矿资源
	 * @param human
	 */
	void onSnatchOilResource(Human human);
	
	/**
	 * 通商操作-完成申请和接受操作
	 * @param human
	 */
	void onBussinessOperation(Human human);
	
	/**
	 * 招募武将
	 * @param human
	 */
	void onRecruitPet(Human human);
	
	/**
	 * 进行A次投资
	 * @param human
	 */
	void onCompleteATimesInvest(Human human);
	
	/**
	 * 进行A此委派
	 * @param human
	 */
	void onCompleteATimesSieze(Human human);
	
	/**
	 * 发送邮件给别人
	 * @param human
	 */
	void onSendToOtherMail(Human human);

	/**
	 * 迁徙到其他区域
	 * 
	 * @param human
	 */
	void onMoveToNewRegion(Human human);


	/**
	 * 学习属性
	 * 
	 * @param human 
	 * 
	 */
	void onLearnProps(Human human);
}
