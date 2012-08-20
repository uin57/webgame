package com.pwrd.war.gameserver.mail.msg;

import java.util.List;

import com.pwrd.war.common.model.mail.MailInfo;
import com.pwrd.war.gameserver.mail.MailInstance;

public class MailMessageBuilder {
	
	public static GCMailUpdate buildGCMailInfo(MailInstance mail) {
		GCMailUpdate msg = new GCMailUpdate();		
		msg.setMail(createMailInfo(mail));		
		return msg;
	}
	
	public static MailInfo[] createMailInfo(List<MailInstance> mails)
	{		
		MailInfo[] mailInfos = new MailInfo[mails.size()];
		int i = 0;
		for (MailInstance mail : mails) {
			mailInfos[i++] = createMailInfo(mail);
		}
		return mailInfos;
	}
	
	public static MailInfo createMailInfo(MailInstance mail)
	{
		MailInfo mailInfo = new MailInfo();
		mailInfo.setUuid(mail.getMailUUID());
		mailInfo.setSendName(mail.getSendName());
		mailInfo.setMailType(mail.getMailType().getIndex());
		mailInfo.setMailStatus(mail.getMailStatus());
		mailInfo.setTitle(mail.getTitle());
		mailInfo.setContent(mail.getContent());	
		mailInfo.setCreateTime(mail.getCreateTimeInGame());
		mailInfo.setUpdateTime(mail.getUpdateTime().getTime());
		return mailInfo;
	}
	
}
