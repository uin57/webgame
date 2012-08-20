package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回成长变化对应属性增加
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGrowProps extends GCMessage{
	
	/** 目标UUID */
	private String targetUUID;
	/** 最大成长值 */
	private int maxGrow;
	/** 所有变化信息 */
	private com.pwrd.war.gameserver.human.domain.GrowChange[] grows;

	public GCGrowProps (){
	}
	
	public GCGrowProps (
			String targetUUID,
			int maxGrow,
			com.pwrd.war.gameserver.human.domain.GrowChange[] grows ){
			this.targetUUID = targetUUID;
			this.maxGrow = maxGrow;
			this.grows = grows;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		targetUUID = readString();
		maxGrow = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		grows = new com.pwrd.war.gameserver.human.domain.GrowChange[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.human.domain.GrowChange obj = new com.pwrd.war.gameserver.human.domain.GrowChange();
			obj.setGrow(readInteger());
			obj.setNeedMoney(readInteger());
			obj.setAddAtk(readInteger());
			obj.setAddDef(readInteger());
			obj.setAddMaxHp(readInteger());
			obj.setAddAllAtk(readInteger());
			obj.setAddAllDef(readInteger());
			obj.setAddAllMaxHp(readInteger());
			grows[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(targetUUID);
		writeInteger(maxGrow);
		writeShort(grows.length);
		for(int i=0; i<grows.length; i++){
			writeInteger(grows[i].getGrow());
			writeInteger(grows[i].getNeedMoney());
			writeInteger(grows[i].getAddAtk());
			writeInteger(grows[i].getAddDef());
			writeInteger(grows[i].getAddMaxHp());
			writeInteger(grows[i].getAddAllAtk());
			writeInteger(grows[i].getAddAllDef());
			writeInteger(grows[i].getAddAllMaxHp());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GROW_PROPS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GROW_PROPS";
	}

	public String getTargetUUID(){
		return targetUUID;
	}
		
	public void setTargetUUID(String targetUUID){
		this.targetUUID = targetUUID;
	}

	public int getMaxGrow(){
		return maxGrow;
	}
		
	public void setMaxGrow(int maxGrow){
		this.maxGrow = maxGrow;
	}

	public com.pwrd.war.gameserver.human.domain.GrowChange[] getGrows(){
		return grows;
	}

	public void setGrows(com.pwrd.war.gameserver.human.domain.GrowChange[] grows){
		this.grows = grows;
	}	
}