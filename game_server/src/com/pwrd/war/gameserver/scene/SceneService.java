package com.pwrd.war.gameserver.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.annotation.SyncIoOper;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.msg.sys.ScheduledMessage;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.config.GameServerConfig;
import com.pwrd.war.gameserver.common.db.GameDaoService;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.common.i18n.constants.SceneLangConstants_60000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.monster.MonsterService;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerState;
import com.pwrd.war.gameserver.player.msg.GCNotifyException;
import com.pwrd.war.gameserver.player.msg.SysEnterScene;
import com.pwrd.war.gameserver.player.msg.SysLeaveScene;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;
import com.pwrd.war.gameserver.scene.listener.AfterSwitchScene;
import com.pwrd.war.gameserver.scene.listener.CheckVitListener;
import com.pwrd.war.gameserver.scene.listener.SceneListener;
import com.pwrd.war.gameserver.scene.manager.SceneMonsterManager;
import com.pwrd.war.gameserver.scene.msg.GCPlayerChangePos;
import com.pwrd.war.gameserver.scene.msg.GCTeamSwitchSceneLine;
import com.pwrd.war.gameserver.scene.vo.SceneInfoVO;
import com.pwrd.war.gameserver.scene.vo.SceneInfoVO.SceneType;
import com.pwrd.war.gameserver.team.TeamInfo;

/**
 * 场景服务
 *
 */
public class SceneService implements InitializeRequired {
	
	/** 非法的场景Id */
	public static final int INVALID_SCENEID = -1;
	
//	/** 场景心跳 */
//	protected HeartbeatThread sceneTaskScheduler;
	
	/** 场景Map,key为场景id-线数编号 */
	protected Map<String, Scene> sceneMap;
	
	/** 场景List */
	protected volatile List<Scene> sceneList;
	
	/** 每个地图id对应的线数总数 **/
	protected Map<String, Integer> sceneLine;
	
	/** 每个场景对应的锁 **/
	protected Map<String, Lock> sceneLock;
	
	/** 系统配置 */
	protected final GameServerConfig config;


	protected Map<String, SceneRunner> sceneRunners;
	protected OnlinePlayerService onlinePlayerService;

	/** 怪物服务 **/
	protected MonsterService monsterService;
	/** 加载地图配置信息服务 **/
	protected LoadSceneService loadSceneService;
//	/** 模版服务 */
//	private TemplateService templateService;
//	/** UUID 服务 */
//	private UUIDService uuidService;
	/** 游戏数据访问层对象 */
	protected GameDaoService daoService;
	
	/** 清理空闲分线的线程 **/
	protected Thread clearLineThread;
	protected Object lock=new Object();
	
	public SceneService(LoadSceneService loadSceneService, 
		OnlinePlayerService onlinePlayerService, 
		GameDaoService daoService, 
		GameServerConfig config,
		MonsterService monsterService) {
		sceneMap = new ConcurrentHashMap<String, Scene>();
		sceneList = new ArrayList<Scene>();
		sceneRunners = new ConcurrentHashMap<String, SceneRunner>();
		this.onlinePlayerService = onlinePlayerService;
		this.loadSceneService = loadSceneService;
		this.daoService = daoService;
		this.config = config;	
		
		this.monsterService = monsterService;
		sceneLine = new ConcurrentHashMap<String, Integer>();
		sceneLock = new ConcurrentHashMap<String, Lock>(); 
	}
	

