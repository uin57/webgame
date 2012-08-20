package com.pwrd.war.gameserver.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pwrd.war.core.util.KeyValuePair;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.Camp;
import com.pwrd.war.gameserver.common.enums.Sex;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.common.event.RoleLevelUpEvent;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.common.unit.DynamicUnit;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.HumanConstants;
import com.pwrd.war.gameserver.human.msg.GCPropertyChangedNumber;
import com.pwrd.war.gameserver.human.msg.GCPropertyChangedString;
import com.pwrd.war.gameserver.human.template.LevelUpExpTemplate;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.role.properties.RoleBaseIntAProperties;
import com.pwrd.war.gameserver.role.properties.RoleBaseIntProperties;
import com.pwrd.war.gameserver.role.properties.RoleBaseStrProperties;
import com.pwrd.war.gameserver.role.properties.RolePropertyManager;
import com.pwrd.war.gameserver.role.properties.SkillBaseIntProperties;
import com.pwrd.war.gameserver.role.template.RolePropertyFactorTemplate;
import com.pwrd.war.gameserver.transferVocation.template.TransferVocationStarTemplate;
import com.pwrd.war.gameserver.warcraft.container.PetWarcraftEquipBag;
import com.pwrd.war.gameserver.warcraft.model.Warcraft;

public abstract class Role extends DynamicUnit {

	public static final int DEFAULT_TRANSFER_LEVEL = 1;
	public static final int DEFAULT_TRANSFER_STARS = 60;

	/** 角色类型 */
	protected short roleType;
	/** 角色的属性定义：角色在游戏过程中对客户端不可见的属性 */
	protected final RoleFinalProps finalProps = new RoleFinalProps();
	/** 基础属性：整型 ,double,如果是double，扩大1000倍以int给客户端 */
	protected final RoleBaseIntProperties baseIntProperties;

	/** 此类属性本是double，四舍五入以int返回客户端 **/
	protected final RoleBaseIntAProperties baseIntAProperties;
	/** 基础属性：对象型 */
	protected final RoleBaseStrProperties baseStrProperties;

	/** 技能等级 **/
	protected final SkillBaseIntProperties skillBaseIntProperties;
	/** 技能 */
	protected final SkillBaseStrProperties skillBaseStrProperties;

	protected RolePropertyFactorTemplate rolePropertyFactorTemplate;
	
	//转职星数模板
	protected TransferVocationStarTemplate transferVocationStarTemplate;
	
	public Role(short roleType) {
		this.roleType = roleType;
		baseIntProperties = new RoleBaseIntProperties();
		baseStrProperties = new RoleBaseStrProperties();
		baseIntAProperties = new RoleBaseIntAProperties();
		skillBaseIntProperties = new SkillBaseIntProperties();
		skillBaseStrProperties = new SkillBaseStrProperties();
	}

	public void setRoleType(short roleType) {
		this.roleType = roleType;
	}

	public short getRoleType() {
		return roleType;
	}

	@Override
	public void heartBeat() {

	}

	/**
	 * 重置所有属性的修改标识
	 * 
	 * @param reset
	 */
	public void resetChange() {
		this.getPropertyManager().resetChanged();
		this.finalProps.resetChanged();
		this.baseIntProperties.resetChanged();
		this.baseIntAProperties.resetChanged();
		this.baseStrProperties.resetChanged();
	}

	/******************* 设置和取得属性值 ************************/
	public String getName() {
		return this.baseStrProperties.getString(RoleBaseStrProperties.NAME);
	}

	public void setName(String name) {
		this.baseStrProperties.setString(RoleBaseStrProperties.NAME, name);
	}

