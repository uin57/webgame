package com.pwrd.war.gameserver.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.pwrd.war.common.HeartBeatAble;
import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.NonThreadSafe;
import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.GameColor;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.NoticeTimes;
import com.pwrd.war.common.constants.NoticeTypes;
import com.pwrd.war.common.constants.SysMsgShowTypes;
import com.pwrd.war.common.constants.SysPromptType;
import com.pwrd.war.common.constants.WallowConstants.WallowStatus;
import com.pwrd.war.common.model.human.HumanInfo;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageQueue;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.db.dao.HumanDao;
import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.buff.enums.BufferType;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.LangService;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.msg.GCSystemMessage;
import com.pwrd.war.gameserver.common.msg.GCSystemNotice;
import com.pwrd.war.gameserver.common.msg.GCSystemPrompt;
import com.pwrd.war.gameserver.common.msg.GCWindowMessage;
import com.pwrd.war.gameserver.common.unit.GameUnit;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.msg.GCSceneUpdateRole;
import com.pwrd.war.gameserver.human.xiulian.XiulianInfo;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.player.msg.GCNotifyException;
import com.pwrd.war.gameserver.player.persistance.PlayerDataNotifier;
import com.pwrd.war.gameserver.player.persistance.PlayerDataUpdater;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 游戏中的玩家，维护玩家的会话和玩家所有角色的引用
 * 
 */
public class Player implements HeartBeatAble, NonThreadSafe, GameUnit,InitializeRequired {
	/** 玩家上线时由玩家所属GameServer分配的 */
	private int id;
	/** 玩家当前使用的角色 */
	private Human human; 
	/** 玩家所属场景线程Id */
	private long threadId;
	/** 玩家与GameServer的会话 */
	private GameClientSession session;	
	/** 玩家的所有角色列表,登陆选择角色使用 */
	private  RoleInfo  role ;
	/** 玩家的passport * */
	private String passportId;
	/** 玩家的passprot名称 */
	private String passportName;
	/** 玩家的权限 */
	private int permission;
	/** 玩家今日累计在线时长，分钟数 */
	private int todayOnlineTime;
	/** 上次玩家登录时间 */
	private long lastLoginTime;
	/** 上次玩家登出时间 */
	private long lastLogoutTime;
	/** 玩家的消息队列 */
	private MessageQueue msgQueue;
	
	/** 最后一次发送聊天消息的时间 */
	private transient Map<Integer, Long> lastChatTime;
	/** 最后一次执行好友搜索的时间 */
	private transient long lastSearchTime;
	
	/** 上次设置玩家位置的时间 **/
	private transient long lastSetPosTime;
	
	/** 玩家的状态 */
	private PlayerStateManager stateManager;
	/** 玩家数据更新调度器 */
	private PlayerDataUpdater dataUpdater;
	/** 数据变更通知器*/
	private PlayerDataNotifier dataNotifier;
	/** 玩家ip地址 */
	private String clientIp;
	/** 退出原因 */
	public PlayerExitReason exitReason;
	/** 处理的消息总数,为避免同步，在主线程中修改 */
	private static volatile long playerMessageCount = 0;
	/** 处理的出错消息个数 */
	private static volatile int playerErrorMessageCount = 0;
	
	
	/** 累计在线时长 秒 */
	private int accOnlineTime;
	/** 防沉迷标识 */
	private int wallowFlag;
	/** 防沉迷状态 */
	private WallowStatus wallowState = WallowStatus.NORMAL;	
	private long lastAccTime;//上次计算防沉迷的时间
	

	/** 目标场景id */
	private String targetSceneId;

	/** 玩家状态保存器 */
	private final StatefulHolder statefulHolder = new StatefulHolder();
	
	/** 移动的目标点X **/
	private int toX;
	/** 移动的目标点Y **/
	private int toY;
	
	public Player(GameClientSession session) {
		this.session = session;		
		session.setPlayer(this);
		this.stateManager = new PlayerStateManager(this);
	}
	
	@Override
	public void init()
	{
		this.msgQueue = new MessageQueue();
		dataUpdater = new PlayerDataUpdater();
		dataNotifier = new PlayerDataNotifier(Globals.getEventService());
		lastChatTime = new HashMap<Integer, Long>();
	}

	@Override
	public boolean checkThread() {
		return false;// FIXME
	}

