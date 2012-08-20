package com.pwrd.war.robot.msg;

import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.common.MessageMappingProvider;

import com.pwrd.war.gameserver.giftBag.msg.CGGiftBagInfoList;
import com.pwrd.war.gameserver.giftBag.msg.GCGiftBagInfoList;
import com.pwrd.war.gameserver.giftBag.msg.CGGetGiftBagPrize;

/**
 *  Generated by MessageCodeGenerator,don't modify please.
 *  Need to register in<code>GameMessageRecognizer#init</code>
 */
public class GiftbagMsgMappingProvider implements MessageMappingProvider {

	@Override
	public Map<Short, Class<?>> getMessageMapping() {
		Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
		map.put(MessageType.CG_GIFT_BAG_INFO_LIST, CGGiftBagInfoList.class);
		map.put(MessageType.GC_GIFT_BAG_INFO_LIST, GCGiftBagInfoList.class);
		map.put(MessageType.CG_GET_GIFT_BAG_PRIZE, CGGetGiftBagPrize.class);
		return map;
	}

}