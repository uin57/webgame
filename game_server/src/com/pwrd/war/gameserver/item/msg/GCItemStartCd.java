package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 使用道具后通知客户端开始转CD
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCItemStartCd extends GCMessage{
	
	/** 道具实例uuid */
	private String uuid;
	/** 冷却时间，单位：毫秒 */
	private int cooldown;

	public GCItemStartCd (){
	}
	
	public GCItemStartCd (
			String uuid,
			int cooldown ){
			this.uuid = uuid;
			this.cooldown = cooldown;
	}

	@Override
	protected boolean readImpl() {
		uuid = readString();
		cooldown = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(uuid);
		writeInteger(cooldown);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ITEM_START_CD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ITEM_START_CD";
	}

	public String getUuid(){
		return uuid;
	}
		
	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	public int getCooldown(){
		return cooldown;
	}
		
	public void setCooldown(int cooldown){
		this.cooldown = cooldown;
	}
}