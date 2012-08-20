package com.pwrd.war.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.constants.CommonErrorLogInfo;

/**
 * JSON工具类
 * 
 * 
 */
@SuppressWarnings("unchecked")
public class JsonUtils {

	/** 日志 */
	public static final Logger logger = LoggerFactory.getLogger("jsonutils");

	/** 默认字符串 */
	public final static String DEFAULT_STRING = "";

	/** 默认Long */
	private final static long DEFAULT_LONG = 0l;

	/** 默认Int {@value} */
	public final static int DEFAULT_INT = 0;

	/** 默认Float */
	public final static float DEFAULT_FLOAT = 0f;

	/** 默认Double */
	public final static double DEFAULT_DOUBLE = 0d;

	/** 默认Boolean */
	public final static boolean DEFAULT_BOOLEAN = false;

	/**
	 * 将Map中的值转化为JSON String格式
	 * 
	 * @param ps
	 * @return
	 */
	public static String toJsonString(Map<String, Object> ps) {
		JSONObject jsonobj = JSONObject.fromObject(ps);
		return jsonobj.toString();
	}

	/**
	 * 将JSON String格式转化为Map,需要注意的是,JSON会换float->double
	 * 
	 * @param jsonstr
	 * @return
	 */
	public static Map<String, Object> toMap(String jsonstr) {
		if (jsonstr == null) {
			return Collections.EMPTY_MAP;
		} else {
			JSONObject jsonobj = JSONObject.fromObject(jsonstr);
			return (Map) JSONObject.toBean(jsonobj, Map.class);
		}
	}

