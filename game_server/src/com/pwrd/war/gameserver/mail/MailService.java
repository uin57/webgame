package com.pwrd.war.gameserver.mail;

import java.sql.Timestamp;

import org.apache.commons.collections.map.MultiKeyMap;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.db.model.MailEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.operation.BindUUIDIoOperation;
import com.pwrd.war.gameserver.common.i18n.LangService;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.mail.MailDef.MailStatus;
import com.pwrd.war.gameserver.mail.MailDef.MailType;
import com.pwrd.war.gameserver.mail.MailDef.SysMailReasonType;
import com.pwrd.war.gameserver.mail.msg.SysReceiveMailFinish;
import com.pwrd.war.gameserver.mail.template.MailContentTemplate;
import com.pwrd.war.gameserver.player.Player;

public class MailService implements InitializeRequired{
	
	private TemplateService tmplService;
	
	private final String systemSenderName;
	
	/**
	 * 系统邮件内容,<<reasonId,behaviorId>,MailContentTemplate>
	 */
	private MultiKeyMap sysMailContent = new MultiKeyMap();
	
	public MailService(TemplateService tmplService,LangService langService)
	{
		this.tmplService = tmplService;	

		this.systemSenderName = langService.read(CommonLangConstants_10000.SYSTEM_MAIL_SENDER_NAME);
	}
	
	@Override
	public void init() {
		for(MailContentTemplate template :this.tmplService.getAll(MailContentTemplate.class).values())
		{
			SysMailReasonType reasonType = SysMailReasonType.valueOf(template.getReasonId());
			sysMailContent.put(reasonType,template.getBehaviorId(), template);
		}	
	}
	
	/**
	 * 根据原因和触发行为id得到邮件内容模板
	 * @param reasonType
	 * @param behaviorId
	 * @return
	 */
	public MailContentTemplate getSystemMailInfo(SysMailReasonType reasonType,int behaviorId)
	{
		return (MailContentTemplate)sysMailContent.get(reasonType, behaviorId);
	}
	
	/**
	 * 发送系统邮件,一般为系统触发
	 * @param recId
	 * @param recName
	 * @param title
	 * @param content
	 */
	public void sendSysMail(String recId,String recName,MailType mailType,String title,String content)
	{
		if(mailType != MailType.SYSTEM && mailType != MailType.BATTLE)
		{
			return ;
		}
		
		MailInstance mailInstance = createMail(recId,MailDef.SYSTEM_MAIL_SEND_ID,systemSenderName,recName,mailType,title,content);
		
		SaveMailOperation saveTask = new SaveMailOperation(mailInstance);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(saveTask);	
	}
	
	
	/**
	 * 发送普通邮件,一对一
	 * @param sendId
	 * @param sendName
	 * @param recId
	 * @param recName
	 * @param title
	 * @param content
	 */
	public void sendMail(String sendId,String sendName,String recId,String recName,String title,String content)
	{
		MailInstance mailInstance = createMail(recId,sendId,sendName,recName,MailType.NORMAL,title,content);
	
		SaveMailOperation saveTask = new SaveMailOperation(mailInstance);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(saveTask);		
	}
	
	/**
	 * 创建一封邮件
	 * @param recId
	 * @param sendId
	 * @param sendName
	 * @param recName
	 * @param mailType
	 * @param title
	 * @param content
	 * @return
	 */
	private MailInstance createMail(String recId,String sendId,String sendName,String recName,MailType mailType,String title, String content)
	{
		MailInstance mailInstance = MailInstance.newDeactivedInstanceWithoutOwner();
		
		mailInstance.setInDb(false);
		mailInstance.setOwnerId(recId);
		mailInstance.setSendId(sendId);
		mailInstance.setSendName(sendName);		
		mailInstance.setRecId(recId);
		mailInstance.setRecName(recName);
		mailInstance.setMailType(mailType);
		mailInstance.setTitle(title);
		mailInstance.setContent(content);
		mailInstance.setMailStatus(MailStatus.UNREAD.mark);
		mailInstance.setUpdateTime(new Timestamp(Globals.getTimeService().now()));
		mailInstance.setHasAttachment(false);
		
		return mailInstance;
	}
	
	
	
	private static class SaveMailOperation implements BindUUIDIoOperation
	{
		private MailInstance mailInst;
		
		private MailEntity mailEntity;
		
		public SaveMailOperation(MailInstance mailInst)
		{
			this.mailInst = mailInst;			
		}
		
		@Override
		public String getBindUUID() {
			return mailInst.getRecId();
		}
		
		@Override
		public int doStart() {
			try {
				this.mailEntity = mailInst.toEntity();
			} catch (Exception e) {
				Loggers.playerLogger.error(String.format(
						"mail conventer error. sendId=%s,recId=%s", mailInst
								.getSendId(), mailInst.getRecId()), e);
			}			
			return STAGE_START_DONE;
		}

		@Override
		public int doIo() {
			try {
				if (this.mailEntity != null) {
					Globals.getDaoService().getMailDao().save(this.mailEntity);
					this.mailInst.setInDb(true);
				}							
			} catch (Exception e) {
				Loggers.playerLogger.error(String.format(
						"save mail info error. sendId=%s,recId=%s",mailInst
							.getSendId(), mailInst.getRecId()), e);
			}
			return STAGE_IO_DONE;
		}

		@Override
		public int doStop() {
			String ownerId = this.mailInst.getOwnerId();
			Player player = Globals.getOnlinePlayerService().getPlayer(ownerId);
			if(player != null)
			{			
				SysReceiveMailFinish mailreceivedmsg = new SysReceiveMailFinish(ownerId, mailInst);
				player.putMessage(mailreceivedmsg);
			}
			return STAGE_STOP_DONE;
		}
		
	}

}
