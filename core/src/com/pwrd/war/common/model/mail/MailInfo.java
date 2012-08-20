package com.pwrd.war.common.model.mail;


public class MailInfo {
	
	private String uuid;
	
	private String title;
	
	private String content;
	
	private int mailType;
	
	private int mailStatus;
	
	private String createTime;
	
	private long updateTime;
	
	private String sendName;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMailType() {
		return mailType;
	}

	public void setMailType(int mailType) {
		this.mailType = mailType;
	}

	public int getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(int mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String toString() {
		return "MailInfo [ UUId = " + this.uuid
			+ ", title = " + this.title
			+ ", content = " + this.content
			+ ", mailType = " + this.mailType
			+ ", mailStatus = " + this.mailStatus
			+ ", createTime = " + this.createTime
			+ ", updateTime = " + this.updateTime
			+ ", sendName = " + this.sendName + " ]";
	}
}
