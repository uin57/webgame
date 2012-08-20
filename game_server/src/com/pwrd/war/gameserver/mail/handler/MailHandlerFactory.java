package com.pwrd.war.gameserver.mail.handler;

/**
 * 
 * 邮件消息处理器工厂
 * 
 * @author jiliang.lu
 *
 */
public class MailHandlerFactory {

	private static final MailMessageHandler handler = new MailMessageHandler();

	public static MailMessageHandler getHandler() {
		return handler;
	}

}
