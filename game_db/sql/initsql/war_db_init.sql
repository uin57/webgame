SET CHARACTER_SET_CLIENT=utf8;
SET CHARACTER_SET_CONNECTION=utf8;

DROP DATABASE IF EXISTS `war_battle_report`;
CREATE DATABASE `war_battle_report` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `war_battle_report`;

DROP DATABASE IF EXISTS `war`;
CREATE DATABASE `war` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `war`;
-- 角色信息表
DROP TABLE IF EXISTS `war`.`t_character_info`;
CREATE TABLE `war`.`t_character_info` (
  `id` bigint(20) NOT NULL,
  `passportId` bigint(20) DEFAULT NULL,
  `name` varchar(36) DEFAULT NULL,
  `photo` int(11) DEFAULT 0 NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `deleteTime` datetime DEFAULT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  `lastLoginIp` varchar(50) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `lastLogoutTime` datetime DEFAULT NULL,
  `totalMinute` int(11) DEFAULT 0 NOT NULL,
  `onlineStatus` int(11) DEFAULT 0 NOT NULL,
  `idleTime` int(11) DEFAULT 0 NOT NULL,
  `vipLevel` int(11) DEFAULT 0 NOT NULL,
  `todayCharge` int(11) DEFAULT 0 NOT NULL,
  `totalCharge` int(11) DEFAULT 0 NOT NULL,
  `lastChargeTime` datetime DEFAULT NULL,
  `lastVipTime` datetime DEFAULT NULL,
  `maxMoveLevel` int(11) DEFAULT 0 NOT NULL,
  `sceneId` int(11) DEFAULT 0 NOT NULL,
  `district` int(11) DEFAULT 0 NOT NULL,
  `missionId` int(11) DEFAULT 0 NOT NULL,
  `flagword` varchar(10) DEFAULT NULL,
  `leaveword` varchar(100) DEFAULT NULL,
  `rank` int(11) DEFAULT 0 NOT NULL,
  `level` int(11) DEFAULT 0 NOT NULL,
  `alliance` int(11) DEFAULT 0 NOT NULL,
  `gold` int(11) DEFAULT 0 NOT NULL,
  `copper` int(11) DEFAULT 0 NOT NULL,
  `food` int(11) DEFAULT 0 NOT NULL,
  `troops` int(11) DEFAULT 0 NOT NULL,
  `copperMax` int(11) DEFAULT 0 NOT NULL,
  `foodMax` int(11) DEFAULT 0 NOT NULL,
  `troopsMax` int(11) DEFAULT 0 NOT NULL,
  `exploit` int(11) DEFAULT 0 NOT NULL,
  `prestige` int(11) DEFAULT 0 NOT NULL,
  `honor` int(11) DEFAULT 0 NOT NULL,
  `token` int(11) DEFAULT 0 NOT NULL,
  `specialToken` int(11) DEFAULT 0 NOT NULL,
  `generalMax` int(11) DEFAULT 0 NOT NULL,
  `inventoryMax` int(11) DEFAULT 0 NOT NULL,
  `defaultArray` int(11) DEFAULT -1 NOT NULL,
  `hostilityOfTM` int(11) DEFAULT 0 NOT NULL,
  `hostilityOfZX` int(11) DEFAULT 0 NOT NULL,
  `hostilityOfGCGJ` int(11) DEFAULT 0 NOT NULL,
  `townPack` text DEFAULT NULL,
  `heroPack` text DEFAULT NULL,
  `petPack` text DEFAULT NULL,
  `constructionPack` text DEFAULT NULL,
  `technologyPack` text DEFAULT NULL,
  `cdQueuePack` text DEFAULT NULL,
  `armiesArrayPack` text DEFAULT NULL,
  `missionPack` text DEFAULT NULL,
  `resourcePack` text DEFAULT NULL,
  `guildPack` text DEFAULT NULL,
  `props` text DEFAULT NULL,
  `parentUUId` bigint(20) DEFAULT '0',
  `childUUIds` text DEFAULT NULL,
  `buffPack` text,
  `avatarPack` varchar(256) DEFAULT NULL,
  `passDirectly` int(11) DEFAULT '0' COMMENT '玩家申请通商时是否直接通过开关',
  `todayTradedUUIds` text COMMENT '今日已通商玩家列表',
  `applyTradeUUIds` text COMMENT '当前申请通商的玩家列表',
  `remainSendAmount` int(11) DEFAULT '0' COMMENT '当前剩余可发送商队数目',
  `remainRecieveAmount` int(11) DEFAULT '0' COMMENT '当前剩余可接收商队数目',
  `applyingTradeUUId` bigint(20) DEFAULT '0' COMMENT '当前正在向其申请通商的玩家',
  `merchantAmount` int(11) DEFAULT '0' COMMENT '本次派遣商队数目',
  `lastUpdateTradeTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`), 
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`), 
  KEY `sceneId` (`sceneId`),
  KEY `passportId` (`passportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- doing任务信息表