	@Override
	public void init() {
		// 获取场景模版字典
		Map<String, SceneInfoVO> sceneTmplMap = this.loadSceneService.getAllScenes();
		// 场景事件监听
		List<SceneListener> listeners = Arrays.asList(new SceneListener[] {
			new SceneSeasonEffectListener(), 
//			new SceneGuideListener(), 
			new CheckVitListener(),
		});

	

		for (String id : sceneTmplMap.keySet()) {
			// 获取场景模版
			SceneInfoVO sceneTmpl = sceneTmplMap.get(id);
			//只创建普通地图
			if(sceneTmpl.getSceneType() == SceneType.NORMAL){
				// 初始化场景
				this.initScene(sceneTmpl, listeners);
			}
		}

		Collections.sort(this.sceneList, new Comparator<Scene>() {
			@Override
			public int compare(Scene s0, Scene s1) {
				if (s0.getType() != s1.getType()) {
					// 以城市类型排序
					return s1.getType() - s1.getType();
				} else {
					// 以模版 Id 排序
					return s0.getSceneId().compareTo(s1.getSceneId());
				}
			}
		});
	}
	/**
	 * 根据地图ID创建地图
	 * @author xf
	 */
	public Scene createScene(String sceneId){
		SceneInfoVO vo = this.loadSceneService.getSceneById(sceneId);
		return this.initScene(vo,  Arrays.asList(new SceneListener[]{}));
	}
	/**
	 * 根据模版初始化场景
	 * 
	 * @param template
	 * @param listeners 
	 * 
	 */
	@SyncIoOper
	private Scene initScene(SceneInfoVO vo, List<SceneListener> listeners) {
		if (vo == null) {
			return null;
		}

//		SceneDao sceneDao = this.daoService.getSceneDao();
//		int sceneId = vo.getSceneId();

		// 创建场景对象
		Scene sceneObj = SceneFactory.createScene(this, vo, onlinePlayerService, monsterService);
		
		// 获取场景实体
//		SceneEntity entity = sceneDao.getSceneByTemplateId(sceneId);

//		if (entity == null) {
//			entity = sceneObj.toEntity();
////			entity.setId(this.uuidService.getNextUUID(UUIDType.SCENE));
//		 
//
//			// 将场景实体保存到数据库
//			sceneDao.save(entity);
//		}
//
//		// 反序列化 & 初始化场景
//		sceneObj.fromEntity(entity);
		

		for (SceneListener listener : listeners) {
			// 注册事件监听
			sceneObj.registerListener(listener);
		}

		// 初始化玩家角色列表
//		this.initSceneHumanList(sceneObj);

		this.addScene(sceneObj);
		return sceneObj;
	}

	 
	/**
	 * 初始化场景中的玩家角色列表
	 * 
	 * @param sceneObj
	 */
	private void initSceneHumanList(Scene sceneObj) {
		if (sceneObj == null) {
			return;
		}

//		HumanDao humanDao = this.daoService.getHumanDao();

		// 获取玩家角色实体
//		List<HumanEntity> humanEntityList = humanDao.queryHumansBySceneId(
//			sceneObj.getSceneId());

//		if (humanEntityList != null) {
//			for (HumanEntity humanEntity : humanEntityList) {
//				// 添加玩家角色 Id
//				sceneObj.addHumanUUId(humanEntity.getId(), humanEntity.getDistrict());
//			}
//		}
	}

	/**
	 * 根据场景id和当前的分线编号，获取存储的场景id
	 * @author xf
	 */
	private String getSavedSceneId(String sceneId, int lineNo){
		return sceneId + "-" +lineNo;
	}
	/**
	 * 获取指定场景对象
	 * 
	 * @param sceneId
	 *            场景ID
	 * @return 如果不存在该ID的场景,则返回null
	 * @exception ConcurrentModificationException
	 *                如果不是该场景的线程调用该方法
	 */
	public Scene getScene(String sceneId, int lineNo) {
		return sceneMap.get(this.getSavedSceneId(sceneId,lineNo));
	}
	
	/**
	 * 取得某地图的所有场景，包括所有分线
	 * @author xf
	 */
	public List<Scene> getScene(String sceneId) {
		Integer line = sceneLine.get(sceneId);
		if(line == null || line == 0)return new ArrayList<Scene>();
		List<Scene> list = new ArrayList<Scene>();
		for(int i = 1; i <= line ;i++){
			list.add(sceneMap.get(this.getSavedSceneId(sceneId,i)));
		}
		return list; 
	}
	
	/**
	 * @warning 如果有地方同时使用了scenes和sceneRunners，需要先获得锁，
	 *          再进行操作，因为存在只更新了scenes，而没更新sceneRunners的糟糕情况，
	 *          需要保证它们更新的原子性。目前绝大多数对scenes和sceneRunners的引用
	 *          都没有考虑该问题,这是因为他们没有同时使用这两个集合。
	 * 
	 * @param scene
	 */
	protected void addScene(Scene scene) {
		synchronized (lock) {
			sceneMap.put(this.getSavedSceneId(scene.getSceneId(), scene.getLine()), scene);
			sceneRunners.put(this.getSavedSceneId(scene.getSceneId(), scene.getLine()), new SceneRunner(scene));
			sceneList.add(scene);
		}
	}

