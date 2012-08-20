package com.pwrd.war.gameserver.role.properties;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import com.pwrd.war.common.constants.ProbabilityConstants;
import com.pwrd.war.core.annotation.Comment;
import com.pwrd.war.core.annotation.Type;
import com.pwrd.war.core.util.KeyValuePair;

/**
 * 基础角色属性（人物角色，宠物公用）: 数值型 ， 统一作为Integer处理
 * 
 */
public class RoleBaseIntProperties extends PropertyObject/* GenericPropertyObject<Integer>*/ {

	/** 基础整型属性索引开始值 */
	public static int _BEGIN = 0;
	
	/** 基础整型属性索引结束值 */
	public static int _END = _BEGIN;
	
	/* 公用 */
	@Comment(content = "等级")
	@Type(Integer.class)
	public static final int LEVEL = ++_END; 

	@Comment(content = "职业")
	@Type(Integer.class)
	public static final int VOCATION = ++_END;
	
	@Comment(content = "性别")
	@Type(Integer.class)
	public static final int SEX = ++_END;

	@Comment(content = "成长值")
	@Type(Integer.class)
	public static final int GROW = ++_END;
  
	@Comment(content = "当前经验值")
	@Type(Integer.class)
	public static final int CUR_EXP = ++_END;
	@Comment(content = "最大经验值")
	@Type(Integer.class)
	public static final int MAX_EXP = ++_END; 
	
	

	@Comment(content = "暴击")
	@Type(Double.class)
	public static final int CRI = ++_END;
	
	@Comment(content = "闪避")
	@Type(Double.class)
	public static final int SHANBI = ++_END;
	@Comment(content = "伤害")
	@Type(Double.class)
	public static final int SHANGHAI = ++_END;
	@Comment(content = "免伤")
	@Type(Double.class)
	public static final int MIANSHANG = ++_END;
	@Comment(content = "反击")
	@Type(Double.class)
	public static final int FANJI = ++_END;
	@Comment(content = "命中")
	@Type(Double.class)
	public static final int MINGZHONG = ++_END;
	@Comment(content = "连击")
	@Type(Double.class)
	public static final int LIANJI = ++_END;
	@Comment(content = "韧性")
	@Type(Double.class)
	public static final int RENXING = ++_END;
	@Comment(content = "暴击伤害")
	@Type(Double.class)
	public static final int CRISHANGHAI = ++_END;
	
	

	@Comment(content = "所在位置X")
	@Type(Integer.class)
	public static final int LOCATIONX = ++_END;
	
	@Comment(content = "所在位置Y")
	@Type(Integer.class)
	public static final int LOCATIONY = ++_END;
	
 
	
	@Comment(content = "阵营")
	@Type(Integer.class)
	public static final int CAMP = ++_END;
 


	@Comment(content = "成长上限")
	@Type(Integer.class)
	public static final int MAX_GROW = ++_END;
	
	@Comment(content = "转职等级")
	@Type(Integer.class)
	public static final int TRANSFER_LEVEL = ++_END;
	
	
	@Comment(content = "生命上线研究等级")
	@Type(Integer.class)
	public static final int MAXHP_LEVEL = ++_END;
	
	@Comment(content = "攻击研究等级")
	@Type(Integer.class)
	public static final int ATK_LEVEL = ++_END;
	
	@Comment(content = "防御研究等级")
	@Type(Integer.class)
	public static final int DEF_LEVEL = ++_END;
	
	@Comment(content = "近程攻击研究等级")
	@Type(Integer.class)
	public static final int SHORT_ATK_LEVEL = ++_END;
	
	@Comment(content = "近程防御研究等级")
	@Type(Integer.class)
	public static final int SHORT_DEF_LEVEL = ++_END;
	@Comment(content = "远程攻击研究等级")
	@Type(Integer.class)
	public static final int REMOTE_ATK_LEVEL = ++_END;
	
