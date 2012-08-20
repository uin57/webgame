package com.pwrd.war.core.i18n.impl;

import java.text.MessageFormat;

import com.pwrd.war.core.i18n.I18NDictionary;
import com.pwrd.war.core.i18n.SysLangService;

/**
 * 多语言管理器
 * 
 * 
 */
public class SysLangServiceImpl implements SysLangService {
	/** 多语言容器 */
	private I18NDictionary<Integer, String> sysLangs;

	/**
	 * 
	 * @param sysLangConfigFile
	 *            多语言配置的文件路径
	 */
	public SysLangServiceImpl(final String sysLangConfigFile) {
		sysLangs = new ExcelIntDictionaryImpl(sysLangConfigFile);
	}

	@Override
	public String read(Integer key) {
		return sysLangs.read(key);
	}

	@Override
	public String read(Integer key, Object... params) {
		String _msg = sysLangs.read(key);
		if (_msg == null) {
			return "lang_" + key;
		}
		if (params != null) {
			return MessageFormat.format(_msg, params);
		} else {
			return _msg;
		}
	}

	@Override
	public void init() {
		
	}
}
