package com.pwrd.war.gameserver.common.db;

import java.net.URL;
import java.util.HashMap;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.event.EventListenerAdapter;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.orm.DBAccessEvent;
import com.pwrd.war.core.orm.DBAccessEventListener;
import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.core.orm.DBServiceBuilder;
import com.pwrd.war.db.dao.ArenaAchievementDao;
import com.pwrd.war.db.dao.ArenaDao;
import com.pwrd.war.db.dao.ArenaHistoryDao;
import com.pwrd.war.db.dao.BaseDao;
import com.pwrd.war.db.dao.BufferDao;
import com.pwrd.war.db.dao.PromptButtonDao;
import com.pwrd.war.db.dao.ChargeInfoDao;
import com.pwrd.war.db.dao.CooldownDao;
import com.pwrd.war.db.dao.DailyQuestDao;
import com.pwrd.war.db.dao.DayTaskInfoDao;
import com.pwrd.war.db.dao.DbVersionDao;
import com.pwrd.war.db.dao.DoingQuestDao;
import com.pwrd.war.db.dao.FamilyApplyMemberDao;
import com.pwrd.war.db.dao.FamilyDao;
import com.pwrd.war.db.dao.FamilyLogDao;
import com.pwrd.war.db.dao.FamilyMemberDao;
import com.pwrd.war.db.dao.FinishedQuestDao;
import com.pwrd.war.db.dao.FinishedStoryDao;
import com.pwrd.war.db.dao.FormDao;
import com.pwrd.war.db.dao.FriendDao;
import com.pwrd.war.db.dao.GiftBagDao;
import com.pwrd.war.db.dao.HeroWarActivityDao;
import com.pwrd.war.db.dao.HumanDao;
import com.pwrd.war.db.dao.HumanRepInfoDao;
import com.pwrd.war.db.dao.HumanRobberyDAO;
import com.pwrd.war.db.dao.HumanXiulianDAO;
import com.pwrd.war.db.dao.ItemDao;
import com.pwrd.war.db.dao.MailDao;
import com.pwrd.war.db.dao.PetDao;
import com.pwrd.war.db.dao.PlatformPrizeInfoDao;
import com.pwrd.war.db.dao.PlayerGuideDAO;
import com.pwrd.war.db.dao.ProbInfoDAO;
import com.pwrd.war.db.dao.ProcessEventDao;
import com.pwrd.war.db.dao.RepAgainstDAO;
import com.pwrd.war.db.dao.RobberyDAO;
import com.pwrd.war.db.dao.SceneDao;
import com.pwrd.war.db.dao.SecretShopInfoDao;
import com.pwrd.war.db.dao.ServerInfoDAO;
import com.pwrd.war.db.dao.SkillGroupDao;
import com.pwrd.war.db.dao.TimeNoticeDAO;
import com.pwrd.war.db.dao.TowerInfoDao;
import com.pwrd.war.db.dao.TreeInfoDao;
import com.pwrd.war.db.dao.TreeWaterDao;
import com.pwrd.war.db.dao.UserArchiveEntityDAO;
import com.pwrd.war.db.dao.UserInfoDao;
import com.pwrd.war.db.dao.UserPrizeDao;
import com.pwrd.war.db.dao.VocationSkillDao;
import com.pwrd.war.db.dao.WarcraftDao;
import com.pwrd.war.db.dao.WarcraftInfoDao;
import com.pwrd.war.db.dao.battle.BattleEndDao;
import com.pwrd.war.db.dao.battle.BattleInitDao;
import com.pwrd.war.db.dao.battle.BattleReportDao;
import com.pwrd.war.gameserver.common.config.GameServerConfig;
import com.pwrd.war.gameserver.human.Human;

/**
 * GameServer用到的数据库访问对象管理器
 * 
 */
public class GameDaoService {

	/** 辅助初始化类 */
	private DaoHelper daoHelper;

	public GameDaoService(GameServerConfig config) {

		daoHelper = new DaoHelper(config);
	}

