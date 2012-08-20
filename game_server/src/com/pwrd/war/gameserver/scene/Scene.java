package com.pwrd.war.gameserver.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.pwrd.war.common.DestroyRequired;
import com.pwrd.war.common.HeartBeatAble;
import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.Tickable;
import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.model.human.HumanInfo;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageQueue;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatTaskExecutor;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatTaskExecutorImpl;
import com.pwrd.war.gameserver.common.listener.Listenable;
import com.pwrd.war.gameserver.common.listener.Listener;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.msg.GCSceneAddRole;
import com.pwrd.war.gameserver.human.msg.GCSceneDelRole;
import com.pwrd.war.gameserver.human.msg.GCSceneRoleList;
import com.pwrd.war.gameserver.monster.MonsterService;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.monster.VisibleMonsterInfo;
import com.pwrd.war.gameserver.monster.msg.GCMonsterList;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerState;
import com.pwrd.war.gameserver.player.msg.GCEnterScene;
import com.pwrd.war.gameserver.scene.listener.GroupMonsterDeadListener;
import com.pwrd.war.gameserver.scene.listener.SceneListener;
import com.pwrd.war.gameserver.scene.manager.SceneMonsterManager;
import com.pwrd.war.gameserver.scene.manager.ScenePlayerManager;
import com.pwrd.war.gameserver.scene.persistance.SceneDataUpdater;
import com.pwrd.war.gameserver.scene.vo.SceneInfoVO;

public abstract class Scene implements Tickable, HeartBeatAble, Listenable<SceneListener>, InitializeRequired, DestroyRequired {
	
//	/** UUID */
//	private String UUID;
	
//	/** 场景模版 */
//	private SceneTemplate sceneTemplate;
	/** 分配的线号 **/
	protected int line;
	/** 地图数据 **/
	protected SceneInfoVO sceneInfoVO;
	
	/** 场景最多人数 */
	public static final int MAX_PLAYER_COUNT = 1024;
	
	/** 场景的消息队列 */
	protected MessageQueue msgQueue;
	
	/** 场景玩家管理 */
	protected ScenePlayerManager playerManager;
	
	/** 地图怪物管理器 **/
	protected SceneMonsterManager monsterManager;
	/** 心跳任务处理器 */
	protected HeartbeatTaskExecutor hbTaskExecutor;
	
	/** 注册到该场景上的监听器 */
	protected List<SceneListener> listeners;
	
	/** 场景数据更新器 */
	protected SceneDataUpdater dataUpdater;
	
	/** 一组怪物死亡后调用该事件  **/
	private GroupMonsterDeadListener groupMonsterDeadListener;
//	/** 生命周期 */
//	private LifeCycle lifeCycle;
//	
//	/** 此实例是否在db中 */
//	private boolean isInDb;

	private boolean bDestroy = false;
	public Scene(SceneInfoVO sceneInfoVO, 
		OnlinePlayerService onlinePlayerService,
		MonsterService monsterService) {
		if (sceneInfoVO == null) {
			throw new IllegalArgumentException("mapVO is null");
		}

		if (onlinePlayerService == null) {
			throw new IllegalArgumentException("onlinePlayerService is null");
		}

		this.sceneInfoVO = sceneInfoVO;
		
		msgQueue = new MessageQueue();
		playerManager = new ScenePlayerManager(this, onlinePlayerService,MAX_PLAYER_COUNT);	
		monsterManager = new SceneMonsterManager(this, monsterService);
		listeners = new ArrayList<SceneListener>(
				SceneListener.DEFAULT_LISTENER_CONTAINER_CAPACITY);
		hbTaskExecutor = new HeartbeatTaskExecutorImpl();

		this.dataUpdater = new SceneDataUpdater();

		// 设置生命周期并激活
//		this.lifeCycle = new LifeCycleImpl(this);
//		this.lifeCycle.activate();

	}
	
	@Override
	public void init() {
		monsterManager.init();
	}

	/**
	 * @warning 在主处理线程中被调用
	 */
	@Override
	public void deleteListener(SceneListener listener) {
		if (!listeners.contains(listener)) {
			return;
		}
		listeners.remove(listener);
		listener.onDeleted(this);
	}

