package com.pwrd.war.gameserver.human.async;

import com.pwrd.war.common.model.human.HumanInfo;

/**
 * 查询角色信息的回调,因为调用有可能是异步调用
 * 
 * @author jiliang.lu
 *
 */
public interface QueryHumanInfoCallback {
	
	/**
	 * HumanInfo成功得到后,调用
	 * 
	 * @param humanInfo
	 */
	public void afterQueryComplete(HumanInfo humanInfo);
	

}
