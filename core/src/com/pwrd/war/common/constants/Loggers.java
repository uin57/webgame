package com.pwrd.war.common.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统一定义系统使用的slf4j的Logger
 * 
 * 
 */
public class Loggers {
	/** Server相关的日志 */
	public static final Logger serverLogger = LoggerFactory.getLogger("war.server");
	/** Game Server相关的日志 */
	public static final Logger gameLogger = LoggerFactory.getLogger("war.game");
	/** 登录相关的日志 */
	public static final Logger loginLogger = LoggerFactory.getLogger("war.login");
	/** 玩家相关的日志 */
	public static final Logger playerLogger = LoggerFactory.getLogger("war.player");
	/** 插件相关的日志 */
	public static final Logger pluginLogger = LoggerFactory.getLogger("war.plugin");
	/** 数据库相关的日志 */
	public static final Logger dbLogger = LoggerFactory.getLogger("war.db");
	/** 系统对象相关的日志 */
	public static final Logger objectLogger = LoggerFactory.getLogger("war.object");
	/** 任务调度相关的日志 */
	public static final Logger scheduleLogger = LoggerFactory.getLogger("war.shcedule");
	/** 异步操作相关的日志 */
	public static final Logger asyncLogger = LoggerFactory.getLogger("war.async");
	/** 消息处理相关的日志 */
	public static final Logger msgLogger = LoggerFactory.getLogger("war.msg");
	/** 缓存服务相关的日志 */
	public static final Logger cacheLogger = LoggerFactory.getLogger("war.cache");
	/** 事件相关的日志 */
	public static final Logger eventLogger = LoggerFactory.getLogger("war.event");
	/** 战斗相关的日志 */
	public static final Logger battleLogger = LoggerFactory.getLogger("war.battle");
	/** 组队相关的日志 */
	public static final Logger teamLogger = LoggerFactory.getLogger("war.team");
	/** 角色相关的日志 */
	public static final Logger charLogger = LoggerFactory.getLogger("war.char");
	/** 模板相关的日志 */
	public static final Logger templateLogger = LoggerFactory.getLogger("war.template");
	/** 聊天相关的日志 */
	public static final Logger chatLogger = LoggerFactory.getLogger("war.chat");
	/** GM命令相关的日志 */
	public static final Logger gmcmdLogger = LoggerFactory.getLogger("war.gmcmd");
	/** 物品相关的日志 */
	public static final Logger itemLogger = LoggerFactory.getLogger("war.item");
	/** 邮件相关的日志 */
	public static final Logger mailLogger = LoggerFactory.getLogger("war.mail");
	/** 地图相关的日志 */
	public static final Logger mapLogger = LoggerFactory.getLogger("war.map");
	/** 角色相关的日志 */
	public static final Logger humanLogger = LoggerFactory.getLogger("war.human");
	/** 场景相关的日志 */
	public static final Logger sceneLogger = LoggerFactory.getLogger("war.scene");
	/** 状态相关的日志 */
	public static final Logger stateLogger = LoggerFactory.getLogger("war.state");
	/** 任务相关的日志 */
	public static final Logger questLogger = LoggerFactory.getLogger("war.quest");
	/** 服务器状态统计 */
	public static final Logger serverStatusStatistics = LoggerFactory.getLogger("war.statistics");
	/** 服务器性能统计 */
	public static final Logger probeLogger = LoggerFactory.getLogger("war.probe");
	/** 商队相关的日志 */
	public static final Logger tradeLogger = LoggerFactory.getLogger("war.trade");
	/** 服务器状态日志 */
	public static final Logger statusLogger= LoggerFactory.getLogger("war.status");	
	/** 数据更新的日志 */
	public static final Logger updateLogger = LoggerFactory.getLogger("war.update");
	/** 随机产生脚本的日志 */
	public static final Logger scriptLogger = LoggerFactory.getLogger("war.script");
	/** 异步数据库日志 */
	public static final Logger asyncDbLogger = LoggerFactory.getLogger("war.asyncdb");
	/** 充值日志 */
	public static final Logger chargeLogger = LoggerFactory.getLogger("war.charge");
	/** 客户端消息采样日志 */
	public static final Logger clientLogger = LoggerFactory.getLogger("war.client");
	/** 活动相关的日志 */
	public static final Logger activityLogger = LoggerFactory.getLogger("war.activity");
	/** 奖励相关的日志*/
	public static final Logger prizeLogger = LoggerFactory.getLogger("war.prize");
	/** 进程相关的日志 */
	public static final Logger processLogger = LoggerFactory.getLogger("war.process");
	/** 礼包相关的日志*/
	public static final Logger giftLogger = LoggerFactory.getLogger("war.gift");
	/** 武将相关的日志*/
	public static final Logger petLogger = LoggerFactory.getLogger("war.pet");
	/** 建筑相关的日志*/
	public static final Logger buildingLogger = LoggerFactory.getLogger("war.building");
	/** 防沉迷相关的日志 */
	public static final Logger wallowLogger = LoggerFactory.getLogger("war.wallow");
	/** 商店系统相关日志 */
	public static final Logger shopLogger = LoggerFactory.getLogger("war.shop");
	/** 商城系统相关日志 */
	public static final Logger shopmallLogger = LoggerFactory.getLogger("war.shopmall");
	/** 时间波动相关日志 */
	public static final Logger timewaveLogger = LoggerFactory.getLogger("war.timewave");
	/** 平台相关的日志 */
	public static final Logger localLogger = LoggerFactory.getLogger("war.local");
	/** 战报日志 */
	public static final Logger battleReportLogger = LoggerFactory.getLogger("war.battlereport");
	/** 运行异常的日志 */
	public static final Logger ASSERT_LOGGER = LoggerFactory.getLogger("assert");
	/** 直充相关的日志 */
	public static final Logger directChargeLogger = LoggerFactory.getLogger("war.directcharge");
	/** 剧情相关的日志 */
	public static final Logger StoryLogger = LoggerFactory.getLogger("war.story");
	/** 竞技场相关日志 */
	public static final Logger arenaLogger = LoggerFactory.getLogger("war.arena");
	
}
