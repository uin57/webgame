package com.pwrd.war.gameserver.interactive.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 查看别人的信息的返回消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLookUpMessage extends GCMessage{
	
	/** 玩家的UUID */
	private String playUUID;
	/** 玩家名称 */
	private String name;
	/** 性别 */
	private int sex;
	/** 性别 */
	private int vocation;
	/** 性别 */
	private int level;
	/** 开启功能（聊天功能: 0x0001,基本信息:0x0002,切磋功能:0x0004,加好友功能:0x0008） */
	private int openFunction;

	public GCLookUpMessage (){
	}
	
	public GCLookUpMessage (
			String playUUID,
			String name,
			int sex,
			int vocation,
			int level,
			int openFunction ){
			this.playUUID = playUUID;
			this.name = name;
			this.sex = sex;
			this.vocation = vocation;
			this.level = level;
			this.openFunction = openFunction;
	}

	@Override
	protected boolean readImpl() {
		playUUID = readString();
		name = readString();
		sex = readInteger();
		vocation = readInteger();
		level = readInteger();
		openFunction = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(playUUID);
		writeString(name);
		writeInteger(sex);
		writeInteger(vocation);
		writeInteger(level);
		writeInteger(openFunction);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Look_Up_Message;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Look_Up_Message";
	}

	public String getPlayUUID(){
		return playUUID;
	}
		
	public void setPlayUUID(String playUUID){
		this.playUUID = playUUID;
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}

	public int getSex(){
		return sex;
	}
		
	public void setSex(int sex){
		this.sex = sex;
	}

	public int getVocation(){
		return vocation;
	}
		
	public void setVocation(int vocation){
		this.vocation = vocation;
	}

	public int getLevel(){
		return level;
	}
		
	public void setLevel(int level){
		this.level = level;
	}

	public int getOpenFunction(){
		return openFunction;
	}
		
	public void setOpenFunction(int openFunction){
		this.openFunction = openFunction;
	}
}