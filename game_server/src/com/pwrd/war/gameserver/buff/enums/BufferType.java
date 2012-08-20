package com.pwrd.war.gameserver.buff.enums;

public enum BufferType {
	/** 金钱 */
	money(1),
	/** 经验 */
	experience(2),
	/** 攻击加成 */
	attack(3),
	/** 防御加成 */
	defense(4),
	/** 生命加成 */
	life(5),
	/** 体力 */
	vitality(6),
	/** 药品 */
	medicinal(7),
	/** 变身 */
	change(8),
	/** vip */
	vip(9);
	

	private int id;

	BufferType(int id) {
		this.id = id;
	}

	public static BufferType getBufferTypeById(int id){
		for (BufferType bufferType : BufferType.values()) {
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
