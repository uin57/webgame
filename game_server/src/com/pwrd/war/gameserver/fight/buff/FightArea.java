package com.pwrd.war.gameserver.fight.buff;

import com.pwrd.war.gameserver.skill.enums.SkillBuffTypeEnum;

/**
 * 立即生效没有延迟的区域buff
 * @author zy
 *
 */
public class FightArea extends FightBuff {
	private int targetPos;		//区域buff中心位置
	private int areaSn;			//区域buff对应的编号
	private boolean isAttack;	//区域buff目标所属方

	public FightArea(int buffSn, SkillBuffTypeEnum buffType, int buffValue,
			int endRound, boolean canRemove, int targetPos, boolean isAttack,
			int areaSn) {
		super(buffSn, buffType, buffValue, endRound, canRemove);
		this.targetPos = targetPos;
		this.areaSn = areaSn;
		this.isAttack = isAttack;
	}

	public int getTargetPos() {
		return targetPos;
	}

	public boolean isAttack() {
		return isAttack;
	}

	public int getAreaSn() {
		return areaSn;
	}
	
}
