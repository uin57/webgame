package com.pwrd.war.gameserver.player;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;

import com.google.common.collect.Maps;
import com.pwrd.war.common.NonThreadSafe;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.common.exception.CrossThreadException;
import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.Camp;
import com.pwrd.war.gameserver.common.event.PlayerOffLineEvent;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.common.unit.GameUnitList;
import com.pwrd.war.gameserver.player.async.ReportLogout2Local;
import com.pwrd.war.gameserver.player.async.SavePlayerRoleOperation;
import com.pwrd.war.gameserver.rep.Rep;
import com.pwrd.war.gameserver.scene.SceneService;
import com.pwrd.war.gameserver.startup.GameServerRuntime;

/**
 * 在线玩家管理器
 * 
 * 
 */
public class OnlinePlayerService implements NonThreadSafe {
	/** 最多同时在线的人数 */
	private int maxPlayerNum;
	/** 维护当前Server所有在线玩家实例 */
	private GameUnitList<Player> onlinePlayers;
	/** 在线玩家列表，方便查询，key：玩家当前角色UUID，value：玩家引用 */
	private Map<String, Player> onlinePlayersMap;
	
	/** 在线玩家的会话管理 */
	private Map<ISession,Player> sessionPlayers;
	
	/** 登录用户集合 <String passportId,Player loginUser> */
	private Map<String, Player> passportIdPlayers;
	/** 登录用户集合 <String roleName, Player loginUser> */
	private Map<String, Player> roleNamePlayers;
	/** 阵营玩家集合 ,List为roleUUID**/
	private Map<Camp, Set<String>> campPlayers;
	
	/** 即将登录，处于等待被顶下线的用户集合 **/
	private Map<String, Player> willAuthPlayers;
	
	private ChannelService channelService;
	
	/** 管理器所属线程Id，为主线程Id */
	private long threadId;
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();
	/** */
	public static final Logger logger = Loggers.playerLogger;

	/**
	 * 初始化在线玩家实例数组
	 * 
	 * @param maxPlayerNum
	 *            最多同时在线的人数
	 */
	public OnlinePlayerService(int maxPlayerNum) {
		this.maxPlayerNum = maxPlayerNum;
		onlinePlayers = new GameUnitList<Player>(maxPlayerNum);
		onlinePlayersMap = new ConcurrentHashMap<String, Player>(maxPlayerNum);
		passportIdPlayers = new ConcurrentHashMap<String, Player>();
		roleNamePlayers = new ConcurrentHashMap<String, Player>();
		sessionPlayers = Maps.newConcurrentHashMap();
		campPlayers = new ConcurrentHashMap<Camp, Set<String>>();
		for(Camp camp : Camp.values()){
			campPlayers.put(camp, new HashSet<String>());
		}
		willAuthPlayers = new ConcurrentHashMap<String, Player>();
		
		channelService = new ChannelService();
	}
	
 
	/**
	 * 根据 passportId 查找在线用户
	 * 
	 * @param passportId
	 * @return
	 */
	public Player getPlayerByPassportId(String passportId) {
		return passportIdPlayers.get(passportId);
	}

	/**
	 * 根据玩家角色uuid获得玩家对象的引用
	 * 
	 * @param roleUUID
	 *            玩家当前角色UUID
	 * @return
	 */
	public Player getPlayerById(String roleUUID) {
		return onlinePlayersMap.get(roleUUID);
	}
	
	/**
	 * 根据玩家的session获得玩家对象的引用
	 * 
	 * @param session
	 * @return
	 */
	public Player getPlayer(ISession session) {
		if(session == null)
		{
			return null;
		}
		return sessionPlayers.get(session);
	}

	/**
	 * 根据玩家的角色名称获得玩家对象的引用
	 * 
	 * @param roleName
	 * @return
	 * 
	 */
	public Player getPlayer(String roleName) {
		return roleNamePlayers.get(roleName);
	}

