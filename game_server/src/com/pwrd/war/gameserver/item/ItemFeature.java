package com.pwrd.war.gameserver.item;


/**
 * 物品实例属性，例如装备的随机属性，宝石属性，消耗品的剩余使用量等
 * 这里定义装备、消耗品都有耐久的概念，对于装备即为损坏程度，对于消耗品为剩余使用量、剩余使用次数等
 * 
 */
public interface ItemFeature {
	
	/**
	 * 恢复为满耐久
	 */
	void recoverEndure();

	/**
	 * 获得当前耐久
	 * 
	 * @return
	 */
	int getCurEndure();

	/**
	 * 设置当前耐久度，此方法规格化输入参数，保证在区间[0,{@link #getCurEndure()}]内
	 * 
	 * @return
	 */
	void setCurEndure(int curEndure);

	/**
	 * 当前是否为满耐久
	 * 
	 * @return
	 */
	boolean isFullEndure();

	/**
	 * 当耐久上限
	 * 
	 * @return
	 */
	int getCurMaxEndure();
	
	/**
	 * 绑定一个新的Item
	 * 
	 * @param item
	 */
	void bindItem(Item item);

	/**
	 * feature复制到另一个feature
	 * @author xf
	 */
	public ItemFeature cloneTo(ItemFeature to);
	
	/**
	 * 转化为featureString给客户端
	 * @author xf
	 */
	public String toFeatureString();

	/**
	 * 转化成property字符串存储到数据库
	 */
	public String toPropertyString();
	
	/**
	 * 从property字符串恢复feature
	 */
	public void fromPropertyString(String propStr);
}
