package com.pwrd.war.gameserver.human.domain;

import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.ProbEntity;
import com.pwrd.war.gameserver.human.Human;

public class ProbInfo implements PersistanceObject<String, ProbEntity> {
	

	private String id;
	
	private String playerUUID;
	
	private String eventId;//事件
	
	private int[] times;//每个概率产生的次数
	
	private int[] probs;//每个概率的权重
	
	private boolean inDb;
	
	private final LifeCycle lifeCycle;
	private Human human;
	
	public ProbInfo(Human human) {
		this.human = human;
		lifeCycle = new LifeCycleImpl(this);
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
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) { 
		this.inDb = inDb;
	}

	@Override
	public String getCharId() { 
		return this.playerUUID;
	}

	@Override
	public ProbEntity toEntity() { 
		ProbEntity ent = new ProbEntity();
		ent.setId(this.id);
		ent.setEventId(this.eventId);
		ent.setPlayerUUID(this.playerUUID);
		ent.setProbs(this.probs);
		ent.setTimes(this.times);
		return ent;
	}

	@Override
	public void fromEntity(ProbEntity entity) { 
		this.id = entity.getId();
		this.eventId = entity.getEventId();
		this.playerUUID = entity.getPlayerUUID();
		this.probs = entity.getProbs();
		this.times = entity.getTimes();
	}

	@Override
	public LifeCycle getLifeCycle() { 
		return this.lifeCycle;
	}

	@Override
	public void setModified() { 
		if (this.lifeCycle.isActive()) {			 
			if(!human.getPlayer().getDataUpdater().isUpdating()){
				//如果正在更新暂时先不更新
				human.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}			 
		}
	}

	public String getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(String playerUUID) {
		this.playerUUID = playerUUID;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public int[] getTimes() {
		return times;
	}

	public void setTimes(int[] times) {
		this.times = times;
	}

	public int[] getProbs() {
		return probs;
	}

	public void setProbs(int[] probs) {
		this.probs = probs;
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

}
