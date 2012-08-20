package com.pwrd.war.gameserver.qiecuo.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 切磋战报
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCQiecuoResultMessage extends GCMessage{
	
	/** 切磋发起者 */
	private String sponsorPlayerUUID;
	/** 切磋发起者名 */
	private String sponsorPlayerName;
	/** 被切磋者 */
	private String bearPlayerUUID;
	/** 被切磋者名 */
	private String bearPlayerName;
	/** 是否战胜 */
	private boolean isWin;
	/** 战场sn */
	private String battleSn;

	public GCQiecuoResultMessage (){
	}
	
	public GCQiecuoResultMessage (
			String sponsorPlayerUUID,
			String sponsorPlayerName,
			String bearPlayerUUID,
			String bearPlayerName,
			boolean isWin,
			String battleSn ){
			this.sponsorPlayerUUID = sponsorPlayerUUID;
			this.sponsorPlayerName = sponsorPlayerName;
			this.bearPlayerUUID = bearPlayerUUID;
			this.bearPlayerName = bearPlayerName;
			this.isWin = isWin;
			this.battleSn = battleSn;
	}

	@Override
	protected boolean readImpl() {
		sponsorPlayerUUID = readString();
		sponsorPlayerName = readString();
		bearPlayerUUID = readString();
		bearPlayerName = readString();
		isWin = readBoolean();
		battleSn = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sponsorPlayerUUID);
		writeString(sponsorPlayerName);
		writeString(bearPlayerUUID);
		writeString(bearPlayerName);
		writeBoolean(isWin);
		writeString(battleSn);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_QieCuo_Result_Message;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QieCuo_Result_Message";
	}

	public String getSponsorPlayerUUID(){
		return sponsorPlayerUUID;
	}
		
	public void setSponsorPlayerUUID(String sponsorPlayerUUID){
		this.sponsorPlayerUUID = sponsorPlayerUUID;
	}

	public String getSponsorPlayerName(){
		return sponsorPlayerName;
	}
		
	public void setSponsorPlayerName(String sponsorPlayerName){
		this.sponsorPlayerName = sponsorPlayerName;
	}

	public String getBearPlayerUUID(){
		return bearPlayerUUID;
	}
		
	public void setBearPlayerUUID(String bearPlayerUUID){
		this.bearPlayerUUID = bearPlayerUUID;
	}

	public String getBearPlayerName(){
		return bearPlayerName;
	}
		
	public void setBearPlayerName(String bearPlayerName){
		this.bearPlayerName = bearPlayerName;
	}

	public boolean getIsWin(){
		return isWin;
	}
		
	public void setIsWin(boolean isWin){
		this.isWin = isWin;
	}

	public String getBattleSn(){
		return battleSn;
	}
		
	public void setBattleSn(String battleSn){
		this.battleSn = battleSn;
	}
}