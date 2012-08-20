package com.pwrd.war.gameserver.item.template;

/**
 * 物品模板属性
 * 
 * 
 */
public interface ItemTemplateFeature {
	
	/**
	 * 检验附加属性数据的正确性
	 * 
	 * @param template
	 */
	void check(ItemTemplate template);

	/**
	 * 创建feature对象
	 * 
	 * @param template
	 * @return
	 */
	void buildFeature(ItemTemplate template);
	
}
