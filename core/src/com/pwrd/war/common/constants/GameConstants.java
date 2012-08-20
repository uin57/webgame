package com.pwrd.war.common.constants;

import com.pwrd.war.core.config.Config;

/**
 * 游戏全局参数定义，在resource/script/constants.js中填写
 *
 */
public class GameConstants implements Config {
	/** 确认对话框 OK 按钮返回值 */
	public static final String OPTION_OK = "ok";
	/** 确认对话框 Cancel 按钮返回值 */
	public static final String OPTION_CANCEL = "cancel";

	/** 世界聊天的最小间隔，单位：秒 */
	private int worldChat;
	/** 私聊的最小时间间隔，单位：秒 */
	private int privateChat;
	/** 附近聊天的最小时间间隔，单位：秒 */
	private int nearChat;
	/** 地区聊天的最小时间间隔，单位：秒 */
	private int regionChat;
	/** 军团聊天的最小时间间隔，单位：秒 */
	private int guildChat;
	/** 喇叭聊天的最小间隔，单位：秒 */
	private int trumpetChat;
	/** 普通聊天的最小间隔，单位：秒 */
	private int generalChat;
	/** 世界聊天的最小级别 */
	private int worldChatMinLevel;
	/** 跑马灯的速度 */
	private int defaultNoticeSpeed;
	
	
	
	/** 进入删除箱后,过期时间,过期后自动删除 */
	private int mailInDelboxExpiredTime;
	/** 非保存的邮件,过期时间,过期后自动删除 */
	private int mailInInboxExpiredTime;
	/** 保存邮箱中的邮件最大数量 */
	private int mailInSaveboxMaxCount;
	/** 每页的邮件数量 */
	private int mailNumPerPage;
	
	/** 冷却队列操作熟练重置时间 */
	private int cdOpCountResetTime;
	/** 初始的军令上限值 */
	private int tokenMax;
	

	/** 每页的任务数量 */
	private int questNumPerPage;
	/** 日常任务的开始等级 */
	private int dailyQuestBeginLevel;
	/** 日常任务携带数量 */
	private int dailyQuestCount;
	/** 日常任务刷新需要的钻石 */
	private int dailyQuestRefreshNeedDiamond;
	/** 日常任务立即完成需要的钻石 */
	private int dailyQuestFinishNowNeedDiamond;

	
	/** 军团长可以被置换需要的离线时间（单位：小时） */
	private int guildLeaderReplaceOfflineTime = 72;
	
	

	

	@Override
	public boolean getIsDebug() {
		return false;
	}

	@Override
	public String getVersion() {
		return null;
	}

	@Override
	public void validate() {
//		if(givebackMoneyRate <= 0 || givebackMoneyRate >= 100)
//		{
//			throw new ConfigException(
//				"constants.js中，givebackMoneyRate小于0或者大于100");
//		}
	}

	/**
	 * 世界聊天的最小间隔, 单位: 秒 
	 * 
	 * @return
	 */
	public int getWorldChat() {
		return worldChat;
	}

	/**
	 * 设置世界聊天的最小间隔, 单位: 秒
	 *  
	 * @param worldChat
	 */
	public void setWorldChat(int worldChat) {
		this.worldChat = worldChat;
	}

	/**
	 * 获取私聊的最小时间间隔, 单位: 秒
	 * 
	 * @return
	 */
	public int getPrivateChat() {
		return this.privateChat;
	}

	/**
	 * 设置私聊的最小时间间隔, 单位: 秒
	 * 
	 * @param privateChat
	 */
	public void setPrivateChat(int privateChat) {
		this.privateChat = privateChat;
	}
	
	public int getRegionChat() {
		return regionChat;
	}

	public void setRegionChat(int regionChat) {
		this.regionChat = regionChat;
	}

	public int getNearChat() {
		return nearChat;
	}

	public void setNearChat(int nearChat) {
		this.nearChat = nearChat;
	}

	public int getGuildChat() {
		return guildChat;
	}

	public void setGuildChat(int guildChat) {
		this.guildChat = guildChat;
	}
	
	public int getTrumpetChat() {
		return trumpetChat;
	}

	public void setTrumpetChat(int trumpetChat) {
		this.trumpetChat = trumpetChat;
	}

