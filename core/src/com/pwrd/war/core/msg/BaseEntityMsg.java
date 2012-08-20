package com.pwrd.war.core.msg;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 消息实体的消息基类
 * 
 */
public abstract class BaseEntityMsg<T extends BaseEntity<?>> extends BaseMessage {

	/**
	 * 取得该消息所对应的实体类型
	 * 
	 * @return
	 */
	public abstract Class<?> getEntityClass();

	/**
	 * 取得该消息读取后的实体对象
	 * 
	 * @return
	 */
	public abstract T getEntity();

	/**
	 * 取得该消息写入前的实体对象
	 * 
	 * @param entity
	 */
	public abstract void setEntity(T entity);

	/**
	 * 在没有设置Entity的情况下,创建一个实体
	 * 
	 * @exception IllegalStateException
	 *                如果已经有了Entity,或者重复调用该方法会抛出此异常
	 */
	public abstract void initEntity();

	@Override
	protected final boolean readImpl() {
		final int _limit = this.getLength() + this.buf.position() - HEADER_SIZE;
		while (this.buf.position() < _limit) {
			int _tag = CodeVarInt.readTag(this.buf);
			final byte _sequence = (byte) CodeVarInt.WireFormat
					.getTagFieldNumber(_tag);
			final byte _type = (byte) CodeVarInt.WireFormat
					.getTagWireType(_tag);
			if (!_read(_sequence)) {
				// 读取错误,跳过这些数据
				switch (_type) {
				case DataType.ByteType:
					this._readByte();
					break;
				case DataType.IntType:
					this._readInt();
					break;
				case DataType.ShorType:
					this._readShort();
					break;
				case DataType.FloatType:
					this._readFloat();
					break;
				case DataType.DoubleType:
					this._readDouble();
					break;
				case DataType.LongType:
					this._readLong();
					break;
				case DataType.StringType:
					this._readString();
					break;
				case DataType.EntityEmbedByteArray:
					this._readByteArray();
				default:
					throw new IllegalArgumentException("Unsupported type:"
							+ _type);
				}
			}
		}
		return true;
	}

	@Override
	protected final boolean writeImpl() {
		return _write();
	}

	/**
	 * 根据指定的顺序读取数据
	 * 
	 * @param sequence
	 * @return true,读取成功;false,读取失败
	 */
	protected abstract boolean _read(final byte sequence);

	/**
	 * 调用以_write开头的方法进行写操作
	 * 
	 * @return true,写入成功;falkse,写失败
	 */
	protected abstract boolean _write();

	protected final void _writeInt(final int fieldNumber, final int value) {
		CodeVarInt.writeTag(fieldNumber, DataType.IntType, this.buf);
		CodeVarInt.writeRawVarint32(value, this.buf);
	}

	protected final int _readInt() {
		return CodeVarInt.readRawVarint32(this.buf);
	}

	protected final void _writeShort(final int fieldNumber, final short value) {
		CodeVarInt.writeTag(fieldNumber, DataType.ShorType, this.buf);
		CodeVarInt.writeRawVarint32(value, buf);
	}

	protected final short _readShort() {
		return (short) CodeVarInt.readRawVarint32(buf);
	}

	protected final void _writeLong(int fieldNumber, long value) {
		CodeVarInt.writeTag(fieldNumber, DataType.LongType, this.buf);
		CodeVarInt.writeRawVarint64(value, this.buf);
	}

	protected final long _readLong() {
		return CodeVarInt.readRawVarint64(this.buf);
	}

	protected final void _writeByte(int fieldNumber, byte value) {
		CodeVarInt.writeTag(fieldNumber, DataType.ByteType, this.buf);
		this.writeByte(value);
	}

	protected final byte _readByte() {
		return this.readByte();
	}

	protected final void _writeFloat(int fieldNumber, float value) {
		CodeVarInt.writeTag(fieldNumber, DataType.FloatType, this.buf);
		this.writeFloat(value);
	}

	protected final float _readFloat() {
		return this.readFloat();
	}

	protected final void _writeDouble(int fieldNumber, double value) {
		CodeVarInt.writeTag(fieldNumber, DataType.DoubleType, this.buf);
		this.writeDouble(value);
	}

	protected final double _readDouble() {
		return this.readDouble();
	}

	protected final void _writeString(int fieldNumber, String value) {
		CodeVarInt.writeTag(fieldNumber, DataType.StringType, this.buf);
		this.writeString(value);
	}

	protected final String _readString() {
		return this.readString();
	}

	protected final byte[] _readByteArray() {
		return this.readByteArray();
	}

	protected final void _writeByteArray(int fieldNumber, byte[] datas) {
		CodeVarInt.writeTag(fieldNumber, DataType.EntityEmbedByteArray,
				this.buf);
		this.writeByteArray(datas);
	}

	@Override
	public void execute() {
	}
}
