package com.pwrd.war.common.constants;


/**
 * 角色基本常量定义
 * 
 * 
 */
public class RoleConstants {

	// 性别
	/** 无性别 */
	public static final int NOSEX = 0;
	/** 男/雄 */
	public static final int MALE = 1;
	/** 女/雌 */
	public static final int FEMALE = 2;

	
	/** 人物初始等级 */
	public static final int HUMAN_INIT_LEVEL_NUM = 1;
	/** 人物最大等级 */
	public static final int HUMAN_MAX_LEVEL_NUM = 150;	
	/** 武将初始级别 */
	public static final short PET_INIT_LEVEL_NUM = 1;	
	/** 武将最多携带数 */
	public static final short PET_MAX_SIZE = 20;

	// 各种背包的初始容量
	/** 主背包初始页数 */
	public static final int PRIM_BAG_INIT_PAGE = 1;
	/** 主背包最大页数 */
	public static final int PRIM_BAG_MAX_PAGE = 4;
	/** 主背包每页容量 */
	public static final int PRIM_BAG_CAPACITY_PER_PAGE = 16;
	
	/** 最多穿8个装备 **/
	public static final int EQUIP_MAX = 8;
	
	/** 农田初始块数 */
	public static final int FARM_INIT_COUNT = 0;
	/** 银矿初始块数 */
	public static final int SILVERORE_INIT_COUNT = 0;
	/** 属臣初始个数 */
	public static final int SLAVE_INIT_COUNT = 0;

	/** 武将身上的装备包的容量 */
	public static final int PET_EQUIP_BAG_CAPACITY = 8;
	/**怪物自适应数量*/
	public static final int TRIGGER_MONSTER_TYPE_SELF_NUMBER=0;
	/**怪物固定数量*/
	public static final int TRIGGER_MONSTER_TYPE_FIXED_NUMBER=1;
	/**明雷中最多怪物数 */
	public static final int MAX_MONSTER=15;

}