	/**
	 * 获取用户信息访问Dao
	 * 
	 * @return
	 */
	public UserInfoDao getUserInfoDao() {
		return daoHelper.userInfoDao;
	}

	/**
	 * 获取CharacterInfoDao
	 * 
	 * @return
	 */
	public HumanDao getHumanDao() {
		return daoHelper.humanDao;
	}

	/**
	 * 获取数据库版本号Dao
	 * 
	 * @return
	 */
	public DbVersionDao getDbVersionDao() {
		return daoHelper.dbVersionDao;
	}

	/**
	 * 获取PetDao
	 * 
	 * @return
	 */
	public PetDao getPetDao() {
		return daoHelper.petDao;
	}

	/**
	 * 获取ItemDao
	 * 
	 * @return
	 */
	public ItemDao getItemDao() {
		return daoHelper.itemDao;
	}

	/**
	 * 获取MailDao
	 * 
	 * @return
	 */
	public MailDao getMailDao() {
		return daoHelper.mailDao;
	}

	/**
	 * 获取场景 DAO
	 * 
	 * @return
	 */
	public SceneDao getSceneDao() {
		return daoHelper.sceneDao;
	}

	/**
	 * 获取进行中的任务数据
	 * 
	 * @return
	 */
	public DoingQuestDao getDoingQuestDao() {
		return daoHelper.doingQuestDao;
	}

	/**
	 * 获取已完成的任务数据
	 * 
	 * @return
	 */
	public FinishedQuestDao getFinishedQuestDao() {
		return daoHelper.finishedQuestDao;
	}

	/**
	 * 获取日常任务数据
	 * 
	 * @return
	 */
	public DailyQuestDao getDailyQuestDao() {
		return daoHelper.dailyQuestDao;
	}

	/**
	 * 得到平台奖励DAO
	 * 
	 * @return
	 */
	public PlatformPrizeInfoDao getPlatformPrizeDao() {
		return daoHelper.platformPrizeDao;
	}

	/**
	 * 获取UserPrizeDao
	 * 
	 * @return
	 */
	public UserPrizeDao getUserPrizeDao() {
		return daoHelper.userPrizeDao;
	}

	/**
	 * 获取ChargeInfoDao
	 * 
	 * @return
	 */
	public ChargeInfoDao getChargeInfoDao() {
		return daoHelper.chargeInfoDao;
	}

	/**
	 * 获取ProcessEventDao
	 * 
	 * @return
	 */
	public ProcessEventDao getProcessEventDao() {
		return daoHelper.processDao;
	}

	public DBService getDBService() {
		if (daoHelper == null) {
			return null;
		}
		return daoHelper.dbService;
	}

	public VocationSkillDao getVacationSkillDao() {
		return daoHelper.vacationSkillDao;
	}


	public SkillGroupDao getSkillGroupDao() {
		return daoHelper.skillGroupDao;
	}

	public FormDao getFormDao() {
		return daoHelper.formDao;
	}

	public CooldownDao getCooldownDao() {
		return daoHelper.cooldownDao;
	}
	
	public BufferDao getBufferDao() {
		return daoHelper.bufferDao;
	}
	
	public ServerInfoDAO getServerInfoDAO() {
		return daoHelper.serverInfoDAO;
	}
	public UserArchiveEntityDAO getUserArchiveEntityDAO() {
		return daoHelper.userArchiveEntityDAO;
	}
	/**
	 * 根据PersistanceObject
	 * 
	 * @param poClass
	 * @return
	 */
	public BaseDao<?> getDaoByPOClass(Class<?> poClass) {
		return daoHelper.clazzDaoMap.get(poClass);
	}

	/**
	 * 副本信息
	 * @return
	 */
	public HumanRepInfoDao getHumanRepInfoDao(){
		return daoHelper.humanRepInfoDao;
	}
	
	
	public BattleInitDao getBattleInitDao(){
		return daoHelper.battleInitDao;
	}
	
	public BattleReportDao getBattleReportDao(){
		return daoHelper.battleReportDao;
	}
	
	public BattleEndDao getBattleEndDao(){
		return daoHelper.battleEndDao;
	}
	
