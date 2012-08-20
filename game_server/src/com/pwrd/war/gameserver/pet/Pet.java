package com.pwrd.war.gameserver.pet;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.List;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.KeyValuePair;
import com.pwrd.war.db.model.PetEntity;
import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.Camp;
import com.pwrd.war.gameserver.common.enums.Sex;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.common.event.HPReduceEvent;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.HumanConstants;
import com.pwrd.war.gameserver.human.OpenFunction;
import com.pwrd.war.gameserver.human.enums.MoodType;
import com.pwrd.war.gameserver.human.template.LevelUpExpTemplate;
import com.pwrd.war.gameserver.item.EquipmentFeature;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.container.PetBodyEquipBag;
import com.pwrd.war.gameserver.pet.properties.PetIntProperty;
import com.pwrd.war.gameserver.pet.properties.PetPropertyManager;
import com.pwrd.war.gameserver.pet.template.PetTemplate;
import com.pwrd.war.gameserver.role.AbstractPet;
import com.pwrd.war.gameserver.role.Role;
import com.pwrd.war.gameserver.role.RoleFinalProps;
import com.pwrd.war.gameserver.role.RoleTypes;
import com.pwrd.war.gameserver.role.properties.RoleBaseIntAProperties;
import com.pwrd.war.gameserver.role.template.JingjieTemplate;
import com.pwrd.war.gameserver.role.template.RoleToSkillTemplate;
import com.pwrd.war.gameserver.warcraft.container.PetWarcraftEquipBag;
import com.pwrd.war.gameserver.warcraft.model.Warcraft;

