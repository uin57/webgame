package com.pwrd.war.gameserver.scene.handler;

import java.util.Arrays;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.SceneLangConstants_60000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.msg.GCNotifyException;
import com.pwrd.war.gameserver.rep.Rep;
import com.pwrd.war.gameserver.scene.Scene;
import com.pwrd.war.gameserver.scene.SceneService;
import com.pwrd.war.gameserver.scene.msg.CGPlayerJump;
import com.pwrd.war.gameserver.scene.msg.CGPlayerMove;
import com.pwrd.war.gameserver.scene.msg.CGPlayerPos;
import com.pwrd.war.gameserver.scene.msg.CGPlayerSit;
import com.pwrd.war.gameserver.scene.msg.CGPlayerStand;
import com.pwrd.war.gameserver.scene.msg.CGSceneLine;
import com.pwrd.war.gameserver.scene.msg.CGSwitchScene;
import com.pwrd.war.gameserver.scene.msg.CGSwitchSceneLine;
import com.pwrd.war.gameserver.scene.msg.GCPlayerJump;
import com.pwrd.war.gameserver.scene.msg.GCPlayerMove;
import com.pwrd.war.gameserver.scene.msg.GCPlayerSit;
import com.pwrd.war.gameserver.scene.msg.GCPlayerStand;
import com.pwrd.war.gameserver.scene.msg.GCSceneLine;
import com.pwrd.war.gameserver.team.TeamInfo;

/**
 * 场景消息处理，主要处理玩家进入、退出场景，玩家移动等消息
 * 
 * 
 */
public class SceneMessageHandler {

	private SceneService sceneService;
	private OnlinePlayerService onlinePlayerService;

	protected SceneMessageHandler(SceneService sceneManager,
			OnlinePlayerService onlinePlayerManager) {
		this.sceneService = sceneManager;
		this.onlinePlayerService = onlinePlayerManager;
	}

	/**
	 * <pre>
	 * 玩家进入场景，应只在场景所在的线程中调用，先将玩家从当前场景中移
	 * 除
	 * </pre>
	 * 
	 * @param playerId
	 * @param sceneId
	 * @param location
	 *            TODO
	 */
	public boolean handleEnterScene(int playerId, String sceneId, int line) {
		Scene scene = sceneService.getScene(sceneId, line);
		Player player = onlinePlayerService.getPlayerByTempId(playerId);
		if (player == null || scene == null) {
			return false;
		}
		return scene.onPlayerEnter(player);
	}

	/**
	 * <pre>
	 * 玩家离开场景，应只在场景所在的线程中调用，先将玩家从当前场景中移
	 * 除，然后再发送消息给主线程
	 * </pre>
	 * 
	 * @param playerId
	 * @param sceneId
	 */
	public void handleLeaveScene(final Player player){
		Scene scene = player.getHuman().getScene();
		if (scene != null) {
			scene.onPlayerLeave(player);
		} else {
			Loggers.gameLogger.error(String.format( 
					"leave scene fail. cannot find player in onlinePlayerService. %s",
									player.getHuman().getName()));
		}
	}

	/**
	 * 切换进入场景 
	 */
	public void handleSwitchScene(Player player, CGSwitchScene entrySecne) {
		//如果是队员不能主动切换
		if(player.getHuman().isInTeam()){
			TeamInfo t = player.getHuman().getTeamInfo();
			if(!t.isLeader(player.getHuman())){
				player.sendErrorMessage("非队长无法主动切换场景");
				Loggers.msgLogger.error("非队长无法主动切换场景");
				return;
			}
		} 
		
		boolean rs =  Globals.getSceneService().onPlayerSwitchScene(player, entrySecne.getSceneId(), -1);
		if(!rs){
			Loggers.gameLogger.warn("player " + player.getPassportName() + " can't enter scene, targetSceneId :" + player.getTargetSceneId());
//			player.sendMessage(new GCNotifyException(SceneLangConstants_60000.ENTER_SCENE_FAIL,
//					Globals.getLangService().read(SceneLangConstants_60000.ENTER_SCENE_FAIL)));
			return;
		} 
		
	}
	/**
	 * 在场景内有移动
	 * @author xf
	 */
	public void handlePlayerMove(Player p, CGPlayerMove playerMove) {
		if(p.getHuman().isInTeam()){
			//在组队中，判断是否队长
			TeamInfo team = p.getHuman().getTeamInfo();
			if(!team.isLeader(p.getHuman())){
				p.sendErrorMessage("非队长不能手动移动");
				Loggers.msgLogger.info("非队长不能手动移动");
				return ;
			}
		}
		String sceneId = p.getSceneId();
		//广播给当前场景内的所有玩家
		if(!StringUtils.isEquals(p.getSceneId(),sceneId)){
			//TODO 场景错误
			GCNotifyException ex = new GCNotifyException(SceneLangConstants_60000.ENTER_SCENE_FAIL,
					Globals.getLangService().read(SceneLangConstants_60000.ENTER_SCENE_FAIL));
			p.sendMessage(ex);
			return;
		}
		
		Scene scene =  p.getHuman().getScene();
		if(scene == null){
//			p.sendErrorMessage("不在场景中不能移动");
			return;
		}
		p.setToX(playerMove.getTox());
		p.setToY(playerMove.getToy()); 
		//TODO 验证合法性
		//获取在线玩家id，然后逐个发送
		GCPlayerMove msg = new GCPlayerMove();		 
		msg.setRoleUUID(p.getRoleUUID()); 
		msg.setSrcx(playerMove.getSrcx());
		msg.setSrcy(playerMove.getSrcy());
		msg.setTox(playerMove.getTox());
		msg.setToy(playerMove.getToy());
		scene.getPlayerManager().sendGCMessage(msg, Arrays.asList(p.getId()));
		
		//通知其他队员开始移动
		TeamInfo team = p.getHuman().getTeamInfo();
		if(team != null){
			for(Human h : team.getTeamer()){
				scene =  h.getScene();
				h.getPlayer().setToX(playerMove.getTox());
				h.getPlayer().setToY(playerMove.getToy()); 
				 
				msg = new GCPlayerMove();		 
				msg.setRoleUUID(h.getPlayer().getRoleUUID()); 
				msg.setSrcx(playerMove.getSrcx());
				msg.setSrcy(playerMove.getSrcy());
				msg.setTox(playerMove.getTox());
				msg.setToy(playerMove.getToy());
				scene.getPlayerManager().sendGCMessage(msg, null);
			}
		}
		
	} 
	
