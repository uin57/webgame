package com.pwrd.war.gameserver.fight.buff;

import com.pwrd.war.gameserver.skill.enums.SkillBuffTypeEnum;


/**
 * 立即生效的普通hot、dot对象，具有开始回合和间隔回合信息
 * @author zy
 *
 */
public class FightHot extends FightBuff {
	protected int interval;	//间隔回合
	
	public FightHot(int buffSn, SkillBuffTypeEnum buffType, int buffValue, int endRound, boolean canRemove, int interval) {
		super(buffSn, buffType, buffValue, endRound, canRemove);
		this.interval = interval;
	}

	@Override
	public boolean isEffect(int currentRound) {
		//当结束回合与当前回合只差是间隔回合的整数倍时才生效
		return ((endRound - currentRound) % interval == 0);
	}

}
