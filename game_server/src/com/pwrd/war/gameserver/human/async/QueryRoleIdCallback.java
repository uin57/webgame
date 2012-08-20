package com.pwrd.war.gameserver.human.async;


public interface QueryRoleIdCallback {
	
	
	/**
	 * HumanInfo成功得到后,调用
	 * 
	 * @param humanInfo
	 */
	public void afterQueryComplete(String roleName,Long roleUUID);

}
