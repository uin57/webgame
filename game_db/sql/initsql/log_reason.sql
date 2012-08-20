use tr_log;
truncate reason_list;
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

