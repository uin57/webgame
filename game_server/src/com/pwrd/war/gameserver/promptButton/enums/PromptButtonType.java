/**
 * 
 */
package com.pwrd.war.gameserver.promptButton.enums;


/**
 * @author 提示按钮图标类型
 *
 */
public enum PromptButtonType {

	/** 信封消息 */
	XINFENGXIAOXI(1),
	/** 战报 */
	ZHANBAO(2);

	private int value;

	PromptButtonType(int value) {
		this.value = value;
	}

	public static PromptButtonType getPromptButtonType(int value) {
		for (PromptButtonType promptButtonType : PromptButtonType.values()) {
			if (promptButtonType.getValue() == value) {
				return promptButtonType;
			}
		}
		return null;
	}

	public int getValue() {
		return value;
	}
}
