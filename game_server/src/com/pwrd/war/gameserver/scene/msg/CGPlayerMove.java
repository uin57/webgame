package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;

/**
 * 在场景内有移动
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerMove extends CGMessage{
	
	/** 当前x位置 */
	private int srcx;
	/** 当前y位置 */
	private int srcy;
	/** 目标x位置 */
	private int tox;
	/** 目标y位置 */
	private int toy;
	
	public CGPlayerMove (){
	}
	
	public CGPlayerMove (
			int srcx,
			int srcy,
			int tox,
			int toy ){
			this.srcx = srcx;
			this.srcy = srcy;
			this.tox = tox;
			this.toy = toy;
	}
	
	@Override
	protected boolean readImpl() {
		srcx = readInteger();
		srcy = readInteger();
		tox = readInteger();
		toy = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(srcx);
		writeInteger(srcy);
		writeInteger(tox);
		writeInteger(toy);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PLAYER_MOVE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_MOVE";
	}

	public int getSrcx(){
		return srcx;
	}
		
	public void setSrcx(int srcx){
		this.srcx = srcx;
	}

	public int getSrcy(){
		return srcy;
	}
		
	public void setSrcy(int srcy){
		this.srcy = srcy;
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
		SceneHandlerFactory.getHandler().handlePlayerMove(this.getSession().getPlayer(), this);
	}
}