package com.pwrd.war.gameserver.common.db.operation;

import com.pwrd.war.core.async.IIoOperation;

/**
 * 与UUID绑定的数据异步操作接口
 * 
 * 
 * 
 */
public interface BindUUIDIoOperation extends IIoOperation {
	
	/**
	 * 取得该操作绑定的uuid
	 * 
	 * @return
	 */
	public String getBindUUID();
}
