package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.db.model.ProcessEventEntity;
import com.pwrd.war.gameserver.common.process.IProcessEventProcessor;
import com.pwrd.war.gameserver.player.Player;

/**
 * 进程执行消息
 *
 */
public class SysProcessEventExecute extends SysInternalMessage implements ProcessEventMessage{
	
	/**
	 * 进程事件实体
	 */
	private ProcessEventEntity event;
	
	/**
	 * 进程事件处理器
	 */
	private IProcessEventProcessor processor;
	
	/**
	 * 消息执行器索取Player
	 */
	private Player onlinePlayer;

	@Override
	public void execute() {
		if(onlinePlayer != null)
		{
			processor.execute(onlinePlayer.getHuman(), event);
		}
		else
		{
			processor.execute(null, event);
		}			
	}
	
	@Override
	public String getTypeName() {
		return "SysEventExecutorMessage";
	}
	
	public ProcessEventEntity getEvent() {
		return event;
	}

	public void setEvent(ProcessEventEntity event) {
		this.event = event;
	}

	public IProcessEventProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(IProcessEventProcessor processor) {
		this.processor = processor;
	}

	@Override
	public String getRoleUUID() {
		return this.event.getCharId();
	}

	@Override
	public void setPlayer(Player player) {
		onlinePlayer = player;
	}


}
