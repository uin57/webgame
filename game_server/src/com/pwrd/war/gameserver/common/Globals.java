package com.pwrd.war.gameserver.common;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import com.pwrd.war.common.constants.GameConstants;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.common.model.GameServerStatus;
import com.pwrd.war.common.model.template.DirtyWordsTemplate;
import com.pwrd.war.common.model.template.DirtyWordsTemplateVO;
import com.pwrd.war.common.model.template.NameDirtyWordsTemplate;
import com.pwrd.war.common.service.DirtFilterService;
import com.pwrd.war.core.async.AsyncService;
import com.pwrd.war.core.config.ConfigUtil;
import com.pwrd.war.core.config.ServerConfig;
import com.pwrd.war.core.msg.DataType;
import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.core.schedule.ScheduleService;
import com.pwrd.war.core.schedule.ScheduleServiceImpl;
import com.pwrd.war.core.server.MessageDispatcher;
import com.pwrd.war.core.server.QueueMessageProcessor;
import com.pwrd.war.core.server.ShutdownHooker;
import com.pwrd.war.core.session.OnlineSessionService;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.core.time.SystemTimeService;
import com.pwrd.war.core.time.TimeService;
import com.pwrd.war.core.util.ServerVersion;
import com.pwrd.war.core.uuid.UUIDService;
import com.pwrd.war.core.uuid.UUIDServiceImpl;
import com.pwrd.war.core.uuid.UUIDType;
import com.pwrd.war.db.model.msg.EntityTypeFactory;
import com.pwrd.war.gameserver.GameServer;
import com.pwrd.war.gameserver.activity.ActivityService;
import com.pwrd.war.gameserver.arena.ArenaService;
import com.pwrd.war.gameserver.buff.BufferService;
import com.pwrd.war.gameserver.charge.ChargeLogicalProcessor;
import com.pwrd.war.gameserver.charge.ChargePrizeService;
import com.pwrd.war.gameserver.chat.ChatService;
import com.pwrd.war.gameserver.chat.WordFilterService;
import com.pwrd.war.gameserver.chat.impl.WordFilterServiceImpl;
import com.pwrd.war.gameserver.common.cache.GameCacheService;
import com.pwrd.war.gameserver.common.config.GameServerConfig;
import com.pwrd.war.gameserver.common.db.AsyncServiceImpl;
import com.pwrd.war.gameserver.common.db.GameDaoService;
import com.pwrd.war.gameserver.common.event.AddPetEvent;
import com.pwrd.war.gameserver.common.event.CheckFunctionEvent;
import com.pwrd.war.gameserver.common.event.DayTaskEvent;
import com.pwrd.war.gameserver.common.event.EventService;
import com.pwrd.war.gameserver.common.event.FormChangeEvent;
import com.pwrd.war.gameserver.common.event.HPReduceEvent;
import com.pwrd.war.gameserver.common.event.PlayerChargeDiamondEvent;
import com.pwrd.war.gameserver.common.event.PlayerOffLineEvent;
import com.pwrd.war.gameserver.common.event.QuestKillEvent;
import com.pwrd.war.gameserver.common.event.RoleLevelUpEvent;
import com.pwrd.war.gameserver.common.event.RolePropsChangedEvent;
import com.pwrd.war.gameserver.common.event.StoryShowEvent;
import com.pwrd.war.gameserver.common.event.VisibleMonsterFindPlayerEvent;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatThread;
import com.pwrd.war.gameserver.common.i18n.LangService;
import com.pwrd.war.gameserver.common.i18n.LangServiceImpl;
import com.pwrd.war.gameserver.common.listener.AddPetEventListener;
import com.pwrd.war.gameserver.common.listener.CheckFunctionEventListener;
import com.pwrd.war.gameserver.common.listener.DayTaskListener;
import com.pwrd.war.gameserver.common.listener.FormChangeListener;
import com.pwrd.war.gameserver.common.listener.HPRedeceListener;
import com.pwrd.war.gameserver.common.listener.PlayerChargeDiamondListener;
import com.pwrd.war.gameserver.common.listener.PlayerOffLineEventListener;
import com.pwrd.war.gameserver.common.listener.QuestKillEventListener;
import com.pwrd.war.gameserver.common.listener.RoleLevelUpListener;
import com.pwrd.war.gameserver.common.listener.RolePropsChangedListener;
import com.pwrd.war.gameserver.common.listener.StoryShowEventListener;
import com.pwrd.war.gameserver.common.listener.VisibleMonsterFindPlayerListener;
import com.pwrd.war.gameserver.common.log.LogService;
import com.pwrd.war.gameserver.common.log.PlayerLogService;
import com.pwrd.war.gameserver.common.msg.FriendMessage;
import com.pwrd.war.gameserver.common.msg.GuildMessage;
import com.pwrd.war.gameserver.common.process.ProcessService;
import com.pwrd.war.gameserver.common.service.SystemNoticeService;
import com.pwrd.war.gameserver.common.thread.CrossThreadOperationService;
import com.pwrd.war.gameserver.common.timer.MilliHeartbeatTimer;
import com.pwrd.war.gameserver.common.world.World;
import com.pwrd.war.gameserver.common.world.WorldService;
import com.pwrd.war.gameserver.dayTask.DayTaskService;
import com.pwrd.war.gameserver.economy.EconomyRegulateService;
import com.pwrd.war.gameserver.family.service.FamilyService;
import com.pwrd.war.gameserver.form.FormService;
import com.pwrd.war.gameserver.giftBag.service.GiftBagService;
import com.pwrd.war.gameserver.human.HumanAssistantService;
import com.pwrd.war.gameserver.human.HumanService;
import com.pwrd.war.gameserver.item.ItemService;
import com.pwrd.war.gameserver.item.xinghun.XinghunParaService;
import com.pwrd.war.gameserver.mail.MailService;
import com.pwrd.war.gameserver.mall.MallService;
import com.pwrd.war.gameserver.map.MapBattleBgService;
import com.pwrd.war.gameserver.monster.MonsterAIService;
import com.pwrd.war.gameserver.monster.MonsterService;
import com.pwrd.war.gameserver.pet.PetService;
import com.pwrd.war.gameserver.player.LoginLogicalProcessor;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.auth.DBUserAuthImpl;
import com.pwrd.war.gameserver.player.auth.LocalUserAuthImpl;
import com.pwrd.war.gameserver.player.auth.UserAuth;
import com.pwrd.war.gameserver.prize.PrizeService;
import com.pwrd.war.gameserver.promptButton.service.PromptButtonService;
import com.pwrd.war.gameserver.quest.QuestService;
import com.pwrd.war.gameserver.rep.RepService;
import com.pwrd.war.gameserver.rep.against.AgainstRepService;
import com.pwrd.war.gameserver.robbery.RobberyService;
import com.pwrd.war.gameserver.role.properties.amend.AmendService;
import com.pwrd.war.gameserver.scene.LoadSceneService;
import com.pwrd.war.gameserver.scene.LoadSceneServiceImpl;
import com.pwrd.war.gameserver.scene.SceneService;
import com.pwrd.war.gameserver.secretShop.SecretShopService;
import com.pwrd.war.gameserver.skill.SkillService;
import com.pwrd.war.gameserver.startup.GameExecutorService;
import com.pwrd.war.gameserver.startup.GameMessageProcessor;
import com.pwrd.war.gameserver.startup.MinaGameClientSession;
import com.pwrd.war.gameserver.status.ServerStatusService;
import com.pwrd.war.gameserver.story.StoryService;
import com.pwrd.war.gameserver.team.TeamService;
import com.pwrd.war.gameserver.timeevent.TimeEventService;
import com.pwrd.war.gameserver.timeevent.TimeWaveChangeService;
import com.pwrd.war.gameserver.tower.TowerService;
import com.pwrd.war.gameserver.tower.auto.AutoTowerService;
import com.pwrd.war.gameserver.transferVocation.TransferVocationService;
import com.pwrd.war.gameserver.tree.TreeService;
import com.pwrd.war.gameserver.vip.VipService;
import com.pwrd.war.gameserver.vitality.VitalityService;
import com.pwrd.war.gameserver.wallow.WallowLogicalProcessor;
import com.pwrd.war.gameserver.wallow.WallowService;
import com.pwrd.war.gameserver.warcraft.service.WarcraftService;

