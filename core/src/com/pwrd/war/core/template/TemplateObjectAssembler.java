package com.pwrd.war.core.template;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.pwrd.war.common.exception.ConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.PoiUtils;

/**
 * 
 * 用来加载可Excel绑定的对象
 * 
 * 
 */
public class TemplateObjectAssembler {

	/** 指定多个值的时候，用来分隔的字符串 */
	private static final String ARRAY_SPLIT = ",";
	private static final String OBJATTRIBUTE_SPLIT = ",";
	private static final String COLLECTION_SPLIT = ";";
	public static final Map<Class<?>, Field[]> classFields = new HashMap<Class<?>, Field[]>(
			30, 0.8f);
	public static final Map<Class<?>, Method[]> classMethods = new HashMap<Class<?>, Method[]>(
			30, 0.8f);

	public void doAssemble(Object obj, HSSFRow row, Class<?> clazz, Map<Integer, I18NField> i18nField)
			throws Exception {
		Class<?> superClazz = clazz.getSuperclass();
		// 如果父类是一个绑定类，那么先处理
		if (superClazz != null
				&& superClazz.isAnnotationPresent(ExcelRowBinding.class)) {
			doAssemble(obj, row, superClazz, i18nField);
		}
		Field[] fields = null;
		if (classFields.containsKey(clazz)) {
			fields = classFields.get(clazz);
		} else {
			fields = clazz.getDeclaredFields();
			Field.setAccessible(fields, true);
			classFields.put(clazz, fields);
		}
		for (int i = 0; i < fields.length; i++) {
			if ((fields[i].getModifiers() & Modifier.STATIC) != 0) {
				continue;
			} else if (fields[i].isAnnotationPresent(ExcelCellBinding.class)) {
				ExcelCellBinding binding = fields[i]
						.getAnnotation(ExcelCellBinding.class);
				Class<?> fieldType = fields[i].getType();
				Object fValue = getFieldValue(fields[i], row, fieldType,
						binding.offset(), i18nField);
				invokeSetMethod(clazz, fields[i], obj, fValue);
				// 处理本身是绑定的字段
			} else if (fields[i].isAnnotationPresent(ExcelRowBinding.class)) {
				Class<?> fieldType = fields[i].getType();
				Object subObject = fieldType.newInstance();
				invokeSetMethod(clazz, fields[i], obj, subObject);
				doAssemble(subObject, row, fieldType, i18nField);
			} else if (fields[i]
					.isAnnotationPresent(ExcelCollectionMapping.class)) {
				this.insertCollection(fields[i], obj, row, i18nField);
			}
		}
	}

