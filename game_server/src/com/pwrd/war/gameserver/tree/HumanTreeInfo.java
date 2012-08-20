package com.pwrd.war.gameserver.tree;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.db.model.TreeInfoEntity;
import com.pwrd.war.gameserver.human.Human;

public class HumanTreeInfo implements
		PersistanceObject<String, TreeInfoEntity>, InitializeRequired {

	private String id;
	
	/** 所属人 */
	private Human owner;

	/** 是否已经在数据库中 */
	private boolean inDb;

	/** 生命期 */
	private final LifeCycle lifeCycle;
	
	/** 所属角色ID */
	private String charId;
	
	/** 摇钱次数 */
	private int shakeTimes;	
	
	/** 上次摇钱的时间 */
	private long lastShakeTime;
	
	/** 给自己的浇水次数 */
	private int waterTimes;
	
	/** 给自己的上次浇水时间 */
	private long lastWaterTime;
	
	/** 给好友的浇水次数 */
	private int friendWaterTimes;
	
	/** 给好友的上次浇水时间 */
	private long lastFriendWaterTime;
	
	/** 摇钱树当前经验 */
	private int curTreeExp;
	
	/** 摇钱树最大经验 */
	private int treeLevel;
	
	/** 已经领取的果子等级 */
	private int fruitLevel;
	
	/** 摇钱树升级经验 */
	private int maxTreeExp;


	public HumanTreeInfo() {
		super();
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	public HumanTreeInfo(Human owner){
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
	public TreeInfoEntity toEntity() {
		TreeInfoEntity entity = new TreeInfoEntity();
		if(StringUtils.isNotEmpty(this.id)){
			entity.setId(this.id);
		}

		entity.setCharId(this.getCharId());
		entity.setShakeTimes(this.getShakeTimes());		
		entity.setLastShakeTime(this.getLastShakeTime());
		
		entity.setCurTreeExp(this.getCurTreeExp());
		entity.setFriendWaterTimes(this.getFriendWaterTimes());
		entity.setFruitLevel(this.getFruitLevel());
		entity.setLastWaterTime(this.getLastWaterTime());
		entity.setTreeLevel(this.treeLevel);
		entity.setWaterTimes(this.getWaterTimes());
		entity.setLastFriendWaterTime(this.lastFriendWaterTime);
		entity.setMaxTreeExp(this.maxTreeExp);
		
		return entity;
	}

	@Override
	public void fromEntity(TreeInfoEntity entity) {
		this.setDbId(entity.getId());
		this.setInDb(true);
		this.setCharId(entity.getCharId());
		this.setShakeTimes(entity.getShakeTimes());
		this.setLastShakeTime(entity.getLastShakeTime());
		
		this.setCurTreeExp(entity.getCurTreeExp());
		this.setFriendWaterTimes(entity.getFriendWaterTimes());
		this.setFruitLevel(entity.getFruitLevel());
		this.setLastWaterTime(entity.getLastWaterTime());
		this.setTreeLevel(entity.getTreeLevel());
		this.setWaterTimes(entity.getWaterTimes());
		this.setLastFriendWaterTime(entity.getLastFriendWaterTime());
		this.setMaxTreeExp(entity.getMaxTreeExp());
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

	public int getShakeTimes() {
		return shakeTimes;
	}

	public void setShakeTimes(int shakeTimes) {
		this.shakeTimes = shakeTimes;
	}

	public long getLastShakeTime() {
		return lastShakeTime;
	}

	public void setLastShakeTime(long lastShakeTime) {
		this.lastShakeTime = lastShakeTime;
	}

	public int getWaterTimes() {
		return waterTimes;
	}

	public void setWaterTimes(int waterTimes) {
		this.waterTimes = waterTimes;
	}

	public long getLastWaterTime() {
		return lastWaterTime;
	}

	public void setLastWaterTime(long lastWaterTime) {
		this.lastWaterTime = lastWaterTime;
	}

	public int getFriendWaterTimes() {
		return friendWaterTimes;
	}

	public void setFriendWaterTimes(int friendWaterTimes) {
		this.friendWaterTimes = friendWaterTimes;
	}

	public int getCurTreeExp() {
		return curTreeExp;
	}

	public void setCurTreeExp(int curTreeExp) {
		this.curTreeExp = curTreeExp;
	}

	public int getTreeLevel() {
		return treeLevel;
	}

	public void setTreeLevel(int treeLevel) {
		this.treeLevel = treeLevel;
	}

	public int getFruitLevel() {
		return fruitLevel;
	}

	public void setFruitLevel(int fruitLevel) {
		this.fruitLevel = fruitLevel;
	}

	public long getLastFriendWaterTime() {
		return lastFriendWaterTime;
	}

	public void setLastFriendWaterTime(long lastFriendWaterTime) {
		this.lastFriendWaterTime = lastFriendWaterTime;
	}

	public int getMaxTreeExp() {
		return maxTreeExp;
	}

	public void setMaxTreeExp(int maxTreeExp) {
		this.maxTreeExp = maxTreeExp;
	}
}
