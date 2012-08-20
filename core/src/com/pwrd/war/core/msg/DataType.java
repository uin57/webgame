package com.pwrd.war.core.msg;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.SimpleByteBufferAllocator;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.orm.BaseEntity;

/**
 * 数据类型定义及其读写方法
 * 
 * 
 */
public class DataType {
	/* 基本数据类型定义 */
	/** int */
	public static final byte IntType = 0;
	/** long */
	public static final byte LongType = 1;
	/** short */
	public static final byte ShorType = 2;
	/** byte */
	public static final byte ByteType = 3;
	/** float */
	public static final byte FloatType = 4;
	/** double */
	public static final byte DoubleType = 5;
	/** string */
	public static final byte StringType = 6;
	/** array */
	public static final byte ArrayType = 7;
	/** entity */
	public static final byte EntityType = 8;
	/** null */
	public static final byte NullType = 9;
	/** Timestamp */
	public static final byte TimestampType = 10;

	/** 实体对象中的byte数组类型：仅用于处理实体对象时做为类型标识，不在本类做为基础类型使用 */
	public static final byte EntityEmbedByteArray = 11;

	/** 实体类型定义,Key:EntityType,Value:short */
	@SuppressWarnings("unchecked")
	private static final Map<Class, EntityType> entityTypes = new HashMap<Class, EntityType>();

	/** 实体类型定义,Key:Short,Value:short */
	@SuppressWarnings("unchecked")
	private final static Map<Short, EntityType> entityNumberTypes = new HashMap<Short, EntityType>();



	/**
	 * 注册实体类型
	 * 
	 * @param type
	 */
	@SuppressWarnings("unchecked")
	public static void registerEntityClass(EntityType type) {
		entityTypes.put(type.entityClass, type);
		entityNumberTypes.put(type.type, type);
	}

	/**
	 * 根据指定的实体类型创建相应的消息协议对象
	 * 
	 * @param entityType
	 *            实体的消息类型,参见{@link EntityMessageType}
	 * @return
	 * @exception IllegalArgumentException
	 *                如果entityType没有在DataType中注册,则会抛出此异常
	 */
	@SuppressWarnings("unchecked")
	public static BaseEntityMsg buildEntityMessage(final short entityType) {
		EntityType _type = entityNumberTypes.get(entityType);
		if (_type != null) {
			return _type.creator.createEntityMessage();
		}
		throw new IllegalArgumentException("Unknown type [" + entityType + "]");
	}

	/**
	 * 根据指定的实体类创建相应的消息协议对象
	 * 
	 * @param entityClass
	 *            实体的类
	 * @return
	 * @exception IllegalArgumentException
	 *                如果entityClass没有在DataType中注册,则会抛出此异常
	 */
	@SuppressWarnings("unchecked")
	public static BaseEntityMsg buildEntityMessage(final Class entityClass) {
		EntityType _type = entityTypes.get(entityClass);
		if (_type != null) {
			return _type.creator.createEntityMessage();
		}
		throw new IllegalArgumentException("Unknown type [" + entityClass + "]");
	}

