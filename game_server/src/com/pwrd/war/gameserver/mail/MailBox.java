package com.pwrd.war.gameserver.mail;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.pwrd.war.common.HeartBeatListener;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.annotation.SyncIoOper;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.db.model.MailEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.RoleDataHolder;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatTaskExecutor;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatTaskExecutorImpl;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.mail.msg.GCHasNewMail;
import com.pwrd.war.gameserver.mail.msg.GCMailUpdate;
import com.pwrd.war.gameserver.mail.msg.MailMessageBuilder;

public class MailBox implements RoleDataHolder, HeartBeatListener{
	
	/** 邮件所属的玩家 */
	private final Human owner;

	
	/** 所有邮件 */
	private Map<String, MailInstance> allmails;
	
	/** 收件箱[全部] */
	private List<MailInstance> inbox;
	
	/** 系统收件箱 */
	private List<MailInstance> sysbox;
	
	/** 玩家的私人邮件 */
	private List<MailInstance> privatebox;
	
	/** 战报邮件 */
	private List<MailInstance> battleReportbox;
	
	/** 史实邮件 */
	private List<MailInstance> storyBox;
	
	/** 保存箱 */
	private SortedSet<MailInstance> savebox;
	
	/** 删除箱 */
	private SortedSet<MailInstance> delbox;
	
	/** 心跳任务处理器 */
	private HeartbeatTaskExecutor hbTaskExecutor;
	

	public MailBox(Human owner)
	{
		this.owner = owner;
		this.allmails = Maps.newLinkedHashMap();
		this.inbox = Lists.newLinkedList();
		this.sysbox = Lists.newLinkedList();
		this.privatebox = Lists.newLinkedList();
		this.battleReportbox = Lists.newLinkedList();
		this.savebox = Sets.newSortedArraySet();
		this.delbox =  Sets.newSortedArraySet();
		this.storyBox = Lists.newLinkedList();
		
		hbTaskExecutor = new HeartbeatTaskExecutorImpl();		
	}
	
	@SyncIoOper
	public void load() {
		
		// 从数据库加载邮件,同步io操作
		loadAllMailsFromDB(this);
		
	}
	
	/**
	 * 从数据库中加载玩家的所有物品
	 * 
	 * @param taskInfo
	 */
	@SyncIoOper
	public void loadAllMailsFromDB(MailBox mailbox) {
		Assert.notNull(mailbox);
		String _charId = mailbox.getOwner().getUUID();
		
		List<MailEntity> mails = null;
		try {
			mails = Globals.getDaoService().getMailDao().getMailsByCharId(_charId);
			for (MailEntity entity : mails) {
				MailInstance mail = MailInstance.buildFromItemEntity(entity, mailbox.getOwner());
				mail.setInDb(true); // 从db中读出来的都在db中
				if (mail != null) {
					mail.getLifeCycle().deactivate();
					if (mail.validateOnLoaded(entity)) {
						mailbox.putMail(mail);
					} 
				}
			}
		} catch (DataAccessException e) {
			if (Loggers.mailLogger.isErrorEnabled()) {
				Loggers.mailLogger.error(LogUtils.buildLogInfoStr(mailbox
						.getOwner().getUUID(), "从数据库中加载邮件信息出错"), e);
			}
			return;
		}
	}
	
	
	
	/**
	 * 
	 * 邮件接受,并通知客户端
	 * 
	 * @param mail
	 */
	public void receiveMail(MailInstance mailInst)
	{
		if(mailInst != null){
			putMail(mailInst);
			noticeHasNewMail();
		}
	}
	
	public void noticeMailUpdate(MailInstance mailInst)
	{
		GCMailUpdate gcMailUpdate = new GCMailUpdate();
		gcMailUpdate.setMail(MailMessageBuilder.createMailInfo(mailInst));
		this.getOwner().sendMessage(gcMailUpdate);		
	}
	
	private void noticeHasNewMail()
	{
		GCHasNewMail gcHasNewMail = new GCHasNewMail();
		this.getOwner().sendMessage(gcHasNewMail);
	}

	private void putMail(MailInstance mail) {
		allmails.put(mail.getMailUUID(), mail);
		if(mail.isDeleted())
		{
			delbox.add(mail);
		}
		else if(mail.isSaved())
		{
			savebox.add(mail);
		}
		else
		{
			inbox.add(0, mail);
			switch(mail.getMailType())
			{
				case SYSTEM:
				{
					sysbox.add(0, mail);
					break;
				}
				case NORMAL:
				{
					privatebox.add(0, mail);
					break;
				}
				case BATTLE:
				{
					battleReportbox.add(0, mail);
					break;
				}
				case STORY:
				{
					storyBox.add(0, mail);
					break;
				}
			}		
		}
	}

	@Override
	public void checkAfterRoleLoad() {
		// 激活邮件对象
		activeAllMails();
	}
	
	private void activeAllMails() {
		for (MailInstance mail : allmails.values()) {
			mail.getLifeCycle().activate();
		}
	}


	@Override
	public void checkBeforeRoleEnter() {
		
	}

	@Override
	public void onHeartBeat() {
		hbTaskExecutor.onHeartBeat();
	}

	public Human getOwner() {
		return owner;
	}
	
	public MailInstance getMailByUUID(String uuid)
	{
		return allmails.get(uuid);
	}

	public void removeMailFromInbox(MailInstance mailInstance)
	{
		inbox.remove(mailInstance);
	}
	
	public void removeMailFromSysbox(MailInstance mailInstance)
	{
		sysbox.remove(mailInstance);
	}
	
	public void removeMailFromPrivatebox(MailInstance mailInstance)
	{
		privatebox.remove(mailInstance);
	}
	
	public void removeMailFromBattleReportbox(MailInstance mailInstance)
	{
		battleReportbox.remove(mailInstance);
	}
	
	public void removeMailFromSavebox(MailInstance mailInstance)
	{
		savebox.remove(mailInstance);
	}
	
	public void removeMailFromDelbox(MailInstance mailInstance)
	{
		delbox.remove(mailInstance);
	}
	
	public void removeMailFromStorybox(MailInstance mailInstance)
	{
		storyBox.remove(mailInstance);
	}
	
	public void addMailToInbox(MailInstance mailInstance)
	{
		inbox.add(mailInstance);		
	}
	
	public void addMailToSysbox(MailInstance mailInstance)
	{
		sysbox.add(mailInstance);
	}
	
	public void addMailToPrivatebox(MailInstance mailInstance)
	{
		privatebox.add(mailInstance);
	}
	
	public void addMailToBattleReportbox(MailInstance mailInstance)
	{
		battleReportbox.add(mailInstance);
	}
	
	public void addMailToSavebox(MailInstance mailInstance)
	{
		savebox.add(mailInstance);
	}
		
	public void addMailToDelbox(MailInstance mailInstance)
	{
		delbox.add(mailInstance);
	}
	
	public void addMailToStorybox(MailInstance mailInstance)
	{
		storyBox.add(mailInstance);
	}
	

	public List<MailInstance> getInbox() {
		return inbox;
	}
	
	public List<MailInstance> getSysbox() {
		return sysbox;
	}
	
	public List<MailInstance> getPrivatebox() {
		return privatebox;
	}
	
	public List<MailInstance> getBattleReportbox() {
		return battleReportbox;
	}
	
	public List<MailInstance> getStoryBox() {
		return storyBox;
	}

	public SortedSet<MailInstance> getSavebox() {
		return savebox;
	}

	public SortedSet<MailInstance> getDelbox() {
		return delbox;
	}
	

}
