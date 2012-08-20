package com.pwrd.war.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志系统的日志原因定义
 * 
 * 
 */
public interface LogReasons {

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target( { ElementType.FIELD, ElementType.TYPE })
	public @interface ReasonDesc {
		/**
		 * 原因的文字描述
		 * 
		 * @return
		 */
		String value();
	}

	@Documented
	@Retention(RetentionPolicy.RUNTIME)
	@Target( { ElementType.FIELD, ElementType.TYPE })
	public @interface LogDesc {
		/**
		 * 日志描述
		 * 
		 * @return
		 */
		String desc();
	}

	/**
	 * LogReason的通用接口
	 */
	public static interface ILogReason {
		/**
		 * 取得原因的序号
		 * 
		 * @return
		 */
		public int getReason();

		/**
		 * 获取原因的文本
		 * 
		 * @return
		 */
		public String getReasonText();
	}

	/**
	 * 经验的原因接口
	 * 
	 * @param <E>
	 *            枚举类型
	 */
	public static interface IItemLogReason<E extends Enum<E>> extends
			ILogReason {
		public E getReasonEnum();
	}

	/**
	 * 
	 * 物品产生日志的原因
	 * 
	 */
	@LogDesc(desc = "物品产生")
	public enum ItemGenLogReason implements ILogReason {		
		/** 任务奖励 */
		@ReasonDesc("任务奖励")
		QUEST_BONUS(3, "任务Id={0,number,#}|任务名称={1}|任务类型={2}|道具Id,道具名称,道具数量={3}"),
		/** 拆分物品 */
		@ReasonDesc("拆分物品")
		SPLIT(9, ""),
		/** 领取平台奖励 */
		@ReasonDesc("领取平台奖励")
		PLATFORM_PRIZE(10, "奖励ID={0,number,#}"),
		/** debug测试 */
		@ReasonDesc("debug测试")
		DEBUG_GIVE(12, ""),
		/** 首次登陆奖励 */
		@ReasonDesc("首次登陆奖励")
		FIRST_LOGIN(13, ""),		
		/** 礼包获取 */
		@ReasonDesc("礼包获取")
		ITEM_GIFT_GET(28, "道具Id={0}|道具名称={1}|道具数量{2}"),
		@ReasonDesc("商店购买")
		ITEM_MALL_GET(29, "商店购买"),
		
		@ReasonDesc("打怪掉落")
		MONSTER_DROP(30, "商店购买"),

		@ReasonDesc("摇钱树")
		TREE_FRUIT(31, "摇钱树"),
		@ReasonDesc("在线奖励")
		ONLINE_PRIZE(32, "在线奖励"),
		@ReasonDesc("登陆奖励")
		DAYLOGIN_PRIZE(33, "登陆奖励"),
		
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ItemGenLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
	}

	/**
	 * 物品变更原因
	 * 
	 */
	@LogDesc(desc = "物品更新")
	public enum ItemLogReason implements ILogReason {
		/** 物品数量增加 (具体增加原因，见param) */
		@ReasonDesc("物品数量增加")
		COUNT_ADD(0, ""),
		/** 使用后减少 */
		@ReasonDesc("使用后减少")
		USED(1, ""),
		/** 玩家丢弃 */
		@ReasonDesc("玩家丢弃")
		PLAYER_DROP(4, ""),
		/** 卖出道具 */
		@ReasonDesc("卖出道具")
		SELL_COST(5, "道具Id={0,number,#}|卖出价格={1,number,#}"),
		/** 背包中移动：在更新时该不处理，删除时处理 */
		@ReasonDesc("背包中移动：在更新时该不处理，删除时处理")
		SHOULDERBAG2ITSELF(10, ""),
		/** 加载角色物品时数据错误 */
		@ReasonDesc("加载角色物品时数据错误")
		LOAD_VALID_ERR(17, ""),
		/** 拆分后减少 */
		@ReasonDesc("拆分后减少")
		SPLITTED(20, ""), 
		/** 整理背包改变数量 */
		@ReasonDesc("整理背包改变数量")
		TIDY_UP(21, ""),
		/** Debug命令全部清空物品 */
		@ReasonDesc("Debug命令全部清空物品")
		DEBUG_REMOVE_ALL_ITEM(26, ""),
		/** 超过使用期限 */
		@ReasonDesc("超过使用期限")
		EXPIRED(27, ""),
		/** 回购买入 */
		@ReasonDesc("回购买入")
		REDEEM_BUY(28, ""),
		/** 卷轴获得 */
		@ReasonDesc("卷轴获得")
		REEL(29, ""),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ItemLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return reason;
		}

