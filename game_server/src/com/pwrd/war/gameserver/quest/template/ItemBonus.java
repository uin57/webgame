package com.pwrd.war.gameserver.quest.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.BeanFieldNumber;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.ItemParam;

/**
 * 
 * 并非真正的奖励，此对象主要是用来加载任务表中的物品奖励部分
 * 
 * 
 */
@ExcelRowBinding
public class ItemBonus {
	/** 奖励道具编号 */
	@BeanFieldNumber(number = 1)
	private String sn;
	/** 奖励道具数量 */
	@BeanFieldNumber(number = 2)
	private int count;

	/**
	 * 将物品奖励转换为物品对象，状态为已绑定
	 * @param questId
	 * @return 物品对象
	 */
	public ItemParam getItem(int questId) {
		if (sn.isEmpty()) {
			return null;
		}
		
		if (count <= 0) {
			throw new TemplateConfigException("任务主表", questId, "任务奖励道具数量必须大于0！");
		}

//		if (Globals.getItemService().getTemplateByItemSn(sn) == null) {
//			throw new TemplateConfigException("任务主表", questId, "任务奖励道具不存在，sn=" + sn);
//		}
		
		return new ItemParam(sn, count, BindStatus.BIND_YET);
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	

}
