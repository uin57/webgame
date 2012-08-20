package com.pwrd.war.gameserver.fight;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.gameserver.fight.field.unit.FightUnit;


public class FightUtils {
	public static final String[] CastAnimations = {"1:2", "2:3"};	//全部攻击动作组合
	public static final int NormalBackGrid = 10;	//普通击退距离
	public static final int MaxLines = 3;		//战场最大线数
	public static final int MaxGrids = 120;		//战场每线最大格数
	public static final int MinDistance = 9;	//战斗双方最小距离格数
	public static final int MinBlockDistance = 5;	//战斗中与障碍最小距离格数
	public static final int BlockInitDistance = 25;	//战斗中产生栅栏时距离目标格数
	public static final int FrontAreaPosition = 20;		//对于进攻方来说前场中心位置
	public static final int CenterAreaPosition = 60;	//对于进攻方来说中场中心位置
	public static final int RearAreaPosition = 100;		//对于进攻方来说后场中心位置
	public static final int AreaRadio = 35;				//区域范围半径
	public static final int BlockPosition = 75;			//特殊栅栏位置，己方后场
	public static final int MaxRound = 200;		//最大回合限制
	public static final long TimePerRound = 500L;		//每回合实际时间
	public static final int[][] FormLinePosition = {	//阵型各个位置对应的分线和位置
		{0, 0}, {0, 8}, {0, 16},
		{1, 0}, {1, 8}, {1, 16},
		{2, 0}, {2, 8}, {2, 16},
		{0, 2}, {0, 4}, {0, 6}, {0, 9}, {0, 11}, {0, 13}, {0, 15},
		{1, 2}, {1, 4}, {1, 6}, {1, 9}, {1, 11}, {1, 13}, {1, 15},
		{2, 2}, {2, 4}, {2, 6}, {2, 9}, {2, 11}, {2, 13}, {2, 15}
	};
	public static final double CRI_DMG_ENHANCE = 1.5D;		//暴击伤害增加率
	public static final int effectRateBase = 1000;			//buff效果生效几率基础值
	public static final double BuffRateBase = 1000D;		//buff增加百分比效果的基础值
	public static final int FightAreaBuffSn = 3001001;		//开场区域buff编号
	public static final int FightAreaEffectSn = 3001002;	//开场区域buff效果编号，增加攻击力200%
	
	/**
	 * 根据阵型位置编号(1-9)获取对应的初始分线号和站位信息，默认返回1号位置信息
	 * @param formPosition
	 * @return 格式为[分线, 起始位置]
	 */
	public static int[] getFormLinePosition(int formPosition) {
		if (formPosition <= FormLinePosition.length && formPosition > 0) {
			return FormLinePosition[formPosition - 1];
		} else {
			return FormLinePosition[0];
		}
	}
	
	/**
	 * 将防守方位置值和进攻方位置值相互转化
	 * @param position
	 * @return
	 */
	public static int flipPosition(int position) {
		return MaxGrids - position - 1;
	}
	
	/**
	 * 判断是否命中目标
	 * 己方命中率=100%+己方武将增加命中率 - 对方武将闪避率
	 * 命中率大于100%，按100%计算，未命中即为对方闪避（闪避不受伤害）
	 * 普通攻击最小命中率20%，但如果技能或武将技效果可能使得某一次攻击被100%闪避
	 * @param unit
	 * @param target
	 * @param isNormalAttack
	 * @return
	 */
	public static boolean isHit(FightUnit unit, FightUnit target, boolean isNormalAttack) {
		double hit = 1.0 + unit.getHit() - target.getDodge();
		if (isNormalAttack && hit < 0.2) {
			hit = 0.2;
		}
		return (RandomUtils.nextDouble() < hit);
	}
	
	/**
	 * 计算是否暴击
	 * 实际暴击率=暴击率-韧性
	 * @param unit
	 * @return
	 */
	public static boolean isCri(FightUnit unit) {
		return (RandomUtils.nextDouble() < unit.getCri());
	}
	
	/**
	 * 计算伤害值
	 * 普通伤害=max（攻击-防御，等级对应伤害基础定值）*（1+伤害提升百分比）*（1+武将技技能系数）*（1-减伤系数）
	 * 等级对应伤害基础定值=8*1.0414^己方武将等级
	 * 暴击伤害=普通伤害*（1+（0.5*（1+暴击伤害增加百分比））
	 * @param unit
	 * @param target
	 * @return
	 */
	public static double calcDamage(FightUnit unit, FightUnit target) {
		double damage = 2D * Math.pow(1.0414D, unit.getUnitLevel());
		damage = Math.max(damage, unit.getAtk() - target.getDef());
		damage = damage * (1D - resistFactor(target));
		return damage;
	}
	
	/**
	 * 减伤率
	 * 己方减伤率=己方武将增加减伤率-对方武将减少减伤率
	 * 基础减伤系数为0（即不触发减伤的时候该系数为0）
	 * 减伤为受到50%伤害（即出发后默认减伤系数为0.5）
	 * 减伤率大于100%按100%计算
	 * 减伤系数大于80%按80%计算
	 * @param defenseBattleUnit
	 * @return
	 */
	private static double resistFactor(FightUnit unit) {
		if (isResist(unit)) {
			return 0.5D;
		} else {
			return 0D;
		}
	}
	
	/**
	 * 计算是否抵抗
	 * @param unit
	 * @return
	 */
	private static boolean isResist(FightUnit unit) {
		double hit = unit.getHit();
		if (hit > 0.95) {
			return (RandomUtils.nextDouble() < 0.95);
		} else if (hit > 0.8) {
			return (RandomUtils.nextDouble() < 0.8);
		} else {
			return (RandomUtils.nextDouble() < hit);
		}
	}
}