public class Pet extends AbstractPet implements
		PersistanceObject<String, PetEntity>, InitializeRequired {



	/** 宠物uuid */
	private String petUUID;

	/** 主人 */
	private Human owner;

	/** 是否已经在数据库中 */
	private boolean inDb;

	/** 生命期 */
	private final LifeCycle lifeCycle;

	/** 配置武将配置文件 */
	private PetTemplate template;
	
	private RoleToSkillTemplate skillTemplate;

	/** 武将Sn */
	private String petSn;

	/** 属性管理器 */
	private final PetPropertyManager propertyManager;

	public Pet(String petSn) {
		super(RoleTypes.PET);
		this.lifeCycle = new LifeCycleImpl(this);
		this.propertyManager = new PetPropertyManager(this);
		this.petSn = petSn;
		this.template = Globals.getPetService().getPetTemplate(petSn);
		skillTemplate=Globals.getPetService().getRoleToSkillTemplate(petSn);
	}

	/*
	 * 从模版加载数据到武将信息
	 */
	@Override
	public void init() {
		LevelUpExpTemplate leveltmp = Globals.getTemplateService().get(1, LevelUpExpTemplate.class);
		this.setCurExp(0);
		this.setMaxExp(leveltmp.getNeedExp());
		this.setName(template.getName());
		this.setLevel(template.getLevel());
		this.setSex(Sex.getByCode(template.getSex()));
		this.setVocationType(VocationType.getByCode(template.getVocation()));
		this.setTransferexp(0);
		this.setTransferLevel(Role.DEFAULT_TRANSFER_LEVEL);
		this.setTransferstar(0);
		
		this.initPropsFromTmp() ;
		
		this.setSkeletonId(template.getSkeletonId());
		this.setCamp(Camp.getByCode(template.getCamp()));
		this.propertyManager.setStar(template.getStar());
		this.propertyManager.setGrowType(template.getGrowType());
		this.setGrow(template.getSpecialGrow());
		this.setMaxGrow(Globals.getHumanService().getMaxGrow());
		this.propertyManager.setSpecialGrow(template.getSpecialGrow());
		this.setShanbi(template.getShanbi());
		this.setCri(template.getCri());
		// TODO
		// this.setHpPropConv((int)template.getHp()*100);
		this.setCurHp(this.getMaxHp());
		this.setSkill1("");
		this.setSkill2("");
		this.setSkill3("");
		this.setPassiveSkillLevel1(-1);//天生技能
		this.setPassiveSkillLevel2(-1);
		this.setPassiveSkillLevel3(-1);
		this.setPassiveSkillLevel4(-1);
		this.setPassiveSkillLevel5(-1);
		this.setPassiveSkillLevel6(-1);
		this.setPetSkill(skillTemplate.getPetSkill());
		this.setPassiveSkill1(skillTemplate.getPassiveSkill1());
		this.setPassiveSkill2(skillTemplate.getPassiveSkill2());
		this.setPassiveSkill3(skillTemplate.getPassiveSkill3());
		this.setPassiveSkill4(skillTemplate.getPassiveSkill4());
		this.setPassiveSkill5(skillTemplate.getPassiveSkill5());
		this.setPassiveSkill6(skillTemplate.getPassiveSkill6());
		this.initTmpZhandouli();
	}

	/**
	 * 激活此武将，并初始化属性 此方法在玩家登录加载完数据，或者获得新武将时调用
	 */
	public void onInit() {
		getLifeCycle().activate();
		setSkillLevel1();
		setSkillLevel2();
		setSkillLevel3();
		initMood();
		// getPropertyManager().updateProperty(RolePropertyManager.PROP_FROM_MARK_ALL);
	}

	private void initMood() {
		int size = MoodType.values().length;
		int mood = Calendar.getInstance().hashCode() % size;
		mood = (mood + getUUID().hashCode()) % size;
		getPropertyManager().setMood(mood);
	}
	public void calcProps(boolean bLevelUp) {
		this.calcProps(bLevelUp, true);
	}
	// TODO
	public void calcProps(boolean bLevelUp, boolean sendMessage) {
		// 先deactive;
		boolean needActive = false;
		if (this.lifeCycle.isActive()) {
			this.lifeCycle.deactivate();
			needActive = true;
		}

		// 读取初始配置属性，设置
		this.initPropsFromTmp();

		//读取境界属性
		JingjieTemplate nowJingjie = Globals.getHumanService().getJingjieTemplate(this.getGrow());
		for(int i=0;i<nowJingjie.getJingjie();i++){
			JingjieTemplate jingjie = Globals.getHumanService().getJingjieTemplates().get(i);
			if(jingjie != null){
				this.addAtk(jingjie.getAtk());
				this.addDef(jingjie.getDef());
				this.addMaxHp(jingjie.getHp());
				this.setShanghai(this.getShanghai() + jingjie.getShanghai());
			}
		}
				
		PetBodyEquipBag petBodyEquipBag = owner.getInventory().getBagByPet(
				petUUID);
		if (petBodyEquipBag != null) {
			EnumMap<Position, Item> equips = petBodyEquipBag.getAllEquips();
			for (Item item : equips.values()) {
				EquipmentFeature feature = (EquipmentFeature) item.getFeature();
				this.addAtk(feature.getAtk());
				this.addDef(feature.getDef());
				this.addMaxHp(feature.getMaxHp());
				// this.setSpd(this.getSpd() + equip.getSpd());
				this.setCri(this.getCri() + feature.getCri());
				this.setShanbi(this.getShanbi() + feature.getShanbi());
				this.setShanghai(this.getShanghai() + feature.getShanghai());
				this.setMianshang(this.getMianshang() + feature.getMianshang());
				this.setFanji(this.getFanji() + feature.getFanji());
				this.setMingzhong(this.getMingzhong() + feature.getMingzhong());
				this.setLianji(this.getLianji() + feature.getLianji());
				
				this.addZhandouli(feature.getZhandouli());
			}
		}
		
		//计算兵法属性
		PetWarcraftEquipBag warcraftEquipBag = owner.getWarcraftInventory().getPetWarcraftEquipBags().get(petUUID);
		int score = 0;
		if(warcraftEquipBag != null){
			List<Warcraft> warcrafts = warcraftEquipBag.getAllWarcraft();
			for(Warcraft warcraft : warcrafts){
				this.addAtk(warcraft.getAtk());
				this.addDef(warcraft.getDef());
				this.setShanbi(this.getShanbi() + warcraft.getShanbi());
				this.setMingzhong(this.getMingzhong() + warcraft.getMingzhong());
				this.setCri(this.getCri() + warcraft.getCri());
				this.addRenxing(warcraft.getRenXing());
				this.setCriShanghai(this.getCriShanghai() + warcraft.getCriShanghai());
				score += warcraft.getExp();
			}
		}
		
		//同步兵法积分和属性
		this.setWarcraftScore(score / 30);
		
		//buff
		this.clearBuffValue();
		for(Buffer buffer:owner.getBuffers().values()){
			buffer.updateThisRole(this);
		}
		
		// check
//		if (this.getCurHp() > this.getMaxHp()) {
			this.setCurHp(this.getMaxHp());
//		}
		// active;
		if (needActive) {
			this.lifeCycle.activate();

			if (bLevelUp) {
				this.setCurHp(this.getMaxHp());
			} else {
				HPReduceEvent e = new HPReduceEvent(this);
				Globals.getEventService().fireEvent(e);
			}

			this.onModified();
			// 发送消息
			if(sendMessage){
				this.snapChangedProperty(true);
			}
		}
	}

	/**
	 * 获取主人
	 * 
	 * @return
	 */
	public Human getOwner() {
		return owner;
	}

	/**
	 * 获得主人的uuid
	 * 
	 * @return
	 */
	public String getOwnerUUID() {
		return owner != null ? owner.getUUID() : "";
	}

	/**
	 * 设置主人
	 * 
	 * @param owner
	 * @throws IllegalArgumentException
	 *             当owner为空时抛出
	 */
	public void setOwner(Human owner) {
		Assert.notNull(owner);
		this.owner = owner;
		// onModified();
	}

	/**
	 * 获取武将id
	 * 
	 * @return
	 */
	public String getPetSn() {
		return this.petSn;
	}

	/**
	 * 设置武将Id
	 * 
	 * @param petId
	 */
	public void setPetSn(String petSn) {
		this.petSn = petSn;
		onModified();
	}

	@Override
	protected void onModified() {
		this.setModified();
	}

	@Override
	public void setModified() {
		if (owner != null) {
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive()) {
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}

	/**
	 * 设置为删除
	 * 
	 * @author xf
	 */
	public void setDeleted() {
		if (!this.lifeCycle.isActive()) {
			owner.getPlayer().getDataUpdater().addDelete(lifeCycle);
		}
	}

	/**
	 * 获取宠物标识
	 * 
	 * @return
	 */
	@Override
	public String getUUID() {
		return this.petUUID;
	}

	@Override
	public String getGUID() {
		return "pet#" + getUUID();
	}

	@Override
	public String getCharId() {
		return owner != null ? owner.getUUID() : "";
	}

	@Override
	public String getDbId() {
		return petUUID;
	}

	@Override
	public void setDbId(String id) {
		petUUID = id;
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public boolean isInDb() {
		return this.inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
	}

	@Override
	public void fromEntity(PetEntity entity) {
		this.setDbId(entity.getId());
		this.setInDb(true);
		this.setPetSn(entity.getPetSn());
		this.setName(entity.getName());
		this.setSex(Sex.getByCode(entity.getSex()));
		this.setLevel(entity.getLevel());
		this.setVocationType(VocationType.getByCode(entity.getVocation()));

		this.setGrow(entity.getGrow());
		this.setCurExp(entity.getCurExp());
		this.setMaxExp(entity.getMaxExp());
		this.setCurHp(entity.getCurHp());
		this.setMaxHp(entity.getMaxHp());
		this.setAtk(entity.getAtk());
		this.setDef(entity.getDef());
		this.setSpd(entity.getSpd());
		this.setCri(entity.getCri());
		this.setCriShanghai(entity.getCriShanghai());
		this.setShanbi(entity.getShanbi());
		this.setShanghai(entity.getShanghai());
		this.setMianshang(entity.getMianshang());
		this.setFanji(entity.getFanji());
		this.setMingzhong(entity.getMingzhong());
		this.setLianji(entity.getLianji());
		this.setRenXing(entity.getRenxing());
		this.setZhandouli(entity.getZhandouli());

		this.setSkeletonId(entity.getSkeletonId());
		this.setCamp(Camp.getByCode(entity.getCamp()));
		this.setPetSkill(entity.getPetSkill());
		this.setSkill1(entity.getSkill1());
		this.setSkill2(entity.getSkill2());
		this.setSkill3(entity.getSkill3());

		this.setPassiveSkill1(entity.getPassSkill1());
		this.setPassiveSkill2(entity.getPassSkill2());
		this.setPassiveSkill3(entity.getPassSkill3());
		this.setPassiveSkill4(entity.getPassSkill4());
		this.setPassiveSkill5(entity.getPassSkill5());
		this.setPassiveSkill6(entity.getPassSkill6());
		this.setPassiveSkillLevel1(entity.getPassSkillLevel1());
		this.setPassiveSkillLevel2(entity.getPassSkillLevel2());
		this.setPassiveSkillLevel3(entity.getPassSkillLevel3());
		this.setPassiveSkillLevel4(entity.getPassSkillLevel4());
		this.setPassiveSkillLevel5(entity.getPassSkillLevel5());
		this.setPassiveSkillLevel6(entity.getPassSkillLevel6());
		this.setMaxGrow(entity.getMaxGrow());
		this.setTransferLevel(entity.getTransferLevel());
		this.setTransferexp(entity.getTransferExp());
		this.setTransferstar(entity.getTransferstar());
		
		this.propertyManager.setBaseGrow(entity.getBaseGrow());
		this.propertyManager.setGrowType(entity.getGrowType());
		this.propertyManager.setSpecialGrow(entity.getSpecialGrow());
		this.propertyManager.setStar(entity.getStar());
		this.propertyManager.setIsInBattle(entity.isInBattle());
		this.propertyManager.getPetNormalProperty().setBuffAtk(entity.getBuffAtk());
		this.propertyManager.getPetNormalProperty().setBuffDef(entity.getBuffDef());
		this.propertyManager.getPetNormalProperty().setBuffMaxHp(entity.getBuffMaxHp());
	}

	@Override
	public PetEntity toEntity() {
		PetEntity entity = new PetEntity();
		entity.setId(this.getUUID());
		entity.setVocation(this.getVocationType().getCode());
		entity.setSex(this.getSex().getCode());
		entity.setCharId(this.getCharId());
		entity.setName(this.getName());
		entity.setLevel((short) this.getLevel());
		entity.setPetSn(this.getPetSn());

		entity.setCamp(this.getCamp().getCode());
		entity.setGrow(this.getGrow());
		entity.setCurExp(this.getCurExp());
		entity.setMaxExp(this.getMaxExp());
		entity.setCurHp(this.getCurHp());
		entity.setMaxHp(this.getMaxHp());
		entity.setAtk(this.getAtk());
		entity.setDef(this.getDef());
		entity.setSpd(this.getSpd());
		entity.setCri(this.getCri());
		entity.setShanbi(this.getShanbi());
		entity.setShanghai(this.getShanghai());
		entity.setMianshang(this.getMianshang());
		entity.setFanji(this.getFanji());
		entity.setMingzhong(this.getMingzhong());
		entity.setLianji(this.getLianji());
		entity.setRenxing(this.getRenxing());
		entity.setZhandouli(this.getZhandouli());

		entity.setSkeletonId(this.getSkeletonId());
		entity.setPetSkill(this.getPetSkill());
		entity.setSkill1(this.getSkill1());
		entity.setSkill2(this.getSkill2());
		entity.setSkill3(this.getSkill3());
		entity.setPassSkill1(this.getPassiveSkill1());
		entity.setPassSkill2(this.getPassiveSkill2());
		entity.setPassSkill3(this.getPassiveSkill3());
		entity.setPassSkill4(this.getPassiveSkill4());
		entity.setPassSkill5(this.getPassiveSkill5());
		entity.setPassSkill6(this.getPassiveSkill6());
		entity.setPassSkillLevel1(this.getPassiveSkillLevel1());
		entity.setPassSkillLevel2(this.getPassiveSkillLevel2());
		entity.setPassSkillLevel3(this.getPassiveSkillLevel3());
		entity.setPassSkillLevel4(this.getPassiveSkillLevel4());
		entity.setPassSkillLevel5(this.getPassiveSkillLevel5());
		entity.setPassSkillLevel6(this.getPassiveSkillLevel6());
		entity.setMaxGrow(this.getMaxGrow());
		entity.setTransferLevel(this.getTransferLevel());
		entity.setTransferExp(this.getTransferexp());
		entity.setTransferstar(this.getTransferstar());
		
		entity.setBaseGrow(this.propertyManager.getBaseGrow());
		entity.setGrowType(this.propertyManager.getGrowType());
		entity.setSpecialGrow(this.propertyManager.getSpecialGrow());
		entity.setStar(this.propertyManager.getStar());
		entity.setInBattle(this.propertyManager.getIsInBattle());
		entity.setBuffAtk(this.getPropertyManager().getBuffAtk());
		entity.setBuffDef(this.getPropertyManager().getBuffDef());
		entity.setBuffMaxHp(this.getPropertyManager().getBuffMaxHp());

		return entity;
	}

	@Override
	public PetPropertyManager getPropertyManager() {
		return propertyManager;
	}

	@Override
	protected List<KeyValuePair<Integer, Integer>> changedNum() {
		// 保存数值类属性变化
		List<KeyValuePair<Integer, Integer>> intNumChanged = new ArrayList<KeyValuePair<Integer, Integer>>();

		// 处理 一二级属性
		if (this.getPropertyManager().isNumchanged()) {
			List<KeyValuePair<Integer, Integer>> _numChanged = this
					.getPropertyManager().getChangedNum(); // float
			for (KeyValuePair<Integer, Integer> pair : _numChanged) {
				intNumChanged.add(new KeyValuePair<Integer, Integer>(pair
						.getKey(), pair.getValue()));
			}
		}
		if (this.baseIntAProperties.isChanged()) {
			List<KeyValuePair<Integer, Integer>> changes = this.baseIntAProperties
					.getChangedNum();
			for (KeyValuePair<Integer, Integer> pair : changes) {
				intNumChanged.add(pair);
			}
		}
		// 处理 baseIntProps
		if (this.baseIntProperties.isChanged()) {
			List<KeyValuePair<Integer, Integer>> changes = this.baseIntProperties
					.getChangedNum();
			for (KeyValuePair<Integer, Integer> pair : changes) {
				intNumChanged.add(pair);
			}
		}

		return intNumChanged;
	}

	@Override
	protected List<KeyValuePair<Integer, String>> changedStr() {
		// 保存字符串类属性变化
		List<KeyValuePair<Integer, String>> _strChanged = new ArrayList<KeyValuePair<Integer, String>>();
		if (this.getPropertyManager().isStrchanged()) {
			List<KeyValuePair<Integer, String>> s = this.propertyManager
					.getChangedString();
			_strChanged.addAll(s);
		}

		// 处理 baseStrProps
		if (this.baseStrProperties.isChanged()) {
			KeyValuePair<Integer, Object>[] v = this.baseStrProperties
					.getChanged();
			for (int i = 0; i < v.length; i++) {
				_strChanged.add(new KeyValuePair<Integer, String>(
						v[i].getKey(), v[i].getValue().toString()));
			}
		}
		if(this.skillBaseStrProperties.isChanged()){ 
			KeyValuePair<Integer, Object>[] v = this.skillBaseStrProperties
					.getChanged();
			for (int i = 0; i < v.length; i++) {
				_strChanged.add(new KeyValuePair<Integer, String>( v[i].getKey(), v[i].getValue()+""));
			}
		}
		return _strChanged;
	}

	@Override
	protected void sendMessage(GCMessage msg) {
		if (msg != null) {
			if (owner != null) {
				if (owner.getPlayer() != null) {
					owner.getPlayer().sendMessage(msg);
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public int getAvatarImg() {
		// PetBodyEquipBag wearings =
		// owner.getInventory().getBagByPet(this.getUUID());
		// return
		// wearings.getAvatarIdByPos(Position.FASHION_CLOTHES,Position.CLOTHES);
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getHairImg() {
		// PetBodyEquipBag wearings =
		// owner.getInventory().getBagByPet(this.getUUID());
		// return wearings.getHairIdByPos(Position.FASHION_HAIR);
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getFeatureImg() {
		// PetBodyEquipBag wearings =
		// owner.getInventory().getBagByPet(this.getUUID());
		// return wearings.getFeatureIdByPos(Position.FASHION_HAIR);
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public int getCapImg() {
		// PetBodyEquipBag wearings =
		// owner.getInventory().getBagByPet(this.getUUID());
		// return wearings.getCapIdByPos(Position.CAP,Position.FASHION_HAIR);
		return 0;
	}

	// ///////////////////////////////
	// 以下是武将各属性访问接口
	// ///////////////////////////////

	public String getName() {
		return super.getName();
	}

	public void setName(String name) {
		this.name = name;
		super.setName(name);
	}

	// //////////////////////
	// 基于baseIntProperties or baseStrProperties各种属性的getter/setter（内置属性）
	// //////////////////////

	/*
	 * 重新计算战斗力，包含角色，装备，道具，buff
	 */
	public void initTmpZhandouli() {
		// 角色的
		double zhandouli = (500 + this.getGrow() * this.getLevel())
				* (1 + (this.getLevel() - 1) / (this.getLevel() + 200));
		// TODO 装备道具buff
		zhandouli += 0;
		this.baseIntAProperties
				.set(RoleBaseIntAProperties.ZHANDOULI, zhandouli);
	}

	/**
	 * 增加战斗力
	 * 
	 * @param zhandouli
	 */
	protected void addZhandouli(double zhandouli) {
		this.baseIntAProperties.set(RoleBaseIntAProperties.ZHANDOULI,
				this.getZhandouli() + zhandouli);
	}

	/**
	 * 获得武将模板
	 * 
	 * @return
	 */
	public PetTemplate getTemplate() {
		return template;
	}

	public Timestamp getCreateDate() {
		return (Timestamp) this.finalProps
				.getObject(RoleFinalProps.CREATE_TIME);
	}

	public void setCreateDate(Timestamp createDate) {
		this.finalProps.setObject(RoleFinalProps.CREATE_TIME, createDate);
		this.onModified();
	}

	// 不需要调用onModified()的属性,也就是模板数据,主要为了前台差量更新

	public int getPetType() {
		return 0;
		// return this.baseIntProperties.get(RoleBaseIntProperties.PET_TYPE);
	}

	public void setPetType(int petType) {
		// this.baseIntProperties.set(RoleBaseIntProperties.PET_TYPE, petType);
	}

	// ////////////////////////////////
	// 从PropertyManager的接口
	// ////////////////////////////////

 

	
	public int getGrowUpAtkAdd(int toGrow) {
		return HumanConstants.GROW_ADD_ATK; 
	}

	public int getGrowUpDefAdd(int toGrow) {
		return HumanConstants.GROW_ADD_DEF;
	}

	public int getGrowUpMaxHpAdd(int toGrow) {
		return HumanConstants.GROW_ADD_MAX_HP;
	}
	public int getGrowAtk(){
		return (int) Math.round(HumanConstants.GROW_ADD_ATK * this.getGrow());
	}
	public int getGrowDef(){
		return (int) Math.round(HumanConstants.GROW_ADD_DEF * this.getGrow());
	}
	public int getGrowMaxHp(){
		return (int) Math.round(HumanConstants.GROW_ADD_MAX_HP * this.getGrow());
	}
	@Override
	public int addHP(int hp) {
		hp = super.addHP(hp);
		if (hp < 0) {
			// 扣除血，发送事件通知
			// 扣除血，发送事件通知
			if (this.lifeCycle.isActive()) {
				HPReduceEvent event = new HPReduceEvent(this);
				Globals.getEventService().fireEvent(event);
			}
		}
		return hp;
	}

	@Override
	public void addMaxHp(double hp) {
		boolean bAdd = hp > 0;
		super.addMaxHp(hp);

		if (this.lifeCycle.isActive()) {
			if (this.getCurHp() > hp) {
				this.setCurHp(hp);
			}
			if (bAdd) {
				HPReduceEvent event = new HPReduceEvent(this);
				Globals.getEventService().fireEvent(event);
			}
		}

	}
	@Override
	public boolean levelUp(boolean changeExp){
		boolean rs = super.levelUp(changeExp);
		if(rs){
			this.owner.sendSystemMessage(Globals.getLangService().read(PlayerLangConstants_30000.ROLE_LEVEL_UP, this.getName()));
			//更新武将兵法装备位
			this.owner.getWarcraftInventory().updateWarcraftEquipLevel(this.getLevel(), this.getUUID());
		}
		return rs;
	}
	protected int getMaxLevel() {
		return 100;
	}

	public double getBuffAtk() {
		return this.propertyManager.getBuffAtk();
	}

	public void setBuffAtk(double buffAtk) {
		this.propertyManager.setBuffAtk(buffAtk);
	}

	public double getBuffDef() {
		return this.propertyManager.getBuffDef();
	}

	public void setBuffDef(double buffDef) {
		this.propertyManager.setBuffDef(buffDef);
	}

	public double getBuffMaxHp() {
		return this.propertyManager.getBuffMaxHp();
	}

	@Override
	public void setBuffMaxHp(double buffMaxHp) {
		this.propertyManager.setBuffMaxHp(buffMaxHp);
	}
	public  void clearBuffValue(){
		this.getPropertyManager().getPetNormalProperty().setBuffAtk(0);
		this.getPropertyManager().getPetNormalProperty().setBuffDef(0);
		this.getPropertyManager().getPetNormalProperty().setBuffMaxHp(0);
	}

	public RoleToSkillTemplate getSkillTemplate() {
		return skillTemplate;
	}

	public void setSkillTemplate(RoleToSkillTemplate skillTemplate) {
		this.skillTemplate = skillTemplate;
	}

	@Override
	protected void initPropsFromTmp() {
		// super.setSpd(initTemplate.getInitSpd());
		super.setCri(template.getCri());
		super.setShanbi(template.getShanbi());

		// TODO 模板木有伤害
		// super.setShanghai(template.getAtk() );
		super.setMianshang(template.getMianshang());
		super.setFanji(template.getFanji());
		super.setMingzhong(template.getHit());
		super.setLianji(template.getLianji());
		super.setRenXing(0);
		
		double atk = super.getRoleGrowAtk(this.getLevel())
				* (this.getGrow())
				+ this.template.getAtk();
		atk += Globals.getHumanService().getLevelAtk(this.getLevel()) * this.getRolePropertyFactorTemplate().getAtkVocationFactor();
		atk += Globals.getHumanService().getResearchTemplateByLevel(this.getAtkLevel()).getAddAtk();
		super.setAtk(atk);
		
		double def = super.getRoleGrowDef(this.getLevel())
				* (this.getGrow())
				+ this.template.getDef();
		def += Globals.getHumanService().getLevelDef(this.getLevel()) * this.getRolePropertyFactorTemplate().getDefVocationFactor();
		def += Globals.getHumanService().getResearchTemplateByLevel(this.getDefLevel()).getAddDef();
		super.setDef(def);
		
		double maxHp = super.getRoleGrowHp(this.getLevel())
				* (this.getGrow())
				+ this.template.getHp();
		maxHp += Globals.getHumanService().getLevelMaxHp(this.getLevel()) * this.getRolePropertyFactorTemplate().getHpVocationFactor();
		maxHp += Globals.getHumanService().getResearchTemplateByLevel(this.getMaxHpLevel()).getAddMaxHp();
		super.setMaxHp(maxHp);
		
		// 速度 = 特有速度
		double speed = template.getSpd();

		super.setSpd(speed);
	
		if(super.transferVocationStarTemplate != null&& 
				OpenFunction.isopen(this.owner.getPropertyManager().getOpenFunc(), OpenFunction.guanzhi)){
			super.addAtk(super.transferVocationStarTemplate.getAddAtk());
			super.addDef(super.transferVocationStarTemplate.getAddDef());
			super.addMaxHp(super.transferVocationStarTemplate.getAddHp());
		}
		
		// 角色的
		double zhandouli = (500 + this.getGrow() * this.getLevel())
				* (1 + (this.getLevel() - 1) / (this.getLevel() + 200));
		 
		this.baseIntAProperties.set(RoleBaseIntAProperties.ZHANDOULI, zhandouli);
		
		super.setShortAtk(Globals.getHumanService().getResearchTemplateByLevel(this.getShortAtkLevel()).getAddShortAtk());
		super.setRemoteAtk(Globals.getHumanService().getResearchTemplateByLevel(this.getRemoteAtkLevel()).getAddRemoteAtk());
		super.setShortDef(Globals.getHumanService().getResearchTemplateByLevel(this.getShortDefLevel()).getAddShortDef());
		super.setRemoteDef(Globals.getHumanService().getResearchTemplateByLevel(this.getRemoteDefLevel()).getAddRemoteDef());
		
		super.setCooldownRound(template.getCooldownRound());
		super.setMaxMorale(template.getMaxMorale());
		super.setCastAnim(template.getCastAnim());
		super.setAttackedAnim(template.getAttackedAnim());
		super.setVocationAnim(template.getVocationAnim());
	}
	
	protected List<KeyValuePair<Integer, Integer>> generatorIntegerInfomation(){
		List<KeyValuePair<Integer, Integer>> integerInfomations=super.generatorIntegerInfomation();
		integerInfomations.add(new KeyValuePair<Integer, Integer>(
				this.propertyManager.getPetIntProperty()
						.getKeyByProp(PetIntProperty.STAR),this.propertyManager.getStar()));
		return integerInfomations;
	}

}
