package com.pwrd.war.gameserver.common.process;

import com.pwrd.war.db.model.ProcessEventEntity;
import com.pwrd.war.gameserver.human.Human;

/**
 * 进程事件处理器
 *
 */
public interface IProcessEventProcessor {	
	
	/**
	 * 
	 * 进程到时执行 逻辑处理
	 * 
	 * @param event
	 */
	public void execute(Human human,ProcessEventEntity event);	
	
	/**
	 * 进程取消 逻辑处理
	 * 
	 * @param event 
	 */
	public void destroy(Human human,ProcessEventEntity event);

}
