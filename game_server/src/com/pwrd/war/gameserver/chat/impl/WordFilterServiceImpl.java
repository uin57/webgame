package com.pwrd.war.gameserver.chat.impl;

import java.util.regex.Pattern;

import com.pwrd.war.gameserver.chat.WordFilterService;
import com.pwrd.war.gameserver.common.Globals;

/**
 * 过滤聊天中的不良信息
 * 
 * 
 */
public class WordFilterServiceImpl implements WordFilterService {

	private static final Pattern html_pattern = Pattern.compile("<[^>]+>",
			Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

	@Override
	public boolean containKeywords(String chatContent) {
		return Globals.getDirtFilterService().contains(chatContent);
	}

	@Override
	public String filter(String chatContent) {
		return Globals.getDirtFilterService().filt(chatContent);
	}

	@Override
	public String filterHtmlTag(String chatContent) {
		return html_pattern.matcher(chatContent).replaceAll("");
	}

}