	/**
	 * @warning 与{@link SceneService#addScene(Scene)}类似，需要确保
	 *          在同时修改scenes和sceneRunners时加锁。
	 * 
	 * @param sceneId
	 */
	public void removeScene(String sceneId, int lineNo) {
		synchronized (lock) {
			Scene removedScene = sceneMap.remove(this.getSavedSceneId(sceneId, lineNo));
			sceneRunners.remove(this.getSavedSceneId(sceneId, lineNo));
			sceneList.remove(removedScene);
			removedScene.destroy();
		}
	}
	/**
	 * 删除所有地图，
	 * @author xf
	 */
	public void removeScene(String sceneId) {
		synchronized (lock) {
			Integer line = sceneLine.get(sceneId);
			if(line == null || line == 0)return ;
			for(int i = 1; i <= line ;i++){
				Scene removedScene = sceneMap.remove(this.getSavedSceneId(sceneId, i));
				removedScene.setDestroy();//设置状态，不再心跳，否则可能出错
				sceneRunners.remove(this.getSavedSceneId(sceneId, i));
				sceneList.remove(removedScene);
				removedScene.destroy();
			}
			this.sceneLock.remove(sceneId);
			this.sceneLine.remove(sceneId);
			SceneMonsterManager.realeasSharedMonster(sceneId);
		}
	}
	/**
	 * @warning 非线程安全
	 * @return
	 */
	public List<Scene> getAllScenes() {
		return this.sceneList;
	}
	/**
	 * 玩家切换地图，返回目标地图的切换点信息
	 * @author xf
	 */
	public boolean onPlayerSwitchScene(final Player player, String switchId, int lineNo){
		if(player.getHuman().isInRepAgainsg()){
			player.sendErrorPromptMessage(PlayerLangConstants_30000.STOP_AGAINST_FIRST);
			return false;
		}
		boolean rs = player.getHuman().setGamingState(GamingStateIndex.IN_SWITCHSCENE);
		if(!rs){ 
			player.getHuman().setGamingState(GamingStateIndex.IN_NOMAL);//切换失败继续返回gaming状态
			player.sendMessage(new GCNotifyException(SceneLangConstants_60000.ENTER_SCENE_FAIL, 
					Globals.getLangService().read(SceneLangConstants_60000.ENTER_SCENE_FAIL)));
			return false;
		}
		
		Scene scene = player.getHuman().getScene();
		if(scene == null){
			Loggers.msgLogger.error("不在场景中不能切换场景");
			player.getHuman().setGamingState(GamingStateIndex.IN_NOMAL);//切换失败继续返回gaming状态
			return false; 
		}
		SceneInfoVO.SwitchInfo target = scene.getSceneInfoVO().switchScene(
				player.getHuman().getX(), player.getHuman().getY(), switchId);
		if(target == null){
			Loggers.mapLogger.error(LogUtils.buildLogInfoStr(player
					.getRoleUUID(), "目标场景id或坐标不正确"));

			player.getHuman().setGamingState(GamingStateIndex.IN_NOMAL);//切换失败继续返回gaming状态
			return false;
		}else{
			String sceneId = target.nextSceneId;//目标地图。把11001变成11000
			SceneInfoVO sceneVo = this.loadSceneService.getSceneById(sceneId);
			//找到目标地图的切换点
			SceneInfoVO.SwitchInfo s = sceneVo.getNextMapId().get(target.nextId); 
			if(s == null){
				player.getHuman().setGamingState(GamingStateIndex.IN_NOMAL);//切换失败继续返回gaming状态
				return false;
			}
			//离开场景后调用

			//先将玩家离开当前地图
			this.onPlayerLeaveScene(player, 
					new AfterSwitchScene(s.toX, s.toY, 
							switchId, target.nextSceneId, target.nextId, lineNo)); 
			return true;
		} 
	}
	