	public FinishedStoryDao getFinishedStoryDao() {
		return daoHelper.finishedStoryDao;
	}
	public FriendDao getFriendDao() {
		return daoHelper.friendDao;
	}
	public SecretShopInfoDao getSecretShopInfoDao() {
		return daoHelper.secretShopInfo;
	}
	public RobberyDAO getRobberyDAO() {
		return daoHelper.robberyDAO;
	}
	
	public ArenaDao getAreanDao() {
		return daoHelper.arenaDAO;
	}
	public ArenaHistoryDao getArenaHistoryDao() {
		return daoHelper.arenaHistoryDAO;
	}
	public ArenaAchievementDao getArenaAchievementDao() {
		return daoHelper.arenaAchievementDAO;
	}
	public HeroWarActivityDao getHeroWarActivityDao() {
		return daoHelper.heroWarActivityDao;
	}
	public DayTaskInfoDao getDayTaskInfoDao() {
		return daoHelper.dayTaskInfoDao;
	}
	
	public TowerInfoDao getTowerInfoDao(){
		return daoHelper.towerInfoDao;
	}
	
	/** 获取家族dao */
	public FamilyDao getFamilyDao(){
		return daoHelper.familyDao;
	}
	
	/** 获取家族日志dao */
	public FamilyLogDao getFamilyLogDao(){
		return daoHelper.familyLogDao;
	}
	
	/** 获取家族成员dao */
	public FamilyMemberDao getFamilyMemberDao(){
		return daoHelper.familyMemberDao;
	}
	
	/** 获取礼包dao */
	public GiftBagDao getGiftBagDao(){
		return daoHelper.giftBagDao;
	}
	
	/** 获取兵法dao */
	public WarcraftDao getWarcraftDao(){
		return daoHelper.warcraftDao;
	}
	
	/** 获取兵法信息dao */
	public WarcraftInfoDao getWarcraftInfoDao(){
		return daoHelper.warcraftInfoDao;
	}
	public PlayerGuideDAO getPlayerGuideDAO(){
		return daoHelper.playerGuideDAO;
	}
	public ProbInfoDAO getProbInfoDAO(){
		return daoHelper.probInfoDAO;
	}
	
	/** 摇钱树dao */
	public TreeInfoDao getTreeInfoDao(){
		return daoHelper.treeInfoDao;
	}
	
	/** 摇钱树浇水dao */
	public TreeWaterDao getTreeWaterDao(){
		return daoHelper.treeWaterDao;
	}
	/**
	 * 修炼信息DAO
	 */
	public HumanXiulianDAO getHumanXiulianDAO(){
		return daoHelper.humanXiulianDAO;
	}
	/**
	 * 夺宝信息DAO
	 */
	public HumanRobberyDAO getHumanRobberyDAO(){
		return daoHelper.humanRobberyDAO;
	}
	
	/**
	 * 申请家族成员DAO
	 * @return
	 */
	public FamilyApplyMemberDao getFamilyApplyMemberDao(){
		return daoHelper.familyApplyMemberDao;
	}
	/**
	 * 定时广播DAO
	 */
	public TimeNoticeDAO getTimeNoticeDAO(){
		return daoHelper.timeNoticeDAO;
	}
	/**
	 * 扫荡信息DAO
	 */
	public RepAgainstDAO getRepAgainstDAO(){
		return daoHelper.repAgainstDAO;
	}
	/**
	 * 提示按钮DAO
	 * @return
	 */
	public PromptButtonDao getPromptButtonDao(){
		return daoHelper.promptButtonDao;
	}
	/**
	 * Dao 配置，初始化 - dao
	 * 
	 * 
	 */
	private static final class DaoHelper {

		/** 数据库连接 */
		private final DBService dbService;
		/** 数据库版本号DAO */
		private final DbVersionDao dbVersionDao;
		/** 用户信息 DAO */
		private final UserInfoDao userInfoDao;
		/** 角色信息 DAO */
		private final HumanDao humanDao;
		/** 进行中的任务 DAO */
		private final DoingQuestDao doingQuestDao;
		/** 已完成的任务 DAO */
		private final FinishedQuestDao finishedQuestDao;
		/** 日常任务 DAO */
		private final DailyQuestDao dailyQuestDao;
		
