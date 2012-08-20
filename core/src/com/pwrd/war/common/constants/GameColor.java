package com.pwrd.war.common.constants;

/**
 * 颜色码定义
 * 
 * 
 */
public enum GameColor {
	/** 白色 */
	WHITE("FFFFFF", 0xFFFFFF),
	/** 绿色 */
	GREEN("01FE0D", 0x01FE0D),
	/** 蓝色 */
	BLUE("1C8BFD", 0x1C8BFD),
	/** 宝石描述的蓝 */
	DIABLUE("4DDFE8", 0x4DDFE8),
	/** 紫色 */
	PURPLE("974EFB", 0x974EFB),
	/** 橙色 */
	ORANGE("F76221", 0xF76221),
	/** 淡黄色 */
	YELLOW("F0E3B0", 0xF0E3B0),
	/** 红色 */
	RED("FF3020", 0xFF3020),
	/** 黑色 */
	BLACK("000000", 0x000000),
	/** 粉红 */
	PINK("F964FB", 0xF964FB),
	/** 灰色 */
	GRAY("828282", 0x828282),

	/* 系统消息的颜色 */
	/** 普通消息颜色 */
	SYSMSG_COMMON("FFFFFF", 0xFFFFFF),
	/** 错误消息颜色 */
	SYSMSG_FAIL("FD4D2B", 0xFD4D2B),
	/** 系统消息颜色 */
	SYSMSG_SYS("FD4D2B", 0xFD4D2B),
	/** 公告消息颜色 */
	SYSMSG_NOTICE("3EE5EF", 0x3EE5EF),
	/** 重要信息提示 */
	SYSMSG_IMPORT("FFFF00",0xFFFF00);

	private final String hexCode;
	private final int rgb;

	private GameColor(final String hexCode, final int rgb) {
		this.hexCode = hexCode;
		this.rgb = rgb;
	}

	/**
	 * 返回RGB的数值
	 * @return
	 */
	public int getRgb() {
		return rgb;
	}

	/**
	 * 返回16进制格式的编码
	 * 
	 * @return
	 */
	public String getHexCode() {
		return hexCode;
	}
}