package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.common.model.mail.MailInfo;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.util.MathUtils;
import com.pwrd.war.gameserver.mail.MailDef.BoxType;
import com.pwrd.war.gameserver.mail.msg.CGDelMail;
import com.pwrd.war.gameserver.mail.msg.CGMailList;
import com.pwrd.war.gameserver.mail.msg.CGSaveMail;
import com.pwrd.war.gameserver.mail.msg.CGSendMail;
import com.pwrd.war.gameserver.mail.msg.GCMailList;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.LoopExecuteStrategy;

/**
 * 发送邮件
 * @author jiliang.lu
 *
 */
public class SendMailStrategy extends LoopExecuteStrategy {
	
	/** 类参数构造器 */
	public SendMailStrategy(Robot robot, int delay, int period) {
		super(robot, delay,period);
	}

	@Override
	public void doAction() {
		requestMails();
	}
	
	public void sendMail()
	{
		CGSendMail cgSendMail = new CGSendMail();
		cgSendMail.setTitle("第三帝国邮件测试");
		cgSendMail.setContent("第三帝国邮件测试,内容测试。");		
		String selfname = "r" + getRobot().getPid();
		cgSendMail.setRecName(selfname);		
		
		sendMessage(cgSendMail);
	}
	
	public void requestMails()
	{
		CGMailList cgMailList = new CGMailList();
		cgMailList.setBoxType((short)BoxType.INBOX.getIndex());
		cgMailList.setQueryIndex((short)0);
		
		sendMessage(cgMailList);
	}
	
	public void saveMail()
	{
		MailInfo mailInfo = getRobot().getMailManager().getMail();
		if(mailInfo != null)
		{
			CGSaveMail cgSaveMail = new CGSaveMail();
			cgSaveMail.setUuid(mailInfo.getUuid());
			
			sendMessage(cgSaveMail);
		}		
	}
	
	public void deleteMail()
	{
		MailInfo mailInfo = getRobot().getMailManager().getMail();
		if(mailInfo != null)
		{
			CGDelMail cgDelMail = new CGDelMail();
			cgDelMail.setUuid(mailInfo.getUuid());
			
			sendMessage(cgDelMail);
		}		
	}

	
	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCMailList)
		{
			getRobot().getMailManager().updateAll(((GCMailList)message));
			int randomResult = MathUtils.random(0, 100);
			if(randomResult <= 60)
			{
				sendMail();
			}
			else if(randomResult <= 100)
			{
				saveMail();
			}
			else if(randomResult <= 100)
			{
				deleteMail();
			}			
		}
	}

}
