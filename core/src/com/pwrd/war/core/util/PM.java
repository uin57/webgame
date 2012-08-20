package com.pwrd.war.core.util;

import java.util.List;

/**
 * PMInner的静态包装类
 *  * 用法 PM.kv("cid","xxxx").kv("pid","xxxx").tos()
 * @author wenpin.qian
 *
 */
public class PM {
	
	public static PMInner kv(String key,int value){
		return kv(key,"" + value);
	}
	
	public static PMInner kv(String key,long value){
		return kv(key,"" + value);
	}
	
	public static PMInner kv(String key,boolean value){
		return kv(key,"" + value);
	}
	
	public static PMInner kv(String key,short value){
		return kv(key,"" + value);
	}
	
	public static PMInner kv(String key,String value){
		PMInner _pm = new PMInner(3);
		_pm.kv(key, value);
		return _pm;
	}
	
	public static PMInner kv(String key,List<String> values){
		PMInner _pm = new PMInner(3);
		_pm.kv(key, values);
		return _pm;
	}
	
	public static PMInner kv(String key,String[] values){
		PMInner _pm = new PMInner(3);
		_pm.kv(key, values);
		return _pm;
	}
}
