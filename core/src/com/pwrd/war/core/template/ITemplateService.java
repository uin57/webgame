package com.pwrd.war.core.template;

import java.net.URL;
import java.util.Map;

/**
 * 
 * 
 * 
 */
public interface ITemplateService {

	/**
	 * 初始化配置文件，并加载excel脚本
	 * 
	 * @param cfgpath
	 *            配置文件路径
	 */
	void init(URL cfgpath);

	/**
	 * @param <T>
	 * @param id
	 * @param clazz
	 * @return
	 */
	<T extends TemplateObject> T get(int id, Class<T> clazz);

	/**
	 * @param <T>
	 * @param t
	 */
	<T extends TemplateObject> void add(T t);

	/**
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	<T extends TemplateObject> Map<Integer, T> getAll(Class<T> clazz);
	
	/**
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	<T extends TemplateObject> Map<Integer, T> removeAll(Class<T> clazz);
	
	/**
	 * 判断某一对象是否存在
	 * @param <T>
	 * @param id
	 * @param clazz
	 * @return
	 */
	<T extends TemplateObject> boolean isTemplateExist(int id, Class<T> clazz);
	
}
