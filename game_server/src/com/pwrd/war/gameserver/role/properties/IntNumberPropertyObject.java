package com.pwrd.war.gameserver.role.properties;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.core.util.IntNumberPropertyArray;
import com.pwrd.war.core.util.KeyValuePair;


/**
 * 整数数值属性对象
 * 继承于基本整数型，增加了可修改标志，及属性类型标志
 *
 */
public class IntNumberPropertyObject extends IntNumberPropertyArray  {
	
//	protected final IntNumberPropertyArray props;
	protected final PropertyType propertyType;

	/** 是否可以修改 */
	private boolean isReadOnly = false;

	/**
	 * 属性的个数
	 * 
	 * @param size
	 */
	public IntNumberPropertyObject(int size, PropertyType propertyType) {
		super(size);
		this.propertyType = propertyType;
	}

	/**
	 * 从指定的参数中拷贝数据到本身
	 * 
	 * @param src
	 * @exception IllegalArgumentException
	 *                如果src的对象类型与该类型不一致
	 * @exception IllegalStateException
	 *                如果该对象处于只读状态
	 */
	public final void copyFrom(IntNumberPropertyObject src) {
		if (src.propertyType != this.propertyType) {
			throw new IllegalArgumentException("Not the same property type.");
		}
		if (!isReadOnly) {
			super.copyFrom(src);
		} else {
			throw new IllegalStateException("Read only");
		}
	}

	/**
	 * 已废弃，因为copyFrom可以完成该功能
	 * 向指定的参数拷贝数据
	 * 
	 * @param target
	 * @exception IllegalArgumentException
	 *                如果target的对象类型与该类型不一致
	 */
	@Deprecated()
	public final void copyTo(IntNumberPropertyObject target) {
		if (target.propertyType != this.propertyType) {
			throw new IllegalArgumentException("Not the same property type.");
		}
		target.copyFrom(this);
	}

	/**
	 * 将本对象的指定的属性拷贝对目标对象中
	 * 
	 * @param target
	 *            目标对象
	 * @param props
	 *            属性索引
	 */
	@Deprecated
	public final void copyTo(IntNumberPropertyObject target, int[] props) {
		for (int i = 0; i < props.length; i++) {
			target.set(props[i], this.get(props[i]));
		}
	}
	
	/**
	 * 将指定参数中的数据加到本身对应索引中
	 * 
	 * @param src
	 * @exception IllegalArgumentException
	 *                如果src的对象类型与该类型不一致
	 * @exception IllegalStateException
	 *                如果该对象处于只读状态
	 */
	public void add(IntNumberPropertyObject src) {
		addBySign(src, 1);
	}

	/**
	 * 从本身减去将指定参数中的数据
	 * 
	 * @param src
	 * @exception IllegalArgumentException
	 *                如果src的对象类型与该类型不一致
	 * @exception IllegalStateException
	 *                如果该对象处于只读状态
	 */
	public void dec(IntNumberPropertyObject src) {
		addBySign(src, -1);
	}

	/**
	 * 为本身加上或减去将指定参数中的数据
	 * 
	 * @param src
	 * @param sign
	 *            1 or -1 (加/减)
	 * @exception IllegalArgumentException
	 *                如果src的对象类型与该类型不一致
	 * @exception IllegalStateException
	 *                如果该对象处于只读状态
	 */
	private void addBySign(IntNumberPropertyObject src, int sign) {
		if (src.propertyType != this.propertyType) {
			throw new IllegalArgumentException("Not the same property type.");
		}
		if (!isReadOnly) {
			for (int i = 0; i < size(); i++) {
				add(i, sign * src.get(i));
			}
		} else {
			throw new IllegalStateException("Read only");
		}
	}
 
  

	/**
	 * 设定指定索引的int值
	 * 
	 * @param index
	 *            属性索引
	 * @param value
	 *            新值
	 * @return true,值被修改;fase,值未被修改
	 * @exception IllegalStateException
	 *                如果该对象处于只读状态
	 */
	public final boolean set(int index, Integer value) {
		if (!isReadOnly) {
			value = value == null ?0:value;
			return super.set(index, value);
		} else {
			throw new IllegalStateException("Read only");
		}
	}

	/**
	 * 清空所有的属性,将所有的属性设置为0
	 * 
	 * @exception IllegalStateException
	 *                如果该对象处于只读状态
	 */
	public void clear() {
		if (!isReadOnly) {
			super.clear();
		} else {
			throw new IllegalStateException("Read only");
		}
	}

	/**
	 * 将指定索引<tt>index</tt>的属性值加<tt>value</tt>
	 * 
	 * @param index
	 * @param value
	 * @return 返回相加后的结果
	 */
	public final int add(int index, int value) {
		if (!isReadOnly) {
			return super.add(index, value);
		} else {
			throw new IllegalStateException("Read only");
		}
	}
 

	/**
	 * 获取该对象的类型
	 * 
	 * @return
	 */
	public final PropertyType getPropertyType() {
		return propertyType;
	}
 

	/**
	 * 将该数值对象设置为只读状态,从调用此方法的一刻起,该数值对象的值将不能够再被修改,适用于对象的值一旦设置完成后,而且以后不会再进行修改
	 */
	public void readOnly() {
		this.isReadOnly = true;
	}
 
	/* 
	 * 取得已经变化的，同时会根据属性类型，自动修正其值
	 */
	public KeyValuePair<Integer, Integer>[] getChanged() {
		KeyValuePair<Integer, Integer>[] changes= super.getChanged();
		for(int i=0;i<changes.length;i++)
		{			
			changes[i].setKey(propertyType.genPropertyKey(changes[i].getKey())); 
		}
		return changes;
	}
	/* 
	 * 取得所有的值，去除哪些为null的，因为这里设置的不可能为null，null也是0
	 */
	public KeyValuePair<Integer, Integer>[] getIndexValuePairs() {
		//去除null的
		KeyValuePair<Integer, Integer>[] indexValuePairs = super.getIndexValuePairs();
		List<KeyValuePair<Integer, Integer>> list = new ArrayList<KeyValuePair<Integer, Integer>>();
		for(int i = 0; i< indexValuePairs.length; i++){
			if(values[i] == null)continue;
			list.add(new KeyValuePair<Integer, Integer>(propertyType.genPropertyKey(i),
					values[i]));
		}
		indexValuePairs = KeyValuePair.newKeyValuePairArray(list.size());		
		list.toArray(indexValuePairs);
		return indexValuePairs;
	}

	@Override
	public Integer get(int index) { 
		Integer i= super.get(index);
		if(i == null)return 0;
		return i;
	}
 
}
