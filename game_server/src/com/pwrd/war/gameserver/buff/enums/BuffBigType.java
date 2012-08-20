package com.pwrd.war.gameserver.buff.enums;

/**
 * buff大类型
 */
public enum BuffBigType{
	/** 金钱 */
	item_money(1),
	/** 经验 */
	item_exp(2),
	/** 攻击加成 */
	item_attack(3),
	/** 防御加成 */
	item_defense(4),
	/** 生命加成 */
	item_life(5),
	/** 体力 */
	item_vitality(6),
	/** 药品 */
	item_medicinal(7),
	/** 变身 */
	item_change(8),
	/** 体力 */
	act_vitality(9),
	/** 活动经验buff */
	act_exp(10),
	/** 获得金钱buff */
	act_money(11),
	/** 鼓舞攻击加成 */
	guwu_attack(12),
	/** 犒赏攻击加成 */
	kaoshang_attack(13),
	/** VIP1级体验 */
	vip1(14),
	/** VIP2级体验 */
	vip2(15),
	/** VIP3级体验 */
	vip3(16),
	/** VIP4级体验 */
	vip4(17),
	/** VIP5级体验 */
	vip5(18),
	/** VIP6级体验 */
	vip6(19),
	/** VIP7级体验 */
	vip7(20),
	/** VIP8级体验 */
	vip8(21),
	/** VIP9级体验 */
	vip9(22),
	/** VIP10级体验 */
	vip10(23),
	/** VIP11级体验 */
	vip11(24),
	/** VIP12级体验 */
	vip12(25);
	
	private int id;

	BuffBigType(int id) {
		this.id = id;
	}

	public static BuffBigType getBufferTypeById(int id){
		for (BuffBigType bufferType : BuffBigType.values()) {
			if(bufferType.getId()==id){
				return bufferType;
			}
		}
		return null;
	}
	
	public int getId() {
		return id;
	}
}
