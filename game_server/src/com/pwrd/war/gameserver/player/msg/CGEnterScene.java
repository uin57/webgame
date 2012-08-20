package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 玩家可以进入场景
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGEnterScene extends CGMessage{
	
	/** 分线号，-1表示随机分配 */
	private int line;
	
	public CGEnterScene (){
	}
	
	public CGEnterScene (
			int line ){
			this.line = line;
	}
	
	@Override
	protected boolean readImpl() {
		line = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(line);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ENTER_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_SCENE";
	}

	public int getLine(){
		return line;
	}
		
	public void setLine(int line){
		this.line = line;
	}

	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handleEnterScene(this.getSession().getPlayer(), this);
	}
}