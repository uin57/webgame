package com.pwrd.war.gameserver.pet.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 原生武将模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class PetTemplateVO extends TemplateObject {

	/** 武将ID */
	@ExcelCellBinding(offset = 1)
	protected String petSn;

	/** 武将名 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 武将等级 */
	@ExcelCellBinding(offset = 3)
	protected int level;

	/**  性别 */
	@ExcelCellBinding(offset = 4)
	protected int sex;

	/** 阵营 */
	@ExcelCellBinding(offset = 5)
	protected int vocation;

	/** 骨骼Id */
	@ExcelCellBinding(offset = 6)
	protected String skeletonId;

	/** 势力 */
	@ExcelCellBinding(offset = 7)
	protected int camp;

	/** 武将星级 */
	@ExcelCellBinding(offset = 8)
	protected int star;

	/** 成长等级 */
	@ExcelCellBinding(offset = 9)
	protected int growType;

	/** 武将特有成长 */
	@ExcelCellBinding(offset = 10)
	protected int specialGrow;

	/** 初始速度 */
	@ExcelCellBinding(offset = 11)
	protected double spd;

	/** 初始闪避 */
	@ExcelCellBinding(offset = 12)
	protected double shanbi;

	/** 初始暴击 */
	@ExcelCellBinding(offset = 13)
	protected double cri;

	/** 初始命中 */
	@ExcelCellBinding(offset = 14)
	protected double hit;

	/** 初始生命 */
	@ExcelCellBinding(offset = 15)
	protected double hp;

	/** 生命系数 */
	@ExcelCellBinding(offset = 16)
	protected double hpVocationFactor;

	/** 初始攻击 */
	@ExcelCellBinding(offset = 17)
	protected double atk;

	/** 攻击系数 */
	@ExcelCellBinding(offset = 18)
	protected double atkVocationFactor;

	/** 初始防御 */
	@ExcelCellBinding(offset = 19)
	protected double def;

	/** 防御系数 */
	@ExcelCellBinding(offset = 20)
	protected double defVocationFactor;

	/** 激活天赋树 */
	@ExcelCellBinding(offset = 21)
	protected String tree;

	/** 天赋点数 */
	@ExcelCellBinding(offset = 22)
	protected int treePoint;

	/** 初始减伤概率 */
	@ExcelCellBinding(offset = 23)
	protected double jianshang;

	/** 初始免伤百分比 */
	@ExcelCellBinding(offset = 24)
	protected double mianshang;

	/** 初始连击率 */
	@ExcelCellBinding(offset = 25)
	protected double lianji;

	/** 初始反击率 */
	@ExcelCellBinding(offset = 26)
	protected double fanji;

	/** 死亡说话内容 */
	@ExcelCellBinding(offset = 27)
	protected String deadChat;

	/** 武将列传 */
	@ExcelCellBinding(offset = 28)
	protected String description;

	/** 被击后攻击方冷却回合数 */
	@ExcelCellBinding(offset = 29)
	protected int cooldownRound;

	/** 攻击弹道动画 */
	@ExcelCellBinding(offset = 30)
	protected String castAnim;

	/** 被击光效 */
	@ExcelCellBinding(offset = 31)
	protected String attackedAnim;

	/** 职业特性弹道动画 */
	@ExcelCellBinding(offset = 32)
	protected String vocationAnim;

	/** 最大士气 */
	@ExcelCellBinding(offset = 33)
	protected int maxMorale;


	public String getPetSn() {
		return this.petSn;
	}

	public void setPetSn(String petSn) {
		if (StringUtils.isEmpty(petSn)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[武将ID]petSn不可以为空");
		}
		this.petSn = petSn;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[武将名]name不可以为空");
		}
		this.name = name;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[武将等级]level不可以为0");
		}
		if (level < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[武将等级]level的值不得小于1");
		}
		this.level = level;
	}
	
	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		if (sex == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 性别]sex不可以为0");
		}
		if (sex > 2 || sex < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 性别]sex的值不合法，应为1至2之间");
		}
		this.sex = sex;
	}
	
	public int getVocation() {
		return this.vocation;
	}

	public void setVocation(int vocation) {
		if (vocation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[阵营]vocation不可以为0");
		}
		this.vocation = vocation;
	}
	
	public String getSkeletonId() {
		return this.skeletonId;
	}

	public void setSkeletonId(String skeletonId) {
		if (StringUtils.isEmpty(skeletonId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[骨骼Id]skeletonId不可以为空");
		}
		this.skeletonId = skeletonId;
	}
	
	public int getCamp() {
		return this.camp;
	}

	public void setCamp(int camp) {
		if (camp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[势力]camp的值不得小于0");
		}
		this.camp = camp;
	}
	
	public int getStar() {
		return this.star;
	}

	public void setStar(int star) {
		if (star < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[武将星级]star的值不得小于0");
		}
		this.star = star;
	}
	
	public int getGrowType() {
		return this.growType;
	}

	public void setGrowType(int growType) {
		if (growType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[成长等级]growType的值不得小于0");
		}
		this.growType = growType;
	}
	
	public int getSpecialGrow() {
		return this.specialGrow;
	}

	public void setSpecialGrow(int specialGrow) {
		if (specialGrow < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[武将特有成长]specialGrow的值不得小于0");
		}
		this.specialGrow = specialGrow;
	}
	
	public double getSpd() {
		return this.spd;
	}

	public void setSpd(double spd) {
		if (spd < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[初始速度]spd的值不得小于0");
		}
		this.spd = spd;
	}
	
	public double getShanbi() {
		return this.shanbi;
	}

	public void setShanbi(double shanbi) {
		if (shanbi < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[初始闪避]shanbi的值不得小于0");
		}
		this.shanbi = shanbi;
	}
	
	public double getCri() {
		return this.cri;
	}

	public void setCri(double cri) {
		if (cri < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[初始暴击]cri的值不得小于0");
		}
		this.cri = cri;
	}
	
	public double getHit() {
		return this.hit;
	}

	public void setHit(double hit) {
		if (hit < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[初始命中]hit的值不得小于0");
		}
		this.hit = hit;
	}
	
	public double getHp() {
		return this.hp;
	}

	public void setHp(double hp) {
		if (hp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[初始生命]hp的值不得小于0");
		}
		this.hp = hp;
	}
	
	public double getHpVocationFactor() {
		return this.hpVocationFactor;
	}

	public void setHpVocationFactor(double hpVocationFactor) {
		if (hpVocationFactor < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[生命系数]hpVocationFactor的值不得小于0");
		}
		this.hpVocationFactor = hpVocationFactor;
	}
	
	public double getAtk() {
		return this.atk;
	}

	public void setAtk(double atk) {
		if (atk < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[初始攻击]atk的值不得小于0");
		}
		this.atk = atk;
	}
	
	public double getAtkVocationFactor() {
		return this.atkVocationFactor;
	}

	public void setAtkVocationFactor(double atkVocationFactor) {
		if (atkVocationFactor < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[攻击系数]atkVocationFactor的值不得小于0");
		}
		this.atkVocationFactor = atkVocationFactor;
	}
	
	public double getDef() {
		return this.def;
	}

	public void setDef(double def) {
		if (def < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[初始防御]def的值不得小于0");
		}
		this.def = def;
	}
	
	public double getDefVocationFactor() {
		return this.defVocationFactor;
	}

	public void setDefVocationFactor(double defVocationFactor) {
		if (defVocationFactor < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[防御系数]defVocationFactor的值不得小于0");
		}
		this.defVocationFactor = defVocationFactor;
	}
	
	public String getTree() {
		return this.tree;
	}

	public void setTree(String tree) {
		this.tree = tree;
	}
	
	public int getTreePoint() {
		return this.treePoint;
	}

	public void setTreePoint(int treePoint) {
		if (treePoint < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[天赋点数]treePoint的值不得小于0");
		}
		this.treePoint = treePoint;
	}
	
	public double getJianshang() {
		return this.jianshang;
	}

	public void setJianshang(double jianshang) {
		if (jianshang < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[初始减伤概率]jianshang的值不得小于0");
		}
		this.jianshang = jianshang;
	}
	
	public double getMianshang() {
		return this.mianshang;
	}

	public void setMianshang(double mianshang) {
		if (mianshang < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[初始免伤百分比]mianshang的值不得小于0");
		}
		this.mianshang = mianshang;
	}
	
	public double getLianji() {
		return this.lianji;
	}

	public void setLianji(double lianji) {
		if (lianji < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					26, "[初始连击率]lianji的值不得小于0");
		}
		this.lianji = lianji;
	}
	
	public double getFanji() {
		return this.fanji;
	}

	public void setFanji(double fanji) {
		if (fanji < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					27, "[初始反击率]fanji的值不得小于0");
		}
		this.fanji = fanji;
	}
	
	public String getDeadChat() {
		return this.deadChat;
	}

	public void setDeadChat(String deadChat) {
		this.deadChat = deadChat;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getCooldownRound() {
		return this.cooldownRound;
	}

	public void setCooldownRound(int cooldownRound) {
		if (cooldownRound < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					30, "[被击后攻击方冷却回合数]cooldownRound的值不得小于0");
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
	
	public int getMaxMorale() {
		return this.maxMorale;
	}

	public void setMaxMorale(int maxMorale) {
		if (maxMorale == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					34, "[最大士气]maxMorale不可以为0");
		}
		this.maxMorale = maxMorale;
	}
	

	@Override
	public String toString() {
		return "PetTemplateVO[petSn=" + petSn + ",name=" + name + ",level=" + level + ",sex=" + sex + ",vocation=" + vocation + ",skeletonId=" + skeletonId + ",camp=" + camp + ",star=" + star + ",growType=" + growType + ",specialGrow=" + specialGrow + ",spd=" + spd + ",shanbi=" + shanbi + ",cri=" + cri + ",hit=" + hit + ",hp=" + hp + ",hpVocationFactor=" + hpVocationFactor + ",atk=" + atk + ",atkVocationFactor=" + atkVocationFactor + ",def=" + def + ",defVocationFactor=" + defVocationFactor + ",tree=" + tree + ",treePoint=" + treePoint + ",jianshang=" + jianshang + ",mianshang=" + mianshang + ",lianji=" + lianji + ",fanji=" + fanji + ",deadChat=" + deadChat + ",description=" + description + ",cooldownRound=" + cooldownRound + ",castAnim=" + castAnim + ",attackedAnim=" + attackedAnim + ",vocationAnim=" + vocationAnim + ",maxMorale=" + maxMorale + ",]";

	}
}