	/**
	 * 切换地图分线
	 * @author xf
	 */
	public boolean onPlayerSwitchSceneLine(final Player player, String sceneId, int lineNo){	
		boolean rs = player.getHuman().setGamingState(GamingStateIndex.IN_SWITCHSCENE);
		if(!rs){ 
			player.getHuman().setGamingState(GamingStateIndex.IN_NOMAL);//切换失败继续返回gaming状态 
			player.sendMessage(new GCNotifyException(SceneLangConstants_60000.ENTER_SCENE_FAIL, 
					Globals.getLangService().read(SceneLangConstants_60000.ENTER_SCENE_FAIL)));
			return false;
		}
		Scene scene = player.getHuman().getScene();
		if(!StringUtils.equals(sceneId, scene.getSceneId())){
			Loggers.mapLogger.error(LogUtils.buildLogInfoStr(player
					.getRoleUUID(), "目标场景id或坐标不正确"));
			player.getHuman().setGamingState(GamingStateIndex.IN_NOMAL);//切换失败继续返回gaming状态 
			return false;
		}
		int maxLine = this.getSceneLine().get(sceneId);
		if(lineNo > maxLine){
			Loggers.mapLogger.error(LogUtils.buildLogInfoStr(player
					.getRoleUUID(), "目标分线错误！"));
			player.getHuman().setGamingState(GamingStateIndex.IN_NOMAL);//切换失败继续返回gaming状态 
			return false;
		}
		//离开场景后调用
		class T implements PlayerLeaveSceneCallback{
			int lineNo;
			String sceneId; 
			public T( String sceneId, int lineNo){
				this.sceneId = sceneId; 
				this.lineNo = lineNo;
			}
			@Override
			public void afterLeaveScene(Player player) { 
				player.setTargetSceneId(sceneId);//目标的切换点id
				Loggers.msgLogger.info("成功切换到地图分线："+sceneId+","+lineNo);
//				GCSwitchSceneLine msg = new GCSwitchSceneLine();
//				msg.setSceneId(nextSceneId);
//				player.sendMessage(msg);
				SceneService.this.onPlayerEnterScene(player, lineNo);
				
				//TODO队员
				if(player.getHuman().isInTeam()){
					TeamInfo t = player.getHuman().getTeamInfo();
					if(t.isLeader(player.getHuman())){
						//TODO 告诉队员开始切换场景到目标线
						//同时队员开始离开当前场景并且切换到指定线
						for(Human p : t.getTeamer()){
							GCTeamSwitchSceneLine msg = new GCTeamSwitchSceneLine();
							msg.setLineNo(lineNo);
							msg.setSceneId(sceneId);
							p.sendMessage(msg);
							
							SceneService.this.onPlayerSwitchSceneLine(p.getPlayer(), sceneId, lineNo);
						}
					}
				}
			} 
		}
		//先将玩家离开当前地图
		this.onPlayerLeaveScene(player, new T(sceneId, lineNo)); 
		return true;
	}
	
