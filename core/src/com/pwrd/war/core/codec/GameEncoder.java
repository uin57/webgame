package com.pwrd.war.core.codec;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderException;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageParseException;

/**
 * 支持任意长度消息的编写，只要消息支持setBuffer和write方法
 * 
 * 
 */
public class GameEncoder implements ProtocolEncoder {

	public void dispose(IoSession arg0) throws Exception {
	}

	public void encode(IoSession session, Object msg, ProtocolEncoderOutput out)
			throws Exception {
		if (!(msg instanceof IMessage)) {
			throw new ProtocolEncoderException(
					"This encoder only encode mop's IMessage");
		}
		// if (msg instanceof IReusableMessage) {
		// encodeResuableMessage((IReusableMessage) msg, out);
		// } else {
		encodeCommonMessage(msg, out);
		// }
	}

	private void encodeCommonMessage(Object msg, ProtocolEncoderOutput out)
			throws MessageParseException {
		IMessage m = (IMessage) msg;
		ByteBuffer buf = ByteBuffer.allocate(m.getInitBufferLength());
		buf.setAutoExpand(true);
		m.setBuffer(buf);

		boolean success = false;
		try {
			if (m.write()) {
				success = true;
			}
		} finally {
			buf.flip();
			if (success) {
				// 如果成功，保留缓存区，写入out
				if (buf.hasRemaining()) {
					// 统一处理，如果消息太长
					out.write(buf);
					// 因为调用了ProtocolEncoderOutput，因此不需要release
				} else {
					// 这个消息太长了，放弃
					buf.release();
				}
			} else {
				// 写入失败，释放内存，应该记录下来这个问题
				buf.release();
			}
			m.setBuffer(null);
		}
	}

//	private void encodeResuableMessage(IReusableMessage m,
//			ProtocolEncoderOutput ou) throws MessageParseException {
//		if (m.getCloneBuffer() == null) {
//			// 还没有呢,进行初始化
//			ByteBuffer buf = ByteBuffer.allocate(m.getInitBufferLength());
//			buf.setAutoExpand(true);
//			ByteBuffer _preBuf = m.getBuffer();
//			try {
//				m.setBuffer(buf);
//				m.write();
//				buf.flip();
//				m.setCloneBuffer(buf);
//			} finally {
//				m.setBuffer(_preBuf);
//			}
//		}
//		final boolean success = m.getCloneBuffer() != null;
//		if (success) {
//			final byte[] _cloneData = m.getCloneBuffer().array();
//			final int _offset = m.getCloneBuffer().position();
//			final int _length = m.getCloneBuffer().limit() - _offset;
//			ByteBuffer _clone = ByteBuffer.wrap(_cloneData, _offset, _length);
//			// 如果成功，保留缓存区，写入out
//			if (_clone.hasRemaining()) {
//				// 统一处理，如果消息太长
//				ou.write(_clone);
//				// 因为调用了ProtocolEncoderOutput，因此不需要release
//			}
//		}
//	}
}
