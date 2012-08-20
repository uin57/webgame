package com.pwrd.war.gameserver.startup;

import org.slf4j.Logger;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.PlayerQueueMessage;
import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.core.msg.sys.ScheduledMessage;
import com.pwrd.war.core.server.ExecutableMessageHandler;
import com.pwrd.war.core.server.IMessageProcessor;
import com.pwrd.war.core.server.QueueMessageProcessor;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.common.msg.ProcessEventMessage;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.scene.msg.CGPlayerMove;
import com.pwrd.war.gameserver.scene.msg.CGPlayerPos;

/**
 * 游戏服务器消息处理器
 * 
 */
public class GameMessageProcessor implements IMessageProcessor {
	protected static final Logger log = Loggers.msgLogger;

	/** 主消息处理器，处理服务器内部消息、玩家不属于任何场景时发送的消息 */
	private QueueMessageProcessor mainMessageProcessor;

	public GameMessageProcessor() {
		mainMessageProcessor = new QueueMessageProcessor(
				new ExecutableMessageHandler());
	}

	@Override
	public boolean isFull() {
		return mainMessageProcessor.isFull();
	}

	/**
	 * <pre>
	 * 1、服务器内部消息、玩家不属于任何场景时发送的消息，单独一个消息队列进行处理
	 * 2、玩家在场景中发送过来的消息，添加到玩家的消息队列中，在场景的线程中进行处理
	 * </pre>
	 */
	@Override
	public void put(IMessage msg) {
		if(!(msg instanceof CGPlayerPos || msg instanceof ScheduledMessage
				|| msg instanceof CGPlayerMove)){
			log.info("【Receive】" + msg );
		}
		if (!GameServerRuntime.isOpen() && !(msg instanceof SysInternalMessage)
				&& !(msg instanceof ScheduledMessage)) {
			log.info("【Receive but will not process because server not open】"	+ msg);
			return;
		}
		if (msg instanceof CGMessage) {
			GameClientSession session = ((CGMessage) msg).getSession();
			if (session == null) {
				return;
			}
			Player player = session.getPlayer();
			if (player == null) {
				log.error("player not found. msg:" + msg);
				return;
			}
			log.debug("【Receive】" + msg + player);
			if (log.isDebugEnabled()) {
				if (player.getHuman() != null) {
					log.debug("【Receive】" + msg + " from player : "
							+ player.getHuman().getName() + ",roleUUID : "
							+ player.getHuman().getUUID() + ", sceneRoleId : "
							+ player.getHuman().getId());
				} else {
					log.debug("【Receive】" + msg);
				}
			}
			if (!player.getStateManager().canProcess(msg)) {
				if (player.getPermission() == SharedConstants.ACCOUNT_ROLE_GM
						&& player.getPassportName().indexOf("robot") > -1) {
				} else {
					Loggers.gameLogger.warn(LogUtils.buildLogInfoStr(player
							.getRoleUUID(), "msg type " + msg.getType()
							+ " can't be processed in curState:"
							+ player.getStateManager().getState()));
				}
				return;
			}
			if (player.isInScene()) {
				player.putMessage(msg);
			} else {
				mainMessageProcessor.put(msg);
			}
		}
		else if (msg instanceof PlayerQueueMessage) {
			PlayerQueueMessage playerQueueMsg = (PlayerQueueMessage) msg;
			String roleUUID = playerQueueMsg.getRoleUUID();
			Player player = Globals.getOnlinePlayerService().getPlayer(roleUUID);
			if (player == null) {
				log.error("player  with roleUUID:" + roleUUID + " not found");
				return;
			}
			if (log.isDebugEnabled()) {
				log.debug("【Receive】" + msg + " roleUUID : " + roleUUID);
			}
			player.putMessage(msg);
		}
		else if (msg instanceof ProcessEventMessage){
			ProcessEventMessage processEventMsg = (ProcessEventMessage) msg;
			String roleUUID = processEventMsg.getRoleUUID();
			Player player = Globals.getOnlinePlayerService().getPlayer(roleUUID);
			if (player != null) {
				processEventMsg.setPlayer(player);				
			}		
			Globals.getWorld().putMessage(msg);
		}
		else {
			if (log.isDebugEnabled() && !(msg instanceof ScheduledMessage)) {
				log.debug("【Receive】" + msg);
			}
			mainMessageProcessor.put(msg);
			return;
		}
	}

	@Override
	public void start() {
		mainMessageProcessor.start();
	}

	@Override
	public void stop() {
		mainMessageProcessor.stop();
	}

	/**
	 * 获得主消息处理线程Id
	 * 
	 * @return
	 */
	public long getThreadId() {
		return mainMessageProcessor.getThreadId();
	}

	/**
	 * @return
	 */
	public boolean isStop() {
		return mainMessageProcessor.isStop();
	}
}
