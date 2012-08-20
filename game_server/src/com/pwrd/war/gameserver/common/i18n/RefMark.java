package com.pwrd.war.gameserver.common.i18n;

import com.pwrd.war.core.template.TemplateService;

/**
 * 应用标记
 * 
 */
public interface RefMark<T> {

	/**
	 * 将标记转换成对应的html
	 * 
	 * @param obj
	 * @param mark
	 * @param lut
	 * @param templateService
	 * @return
	 * @throws Exception
	 */
	public String transToHtml(T obj, String mark, NameLookupTable lut,
			TemplateService templateService) throws Exception;
}
