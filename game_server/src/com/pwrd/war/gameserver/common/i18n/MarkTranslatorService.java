package com.pwrd.war.gameserver.common.i18n;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.exception.ConfigException;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.core.util.Assert;

public class MarkTranslatorService {
	private static final Pattern markPattern = Pattern.compile("[\\$#]");

	/**
	 * 获得mark中的参数
	 * 
	 * @param mark
	 * @return
	 */
	public static String getParam(String mark) {
		int start = mark.indexOf("{") + 1;
		return mark.substring(start, mark.length() - 1);
	}

	/**
	 * 解析出mark类型名
	 * 
	 * @param mark
	 * @return
	 */
	public static String getMarkType(String mark) {
		Assert.notNull(mark);
		int typeEnd = mark.indexOf("{");
		if (typeEnd == -1) {
			typeEnd = mark.length();
		}
		return mark.substring(1, typeEnd).toUpperCase();
	}

	@SuppressWarnings("unchecked")
	private static RefMark parse(Class<? extends RefMark> refMarkClass,
			String mark) {
		if (refMarkClass == NameRefMark.class) {
			return NameRefMark.parse(mark);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static String translate(Pattern pattern,
			Class<? extends RefMark> refMarkClass, NameLookupTable lut,
			Object obj, String sentence, int templateId,
			TemplateService templateService) {
		if (sentence == null || StringUtils.isEmpty(sentence)) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		Matcher markMatcher = markPattern.matcher(sentence);
		Matcher nameMatcher = pattern.matcher(sentence);
		ColorMark lastMark = null;
		int pos = 0;
		String curMark;
		try {
			while (markMatcher.find(pos)) {
				String h = markMatcher.group();
				int st = markMatcher.start();
				builder.append(sentence.subSequence(pos, st));
				pos = st;
				if (lastMark != null) {
					// 只要发现一个标记，就结束上一个标记
					builder.append(lastMark.getTailHtml());
				}
				if (h.equals("#")) { // 颜色标签
					curMark = sentence.substring(st, st + 2);
					ColorMark cMark = ColorMark.parse(curMark);
					// 加入此标签头部
					builder.append(cMark.getHeadHtml());
					lastMark = cMark;

				} else { // 名称引用标签
					// 写了$就必须是名字引用，否则报错
					if (!nameMatcher.find(st)) {
						throw new IllegalArgumentException(String.format(
								"%s [st=%s]", sentence, st));
					}
					curMark = nameMatcher.group();
					RefMark _nMark = MarkTranslatorService.parse(refMarkClass,
							curMark);
					String _text = _nMark.transToHtml(obj, curMark, lut,
							templateService);
					builder.append(_text);
					lastMark = null;
				}
				// 移动到下一个位置
				pos += curMark.length();
			}
			if (lastMark != null) {
				// 结束上一个标记
				builder.append(lastMark.getTailHtml());
			}
			// 加入后续字符串
			builder.append(sentence.substring(pos));
		} catch (Exception e) {
			String msg = String.format("翻译引用标记时出现异常 类型=%s id=%s 句子=%s", obj
					.getClass().getName(), templateId, sentence);
			throw new ConfigException(msg, e);
		}
		return builder.toString();
	}
}
