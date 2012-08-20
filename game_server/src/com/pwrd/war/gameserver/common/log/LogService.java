package com.pwrd.war.gameserver.common.log;

import com.pwrd.war.logserver.model.ItemLog;
import com.pwrd.war.logserver.model.CheatLog;
import com.pwrd.war.logserver.model.TaskLog;
import com.pwrd.war.logserver.model.ChatLog;
import com.pwrd.war.logserver.model.SnapLog;
import com.pwrd.war.logserver.model.ItemGenLog;
import com.pwrd.war.logserver.model.OnlineTimeLog;
import com.pwrd.war.logserver.model.ChargeLog;
import com.pwrd.war.logserver.model.LevelLog;
import com.pwrd.war.logserver.model.GmCommandLog;
import com.pwrd.war.logserver.model.BasicPlayerLog;
import com.pwrd.war.logserver.model.PetLog;
import com.pwrd.war.logserver.model.MailLog;
import com.pwrd.war.logserver.model.GuildLog;
import com.pwrd.war.logserver.model.PrizeLog;
import com.pwrd.war.logserver.model.BattleLog;
import com.pwrd.war.logserver.model.PetExpLog;
import com.pwrd.war.logserver.model.PetLevelLog;
import com.pwrd.war.logserver.model.ActivityLog;
import com.pwrd.war.logserver.model.CurrencyLog;
import com.pwrd.war.logserver.model.EquipmentLog;
import com.pwrd.war.logserver.model.FamilyLog;
import com.pwrd.war.logserver.model.FriendLog;
import com.pwrd.war.logserver.model.GetItemLog;
import com.pwrd.war.logserver.model.LevelUpLog;
import com.pwrd.war.logserver.model.LoginLog;
import com.pwrd.war.logserver.model.OfficialLog;
import com.pwrd.war.logserver.model.QuestLog;
import com.pwrd.war.logserver.model.RepDropLog;
import com.pwrd.war.logserver.model.RepLog;
import com.pwrd.war.logserver.model.TowerLog;
import com.pwrd.war.logserver.model.VipLog;
import com.pwrd.war.logserver.model.WarcraftLog;
import com.pwrd.war.logserver.model.XinghunLog;
import com.pwrd.war.core.log.UdpLoggerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pwrd.war.common.LogReasons;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.common.Globals;

/**
 * This is an auto generated source,please don't modify it.
 */
public class LogService {
	private static final Logger logger = LoggerFactory.getLogger(LogService.class);
	/** 日志客户端 */
	private final UdpLoggerClient udpLoggerClient = UdpLoggerClient.getInstance();
	/**  游戏区ID */
	private int regionID;
	/** 游戏服务器ID */
	private int serverID;

	/**
	 * 初始化
	 * 
	 * @param logServerIp
	 *            日志服务器IP
	 * @param logServerPort
	 *            日志服务器端口
	 * @param regionID
	 *            游戏区ID
	 * @param serverID
	 *            游戏服务器ID
	 */
	public LogService(String logServerIp, int logServerPort, int regionID, int serverID) {
		udpLoggerClient.initialize(logServerIp, logServerPort);
		udpLoggerClient.setRegionId(regionID);
		udpLoggerClient.setServerId(serverID);

		this.regionID = regionID;
		this.serverID = serverID;
		
		if (logger.isInfoEnabled()) {
			logger.info("Connnect to Log server : " + logServerIp + " : " + logServerPort);
		}
	}