		/** 定时公告DAO **/
		private final TimeNoticeDAO timeNoticeDAO;
		/** 道具物品 DAO */
		private final ItemDao itemDao;
		/** 邮件信息 DAO */
		private final MailDao mailDao;
		/** 武将信息 DAO */
		private final PetDao petDao;
		/** 场景信息 DAO */
		private final SceneDao sceneDao;
		/** 进程事件 DAO */
		private final ProcessEventDao processDao;
		/** 平台奖励 DAO */
		private final PlatformPrizeInfoDao platformPrizeDao;
		/** GM奖励的 DAO */
		private final UserPrizeDao userPrizeDao;
		/** 直充订单 DAO */
		private final ChargeInfoDao chargeInfoDao;

		private final VocationSkillDao vacationSkillDao;

		private final SkillGroupDao skillGroupDao;

		private final FormDao formDao;
		
		private final HumanRepInfoDao humanRepInfoDao;

		/** 服务器存储的一些信息 **/
		private final ServerInfoDAO serverInfoDAO;
		/**冷却dao*/
		private final CooldownDao cooldownDao;
		/**buffer dao*/
		private final BufferDao bufferDao;
		/**  成就DAO **/
		private final UserArchiveEntityDAO userArchiveEntityDAO;
		
		private final BattleInitDao battleInitDao;
		
		private final BattleReportDao battleReportDao;
		
		private final BattleEndDao battleEndDao;
		
		/** 剧情DAO */
		private final FinishedStoryDao finishedStoryDao;
		
		private final FriendDao friendDao;
		
		private final SecretShopInfoDao secretShopInfo;
		
		private final RobberyDAO robberyDAO;
		
		/** 竞技场DAO */
		private final ArenaDao arenaDAO;
		private final ArenaHistoryDao arenaHistoryDAO;
		private final ArenaAchievementDao arenaAchievementDAO;
				
		/** 主将争夺战 **/
		private final HeroWarActivityDao heroWarActivityDao;
		
		/** 每日任务DAO */
		private final DayTaskInfoDao dayTaskInfoDao;
		
		/** 将星云路DAO */		
		private final TowerInfoDao towerInfoDao;
		
		/** 家族DAO */
		private final FamilyDao familyDao;
		
		/** 家族日志DAO */
		private final FamilyLogDao familyLogDao;
		
		/** 家族成员DAO */
		private final FamilyMemberDao familyMemberDao;
		
		/** 申请家族成员DAO */
		private final FamilyApplyMemberDao familyApplyMemberDao;
		
		/** 礼包DAO */
		private final GiftBagDao giftBagDao;
		
		/** 兵法DAO */
		private final WarcraftDao warcraftDao;
		
		/** 兵法信息DAO */
		private final WarcraftInfoDao warcraftInfoDao;
		
		private final PlayerGuideDAO playerGuideDAO;
		
		private final ProbInfoDAO probInfoDAO;
		
		/** 摇钱树DAO */
		private final TreeInfoDao treeInfoDao;
		
		/** 摇钱树浇水DAO */
		private final TreeWaterDao treeWaterDao;
		
		/** 修炼DAO **/
		private final HumanXiulianDAO humanXiulianDAO;
		
		/** 夺宝信息DAO **/
		private final HumanRobberyDAO humanRobberyDAO;
	
		/** 扫荡DAO **/
		private final RepAgainstDAO repAgainstDAO;
		
		/** 提示按钮DAO */
		private final PromptButtonDao promptButtonDao;
		
		/** 不同POclass对应的dao */
		private HashMap<Class<? extends PersistanceObject<?, ?>>, BaseDao<?>> clazzDaoMap;

