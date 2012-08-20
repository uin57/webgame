package com.pwrd.war.core.msg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

import org.apache.mina.common.ByteBuffer;

/**
 * 消息数据压缩工具类
 * 
 * 
 */
public class MessageCompressor {
	/**
	 * 将指定消息的消息体进行压缩,通过调用message的{@link BaseMessage#writeImpl()}
	 * 将消息体的数据写入到字节流中再对该字节流进行压缩
	 * 
	 * @param message
	 * @return 结过压缩的字节数组
	 */
	public static byte[] compressMessageBody(BaseMessage message) {
		ByteBuffer _old = message.getBuffer();
		ByteBuffer _temp = ByteBuffer.allocate(message.getInitBufferLength());
		_temp.setAutoExpand(true);
		message.setBuffer(_temp);
		message.writeImpl();
		_temp.flip();
		message.setBuffer(_old);
		_old = null;

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		bout.write(_temp.array(), 0, _temp.limit());
		_temp.release();

		Deflater compressor = new Deflater();
		compressor.setLevel(Deflater.BEST_SPEED);
		byte[] input = bout.toByteArray();
		bout = null;
		compressor.setInput(input);
		compressor.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[512];
		while (!compressor.finished()) {
			int count = compressor.deflate(buf);
			bos.write(buf, 0, count);
		}
		try {
			bos.close();
		} catch (IOException e) {
		}
		return bos.toByteArray();
	}
}