	/**
	 * 将对象写到buffer中,支持数组,实体对象及基本的数据类型
	 * 
	 * @param data
	 * @param buffer
	 * @return true,写入成功;false,写入失败
	 * @exception IllegalStateException
	 */
	@SuppressWarnings("unchecked")
	public static void writeObjectData(final Object data,
			final ByteBuffer buffer) {
		if (data == null) {
			buffer.put(NullType);
			return;
		}
		final Class<?> _dataClass = data.getClass();
		if (_dataClass.isArray()) {
			final int _arrayLength = Array.getLength(data);
			buffer.put(ArrayType);
			buffer.putShort((short) _arrayLength);
			for (int i = 0; i < _arrayLength; i++) {
				writeObjectData(Array.get(data, i), buffer);
			}
			return;
		} else if (Collection.class.isAssignableFrom(_dataClass)) {
			// 将Collecion对象转换为数组存储
			final Collection _collection = (Collection) (data);
			buffer.put(ArrayType);
			buffer.putShort((short) _collection.size());
			for (Object _data : _collection) {
				writeObjectData(_data, buffer);
			}
			return;
		} else if (BaseEntity.class.isAssignableFrom(_dataClass)) {
			EntityType _entityType = entityTypes.get(_dataClass);
			if (_entityType != null) {
				buffer.put(EntityType);
				BaseEntityMsg _msg = _entityType.creator.createEntityMessage();
				_msg.setEntity((BaseEntity) data);
				_msg.setBuffer(buffer);
				try {
					_msg.write();
				} catch (MessageParseException e) {
					throw new RuntimeException(e);
				}
				return;
			} else {
				throw new IllegalStateException("Unknown entity type:"
						+ _dataClass);
			}
		} else {
			writeRawTypeData(data, buffer);
		}
	}

	@SuppressWarnings("unchecked")
	public static Object readObjectData(final ByteBuffer buffer) {
		final byte _dataType = buffer.get();
		if (_dataType == NullType) {
			return null;
		}
		if (_dataType == ArrayType) {
			final short _arrayLength = buffer.getShort();
			Object[] _arrayObjects = new Object[_arrayLength];
			for (int i = 0; i < _arrayLength; i++) {
				_arrayObjects[i] = readObjectData(buffer);
			}
			return _arrayObjects;
		} else if (_dataType == EntityType) {
			buffer.position(buffer.position() + 2);
			final short _subType = buffer.getShort();
			buffer.position(buffer.position() - 4);
			EntityType _entityType = entityNumberTypes.get(_subType);
			if (_entityType != null) {
				BaseEntityMsg _msg = _entityType.creator.createEntityMessage();
				_msg.initEntity();
				_msg.setBuffer(buffer);
				try {
					_msg.read();
					return _msg.getEntity();
				} catch (MessageParseException e) {
					throw new RuntimeException(e);
				}
			} else {
				throw new IllegalStateException("Unknown type:" + _subType);
			}
		} else {
			buffer.position(buffer.position() - 1);
			return readRawTypeData(buffer);
		}
	}

	/**
	 * 将基本类型的数据data写入到buffer中
	 * 
	 * @param data
	 *            不支持数组
	 * @param buffer
	 * @throws MessageParseException
	 */
	public static void writeRawTypeData(final Object data,
			final ByteBuffer buffer) {
		final Class<?> _dataClass = data.getClass();
		if (_dataClass == Integer.class) {
			buffer.put(IntType);
			buffer.putInt(((Integer) data).intValue());
		} else if (_dataClass == Long.class) {
			buffer.put(LongType);
			buffer.putLong(((Long) data).longValue());
		} else if (_dataClass == Short.class) {
			buffer.put(ShorType);
			buffer.putShort(((Short) data).shortValue());
		} else if (_dataClass == Byte.class) {
			buffer.put(ByteType);
			buffer.put(((Byte) data).byteValue());
		} else if (_dataClass == Float.class) {
			buffer.put(FloatType);
			buffer.putFloat(((Float) data).floatValue());
		} else if (_dataClass == Double.class) {
			buffer.put(DoubleType);
			buffer.putDouble(((Double) data).doubleValue());
		} else if (_dataClass == String.class) {
			buffer.put(StringType);
			writeString((String) data, buffer);
		} else if (_dataClass == Timestamp.class) {
			buffer.put(TimestampType);
			buffer.putLong(((Timestamp) data).getTime());
		} else {
			throw new IllegalArgumentException("Unknown type:" + _dataClass);
		}
	}

