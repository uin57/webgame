package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 打开背包某页的花费
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCOpenBagPageCost extends GCMessage{
	
	/** 第二页花费 */
	private int page2goldcost;
	/** 第三页花费 */
	private int page3goldcost;
	/** 第四页花费 */
	private int page4goldcost;

	public GCOpenBagPageCost (){
	}
	
	public GCOpenBagPageCost (
			int page2goldcost,
			int page3goldcost,
			int page4goldcost ){
			this.page2goldcost = page2goldcost;
			this.page3goldcost = page3goldcost;
			this.page4goldcost = page4goldcost;
	}

	@Override
	protected boolean readImpl() {
		page2goldcost = readInteger();
		page3goldcost = readInteger();
		page4goldcost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(page2goldcost);
		writeInteger(page3goldcost);
		writeInteger(page4goldcost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_BAG_PAGE_COST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_BAG_PAGE_COST";
	}

	public int getPage2goldcost(){
		return page2goldcost;
	}
		
	public void setPage2goldcost(int page2goldcost){
		this.page2goldcost = page2goldcost;
	}

	public int getPage3goldcost(){
		return page3goldcost;
	}
		
	public void setPage3goldcost(int page3goldcost){
		this.page3goldcost = page3goldcost;
	}

	public int getPage4goldcost(){
		return page4goldcost;
	}
		
	public void setPage4goldcost(int page4goldcost){
		this.page4goldcost = page4goldcost;
	}
}