		@Override
		public String getReasonText() {
			return reasonText;
		}
	}

	/**
	 * 已经废弃
	 * @author gameuser
	 *
	 */
	@LogDesc(desc = "金钱改变")
	public enum MoneyLogReason implements ILogReason {
		/** 任务奖励金钱 */
		@ReasonDesc("任务奖励金钱")
		QUEST_BONUS(1, "任务模版Id={0,number,#}|任务名称={1}|任务类型={2}"),
		/** 从商店购买 */
		@ReasonDesc("从商店购买")
		BUY_FROM_SHOP(3, "道具模版Id={0,number,#}|道具名称={1}"),
		/** 背包卖出道具 */
		@ReasonDesc("背包卖出道具")
		SELL_TO_SHOP(4, "道具模版Id={0,number,#}|道具名称={1}"),
		/** 通过debug命令给金钱 */
		@ReasonDesc("通过debug命令给金钱")
		DEBUG_CMD_GIVE(5, ""),
		/** 玩家充值获得钻石 */
		@ReasonDesc("玩家充值获得钻石")
		REASON_MONEY_CHARGE_DIAMOND(17, "兑换的平台币数量={0}"),
		/** 每日充值奖励 */
		@ReasonDesc("每日充值奖励")
		TODAY_CHARGE_PRIZE(18, "当天充值金额={0}|当次充值金额={1}"),
		/** 立即完成日常任务*/
		@ReasonDesc("立即完成日常任务")
		FINISHNOW_DAILY_QUEST(37, "任务模版Id={0,number,#}|任务名称={1}"),
		/** 刷新日常任务 */
		@ReasonDesc("刷新日常任务")
		REFRESH_DAILY_QUEST(38, ""),
		/** 领取平台奖励 */
		@ReasonDesc("领取平台奖励")
		PLATFORM_PRIZE(39, "奖励ID={0,number,#}"),
		@ReasonDesc("招募武将")
		HIRE_PET(43, "武将模版Id={0,number,#}|武将名称={1}"), 
		@ReasonDesc("世界聊天消息")
		WORLD_CHAT(68, ""),
		/** 回购物品 */
		@ReasonDesc("回购物品")
		REDEEM_BUY(51, "回购物品"),
		/** 购买体力 */
		@ReasonDesc("购买体力")
		BUY_VITALITY(67, "购买体力"),
		/** 打怪掉落 */
		@ReasonDesc("打怪掉落")
		MONSTER_DROP(25, "打怪掉落"),
		@ReasonDesc("刷新神秘商店物品")
		REFRESH_SECRET_SHOP(69, "刷新神秘商店物品"),
		
		@ReasonDesc("竞技场增加挑战次数上限扣除元宝")
		ARENA_ADD_TIME(80, "竞技场增加挑战次数上限扣除元宝"),
		@ReasonDesc("竞技场战斗奖励金钱")
		ARENA_BATTLE_PRIZE(81, "竞技场战斗奖励金钱"),
		@ReasonDesc("竞技场成就奖励金钱")
		ARENA_ACHIEVEMENT_PRIZE(82, "竞技场成就奖励金钱"),
		@ReasonDesc("星魂洗练")
		XINGHUN_XILIAN(83, "星魂洗练"),
		@ReasonDesc("卖出兵法")
		SELL_WARCRAFT(84, "卖出兵法"),
		
		@ReasonDesc("官职")
		TRANSFER_VOCATION(100, "官职"),
		
		@ReasonDesc("摇钱树")
		TREE_FRUIT(101, "摇钱树"),
		
		@ReasonDesc("夺宝")
		ROBBEY(102, "夺宝"),
		
		@ReasonDesc("过关斩将")
		GGZJ(103, "夺宝"),
		@ReasonDesc("扫荡副本")
		REP_AGAINST(104, "扫荡副本"),

		@ReasonDesc("强化装备")
		QIANGHUA(105, "强化装备"),

		@ReasonDesc("副本战斗")
		REP_BATTLE(105, "副本战斗"),
		
		@ReasonDesc("成长提升")
		GROW_UP(106, "成长提升"),

		
		;
		
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private MoneyLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 作弊日志产生原因
	 */
	@LogDesc(desc = "作弊")
	public enum CheatLogReason implements ILogReason {

		/** 使用物品时作弊 */
		@ReasonDesc("使用物品时作弊")
		USE_ITEM(11, "");

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private CheatLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 武将日志产生原因
	 * 
	 */
	@LogDesc(desc = "武将产生")
	public enum PetLogReason implements ILogReason {

		/** GM命令获得武将 */
		@ReasonDesc("GM命令获得武将")
		GIVE_PET_BY_GM_CMD(1, "GM命令获得武将"),
		
		/** 普通招募武将 */
		@ReasonDesc("普通招募")
		HIRE_PET(2, "普通招募"),
				
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private PetLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 经验的原因接口
	 * 
	 * @param <E>
	 *            枚举类型
	 */
	public static interface IExpLogReason<E extends Enum<E>> extends ILogReason {
		public E getReasonEnum();
	}

	/**
	 * 角色的经验值相关
	 */
	@LogDesc(desc = "角色经验")
	public enum ExpLogReason implements IExpLogReason<ExpLogReason> {
		
		
		;
		
		/**
		 * 原因序号
		 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ExpLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public ExpLogReason getReasonEnum() {
			return this;
		}

		@Override
		public int getReason() {
			return this.reason;
		}
		
		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 武将的经验值相关
	 * 
	 */
	@LogDesc(desc = "武将经验")
	public enum PetExpLogReason implements IExpLogReason<PetExpLogReason> {

		
		/** GM命令给予经验值 */
		@ReasonDesc("GM命令给予经验值")
		REASON_EXP_ADD_GM_GIVE(1, ""),

		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private PetExpLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public PetExpLogReason getReasonEnum() {
			return this;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 升级的原因接口
	 * 
	 * @param <E>
	 *            枚举类型
	 */
	public static interface ILevelLogReason<E extends Enum<E>> extends
			ILogReason {
		public E getReasonEnum();
	}

	/**
	 * 建筑的级别变化相关
	 */
	@LogDesc(desc = "建筑级别变化")
	public enum LevelLogReason implements ILevelLogReason<LevelLogReason> {
		/** 正常升级 */
		@ReasonDesc("正常升级")
		REASON_LEVEL_UP_NORMAL(1, "建筑等级列表={0}"),
		/** GM命令直接修改级别 */
		@ReasonDesc("GM命令直接修改级别")
		REASON_LEVEL_UP_GM_SET_DIRECT(3, "");
		
		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private LevelLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public LevelLogReason getReasonEnum() {
			return this;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 武将级别变化相关
	 */
	@LogDesc(desc = "武将级别变化")
	public enum PetLevelLogReason implements ILevelLogReason<PetLevelLogReason> {
		/** 正常升级 */
		@ReasonDesc("正常升级")
		REASON_LEVEL_UP_NORMAL(1, "武将Id={0,number,#}|武将名称={1}|武将等级={2}"),
		/** GM命令给予经验值后升级 */
		@ReasonDesc("GM命令给予经验值后升级")
		REASON_LEVEL_UP_GM_GIVE_EXP(2, ""),
		/** GM命令直接修改级别 */
		@ReasonDesc("GM命令直接修改级别")
		REASON_LEVEL_UP_GM_SET_DIRECT(3, ""),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private PetLevelLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public PetLevelLogReason getReasonEnum() {
			return this;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 任务日志产生原因
	 */
	@LogDesc(desc = "任务")
	public enum TaskLogReason implements ILogReason {
		/** 领取任务 */
		@ReasonDesc("领取任务")
		REASON_TASK_ACCEPT(0, "任务Id={0,number,#}|任务名称={1}"),
		/** 放弃任务 */
		@ReasonDesc("放弃任务")
		REASON_TASK_GIVEUP(1, "任务Id={0,number,#}|任务名称={1}"),
		/** 完成任务 */
		@ReasonDesc("完成任务")
		REASON_TASK_FINISH(2, "任务Id={0,number,#}|任务名称={1}"),
		/** 删除已完成任务 */
		@ReasonDesc("删除已完成任务")
		REASON_TASK_FINISH_DELETE(3, ""),
		/** 删除正执行任务 */
		@ReasonDesc("删除正执行任务")
		REASON_TASK_DOING_DELETE(4, "");

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private TaskLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 聊天日志产生原因
	 */
	@LogDesc(desc = "聊天")
	public enum ChatLogReason implements ILogReason {
		/** 包含不良信息 */
		@ReasonDesc("包含不良信息")
		REASON_CHAT_DIRTY_WORD(0, ""),
		/** 普通聊天信息 */
		@ReasonDesc("普通聊天信息")
		REASON_CHAT_COMMON(1, ""), ;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ChatLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}


	/**
	 * 快照日志的产生原因
	 */
	@LogDesc(desc = "快照")
	public enum SnapLogReason implements ILogReason {
		/** 登入时的用户状态快照 */
		@ReasonDesc("登入时的用户状态快照")
		REASON_SNAP_LOGIN_STATUS(0, ""),
		/** 退出时的用户状态快照 */
		@ReasonDesc("退出时的用户状态快照")
		REASON_SNAP_LOGOUT_STATUS(1, ""),
		/** 断线时的用户状态快照 */
		@ReasonDesc("被踢掉时的用户状态快照")
		REASON_SNAP_KICK_STATUS(2, ""),
		/** 用户进入时身上物品的状态 */
		@ReasonDesc("用户进入时身上物品的状态")
		REASON_SNAP_LOGIN_ITEM(3, ""),
		/** 用户退出时身上物品的状态 */
		@ReasonDesc("用户退出时身上物品的状态")
		REASON_SNAP_LOGOUT_ITEM(4, ""),
		/** 异常掉线时的保存 */
		@ReasonDesc("被踢掉时身上物品的状态")
		REASON_SNAP_KICK_ITEM(5, ""),
		/** 用户进入时武将的状态 */
		@ReasonDesc("用户进入时武将的状态")
		REASON_SNAP_LOGIN_PET(6, ""),
		/** 用户退出时武将的状态 */
		@ReasonDesc("用户退出时武将的状态")
		REASON_SNAP_LOGOUT_PET(7, ""),
		/** 异常掉线时的武将状态 */
		@ReasonDesc("被踢掉时的武将状态")
		REASON_SNAP_KICK_PET(8, ""), 
		/** 用户进入时武将的状态 */
		@ReasonDesc("用户进入时副本信息的状态")
		REASON_SNAP_LOGIN_REPINFO(6, ""),
		/** 用户退出时武将的状态 */
		@ReasonDesc("用户退出时副本信息的状态")
		REASON_SNAP_LOGOUT_REPINFO(7, ""),
		/** 异常掉线时的武将状态 */
		@ReasonDesc("被踢掉时的副本信息状态")
		REASON_SNAP_KICK_REPINFO(8, ""),;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private SnapLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 玩家基础日志原因
	 */
	@LogDesc(desc = "玩家基础日志")
	public enum BasicPlayerLogReason implements ILogReason {
		/** 正常登录 */
		@ReasonDesc("正常登录")
		REASON_NORMAL_LOGIN(0, ""),
		/** 正常退出 */
		@ReasonDesc("正常退出")
		REASON_NORMAL_LOGOUT(2, ""),
		/** 非战斗状态掉线 */
		@ReasonDesc("非战斗状态掉线")
		REASON_LOGOUT_LOST_CONNECTION(3, ""),
		/** 服务器下线 */
		@ReasonDesc("服务器下线")
		REASON_LOGOUT_SERVER_OFFLINE(9, ""),
		/** 服务器停机,保存玩家数据 */
		@ReasonDesc("服务器停机,保存玩家数据")
		REASON_LOGOUT_SERVER_SHUTDOWN(10, ""),
		/** GM踢人 */
		@ReasonDesc("GM踢人")
		GM_KICK(13,""),
		/** 自己踢掉自己 */
		@ReasonDesc("自己踢掉自己")
		MULTI_LOGIN(15,""),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private BasicPlayerLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * Gm命令日志原因
	 * 
	 */
	@LogDesc(desc = "使用GM命令")
	public enum GmCommandLogReason implements ILogReason {
		/** 非法使用GM命令 */
		@ReasonDesc("非法使用GM命令")
		REASON_INVALID_USE_GMCMD(0, ""),
		/** 合法使用GM命令 */
		@ReasonDesc("合法使用GM命令")
		REASON_VALID_USE_GMCMD(1, ""), 
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private GmCommandLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 在线日志原因
	 * 
	 */
	@LogDesc(desc = "玩家在线时长")
	public enum OnlineTimeLogReason implements ILogReason {
		/** 无意义 */
		@ReasonDesc("无意义")
		DEFAULT(0, ""), 
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private OnlineTimeLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	/**
	 * 充值日志产生原因
	 * 
	 */
	@LogDesc(desc = "充值")
	public enum ChargeLogReason implements ILogReason {
		/** 充值成功 */
		@ReasonDesc("充值成功")
		CHARGE_DIAMOND_SUCCESS(0, ""),
		/** 充值失败 */
		@ReasonDesc("充值失败")
		CHARGE_DIAMOND_FAIL(1, ""),
		/** 调用充值接口异常 */
		@ReasonDesc("调用充值接口异常")
		CHARGE_DIAMOND_INVOKE_FAIL(2, ""),
		/** 充值后钻石数溢出 */
		@ReasonDesc("充值后钻石数溢出")
		CHARGE_DIAMOND_OVERFLOW(3, "");

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ChargeLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	@LogDesc(desc = "奖励")
	public enum PrizeLogReason implements ILogReason {
		/** 奖励成功 */
		@ReasonDesc("奖励成功")
		PRIZE_SUCCESS(0, ""),
		/** 奖励失败,取平台后奖励条件不满足 */
		@ReasonDesc("奖励失败,取平台后奖励条件不满足")
		PRIZE_FAIL_CONDITION_AFTER_UNMEET(1, ""),
		/** 补偿失败,奖励条件不满足,已扣取 */
		@ReasonDesc("补偿失败,奖励条件不满足,已扣取")
		PRIZE_FAIL_USER_PRIZE_AFTER_UNMEET(2, ""),
		/** 奖励失败,状态被打断 */
		@ReasonDesc("奖励失败,状态被打断")
		PRIZE_FAIL_GET_PRIZE_STATE_EXIT(3, ""),
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private PrizeLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}

	@LogDesc(desc = "军团")
	public enum GuildLogReason implements ILogReason {
		/** 创建军团 */
		@ReasonDesc("创建军团")
		GUILD_CREATE(0, ""),
		/** 申请加入军团 */
		@ReasonDesc("申请加入军团")
		GUILD_JOIN(1, ""),
		/** 批准加入军团 */
		@ReasonDesc("批准加入军团 ")
		GUILD_APPLY_JOIN(2, "军团长ID={0,number,#}|军团长名称={1}|批准成员ID={2,number,#}|批准成员名称={3}"),		
		/** 拒绝批准加入军团 */
		@ReasonDesc("拒绝批准加入军团 ")
		GUILD_REJECT_JOIN(3, "军团长ID={0,number,#}|军团长名称={1}|拒绝成员ID={2,number,#}|拒绝成员名称={3}"),
		/** 解散军团 */
		@ReasonDesc("解散军团")
		GUILD_DISMISS(5, ""),
		/** 退出军团 */
		@ReasonDesc("退出军团")
		GUILD_QUIT(6, "军团职位={0}"),
		/** 转让军团长 */
		@ReasonDesc("转让军团长")
		GUILD_TRAN_LEADER(7, ""),
		/** 提升职位 */
		@ReasonDesc("提升职位")
		GUILD_MODIFY_RANK(8, "提升前职位={0}|提升后职位={1}"),
		/** 踢出军团成员 */
		@ReasonDesc("踢出军团成员")
		GUILD_FIRE_MEMBER(9, "操作者职位={0}|被踢出军团成员ID={1,number,#}|被踢出军团成员名称={2}"),
		/** 修改军团留言 */
		@ReasonDesc("修改军团留言")
		GUILD_MODIFY_MESSAGE(10, ""),
		/** 修改个人留言 */
		@ReasonDesc("修改个人留言")
		GUILD_MODIFY_PRIVATE_MESSAGE(11, ""),		
		/** 修改军团军徽文字 */
		@ReasonDesc("修改军团军徽文字")
		GUILD_MODIFY_SYMBOL(12,""),
		/** 提升军团军徽等级 */
		@ReasonDesc("提升军团军徽等级")
		GUILD_LEVEL_UP_SYMBOL_LEVEL(13,""),
		/** 捐献金币给军团科技 */
		@ReasonDesc("捐献金币给军团科技")
		GUILD_CONTRIB_GOLD_TO_TECH(14,"捐献目标科技ID={0,number,#}|捐献目标科技名称={1}|捐献后科技等级={2}|捐献金币数量={3,number,#}"),
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private GuildLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	@LogDesc(desc = "战斗")
	public enum BattleLogReason implements ILogReason {
		
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		BattleLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	@LogDesc(desc = "发送邮件")
	public enum MailLogReason implements ILogReason {

		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private MailLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 剧情产生原因
	 */
	@LogDesc(desc = "剧情")
	public enum StoryLogReason implements ILogReason {
		/** 领取任务 */
		@ReasonDesc("领取任务")
		REASON_STORY_ACCEPT_QUEST(0, "剧情Id={0,number,#}"),
		/** 完成任务 */
		@ReasonDesc("完成任务")
		REASON_STORY_FINISH_QUSET(1, "剧情Id={0,number,#}"),
		/** 进入副本 */
		@ReasonDesc("进入副本")
		REASON_STORY_ENTER_REP(2, "剧情Id={0,number,#}"),
		/** 杀死怪物 */
		@ReasonDesc("杀死怪物")
		REASON_STORY_KILL_MONSTER(3, "剧情Id={0,number,#}");

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private StoryLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 参加活动原因
	 */
	@LogDesc(desc = "参加活动日志")
	public enum ActivityLogReason implements ILogReason {
		/** 活动 */
		@ReasonDesc("活动")
		JOIN(0, "参加活动"),
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private ActivityLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 货币产出或者消耗原因
	 */
	@LogDesc(desc = "货币日志")
	public enum CurrencyLogReason implements ILogReason {
		@ReasonDesc("NULL")
		NULL(0, ""),
		
		/**VIP*/
		@ReasonDesc("VIP")
		VIP(1, "VIP"),
		
		/** 商城购买 */
		@ReasonDesc("商城购买 ")
		SHANGCHENG(2, "商城购买"),
		
		/** 活动*/
		@ReasonDesc("活动")
		HUODONG(3, "活动"),
		
		/** GM*/
		@ReasonDesc("GM")
		GM(4, "GM命令"), 
		
		/** 系统*/
		@ReasonDesc("系统")
		XITONG(5, "系统赠送"), 
		
		/** 守军争夺战*/
		@ReasonDesc("守军争夺战")
		SHOUJUN(6, "守军争夺战"), 
		
		/** 竞技场*/
		@ReasonDesc("竞技场")
		JINGJICHANG(7, "竞技场"), 
		
		/** 强化*/
		@ReasonDesc("强化")
		QIANGHUA(8, "强化"), 
		
		/** 提升成长*/
		@ReasonDesc("提升成长")
		TISHENGCZ(9, "提升成长"), 
		
		/** 修炼*/
		@ReasonDesc("修炼")
		XIULIAN(10, "修炼"),
		
		/** 武将*/
		@ReasonDesc("武将")
		WUJIANG(11, "武将"), 
		
		/** 副本*/
		@ReasonDesc("副本")
		REP(12, "副本"), 
		
		/** 夺宝*/
		@ReasonDesc("夺宝")
		DUOBAO(13, "夺宝"), 
		
		/** 爬塔*/
		@ReasonDesc("爬塔")
		TOWER(14, "爬塔"),
		
		/** 购买体力*/
		@ReasonDesc("购买体力")
		VIT(15, "购买体力"), 
		
		/** 兵法*/
		@ReasonDesc("兵法")
		WARCRAFT(16, "兵法"), 
		
		/** 转职*/
		@ReasonDesc("转职")
		VOCATIONTRANFER(17, "转职"), 
		
		/** 摇钱树*/
		@ReasonDesc("摇钱树模块")
		TREE(18, "摇钱树"), 
		
		/** 阵营*/
		@ReasonDesc("阵营")
		ZHENYING(19, "阵营"), 
		
		/** 充值*/
		@ReasonDesc("充值")
		CHARGE(20, "充值"), 
		
		/** 每日任务*/
		@ReasonDesc("每日任务")
		DAYTASK(21, "每日任务"), 
		
		/** 怪物掉落*/
		@ReasonDesc("怪物掉落")
		MONSTER(22, "怪物掉落"), 
		
		/** 卖出物品*/
		@ReasonDesc("卖出物品")
		SELL(23, "卖出物品"), 
		
		/** 过关斩将*/
		@ReasonDesc("过关斩将")
		GGZJ(24, "过关斩将"), 
		
		/** 南蛮入侵*/
		@ReasonDesc("南蛮入侵")
		BOSSACTIVITY(25, "南蛮入侵"), 
		
		
		/** 神秘商店 */
		@ReasonDesc("神秘商店 ")
		SECRET_SHOP(26, "神秘商店"),
		/** 阵营战 */
		@ReasonDesc("阵营战")
		CAMP_WAR(27, "阵营战"),
		
		/** 其他*/
		@ReasonDesc("其他")
		QITA(100, "其他"), 
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private CurrencyLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 穿上或卸载装备原因
	 */
	@LogDesc(desc = "穿上或卸载装备日志")
	public enum EquipmentLogReason implements ILogReason {
		/** 人穿*/
		@ReasonDesc("人穿")
		HUMAN_EQ(1, "人穿"), 
		
		/** 人换*/
		@ReasonDesc("人换")
		HUMAN_SWAP(2, "人换"), 
		
		/** 人脱*/
		@ReasonDesc("人脱")
		HUMAN_UNEQ(3, "人脱"), 
		
		/** 宠穿*/
		@ReasonDesc("宠穿")
		PET_EQ(4, "宠穿"), 
		
		/** 人换*/
		@ReasonDesc("宠换")
		PET_SWAP(5, "宠换"), 
		
		/** 人脱*/
		@ReasonDesc("宠脱")
		PET_UNEQ(6, "宠脱"),
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private EquipmentLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 家族原因
	 */
	@LogDesc(desc = "参加活动日志")
	public enum FamilyLogReason implements ILogReason {
		/** 家族建立*/
		@ReasonDesc("家族建立")
		CREATE(1, "家族建立"), 
		
		/** 家族加入*/
		@ReasonDesc("家族加入")
		APPLY(2, "家族加入"), 
		
		/** 家族解散*/
		@ReasonDesc("家族解散")
		DELETE(3, "家族解散"), 
		
		/** 离开家族*/
		@ReasonDesc("离开家族")
		LEAVE(4, "离开家族"), 
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private FamilyLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 添加或者删除好友原因
	 */
	@LogDesc(desc = "添加或者删除好友日志")
	public enum FriendLogReason implements ILogReason {
		/** 好友添加*/
		@ReasonDesc("好友添加")
		ADD(1, "好友添加"), 
		
		/** 好友删除*/
		@ReasonDesc("好友删除")
		DELETE(2, "好友删除"), 
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private FriendLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 物品获得原因
	 */
	@LogDesc(desc = "物品获得日志")
	public enum GetItemLogReason implements ILogReason {
		@ReasonDesc("NULL")
		NULL(0, null),
		
		/**物品获得*/
		@ReasonDesc("物品获得")
		ITEMGET(1, "物品获得"),
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private GetItemLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 人物升级原因
	 */
	@LogDesc(desc = "人物升级日志")
	public enum LevelUpLogReason implements ILogReason {
		@ReasonDesc("NULL")
		NULL(0, null),
		
		/**用户升级*/
		@ReasonDesc("用户升级")
		LEVELUP(1, "LEVELUP"),
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private LevelUpLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 登入登出原因
	 */
	@LogDesc(desc = "登入登出日志")
	public enum LoginLogReason implements ILogReason {
		/** 登入*/
		@ReasonDesc("登入")
		LOGIN(1, "登入"), 
		
		/** 登出*/
		@ReasonDesc("登出")
		LOGOUT(2, "登出")
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private LoginLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 官职改变原因
	 */
	@LogDesc(desc = "官职改变日志")
	public enum OfficialLogReason implements ILogReason {
		/** 培养*/
		@ReasonDesc("培养")
		PEIYANG(1, "培养"), 
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private OfficialLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 任务
	 */
	@LogDesc(desc = "任务日志")
	public enum QuestLogReason implements ILogReason {
		/** 接受任务*/
		@ReasonDesc("接受任务")
		ACCEPT(1, "接受任务"), 
		
		/** 完成任务*/
		@ReasonDesc("完成任务")
		COMPLETE(2, "完成任务"),
		
		/** 取消任务*/
		@ReasonDesc("取消任务")
		CANCEL(3, "取消任务")
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private QuestLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 副本掉落原因
	 */
	@LogDesc(desc = "副本掉落日志")
	public enum RepDropLogReason implements ILogReason {
		@ReasonDesc("NULL")
		NULL(0, null),
		
		/**副本完成结算*/
		@ReasonDesc("副本完成结算")
		REP_COMPLETE(1, "REPCOMPLETE"),
		
		/**杀怪*/
		@ReasonDesc("杀怪")
		KILL(2, "杀怪"),
		
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private RepDropLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 完成副本
	 */
	@LogDesc(desc = "完成副本日志")
	public enum RepLogReason implements ILogReason {
		@ReasonDesc("NULL")
		NULL(0, null),
		
		/**完成副本*/
		@ReasonDesc("完成副本")
		REPCOMPLETE(1, "REPCOMPLETE")
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private RepLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 爬塔
	 */
	@LogDesc(desc = "爬塔日志")
	public enum TowerLogReason implements ILogReason {
		@ReasonDesc("NULL")
		NULL(0, null),
		
		/**将星*/
		@ReasonDesc("将星")
		TOWER(1, "TOWER"),
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private TowerLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * VIP
	 */
	@LogDesc(desc = "VIP改变日志")
	public enum VipLogReason implements ILogReason {
		@ReasonDesc("NULL")
		NULL(0, null),
		
		/** vip*/
		@ReasonDesc("vip改变")
		CHANGE(1, "vip改变"),
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private VipLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 兵法
	 */
	@LogDesc(desc = "兵法日志")
	public enum WarcraftLogReason implements ILogReason {
		@ReasonDesc("NULL")
		NULL(0, null),
		
		/**兵法获得*/
		@ReasonDesc("兵法获得")
		GRASP(1, "兵法获得"),
		
		/**兵法合成*/
		@ReasonDesc("兵法合成")
		HECHENG(2, "兵法合成"),
		
		/**兵法丢弃*/
		@ReasonDesc("兵法丢弃")
		DELETE(3, "兵法丢弃"),
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private WarcraftLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
	
	/**
	 * 星魂
	 */
	@LogDesc(desc = "星魂日志")
	public enum XinghunLogReason implements ILogReason {
		
		@ReasonDesc("NULL")
		NULL(0, null),
		
		/**星魂洗练*/
		@ReasonDesc("星魂洗练")
		XILIAN(1, "XILIAN"),
		
		;

		/** 原因序号 */
		public final int reason;
		/** 原因文本 */
		public final String reasonText;

		private XinghunLogReason(int reason, String reasonText) {
			this.reason = reason;
			this.reasonText = reasonText;
		}

		@Override
		public int getReason() {
			return this.reason;
		}

		@Override
		public String getReasonText() {
			return this.reasonText;
		}
	}
}
