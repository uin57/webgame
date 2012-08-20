package com.pwrd.war.gameserver.story;

import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.db.model.FinishedStoryEntity;
import com.pwrd.war.gameserver.human.Human;

/**
 * 已完成的剧情
 * 
 */
public class FinishedStory implements PersistanceObject<String, FinishedStoryEntity> {
	/** 主键 */
	private String dbId;
	/** 剧情ID */
	private String storyId;
	
	/** 是否已经在数据库中 */
	private boolean inDb;
	/** 生命期 */
	private final LifeCycle lifeCycle;
	/** 所有者 */
	private Human owner;

	public FinishedStory(Human owner, String storyId) {
		super();
		this.owner = owner;
		this.storyId = storyId;
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

	/**
	 * 获取人物
	 * 
	 * @return
	 */
	public Human getOwner() {
		return owner;
	}

	/**
	 * 设置人物
	 * 
	 * @param owner
	 * @throws IllegalArgumentException
	 *             当owner为空时抛出
	 */
	public void setOwner(Human owner) {
		Assert.notNull(owner);
		this.owner = owner;
		this.setModified();
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
	public String getGUID() {
		return this.dbId;
	}

	@Override
	public String getDbId() {
		return dbId;
	}

	@Override
	public void setDbId(String dbId) {
		this.dbId = dbId;
		this.setModified();
	}

	@Override
	public String getCharId() {
		if (owner != null) {
			return owner.getUUID();
		} else {
			return "";
		}
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	/**
	 * 直接将任务设置为修改状态
	 */
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

	@Override
	public void fromEntity(FinishedStoryEntity entity) {
		this.setDbId(entity.getId());
		this.setInDb(true);
		this.setStoryId(entity.getStoryId());
	}

	@Override
	public FinishedStoryEntity toEntity() {
		FinishedStoryEntity entity = new FinishedStoryEntity();
		entity.setId(this.getDbId());
		entity.setStoryId(this.getStoryId());
		entity.setCharId(this.getCharId());
		return entity;
	}
}
