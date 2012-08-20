package com.pwrd.war.core.msg.property;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.mina.common.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.constants.SharedConstants;


/**
 * 属性集合，<key, value>对，key为整数类型，
 * value支持九种类型：byte, short, int, long, float, double, byte[], string, PropertyBag, PropertyList
 * 用于服务器和客户端之间传输类似struct的负载类型，便于扩展，因为游戏中，经常需要在客户端和服务器之间添加不同的属性
 *  
 */
public class PropertyBag implements Serializable, IPropertyBagable {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(PropertyBag.class);
	
	public static PropertyResolver resolver;
	
	/**
	 * 在解析时，如果遇到为null的PropertyBag，采用这个对象替代
	 */
	public static final PropertyBag EMPTY_PROPERTYBAG = new PropertyBag();
	/**
	 * 在解析时，如果遇到为null的PropertyList，采用这个对象替代
	 */
	public static final PropertyList EMPTY_PROPERTYLIST = new PropertyList(ValueType.PNull);
	/**
	 * 在解析时，如果遇到为null的String，采用这个对象替代
	 */
	public static final String EMPTY_STRING = "";
	/**
	 * 在解析时，如果遇到为null的byte[]，采用这个对象替代
	 */
	public static final byte[] EMPTY_BYTEARRAY = new byte[0];
	
	
	public enum ValueType {
		PInvalid, PNull, PBoolean, PByte, PShort, PInt, PLong, PFloat, PDouble, 
		PByteArray, PString, PropertyBag, PropertyList
	}
	
	public static class Value<T>{
		ValueType type;
		T value; 
		int size;
		
		public Value(ValueType type, T value, int size){
			this.type = type;
			this.value = value;
			this.size = size;
		}
		
		public T getValue(){
			return value;
		}
		
	}
	
	private Map<Integer, Value<?>> valueMap;
	
	public PropertyBag(){
		valueMap = new TreeMap<Integer, Value<?>>();
	}
	
	public Map<Integer, Value<?>> getValueMap(){
		return Collections.unmodifiableMap(valueMap);
	}
	
	public Value<?> getValue(int prop){
		return valueMap.get(prop);
	}

	/**
	 * 向Bag中存放任意类型，如果类型不可识别，则不存放
	 * 
	 * @param property  属性
	 * @param value		属性值
	 */
	public void put(int property, Object value){
		if(value != null){
			Class<?> clazz = value.getClass();
			if(clazz == Boolean.TYPE || clazz == Boolean.class){
				putBoolean(property, (Boolean)value);
			}
			else if(clazz == Byte.TYPE || clazz == Byte.class){
				putByte(property, (Byte)value);
			}
			else if(clazz == Short.TYPE || clazz == Short.class){
				putShort(property, (Short)value);
			}
			else if(clazz == Integer.TYPE || clazz == Integer.class){
				putInt(property, (Integer)value);
			}
			else if(clazz == Long.TYPE || clazz == Long.class){
				putLong(property, (Long)value);
			}
			else if(clazz == Float.TYPE || clazz == Float.class){
				putFloat(property, (Float)value);
			}
			else if(clazz == Double.TYPE || clazz == Double.class){
				putDouble(property, (Double)value);
			}
			else if(clazz == byte[].class){
				putBytes(property, (byte[])value);
			}
			else if(clazz == String.class){
				putString(property, (String)value);
			}
			else if(clazz == PropertyBag.class){
				putPropertyBag(property, (PropertyBag)value);
			}
			else if(clazz == PropertyList.class){
				putPropertyList(property, (PropertyList)value);
			}
		}
		else{
			putNull(property);
		}
	}
	
	public void putNull(int property){
		Value<?> v = new Value<Object>(ValueType.PNull, null, 0);
		valueMap.put(property, v);
	}
	
	public void putBoolean(int property, boolean value){
		Value<?> v = new Value<Boolean>(ValueType.PBoolean, (Boolean)value, 1);
		valueMap.put(property, v);
	}
	
	public void putByte(int property, byte value){
		Value<?> v = new Value<Byte>(ValueType.PByte, (Byte)value, 1);
		valueMap.put(property, v);
	}
	
	public void putShort(int property, short value){
		Value<?> v = new Value<Short>(ValueType.PShort, (Short)value, 2);
		valueMap.put(property, v);
	}
	
