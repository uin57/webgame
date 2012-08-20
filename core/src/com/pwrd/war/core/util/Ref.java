package com.pwrd.war.core.util;

/**
 * 
 * 描述一个类型的引用,通过id进行索取真正的内容
 * 
 * 用于解耦和延迟加载等情况
 * 
 *
 * @param <IdType>
 * @param <T>
 */
public class Ref<IdType extends java.io.Serializable,T> {
		
	private Class<T> clazz;
	
	private IdType id;
	
	public Ref(Class<T> clazz, IdType id){
		this.clazz = clazz;
		this.id = id;
	}

	public IdType getId() {
		return id;
	}
	
	public void setId(IdType id) {
		this.id = id;
	}
	
	public Class<T> getClazz() {
		return clazz;
	}
	
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

}