/**
 * 各种全局的业务管理器、公共服务实例的持有者，负责各种管理器的初始化和实例的获取
 * 
 */
public class Globals {
	/** 服务器配置信息 */
	private static GameServerConfig config;
	/** 主消息处理器，运行在主线程中，处理玩家登陆退出以及服务器内部消息 */
	private static MessageDispatcher<GameMessageProcessor> messageProcessor;
	/** 系统线程池服务 */
	private static GameExecutorService executorService;
	/** 全局常量数据 */
	private static GameConstants gameConstants;
	/** 模板数据管理器 */
	private static TemplateService templateService;
	/** 会话管理器 */
	private static OnlineSessionService<MinaGameClientSession> sessionService;
	/** 在线玩家管理器 */
	private static OnlinePlayerService onlinePlayerService;
	/** 世界管理服务 */
	private static WorldService worldService;	
	/** 场景管理器 */
	private static SceneService sceneService;
	/** 负责系统中时间操作的服务类 */
	private static TimeService timeService;
	/** 进程停止Hooker */
	private static ShutdownHooker shutdownHooker;
	/** 聊天不良信息过滤服务 */
	private static WordFilterService wordFilterService;
	/** 定时任务管理器 */
	private static ScheduleService scheduleService;
	/** 多语言管理器 */
	private static LangService langService;
	/** 通过logserver记录日志的服务 */
	private static LogService logService;
	/** 平台接口服务 */
//	private static LocalService localService;
	/** 聊天管理器 */
	private static ChatService chatService;
	/** 数据访问对象管理器 */
	private static GameDaoService daoService;
	/** 缓存访问对象管理器 */
	private static GameCacheService cacheService;
	/** 异步操作管理器 */
	private static AsyncService asyncService;
	/** UUID服务 */
	private static UUIDService uuidService;
	/** 全局事件管理器 */
	private static EventService eventService;
	/** 脏话过滤器 */
	private static DirtFilterService dirtFilterService;
	/** 武将服务 */
	private static PetService petService;

