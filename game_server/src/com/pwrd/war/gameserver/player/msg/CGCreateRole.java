package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 创建角色
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGCreateRole extends CGMessage{
	
	/** 角色名字 */
	private String name;
	/** 选择的性别 */
	private int sex;
	/** 选择的职业 */
	private int vocation;
	/** 选择的阵营  */
	private int camp;
	
	public CGCreateRole (){
	}
	
	public CGCreateRole (
			String name,
			int sex,
			int vocation,
			int camp ){
			this.name = name;
			this.sex = sex;
			this.vocation = vocation;
			this.camp = camp;
	}
	
	@Override
	protected boolean readImpl() {
		name = readString();
		sex = readInteger();
		vocation = readInteger();
		camp = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(name);
		writeInteger(sex);
		writeInteger(vocation);
		writeInteger(camp);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CREATE_ROLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CREATE_ROLE";
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

	public int getCamp(){
		return camp;
	}
		
	public void setCamp(int camp){
		this.camp = camp;
	}

	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handleCreateRole(this.getSession().getPlayer(), this);
	}
}