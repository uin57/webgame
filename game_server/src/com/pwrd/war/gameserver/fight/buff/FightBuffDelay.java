package com.pwrd.war.gameserver.fight.buff;

import com.pwrd.war.gameserver.skill.enums.SkillBuffTypeEnum;


/**
 * 延迟生效的普通buff、debuff对象，具有起始回合信息
 * @author zy
 *
 */
public class FightBuffDelay extends FightBuff {
	protected int startRound;	//起始回合
	
	public FightBuffDelay(int buffSn, SkillBuffTypeEnum buffType, int buffValue, int endRound, boolean canRemove, int startRound) {
		super(buffSn, buffType, buffValue, endRound, canRemove);
		this.startRound = startRound;
	}

	@Override
	public boolean isStart(int currentRound) {
		//当达到起始回合时才开始生效
		return (currentRound >= startRound);
	}

	@Override
	public boolean isEffect(int currentRound) {
		//当达到起始回合时才开始生效
		return (currentRound >= startRound);
	}
	
}
