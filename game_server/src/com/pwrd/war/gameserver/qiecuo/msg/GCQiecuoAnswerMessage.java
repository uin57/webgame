package com.pwrd.war.gameserver.qiecuo.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 切磋应答失败返回的消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCQiecuoAnswerMessage extends GCMessage{
	
	/** 切磋发起者 */
	private String sponsorPlayerUUID;
	/** 切磋发起者名称 */
	private String sponsorPlayerName;
	/** 被切磋者 */
	private String bearPlayerUUID;
	/** 被切磋者名称 */
	private String bearPlayerName;
	/** 0表示 接受切磋 ，1表示取消切磋 */
	private int result;

	public GCQiecuoAnswerMessage (){
	}
	
	public GCQiecuoAnswerMessage (
			String sponsorPlayerUUID,
			String sponsorPlayerName,
			String bearPlayerUUID,
			String bearPlayerName,
			int result ){
			this.sponsorPlayerUUID = sponsorPlayerUUID;
			this.sponsorPlayerName = sponsorPlayerName;
			this.bearPlayerUUID = bearPlayerUUID;
			this.bearPlayerName = bearPlayerName;
			this.result = result;
	}

	@Override
	protected boolean readImpl() {
		sponsorPlayerUUID = readString();
		sponsorPlayerName = readString();
		bearPlayerUUID = readString();
		bearPlayerName = readString();
		result = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sponsorPlayerUUID);
		writeString(sponsorPlayerName);
		writeString(bearPlayerUUID);
		writeString(bearPlayerName);
		writeInteger(result);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_QieCuo_Answer_Message;
	}
	
	@Override
	public String getTypeName() {
		return "GC_QieCuo_Answer_Message";
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

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}
}