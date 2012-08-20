package com.pwrd.war.gameserver.common.i18n;

import com.pwrd.war.core.template.TemplateService;

/**
 * 标记翻译器接口
 * 
 * @author liuli
 * @since 2010-5-20
 */
public interface MarkTranslator {

	/**
	 * 绑定名字查找表
	 * 
	 * @param lut id与name的对应表
	 */
	void bindLookupTable(NameLookupTable lut);

	/**
	 * 将所有TemplateObject中的多语言中的名字引用标签翻译出来
	 * 
	 * @param templateService
	 */
	void translateAllTemplate(TemplateService templateService) throws Exception;
}
