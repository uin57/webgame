package com.pwrd.war.gameserver.command;

import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.common.MessageMappingProvider;

/**
 * 
 * 
 */
public class CommonMsgMappingProvider implements MessageMappingProvider {

	@Override
	public Map<Short, Class<?>> getMessageMapping() {
		Map<Short, Class<?>> map = new HashMap<Short, Class<?>>();
		return map;
	}

}
