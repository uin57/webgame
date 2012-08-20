package com.pwrd.war.gameserver.dayTask;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.db.model.DayTaskInfoEntity;
import com.pwrd.war.gameserver.human.Human;

public class HumanDayTaskInfo implements
		PersistanceObject<String, DayTaskInfoEntity>, InitializeRequired {

	private String id;
	
	/** 所属人 */
	private Human owner;

	/** 是否已经在数据库中 */
	private boolean inDb;

	/** 生命期 */
	private final LifeCycle lifeCycle;

	/** 所属角色ID */
	private String charId;

	/** 当天任务的次数 根据弹药 */
	private int dayTimes;	
	
	/** 当前任务的次数 */
	private int taskTimes;
	
	/** 剩余弹药 */
	private int bullet;
	
	/** ture为完成任务 */
	private boolean taskFlag = true;	
	
	/** 当前任务id */
	private String taskId;


	public HumanDayTaskInfo() {
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	public HumanDayTaskInfo(Human owner){
		this.owner = owner;
		this.lifeCycle =  new LifeCycleImpl(this); 
	}
	
	@Override
	public void init() {
	}

	@Override
	public String getCharId() {
		return this.charId;
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
		onModified();
	}

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

	public void delete(){
		if (owner != null) { 
			this.lifeCycle.checkModifiable();
			this.lifeCycle.destroy();
			owner.getPlayer().getDataUpdater().addDelete(getLifeCycle());
			 
		}
	}
	
	@Override
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
	public boolean isInDb() {
		return this.inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
	}

	@Override
	public DayTaskInfoEntity toEntity() {
		DayTaskInfoEntity entity = new DayTaskInfoEntity();
		entity.setId(this.id);
		entity.setCharId(owner.getUUID());
		entity.setBullet(this.bullet);
		entity.setDayTimes(this.dayTimes);
		entity.setTaskFlag(this.taskFlag);
		entity.setTaskId(this.taskId);
		entity.setTaskTimes(this.taskTimes);
		
		return entity;
	}

	@Override
	public void fromEntity(DayTaskInfoEntity entity) {
		this.setDbId(entity.getId());
		this.setInDb(true);
		this.setBullet(entity.getBullet());
		this.setCharId(entity.getCharId());
		this.setDayTimes(entity.getDayTimes());
		this.setTaskFlag(entity.isTaskFlag());
		this.setTaskId(entity.getTaskId());
		this.setTaskTimes(entity.getTaskTimes());
	}

	/**
	 * 激活此副本，并初始化属性 此方法在玩家登录加载完数据，或者获得新武将时调用
	 */
	public void activeAndInitProps() {
		getLifeCycle().activate();
		// getPropertyManager().updateProperty(RolePropertyManager.PROP_FROM_MARK_ALL);
	}


	@Override
	public String getGUID() { 
		return this.id;
	}

	public Human getOwner() {
		return owner;
	}

	public void setCharId(String charId) {
		this.charId = charId;
	}

	public int getDayTimes() {
		return dayTimes;
	}

	public void setDayTimes(int dayTimes) {
		this.dayTimes = dayTimes;
	}

	public int getTaskTimes() {
		return taskTimes;
	}

	public void setTaskTimes(int taskTimes) {
		this.taskTimes = taskTimes;
	}

	public int getBullet() {
		return bullet;
	}

	public void setBullet(int bullet) {
		this.bullet = bullet;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public boolean isTaskFlag() {
		return taskFlag;
	}

	public void setTaskFlag(boolean taskFlag) {
		this.taskFlag = taskFlag;
	}

}
