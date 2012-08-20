package com.pwrd.war.core.msg;

import org.apache.mina.common.ByteBuffer;

/**
 * 采取类似Google Protocol Buffer的VarInt的算法
 * 
 * 
 */
public class CodeVarInt {

	public static final class WireFormat {
		// Do not allow instantiation.
		private WireFormat() {
		}

		static final int TAG_TYPE_BITS = 4;
		static final int TAG_TYPE_MASK = (1 << TAG_TYPE_BITS) - 1;

		/** Given a tag value, determines the wire type (the lower 4 bits). */
		static int getTagWireType(final int tag) {
			return tag & TAG_TYPE_MASK;
		}

		/** Given a tag value, determines the field number (the upper 28 bits). */
		public static int getTagFieldNumber(final int tag) {
			return tag >>> TAG_TYPE_BITS;
		}

		/** Makes a tag value given a field number and wire type. */
		static int makeTag(final int fieldNumber, final int wireType) {
			return (fieldNumber << TAG_TYPE_BITS) | wireType;
		}
	}

	/** Encode and write a tag. */
	public static void writeTag(final int fieldNumber, final int wireType, final ByteBuffer buffer) {
		writeRawVarint32(WireFormat.makeTag(fieldNumber, wireType), buffer);
	}

	public static int readTag(ByteBuffer buffer) {
		return readRawVarint32(buffer);
	}

	public static void writeRawVarint32(int value, ByteBuffer buffer) {
		while (true) {
			if ((value & ~0x7F) == 0) {
				buffer.put((byte) value);
				return;
			} else {
				buffer.put((byte) ((value & 0x7F) | 0x80));
				value >>>= 7;
			}
		}
	}

	/**
	 * Read a raw Varint from the stream. If larger than 32 bits, discard the
	 * upper bits.
	 */
	public static int readRawVarint32(ByteBuffer buffer) {
		byte tmp = buffer.get();
		if (tmp >= 0) {
			return tmp;
		}
		int result = tmp & 0x7f;
		if ((tmp = buffer.get()) >= 0) {
			result |= tmp << 7;
		} else {
			result |= (tmp & 0x7f) << 7;
			if ((tmp = buffer.get()) >= 0) {
				result |= tmp << 14;
			} else {
				result |= (tmp & 0x7f) << 14;
				if ((tmp = buffer.get()) >= 0) {
					result |= tmp << 21;
				} else {
					result |= (tmp & 0x7f) << 21;
					result |= (tmp = buffer.get()) << 28;
					if (tmp < 0) {
						// Discard upper 32 bits.
						for (int i = 0; i < 5; i++) {
							if (buffer.get() >= 0) {
								return result;
							}
						}
						throw new IllegalArgumentException("encountered a malformed varint");
					}
				}
			}
		}
		return result;
	}

	/** Read a raw Varint from the stream. */
	public static long readRawVarint64(final ByteBuffer buffer) {
		int shift = 0;
		long result = 0;
		while (shift < 64) {
			final byte b = buffer.get();
			result |= (long) (b & 0x7F) << shift;
			if ((b & 0x80) == 0) {
				return result;
			}
			shift += 7;
		}
		throw new IllegalArgumentException("encountered a malformed varint");
	}

	/**
	 * Compute the number of bytes that would be needed to encode a varint.
	 * {@code value} is treated as unsigned, so it won't be sign-extended if
	 * negative.
	 */
	public static int computeRawVarint32Size(final int value) {
		if ((value & (0xffffffff << 7)) == 0)
			return 1;
		if ((value & (0xffffffff << 14)) == 0)
			return 2;
		if ((value & (0xffffffff << 21)) == 0)
			return 3;
		if ((value & (0xffffffff << 28)) == 0)
			return 4;
		return 5;
	}

	/** Encode and write a varint. */
	public static void writeRawVarint64(long value, ByteBuffer buffer) {
		while (true) {
			if ((value & ~0x7FL) == 0) {
				buffer.put((byte) value);
				return;
			} else {
				buffer.put((byte) ((value & 0x7F) | 0x80));
				value >>>= 7;
			}
		}
	}

	/** Compute the number of bytes that would be needed to encode a varint. */
	public static int computeRawVarint64Size(final long value) {
		if ((value & (0xffffffffffffffffL << 7)) == 0)
			return 1;
		if ((value & (0xffffffffffffffffL << 14)) == 0)
			return 2;
		if ((value & (0xffffffffffffffffL << 21)) == 0)
			return 3;
		if ((value & (0xffffffffffffffffL << 28)) == 0)
			return 4;
		if ((value & (0xffffffffffffffffL << 35)) == 0)
			return 5;
		if ((value & (0xffffffffffffffffL << 42)) == 0)
			return 6;
		if ((value & (0xffffffffffffffffL << 49)) == 0)
			return 7;
		if ((value & (0xffffffffffffffffL << 56)) == 0)
			return 8;
		if ((value & (0xffffffffffffffffL << 63)) == 0)
			return 9;
		return 10;
	}
}
