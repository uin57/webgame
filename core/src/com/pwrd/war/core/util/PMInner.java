package com.pwrd.war.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * ParameterMap
 *
 */
public class PMInner{
	
	private List<String> keys;
	private List<String> values;
	
	private static final String NULL = "null";
	
	protected PMInner(int size){
		keys = new ArrayList<String>(size);
		values = new ArrayList<String>(size);
	}
	
	protected PMInner(){
		this(1);
	}
	
	public <E> PMInner kv(String key,E value){
		if(value == null){
			return this;
		}
		return kv(key,value.toString());
	}
	
	/**
	 * 添加键值对
	 * @param key
	 * @param value
	 * @return
	 */
	public PMInner kv(String key,String value){
		if(key == null || key.trim().length() == 0){
			return this;
		}
		if(value == null){
			value = NULL;
		}else if(value.trim().length() == 0){
			value = NULL;
		}else{
			value = value.trim();
		}
		keys.add(key);
		values.add(value);
		return this;
	}
	
	/**
	 * 添加键值对
	 * @param key
	 * @param vals
	 * @return
	 */
	public <E> PMInner kv(String key,List<E> vals){
		if(key == null || key.trim().length() == 0){
			return this;
		}
		keys.add(key);
		if(vals == null){
			values.add(NULL);
			return this;
		}
		values.add("{" + StringUtils.join(vals,',') + "}");
		return this;
	}
	
	/**
	 * 添加键值对
	 * @param key
	 * @param vals
	 * @return
	 */
	public <E> PMInner kv(String key,E[] vals){
		if(key == null || key.trim().length() == 0){
			return this;
		}
		if(vals == null){
			return kv(key,"");
		}
		return kv(key,Arrays.asList(vals));
	}
	
	/**
	 * 克隆一个
	 * @return
	 */
	public PMInner same(){
		PMInner _pm = new PMInner(4);
		_pm.keys.addAll(keys);
		_pm.values.addAll(values);
		return _pm;
	}
	
	/**
	 * 返回最终的paramString
	 * @return
	 */
	public String tos(){
		StringBuffer _buf = new StringBuffer();
		for(int i=0;i<keys.size();i++){
			_buf.append(keys.get(i));
			_buf.append('=');
			_buf.append(values.get(i));
			
			if(i < keys.size() - 1){
				_buf.append(';');
			}
		}
		return _buf.toString();
	}
	
	public String toString(){
		return tos();
	}
	
}