	/**
	 * 在class为clazz的obj找到field的标准set方法，并调用将value作为set方法的参数
	 * 
	 * @param clazz
	 * @param field
	 * @param obj
	 * @param value
	 * @return 调用是否成功
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static boolean invokeSetMethod(Class<?> clazz, Field field,
			Object obj, Object value) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException {
		String name = field.getName();
		StringBuilder mNameBuilder = new StringBuilder();
		mNameBuilder.append("set");
		Class<?> fType = field.getType();
		if ((fType == boolean.class || fType == Boolean.class)
				&& name.startsWith("is")) {
			// boolean 值set方法不带is
			mNameBuilder.append(name.substring(2, 3).toUpperCase());
			mNameBuilder.append(name.substring(3));
		} else {
			mNameBuilder.append(name.substring(0, 1).toUpperCase());
			mNameBuilder.append(name.substring(1));
		}
		String methodName = mNameBuilder.toString();
		Method[] methods = null;
		if (TemplateObjectAssembler.classMethods.containsKey(clazz)) {
			methods = TemplateObjectAssembler.classMethods.get(clazz);
		} else {
			methods = clazz.getDeclaredMethods();
			Method.setAccessible(methods, true);
			TemplateObjectAssembler.classMethods.put(clazz, methods);
		}

		Method setMethod = searchSetterMethod(methods, methodName, field
				.getType());
		setMethod.invoke(obj, value);
		return true;
	}

	private static Method searchSetterMethod(Method[] methods, String name,
			Class<?> parameterType) {
		Method m = null;
		String internedName = name.intern();
		for (int i = 0; i < methods.length; i++) {
			m = methods[i];
			if (m.getName() == internedName
					&& m.getParameterTypes().length == 1
					&& parameterType == m.getParameterTypes()[0])
				return m;
		}
		return null;
	}

	/**
	 * 初始化obj中的Collection属性；
	 * 
	 * @param field
	 * @param obj
	 * @param row
	 * @throws ScriptRuleException
	 */
	@SuppressWarnings("unchecked")
	private void insertCollection(Field field, Object obj, HSSFRow row, Map<Integer, I18NField> i18nField) {
		ExcelCollectionMapping ecm = field
				.getAnnotation(ExcelCollectionMapping.class);
		Class<?> fieldType = field.getType();
		try {

			for (Class<?> tmp : field.getType().getInterfaces()) {
				if (tmp == List.class || tmp == Set.class || tmp == Map.class) {
					fieldType = tmp;
				}
			}

			if (fieldType == List.class) {
				List<Object> list = (List<Object>) field.get(obj);
				List<Object> fieldValue = list;
				if (fieldValue == null)
					fieldValue = new ArrayList<Object>();
				this.insertSetOrList(fieldValue, ecm, row, i18nField);
				invokeSetMethod(field.getDeclaringClass(), field, obj,
						fieldValue);
			} else if (fieldType == Set.class) {
				Set<Object> fieldValue = (Set<Object>) field.get(obj);
				if (fieldValue == null)
					fieldValue = new HashSet<Object>();
				this.insertSetOrList(fieldValue, ecm, row, i18nField);
				invokeSetMethod(field.getDeclaringClass(), field, obj,
						fieldValue);
			} else if (fieldType == Map.class) {
				Map<String, Object> fieldValue = (Map<String, Object>) field
						.get(obj);
				if (fieldValue == null)
					fieldValue = new HashMap<String, Object>();
				this.insertMap(fieldValue, ecm, row, i18nField);
				invokeSetMethod(field.getDeclaringClass(), field, obj,
						fieldValue);
			} else if (fieldType.isArray()) {
				Object arr = this.getArray(fieldType, ecm, row, i18nField);
				invokeSetMethod(field.getDeclaringClass(), field, obj, arr);
			} else {
				throw new ConfigException("Unsupported field type :"
						+ fieldType);
			}
		} catch (Exception e) {
			throw new ConfigException(e);
		}
	}

	private Object getArray(Class<?> fieldType, ExcelCollectionMapping ecm,
			HSSFRow row , Map<Integer, I18NField> i18nField) throws Exception {
		Class<?> element_cl = fieldType.getComponentType();
		String cn = ecm.collectionNumber();
		String[] cns = cn.split(COLLECTION_SPLIT);
		Object arr = Array.newInstance(element_cl, cns.length);
		for (int i = 0; i < cns.length; i++) {
			String[] strs = cns[i].split(OBJATTRIBUTE_SPLIT);
			Object o = null;
			if (element_cl.isAnnotationPresent(ExcelRowBinding.class))
				o = this.getElementObject(element_cl, strs, row, i18nField); // 处理自定义类的情况
			else {// 处理基本类型
				if (strs.length > 1)
					throw new ConfigException("cell's number greater than 1");
				o = this.getFieldValue(null, row, element_cl, new Integer(strs[0]), i18nField);
			}
			Array.set(arr, i, o);
		}
		return arr;
	}

	private void insertSetOrList(Collection<Object> col,
			ExcelCollectionMapping ecm, HSSFRow row, Map<Integer, I18NField> i18nField) throws Exception {
		Class<?> element_cl = ecm.clazz();
		String cn = ecm.collectionNumber();
		String[] cns = cn.split(COLLECTION_SPLIT);
		for (String str : cns) {
			String[] strs = str.split(OBJATTRIBUTE_SPLIT);

			Object o = null;
			if (element_cl.isAnnotationPresent(ExcelRowBinding.class))
				o = this.getElementObject(element_cl, strs, row, i18nField); // 处理自定义类的情况
			else {// 处理基本类型
				if (strs.length > 1)
					throw new ConfigException(String.format(
							"cell's number greater than 1 on row(%d)", row
									.getRowNum()));
				o = this.getFieldValue(null, row, element_cl, new Integer( strs[0]), i18nField);
			}
			col.add(o);
		}
	}

