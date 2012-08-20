package com.pwrd.war.core.msg.recognizer;

import org.apache.mina.common.ByteBuffer;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageParseException;

/**
 * 消息识别器,用于判断某个数据区属于哪种消息类型
 * 
 * 
 */
public interface IMessageRecognizer {
	/**
	 * 判断buf中的数据是哪种消息类型
	 * 
	 * @param buf
	 *            数据缓存区
	 * @return buf中的数据足够识别一个消息的类型,并且数据已经全部到达时返回识别出的消息实例;
	 *         没有足够的数据识别出一个消息，或者数据还未全部到达时返回null
	 * @throws MessageParseException
	 *             在解析的过程中出理违反协议的情况下，会抛出此异常
	 */
	public IMessage recognize(ByteBuffer buf) throws MessageParseException;
	
	
	/**
	 * 识别buf中消息头的长度
	 * @param buff
	 * @return -1,无法取得消息头的长度;>-1,消息头中的消息长度
	 */
	public int recognizePacketLen(ByteBuffer buff);
}
