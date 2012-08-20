package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 摇钱
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTreeInfo extends GCMessage{
	
	/** 当天摇钱次数 */
	private int shakeTimes;
	/** 最大摇钱次数 */
	private int totalTimes;
	/** 一次花多少元宝 */
	private int costGold;
	/** 一次得多少铜钱 */
	private int getCoin;
	/** 能够进行批量摇钱 */
	private boolean batchFlag;
	/** 批量一次花多少元宝 */
	private int costBatch;

	public GCTreeInfo (){
	}
	
	public GCTreeInfo (
			int shakeTimes,
			int totalTimes,
			int costGold,
			int getCoin,
			boolean batchFlag,
			int costBatch ){
			this.shakeTimes = shakeTimes;
			this.totalTimes = totalTimes;
			this.costGold = costGold;
			this.getCoin = getCoin;
			this.batchFlag = batchFlag;
			this.costBatch = costBatch;
	}

	@Override
	protected boolean readImpl() {
		shakeTimes = readInteger();
		totalTimes = readInteger();
		costGold = readInteger();
		getCoin = readInteger();
		batchFlag = readBoolean();
		costBatch = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(shakeTimes);
		writeInteger(totalTimes);
		writeInteger(costGold);
		writeInteger(getCoin);
		writeBoolean(batchFlag);
		writeInteger(costBatch);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TREE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TREE_INFO";
	}

	public int getShakeTimes(){
		return shakeTimes;
	}
		
	public void setShakeTimes(int shakeTimes){
		this.shakeTimes = shakeTimes;
	}

	public int getTotalTimes(){
		return totalTimes;
	}
		
	public void setTotalTimes(int totalTimes){
		this.totalTimes = totalTimes;
	}

	public int getCostGold(){
		return costGold;
	}
		
	public void setCostGold(int costGold){
		this.costGold = costGold;
	}

	public int getGetCoin(){
		return getCoin;
	}
		
	public void setGetCoin(int getCoin){
		this.getCoin = getCoin;
	}

	public boolean getBatchFlag(){
		return batchFlag;
	}
		
	public void setBatchFlag(boolean batchFlag){
		this.batchFlag = batchFlag;
	}

	public int getCostBatch(){
		return costBatch;
	}
		
	public void setCostBatch(int costBatch){
		this.costBatch = costBatch;
	}
}