package com.pwrd.war.gameserver.wallow.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 防沉迷信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCWallowInfo extends GCMessage{
	
	/** 累计上线时间 */
	private int min;
	/** 信息提示 */
	private String tip;

	public GCWallowInfo (){
	}
	
	public GCWallowInfo (
			int min,
			String tip ){
			this.min = min;
			this.tip = tip;
	}

	@Override
	protected boolean readImpl() {
		min = readInteger();
		tip = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(min);
		writeString(tip);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_WALLOW_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_WALLOW_INFO";
	}

	public int getMin(){
		return min;
	}
		
	public void setMin(int min){
		this.min = min;
	}

	public String getTip(){
		return tip;
	}
		
	public void setTip(String tip){
		this.tip = tip;
	}
}