	public void putInt(int property, int value){
		Value<?> v = new Value<Integer>(ValueType.PInt, (Integer)value, 4);
		valueMap.put(property, v);
	}
	
	public void putLong(int property, long value){
		Value<?> v = new Value<Long>(ValueType.PLong, (Long)value, 8);
		valueMap.put(property, v);
	}
	
	public void putFloat(int property, float value){
		Value<?> v = new Value<Float>(ValueType.PFloat, (Float)value, 4);
		valueMap.put(property, v);
	}
	
	public void putDouble(int property, double value){
		Value<?> v = new Value<Double>(ValueType.PDouble, (Double)value, 8);
		valueMap.put(property, v);
	}
	
	public void putBytes(int property, byte[] bytes){
		if(bytes == null){
			bytes = EMPTY_BYTEARRAY;
		}
		Value<?> v = new Value<byte[]>(ValueType.PByteArray, bytes, bytes.length+2);
		valueMap.put(property, v);
	}
	
	public void putString(int property, String value){
		if(value == null){
			value = EMPTY_STRING;
		}
		try {
			Value<?> v = new Value<String>(ValueType.PString, value, 
					value.getBytes(SharedConstants.DEFAULT_CHARSET).length+2);
			valueMap.put(property, v);
		} catch (UnsupportedEncodingException e) {
			logger.error("{}", e);
		}
		
	}
	
	public void putPropertyBag(int property, PropertyBag value){
		if(value == null){
			value = EMPTY_PROPERTYBAG;
		}
		Value<?> v = new Value<PropertyBag>(ValueType.PropertyBag, value, value.countSize());
		valueMap.put(property, v);
	}
	
	public void putPropertyList(int property, PropertyList value){
		if(value == null){
			value = EMPTY_PROPERTYLIST;
		}
		Value<?> v = new Value<PropertyList>(ValueType.PropertyList, value, value.countSize());
		valueMap.put(property, v);
	}
	
	public void putList(int property, List<?> value){
		if(value != null){
			PropertyList plist = new PropertyList(value);
			Value<?> v = new Value<PropertyList>(ValueType.PropertyList, plist, plist.countSize());
			valueMap.put(property, v);
		}
		else{
			putNull(property);
		}
	}
	
	
	/**
	 * 返回PropertyBag序列化为字节码后的总长度
	 * @return
	 */
	public int countSize(){
		/**
		 * length(2)+sum[property(4)+type(1)+value(n)]
		 */
		int size = 2;
		for(Value<?> v : valueMap.values()){
			size += (4+1+v.size);
		}
		return size;
	}
	
	/**
	 * 从ByteBuffer解析得到PropertyBag
	 * 
	 * @param buf 采用mina的ByteBuffer，因为这个支持自动增长
	 * @return
	 */
	public static PropertyBag readBuffer(ByteBuffer buf){
		PropertyBag bag = new PropertyBag();
		short fieldCount = buf.getShort();
		for(int i=0; i<fieldCount; i++){
			int property = buf.getInt();
			ValueType type =getTypeCode( buf.get());
			Object val = ProtocolUtils.readValueFromPkt(buf, type);
			bag.put(property, val);
		}
		return bag;
	}

	/**
	 * 向ByteBuffer中写入PropertyBag
	 * @param buf	采用mina的ByteBuffer，因为这个支持自动增长
	 */
	public void writeBuffer(ByteBuffer buf){
		short fieldCount = (short)valueMap.size();
		buf.putShort(fieldCount);
		for(Map.Entry<Integer, Value<?>> entry : valueMap.entrySet()){
			buf.putInt(entry.getKey());
			Value<?> value = entry.getValue();
			buf.put((byte)value.type.ordinal());
			ProtocolUtils.putValueIntoPkt(buf, value.type, value.value);
		}
	}
	
	/**
	 * 字节数组序列化成PropertyBag
	 * @param byties
	 * @return
	 */
	public static PropertyBag valueOfByties(byte[] byties)
	{
		if(byties == null || byties.length < 2)
		{
			return new PropertyBag();
		}
		
		return readBuffer(ByteBuffer.wrap(byties));
		
	}
	
	/**
	 * 转换为字节数组，包括长度
	 * @return
	 */
	public byte[] getByties()
	{
		int size = countSize();
		
		ByteBuffer buffer = ByteBuffer.allocate(size);
		
		writeBuffer(buffer);
		
		buffer.flip();
		
		byte[] ret = new byte[size];
		
		buffer.get(ret);
		
		return ret;
		
	}
	
