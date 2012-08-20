package com.pwrd.war.gameserver.common.i18n;

/**
 * 
 * 颜色标记，这种标记影响从他出现到下一个标记出现的文本
 * 
 * @author liuli
 * @since 2010-5-19
 */
public enum ColorMark {

	/** 默认颜色 */
	NORMAL("", "", "#N"),
	/** 红色 */
	RED("<font color='#FF0000'>", "</font>", "#R"),
	/** 蓝色 */
	BLUE("<font color='#0000FF'>", "</font>", "#B"),
	/** 绿色 */
	GREEN("<font color='#00FF00'>", "</font>", "#G"),
	/** 橙色 */
	ORAGNE("<font color='#FF8000'>", "</font>", "#O");

	private ColorMark(String headHtml, String tailHtml, String mark) {
		this.headHtml = headHtml;
		this.tailHtml = tailHtml;
		this.mark = mark;
	}

	/** 起始html标记 */
	private String headHtml;
	/** 结尾html标记 */
	private String tailHtml;
	/** 标记 */
	private String mark;

	public String getHeadHtml() {
		return headHtml;
	}

	public String getTailHtml() {
		return tailHtml;
	}

	public String getMark() {
		return mark;
	}

	/**
	 * 根据标记字串返回标记类型
	 * 
	 * @param mark
	 * @return 如果无此字串，返回ColorMarks.NULL
	 */
	public static ColorMark parse(String mark) {
		for (ColorMark c : ColorMark.values()) {
			if (c.getMark().equals(mark)) {
				return c;
			}
		}
		throw new IllegalArgumentException(mark);
	}
}