	/**
	 * @warning 在主处理线程中被调用
	 */
	@Override
	public void registerListener(SceneListener listener) {
		if (listeners.contains(listener)) {
			// 不可重入
			return;
		}
		listeners.add(listener);
		listener.onRegisted(this);
		Collections.sort(listeners, Listener.comparator);
	}
	
	 
	/**
	 * 玩家进入场景
	 * 
	 * @param player
	 */
	public boolean onPlayerEnter(Player player) {
		this.playerManager.add(player.getId());
		Human human = player.getHuman();
		if (Loggers.msgLogger.isDebugEnabled()) {
			Loggers.msgLogger.debug("player[" + human.getUUID()
					+ "] enter scene[" + this.getSceneId() + "]");
		}
		// 最后进入前的数据预处理 ： 检查数据， 初始化
		human.checkBeforeRoleEnter();
		
		// 修改玩家所在场景和区域 Id
		human.setScene(this);
		human.setSceneId(this.getSceneId());

		
		// 保存玩家角色修改
		human.setModified();
		human.snapChangedProperty(true);
		
		//发送进入场景后的相关信息
		GCEnterScene gcEnter = new GCEnterScene(); 
		human.sendMessage(gcEnter);
		//发送场景中的其他玩家列表消息给自己
		List<HumanInfo> otherList = new ArrayList<HumanInfo>();
		Collection<Integer> ids = this.playerManager.getPlayerIds();
		for(Integer id : ids){
//			if(!id.equals(player.getId())){
				HumanInfo info = this.playerManager.getByPlayerId(id).toHumanInfo();
				otherList.add(info);
//			}
		}
		GCSceneRoleList gcSceneRoleList = new GCSceneRoleList();
		HumanInfo[] humanInfoArr = new HumanInfo[otherList.size()];
		otherList.toArray(humanInfoArr);
		gcSceneRoleList.setRoleInfo(humanInfoArr);
		human.sendMessage(gcSceneRoleList);
		
		//发送自己的消息给其他玩家
		HumanInfo myInfo = player.toHumanInfo();
		GCSceneAddRole gcSceneAddRole = new GCSceneAddRole();
		gcSceneAddRole.setRoleInfo(myInfo);
		this.playerManager.sendGCMessage(gcSceneAddRole,  Arrays.asList(player.getId()));
		
		//发送明雷信息给自己
		List<VisibleMonster> monsters = this.monsterManager.getAllVisibleMonster();
		GCMonsterList gcMonsterList = new GCMonsterList();
		VisibleMonsterInfo[] monsterInfo = new VisibleMonsterInfo[monsters.size()];
		int i = 0;
		for(VisibleMonster m : monsters){
			monsterInfo[i++] = m.toVisibleMonsterInfo();
		}
		gcMonsterList.setMonsterInfo(monsterInfo);
		human.sendMessage(gcMonsterList);
		
		// 监听器监听
		for (SceneListener listener : listeners) {
			listener.afterHumanEnter(this, human);
		}

		return true;
	}
	
	/**
	 * 玩家离开场景，此方法在场景线程中执行
	 * 
	 * @param player
	 */
	public void onPlayerLeave(Player player) {
		System.out.println("玩家离开了场景");
		
		int playerId = player.getId();
		Human human = player.getHuman();
		if (human == null) {
			// 玩家信息还未加载
			return;
		}
		try {
			if (!playerManager.containPlayer(playerId)) {
				Loggers.mapLogger.warn("player[" + human.getUUID()
						+ "] not in scene[" + this.getSceneId() + "]");
				return;
			}
			if (Loggers.msgLogger.isDebugEnabled()) {
				Loggers.msgLogger.debug("player[" + human.getUUID()
						+ "] leave scene[" + this.getSceneId() + "]");
			}
			// 监听器监听,在玩家被置为离开之前
			if (PlayerState.logouting == human.getPlayer().getState()) {
				// 通知军团玩家退出游戏,在场景线程处理
//				human.getGuildManager().onPlayerExit();
				for (SceneListener listener : listeners) {
					listener.leaveOnLogoutSaving(this, human);
				}
			} else {
				for (SceneListener listener : listeners) {
					listener.beforeHumanLeave(this, human);
				}
			}

			// something to cancel
			//告诉场景其他玩家该玩家已经离开
			this.playerManager.remove(playerId);
			GCSceneDelRole gcSceneDelRole = new GCSceneDelRole();
			gcSceneDelRole.setUUID(player.getRoleUUID());
			this.playerManager.sendGCMessage(gcSceneDelRole, null);
			
			
		} catch (Exception e) {
			Loggers.gameLogger
					.error("Error occurs when player leave scene", e);
		} finally {			
			human.setScene(null);			
		}
	}
	
