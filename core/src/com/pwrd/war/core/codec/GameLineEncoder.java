package com.pwrd.war.core.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class GameLineEncoder extends ProtocolEncoderAdapter {

	private static final String ENCODER = GameLineEncoder.class.getName()
			+ ".encoder";

	private final Charset charset;
	// private final LineDelimiter delimiter;
	private int maxLineLength = Integer.MAX_VALUE;

	public GameLineEncoder() {
		this(Charset.defaultCharset());
	}

	public GameLineEncoder(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		this.charset = charset;
	}

	public int getMaxLineLength() {
		return maxLineLength;
	}

	public void setMaxLineLength(int maxLineLength) {
		if (maxLineLength <= 0) {
			throw new IllegalArgumentException("maxLineLength: "
					+ maxLineLength);
		}

		this.maxLineLength = maxLineLength;
	}

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		CharsetEncoder encoder = (CharsetEncoder) session.getAttribute(ENCODER);
		if (encoder == null) {
			encoder = charset.newEncoder();
			session.setAttribute(ENCODER, encoder);
		}

		String value = message.toString();
		ByteBuffer buf = ByteBuffer.allocate(value.length())
				.setAutoExpand(true);
		buf.putString(value, encoder);
		if (buf.position() > maxLineLength) {
			throw new IllegalArgumentException("Line length: " + buf.position());
		}
		buf.put((byte) 0);
		buf.flip();
		out.write(buf);
	}

}
