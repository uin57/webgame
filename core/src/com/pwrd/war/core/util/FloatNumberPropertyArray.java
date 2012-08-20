package com.pwrd.war.core.util;


/**
 * 基本数组实现的数值属性值对象
 * 继承于通用型，增加了拷贝构造，及给某索引加值，计算和的方法
 * 
 */
public class FloatNumberPropertyArray extends GenericPropertyArray<Float>  implements Cloneable {
 

	/**
	 * 创建一个有size个数据的数值属性集合
	 * 
	 * @param size
	 *            数据的个数
	 */
	public FloatNumberPropertyArray(int size) {
		super(Float.class,size);
	}

	public FloatNumberPropertyArray(FloatNumberPropertyArray set) {
		super(Float.class, set.size());
		super.copyFrom(set);
	}
 
 

	/**
	 * 增加index对应的值
	 * 
	 * @param index
	 *            属性的索引
	 * @param value
	 *            将要增加的值
	 * @return 增加后的值
	 */
	public float add(int index, float value) {
		float _o = super.get(index);
		if (!MathUtils.floatEquals(0.0f, value)) {
			float _n = _o + value;
			super.set(index, _n);
		}
		return super.get(index);
	}

	 

	/**
	 * 清空状态,将values重置为0;将所有属性都设置为changed
	 */
	public void clear() {
		for (int i = 0; i < values.length; i++) {
			if (!MathUtils.floatEquals(values[i], 0.0f)) {
//				this.bitSet.set(i);
				super.set(i, 0.0f);
			}
			values[i] = 0.0f; 
		} 
	}

	/**
	 * 计算该数据对象的所有数值的和
	 * 
	 * @return
	 */
	public float sum() {
		float _sum = 0;
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
	public float sum(int[] indexs) {
		float _sum = 0;
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
	public float sumExcept(final int[] exceptIndexs) {
		float _sum = 0;
		for (int i = 0; i < values.length; i++) {
			_sum += this.values[i];
		}
		for (int i = 0; i < exceptIndexs.length; i++) {
			_sum -= this.values[exceptIndexs[i]];
		}
		return _sum;
	}

 

	@Override
	public FloatNumberPropertyArray clone() {
		FloatNumberPropertyArray newGuy = new FloatNumberPropertyArray(this);
		return newGuy;
	}
 

}
