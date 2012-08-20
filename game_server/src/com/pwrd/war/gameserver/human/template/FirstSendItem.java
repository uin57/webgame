package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.BeanFieldNumber;

/**
 * 
 * 赠送的道具
 * 
 * 
 */
@ExcelRowBinding
public class FirstSendItem {

	/** 属性key字符串 从1到32 */
	@BeanFieldNumber(number = 1)
	private int itemTmplId;
	/** 属性值 */
	@BeanFieldNumber(number = 2)
	private int itemCount;

	public int getItemTmplId() {
		return itemTmplId;
	}

	public void setItemTmplId(int itemTmplId) {
		this.itemTmplId = itemTmplId;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	@Override
	public String toString() {
		return "FirstSendGiftItem [itemId=" + itemTmplId + ", itemAccount="
				+ itemCount + "]";
	}

}
