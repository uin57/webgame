package com.pwrd.war.gameserver.item;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.gameserver.human.wealth.Bindable;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;

/**
 * 道具参数，各系统向道具系统发请求时使用
 * 
 * 
 */
public class ItemParam {
	/** 模板SN */
	private String itemSN;
	/** 数量 */
	private int count;
	/** 绑定情况 */
	private BindStatus bind;

	public ItemParam(String itemSN, int count, BindStatus bind) {
		super();
		this.itemSN = itemSN;
		this.count = count;
		this.bind = bind;
	}

	public String getItemSN() {
		return this.itemSN;
	}

	public void setItemSN(String itemSN) {
		this.itemSN = itemSN;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Bindable.BindStatus getBind() {
		return bind;
	}

	public void setBind(Bindable.BindStatus bind) {
		this.bind = bind;
	}
	
	/**
	 * 根据道具模板id，和绑定状态生成一个唯一的long型key
	 * 
	 * @return
	 */
	public String getKeyByTmplIdAndBind() {
//		return Item.genKeyByTmplIdAndBind(templateId, bind);
		return this.itemSN+"_"+bind;
	}
	
	/**
	 * 将输入中具有相同ItemId和Bind的ItemParam合并为
	 * 
	 * @param params
	 *            要个并的ItemParam
	 * @param mode
	 *            合并模式
	 * @return
	 */
	public static Collection<ItemParam> mergeByTmplId(Collection<ItemParam> params, MergeMode mode) {
		Map<String, ItemParam> merged = new HashMap<String, ItemParam>();
		for (ItemParam param : params) {
			String itemSN = param.getItemSN();
			BindStatus bind = param.getBind();
			String key = "";
			if (mode == MergeMode.CONSIDER_BIND) {
				key = param.getKeyByTmplIdAndBind();
			} else {
				key = param.getItemSN();
			}
			ItemParam existed = merged.get(key);
			if (existed == null) {
				existed = new ItemParam(itemSN, 0, bind);
				merged.put(itemSN, existed);
			}
			existed.setCount(existed.getCount() + param.getCount());
		}
		return merged.values();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bind == null) ? 0 : bind.hashCode());
		result = prime * result + count;
		result = prime * result + itemSN.hashCode();
//		result = prime * result + templateId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemParam other = (ItemParam) obj;
		if (bind == null) {
			if (other.bind != null)
				return false;
		} else if (!bind.equals(other.bind))
			return false;
		if (count != other.count)
			return false;
		if (!itemSN .equals(other.itemSN))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemParam [bind=" + bind + ", count=" + count + ", itemSN=" + itemSN + "]";
	}
	
	public static enum MergeMode {
		/** 区分绑定状态 */
		CONSIDER_BIND,
		/** 不区分绑定状态 */
		IGNORE_BIND;
	}

}
