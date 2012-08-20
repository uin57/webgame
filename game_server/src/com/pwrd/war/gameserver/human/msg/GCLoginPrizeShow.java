package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 是否显示登陆奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLoginPrizeShow extends GCMessage{
	
	/** true显示，false不显示 */
	private boolean show;

	public GCLoginPrizeShow (){
	}
	
	public GCLoginPrizeShow (
			boolean show ){
			this.show = show;
	}

	@Override
	protected boolean readImpl() {
		show = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(show);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LOGIN_PRIZE_SHOW;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LOGIN_PRIZE_SHOW";
	}

	public boolean getShow(){
		return show;
	}
		
	public void setShow(boolean show){
		this.show = show;
	}
}