package com.pwrd.war.gameserver.human;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Sets;
import com.pwrd.op.LogOp;
import com.pwrd.op.LogOpChannel;
import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.LevelUpLogReason;
import com.pwrd.war.common.LogReasons.LoginLogReason;
import com.pwrd.war.common.LogReasons.OnlineTimeLogReason;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.core.util.KeyValuePair;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.db.model.BufferEntity;
import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.behavior.BehaviorManager;
import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.buff.enums.BuffBigType;
import com.pwrd.war.gameserver.buff.enums.BufferType;
import com.pwrd.war.gameserver.buff.enums.ModifyType;
import com.pwrd.war.gameserver.buff.msg.GCBufferInitMessage;
import com.pwrd.war.gameserver.charge.ChargeManager;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.RoleDataHolder;
import com.pwrd.war.gameserver.common.enums.Sex;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.common.event.CheckFunctionEvent;
import com.pwrd.war.gameserver.common.event.DayTaskEvent;
import com.pwrd.war.gameserver.common.event.HPReduceEvent;
import com.pwrd.war.gameserver.common.i18n.LangService;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.currency.CurrencyProcessor;
import com.pwrd.war.gameserver.dayTask.DayTaskType;
import com.pwrd.war.gameserver.family.manager.FamilyManager;
import com.pwrd.war.gameserver.family.model.Family;
import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.form.PetOrder;
import com.pwrd.war.gameserver.friend.FriendManager;
import com.pwrd.war.gameserver.giftBag.model.GiftBagContianer;
import com.pwrd.war.gameserver.human.cooldown.CoolDownManager;
import com.pwrd.war.gameserver.human.manager.ArchiveManager;
import com.pwrd.war.gameserver.human.manager.HumanInitManager;
import com.pwrd.war.gameserver.human.manager.HumanPetManager;
import com.pwrd.war.gameserver.human.manager.HumanProbInfoManager;
import com.pwrd.war.gameserver.human.manager.HumanPropertyManager;
import com.pwrd.war.gameserver.human.manager.HumanRepInfoManager;
import com.pwrd.war.gameserver.human.msg.GCLoginPrizeShow;
import com.pwrd.war.gameserver.human.msg.GCOnlinePrize;
import com.pwrd.war.gameserver.human.template.HumanInitPropTemplate;
import com.pwrd.war.gameserver.human.template.NewPlayerPrizeTemplate;
import com.pwrd.war.gameserver.human.xiulian.XiulianInfo;
import com.pwrd.war.gameserver.item.EquipmentFeature;
import com.pwrd.war.gameserver.item.Inventory;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.mail.MailBox;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerExitReason;
import com.pwrd.war.gameserver.player.async.PlayerGetDirectCharge;
import com.pwrd.war.gameserver.player.msg.GCNotifyException;
import com.pwrd.war.gameserver.promptButton.manager.PromptButtonManager;
import com.pwrd.war.gameserver.quest.QuestDiary;
import com.pwrd.war.gameserver.quest.QuestMessageBuilder;
import com.pwrd.war.gameserver.rep.Rep;
import com.pwrd.war.gameserver.rep.against.AgainstRepInfo;
import com.pwrd.war.gameserver.rep.against.HumanRepAgaisntInfo;
import com.pwrd.war.gameserver.robbery.HumanRobberyInfo;
import com.pwrd.war.gameserver.role.AbstractHuman;
import com.pwrd.war.gameserver.role.Role;
import com.pwrd.war.gameserver.role.RoleFinalProps;
import com.pwrd.war.gameserver.role.RoleTypes;
import com.pwrd.war.gameserver.role.properties.HumanNormalProperty;
import com.pwrd.war.gameserver.role.properties.HumanNumProperty;
import com.pwrd.war.gameserver.role.properties.RoleBaseIntProperties;
import com.pwrd.war.gameserver.role.template.JingjieTemplate;
import com.pwrd.war.gameserver.startup.MinaGameClientSession;
import com.pwrd.war.gameserver.story.StoryManager;
import com.pwrd.war.gameserver.team.TeamInfo;
import com.pwrd.war.gameserver.tower.TowerInfoManager;
import com.pwrd.war.gameserver.tree.TreeInfoManager;
import com.pwrd.war.gameserver.tree.TreeWaterManager;
import com.pwrd.war.gameserver.vitality.template.VipToVitTemplate;
import com.pwrd.war.gameserver.vocation.VocationSkill;
import com.pwrd.war.gameserver.warcraft.constants.WarcraftDef;
import com.pwrd.war.gameserver.warcraft.container.WarcraftEquipBag;
import com.pwrd.war.gameserver.warcraft.manager.WarcraftInventory;
import com.pwrd.war.gameserver.warcraft.model.Warcraft;

/**
 * 
 * 人物角色
 * 
 */
