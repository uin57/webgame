package com.pwrd.war.gameserver.tree;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.db.model.TreeInfoEntity;
import com.pwrd.war.db.model.TreeWaterEntity;
import com.pwrd.war.gameserver.human.Human;

public class HumanTreeWaterInfo implements
		PersistanceObject<String, TreeWaterEntity>, InitializeRequired {

	private String id;
	
	/** 所属人 */
	private Human owner;

	/** 是否已经在数据库中 */
	private boolean inDb;

	/** 生命期 */
	private final LifeCycle lifeCycle;
	
	/** 浇水角色ID */
	private String charId;
	
	/** 浇水角色名称 */
	private String charName;
	
	/** 被浇水角色ID */
	private String toCharId;
	
	/** 被浇水角色名称 */
	private String toCharName;	
	
	/** 浇水的时间 */
	private long waterTime;

	public HumanTreeWaterInfo() {
		super();
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	public HumanTreeWaterInfo(Human owner){
		super();
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
	public TreeWaterEntity toEntity() {
		TreeWaterEntity entity = new TreeWaterEntity();
		if(StringUtils.isNotEmpty(this.id)){
			entity.setId(this.id);
		}

		entity.setCharId(this.getCharId());
		entity.setCharName(this.getCharName());
		entity.setToCharId(this.getToCharId());
		entity.setToCharName(this.getToCharName());
		entity.setWaterTime(this.getWaterTime());
		
		return entity;
	}

	@Override
	public void fromEntity(TreeWaterEntity entity) {
		this.setDbId(entity.getId());
		this.setInDb(true);
		this.setCharId(entity.getCharId());
		this.setCharName(entity.getCharName());
		this.setToCharId(entity.getToCharId());
		this.setToCharName(entity.getToCharName());
		this.setWaterTime(entity.getWaterTime());
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

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public String getToCharId() {
		return toCharId;
	}

	public void setToCharId(String toCharId) {
		this.toCharId = toCharId;
	}

	public String getToCharName() {
		return toCharName;
	}

	public void setToCharName(String toCharName) {
		this.toCharName = toCharName;
	}

	public long getWaterTime() {
		return waterTime;
	}

	public void setWaterTime(long waterTime) {
		this.waterTime = waterTime;
	}


}
