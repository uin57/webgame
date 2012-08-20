package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 用户初始化属性信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class HumanInitPropTemplateVO extends TemplateObject {

	/**  职业ID */
	@ExcelCellBinding(offset = 1)
	protected int vocation;

	/** 职业名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 职业初始等级 */
	@ExcelCellBinding(offset = 3)
	protected int initLevel;

	/** 初始成长度 */
	@ExcelCellBinding(offset = 4)
	protected int initGrow;

	/** 初始速度 */
	@ExcelCellBinding(offset = 5)
	protected double initSpd;

	/** 初始闪避 */
	@ExcelCellBinding(offset = 6)
	protected double initShanbi;

	/** 初始暴击 */
	@ExcelCellBinding(offset = 7)
	protected double initCri;

	/** 初始伤害 */
	@ExcelCellBinding(offset = 8)
	protected double initShanghai;

	/** 初始免伤 */
	@ExcelCellBinding(offset = 9)
	protected double initMianshang;

	/** 初始反击 */
	@ExcelCellBinding(offset = 10)
	protected double initFanji;

	/** 初始命中 */
	@ExcelCellBinding(offset = 11)
	protected double initMingzhong;

	/** 初始连击 */
	@ExcelCellBinding(offset = 12)
	protected double initLianji;

	/** 攻击系数 */
	@ExcelCellBinding(offset = 13)
	protected double atkVocationFactor;

	/** 攻击系数对应攻击 */
	@ExcelCellBinding(offset = 14)
	protected double initAtk;

	/** 防御系数 */
	@ExcelCellBinding(offset = 15)
	protected double defVocationFactor;

	/** 防御系数对应防御 */
	@ExcelCellBinding(offset = 16)
	protected double initDef;

	/** 血量系数 */
	@ExcelCellBinding(offset = 17)
	protected double hpVocationFactor;

	/** 血量系数对应血量 */
	@ExcelCellBinding(offset = 18)
	protected double initHp;

	/** 可转职次数上限 */
	@ExcelCellBinding(offset = 19)
	protected int maxTransgerNum;

	/** 被击后攻击方冷却回合数 */
	@ExcelCellBinding(offset = 20)
	protected int cooldownRound;

	/** 攻击弹道动画(男) */
	@ExcelCellBinding(offset = 21)
	protected String castAnim;

	/** 被击光效(男) */
	@ExcelCellBinding(offset = 22)
	protected String attackedAnim;

	/** 职业特性弹道动画(男) */
	@ExcelCellBinding(offset = 23)
	protected String vocationAnim;

	/** 攻击弹道动画(女) */
	@ExcelCellBinding(offset = 24)
	protected String castAnimFemale;

	/** 被击光效(女) */
	@ExcelCellBinding(offset = 25)
	protected String attackedAnimFemale;

	/** 职业特性弹道动画(女) */
	@ExcelCellBinding(offset = 26)
	protected String vocationAnimFemale;

	/** 最大士气 */
	@ExcelCellBinding(offset = 27)
	protected int maxMorale;


	public int getVocation() {
		return this.vocation;
	}

	public void setVocation(int vocation) {
		if (vocation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 职业ID]vocation不可以为0");
		}
		this.vocation = vocation;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[职业名称]name不可以为空");
		}
		this.name = name;
	}
	
	public int getInitLevel() {
		return this.initLevel;
	}

	public void setInitLevel(int initLevel) {
		if (initLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[职业初始等级]initLevel不可以为0");
		}
		this.initLevel = initLevel;
	}
	
	public int getInitGrow() {
		return this.initGrow;
	}

	public void setInitGrow(int initGrow) {
		if (initGrow < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[初始成长度]initGrow的值不得小于0");
		}
		this.initGrow = initGrow;
	}
	
	public double getInitSpd() {
		return this.initSpd;
	}

	public void setInitSpd(double initSpd) {
		if (initSpd < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[初始速度]initSpd的值不得小于0");
		}
		this.initSpd = initSpd;
	}
	
	public double getInitShanbi() {
		return this.initShanbi;
	}

	public void setInitShanbi(double initShanbi) {
		if (initShanbi < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[初始闪避]initShanbi的值不得小于0");
		}
		this.initShanbi = initShanbi;
	}
	
	public double getInitCri() {
		return this.initCri;
	}

	public void setInitCri(double initCri) {
		if (initCri < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[初始暴击]initCri的值不得小于0");
		}
		this.initCri = initCri;
	}
	
	public double getInitShanghai() {
		return this.initShanghai;
	}

	public void setInitShanghai(double initShanghai) {
		if (initShanghai < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[初始伤害]initShanghai的值不得小于0");
		}
		this.initShanghai = initShanghai;
	}
	
	public double getInitMianshang() {
		return this.initMianshang;
	}

	public void setInitMianshang(double initMianshang) {
		if (initMianshang < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[初始免伤]initMianshang的值不得小于0");
		}
		this.initMianshang = initMianshang;
	}
	
	public double getInitFanji() {
		return this.initFanji;
	}

	public void setInitFanji(double initFanji) {
		if (initFanji < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[初始反击]initFanji的值不得小于0");
		}
		this.initFanji = initFanji;
	}
	
	public double getInitMingzhong() {
		return this.initMingzhong;
	}

	public void setInitMingzhong(double initMingzhong) {
		if (initMingzhong < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[初始命中]initMingzhong的值不得小于0");
		}
		this.initMingzhong = initMingzhong;
	}
	
	public double getInitLianji() {
		return this.initLianji;
	}

	public void setInitLianji(double initLianji) {
		if (initLianji < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[初始连击]initLianji的值不得小于0");
		}
		this.initLianji = initLianji;
	}
	
	public double getAtkVocationFactor() {
		return this.atkVocationFactor;
	}

	public void setAtkVocationFactor(double atkVocationFactor) {
		if (atkVocationFactor < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[攻击系数]atkVocationFactor的值不得小于0");
		}
		this.atkVocationFactor = atkVocationFactor;
	}
	
	public double getInitAtk() {
		return this.initAtk;
	}

	public void setInitAtk(double initAtk) {
		if (initAtk < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[攻击系数对应攻击]initAtk的值不得小于0");
		}
		this.initAtk = initAtk;
	}
	
	public double getDefVocationFactor() {
		return this.defVocationFactor;
	}

	public void setDefVocationFactor(double defVocationFactor) {
		if (defVocationFactor < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[防御系数]defVocationFactor的值不得小于0");
		}
		this.defVocationFactor = defVocationFactor;
	}
	
	public double getInitDef() {
		return this.initDef;
	}

	public void setInitDef(double initDef) {
		if (initDef < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[防御系数对应防御]initDef的值不得小于0");
		}
		this.initDef = initDef;
	}
	
	public double getHpVocationFactor() {
		return this.hpVocationFactor;
	}

	public void setHpVocationFactor(double hpVocationFactor) {
		if (hpVocationFactor < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[血量系数]hpVocationFactor的值不得小于0");
		}
		this.hpVocationFactor = hpVocationFactor;
	}
	
	public double getInitHp() {
		return this.initHp;
	}

	public void setInitHp(double initHp) {
		if (initHp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[血量系数对应血量]initHp的值不得小于0");
		}
		this.initHp = initHp;
	}
	
	public int getMaxTransgerNum() {
		return this.maxTransgerNum;
	}

	public void setMaxTransgerNum(int maxTransgerNum) {
		if (maxTransgerNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[可转职次数上限]maxTransgerNum的值不得小于0");
		}
		this.maxTransgerNum = maxTransgerNum;
	}
	
	public int getCooldownRound() {
		return this.cooldownRound;
	}

	public void setCooldownRound(int cooldownRound) {
		if (cooldownRound < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[被击后攻击方冷却回合数]cooldownRound的值不得小于0");
		}
		this.cooldownRound = cooldownRound;
	}
	
	public String getCastAnim() {
		return this.castAnim;
	}

	public void setCastAnim(String castAnim) {
		this.castAnim = castAnim;
	}
	
	public String getAttackedAnim() {
		return this.attackedAnim;
	}

	public void setAttackedAnim(String attackedAnim) {
		this.attackedAnim = attackedAnim;
	}
	
	public String getVocationAnim() {
		return this.vocationAnim;
	}

	public void setVocationAnim(String vocationAnim) {
		this.vocationAnim = vocationAnim;
	}
	
	public String getCastAnimFemale() {
		return this.castAnimFemale;
	}

	public void setCastAnimFemale(String castAnimFemale) {
		this.castAnimFemale = castAnimFemale;
	}
	
	public String getAttackedAnimFemale() {
		return this.attackedAnimFemale;
	}

	public void setAttackedAnimFemale(String attackedAnimFemale) {
		this.attackedAnimFemale = attackedAnimFemale;
	}
	
	public String getVocationAnimFemale() {
		return this.vocationAnimFemale;
	}

	public void setVocationAnimFemale(String vocationAnimFemale) {
		this.vocationAnimFemale = vocationAnimFemale;
	}
	
	public int getMaxMorale() {
		return this.maxMorale;
	}

	public void setMaxMorale(int maxMorale) {
		if (maxMorale == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					28, "[最大士气]maxMorale不可以为0");
		}
		this.maxMorale = maxMorale;
	}
	

	@Override
	public String toString() {
		return "HumanInitPropTemplateVO[vocation=" + vocation + ",name=" + name + ",initLevel=" + initLevel + ",initGrow=" + initGrow + ",initSpd=" + initSpd + ",initShanbi=" + initShanbi + ",initCri=" + initCri + ",initShanghai=" + initShanghai + ",initMianshang=" + initMianshang + ",initFanji=" + initFanji + ",initMingzhong=" + initMingzhong + ",initLianji=" + initLianji + ",atkVocationFactor=" + atkVocationFactor + ",initAtk=" + initAtk + ",defVocationFactor=" + defVocationFactor + ",initDef=" + initDef + ",hpVocationFactor=" + hpVocationFactor + ",initHp=" + initHp + ",maxTransgerNum=" + maxTransgerNum + ",cooldownRound=" + cooldownRound + ",castAnim=" + castAnim + ",attackedAnim=" + attackedAnim + ",vocationAnim=" + vocationAnim + ",castAnimFemale=" + castAnimFemale + ",attackedAnimFemale=" + attackedAnimFemale + ",vocationAnimFemale=" + vocationAnimFemale + ",maxMorale=" + maxMorale + ",]";

	}
}