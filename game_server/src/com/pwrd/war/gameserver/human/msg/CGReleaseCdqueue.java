package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 使队列cd时间为0
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGReleaseCdqueue extends CGMessage{
	
	/** 队列序号 */
	private int index;
	/** 队列类型,1强化装备，2属性成长 */
	private int cdType;
	
	public CGReleaseCdqueue (){
	}
	
	public CGReleaseCdqueue (
			int index,
			int cdType ){
			this.index = index;
			this.cdType = cdType;
	}
	
	@Override
	protected boolean readImpl() {
		index = readInteger();
		cdType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(index);
		writeInteger(cdType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_RELEASE_CDQUEUE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_RELEASE_CDQUEUE";
	}

	public int getIndex(){
		return index;
	}
		
	public void setIndex(int index){
		this.index = index;
	}

	public int getCdType(){
		return cdType;
	}
		
	public void setCdType(int cdType){
		this.cdType = cdType;
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleReleaseCdqueue(this.getSession().getPlayer(), this);
	}
}