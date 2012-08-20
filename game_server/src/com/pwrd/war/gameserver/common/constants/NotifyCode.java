package com.pwrd.war.gameserver.common.constants;

/**
 * 通知客户端的GCNotifyException的所有code
 */
public enum NotifyCode {

	/**
	 * 正常
	 */
	OK(0),
	/** 角色名字重复 */
	DUPLICATE_ROLE_NAME(1000),

	/***** 场景 2000 ****/
//	SC_INVALID_SCENE(2000),
//	/** 升级技能时，等级不够 23001 */
//	NO_ENOUGH_LEVEL_IN_UPGRADE_SKILL(LangConstants.NO_ENOUGH_LEVEL_IN_UPGRADE_SKILL),
//	/** 升级技能时，阅历不够 23002 */
//	NO_ENOUGH_SEE_IN_UPGRADE_SKILL(LangConstants.NO_ENOUGH_SEE_IN_UPGRADE_SKILL),
//
//	/** 升级技能时，碎银不够 23003 */
//	NO_ENOUGH_MONEY_IN_UPGRADE_SKILL(LangConstants.NO_ENOUGH_MONEY_IN_UPGRADE_SKILL),
//	
//	/**技能升级时，没这个技能*/
//    NO_SUCH_SKILL_IN_UPGRADE_SKILL(LangConstants.NO_SUCH_SKILL_IN_UPGRADE_SKILL),
//	/** 职业技能排序时，技能重复 */
//	SKILL_INVALID_ORDER(LangConstants.SKILL_INVALID_ORDER),
//	
//	/** 阵型排序时，非法武将 */
//	INVALID_PET(LangConstants.SKILL_INVALID_ORDER),
//	/** 玩家正在战斗不能进行新的战斗 */
//	PLAYER_IN_BATTLE(LangConstants.PLAYER_IN_BATTLE),
//
//	/** 玩家等级过高不能进行战斗 */
//	PLAYER_LEVEL_HIGHT_IN_BATTLE(LangConstants.PLAYER_LEVEL_HIGHT_IN_BATTLE),
//	
//	/** 玩家等级过低不能进行战斗 */
//	PLAYER_LEVEL_LOWER_IN_BATTLE(LangConstants.PLAYER_LEVEL_LOWER_IN_BATTLE),
//	
//	/** 怪物正在战斗，不能进行战斗 */
//	MONSTER_IN_BATTLE(LangConstants.MONSTER_IN_BATTLE),
//	
//	MONSTER_IN_DEATH(1),
//	
//	/**没有该等级的阵型*/
//	NOT_SUCH_FORM(1000),
//	/**阅历不够*/
//	NOT_ENOUGH_SEE(10001),
//	/**铜钱不够*/
//	NOT_ENOUGH_MONEY(10002),
	;

	private short code;




	private NotifyCode(int code) {
		this.code = (short) code;
	}

	public static NotifyCode valueOf(short code) {
		for (NotifyCode value : NotifyCode.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		return null;
	}

	public short getCode() {
		return code;
	}
}
