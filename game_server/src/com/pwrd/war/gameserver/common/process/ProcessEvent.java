package com.pwrd.war.gameserver.common.process;

import java.sql.Timestamp;

import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.ProcessEventEntity;
import com.pwrd.war.gameserver.common.process.ProcessDef.ProcessEventType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.JsonPropDataHolder;

public abstract class ProcessEvent implements PersistanceObject<Long, ProcessEventEntity>,JsonPropDataHolder{
	
	/** 队列的事件UUID */
	private long eventUUID;
	
	/** 所有者 */
	private Human owner;
	
	/** 此实例是否在db中 */
	private boolean isInDb;
	
	/** 队列事件的生命期的状态 */
	private final LifeCycle lifeCycle;
	
	/** 队列事件的事件类型 */
	private ProcessEventType eventType;
	
	/** 创建时间 */
	private Timestamp createTime;
	
	/** 结束时间 */
	private Timestamp endTime;
	
	private ProcessEvent(Human owner) {
		lifeCycle = new LifeCycleImpl(this);
		this.owner = owner;
	}


	@Override
	public void setDbId(Long id) {
		this.eventUUID = id;
	}

	@Override
	public Long getDbId() {
		return eventUUID;
	}

	@Override
	public String getGUID() {
		return "event#" + getDbId();
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
		return owner != null ? owner.getUUID() : "";
	}

	public long getEventUUID() {
		return eventUUID;
	}


	public ProcessEventType getEventType() {
		return eventType;
	}
	
	
	public void setEventType(ProcessEventType eventType) {
		this.eventType = eventType;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}
	
	/**
	 * 只有加速进程,才会更新库,其他不需要更新
	 * @param endTime
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
		this.setModified();
	}


	@Override
	public ProcessEventEntity toEntity() {
		ProcessEventEntity processEntity = new ProcessEventEntity();
		processEntity.setId(this.getDbId());
		processEntity.setCharId(this.getCharId());
		processEntity.setType(this.getEventType().getIndex());
		processEntity.setCreateTime(this.getCreateTime());
		processEntity.setEndTime(this.getEndTime());
		processEntity.setProps(toJsonProp());
		return processEntity;
	}

	@Override
	public void fromEntity(ProcessEventEntity entity) {
		this.setDbId(entity.getId());
		this.setEventType(ProcessEventType.valueOf(entity.getType()));
		this.setCreateTime(entity.getCreateTime());
		this.setEndTime(entity.getEndTime());
		this.loadJsonProp(entity.getProps());		
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public void setModified() {
		if (owner != null) {
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive()) {
				// 邮件的生命期处于活动状态,则执行通知更新机制进行
				this.onUpdate();
			}
		}
	}
	
	/**
	 * 在线更新
	 */
	private void onUpdate() {
		this.owner.getPlayer().getDataUpdater().addUpdate(
				this.getLifeCycle());
	}
	
	/**
	 * 在线删除
	 */
	public void onDelete() {
		this.lifeCycle.destroy();
		this.owner.getPlayer().getDataUpdater().addDelete(
				this.getLifeCycle());
	}
	
	/**
	 * 不同进程事件进行属性json存储
	 */
	@Override
	abstract public String toJsonProp() ;

	/**
	 * 加载json到对应属性上
	 */
	@Override
	abstract public void loadJsonProp(String value);
}
