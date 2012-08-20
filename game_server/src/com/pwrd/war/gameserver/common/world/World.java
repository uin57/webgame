package com.pwrd.war.gameserver.common.world;

import com.pwrd.war.common.HeartBeatAble;
import com.pwrd.war.common.Tickable;
import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageQueue;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.gameserver.common.db.EntityDataUpdater;
import com.pwrd.war.gameserver.common.db.GlobalDataUpdater;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatTaskExecutor;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatTaskExecutorImpl;

public class World implements Tickable, HeartBeatAble{
	
	/** 全局数据更新器 - 保存跟角色不相关的业务对象 */
	private GlobalDataUpdater dataUpdater;
	
	/** 实体数据更新器 */
	private EntityDataUpdater entityUpdater;
	
	/** 世界的消息队列 */
	private MessageQueue msgQueue;
	
	/** 心跳任务处理器 */
	private HeartbeatTaskExecutor hbTaskExecutor;
	
	/** 唯一实例 */
	private static World _instance = null;
	
	private World()
	{
		msgQueue = new MessageQueue();
		hbTaskExecutor = new HeartbeatTaskExecutorImpl();
		dataUpdater = new GlobalDataUpdater();
		entityUpdater = new EntityDataUpdater();
	}
	
	static{
		_instance = new World();
	}
	
	/**
	 * 获得世界对象
	 * @return
	 */
	public static World getWorld()
	{
		return _instance;
	}
	

	@Override
	public void tick() {
		// 处理世界消息
		while (!msgQueue.isEmpty()) {
			IMessage msg = msgQueue.get();
			msg.execute();
		}		
		this.heartBeat();
	}

	@Override
	public void heartBeat() {
		this.updateData();
		hbTaskExecutor.onHeartBeat();
	}
	
	/**
	 * @param message
	 * @return
	 */
	public boolean putMessage(IMessage message) {
		msgQueue.put(message);
		return true;
	}
	
	/**
	 * 更新数据
	 */
	private void updateData() {
		try {
			this.dataUpdater.update();
			this.entityUpdater.update();
		} catch (Exception e) {
			if (Loggers.updateLogger.isErrorEnabled()) {
				Loggers.updateLogger.error(ErrorsUtil.error(
						CommonErrorLogInfo.INVALID_STATE,
						"#GS.World.updateData", ""), e);
			}
		}
	}
	
	/**
	 * 获得全局数据更新器
	 * @return
	 */
	public GlobalDataUpdater getDataUpdater() {
		return dataUpdater;
	}

	/**
	 * 获得数据实体保存的更新器
	 * @return
	 */
	public EntityDataUpdater getEntityUpdater() {
		return entityUpdater;
	}

}
