package com.pwrd.war.gameserver.common.i18n.constants;

import com.pwrd.war.core.annotation.SysI18nString;

public class CommonLangConstants_10000  {
	/** 公用常量 1 ~ 10000 */
	public static Integer COMMON_BASE = 10000;
	@SysI18nString(content = "冷却时间未到")
	public static final Integer COMMON_COOLINGDOWN = ++COMMON_BASE;
	@SysI18nString(content = "您的{0}不足", comment = "{0}某种属性，例如金钱、声望等等")
	public static final Integer COMMON_NOT_ENOUGH = ++COMMON_BASE;
	@SysI18nString(content = "连接服务器失败")
	public static final Integer CONNECT_SERVER_FAIL = ++COMMON_BASE;
	@SysI18nString(content = "该玩家已下线")
	public static final Integer PLAYER_OFFLINE = ++COMMON_BASE;
	@SysI18nString(content = "参数不正确")
	public static final Integer DEBUG_PARAM_ERROR = ++COMMON_BASE;
	@SysI18nString(content = "{0}为空", comment = "{0}角色名或宠物名或签名")
	public static final Integer GAME_INPUT_NULL = ++COMMON_BASE;
	@SysI18nString(content = "{0}最小长度为{1}个字符", comment = "{0}角色名或宠物名或签名,{1}为长度")
	public static final Integer GAME_INPUT_TOO_SHORT = ++COMMON_BASE;
	@SysI18nString(content = "{0}最大长度为{1}个英文字符或{2}个中文字符", comment = "{0}角色名或宠物名或签名,{1}为长度")
	public static final Integer GAME_INPUT_TOO_LONG = ++COMMON_BASE;
	@SysI18nString(content = "{0}包含异常字符", comment = "{0}输入项")
	public static final Integer GAME_INPUT_ERROR1 = ++COMMON_BASE;
	@SysI18nString(content = "{0}包含屏蔽字符", comment = "{0}输入项")
	public static final Integer GAME_INPUT_ERROR2 = ++COMMON_BASE;
	@SysI18nString(content = "{0}包含非法字符", comment = "{0}输入项")
	public static final Integer GAME_INPUT_ERROR3 = ++COMMON_BASE;
	@SysI18nString(content = "角色名")
	public static final Integer GAME_INPUT_TYPE_CHARACTER_NAME = ++COMMON_BASE;
	@SysI18nString(content = "称号")
	public static final Integer GAME_INPUT_TYPE_FLAG_WORD = ++COMMON_BASE;
	@SysI18nString(content = "留言")
	public static final Integer GAME_INPUT_TYPE_LEAVE_WORD = ++COMMON_BASE;
	@SysI18nString(content = "邮件标题")
	public static final Integer GAME_INPUT_TYPE_MAIL_TITLE = ++COMMON_BASE;
	@SysI18nString(content = "邮件内容")
	public static final Integer GAME_INPUT_TYPE_MAIL_CONTENT = ++COMMON_BASE;
	@SysI18nString(content = "自定义聊天频道")
	public static final Integer GAME_INPUT_TYPE_CHATCHANNEL = ++COMMON_BASE;
	@SysI18nString(content = "当前状态下不能这样做")
	public static final Integer CANNOT_DO_IT_NOW = ++COMMON_BASE;
	@SysI18nString(content = "您获得了{0}个{1}", comment = "{0}获得事物的个数{1}获得的事物名称")
	public static final Integer GET_SOMETHING = ++COMMON_BASE;
	@SysI18nString(content = "服务器退出保存中，请稍候")
	public static final Integer WAITING_EXIT = ++COMMON_BASE;
	@SysI18nString(content = "您的账号已经从另一个客户端登录")
	public static final Integer LOGIN_ON_ANOTHER_CLIENT = ++COMMON_BASE;
	@SysI18nString(content = "加载角色列表错误")
	public static final Integer LOAD_PLAYER_ROLES = ++COMMON_BASE;
	@SysI18nString(content = "加载角色信息错误")
	public static final Integer LOAD_PLAYER_SELECTED_ROLE = ++COMMON_BASE;
	@SysI18nString(content = "忙碌中")
	public static final Integer IS_BUSY = ++COMMON_BASE;
	@SysI18nString(content = "确定")
	public static final Integer OK = ++COMMON_BASE;
	@SysI18nString(content = "取消")
	public static final Integer CANCEL = ++COMMON_BASE;
	@SysI18nString(content = "胜利")
	public static final Integer WIN = ++COMMON_BASE;
	@SysI18nString(content = "失败")
	public static final Integer LOSS = ++COMMON_BASE;	
	@SysI18nString(content = "功能未开放")
	public static final Integer FUNC_INVALID = ++COMMON_BASE;
	@SysI18nString(content = "系统")
	public static final Integer SYSTEM_MAIL_SENDER_NAME = ++COMMON_BASE;	
	@SysI18nString(content = "GM踢人")
	public static final Integer GM_KICK = ++COMMON_BASE;
	@SysI18nString(content = "您目前与服务器断开连接")
	public static final Integer SERVER_ERROR = ++COMMON_BASE;
	@SysI18nString(content = "该玩家已离线")
	public static final Integer OBJECT_NOT_EXIST = ++COMMON_BASE;
	@SysI18nString(content = "性别错误")
	public static final Integer SEX_ERROR = ++COMMON_BASE;
	@SysI18nString(content = "职业错误")
	public static final Integer VOCATION_ERROR = ++COMMON_BASE;
	@SysI18nString(content = "等级不够")
	public static final Integer LEVEL_NOT_REACH = ++COMMON_BASE;
	@SysI18nString(content = "vip等级不足")
	public static final Integer VIP_LEVEL_NOT_ENOUGH = ++COMMON_BASE;
	@SysI18nString(content = "阅历不够")
	public static final Integer SEE_NOT_REACH = ++COMMON_BASE;
	@SysI18nString(content = "武将类型错误")
	public static final Integer PETTYPE_ERROR = ++COMMON_BASE;
	@SysI18nString(content = "系统内部错误")
	public static final Integer SYSTEM_ERROR = ++COMMON_BASE;
	@SysI18nString(content = "心跳出现错误")
	public static final Integer DC_HEARTBEAT_EXCEPTION = ++COMMON_BASE;
	@SysI18nString(content = "处理消息错误")
	public static final Integer DC_HANDLE_MSG_EXCEPTION = ++COMMON_BASE;
	@SysI18nString(content = "该玩家不存在")
	public static final Integer NO_THIS_PLAYER = ++COMMON_BASE;
	@SysI18nString(content = "服务器即将关闭...")
	public static final Integer SERVER_WILL_SHUTDOWN = ++COMMON_BASE;
	@SysI18nString(content = "+{0}{1}", comment="例如+25经验")
	public static final Integer ADDPROP = ++COMMON_BASE;
	
