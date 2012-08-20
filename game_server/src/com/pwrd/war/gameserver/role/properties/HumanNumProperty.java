package com.pwrd.war.gameserver.role.properties;

import com.pwrd.war.core.annotation.Comment;
import com.pwrd.war.core.annotation.Type;


/**
 * 人物一级属性数据对象 
 *  
 */
@Comment(content = "人物一级属性")
public class HumanNumProperty extends PropertyObject {
	
	/** 一级属性索引开始值 */
	public static int _BEGIN = 0;
	
	/** 一级属性索引结束值 */
	public static int _END = _BEGIN;
	
	
	
	@Comment(content = "称号")
	@Type(Integer.class)
	public static final int TITLE = ++_END;
	

	@Comment(content = "当前体力")
	@Type(Integer.class)
	public static final int VITALITY = ++_END;
	
	@Comment(content = "最大体力")
	@Type(Integer.class)
	public static final int MAX_VITALITY = ++_END;
		
	
	
	@Comment(content = "魅力")
	@Type(Integer.class)
	public static final int CHARM = ++_END;
	

	@Comment(content = "护花值")
	@Type(Integer.class)
	public static final int PROTECTFLOWER = ++_END;
	

	@Comment(content = "官职")
	@Type(Integer.class)
	public static final int OFFICAL = ++_END;
	
 
	@Comment(content = "荣誉 ")
	@Type(Integer.class)
	public static final int HONOUR = ++_END;
	

	@Comment(content = "杀戮")
	@Type(Integer.class)
	public static final int MASSACRE = ++_END;
	

	@Comment(content = "战功值")
	@Type(Integer.class)
	public static final int BATTLEACHIEVE = ++_END;
	

	@Comment(content = "阅历")
	@Type(Integer.class)
	public static final int SEE = ++_END;
	
	@Comment(content = "VIP")
	@Type(Integer.class)
	public static final int VIP = ++_END;	
	
	
	@Comment(content = "铜钱")
	@Type(Integer.class)
	public static final int COINS = ++_END;
	
//	@Comment(content = "铜币")
//	@Type(Integer.class)
//	public static final int SLIVER = ++_END;
	
	@Comment(content = "元宝")
	@Type(Integer.class)
	public static final int GOLD = ++_END;
	
	@Comment(content = "点券")
	@Type(Integer.class)
	public static final int COUPON = ++_END;
	
	@Comment(content = "声望")
	@Type(Integer.class)
	public static final int POPULARITY = ++_END;
	
	@Comment(content = "手动战斗状态设置")
	@Type(Integer.class)
	public static final int MUAN_BATTLE = ++_END;
	
	@Comment(content = "已开启强化队列数目")
	@Type(Integer.class)
	public static final int EQUIP_ENHANCE_NUM = ++_END;
	 
	@Comment(content = "是否已开启阵法")
	@Type(Integer.class)
	public static final int IS_OPEN_FORM = ++_END;
	
	@Comment(content = "之前的X坐标")
	@Type(Integer.class)
	public static final int BEFORE_X = ++_END;
	
	@Comment(content = "之前的Y坐标")
	@Type(Integer.class)
	public static final int BEFORE_Y= ++_END;
 	
	@Comment(content = "是否出战")
	@Type(Integer.class)
	public static final int ISIN_BATTLE = ++_END;
	
	@Comment(content = "今日购买体力次数")
	@Type(Integer.class)
	public static final int BUY_VIT_TIMES = ++_END;
	
	@Comment(content = "功勋")
	@Type(Integer.class)
	public static final int MERIT = ++_END;
	
	@Comment(content = "已充值元宝")
	@Type(Integer.class)
	public static final int CHARGE_AMOUNT = ++_END;
	
 
	
	@Comment(content = "玩家家族职位")
	@Type(Integer.class)
	public static final int FAMILY_ROLE = ++_END;
	
	@Comment(content = "魂石")
	@Type(Integer.class)
	public static final int HUNSHI = ++_END;
	
	@Comment(content = "VIP体验等级")
	@Type(Integer.class)
	public static final int BUFF_VIP = ++_END;
	
	
	/** 一级属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;
//	public static final int _SIZE = _END - _BEGIN;
	
	
	/**
	 * 是否是合法的索引
	 * 
	 * @param index
	 * @return
	 */
	public static final boolean isValidIndex(int index){
		return index>=0&&index<HumanNumProperty._SIZE;
	}

	public static final PropertyType TYPE = PropertyType.HUMAN_PROP_A;

	public HumanNumProperty() {
		super(_SIZE, TYPE);
	}
	

}
