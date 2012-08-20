package com.pwrd.war.gameserver.vocation;

import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.VocationSkillEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;

public class VocationSkill implements
		PersistanceObject<String, VocationSkillEntity> {

	/** 是否已经变更了 */
	private boolean modified;

	/** 物品的生命期的状态 */
	private final LifeCycle lifeCycle;

	/** 此实例是否在db中 */
	private boolean isInDb;
	
	private int vocationType;

	/** 所有者 */
	private Human owner;

	private String sn;
	
	private String humanSn;
	
	private SkillUnit[] skillUnits = new SkillUnit[4];
	
    private SkillGroup[] skillGroups =new SkillGroup[4];
    

	public VocationSkill(){
		lifeCycle = new LifeCycleImpl(this);
		lifeCycle.activate();
	}
	

	public VocationSkill(Human owner,String humanSn) {
		lifeCycle = new LifeCycleImpl(this);
		lifeCycle.activate();
		this.owner=owner;
		this.humanSn=humanSn;
	}

	@Override
	public void setDbId(String id) {
		sn = id;

	}

	@Override
	public String getDbId() {
		return sn;
	}

	@Override
	public String getGUID() {
		return sn;
	}

	@Override
	public boolean isInDb() {
		return this.isInDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.isInDb = inDb;
		for (SkillGroup skillGroup : skillGroups) {
			skillGroup.setInDb(inDb);
		}
	}

	@Override
	public String getCharId() {
		return owner == null ? "" : owner.getCharId();
	}

	@Override
	public VocationSkillEntity toEntity() {
		VocationSkillEntity vacationSkillEntity =new VocationSkillEntity();
		vacationSkillEntity.setHumanSn(humanSn);
		vacationSkillEntity.setVocationType(vocationType);
		vacationSkillEntity.setId(this.getDbId());
		vacationSkillEntity.setSkillLevel1(skillUnits[0].getSkillRank());
		vacationSkillEntity.setSkillSn1(skillUnits[0].getSkillSn());
		vacationSkillEntity.setSkillLevel2(skillUnits[1].getSkillRank());
		vacationSkillEntity.setSkillSn2(skillUnits[1].getSkillSn());
		vacationSkillEntity.setSkillLevel3(skillUnits[2].getSkillRank());
		vacationSkillEntity.setSkillSn3(skillUnits[2].getSkillSn());
		vacationSkillEntity.setSkillLevel4(skillUnits[3].getSkillRank());
		vacationSkillEntity.setSkillSn4(skillUnits[3].getSkillSn());
		vacationSkillEntity.setSkillGroup1(this.skillGroups[0].getDbId());
		vacationSkillEntity.setSkillGroup2(this.skillGroups[1].getDbId());
		vacationSkillEntity.setSkillGroup3(this.skillGroups[2].getDbId());
		vacationSkillEntity.setSkillGroup4(this.skillGroups[3].getDbId());
		return vacationSkillEntity;
	}

	@Override
	public void fromEntity(VocationSkillEntity entity) {
		this.setDbId(entity.getId());
		humanSn=entity.getHumanSn();
		vocationType=entity.getVocationType();
		skillUnits[0] =new SkillUnit();
		skillUnits[0].setSkillRank(entity.getSkillLevel1());
		skillUnits[0].setSkillSn(entity.getSkillSn1());
		skillUnits[1] =new SkillUnit();
		skillUnits[1].setSkillRank(entity.getSkillLevel2());
		skillUnits[1].setSkillSn(entity.getSkillSn2());
		skillUnits[2] =new SkillUnit();
		skillUnits[2].setSkillRank(entity.getSkillLevel3());
		skillUnits[2].setSkillSn(entity.getSkillSn3());
		skillUnits[3] =new SkillUnit();
		skillUnits[3].setSkillRank(entity.getSkillLevel4());
		skillUnits[3].setSkillSn(entity.getSkillSn4());
		skillGroups[0]=SkillGroup.buildSkillGroup(this.owner, Globals.getDaoService().getSkillGroupDao().get(entity.getSkillGroup1()));
		skillGroups[1]=SkillGroup.buildSkillGroup(this.owner, Globals.getDaoService().getSkillGroupDao().get(entity.getSkillGroup2()));
		skillGroups[2]=SkillGroup.buildSkillGroup(this.owner, Globals.getDaoService().getSkillGroupDao().get(entity.getSkillGroup3()));
		skillGroups[3]=SkillGroup.buildSkillGroup(this.owner, Globals.getDaoService().getSkillGroupDao().get(entity.getSkillGroup4()));
		this.setDbId(entity.getId());
	}
	
	public static VocationSkill buildVacationSkill(Human owner,VocationSkillEntity entity){
		VocationSkill  vacationSkill =new VocationSkill(owner,owner.getUUID());
		vacationSkill.fromEntity(entity);
		return vacationSkill;
	}

	@Override
	public LifeCycle getLifeCycle() {
		return this.lifeCycle;
	}

	@Override
	public void setModified() {
		modified = true;
		// 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive()) {
			// 物品的生命期处于活动状态,并且该物品不是空的,则执行通知更新机制进行
			owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
		}
	}






	public int getVocationType() {
		return vocationType;
	}

	public void setVocationType(int vocationType) {
		modified = true;
		this.vocationType = vocationType;
	}


	public SkillUnit[] getSkillUnits() {
		return skillUnits;
	}


	public void setSkillUnits(SkillUnit[] skillUnits) {
		modified = true;
		this.skillUnits = skillUnits;
	}


	public SkillGroup[] getSkillGroups() {
		return skillGroups;
	}


	public void setSkillGroups(SkillGroup[] skillGroups) {
		modified = true;
		this.skillGroups = skillGroups;
	}


	public Human getOwner() {
		return owner;
	}


	public void setOwner(Human owner) {
		this.owner = owner;
	}


	public void setModified(boolean modified) {
		this.modified = modified;
	}







}