	/**
	 * @param message
	 * @return
	 */
	public boolean putMessage(IMessage message) {
		msgQueue.put(message);
		return true;
	}
	
	@Override
	public void tick() {
		if(bDestroy)return;
		playerManager.tick();
		// 处理场景消息
		while (!msgQueue.isEmpty()) {
			IMessage msg = msgQueue.get();
			msg.execute();
		}
		this.heartBeat();
	}

	@Override
	public void heartBeat() {
		if(bDestroy)return;
		playerManager.heartBeat();//存库操作,要在其他manager调用后做
		monsterManager.heartBeat();
		this.updateData();
		hbTaskExecutor.onHeartBeat();
	}


	@Override
	public void destroy() {
		msgQueue = null;
		playerManager = null; 
		bDestroy = true;
	}
	public void setDestroy(){
		bDestroy = true;
	}
	
	
	public ScenePlayerManager getPlayerManager() {
		return playerManager;
	}

	

 

	/**
	 * 获取场景数据更新器
	 * 
	 * @return
	 */
	public SceneDataUpdater getDataUpdater() {
		return this.dataUpdater;
	}

	 
   
  

	/**
	 * 更新数据
	 */
	private void updateData() {
		try {
			this.dataUpdater.update();
		} catch (Exception e) {
			if (Loggers.updateLogger.isErrorEnabled()) {
				Loggers.updateLogger.error(ErrorsUtil.error(
						CommonErrorLogInfo.INVALID_STATE,
						"#GS.Scene.updateData", ""), e);
			}
		}
	}






	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Scene)) {
			return false;
		}

		return ((Scene)obj).getSceneId().equals(this.getSceneId());
	}
	public String getSceneId(){
		return this.sceneInfoVO.getSceneId();
	}
	public SceneInfoVO getSceneInfoVO() {
		return sceneInfoVO;
	}

	 
	 
	public int getType() {
		return this.sceneInfoVO.getType();
	} 
	public int getNeedLevel() {
		return this.sceneInfoVO.getNeedLevel();
	} 
	public int getMaxLevel() {
		return this.sceneInfoVO.getMaxLevel();
	} 
	 
  
	public void setGroupMonsterDeadListener(GroupMonsterDeadListener groupMonsterDeadListener){
		this.groupMonsterDeadListener = groupMonsterDeadListener;
	}
	/**
	 * 一组怪死亡时调用，group死亡的组的序号
	 */
	public void onGrounMonsterDead(int group){
		if(groupMonsterDeadListener != null){
			groupMonsterDeadListener.onDead(group);
		}
	}

	public SceneMonsterManager getMonsterManager() {
		return monsterManager;
	}
	
	/**
	 * 获取以x,y为中心点，width为宽度，height为高的矩形内的玩家列表
	 * @author xf
	 */
	public List<Player> getRectPlayers(int x, int y, int width, int height){
		int startX = x - width/2;
		int endX = x + width/2;
		int startY = y - height/2;
		int endY = y + height/2;
		List<Player> list = new ArrayList<Player>();
		for(Integer id : this.playerManager.getPlayerIds()){
			Player p = this.playerManager.getByPlayerId(id);
			if(p != null){
				if(p.getHuman().getX() >= startX && p.getHuman().getX() <= endX
						&& p.getHuman().getY() >= startY && p.getHuman().getY() <= endY){
					list.add(p);
				}
			}
		}
		return list;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
	
	public void onMonsterDead(String monsterSN) {
		
	}
	
}