	/**
	 * 玩家进入场景
	 * 
	 * @param player
	 * @return
	 */
	public boolean onPlayerEnterScene(final Player player, int line) {
		
		//判断当前地图是否可以到达目标地图		
		String sceneId = player.getTargetSceneId();
		if (StringUtils.isEmpty(sceneId)) {
			Loggers.mapLogger.error(LogUtils.buildLogInfoStr(player
					.getRoleUUID(), "目标场景id或坐标不正确"));
			return false;
		}
			 
		Scene scene = this.getTargetScene(sceneId, line); 
		if (scene == null) {
			//如果目标地图属于非普通地图，返回默认地图
			SceneInfoVO vo = this.loadSceneService.getSceneById(sceneId);
			if(vo == null || vo.getSceneType() == SceneInfoVO.SceneType.NORMAL){
				Loggers.mapLogger.error(LogUtils.buildLogInfoStr(player
					.getRoleUUID(), "场景id：" + sceneId + "不存在"));
				return false;
			}
			if(vo.getSceneType() != SceneInfoVO.SceneType.NORMAL){
				String fsceneId = this.getFirstCityId(player.getHuman());
				player.setTargetSceneId(fsceneId);

				player.getHuman().setSceneId(fsceneId);
				SceneInfoVO s = loadSceneService.getSceneById(fsceneId);
				player.getHuman().setX(s.getStartX());
				player.getHuman().setY(s.getStartY());
				
				scene = this.getTargetScene(fsceneId, 1); 
				if(scene == null){
					return false;
				}
			}else{
				return false;
			}
		}
		// 标记此玩家已进入场景，在场景线程将玩家加入场景中之前如果收到
		// 玩家下线的消息，则向场景发送移除此玩家的消息
		player.getHuman().setScene(scene);
		SysEnterScene esm = new SysEnterScene(player.getId(), scene.getSceneId(), scene.getLine());
		scene.putMessage(esm);
		return true;
	}
	/**
	 * 取得可进去的线号，如果都满了，则返回当前最大线号+1
	 * @author xf
	 */
	public int getAvailableLine(String sceneId){
		Integer line = this.getSceneLine().get(sceneId);
		if(line == null) return -1;
		for(int i = 1; i <= line; i++){
			Scene scene = this.getScene(sceneId, i);
			Loggers.msgLogger.info("地图"+scene.getSceneId()+"线"+i+"，人="+scene.getPlayerManager().getPlayerNum()); 
			if(scene.getSceneInfoVO().getMaxPlayer() > scene.getPlayerManager().getPlayerNum()){
				//放到此线
				return i;
			}			 
		}
		return line + 1;//需要新开辟线路
	}
	/**
	 * 当进入地图或者切换地图时选择一个可以进入的分线地图
	 * @author xf
	 */
	private Scene getTargetScene(String sceneId, int toLine){
		Scene targetScene = null;
		Lock lock = this.getSceneLock().get(sceneId); 
		if(lock == null){
			return null;
		}
		lock.lock();
		try{
			Integer line = this.getSceneLine().get(sceneId);
			if(toLine > line + 1)toLine = 1;//如果仅是加1表示新开线，其他的暂时先放1线吧
			if(toLine >= 1){
				//指定线
				targetScene =  this.getScene(sceneId, toLine);
				if(targetScene == null){
					//开辟新线
					line++;
					this.getSceneLine().put(sceneId, line);
					targetScene = SceneFactory.createScene(this, this.loadSceneService.getSceneById(sceneId),
							this.onlinePlayerService, monsterService);
					this.addScene(targetScene);
				}
				targetScene.getPlayerManager().addAPlayer(); 
			}else{
				//逐个分配
				for(int i = 1; i <= line; i++){
					Scene scene = this.getScene(sceneId, i);
					Loggers.msgLogger.info("地图"+scene.getSceneId()+"线"+i+"，人="+scene.getPlayerManager().getPlayerNum()); 
					if(scene.getSceneInfoVO().getMaxPlayer() > scene.getPlayerManager().getPlayerNum()){
						//放到此线
						targetScene = scene;
						break;
					} 
				}
				if(targetScene == null){
					//开辟新线
					line++;
					this.getSceneLine().put(sceneId, line);
					targetScene = SceneFactory.createScene(this, this.loadSceneService.getSceneById(sceneId),
							this.onlinePlayerService, monsterService);
					this.addScene(targetScene);
				}
				targetScene.getPlayerManager().addAPlayer();
			} 
		}finally{
			lock.unlock();
		}
		return targetScene;
	}
	/**
	 * 玩家离开场景
	 * 
	 * @param player
	 * @param afterPlayerLeave
	 */
	public boolean onPlayerLeaveScene(Player player, PlayerLeaveSceneCallback afterPlayerLeave) {
		Scene scene = player.getHuman().getScene();
		if (scene == null) {
			Loggers.mapLogger.warn(LogUtils.buildLogInfoStr(player
					.getRoleUUID(), "sceneId:" + player.getSceneId()
					+ " not exist"));
			return false;
		}
		if(!player.getHuman().containsGamingState(GamingStateIndex.IN_SWITCHSCENE)
				&& player.getState() != PlayerState.logouting){//切换场景时不需要设置为leaving
			player.setState(PlayerState.leaving);
		}
		SysLeaveScene esm = new SysLeaveScene(player.getId(), afterPlayerLeave);
		scene.putMessage(esm);		
		
		return true;
	}

	/**
	 * 线程安全的
	 * 
	 * @return
	 */
	public List<SceneRunner> getAllSceneRunners() {
		List<SceneRunner> runnerList = new ArrayList<SceneRunner>();
		for (SceneRunner runner : sceneRunners.values()) {
			runnerList.add(runner);
		}
		return runnerList;
	}
	