	@SysI18nString(content = "未知国家")
	public static final Integer CAMP_NONE = ++COMMON_BASE;
	@SysI18nString(content = "魏")
	public static final Integer CAMP_WEI = ++COMMON_BASE;
	@SysI18nString(content = "蜀")
	public static final Integer CAMP_SHU = ++COMMON_BASE;
	@SysI18nString(content = "吴")
	public static final Integer CAMP_WU = ++COMMON_BASE;
	@SysI18nString(content = "群雄")
	public static final Integer CAMP_QUN = ++COMMON_BASE;
	@SysI18nString(content = "功勋")
	public static final Integer MERIT = ++COMMON_BASE;
	@SysI18nString(content = "阅历")
	public static final Integer SEE = ++COMMON_BASE;
	@SysI18nString(content = "经验")
	public static final Integer EXP = ++COMMON_BASE;
	@SysI18nString(content = "声望")
	public static final Integer SHENGWANG = ++COMMON_BASE;
	@SysI18nString(content = "攻击")
	public static final Integer ATK = ++COMMON_BASE;
	@SysI18nString(content = "防御")
	public static final Integer DEF = ++COMMON_BASE;
	@SysI18nString(content = "生命")
	public static final Integer HP = ++COMMON_BASE;
	@SysI18nString(content = "官职经验")
	public static final Integer GUAN_EXP = ++COMMON_BASE;
	@SysI18nString(content = "体力")
	public static final Integer VIT = ++COMMON_BASE;
	
