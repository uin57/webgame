/**
 * 
 */
package com.pwrd.war.gameserver.promptButton.model;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.PromptButtonEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.operation.DeleteEntityOperation;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 
 * 提示消息
 * @author dengdan
 *
 */
public class PromptButton implements PersistanceObject<String, PromptButtonEntity> {
	
	/** 实例id */
	private String id;
	/** 角色id */
	private String charId;
	/** 内容 */
	private String content;
	/** 功能id */
	private int functionId;
	/** 时间 */
	private long time;
	
	private boolean inDb;
	private final LifeCycle lifeCycle;
	
	public PromptButton(){
		this.lifeCycle =  new LifeCycleImpl(this);
		this.lifeCycle.activate();
		this.inDb = false;
	}

	@Override
	public void setDbId(String id) {
		this.id = id;
	}

	@Override
	public String getDbId() {
		return id;
	}

	@Override
	public String getGUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInDb() {
		// TODO Auto-generated method stub
		return this.inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
	}

	@Override
	public String getCharId() {
		// TODO Auto-generated method stub
		return charId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setCharId(String charId){
		this.charId = charId;
	}

	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	@Override
	public PromptButtonEntity toEntity() {
		PromptButtonEntity entity = new PromptButtonEntity();
		entity.setId(this.getDbId());
		entity.setCharId(this.getCharId());
		entity.setContent(this.getContent());
		entity.setFunctionId(this.getFunctionId());
		entity.setTime(this.getTime());
		return entity;
	}

	@Override
	public void fromEntity(PromptButtonEntity entity) {
		this.setDbId(entity.getId());
		this.setCharId(entity.getCharId());
		this.setContent(entity.getContent());
		this.setFunctionId(entity.getFunctionId());
		this.setTime(entity.getTime());
	}

	@Override
	public LifeCycle getLifeCycle() {
		return this.lifeCycle;
	}

	@Override
	public void setModified() {
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive()) {  		
			IIoOperation _oper = new SaveObjectOperation<PromptButtonEntity, PromptButton>(
					this, Globals.getDaoService().getPromptButtonDao());
			Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper); 
		}
		
	}
	
	public void delete() {
		this.lifeCycle.checkModifiable(); 
		this.lifeCycle.destroy();
		IIoOperation _oper = new DeleteEntityOperation<PromptButtonEntity>(this.toEntity(), this.getCharId(), Globals.getDaoService().getPromptButtonDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