	/**
	 * 获取字符串类型数据
	 * 
	 * @param jsonObj
	 *            Json源
	 * @param key
	 * @param logger
	 *            需要记录到的logger
	 * @return
	 */
	public static String getString(JSONObject jsonObj, Object key) {
		try {
			String _key = String.valueOf(key);
			if (jsonObj.containsKey(_key)) {
				return jsonObj.getString(_key);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.JSON_ANALYZE_FAIL,
						"#GS.JsonUtils.getString", "Json format error"), e);
			}
		}
		return DEFAULT_STRING;
	}

	/**
	 * 获取long类型数据
	 * 
	 * @param jsonObj
	 *            Json源
	 * @param key
	 * @param logger
	 *            需要记录到的logger
	 * @return
	 */
	public static long getLong(JSONObject jsonObj, Object key) {
		try {
			String _key = String.valueOf(key);
			if (jsonObj.containsKey(_key)) {
				return Long.parseLong(jsonObj.getString(_key));
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.JSON_ANALYZE_FAIL,
						"#GS.JsonUtils.getString", "Json format error"), e);
			}
		}
		return DEFAULT_LONG;
	}

	/**
	 * 获取int类型数据
	 * 
	 * @param jsonObj
	 *            Json源
	 * @param key
	 * @param logger
	 *            需要记录到的logger
	 * @return
	 */
	public static int getInt(JSONObject jsonObj, Object key) {
		try {
			String _key = String.valueOf(key);
			if (jsonObj.containsKey(_key)) {
				return jsonObj.getInt(_key);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.JSON_ANALYZE_FAIL,
						"#GS.JsonUtils.getString", "Json format error"), e);
			}
		}
		return DEFAULT_INT;
	}

	/**
	 * 获取double类型数据
	 * 
	 * @param jsonObj
	 *            Json源
	 * @param key
	 * @param logger
	 *            需要记录到的logger
	 * @return
	 */
	public static double getDouble(JSONObject jsonObj, Object key) {
		try {
			String _key = String.valueOf(key);
			if (jsonObj.containsKey(_key)) {
				return jsonObj.getDouble(_key);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.JSON_ANALYZE_FAIL,
						"#GS.JsonUtils.getString", "Json format error"), e);
			}
		}
		return DEFAULT_DOUBLE;
	}

	/**
	 * 获取float类型数据
	 * 
	 * @param jsonObj
	 *            Json源
	 * @param key
	 * @param logger
	 *            需要记录到的logger
	 * @return
	 */
	public static float getFloat(JSONObject jsonObj, Object key) {
		try {
			String _key = String.valueOf(key);
			if (jsonObj.containsKey(_key)) {
				return (float) jsonObj.getDouble(_key);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.JSON_ANALYZE_FAIL,
						"#GS.JsonUtils.getString", "Json format error"), e);
			}
		}
		return DEFAULT_FLOAT;
	}

	/**
	 * 获取boolean类型数据
	 * 
	 * @param jsonObj
	 *            Json源
	 * @param key
	 * @param logger
	 *            需要记录到的logger
	 * @return
	 */
	public static boolean getBoolean(JSONObject jsonObj, Object key) {
		try {
			String _key = String.valueOf(key);
			if (jsonObj.containsKey(_key)) {
				return jsonObj.getBoolean(_key);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.JSON_ANALYZE_FAIL,
						"#GS.JsonUtils.getString", "Json format error"), e);
			}
		}
		return DEFAULT_BOOLEAN;
	}

	/**
	 * 获取obj类型数据
	 * 
	 * @param jsonObj
	 *            Json源
	 * @param key
	 * @param logger
	 *            需要记录到的logger
	 * @return
	 */
	public static Object getObject(JSONObject jsonObj, Object key) {
		try {
			String _key = String.valueOf(key);
			if (jsonObj.containsKey(_key)) {
				return jsonObj.get(_key);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.JSON_ANALYZE_FAIL,
						"#GS.JsonUtils.getString", "Json format error"), e);
			}
		}
		return null;
	}
	
	/**
	 * 判断JsonObject是否包含特定key
	 * @param jsonObj
	 * @param key
	 * @return
	 */
	public static boolean containsKey(JSONObject jsonObj, Object key) {
		try {
			String _key = String.valueOf(key);
			if (jsonObj.containsKey(_key)) {
				return true;
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.JSON_ANALYZE_FAIL,
						"#GS.JsonUtils.getString", "Json format error"), e);
			}
		}
		return false;
	}

	/**
	 * 获取JSONArray类型数据
	 * 
	 * @param jsonObj
	 *            Json源
	 * @param key
	 * @param logger
	 *            需要记录到的logger
	 * @return
	 */
	public static JSONArray getJSONArray(JSONObject jsonObj, Object key) {
		try {
			String _key = String.valueOf(key);
			if (jsonObj.containsKey(_key)) {
				return jsonObj.getJSONArray(_key);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.JSON_ANALYZE_FAIL,
						"#GS.JsonUtils.getString", "Json format error"), e);
			}
		}
		return null;
	}

	/**
	 * 获取JSONObject类型数据
	 * 
	 * @param jsonObj
	 *            Json源
	 * @param key
	 * @param logger
	 *            需要记录到的logger
	 * @return
	 */
	public static JSONObject getJSONObject(JSONObject jsonObj, Object key) {
		try {
			String _key = String.valueOf(key);
			if (jsonObj.containsKey(_key)) {
				return jsonObj.getJSONObject(_key);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.JSON_ANALYZE_FAIL,
						"#GS.JsonUtils.getString", "Json format error"), e);
			}
		}
		return null;
	}

	public static String listToJson(List<Map<String, Object>> list) {
		JSONArray array = JSONArray.fromObject(list);
		return array.toString();
	}

	public static List<Map<String, Object>> jsonToList(String jsonstr) {
		if (jsonstr == null) {
			return Collections.EMPTY_LIST;
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray array = JSONArray.fromObject(jsonstr);
		for (int i = 0; i < array.size(); i++) {
			JSONObject jsonObj = array.getJSONObject(i);
			Map map = (Map) JSONObject.toBean(jsonObj, Map.class);
			list.add(map);
		}
		return list;
	}
	
	public static String toJsonString(Object object){
		return JSONObject.fromObject(object).toString();
	}
}