	public static Integer COMMON_BASE_11000 = 10000 + 1000;
	/** 货币相关的常量15001 ~ 16000 */ 
	@SysI18nString(content = "元宝")
	public static final Integer CURRENCY_NAME_GOLD = ++COMMON_BASE_11000; 
	@SysI18nString(content = "元宝")
	public static final Integer CURRENCY_NAME_COUPON = ++COMMON_BASE_11000;
	@SysI18nString(content = "铜钱")
	public static final Integer CURRENCY_NAME_COINS = ++COMMON_BASE_11000;
	@SysI18nString(content = "铜币")
	public static final Integer CURRENCY_NAME_SLIVER = ++COMMON_BASE_11000;
	@SysI18nString(content = "您可携带的铜钱已达上限，该等级最多可携带{0}铜钱。")
	public static final Integer MAX_COINS = ++COMMON_BASE_11000;
	@SysI18nString(content = "您可携带的元宝已达上限，最多可携带{0}元宝。")
	public static final Integer MAX_GOLD = ++COMMON_BASE_11000;
	
	
	public static Integer COMMON_BASE_11100 = 10000 + 1100;
	/** Local平台的描述 11100 ~ 12000 */
 
	@SysI18nString(content = "local接口未开启")
	public static final Integer LOCAL_TURN_OFF = ++COMMON_BASE_11100;
	@SysI18nString(content = "签名验证失败")
	public static final Integer LOCAL_LOGIN_SIGN_AUTH_FAIL = ++COMMON_BASE_11100;
	@SysI18nString(content = "时间戳过期")
	public static final Integer LOCAL_LOGIN_TIMESTAMP_EXPIRSE = ++COMMON_BASE_11100;
	@SysI18nString(content = "有参数为空或者格式不正确")
	public static final Integer LOCAL_LOGIN_PARAM_FORMAT_ERROR = ++COMMON_BASE_11100;
	@SysI18nString(content = "用户名密码验证未通过")
	public static final Integer LOCAL_LOGIN_PASS_ERR = ++COMMON_BASE_11100;
	@SysI18nString(content = "用户已经被锁定")
	public static final Integer LOCAL_LOGIN_ACCOUNT_BLOCK = ++COMMON_BASE_11100;
	@SysI18nString(content = "密保未通过")
	public static final Integer LOCAL_LOGIN_PASS_PROTECT_ERR = ++COMMON_BASE_11100;
	@SysI18nString(content = "cookie验证未通过")
	public static final Integer LOCAL_LOGIN_COOKIE_AUTH_FAIL = ++COMMON_BASE_11100;
	@SysI18nString(content = "token验证未通过")
	public static final Integer LOCAL_LOGIN_TOKEN_AUTH_FAIL = ++COMMON_BASE_11100;
	@SysI18nString(content = "大区验证未通过")
	public static final Integer LOCAL_LOGIN_REGION_AUTH_FAIL = ++COMMON_BASE_11100;
	@SysI18nString(content = "账户未激活")
	public static final Integer LOCAL_LOGIN_INACTIVE_FAIL = ++COMMON_BASE_11100;
	@SysI18nString(content = "签名验证失败")
	public static final Integer LOCAL_CHARGE_SIGN_AUTH_FAIL = ++COMMON_BASE_11100;
	@SysI18nString(content = "时间戳过期")
	public static final Integer LOCAL_CHARGE_TIMESTAMP_EXPIRSE = ++COMMON_BASE_11100;
	@SysI18nString(content = "有参数为空或者格式不正确")
	public static final Integer LOCAL_CHARGE_PARAM_FORMAT_ERROR = ++COMMON_BASE_11100;
	@SysI18nString(content = "余额不足")
	public static final Integer LOCAL_CHARGE_BALANCE_ERR = ++COMMON_BASE_11100;
	@SysI18nString(content = "真实姓名不合法")
	public static final Integer LOCAL_WALLOW_TRUE_NAME_ERROR = ++COMMON_BASE_11100;
	@SysI18nString(content = "身份证格式错误")
	public static final Integer LOCAL_WALLOW_IDCARD_ERROR = ++COMMON_BASE_11100;
	@SysI18nString(content = "真实姓名或身份证号不合法")
	public static final Integer LOCAL_WALLOW_INFO_ERROR = ++COMMON_BASE_11100;
	@SysI18nString(content = "登录位置错误")
	public static final Integer LOCAL_UNKNOW_ERROR = ++COMMON_BASE_11100;
	
	
	public static Integer COMMON_BASE_12000 = 10000 + 2000;
	/** 聊天相关常量 12000 ~ 13000 */
	 
