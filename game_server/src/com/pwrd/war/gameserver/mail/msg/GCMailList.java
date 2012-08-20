package com.pwrd.war.gameserver.mail.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 邮件列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCMailList extends GCMessage{
	
	/** 邮箱类型1-inbox,2-system,3-private,4-battle,5-savebox,6-delbox */
	private short boxType;
	/** 查询的页面索引 */
	private short queryIndex;
	/** 总数 */
	private short totalNums;
	/** 邮件列表 */
	private com.pwrd.war.common.model.mail.MailInfo[] mailInfos;

	public GCMailList (){
	}
	
	public GCMailList (
			short boxType,
			short queryIndex,
			short totalNums,
			com.pwrd.war.common.model.mail.MailInfo[] mailInfos ){
			this.boxType = boxType;
			this.queryIndex = queryIndex;
			this.totalNums = totalNums;
			this.mailInfos = mailInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		boxType = readShort();
		queryIndex = readShort();
		totalNums = readShort();
		count = readShort();
		count = count < 0 ? 0 : count;
		mailInfos = new com.pwrd.war.common.model.mail.MailInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.common.model.mail.MailInfo obj = new com.pwrd.war.common.model.mail.MailInfo();
			obj.setUuid(readString());
			obj.setTitle(readString());
			obj.setSendName(readString());
			obj.setContent(readString());
			obj.setMailType(readInteger());
			obj.setMailStatus(readInteger());
			obj.setCreateTime(readString());
			obj.setUpdateTime(readLong());
			mailInfos[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(boxType);
		writeShort(queryIndex);
		writeShort(totalNums);
		writeShort(mailInfos.length);
		for(int i=0; i<mailInfos.length; i++){
			writeString(mailInfos[i].getUuid());
			writeString(mailInfos[i].getTitle());
			writeString(mailInfos[i].getSendName());
			writeString(mailInfos[i].getContent());
			writeInteger(mailInfos[i].getMailType());
			writeInteger(mailInfos[i].getMailStatus());
			writeString(mailInfos[i].getCreateTime());
			writeLong(mailInfos[i].getUpdateTime());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MAIL_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MAIL_LIST";
	}

	public short getBoxType(){
		return boxType;
	}
		
	public void setBoxType(short boxType){
		this.boxType = boxType;
	}

	public short getQueryIndex(){
		return queryIndex;
	}
		
	public void setQueryIndex(short queryIndex){
		this.queryIndex = queryIndex;
	}

	public short getTotalNums(){
		return totalNums;
	}
		
	public void setTotalNums(short totalNums){
		this.totalNums = totalNums;
	}

	public com.pwrd.war.common.model.mail.MailInfo[] getMailInfos(){
		return mailInfos;
	}

	public void setMailInfos(com.pwrd.war.common.model.mail.MailInfo[] mailInfos){
		this.mailInfos = mailInfos;
	}	
}