	/**
	 * 客户端和服务端同步当前位置
	 * @author xf
	 */
	public void handlePlayerPos(Player p, CGPlayerPos playerPos) {
		if(p.getHuman().getScene() instanceof Rep){
			p.getHuman().setX(playerPos.getSrcx());
			p.getHuman().setY(playerPos.getSrcy()); 
			p.setLastSetPosTime(Globals.getTimeService().now()); 
			return;
		}
		if(p.getHuman().getScene() == null){
//			p.sendErrorMessage("不在场景中不能移动");
			return;
		}
		
		String sceneId = playerPos.getSceneId();
		//广播给当前场景内的所有玩家
		if(!StringUtils.isEquals(p.getSceneId(),sceneId)){
			//TODO 场景错误
			GCNotifyException ex = new GCNotifyException(SceneLangConstants_60000.ENTER_SCENE_FAIL, 
					Globals.getLangService().read(SceneLangConstants_60000.ENTER_SCENE_FAIL));
			p.sendMessage(ex);
			return;
		}
		//TODO 需要验证
		//2此时间间隔，以验证速度是否正常
		long tt = Globals.getTimeService().now() - p.getLastSetPosTime();
		Scene scene = sceneService.getScene(sceneId, playerPos.getLineNo());
		if(scene == null)return ;
//		GCPlayerPos msg = new GCPlayerPos();
		if(scene.getSceneInfoVO().isInMap(tt, p.getHuman().getX(), p.getHuman().getY(),
				playerPos.getSrcx(), playerPos.getSrcy())){
			p.getHuman().setX(playerPos.getSrcx());
			p.getHuman().setY(playerPos.getSrcy());
//			msg.setResult(true);
			p.setLastSetPosTime(Globals.getTimeService().now()); 
			 
		}else{
//			msg.setSceneId(sceneId);
//			msg.setLineNo(scene.getLine());
//			msg.setSrcx(p.getHuman().getX());
//			msg.setSrcy(p.getHuman().getY());
//			msg.setResult(false);
//			p.sendMessage(msg);
		}
	} 
	
	/**
	 * 取得该场景有多少条分线
	 * @author xf
	 */
	public void handleSceneLine(Player player, CGSceneLine msg){
		String sceneId = msg.getSceneId();
		Integer line = this.sceneService.getSceneLine().get(sceneId);
		if(line == null)return;
		GCSceneLine gcmsg = new GCSceneLine();
		gcmsg.setLine(line);
		gcmsg.setSceneId(sceneId);
		player.sendMessage(gcmsg); 
	}
	
	/**
	 * 切换到场景内的其他线
	 * @author xf
	 */
	public void handleSwitchSceneLine(Player player, CGSwitchSceneLine msg){
		//如果是队员不能主动切换
		if(player.getHuman().isInTeam()){
			TeamInfo t = player.getHuman().getTeamInfo();
			if(!t.isLeader(player.getHuman())){
				player.sendErrorMessage("非队长无法主动切换场景");
				Loggers.msgLogger.error("非队长无法主动切换场景");
				return;
			}
		}
				
		String sceneid = msg.getSceneId();
		
		boolean rs = this.sceneService.onPlayerSwitchSceneLine(player, sceneid, msg.getLineNo()); 
		if(!rs){
			Loggers.gameLogger.warn("player " + player.getPassportName() + " can't enter scene, targetSceneId :" + player.getTargetSceneId());
			player.sendMessage(new GCNotifyException(SceneLangConstants_60000.ENTER_SCENE_FAIL,
					Globals.getLangService().read(SceneLangConstants_60000.ENTER_SCENE_FAIL))); 
		} 
	}

	public void handlePlayerJump(final Player player, CGPlayerJump msg){
		GCPlayerJump jump = new GCPlayerJump();
		jump.setRoleUUID(player.getRoleUUID());
		jump.setTox(msg.getTox());
		jump.setToy(msg.getToy());
		Scene s = player.getHuman().getScene();
		if(s != null){
			s.getPlayerManager().sendGCMessage(jump, Arrays.asList(player.getId()));
		}
	}
	
	public void handlePlayerSit(final Player player, CGPlayerSit msg){
		GCPlayerSit sit = new GCPlayerSit();
		sit.setRoleUUID(player.getRoleUUID());
		player.getHuman().getScene().getPlayerManager().sendGCMessage(sit, Arrays.asList(player.getId()));
	}
	
	public void handlePlayerStand(final Player player, CGPlayerStand msg){
		GCPlayerStand playerStand = new GCPlayerStand();
		playerStand.setRoleUUID(player.getRoleUUID());
		if(player.getHuman().getScene() != null){
			player.getHuman().getScene().getPlayerManager().sendGCMessage(playerStand, null);
		}
	}
}
