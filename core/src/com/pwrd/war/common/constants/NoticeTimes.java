/**
 * 
 */
package com.pwrd.war.common.constants;

/**
 * 每个提示消息对应的的显示时长
 * @author dendgan
 *
 */
public interface NoticeTimes {

	/** 系统公告显示时长 */
	short NOTICE_TIME = 6;
	/** 重要提示显示时长 */
	short PROMPT_TIME = 3;
	/** 错误提示显示时长 */
	short ERROR_TIME = 3;
	/** 提示按钮显示时长:0为一直显示*/
	short BUTTON_TIME = 0;
	/** 头顶消息显示时长 */
	short HEAD_TIME = 2;
	/** 特殊消息显示时长 */
	short SPECIAL_TIME = 2;
}
