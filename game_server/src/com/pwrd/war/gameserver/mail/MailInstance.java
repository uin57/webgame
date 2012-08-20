package com.pwrd.war.gameserver.mail;

import java.sql.Timestamp;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.db.model.MailEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.mail.MailDef.MailStatus;
import com.pwrd.war.gameserver.mail.MailDef.MailType;

public class MailInstance implements PersistanceObject<String, MailEntity>,Comparable<MailInstance>{
	
	/** 邮件的实例UUID */
	private String mailUUID;
	
	/** 所有者 */
	private Human owner;
	
	/** 此实例是否在db中 */
	private boolean isInDb;
	
	/** 邮件的生命期的状态 */
	private final LifeCycle lifeCycle;
	
	private String ownerId = "";
	
	/** 发送者uuid */
	private String sendId;
	
	/** 发送者名称 */
	private String sendName;
	
	/** 接收者uuid */
	private String recId;
	
	/** 接收者名称 */
	private String recName;
	
	/** 名称 */
	private String title;
	
	/** 内容 */
	private String content;
	
	/** 邮件类型 */
	private MailType mailType;
	
	/** 邮件状态值{@see MailStatus} */
	private int mailStatus;
	
	/** 创建时间 游戏内 */
	private String createTimeInGame;
	
	/** 创建时间 */
	private Timestamp createTime;
	
	/** 更新时间 */
	private Timestamp updateTime;
	
	/** 是否有附件 */
	private boolean hasAttachment = false;
	

	private MailInstance(Human owner) {
		lifeCycle = new LifeCycleImpl(this);
		this.owner = owner;
	}
	

	/**
	 * 创建一个激活的邮件对象,例如从库中读取
	 * 
	 * 
	 * @param owner
	 * @return
	 */
	public static MailInstance newActivatedInstance(Human owner) {
		Assert.notNull(owner);
		MailInstance mail = new MailInstance(owner);
		mail.setOwnerId(owner.getCharId());
		mail.setMailUUID(KeyUtil.UUIDKey());
		mail.setCreateTime(new Timestamp(Globals.getTimeService().now()));
		mail.lifeCycle.activate();
		return mail;
	}
	
	/**
	 * 创建不绑定所有者的邮件，创建时默认是未激活状态，即不会出现在游戏世界中，也不会对应数据库中的记录
	 * 
	 * @param human
	 * @return
	 */
	public static MailInstance newDeactivedInstanceWithoutOwner() {
		MailInstance mail = new MailInstance(null);
		mail.lifeCycle.deactivate();
		mail.setMailUUID(KeyUtil.UUIDKey());
//		mail.setCreateTimeInGame(Globals.getSeasonService().getCurrSeasonString());
		mail.setCreateTime(new Timestamp(Globals.getTimeService().now()));
		return mail;
	}
	
	/**
	 * 从ItemEntity生成一个item实例
	 * 
	 * @param entity
	 * @return
	 */
	public static MailInstance buildFromItemEntity(MailEntity entity, Human owner) {
		MailInstance mail = new MailInstance(owner);
		mail.fromEntity(entity);
		return mail;
	}
	
	/**
	 * 邮件数据加载完成后进行数据的校验,只有校验通过的邮件才会加载,过期邮件删除
	 * 
	 * @return true,数据正常,校验通过;false,数据异常,校验未通过
	 */
	public boolean validateOnLoaded(MailEntity mailInfo) {
		boolean isValid = true;
		do
		{
			if(this.getOwnerId().equals("") || this.getMailType() == null || this.getCreateTime() == null || !MailStatus.isValidStatus(this.getMailStatus()))
			{
				onDelete();
				
				if (Loggers.mailLogger.isWarnEnabled()) {
					Loggers.mailLogger.error(LogUtils.buildLogInfoStr(
							getCharId(), String.format(
									"[Found inValid mail from db mail=%s",
									mailInfo.toString())));
				}
				
				isValid = false;
				break;
			}
			
			if(isExpired(Globals.getTimeService().now()))
			{
				onDelete();
				
				if (Loggers.mailLogger.isWarnEnabled()) {
					Loggers.mailLogger.error(LogUtils.buildLogInfoStr(
							getCharId(), String.format(
									"[Found inValid mail from db mail=%s",
									mailInfo.toString())));
				}
			
				isValid = false;
				break;
			}
									
			
		}while(false);	
		return isValid;	
	}

	@Override
	public void fromEntity(MailEntity entity) {
		this.setDbId(entity.getId());
		if(this.getOwner() != null)
		{
			this.setOwnerId(this.getOwner().getUUID());
		}
		this.setSendId(entity.getSendId());
		this.setSendName(entity.getSendName());
		this.setRecId(entity.getRecId());
		this.setRecName(entity.getRecName());
		this.setTitle(entity.getTitle());
		this.setContent(entity.getContent());
		this.setMailType(MailType.valueOf(entity.getMsgType()));
		this.setMailStatus(entity.getMsgStatus());
		this.setCreateTimeInGame(entity.getCreateTimeInGame());
		this.setCreateTime(entity.getCreateTime());
		this.setUpdateTime(entity.getUpdateTime());
		this.setHasAttachment(entity.isHasAttachment());		
	}
	
	@Override
	public MailEntity toEntity() {
		MailEntity mailEntity = new MailEntity();
		mailEntity.setId(this.getMailUUID());
		mailEntity.setCharId(this.getCharId());
		mailEntity.setSendId(this.getSendId());
		mailEntity.setSendName(this.getSendName());
		mailEntity.setRecId(this.getRecId());
		mailEntity.setRecName(this.getRecName());
		mailEntity.setTitle(this.getTitle());
		mailEntity.setContent(this.getContent());
		mailEntity.setMsgType(this.getMailType().getIndex());
		mailEntity.setMsgStatus(this.getMailStatus());
		mailEntity.setCreateTimeInGame(this.getCreateTimeInGame());
		mailEntity.setCreateTime(this.getCreateTime());
		mailEntity.setUpdateTime(this.getUpdateTime());		
		mailEntity.setHasAttachment(this.isHasAttachment());		
		return mailEntity;
	}
	