	public void setSession(GameClientSession session) {
		this.session = session;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public Human getHuman() {
		return human;
	}
	
	/**
	 * 放置玩家需要处理的消息
	 * @param msg
	 */
	public void putMessage(IMessage msg) {
		this.msgQueue.put(msg);
		// 记录玩家处理消息个数
		playerMessageCount++;
	}
	
	/**
	 * 处理服务器收到的来自玩家的消息，在玩家当前所属的场景线程中调用
	 */
	public void processMessage() {
		for (int i = 0; i < 12; i++) {
			if (msgQueue.isEmpty()) {
				break;
			}
			IMessage msg = msgQueue.get();
			Assert.notNull(msg);
			long begin = System.nanoTime();
			
			try {
				if (!stateManager.canProcess(msg)) {
					Loggers.playerLogger.warn(LogUtils.buildLogInfoStr(human
							.getUUID(), "msg type " + msg.getType()
							+ " can't be processed in curState:"
							+ stateManager.getState()));
					return;
				}
				else
				{		
					msg.execute();
					
				}
			} catch (Throwable e) {
				playerErrorMessageCount++;
				Loggers.playerLogger.error("Process input message error!", e);
				sendMessage(new GCNotifyException(
						CommonLangConstants_10000.DC_HANDLE_MSG_EXCEPTION, 
						Globals.getLangService().read(CommonLangConstants_10000.DC_HANDLE_MSG_EXCEPTION)));
				this.exitReason = PlayerExitReason.SERVER_ERROR;
				this.disconnect();
		
			} finally {
				// 特例，统计时间跨度
				long time = (System.nanoTime() - begin) / (1000 * 1000);
				if (time > 1) {
					int type = msg.getType();
					Loggers.clientLogger.info("Message:" + msg.getTypeName()
							+ " Type:" + type + " Time:" + time + "ms"
							+ " Total:" + playerMessageCount
							+ " Error:"	+ playerErrorMessageCount);
				}
			}
		}
	}


	/**
	 * 将消息发送给Player
	 * 
	 * @param msg
	 */
	public void sendMessage(IMessage msg) {
		Assert.notNull(msg);
		if (!stateManager.needSend(msg.getType())) {
			Loggers.msgLogger.debug("msg:" + msg
					+ " don't need to be send to player in curState:"
					+ stateManager.getState());
			return;
		}
		if (session != null) {
			session.write(msg);			
		}
	}

	/**
	 * 
	 * 发送系统消息(聊天框)
	 * 
	 * @param content
	 */
	public void sendSystemMessage(String content) {
		if (content != null) {
			GCSystemMessage msg = new GCSystemMessage(content,
					GameColor.SYSMSG_SYS.getRgb(), SysMsgShowTypes.generic);
			sendMessage(msg);
		}
	}
	public GCSystemMessage getSystemMessage(String content){
		GCSystemMessage msg = new GCSystemMessage(content,
				GameColor.SYSMSG_SYS.getRgb(), SysMsgShowTypes.generic);
		return msg;
	}
	public GCSystemMessage getSystemMessage(Integer key, Object... params) {
		String content = Globals.getLangService().read(  key, params);
		return this.getSystemMessage(content);
	}
	/**
	 * 返回带人物消息的多语言参数
	 */
	public JSONObject getSystemPlayerMsgPart(Player player){
		JSONObject ob = new JSONObject();
		ob.put("name", player.getHuman().getName());
		ob.put("uuid", player.getRoleUUID());
		return ob;
	}
	/**
	 * 返回带物品消息的多语言参数
	 */
	public JSONObject getSystemItemMsgPart(ItemTemplate tmp){
		JSONObject ob = new JSONObject();
		ob.put("sn", tmp.getItemSn());
		return ob ;
	}
	 
	/**
	 * 发送公告信息
	 * 
	 * @param content
	 */
	public void sendNoticeMessage(String content, short speed) {
		if (content != null) {
			GCSystemNotice msg = new GCSystemNotice(content,
					GameColor.SYSMSG_NOTICE.getRgb(), speed,NoticeTimes.NOTICE_TIME);
			sendMessage(msg);
		}
	}

	public void sendSystemMessage( Integer key) {
		String content = Globals.getLangService().read(  key);
		sendSystemMessage(content);
	}

	public void sendSystemMessage(   Integer key, Object... params) {
		String content = Globals.getLangService().read(  key, params);
		sendSystemMessage(content);
	}
 

	/**
	 * 发送确认对话框消息, 但不显示对话框标题
	 * 
	 * @param tag 标识
	 * @param langId 多语言 Id
	 */
	public void sendOptionDialogMessage(String tag, Integer langId) {
		this.sendOptionDialogMessage(tag, "", langId, "");
	}

	/**
	 * 发送确认对话框消息, 但不显示对话框标题
	 * 
	 * @param tag 标识
	 * @param langId 多语言 Id
	 * @param params 多语言格式化参数
	 */
	public void sendOptionDialogMessage(String tag, Integer langId, Object ... params) {
		this.sendOptionDialogMessage(tag, "", langId, params);
	}

	/**
	 * 发送确认对话框消息
	 * 
	 * @param tag 标识
	 * @param title 对话框标题
	 * @param langId 多语言 Id
	 */
	public void sendOptionDialogMessage(String tag, String title, Integer langId) {
		this.sendOptionDialogMessage(tag, title, langId, "");
	}

	/**
	 * 发送确认对话框消息, 
	 * 客户端接收到该消息后会显示一个含有 &lt;确定&gt; 和 &lt;取消&gt; 按钮的提示框
	 * 
	 * @param tag 标识
	 * @param title 对话框标题
	 * @param langId 多语言 Id
	 * @param params 多语言格式化参数
	 */
	public void sendOptionDialogMessage(String tag, String title, Integer langId, Object ... params) {
		// 获取语言服务
		LangService langServ = Globals.getLangService();

//		// 提示内容
//		String optionContent = langServ.readSysLang(langId, params);
//		// 确定按钮文本
//		String okText = langServ.readSysLang(LangConstants.OK);
//		// 取消按钮文本
//		String cancelText = langServ.readSysLang(LangConstants.CANCEL);
//
//		GCShowOptionDlg optionDlg = new GCShowOptionDlg();
//		
//		optionDlg.setTitle(title);
//		optionDlg.setContent(optionContent);
//		optionDlg.setOkText(okText);
//		optionDlg.setCancelText(cancelText);
//		optionDlg.setTag(tag);

//		this.sendMessage(optionDlg);
	}

	/**
	 * 提示语句和语句的参数全部采用langId作参数发送系统消息
	 * 
	 * @param key
	 *            提示语句langId
	 * @param paramKeys
	 *            参数的langId
	 */
	public void sendSysMsgWithI18nParams( Integer key, Integer... paramKeys) {
		sendSystemMessage(createLangString(key, paramKeys));
	}

	private String createLangString( Integer key, Integer... paramKeys) {
		String[] params = new String[paramKeys.length];
		int i = 0;
		for (Integer paramKey : paramKeys) {
			params[i++] = Globals.getLangService().read( paramKey);
		}
		return Globals.getLangService().read( key, (Object[]) params);
	}

	/**
	 * 发送系统提示信息(聊天框)
	 * 
	 * @param content
	 */
	public void sendSystemWithChatMessage(String content) {
		if (content != null) {
			GCSystemMessage msg = new GCSystemMessage(content,
					GameColor.SYSMSG_IMPORT.getRgb(), SysMsgShowTypes.important);
			sendMessage(msg);
		}
	}
 

	public void sendSystemWithChatMessage(Integer key, Object... params) {
		String content = Globals.getLangService().read( key, params);
		sendSystemWithChatMessage(content);
	}

	/**
	 * 发送弹框提示信息
	 * 
	 * @param content
	 */
	public void sendBoxMessage(String content) {
		if (content != null) {
			GCSystemMessage msg = new GCSystemMessage(content,
					GameColor.SYSMSG_IMPORT.getRgb(), SysMsgShowTypes.box);
			sendMessage(msg);
		}
	}


	public void sendBoxMessage( Integer key, Object... params) {
		String content = Globals.getLangService().read( key, params);
		sendBoxMessage(content);
	}

	/**
	 * 提示语句和语句的参数全部采用langId作参数发送系统消息
	 * 
	 * @param key
	 *            提示语句langId
	 * @param paramKeys
	 *            参数的langId
	 */
	public void sendBoxMsgWithI18nParams(Integer key, Integer... paramKeys) {
		sendBoxMessage(createLangString( key, paramKeys));
	}

	/**
	 * 发送错误消息,聊天框
	 * @param content
	 */
	public void sendErrorMessage(String content) {
		if (human != null) {
			if (human.getMessageControl().reserverErrorMessage(content)) {
				// 如果记录出错信息则返回
				return;
			}
		}
		GCSystemMessage msg = new GCSystemMessage(content,
				GameColor.SYSMSG_FAIL.getRgb(), SysMsgShowTypes.error);
		sendMessage(msg);
	}

 
	public void sendErrorMessage(Integer key, Object... params) {
		String content = Globals.getLangService().read( key, params);
		sendErrorMessage(content);
	}

	/**
	 * 提示语句和语句的参数全部采用langId作参数发送出错消息
	 * 
	 * @param key
	 *            提示语句langId
	 * @param paramKeys
	 *            参数的langId
	 */
	public void sendErrorMsgWithI18nParams(Integer key, Integer... paramKeys) {
		sendErrorMessage(createLangString(key, paramKeys));
	}

	/**
	 * 发送错误提示消息
	 * 
	 * @param content
	 */
	public void sendErrorPromptMessage(String content){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.error,content,GameColor.SYSMSG_FAIL.getRgb());
		sendMessage(msg);
	}
	
