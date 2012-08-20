package com.pwrd.war.gameserver.common.i18n;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.annotation.NotTranslate;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.template.TemplateObjectAssembler;
import com.pwrd.war.core.template.TemplateService;

/**
 * 
 * 标记翻译器的基本实现
 * 
 * @see MarkTranslator
 * @author liuli
 * @since 2010-5-20
 */
public class MarkTranslatorImpl implements MarkTranslator {

	private NameLookupTable lut;
	/** 递归翻译的最大深度 */
	private static final int MAX_DEPTH = 2;
	/** 递归翻译对象不包括的类型 */
	private static final Set<Class<?>> EXCLUDE_CLASSE_SET = new HashSet<Class<?>>();
	/** 要翻译的对象的全名前缀 */
	private static final String INCLUDE_PACKAGE_PREFIX;
	static {
		String thisClassName = MarkTranslatorImpl.class.getName();
		int end = 0;
		for (int i = 0; i < 2; i++) {
			end = thisClassName.indexOf(".", end + 1);
		}
		INCLUDE_PACKAGE_PREFIX = thisClassName.substring(0, end + 1);
		EXCLUDE_CLASSE_SET.add(Integer.class);
		EXCLUDE_CLASSE_SET.add(int.class);
		EXCLUDE_CLASSE_SET.add(int[].class);
		EXCLUDE_CLASSE_SET.add(Long.class);
		EXCLUDE_CLASSE_SET.add(long.class);
		EXCLUDE_CLASSE_SET.add(long[].class);
		EXCLUDE_CLASSE_SET.add(Short.class);
		EXCLUDE_CLASSE_SET.add(short.class);
		EXCLUDE_CLASSE_SET.add(short[].class);
		EXCLUDE_CLASSE_SET.add(Float.class);
		EXCLUDE_CLASSE_SET.add(float.class);
		EXCLUDE_CLASSE_SET.add(float[].class);
		EXCLUDE_CLASSE_SET.add(Double.class);
		EXCLUDE_CLASSE_SET.add(double.class);
		EXCLUDE_CLASSE_SET.add(double[].class);
		EXCLUDE_CLASSE_SET.add(String.class);
		EXCLUDE_CLASSE_SET.add(String[].class);
	}

	@Override
	public void bindLookupTable(NameLookupTable lut) {
		this.lut = lut;
	}

	/**
	 * 翻译所有模板中需要替换标签的字段
	 */
	@Override
	public void translateAllTemplate(TemplateService templateService)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Map<Class<?>, Map<Integer, TemplateObject>> templates = templateService
				.getAllClassTemplateMaps();
		boolean hasError = false;
		for (Map<Integer, TemplateObject> clazzMap : templates.values()) {
			for (TemplateObject templateObj : clazzMap.values()) {
				try {
					translateOne(templateObj, 0, templateObj.getId(), templateObj.getClass(),
							templateService);
				} catch (Exception e) {
					Loggers.gameLogger.error("启动翻译标记时发生错误 templateObj = " + templateObj + ",templateId:" + templateObj.getId(), e);
					hasError = true;
				}
			}
		}
		if (hasError) {
			System.exit(1);
		}
	}

	/**
	 * 翻译一个对象
	 */
	private void translateOne(Object obj, int depth, int templateId,
			Class<?> clazz, TemplateService templateService)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		if (obj == null) {
			return;
		}


		if (!clazz.getName().startsWith(INCLUDE_PACKAGE_PREFIX)) {
			// 被翻译的对象必须是com.imop下的，也就是此游戏项目定义的类
			return;
		}

		Class<?> superClazz = clazz.getSuperclass();
		// 如果父类是一个绑定类，那么先处理
		if (superClazz != null) {
			translateOne(obj, depth, templateId, superClazz, templateService);
		}

		depth++;
		Field[] fields = null;
		if (TemplateObjectAssembler.classFields.containsKey(clazz)) {
			fields = TemplateObjectAssembler.classFields.get(clazz);
		} else {
			fields = clazz.getDeclaredFields();
			TemplateObjectAssembler.classFields.put(clazz, fields);
		}

		if (fields == null || fields.length == 0) {
			return;
		}
		Field.setAccessible(fields, true);
		for (Field fd : fields) {

			Class<?> fdType = fd.getType();
			Object fdValueObj = fd.get(obj);
			if (fdValueObj == null) {
				continue;
			}
			if ((fd.getModifiers() & Modifier.STATIC) != 0) {
				continue;
			} else if (fdValueObj instanceof TemplateObject) {
				continue;
			} else if (fdType == String.class) {
				if (fd.isAnnotationPresent(NotTranslate.class)) {
					continue;
				}
				String sentence = (String) fdValueObj;
				sentence = MarkTranslatorService.translate(NameRefMark.pattern,
						NameRefMark.class, lut, obj, sentence, templateId,
						templateService);
				fd.set(obj, sentence);
			} else if (fdType == String[].class) {
				String[] array = (String[]) fdValueObj;
				for (String sentence : array) {
					sentence = MarkTranslatorService.translate(NameRefMark.pattern, 
							NameRefMark.class, lut, obj, sentence, templateId, 
							templateService);
				}
			} else if (!EXCLUDE_CLASSE_SET.contains(fdType)) {
				if (depth >= MAX_DEPTH) {
					continue;
				}
				translateObjField(obj, fd, depth, templateId, templateService);
			}
		}
	}

	/**
	 * 翻译自定义对象，即一级模板下组合的二级模板对象
	 */
	private void translateObjField(Object obj, Field fd, int depth,
			int templateId, TemplateService templateService)
			throws IllegalArgumentException, IllegalAccessException,
			SecurityException, NoSuchMethodException, InvocationTargetException {
		Class<?> fdType = fd.getType();
		Object fdValueObj = fd.get(obj);
		if (fdValueObj == null) {
			return;
		}
		if (fdType.isArray()) {
			Object[] array = (Object[]) fdValueObj;
			for (Object elem : array) {
				translateOne(elem, depth, templateId, elem.getClass(),
						templateService);
			}
			return;
		}

		Class<?>[] interfaces = fdType.getInterfaces();
		Class<?> tmp = null;
		for (Class<?> inter : interfaces) {
			if (inter == List.class || inter == Set.class || inter == Map.class
					|| inter == Collection.class) {
				tmp = inter;
				break;
			}
		}

		if (tmp == null) {
			translateOne(fdValueObj, depth, templateId, fdValueObj.getClass(),
					templateService);
			return;
		}

		if (tmp == List.class) {
			List<?> list = (List<?>) fdValueObj;
			for (Object elem : list) {
				translateOne(elem, depth, templateId, elem.getClass(),
						templateService);
			}
		} else if (tmp == Set.class) {
			Set<?> set = (Set<?>) fdValueObj;
			for (Object elem : set) {
				translateOne(elem, depth, templateId, elem.getClass(),
						templateService);
			}
		} else if (tmp == Map.class) {
			Map<?, ?> map = (Map<?, ?>) fdValueObj;
			for (Object elem : map.values()) {
				translateOne(elem, depth, templateId, elem.getClass(),
						templateService);
			}
		} else if (tmp == Collection.class) {
			Collection<?> collection = (Collection<?>) fdValueObj;
			for (Object elem : collection) {
				translateOne(elem, depth, templateId, elem.getClass(),
						templateService);
			}
		}
	}
	

}