	@Comment(content = "远程防御研究等级")
	@Type(Integer.class)
	public static final int REMOTE_DEF_LEVEL = ++_END;
	
	
	@Comment(content = "被击后攻击方冷却回合")
	@Type(Integer.class)
	public static final int COOLDOWN_ROUND = ++_END;
	
	@Comment(content = "最大士气")
	@Type(Integer.class)
	public static final int MAX_MORALE = ++_END;
	
	@Comment(content = "伤害")
	@Type(Double.class)
	public static final int DAMAGE = ++_END;
	
	@Comment(content = "官职星级")
	@Type(Integer.class)
	public static final int TRANSFERSTAR = ++_END;
	@Comment(content = "官职星级经验")
	@Type(Integer.class)
	public static final int TRANSFEREXP = ++_END;
	
	@Comment(content = "成长境界值")
	@Type(Integer.class)
	public static final int JINGJIE = ++_END;
	
	@Comment(content = "兵法积分")
	@Type(Integer.class)
	public static final int WARCRAFT_SCORE = ++_END;
	
	/** 基础整型属性的个数 */
	public static final int _SIZE = _END - _BEGIN + 1;

	public static final PropertyType TYPE = PropertyType.BASE_ROLE_PROPS_INT;

	/** 数值是否修改的副本标识 */
	private final BitSet shadowBitSet;

	public RoleBaseIntProperties() {
		super( _SIZE, TYPE);
		this.shadowBitSet = new BitSet(this.size());
	}

	/**
	 * 重载{@link #resetChanged()},在重置前将props的修改记录下来
	 */
	@Override
	public void resetChanged() {
		super.fillChangedBit(this.shadowBitSet);
		super.resetChanged();
	}

	/**
	 * 是否有副本属性的修改
	 * 
	 * @return ture,有修改
	 */
	public boolean isShadowChanged() {
		return super.isChanged() || (!this.shadowBitSet.isEmpty());
	}

	/**
	 * 检查指定的副本属性索引是否有修改
	 * 
	 * @param index
	 * @return true,有修改;false,无修改
	 */
	public boolean isShadowChanged(final int index) {
		return super.isChanged(index) || this.shadowBitSet.get(index);
	}

	/**
	 * 
	 */
	public void resetShadowChanged() {
		this.shadowBitSet.clear();
	}
 
//	/* 
//	 * 取得所有的值，去除哪些为null的，因为这里设置的不可能为null，null也是0
//	 */
//	public List<KeyValuePair<Integer, Integer>> getAllValuePairs() {
//		//去除null的
//		KeyValuePair<Integer, Object>[] indexValuePairs = super.getIndexValuePairs();
//		List<KeyValuePair<Integer, Integer>> list = new ArrayList<KeyValuePair<Integer, Integer>>();
//		for(int i = 0; i< indexValuePairs.length; i++){
//			if(values[i] == null)continue;
//			int key = propertyType.genPropertyKey(i);
//			if(values[i] instanceof Integer){
//				list.add(new KeyValuePair<Integer, Integer>(key, (Integer) values[i]));
//			}else{
//				double d = (Double) values[i]; 
//				list.add(new KeyValuePair<Integer, Integer>(key, (int)(d * ProbabilityConstants.PROBABILITY)));
//			}
//		}
////		indexValuePairs = KeyValuePair.newKeyValuePairArray(list.size());		
////		list.toArray(indexValuePairs);
//		return list;
//	}
//	/**
//	 * 获取属性值
//	 * 
//	 * @param index
//	 * @return
//	 */ 
//	public Integer getInteger(int index) {
//		Object value = super.get(index);
//		if (value != null) {
//			return (Integer) value;
//		} else {
//			return 0;
//		}
//	}
// 
//	public boolean setInteger(int index, Integer val) {
//		val = val == null?0:val;
//		return super.set(index, val);
//	}
//	
	/**
	 * 取得变化的数值，如果是double需要乘以10000转化为int
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
				double d = (Double) indexValuePairs[i].getValue(); 
				list.add(new KeyValuePair<Integer, Integer>(key, (int)(d * ProbabilityConstants.CLIENT_PROB)));
			}
		} 
		return list;
	}
 
}
