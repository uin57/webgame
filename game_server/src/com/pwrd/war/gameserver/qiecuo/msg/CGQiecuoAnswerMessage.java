package com.pwrd.war.gameserver.qiecuo.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.qiecuo.handler.QiecuoHandlerFactory;

/**
 * 切磋应答
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGQiecuoAnswerMessage extends CGMessage{
	
	/** 切磋发起者 */
	private String sponsorPlayerUUID;
	/** 切磋发起者名称 */
	private String sponsorPlayerName;
	/** 0表示 接受切磋 ，1表示取消切磋 */
	private int result;
	/** 被切磋者 */
	private String bearPlayerUUID;
	/** 被切磋者名称 */
	private String bearPlayerName;
	
	public CGQiecuoAnswerMessage (){
	}
	
	public CGQiecuoAnswerMessage (
			String sponsorPlayerUUID,
			String sponsorPlayerName,
			int result,
			String bearPlayerUUID,
			String bearPlayerName ){
			this.sponsorPlayerUUID = sponsorPlayerUUID;
			this.sponsorPlayerName = sponsorPlayerName;
			this.result = result;
			this.bearPlayerUUID = bearPlayerUUID;
			this.bearPlayerName = bearPlayerName;
	}
	
	@Override
	protected boolean readImpl() {
		sponsorPlayerUUID = readString();
		sponsorPlayerName = readString();
		result = readInteger();
		bearPlayerUUID = readString();
		bearPlayerName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(sponsorPlayerUUID);
		writeString(sponsorPlayerName);
		writeInteger(result);
		writeString(bearPlayerUUID);
		writeString(bearPlayerName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_QieCuo_Answer_Message;
	}
	
	@Override
	public String getTypeName() {
		return "CG_QieCuo_Answer_Message";
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

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
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

	@Override
	public void execute() {
		QiecuoHandlerFactory.getHandler().handleQiecuoAnswerMessage(this.getSession().getPlayer(), this);
	}
}