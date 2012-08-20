package com.pwrd.war.core.msg.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.mina.common.ByteBuffer;

import com.pwrd.war.core.msg.property.PropertyBag.ValueType;

/**
 * 同类型属性集合，列表结果
 * 支持十种类型：byte, short, int, long, float, double, byte[], string, PropertyBag, PropertyList
 * 创建后无法修改
 * 
 */
public class PropertyList {

	private List<Object> list;		//内部列表结构
	private ValueType type;			//值类型
	
	public PropertyList(List<?> list){
		this.list = new ArrayList<Object>();
		if(list.size() == 0){
			type = ValueType.PNull;	
		}
		else{
			type = ProtocolUtils.getValueTypeByClass(list.get(0).getClass());
			this.list.addAll(list);
		}
	}
	
	public PropertyList(ValueType type, List<?> list){
		this.type = type;
		this.list = new ArrayList<Object>(list.size());
		for(Object obj : list)
			this.list.add(obj);
	}
	
	public PropertyList(ValueType type){
		this.type = type;
		this.list = new ArrayList<Object>();
	}
	
	public ValueType getType(){
		return type;
	}
	
	/**
	 * 返回PropertyBagList序列化为字节码后的总长度
	 * @return
	 */
	public int countSize(){
		/**
		 * type(1)+count(2)+count*v.size
		 */
		int size = 3;
		if(list.size() == 0) return size;
		if(type == ValueType.PropertyBag){
			for(Object value : list){
				size += ((PropertyBag)value).countSize();
			}
		}
		else if(type == ValueType.PropertyList){
			for(Object value : list){
				size += ((PropertyList)value).countSize();
			}
		}
		else{
			size += list.size() * ProtocolUtils.getObjectSize(list.get(0));
		}
		return size;
	}
	
	/**
	 * 获得列表
	 * 拷贝对象，不能修改本身
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(){
		List<T> list = new ArrayList<T>();
		list.addAll((Collection<? extends T>) this.list);
		return list;
	}
	
	/**
	 * 从ByteBuffer解析得到PropertyBag
	 * 
	 * @param buf
	 * @return
	 */
	public static PropertyList readBuffer(ByteBuffer buf){
		ValueType type = PropertyBag.getTypeCode( buf.get());
		short fieldCount = buf.getShort();
		List<Object> list = new ArrayList<Object>();
		for(int i=0; i<fieldCount; i++){
			Object value = ProtocolUtils.readValueFromPkt(buf, type);
			list.add(value);
		}
		PropertyList plist = new PropertyList(type, list);
		return plist;
	}
	
	
	/**
	 * 向ByteBuffer中写入PropertyBagList
	 * @param buf
	 */
	public void writeBuffer(ByteBuffer buf){
		//type
		buf.put((byte)type.ordinal());
		
		//count
		short fieldCount = (short)list.size();
		buf.putShort(fieldCount);
		
		//values
		for(Object value : list){
			ProtocolUtils.putValueIntoPkt(buf, type, value);
		}
	}
	
	public static void main(String[] args){
		PropertyBag bag = new PropertyBag();
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		bag.putList(1, list);
		byte[] bytes = bag.getByties();
		PropertyBag bag2 = PropertyBag.valueOfByties(bytes);
		PropertyList plist = (PropertyList)bag2.getValue(1).getValue();
		List<Integer> list2 = plist.getList();
		list2.clear();
	}
	
	
}
