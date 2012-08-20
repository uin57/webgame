package com.pwrd.war.common;

/**
 * 可作为模板的接口 
 *
 */
public interface Mouldable<T> {
	
	/**
	 * 模板级别的复制，不会复制原对象中的实例数值，只是根据
	 * 原对象的构造参数再次构造一个类似的对象。区别于clone。
	 * 
	 * @return
	 */
	public T module();
}
