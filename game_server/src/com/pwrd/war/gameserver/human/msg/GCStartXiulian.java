package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 开始挂机修炼结果
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStartXiulian extends GCMessage{
	
	/** 剩余时间 */
	private int leftTime;
	/** 累计经验 */
	private int allExp;
	/** 队伍战斗力 */
	private int zhandouli;
	/** 修炼境界等级 */
	private int xiulianLevel;
	/** 修炼境界等级名称 */
	private String xiulianLevelName;
	/** 提升境界需要多少元宝 */
	private int xiulianLevelNeedGold;
	/** 提升到该境界可维持时间 */
	private int xiulianLevelDurTime;
	/** 修炼可采集次数 */
	private int xiulianCollectTimes;
	/** 给予下次采集次数剩余多少秒 */
	private int nextRemainTime;
	/** 修炼被采集次数 */
	private int xiulianBeCollectTimes;
	/** 修炼总被采集次数 */
	private int xiulianBeCollectAllTimes;

	public GCStartXiulian (){
	}
	
	public GCStartXiulian (
			int leftTime,
			int allExp,
			int zhandouli,
			int xiulianLevel,
			String xiulianLevelName,
			int xiulianLevelNeedGold,
			int xiulianLevelDurTime,
			int xiulianCollectTimes,
			int nextRemainTime,
			int xiulianBeCollectTimes,
			int xiulianBeCollectAllTimes ){
			this.leftTime = leftTime;
			this.allExp = allExp;
			this.zhandouli = zhandouli;
			this.xiulianLevel = xiulianLevel;
			this.xiulianLevelName = xiulianLevelName;
			this.xiulianLevelNeedGold = xiulianLevelNeedGold;
			this.xiulianLevelDurTime = xiulianLevelDurTime;
			this.xiulianCollectTimes = xiulianCollectTimes;
			this.nextRemainTime = nextRemainTime;
			this.xiulianBeCollectTimes = xiulianBeCollectTimes;
			this.xiulianBeCollectAllTimes = xiulianBeCollectAllTimes;
	}

	@Override
	protected boolean readImpl() {
		leftTime = readInteger();
		allExp = readInteger();
		zhandouli = readInteger();
		xiulianLevel = readInteger();
		xiulianLevelName = readString();
		xiulianLevelNeedGold = readInteger();
		xiulianLevelDurTime = readInteger();
		xiulianCollectTimes = readInteger();
		nextRemainTime = readInteger();
		xiulianBeCollectTimes = readInteger();
		xiulianBeCollectAllTimes = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(leftTime);
		writeInteger(allExp);
		writeInteger(zhandouli);
		writeInteger(xiulianLevel);
		writeString(xiulianLevelName);
		writeInteger(xiulianLevelNeedGold);
		writeInteger(xiulianLevelDurTime);
		writeInteger(xiulianCollectTimes);
		writeInteger(nextRemainTime);
		writeInteger(xiulianBeCollectTimes);
		writeInteger(xiulianBeCollectAllTimes);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_START_XIULIAN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_START_XIULIAN";
	}

	public int getLeftTime(){
		return leftTime;
	}
		
	public void setLeftTime(int leftTime){
		this.leftTime = leftTime;
	}

	public int getAllExp(){
		return allExp;
	}
		
	public void setAllExp(int allExp){
		this.allExp = allExp;
	}

	public int getZhandouli(){
		return zhandouli;
	}
		
	public void setZhandouli(int zhandouli){
		this.zhandouli = zhandouli;
	}

	public int getXiulianLevel(){
		return xiulianLevel;
	}
		
	public void setXiulianLevel(int xiulianLevel){
		this.xiulianLevel = xiulianLevel;
	}

	public String getXiulianLevelName(){
		return xiulianLevelName;
	}
		
	public void setXiulianLevelName(String xiulianLevelName){
		this.xiulianLevelName = xiulianLevelName;
	}

	public int getXiulianLevelNeedGold(){
		return xiulianLevelNeedGold;
	}
		
	public void setXiulianLevelNeedGold(int xiulianLevelNeedGold){
		this.xiulianLevelNeedGold = xiulianLevelNeedGold;
	}

	public int getXiulianLevelDurTime(){
		return xiulianLevelDurTime;
	}
		
	public void setXiulianLevelDurTime(int xiulianLevelDurTime){
		this.xiulianLevelDurTime = xiulianLevelDurTime;
	}

	public int getXiulianCollectTimes(){
		return xiulianCollectTimes;
	}
		
	public void setXiulianCollectTimes(int xiulianCollectTimes){
		this.xiulianCollectTimes = xiulianCollectTimes;
	}

	public int getNextRemainTime(){
		return nextRemainTime;
	}
		
	public void setNextRemainTime(int nextRemainTime){
		this.nextRemainTime = nextRemainTime;
	}

	public int getXiulianBeCollectTimes(){
		return xiulianBeCollectTimes;
	}
		
	public void setXiulianBeCollectTimes(int xiulianBeCollectTimes){
		this.xiulianBeCollectTimes = xiulianBeCollectTimes;
	}

	public int getXiulianBeCollectAllTimes(){
		return xiulianBeCollectAllTimes;
	}
		
	public void setXiulianBeCollectAllTimes(int xiulianBeCollectAllTimes){
		this.xiulianBeCollectAllTimes = xiulianBeCollectAllTimes;
	}
}