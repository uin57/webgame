package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 改变玩家位置
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPlayerChangePos extends GCMessage{
	
	/** 玩家uuid */
	private String uuid;
	/** 当前x位置 */
	private int x;
	/** 当前y位置 */
	private int y;

	public GCPlayerChangePos (){
	}
	
	public GCPlayerChangePos (
			String uuid,
			int x,
			int y ){
			this.uuid = uuid;
			this.x = x;
			this.y = y;
	}

	@Override
	protected boolean readImpl() {
		uuid = readString();
		x = readInteger();
		y = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(uuid);
		writeInteger(x);
		writeInteger(y);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PLAYER_CHANGE_POS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PLAYER_CHANGE_POS";
	}

	public String getUuid(){
		return uuid;
	}
		
	public void setUuid(String uuid){
		this.uuid = uuid;
	}

	public int getX(){
		return x;
	}
		
	public void setX(int x){
		this.x = x;
	}

	public int getY(){
		return y;
	}
		
	public void setY(int y){
		this.y = y;
	}
}