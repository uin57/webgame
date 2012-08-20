package com.pwrd.war.gameserver.fight.buff;

import com.pwrd.war.gameserver.skill.enums.SkillBuffTypeEnum;


/**
 * 战场buff对象接口
 * @author zy
 *
 */
public interface IFightBuff {
	/**
	 * 获取buff编号
	 * @return
	 */
	public int getBuffSn();
	
	/**
	 * 获取buff类型
	 * @return
	 */
	public SkillBuffTypeEnum getBuffType();
	
	/**
	 * 是否可以移除
	 * @return
	 */
	public boolean canRemove();
	
	/**
	 * 获取buff唯一序号
	 * @return
	 */
	public int getBuffId();
	
	/**
	 * 判断buff在指定回合是否已经开始
	 * @param currentRound
	 * @return
	 */
	public boolean isStart(int currentRound);
	
	/**
	 * 判断buff在指定回合是否已经结束
	 * @param currentRound
	 * @return
	 */
	public boolean isEnd(int currentRound);
	
	/**
	 * 获取结束回合
	 * @return
	 */
	public int getEndRound();
	
	/**
	 * 判断buff在指定回合是否产生效果
	 * @param currentRound
	 * @return
	 */
	public boolean isEffect(int currentRound);
	
	/**
	 * 获取buff效果值
	 * @return
	 */
	public int getValue();
}
