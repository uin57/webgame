package com.pwrd.war.gameserver.common.enums;

import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;

/**
 * 阵营
 * @author xf
 */
public enum Camp {
	NONE(0, "", CommonLangConstants_10000.CAMP_NONE),
	WEI(1, "魏", CommonLangConstants_10000.CAMP_WEI),
	SHU(2, "蜀", CommonLangConstants_10000.CAMP_SHU),
	WU(3, "吴", CommonLangConstants_10000.CAMP_WU),
	QUNXIONG(4, "群雄", CommonLangConstants_10000.CAMP_QUN);
	
	
	private int code;
	private String value;
	private int langCode;
	private Camp(int code, String value, int langCode){
		this.code = code;
		this.value = value;
		this.langCode = langCode;
	}
	
	
	public static Camp getByCode(int code){
		Camp[] vs = Camp.values();
		for(Camp v : vs){
			if(v.code == code)return v;
		}
		return NONE;
	}


	public int getCode() {
		return code;
	}


	public String getValue() {
		return value;
	}


	public int getLangCode() {
		return langCode;
	}


	public void setLangCode(int langCode) {
		this.langCode = langCode;
	}
}
