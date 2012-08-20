package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 告诉服务器玩家当前位置
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPlayerPos extends GCMessage{
	
	/** 玩家当前位置是否正确，不准确则按下面的校正 */
	private boolean result;
	/** 场景 Id */
	private String sceneId;
	/** 分线号 */
	private int lineNo;
	/** 当前x位置 */
	private int srcx;
	/** 当前y位置 */
	private int srcy;

	public GCPlayerPos (){
	}
	
	public GCPlayerPos (
			boolean result,
			String sceneId,
			int lineNo,
			int srcx,
			int srcy ){
			this.result = result;
			this.sceneId = sceneId;
			this.lineNo = lineNo;
			this.srcx = srcx;
			this.srcy = srcy;
	}

	@Override
	protected boolean readImpl() {
		result = readBoolean();
		sceneId = readString();
		lineNo = readInteger();
		srcx = readInteger();
		srcy = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(result);
		writeString(sceneId);
		writeInteger(lineNo);
		writeInteger(srcx);
		writeInteger(srcy);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PLAYER_POS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PLAYER_POS";
	}

	public boolean getResult(){
		return result;
	}
		
	public void setResult(boolean result){
		this.result = result;
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
}