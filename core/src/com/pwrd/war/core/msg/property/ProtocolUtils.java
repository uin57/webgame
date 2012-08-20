package com.pwrd.war.core.msg.property;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.mina.common.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.msg.property.PropertyBag.ValueType;

public class ProtocolUtils {
	private static final Logger logger = LoggerFactory.getLogger(ProtocolUtils.class);
	
	static void putValueIntoPkt(ByteBuffer packet, ValueType type, Object value){
		switch (type) {
		case PNull:
			break;
		case PBoolean:
			boolean b = (Boolean)value;
			packet.put((byte)(b?1:0));
			break;
		case PByte:
			packet.put((Byte) value);
			break;
		case PShort:
			packet.putShort((Short) value);
			break;
		case PInt:
			packet.putInt((Integer) value);
			break;
		case PLong:
			packet.putLong((Long) value);
			break;
		case PFloat:
			packet.putFloat((Float) value);
			break;
		case PDouble:
			packet.putDouble((Double) value);
			break;
		case PString:{
			byte[] argBytes = new byte[0];
			try {
				argBytes = ((String)value).getBytes(SharedConstants.DEFAULT_CHARSET);
			} catch (UnsupportedEncodingException e) {
				logger.error("{}", e);
			}
//			boolean dump = false;
//			if(dump){
//				String str = HexBin.encode(argBytes);
//				int a = 10;
//			}
			packet.putShort((short)argBytes.length);
			packet.put(argBytes);
			break;
		}
		case PByteArray:{
			byte[] argBytes = (byte[]) value;
			packet.putShort((short) argBytes.length);
			packet.put(argBytes);
			break;
		}
		case PropertyBag:{
			PropertyBag bag = (PropertyBag)value;
			bag.writeBuffer(packet);
			break;
		}
		case PropertyList:{
			PropertyList list = (PropertyList)value;
			list.writeBuffer(packet);
			break;
		}
		}
	}
	
	static Object readValueFromPkt(ByteBuffer packet, ValueType type){
		Object value=null;
		switch (type) {
		case PNull:
			value = null;
			break;
		case PBoolean:
			value = packet.get()==1?true:false;
			break;
		case PByte:
			value=packet.get();
			break;
		case PShort:
			value=packet.getShort();
			break;
		case PInt:
			value=packet.getInt();
			break;
		case PLong:
			value=packet.getLong();
			break;
		case PFloat:
			value=packet.getFloat();
			break;
		case PDouble:
			value=packet.getDouble();
			break;
		case PString:{
			short len=packet.getShort();
			byte[] bytes=new byte[len];
	        packet.get(bytes);
	        try {
				value=new String(bytes, SharedConstants.DEFAULT_CHARSET);
			} catch (UnsupportedEncodingException e) {
				logger.error("{}", e);
			}
			break;
		}
		case PByteArray:{
			short len=packet.getShort();
			byte[] bytes=new byte[len];
	        packet.get(bytes);
	        value=bytes;
			break;
		}
		case PropertyBag:{
			value = PropertyBag.readBuffer(packet);
			break;
		}
		case PropertyList:{
			value = PropertyList.readBuffer(packet);
			break;
		}
		}
		return value;
	}
	

	public static int getObjectSize(Object obj){
		if(obj == null){
			return 0;
		}
		else{
			Class<?> clazz = obj.getClass();
			if(clazz == Boolean.TYPE || clazz == Boolean.class){
				return 1;
			}
			else if(clazz == Byte.TYPE || clazz == Byte.class){
				return 1;
			}
			else if(clazz == Short.TYPE || clazz == Short.class){
				return 2;
			}
			else if(clazz == Integer.TYPE || clazz == Integer.class){
				return 4;
			}
			else if(clazz == Long.TYPE || clazz == Long.class){
				return 8;
			}
			else if(clazz == Float.TYPE || clazz == Float.class){
				return 4;
			}
			else if(clazz == Double.TYPE || clazz == Double.class){
				return 8;
			}
			else if(clazz == byte[].class){
				return ((byte[])obj).length+2;
			}
			else if(clazz == String.class){
				try {
					return ((String)obj).getBytes(SharedConstants.DEFAULT_CHARSET).length+2;
				} catch (UnsupportedEncodingException e) {
					logger.error("", e);
					return 2;
				}
			}
			else if(clazz == PropertyBag.class){
				return ((PropertyBag)obj).countSize();
			}
			else if(clazz == PropertyList.class){
				return ((PropertyList)obj).countSize();
			}
			else{
				return 0;
			}

		}
	}
	
	public static ValueType getValueTypeByClass(Class<?> clazz){
		ValueType type = ValueType.PInvalid; 
		if(clazz == Boolean.TYPE || clazz == Boolean.class){
			type = ValueType.PBoolean;
		}
		else if(clazz == Byte.TYPE || clazz == Byte.class){
			type = ValueType.PByte;
		}
		else if(clazz == Short.TYPE || clazz == Short.class){
			type = ValueType.PShort;
		}
		else if(clazz == Integer.TYPE || clazz == Integer.class){
			type = ValueType.PInt;
		}
		else if(clazz == Long.TYPE || clazz == Long.class){
			type = ValueType.PLong;
		}
		else if(clazz == Float.TYPE || clazz == Float.class){
			type = ValueType.PFloat;
		}
		else if(clazz == Double.TYPE || clazz == Double.class){
			type = ValueType.PDouble;
		}
		else if(clazz == byte[].class){
			type = ValueType.PByteArray;
		}
		else if(clazz == String.class){
			type = ValueType.PString;
		}
		else if(clazz == PropertyBag.class){
			type = ValueType.PropertyBag;
		}
		else if(ProtocolUtils.isListType(clazz)){
			type = ValueType.PropertyList;
		}
		return type;
	}
	
	public static boolean isListType(Class<?> clazz){
		Class<?> listClass = List.class;
		if(clazz == listClass){
			return true;
		}
		else{
			Class<?>[] interfaces = clazz.getInterfaces();
			for(Class<?> interf: interfaces){
				if(interf == listClass){
					return true;
				}
			}
			
		}
		return false;
	}
	
	public static void main(String[] args){
		// 如何判断List的类型
		
		List<?> a = new ArrayList<Integer>();
		Class<?> clazz = a.getClass();
		System.out.println(isListType(clazz));
		
		System.out.println(Arrays.toString(clazz.getTypeParameters()));
		
		int[] c = new int[10];
		clazz = c.getClass();
		System.out.println(clazz == int[].class);
		System.out.println(clazz == Integer[].class);
		System.out.println(c.getClass());
		
	}
}
