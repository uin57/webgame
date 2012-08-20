package com.pwrd.war.gameserver.human.cooldown;

import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.CooldownEntity;
import com.pwrd.war.gameserver.human.Human;

public class Cooldown implements PersistanceObject<String, CooldownEntity> {
	
	/**玩家sn*/
	private String humanUUID;
	
	private String dbId;
	
	private int index;
	
	private int coolType;
	
	private long startTime;
	
	private long endTime;
	
	private Human owner;
	
	/** 是否已在数据库中 */
	private boolean isInDb;

	/** 是否已经变更了 */
	private boolean modified =false;

	/** 生命期 */
	private final LifeCycle lifeCycle;

	@Override
	public void setDbId(String id) {
		this.dbId = id;
	}

	@Override
	public String getDbId() {
		return this.dbId;
	}

 
	public Cooldown(Human owner) {
		this.lifeCycle = new LifeCycleImpl(this);
		lifeCycle.activate();
		this.owner = owner;
	}

	@Override
	public String getGUID() {
		return this.dbId;
	}

	@Override
	public boolean isInDb() {
		return isInDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.isInDb=inDb;
	}

	@Override
	public String getCharId() {
		return owner == null ? "" : owner.getCharId();
	}

	@Override
	public CooldownEntity toEntity() {
		CooldownEntity entity =new CooldownEntity();
		entity.setCoolType(getCoolType());
		entity.setId(getDbId());
		entity.setHumanUUID(humanUUID);
		entity.setIndex(index);
		entity.setStartTime(getStartTime());
		entity.setEndTime(getEndTime());
		return entity;
	}

	@Override
	public void fromEntity(CooldownEntity entity) {
         setInDb(true);
         setDbId(entity.getId());
         setHumanUUID(entity.getHumanUUID());
         setIndex(entity.getIndex());
         setCoolType(entity.getCoolType());
         setEndTime(entity.getEndTime());
         setStartTime(entity.getStartTime());
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public void setModified() {
		modified = true;
		// 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive()) {
			// 物品的生命期处于活动状态,并且该物品不是空的,则执行通知更新机制进行
			owner.getPlayer().getDataUpdater().addUpdate(this.getLifeCycle());
		}
	}
	
	
	public void onDelete() {
		modified = true;
		this.lifeCycle.deactivate();
		
		// 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive()) {
			// 物品的生命期处于活动状态,并且该物品不是空的,则执行通知更新机制进行
			owner.getPlayer().getDataUpdater().addDelete(this.getLifeCycle());
		}
	}

	 

	public int getCoolType() {
		return coolType;
	}

	public void setCoolType(int coolType) {
		this.coolType = coolType;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}
 

	public String getHumanUUID() {
		return humanUUID;
	}

	public void setHumanUUID(String humanUUID) {
		this.humanUUID = humanUUID;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