	/** 角色辅助管理器 */
	private static HumanAssistantService humanAssistantService;
	/** 属性修正服务 */
	private static AmendService amendService;
	/** H2Cache的服务 */
//	private static HumanService humanService;
	/** 跨线程操作服务 */
	private static CrossThreadOperationService crossThreadOperationService;	
	/** 任务查找表 */
	private static QuestService questService;	
	/** 道具模板生成实例管理器 */
	private static ItemService itemService;	
	/** 邮件服务 */
	private static MailService mailService;	
	/** 定时事件服务 */
	private static TimeEventService timeEventService;	
	/** 时间波动服务 */
	private static TimeWaveChangeService timeWaveChangeService;	
	/** 进程服务 */
	private static ProcessService processService;
//	/** 性能采集管理 */
//	private static ProbeService probeService;
	/** 充值服务 */
	private static ChargePrizeService chargePrizeService;	
	/** 防沉迷服务 */
	private static WallowService wallowService;
	/** 系统公告 */
	private static SystemNoticeService noticeService;	
	/** 奖励服务 */
	private static PrizeService prizeService;
	/** 玩家日志服务 */
	private static PlayerLogService playerLogService;
	/** 收益调节服务 */
	private static EconomyRegulateService economyRegulateService;	
	/** 游戏服务器状态控制 */
	private static GameServerStatus serverStatus;	
	/** 服务器状态汇报服务 */
	private static ServerStatusService serverStatusService;	
	/** 登陆逻辑处理器 */
	private static LoginLogicalProcessor loginLogicalProcessor;	
	/** 充值业务逻辑 */
	private static ChargeLogicalProcessor chargeLogicalProcessor;


	/** 加载地图信息服务 **/
	private static LoadSceneService loadSceneService;
	
	private static SkillService skillService;
	
	private static FormService formService;
	
	private static BufferService bufferService;
	

	private static MonsterAIService monsterAIService;
	

	/** 商店信息**/
	private static MallService mallService;
	
	/** 组队服务 **/
	private static TeamService teamService;
	
	/** 副本服务 **/
	private static RepService repService;

	/** 执行心跳的县城 **/
	private static HeartbeatThread heartbeatThread;
	
	/** 体力系统*/
	private static VitalityService vitService;
	
	/** 转职系统*/
	private static TransferVocationService transferVocationService;
	
	private static HumanService humanService;
	//怪物
	private static MonsterService monsterService;
	//扫荡
	private static AgainstRepService againstRepService;
	
	/** 剧情系统*/
	private static StoryService storyService;
	
