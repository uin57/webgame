package com.pwrd.war.gameserver.common.i18n;


/**
 * 事物名字相关信息，可能包括所在地图id，坐标等
 * 
 */
public class NameInfo {
	/** 事物Id */
	private int id;
	/** 事物名字 */
	private String name;
	/** 事物显示颜色值 */
	private String color;

	public NameInfo(int id, String name, String color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "NameInfo [id=" + id + ", name=" + name + ", color=" + color + "]";
	}

}
