package com.pwrd.war.common.constants;

/**
 * 公告类型
 * 
 */
public interface NoticeTypes {
	/** 系统公告 */
	short system = 1;
	/** 系统提示(聊天框) */
	short sysChat = 2;
	/** 重要提示(个人提示)*/
	short important = 3;
	/** 普通提示(聊天框)*/
	short common = 4;
	/** 错误提示 */
	short error = 5;
	/** 提示按钮 */
	short button = 6;
	/** 头顶消息 */
	short head= 7;
	/** 特殊消息(显示在头像旁) */
	short special = 8;
	/** 正确提示消息 */
	short right = 9;
	/** 特殊窗口提示消息 */
	short window = 10;
}
