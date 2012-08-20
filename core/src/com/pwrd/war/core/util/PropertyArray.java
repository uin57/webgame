package com.pwrd.war.core.util;


/**
 * 基于数组实现的对象属性值对象
 * 可以存储任何类型数据
 * 
 */
public class PropertyArray extends GenericPropertyArray<Object> { 

	public PropertyArray(int size) {
		super(Object.class, size);
	}

 
	public void setInt(int index, int val) {
		this.set(index, Integer.valueOf(val));
	}

	public int getInt(int index) {
		Integer _val = (Integer) this.values[index];
		if (_val == null) {
			return 0;
		}
		return _val.intValue();
	}

	public void setLong(int index, long val) {
		this.set(index, Long.valueOf(val));
	}

	public long getLong(int index) {
		Long _val = (Long) this.values[index];
		if (_val == null) {
			return 0;
		}
		return _val.longValue();
	}

	public void setShort(int index, short val) {
		this.set(index, Short.valueOf(val));
	}

	public short getShort(int index) {
		Short _val = (Short) this.values[index];
		if (_val == null) {
			return 0;
		}
		return _val.shortValue();
	}

	public void setByte(int index, byte val) {
		this.set(index, Byte.valueOf(val));
	}

	public byte getByte(int index) {
		Byte _val = (Byte) this.values[index];
		if (_val == null) {
			return 0;
		}
		return _val.byteValue();
	}

	public void setString(int index, String val) {
		this.set(index, val);
	}

	public String getString(int index) {
		return (String) this.values[index];
	}
	public void setDouble(int index, double val) {
		this.set(index, val);
	}

	public double getDouble(int index) {
		Double d = (Double) this.values[index];
		if(d == null){
			return 0;
		}
		return d.doubleValue();
	}

	
	public void setObject(int index, Object val) {
		this.set(index, val);
	}

	public Object getObject(int index) {
		return this.values[index];
	}
	@Override
	public boolean set(int index, Object val) { 
		if(val instanceof Double){
			//double要特殊判断，如果在某一精度内，判定相等
			Double _new = (Double) val;
			Double old = this.getDouble(index);
			if(old.doubleValue() - _new.doubleValue() <= 0.00001 &&
					old.doubleValue() - _new.doubleValue() >= -0.00001){
				return false;
			}else{
				return super.set(index, val);
			}
			
		}else{
			return super.set(index, val);
		}
	}
	

}
