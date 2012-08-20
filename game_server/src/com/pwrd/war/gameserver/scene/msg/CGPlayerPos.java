package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;

/**
 * 告诉服务器玩家当前位置
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerPos extends CGMessage{
	
	/** 场景 Id */
	private String sceneId;
	/** 分线号 */
	private int lineNo;
	/** 当前x位置 */
	private int srcx;
	/** 当前y位置 */
	private int srcy;
	
	public CGPlayerPos (){
	}
	
	public CGPlayerPos (
			String sceneId,
			int lineNo,
			int srcx,
			int srcy ){
			this.sceneId = sceneId;
			this.lineNo = lineNo;
			this.srcx = srcx;
			this.srcy = srcy;
	}
	
	@Override
	protected boolean readImpl() {
		sceneId = readString();
		lineNo = readInteger();
		srcx = readInteger();
		srcy = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sceneId);
		writeInteger(lineNo);
		writeInteger(srcx);
		writeInteger(srcy);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PLAYER_POS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_POS";
	}

	public String getSceneId(){
		return sceneId;
	}
		
	public void setSceneId(String sceneId){
		this.sceneId = sceneId;
	}

	public int getLineNo(){
		return lineNo;
	}
		
	public void setLineNo(int lineNo){
		this.lineNo = lineNo;
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

	@Override
	public void execute() {
		SceneHandlerFactory.getHandler().handlePlayerPos(this.getSession().getPlayer(), this);
	}
}