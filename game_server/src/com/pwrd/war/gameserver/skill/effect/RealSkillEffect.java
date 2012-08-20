package com.pwrd.war.gameserver.skill.effect;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.gameserver.fight.FightUtils;
import com.pwrd.war.gameserver.skill.enums.SkillBuffTypeEnum;
import com.pwrd.war.gameserver.skill.template.SkillBuffTemplate;


/**
 * 实际技能效果对象
 * @author zy
 *
 */
public class RealSkillEffect {
	protected int effectRound;	//效果生效回合
	protected int effectChance;	//效果生效概率
	protected int buffSn;		//产生的buff编号
	protected SkillBuffTypeEnum buffType;	//产生的buff类型
	protected int buffValue;	//产生的buff值
	protected int delayRound;	//产生的buff延迟回合
	protected int totalRound;	//产生的buff持续回合
	protected int interval;		//产生的buff间隔回合
	protected boolean isToTarget;	//效果是否对技能目标使用
	protected boolean canRemove;	//产生的buff是否可移除
	protected boolean isBeforeAttack;	//产生的buff是否在攻击前
	protected int isAreaBuff;	//是否为区域buff
	protected int areaBuffSn;	//区域buff编号
	
	public RealSkillEffect(int effectRound, int effectChance, int buffSn, int delayRound,
			int totalRound, int interval, int toTarget, int remove, int before,
			int isAreaBuff, int areaBuffSn) {
		this.effectRound = effectRound;
		this.effectChance = effectChance;
		this.buffSn = buffSn;
		this.delayRound = delayRound;
		this.totalRound = totalRound;
		this.interval = interval;
		this.isToTarget = (toTarget == 0);		//对自己为1
		this.canRemove = (remove == 1);			//可移除为1
		this.isBeforeAttack = (before == 0);	//攻击后为1
		this.isAreaBuff = isAreaBuff;
		this.areaBuffSn = areaBuffSn;
	}
	
	/**
	 * 根据buff编号获取buff类型和buff值
	 */
	public void check(Map<Integer, SkillBuffTemplate> buffs) {
		SkillBuffTemplate template = buffs.get(buffSn);
		if (template != null) {
			this.buffType = template.getRealBuffType();
			this.buffValue = template.getBuffValue();
		} else {
			this.buffType = SkillBuffTypeEnum.NONE;
			this.buffValue = 0;
		}
	}
	
	/**
	 * 根据触发几率判断是否可以触发
	 * @return
	 */
	public boolean canEffectChance() {
		return RandomUtils.nextInt(FightUtils.effectRateBase) <= effectChance;
	}
	
	/**
	 * 根据触发几率判断是否可以触发，触发几率具有指定的加成
	 * @param enhance
	 * @return
	 */
	public boolean canEffectChance(int enhance) {
		int chance = effectChance;
		if (enhance > 0) {
			chance = chance + enhance;
		}
		return RandomUtils.nextInt(FightUtils.effectRateBase) <= chance;
	}
	
	public int getEffectRound() {
		return effectRound;
	}
	
	public int getBuffSn() {
		return buffSn;
	}

	public int getDelayRound() {
		return delayRound;
	}

	public int getTotalRound() {
		return totalRound;
	}

	public int getInterval() {
		return interval;
	}

	public boolean isToTarget() {
		return isToTarget;
	}

	public boolean isCanRemove() {
		return canRemove;
	}

	public boolean isBeforeAttack() {
		return isBeforeAttack;
	}
	
	public SkillBuffTypeEnum getBuffType() {
		return buffType;
	}

	public int getBuffValue() {
		return buffValue;
	}

	public int getIsAreaBuff() {
		return isAreaBuff;
	}

	public int getAreaBuffSn() {
		return areaBuffSn;
	}

	/**
	 * 解析原始字符串数据获得实际技能效果对象
	 * 生效回合,生效概率,buff编号,延迟回合,持续回合,间隔回合,对自己还是对技能目标(1为对自己),
	 * 是否可移除(1为可移除),攻击前还是攻击后使用(1为攻击后,如果是攻击前使用并且没有配置技能带攻击的话就无法施放出来了)
	 * @param param
	 * @return
	 */
	public static RealSkillEffect buildEffect(String param) {
		if (StringUtils.isNotBlank(param)) {
			String[] params = param.split(",");
			if (params.length > 10) {
				return new RealSkillEffect(str2Int(params[0]), str2Int(params[1]),
						str2Int(params[2]), str2Int(params[3]), str2Int(params[4]),
						str2Int(params[5]), str2Int(params[6]), str2Int(params[7]),
						str2Int(params[8]), str2Int(params[9]), str2Int(params[10]));
			}
		}
		return null;
	}
	
	/**
	 * 将字符串转换为整数，如果无法转换返回0
	 * @param str
	 * @return
	 */
	private static int str2Int(String str) {
		if (StringUtils.isNotBlank(str) && StringUtils.isNumeric(str)) {
			return Integer.valueOf(str);
		} else {
			return 0;
		}
	}
}
