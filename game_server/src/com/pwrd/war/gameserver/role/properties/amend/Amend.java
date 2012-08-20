package com.pwrd.war.gameserver.role.properties.amend;

import com.pwrd.war.gameserver.role.properties.PropertyType;


/**
 * 属性修正功能定义
 * 
 */
public final class Amend {
	/** 修正的Key值,策划在脚本里引用的key值 */
	private final int key;
	/** 被修正的属性类型,参见{@link PropertyType}中的定义 */
	private final PropertyType propertyType;
	/** 被修正的属性索引,参见{@link PetAProperty}{@link SoldierAProperty}{@link SoldierBProperty}等中的定义 */
	private final int properytIndex;

	/**
	 * 
	 * @param key
	 * @param propertyType
	 * @param properytIndex
	 */
	Amend(int key, PropertyType propertyType, int properytIndex) {
		this.key = key;
		this.propertyType = propertyType;
		this.properytIndex = properytIndex;
	}

	/**
	 * 返回修正的Key值,供策划在数据里引用
	 */
	public int getKey() {
		return key;
	}

	/**
	 * 取得修正的属性类型
	 * 
	 * @return
	 */
	public PropertyType getPropertyType() {
		return propertyType;
	}

	/**
	 * 取得修正属性的索引
	 * 
	 * @return
	 */
	public int getProperytIndex() {
		return properytIndex;
	}

	@Override
	public String toString() {
		return "Amend [key=" + key + ", propertyType=" + propertyType
				+ ", properytIndex=" + properytIndex + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + key;
		result = prime * result + propertyType.getType();
		result = prime * result + properytIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Amend other = (Amend) obj;
		if (key != other.key)
			return false;
		if (propertyType != other.propertyType)
			return false;
		if (properytIndex != other.properytIndex)
			return false;
		return true;
	}
}
