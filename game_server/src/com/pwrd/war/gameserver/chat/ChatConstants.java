package com.pwrd.war.gameserver.chat;

//import com.pwrd.war.gameserver.currency.Currency;

/**
 * 聊天时间间隔常量
 * 
 * @author zhairuike
 * @since 2010-5-11
 */
public class ChatConstants {

	/** 没有链接信息的，聊天信息最大长度 */
	public static final int CHAT_NOLINK_MSG_MAX_LENGTH = 100;
	/** 包含链接信息的，聊天信息最大长度 */
	public static final int CHAT_LINK_MSG_MAX_LENGTH = 300;

//	/** 世界频道聊天消耗的金钱类型 */
//	public static final Currency CHAT_WORLD_PAY_TYPE = Currency.BIND_GOLD;
	/** 世界聊天花费的金钱 */
	public static final int CHAT_WORLD_COST_MONEY = 20;
	/** 私聊开始符 */
	public static final String PRIVATE_CHAT_PRFIX = "/";
	/** 添加自定义聊天频道 */
	public static final int CHAT_CHANNEL_OPER_TYPE_ADD = 1;
	/** 删除自定义聊天频道 */
	public static final int CHAT_CHANNEL_OPER_TYPE_DEL = 2;
	/** 修改自定义聊天频道 */
	public static final int CHAT_CHANNEL_OPER_TYPE_MODIFY = 3;
	/** 自定义聊天频道最大数 */
	public static final int MAX_CHAT_CHANNEL_NUM = 3;
	/** 自定义聊天频道名字的最大长度 */
	public static final int MAX_CHAT_CHANNEL_LEN = 8;
	/** 自定义聊天频道名字的最小长度 */
	public static final int MIN_CHAT_CHANNEL_LEN = 1;
}
