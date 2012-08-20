package com.pwrd.war.gameserver.common.enums;

/**
 * 职业
 * @author xf
 */
public enum VocationType {

	NONE(0, ""),
	MENGJIANG(1, "猛将"),
	HAOJIE(2, "豪杰"),
	SHESHOU(3, "射手"),
	MOUSHI(4, "谋士"),
	;
	
	private int code;
	private String value;
	private VocationType(int code, String value){
		this.code = code;
		this.value = value;
	}
	
	
	public static VocationType getByCode(int code){
		VocationType[] vs = VocationType.values();
		for(VocationType v : vs){
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
}
