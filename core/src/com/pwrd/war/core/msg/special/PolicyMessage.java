package com.pwrd.war.core.msg.special;

import java.io.UnsupportedEncodingException;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.msg.BaseMinaMessage;
import com.pwrd.war.core.msg.MessageParseException;
import com.pwrd.war.core.msg.MessageType;

/**
 * Flash Policy请求
 * 
 * 
 */
@SuppressWarnings("unchecked")
public class PolicyMessage extends BaseMinaMessage {
	private String policy;

	protected boolean readImpl() {
		int times = 20;
		byte b = readByte();
		while (b != 0 && times > 0) {
			b = readByte();
			times--;
		}
		return true;
	}

	public boolean write() throws MessageParseException {
		return writeImpl();
	}

	protected boolean writeImpl() {
		try {
			byte[] bytes = policy.getBytes(SharedConstants.DEFAULT_CHARSET);
			writeBytes(bytes);
			writeByte(0);
			return true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}

	public short getType() {
		return MessageType.FLASH_POLICY;
	}

	public String getTypeName() {
		return "CS_POLICY";
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}
 
	@Override
	public void execute() {
	}

}