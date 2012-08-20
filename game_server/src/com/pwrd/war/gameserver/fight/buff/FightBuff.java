package com.pwrd.war.gameserver.fight.buff;

import com.pwrd.war.gameserver.skill.enums.SkillBuffTypeEnum;


/**
 * 立即生效的普通buff、debuff对象
 * @author zy
 *
 */
public class FightBuff implements IFightBuff {
	private static int oneId = 1;
	protected int buffId;		//buff唯一序号
	protected int buffSn;		//buff编号
	protected SkillBuffTypeEnum buffType;	//buff类型
	protected int buffValue;	//buff数值
	protected int endRound;		//结束回合
	protected boolean canRemove;	//是否可移除
	
	public FightBuff(int buffSn, SkillBuffTypeEnum buffType, int buffValue, int endRound, boolean canRemove) {
		this.buffId = ++ oneId;
		this.buffSn = buffSn;
		this.buffType = buffType;
		this.buffValue = buffValue;
		this.endRound = endRound;
		this.canRemove = canRemove;
	}
	
	@Override
	public boolean canRemove() {
		return canRemove;
	}

	@Override
	public int getBuffId() {
		return buffId;
	}
	
	@Override
	public boolean isStart(int currentRound) {
		//普通buff一旦创建就立即开始
		return true;
	}

	@Override
	public boolean isEnd(int currentRound) {
		//由于buff在回合最后结算，因此达到结束回合时就失效
		return (currentRound >= endRound);
	}

	@Override
	public boolean isEffect(int currentRound) {
		//普通buff一旦创建随时有效
		return true;
	}

	@Override
	public int getValue() {
		return buffValue;
	}

	@Override
	public int getBuffSn() {
		return buffSn;
	}

	@Override
	public SkillBuffTypeEnum getBuffType() {
		return buffType;
	}

	@Override
	public int getEndRound() {
		return endRound;
	}
	
}