	private void insertMap(Map<String, Object> map, ExcelCollectionMapping ecm,
			HSSFRow row, Map<Integer, I18NField> i18nField) throws Exception {
		Class<?> element_cl = ecm.clazz();
		String cn = ecm.collectionNumber();
		String[] cns = cn.split(COLLECTION_SPLIT);
		for (String str : cns) {
			String[] strs = str.split(OBJATTRIBUTE_SPLIT);
			String keyCell = strs[0];
			HSSFCell cell = row.getCell(Integer.parseInt(keyCell));
			String key = PoiUtils.getStringValue(cell);
			String[] strs_other = new String[strs.length - 1];
			for (int i = 1; i < strs.length; i++)
				strs_other[i - 1] = strs[i];
			Object o = null;
			if (element_cl.isAnnotationPresent(ExcelRowBinding.class)) {
				o = this.getElementObject(element_cl, strs_other, row, i18nField);
			} else {
				// if (strs.length > 1)
				// throw new ConfigException("cell's number greater than 1");
				o = this.getFieldValue(null, row, element_cl, new Integer(strs_other[0]), i18nField);
			}
			map.put(key, o);
		}
	}

	/**
	 * 根据Excel表格，组装集合（Map、List、Set）中的一个元素；
	 * 
	 * @param clazz
	 *            集合中元素的Class对象
	 * @param strs
	 *            对应的excel表格号，如{7,8,9}
	 * @param row
	 * @return
	 */
	private Object getElementObject(Class<?> clazz, String[] strs, HSSFRow row, Map<Integer, I18NField> i18nField) {
		Object member_obj = null;
		try {
			member_obj = clazz.newInstance();

			Field[] fields = null;
			if (classFields.containsKey(clazz)) {
				fields = classFields.get(clazz);
			} else {
				fields = clazz.getDeclaredFields();
				Field.setAccessible(fields, true);
				classFields.put(clazz, fields);
			}

			Map<Integer, Field> refFields = new HashMap<Integer, Field>();
			for (Field field : fields) {
				if (field.isAnnotationPresent(BeanFieldNumber.class)) {
					refFields.put(field.getAnnotation(BeanFieldNumber.class)
							.number(), field);
				}
			}

			for (int j = 0; j < strs.length; j++) {
				int number = Integer.parseInt(strs[j]);
				Object fValue;
				Field curField = refFields.get(j + 1);

				if (curField == null) {
					continue;
				}

				fValue = getFieldValue(curField, row, curField.getType(), number, i18nField);
				invokeSetMethod(curField.getDeclaringClass(), curField,
						member_obj, fValue);
			}
		} catch (Exception e) {
			throw new ConfigException(e);
		}
		return member_obj;
	}

