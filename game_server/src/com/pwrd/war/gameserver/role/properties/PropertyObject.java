package com.pwrd.war.gameserver.role.properties;

import com.pwrd.war.core.util.KeyValuePair;
import com.pwrd.war.core.util.PropertyArray;

/**
 * 适用于各种类型对象的属性对象
 * 增加了属性类型，在获取getChanged时会自动修正key 
 * 
 */
public class PropertyObject  extends PropertyArray{
	 
	protected final PropertyType propertyType;

	public PropertyObject(int size, PropertyType properType) {
		super(size);
		this.propertyType = properType;
	}

	 
	public int getKeyByProp(int prop){
		return propertyType.genPropertyKey(prop);
	} 

	/* 
	 * 取得已经变化的，同时会根据属性类型，自动修正其值
	 */
	public KeyValuePair<Integer, Object>[] getChanged() {
		KeyValuePair<Integer, Object>[] changes= super.getChanged();
		for(int i=0;i<changes.length;i++)
		{
			changes[i].setKey(propertyType.genPropertyKey(changes[i].getKey()));
		}
		return changes;
	}
	
}
