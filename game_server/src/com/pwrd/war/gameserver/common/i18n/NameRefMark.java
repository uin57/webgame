package com.pwrd.war.gameserver.common.i18n;

import java.util.regex.Pattern;

import com.pwrd.war.core.template.TemplateService;

/**
 * 引用标记，这种标记只是引用另一段文本的占位符，将被整体替换，不影响之前或之后的文本
 * 
 */
public enum NameRefMark implements RefMark<Object> {

	/** 物品名 */
	ITEM("<font color='%1$s'>%2$s</font>") {
		@Override
		public String transToHtml(Object obj, String mark, NameLookupTable lut,
				TemplateService templateService) {
			int itemTmplId = Integer.parseInt(MarkTranslatorService
					.getParam(mark));
			NameInfo info = lut.get(this, itemTmplId);
			return transToHtml(info);
		}

		@Override
		public String transToHtml(NameInfo info) {
			return String.format(getHtmlTmpl(),info.getColor(), info.getName());
		}
	},

	;

	public static final Pattern pattern = Pattern
			.compile("\\$\\w+\\{(\\d+,)*\\d+\\}");

	private NameRefMark(String htmlTmpl) {
		this.htmlTmpl = htmlTmpl;
	}

	/**
	 * 将标记转换成对应的html
	 * 
	 * @param info
	 * @return
	 */
	public abstract String transToHtml(NameInfo info);

	/**
	 * 根据标记字串返回标记类型
	 * 
	 * @param mark
	 * @return 如果无此字串，返回NameRefMark.NULL
	 */
	public static NameRefMark parse(String mark) {
		return valueOf(MarkTranslatorService.getMarkType(mark));
	}

	/** 对应的html模板 */
	private String htmlTmpl;

	public String getHtmlTmpl() {
		return htmlTmpl;
	}
}