		private DaoHelper(GameServerConfig config) {
			/** 资源初始化 */
			EventListenerAdapter eventAdapter = new EventListenerAdapter();
			eventAdapter.addListener(DBAccessEvent.class,
					new DBAccessEventListener(config));
			ClassLoader _classLoader = Thread.currentThread()
					.getContextClassLoader();
			int daoType = config.getDbInitType();

			// db
			String[] _dbConfig = config.getDbConfigName().split(",");
			URL _dbUrl = _classLoader.getResource(_dbConfig[0]);
			String[] _dbResources = new String[_dbConfig.length - 1];
			if (_dbConfig.length > 1) {
				System.arraycopy(_dbConfig, 1, _dbResources, 0,
						_dbConfig.length - 1);
			}

			/* 数据库类初始化 */
			dbService = DBServiceBuilder.buildDirectDBService(eventAdapter,
					daoType, _dbUrl, _dbResources);

			Loggers.gameLogger.info("DBService instance:" + dbService);

			/* dao管理类初始化 */
			timeNoticeDAO = new TimeNoticeDAO(dbService);
			userInfoDao = new UserInfoDao(dbService);
			humanDao = new HumanDao(dbService);
			dbVersionDao = new DbVersionDao(dbService);
			itemDao = new ItemDao(dbService);
			mailDao = new MailDao(dbService);
			doingQuestDao = new DoingQuestDao(dbService);
			finishedQuestDao = new FinishedQuestDao(dbService);
			dailyQuestDao = new DailyQuestDao(dbService);
			petDao = new PetDao(dbService);
			sceneDao = new SceneDao(dbService);
			platformPrizeDao = new PlatformPrizeInfoDao(dbService);
			userPrizeDao = new UserPrizeDao(dbService);
			chargeInfoDao = new ChargeInfoDao(dbService);
			processDao = new ProcessEventDao(dbService);
			vacationSkillDao = new VocationSkillDao(dbService);
			skillGroupDao = new SkillGroupDao(dbService);
			formDao = new FormDao(dbService);
			serverInfoDAO = new ServerInfoDAO(dbService);
			userArchiveEntityDAO = new UserArchiveEntityDAO(dbService);
			cooldownDao =new CooldownDao(dbService);
			battleInitDao= new BattleInitDao(dbService);
			battleEndDao =new BattleEndDao(dbService);
			battleReportDao =new BattleReportDao(dbService);
			bufferDao =new BufferDao(dbService);
			clazzDaoMap = new HashMap<Class<? extends PersistanceObject<?, ?>>, BaseDao<?>>();
			clazzDaoMap.put(Human.class, humanDao);
			humanRepInfoDao = new HumanRepInfoDao(dbService);
			finishedStoryDao = new FinishedStoryDao(dbService);
			friendDao = new FriendDao(dbService);
			secretShopInfo = new SecretShopInfoDao(dbService);
			robberyDAO = new RobberyDAO(dbService);
			arenaDAO = new ArenaDao(dbService);
			arenaHistoryDAO = new ArenaHistoryDao(dbService);
			arenaAchievementDAO = new ArenaAchievementDao(dbService);
			heroWarActivityDao = new HeroWarActivityDao(dbService);
			dayTaskInfoDao = new DayTaskInfoDao(dbService);
			towerInfoDao = new TowerInfoDao(dbService);
			familyDao = new FamilyDao(dbService);
			familyLogDao = new FamilyLogDao(dbService);
			familyMemberDao = new FamilyMemberDao(dbService);
			giftBagDao = new GiftBagDao(dbService);
			warcraftDao = new WarcraftDao(dbService);
			warcraftInfoDao = new WarcraftInfoDao(dbService);
			playerGuideDAO = new PlayerGuideDAO(dbService);
			probInfoDAO = new ProbInfoDAO(dbService);
			treeInfoDao = new TreeInfoDao(dbService);
			treeWaterDao = new TreeWaterDao(dbService);
			humanXiulianDAO = new HumanXiulianDAO(dbService);
			humanRobberyDAO = new HumanRobberyDAO(dbService);
			familyApplyMemberDao = new FamilyApplyMemberDao(dbService);
			repAgainstDAO = new RepAgainstDAO(dbService);
			promptButtonDao = new PromptButtonDao(dbService);
		}	
	}

}
