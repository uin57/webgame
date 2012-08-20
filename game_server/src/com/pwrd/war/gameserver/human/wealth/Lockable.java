package com.pwrd.war.gameserver.human.wealth;

/**
 * 
 * 此接口的实现类支持锁定，锁定后对此类的其他操作将受到一定限制
 * 
 * 
 */
public interface Lockable {

	/**
	 * 是否已经锁定了
	 * 
	 * @return
	 */
	boolean isLocked();

	/**
	 * 锁定
	 */
	void lock();

	/**
	 * 解锁
	 */
	void unlock();

}