	@SysI18nString(content = "您说话太快")
	public static final Integer CHAT_TOO_FAST = ++COMMON_BASE_12000;
	@SysI18nString(content = "玩家已下线或者不存在")
	public static final Integer CHAT_PLAYER_NOTEXIST = ++COMMON_BASE_12000;
	@SysI18nString(content = "司令部等级达到{0}级后才可以使用世界频道 ", comment = "{0}世界聊天需要的最小级别")
	public static final Integer CHAT_WORLD_MIN_LEVEL = ++COMMON_BASE_12000;
	@SysI18nString(content = "世界频道 ")
	public static final Integer CHAT_WORLD_CHANNEL = ++COMMON_BASE_12000;
	@SysI18nString(content = "{0}的最小发言间隔为{1}秒 ")
	public static final Integer CHAT_WORLD_TOO_FAST = ++COMMON_BASE_12000;
	
	public static Integer COMMON_BASE_13000 = 10000 + 3000;
	/** 邮件系统相关常量 13000 ~ 14000 */ 
	@SysI18nString(content = "未知类型邮件", comment = "未知类型邮件")
	public static final Integer MAIL_TYPE_NONE = ++COMMON_BASE_13000;
	@SysI18nString(content = "玩家邮件", comment = "玩家邮件")
	public static final Integer MAIL_TYPE_PRIVATE = ++COMMON_BASE_13000;
	@SysI18nString(content = "军团邮件", comment = "军团邮件")
	public static final Integer MAIL_TYPE_GUILD = ++COMMON_BASE_13000;
	@SysI18nString(content = "系统邮件", comment = "系统邮件")
	public static final Integer MAIL_TYPE_SYSTEM = ++COMMON_BASE_13000;
	@SysI18nString(content = "战报邮件", comment = "战报邮件")
	public static final Integer MAIL_TYPE_BATTLE = ++COMMON_BASE_13000;
	@SysI18nString(content = "史实邮件", comment = "史实邮件")
	public static final Integer MAIL_TYPE_STORY = ++COMMON_BASE_13000;
	@SysI18nString(content = "未读", comment = "未读状态")
	public static final Integer MAIL_STATUS_UNREAD = ++COMMON_BASE_13000;
	@SysI18nString(content = "已读", comment = "已读状态")
	public static final Integer MAIL_STATUS_READED = ++COMMON_BASE_13000;
	@SysI18nString(content = "已保存", comment = "已保存状态")
	public static final Integer MAIL_STATUS_SAVED = ++COMMON_BASE_13000;
	@SysI18nString(content = "已删除", comment = "已删除状态")
	public static final Integer MAIL_STATUS_DELETED = ++COMMON_BASE_13000;
	@SysI18nString(content = "收件人不存在", comment = "收件人不存在")
	public static final Integer MAIL_SEND_ERROR_RECID_NOEXIST = ++COMMON_BASE_13000;
	@SysI18nString(content = "收件人阵营不同,不能通讯", comment = "收件人阵营不同")
	public static final Integer MAIL_SEND_ERROR_ALLIANCE = ++COMMON_BASE_13000;
	@SysI18nString(content = "保存邮件箱已满", comment = "保存邮件箱已满")
	public static final Integer SAVE_MAIL_BOX_IS_FULL = ++COMMON_BASE_13000;
	@SysI18nString(content = "成功发送了一封邮件", comment = "发送成功")
	public static final Integer MAIL_SEND_SUCCESS_INFO = ++COMMON_BASE_13000;
	
