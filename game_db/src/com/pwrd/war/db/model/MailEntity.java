package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_mail_info")
public class MailEntity implements BaseEntity<String> , CharSubEntity{
	private static final long serialVersionUID = 1L;

	/** 主键 */	
	private String id;
	
	/** 所属角色ID */
	private String charId;
	
	/** 发送角色ID */
	private String sendId;
	
	/** 发送角色名称 */
	private String sendName;
	
	/** 接收角色ID */
	private String recId;
	
	/** 接收角色名称 */
	private String recName;
	
	/** 邮件标题 */
	private String title;
	
	/** 邮件内容 */
	private String content;
	
	/** 邮件类别 */
	private int msgType;
	
	/** 邮件状态 */
	private int msgStatus;
	
	/** 创建时间[游戏内] */
	private String createTimeInGame;
	
	/** 创建时间 */
	private Timestamp createTime;
		
	/** 最后更新时间 */
	private Timestamp updateTime;
	
	/** 是否已删除 */
	private int deleted;
	
	/** 删除时间 */
	private Timestamp deleteTime;
	
	/** 是否有附件 */
	private boolean hasAttachment;
	
	/** 附件内容(JSON字符串) */
	private String attachmentProps;
	
	/** 附件是否可以取出(是否合法) */
	private boolean isAttachmentValid;

	@Id
	@Override
	@Column(length = 36)
	public String getId() {
		return id;
	}
	
	@Override
	@Column
	public String getCharId() {
		return charId;
	}

	@Column
	public String getSendId() {
		return sendId;
	}

	@Column
	public String getSendName() {
		return sendName;
	}

	@Column
	public String getRecId() {
		return recId;
	}

	@Column
	public String getRecName() {
		return recName;
	}
	
	@Column
	public String getTitle() {
		return title;
	}

	@Column
	public String getContent() {
		return content;
	}


	@Column
	public int getMsgType() {
		return msgType;
	}

	@Column
	public int getMsgStatus() {
		return msgStatus;
	}
		
	@Column
	public String getCreateTimeInGame() {
		return createTimeInGame;
	}


	@Column
	public Timestamp getCreateTime() {
		return createTime;
	}

	@Column
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	@Column
	public int getDeleted() {
		return deleted;
	}

	@Column
	public Timestamp getDeleteTime() {
		return deleteTime;
	}

	@Column
	public boolean isHasAttachment() {
		return hasAttachment;
	}

	@Column
	public String getAttachmentProps() {
		return attachmentProps;
	}

	@Column
	public boolean isAttachmentValid() {
		return isAttachmentValid;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public void setCharId(String charId) {
		this.charId = charId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public void setMsgStatus(int msgStatus) {
		this.msgStatus = msgStatus;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}

	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	public void setAttachmentProps(String attachmentProps) {
		this.attachmentProps = attachmentProps;
	}

	public void setAttachmentValid(boolean isAttachmentValid) {
		this.isAttachmentValid = isAttachmentValid;
	}
	

	public void setCreateTimeInGame(String createTimeInGame) {
		this.createTimeInGame = createTimeInGame;
	}
	

	@Override
	public String toString() {
		return "MailEntity [id=" + id 
				+ ", charId=" + charId 
				+ ", sendId=" + sendId 
				+ ", sendName=" + sendName 
				+ ", recId=" + recId 
				+ ", recName=" + recName
				+ ", title=" + title 
				+ ", content=" + content 
				+ ", msgType=" + msgType 
				+ ", msgStatus=" + msgStatus 
				+ ", createTime=" + createTime 
				+ ", updateTime=" + updateTime 
				+ ", deleted=" + deleted 
				+ ", deleteTime=" + deleteTime
				+ ", hasAttachment=" + hasAttachment 
				+ ", attachmentProps=" + attachmentProps
				+ ", isAttachmentValid=" + isAttachmentValid
				+ "]";
	}

}