	/**
	 * 根据当前buffer的数据,读取数据,不支持数组
	 * 
	 * @param buffer
	 * @return
	 */
	public static Object readRawTypeData(final ByteBuffer buffer) {
		final byte _dataType = buffer.get();
		switch (_dataType) {
		case IntType:
			return buffer.getInt();
		case LongType:
			return buffer.getLong();
		case ShorType:
			return buffer.getShort();
		case ByteType:
			return buffer.get();
		case FloatType:
			return buffer.getFloat();
		case DoubleType:
			return buffer.getDouble();
		case StringType:
			return readString(buffer);
		case TimestampType:
			return new Timestamp(buffer.getLong());
		default:
			throw new IllegalArgumentException("Unsupport data type:"
					+ _dataType);
		}
	}

	/**
	 * 装字符串使用默认编码<code>{@value SharedConstants#DEFAULT_CHARSET}</code>写入到buffer中
	 * 
	 * @param str
	 * @param buffer
	 * @exception RuntimeException
	 *                在编码过程中出现错误会抛出此异常
	 */
	public static void writeString(String str, final ByteBuffer buffer) {
		writeString(str, SharedConstants.DEFAULT_CHARSET, buffer);
	}

	/**
	 * 将字符串使用指定的编码<code>charset</code>写入到buffer中
	 * 
	 * @param str
	 *            将被写入的字符串
	 * @param charset
	 *            字符串的编码
	 * @param buffer
	 * @exception RuntimeException
	 *                在编码过程中出现错误会抛出此异常
	 */
	public static void writeString(String str, String charset,
			final ByteBuffer buffer) {
		try {
			if (str == null) {
				buffer.putShort((short) 0);
				return;
			}
			byte[] bytes = str.getBytes(charset);
			buffer.putShort((short) bytes.length);
			buffer.put(bytes);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用默认编码<code>{@value SharedConstants#DEFAULT_CHARSET}</code>从buffer中读取字符串
	 * 
	 * @param buffer
	 * @return
	 * @exception RuntimeException
	 *                在编码过程中出现错误会抛出此异常
	 */
	public static String readString(final ByteBuffer buffer) {
		return readString(SharedConstants.DEFAULT_CHARSET, buffer);
	}

	/**
	 * 使用指定的编码<code>charset</code>从buffer中读取字符串
	 * 
	 * @param charset
	 * @param buffer
	 * @return
	 * @exception RuntimeException
	 *                在编码过程中出现错误会抛出此异常
	 */
	public static String readString(String charset, final ByteBuffer buffer) {
		short len = buffer.getShort();
		byte[] bytes = new byte[len];
		buffer.get(bytes);
		try {
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private static final SimpleByteBufferAllocator allocator = new SimpleByteBufferAllocator();

	/**
	 * 将对象转换为二进制数据,默认使用512字节初始化ByteBuffer
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] obj2byte(Object object) {
		return obj2byte(object, 512);
	}

	/**
	 * 将对象转换为二进制数据
	 * 
	 * @param object
	 *            将要被转换的数据
	 * @param initLength
	 *            初始长度,用于初始化ByteBuffer的长度
	 * @return
	 */
	public static byte[] obj2byte(Object object, int initLength) {
		if (object == null) {
			return null;
		}
		ByteBuffer _buffer = allocator.allocate(initLength, false);
		_buffer.setAutoExpand(true);
		DataType.writeObjectData(object, _buffer);
		_buffer.flip();
		byte[] _arrayData = _buffer.array();
		byte[] _charInfoData = new byte[_buffer.limit()];
		System.arraycopy(_arrayData, 0, _charInfoData, 0, _charInfoData.length);
		return _charInfoData;
	}

	/**
	 * 将二进制数据转换为对象
	 * 
	 * @param arrayData
	 * @return
	 */
	public static Object byte2obj(byte[] arrayData) {
		if (arrayData == null || arrayData.length == 0) {
			return null;
		}
		java.nio.ByteBuffer _buffer = java.nio.ByteBuffer.wrap(arrayData);
		ByteBuffer _minaBuffer = allocator.wrap(_buffer);
		return DataType.readObjectData(_minaBuffer);
	}

}