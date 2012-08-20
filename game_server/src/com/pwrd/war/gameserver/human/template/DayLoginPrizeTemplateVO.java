package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import java.util.Map;
import com.pwrd.war.core.template.ExcelCollectionMapping;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 登陆礼包模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class DayLoginPrizeTemplateVO extends TemplateObject {

	/** 天 */
	@ExcelCellBinding(offset = 1)
	protected short day;

	/** 奖励列表 */
	@ExcelCollectionMapping(clazz = String.class, collectionNumber = "2,3;4,5;6,7;8,9;10,11;12,13")
	protected Map<String,String> gifts;


	public short getDay() {
		return this.day;
	}

	public void setDay(short day) {
		this.day = day;
	}
	
	public Map<String,String> getGifts() {
		return this.gifts;
	}

	public void setGifts(Map<String,String> gifts) {
		if (gifts == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[奖励列表]gifts不可以为空");
		}	
		this.gifts = gifts;
	}
	

	@Override
	public String toString() {
		return "DayLoginPrizeTemplateVO[day=" + day + ",gifts=" + gifts + ",]";

	}
}