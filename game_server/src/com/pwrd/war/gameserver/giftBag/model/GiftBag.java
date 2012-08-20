/**
 * 
 */
package com.pwrd.war.gameserver.giftBag.model;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.GiftBagEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.operation.DeleteEntityOperation;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;
import com.pwrd.war.gameserver.giftBag.template.GiftBagTemplate;

/**
 * @author DevUser
 *
 */
public class GiftBag implements PersistanceObject<String, GiftBagEntity> {
	
	/** 礼包的实例id */
	private String id;
	/** 礼包所属玩家 */
	private String charId;
	/** 对应的礼包模版 */
	private GiftBagTemplate giftBagTemplate;
	/** 发放时间 */
	private long sendTime;
	/** 礼包的生命期的状态  */
	private final LifeCycle lifeCycle;
	/** 此实例是否在db中  */
	private boolean inDb;
	/** 是否已经变更了 */
	private boolean modified;
	
	public GiftBag(){
		this.lifeCycle = new LifeCycleImpl(this);
		this.lifeCycle.activate();
		this.inDb = false;
	}
	@Override
	public void setDbId(String id) {
		this.id = id;
	}

	@Override
	public String getDbId() {
		return this.id;
	}

	public String getGUID() {
		return null;
	}

	public boolean isInDb() {
		return inDb;
	}
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
	}
	
	public void setCharId(String charId){
		this.charId = charId;
	}
	
	public String getCharId() {
		return charId;
	}

	public GiftBagTemplate getGiftBagTemplate() {
		return giftBagTemplate;
	}
	
	public void setGiftBagTemplate(GiftBagTemplate giftBagTemplate) {
		this.giftBagTemplate = giftBagTemplate;
	}
	
	public long getSendTime() {
		return sendTime;
	}
	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	@Override
	public GiftBagEntity toEntity() {
		GiftBagEntity entity = new GiftBagEntity();
		entity.setId(this.getDbId());
		entity.setCharId(this.getCharId());
		entity.setGiftBagId(this.getGiftBagTemplate().getGiftId());
		entity.setSendTime(this.getSendTime());
		return entity;
	}

	@Override
	public void fromEntity(GiftBagEntity entity) {
		this.setDbId(entity.getId());
		this.setSendTime(entity.getSendTime());
		this.setGiftBagTemplate(Globals.getGiftBagService().getGiftBagTemplate(entity.getGiftBagId()));
		this.setCharId(entity.getCharId());
		this.inDb = true;
	}

	@Override
	public LifeCycle getLifeCycle() {
		return this.lifeCycle;
	}

	@Override
	public void setModified() {
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive()) {  		
			IIoOperation _oper = new SaveObjectOperation<GiftBagEntity, GiftBag>(
					this, Globals.getDaoService().getGiftBagDao());
			Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper); 
		}
	}
	
	public void delete(){
		this.lifeCycle.checkModifiable(); 
		this.lifeCycle.destroy();
		IIoOperation _oper = new DeleteEntityOperation<GiftBagEntity>(this.toEntity(), this.getCharId(), Globals.getDaoService().getGiftBagDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