	/**
	 * 根据类型自动获取值
	 * 
	 * @param field
	 * @param row
	 * @param fieldType
	 * @param offset
	 * @return
	 * @throws Exception
	 */
	private Object getFieldValue(Field field, HSSFRow row, Class<?> fieldType,
			int offset, Map<Integer, I18NField> i18nField) throws Exception {
		HSSFCell cell = row.getCell(offset);
		if (fieldType == int.class || fieldType == Integer.class) {
			return PoiUtils.getIntValue(cell);
		} else if (fieldType == long.class || fieldType == Long.class) {
			return (long) PoiUtils.getDoubleValue(cell);
		} else if (fieldType == short.class || fieldType == Short.class) {
			return PoiUtils.getShortValue(cell);
		} else if (fieldType == byte.class || fieldType == Byte.class) {
			return PoiUtils.getByteValue(cell);
		} else if (fieldType == double.class || fieldType == Double.class) {
			return PoiUtils.getDoubleValue(cell);
		} else if (fieldType == float.class || fieldType == Float.class) {
			return PoiUtils.getFloatValue(cell);
		} else if (fieldType == Date.class) {
			return PoiUtils.getDateValue(cell, null);
		} else if (fieldType == Calendar.class) {
			return PoiUtils.getCalendarValue(cell);
		} else if (fieldType == String.class) {
			String str = PoiUtils.getStringValue(cell);
			if(i18nField.get(offset) != null && !StringUtils.isEmpty(str)){
				str = i18nField.get(offset).getId(row.getRowNum());
			}
			return str;
		} else if (fieldType == Boolean.class || fieldType == boolean.class) {
			int v = PoiUtils.getIntValue(cell);
			if (v == 0)
				return Boolean.FALSE;
			if (v == 1)
				return Boolean.TRUE;
			throw new ConfigException("boolean type value error :" + v);
		} else if (fieldType.isEnum()
				&& IndexedEnum.class.isAssignableFrom(fieldType)) {
			int v = PoiUtils.getIntValue(cell);
			IndexedEnum[] values = (IndexedEnum[]) fieldType.getEnumConstants();
			for (int i = 0; i < values.length; i++) {
				if (values[i].getIndex() == v) {
					return values[i];
				}
			}
			throw new ConfigException("Illegal Enum value:" + v);

			// 是一个数组
		} else if (fieldType.isArray()) {
			Class<?> componentType = fieldType.getComponentType();
			String v = PoiUtils.getStringValue(cell);
			String vs[] = v.split(ARRAY_SPLIT);
			if (componentType == String.class)
				return vs;
			if (vs.length == 1 && vs[0].trim().equals("")) {// 如果是空白字符串，那么直接返回
				return Array.newInstance(componentType, 0);
			}
			Object result = Array.newInstance(componentType, vs.length);

			convertValueToType(vs, result, componentType);
			return result;

		}
		throw new ConfigException("Unsupported field type :" + fieldType);
	}

	/**
	 * 把字符串数组里的元素挨个转换到目标类型
	 * 
	 * @param values
	 * @param result
	 * @param fieldType
	 */
	private void convertValueToType(String[] values, Object result,
			Class<?> fieldType) {

		if (fieldType == int.class || fieldType == Integer.class) {
			for (int i = 0; i < values.length; i++)
				Array.setInt(result, i, (int) Double.parseDouble(values[i]));
		} else if (fieldType == long.class || fieldType == Long.class) {
			for (int i = 0; i < values.length; i++)
				Array.setLong(result, i, (long) Double.parseDouble(values[i]));
		} else if (fieldType == short.class || fieldType == Short.class) {
			for (int i = 0; i < values.length; i++)
				Array
						.setShort(result, i, (short) Double
								.parseDouble(values[i]));
		} else if (fieldType == double.class || fieldType == Double.class) {
			for (int i = 0; i < values.length; i++)
				Array.setDouble(result, i, Double.parseDouble(values[i]));
		} else if (fieldType == float.class || fieldType == Float.class) {
			for (int i = 0; i < values.length; i++)
				Array
						.setFloat(result, i, (float) Double
								.parseDouble(values[i]));
		} else if (fieldType == Boolean.class || fieldType == boolean.class) {
			for (int i = 0; i < values.length; i++) {
				int v = (int) Double.parseDouble(values[i]);
				if (v == 0) {
					Array.setBoolean(result, i, Boolean.FALSE);
				} else if (v == 1) {
					Array.setBoolean(result, i, Boolean.TRUE);
				} else {
					throw new ConfigException("boolean type value error :" + v);
				}
			}

		} else if (fieldType.isEnum()
				&& IndexedEnum.class.isAssignableFrom(fieldType)) {
			for (int i = 0; i < values.length; i++) {
				int v = (int) Double.parseDouble(values[i]);
				boolean isSet = false;
				IndexedEnum[] enums = (IndexedEnum[]) fieldType
						.getEnumConstants();
				for (int j = 0; j < enums.length; j++) {
					if (enums[j].getIndex() == v) {
						Array.set(result, i, enums[j]);
						isSet = true;
						break;
					}
				}
				if (!isSet) {
					throw new ConfigException("Illegal Enum value:" + v);
				}
			}
		} else {
			throw new ConfigException("Unsupported Array component type  :"
					+ fieldType);
		}
	}
}
