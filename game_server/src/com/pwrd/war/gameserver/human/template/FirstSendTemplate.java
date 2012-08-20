package com.pwrd.war.gameserver.human.template;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pwrd.war.common.exception.ConfigException;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.common.model.template.RoleTemplate;
import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.ExcelCollectionMapping;
import com.pwrd.war.gameserver.human.wealth.Bindable;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.item.ItemService;

/**
 * 初始化模板，角色创建后第一次登陆系统赠予的道具
 * 
 *  
 */
@ExcelRowBinding
public class FirstSendTemplate extends RoleTemplate {
	
	/**  国家 */
	@ExcelCellBinding(offset = 1)
	protected int alliance;

	/**  性别 */
	@ExcelCellBinding(offset = 2)
	protected int sex;
	
	/** 赠送道具 */
	private List<ItemParam> firstSendItems;

	/** 赠送道具（读模板用） */
	@ExcelCollectionMapping(clazz = FirstSendItem.class, collectionNumber = "3,4;5,6;7,8;9,10;11,12")
	private List<FirstSendItem> tempFirstSendItems = null;
	
	public int getAlliance() {
		return alliance;
	}

	public void setAlliance(int alliance) {
		this.alliance = alliance;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public List<ItemParam> getFirstSendItems() {
		return firstSendItems;
	}

	public void setTempFirstSendItems(List<FirstSendItem> tempFirstSendItems) {
		this.tempFirstSendItems = tempFirstSendItems;
	}

	@Override
	public void check() throws TemplateConfigException {
		
	}
	
	/**
	 * 校正数值，管理者有责任在使用前调用该方法
	 */
	public void revise(ItemService itemService) {
		// 检查数据
//		for (Iterator<FirstSendItem> iter = tempFirstSendItems.iterator(); iter
//				.hasNext();) {
//			FirstSendItem _firtsSandItem = iter.next();
//			if (_firtsSandItem.getItemTmplId() == 0) { // 读模板的工具会把int类型的空cell读成0
//				iter.remove();
//			} else {
//				if (itemService.getItemTempByTempId(_firtsSandItem
//						.getItemTmplId()) == null) {
//					throw new TemplateConfigException(getSheetName(), this.getId(),
//							String.format("赠送的物品(%d)不存在", _firtsSandItem.getItemTmplId()));
//				}
//				if (_firtsSandItem.getItemCount() == 0)
//					throw new ConfigException("首次登陆给予的item对象[id="
//							+ _firtsSandItem.getItemTmplId() + "]个数为零!");
//			}
//		}
//
//		// 构造赠送的物品
//		firstSendItems = new ArrayList<ItemParam>();
//		for (FirstSendItem sendItem : tempFirstSendItems) {
////			firstSendItems.add(new ItemParam(sendItem.getItemTmplId(), sendItem
////					.getItemCount(), Bindable.BindStatus.BIND_YET));
//		}
//		tempFirstSendItems.clear();
//		tempFirstSendItems = null;
	}

}