	private static SecretShopService secretShopService;
	
	/** 夺宝系统 **/
	private static RobberyService robberyService;
	
	/** 活动系统 **/
	private static ActivityService activityService;
	/** VIP系统 **/
	private static VipService vipService;
	
	/** 每日任务 **/
	private static DayTaskService dayTaskService;
	
	/** 竞技场系统 */
	private static ArenaService arenaService;
	
	/** 将星云路系统 */
	private static TowerService towerService;
	
	/** 将星云路自动挑战系统 */
	private static AutoTowerService autoTowerService;
	
	/** 家族系统*/
	private static FamilyService familyService;
	
	private static XinghunParaService xinghunParaService;
	
	/** 礼包系统 */
	private static GiftBagService giftBagService;
	
	/** 兵法系统 */
	private static WarcraftService warcraftService;
	
	/** 摇钱树系统 */
	private static TreeService treeService;
	
	/** 战斗地图背景系统 */
	private static MapBattleBgService mapBattleBgService;
	
	/** 提示按钮系统 */
	private static PromptButtonService promptButtonService;
	
	/**
	 * 服务器启动时调用，初始化所有管理器实例
	 * 
	 * @param cfg
	 * @throws Exception
	 * @see GameServer
	 */
	public static void init(GameServerConfig cfg) throws Exception {
		config = cfg;
		messageProcessor = buildMessageProcessor();
		amendService = new AmendService();
		timeService = new SystemTimeService(true);
		shutdownHooker = new ShutdownHooker();
		daoService = new GameDaoService(cfg);
		cacheService = new GameCacheService(cfg);
		DataType.registerEntityClass(EntityTypeFactory.ITEMINFO);
		
		// 初始化各种管理器实例
		MilliHeartbeatTimer.setTimeService(timeService);
//		langService = LangServiceImpl.buildLangService(cfg);
		langService = new LangServiceImpl();

		templateService = new TemplateService(cfg.getScriptDirFullPath(), cfg.getIsDebug());
		templateService.init(ConfigUtil.getConfigURL("templates.xml"));
		sessionService = new OnlineSessionService<MinaGameClientSession>();
		onlinePlayerService = new OnlinePlayerService(cfg.getMaxOnlineUsers());
		
		worldService = new WorldService(World.getWorld());
		
		itemService = new ItemService(templateService);
		itemService.init();
		
//		mailService = new MailService(templateService,langService);
//		mailService.init();
		
//		localService = new LocalService(cfg);
		
		economyRegulateService = new EconomyRegulateService();
		
//		humanService = new HumanService(cfg);
		
		humanAssistantService = new HumanAssistantService(templateService,itemService);
		humanAssistantService.init();

		// 翻译多语言标记
		translateLangs(cfg);

		// 初始化各种基础服务
		executorService = new GameExecutorService();
		wordFilterService = new WordFilterServiceImpl();

		logService = new LogService(config.getLogConfig().getLogServerIp(),
				config.getLogConfig().getLogServerPort(), Integer
						.parseInt(config.getRegionId()), Integer
						.parseInt(config.getServerId()));
		scheduleService = new ScheduleServiceImpl(messageProcessor, timeService);
		
		timeEventService = new TimeEventService(templateService, timeService, scheduleService, onlinePlayerService);
		timeEventService.init();
		
		timeWaveChangeService = new TimeWaveChangeService(templateService, timeEventService);
		timeWaveChangeService.init();
		
		processService = new ProcessService(timeService,executorService, daoService);
		processService.init();

		asyncService = new AsyncServiceImpl(10, 5, messageProcessor);
		crossThreadOperationService = new CrossThreadOperationService(asyncService, onlinePlayerService);
		eventService = buildEventService();
		gameConstants = initGameConstants();
		
		monsterService = new MonsterService(templateService);
		
		loadSceneService = new LoadSceneServiceImpl(cfg.getMapDirFullPath());
		sceneService = new SceneService(loadSceneService, onlinePlayerService,daoService, config, monsterService);
		sceneService.init();

		String[] dirty = initDirtWords(DirtyWordsTemplate.class);
		String[] nameDirty = initDirtWords(NameDirtyWordsTemplate.class);
		dirtFilterService = new DirtFilterService(  dirty, mergeArray(dirty, nameDirty));
		
		serverStatus = buildGameServerStatus(config);
		serverStatusService = new ServerStatusService();
		
		// 初始化登录逻辑处理器
		UserAuth userAuth = buildUserAuth();
		loginLogicalProcessor = new LoginLogicalProcessor(userAuth);

		chargeLogicalProcessor = new ChargeLogicalProcessor();
		
		chatService = new ChatService(templateService);
		chatService.init();
		
		chargePrizeService = new ChargePrizeService(templateService);
		chargePrizeService.init();
		
		prizeService = new PrizeService();
		
		questService = new QuestService();
		questService.init();
		
		storyService = new StoryService();
		storyService.init();
		
		wallowService = new WallowService(scheduleService,
				new WallowLogicalProcessor(),  onlinePlayerService);
		
		noticeService = new SystemNoticeService(scheduleService);
		noticeService.init();
		
		petService = new PetService();		

		playerLogService = new PlayerLogService(sceneService, templateService);
		
//		probeService = new ProbeService();
		

		
		bufferService =new BufferService();
		skillService=new SkillService();
		formService =new FormService();
		

		monsterAIService =new MonsterAIService();

		mallService = new MallService();
		teamService = new TeamService();

		repService = new RepService(loadSceneService, onlinePlayerService,daoService, config, monsterService);;

		heartbeatThread = new HeartbeatThread(config.getHeartbeatThreadNum());
		
		vitService = new VitalityService();
		
		transferVocationService=new TransferVocationService();
		
		humanService = new HumanService(templateService, scheduleService);
		againstRepService = new AgainstRepService(scheduleService);
		
		secretShopService = new SecretShopService();
		robberyService = new RobberyService(templateService, daoService.getRobberyDAO());
		vipService = new VipService();
		
		dayTaskService = new DayTaskService();
		arenaService = new ArenaService(daoService.getAreanDao());
		arenaService.init();
		activityService = new ActivityService();
		towerService = new TowerService();
		autoTowerService = new AutoTowerService(scheduleService);
		
		familyService = new FamilyService();
		familyService.init();
		xinghunParaService = new XinghunParaService();
		
		giftBagService = new GiftBagService();
		giftBagService.init();
		
		warcraftService = new WarcraftService();
		warcraftService.init();
		
		treeService = new TreeService();
		
		mapBattleBgService = new MapBattleBgService();
		promptButtonService = new PromptButtonService();
		
		// ***********在此之前初始化************
		Globals.checkAfterInit();
	}
	
