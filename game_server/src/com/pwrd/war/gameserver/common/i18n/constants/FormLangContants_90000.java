package com.pwrd.war.gameserver.common.i18n.constants;

import com.pwrd.war.core.annotation.SysI18nString;

public class FormLangContants_90000 {
	public static Integer BASE = 90000;
	
	@SysI18nString(content = "阵型等级不正确")
	public static final Integer NOT_SUCH_FORM = ++BASE;
	
	@SysI18nString(content = "阅历不够")
	public static final Integer NOT_ENOUGH_SEE = ++BASE;
	
	@SysI18nString(content = "金钱不够")
	public static final Integer NOT_ENOUGH_MONEY = ++BASE;
	
	@SysI18nString(content = "您获得了{0}阵法")
	public static final Integer GET_FORMS = ++BASE;

}
