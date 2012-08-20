package com.pwrd.war.gameserver.role.properties;

import com.pwrd.war.core.util.FloatNumberPropertyArray;
import com.pwrd.war.core.util.KeyValuePair;
import com.pwrd.war.core.util.MathUtils;

/**
 * 浮点类型的数值属性对象
 * 
 * @author <a href="mailto:dongyong.wang@opi-corp.com">wang dong yong<a>
 * 
 */
public class FloatNumberPropertyObject {
	protected final FloatNumberPropertyArray props;
	protected final PropertyType propertyType;

	/** 是否可以修改 */
	private boolean isReadOnly = false;

	/**
	 * 属性的个数
	 * 
	 * @param size
	 */
	public FloatNumberPropertyObject(int size, PropertyType propertyType) {
		this.props = new FloatNumberPropertyArray(size);
		this.propertyType = propertyType;
	}

	/**
	 * 从指定的参数中拷贝数据到本身<br>
	 * 注：此方法change全部的Bit位，如果需要精确比较，请使用
	 * {@link #copyFromAndCompare(FloatNumberPropertyObject)}
	 * 
	 * @param src
	 * @exception IllegalArgumentException
	 *                如果src的对象类型与该类型不一致
	 * @exception IllegalStateException
	 *                如果该对象处于只读状态
	 */
	public void copyFrom(FloatNumberPropertyObject src) {
		if (src.propertyType != this.propertyType) {
			throw new IllegalArgumentException("Not the same property type.");
		}
		if (!isReadOnly) {
			this.props.copyFrom(src.props);
		} else {
			throw new IllegalStateException("Read only");
		}
	}

	/**
	 * 向指定的参数拷贝数据
	 * 
	 * @param target
	 * @exception IllegalArgumentException
	 *                如果target的对象类型与该类型不一致
	 */
	public void copyTo(FloatNumberPropertyObject target) {
		if (target.propertyType != this.propertyType) {
			throw new IllegalArgumentException("Not the same property type.");
		}
		target.props.copyFrom(this.props);
	}

	/**
	 * 将本对象的指定的属性拷贝对目标对象中
	 * 
	 * @param target
	 *            目标对象
	 * @param props
	 *            属性索引
	 */
	public void copyTo(FloatNumberPropertyObject target, int[] props) {
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
	public void add(FloatNumberPropertyObject src) {
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
	public void dec(FloatNumberPropertyObject src) {
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
	private void addBySign(FloatNumberPropertyObject src, int sign) {
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
	 * 是否有修改
	 * 
	 * @return
	 */
	public boolean isChanged() {
		return props.isChanged();
	}
	
	/**
	 * 判定指定位是否修改
	 * 
	 * @param index
	 * @return
	 */
	public boolean isChanged(int index){
		return props.isChanged(index);
	}

	/**
	 * 被修改过的属性索引及其值
	 * 
	 * @return
	 */
	public KeyValuePair<Integer, Float>[] getChanged() {
		return props.getChanged();
	}

	/**
	 * 属性的个数
	 * 
	 * @return
	 */
	public final int size() {
		return props.size();
	}

	/**
	 * 取得指定索引的float值
	 * 
	 * @param index
	 *            属性索引
	 * @return
	 */
	public final float get(int index) {
		return props.get(index);
	}

	/**
	 * 取得指定索引的int值（四舍五入的int值）
	 * 
	 * @param index
	 *            属性索引
	 * @return
	 */
	public final int getAsInt(int index) {
		return MathUtils.float2Int(props.get(index));
	}

	/**
	 * 设定指定索引的float值
	 * 
	 * @param index
	 *            属性索引
	 * @param value
	 *            新值
	 * @return true,值被修改;false,值未修改
	 * @exception IllegalStateException
	 *                如果该对象处于只读状态
	 */
	public final boolean set(int index, float value) {
		if (!isReadOnly) {
			return props.set(index, value);
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
			this.props.clear();
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
	public final float add(int index, float value) {
		if (!isReadOnly) {
			return props.add(index, value);
		} else {
			throw new IllegalStateException("Read only");
		}
	}

	/**
	 * 将修改标志重新置位
	 */
	public final void resetChanged() {
		props.resetChanged();
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
	 * 将所有的属性以int类型相加
	 * 
	 * @return
	 */
	public float sum() {
		return this.props.sum();
	}

	/**
	 * 将由属性索引数组index指定的属性相加
	 * 
	 * @param index
	 *            属性的索引
	 * @return
	 */
	public float sum(int[] index) {
		return this.props.sum(index);
	}

	/**
	 * 计算除了指定的索引数组标识的以外的属性数值的和
	 * 
	 * @param exceptIndexs
	 *            被排除的属性索引数组
	 * @return
	 */
	public float sumExcept(int[] exceptIndexs) {
		return props.sumExcept(exceptIndexs);
	}

	/**
	 * 将该数值对象设置为只读状态,从调用此方法的一刻起,该数值对象的值将不能够再被修改,适用于对象的值一旦设置完成后,而且以后不会再进行修改
	 */
	public void readOnly() {
		this.isReadOnly = true;
	}

	/**
	 * 拷贝并比较<br>
	 * 只设置改变的值的Bit位 如不关心change，请使用 {@link #copyFrom(FloatNumberPropertyObject)}
	 * 
	 * @author sd 2009-9-17
	 * @param src
	 * @exception IllegalArgumentException
	 *                如果src的对象类型与该类型不一致
	 * @exception IllegalStateException
	 *                如果该对象处于只读状态
	 */
	public void copyFromAndCompare(FloatNumberPropertyObject src) {
		if (src.propertyType != this.propertyType) {
			throw new IllegalArgumentException("Not the same property type.");
		}
		int _size = this.size();
		for (int i = 0; i < _size; i++) {
			this.set(i, src.get(i));
		}
	}

	@Override
	public String toString() {
		return props.toString();
	}
	
	public KeyValuePair<Integer,Float>[] getIndexValuePairs(){
		return props.getIndexValuePairs();
	}
	
}
