package com.pwrd.war.gameserver.scene.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.pwrd.war.common.HeartBeatAble;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.scene.Scene;

/**
 * 管理场景内玩家列表
 * 
 */
public class ScenePlayerManager implements HeartBeatAble {
	
	/** 当前场景对象 **/
	private Scene scene;
	/** 所有在线玩家的管理器,根据此对象取得玩家的实例 **/
	private OnlinePlayerService onlinePlayerManager;
	/** 玩家ID的列表.此处不引用玩家的实例,获取玩家实例需调用onlinePlayerManager **/
	private Set<Integer> playerIds;
 
	/** 镜像人数,仅用于非实时性的一些需求,且可能在多线程中调用 */
	private volatile int mirrorPlayerNum;

	private int realNum = 0;
	
	public ScenePlayerManager(Scene scene,
			OnlinePlayerService onlinePlayerManager, int maxPlayerCount) {
		this.scene = scene;
		this.onlinePlayerManager = onlinePlayerManager;
		playerIds = new CopyOnWriteArraySet<Integer>();//maxPlayerCount
		
		 
	}

	public void add(Integer playerId) {
		playerIds.add(playerId); 
	}
 
 
	/**
	 * 设置id离开，不需要从online中删除，其他会地方会删除
	 * @author xf
	 */
	public boolean remove(Integer playerId) {
		if(playerId == null)return false; 
		this.delAPlayer();
		return playerIds.remove(playerId);
	}

	/**
	 * 当前场景中是否包含某个玩家
	 * 
	 * @param playerId
	 * @return
	 */
	public boolean containPlayer(Integer playerId) {
		return this.playerIds.contains(playerId);
	}
	/**
	 * 根据playerId获取玩家信息
	 * @author xf
	 */
	public Player getByPlayerId(Integer playerId){
		if(containPlayer(playerId)){
			return onlinePlayerManager.getPlayerByTempId(playerId);
		}
		return null;
	}
	/**
	 * 群发消息给场景内玩家，extIds的除外
	 * @author xf
	 */
	public void sendGCMessage(GCMessage message, List<Integer> extIds){
		if(extIds == null) extIds = new ArrayList<Integer>();
		Set<Integer> ids = this.getPlayerIds();
		Iterator<Integer> it = ids.iterator();
		while(it.hasNext()){
			Integer id = it.next();
			if(!extIds.contains(id)){
				this.onlinePlayerManager.sendMessage(id, message);
			}
		}
	}
	
	/**
	 * 发送消息给场景内的特定人
	 * @author xf
	 */
	public void sendGCMessagetTo(GCMessage message, List<Integer> ids){
		if(ids == null) return;
		for(Integer id : ids){
			if(playerIds.contains(id)){
				this.onlinePlayerManager.sendMessage(id, message);
			}
		}
	}
	/**
	 * 处理场景内玩家的输入输出消息
	 */
	public void tick() {
		// 可能在迭代中删除元素,故不能用for-each形式
//		for (Iterator<Integer> iterator = playerIds.iterator(); iterator
//				.hasNext();) {
//			Integer playerId = iterator.next();
//			Player player = onlinePlayerManager.getPlayerByTempId(playerId);
//			// 如果玩家已不在在线列表中,或已经不属于当前场景，则从ID列表中删除
//			if (player == null  || !StringUtils.isEquals(player.getSceneId(),scene.getSceneId())) {
//				iterator.remove();
//				continue;
//			}
//			player.processMessage();
//		}
		for (Integer playerId : playerIds) { 
			Player player = onlinePlayerManager.getPlayerByTempId(playerId);
			// 如果玩家已不在在线列表中,或已经不属于当前场景，则从ID列表中删除
			if (player == null  || !StringUtils.isEquals(player.getSceneId(),scene.getSceneId())) {
				playerIds.remove(playerIds);
				continue;
			}
			player.processMessage();
		}
	}

	@Override
	public void heartBeat() {
		// 可能在迭代中删除元素,故不能用for-each形式
		for (Iterator<Integer> iterator = playerIds.iterator(); iterator
				.hasNext();) {
			Integer playerId = iterator.next();
			Player player = onlinePlayerManager.getPlayerByTempId(playerId);
			// 如果玩家已不在在线列表中,或已经不属于当前场景，则从ID列表中删除
			if (player == null || !StringUtils.isEquals(player.getSceneId(),scene.getSceneId())) {
				iterator.remove();
				continue;
			}
			player.heartBeat();
		}
		mirrorPlayerNum = playerIds.size();
	}

	public Set<Integer> getPlayerIds() {
		return this.playerIds;
	}
	
	/**
	 * 当前场景一个非实时的人数
	 * @see #mirrorPlayerNum
	 * 
	 * @return
	 */
	public int getMirrorPlayerNum() {
		return mirrorPlayerNum;
	}

	public  void addAPlayer(){
		this.realNum++;
	}
	
	public  void delAPlayer(){
		this.realNum--;
	}
	public int getPlayerNum(){
		return this.realNum;
	}
}