	@Override
	public String getCharId() {	
		return ownerId;
	}

	@Override
	public String getGUID() {
		return this.mailUUID;
	}

	@Override
	public String getDbId() {
		return this.mailUUID;
	}

	@Override
	public void setDbId(String id) {
		this.mailUUID = id;
	}
	
	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}
		
	@Override
	public boolean isInDb() {
		return isInDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.isInDb = inDb;
	}

	@Override
	public void setModified() {		
		if (owner != null) {
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive()) {
				// 邮件的生命期处于活动状态,则执行通知更新机制进行
				this.onUpdate();
			}
		}		
	}

	public String getMailUUID() {
		return mailUUID;
	}

	public void setMailUUID(String mailUUID) {
		this.mailUUID = mailUUID;
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
		setModified();
	}
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
		setModified();
	}


	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
		setModified();
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
		setModified();
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
		setModified();
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
		setModified();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		setModified();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		setModified();
	}

	public MailType getMailType() {
		return mailType;
	}

	public void setMailType(MailType mailType) {
		this.mailType = mailType;
		setModified();
	}

	public int getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(int mailStatus) {
		this.mailStatus = mailStatus;
		setModified();
	}
	
	public void removeMailStatus(int mailStatus) {
		this.mailStatus &= (MailStatus.MAX_MAIL_MASK ^ mailStatus);
		setModified();
	}
	
	public void addMailStatus(int mailStatus) {
		this.mailStatus |= mailStatus;
		setModified();
	}
	
	

	public String getCreateTimeInGame() {
		return createTimeInGame;
	}


	public void setCreateTimeInGame(String createTimeInGame) {
		this.createTimeInGame = createTimeInGame;
		setModified();
	}


	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
		setModified();
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
		setModified();
	}

	public boolean isHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
		setModified();
	}
	
	
	public boolean isUnread()
	{
		return (this.getMailStatus() & MailStatus.UNREAD.mark) != 0;
	}
	
	public boolean isReaded()
	{
		return (this.getMailStatus() & MailStatus.READED.mark) != 0;
	}
	
	public boolean isSaved()
	{
		return (this.getMailStatus() & MailStatus.SAVED.mark) != 0;
	}
	
	public boolean isDeleted()
	{
		return (this.getMailStatus() & MailStatus.DELETED.mark) != 0;
	}
	
	private boolean isExpired(long now) 
	{
		if(isDeleted()) //如果用户已经手动删除,只保留一天就删除
		{
			if(this.updateTime != null)
			{
				long validityExpireTime = TimeUtils.getDeadLine(this.updateTime,
					Globals.getGameConstants().getMailInDelboxExpiredTime(), TimeUtils.HOUR);
				return now >= validityExpireTime;
			}
			else
			{
				long validityExpireTime = TimeUtils.getDeadLine(this.createTime,
						Globals.getGameConstants().getMailInDelboxExpiredTime(), TimeUtils.HOUR);
				return now >= validityExpireTime;
			}
		}
		else
		{
			
			if(!this.isSaved()) //非保存邮件,7天删除
			{
				long validityExpireTime = TimeUtils.getDeadLine(this.createTime,
						Globals.getGameConstants().getMailInInboxExpiredTime(), TimeUtils.HOUR);
				if(now >= validityExpireTime)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else //保存邮件只通过数量限制
			{
				return false;
			}
		}			
	}
	
	/**
	 * 邮件实例被修改(新增加或者属性更新)时调用,触发更新机制
	 */
	private void onUpdate() {
		if (Loggers.mailLogger.isDebugEnabled()) {
			Loggers.mailLogger.debug(String.format(
					"update mail=%s ", this.toString()));
		}
		this.getOwner().getPlayer().getDataUpdater().addUpdate(
				this.getLifeCycle());
	}
	
	
	/**
	 * 邮件被删除时调用,恢复默认值,并触发删除机制
	 * 
	 */
	public void onDelete() {
		this.lifeCycle.destroy();
		if (Loggers.mailLogger.isDebugEnabled()) {
			Loggers.mailLogger.debug(String.format(
					"delete item=%s ", this.toString()));
		}
		this.getOwner().getPlayer().getDataUpdater().addDelete(
				this.getLifeCycle());
	}
	
	@Override
	public int hashCode() {
		return mailUUID.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MailInstance other = (MailInstance) obj;
		if (!mailUUID.equals(other.mailUUID))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "MailInstance [UUID=" + mailUUID 
				+ ", owner=" + owner
				+ ", isInDb=" + isInDb 
				+ ", lifeCycle=" + lifeCycle 
				+ ", ownerId=" + ownerId 
				+ ", sendId=" + sendId 
				+ ", sendName=" + sendName 
				+ ", recId=" + recId 
				+ ", recName=" + recName 
				+ ", title=" + title 
				+ ", content=" + content 
				+ ", mailType=" + mailType.getIndex() 
				+ ", mailStatus=" + mailStatus
				+ ", createTime=" + createTime 
				+ ", updateTime=" + updateTime + "]";
	}


	/**
	 * 被传入邮件的创建时间更早,返回负数,在treeSet中更靠后
	 */
	@Override
	public int compareTo(MailInstance other) {
		Timestamp currTime = this.getCreateTime();
		Timestamp otherTime = other.getCreateTime();
		return otherTime.compareTo(currTime);
	}
		
	
}