	/**
	 * 发送错误提示消息
	 * 
	 * @param key
	 * @param params
	 */
	public void sendErrorPromptMessage(Integer key, Object... params){
		String content = Globals.getLangService().read( key, params);
		sendErrorPromptMessage(content);
	}
	
	/**
	 * 提示语句和语句的参数全部采用langId作参数发送错误提示消息
	 * 
	 * @param key
	 * @param paramKeys
	 */
	public void sendErrorPromptMessageWithI18nParams(Integer key, Integer... paramKeys){
		sendErrorPromptMessage(createLangString(key, paramKeys));
	}
	
	/**
	 * 发送重要提示(个人重要消息)
	 * 
	 * @param content
	 */
	public void sendImportPromptMessage(String content){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.prompt,content,GameColor.SYSMSG_COMMON.getRgb());
		sendMessage(msg);
	}
	
	/**
	 * 发送重要提示(个人重要消息)
	 * 
	 * @param key
	 * @param params
	 */
	public void sendImportPromptMessage(Integer key, Object... params){
		String content = Globals.getLangService().read( key, params);
		sendImportPromptMessage(content);
	}
	
	/**
	 * 提示语句和语句的参数全部采用langId作参数发送系统提示消息
	 * 
	 * @param key
	 * @param paramKeys
	 */
	public void sendImportPromptMessageWithI18nParams(Integer key,Integer... paramKeys){
		sendImportPromptMessage(createLangString(key, paramKeys));
	}
	
	/**
	 * 发送提示按钮
	 * 
	 * @param content
	 */
	public void sendButtonMessage(String content){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.button,content,GameColor.SYSMSG_COMMON.getRgb());
		sendMessage(msg);
	}
	
	/**
	 * 发送提示按钮
	 * 
	 * @param key
	 * @param params
	 */
	public void sendButtonMessage(Integer key, Object... params){
		String content = Globals.getLangService().read( key, params);
		sendButtonMessage(content);
	}
	
	/**
	 * 提示语句和语句的参数全部采用langId作参数发送系统提示按钮
	 * 
	 * @param key
	 * @param paramKeys
	 */
	public void sendButtonMessageWithI18nParams(Integer key,Integer... paramKeys){
		sendButtonMessage(createLangString(key, paramKeys));
	}
	
	/**
	 * 发送头顶消息
	 * 
	 * @param content
	 */
	public void sendHeadMessage(String content,int color){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.head,content,color);
		sendMessage(msg);
	}
	
	/**
	 * 发送头顶消息
	 * 
	 * @param key
	 * @param params
	 */
	public void sendHeadMessage(Integer key, int color,Object... params){
		String content = Globals.getLangService().read( key, params);
		sendHeadMessage(content,color);
	}
	
	/**
	 * 提示语句和语句的参数全部采用langId作参数发送头顶消息
	 * 
	 * @param key
	 * @param paramKeys
	 */
	public void sendHeadMessageWithI18nParams(Integer key,int color,Integer... paramKeys){
		sendHeadMessage(createLangString(key, paramKeys),color);
	}
	
	/**
	 * 发送特殊消息
	 * 
	 * @param content
	 */
	public void sendSpecialMessage(String content){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.special,content,GameColor.SYSMSG_COMMON.getRgb());
		sendMessage(msg);
	}
	
	/**
	 * 发送特殊消息
	 * 
	 * @param key
	 * @param params
	 */
	public void sendSpecialMessage(Integer key, Object... params){
		String content = Globals.getLangService().read( key, params);
		sendSpecialMessage(content);
	}
	
	/**
	 * 提示语句和语句的参数全部采用langId作参数发送特殊消息
	 * 
	 * @param key
	 * @param paramKeys
	 */
	public void sendSpecialMessageWithI18nParams(Integer key,Integer... paramKeys){
		sendSpecialMessage(createLangString(key, paramKeys));
	}
	
	/**
	 * 发送正确提示消息
	 * @param content
	 */
	public void sendRightMessage(String content){
		GCSystemPrompt msg = new GCSystemPrompt(SysPromptType.right,content,GameColor.SYSMSG_COMMON.getRgb());
		sendMessage(msg);
	}
	
	/**
	 * 发送正确消息
	 * 
	 * @param key
	 * @param params
	 */
	public void sendRightMessage(Integer key, Object... params){
		String content = Globals.getLangService().read( key, params);
		sendRightMessage(content);
	}
	
	/**
	 * 提示语句和语句的参数全部采用langId作参数发送正确消息
	 * @param key
	 * @param paramKeys
	 */
	public void sendRightMessageWithI18nParams(Integer key,Integer... paramKeys){
		sendRightMessage(createLangString(key, paramKeys));
	}
	
	/**
	 * 发送特殊窗口提示消息
	 * @param content
	 * @param windowId
	 */
	public void sendWindowMessage(String content,int windowId){
		GCWindowMessage msg = new GCWindowMessage();
		msg.setWindowId(windowId);
		msg.setContent(content);
		sendMessage(msg);
	}
	
	/**
	 * 发送特殊窗口提示消息
	 * @param content
	 * @param windowId
	 */
	public void sendWindowMessage(int windowId,Integer key, Object... params){
		String content = Globals.getLangService().read( key, params);
		sendWindowMessage(content,windowId);
	}
	
	/**
	 * 发送特殊窗口提示消息
	 * @param content
	 * @param windowId
	 */
	public void sendWindowMessageWithI18nParams(int windowId,Integer key, Integer... params){
		sendWindowMessage(createLangString(key,params),windowId);
	}

	
	/**
	 * 根据指定位置频道发送多个系统提示消息
	 * 
	 * @param content
	 * @param paramKeys
	 */
	public void sendMultiSystemMessage(String content,Short... paramKeys){
		if(paramKeys == null || paramKeys.length == 0){
			return;
		}
		for(short noticeType : paramKeys){
			//发送公告消息
			if(noticeType == NoticeTypes.system){
				this.sendNoticeMessage(content, (short)Globals.getGameConstants()
						.getDefaultNoticeSpeed());
				continue;
			}
			//发送公告消息,聊天框
			if(noticeType == NoticeTypes.sysChat){
				this.sendSystemWithChatMessage(content);
				continue;
			}
			//发送个人(重要)提示消息
			if(noticeType == NoticeTypes.important){
				this.sendImportPromptMessage(content);
				continue;
			}
			//发送普通提示消息(聊天框)
			if(noticeType == NoticeTypes.common){
				this.sendSystemMessage(content);
				continue;
			}
			//发送错误提示消息
			if(noticeType == NoticeTypes.error){
				this.sendErrorPromptMessage(content);
				continue;
			}
			//发送提示按钮消息
			if(noticeType == NoticeTypes.button){
				this.sendButtonMessage(content);
				continue;
			}
			//发送头顶提示消息
			if(noticeType == NoticeTypes.head){
				this.sendHeadMessage(content,GameColor.SYSMSG_COMMON.getRgb());
				continue;
			}
			//发送特殊提示消息
			if(noticeType == NoticeTypes.special){
				this.sendSpecialMessage(content);
				continue;
			}
			//发送特殊提示消息
			if(noticeType == NoticeTypes.right){
				this.sendRightMessage(content);
				continue;
			}
			//发送特殊窗口提示消息
			if(noticeType == NoticeTypes.window){
				this.sendWindowMessage(content,NoticeTypes.window);
				continue;
			}
		}
	}
	
	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public long getThreadId() {
		return threadId;
	}

	 
	
	public RoleInfo getRole() {
		return role;
	}

	public void setRole(RoleInfo role) {
		this.role = role;
	}

	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}

	public String getPassportId() {
		return passportId;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param state
	 * @return 当状态设置成功返回真
	 */
	public boolean setState(PlayerState state) {
		if (state == PlayerState.logouting
				&& this.getState() == PlayerState.logouting) {
			return true;
		}
		return this.stateManager.setState(state);
	}

	/**
	 * @return
	 */
	public PlayerState getState() {
		return this.stateManager.getState();
	}

	/**
	 * 判断玩家当前是否在场景中
	 * 
	 * @return
	 */
	public boolean isInScene() {
		if(human != null)
			return human.getScene() != null;
		else
			return false;
	}

	
	/**
	 * @return
	 */
	public GameClientSession getSession() {
		return session;
	}
	
	/**
	 * 判断玩家是否在线
	 * 
	 * @return
	 */
	public boolean isOnline() {
		return session.isConnected();
	}

	/**
	 * @return the lastChatTime
	 */
	public long getLastChatTime(int scope) {
		Long time = this.lastChatTime.get(scope);

		if (time == null) {
			return 0;
		}
		return time;
	}

	/**
	 * @param lastChatTime
	 *            the lastChatTime to set
	 */
	public void setLastChatTime(int scope, long lastChatTime) {
		this.lastChatTime.put(scope, lastChatTime);
	}

	public long getLastSearchTime() {
		return lastSearchTime;
	}

	public void setLastSearchTime(long lastSearchTime) {
		this.lastSearchTime = lastSearchTime;
	}
	
	/**
	 * @return
	 */
	public String getSceneId() {
		return human.getSceneId();
	}

	public void setTargetSceneId(String targetSceneId) {
		this.targetSceneId = targetSceneId;
	}

	public String getTargetSceneId() {
		return targetSceneId;
	}
	

	/**
	 * 获得玩家当前角色UUID
	 * 
	 * @return
	 */
	public String getRoleUUID() {
		if (human == null) {
//			return -1l;
			return "";
		} else {
			return human.getUUID();
		}
	}

	public PlayerStateManager getStateManager() {
		return stateManager;
	}

	/**
	 * 在处理玩家消息出现异常时调用
	 */
	public void onException() {
		if (session.closeOnException()) {
			// 此处会自动触发GameServerIoHandler#sessionClosed
			session.close();
		}
	}
	
	/**
	 * 关闭用户连接,解除和session的绑定
	 * 
	 */
	public void disconnect() {
		if (this.session != null && this.session.isConnected()) {
			// 此处会自动触发GameServerIoHandler#sessionClosed
			this.session.close();
		}
	}

	public void setPassportName(String passportName) {
		this.passportName = passportName;
	}

	public String getPassportName() {
		return passportName;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public int getPermission() {
		return permission;
	}

	public void setTodayOnlineTime(int todayOnlineTime) {
		this.todayOnlineTime = todayOnlineTime;
	}

	public int getTodayOnlineTime() {
		return todayOnlineTime;
	}

	public void setLastLoginTime(Long lastLogoutTime) {
		this.lastLoginTime = lastLogoutTime;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}
	
	public void setLastLogoutTime(long lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

	public long getLastLogoutTime() {
		return lastLogoutTime;
	}
	
	public int getAccOnlineTime() {
		return accOnlineTime;
	}

	public void setAccOnlineTime(int accOnlineTime) {
		this.accOnlineTime = accOnlineTime;
	}

	public int getWallowFlag() {
		return wallowFlag;
	}

	public void setWallowFlag(int wallowFlag) {
		this.wallowFlag = wallowFlag;
	}
	
	public WallowStatus getWallowState() {
		return wallowState;
	}

	public void setWallowState(WallowStatus wallowState) {
		this.wallowState = wallowState;
	}

	/**
	 * 更新数据
	 */
	private void updateData() {
		try {
			dataUpdater.update();
		} catch (Exception e) {
			if (Loggers.updateLogger.isErrorEnabled()) {
				Loggers.updateLogger.error(ErrorsUtil.error(
						CommonErrorLogInfo.INVALID_STATE,
						"#GS.Player.updateData", ""), e);
			}
		}
		try {
			dataNotifier.onChange();
		} catch (Exception e) {
			if (Loggers.updateLogger.isErrorEnabled()) {
				Loggers.updateLogger.error(ErrorsUtil.error(
						CommonErrorLogInfo.INVALID_STATE, "#GS.Player.updateData", ""),
						e);
			}
		}
	}

	/**
	 * @return
	 */
	public PlayerDataUpdater getDataUpdater() {
		return dataUpdater;
	}

	/**
	 * @return
	 */
	public PlayerDataNotifier getDataNotifier() {
		return dataNotifier;
	}

	@Override
	public void heartBeat() {
		stateManager.onHeartBeat();
		human.heartBeat();
		this.updateData();
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getClientIp() {
		return clientIp;
	}

	/**
	 * 获取状态保存接口
	 * 
	 * @return
	 */
	public StatefulHolder getStatefulHolder() {
		return this.statefulHolder;
	}
	
	
	
	public long getLastSetPosTime() {
		return lastSetPosTime;
	}

	public void setLastSetPosTime(long lastSetPosTime) {
		this.lastSetPosTime = lastSetPosTime;
	}

	public int getToX() {
		return toX;
	}

	public void setToX(int toX) {
		this.toX = toX;
	}

	public int getToY() {
		return toY;
	}

	public void setToY(int toY) {
		this.toY = toY;
	}
	
	/**
	 * 取得变化的状态信息，告诉客户端，一般用来告诉场景里的人
	 * @author xf
	 */
	public void sendInfoChangeMessageToScene(){
		if(this.isInScene()){
			GCSceneUpdateRole msg = new GCSceneUpdateRole();
			msg.setRoleInfo(this.toHumanInfo());
			this.human.getScene().getPlayerManager().sendGCMessage(msg, /*Arrays.asList(this.getId())*/ null);
		}
		
	}
	
	public HumanInfo toHumanInfo(){
		HumanInfo info = new HumanInfo();
		info.setName(this.human.getName());
		info.setCamp(this.human.getCamp().getCode());
		info.setLevel(this.human.getLevel());
		info.setRoleUUID(this.human.getUUID());
		info.setSceneId(this.human.getSceneId());
		info.setLineNo(this.human.getScene().getLine());
		info.setSex(this.human.getSex().getCode());
		info.setVocation(this.human.getVocationType().getCode());
		info.setX(this.human.getX());
		info.setY(this.human.getY());
		if(this.getToX() == 0){
			info.setToX(this.human.getX());
		}else{
			info.setToX(this.getToX());
		}
		if(this.getToY() == 0){
			info.setToY(this.human.getY());
		}else{
			info.setToY(this.getToY());
		} 
		info.setHumanStatus(this.getHuman().getGamingState());
		Item weapon = this.human.getInventory().getEquipBag().getWeapon();
		if(weapon != null){
			info.setWeaponSn(weapon.getTemplate().getItemSn());
		}else{
			info.setWeaponSn("");
		}
		List<Buffer> buffer = this.human.getBufferByType(BufferType.change);
		if(buffer != null && buffer.size() > 0){
			info.setBodySn(buffer.get(0).getExt());
		}else{
			info.setBodySn("");
		}
		//修炼信息
		XiulianInfo xinfo = Globals.getHumanService().getHumanXiulianService().getByUUID(this.getRoleUUID());
		info.setXiulianAllCollectTimes(0);
		info.setXiulianCollectTimes(0);
		info.setXiulianSymbolId(0);		
		if(xinfo != null){
			if(xinfo.getXiulianSymbolTemplate() != null){
				info.setXiulianAllCollectTimes(xinfo.getXiulianSymbolTemplate().getCollectTimes());
				info.setXiulianCollectTimes(xinfo.getHasCollectTimes());
				info.setXiulianSymbolId(xinfo.getXiulianSymbolTemplate().getSymbolId());
			}
		}
		return info;
	}

	public long getLastAccTime() {
		return lastAccTime;
	}

	public void setLastAccTime(long lastAccTime) {
		this.lastAccTime = lastAccTime;
	}

	/**
	 * 取得服务器玩家最大等级
	 */
	public static int getPlayerMaxLevel(){
		HumanDao dao = Globals.getDaoService().getHumanDao();
		return dao.getMaxLevel();
	}

	public boolean isCanChat() {
		return human.getPropertyManager().getPropertyNormal().isCanChat();
	}

	public void setCanChat(boolean canChat) {
		human.getPropertyManager().getPropertyNormal().setCanChat(canChat);
	}
}