	public static Integer COMMON_BASE_14000 = 10000 + 4000;
	/** 家族系统相关常量 14000 ~ 15000 */ 
	@SysI18nString(content = "家族名称", comment = "家族名称")
	public static final Integer FAMILY_NAME = ++COMMON_BASE_14000;
	@SysI18nString(content = "你已经有家族了", comment = "你已经有家族了")
	public static final Integer HAVE_FAMILY = ++COMMON_BASE_14000;
	@SysI18nString(content = "该家族名称已经存在", comment = "该家族名称已经存在")
	public static final Integer FAMILY_NAME_IS_EXISTS = ++COMMON_BASE_14000;
	@SysI18nString(content = "你的铜钱不够", comment = "你的铜钱不够")
	public static final Integer NOT_ENOUGH_MONEY = ++COMMON_BASE_14000;
	@SysI18nString(content = "你未加入家族，无法在家族频道中发言", comment = "你未加入家族，无法在家族频道中发言")
	public static final Integer NOT_HAVE_FAMILY = ++COMMON_BASE_14000;
	@SysI18nString(content = "该成员不存在此家族中", comment = "该成员不存在此家族中")
	public static final Integer NOT_IN_FAMILY = ++COMMON_BASE_14000;
	@SysI18nString(content = "这个家族不存在", comment = "这个家族不存在")
	public static final Integer FAMILY_IS_NOT_EXISTS = ++COMMON_BASE_14000;
	@SysI18nString(content = "你不能查看其他家族的日志", comment = "你不能查看其他家族的日志")
	public static final Integer CANNOT_GET_OTHER_FAMILY_LOGS = ++COMMON_BASE_14000;
	@SysI18nString(content = "家族日志不存在", comment = "家族日志不存在")
	public static final Integer FAMILY_LOGS_NOT_EXISTS = ++COMMON_BASE_14000;
	@SysI18nString(content = "申请的家族不存在", comment = "申请的家族不存在")
	public static final Integer APPLY_FAMILY_NOT_EXISTS = ++COMMON_BASE_14000;
	@SysI18nString(content = "你已经申请过这个家族了", comment = "你已经申请过这个家族了")
	public static final Integer HAVE_APPLY_FAMILY = ++COMMON_BASE_14000;
	@SysI18nString(content = "你不能查看其他家族的申请列表", comment = "你不能查看其他家族的申请列表")
	public static final Integer CANNOT_GET_OTHER_FAMILY_APPLY = ++COMMON_BASE_14000;
	@SysI18nString(content = "只有族长和长老才能查看申请玩家列表", comment = "只有族长和长老才能查看申请玩家列表")
	public static final Integer ONLY_FAMILY_LEADER_CAN_GET_APPLY = ++COMMON_BASE_14000;
	@SysI18nString(content = "不能修改其他家族的信息", comment = "不能修改其他家族的信息")
	public static final Integer CANNOT_UPDATE_OTHER_FAMILY = ++COMMON_BASE_14000;
	@SysI18nString(content = "只有族长才和长老才能进行此操作", comment = "只有族长才和长老才能进行此操作")
	public static final Integer ONLY_LEADER_CAN_UPDATE_FAMILY = ++COMMON_BASE_14000;
	@SysI18nString(content = "家族成员数量已达上限", comment = "家族成员数量已达上限")
	public static final Integer FAMILY_MEMBER_MAX = ++COMMON_BASE_14000;
	@SysI18nString(content = "该玩家已加入其他帮会", comment = "该玩家已加入其他帮会")
	public static final Integer FAMILY_MEMBER_JOIN_OTHER= ++COMMON_BASE_14000;
	@SysI18nString(content = "只有族长才能转让族长", comment = "只有族长才能转让族长")
	public static final Integer ONLY_FAMILY_LEADER_CAN_TRANSFER= ++COMMON_BASE_14000;
	@SysI18nString(content = "只能转让给长老", comment = "只能转让给长老")
	public static final Integer ONLY_TRANSFER_TO_ELDER= ++COMMON_BASE_14000;
	@SysI18nString(content = "已经退出家族", comment = "已经退出家族")
	public static final Integer HAVE_LEAVE_FAMILY= ++COMMON_BASE_14000;
	@SysI18nString(content = "请先转让族长职位再退出家族", comment = "请先转让族长职位再退出家族")
	public static final Integer TRANSFER_LEADER_WITH_LEAVE_FAMILY= ++COMMON_BASE_14000;
	@SysI18nString(content = "只有族长和长老才有权限开除成员", comment = "只有族长和长老才有权限开除成员")
	public static final Integer ONLY_LEADER_ELDER_CAN_REMOVE_OTHER= ++COMMON_BASE_14000;
	@SysI18nString(content = "长老只能开除普通成员", comment = "长老只能开除普通成员")
	public static final Integer ELDER_CAN_ONLY_REMOVE_FAMILY_MEMBER= ++COMMON_BASE_14000;
	@SysI18nString(content = "申请失败,帮会贡献不足", comment = "申请失败,帮会贡献不足")
	public static final Integer APPLY_FAMILY_LEADER_FAIL= ++COMMON_BASE_14000;
	@SysI18nString(content = "申请成功", comment = "申请成功")
	public static final Integer APPLY_FAMILY_LEADER_SUCCESS= ++COMMON_BASE_14000;
	@SysI18nString(content = "只有族长才能修改成员家族职位", comment = "只有族长才能修改成员家族职位")
	public static final Integer ONLY_FAMILY_LEADER_CAN_UPDATE_ROLE= ++COMMON_BASE_14000;
	@SysI18nString(content = "不能修改自己的家族职位", comment = "不能修改自己的家族职位")
	public static final Integer CANNOT_UPDATE_MYSELF_FAMILY_ROLE= ++COMMON_BASE_14000;
	@SysI18nString(content = "长老职位已满", comment = "长老职位已满")
	public static final Integer FAMILY_ELDER_NUM_MAX= ++COMMON_BASE_14000;
	@SysI18nString(content = "创建家族成功", comment = "创建家族成功")
	public static final Integer CREATE_FAMILY_SUCCESS= ++COMMON_BASE_14000;
	@SysI18nString(content = "申请家族成功", comment = "申请家族成功")
	public static final Integer APPLY_FAMILY_SUCCESS= ++COMMON_BASE_14000;
	@SysI18nString(content = "只有族长才能解散家族", comment = "只有族长才能解散家族")
	public static final Integer ONLY_LEADER_CAN_DELETE_FAMILY= ++COMMON_BASE_14000;
	@SysI18nString(content = "名字不合法，请重新创建", comment = "名字不合法，请重新创建")
	public static final Integer NAME_UNLAWFUL= ++COMMON_BASE_14000;
	@SysI18nString(content = "申请家族数量已达上限", comment = "申请家族数量已达上限")
	public static final Integer APPLY_FAMILY_MAX= ++COMMON_BASE_14000;
	@SysI18nString(content = "{0}退出了家族", comment = "{0}退出了家族")
	public static final Integer LEAVE_FAMILY = ++COMMON_BASE_14000;
	@SysI18nString(content = "{0}家族解散了", comment = "{0}家族解散了")
	public static final Integer FAMILY_DELETE = ++COMMON_BASE_14000;
	@SysI18nString(content = "你的家族解散了", comment = "你的家族解散了")
	public static final Integer YOUR_FAMILY_HAS_DELETE = ++COMMON_BASE_14000;
	@SysI18nString(content = "家族名字不能少于{0}个字", comment = "家族名字不能少于{0}个字")
	public static final Integer FAMILY_NAME_CANNOT_LESS_THAN = ++COMMON_BASE_14000;
	@SysI18nString(content = "家族名字不能超过{0}个字", comment = "家族名字不能超过{0}个字")
	public static final Integer FAMILY_NAME_CANNOT_MORE_THAN = ++COMMON_BASE_14000;
	@SysI18nString(content = "已是该家成族员", comment = "已是该家成族员")
	public static final Integer FAMILY_HAS_JOIN = ++COMMON_BASE_14000;
	@SysI18nString(content = "你已经成为普通会员", comment = "你已经成为普通会员")
	public static final Integer FAMILY_HAS_BEEN_COMMON = ++COMMON_BASE_14000;
	@SysI18nString(content = "{0}已经成为新族长", comment = "{0}已经成为新族长")
	public static final Integer FAMILY_HAS_BEEN_LEADER = ++COMMON_BASE_14000;
	