	/**
	 * 建立连接时建立Session与Player的对应关系
	 * @param session
	 * @param user
	 */
	public void putPlayer(ISession session, Player user)
	{
		sessionPlayers.put(session, user);
	}
	
	/**
	 * 将在线角色名称保存到map
	 * @param roleName
	 * @param user
	 */
	public void putPlayer(String roleName, Player user)
	{
		roleNamePlayers.put(roleName, user);
		//TODO 在此处，建立玩家的阵营
		if(user.getHuman() != null){
			Camp camp = user.getHuman().getCamp();
			if(campPlayers.containsKey(camp)){
				campPlayers.get(camp).add(user.getRoleUUID());
			}
		}
	}
	
	/**
	 * 获取阵营在线玩家id列表
	 * @author xf
	 */
	public Set<String> getCampOnlinePlayers(Camp camp){
		return campPlayers.get(camp);
	}
	/**
	 * 添加一个即将登录的玩家
	 * @author xf
	 */
	public void addWillAuthPlayer(Player player){
		willAuthPlayers.put(player.getPassportId(), player);
	}
	/**
	 * 获取并且删除一个即将登录的玩家
	 * @author xf
	 */
	public Player getAndRemoveWillAuthPlayer(String passportId){
		return willAuthPlayers.remove(passportId);
	}
	

