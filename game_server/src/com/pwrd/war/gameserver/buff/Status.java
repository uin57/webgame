package com.pwrd.war.gameserver.buff;

import com.pwrd.war.gameserver.buff.enums.SkillBufferType;
import com.pwrd.war.gameserver.skill.enums.EffectType;

public class Status {

	/** buff类型 */
	private SkillBufferType bufferType;

	/** 状态id */
	protected String statusSn;

	/** 等级 */
	protected Integer statusLevel;

	/** 状态名称 */
	protected String statusName;

	/** 状态类别 */
	protected int statusType;

	/** 状态说明 */
	protected String statusDescription;

	/** 状态特效 */
	protected String statusSpecialEffect;

	/** 状态权重 */
	protected double statusWeight;

	/** 状态回合数 */
	protected int statusRound;
	/** 使用几率 */
	protected double useRatio;

	/** 状态效果 */
	protected EffectType effectType;

	/** 参数1 */
	protected String arg1;

	/** 参数2 */
	protected String arg2;


	public SkillBufferType getBufferType() {
		return bufferType;
	}

	public String getStatusSn() {
		return statusSn;
	}

	public Integer getStatusLevel() {
		return statusLevel;
	}

	public String getStatusName() {
		return statusName;
	}

	public int getStatusType() {
		return statusType;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public String getStatusSpecialEffect() {
		return statusSpecialEffect;
	}

	public double getStatusWeight() {
		return statusWeight;
	}

	public int getStatusRound() {
		return statusRound;
	}

	public double getUseRatio() {
		return useRatio;
	}

	public void setBufferType(SkillBufferType bufferType) {
		this.bufferType = bufferType;
	}

	public void setStatusSn(String statusSn) {
		this.statusSn = statusSn;
	}

	public void setStatusLevel(Integer statusLevel) {
		this.statusLevel = statusLevel;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setStatusType(int statusType) {
		this.statusType = statusType;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public void setStatusSpecialEffect(String statusSpecialEffect) {
		this.statusSpecialEffect = statusSpecialEffect;
	}

	public void setStatusWeight(double statusWeight) {
		this.statusWeight = statusWeight;
	}

	public void setStatusRound(int statusRound) {
		this.statusRound = statusRound;
	}

	public void setUseRatio(double useRatio) {
		this.useRatio = useRatio;
	}

	public String getArg1() {
		return arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}

	public String getArg2() {
		return arg2;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}

	public EffectType getEffectType() {
		return effectType;
	}

	public void setEffectType(EffectType effectType) {
		this.effectType = effectType;
	}

//	public double calAtk(BattleUnit attackBattleUnit,
//			BattleUnit defenseBattleUnit) {
//		double atk = 0;
//		switch (getEffectType()) {
//		case A1:
//			atk += Double.parseDouble(getArg1());
//			break;
//		case A38:
//			if (defenseBattleUnit.getCamp() == Camp.WEI) {
//				atk += Double.parseDouble(getArg1());
//			}
//			break;
//		case A39:
//			if (defenseBattleUnit.getCamp() == Camp.WU) {
//				atk += Double.parseDouble(getArg1());
//			}
//			break;
//		case A40:
//			if (defenseBattleUnit.getCamp() == Camp.SHU) {
//				atk += Double.parseDouble(getArg1());
//			}
//			break;
//		case A41:
//			if (defenseBattleUnit.getCamp() == Camp.QUNXIONG) {
//				atk += Double.parseDouble(getArg1());
//			}
//			break;
//		case A42:
//			if (defenseBattleUnit.getVocationType() == VocationType.MENGJIANG) {
//				atk += Double.parseDouble(getArg1());
//			}
//			break;
//		case A43:
//			if (defenseBattleUnit.getVocationType() == VocationType.MOUSHI) {
//				atk += Double.parseDouble(getArg1());
//			}
//			break;
//		case A44:
//			if (defenseBattleUnit.getVocationType() == VocationType.SHESHOU) {
//				atk += Double.parseDouble(getArg1());
//			}
//			break;
//		case A45:
//			if (defenseBattleUnit.getVocationType() == VocationType.HAOJIE) {
//				atk += Double.parseDouble(getArg1());
//			}
//			break;
//		default:
//			break;
//		}
//		return atk;
//	}

	/**
	 * 忽视防御
	 * 
	 * @param attackBattleUnit
	 * @param defenseBattleUnit
	 * @return
	 */
//	public double calIgnoreDef(BattleUnit attackBattleUnit,
//			BattleUnit defenseBattleUnit) {
//		double def = 0;
//		switch (getEffectType()) {
//		case A5:
//			def += Double.parseDouble(getArg1());
//			break;
//		default:
//			break;
//		}
//		return def;
//	}

	/**
	 * A17 攻击目标假如是猛将，则获得【参数一】士气 A18 攻击目标假如是豪杰，则获得【参数一】士气 A19攻击目标假如是射手，则获得【参数一】士气
	 * A20 攻击目标假如是谋士，则获得【参数一】士气
	 */
//	public double calMorale(BattleUnit attackBattleUnit,
//			BattleUnit defenseBattleUnit) {
//		double morale = 0;
//		switch (getEffectType()) {
//		case A17:
//			if (defenseBattleUnit.getVocationType() == VocationType.MENGJIANG) {
//				morale += Double.parseDouble(getArg1());
//			}
//			break;
//		case A18:
//			if (defenseBattleUnit.getVocationType() == VocationType.HAOJIE) {
//				morale += Double.parseDouble(getArg1());
//			}
//			break;
//		case A19:
//			if (defenseBattleUnit.getVocationType() == VocationType.SHESHOU) {
//				morale += Double.parseDouble(getArg1());
//			}
//			break;
//		case A20:
//			if (defenseBattleUnit.getVocationType() == VocationType.MOUSHI) {
//				morale += Double.parseDouble(getArg1());
//			}
//			break;
//		default:
//			break;
//		}
//		return morale;
//	}
//	
//	public double calMingZhong(BattleUnit attackBattleUnit,
//			BattleUnit defenseBattleUnit) {
//		double mingZhong = 0;
//		switch (getEffectType()) {
//		case A32:
//			mingZhong += Double.parseDouble(getArg1());
//			break;
//
//		default:
//			break;
//		}
//		return mingZhong;
//	}
//	
//	public double calShangBi(BattleUnit attackBattleUnit,
//			BattleUnit defenseBattleUnit) {
//		double shangBi = 0;
//		switch (getEffectType()) {
//		case A33:
//			shangBi += Double.parseDouble(getArg1());
//			break;
//
//		default:
//			break;
//		}
//		return shangBi;
//	}
//	
//	public double calLianJi(BattleUnit attackBattleUnit,
//			BattleUnit defenseBattleUnit) {
//		double lianJi = 0;
//		switch (getEffectType()) {
//		case A35:
//			lianJi += Double.parseDouble(getArg1());
//			break;
//		default:
//			break;
//		}
//		return lianJi;
//	}
//	
//	public double calCri(BattleUnit attackBattleUnit,
//			BattleUnit defenseBattleUnit) {
//		double cri = 0;
//		switch (getEffectType()) {
//		case A22:
//			cri += Double.parseDouble(getArg1());
//			break;
//		default:
//			break;
//		}
//		return cri;
//	}
//	
//	
//	public double calBleen() {
//		double bleen = 0;
//		switch (getEffectType()) {
//		case A31:
//			bleen += Double.parseDouble(getArg1());
//			break;
//		default:
//			break;
//		}
//		return bleen;
//	}
//	
//	public double calCure(){
//		double cure = 0;
//		switch (getEffectType()) {
//		case A37:
//			cure += Double.parseDouble(getArg1());
//			break;
//		default:
//			break;
//		}
//		return cure;
//	}
//	
//	public double calShanghai(BattleUnit attackBattleUnit,
//			BattleUnit defenseBattleUnit,double shanghai){
//		switch (getEffectType()) {
//		case A3:
//			shanghai += Double.parseDouble(getArg1());
//			break;
//		case A4:
//			shanghai *=(1+ Double.parseDouble(getArg1()));
//			break;
//		case A13:
//			if(defenseBattleUnit.getVocationType()==VocationType.MENGJIANG){
//				shanghai *=(1+ Double.parseDouble(getArg1()));
//			}
//			break;
//		case A14:
//			if(defenseBattleUnit.getVocationType()==VocationType.HAOJIE){
//				shanghai *=(1+ Double.parseDouble(getArg1()));
//			}
//			break;
//		case A15:
//			if(defenseBattleUnit.getVocationType()==VocationType.SHESHOU){
//				shanghai *=(1+ Double.parseDouble(getArg1()));
//			}
//			break;
//		case A16:
//			if(defenseBattleUnit.getVocationType()==VocationType.MOUSHI){
//				shanghai *=(1+ Double.parseDouble(getArg1()));
//			}
//			break;
//			
//		case A25:
//			if(1.0*defenseBattleUnit.getCurHp()/defenseBattleUnit.getMaxHp()<Double.parseDouble(getArg1())){
//				shanghai *=(1+ Double.parseDouble(getArg2()));
//			}
//			break;
//		default:
//			break;
//		}
//		return shanghai;
//	}
//	
//	public double calSpd(){
//		double spd=0;
//		switch (getEffectType()) {
//		case A36:
//			spd += Double.parseDouble(getArg1());
//			break;
//		default:
//			break;
//		}
//		return spd;
//	}
//
//	/**
//	 * 防御
//	 * 
//	 * @param attackBattleUnit
//	 * @param defenseBattleUnit
//	 * @return
//	 */
//	public double calDef(BattleUnit attackBattleUnit,
//			BattleUnit defenseBattleUnit) {
//		double def = 0;
//		System.out.println( "buffer   ________________________"+getStatusSn());
//		switch (getEffectType()) {
//		case A2:
//			def += Double.parseDouble(getArg1());
//			break;
//
//		default:
//			break;
//		}
//		return def;
//	}

}
