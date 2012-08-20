package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 新手引导
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerGuide extends CGMessage{
	
	/**  */
	private String guideId;
	
	public CGPlayerGuide (){
	}
	
	public CGPlayerGuide (
			String guideId ){
			this.guideId = guideId;
	}
	
	@Override
	protected boolean readImpl() {
		guideId = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(guideId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PLAYER_GUIDE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_GUIDE";
	}

	public String getGuideId(){
		return guideId;
	}
		
	public void setGuideId(String guideId){
		this.guideId = guideId;
	}

	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handlePlayerGuide(this.getSession().getPlayer(), this);
	}
}