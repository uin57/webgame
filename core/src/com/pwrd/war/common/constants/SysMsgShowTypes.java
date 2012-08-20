package com.pwrd.war.common.constants;

/**
 * 系统消息显示类型
 * 
 */
public interface SysMsgShowTypes {
	/** 普通消息类 */
	short generic = 1;
	/** 重要消息类 */
	short important = 2;
	/** 操作错误提示,直接冒出提示 */
	short error = 3;
	/** 弹框提示 */
	short box = 5;
}