	private static UserAuth buildUserAuth() {
		return config.getAuthType() == SharedConstants.AUTH_TYPE_INTERFACE ? new LocalUserAuthImpl(
				daoService.getUserInfoDao() )
				: new DBUserAuthImpl(daoService.getUserInfoDao());
	}

	public static MonsterAIService getMonsterAIService() {
		return monsterAIService;
	}

	public static void setMonsterAIService(MonsterAIService monsterAIService) {
		Globals.monsterAIService = monsterAIService;
	}

	public static void start() {
		//心跳开启
		heartbeatThread.start();
		
		timeEventService.start();
		
		// 玩家缓存服务启动
//		humanService.start();
			
		// 战报服务启动（建表）
//		Globals.getBattleReportService().start();
		
		
		wallowService.start();

		
	}
	
	public static void stop(){
		heartbeatThread.shutdown();
	}
	

	/**
	 * 初始化dirty words
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T extends TemplateObject> String[] initDirtWords(Class<T> clazz) {
		Collection<DirtyWordsTemplateVO> words = (Collection<DirtyWordsTemplateVO>)templateService.getAll(clazz)
				.values();
		int len = words.size();
		String[] dirty = new String[len];
		int i = 0;
		for (DirtyWordsTemplateVO tmpl : words) {
			dirty[i] = tmpl.getDirtyWords();
			i++;
		}
		return dirty;
	}

	/**
	 * 合并两个数组
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	private static String[] mergeArray(String[] arr1, String[] arr2) {
		int len = arr1.length + arr2.length;
		String[] arr = new String[len];
		int i = 0;
		for (String str : arr1) {
			arr[i] = str;
			i++;
		}

		for (String str : arr2) {
			arr[i] = str;
			i++;
		}

		return arr;
	}


	/**
	 * 做初始化后检查，确保仅是检查操作，游戏不依赖与该操作
	 */
	private static void checkAfterInit() {
		
		
	}