	/** 兵法系统相关常量15000~16000 */
	public static Integer COMMON_BASE_15000 = 10000 + 5000;
	@SysI18nString(content = "兵法", comment = "兵法")
	public static final Integer WARCRAFT = ++COMMON_BASE_15000;
	@SysI18nString(content = "兵法存放数量已满", comment = "兵法存放数量已满")
	public static final Integer WARCRAFT_TEMP_NOT_ENOUGH_SPACE = ++COMMON_BASE_15000;
	@SysI18nString(content = "兵法背包空间不足", comment = "兵法背包空间不足")
	public static final Integer WARCRAFT_BAG_NOT_ENOUGH_SPACE = ++COMMON_BASE_15000;
	@SysI18nString(content = "兵法装备位没有空余的位置", comment = "兵法装备位没有空余的位置")
	public static final Integer WARCRAFT_EQUIP_BAG_NOT_ENOUGH_SPACE = ++COMMON_BASE_15000;
	@SysI18nString(content = "你的等级不够,不能开启兵法系统", comment = "你的等级不够,不能开启兵法系统")
	public static final Integer WARCRAFT_OPEN_LEVEL_NOT_ACHIEVE= ++COMMON_BASE_15000;
	@SysI18nString(content = "已有该类型兵法", comment = "已有该类型兵法")
	public static final Integer WARCRAFT_EQUIP_EFFECT = ++COMMON_BASE_15000;
	@SysI18nString(content = "铜钱不足", comment = "铜钱不足")
	public static final Integer WARCRAFT_GRASP_COIN_NOT_ENOUGH= ++COMMON_BASE_15000;
	@SysI18nString(content = "不能拾取灰色兵法", comment = "不能拾取灰色兵法")
	public static final Integer GRAY_WARCRAFT_CAN_NOT_PICK= ++COMMON_BASE_15000;
	@SysI18nString(content = "灰色兵法不能合成", comment = "灰色兵法不能合成")
	public static final Integer GRAY_WARCRAFT_CAN_NOT_COMPOSE= ++COMMON_BASE_15000;
	@SysI18nString(content = "只有灰色兵法才能出售", comment = "只有灰色兵法才能出售")
	public static final Integer ONLY_GRAY_WARCRAFT_CAN_SELL= ++COMMON_BASE_15000;
	@SysI18nString(content = "只能移动兵法背包和装备位里的兵法", comment = "只能移动兵法背包和装备位里的兵法")
	public static final Integer TEMP_WARCRAFT_CAN_NOT_MOVE = ++COMMON_BASE_15000;
	@SysI18nString(content = "该兵法不存在", comment = "该兵法不存在")
	public static final Integer WARCRAFT_NOT_EXIST = ++COMMON_BASE_15000;
	@SysI18nString(content = "背包不存在", comment = "背包不存在")
	public static final Integer WARCRAFT_BAG_NOT_EXIST = ++COMMON_BASE_15000;
	@SysI18nString(content = "兵法等级已满不能吞噬", comment = "兵法等级已满不能吞噬")
	public static final Integer WARCRAFT_LEVEL_MAX = ++COMMON_BASE_15000;
	@SysI18nString(content = "该奖励已经领取过了", comment = "该奖励已经领取过了")
	public static final Integer WARCRAFT_PRIZE_HAS_GET = ++COMMON_BASE_15000;
	@SysI18nString(content = "该奖励不能领取", comment = "该奖励不能领取")
	public static final Integer WARCRAFT_PRIZE_CAN_NOT_GET = ++COMMON_BASE_15000;
	@SysI18nString(content = "获得铜钱", comment = "获得铜钱")
	public static final Integer WARCRAFT_SELL_COIN= ++COMMON_BASE_15000;
	@SysI18nString(content = "只有达到vip{0}及以上才能开启此功能" ,comment = "只有达到vip{0}及以上才能开启此功能")
	public static final Integer OPEN_NEED_VIP_LEVEL = ++COMMON_BASE_15000;
	@SysI18nString(content = "等级{0}或VIP{1}开启功此能",comment = "等级{0}或VIP{1}开启功此能")
	public static final Integer GRASP_AND_PICK_ALL_NEED_LEVEL = ++COMMON_BASE_15000;
	@SysI18nString(content = "经验兵法不能装备",comment = "经验兵法不能装备")
	public static final Integer EXP_WARCRAFT_CANNOT_EQUIP = ++COMMON_BASE_15000;
	@SysI18nString(content = "经验兵法不能相互吞噬",comment = "经验兵法不能相互吞噬")
	public static final Integer EXP_WARCRAFT_CANNOT_COMPOSE = ++COMMON_BASE_15000;
	