	public int getOnlinePlayerCount() {
		return onlinePlayers.size();
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public long getThreadId() {
		return threadId;
	}

	/**
	 * 玩家进入当前服务器
	 * 
	 * @param player
	 * @param roleUUID
	 *            玩家当前角色的UUID
	 */
	public boolean onPlayerEnterServer(Player player, String roleUUID) {
		if (!this.addPlayer(player)) {
			return false;
		}
		onlinePlayersMap.put(roleUUID, player);
		passportIdPlayers.put(player.getPassportId(), player);
		return true;
	}


	/**
	 * 遍历维护在线玩家实例的数组，找到数组中第一 个引用为<code>null</code>的 索引，将当前<code>player</code>
	 * 对象的引用存储到数组的该位置
	 * <p>
	 * 注意：<b>此方法只能在主线程中调用</b>，否则会 抛出异常{@link CrossThreadException}
	 * 
	 * @param player
	 * @return
	 */
	private boolean addPlayer(Player player) {
		Assert.notNull(player);
		checkThread();
		if (onlinePlayers.size() >= maxPlayerNum) {
			logger.info("Online player number reaches the upper limit");
			return false;
		}
		writeLock.lock();
		try {
			onlinePlayers.add(player);
			return true;
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * 向所有在线玩家广播消息，如果当前线程不是主线程，会将此消息转发到主线程进行广播
	 * 
	 * @param msg
	 */
	public void broadcastMessage(final GCMessage msg) {
		if (Thread.currentThread().getId() == Globals.getMessageProcessor().getThreadId()) {
			// 获取在线玩家
			List<Player> onlinePlayerList = this.getOnlinePlayers();

			for (Player onlinePlayer : onlinePlayerList) {
				if (onlinePlayer.getState() == PlayerState.gaming) {
					// 如果玩家已经进入游戏状态, 
					// 向玩家发送聊天信息
					onlinePlayer.sendMessage(msg);
				}
			}
		} else {
			Globals.getMessageProcessor().put(new SysInternalMessage() {
				@Override
				public void execute() {
					Globals.getOnlinePlayerService().broadcastMessage(msg);
				}
			});
		}
	}

	/**
	 * 向一个在线玩家发送消息
	 * 
	 * @param playerId
	 * @param msg
	 */
	public void sendMessage(int playerId, GCMessage msg) {
		Player player = onlinePlayers.get(playerId);
		if (player != null) {
			player.sendMessage(msg);
		}
	}

	/**
	 * 获得玩家实例
	 * 
	 * @param playerId
	 * @return
	 */
	public Player getPlayerByTempId(int playerId) {
		readLock.lock();
		try {
			Player player = onlinePlayers.get(playerId);
			return player;
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * 获取所有在线玩家passportId列表
	 * 
	 * <pre>
	 * 需要遍历在线玩家列表时调用此方法
	 * 不应该将onlinePlayers对外暴露
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	public Collection<String> getAllOnlinePlayerRoleUUIDs() {
		Set<String> onlinePlayerUUIDs = this.onlinePlayersMap.keySet();
		return onlinePlayerUUIDs;
	}

	/**
	 * 使所有玩家下线，注意一定要在调用{@link SceneService#stop()}方法之后调用此方法
	 * 
	 * @param reason
	 *            服务器主动发起的
	 */
	public void offlineAllPlayers(PlayerExitReason reason) {
		checkThread();
		Assert.isTrue(reason == PlayerExitReason.SERVER_SHUTDOWN);
		Loggers.playerLogger
				.info("All players will be offline,to save players "
						+ this.onlinePlayers.size());
		Collection<Player> players = onlinePlayersMap.values();

		for (Player player : players) {
			if (player != null && player.getState() != PlayerState.logouting) {
				try {
					PlayerOffLineEvent e = new PlayerOffLineEvent(player);
					Globals.getEventService().fireEvent(e);
					this.offlinePlayer(player, reason);
				} catch (Exception e) {
					Loggers.playerLogger.error(
							"Error occurs when offline all players", e);
				}
			}
		}
	}

	/**
	 * <pre>
	 * 玩家下线，此方法在主线程中调用
	 *</pre>
	 * 
	 * @param player
	 * @param reason
	 */
	public void offlinePlayer(Player player, final PlayerExitReason reason) {
		checkThread();
		player.exitReason = reason;
		//修改用户状态
//		modifyBattleMessage(player.getHuman());
		
		boolean isNeedReportLogout = false;
		if(player.getState() != PlayerState.connected && player.getState() != PlayerState.auth)
		{
			isNeedReportLogout = true;
		}
		
		if(player.getState() == PlayerState.connected
				||player.getState() == PlayerState.auth
				||player.getState() == PlayerState.loadingrolelist
				||player.getState() == PlayerState.waitingselectrole
				||player.getState() == PlayerState.creatingrole)
		{
			//不需要保存数据库，直接删除session即可
			player.setState(PlayerState.logouting);
			Globals.getOnlinePlayerService().removeSession(player.getSession());
			Globals.getOnlinePlayerService().removePlayer(player);
			
			if (isNeedReportLogout && Globals.getServerConfig().isTurnOnLocalInterface() && Globals.getServerConfig().getAuthType() == SharedConstants.AUTH_TYPE_INTERFACE) {
				ReportLogout2Local _reportLogoutTask = new ReportLogout2Local(player);
				Globals.getAsyncService().createSyncOperationAndExecuteAtOnce(_reportLogoutTask);
			}
			
			return;
		}
		
		if (player.getState() == PlayerState.loading
				|| player.getState() == PlayerState.entering
				|| player.getState() == PlayerState.leaving) 
		{
			player.setState(PlayerState.logouting);
		}
		
		
		// 增加离线保存战斗信息快照的mask
		SavePlayerRoleOperation _saveTask = new SavePlayerRoleOperation(player,
				PlayerConstants.CHARACTER_INFO_MASK_BASE 
				| PlayerConstants.CHARACTER_INFO_MASK_BATTLE_SNAP,
				true);
		
		
		
		if (reason == PlayerExitReason.SERVER_SHUTDOWN) {
			//服务器关闭
			player.setState(PlayerState.logouting);
			player.getHuman().onServerShutDown();
			Globals.getAsyncService().createSyncOperationAndExecuteAtOnce(_saveTask);
			if (isNeedReportLogout && Globals.getServerConfig().isTurnOnLocalInterface() && Globals.getServerConfig().getAuthType() == SharedConstants.AUTH_TYPE_INTERFACE) {
				ReportLogout2Local _reportLogoutTask = new ReportLogout2Local(player);
				Globals.getAsyncService().createSyncOperationAndExecuteAtOnce(_reportLogoutTask);
			}
		} else if(reason == PlayerExitReason.MULTI_LOGIN){
			//被顶下，需要同步执行
			if (player.isInScene()) {
				player.setState(PlayerState.logouting);
				// 离开场景后会自动调存库方法，存库之后会自动移除玩家 
				if(player.getHuman().getScene() instanceof Rep){
					Globals.getRepService().onPlayerOffline(player, (Rep) player.getHuman().getScene());
				}else{
					LetPlayerGotoBeforeSceneCallBack back = new LetPlayerGotoBeforeSceneCallBack();
					Globals.getSceneService().onPlayerLeaveScene(player, back);	
				}
							
			} else {
				// 同步保存，保存玩后开始通知玩家登录
				Globals.getAsyncService().createSyncOperationAndExecuteAtOnce(_saveTask);
				if (isNeedReportLogout && Globals.getServerConfig().isTurnOnLocalInterface() && Globals.getServerConfig().getAuthType() == SharedConstants.AUTH_TYPE_INTERFACE) {
					ReportLogout2Local _reportLogoutTask = new ReportLogout2Local(player);
					Globals.getAsyncService().createOperationAndExecuteAtOnce(_reportLogoutTask);
				}
				//让等待的玩家开始登录
				Player newPlayer = this.getAndRemoveWillAuthPlayer(player.getPassportId());
				Globals.getLoginLogicalProcessor().letWillAuthPlayerAuth(newPlayer);				
			}
		}else {
			//在场景中先退出场景,再调用该方法保存玩家信息，(如果是关闭服务器，这个时候场景心跳线程已经关闭了)
			if (player.isInScene()) {
				player.setState(PlayerState.logouting);
				// 离开场景后会自动调存库方法，存库之后会自动移除玩家 
				if(player.getHuman().getScene() instanceof Rep){
					Globals.getRepService().onPlayerOffline(player, (Rep) player.getHuman().getScene());
				}else{					
					LetPlayerGotoBeforeSceneCallBack back = new LetPlayerGotoBeforeSceneCallBack();
					Globals.getSceneService().onPlayerLeaveScene(player, back);					
				}
			} else {
				// 异步存库，存库之后会将玩家移除
				Globals.getAsyncService().createOperationAndExecuteAtOnce(_saveTask);
				if (isNeedReportLogout && Globals.getServerConfig().isTurnOnLocalInterface() && Globals.getServerConfig().getAuthType() == SharedConstants.AUTH_TYPE_INTERFACE) {
					ReportLogout2Local _reportLogoutTask = new ReportLogout2Local(player);
					Globals.getAsyncService().createOperationAndExecuteAtOnce(_reportLogoutTask);
				}
			}
		}
		
	}
	
//	private void modifyBattleMessage(Human human){
//		if(human!=null&&human.getGamingState()==GamingStateIndex.IN_BATTLE.getValue()){
//			String battleSn=BattleFieldManager.getBattleSn(human.getUUID());
//			if(org.apache.commons.lang.StringUtils.isNotEmpty(battleSn)){
//				AbstractBattleField battleField=(AbstractBattleField) BattleFieldManager.getBattleField(battleSn);
//				BattlePlayer battlePlayer=battleField.getBattlePlay(human.getUUID());
//				if(battlePlayer!=null){
//					battlePlayer.setOnlineStatus(OnlineStatus.EXIT);
//				}
//			}
//		}
//	}

	/**
	 * 根据玩家临时id移除一个在线玩家，同时通知WorldServer
	 * 
	 * <pre>
	 * 1、如果正在异步加载角色信息或退出保存，则只修改状态为logout，不进行移除操作
	 * 2、注意&lt;b&gt;此方法只能在主线程中调用&lt;/b&gt;，否则会 抛出异常{@link CrossThreadException}
	 * </pre>
	 * 
	 * @param playerId
	 *            玩家在场景中的Id
	 */
	public void removePlayer(Player player) {
		checkThread();
		if (player.getState() != PlayerState.logouting) {
			logger.error("Only player with state [logouting] can be remove "
					+ player.getPassportId());
			return;
		}
		logger.info("offline player passport=" + player.getPassportId());
		writeLock.lock();
		try {
			//被顶下线，不删除除session以外的map
			if (player.getHuman() != null/* && player.exitReason != PlayerExitReason.MULTI_LOGIN*/) {
				player.getHuman().setPlayer(null);
				onlinePlayersMap.remove(player.getHuman().getUUID());	
				roleNamePlayers.remove(player.getHuman().getName()); 
				campPlayers.get(player.getHuman().getCamp()).remove(player.getRoleUUID());
			}
//			if(player.getBattleInfoManage()!=null){
//				Globals.getBattleStateService().remove(player.getBattleInfoManage().getBattleSn());
//				BattleFieldManager.remove(player.getBattleInfoManage().getBattleSn());
//			}
			//被顶下线，不删除passportId列表
			if(player.getPassportId() != null /*&& player.exitReason != PlayerExitReason.MULTI_LOGIN*/){
				passportIdPlayers.remove(player.getPassportId());
			}
			onlinePlayers.remove(player);
		} finally {
			writeLock.unlock();
		}
		player.setState(PlayerState.logouted);
	}
	
	/**
	 * 去除session
	 * @param session
	 */
	public void removeSession(ISession session)
	{
		Player removePlayer = sessionPlayers.remove(session);
		if(removePlayer == null) 
		{
			return;
		}
		//TODO
//		removePlayer.setSession(null);
	}
	
	/**
	 * 获得全部的session数量
	 * @return
	 */
	public int getSessionCount(){
		return sessionPlayers.size();
	}

	/**
	 * 如果当前线程不合法，则抛出异常{@link CrossThreadException}
	 */
	@Override
	public boolean checkThread() {
		if (GameServerRuntime.isShutdowning() || !GameServerRuntime.isOpen() || Globals.getMessageProcessor().isStop()) {
			return true;
		}
		if (Thread.currentThread().getId() != Globals.getMessageProcessor().getThreadId()) {
			throw new CrossThreadException();
		}
		return true;
	}
	
	/**
	 * 根据场景ID获取当前场景玩家数
	 * 
	 * @param sceneId
	 * @return
	 */
	public int getOnlinePlayerCountBySceneId(String sceneId) {
		int count = 0;
		for (Player player : onlinePlayers) {
			if (StringUtils.isEquals(player.getSceneId(), sceneId )) {
				count++;
			}
		}
		return count;
	}

	public GameUnitList<Player> getOnlinePlayers() {
		return onlinePlayers;
	}
	
	/**
	 * 检查世界否已经爆满
	 * 
	 * @return
	 */
	public boolean isFull() {		
		// 当世界总人数达到世界人数上限时爆满
		int onlinePlayerCount = getOnlinePlayerCount();
		
		if (onlinePlayerCount >= Globals.getServerConfig()
				.getMaxOnlineUsers()) {
			return true;
		}
		return false;
	}
	
	

	/**
	 * 
	 * @param playerChar
	 */
	
	/**
	 * 根据姓名获得角色信息
	 * 
	 * @param name
	 * @return
	 */
	public String loadPlayerByName(String name) {
		HumanEntity humanEntity = Globals.getDaoService().getHumanDao().loadHuman(name);
		if(humanEntity != null)
		{
			return humanEntity.getName();			
		}
		else
		{
			return null;
		}
	}


	public ChannelService getChannelService() {
		return channelService;
	}

}