DROP TABLE IF EXISTS `war`.`t_doing_task`;
CREATE TABLE `war`.`t_doing_task` (
  `id` varchar(36) NOT NULL,
  `charId` bigint(20) DEFAULT NULL,
  `props` varchar(255) DEFAULT NULL,
  `questId` int(11) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `trace` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 已完成任务信息表
DROP TABLE IF EXISTS `war`.`t_finished_quest`;
CREATE TABLE  `war`.`t_finished_quest` (
  `id` varchar(255) NOT NULL,
  `charId` bigint(20) DEFAULT NULL,
  `dailyTimes` int(11) DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `questId` int(11) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`), 
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- daily任务信息表
DROP TABLE IF EXISTS `war`.`t_daily_task`;
CREATE TABLE `war`.`t_daily_task` (
  `id` varchar(36) NOT NULL,
  `charId` bigint(20) DEFAULT NULL,
  `props` varchar(255) DEFAULT NULL,
  `questId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`), 
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 武将表
DROP TABLE IF EXISTS `war`.`t_pet_info`;
CREATE TABLE `war`.`t_pet_info` (
  `id` bigint(20) NOT NULL,
  `charId` bigint(20) NOT NULL,
  `type` int(11) NOT NULL DEFAULT 0,
  `templateId` int(11) NOT NULL DEFAULT 0,
  `createDate` datetime DEFAULT NULL,
  `deleted` int(11) NOT NULL DEFAULT 0,
  `deleteDate` datetime DEFAULT NULL,
  `hired` int(11) NOT NULL DEFAULT 0,
  `name` varchar(50) DEFAULT NULL,
  `photo` int(11) NOT NULL DEFAULT 0,
  `description` varchar(250) DEFAULT NULL,
  `avatar` int(11) DEFAULT 0,
  `hair` int(11) DEFAULT 0,
  `feature` int(11) DEFAULT 0,
  `cap` int(11) DEFAULT 0,
  `attrGroup` int(11) NOT NULL DEFAULT 0,
  `soldierId` int(11) NOT NULL DEFAULT 0,
  `skillId` int(11) NOT NULL DEFAULT 0,
  `level` int(11) NOT NULL DEFAULT 0,
  `exp` bigint(20) NOT NULL DEFAULT 0,
  `soldierAmount` int(11) NOT NULL DEFAULT 0,
  `basicForceGrade` int(11) NOT NULL DEFAULT 0,
  `leadershipAdded` int(11) NOT NULL DEFAULT 0,
  `mightAdded` int(11) NOT NULL DEFAULT 0,
  `intellectAdded` int(11) NOT NULL DEFAULT 0,
  `trainingType` int(11) NOT NULL DEFAULT 0,
  `trainingEndTime` datetime DEFAULT NULL,
  `trainingGetExpTime` datetime DEFAULT NULL,
  `lastLearnGiftResult` varchar(250) DEFAULT NULL,
  `learnedSoldierInfo` text DEFAULT NULL,
  `learnedSkillInfo` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `IX_charId` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 物品表
DROP TABLE IF EXISTS `war`.`t_item_info`;
CREATE TABLE `war`.`t_item_info` (
  `id` varchar(36) NOT NULL,
  `bagId` int(11) DEFAULT NULL,
  `bagIndex` int(11) DEFAULT NULL,
  `bind` int(11) DEFAULT NULL,
  `charId` bigint(20) NOT NULL,
  `wearerId` bigint(20) NOT NULL,
  `coolDownTimePoint` bigint(20) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `curEndure` int(11) DEFAULT NULL,
  `deadline` datetime DEFAULT NULL,
  `deleteDate` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `overlap` int(11) DEFAULT NULL,
  `properties` varchar(1024) DEFAULT NULL,
  `star` int(11) DEFAULT NULL,
  `templateId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`), 
  KEY `charId_bagId` (`charId`, `bagId`),
  KEY `wearerId` (`wearerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 邮件表
DROP TABLE IF EXISTS `war`.`t_mail_info`;
CREATE TABLE `war`.`t_mail_info` (
  `id` varchar(36) NOT NULL,
  `charId` bigint(20) NOT NULL,
  `sendId` bigint(20) DEFAULT NULL,
  `sendName` varchar(36) DEFAULT NULL,
  `recId` bigint(20) DEFAULT NULL,
  `recName` varchar(36) DEFAULT NULL,
  `title` varchar(36) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `msgType` int(11) NOT NULL,
  `msgStatus` int(11) NOT NULL,
  `createTimeInGame` varchar(36) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `deleted` int(11) DEFAULT NULL,
  `deleteTime` datetime DEFAULT NULL,
  `hasAttachment` bit(1) DEFAULT b'0',
  `attachmentProps` varchar(1024) DEFAULT NULL,
  `attachmentValid` bit(1) DEFAULT b'0', 
  PRIMARY KEY (`id`), 
  UNIQUE KEY `id` (`id`),
  KEY `charId` (`charId`), 
  KEY `recId` (`recId`), 
  KEY `title` (`title`),
  KEY `recId_msgType` (`recId`, `msgType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 好友关系表
DROP TABLE IF EXISTS `war`.`t_friend_relation`;
CREATE TABLE  `war`.`t_friend_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addTime` datetime DEFAULT NULL,
  `friendGroup` int(11) DEFAULT NULL,
  `friendName` varchar(255) DEFAULT NULL,
  `friendUUID` bigint(20) DEFAULT NULL,
  `frindAmity` int(11) DEFAULT NULL,
  `roleUUID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `roleUUID` (`roleUUID`), 
  KEY `roleUUID_friendGroup` (`roleUUID`, `friendGroup`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- 游戏公告信息
DROP TABLE IF EXISTS `war`.`t_game_notice`;
CREATE TABLE  `war`.`t_game_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT NULL,
  `serverIds` varchar(255) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`), 
  UNIQUE KEY `id` (`id`),
  KEY `serverIds` (`serverIds`),
  KEY `serverIds_status` (`serverIds`, `status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 游戏定时公告信息
DROP TABLE IF EXISTS `war`.`t_time_notice`;
CREATE TABLE  `war`.`t_time_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `intervalTime` int(11) unsigned zerofill DEFAULT NULL,
  `serverIds` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  `content` text,
  `startTime` timestamp NULL DEFAULT NULL,
  `endTime` timestamp NULL DEFAULT NULL,
  `openType` tinyint(4) NOT NULL DEFAULT '0',
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `subType` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`), 
  UNIQUE KEY `id` (`id`), 
  KEY `serverIds` (`serverIds`), 
  KEY `type` (`type`),
  KEY `subType` (`subType`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- 用户信息表
DROP TABLE IF EXISTS `war`.`t_user_info`;
CREATE TABLE  `war`.`t_user_info` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `question` varchar(50) DEFAULT NULL,
  `answer` varchar(50) DEFAULT NULL,
  `joinTime` datetime DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `lastLogoutTime` datetime DEFAULT NULL,
  `failedLogins` int(11) DEFAULT 0 NOT NULL,  
  `lastLoginIp` varchar(50) DEFAULT NULL,
  `locale` varchar(50) DEFAULT NULL,
  `version` varchar(50) DEFAULT NULL,
  `role` int(11) DEFAULT 0 NOT NULL,
  `lockStatus` int(11) DEFAULT 0 NOT NULL,
  `muteTime` int(11) DEFAULT 0 NOT NULL,
  `props` varchar(256) DEFAULT NULL,
  `todayOnlineTime` int(11) DEFAULT 0 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `email` (`email`), 
  KEY `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据库版本信息
DROP TABLE IF EXISTS `war`.`t_db_version`;
CREATE TABLE  `war`.`t_db_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `updateTime` datetime NOT NULL,
  `version` varchar(255) NOT NULL,
  PRIMARY KEY (`id`), 
  UNIQUE KEY `id` (`id`), 
  UNIQUE KEY `version` (`version`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- 场景信息
DROP TABLE IF EXISTS `war`.`t_scene_info`;
CREATE TABLE `war`.`t_scene_info` (
  `id` bigint(20) NOT NULL,
  `properties` text DEFAULT NULL,
  `templateId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`), 
  KEY `IX_templateId` (`templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 季节信息
DROP TABLE IF EXISTS `t_season_info`;
CREATE TABLE `t_season_info` (
  `id` bigint(20) NOT NULL,
  `season` int(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`), 
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 战斗快照信息
DROP TABLE IF EXISTS `war`.`t_battle_info_snap`;
CREATE TABLE `war`.`t_battle_info_snap` (
  `id` bigint(20) NOT NULL,
  `name` varchar(36) DEFAULT NULL,
  `level` int(11) DEFAULT 0 NOT NULL,
  `totalAmount` int(11) DEFAULT 0 NOT NULL,
  `arrayId` int(11) DEFAULT 0 NOT NULL,
  `commanderAbility` int(11) DEFAULT 0 NOT NULL,
  `commanderAvatar` varchar(255) DEFAULT NULL,
  `armies` text,
  PRIMARY KEY (`id`), 
  UNIQUE KEY `id` (`id`), 
  KEY `name` (`name`), 
  KEY `level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 关卡敌人信息
DROP TABLE IF EXISTS `war`.`t_mission_enemy_info`;
CREATE TABLE `war`.`t_mission_enemy_info` (
  `id` int(11) NOT NULL,
  `countOfTM` int(11) DEFAULT 0 NOT NULL,
  `countOfZX` int(11) DEFAULT 0 NOT NULL,
  `countOfGCGJ` int(11) DEFAULT 0 NOT NULL,
  `reportList` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 农田表
DROP TABLE IF EXISTS `war`.`t_farm_info`;
CREATE TABLE `t_farm_info` (
  `id` bigint(20) NOT NULL,
  `cityId` int(11) DEFAULT NULL,
  `farm1` varchar(255) DEFAULT NULL,
  `farm10` varchar(255) DEFAULT NULL,
  `farm11` varchar(255) DEFAULT NULL,
  `farm12` varchar(255) DEFAULT NULL,
  `farm13` varchar(255) DEFAULT NULL,
  `farm14` varchar(255) DEFAULT NULL,
  `farm15` varchar(255) DEFAULT NULL,
  `farm16` varchar(255) DEFAULT NULL,
  `farm2` varchar(255) DEFAULT NULL,
  `farm3` varchar(255) DEFAULT NULL,
  `farm4` varchar(255) DEFAULT NULL,
  `farm5` varchar(255) DEFAULT NULL,
  `farm6` varchar(255) DEFAULT NULL,
  `farm7` varchar(255) DEFAULT NULL,
  `farm8` varchar(255) DEFAULT NULL,
  `farm9` varchar(255) DEFAULT NULL,
  `pageIndex` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`), 
  KEY `cityId` (`cityId`), 
  UNIQUE KEY `cityId_pageIndex` (`cityId`, `pageIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `war`.`t_silverore_info`;
CREATE TABLE `t_silverore_info` (
  `id` bigint(20) NOT NULL,
  `cityId` int(11) DEFAULT NULL,
  `pageIndex` int(11) DEFAULT NULL,
  `silverore1` varchar(255) DEFAULT NULL,
  `silverore10` varchar(255) DEFAULT NULL,
  `silverore11` varchar(255) DEFAULT NULL,
  `silverore12` varchar(255) DEFAULT NULL,
  `silverore13` varchar(255) DEFAULT NULL,
  `silverore14` varchar(255) DEFAULT NULL,
  `silverore15` varchar(255) DEFAULT NULL,
  `silverore16` varchar(255) DEFAULT NULL,
  `silverore2` varchar(255) DEFAULT NULL,
  `silverore3` varchar(255) DEFAULT NULL,
  `silverore4` varchar(255) DEFAULT NULL,
  `silverore5` varchar(255) DEFAULT NULL,
  `silverore6` varchar(255) DEFAULT NULL,
  `silverore7` varchar(255) DEFAULT NULL,
  `silverore8` varchar(255) DEFAULT NULL,
  `silverore9` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`), 
  UNIQUE KEY `id` (`id`), 
  KEY `cityId` (`cityId`), 
  UNIQUE KEY `cityId_pageIndex` (`cityId`, `pageIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 商城
DROP TABLE IF EXISTS `war`.`t_shopmall_info`;
CREATE TABLE `t_shopmall_info` (
  `id` int(11) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 帮会
DROP TABLE IF EXISTS `t_guild`;
CREATE TABLE `t_guild` (
  `id` bigint(20) NOT NULL,
  `guildName` varchar(50) NOT NULL,
  `guildLevel` int(11) DEFAULT NULL,
  `guildSymbolLevel` int(11) DEFAULT NULL,
  `guildSymbolName` varchar(50) NOT NULL,
  `messageInfo` varchar(512) DEFAULT NULL,
  `creatorId` bigint(20) DEFAULT NULL,
  `creatorName` varchar(50) NOT NULL,
  `createdTime` datetime NOT NULL,
  `createdYear` int(11) DEFAULT NULL,
  `createdSeason` int(11) DEFAULT NULL,
  `leaderId` bigint(20) DEFAULT NULL,
  `leaderName` varchar(50) NOT NULL,
  `state` int(11) DEFAULT NULL,
  `memberCapacity` int(11) DEFAULT NULL,
  `alliance` int(11) DEFAULT NULL,
  `props` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `guildName` (`guildName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 帮会成员
DROP TABLE IF EXISTS `t_guild_member`;
CREATE TABLE `t_guild_member` (
  `id` bigint(20) NOT NULL,
  `roleId` bigint(20) DEFAULT NULL,
  `guildId` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `head` int(11) DEFAULT 0,
  `body` int(11) DEFAULT 0,
  `hair` int(11) DEFAULT 0,
  `feature` int(11) DEFAULT 0,
  `cap` int(11) DEFAULT 0,
  `sceneId` int(11) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `guildRank` int(11) DEFAULT NULL,
  `curContrib` int(11) NOT NULL DEFAULT '0',
  `selfDesc` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `joinTime` datetime DEFAULT NULL,
  `lastOnlineTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `guildId` (`guildId`), 
  KEY `roleId` (`roleId`), 
  KEY `roleId_guildId` (`roleId`, `guildId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 活动积分流水
DROP TABLE IF EXISTS `war`.`t_activity_record_info`;
CREATE TABLE `t_activity_record_info` (
  `id` bigint(20) NOT NULL,
  `activityId` int(11) DEFAULT '0',
  `activityStartTime` datetime DEFAULT NULL,
  `activityStopTime` datetime DEFAULT NULL,
  `lastUpdateScoreTime` datetime DEFAULT NULL,
  `score` int(11) DEFAULT '0',
  `hasTakePrize` int(11) DEFAULT '0',
  `lastTakePrizeTime` datetime DEFAULT NULL,
  `lastUpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 活动积分排名
DROP TABLE IF EXISTS `war`.`t_activity_rank_info`;
CREATE TABLE `t_activity_rank_info` (
  `id` bigint(20) NOT NULL,
  `activityId` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `activityStartTime` datetime DEFAULT NULL,
  `activityStopTime` datetime DEFAULT NULL,
  `lastUpdateTime` datetime DEFAULT NULL,
  `hasTakePrize` int(11) DEFAULT '0',
  `lastTakePrizeTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 日程战斗
DROP TABLE IF EXISTS `war`.`t_scheduled_war`;
CREATE TABLE `war`.`t_scheduled_war` (
  `id` varchar(36) NOT NULL,
  `type` int(11) DEFAULT 0 NOT NULL,
  `prepareTime` datetime DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `detailInfo` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `type` (`type`), 
  KEY `startTime` (`startTime`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- 节日活动
DROP TABLE IF EXISTS `war`.`t_festival_info`;
CREATE TABLE `war`.`t_festival_info` (
  `id` int(11) NOT NULL,
  `stopped` int(11) DEFAULT 0 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 平台礼包表
DROP TABLE IF EXISTS `war`.`t_prize`;
CREATE TABLE `war`.`t_prize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coin` varchar(255) default NULL,
  `createTime` datetime default NULL,
  `item` varchar(255) default NULL,
  `pet` varchar(255) default NULL,
  `prizeId` int(11) default NULL,
  `prizeName` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- GM发奖表
DROP TABLE IF EXISTS `war`.`t_user_prize`;
CREATE TABLE `war`.`t_user_prize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coin` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `item` varchar(255) DEFAULT NULL,
  `passportId` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `userPrizeName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `passportId` (`passportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 直充订单表
DROP TABLE IF EXISTS `war`.`t_charge`;
CREATE TABLE `war`.`t_charge` (
  `id` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `money` int(11) NOT NULL DEFAULT '0',
  `time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP DATABASE IF EXISTS `war_log`;
CREATE DATABASE `war_log` DEFAULT CHARACTER SET utf8  COLLATE utf8_general_ci;
USE `war_log`;

-- ----------------------------
-- 
-- ----------------------------
CREATE TABLE `reason_list` (
  `log_uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `log_type` int(10) unsigned NOT NULL,
  `log_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `log_table` varchar(32) NOT NULL,
  `log_desc` varchar(128) NOT NULL,
  `log_field` varchar(32) NOT NULL,
  `reason` int(10) NOT NULL,
  `reason_name` varchar(128) NOT NULL,
  PRIMARY KEY (`log_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=557 DEFAULT CHARSET=utf8;

-- ---------------------------
-- Records
-- ---------------------------

-- --init reason_list table----
-- --basic_player_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (539,'basic_player_log','玩家基础日志','reason',0,'正常登录');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (539,'basic_player_log','玩家基础日志','reason',2,'正常退出');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (539,'basic_player_log','玩家基础日志','reason',3,'非战斗状态掉线');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (539,'basic_player_log','玩家基础日志','reason',9,'服务器下线');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (539,'basic_player_log','玩家基础日志','reason',10,'服务器停机,保存玩家数据');
-- --basic_player_log end----

-- --battle_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (545,'battle_log','战斗','reason',0,'关卡征战');
-- --battle_log end----

-- --camp_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (530,'camp_log','兵营','reason',0,'征兵');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (530,'camp_log','兵营','reason',1,'义兵');
-- --camp_log end----

-- --charge_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (521,'charge_log','充值','reason',0,'充值成功');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (521,'charge_log','充值','reason',1,'充值失败');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (521,'charge_log','充值','reason',2,'调用充值接口异常');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (521,'charge_log','充值','reason',3,'充值后钻石数溢出');
-- --charge_log end----

-- --chat_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (511,'chat_log','聊天','reason',0,'包含不良信息');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (511,'chat_log','聊天','reason',1,'普通聊天信息');
-- --chat_log end----

-- --cheat_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (505,'cheat_log','作弊','reason',11,'使用物品时作弊');
-- --cheat_log end----

-- --exploit_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',0,'捐献军功');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',1,'活动奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',2,'学习属性');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',3,'征战获得');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',4,'军功鼓舞');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',5,'军功突飞');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',6,'升级科技');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',7,'普通任务奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',8,'日常任务奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',9,'GM后台给予');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',10,'创建军团');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (537,'exploit_log','军功改变','reason',11,'节日礼包');
-- --exploit_log end----

-- --gm_command_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (538,'gm_command_log','使用GM命令','reason',0,'非法使用GM命令');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (538,'gm_command_log','使用GM命令','reason',1,'合法使用GM命令');
-- --gm_command_log end----

-- --grain_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (535,'grain_log','粮草改变','reason',0,'购买粮草');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (535,'grain_log','粮草改变','reason',1,'卖出粮草');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (535,'grain_log','粮草改变','reason',2,'黑市买粮');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (535,'grain_log','粮草改变','reason',3,'活动奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (535,'grain_log','粮草改变','reason',4,'世界农场收获');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (535,'grain_log','粮草改变','reason',5,'征兵');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (535,'grain_log','粮草改变','reason',6,'农田收获');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (535,'grain_log','粮草改变','reason',7,'离线农田收获，延迟到上线时处理');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (535,'grain_log','粮草改变','reason',8,'GM后台给予');
-- --grain_log end----

-- --guild_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',0,'创建军团');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',1,'申请加入军团');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',2,'批准加入军团 ');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',3,'拒绝批准加入军团 ');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',5,'解散军团');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',6,'退出军团');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',7,'转让军团长');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',8,'提升职位');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',9,'踢出军团成员');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',10,'修改军团留言');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',11,'修改个人留言');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',12,'修改军团军徽文字');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',13,'提升军团军徽等级');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (543,'guild_log','军团','reason',14,'捐献金币给军团科技');
-- --guild_log end----

-- --invest_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (531,'invest_log','投资','reason',0,'投资');
-- --invest_log end----

-- --item_gen_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (516,'item_gen_log','物品产生','reason',0,'从商店购买');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (516,'item_gen_log','物品产生','reason',1,'打败关卡NPC掉落');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (516,'item_gen_log','物品产生','reason',3,'任务奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (516,'item_gen_log','物品产生','reason',8,'从商城购买');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (516,'item_gen_log','物品产生','reason',9,'拆分物品');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (516,'item_gen_log','物品产生','reason',10,'领取平台奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (516,'item_gen_log','物品产生','reason',12,'debug测试');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (516,'item_gen_log','物品产生','reason',13,'首次登陆奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (516,'item_gen_log','物品产生','reason',25,'通关奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (516,'item_gen_log','物品产生','reason',26,'从军火库缴获获得');
-- --item_gen_log end----

-- --item_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',0,'物品数量增加');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',1,'使用后减少');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',4,'玩家丢弃');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',5,'卖出道具');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',10,'背包中移动：在更新时该不处理，删除时处理');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',17,'加载角色物品时数据错误');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',20,'拆分后减少');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',21,'整理背包改变数量');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',26,'Debug命令全部清空物品');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',27,'超过使用期限');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',35,'装备强化');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',41,'缴获获得的物品');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (502,'item_log','物品更新','reason',42,'直接卖出缴获获得的物品');
-- --item_log end----

-- --learn_ability_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (534,'learn_ability_log','学习能力','reason',0,'学习兵种');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (534,'learn_ability_log','学习能力','reason',1,'学习技能');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (534,'learn_ability_log','学习能力','reason',2,'开启兵种矩阵');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (534,'learn_ability_log','学习能力','reason',3,'开启技能矩阵');
-- --learn_ability_log end----

-- --learn_gift_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (533,'learn_gift_log','学习属性','reason',0,'学习属性');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (533,'learn_gift_log','学习属性','reason',1,'确认学习属性');
-- --learn_gift_log end----

-- --level_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (529,'level_log','建筑级别变化','reason',1,'正常升级');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (529,'level_log','建筑级别变化','reason',3,'GM命令直接修改级别');
-- --level_log end----

-- --mail_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (542,'mail_log','发送邮件','reason',0,'征战');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (542,'mail_log','发送邮件','reason',1,'征服');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (542,'mail_log','发送邮件','reason',2,'农田占领');
-- --mail_log end----

-- --money_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',1,'任务奖励金钱');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',3,'从商店购买');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',4,'背包卖出道具');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',5,'通过debug命令给金钱');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',17,'玩家充值获得钻石');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',18,'每日充值奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',20,'背包开页');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',21,'装备降级返还金钱');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',22,'装备降级');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',23,'装备强化');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',24,'从商城购买花费金钱');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',33,'捐献金币给军团科技');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',34,'升级军徽');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',36,'强制装备强化100%成功');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',37,'立即完成日常任务');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',38,'刷新日常任务');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',39,'领取平台奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',40,'建筑升级');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',41,'增加冷却队列');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',42,'清除冷却队列');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',43,'招募武将');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',44,'征收');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',45,'强制征收');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',46,'征收事件奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',47,'开始训练');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',48,'停止训练');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',49,'改变训练模式');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',50,'购买训练位');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',51,'购买粮食');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',52,'卖出粮食');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',53,'投资');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',54,'领取俸禄');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',55,'银矿收益');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',56,'黑市买粮');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',57,'从军火库缴获道具');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',58,'直接卖出缴获获得的物品');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',59,'接收玩家派遣商队获得');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',60,'向玩家派遣商队');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',61,'学习属性');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',62,'学习能力');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',63,'属臣折磨');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',64,'强攻精英');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',65,'钻石鼓舞');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',66,'活动奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',67,'节日礼包奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',68,'世界聊天消息');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',69,'钻石突飞');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (503,'money_log','金钱改变','reason',70,'购买军令');
-- --money_log end----

-- --online_time_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (518,'online_time_log','玩家在线时长','reason',0,'无意义');
-- --online_time_log end----

-- --pet_exp_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (546,'pet_exp_log','武将经验','reason',1,'GM命令给予经验值');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (546,'pet_exp_log','武将经验','reason',2,'训练增加经验');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (546,'pet_exp_log','武将经验','reason',3,'突飞增加经验');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (546,'pet_exp_log','武将经验','reason',4,'钻石突飞增加经验');
-- --pet_exp_log end----

-- --pet_level_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (547,'pet_level_log','武将级别变化','reason',1,'正常升级');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (547,'pet_level_log','武将级别变化','reason',2,'GM命令给予经验值后升级');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (547,'pet_level_log','武将级别变化','reason',3,'GM命令直接修改级别');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (547,'pet_level_log','武将级别变化','reason',4,'转生改变等级');
-- --pet_level_log end----

-- --pet_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',1,'GM命令获得武将');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',2,'招募武将');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',3,'解雇武将');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',4,'关卡征战获得新的在野武将');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',5,'升官获得新的在野武将');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',6,'武将开始训练');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',7,'武将结束训练');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',8,'武将改变训练模式');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',9,'武将突飞猛进');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',10,'武将钻石突飞');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',13,'武将转生');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',14,'武将学习属性');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',15,'武将学习属性确认');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',20,'正常升级');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',21,'GM命令给予经验值后升级');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',22,'GM命令直接修改级别');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (541,'pet_log','武将','reason',23,'转生改变等级');
-- --pet_log end----

-- --prestige_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',0,'捐献军功获得威望');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',1,'威望自动衰减');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',2,'活动奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',3,'征收事件获得');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',4,'投资获得');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',5,'攻击玩家获得');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',6,'参加城市战');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',7,'普通任务奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',8,'日常任务奖励');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',9,'GM后台给予');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (536,'prestige_log','威望改变','reason',10,'节日礼包');
-- --prestige_log end----

-- --prize_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (544,'prize_log','奖励','reason',0,'奖励成功');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (544,'prize_log','奖励','reason',1,'奖励失败,取平台后奖励条件不满足');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (544,'prize_log','奖励','reason',2,'补偿失败,奖励条件不满足,已扣取');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (544,'prize_log','奖励','reason',3,'奖励失败,状态被打断');
-- --prize_log end----

-- --reborn_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (532,'reborn_log','转生','reason',0,'转生');
-- --reborn_log end----

-- --snap_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (515,'snap_log','快照','reason',0,'登入时的用户状态快照');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (515,'snap_log','快照','reason',1,'退出时的用户状态快照');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (515,'snap_log','快照','reason',2,'被踢掉时的用户状态快照');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (515,'snap_log','快照','reason',3,'用户进入时身上物品的状态');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (515,'snap_log','快照','reason',4,'用户退出时身上物品的状态');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (515,'snap_log','快照','reason',5,'被踢掉时身上物品的状态');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (515,'snap_log','快照','reason',6,'用户进入时宠物的状态');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (515,'snap_log','快照','reason',7,'用户退出时宠物的状态');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (515,'snap_log','快照','reason',8,'被踢掉时的宠物状态');
-- --snap_log end----

-- --task_log begin----
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (510,'task_log','任务','reason',0,'领取任务');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (510,'task_log','任务','reason',1,'放弃任务');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (510,'task_log','任务','reason',2,'完成任务');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (510,'task_log','任务','reason',3,'删除已完成任务');
insert into reason_list(log_type,log_table,log_desc,log_field,reason,reason_name) values (510,'task_log','任务','reason',4,'删除正执行任务');
-- --task_log end----

-- dbversion初始化
USE `war`;

INSERT INTO t_db_version VALUES ('1', now(), '1.0.0.33');