   public static ValueType getTypeCode(byte opbyte) {
		if ((opbyte < 0) || (opbyte > ValueType.values().length - 1)) {
			logger.error("Unknown op value: {}", opbyte);
			throw new IllegalValueException("Unknown op value: " + opbyte);
		}

		ValueType code = ValueType.values()[opbyte];

		return code;
	}
   
   @Override
   public String toString(){
	   if(resolver == null){
		   return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	   }
	   else{
		   StringBuilder builder = new StringBuilder(100);
		   builder.append("PropertyBag[\r\n");
		   for(Map.Entry<Integer, Value<?>> entry : valueMap.entrySet()){ 
			   builder.append(resolver.getText(entry.getKey())).append("(").append(entry.getKey()).append(")=").append(entry.getValue()).append("\r\n");
		   }
		   builder.append("]");
		   return builder.toString();
	   }
	   
   }

   public void update(PropertyBag bag){
	   union(bag);
   }

   public void union(PropertyBag bag){
		for(Map.Entry<Integer, Value<?>> entry : bag.getValueMap().entrySet()){
			this.put(entry.getKey(), entry.getValue().getValue());
		}
   }
   
   public void reset(){
	   this.valueMap.clear();
   }
   
   public static void main(String[] args){
	 
	   
	   PropertyBag bag = new PropertyBag();
		bag.putByte(0, (byte)1);
		bag.putShort(1, (short)2);
		bag.putInt(2, (int)3);
		bag.putLong(3, (long)4);
		bag.putFloat(4, (float)1.1);
		bag.putDouble(5, (double)2.2);
		bag.putBytes(6, new byte[]{0,1,2,3});
		bag.putString(7, "1234");
		bag.putBoolean(8, true);
		bag.putNull(9);
		
		
		PropertyBag bag2 = new PropertyBag();
		bag2.put(0, (byte)1);
		bag2.put(1, (short)2);
		bag2.put(2, (int)3);
		bag2.put(3, (long)4);
		bag2.put(4, (float)1.1);
		bag2.put(5, (double)2.2);
		bag2.put(6, new byte[]{0,1,2,3});
		bag2.put(7, "1234");
		bag2.put(8, bag);
		bag2.putBoolean(9, true);
		bag2.putNull(10);
		
		
		bag2.put(11, bag);
		
		PropertyBag test = PropertyBag.valueOfByties(bag2.getByties());
		
		System.out.println(test.toString());
   }
	
//	public static void main(String[] args){
//		// 测试PropertyBag基本操作
//		PropertyBag bag = new PropertyBag();
//		bag.putByte(0, (byte)1);
//		bag.putShort(1, (short)2);
//		bag.putInt(2, (int)3);
//		bag.putLong(3, (long)4);
//		bag.putFloat(4, (float)1.1);
//		bag.putDouble(5, (double)2.2);
//		bag.putBytes(6, new byte[]{0,1,2,3});
//		bag.putString(7, "1234");
//		bag.putBoolean(8, true);
//		bag.putNull(9);
//		
//		PropertyBag bag2 = new PropertyBag();
//		bag2.put(0, (byte)1);
//		bag2.put(1, (short)2);
//		bag2.put(2, (int)3);
//		bag2.put(3, (long)4);
//		bag2.put(4, (float)1.1);
//		bag2.put(5, (double)2.2);
//		bag2.put(6, new byte[]{0,1,2,3});
//		bag2.put(7, "1234");
//		bag2.put(8, bag);
//		bag2.putBoolean(9, true);
//		bag2.putNull(10);
//		
//		System.out.println(bag);
//		System.out.println(bag2);
//		
//		
//		// 测试与ByteBuffer的转化
//		ByteBuffer buffer = ByteBuffer.allocate(10).order(SharedConstants.BYTE_ORDER);
//		buffer.setAutoExpand(true);
//		
//		bag2.writeBuffer(buffer);
//		
//		buffer.flip();
//		
//		PropertyBag bag3 = PropertyBag.readBuffer(buffer);
//		System.out.println(bag3);
//	}

	public PropertyBag toPropertyBag() {
		// TODO Auto-generated method stub
		return this;
	}

}
