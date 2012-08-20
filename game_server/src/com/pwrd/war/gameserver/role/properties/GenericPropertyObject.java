package com.pwrd.war.gameserver.role.properties;

import com.pwrd.war.core.util.GenericPropertyArray;
import com.pwrd.war.core.util.KeyValuePair;

/**
 * 
 * 泛型的属性对象
 * 增加了属性类型，在获取getChanged时会自动修正key 
 */
public class GenericPropertyObject<T> extends GenericPropertyArray<T> {

//	protected final GenericPropertyArray<T> props;
	protected final PropertyType propertyType;

	public GenericPropertyObject(Class<T> clazz, int size, PropertyType properType) {
//		this.props = new GenericPropertyArray<T>(clazz, size);
		super(clazz, size);
		this.propertyType = properType;
	}

  
	
	/**
	 * 取得被修改过的的属性索引,修正后的索引，及其对应的值
	 * 
	 * @return
	 */
	public KeyValuePair<Integer, T>[] getChanged() {
		KeyValuePair<Integer, T>[] changes= super.getChanged();
		for(int i=0;i<changes.length;i++)
		{
			changes[i].setKey(propertyType.genPropertyKey(changes[i].getKey()));
		}
		return changes;
	}

}
