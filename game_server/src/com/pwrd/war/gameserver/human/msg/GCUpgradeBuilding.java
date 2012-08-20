package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 建筑升级通知
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCUpgradeBuilding extends GCMessage{
	
	/** 建筑索引值 */
	private int buildingId;
	/** 新的等级 */
	private int newLevel;

	public GCUpgradeBuilding (){
	}
	
	public GCUpgradeBuilding (
			int buildingId,
			int newLevel ){
			this.buildingId = buildingId;
			this.newLevel = newLevel;
	}

	@Override
	protected boolean readImpl() {
		buildingId = readInteger();
		newLevel = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buildingId);
		writeInteger(newLevel);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPGRADE_BUILDING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPGRADE_BUILDING";
	}

	public int getBuildingId(){
		return buildingId;
	}
		
	public void setBuildingId(int buildingId){
		this.buildingId = buildingId;
	}

	public int getNewLevel(){
		return newLevel;
	}
		
	public void setNewLevel(int newLevel){
		this.newLevel = newLevel;
	}
}