package com.pwrd.war.gameserver.role.properties;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.core.annotation.Comment;
import com.pwrd.war.core.annotation.Type;
import com.pwrd.war.core.util.KeyValuePair;

public class RoleBaseIntAProperties extends PropertyObject {
	/** 基础整型属性索引开始值 */
	public static int _BEGIN = 0;
	
	/** 基础整型属性索引结束值 */
	public static int _END = _BEGIN;
	
	@Comment(content = "当前血量")
	@Type(Double.class)
	public static final int CUR_HP = ++_END;
	@Comment(content = "最大血量")
	@Type(Double.class)
	public static final int MAX_HP = ++_END;
	@Comment(content = "攻击")
	@Type(Double.class)
	public static final int ATK = ++_END;
	@Comment(content = "防御")
	@Type(Double.class)
	public static final int DEF = ++_END;
	@Comment(content = "速度")
	@Type(Double.class)
	public static final int SPD = ++_END;
	
	
	@Comment(content = "总战斗力")
	@Type(Double.class)
	public static final int ZHANDOULI = ++_END; 
	
	@Comment(content = "近程攻击")
	@Type(Double.class)
	public static final int SHORT_ATK = ++_END;
	
	@Comment(content = "远程攻击")
	@Type(Double.class)
	public static final int REMOTE_ATK = ++_END;
	
	@Comment(content = "近程防御")
	@Type(Double.class)
	public static final int SHORT_DEF = ++_END;
	
	@Comment(content = "远程防御")
	@Type(Double.class)
	public static final int REMOTE_DEF = ++_END;
	
	/** 基础整型属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;

	public static final PropertyType TYPE = PropertyType.BASE_ROLE_PROPS_INT_A;
	public RoleBaseIntAProperties() {
		super(_SIZE, TYPE); 
	}

	/**
	 * 取得变化的数值，如果是double需要Math.floor转化为int
	 * @author xf
	 */
	public List<KeyValuePair<Integer, Integer>> getChangedNum(){
		KeyValuePair<Integer, Object>[] indexValuePairs = super.getChanged();
		List<KeyValuePair<Integer, Integer>> list = new ArrayList<KeyValuePair<Integer, Integer>>();
		for(int i = 0; i< indexValuePairs.length; i++){
			if(indexValuePairs[i] == null)continue;
			int key = indexValuePairs[i].getKey();
			if(indexValuePairs[i].getValue() instanceof Integer){
				list.add(new KeyValuePair<Integer, Integer>(key, (Integer) indexValuePairs[i].getValue()));
			}else{
				//一级属性直接转化为int
				double d = (Double) indexValuePairs[i].getValue(); 
				list.add(new KeyValuePair<Integer, Integer>(key, (int)(Math.round(d))));
			}
		} 
		return list;
	}
	@Override
	public void resetChanged() { 
		super.resetChanged();
	}

	
}
