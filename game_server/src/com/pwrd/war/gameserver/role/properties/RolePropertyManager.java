package com.pwrd.war.gameserver.role.properties;

import java.util.List;

import com.pwrd.war.core.util.KeyValuePair;

public abstract class RolePropertyManager {
	public abstract boolean isNumchanged(); 
	public abstract boolean isStrchanged();
	public abstract List<KeyValuePair<Integer, Integer>> getChangedNum(); 
	public abstract List<KeyValuePair<Integer, String>> getChangedString();
	
	public abstract void resetChanged();
}