public class Human extends AbstractHuman implements
		PersistanceObject<String, HumanEntity>, RoleDataHolder {

	/** 人物uuid */
	private String roleUUID;

	/** 角色所属玩家 */
	private Player player;

	/** 是否已在数据库中 */
	private boolean isInDb;

	/** 物品 */
	private Inventory inventory;
	/** 任务日志 */
	private QuestDiary questDiary;
	/** 剧情 */
	private StoryManager storyManager;
	/** 邮箱 */
	private MailBox mailbox;
	/** 消息控制 */
	private HumanMessageControl messageControl;
	/** 生命期 */
	private final LifeCycle lifeCycle;

	/** 所有可以充值的订单IDs */
	private Set<Long> directChargeOrderIds;

	/** 在线状态 */
	private int onlineStatus;

	// ///////////
	// 各种管理器//
	// ///////////
	/** 初始化管理器 */
	private HumanInitManager initManager;
	/** 属性管理器 */
	private HumanPropertyManager propertyManager;
	/** 武将管理器 */
	private HumanPetManager petManager;
	/** 行为记录管理器 */
	private BehaviorManager behaviorManager;
	/** 充值管理器 */
	private ChargeManager chargeManager;

	private HumanRepInfoManager humanRepInfoManager;

	private FriendManager friendManager; 
	/** 成就 **/
	private ArchiveManager archiveManager;

	/** 初始属性模版 **/
	private HumanInitPropTemplate initTemplate;

	/** 战斗阵型 */
	private BattleForm battleForm;

	/** 启动的阵型 */
//	private BattleForm defaultForm;

	private VocationSkill[] vocationSkills;

	/** 组队信息 **/
	private TeamInfo teamInfo;

	/** 玩家在游戏中的状态 */
	private int gamingState = GamingStateIndex.IN_NOMAL.getValue();

	private CoolDownManager cooldownManager;

//	private Map<BufferType, Buffer> buffers = new ConcurrentHashMap<BufferType, Buffer>();
	private Map<BuffBigType, Buffer> buffers = new ConcurrentHashMap<BuffBigType, Buffer>();
	
	/**是否有切磋请求*/
	private AtomicBoolean isRequestQiecuo =new AtomicBoolean(false);
	
	/**与其切磋者*/
	private String qiecuoPlayerUUID;
	
	/** 家族 */
	private Family family;
	
	/**
	 * 玩家家族管理器
	 */
	private FamilyManager familyManager;
	
	/** 玩家礼包容器管理器 */
	private GiftBagContianer giftBagContianer;
	
	/** 玩家兵法容器管理器 */
	private WarcraftInventory warcraftInventory;
	
	/** 玩家将星云路管理器*/
	private TowerInfoManager towerInfoManager;

	/** 伪随机管理器，保证必然出现所有随机 **/
	private HumanProbInfoManager humanProbInfoManager;
	
	/** 摇钱树 **/
	private TreeInfoManager treeInfoManager;
	
	/** 摇钱树 **/
	private TreeWaterManager treeWaterManager;
	
	/** 夺宝数据信息 **/
	private HumanRobberyInfo robberyInfo;
	
	/** 扫荡信息 **/
	private HumanRepAgaisntInfo repAgainstInfo;
	
	/** 提示按钮管理器 */
	private PromptButtonManager promptMessageManager;
	/*****************************/
	/**
	 * 添加buffer
	 * 
	 * @param bufferSn
	 * @return 添加成功没
	 */
	public boolean addBuffer(String bufferSn) {
		Buffer buffer = Globals.getBufferService().getInstanceBuffer(bufferSn,
				Globals.getTimeService().now());
		if (buffer == null) {
			return false;
		}
		buffer.setOwner(this);
		return buffer.addBuffer(this);
	}

	/**
	 * 修改buffer
	 * 
	 * @param bufferType
	 * @param number
	 * @return 剩余需要修改的
	 */
	public double modifyBuffer(BufferType bufferType, int number) {
		Buffer buffer = buffers.get(bufferType);
		if (buffer != null) {
			return buffer.modify(this, number);
		}
		return number;
	}

	public Human() {
		super(RoleTypes.HUMAN);
		lifeCycle = new LifeCycleImpl(this);
		mailbox = new MailBox(this);
		initManager = new HumanInitManager(this);
		propertyManager = new HumanPropertyManager(this);
		petManager = new HumanPetManager(this);
		messageControl = new HumanMessageControl(this);
		behaviorManager = new BehaviorManager(this);

		chargeManager = new ChargeManager(this,
				Globals.getChargePrizeService(), Globals.getTimeService());

		archiveManager = new ArchiveManager(this);

		directChargeOrderIds = Sets.newHashSet();

		humanRepInfoManager = new HumanRepInfoManager(this);

		cooldownManager = new CoolDownManager(this);
		
		questDiary = new QuestDiary(this, new QuestMessageBuilder(), Globals.getDaoService(), Globals.getLogService());
		storyManager = new StoryManager(this, Globals.getDaoService(), Globals.getLogService());
		
		friendManager = new FriendManager(this);
		
		familyManager = new FamilyManager(this);
		
		giftBagContianer = new GiftBagContianer(this);
		
		warcraftInventory = new WarcraftInventory(this);
		towerInfoManager = new TowerInfoManager(this);
		humanProbInfoManager = new HumanProbInfoManager(this);
		treeInfoManager = new TreeInfoManager(this);
		treeWaterManager = new TreeWaterManager(this);
		promptMessageManager = new PromptButtonManager(this);
	}

	/**
	 * 加载角色属性,才能初始化背包
	 */
	public void initInventory() {
		inventory = new Inventory(this, Globals.getItemService(),
				Globals.getLangService());
	}

	/**
	 * 在角色数据加载完成之后的初始化
	 * 
	 * @param templateServ
	 * @param langServ
	 */
	public void onInit(TemplateService templateServ, LangService langServ) {
		// 武将管理器初始化
		this.petManager.init();
		// 行为管理器初始化
		this.behaviorManager.init(templateServ);
		// 加载成就信息
		this.archiveManager.init();
		// 冷却队列初始化
		this.cooldownManager.init();
		//
		this.humanRepInfoManager.load();
		
		this.friendManager.init();
		// 开始计算属性，这个时候装备啥的都已经加载完毕
		this.calcProps();
		//发送提示按钮信息
		this.promptMessageManager.init();
		
	}
	/**
	 * 加载并且初始化buff
	 */
	public void loadBuff(){
		List<BufferEntity> bufferEntitys = Globals.getDaoService()
				.getBufferDao().getBufferByHumanId(getUUID());
		if (bufferEntitys != null && !bufferEntitys.isEmpty()) {
			for (BufferEntity bufferEntity : bufferEntitys) {
				Buffer buffer = Buffer.generatorBuffer(bufferEntity);
				if (buffer != null) {
					buffer.setInDb(true);
					buffer.setOwner(this);
					BufferType bufferType = BufferType.getBufferTypeById(buffer
							.getBufferType());
					if (bufferType != null) {
						switch (bufferType) {
						case money:
							buffers.put(buffer.getBuffBigType(), buffer);
							break;
						case experience:
							if (buffer.getEndTime() > System
									.currentTimeMillis()) {
								buffers.put(buffer.getBuffBigType(), buffer);
							} else {
								buffer.onDelete();
							}
							break;
						case attack:
							if (buffer.getEndTime() > System
									.currentTimeMillis()) {
								buffers.put(buffer.getBuffBigType(), buffer);
							} else {
								buffer.onDelete();
							}
							break;
						case defense:
							if (buffer.getEndTime() > System
									.currentTimeMillis()) {
								buffers.put(buffer.getBuffBigType(), buffer);
							} else {
								buffer.onDelete();
							}
							break;
						case life:
							if (buffer.getEndTime() > System
									.currentTimeMillis()) {
								buffers.put(buffer.getBuffBigType(), buffer);
							} else {
								buffer.onDelete();
							}
							break;
						case vitality:
							buffers.put(buffer.getBuffBigType(), buffer);
							break;
						case medicinal:
							buffers.put(buffer.getBuffBigType(), buffer);
							break;
						case change:
							buffers.put(buffer.getBuffBigType(), buffer);
							break;
						case vip:
							if (buffer.getEndTime() > System
									.currentTimeMillis()) {
								buffers.put(buffer.getBuffBigType(), buffer);
							} else {
								buffer.onDelete();
							}
							break;
						default:
							break;
						}
					}
				}

			} 
		}
	}
	/**
	 * 发送buff消息
	 */
	public void sendBufferMessage() {
		GCBufferInitMessage message = new GCBufferInitMessage();		 
		message.setBuffers(buffers.values().toArray(
				new Buffer[buffers.size()]));
		sendMessage(message);		
	}

	/**
	 * 在数据加载完之后的登陆，不发送消息
	 * 
	 * @param isFirstLogin
	 */
	public void onLogin(boolean isFirstLogin) {
		// 是否是玩家第一登录游戏
		if (isFirstLogin) {
			// 发送新手物品
			Globals.getHumanAssistantService().giveFirstLoginGifts(this);

			Globals.getHumanAssistantService().initHuman(this);

		} 
		//判断是不是今日第一次登录
		long last =  0 ;
		if(!isFirstLogin){
			last = getLastLoginTime();
		}
		long now = Globals.getTimeService().now();
		if(!TimeUtils.isSameDay(last, now) || isFirstLogin){
			this.onDayFirstLogin();
		}
 
		// 记录登陆时间和ip
		logLogin(now, getPlayer().getSession().getIp()); 
		
	}	
	/**
	 * 每日第一次登录该做的事情改一些数值啊
	 * @author xf
	 */
	public void onDayFirstLogin(){
		this.getPropertyManager().getPropertyNormal().setTotalDays(this.getPropertyManager().getPropertyNormal().getTotalDays() + 1);
		//充值夺宝信息
		Globals.getRobberyService().dayInit(this);
		
	}
	/**
	 * 检查在线奖励，是否重新开始倒计时，升级时不重新，上线时重新
	 * @author xf
	 */
	public void checkOnlinePrize(boolean restart){
		short nowbuzhou = this.getPropertyManager().getPropertyNormal().getNewPrize();
		NewPlayerPrizeTemplate tmp = Globals.getHumanService().getPrizeTemplateByBuzhou(nowbuzhou);
		if(tmp != null ){
			if(tmp.getLevel() <= this.getLevel()){//当前步骤可完成
				if(restart || this.getPropertyManager().getPropertyNormal().getNewPrizeSTime() == 0
						){
					this.getPropertyManager().getPropertyNormal().setNewPrizeSTime(Globals.getTimeService().now());
				}
				this.getPropertyManager().getPropertyNormal().setNewPrize(tmp.getBuzhou());
				GCOnlinePrize rmsg = new GCOnlinePrize();
				rmsg.setBuzhou(tmp.getBuzhou());
				rmsg.setLeftTime((int) (tmp.getOnlinetime() - 
						(Globals.getTimeService().now() - getPropertyManager().getPropertyNormal().getNewPrizeSTime())/1000));
				if(rmsg.getLeftTime() < 0)rmsg.setLeftTime(0);
				rmsg.setPrizeTip(tmp.getPrizeTip());
				rmsg.setTip(tmp.getTip());
				this.sendMessage(rmsg);
				return;
			} 
		}
	}
	/**
	 * 检查有无登陆奖励
	 */
	public void checkLoginGifts(){
		if(Globals.getServerConfig().isTestGiftEnabled()){
			//开启了功能
			int days = this.getPropertyManager().getPropertyNormal().getTotalDays();
			String gets = this.getPropertyManager().getPropertyNormal().getLoginGifts();
			String getss[] = null;
			if(StringUtils.isEmpty(gets)){
				getss = new String[0];
			}else{
				getss = gets.split(",");
			}			
			int[] getsi = new int[getss.length];
			int index = 0;
			for(String s : getss){
				if(StringUtils.isNotEmpty(s)){
					getsi[index++] = Integer.valueOf(s);
				}
			}
			StringBuffer canGet = new StringBuffer();
			StringBuffer hasGet = new StringBuffer();
			for(int i = 1; i <= days; i++){
				boolean get = false;
				for(int g : getsi){
					if(g == i){
						get = true;
						break;
					}
				}
				if(!get){
					canGet.append(i+",");
				}else{
					hasGet.append(i+",");
				}
			}
			if(StringUtils.isEmpty(canGet.toString()) 
					&& days >= Globals.getHumanService().getAllDayLoginPrize().size()){
				this.sendMessage(new GCLoginPrizeShow(false));
			}else{
				this.sendMessage(new GCLoginPrizeShow(true));
			}
		}
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public MailBox getMailbox() {
		return mailbox;
	}

	public QuestDiary getQuestDiary() {
		return questDiary;
	}
	
	public StoryManager getStoryManager() {
		return storyManager;
	}

	public ArchiveManager getArchiveManager() {
		return archiveManager;
	}

	@Override
	public String getUUID() {
		return roleUUID;
	}

	@Override
	public String getGUID() {
		return "human#" + getUUID();
	}

	public void sendMessage(GCMessage msg) {
		if (msg != null && player != null) {
			player.sendMessage(msg);
		}
	}

	public void sendSystemMessage(String content) {
		if (player != null) {
			player.sendSystemMessage(content);
		}
	}

	public void sendErrorMessage(String content) {
		if (player != null) {
			player.sendErrorMessage(content);
		}
	}
 

	public void sendSystemMessage( Integer key, Object... params) {
		if (player != null) {
			player.sendSystemMessage( key, params);
		}
	}

	public void sendSystemMessage(Integer key, Object param) {
		if (player != null) {
			player.sendSystemMessage( key, param);
		}
	}

	public void sendSystemMessage(Integer key, Object param1, Object param2) {
		if (player != null) {
			player.sendSystemMessage( key, param1, param2);
		}
	}

	/**
	 * 提示语句和语句的参数全部采用langId作参数发送系统消息
	 * 
	 * @param key
	 *            提示语句langId
	 * @param paramKeys
	 *            参数的langId
	 */
	public void sendSysMsgWithI18nParams(Integer key, Integer... paramKeys) {
		if (player != null) {
			player.sendSysMsgWithI18nParams( key, paramKeys);
		}
	}

	public void sendNoticeMessage(String content, short speed) {
		if (player != null) {
			player.sendNoticeMessage(content, speed);
		}
	}

	public void sendIMessage(String content) {
		if (player != null) {
			player.sendSystemWithChatMessage(content);
		}
	}

	public void sendSystemWithChatMessage(Integer key) {
		if (player != null) {
			player.sendSystemWithChatMessage(key);
		}
	}

	public void sendSystemWithChatMessage(Integer key, Object... params) {
		if (player != null) {
			player.sendSystemWithChatMessage(key, params);
		}
	}
	public void sendErrorMessage(Integer key) {
		if (player != null) {
			player.sendErrorMessage( key);
		}
	}
	public void sendErrorMessage(Integer key, Object... params) {
		if (player != null) {
			player.sendErrorMessage( key, params);
		}
	}
	public void sendErrorMessage(Integer key, Object param) {
		if (player != null) {
			player.sendErrorMessage( key, param);
		}
	}
	public void sendErrorMessage(Integer key, Object param1, Object param2) {
		if (player != null) {
			player.sendErrorMessage( key, param1, param2);
		}
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
		if (player != null) {
			player.sendErrorMsgWithI18nParams(key, paramKeys);
		}
	}

	public void sendOptionDialogMessge(String tag, Integer langId) {
		if (player != null) {
			player.sendOptionDialogMessage(tag, langId);
		}
	}

	public void sendOptionDialogMessge(String tag, Integer langId,
			Object... params) {
		if (player != null) {
			player.sendOptionDialogMessage(tag, langId, params);
		}
	}

	public void sendOptionDialogMessge(String tag, String title, Integer langId) {
		if (player != null) {
			player.sendOptionDialogMessage(tag, title, langId);
		}
	}

	public void sendOptionDialogMessge(String tag, String title,
			Integer langId, Object... params) {
		if (player != null) {
			player.sendOptionDialogMessage(tag, title, langId, params);
		}
	}

	public HumanMessageControl getMessageControl() {
		return messageControl;
	}

	public String getPassportId() {
		return player.getPassportId();
	}

	public String getPassportName() {
		return player.getPassportName();
	}

	public void addDirectChargeOrderId(long orderId) {
		directChargeOrderIds.add(orderId);
	}

	@Override
	public void heartBeat() {
		try {
			super.heartBeat();
			inventory.onHeartBeat();
			petManager.onHeartBeat();
			dealWithBuff();
		} catch (Exception e) {
			// 心跳发生异常，发送错误码，把玩家踢下线
			Loggers.playerLogger.error(
					LogUtils.buildLogInfoStr(this.roleUUID, "玩家心跳时发生异常"), e);
			this.sendMessage(new GCNotifyException(
					CommonLangConstants_10000.DC_HEARTBEAT_EXCEPTION, 
					Globals.getLangService().read(CommonLangConstants_10000.DC_HEARTBEAT_EXCEPTION)));
			this.getPlayer().exitReason = PlayerExitReason.SERVER_ERROR;
			this.getPlayer().disconnect();
		}
	}

	private void dealWithBuff() {

		for (Map.Entry<BuffBigType, Buffer> bufferMap : buffers.entrySet()) {
			switch (BufferType.getBufferTypeById(bufferMap.getValue()
					.getBufferType())) {
			case money:
				break;
			case experience:
				bufferMap.getValue().modify(this, 0);
				break;
			case attack:
				bufferMap.getValue().modify(this, 0);
				break;
			case defense:
				bufferMap.getValue().modify(this, 0);
				break;
			case life:
				bufferMap.getValue().modify(this, 0);
				break;
			case vitality:
				break;
			case medicinal:
				break;
			case change:
				bufferMap.getValue().modify(this, 0);
				break;
			case vip:
				bufferMap.getValue().modify(this, 0);
				break;
			default:
				break;
			}

		}
	}
	public int getVitalityFromBuffer() {
		Buffer buffer = buffers.get(BufferType.vitality);
		if (buffer != null) {
			return (int) buffer.getValue();
		}
		return 0;
	}

	
	/**
	 * 给钱
	 * 
	 * @param amount
	 *            改变数量，>0才有效
	 * @param currency
	 *            货币类型
	 * @param needNotify
	 *            是否在该函数内通知玩家货币改变，为true时信息格式为"您获得xx（货币单位）"， false时不在本函数内提示
	 * @param reason
	 *            给钱原因
	 * @param detailReason
	 *            详细原因，通常为null，扩展使用
	 * @param consumeDescs
	 *            返回给调用者的货币改变信息
	 * @return 给钱成功返回true,否则返回false,失败可能是钱已经超出了最大限额,参数不合法等
	 */
//	public boolean giveMoney(int amount, Currency currency, boolean needNotify,
//			MoneyLogReason reason, String detailReason, CurrencyCostType costType, CurrencyLogReason currencyReason) {
//		boolean rs = CurrencyProcessor.instance.giveMoney(this, amount, currency,
//				needNotify, reason, detailReason);
//		
//		if(rs && currency == Currency.COUPON && costType != null){
//			//GM平台
//			String reasonText = (reason != null) ? reason.getReasonText() : "";
//			long now = Globals.getTimeService().now();
//			LogOp.log(LogOpChannel.COST, this.getUUID(), now, TimeUtils.formatYMDTime(now), costType.getType(), 0, reasonText, 0, 0, -amount);
//		} 
//		
//		//LOG平台日志
//		if (rs) {
//			String costTypeText = (costType != null) ? costType.getType() : "";
//			Globals.getLogService().sendCurrencyLog(this, currencyReason, null,
//					1, currency.getIndex(), amount, costTypeText,
//					costTypeText, detailReason);
//		}
//		return rs;
//	}
	
	/**
	 * 
	 * @param amount
	 * @param currency
	 * @param needNotify
	 * @param currencyReason
	 * @param detailReason 详细原因 如果需要自己定义需要到货币多语言里按模块定义
	 * @return
	 */
	public boolean giveMoney(int amount, Currency currency, boolean needNotify,
			CurrencyLogReason currencyReason, String detailReason) {

		// 强行判断都填吧……
		if (currencyReason == null || StringUtils.isBlank(detailReason)) {
			return false;
		}

		// 扣钱
		boolean rs = CurrencyProcessor.instance.giveMoney(this, amount,
				currency, needNotify, currencyReason, detailReason);

		// 记日志
		// GM平台
		String costText = currencyReason.getReasonText();
		if (rs && currency == Currency.COUPON) {
			long now = Globals.getTimeService().now();
			LogOp.log(LogOpChannel.COST, this.getUUID(), now,
					TimeUtils.formatYMDTime(now), costText, 0, detailReason, 0,
					0, -amount);
		}

		// LOG平台日志
		if (rs) {
			Globals.getLogService().sendCurrencyLog(this, currencyReason, null,
					1, currency.getIndex(), amount, costText, costText,
					detailReason);
		}
		return rs;
	}

	/**
	 * 扣钱
	 * 
	 * @param amount
	 *            扣得数量，>0才有效
	 * @param mainCurrency
	 *            主货币类型，不为NULL/null才有效
	 * @param altCurrency
	 *            替补货币类型，可以为NULL/null
	 * @param needNotify
	 *            是否在该函数内通知玩家货币改变，为true时信息格式为"您花费了xx（货币单位）用于xx"， false时不在本函数内提示
	 * @param usageLangId
	 *            用途多语言Id,如果needNotify为true，这里提供的用途会被加载提示信息“用于”后面
	 * @param reason
	 *            扣钱的原因
	 * @param detailReasson
	 *            详细原因，通常为null，扩展使用
	 * @param reportItemId
	 *            向平台汇报贵重物品消耗时的itemTemplateId，非物品的消耗时使用-1
	 * @return 扣钱成功返回true,否则返回false,失败可能是钱已经超出了最大限额,参数不合法等
	 */
//	public boolean costMoney(int amount, Currency mainCurrency,
//			Currency altCurrency, boolean needNotify, Integer usageLangId,
//			MoneyLogReason reason, String detailReasson, int reportItemId) {
//		if(mainCurrency == Currency.GOLD){
//			//发送每日任务的事件
//			DayTaskEvent event = new DayTaskEvent(player.getHuman(), DayTaskType.COST_GOLD.getValue());
//			Globals.getEventService().fireEvent(event);
//		}
//		return CurrencyProcessor.instance.costMoney(this, amount, mainCurrency,
//				altCurrency, needNotify, usageLangId, reason, detailReasson,
//				reportItemId);
//	}
	
	/**
	 * 
	 * @param amount
	 * @param mainCurrency
	 * @param altCurrency
	 * @param needNotify
	 * @param usageLangId
	 * @param currencyReason
	 * @param detailReasson 详细原因 如果需要自己定义需要到货币多语言里按模块定义
	 * @param reportItemId
	 * @return
	 */
	public boolean costMoney(int amount, Currency mainCurrency,
			Currency altCurrency, boolean needNotify, Integer usageLangId,
			CurrencyLogReason currencyReason, String detailReasson, int reportItemId) {
		if(mainCurrency == Currency.GOLD){
			//发送每日任务的事件
			DayTaskEvent event = new DayTaskEvent(player.getHuman(), DayTaskType.COST_GOLD.getValue());
			Globals.getEventService().fireEvent(event);
		}
		return CurrencyProcessor.instance.costMoney(this, amount, mainCurrency,
				altCurrency, needNotify, usageLangId, currencyReason, detailReasson,
				reportItemId);
	}
	

	/**
	 * 检查玩家是否足够指定货币，如果替补货币为NULL/null则只检查主货币，主货币不可以为NULL/null
	 * 
	 * @param amount
	 *            数量，>=0才有效，==0时永远返回true
	 * @param mainCurrency
	 *            主货币类型
	 * @param altCurrency
	 *            替补货币类型，为NULL/null时只检测主货币
	 * @param needNotify
	 *            是否需要通知，如果为true则当返回false时会提示，您的xxx（主货币名称）不足
	 * @return 
	 *         如果主货币够amount返回true，主货币不够看替补货币够不够除现有主货币外剩下的，够也返回true，加起来都不够返回false，
	 *         参数无效也会返回false
	 */
	public boolean hasEnoughMoney(int amount, Currency mainCurrency,
			Currency altCurrency, boolean needNotify) {
		return CurrencyProcessor.instance.hasEnoughMoney(this, amount,
				mainCurrency, altCurrency, needNotify);
	}

	/**
	 * 获取玩家货币
	 * 
	 * @param currencyType
	 *            货币类型
	 * @return
	 */
	public int getMoney(Currency currencyType) {
		return CurrencyProcessor.instance.getMoney(this, currencyType);
	}

	/**
	 * 是否有足够多铜钱
	 */
	public boolean hasEnoughCoins(int coins, boolean needNotify){
		return this.hasEnoughMoney(coins, Currency.COINS, null, needNotify);
	}
	/**
	 * 是否有足够多元宝
	 */
	public boolean hasEnoughGold(int gold, boolean needNotify){
		return this.hasEnoughMoney(gold, Currency.COUPON, Currency.GOLD, needNotify);
	}
	
	
//	/**
//	 * 扣铜钱
//	 */
//	public boolean costCoins(int coins, boolean needNotify, Integer usageLangId,
//			MoneyLogReason reason, String detailReason, int reportItemId, CurrencyCostType costType, CurrencyLogReason currencyReason){
//		boolean rs = this.costMoney(coins, Currency.COINS, null, needNotify, usageLangId, reason,
//				detailReason, reportItemId);
//		//Log平台
//		if (rs) {
//			String costTypeText = (costType != null) ? costType.getType() : "";
//			Globals.getLogService().sendCurrencyLog(this, currencyReason, null, 2,
//					Currency.COINS.getIndex(), coins, costTypeText,
//					costTypeText, detailReason);
//		}
//		return rs;
//	}

	/**
	 * 扣铜钱
	 * @param coins
	 * @param needNotify
	 * @param usageLangId
	 * @param currencyReason 
	 * @param detailReason 详细原因 如果需要自己定义需要到货币多语言里按模块定义
	 * @param reportItemId
	 * @return
	 */
	public boolean costCoins(int coins, boolean needNotify,
			Integer usageLangId, CurrencyLogReason currencyReason,
			String detailReason, int reportItemId) {
		
		// 强行判断都填吧……
		if (currencyReason == null || StringUtils.isBlank(detailReason)) {
			return false;
		}
		
		boolean rs = this.costMoney(coins, Currency.COINS, null, needNotify,
				usageLangId, currencyReason, detailReason, reportItemId);
		
		// Log平台
		if (rs) {
			String reasonText = currencyReason.getReasonText();
			Globals.getLogService().sendCurrencyLog(this, currencyReason, null,
					2, Currency.COINS.getIndex(), coins, reasonText,
					reasonText, detailReason);
		}
		return rs;
	}
	
//	/**
//	 * 扣元宝
//	 */
//	public boolean costGold(int golds, boolean needNotify, Integer usageLangId,
//			MoneyLogReason reason, String detailReason, int reportItemId,  CurrencyCostType costType, CurrencyLogReason currencyReason){
//				
//		boolean rs = this.costMoney(golds, Currency.COUPON, Currency.GOLD, needNotify, usageLangId, reason,
//				detailReason, reportItemId);
//		
//		if(rs && costType != null){
//			//GM平台
//			long now = Globals.getTimeService().now();
//			String reasonText = (reason != null) ? reason.getReasonText() : "";
//			LogOp.log(LogOpChannel.COST, this.getUUID(), now, TimeUtils.formatYMDTime(now), costType.getType(), reportItemId, reasonText, 1, golds, 0);
//		}
//		
//		// log平台
//		if (rs) {
//			String costTypeText = (costType != null) ? costType.getType() : "";
//			Globals.getLogService().sendCurrencyLog(this, currencyReason, null, 2,
//					Currency.COUPON.getIndex(), golds, costTypeText,
//					costTypeText, detailReason);
//		}
//		return rs;
//	}
	
	/**
	 * 扣元宝
	 * @param golds
	 * @param needNotify
	 * @param usageLangId
	 * @param currencyReason
	 * @param detailReason  详细原因 如果需要自己定义需要到货币多语言里按模块定义
	 * @param reportItemId
	 * @return
	 */
	public boolean costGold(int golds, boolean needNotify, Integer usageLangId,
			CurrencyLogReason currencyReason, String detailReason,
			int reportItemId) {

		// 强行判断都填吧……
		if (currencyReason == null || StringUtils.isBlank(detailReason)) {
			return false;
		}

		boolean rs = this.costMoney(golds, Currency.COUPON, Currency.GOLD,
				needNotify, usageLangId, currencyReason, detailReason,
				reportItemId);

		if (rs) {
			// GM平台
			String reasonText = currencyReason.getReasonText();
			long now = Globals.getTimeService().now();
			LogOp.log(LogOpChannel.COST, this.getUUID(), now,
					TimeUtils.formatYMDTime(now), reasonText, reportItemId,
					detailReason, 1, golds, 0);

			// log平台
			Globals.getLogService().sendCurrencyLog(this, currencyReason, null,
					2, Currency.COUPON.getIndex(), golds, reasonText,
					reasonText, detailReason);
		}
		return rs;
	}
	
	@Override
	public void checkAfterRoleLoad() {
		this.inventory.checkAfterRoleLoad();
		this.petManager.checkAfterRoleLoad();
		this.questDiary.checkAfterRoleLoad();
		this.mailbox.checkAfterRoleLoad();

		// 加载数据后,更新数据,实际就是为了设置更改状态
		// this.propertyManager.updateProperty(RolePropertyManager.PROP_FROM_MARK_ALL);
	}

	@Override
	public void checkBeforeRoleEnter() {
		this.inventory.checkBeforeRoleEnter();
		this.petManager.checkBeforeRoleEnter();
		this.questDiary.checkBeforeRoleEnter();

		for (Long orderId : this.directChargeOrderIds) {
			PlayerGetDirectCharge _chargOper = new PlayerGetDirectCharge(
					this.getPlayer(), orderId);
			Globals.getAsyncService().createOperationAndExecuteAtOnce(
					_chargOper, this.getUUID());
		}
	}

	@Override
	public String getCharId() {
		return roleUUID;
	}

	@Override
	public String getDbId() {
		return roleUUID;
	}

	@Override
	public boolean isInDb() {
		return isInDb;
	}

	@Override
	public void setDbId(String id) {
		this.roleUUID = id;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.isInDb = inDb;
	}

	@Override
	public HumanEntity toEntity() {
		HumanEntity entity = new HumanEntity();

		entity.setId(this.getUUID());
		entity.setPassportId(this.getPassportId());

		entity.setName(this.getName());
		entity.setSex(this.getSex().getCode());
		entity.setVocation(this.getVocationType().getCode());
		entity.setLevel(this.getLevel());

		entity.setGrow(this.getGrow());

		entity.setCurHp(this.getCurHp());
		entity.setMaxHp(this.getMaxHp());
		entity.setCurExp(this.getCurExp());
		entity.setMaxExp(this.getMaxExp());
		entity.setAtk(this.getAtk());
		entity.setDef(this.getDef());
		entity.setCri(this.getCri());
		entity.setSpd(this.getSpd());
		entity.setShanbi(this.getShanbi());
		entity.setShanghai(this.getShanghai());
		entity.setMianshang(this.getMianshang());
		entity.setFanji(this.getFanji());
		entity.setMingzhong(this.getMingzhong());
		entity.setLianji(this.getLianji());
		entity.setRenxing(this.getRenxing());
		entity.setZhandouli(this.getZhandouli());
		entity.setSkeletonId(this.getSkeletonId());
		
		entity.setMaxHpLevel(this.getMaxHpLevel());
		entity.setDefLevel(this.getDefLevel());
		entity.setAtkLevel(this.getAtkLevel());
		entity.setRemoteAtkLevel(this.getRemoteAtkLevel());
		entity.setRemoteDefLevel(this.getRemoteDefLevel());
		entity.setShortAtkLevel(this.getShortAtkLevel());
		entity.setShortDefLevel(this.getShortDefLevel());
		
		entity.setCamp(this.getCamp().getCode());
		entity.setPetSkill(this.getPetSkill());
		entity.setSkill1(this.getSkill1());
		entity.setSkill2(this.getSkill2());
		entity.setSkill3(this.getSkill3());
		entity.setPassSkill1(this.getPassiveSkill1());
		entity.setPassSkill2(this.getPassiveSkill2());
		entity.setPassSkill3(this.getPassiveSkill3());
		entity.setPassSkill4(this.getPassiveSkill4());
		entity.setPassSkill5(this.getPassiveSkill5());
		entity.setPassSkill6(this.getPassiveSkill6());
		entity.setPassSkillLevel1(this.getPassiveSkillLevel1());
		entity.setPassSkillLevel2(this.getPassiveSkillLevel2());
		entity.setPassSkillLevel3(this.getPassiveSkillLevel3());
		entity.setPassSkillLevel4(this.getPassiveSkillLevel4());
		entity.setPassSkillLevel5(this.getPassiveSkillLevel5());
		entity.setPassSkillLevel6(this.getPassiveSkillLevel6());
		entity.setMaxGrow(this.getMaxGrow());
		entity.setTransferLevel(this.getTransferLevel());
		entity.setTransferExp(this.getTransferexp());
		entity.setTransferstar(this.getTransferstar());
		
		entity.setMerit(this.propertyManager.getMerit());
		entity.setTitle(this.propertyManager.getTitle());
		entity.setVitality(this.propertyManager.getVitality());
		entity.setMaxVitality(this.propertyManager.getMaxVitality());
		entity.setBuyVitTimes(this.propertyManager.getBuyVitTimes());
		entity.setBuyVitalityDate(this.propertyManager.getBuyVitalityDate());
		entity.setCharm(this.propertyManager.getCharm());
		entity.setProtectFlower(this.propertyManager.getProtectFlower());
		entity.setOffical(this.propertyManager.getOffical());
		entity.setFamilyId(this.propertyManager.getFamilyId());
		entity.setFamilyRole(this.propertyManager.getFamilyRole());
		entity.setHonour(this.propertyManager.getHonour());
		entity.setMassacre(this.propertyManager.getMassacre());
		entity.setBattleAchieve(this.propertyManager.getBattleAchieve());
		entity.setSee(this.propertyManager.getSee());
		entity.setVip(this.propertyManager.getVip());
		entity.setBuffVip(this.propertyManager.getBuffVip());
//		entity.setSliver(this.propertyManager.getSliver());
		entity.setCoins(this.propertyManager.getCoins());
		entity.setGold(this.propertyManager.getGold());
		entity.setCoupon(this.propertyManager.getCoupon());
		entity.setHunshi(this.propertyManager.getHunshiNum());
		entity.setMuanBattle(this.propertyManager.getMuanBattle());
		entity.setOpenForm(this.propertyManager.isOpenForm());
		entity.setEquipEnhanceNum(this.propertyManager.getEquipEnhanceNum());
		entity.setInBattle(this.propertyManager.getIsInBattle());
		
		entity.setMaxBagSize(this.propertyManager.getMaxBagSize());
		entity.setBagSize(this.propertyManager.getBagSize());
		entity.setMaxDepotSize(this.propertyManager.getMaxDepotSize());
		entity.setDepotSize(this.propertyManager.getDepotSize());
		entity.setMerit(this.getPropertyManager().getMerit());
		entity.setChargeAmount(this.getPropertyManager().getChargeAmount());
		
		entity.setBuffAtk(this.propertyManager.getBuffAtk());
		entity.setBuffDef(this.propertyManager.getBuffDef());
		entity.setBuffMaxHp(this.propertyManager.getBuffMaxHp());
		
		entity.setSceneId(this.getSceneId());
		entity.setX((short) this.getX());
		entity.setY((short) this.getY());

		entity.setPopularity(this.propertyManager.getPopularity());
		entity.setGamingStatus(this.gamingState);

		entity.setLastLoginTime(this.getLastLoginTime());
		entity.setLastLogoutTime(this.getLastLogoutTime());
		entity.setLastLoginIp(this.getLastLoginIp());
		entity.setTotalMinute(this.getTotalMinute());
		entity.setOnlineStatus(this.getOnlineStatus());
		entity.setTodayCharge(this.getTodayCharge());
		entity.setTotalCharge(this.getTotalCharge());
		entity.setLastChargeTime(this.getLastChargeTime());
		entity.setLastVipTime(this.getLastVipTime());
		
		entity.setPersonSign(this.propertyManager.getPersonSign());
		entity.setOpenFunctions(this.propertyManager.getOpenFunc());
		
		JSONObject json = new JSONObject();
		json.put(HumanPropAttr.BEHAVIOR.toString(), this.getBehaviorManager()
				.toJsonProp());
		json.put(HumanPropAttr.QUEST_DIARY.toString(), this.getQuestDiary()
				.toJsonProp());
		// json.put(HumanPropAttr.BAG_PAGE_SIZE.toString(),
		// this.getBagPageSize());
		entity.setProps(json.toString());

		entity.setBeforeSenceId(this.getPropertyManager().getBeforeSceneId());
		entity.setBeforeX(this.getPropertyManager().getBeforeX());
		entity.setBeforeY(this.getPropertyManager().getBeforeY());
		
		
		//检查扫荡有无数据存储
		HumanNormalProperty prop = player.getHuman().getPropertyManager().getPropertyNormal();

		entity.setTotalDays(prop.getTotalDays());
		
		entity.setLastSecretShopTime(prop.getSecretShopTime());
		entity.setLastDayTaskTime(prop.getDayTaskTime());
		entity.setLastTowerRefreshTime(prop.getTowerRefreshTime());
		entity.setLastFightTime(prop.getLastFightTime());
		
		entity.setInAutoTower(prop.isInAutoTower());
		entity.setAutoTowerLastTime(prop.getAutoTowerLastTime());
		entity.setAutoTowerNum(prop.getAutoTowerNum());
		entity.setAutoTowerStartTime(prop.getAutoTowerStartTime());
		entity.setLastRepTime(prop.getLastRepTime());
		
		entity.setJingjiu1(prop.getJingjiu1());
		entity.setJingjiu2(prop.getJingjiu2());
		entity.setJingjiu3(prop.getJingjiu3());
		entity.setLastJingjiuTime(prop.getLastJingjiuTime());
		
		entity.setNewPrize(prop.getNewPrize());
		entity.setNewPrizeSTime(prop.getNewPrizeSTime());
		entity.setCanChat(prop.isCanChat());
		entity.setLoginGifts(prop.getLoginGifts());
		return entity;
	}

	/**
	 * 离线加载
	 * @param entity
	 */
	public void loadOffline(HumanEntity entity) {
		//加载玩家
		this.fromEntity(entity);
		
		// 初始化物品包裹
		this.initInventory();
		
		// 加载武将
		this.getPetManager().loadOffline();
		
		// 初始化宠物装备包裹
		this.getInventory().initPetBags();	
		
		// 加载物品
		this.getInventory().loadOffline();
		
		//加载Player
		Player player = new Player(new MinaGameClientSession(null));
		player.setPassportId(entity.getPassportId());
		this.setPlayer(player);
	}
	
	@Override
	public void fromEntity(HumanEntity entity) {
		this.getInitManager().loadHuman(entity);
		this.gamingState = entity.getGamingStatus();
	}
	boolean isCalcing;
	/**
	 * 重新计算属性 基础属性+装备属性（宝石等） 在穿戴装备，升级，等需要调用此方法
	 */
	public void calcProps(boolean bLevelUp) {
		if(isCalcing)return;
		isCalcing = true;
		// 先deactive;
		boolean needActive = false;
		if (this.lifeCycle.isActive()) {
			this.lifeCycle.deactivate();
			needActive = true;
		}
		// 读取初始配置属性，设置
		this.initPropsFromTmp();
		//读取境界属性
		JingjieTemplate nowJingjie = Globals.getHumanService().getJingjieTemplate(this.getGrow());
		for(int i=0;i<nowJingjie.getJingjie();i++){
			JingjieTemplate jingjie = Globals.getHumanService().getJingjieTemplates().get(i);
			if(jingjie != null){
				this.addAtk(jingjie.getAtk());
				this.addDef(jingjie.getDef());
				this.addMaxHp(jingjie.getHp());
				this.setShanghai(this.getShanghai() + jingjie.getShanghai());
			}
		}
		
		EnumMap<Position, Item> equips = this.inventory.getEquipBag()
				.getAllEquips();
		for (Item item : equips.values()) {
			EquipmentFeature f = (EquipmentFeature) item.getFeature();
			this.addAtk(f.getAtk());
			this.addDef(f.getDef());
			this.addMaxHp(f.getMaxHp());
			this.addSpd(f.getSpd());
			this.setCri(this.getCri() + f.getCri());
			this.setShanbi(this.getShanbi() + f.getShanbi());
			this.setShanghai(this.getShanghai() + f.getShanghai());
			this.setMianshang(this.getMianshang() + f.getMianshang());
			this.setFanji(this.getFanji() + f.getFanji());
			this.setMingzhong(this.getMingzhong() + f.getMingzhong());
			this.setLianji(this.getLianji() + f.getLianji());
			// 增加战斗力
			this.addZhandouli(f.getZhandouli());
		}
		
		//计算兵法属性
		WarcraftEquipBag warcraftEquipBag = this.warcraftInventory.getWarcraftEquipBag();
		List<Warcraft> warcrafts = warcraftEquipBag.getAllWarcraft();
		int score = 0;
		for(Warcraft warcraft : warcrafts){
			this.addAtk(warcraft.getAtk());
			this.addDef(warcraft.getDef());
			this.setShanbi(this.getShanbi() + warcraft.getShanbi());
			this.setMingzhong(this.getMingzhong() + warcraft.getMingzhong());
			this.setCri(this.getCri() + warcraft.getCri());
			this.addRenxing(warcraft.getRenXing());
			this.setCriShanghai(this.getCriShanghai() + warcraft.getCriShanghai());
			score += warcraft.getExp();
		}
		
		//同步兵法积分和属性
		this.setWarcraftScore(score / 30);
		
		//更新buff加的属性
//		this.getPropertyManager().getPropertyNormal().setBuffAtk(0);
//		this.getPropertyManager().getPropertyNormal().setBuffDef(0);
//		this.getPropertyManager().getPropertyNormal().setBuffMaxHp(0);
//		for(Buffer buff : this.getBufferByType(BufferType.attack)){
//			buff.update(this);
//		}
//		for(Buffer buff : this.getBufferByType(BufferType.defense)){
//			buff.update(this);
//		}
//		for(Buffer buff : this.getBufferByType(BufferType.life)){
//			buff.update(this);
//		}
		this.clearBuffValue();
		for(Buffer buffer:this.getBuffers().values()){
			buffer.updateThisRole(this);
		}
		
		// check
//		if (this.getCurHp() > this.getMaxHp()) {
			this.setCurHp(this.getMaxHp());
//		}
		// active;
		if (needActive) {
			this.lifeCycle.activate();
			 
			if(bLevelUp){
				this.setCurHp(this.getMaxHp()); 
			}else{
				HPReduceEvent e = new HPReduceEvent(this);
				Globals.getEventService().fireEvent(e);
			}
			
			this.onModified();
			// 发送消息
			this.snapChangedProperty(true);
		}
		isCalcing = false;
	}
	 

	/*
	 * 当属性被修改了，可以在这里重新计算战斗力等属性
	 */
	@Override
	public void onModified() {
		this.setModified();
	}

	@Override
	public void setModified() {
		if (this.lifeCycle.isActive()) {
			if(player == null || player.getDataUpdater() == null){
				Loggers.msgLogger.error("出现空指针"+player);
			}else{
				if(!player.getDataUpdater().isUpdating()){
					//如果正在更新暂时先不更新
					player.getDataUpdater().addUpdate(lifeCycle);
				}
			}
		}
	}

	/**
	 * 当属性被修改时触发通知机制
	 */
	protected void setNotified() {
		this.setModified();
		// if (this.lifeCycle.isActive()) {
		// player.getDataNotifier().addChanged(lifeCycle);
		// }
	}

	/**
	 * 记录登录时间
	 * 
	 * @param time
	 * @param ip
	 */
	public void logLogin(long time, String ip) {
		if (time > 0) {
			this.setLastLastLoginTime(this.getLastLoginTime());
			this.setLastLoginTime(time);
			this.setOnlineStatus(1);
			if (this.getPlayer() != null) {
				this.getPlayer().setLastLoginTime(time);
			}
		}
		this.setLastLastLoginIp(this.getLastLoginIp());
		this.setLastLoginIp(ip);
	}

	/**
	 * 记录退出时间和在线时长
	 */
	public void logLogout() {
		long _now = Globals.getTimeService().now();
		this.setLastLogoutTime(_now);
		this.setOnlineStatus(0);
		long _onlineTime = 0;
		long _loginTime = this.getLastLoginTime();
		long _logoutTime = this.getLastLogoutTime();
		if (_loginTime != 0 && _logoutTime != 0) {
			_onlineTime = _logoutTime - _loginTime;
		}
		this.setTotalMinute(this.getTotalMinute()
				+ (int) (_onlineTime / (1000 * 60)));
		// 发送快照到日志服务器 玩家退出日志
		Globals.getPlayerLogService().sendExitLog(this, player.exitReason);
		// 发送在线时长日志
		if (_loginTime != 0 && _logoutTime != 0) {
			try {
				Globals.getLogService().sendOnlineTimeLog(this,
						OnlineTimeLogReason.DEFAULT, null,
						player.getTodayOnlineTime(), this.getTotalMinute(),
						this.getLastLoginTime(),
						this.getLastLogoutTime());
				
				// LOG
				Globals.getLogService().sendLoginLog(this,
						LoginLogReason.LOGOUT, null,
						Globals.getTimeService().now(), this.getLastLoginIp(),
						this.getSceneId(), this.getTotalMinute(),
						LoginLogReason.LOGOUT.getReason());
			} catch (Exception e) {
				Loggers.charLogger.error(LogUtils.buildLogInfoStr(
						this.getUUID(), "记录玩家在线时长日志时出错"), e);
			}
		}
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	/**
	 * 得到最后一次登录ip
	 * 
	 * @return
	 */
	public String getLastLoginIp() {
		return (String) this.finalProps.getObject(RoleFinalProps.LAST_IP);
	}

	/**
	 * 设置最后一次登录IP
	 * 
	 * @param lastLoginIp
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.finalProps.setString(RoleFinalProps.LAST_IP, lastLoginIp);
		this.setModified();
	}

	/**
	 * 设置最后一次登录时间
	 * 
	 * @param lastLoginTime
	 */
	public void setLastLoginTime(Long lastLoginTime) {
		this.finalProps.setObject(RoleFinalProps.LAST_LOGIN, lastLoginTime);
		this.setModified();
	}

	/**
	 * 得到最后一次登录时间
	 * 
	 * @return
	 */
	public long getLastLoginTime() {
		return (Long) this.finalProps.getObject(RoleFinalProps.LAST_LOGIN);
	}

	public void setLastLastLoginTime(Long lastLastLoginTime) {
		this.finalProps.setObject(RoleFinalProps.LAST_LAST_LOGIN, lastLastLoginTime); 
	} 
	public long getLastLastLoginTime() {
		return (Long) this.finalProps.getObject(RoleFinalProps.LAST_LAST_LOGIN);
	}
	public String getLastLastLoginIp() {
		return (String) this.finalProps.getObject(RoleFinalProps.LAST_LAST_IP);
	}
	public void setLastLastLoginIp(String lastLoginIp) {
		this.finalProps.setString(RoleFinalProps.LAST_LAST_IP, lastLoginIp); 
	}
	/**
	 * 设置最后一次登出时间
	 * 
	 * @param lastLoginTime
	 */
	public void setLastLogoutTime(Long lastLogoutTime) {
		this.finalProps.setObject(RoleFinalProps.LAST_LOGOUT, lastLogoutTime);
	}

	/**
	 * 得到最后一次登出时间
	 * 
	 * @return
	 */
	public long getLastLogoutTime() {
		return (Long) this.finalProps
				.getObject(RoleFinalProps.LAST_LOGOUT);
	}

	/**
	 * 获得总在线时长 (分钟)
	 * 
	 * @return
	 */
	public int getTotalMinute() {
		return this.finalProps.getInt(RoleFinalProps.TOTAL_MINUTE);
	}

	/**
	 * 设置总在线时长
	 * 
	 * @param totalOnlineMinute
	 */
	public void setTotalMinute(int totalOnlineMinute) {
		this.finalProps.setInt(RoleFinalProps.TOTAL_MINUTE, totalOnlineMinute);
	}

	public HumanInitManager getInitManager() {
		return initManager;
	}

	/**
	 *   
	 */
	public HumanPropertyManager getPropertyManager() {
		return propertyManager;
	}

	/**
	 * 宠物管理器
	 * 
	 * @return
	 */
	public HumanPetManager getPetManager() {
		return petManager;
	}

	/**
	 * 获取行为记录管理器
	 * 
	 * @return
	 */
	public BehaviorManager getBehaviorManager() {
		return this.behaviorManager;
	}

	/**
	 * 得到充值管理器
	 * 
	 * @return
	 */
	public ChargeManager getChargeManager() {
		return this.chargeManager;
	}

	/**
	 * 副本信息管理器
	 * 
	 * @return
	 */
	public HumanRepInfoManager getHumanRepInfoManager() {
		return humanRepInfoManager;
	}

	@Override
	protected List<KeyValuePair<Integer, Integer>> changedNum() {
		// 保存数值类属性变化
		List<KeyValuePair<Integer, Integer>> intNumChanged = new ArrayList<KeyValuePair<Integer, Integer>>();

		// 处理 一二级属性
		if (this.getPropertyManager().isNumchanged()) {
			List<KeyValuePair<Integer, Integer>> _numChanged = this
					.getPropertyManager().getChangedNum();
			intNumChanged.addAll(_numChanged);
		}

		if (this.baseIntAProperties.isChanged()) {
			List<KeyValuePair<Integer, Integer>> changes = this.baseIntAProperties
					.getChangedNum();
			for (KeyValuePair<Integer, Integer> pair : changes) {
				intNumChanged.add(pair);
			}
		}

		// 处理 baseIntProps
		if (this.baseIntProperties.isChanged()) {
			List<KeyValuePair<Integer, Integer>> changes = this.baseIntProperties
					.getChangedNum();
			for (KeyValuePair<Integer, Integer> pair : changes) {
				intNumChanged.add(pair);
			}
		}
		if(this.skillBaseIntProperties.isChanged()){
			 KeyValuePair<Integer, Object>  changes[] = this.skillBaseIntProperties.getChanged();
			for (KeyValuePair<Integer, Object> pair : changes) {
				intNumChanged.add(new KeyValuePair<Integer, Integer>(pair.getKey(),(Integer)pair.getValue()));
			}
		}

		return intNumChanged;
	}

	@Override
	protected List<KeyValuePair<Integer, String>> changedStr() {
		// 保存字符串类属性变化
		List<KeyValuePair<Integer, String>> _strChanged = new ArrayList<KeyValuePair<Integer, String>>();
		if (this.getPropertyManager().isStrchanged()) {
			List<KeyValuePair<Integer, String>> s = this.propertyManager
					.getChangedString();
			_strChanged.addAll(s);
		}

		// 处理 baseStrProps
		if (this.baseStrProperties.isChanged()) {
			KeyValuePair<Integer, Object>[] v = this.baseStrProperties
					.getChanged();
			for (int i = 0; i < v.length; i++) {
				_strChanged.add(new KeyValuePair<Integer, String>(
						v[i].getKey(), v[i].getValue().toString()));
			}
		}
		if(this.skillBaseStrProperties.isChanged()){ 
			KeyValuePair<Integer, Object>[] v = this.skillBaseStrProperties
					.getChanged();
			for (int i = 0; i < v.length; i++) {
				_strChanged.add(new KeyValuePair<Integer, String>( v[i].getKey(), v[i].getValue()+""));
			}
		}
		return _strChanged;
	}

	// //////////////////////
	// 基于baseIntProperties or baseStrProperties各种属性的getter/setter（内置属性）
	// //////////////////////
 

	public int getGrowUpAtkAdd(int toGrow) {
		return HumanConstants.GROW_ADD_ATK; 
	}

	public int getGrowUpDefAdd(int toGrow) {
		return HumanConstants.GROW_ADD_DEF;
	}

	public int getGrowUpMaxHpAdd(int toGrow) {
		return HumanConstants.GROW_ADD_MAX_HP;
	}
	public int getGrowAtk(){
		return (int) Math.round(HumanConstants.GROW_ADD_ATK * this.getGrow());
	}
	public int getGrowDef(){
		return (int) Math.round(HumanConstants.GROW_ADD_DEF * this.getGrow());
	}
	public int getGrowMaxHp(){
		return (int) Math.round(HumanConstants.GROW_ADD_MAX_HP * this.getGrow());
	}
	public double getBuffAtk() { 
		return this.getPropertyManager().getBuffAtk();
	} 

	public void setBuffAtk(double buffAtk) { 
		this.getPropertyManager().setBuffAtk(buffAtk);
	} 

	public double getBuffDef() { 
		return this.getPropertyManager().getBuffDef();
	} 

	public void setBuffDef(double buffDef) {
		this.getPropertyManager().setBuffDef(buffDef);
	} 

	public double getBuffMaxHp() { 
		return this.getPropertyManager().getBuffMaxHp();
	} 

	public void setBuffMaxHp(double buffMaxHp) { 
		this.getPropertyManager().setBuffMaxHp(buffMaxHp);
	}
	public  void clearBuffValue(){
		this.getPropertyManager().getPropertyNormal().setBuffAtk(0);
		this.getPropertyManager().getPropertyNormal().setBuffDef(0);
		this.getPropertyManager().getPropertyNormal().setBuffMaxHp(0);
	}

	public void setX(int x) {
		this.baseIntProperties.set(RoleBaseIntProperties.LOCATIONX, x);
		// 不用立即保存到数据库
		// this.setModified();
	}

	public void setY(int y) {
		this.baseIntProperties.set(RoleBaseIntProperties.LOCATIONY, y);
		// 不用立即保存到数据库
		// this.setModified();
	}

	/**
	 * 是否在队伍中
	 * 
	 * @author xf
	 */
	public boolean isInTeam() {
		return teamInfo != null;
	}

	/**
	 * 返回队伍信息
	 * 
	 * @author xf
	 */
	public TeamInfo getTeamInfo() {
		return teamInfo;
	}

	public void setTeamInfo(TeamInfo t) {
		this.teamInfo = t;
	}

	/**
	 * 获取总充值数量
	 * 
	 * @return
	 */
	public int getTotalCharge() {
		return this.finalProps.getInt(RoleFinalProps.TOTAL_CHARGE);
	}

	/**
	 * 设置总充值数量
	 * 
	 * @param value
	 */
	public void setTotalCharge(int value) {
		this.finalProps.setInt(RoleFinalProps.TOTAL_CHARGE, value);
		this.onModified();
	}

	/**
	 * 获取当天的总充值数量
	 * 
	 * @return
	 */
	public int getTodayCharge() {
		return this.finalProps.getInt(RoleFinalProps.TODAY_CHARGE);
	}

	/**
	 * 设置当天的总充值数量
	 * 
	 * @param value
	 */
	public void setTodayCharge(int value) {
		this.finalProps.setInt(RoleFinalProps.TODAY_CHARGE, value);
		this.onModified();
	}

	/**
	 * 得到最后一次充值时间
	 * 
	 * @return
	 */
	public Long getLastChargeTime() {
		return (Long) this.finalProps
				.getObject(RoleFinalProps.LAST_CHARGE_TIME);
	}

	/**
	 * 设置最后一次充值时间
	 * 
	 * @param lastLoginTime
	 */
	public void setLastChargeTime(Long lastLogoutTime) {
		this.finalProps.setObject(RoleFinalProps.LAST_CHARGE_TIME,
				lastLogoutTime);
		this.setModified();
	}

	/**
	 * 得到最后一次到达vip等级的时间
	 * 
	 * @return
	 */
	public Long getLastVipTime() {
		return (Long) this.finalProps
				.getObject(RoleFinalProps.LAST_VIP_TIME);
	}

	/**
	 * 设置最后一次到达vip等级的时间
	 * 
	 * @param lastVipTime
	 */
	public void setLastVipTime(Long lastVipTime) {
		this.finalProps.setObject(RoleFinalProps.LAST_VIP_TIME, lastVipTime);
		this.setModified();
	}

	// 不需要调用onModified()的属性,也就是模板数据,主要为了前台差量更新

	/**
	 * 玩家是否在线的状态
	 * 
	 * @return
	 */
	public int getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(int onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	/*
	 * 设置职业的时候获取模版
	 */
	@Override
	public void setVocationType(VocationType v) {
		super.setVocationType(v);
		Map<Integer, HumanInitPropTemplate> tmps = Globals.getTemplateService()
				.getAll(HumanInitPropTemplate.class);
		initTemplate = tmps.get(this.getVocationType().getCode());

	}

	public HumanInitPropTemplate getInitTemplate() {
		return initTemplate;
	}

	public VocationSkill[] getVocationSkills() {
		return vocationSkills;
	}

	public void setVocationSkills(VocationSkill[] vocationSkills) {
		this.vocationSkills = vocationSkills;
	}

	public BattleForm getBattleForm() {
		return battleForm;
	}

	public void setBattleForm(BattleForm battleForm) {
		this.battleForm = battleForm;
	}

	/**
	 * 根据武将sn获得武将的武将技相关信息
	 * 
	 * @param player
	 * @param petSn
	 * @return
	 */
	public PetOrder getPetOrder(String petSn) {
		if (getUUID().equals(petSn)) {
			PetOrder petOrder = new PetOrder();
			petOrder.setPetSn(petSn);
			petOrder.setSkillSn(this.getPetSkill());
			// petOrder.setSkillLevel(getLevel());
			petOrder.setSkillLevel(1);
			return petOrder;
		} else {
			for (Pet pet : getPetManager().getPets()) {
				if (pet.getUUID().equals(petSn)) {
					PetOrder petOrder = new PetOrder();
					petOrder.setPetSn(petSn);
					petOrder.setSkillSn(pet.getPetSkill());
					petOrder.setSkillLevel(1);
					// petOrder.setSkillLevel(pet.getLevel());
					return petOrder;
				}
			}
		}
		return null;
	}

	/**
	 * 获得角色
	 * 
	 * @param petSn
	 * @return
	 */
	public Role getRole(String petSn) {
		if (getUUID().equals(petSn)) {
			return this;
		} else {
			return getPetManager().getPetByUuid(petSn);
		}
	}

	/** 判断人物或武将是否存在 */
	public boolean existPet(String petSn) {
		if (getUUID().equals(petSn)) {
			return true;
		} else {
			return getPetManager().getPetByUuid(petSn) != null;
		}
	}

	/**
	 * 获得用户在正常玩游戏时的状态
	 * 
	 * @return
	 */
	public synchronized int getGamingState() {
		return gamingState;
	}

	/**
	 * 设置用户在正常玩游戏时的状态
	 * 
	 * @param state
	 */
	public synchronized boolean setGamingState(GamingStateIndex state) {
		int ostate = this.gamingState;
		this.gamingState = state.getValue();

		if (state == GamingStateIndex.IN_BATTLE) {
			this.onBattle();
		}
		if (ostate != this.gamingState && player!=null) {
			// 将状态变化信息告诉场景内玩家
			this.player.sendInfoChangeMessageToScene();
		}
		// setModified();
		return true;
	}

	/**
	 * 添加一个用户在正常玩游戏时的状态
	 * 
	 * @param state
	 */
	public void addGamingState(GamingStateIndex state) {
		this.gamingState = this.gamingState | state.getValue();
	}

	/**
	 * 移除一个用户在正常玩游戏时的状态
	 * 
	 * @param state
	 */
	public void removeGamingState(GamingStateIndex state) {
		this.gamingState = this.gamingState ^ state.getValue();
	}

	/**
	 * 是否有某状态 
	 */
	public boolean containsGamingState(GamingStateIndex state) {
		return (this.gamingState & state.getValue()) > 0;
	}

	/**
	 * 开始进行战斗了
	 * 
	 * @author xf
	 */
	private void onBattle() {
		// 停止移动
		this.player.setToX(this.getX());
		this.player.setToY(this.getY());
	}

	public CoolDownManager getCooldownManager() {
		return cooldownManager;
	}

	public void setCooldownManager(CoolDownManager cooldownManager) {
		this.cooldownManager = cooldownManager;
	}

	@Override
	public int addHP(int hp) {
		hp = super.addHP(hp);
		if (hp < 0) {
			// 扣除血，发送事件通知
			if( this.lifeCycle.isActive()){
				HPReduceEvent event = new HPReduceEvent(this); 
				Globals.getEventService().fireEvent(event);
			} 
		}
		return hp;
	}

	@Override
	public void addMaxHp(double hp) {
		boolean bAdd = hp > 0;
		super.addMaxHp(hp);
		if(this.lifeCycle.isActive()){				
			if(this.getCurHp() > this.getMaxHp()){
				this.setCurHp(this.getMaxHp());
			}
			if (bAdd) {
					HPReduceEvent event = new HPReduceEvent(this); 
					Globals.getEventService().fireEvent(event);
			}
			
		}
	}

	public BattleForm getDefaultForm() {
		return battleForm;
	}

//	public void setDefaultForm(BattleForm defaultForm) {
//		this.defaultForm = defaultForm;
//	}

	public boolean isInRep() {
		if (this.getScene() == null) {
			return false;
		}
		if (this.getScene() instanceof Rep) {
			return true;
		}
		return false;
	}
	
	/**
	 *是否在扫荡副本
	 */
	public boolean isInRepAgainsg(){
		AgainstRepInfo r = Globals.getAgainstRepService().getByRoleUUID(roleUUID);
		if(r == null || !r.isBstart()){return false;}
		return true;
	}
	/**
	 * 是否在修炼
	 */
	public boolean isInXiulian(){
		XiulianInfo info = Globals.getHumanService().getHumanXiulianService().getByUUID(roleUUID);
		if(info == null || !info.isStart())return false;
		return true;
	}
	@Override
	protected int getMaxLevel() {
		return 100;
	}

	public Map<BuffBigType, Buffer> getBuffers() {
		return buffers;
	}
	public void removeBuff(BuffBigType type) {
		Buffer buffer = buffers.get(type); 
		if(buffer != null){ 
			player.sendMessage(buffer.generatorMessage(player.getHuman(), ModifyType.delete));
		}
	}
	/**
	 * 根据某个buff的效果类型取所有的buff
	 */
	public List<Buffer> getBufferByType(BufferType type) {
		List<Buffer> list = new ArrayList<Buffer>();
		for(Buffer b : this.buffers.values()){
			if(b.getBufferType() == type.getId()){
				list.add(b);
			}
		}
		return list;
	}

	/**
	 * 取得该角色的所有战斗力
	 * @author xf
	 */
	public int getAllZhandouLi(){
		double z = 0;
		for(Role p : this.getDefaultForm().getAllBattlePets()){
			z += p.getZhandouli();
		}
		return (int) Math.round(z);
	}
	@Override
	public boolean addExp(int exp) { 
		boolean rs = super.addExp(exp);
		if(exp != 0){
//			this.sendSystemMessage(Globals.getLangService().read(ItemLangConstants_20000.GET_EXP, exp));
			String content = Globals.getLangService().read(CommonLangConstants_10000.ADDPROP, exp,
					Globals.getLangService().read(CommonLangConstants_10000.EXP));
			this.player.sendHeadMessage(content, 0x2fd914);//#2fd914
		}
		return rs;
		
	}
	@Override
	public boolean levelUp(boolean changeExp){
		boolean rs = super.levelUp(changeExp);
		if(rs){
			//同步家族成员等级信息
			this.getFamilyManager().updateFamilyMemberLevel();
			//更新玩家兵法装备位
			this.getWarcraftInventory().updateWarcraftEquipLevel(this.getLevel(), null);
			//等级达到要求创建兵法信息
			if(this.getLevel() >= WarcraftDef.WARCRAFT_NEED_LEVEL && this.getWarcraftInventory().getWarcraftInfo() == null){
				this.getWarcraftInventory().createWarcraftInfo();
			}
			this.sendSystemMessage(Globals.getLangService().read(PlayerLangConstants_30000.ROLE_LEVEL_UP,
					this.getName()));
			
			CheckFunctionEvent e = new CheckFunctionEvent(this);
			Globals.getEventService().fireEvent(e);
			
			this.checkOnlinePrize(false);
			
			//GM平台
			long now = Globals.getTimeService().now();
			LogOp.log(LogOpChannel.UPGRADE, this.getUUID(), this.getPlayer().getRole().getName(), this.getLevel(), now, TimeUtils.formatYMDTime(now));
			
			//Log平台
			Globals.getLogService().sendLevelUpLog(this, LevelUpLogReason.LEVELUP, null, this.getLevel(), now, this.getTotalMinute());
		}
		
		
		
		
		return rs;
	}
	
	public void addPopularity(int popularity) {
		if (popularity > 0) {
			this.propertyManager.setPopularity(this.propertyManager.getPopularity() + popularity);
			//更新家族经验
			this.getFamilyManager().addFamilyExp(popularity);
			
			String content = Globals.getLangService().read(CommonLangConstants_10000.ADDPROP, popularity,
					Globals.getLangService().read(CommonLangConstants_10000.SHENGWANG));
			this.player.sendHeadMessage(content, 0x2eb2f6);//#2eb2f6
		}else{
			this.propertyManager.setPopularity(this.propertyManager.getPopularity() + popularity);
		}
	}
	
	/**
	 * 给所有战斗单元增加相同经验
	 * @author xf
	 */
	public void addExpToAllFormUnits(int exp){
		List<Role> roles = this.getDefaultForm().getAllBattlePets();
		for(Role role : roles){
			role.addExp(exp); 
		} 
	}
	
	public void setQuestDiary(QuestDiary questDiary) {
		this.questDiary = questDiary;
	}

	 

	@Override
	protected void initPropsFromTmp() {
		super.setCri(initTemplate.getInitCri());
		super.setShanbi(initTemplate.getInitShanbi());
		super.setShanghai(initTemplate.getInitShanghai());
		super.setMianshang(initTemplate.getInitMianshang());
		super.setFanji(initTemplate.getInitFanji());
		super.setMingzhong(initTemplate.getInitMingzhong());
		super.setLianji(initTemplate.getInitLianji());
		super.setRenXing(0);
 
		
		double atk = super.getRoleGrowAtk(this.getLevel())
				* (this.getGrow())
				+ this.initTemplate.getInitAtk();
		atk += Globals.getHumanService().getLevelAtk(this.getLevel()) * this.getRolePropertyFactorTemplate().getAtkVocationFactor();
		atk += Globals.getHumanService().getResearchTemplateByLevel(this.getAtkLevel()).getAddAtk();
		super.setAtk(atk);
		
		double def = super.getRoleGrowDef(this.getLevel())
				* (this.getGrow())
				+ this.initTemplate.getInitDef();
		def += Globals.getHumanService().getLevelDef(this.getLevel()) * this.getRolePropertyFactorTemplate().getDefVocationFactor();
		def += Globals.getHumanService().getResearchTemplateByLevel(this.getDefLevel()).getAddDef();
		super.setDef(def);
		
		double maxHp = super.getRoleGrowHp(this.getLevel())
				* (this.getGrow())
				+ this.initTemplate.getInitHp();
		maxHp += Globals.getHumanService().getLevelMaxHp(this.getLevel()) * this.getRolePropertyFactorTemplate().getHpVocationFactor();
		maxHp += Globals.getHumanService().getResearchTemplateByLevel(this.getMaxHpLevel()).getAddMaxHp();
		super.setMaxHp(maxHp);
		
//		double spd = this.getRolePropertyFactorTemplate().getBaseSpeed();
		double spd = this.initTemplate.getInitSpd();
		super.setSpd(spd);
		if(super.transferVocationStarTemplate != null && 
				OpenFunction.isopen(this.getPropertyManager().getOpenFunc(), OpenFunction.guanzhi)){
			super.addAtk(super.transferVocationStarTemplate.getAddAtk());
			super.addDef(super.transferVocationStarTemplate.getAddDef());
			super.addMaxHp(super.transferVocationStarTemplate.getAddHp());
		}
		// 角色的
		double zhandouli = (500 + this.getGrow() * this.getLevel())
				* (1 + (this.getLevel() - 1) / (this.getLevel() + 200));
		super.setZhandouli(zhandouli);
		
		super.setShortAtk(Globals.getHumanService().getResearchTemplateByLevel(this.getShortAtkLevel()).getAddShortAtk());
		super.setRemoteAtk(Globals.getHumanService().getResearchTemplateByLevel(this.getRemoteAtkLevel()).getAddRemoteAtk());
		super.setShortDef(Globals.getHumanService().getResearchTemplateByLevel(this.getShortDefLevel()).getAddShortDef());
		super.setRemoteDef(Globals.getHumanService().getResearchTemplateByLevel(this.getRemoteDefLevel()).getAddRemoteDef());
		
		super.setCooldownRound(initTemplate.getCooldownRound());
		super.setMaxMorale(initTemplate.getMaxMorale());
		
		//根据性别获取动画
		if (getSex() == Sex.MALE) {
			super.setCastAnim(initTemplate.getCastAnim());
			super.setAttackedAnim(initTemplate.getAttackedAnim());
			super.setVocationAnim(initTemplate.getVocationAnim());
		} else {
			super.setCastAnim(initTemplate.getCastAnimFemale());
			super.setAttackedAnim(initTemplate.getAttackedAnimFemale());
			super.setVocationAnim(initTemplate.getVocationAnimFemale());
		}
	}

	@Override
	protected List<KeyValuePair<Integer, Integer>> generatorIntegerInfomation(){
		List<KeyValuePair<Integer, Integer>> integerInfomations=super.generatorIntegerInfomation();
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.propertyManager.getPropertyNum()
						.getKeyByProp(HumanNumProperty.TITLE),this.propertyManager.getTitle()));
		return integerInfomations;
	}


	/**
	 * 当服务器被关闭时调用，只用来恢复数据
	 */
	public void onServerShutDown(){
		if(isInRep()){
			Globals.getRepService().onPlayerOffline(getPlayer(), (Rep) getScene());
		}
	}
	
	public AtomicBoolean getIsRequestQiecuo() {
		return isRequestQiecuo;
	}

	public void setIsRequestQiecuo(AtomicBoolean isRequestQiecuo) {
		this.isRequestQiecuo = isRequestQiecuo;
	}

	public String getQiecuoPlayerUUID() {
		return qiecuoPlayerUUID;
	}

	public void setQiecuoPlayerUUID(String qiecuoPlayerUUID) {
		this.qiecuoPlayerUUID = qiecuoPlayerUUID;
	}
	
	/**
	 * 取得vip模版
	 * @author xf
	 */
	public VipToVitTemplate getVipTemplate(){
		int vip = this.propertyManager.getVip();
		return Globals.getHumanService().getVipTemplate(vip);
		
	}

	public FriendManager getFriendManager() {
		return friendManager;
	}

	public Family getFamily() {
		return family;
	}

	public void setFamily(Family family) {
		this.family = family;
	}

	public FamilyManager getFamilyManager() {
		return familyManager;
	}

	public void setFamilyManager(FamilyManager familyManager) {
		this.familyManager = familyManager;
	}

	public GiftBagContianer getGiftBagContianer() {
		return giftBagContianer;
	}

	public void setGiftBagContianer(GiftBagContianer giftBagContianer) {
		this.giftBagContianer = giftBagContianer;
	}

	public WarcraftInventory getWarcraftInventory() {
		return warcraftInventory;
	}

	public void setWarcraftInventory(WarcraftInventory warcraftInventory) {
		this.warcraftInventory = warcraftInventory;
	}

	public HumanProbInfoManager getHumanProbInfoManager() {
		return humanProbInfoManager;
	}
	
	public TowerInfoManager getTowerInfoManager() {
		return towerInfoManager;
	}

	public TreeInfoManager getTreeInfoManager() {
		return treeInfoManager;
	}

	public TreeWaterManager getTreeWaterManager() {
		return treeWaterManager;
	}

	public HumanRobberyInfo getRobberyInfo() {
		return robberyInfo;
	}

	public void setRobberyInfo(HumanRobberyInfo robberyInfo) {
		this.robberyInfo = robberyInfo;
	}
	public int getAllianceId(){
		return this.getCamp().getCode();
	}
	/**
	 * 取得玩家当前的vip等级
	 * @return
	 */
	public int getVipLevel(){
		/**
		 * 当玩家有vip体验buff时取vip级别vip体验等级高的作为当前vip等级
		 */
		int vip = this.getPropertyManager().getVip();
		int buffVip = this.getPropertyManager().getBuffVip();
		return vip >= buffVip ? vip : buffVip;
	}

	public HumanRepAgaisntInfo getRepAgainstInfo() {
		return repAgainstInfo;
	}

	public void setRepAgainstInfo(HumanRepAgaisntInfo repAgainstInfo) {
		this.repAgainstInfo = repAgainstInfo;
	}

	public PromptButtonManager getPromptButtonManager(){
		return this.promptMessageManager;
	}
}