	/**
	 * 发送物品改变日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param bagId 包裹id
	 * @param bagIndex 在包裹中的位置索引
	 * @param templateId 道具模板ID
	 * @param instUUID 道具实例ID
	 * @param delta 变化值
	 * @param resultCount 操作后的最终叠加数
	 * @param itemGenId 道具产生ID：对应ItemGenLog
	 * @param itemData 道具大字段信息，用于在删除贵重道具时将道具二进制信息备份
	 */
	public void sendItemLog(Human human,
				LogReasons.ItemLogReason reason,				String param			,int bagId
			,int bagIndex
			,int templateId
			,String instUUID
			,int delta
			,int resultCount
			,String itemGenId
			,byte[] itemData
		) {
		udpLoggerClient.sendMessage(new ItemLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,bagId
			,bagIndex
			,templateId
			,instUUID
			,delta
			,resultCount
			,itemGenId
			,itemData
		));
	}
	/**
	 * 发送作弊日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param details 详细
	 */
	public void sendCheatLog(Human human,
				LogReasons.CheatLogReason reason,				String param			,String details
		) {
		udpLoggerClient.sendMessage(new CheatLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,details
		));
	}
	/**
	 * 发送任务日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param taskId 任务id
	 */
	public void sendTaskLog(Human human,
				LogReasons.TaskLogReason reason,				String param			,int taskId
		) {
		udpLoggerClient.sendMessage(new TaskLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,taskId
		));
	}
	/**
	 * 发送聊天日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param scope 聊天范围
	 * @param recCharName 如果为私聊，则记录私聊消息的接收玩家角色名称,否则该字段无意义
	 * @param content 聊天的内容
	 */
	public void sendChatLog(Human human,
				LogReasons.ChatLogReason reason,				String param			,int scope
			,String recCharName
			,String content
		) {
		udpLoggerClient.sendMessage(new ChatLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,scope
			,recCharName
			,content
		));
	}
	/**
	 * 发送快照日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param snap 快照信息
	 */
	public void sendSnapLog(Human human,
				LogReasons.SnapLogReason reason,				String param			,String snap
		) {
		udpLoggerClient.sendMessage(new SnapLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,snap
		));
	}
	/**
	 * 发送物品产出日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param templateId 道具模板ID
	 * @param itemName 道具名称
	 * @param count 道具数量
	 * @param bind 绑定类型
	 * @param deadline 使用有限期限
	 * @param properties 物品其他属性
	 * @param itemGenId 物品产生ID
	 */
	public void sendItemGenLog(Human human,
				LogReasons.ItemGenLogReason reason,				String param			,int templateId
			,String itemName
			,int count
			,int bind
			,long deadline
			,String properties
			,String itemGenId
		) {
		udpLoggerClient.sendMessage(new ItemGenLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,templateId
			,itemName
			,count
			,bind
			,deadline
			,properties
			,itemGenId
		));
	}
	/**
	 * 发送玩家在线时间日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param minute 当天累计在线时间(分钟)
	 * @param totalMinute 累计在线时间(分钟)
	 * @param lastLoginTime 最后一次登录时间
	 * @param lastLogoutTime 最后一次登出时间
	 */
	public void sendOnlineTimeLog(Human human,
				LogReasons.OnlineTimeLogReason reason,				String param			,int minute
			,int totalMinute
			,long lastLoginTime
			,long lastLogoutTime
		) {
		udpLoggerClient.sendMessage(new OnlineTimeLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,minute
			,totalMinute
			,lastLoginTime
			,lastLogoutTime
		));
	}
	/**
	 * 发送充值日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param num 充值数额
	 * @param time 充值时间
	 */
	public void sendChargeLog(Human human,
				LogReasons.ChargeLogReason reason,				String param			,int num
			,long time
		) {
		udpLoggerClient.sendMessage(new ChargeLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,num
			,time
		));
	}
	/**
	 * 发送建筑日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param buildingId 升级建筑ID
	 * @param buildingName 升级建筑名称
	 * @param upLevelTime 升级时间
	 * @param intervalTime 距上级时间
	 */
	public void sendLevelLog(Human human,
				LogReasons.LevelLogReason reason,				String param			,int buildingId
			,String buildingName
			,long upLevelTime
			,long intervalTime
		) {
		udpLoggerClient.sendMessage(new LevelLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,buildingId
			,buildingName
			,upLevelTime
			,intervalTime
		));
	}
	/**
	 * 发送GM操作日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param operatorName 操作者姓名
	 * @param targetIp 目标机器
	 * @param command 命令
	 * @param commandDesc 命令描述
	 * @param commandDetail 命令内容
	 * @param returnResult 返回结果
	 */
	public void sendGmCommandLog(Human human,
				LogReasons.GmCommandLogReason reason,				String param			,String operatorName
			,String targetIp
			,String command
			,String commandDesc
			,String commandDetail
			,String returnResult
		) {
		udpLoggerClient.sendMessage(new GmCommandLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,operatorName
			,targetIp
			,command
			,commandDesc
			,commandDetail
			,returnResult
		));
	}
	/**
	 * 发送角色基本日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param ip IP地址
	 * @param exp 经验
	 * @param rankId 官职ID
	 * @param rankName 官职名称
	 * @param sceneName 场景名称
	 * @param missionId 关卡ID
	 * @param missionName 关卡名称
	 */
	public void sendBasicPlayerLog(Human human,
				LogReasons.BasicPlayerLogReason reason,				String param			,String ip
			,int exp
			,int rankId
			,String rankName
			,String sceneName
			,int missionId
			,String missionName
		) {
		udpLoggerClient.sendMessage(new BasicPlayerLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,ip
			,exp
			,rankId
			,rankName
			,sceneName
			,missionId
			,missionName
		));
	}
	/**
	 * 发送武将日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param petId 武将id
	 * @param petName 武将名称
	 * @param hireTime 获得时间
	 */
	public void sendPetLog(Human human,
				LogReasons.PetLogReason reason,				String param			,String petId
			,String petName
			,long hireTime
		) {
		udpLoggerClient.sendMessage(new PetLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,petId
			,petName
			,hireTime
		));
	}
	/**
	 * 发送邮件日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param senderId 发件人ID
	 * @param senderName 发件人姓名
	 * @param recieverId 收件人ID
	 * @param recieverName 收件人姓名
	 * @param title 标题
	 * @param readStatus 阅读状态
	 * @param sendTime 发件时间
	 */
	public void sendMailLog(Human human,
				LogReasons.MailLogReason reason,				String param			,String senderId
			,String senderName
			,String recieverId
			,String recieverName
			,String title
			,int readStatus
			,long sendTime
		) {
		udpLoggerClient.sendMessage(new MailLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,senderId
			,senderName
			,recieverId
			,recieverName
			,title
			,readStatus
			,sendTime
		));
	}
	/**
	 * 发送帮派日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param guildId 军团ID
	 * @param guildName 军团名
	 * @param guildLevel 军团等级
	 * @param guildSymbolLevel 军团军徽等级
	 * @param memberNums 军团人数
	 * @param status 军团状态
	 */
	public void sendGuildLog(Human human,
				LogReasons.GuildLogReason reason,				String param			,String guildId
			,String guildName
			,int guildLevel
			,int guildSymbolLevel
			,int memberNums
			,int status
		) {
		udpLoggerClient.sendMessage(new GuildLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,guildId
			,guildName
			,guildLevel
			,guildSymbolLevel
			,memberNums
			,status
		));
	}
	/**
	 * 发送登录奖励日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param loginTime 登录时间
	 * @param prizeType 奖励物品
	 * @param drawCount 领取次数
	 */
	public void sendPrizeLog(Human human,
				LogReasons.PrizeLogReason reason,				String param			,long loginTime
			,int prizeType
			,int drawCount
		) {
		udpLoggerClient.sendMessage(new PrizeLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,loginTime
			,prizeType
			,drawCount
		));
	}
	/**
	 * 发送战役日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param mapId 地图ID
	 * @param mapName 地图名称
	 * @param battleTime 参加战役时间
	 * @param battleResult 胜&负
	 * @param attackLoss 攻方损兵
	 * @param defenceLoss 守方损兵
	 */
	public void sendBattleLog(Human human,
				LogReasons.BattleLogReason reason,				String param			,int mapId
			,String mapName
			,long battleTime
			,int battleResult
			,int attackLoss
			,int defenceLoss
		) {
		udpLoggerClient.sendMessage(new BattleLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,mapId
			,mapName
			,battleTime
			,battleResult
			,attackLoss
			,defenceLoss
		));
	}
	/**
	 * 发送武将经验日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param petTempId 武将模版ID
	 * @param petId 武将ID
	 * @param expDelta 经验改变
	 * @param expLeft 剩余经验
	 */
	public void sendPetExpLog(Human human,
				LogReasons.PetExpLogReason reason,				String param			,int petTempId
			,long petId
			,long expDelta
			,long expLeft
		) {
		udpLoggerClient.sendMessage(new PetExpLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,petTempId
			,petId
			,expDelta
			,expLeft
		));
	}
	/**
	 * 发送武将等级日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param petTempId 武将模版ID
	 * @param petId 武将ID
	 * @param levelBeforeAction 行动前等级
	 * @param levelAfterAction 行动后等级
	 * @param expBeforeAction 行动前经验
	 * @param expAfterAction 行动后经验
	 */
	public void sendPetLevelLog(Human human,
				LogReasons.PetLevelLogReason reason,				String param			,int petTempId
			,long petId
			,int levelBeforeAction
			,int levelAfterAction
			,long expBeforeAction
			,long expAfterAction
		) {
		udpLoggerClient.sendMessage(new PetLevelLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,petTempId
			,petId
			,levelBeforeAction
			,levelAfterAction
			,expBeforeAction
			,expAfterAction
		));
	}
	/**
	 * 发送参加活动日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param name 活动名称
	 * @param time 参加时间
	 */
	public void sendActivityLog(Human human,
				LogReasons.ActivityLogReason reason,				String param			,String name
			,long time
		) {
		udpLoggerClient.sendMessage(new ActivityLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,name
			,time
		));
	}
	/**
	 * 发送货币日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param costFlag 1产出 2消耗
	 * @param currencyType 货币类型
	 * @param currencyNum 货币数量
	 * @param costType 货币消耗种类的大类别
	 * @param costDetailType 货币消耗种类的详细类别
	 * @param detailReason 具体行为描述
	 */
	public void sendCurrencyLog(Human human,
				LogReasons.CurrencyLogReason reason,				String param			,int costFlag
			,int currencyType
			,int currencyNum
			,String costType
			,String costDetailType
			,String detailReason
		) {
		udpLoggerClient.sendMessage(new CurrencyLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,costFlag
			,currencyType
			,currencyNum
			,costType
			,costDetailType
			,detailReason
		));
	}
	/**
	 * 发送装备日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param flag 1穿上 2卸下
	 * @param equipmentId 装备id
	 * @param enhanceLevle 强化等级
	 * @param petName 装备武将名称
	 * @param time 操作时间
	 */
	public void sendEquipmentLog(Human human,
				LogReasons.EquipmentLogReason reason,				String param			,int flag
			,String equipmentId
			,int enhanceLevle
			,String petName
			,long time
		) {
		udpLoggerClient.sendMessage(new EquipmentLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,flag
			,equipmentId
			,enhanceLevle
			,petName
			,time
		));
	}
	/**
	 * 发送家族日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param flag 1建立 2加入 3解散 4离开
	 * @param familyId 家族id
	 * @param familyName 家族名称
	 * @param time 操作时间
	 */
	public void sendFamilyLog(Human human,
				LogReasons.FamilyLogReason reason,				String param			,int flag
			,String familyId
			,String familyName
			,long time
		) {
		udpLoggerClient.sendMessage(new FamilyLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,flag
			,familyId
			,familyName
			,time
		));
	}
	/**
	 * 发送好友日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param flag 1添加 2删除
	 * @param friendId 好友id
	 * @param friendName 好用名称
	 * @param time 添加或者删除时间
	 * @param totalNum 当前好友数量
	 */
	public void sendFriendLog(Human human,
				LogReasons.FriendLogReason reason,				String param			,int flag
			,String friendId
			,String friendName
			,long time
			,int totalNum
		) {
		udpLoggerClient.sendMessage(new FriendLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,flag
			,friendId
			,friendName
			,time
			,totalNum
		));
	}
	/**
	 * 发送获得物品日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param itemSn 物品id
	 * @param time 获得时间
	 */
	public void sendGetItemLog(Human human,
				LogReasons.GetItemLogReason reason,				String param			,String itemSn
			,long time
		) {
		udpLoggerClient.sendMessage(new GetItemLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,itemSn
			,time
		));
	}
	/**
	 * 发送升级日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param curLevel 玩家当前等级
	 * @param levelUpTime 升级时间
	 * @param onlineTime 当前在线时间
	 */
	public void sendLevelUpLog(Human human,
				LogReasons.LevelUpLogReason reason,				String param			,int curLevel
			,long levelUpTime
			,int onlineTime
		) {
		udpLoggerClient.sendMessage(new LevelUpLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,curLevel
			,levelUpTime
			,onlineTime
		));
	}
	/**
	 * 发送登入登出日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param loginTime 用户登录时间
	 * @param ip 用户登录ip
	 * @param senceId 登录地图
	 * @param onlineTime 当前总在线时间
	 * @param loginFlag 登录或者登出标志 1登录 2登出
	 */
	public void sendLoginLog(Human human,
				LogReasons.LoginLogReason reason,				String param			,long loginTime
			,String ip
			,String senceId
			,int onlineTime
			,int loginFlag
		) {
		udpLoggerClient.sendMessage(new LoginLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,loginTime
			,ip
			,senceId
			,onlineTime
			,loginFlag
		));
	}
	/**
	 * 发送官职日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param officialLevel 当前官职等级
	 * @param time 操作时间
	 */
	public void sendOfficialLog(Human human,
				LogReasons.OfficialLogReason reason,				String param			,int officialLevel
			,long time
		) {
		udpLoggerClient.sendMessage(new OfficialLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,officialLevel
			,time
		));
	}
	/**
	 * 发送任务日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param flag 1接受2完成3取消
	 * @param questId 任务id
	 * @param completeTime 完成时间
	 */
	public void sendQuestLog(Human human,
				LogReasons.QuestLogReason reason,				String param			,int flag
			,int questId
			,long completeTime
		) {
		udpLoggerClient.sendMessage(new QuestLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,flag
			,questId
			,completeTime
		));
	}
	/**
	 * 发送副本掉落日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param repId 副本id
	 * @param repName 副本名称
	 * @param dropId 掉落id
	 * @param dropNum 掉落数量
	 * @param currencyType 掉落货币类型
	 * @param currency 掉落货币
	 * @param dropTime 完成时间
	 * @param dropMonster 掉落怪物
	 */
	public void sendRepDropLog(Human human,
				LogReasons.RepDropLogReason reason,				String param			,String repId
			,String repName
			,String dropId
			,int dropNum
			,int currencyType
			,int currency
			,long dropTime
			,String dropMonster
		) {
		udpLoggerClient.sendMessage(new RepDropLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,repId
			,repName
			,dropId
			,dropNum
			,currencyType
			,currency
			,dropTime
			,dropMonster
		));
	}
	/**
	 * 发送副本完成日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param repId 完成副本id
	 * @param curLevel 用户等级
	 * @param completeTime 完成时间
	 */
	public void sendRepLog(Human human,
				LogReasons.RepLogReason reason,				String param			,String repId
			,int curLevel
			,long completeTime
		) {
		udpLoggerClient.sendMessage(new RepLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,repId
			,curLevel
			,completeTime
		));
	}
	/**
	 * 发送爬塔日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param constellationId 星座id
	 * @param starId 当前所在星
	 * @param curLevel 用户等级
	 * @param completeTime 完成时间
	 * @param secretFlag 1为神秘星 0为不是
	 * @param successful 1成功 0失败
	 */
	public void sendTowerLog(Human human,
				LogReasons.TowerLogReason reason,				String param			,int constellationId
			,int starId
			,int curLevel
			,long completeTime
			,int secretFlag
			,int successful
		) {
		udpLoggerClient.sendMessage(new TowerLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,constellationId
			,starId
			,curLevel
			,completeTime
			,secretFlag
			,successful
		));
	}
	/**
	 * 发送vip日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param curVipLevel vip等级
	 * @param completeTime 完成时间
	 */
	public void sendVipLog(Human human,
				LogReasons.VipLogReason reason,				String param			,int curVipLevel
			,long completeTime
		) {
		udpLoggerClient.sendMessage(new VipLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,curVipLevel
			,completeTime
		));
	}
	/**
	 * 发送兵法日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param flag 1获得 2删除
	 * @param time 获得时间
	 * @param warcraftId 兵法id
	 * @param warcraftName 兵法名称
	 */
	public void sendWarcraftLog(Human human,
				LogReasons.WarcraftLogReason reason,				String param			,int flag
			,long time
			,String warcraftId
			,String warcraftName
		) {
		udpLoggerClient.sendMessage(new WarcraftLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,flag
			,time
			,warcraftId
			,warcraftName
		));
	}
	/**
	 * 发送星魂日志
	 * @param logTime 日志产生时间
	 * @param accountId 玩家账号Id
	 * @param accountName 玩家的账号名
	 * @param charId 角色ID
	 * @param charName 玩家的角色名
	 * @param level 玩家等级
	 * @param allianceId 玩家阵营
	 * @param vipLevel 玩家VIP等级
	 * @param reason 原因
	 * @param sceneId 所在地图id
	 * @param x x
	 * @param y y
	 * @param param 其他参数
	 * @param xinghunId 星魂id
	 * @param xinghunName 星魂名称
	 * @param feature 星魂属性
	 * @param completeTime 完成时间
	 */
	public void sendXinghunLog(Human human,
				LogReasons.XinghunLogReason reason,				String param			,String xinghunId
			,String xinghunName
			,String feature
			,long completeTime
		) {
		udpLoggerClient.sendMessage(new XinghunLog(
				Globals.getTimeService().now(),				this.regionID,				this.serverID,				human.getPassportId(),				human.getPassportName(),				human.getUUID(),				human.getName(),				human.getLevel(),				human.getAllianceId(),				human.getVipLevel(),				reason.reason,				human.getSceneId(),				human.getX(),				human.getY(),
				param			,xinghunId
			,xinghunName
			,feature
			,completeTime
		));
	}

}