	private static void translateLangs(GameServerConfig cfg) throws Exception {
		Loggers.gameLogger.info("Translating name reference marks...");
//		TODO 无需翻译
//		Properties props = new Properties();
//
//		props.put(MultiLangTranslator.RESOURCE_DIR, cfg.getBaseResourceDir());
//		props.put(MultiLangTranslator.I18N_DIR, cfg.getI18nDir());
//		props.put(MultiLangTranslator.USE_LANGUAGE, cfg.getLanguage());
//		
//		// 多语言翻译器
//		MultiLangTranslator mlTranslator = new MultiLangTranslator(templateService, props);
//		mlTranslator.startTranslate();
//
//		// 初始化名字查找表，将templateService的名字，NPC、怪物的名字因为涉及到其坐标不再此处做，在各自的manager初始化对象时做
//		langService.initNameLookupTable(templateService);
//
//		MarkTranslator translator = new MarkTranslatorImpl();
//		translator.bindLookupTable(langService.getNameLookupTable());
//		translator.translateAllTemplate(templateService);
//
//		// excel多语言全部读完了
//		langService.onAllExcelLangRead();

		Loggers.gameLogger.info("Translate complete.");
	}


	/**
	 * 构建UUID管理器
	 * 
	 * @param serverConfig
	 * @return
	 */
	private static UUIDService buildUUIDService(DBService dbServices,
			ServerConfig serverConfig) {
		UUIDType[] _types = new UUIDType[] { UUIDType.HUMAN, UUIDType.PET, UUIDType.SCENE  };
		return new UUIDServiceImpl(_types, dbServices, Integer
				.parseInt(serverConfig.getRegionId()), Integer
				.parseInt(serverConfig.getServerGroupId()), serverConfig
				.getServerIndex());
	}
	
	private static GameServerStatus buildGameServerStatus(GameServerConfig serverConfig)
	{
		GameServerStatus serverStatus = new GameServerStatus();
		
		serverStatus.setServerIndex(serverConfig.getServerIndex());
		serverStatus.setServerId(serverConfig.getServerId());
		serverStatus.setServerName(serverConfig.getServerName());
		serverStatus.setIp(serverConfig.getServerHost());
		serverStatus.setPort(serverConfig.getPorts());		
		serverStatus.setVersion(ServerVersion.getServerVersion());
		
		return serverStatus;
	}
	

	
	/**
	 * 构建消息处理器,分拆不同类型的消息到不同的消息处理器
	 * 
	 * @return
	 */
	private static MessageDispatcher<GameMessageProcessor> buildMessageProcessor() {
		// 主消息处理器，处理登录、聊天等没有IO操作的请求
		GameMessageProcessor mainMessageProcessor = new GameMessageProcessor();
		// 消息分发器，将收到的消息转发到不同的消息处理器
		MessageDispatcher<GameMessageProcessor> msgDispatcher = new MessageDispatcher<GameMessageProcessor>(mainMessageProcessor);
		// 好友消息处理器
		QueueMessageProcessor friendMessageProcessor = new QueueMessageProcessor();
		// 公会消息处理器
		QueueMessageProcessor guildMessageProcessor = new QueueMessageProcessor();
		msgDispatcher.registerMessageProcessor(FriendMessage.class,	friendMessageProcessor);
		msgDispatcher.registerMessageProcessor(GuildMessage.class, guildMessageProcessor);
		return msgDispatcher;
	}

