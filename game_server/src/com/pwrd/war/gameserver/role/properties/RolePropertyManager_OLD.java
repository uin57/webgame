package com.pwrd.war.gameserver.role.properties;

import java.util.BitSet;

import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.KeyValuePair;
import com.pwrd.war.gameserver.role.Role;

public abstract class RolePropertyManager_OLD<T extends Role,V> {
	
	/** 影响属性值的影响器标志 */
	/** ALL */
	public static final int PROP_FROM_MARK_ALL = 0xFFFFFFFF;
	/** 初始 */
	public static final int PROP_FROM_MARK_INIT = 0x0001;
	/** 洗点 */
	public static final int PROP_FROM_MARK_LEARN_GIFT = 0x0002;
	/** 装备 */
	public static final int PROP_FROM_MARK_EQUIP = 0x0004;
	/** buff */
	public static final int PROP_FROM_MARK_BUFF = 0x0008;
	
	/** 等级 */
	public static final int PROP_FROM_MARK_LEVEL = 0x0010;	
	/** 一级属性 */
	public static final int PROP_FROM_MARK_APROPERTY = 0x0020;

	/** 科技 */
	public static final int PROP_FROM_MARK_TECH = 0x0040;

	// 内部标志
	/** 一级属性 */
	protected static final int CHANGE_INDEX_APROP = 0;
	/** 二级属性 */
	protected static final int CHANGE_INDEX_BPROP = 1;
	
	/** 兵一级属性 */
	protected static final int CHANGE_INDEX_SOLDIER_APROP = 2;
	/** 兵二级属性 */
	protected static final int CHANGE_INDEX_SOLDIER_BPROP = 3;

	
	protected T owner;
	
	/** 一级、二级、抗性改变标志 */
	protected BitSet propChangeSet;
	
	
	public RolePropertyManager_OLD(T role,int bitSetSize) {
		Assert.notNull(role);
		owner = role;
		propChangeSet = new BitSet(bitSetSize);
	}
		

	/**
	 * 按指定的影响标识，更新一级属性
	 * 
	 * @param role
	 * @param effectMark
	 * @return
	 */
	abstract protected boolean updateAProperty(T role, int effectMask);

	/**
	 * 按指定的影响标识，更新二级属性
	 * 
	 * @param role
	 * @param effectMark
	 * @return
	 */
	abstract protected boolean updateBProperty(T role, int effectMask);
	
	/**
	 * 按标识更新属性
	 * @param effectMask
	 */
	abstract public void updateProperty(int effectMask);
	
	
	/**
	 * 获取所有改变
	 * @return
	 */
	abstract public KeyValuePair<Integer, V>[] getChanged();
	
	/**
	 * 一、二级或抗性是否有改变
	 * 
	 * @return
	 */
	public boolean isChanged() {
		return !propChangeSet.isEmpty();
	}

	/**
	 * 重置属性修改标识
	 */
	public void resetChanged() {
		propChangeSet.clear();
	}
	

}