	/**
	 * 移除场景中所有的玩家，在服务器断线或停机时调用，此处在主线程中直接操作场景
	 */
	public void removeAllPlayers() {
		for (Scene scene : sceneMap.values()) {
			Collection<Integer> playerIds = scene.getPlayerManager().getPlayerIds();
			for (Integer playerId : playerIds) {
				Player player = Globals.getOnlinePlayerService().getPlayerByTempId(playerId);
				SceneHandlerFactory.getHandler().handleLeaveScene(player);
			}
		}
	}
	
	public void start() {
		Loggers.gameLogger.info("begin start heartBeatThread");
//		sceneTaskScheduler = new HeartbeatThread(config.getHeartbeatThreadNum());
//		sceneTaskScheduler.start();
		
		
		Loggers.gameLogger.info("end start heartBeatThread");
	}

	public void stop() {
		Loggers.gameLogger.info("begin stop heartBeatThread");
//		sceneTaskScheduler.shutdown();
		Loggers.gameLogger.info("end stop heartBeatThread");

	}
	/**
	 * 启动定时清理空闲线路的定时器
	 * @author xf
	 */
	public void startCheckLine(){
		//发送一个定时的清理空闲闲的任务的消息
		Globals.getScheduleService().scheduleWithFixedDelay(
				new ScheduledMessage(Globals.getTimeService().now()) { 
					@Override
					public void execute() { 
						IIoOperation op = new IIoOperation() { 
							public int doStop() { 
								return STAGE_STOP_DONE;
							}  
							public int doStart() { 
								return STAGE_START_DONE;
							} 
							public int doIo() {
								SceneService.this.clearLine();
								return STAGE_IO_DONE;
							}
						};
						Globals.getAsyncService().createOperationAndExecuteAtOnce(op);
					}
				} 
				, 0, 10000);
	}
	/**
	 * 检查删除分线
	 * @author xf
	 */
	public void clearLine(){
		for(Map.Entry<String, Integer> e : this.sceneLine.entrySet()){
			String sceneId = e.getKey();
			int num = e.getValue();
			//检查最大的线人是否为空，空则删除该线。
			Scene scene = this.getScene(sceneId, num); 
			if(scene.getPlayerManager().getMirrorPlayerNum() == 0){
				//没人 了
				if(num > 1){
					this.removeScene(sceneId, num);
					num--;
					e.setValue(num);
					Loggers.msgLogger.info("地图"+sceneId+"删除一条线，当前最大数为"+num);
				}
			}
		}
		Loggers.msgLogger.info("在线总人数为："+this.onlinePlayerService.getOnlinePlayerCount());
	}
	
	/**
	 * 获取第一次登陆时的城市 Id
	 * 
	 * @param human 
	 * @return
	 * 
	 */
	public String getFirstCityId(Human human) {
		// 第一次登陆的城市
		Scene firstCity = null;
		
		if (firstCity == null) {
			return "110000";
		} else {
			return firstCity.getSceneId();
		}
	}


	public Map<String, Integer> getSceneLine() {
		return sceneLine;
	} 
	public LoadSceneService getLoadSceneService() {
		return loadSceneService;
	} 
	public Map<String, Lock> getSceneLock() {
		return sceneLock;
	}
	
	/**
	 * 死亡后复活进入场景
	 * @author xf
	 */
	public void onRelive(final Player player, String sceneId, int x, int y){
		if(!StringUtils.isEmpty(sceneId)){
			//是否当前地图
			if(StringUtils.equals(player.getHuman().getSceneId(), sceneId)){
				//告诉场景所有人它左边变换了
				GCPlayerChangePos pos = new GCPlayerChangePos();
				pos.setUuid(player.getRoleUUID());
				pos.setX(x);
				pos.setY(y);
				player.getHuman().getScene().getPlayerManager().sendGCMessage(pos, null);
				//改变坐标
				player.getHuman().setX(x);
				player.getHuman().setY(y);
				player.getHuman().snapChangedProperty(true);
				return;
			}

			//先将玩家离开当前地图
			player.getHuman().setGamingState(GamingStateIndex.IN_SWITCHSCENE);
			this.onPlayerLeaveScene(player, new AfterSwitchScene(x, y, sceneId)); 
		}
	}

 

 
}
