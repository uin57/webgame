package com.pwrd.war.core.util;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.ByteBufferAllocator;
import org.apache.mina.common.SimpleByteBufferAllocator;

/**
 * Io相关的工厂类
 * 
 * 
 */
public class IoFactory {
	private static final ByteBufferAllocator allocator = new SimpleByteBufferAllocator();

	/**
	 * 创建指定大的小Mina ByteBuffer,使用SimpleByteBufferAllocator创建,同时将其设置为自动扩展
	 * 
	 * @param capacity
	 * @return
	 */
	public static ByteBuffer allocateByteBuffer(int capacity) {
		return allocator.allocate(capacity, false).setAutoExpand(true);
	}

	/**
	 * 将指定的byte数组包装成为Mina ByteBuffer的实现
	 * 
	 * @param src
	 * @return
	 */
	public static ByteBuffer wrapperByteBuffer(byte[] src) {
		return allocator.wrap(java.nio.ByteBuffer.wrap(src));
	}
}