	/**
	 * 初始化全局的游戏参数
	 * @throws IOException 
	 */
	private static GameConstants initGameConstants() throws IOException {
		File file = new File(config.getScriptDirFullPath() + File.separator
				+ "constants.js");
		URL url = null;
		try {
			url = file.toURI().toURL();
			return ConfigUtil.buildConfig(GameConstants.class, url.openStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return gameConstants;
	}

	/**
	 * @return
	 */
	public static GameServerConfig getServerConfig() {
		return config;
	}
//	
//	/**
//	 * 获得同步local服务
//	 * 
//	 * @return
//	 */
//	public static SynLocalService getSynLocalService() {
//		return localService.getSynLocalService();
//	}
//	
//	/**
//	 * 获得异步local服务
//	 * 
//	 * @return
//	 */
//	public static AsyncLocalService getAsyncLocalService() {
//		return localService.getAsyncLocalService();
//	}

	/**
	 * @return
	 */
	public static GameMessageProcessor getMessageProcessor() {
		return messageProcessor.getMainProcessor();
	}

	/**
	 * @return
	 */
	public static GameExecutorService getExecutorService() {
		return executorService;
	}
	
	
	public static ShutdownHooker getShutdownHooker() {
		return shutdownHooker;
	}

	/**
	 * 获得场景管理器实例
	 */
	public static SceneService getSceneService() {
		return sceneService;
	}

	/**
	 * 获得会话服务器实例
	 */
	public static OnlineSessionService<MinaGameClientSession> getSessionService() {
		return sessionService;
	}

	/**
	 * 获得在线玩家管理器实例
	 */
	public static OnlinePlayerService getOnlinePlayerService() {
		return onlinePlayerService;
	}
	
	/**
	 * 获得World服务
	 * @return
	 */
	public static WorldService getWorldService() {
		return worldService;
	}

	/**
	 * 获取World全局心跳和消息处理器
	 * @return
	 */
	public static World getWorld(){
		return World.getWorld();
	}

	public static GameConstants getGameConstants() {
		return gameConstants;
	}

	public static TemplateService getTemplateService() {
		return templateService;
	}	
	
	public static ItemService getItemService() {
		return itemService;
	}
	
	public static SystemNoticeService getNoticeService(){
		return noticeService;
	}
	
	public static AmendService getAmendService() {
		return amendService;
	}
	
	public static PetService getPetService() {
		return petService;
	}
	
	public static ServerStatusService getServerStatusService() {
		return serverStatusService;
	}

	public static TimeService getTimeService() {
		return timeService;
	}

	public static LogService getLogService() {
		return logService;
	}
	
	public static PlayerLogService getPlayerLogService(){
		return playerLogService;
	}
	
	public static LoginLogicalProcessor getLoginLogicalProcessor() {
		return loginLogicalProcessor;
	}
	
	public static ChargeLogicalProcessor getChargeLogicalProcessor() {
		return chargeLogicalProcessor;
	}

	public static LangService getLangService() {
		return langService;
	}
	
	public static QuestService getQuestService() {
		return questService;
	}
	
	public static GameServerStatus getServerStatus() {
		return serverStatus;
	}

	public static ScheduleService getScheduleService() {
		return scheduleService;
	}
	
	public static TimeEventService getTimeEventService() {
		return timeEventService;
	}

	public static TimeWaveChangeService getTimeWaveChangeService() {
		return timeWaveChangeService;
	}
	
	public static ProcessService getProcessService() {
		return processService;
	}

	public static GameDaoService getDaoService() {
		return daoService;
	}
	
	public static GameCacheService getCacheService() {
		return cacheService;
	}

	public static AsyncService getAsyncService() {
		return asyncService;
	}

	
	public static MailService getMailService() {
		return mailService;
	}
	
//	public static ProbeService getProbeService() {
//		return probeService;
//	}
	
	public static ChargePrizeService getChargePrizeService() {
		return chargePrizeService;
	}
	
	public static WallowService getWallowService() {
		return wallowService;
	}
	
	public static PrizeService getPrizeService() {
		return prizeService;
	}
	
	public static VitalityService getVitService(){
		return vitService;
	}

	/**
	 * 获取数据更新服务
	 * 
	 * @return
	 */
	public static final EventService getEventService() {
		return eventService;
	}

	/**
	 * 构建事件管理器
	 * 
	 * @return
	 */
	private static EventService buildEventService() {
		EventService _em = new EventService();
		_em.addListener(PlayerChargeDiamondEvent.class, new PlayerChargeDiamondListener());
		_em.addListener(RoleLevelUpEvent.class, new RoleLevelUpListener());
		_em.addListener(AddPetEvent.class, new AddPetEventListener());
		_em.addListener(HPReduceEvent.class, new HPRedeceListener());
		_em.addListener(FormChangeEvent.class, new FormChangeListener());
		_em.addListener(RolePropsChangedEvent.class, new RolePropsChangedListener());
		_em.addListener(QuestKillEvent.class, new QuestKillEventListener());
		_em.addListener(StoryShowEvent.class, new StoryShowEventListener());
		_em.addListener(VisibleMonsterFindPlayerEvent.class, new VisibleMonsterFindPlayerListener());
		_em.addListener(PlayerOffLineEvent.class, new PlayerOffLineEventListener());
		_em.addListener(DayTaskEvent.class, new DayTaskListener());
		_em.addListener(CheckFunctionEvent.class, new CheckFunctionEventListener());
		return _em;
	}

	public static DirtFilterService getDirtFilterService() {
		return dirtFilterService;
	}

	/**
	 * 获取字符过滤服务
	 * 
	 * @return 
	 */
	public static WordFilterService getWordFilterService() {
		return wordFilterService;
	}

	/**
	 * 获取聊天服务
	 * 
	 * @return
	 */
	public static ChatService getChatService() {
		return chatService;
	}

//	/**
//	 * 得到Human辅助的服务
//	 * 
//	 * @return
//	 */
//	public static HumanService getHumanService() {
//		return humanService;
//	}

	/**
	 * 得到Human辅助的服务
	 * 
	 * @return
	 */
	public static HumanAssistantService getHumanAssistantService() {
		return humanAssistantService;
	}
	
	/**
	 * 获取经济调节服务
	 * @return
	 */
	public static EconomyRegulateService getEconomyRegulateService() {
		return economyRegulateService;
	}

	/**
	 * 获取跨线程操作服务
	 * @return
	 */
	public static CrossThreadOperationService getCrossThreadOperationService() {
		return crossThreadOperationService;
	}
	
	/**
	 * 设置Globals类的templateService
	 * 单元测试专用，因为单元测试中需要加载模板，而许多模板类的check方法是通过Globals.getTemplateService获取
	 * TemplateService的
	 * @param templateService
	 */
	public static void setTemplateServiceForUnitTest(TemplateService templateService) {
		Globals.templateService = templateService;
	}

	public static SkillService getSkillService() {
		return skillService;
	}

	public static void setSkillService(SkillService skillService) {
		Globals.skillService = skillService;
	}

	public static FormService getFormService() {
		return formService;
	}

	public static void setFormService(FormService formService) {
		Globals.formService = formService;
	}

	public static BufferService getBufferService() {
		return bufferService;
	}

	public static void setBufferService(BufferService bufferService) {
		Globals.bufferService = bufferService;
	}

	/**
	 * 获取商城信息服务
	 * 
	 * @return
	 */
	public static MallService getMallService() {
		return mallService;
	}
	/**
	 * 取得组队服务
	 * @author xf
	 */
	public static TeamService getTeamService(){
		return teamService;
	}
	
	/**
	 * 获取副本信息服务
	 * 
	 * @return
	 */
	public static RepService getRepService() {
		return repService;
	}

	public static TransferVocationService getTransferVocationService() {
		return transferVocationService;
	}

	public static void setTransferVocationService(
			TransferVocationService transferVocationService) {
		Globals.transferVocationService = transferVocationService;
	}

	/**
	 * 
	 */
	public static HumanService getHumanService() {
		return humanService;
	}

	public static MonsterService getMonsterService() {
		return monsterService;
	}

	public static AgainstRepService getAgainstRepService() {
		return againstRepService;
	}
	
	public static StoryService getStoryService() {
		return storyService;
	}

	public static SecretShopService getSecretShopService() {
		return secretShopService;
	}

	public static RobberyService getRobberyService() {
		return robberyService;
	}

	public static VipService getVipService() {
		return vipService;
	}
	
	public static DayTaskService getDayTaskService() {
		return dayTaskService;
	}
	
	public static ArenaService getArenaService() {
		return arenaService;
	}

	public static ActivityService getActivityService() {
		return activityService;
	}

	public static void setActivityService(ActivityService activityService) {
		Globals.activityService = activityService;
	}
	
	public static FamilyService getFamilyService() {
		return familyService;
	}
	

	public static TowerService getTowerService() {
		return towerService;
	}

	public static AutoTowerService getAutoTowerService() {
		return autoTowerService;
	}
	
	public static GiftBagService getGiftBagService() {
		return giftBagService;
	}

	public static XinghunParaService getXinghunParaService() {
		return xinghunParaService;
	}

	public static WarcraftService getWarcraftService(){
		return warcraftService;
	}

	public static TreeService getTreeService() {
		return treeService;
	}
	
	public static MapBattleBgService getMapBattleBgService() {
		return mapBattleBgService;
	}
	
	public static PromptButtonService getPromptButtonService(){
		return promptButtonService;
	}
}
