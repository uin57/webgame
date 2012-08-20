package com.pwrd.war.gameserver.buff.enums;

public enum SkillBufferType {

	NONE(0),
	/** 增加攻击 */
	INCR_ATK(1),
	/** 减少攻击 */
	REDUCE_ATK(2),
	/** 增加防御 */
	INCR_DEF(3),
	/** 减少防御 */
	REDUCE_DEF(4),
	/** 流血效果 */
	BLEEN(5),
	/** 减少命中 */
	REDUCE_MINGZHONG(6),
	/** 增加闪避 */
	INCR_SHANBI(7),
	/** 增加暴击 */
	INCR_CRI(8),
	/** 减少反击 */
	REDUCE_FANJI(9),
	/** 减少连击 */
	REDUCE_LIANJI(10),
	/** 速度增加 */
	INCR_SPD(11),
	/** 恢复血量 */
	RENEW_HP(12),
	/** 昏迷 */
	COMA(13),
	/** 混乱 */
	CHAOS(14),
	/** 睡眠 */
	SLEEP(15),
	/** 增加伤害 */
	INC_SHANGHAI(16),
	/** 受到伤害降低 */
	REDUCE_SHANGHAI(17),
	/** 死亡后伤害增加*/
	DEATH_INC_SHANGHAI(18);

	private int id;

	SkillBufferType(int id) {
		this.id = id;
	}

	public static SkillBufferType getBufferTypeByLabel(int id) {
		for (SkillBufferType bufferType : SkillBufferType.values()) {
			if (bufferType.getId() == id) {
				return bufferType;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
