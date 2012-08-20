package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回浇水和果实信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCWaterInfo extends GCMessage{
	
	/** 给自己的浇水次数 */
	private int waterTimes;
	/** 浇水的冷却时间 */
	private int waterTime;
	/** 给好友的浇水次数 */
	private int friendWaterTimes;
	/** 摇钱树当前经验 */
	private int curTreeExp;
	/** 摇钱树升级经验 */
	private int maxTreeExp;
	/** 摇钱树等级 */
	private int treeLevel;
	/** 已经领取的果实等级 */
	private int fruitLevel;

	public GCWaterInfo (){
	}
	
	public GCWaterInfo (
			int waterTimes,
			int waterTime,
			int friendWaterTimes,
			int curTreeExp,
			int maxTreeExp,
			int treeLevel,
			int fruitLevel ){
			this.waterTimes = waterTimes;
			this.waterTime = waterTime;
			this.friendWaterTimes = friendWaterTimes;
			this.curTreeExp = curTreeExp;
			this.maxTreeExp = maxTreeExp;
			this.treeLevel = treeLevel;
			this.fruitLevel = fruitLevel;
	}

	@Override
	protected boolean readImpl() {
		waterTimes = readInteger();
		waterTime = readInteger();
		friendWaterTimes = readInteger();
		curTreeExp = readInteger();
		maxTreeExp = readInteger();
		treeLevel = readInteger();
		fruitLevel = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(waterTimes);
		writeInteger(waterTime);
		writeInteger(friendWaterTimes);
		writeInteger(curTreeExp);
		writeInteger(maxTreeExp);
		writeInteger(treeLevel);
		writeInteger(fruitLevel);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_WATER_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_WATER_INFO";
	}

	public int getWaterTimes(){
		return waterTimes;
	}
		
	public void setWaterTimes(int waterTimes){
		this.waterTimes = waterTimes;
	}

	public int getWaterTime(){
		return waterTime;
	}
		
	public void setWaterTime(int waterTime){
		this.waterTime = waterTime;
	}

	public int getFriendWaterTimes(){
		return friendWaterTimes;
	}
		
	public void setFriendWaterTimes(int friendWaterTimes){
		this.friendWaterTimes = friendWaterTimes;
	}

	public int getCurTreeExp(){
		return curTreeExp;
	}
		
	public void setCurTreeExp(int curTreeExp){
		this.curTreeExp = curTreeExp;
	}

	public int getMaxTreeExp(){
		return maxTreeExp;
	}
		
	public void setMaxTreeExp(int maxTreeExp){
		this.maxTreeExp = maxTreeExp;
	}

	public int getTreeLevel(){
		return treeLevel;
	}
		
	public void setTreeLevel(int treeLevel){
		this.treeLevel = treeLevel;
	}

	public int getFruitLevel(){
		return fruitLevel;
	}
		
	public void setFruitLevel(int fruitLevel){
		this.fruitLevel = fruitLevel;
	}
}