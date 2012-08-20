package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;

/**
 * 在场景内有跳动
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerJump extends CGMessage{
	
	/** 目标x位置 */
	private int tox;
	/** 目标y位置 */
	private int toy;
	
	public CGPlayerJump (){
	}
	
	public CGPlayerJump (
			int tox,
			int toy ){
			this.tox = tox;
			this.toy = toy;
	}
	
	@Override
	protected boolean readImpl() {
		tox = readInteger();
		toy = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(tox);
		writeInteger(toy);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PLAYER_JUMP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_JUMP";
	}

	public int getTox(){
		return tox;
	}
		
	public void setTox(int tox){
		this.tox = tox;
	}

	public int getToy(){
		return toy;
	}
		
	public void setToy(int toy){
		this.toy = toy;
	}

	@Override
	public void execute() {
		SceneHandlerFactory.getHandler().handlePlayerJump(this.getSession().getPlayer(), this);
	}
}