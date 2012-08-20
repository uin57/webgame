package com.pwrd.war.core.i18n;

import com.pwrd.war.common.InitializeRequired;

/**
 * 多语言管理服务管理
 *
 *
 */
public interface SysLangService extends InitializeRequired {

	/**
	 * 读取系统内部的多语言数据
	 * 
	 * @param key
	 * @return
	 */
	public String read(Integer key);
	
	/**
	 * 读取系统内部的多语言数据,如果params不为空,则用其格式化结果
	 * 
	 * @param key
	 * @param params
	 * @return
	 */
	public String read(Integer key, Object... params);
	
}
