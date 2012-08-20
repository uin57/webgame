package com.pwrd.war.core.codec;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderException;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageParseException;
import com.pwrd.war.core.msg.recognizer.IMessageRecognizer;
import com.pwrd.war.core.util.ErrorsUtil;

/**
 * 支持任意尺寸消息，不是通过ByteBuffer的自增长，而是程序根据不足再分配。
 * 
 * 
 */
public class GameDecoder extends ProtocolDecoderAdapter {
	private final static Logger logger = LoggerFactory.getLogger("decoder");
	/** 数据处理缓存* */
	private ByteBuffer readBuf = ByteBuffer.allocate(IMessage.DECODE_MESSAGE_LENGTH);

	/** 消息识别器 * */
	private final IMessageRecognizer recognizer;

	public GameDecoder(IMessageRecognizer recog) {
		if (recog == null) {
			throw new IllegalArgumentException("The recog must not be null.");
		}
		recognizer = recog;
	}

	public void decode(IoSession session, ByteBuffer in, ProtocolDecoderOutput out) throws Exception {
		// 先将最近接收的数据全部放到缓冲中
		put(in);
		try {
			for (;;) {
				IMessage m = null;
				// 开始读,limit = position; position=0
				readBuf.flip();

				final int _len = recognizer.recognizePacketLen(readBuf);
				{
					//第一步,尝试从当前的数据缓存区中识别消息
					final int limit = readBuf.limit();
					final int _position = readBuf.position();
					try {
						// 尝试识别消息
						m = recognizer.recognize(readBuf);
					} catch (MessageParseException eps) {
						// 发生异常,不可识别的命令类型，放弃这次接收到的数据,抛出异常，通知上层收到非法数据，根据情况关闭连接
						if (_len > -1) {
							readBuf.position(_position + _len);
							if (readBuf.hasRemaining()) {
								readBuf.compact();
							} else {
								readBuf.clear();
							}
							eps.setBroken(false);
						} else {
							readBuf.clear();
						}
						throw eps;
					}
					if (m == null) {
						// 可用字节数不足以识别，小于4字节或者不足一条命令回到续写状态,等待数据继续到来
						readBuf.limit(readBuf.capacity());
						readBuf.position(limit);
					} else {
						// 识别出命令,缓冲区恢复到没有开始读取头信息的状态
						readBuf.limit(limit);
						readBuf.position(0);
					}
				}

				if (m == null) {
					break;
				}

				{
					// 第二步,消息的类型已经识别出来,消息数据已经全部到达,开始读取
					boolean _finish = false;
					final int _position = readBuf.position();
					try {
						// 读取完整的信息
						m.setBuffer(readBuf);
						if (m.read()) {
							// 一个完整的m，传递下去处理
							out.write(m);
						} else {
							if (logger.isWarnEnabled()) {
								logger.warn(ErrorsUtil.error(CommonErrorLogInfo.MSG_PRO_ERR_READ_FAIL, "Ignore", m
										.getTypeName()));
							}
							break;
						}
					} catch (MessageParseException e) {
						//读取的时候异常,恢复readBuff
						if (_len > -1) {
							e.setBroken(false);
						}
						throw e;
					} finally {
						if (_len > -1) {
							readBuf.position(_position + _len);
						}
						m.setBuffer(null);
						if (readBuf.hasRemaining()) {
							// 将未处理的数据移动到最前头等待重新处理
							readBuf.compact();
						} else {
							// 处理完毕，缓冲区reset
							readBuf.clear();
							_finish = true;
						}
					}
					if (_finish) {
						break;
					}
				}
			}
		} catch (MessageParseException e) {
			if (logger.isErrorEnabled()) {
				logger.error("Failed to decode Message Type", e);
			}
			throw new ProtocolDecoderException("Failed to decode.", e);
		}
	}

	private void put(ByteBuffer in) {
		// copy to read buffer
		if (in.remaining() > readBuf.remaining())
			expand((readBuf.position() + in.remaining()) * 3 / 2); // 扩大1.5倍，预留空间
		// 新的数据放到最后
		readBuf.put(in.buf());
	}

	private void expand(int newCapacity) {
		ByteBuffer newBuf = ByteBuffer.allocate(newCapacity);
		// 把readBuf中所有未处理的剩余数据拷贝到newBuf的最前面
		readBuf.flip();
		newBuf.put(readBuf);

		readBuf.release();
		readBuf = newBuf;

		if (newCapacity > 1024 * 32) {
			// 如果需要申请超过32K的空间，可能比较危险
			System.err.println(String.format("Warn:GameDecoder.expand(%dK)", newCapacity / 1024));
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (this.readBuf != null) {
			this.readBuf.release();
		}
	}
}
