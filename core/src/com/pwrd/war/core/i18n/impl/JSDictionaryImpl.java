package com.pwrd.war.core.i18n.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.core.i18n.I18NDictionary;
import com.pwrd.war.core.util.ErrorsUtil;

/**
 * 从JS脚本中文件中加载数据
 * 
 */
public class JSDictionaryImpl implements I18NDictionary<Integer, String> {

	private static final Logger log = LoggerFactory.getLogger("template");;
	private final String fileName;
	private final String charset;
	private final Map<Integer, String> data = new HashMap<Integer, String>();

	public JSDictionaryImpl(String fileName,String charset) {
		this.fileName = fileName;
		this.charset = charset;
		load();
	}


	public String read(Integer val) {
		return data.get(val);
	}

	public void load() {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		engine.put("lang", this);
		try {
			String content = FileUtils.readFileToString(new File(this.fileName),charset);
			engine.eval(content);
		} catch (IOException e) {
			throw new RuntimeException(ErrorsUtil.error(com.pwrd.war.common.constants.CommonErrorLogInfo.FILE_IO_FAIL, "Throw Exception", this.fileName), e);
		} catch (ScriptException se) {
			throw new RuntimeException(ErrorsUtil.error(com.pwrd.war.common.constants.CommonErrorLogInfo.SCRITP_EXECUTE_FAIL, "Throw Exception", this.fileName), se);
		}
	}

	public void addTemplate(int key, String value) {
		if (this.data.containsKey(key)) {
			log.warn(ErrorsUtil.error(CommonErrorLogInfo.CONFIG_DUP_FAIL, "Warn", fileName + "[key:" + key + ",value:" + value + "]"));
		} else {
			data.put(key, value);
		}
	}
}
