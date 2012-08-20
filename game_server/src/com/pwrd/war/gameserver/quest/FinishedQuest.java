package com.pwrd.war.gameserver.quest;

import java.sql.Timestamp;

import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.db.model.FinishedQuestEntity;
import com.pwrd.war.gameserver.human.Human;

/**
 * 已完成的任务
 * 
 */
public class FinishedQuest implements PersistanceObject<String, FinishedQuestEntity> {
	/** 主键 */
	private String dbId;
	/** 任务ID */
	private int questId;
	/** 任务开始时间 */
	private Timestamp startTime;
	/** 任务结束时间 */
	private Timestamp endTime;
	/** 任务每天完成次数 */
	private int dailyTimes;
	
	/** 是否已经在数据库中 */
	private boolean inDb;
	/** 生命期 */
	private final LifeCycle lifeCycle;
	/** 所有者 */
	private Human owner;

	public FinishedQuest() {
		this.lifeCycle = new LifeCycleImpl(this);
	}

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
		this.setModified();
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
		this.setModified();
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
		this.setModified();
	}

	public int getDailyTimes() {
		return dailyTimes;
	}

	public void setDailyTimes(int dailyTimes) {
		this.dailyTimes = dailyTimes;
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
	public void fromEntity(FinishedQuestEntity entity) {
		this.setDbId(entity.getId());
		this.setInDb(true);
		this.setStartTime(entity.getStartTime());
		this.setEndTime(entity.getEndTime());
		this.setQuestId(entity.getQuestId());
		this.setDailyTimes(entity.getDailyTimes());
	}

	@Override
	public FinishedQuestEntity toEntity() {
		FinishedQuestEntity quest = new FinishedQuestEntity();
		quest.setId(this.getDbId());
		quest.setStartTime(this.getStartTime());
		quest.setEndTime(this.getEndTime());
		quest.setQuestId(this.getQuestId());
		quest.setCharId(this.getCharId());
		quest.setDailyTimes(this.getDailyTimes());
		return quest;
	}
}
