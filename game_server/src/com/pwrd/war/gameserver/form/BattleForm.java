package com.pwrd.war.gameserver.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.FormEntity;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.role.Role;

public class BattleForm implements PersistanceObject<String, FormEntity> {
	public final static int TotalPositions = 9;

	/** 是否已在数据库中 */
	private boolean isInDb;

	/** 生命期 */
	private final LifeCycle lifeCycle;

	/** 数据库id */
	private String dbId;
	
	private int formSn;
	private String[] positions;
	private Human owner;

	public BattleForm() {
		this.lifeCycle = new LifeCycleImpl(this);
		lifeCycle.activate();
	}

	public BattleForm(Human owner) {
		this.lifeCycle = new LifeCycleImpl(this);
		lifeCycle.activate();
		this.owner = owner;
	}

	@Override
	public void setDbId(String id) {
		dbId = id;

	}

	@Override
	public String getDbId() {
		return dbId;
	}

	@Override
	public String getGUID() {
		return dbId;
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
	public FormEntity toEntity() {
		FormEntity entity = new FormEntity();
		entity.setFormSn(formSn);
		entity.setHumanSn(owner.getCharId());
		entity.setPositionSn(StringUtils.join(positions, ","));
		entity.setId(dbId);
		return entity;
	}

	@Override
	public void fromEntity(FormEntity entity) {
		formSn = entity.getFormSn();
		dbId = entity.getId();
		
		//根据数据库中存放的情况构造武将排列信息
		positions = entity.getPositionSn().split(",");
	}

	/**
	 * 根据数据库中数据构造阵法对象
	 * @param human
	 * @param entity
	 * @return
	 */
	public static BattleForm buildBattleForm(Human human, FormEntity entity) {
		BattleForm battleForm = new BattleForm(human);
		battleForm.fromEntity(entity);
		return battleForm;
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public void setModified() {
		// 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive()) {
			// 物品的生命期处于活动状态,并且该物品不是空的,则执行通知更新机制进行
			this.onUpdate();
		}

	}

	private void onUpdate() {
		owner.getPlayer().getDataUpdater().addUpdate(this.getLifeCycle());
	}

	public int getFormSn() {
		return formSn;
	}

	public void setFormSn(int formSn) {
		this.formSn = formSn;
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public String[] getPositions() {
		return positions;
	}

	public void setPositions(String[] positions) {
		this.positions = positions;
	}
	
	/**
	 * 判断指定编号的角色是否在阵法中
	 * @param petSn
	 * @return
	 */
	public boolean isBattle(String petSn){
		 for(String p : positions){
			 if(StringUtils.isNotEmpty(p) && p.equals(petSn)){
				 return true;
			 }
		 }
		 return false;
	}
	
	/**
	 * 获取全部阵法中角色
	 * @return
	 */
	public List<Role> getAllBattlePets(){
		List<Role> roles = new ArrayList<Role>();
		 for(String p : positions){
			 if (StringUtils.isNotEmpty(p)) {
				 Role role = owner.getRole(p);
				 if (role != null) {
					 roles.add(role);
				 }
			 }
		 }
		 return roles;
	}

}