	/** 商城购买类型相关16000~17000 暂时没有使用*/
	public static Integer COMMON_BASE_16000 = 10000 + 6000;
	@SysI18nString(content = "VIP", comment = "VIP购买")
	public static final Integer VIP = ++COMMON_BASE_16000;
	@SysI18nString(content = "商城购买", comment = "商城购买")
	public static final Integer SHANGCHENG = ++COMMON_BASE_16000;
	@SysI18nString(content = "活动", comment = "活动")
	public static final Integer HUODONG = ++COMMON_BASE_16000;
	@SysI18nString(content = "GM命令", comment = "GM命令")
	public static final Integer GM = ++COMMON_BASE_16000;
	@SysI18nString(content = "系统赠送", comment = "系统赠送")
	public static final Integer XITONG= ++COMMON_BASE_16000;

	/** 成长相关17000~18000 */
	public static Integer COMMON_BASE_17000 = 10000 + 7000;
	@SysI18nString(content = "成长 + {0}", comment = "成长 + {0}")
	public static final Integer CHENGZHANG= ++COMMON_BASE_17000;
	
	/** 摇钱树相关18000~19000 */
	public static Integer COMMON_BASE_18000 = 10000 + 8000;
	@SysI18nString(content = "今日摇钱次数已用光，扩充VIP可获得更多的摇钱次数", comment = "今日摇钱次数已用光，扩充VIP可获得更多的摇钱次数")
	public static final Integer SHAKE_TIMES_NOT_ENOUGH= ++COMMON_BASE_18000;
	@SysI18nString(content = "果实错误", comment = "果实错误")
	public static final Integer FRUIT_ERROR= ++COMMON_BASE_18000;
	@SysI18nString(content = "玩家已经浇过水或系统未开放", comment = "玩家已经浇过水或系统未开放")
	public static final Integer WATER_OR_NOT_OPEN= ++COMMON_BASE_18000;
	@SysI18nString(content = "浇水次数已满", comment = "浇水次数已满")
	public static final Integer WATER_TIMES_ENOUGH= ++COMMON_BASE_18000;
	@SysI18nString(content = "领取成功", comment = "领取成功")
	public static final Integer SUCCESS_GET= ++COMMON_BASE_18000;
	@SysI18nString(content = "好心人，恭喜你获得了{0}铜钱", comment = "好心人，恭喜你获得了{0}铜钱")
	public static final Integer WATER_FREND_BATCH= ++COMMON_BASE_18000;
	@SysI18nString(content = "你太勤劳了，都还不需要浇水！", comment = "你太勤劳了，都还不需要浇水！")
	public static final Integer WATER_FREND_BATCH_NONE_FRIEND= ++COMMON_BASE_18000;
	@SysI18nString(content = "背包已满，请先整理背包", comment = "背包已满，请先整理背包")
	public static final Integer BAG_IS_FULL= ++COMMON_BASE_18000;
	@SysI18nString(content = "好友功能未开放", comment = "背包已满，请先整理背包")
	public static final Integer FRIEND_FUNC_NOT_OPEN= ++COMMON_BASE_18000;
	
	/** 体验vip相关19000~20000 */
	public static Integer COMMON_BASE_19000 = 10000 + 9000;
	@SysI18nString(content = "{0}使用了VIP体验卡，成为了体验VIP会员，可享受尊贵VIP服务。")
	public static final Integer ADD_VIP_BUFF = ++COMMON_BASE_19000;
}