	public int getGeneralChat() {
		return generalChat;
	}

	public void setGeneralChat(int generalChat) {
		this.generalChat = generalChat;
	}

	public int getWorldChatMinLevel() {
		return worldChatMinLevel;
	}

	public void setWorldChatMinLevel(int worldChatMinLevel) {
		this.worldChatMinLevel = worldChatMinLevel;
	}

	public int getMailInDelboxExpiredTime() {
		return mailInDelboxExpiredTime;
	}

	public void setMailInDelboxExpiredTime(int mailInDelboxExpiredTime) {
		this.mailInDelboxExpiredTime = mailInDelboxExpiredTime;
	}

	public int getMailInInboxExpiredTime() {
		return mailInInboxExpiredTime;
	}

	public void setMailInInboxExpiredTime(int mailInInboxExpiredTime) {
		this.mailInInboxExpiredTime = mailInInboxExpiredTime;
	}

	public int getMailInSaveboxMaxCount() {
		return mailInSaveboxMaxCount;
	}

	public void setMailInSaveboxMaxCount(int mailInSaveboxMaxCount) {
		this.mailInSaveboxMaxCount = mailInSaveboxMaxCount;
	}

	public int getMailNumPerPage() {
		return mailNumPerPage;
	}

	public void setMailNumPerPage(int mailNumPerPage) {
		this.mailNumPerPage = mailNumPerPage;
	}
	
	public int getQuestNumPerPage() {
		return questNumPerPage;
	}

	public void setQuestNumPerPage(int questNumPerPage) {
		this.questNumPerPage = questNumPerPage;
	}



	/**
	 * 获取冷却队列操作数量重置时间
	 * 
	 * @return
	 */
	public int getCdOpCountResetTime() {
		return this.cdOpCountResetTime;
	}

	/**
	 * 设置冷却队列操作数量重置时间
	 * 
	 * @param value
	 */
	public void setCdOpCountResetTime(int value) {
		this.cdOpCountResetTime = value;
	}


	/**
	 * 军令上限
	 * @return
	 */
	public int getTokenMax() {
		return tokenMax;
	}

	public void setTokenMax(int tokenMax) {
		this.tokenMax = tokenMax;
	}
	
	

	/**
	 * 日常任务携带数量
	 * @return
	 */
	public int getDailyQuestCount() {
		return dailyQuestCount;
	}

	public void setDailyQuestCount(int dailyQuestCount) {
		this.dailyQuestCount = dailyQuestCount;
	}

	/**
	 * 日常任务的开始等级
	 * @return
	 */
	public int getDailyQuestBeginLevel() {
		return dailyQuestBeginLevel;
	}

	public void setDailyQuestBeginLevel(int dailyQuestBeginLevel) {
		this.dailyQuestBeginLevel = dailyQuestBeginLevel;
	}
	
	/**
	 * 得到日常任务刷新需要的钻石费用
	 * @return
	 */
	public int getDailyQuestRefreshNeedDiamond() {
		return dailyQuestRefreshNeedDiamond;
	}

	public void setDailyQuestRefreshNeedDiamond(int dailyQuestRefreshNeedDiamond) {
		this.dailyQuestRefreshNeedDiamond = dailyQuestRefreshNeedDiamond;
	}

	/**
	 * 得到日常任务立即完成需要的钻石费用
	 * @return
	 */
	public int getDailyQuestFinishNowNeedDiamond() {
		return dailyQuestFinishNowNeedDiamond;
	}

	public void setDailyQuestFinishNowNeedDiamond(int dailyQuestFinishNowNeedDiamond) {
		this.dailyQuestFinishNowNeedDiamond = dailyQuestFinishNowNeedDiamond;
	}

	public int getGuildLeaderReplaceOfflineTime() {
		return guildLeaderReplaceOfflineTime;
	}

	public void setGuildLeaderReplaceOfflineTime(int guildLeaderReplaceOfflineTime) {
		this.guildLeaderReplaceOfflineTime = guildLeaderReplaceOfflineTime;
	}
	
	public int getDefaultNoticeSpeed() {
		return defaultNoticeSpeed;
	}

	public void setDefaultNoticeSpeed(int defaultNoticeSpeed) {
		this.defaultNoticeSpeed = defaultNoticeSpeed;
	}
	
}
