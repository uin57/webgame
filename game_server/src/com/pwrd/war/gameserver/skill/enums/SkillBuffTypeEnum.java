package com.pwrd.war.gameserver.skill.enums;

/**
 * 技能buff类型
 * buff类型编号：-1为普通攻击，0-99为buff，100-199为debuff，
 * 200-299为hot，300-399为dot，400-499为区域buff，
 * 500-599为区域debuff，600-699为区域hot，700-799为区域dot，
 * 800-899为增加障碍物、增加怪物	
 * @author zy
 *
 */
public enum SkillBuffTypeEnum {
	NONE(-1, SkillEffectTypeEnum.NONE),		//普通攻击
	
	ATK_BUFF(1, SkillEffectTypeEnum.BUFF_DEBUFF),		//攻击增加值
	DEF_BUFF(2, SkillEffectTypeEnum.BUFF_DEBUFF),		//防御增加值
	MAX_HP_BUFF(3, SkillEffectTypeEnum.BUFF_DEBUFF),	//最大血量增加值
	SPD_BUFF(4, SkillEffectTypeEnum.BUFF_DEBUFF),		//速度增加值
	ATK_RATE_BUFF(21, SkillEffectTypeEnum.BUFF_DEBUFF),	//攻击增加比例
	DEF_RATE_BUFF(22, SkillEffectTypeEnum.BUFF_DEBUFF),	//防御增加比例
	MAX_HP_RATE_BUFF(23, SkillEffectTypeEnum.BUFF_DEBUFF),	//最大血量增加比例
	SPD_RATE_BUFF(24, SkillEffectTypeEnum.BUFF_DEBUFF),		//速度增加比例
	HIT_RATE_BUFF(25, SkillEffectTypeEnum.BUFF_DEBUFF),		//命中率增加百分比
	CRI_RATE_BUFF(26, SkillEffectTypeEnum.BUFF_DEBUFF),		//暴击率增加百分比
	DODGE_RATE_BUFF(27, SkillEffectTypeEnum.BUFF_DEBUFF),	//闪避率增加百分比
	DMG_ENHANCE_RATE_BUFF(28, SkillEffectTypeEnum.BUFF_DEBUFF),	//伤害加深增加百分比
	DMG_RESIST_RATE_BUFF(29, SkillEffectTypeEnum.BUFF_DEBUFF),	//伤害减免增加百分比
	RX_RATE_BUFF(30, SkillEffectTypeEnum.BUFF_DEBUFF),		//韧性率增加百分比
	FEATURE_BACK_RATE_BUFF(41, SkillEffectTypeEnum.BUFF_DEBUFF),	//增加职业特性中击退效果产生概率的buff
	ANTI_STUN_BUFF(90, SkillEffectTypeEnum.BUFF_DEBUFF),	//免疫晕眩效果的buff
	ONE_DMG_BUFF(91, SkillEffectTypeEnum.BUFF_DEBUFF),		//无论何种伤害只掉1点血的buff
	ANTI_BACK_BUFF(92, SkillEffectTypeEnum.BUFF_DEBUFF),	//免疫各种击退效果的buff
	FEATURE_AVAILABLE_BUFF(93, SkillEffectTypeEnum.BUFF_DEBUFF),	//使职业特性产生效果的buff
	MUST_HIT_BUFF(94, SkillEffectTypeEnum.BUFF_DEBUFF),		//攻击必命中buff
	
	ATK_DEBUFF(101, SkillEffectTypeEnum.BUFF_DEBUFF),		//攻击减少值
	DEF_DEBUFF(102, SkillEffectTypeEnum.BUFF_DEBUFF),		//防御减少值
	MAX_HP_DEBUFF(103, SkillEffectTypeEnum.BUFF_DEBUFF),	//最大血量减少值
	SPD_DEBUFF(104, SkillEffectTypeEnum.BUFF_DEBUFF),		//速度减少值
	ATK_RATE_DEBUFF(121, SkillEffectTypeEnum.BUFF_DEBUFF),	//攻击减少比例
	DEF_RATE_DEBUFF(122, SkillEffectTypeEnum.BUFF_DEBUFF),	//防御减少比例
	MAX_HP_RATE_DEBUFF(123, SkillEffectTypeEnum.BUFF_DEBUFF),	//最大血量减少比例
	SPD_RATE_DEBUFF(124, SkillEffectTypeEnum.BUFF_DEBUFF),		//速度减少比例
	HIT_RATE_DEBUFF(125, SkillEffectTypeEnum.BUFF_DEBUFF),		//命中率减少百分比
	CRI_RATE_DEBUFF(126, SkillEffectTypeEnum.BUFF_DEBUFF),		//暴击率减少百分比
	DODGE_RATE_DEBUFF(127, SkillEffectTypeEnum.BUFF_DEBUFF),	//闪避率减少百分比
	DMG_ENHANCE_RATE_DEBUFF(128, SkillEffectTypeEnum.BUFF_DEBUFF),	//伤害加深减少百分比
	DMG_RESIST_RATE_DEBUFF(129, SkillEffectTypeEnum.BUFF_DEBUFF),	//伤害减免减少百分比
	RX_RATE_DEBUFF(130, SkillEffectTypeEnum.BUFF_DEBUFF),		//韧性率减少百分比
	STUN_DEBUFF(151, SkillEffectTypeEnum.BUFF_DEBUFF),	//晕眩
	
	HOT(201, SkillEffectTypeEnum.HOT_DOT),	//持续加血
	ATK_RATE_HOT(202, SkillEffectTypeEnum.HOT_DOT),	//持续加血，按照施法者攻击力百分比回血
	DOT(301, SkillEffectTypeEnum.HOT_DOT),	//持续减血
	
	ADD_BLOCK(801, SkillEffectTypeEnum.FUNCTION),		//增加障碍
	NORMAL_BACK(803, SkillEffectTypeEnum.FUNCTION),		//普通击退
	SPECIAL_BACK(804, SkillEffectTypeEnum.FUNCTION),	//特殊击退
	ADD_MORALE(805, SkillEffectTypeEnum.FUNCTION),		//增加士气
	ADD_HP(806, SkillEffectTypeEnum.FUNCTION),			//增加当前血量
	;
	
	private int id;
	private SkillEffectTypeEnum effect;
	
	SkillBuffTypeEnum(int id, SkillEffectTypeEnum effect) {
		this.id = id;
		this.effect = effect;
	}

	public static SkillBuffTypeEnum getTypeById(int id) {
		for (SkillBuffTypeEnum amountType : values()) {
			if (amountType.id == id) {
				return amountType;
			}
		}
		return NONE;
	}
	
	public int getId() {
		return id;
	}
	
	public SkillEffectTypeEnum getEffect() {
		return effect;
	}

}
