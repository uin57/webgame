package com.pwrd.war.gameserver.item;

import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.template.ItemTemplate;

/**
 * 处理使用道具时需要的道具基本信息,主要包括与item功能密切相关的属性，如有需要可以进一步扩展此类
 * 
 * 
 */
public class ItemUseInfo {

	/** 被使用消耗品的道具模板 */
	private ItemTemplate template;
	/** 将要被使用的道具 */
	private Item itemToUse;
	/** 道具所在包裹 */
	private BagType bag;
	/** 道具所在索引 */
	private int index;
	/** 道具UUID */
	private String uuid;
	/** 道具未使用时的耐久 */
	private int endure;
	/** 绑定状态 */
	private BindStatus bind;
	
	/** 额外参数 **/
	private String params;
	
	public ItemTemplate getTemplate() {
		return template;
	}

	public void setTemplate(ItemTemplate template) {
		this.template = template;
	}

	/**
	 * 返回将要被使用的item引用，由于使用道具时先执行扣减，这个item可能已经为空，其属性可能被删除
	 * 
	 * @return
	 */
	public Item getItemToUse() {
		return itemToUse;
	}

	public void setItemToUse(Item itemToUse) {
		this.itemToUse = itemToUse;
	}

	public BagType getBag() {
		return bag;
	}

	public void setBag(BagType bag) {
		this.bag = bag;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getEndure() {
		return endure;
	}

	public void setEndure(int endure) {
		this.endure = endure;
	}

	public BindStatus getBind() {
		return bind;
	}

	public void setBind(BindStatus bind) {
		this.bind = bind;
	}

	@Override
	public String toString() {
		return "ItemUseInfo [bag=" + bag + ", bind=" + bind + ", endure="
				+ endure + ", index=" + index + ", itemToUse=" + itemToUse
				+ ", template=" + template + ", uuid=" + uuid + "]";
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
