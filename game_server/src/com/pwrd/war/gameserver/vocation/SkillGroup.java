package com.pwrd.war.gameserver.vocation;

import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.SkillGroupEntity;
import com.pwrd.war.gameserver.human.Human;

public class SkillGroup implements PersistanceObject<String, SkillGroupEntity> {

	/** 是否已经变更了 */
	private boolean modified;

	/** 物品的生命期的状态 */
	private final LifeCycle lifeCycle;

	/** 此实例是否在db中 */
	private boolean isInDb;

	/** 所有者 */
	private Human owner;

	/** 编号 */
	private int number;
	/** 是否被选中 */
	private boolean choose;
	
	private String name;

	private String id;
	
	private String skillSn1;
	
	private int skillRank1;
	
	private String skillSn2;
	private int skillRank2;
	
	private String skillSn3;
	private int skillRank3;
	
	private String skillSn4;
	private int skillRank4;



	public SkillGroup() {
		lifeCycle = new LifeCycleImpl(this);
		lifeCycle.deactivate();
	}
	
	public SkillGroup(Human owner) {
		lifeCycle = new LifeCycleImpl(this);
		lifeCycle.activate();
		this.owner = owner;
	}

	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	

	@Override
	public void setDbId(String id) {
		this.id = id;

	}

	@Override
	public String getDbId() {
		return this.id;
	}

	@Override
	public String getGUID() {
		return this.id;
	}

	@Override
	public boolean isInDb() {
		return isInDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.isInDb = inDb;

	}

	@Override
	public String getCharId() {
		return owner == null ? "" : owner.getCharId();
	}

	@Override
	public void setModified() {
		modified = true;
		// 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive()) {
			// 物品的生命期处于活动状态,并且该物品不是空的,则执行通知更新机制进行
			this.owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
		}
	}



	@Override
	public void fromEntity(SkillGroupEntity entity) {
		setDbId(entity.getId());
		number=entity.getNumber();
		name=entity.getName();
		choose=entity.isChoose();
		skillSn1 =entity.getSkillSn1();
		skillRank1=entity.getSkillRank1();
		skillSn2 =entity.getSkillSn2();
		skillRank2=entity.getSkillRank2();
		skillSn3=entity.getSkillSn3();
		skillRank3=entity.getSkillRank3();
		skillSn4 =entity.getSkillSn4();
		skillRank4=entity.getSkillRank4();
	}

	public static SkillGroup buildSkillGroup(Human human,
			SkillGroupEntity entity) {
		SkillGroup skillGroup = new SkillGroup(human);
		skillGroup.fromEntity(entity);
		return skillGroup;
	}

	@Override
	public SkillGroupEntity toEntity() {
		SkillGroupEntity entity = new SkillGroupEntity();
		entity.setNumber(number);
		entity.setId(id);
		entity.setName(name);
		entity.setChoose(choose);
		entity.setSkillSn1(skillSn1);
		entity.setSkillRank1(skillRank1);
		entity.setSkillSn2(skillSn2);
		entity.setSkillRank2(skillRank2);
		entity.setSkillSn3(skillSn3);
		entity.setSkillRank3(skillRank3);
		entity.setSkillSn4(skillSn4);
		entity.setSkillRank4(skillRank4);
		return entity;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		modified = true;
		this.name = name;
	}

	public String getSkillSn1() {
		return skillSn1;
	}

	public int getSkillRank1() {
		return skillRank1;
	}

	public String getSkillSn2() {
		return skillSn2;
	}

	public int getSkillRank2() {
		return skillRank2;
	}

	public String getSkillSn3() {
		return skillSn3;
	}

	public int getSkillRank3() {
		return skillRank3;
	}

	public String getSkillSn4() {
		return skillSn4;
	}

	public int getSkillRank4() {
		return skillRank4;
	}

	public void setSkillSn1(String skillSn1) {
		modified = true;
		this.skillSn1 = skillSn1;
	}

	public void setSkillRank1(int skillRank1) {
		modified = true;
		this.skillRank1 = skillRank1;
	}

	public void setSkillSn2(String skillSn2) {
		modified = true;
		this.skillSn2 = skillSn2;
	}

	public void setSkillRank2(int skillRank2) {
		modified = true;
		this.skillRank2 = skillRank2;
	}

	public void setSkillSn3(String skillSn3) {
		modified = true;
		this.skillSn3 = skillSn3;
	}

	public void setSkillRank3(int skillRank3) {
		modified = true;
		this.skillRank3 = skillRank3;
	}

	public void setSkillSn4(String skillSn4) {
		modified = true;
		this.skillSn4 = skillSn4;
	}

	public void setSkillRank4(int skillRank4) {
		modified = true;
		this.skillRank4 = skillRank4;
	}

	public int getNumber() {
		return number;
	}

	public boolean getChoose() {
		return choose;
	}

	public void setNumber(int number) {
		modified = true;
		this.number = number;
	}

	public void setChoose(boolean choose) {
		modified = true;
		this.choose = choose;
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		modified = true;
		this.owner = owner;
	}





}
