package com.pwrd.war.gameserver.mail.msg;

import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.mail.MailBox;
import com.pwrd.war.gameserver.mail.MailInstance;
import com.pwrd.war.gameserver.player.Player;

public class SysReceiveMailFinish extends SysInternalMessage {
	
	/**
	 * 收件人的uuid
	 * 
	 */
	private String recHumanUUID;
	
	/**
	 * 创建好的邮件实例
	 */
	private MailInstance mailInst;
	
	
	public SysReceiveMailFinish(String recHumanUUID, MailInstance mailInst) {
		super();
		this.recHumanUUID = recHumanUUID;
		this.mailInst = mailInst;
	}



	@Override
	public void execute() {
		Player player = Globals.getOnlinePlayerService().getPlayer(recHumanUUID);
		if(player != null){
			Human currentRole = player.getHuman();			
			if(currentRole != null)
			{
				this.mailInst.setOwner(currentRole);
				this.mailInst.getLifeCycle().activate();
				MailBox mailbox = currentRole.getMailbox();				
				mailbox.receiveMail(mailInst);			
			}		
		}		
	}
	
	

}
