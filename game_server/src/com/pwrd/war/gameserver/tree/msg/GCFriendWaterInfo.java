package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回浇水和果实信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFriendWaterInfo extends GCMessage{
	
	/** 玩家职业 */
	private int vocation;
	/** 摇钱树当前经验 */
	private int curTreeExp;
	/** 摇钱树升级经验 */
	private int maxTreeExp;
	/** 摇钱树等级 */
	private int treeLevel;

	public GCFriendWaterInfo (){
	}
	
	public GCFriendWaterInfo (
			int vocation,
			int curTreeExp,
			int maxTreeExp,
			int treeLevel ){
			this.vocation = vocation;
			this.curTreeExp = curTreeExp;
			this.maxTreeExp = maxTreeExp;
			this.treeLevel = treeLevel;
	}

	@Override
	protected boolean readImpl() {
		vocation = readInteger();
		curTreeExp = readInteger();
		maxTreeExp = readInteger();
		treeLevel = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(vocation);
		writeInteger(curTreeExp);
		writeInteger(maxTreeExp);
		writeInteger(treeLevel);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FRIEND_WATER_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FRIEND_WATER_INFO";
	}

	public int getVocation(){
		return vocation;
	}
		
	public void setVocation(int vocation){
		this.vocation = vocation;
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
}