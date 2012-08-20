package com.pwrd.war.core.msg.recognizer;

import org.apache.mina.common.ByteBuffer;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageParseException;
import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.core.msg.SysFloodBytesAttack;
import com.pwrd.war.core.msg.SysTestMsgLength;
import com.pwrd.war.core.msg.special.PolicyMessage;
import com.pwrd.war.core.msg.sys.UnknownMessage;

/**
 * 基础的消息识别器
 * 
 * 
 */
public abstract class BaseMessageRecognizer implements IMessageRecognizer {

	@Override
	public int recognizePacketLen(final ByteBuffer buff) {
		// 消息头还未读到,返回null
		if (buff.remaining() < IMessage.MIN_MESSAGE_LENGTH) {
			return -1;
		}
		final int _len = IMessage.Packet.peekPacketLength(buff);
		final short _type = IMessage.Packet.peekPacketType(buff);
		if (_type != MessageType.FLASH_POLICY && buff.remaining() >= _len) {
			return _len;
		}
		return -1;
	}

	/**
	 * 从buf的当前位置读取消息头,并解析消息的类型,如果找到消息的类型,则返回消息实例,否则返回null
	 * 
	 * @param buf
	 * @return 如果buf中数据足够识别出一个消息，并且该消息的数据已经全部到达,则返回对应的消息实例;否则返回null
	 * @throws MessageParseException
	 *             根据消息头的消息类型中无法确定其消息时,会抛出此异常
	 */
	public IMessage recognize(ByteBuffer buf) throws MessageParseException {
		// 消息头还未读到,返回null
		if (buf.remaining() < IMessage.MIN_MESSAGE_LENGTH) {
			return null;
		}
		// 读取前4字节
		try {
			int len = IMessage.Packet.popPacketLength(buf);
			short type = IMessage.Packet.popPacketType(buf);
			if (type == MessageType.FLASH_POLICY) {
				// 处理Flash Policy请求
				boolean finished = false;
				// 找到结束的'\0'标志
				while (buf.remaining() > 0) {
					byte b = buf.get();
					if (b == 0) {
						finished = true;
						break;
					}
				}
				if (!finished) {
					return null;
				}
			} else {
				if (buf.remaining() < len - IMessage.HEADER_SIZE) {
					return null;
				}
			}
			return createMessage(type);
		} catch (IllegalStateException le) {
			throw new MessageParseException(le);
		}
	}

	public IMessage createMessage(short type) throws MessageParseException {
		switch (type) {
		case MessageType.MSG_UNKNOWN: {
			return new UnknownMessage();
		}
		case MessageType.FLASH_POLICY: {
			return new PolicyMessage();
		}
		case MessageType.SYS_TEST_MSG_LENGTH:{
			return new SysTestMsgLength();
		}
		case MessageType.SYS_TEST_FLOOD_BYTE_ATTACK:{
			return new SysFloodBytesAttack();
		}
		default: {
			return createMessageImpl(type);
		}
		}
	}

	/**
	 * 根据type构建消息的实例
	 * 
	 * @param type
	 *            消息的类型
	 * @return 消息实例
	 * @throws MessageParseException
	 *             没有与type相匹配的消息类型时,会抛出此异常
	 */
	public abstract IMessage createMessageImpl(short type)
			throws MessageParseException;
}
