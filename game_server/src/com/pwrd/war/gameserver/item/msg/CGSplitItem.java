package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;

/**
 * 拆分叠加道具
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSplitItem extends CGMessage{
	
	/** 背包ID */
	private short bagId;
	/** 背包内位置索引 */
	private short index;
	/** 拆出的数量 */
	private short count;
	
	public CGSplitItem (){
	}
	
	public CGSplitItem (
			short bagId,
			short index,
			short count ){
			this.bagId = bagId;
			this.index = index;
			this.count = count;
	}
	
	@Override
	protected boolean readImpl() {
		bagId = readShort();
		index = readShort();
		count = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagId);
		writeShort(index);
		writeShort(count);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SPLIT_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SPLIT_ITEM";
	}

	public short getBagId(){
		return bagId;
	}
		
	public void setBagId(short bagId){
		this.bagId = bagId;
	}

	public short getIndex(){
		return index;
	}
		
	public void setIndex(short index){
		this.index = index;
	}

	public short getCount(){
		return count;
	}
		
	public void setCount(short count){
		this.count = count;
	}

	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleSplitItem(this.getSession().getPlayer(), this);
	}
}