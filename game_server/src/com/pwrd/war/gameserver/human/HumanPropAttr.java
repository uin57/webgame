package com.pwrd.war.gameserver.human;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

public enum HumanPropAttr implements IndexedEnum {
	
	/** 行为 */
	BEHAVIOR(1) ,
	/** 任务 */
	QUEST_DIARY(2) ,
	/** 背包页数 */
	BAG_PAGE_SIZE(3),
	/** 已开放的功能 */
	FUNC(4),
	/** 已完成的引导 */
	GUIDE(5), 
	/** 上一次登陆的年号 */
	LAST_LOGIN_YEAR(6),
	/** 节日活动 */
	FESTIVAL(7), 
	/** 城市迁徙 */
	CITY_MOVE(8), 
	/** 农田块数 */
	FARM_COUNT(9),
	/** 银矿块数 */
	SILVERORE_COUNT(10),
	/** 属臣数目 */
	SLAVE_COUNT(11),
	;

	private HumanPropAttr(int index) {
		this.index = index;
	}

	public final int index;

	@Override
	public int getIndex() {
		return index;
	}
	
	public void resolve(JsonPropDataHolder jsonPropDataHolder,String entryString){
		jsonPropDataHolder.loadJsonProp(entryString);
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.index);
	}

	private static final List<HumanPropAttr> values = IndexedEnumUtil.toIndexes(HumanPropAttr.values());

	public static HumanPropAttr valueOf(int index) {
		return EnumUtil.valueOf(values, index);
	}

}
