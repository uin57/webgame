package com.pwrd.war.gameserver.common.i18n;


public interface LangService  {
	/**
	 *  
	 */
	String read(Integer key);

	/**
	 *  
	 */
	String read(Integer key, Object... params);
	
	/**
	 * 取excel模版里的多语言key构造多语言key
	 * @author xf
	 */
	String readFromTemplate(String templateValue, Object... params);
}
