package com.pwrd.war.core.util;

import java.lang.reflect.Field;

/**
 * Java反射的工具类
 * 
 * 
 */
public class RefUtil {

	/**
	 * 取得指定对象的属性值
	 * 
	 * @param obj
	 * @param name
	 *            属性的名称
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValue(Object obj, String name) throws Exception {
		Field _field = findField(obj, name);
		_field.setAccessible(true);
		return _field.get(obj);
	}

	/**
	 * 设定指定对象的属性值
	 * 
	 * @param obj
	 * @param name
	 *            属性名称
	 * @param value
	 *            属性的值
	 * @return
	 * @throws Exception
	 */
	public static void setFieldValue(Object obj, String name, Object value) throws Exception {
		Field _field = findField(obj, name);
		_field.setAccessible(true);
		_field.set(obj, value);
	}

	/**
	 * 查找属性的对象
	 * 
	 * @param obj
	 * @param name
	 * @return
	 * @throws NoSuchFieldException
	 */
	private static Field findField(Object obj, String name) throws NoSuchFieldException {
		Field _field = null;
		Class<?> _clazz = obj.getClass();
		while (true) {
			try {
				_field = _clazz.getDeclaredField(name);
				break;
			} catch (NoSuchFieldException ne) {
				_clazz = _clazz.getSuperclass();
			}
			if (_clazz == Object.class) {
				break;
			}
		}
		if (_field == null) {
			throw new NoSuchFieldException(name);
		}
		return _field;
	}

}
