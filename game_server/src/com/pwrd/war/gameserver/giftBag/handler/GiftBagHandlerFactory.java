/**
 * 
 */
package com.pwrd.war.gameserver.giftBag.handler;


/**
 * 
 * 礼包消息处理器工厂类
 * @author dendgan
 *
 */
public class GiftBagHandlerFactory {

private static final GiftBagMessageHandler giftBagMessageHandler = new GiftBagMessageHandler();
	
	public static GiftBagMessageHandler getHandler(){
		return giftBagMessageHandler;
	}
}
