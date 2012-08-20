package com.pwrd.war.core.uuid;



/**
 * UUID的管理器接口
 * 
 * 
 */
public interface UUIDService {

	/**
	 * 取得指定类型的下一个UUID
	 * 
	 * @param uuidType
	 * @return
	 */
	public abstract long getNextUUID(UUIDType uuidType);

}