package com.pwrd.war.core.util;


/**
 * 基本数组实现的数值属性值对象
 * 继承于通用型，增加了拷贝构造，及给某索引加值，计算和的方法
 * 
 */
public class IntNumberPropertyArray extends GenericPropertyArray<Integer> implements Cloneable {
 

	/**
	 * 创建一个有size个数据的数值属性集合
	 * 
	 * @param size
	 *            数据的个数
	 */
	public IntNumberPropertyArray(int size) {
		super(Integer.class, size);
	}

	/**
	 * 拷贝构造
	 */
	public IntNumberPropertyArray(IntNumberPropertyArray set) {
		super(Integer.class, set.size());
		super.copyFrom(set);
	}
 
 

	/**
	 * 增加index对应的int值
	 * 
	 * @param index
	 *            属性的索引
	 * @param value
	 *            将要增加的值
	 * @return 增加后的值
	 */
	public int add(int index, int value) {
		int _o = super.get(index);
		int _n = _o + value;
		super.set(index, _n);
		return _n;		 
	}
 
 
  

	/**
	 * 清空状态,将values重置为0;把bitSet同时重置
	 */
	public void clear() {
		for (int i = 0; i < values.length; i++) {
			values[i] = 0;
		}
		super.resetChanged();
	}

	/**
	 * 计算该数据对象的所有数值的和
	 * 
	 * @return
	 */
	public int sum() {
		int _sum = 0;
		for (int i = 0; i < this.values.length; i++) {
			_sum += this.values[i];
		}
		return _sum;
	}

	/**
	 * 计算由指定的索引数组标识的属性数值的和
	 * 
	 * @param indexs
	 *            被计算的属性的索引数组
	 * @return
	 */
	public int sum(int[] indexs) {
		int _sum = 0;
		for (int i = 0; i < indexs.length; i++) {
			_sum += this.values[indexs[i]];
		}
		return _sum;
	}

	/**
	 * 计算除了指定的索引数组标识的以外的属性数值的和
	 * 
	 * @param exceptIndexs
	 *            被排除的属性的索引数组
	 * @return
	 */
	public int sumExcept(final int[] exceptIndexs) {
		int _sum = 0;
		for (int i = 0; i < values.length; i++) {
			_sum += this.values[i];
		}
		for (int i = 0; i < exceptIndexs.length; i++) {
			_sum -= this.values[exceptIndexs[i]];
		}
		return _sum;
	}

	public String toString() {
		return StringUtils.obj2String(this, null);
	}

	@Override
	public IntNumberPropertyArray clone() {
		IntNumberPropertyArray newGuy = new IntNumberPropertyArray(this);
		return newGuy;
	}
	

	

}
