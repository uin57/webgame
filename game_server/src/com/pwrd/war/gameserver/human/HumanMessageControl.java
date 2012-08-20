package com.pwrd.war.gameserver.human;


/**
 * 玩家消息控制
 * 
 * @author yisi.zheng
 * @since 2010-10-12
 */
public class HumanMessageControl {
	private final Human owner;
	private boolean reserverErrMsgSign;
	private String lastErrMsg;

	public HumanMessageControl(Human owner) {
		this.owner = owner;
		reserverErrMsgSign = false;
		lastErrMsg = null;
	}

	/**
	 * 保留出错消息,不予以发送，将保留最后出错信息
	 * 
	 * @param reserverSign
	 */
	public void configErrorMessage(final boolean reserverSign) {
		reserverErrMsgSign = reserverSign;
		// 需要记录信息则清空原有出错消息
		if (reserverErrMsgSign) {
			lastErrMsg = null;
		}
	}

	/**
	 * 是否保留出错信息
	 * 
	 * @param errContent
	 * @return
	 */
	public boolean reserverErrorMessage(String errContent) {
		if (reserverErrMsgSign) {
			lastErrMsg = errContent;
			return true;
		}
		return false;
	}

	/**
	 * 发送保留信息期间记录的最后出错信息
	 */
	public void sendLastErrMsg() {
		if (lastErrMsg != null) {
			owner.sendErrorMessage(lastErrMsg);
			lastErrMsg = null;
		}
	}

	/**
	 * 获得保留信息期间记录的最后出错信息
	 * 
	 * @return
	 */
	public String getLastErrMsg() {
		String _errMsg = lastErrMsg;
		lastErrMsg = null;
		return _errMsg;
	}
}