	public int getLevel() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.LEVEL);
	}

	public void setLevel(int level) {
		this.baseIntProperties.set(RoleBaseIntProperties.LEVEL, level);
		this.onModified();
	}

	public VocationType getVocationType() {
		return VocationType.getByCode(this.baseIntProperties
				.getInt(RoleBaseIntProperties.VOCATION));
	}

	/**
	 * 必须先设置职业
	 */
	public void setVocationType(VocationType v) {
		Map<Integer, RolePropertyFactorTemplate> tmps1 = Globals
				.getTemplateService().getAll(RolePropertyFactorTemplate.class);
		for (RolePropertyFactorTemplate t : tmps1.values()) {
			if (t.getVocation() == v.getCode()) {
				this.setRolePropertyFactorTemplate(t);
			}
		}
		this.baseIntProperties.set(RoleBaseIntProperties.VOCATION, v.getCode());
		this.onModified();
	}

	public Sex getSex() {
		return Sex.getByCode(this.baseIntProperties
				.getInt(RoleBaseIntProperties.SEX));
	}

	public void setSex(Sex v) {
		this.baseIntProperties.set(RoleBaseIntProperties.SEX, v.getCode());
		this.onModified();
	}

	public int getGrow() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.GROW);
	}

	public void setGrow(int grow) {
		this.baseIntProperties.set(RoleBaseIntProperties.GROW, grow);
		this.setJingjie(Globals.getHumanService().getJingjieTemplate(grow).getJingjie());
		this.onModified();
	}

	public double getCurHp() {
		return this.baseIntAProperties.getDouble(RoleBaseIntAProperties.CUR_HP);
	}

	public void setCurHp(double curHp) {
		this.baseIntAProperties.set(RoleBaseIntAProperties.CUR_HP, curHp);
		this.onModified();
	}

	public double getMaxHp() {
		return this.baseIntAProperties.getDouble(RoleBaseIntAProperties.MAX_HP);
	}

	public void setMaxHp(double maxHp) {
		this.baseIntAProperties.setDouble(RoleBaseIntAProperties.MAX_HP, maxHp);
		this.onModified();
	}

	public int getCurExp() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.CUR_EXP);
	}

	public void setCurExp(int curExp) {
		this.baseIntProperties.set(RoleBaseIntProperties.CUR_EXP, curExp);
		this.onModified();
	}

	public int getMaxExp() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.MAX_EXP);
	}

	public void setMaxExp(int maxExp) {
		this.baseIntProperties.set(RoleBaseIntProperties.MAX_EXP, maxExp);
		this.onModified();
	}

	public double getAtk() {
		return this.baseIntAProperties.getDouble(RoleBaseIntAProperties.ATK);
	}

	public KeyValuePair<Integer, Integer> getAtkPari() {
		return new KeyValuePair<Integer, Integer>(
				this.baseIntAProperties
						.getKeyByProp(RoleBaseIntAProperties.ATK),
				(int) Math.round(this.getAtk()));
	}

	public void setAtk(double atk) {
		this.baseIntAProperties.setDouble(RoleBaseIntAProperties.ATK, atk);
		this.onModified();
	}

	public double getDef() {
		return this.baseIntAProperties.getDouble(RoleBaseIntAProperties.DEF);
	}

	public void setDef(double def) {
		this.baseIntAProperties.setDouble(RoleBaseIntAProperties.DEF, def);
		this.onModified();
	}

	public double getCri() {
		return this.baseIntProperties.getDouble(RoleBaseIntProperties.CRI);
	}

	public void setCri(double cri) {
		this.baseIntProperties.set(RoleBaseIntProperties.CRI, cri);
		this.onModified();
	}

	public double getSpd() {
		return this.baseIntAProperties.getDouble(RoleBaseIntAProperties.SPD);
	}

	public void setSpd(double spd) {
		this.baseIntAProperties.setDouble(RoleBaseIntAProperties.SPD, spd);
		this.onModified();
	}

	public double getShanbi() {
		return this.baseIntProperties.getDouble(RoleBaseIntProperties.SHANBI);
	}

	public void setShanbi(double shanbi) {
		this.baseIntProperties.set(RoleBaseIntProperties.SHANBI, shanbi);
		this.onModified();
	}

	public double getShanghai() {
		return this.baseIntProperties.getDouble(RoleBaseIntProperties.SHANGHAI);
	}

	public void setShanghai(double shanghai) {
		this.baseIntProperties.set(RoleBaseIntProperties.SHANGHAI, shanghai);
		this.onModified();
	}

	public double getMianshang() {
		return this.baseIntProperties
				.getDouble(RoleBaseIntProperties.MIANSHANG);
	}

	public void setMianshang(double mianshang) {
		this.baseIntProperties.set(RoleBaseIntProperties.MIANSHANG, mianshang);
		this.onModified();
	}

	public double getFanji() {
		return this.baseIntProperties.getDouble(RoleBaseIntProperties.FANJI);
	}

	public void setFanji(double fanji) {
		this.baseIntProperties.set(RoleBaseIntProperties.FANJI, fanji);
		this.onModified();
	}

	public double getMingzhong() {
		return this.baseIntProperties
				.getDouble(RoleBaseIntProperties.MINGZHONG);
	}

	public void setMingzhong(double mingzhong) {
		this.baseIntProperties.set(RoleBaseIntProperties.MINGZHONG, mingzhong);
		this.onModified();
	}

	public double getLianji() {
		return this.baseIntProperties.getDouble(RoleBaseIntProperties.LIANJI);
	}

	public void setLianji(double lianji) {
		this.baseIntProperties.set(RoleBaseIntProperties.LIANJI, lianji);
		this.onModified();
	}
	
	public double getRenxing() {
		return this.baseIntProperties.getDouble(RoleBaseIntProperties.RENXING);
	}

	public void setRenXing(double renxing) {
		this.baseIntProperties.set(RoleBaseIntProperties.RENXING, renxing);
		this.onModified();
	}
	
	public void addRenxing(double renxing){
		renxing = this.getRenxing() + renxing;
		if(renxing < 0){
			renxing = 0;
		}
		this.setRenXing(renxing);
	}

	public double getZhandouli() {
		return this.baseIntAProperties
				.getDouble(RoleBaseIntAProperties.ZHANDOULI);
	}

	/**
	 * 子类需要重载以计算角色的战斗力 装备，道具和buff的战斗力
	 * 
	 * @author xf
	 */
	public void setZhandouli(double zhandouli) {
		this.baseIntAProperties.setDouble(RoleBaseIntAProperties.ZHANDOULI,
				zhandouli);
		this.onModified();
	}

	/**
	 * 增加战斗力
	 * 
	 * @author xf
	 */
	protected void addZhandouli(double zhandouli) {
		this.baseIntAProperties.set(RoleBaseIntAProperties.ZHANDOULI,
				this.getZhandouli() + zhandouli);
	}

	public String getSceneId() {
		return this.baseStrProperties.getString(RoleBaseStrProperties.SCENEID);
	}

	public void setSceneId(String sceneId) {
		this.baseStrProperties
				.setString(RoleBaseStrProperties.SCENEID, sceneId);
		this.onModified();
	}

	public int getX() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.LOCATIONX);
	}

	public void setX(int x) {
		this.baseIntProperties.set(RoleBaseIntProperties.LOCATIONX, x);
		this.onModified();
	}

	public int getY() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.LOCATIONY);
	}

	public void setSkeletonId(String skeletonId) {
		this.baseStrProperties
				.set(RoleBaseStrProperties.SKELETONID, skeletonId);
		this.onModified();
	}

	public String getSkeletonId() {
		return this.baseStrProperties
				.getString(RoleBaseStrProperties.SKELETONID);
	}

	public void setY(int y) {
		this.baseIntProperties.set(RoleBaseIntProperties.LOCATIONY, y);
		this.onModified();
	}

	public Camp getCamp() {
		return Camp.getByCode(this.baseIntProperties
				.getInt(RoleBaseIntProperties.CAMP));
	}

	public void setCamp(Camp c) {
		this.baseIntProperties.set(RoleBaseIntProperties.CAMP, c.getCode());
	}

	public RoleBaseIntProperties getBaseIntProperties() {
		return baseIntProperties;
	}

	public RoleBaseStrProperties getBaseStrProperties() {
		return baseStrProperties;
	}

	/**
	 * 将有改动的数值数据发送到客户端
	 * 
	 * @param reset
	 *            如果reset标识为true,则会在快照完成后重置更新状态
	 */

	public void snapChangedProperty(boolean reset) {
		// 如果 LevelA,LevelB,DynamicNumProp,DynamicOtherProp 均无变化，则返回NULL
		if (!this.getPropertyManager().isNumchanged()
				&& !this.getPropertyManager().isStrchanged()
				&& !this.baseIntProperties.isChanged()
				&& !this.baseIntAProperties.isChanged()
				&& !this.baseStrProperties.isChanged()
				&& !this.skillBaseIntProperties.isChanged()
				&& !this.skillBaseStrProperties.isChanged()) {
			return;
		}
		// 保存数值类属性变化
		List<KeyValuePair<Integer, Integer>> _numChanged = changedNum();
		// 保存字符串类属性变化
		List<KeyValuePair<Integer, String>> _strChanged = changedStr();

		KeyValuePair<Integer, Integer>[] empty = KeyValuePair
				.newKeyValuePairArray(0);

		if (_numChanged != null && !_numChanged.isEmpty()) {
			sendMessage(new GCPropertyChangedNumber(getRoleType(),
					this.getUUID(), _numChanged.toArray(empty)));
		}
		KeyValuePair<Integer, String>[] empty1 = KeyValuePair
				.newKeyValuePairArray(0);
		if (_strChanged != null && !_strChanged.isEmpty()) {
			sendMessage(new GCPropertyChangedString(getRoleType(),
					this.getUUID(), _strChanged.toArray(empty1)));
		}

		if (reset) {
			resetChange();
		}
	}

	public void sendInfomationMessage(Player player) {
		short roleType = 0;
		if (getRoleType() == RoleTypes.HUMAN) {
			roleType = RoleTypes.OTHER_HUMAN;
		} else if (getRoleType() == RoleTypes.PET) {
			roleType = RoleTypes.OTHER_PET;
		}
		// 保存字符串类属性变化
		// 保存数值类属性变化
		List<KeyValuePair<Integer, Integer>> _numChanged = generatorIntegerInfomation();
		// 保存字符串类属性变化
		List<KeyValuePair<Integer, String>> _strChanged = generatorStringInfomation();

		KeyValuePair<Integer, Integer>[] empty = KeyValuePair
				.newKeyValuePairArray(0);

		if (_numChanged != null && !_numChanged.isEmpty()) {
			player.sendMessage(new GCPropertyChangedNumber(roleType, this.getUUID(),
					_numChanged.toArray(empty)));
		}
		KeyValuePair<Integer, String>[] empty1 = KeyValuePair
				.newKeyValuePairArray(0);
		if (_strChanged != null && !_strChanged.isEmpty()) {
			player.sendMessage(new GCPropertyChangedString(roleType, this.getUUID(),
					_strChanged.toArray(empty1)));
		}
	}

	protected List<KeyValuePair<Integer, Integer>> generatorIntegerInfomation() {
		List<KeyValuePair<Integer, Integer>> integerInfomations = new ArrayList<KeyValuePair<Integer, Integer>>();
		integerInfomations
				.add(new KeyValuePair<Integer, Integer>(this.baseIntProperties
						.getKeyByProp(RoleBaseIntProperties.GROW), this.getGrow()));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntProperties
						.getKeyByProp(RoleBaseIntProperties.CUR_EXP),
				this.getCurExp()));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntProperties
						.getKeyByProp(RoleBaseIntProperties.MAX_EXP),
				this.getMaxExp()));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntAProperties
						.getKeyByProp(RoleBaseIntAProperties.ZHANDOULI),
				(int) Math.round(this.getZhandouli())));

		integerInfomations
				.add(new KeyValuePair<Integer, Integer>(this.baseIntProperties
						.getKeyByProp(RoleBaseIntProperties.CAMP), this
						.getCamp().getCode()));

		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntAProperties
						.getKeyByProp(RoleBaseIntAProperties.CUR_HP),
				(int) Math.round(this.getCurHp())));

		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntAProperties
						.getKeyByProp(RoleBaseIntAProperties.MAX_HP),
				(int) Math.round(this.getMaxHp())));

		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntAProperties
						.getKeyByProp(RoleBaseIntAProperties.DEF), (int) Math
						.round(this.getDef())));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntAProperties
						.getKeyByProp(RoleBaseIntAProperties.ATK), (int) Math
						.round(this.getAtk())));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntProperties
						.getKeyByProp(RoleBaseIntProperties.VOCATION), this
						.getVocationType().getCode()));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntAProperties
						.getKeyByProp(RoleBaseIntAProperties.SPD), (int) Math
						.round(this.getSpd())));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntProperties
						.getKeyByProp(RoleBaseIntProperties.MINGZHONG),
				(int) Math.round(this.getMingzhong())));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntProperties
						.getKeyByProp(RoleBaseIntProperties.SHANGHAI),
				(int) Math.round(this.getShanghai())));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntProperties
						.getKeyByProp(RoleBaseIntProperties.MIANSHANG),
				(int) Math.round(this.getMianshang())));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntProperties
						.getKeyByProp(RoleBaseIntProperties.FANJI), (int) Math
						.round(this.getFanji())));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.baseIntProperties
						.getKeyByProp(RoleBaseIntProperties.LIANJI), (int) Math
						.round(this.getLianji())));

		return integerInfomations;
	}

	protected List<KeyValuePair<Integer, String>> generatorStringInfomation() {
		List<KeyValuePair<Integer, String>> stringInfomations = new ArrayList<KeyValuePair<Integer, String>>();
		return stringInfomations;
	}

	public void sendSkillMessage(Player player) {
		short roleType = 0;
		if (getRoleType() == RoleTypes.HUMAN) {
			roleType = RoleTypes.OTHER_HUMAN;
		} else if (getRoleType() == RoleTypes.PET) {
			roleType = RoleTypes.OTHER_PET;
		}
		// 保存字符串类属性变化
		// 保存数值类属性变化
		List<KeyValuePair<Integer, Integer>> _numChanged = generatorSkillIntegerInfomation();
		// 保存字符串类属性变化
		List<KeyValuePair<Integer, String>> _strChanged = generatorSkillStringInfomation();

		KeyValuePair<Integer, Integer>[] empty = KeyValuePair
				.newKeyValuePairArray(0);

		if (_numChanged != null && !_numChanged.isEmpty()) {
			player.sendMessage(new GCPropertyChangedNumber(roleType, this.getUUID(),
					_numChanged.toArray(empty)));
		}
		KeyValuePair<Integer, String>[] empty1 = KeyValuePair
				.newKeyValuePairArray(0);
		if (_strChanged != null && !_strChanged.isEmpty()) {
			player.sendMessage(new GCPropertyChangedString(roleType, this.getUUID(),
					_strChanged.toArray(empty1)));
		}
	}

	protected List<KeyValuePair<Integer, Integer>> generatorSkillIntegerInfomation() {
		List<KeyValuePair<Integer, Integer>> integerInfomations = new ArrayList<KeyValuePair<Integer, Integer>>();
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.skillBaseIntProperties
						.getKeyByProp(SkillBaseIntProperties.PET_SKILL_LEVEL),
				this.getPetSkillLevel()));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.skillBaseIntProperties
						.getKeyByProp(SkillBaseIntProperties.SKILL_LEVEL1),
				this.getSkillLevel1()));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.skillBaseIntProperties
						.getKeyByProp(SkillBaseIntProperties.SKILL_LEVEL2),
				this.getSkillLevel2()));
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.skillBaseIntProperties
						.getKeyByProp(SkillBaseIntProperties.SKILL_LEVEL3),
				this.getSkillLevel3()));
		integerInfomations
				.add(new KeyValuePair<Integer, Integer>(
						this.skillBaseIntProperties
								.getKeyByProp(SkillBaseIntProperties.PASS_SKILL_LEVEL1),
						this.getPassiveSkillLevel1()));
		integerInfomations
				.add(new KeyValuePair<Integer, Integer>(
						this.skillBaseIntProperties
								.getKeyByProp(SkillBaseIntProperties.PASS_SKILL_LEVEL2),
						this.getPassiveSkillLevel2()));
		integerInfomations
				.add(new KeyValuePair<Integer, Integer>(
						this.skillBaseIntProperties
								.getKeyByProp(SkillBaseIntProperties.PASS_SKILL_LEVEL3),
						this.getPassiveSkillLevel3()));
		integerInfomations
				.add(new KeyValuePair<Integer, Integer>(
						this.skillBaseIntProperties
								.getKeyByProp(SkillBaseIntProperties.PASS_SKILL_LEVEL4),
						this.getPassiveSkillLevel4()));
		integerInfomations
		.add(new KeyValuePair<Integer, Integer>(
				this.skillBaseIntProperties
						.getKeyByProp(SkillBaseIntProperties.PASS_SKILL_LEVEL5),
				this.getPassiveSkillLevel5()));
		integerInfomations
		.add(new KeyValuePair<Integer, Integer>(
				this.skillBaseIntProperties
						.getKeyByProp(SkillBaseIntProperties.PASS_SKILL_LEVEL6),
				this.getPassiveSkillLevel6()));
		return integerInfomations;
	}

	protected List<KeyValuePair<Integer, String>> generatorSkillStringInfomation() {
		List<KeyValuePair<Integer, String>> stringInfomations = new ArrayList<KeyValuePair<Integer, String>>();
		stringInfomations.add(new KeyValuePair<Integer, String>(
				this.skillBaseStrProperties
						.getKeyByProp(SkillBaseStrProperties.PET_SKILL), this
						.getPetSkill()));
		stringInfomations.add(new KeyValuePair<Integer, String>(
				this.skillBaseStrProperties
						.getKeyByProp(SkillBaseStrProperties.SKILL1), this
						.getSkill1()));
		stringInfomations.add(new KeyValuePair<Integer, String>(
				this.skillBaseStrProperties
						.getKeyByProp(SkillBaseStrProperties.SKILL2), this
						.getSkill2()));
		stringInfomations.add(new KeyValuePair<Integer, String>(
				this.skillBaseStrProperties
						.getKeyByProp(SkillBaseStrProperties.SKILL3), this
						.getSkill3()));
		stringInfomations.add(new KeyValuePair<Integer, String>(
				this.skillBaseStrProperties
						.getKeyByProp(SkillBaseStrProperties.PASS_SKILL1), this
						.getPassiveSkill1()));
		stringInfomations.add(new KeyValuePair<Integer, String>(
				this.skillBaseStrProperties
						.getKeyByProp(SkillBaseStrProperties.PASS_SKILL2), this
						.getPassiveSkill2()));
		stringInfomations.add(new KeyValuePair<Integer, String>(
				this.skillBaseStrProperties
						.getKeyByProp(SkillBaseStrProperties.PASS_SKILL3), this
						.getPassiveSkill3()));
		stringInfomations.add(new KeyValuePair<Integer, String>(
				this.skillBaseStrProperties
						.getKeyByProp(SkillBaseStrProperties.PASS_SKILL4), this
						.getPassiveSkill4()));
		stringInfomations.add(new KeyValuePair<Integer, String>(
				this.skillBaseStrProperties
						.getKeyByProp(SkillBaseStrProperties.PASS_SKILL5), this
						.getPassiveSkill4()));
		stringInfomations.add(new KeyValuePair<Integer, String>(
				this.skillBaseStrProperties
						.getKeyByProp(SkillBaseStrProperties.PASS_SKILL6), this
						.getPassiveSkill4()));
		return stringInfomations;
	}

	protected abstract List<KeyValuePair<Integer, String>> changedStr();

	/**
	 * 取得角色的UUID
	 * 
	 * @author xf
	 */
	abstract public String getUUID();

	/**
	 * 向前端发送消息
	 * 
	 * @see Human#sendMessage(GCMessage)
	 */
	abstract protected void sendMessage(GCMessage msg);

	/**
	 * 角色的属性管理器
	 * 
	 * @return
	 */
	abstract public RolePropertyManager getPropertyManager();

	/**
	 * 当属性被修改时调用
	 */
	abstract protected void onModified();

	/**
	 * 变化的整数
	 * 
	 * @author xf
	 */
	abstract protected List<KeyValuePair<Integer, Integer>> changedNum();

	/**
	 * 取得最大等级
	 */
	abstract protected int getMaxLevel();

	/**
	 * 重新计算属性，并且发送变化信息 .非升级调用
	 * 
	 * @author xf
	 */
	public void calcProps() {
		this.calcProps(false);
	}

	/**
	 * 重新计算属性，并且发送变化信息， 如果是升级则计算后，应该设置当前血量为最大血量
	 * 
	 * @author xf
	 */
	abstract public void calcProps(boolean bLevelUp);

	/**
	 * 加血。返回实际加的血量
	 * 
	 * @author xf
	 */
	public int addHP(int hp) {
		int r = hp;
		double curHp = this.getCurHp();
		curHp += hp;
		if (curHp > this.getMaxHp()) {
			r = (int) (this.getMaxHp() - this.getCurHp());
			curHp = this.getMaxHp();
		}
		if (curHp < 0) {
			return 0;
		}
		this.setCurHp(curHp);
		return r;
	}

	/**
	 * 增加经验，会触发是否升级操作
	 * 
	 * @author xf
	 */
	public boolean addExp(int exp) {
		int curExp = this.getCurExp();
		curExp += exp;
		if (exp > 0) {
			this.setCurExp(curExp);
		}
		if (curExp >= this.getMaxExp()) {
			return this.levelUp(true);
		}
		return false;
	}

	/**
	 * 升级
	 * 
	 * @author xf
	 */
	public boolean levelUp(boolean changeExp) {
		int nowLevel = this.getLevel();
		if (nowLevel >= this.getMaxLevel()) {
			return false;
		}
		nowLevel += 1;
		this.setLevel(nowLevel);

		// 更改当前经验和升级经验
		LevelUpExpTemplate tmp = Globals.getTemplateService().get(nowLevel,
				LevelUpExpTemplate.class);
		if (changeExp) {
			int curExp = this.getCurExp();
			curExp = curExp - this.getMaxExp();
			this.setCurExp(curExp);
		}
		this.setMaxExp(tmp.getNeedExp());

		this.calcProps(true);

		// 发送事件
		RoleLevelUpEvent e = new RoleLevelUpEvent(this, nowLevel);
		Globals.getEventService().fireEvent(e);

		// 有可能连续升级
		this.addExp(0);
		
		return true;
	}

	/**
	 * 增加速度
	 * 
	 * @author xf
	 */
	public void addSpd(double spd) {
		spd = this.getSpd() + spd;
		if (spd < 0) {
			spd = 0;
		}
		this.setSpd(spd);
	}

	/**
	 * 只是增加攻击
	 * 
	 * @author xf
	 */
	public void addAtk(double atk) {
		atk = this.getAtk() + atk;
		if (atk < 0) {
			atk = 0;
		}
		this.setAtk(atk);
	}

	/**
	 * 只是增加防御
	 * 
	 * @author xf
	 */
	public void addDef(double def) {
		def = this.getDef() + def;
		if (def < 0) {
			def = 0;
		}
		this.setDef(def);
	}

	/**
	 * 只是增加 最大血量
	 * 
	 * @author xf
	 */
	public void addMaxHp(double hp) {
		hp = this.getMaxHp() + hp;
		if (hp < 0) {
			hp = 0;
		}
		this.baseIntAProperties.set(RoleBaseIntAProperties.MAX_HP, hp);

		this.onModified();
	}

	// protected
	protected double getRoleGrowAtk(int level) {
		return HumanConstants.GROW_ADD_ATK; 
	}

	protected double getRoleGrowDef(int level) {
		return HumanConstants.GROW_ADD_DEF; 
	}

	protected double getRoleGrowHp(int level) {
		return HumanConstants.GROW_ADD_MAX_HP; 
	}

	public RolePropertyFactorTemplate getRolePropertyFactorTemplate() {
		return rolePropertyFactorTemplate;
	}

	public void setRolePropertyFactorTemplate(
			RolePropertyFactorTemplate rolePropertyFactorTemplate) {
		this.rolePropertyFactorTemplate = rolePropertyFactorTemplate;
	}

	/** 取得当前成长到目标成长攻击增加值 */
	public abstract int getGrowUpAtkAdd(int toGrow);

	/** 取得当前成长到目标成长防御增加值 */
	public abstract int getGrowUpDefAdd(int toGrow);

	/** 取得当前成长到目标成长血量增加值 */
	public abstract int getGrowUpMaxHpAdd(int toGrow);
	public abstract int getGrowAtk();
	public abstract int getGrowDef();
	public abstract int getGrowMaxHp();

	public abstract double getBuffAtk();
	public abstract void setBuffAtk(double buffAtk);
	public abstract double getBuffDef();
	public abstract void setBuffDef(double buffDef);
	public abstract double getBuffMaxHp();
	public abstract void setBuffMaxHp(double buffMaxHp);
	public abstract void clearBuffValue();

	public int getMaxGrow() {
		return Globals.getHumanService().getMaxGrow();
	}

	public void setMaxGrow(int maxGrow) {
		this.baseIntProperties.setInt(RoleBaseIntProperties.MAX_GROW, maxGrow);
		this.onModified();
	}

	public int getTransferLevel() {
		return this.baseIntProperties
				.getInt(RoleBaseIntProperties.TRANSFER_LEVEL);
	}

	public void setTransferLevel(int level) {
		this.baseIntProperties.setInt(RoleBaseIntProperties.TRANSFER_LEVEL,
				level);
		this.onModified();
	}

	public String getPetSkill() {
		return this.skillBaseStrProperties
				.getString(SkillBaseStrProperties.PET_SKILL);
	}

	public void setPetSkill(String skill) {
		this.skillBaseStrProperties.setString(SkillBaseStrProperties.PET_SKILL,
				skill);
		this.onModified();
	}

	public String getSkill1() {
		return this.skillBaseStrProperties
				.getString(SkillBaseStrProperties.SKILL1);
	}

	public void setSkill1(String skill) {
		this.skillBaseStrProperties.setString(SkillBaseStrProperties.SKILL1,
				skill);
		this.onModified();
	}

	public String getSkill2() {
		return this.skillBaseStrProperties
				.getString(SkillBaseStrProperties.SKILL2);
	}

	public void setSkill2(String skill1) {
		this.skillBaseStrProperties.setString(SkillBaseStrProperties.SKILL2,
				skill1);
		this.onModified();
	}

	public String getSkill3() {
		return this.skillBaseStrProperties
				.getString(SkillBaseStrProperties.SKILL3);
	}

	public void setSkill3(String skill) {
		this.skillBaseStrProperties.setString(SkillBaseStrProperties.SKILL3,
				skill);
		this.onModified();
	}

	public String getPassiveSkill1() {
		return this.skillBaseStrProperties
				.getString(SkillBaseStrProperties.PASS_SKILL1);
	}

	public void setPassiveSkill1(String skill) {
		this.skillBaseStrProperties.setString(
				SkillBaseStrProperties.PASS_SKILL1, skill);
		this.onModified();
	}

	public String getPassiveSkill2() {
		return this.skillBaseStrProperties
				.getString(SkillBaseStrProperties.PASS_SKILL2);
	}

	public void setPassiveSkill2(String skill) {
		this.skillBaseStrProperties.setString(
				SkillBaseStrProperties.PASS_SKILL2, skill);
		this.onModified();
	}

	public String getPassiveSkill3() {
		return this.skillBaseStrProperties
				.getString(SkillBaseStrProperties.PASS_SKILL3);
	}

	public void setPassiveSkill3(String skill) {
		this.skillBaseStrProperties.setString(
				SkillBaseStrProperties.PASS_SKILL3, skill);
		this.onModified();
	}

	public String getPassiveSkill4() {
		return this.skillBaseStrProperties
				.getString(SkillBaseStrProperties.PASS_SKILL4);
	}

	public void setPassiveSkill4(String skill) {
		this.skillBaseStrProperties.setString(
				SkillBaseStrProperties.PASS_SKILL4, skill);
		this.onModified();
	}
	
	public String getPassiveSkill5() {
		return this.skillBaseStrProperties
				.getString(SkillBaseStrProperties.PASS_SKILL5);
	}

	public void setPassiveSkill5(String skill) {
		this.skillBaseStrProperties.setString(
				SkillBaseStrProperties.PASS_SKILL5, skill);
		this.onModified();
	}
	
	public String getPassiveSkill6() {
		return this.skillBaseStrProperties
				.getString(SkillBaseStrProperties.PASS_SKILL6);
	}

	public void setPassiveSkill6(String skill) {
		this.skillBaseStrProperties.setString(
				SkillBaseStrProperties.PASS_SKILL6, skill);
		this.onModified();
	}

	public int getPetSkillLevel() {
		return this.skillBaseIntProperties
				.getInt(SkillBaseIntProperties.PET_SKILL_LEVEL);
	}

	public void setPetSkillLevel(int petSkillLevel) {
		this.skillBaseIntProperties.setInt(
				SkillBaseIntProperties.PET_SKILL_LEVEL, petSkillLevel);
		this.onModified();
	}

	public int getSkillLevel1() {
		return this.skillBaseIntProperties
				.getInt(SkillBaseIntProperties.SKILL_LEVEL1);
	}

	public void setSkillLevel1() {
		this.skillBaseIntProperties.setInt(SkillBaseIntProperties.SKILL_LEVEL1,
				calSkillLevel());
		this.onModified();
	}

	public int getSkillLevel2() {
		return this.skillBaseIntProperties
				.getInt(SkillBaseIntProperties.SKILL_LEVEL2);
	}

	public void setSkillLevel2() {
		this.skillBaseIntProperties.setInt(SkillBaseIntProperties.SKILL_LEVEL2,
				calSkillLevel());
		this.onModified();
	}

	public int getSkillLevel3() {
		return this.skillBaseIntProperties
				.getInt(SkillBaseIntProperties.SKILL_LEVEL3);
	}

	public void setSkillLevel3() {
		this.skillBaseIntProperties.setInt(SkillBaseIntProperties.SKILL_LEVEL3,
				calSkillLevel());
		this.onModified();
	}

	public int getPassiveSkillLevel1() {
		return this.skillBaseIntProperties
				.getInt(SkillBaseIntProperties.PASS_SKILL_LEVEL1);
	}

	public void setPassiveSkillLevel1() {
		this.setPassiveSkillLevel1(1);
	}
	public void setPassiveSkillLevel1(int level) {
		this.skillBaseIntProperties.setInt(
				SkillBaseIntProperties.PASS_SKILL_LEVEL1, level);
		this.onModified();
	}

	public int getPassiveSkillLevel2() {
		return this.skillBaseIntProperties
				.getInt(SkillBaseIntProperties.PASS_SKILL_LEVEL2);
	}
	public void setPassiveSkillLevel2() {
		this.setPassiveSkillLevel2(1);
	}
	public void setPassiveSkillLevel2(int level) {
		this.skillBaseIntProperties.setInt(
				SkillBaseIntProperties.PASS_SKILL_LEVEL2, level);
		this.onModified();
	}

	public int getPassiveSkillLevel3() {
		return this.skillBaseIntProperties
				.getInt(SkillBaseIntProperties.PASS_SKILL_LEVEL3);
	}
	public void setPassiveSkillLevel3() {
		this.setPassiveSkillLevel3(1);
	}
	public void setPassiveSkillLevel3(int level) {
		this.skillBaseIntProperties.setInt(
				SkillBaseIntProperties.PASS_SKILL_LEVEL3, level);
		this.onModified();
	}

	public int getPassiveSkillLevel4() {
		return this.skillBaseIntProperties
				.getInt(SkillBaseIntProperties.PASS_SKILL_LEVEL4);
	}
	public void setPassiveSkillLevel4() {
		this.setPassiveSkillLevel4(1);
	}
	public void setPassiveSkillLevel4(int level) {
		this.skillBaseIntProperties.setInt(
				SkillBaseIntProperties.PASS_SKILL_LEVEL4, level);
		this.onModified();
	}
	
	public int getPassiveSkillLevel5() {
		return this.skillBaseIntProperties
				.getInt(SkillBaseIntProperties.PASS_SKILL_LEVEL5);
	}
	public void setPassiveSkillLevel5() {
		this.setPassiveSkillLevel5(1);
	}
	public void setPassiveSkillLevel5(int level) {
		this.skillBaseIntProperties.setInt(
				SkillBaseIntProperties.PASS_SKILL_LEVEL5, level);
		this.onModified();
	}
	
	public int getPassiveSkillLevel6() {
		return this.skillBaseIntProperties
				.getInt(SkillBaseIntProperties.PASS_SKILL_LEVEL6);
	}

	public void setPassiveSkillLevel6() {
		this.setPassiveSkillLevel6(1);
	}
	public void setPassiveSkillLevel6(int level) {
		this.skillBaseIntProperties.setInt(
				SkillBaseIntProperties.PASS_SKILL_LEVEL6, level);
		this.onModified();
	}

	private int calSkillLevel() {
		return getLevel() / 5 + 1;
	}

	public double getShortAtk() {
		return this.baseIntAProperties
				.getDouble(RoleBaseIntAProperties.SHORT_ATK);
	}

	public void setShortAtk(double shortAtk) {
		this.baseIntAProperties.setDouble(RoleBaseIntAProperties.SHORT_ATK,
				shortAtk);
		this.onModified();
	}

	public double getRemoteAtk() {
		return this.baseIntAProperties
				.getDouble(RoleBaseIntAProperties.REMOTE_ATK);
	}

	public void setRemoteAtk(double remoteAtk) {
		this.baseIntAProperties.setDouble(RoleBaseIntAProperties.REMOTE_ATK,
				remoteAtk);
		this.onModified();
	}

	public double getShortDef() {
		return this.baseIntAProperties
				.getDouble(RoleBaseIntAProperties.SHORT_DEF);
	}

	public void setShortDef(double shortDef) {
		this.baseIntAProperties.setDouble(RoleBaseIntAProperties.SHORT_DEF,
				shortDef);
		this.onModified();
	}

	public double getRemoteDef() {
		return this.baseIntAProperties
				.getDouble(RoleBaseIntAProperties.REMOTE_DEF);
	}

	public void setRemoteDef(double remoteDef) {
		this.baseIntAProperties.setDouble(RoleBaseIntAProperties.REMOTE_DEF,
				remoteDef);
		this.onModified();
	}

	public int getMaxHpLevel() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.MAXHP_LEVEL);
	}

	public void setMaxHpLevel(int maxHpLevel) {
		this.baseIntProperties.setInt(RoleBaseIntProperties.MAXHP_LEVEL,
				maxHpLevel);
		this.onModified();
	}

	public int getAtkLevel() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.ATK_LEVEL);
	}

	public void setAtkLevel(int atkLevel) {
		this.baseIntProperties
				.setInt(RoleBaseIntProperties.ATK_LEVEL, atkLevel);
		this.onModified();
	}

	public int getDefLevel() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.DEF_LEVEL);
	}

	public void setDefLevel(int defLevel) {
		this.baseIntProperties
				.setInt(RoleBaseIntProperties.DEF_LEVEL, defLevel);
		this.onModified();
	}

	public int getShortAtkLevel() {
		return this.baseIntProperties
				.getInt(RoleBaseIntProperties.SHORT_ATK_LEVEL);
	}

	public void setShortAtkLevel(int shortAtkLevel) {
		this.baseIntProperties.setInt(RoleBaseIntProperties.SHORT_ATK_LEVEL,
				shortAtkLevel);
		this.onModified();
	}

	public int getShortDefLevel() {
		return this.baseIntProperties
				.getInt(RoleBaseIntProperties.SHORT_DEF_LEVEL);
	}

	public void setShortDefLevel(int shortDefLevel) {
		this.baseIntProperties.setInt(RoleBaseIntProperties.SHORT_DEF_LEVEL,
				shortDefLevel);
		this.onModified();
	}

	public int getRemoteAtkLevel() {
		return this.baseIntProperties
				.getInt(RoleBaseIntProperties.REMOTE_ATK_LEVEL);
	}

	public void setRemoteAtkLevel(int remoteAtkLevel) {
		this.baseIntProperties.setInt(RoleBaseIntProperties.REMOTE_ATK_LEVEL,
				remoteAtkLevel);
		this.onModified();
	}

	public int getRemoteDefLevel() {
		return this.baseIntProperties
				.getInt(RoleBaseIntProperties.REMOTE_DEF_LEVEL);
	}

	public void setRemoteDefLevel(int remoteDefLevel) {
		this.baseIntProperties.setInt(RoleBaseIntProperties.REMOTE_DEF_LEVEL,
				remoteDefLevel);
		this.onModified();
	}

	public int getCooldownRound() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.COOLDOWN_ROUND);
	}
	
	public void setCooldownRound(int cooldownRound) {
		this.baseIntProperties.setInt(RoleBaseIntProperties.COOLDOWN_ROUND, cooldownRound);
	}
	
	public String getCastAnim() {
		return this.baseStrProperties.getString(RoleBaseStrProperties.CAST_ANIM);
	}
	
	public void setCastAnim(String castAnim) {
		this.baseStrProperties.setString(RoleBaseStrProperties.CAST_ANIM, castAnim);
	}
	
	public String getVocationAnim() {
		return this.baseStrProperties.getString(RoleBaseStrProperties.VOACTION_ANIM);
	}
	
	public void setVocationAnim(String vocationAnim) {
		this.baseStrProperties.setString(RoleBaseStrProperties.VOACTION_ANIM, vocationAnim);
	}
	
	public String getAttackedAnim() {
		return this.baseStrProperties.getString(RoleBaseStrProperties.ATTACKED_ANIM);
	}
	
	public void setAttackedAnim(String attackedAnim) {
		this.baseStrProperties.setString(RoleBaseStrProperties.ATTACKED_ANIM, attackedAnim);
	}
	
	public int getMaxMorale() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.MAX_MORALE);
	}
	
	public void setMaxMorale(int maxMorale) {
		this.baseIntProperties.setInt(RoleBaseIntProperties.MAX_MORALE, maxMorale);
	}
	public int getTransferstar() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.TRANSFERSTAR);
	}
	
	public void setTransferstar(int starLevel) {
		transferVocationStarTemplate = 
				Globals.getTransferVocationService().getStarTmp(starLevel);
		
		this.baseIntProperties.setInt(RoleBaseIntProperties.TRANSFERSTAR, starLevel);
		
		this.setTransferLevel(starLevel/(10) + 1);//星数-》转职等级
	}
	public int getTransferexp() {
		return this.baseIntProperties.getInt(RoleBaseIntProperties.TRANSFEREXP);
	}
	
	public void setTransferexp(int exp) {
		this.baseIntProperties.setInt(RoleBaseIntProperties.TRANSFEREXP, exp);
	}
	public boolean addTransferexp(int addExp){
		int oexp = this.getTransferexp();
		int nexp = oexp + addExp;
		if(transferVocationStarTemplate.getLevelExp() > 0
				&& nexp >= transferVocationStarTemplate.getLevelExp()
				&& transferVocationStarTemplate.getStarLevel() < DEFAULT_TRANSFER_STARS){
			this.setTransferexp(nexp - transferVocationStarTemplate.getLevelExp());
			this.setTransferstar(this.getTransferstar() + 1);
			this.calcProps();
			return this.addTransferexp(0);
		}else{
			this.setTransferexp(nexp);
			if(addExp == 0)return true;
			return false;
		}
	}
	
	public void setDamage(double damage){
		this.baseIntProperties.setDouble(RoleBaseIntProperties.DAMAGE, damage);
		this.onModified();
	}
	
	public double getDamage(){
		return this.baseIntProperties.getDouble(RoleBaseIntProperties.DAMAGE);
	}
	/**
	 * 增加伤害
	 * @param damage
	 */
	public void addDamage(double damage){
		damage = damage + this.getDamage();
		if (damage < 0) {
			damage = 0;
		}
		this.setDamage(damage);
	}
	/**
	 * 从模版读取一下属性
	 */
	protected abstract void initPropsFromTmp();
	
	public int getJingjie(){
		return this.baseIntProperties.getInt(RoleBaseIntProperties.JINGJIE);
	}
	
	public void setJingjie(int jingjie){
		this.baseIntProperties.setInt(RoleBaseIntProperties.JINGJIE, jingjie);
		this.onModified();
	}
	
	public void setWarcraftScore(int score){
		this.baseIntProperties.setInt(RoleBaseIntProperties.WARCRAFT_SCORE, score);
		this.onModified();
	}
	
	public int getWarcraftScore(){
		return this.baseIntProperties.getInt(RoleBaseIntProperties.WARCRAFT_SCORE);
	}
	
	public void setWarcraftProps(String warcraftProps){
		this.baseStrProperties.setString(RoleBaseStrProperties.WARCRAFT_PROPS, warcraftProps);
	}
	
	public String getWarcraftProps(){
		return this.baseStrProperties.getString(RoleBaseStrProperties.WARCRAFT_PROPS);
	}
	
	public void setCriShanghai(double criShanghai){
		this.baseIntProperties.set(RoleBaseIntProperties.CRISHANGHAI, criShanghai);
		this.onModified();
	}
	
	public double getCriShanghai(){
		return this.baseIntProperties.getDouble(RoleBaseIntProperties.CRISHANGHAI);
	}

}
