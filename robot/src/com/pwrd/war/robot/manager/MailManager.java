package com.pwrd.war.robot.manager;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.pwrd.war.common.model.mail.MailInfo;
import com.pwrd.war.gameserver.mail.msg.GCMailList;
import com.pwrd.war.robot.Robot;

public class MailManager extends AbstractManager{
	
	
	public Map<String, MailInfo> pagemails;

	public MailManager(Robot owner) {
		super(owner);
		pagemails = Maps.newHashMap();
	}
	
	public void updateAll(GCMailList gcMailList)
	{
		pagemails.clear();
		for(MailInfo mail :gcMailList.getMailInfos())
		{
			pagemails.put(mail.getUuid(), mail);
		}		
	}
	
	public MailInfo getMail()
	{
		Iterator<Entry<String,MailInfo>> it = pagemails.entrySet().iterator();
		if(it.hasNext())
		{
			return it.next().getValue();
		}
		return null;
	}

}
