package com.pwrd.war.gameserver.common.enums;

/**
 * 性别
 * @author xf
 */
public enum Sex {
	MALE(0,"男"),
	FEMALE(1,"女"),
	NONE(2,"");
	private int code;
	private String value;
	private Sex(int code, String value){
		this.code = code;
		this.value = value;
	}
	
	
	public static Sex getByCode(int code){
		Sex[] vs = Sex.values();
		for(Sex v : vs){
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
