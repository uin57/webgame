package com.pwrd.war.gameserver.item;

import java.util.List;

import com.pwrd.war.core.annotation.NotTranslate;
import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 道具相关类型、常量定义
 * 
 * 
 */
public interface ItemDef {
	
	/**
	 * 新手15级之前,强化肯定成功
	 */
	public static final int NEW_PLAYER_LEVEL = 15;
	
	/**
	 * 强化初始等级 
	 */
	public static final int INIT_ENHANCE_LEVEL = 0;
	
	/**
	 * 道具身份类型，空道具用什么模板
	 * 
	 */
	public static enum IdentityType implements IndexedEnum {
		/** 空 */
		NULL(0),
		/** 装备 */
		EQUIP(10),
		/** 消耗品 */
		CONSUMABLE(11), 
		/** 卷轴 **/
		REEL(12),
		/** 材料 **/
		MATERIAL(13),
		/** 星魂 **/
		XINGHUN(14),
		/** 物品包 **/
		ITEMBAG(15),
		/** 武将卡 **/
		PETCARD(16),
		;
		
		private IdentityType(int index) {
			this.index = index;
		}

		public final int index;

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<IdentityType> values = IndexedEnumUtil
				.toIndexes(IdentityType.values());

		public static IdentityType valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}

//	/**
//	 * 道具大类，
//	 * 如果一组道具具体类型有某种共性,并需要针对这个共性进行某种判断或操作,可将这些具体类型划分到统一大类,但一个具体道具只有一个大类，
//	 * 因此Catalogue各元素的概念必须不能有交集
//	 * 
//	 */
//	public static enum Catalogue implements IndexedEnum {
//		/** 空 */
//		NULL(0),
//		/** 武器 */
//		WEAPON(1),
//		/** 防具 */
//		ARMOR(2),
//		
//		// 各种消耗品
//		/** 主包一般道具 */
//		COMMON_ITEM(10),
//		/** 任务道具 */
//		COMMON_QUEST(11),
//		/** 材料 */
//		COMMON_MATERIAL(12),
//		/** 特殊道具 */
//		SPECIAL(13),
//
//		;
//		
//		public final short index;
//
//		@Override
//		public int getIndex() {
//			return index;
//		}
//
//		private Catalogue(int index) {
//			this.index = (short) index;
//		}
//
//		private static final List<Catalogue> values = IndexedEnumUtil
//				.toIndexes(Catalogue.values());
//
//		public static Catalogue valueOf(int index) {
//			return EnumUtil.valueOf(values, index);
//		}
//	}

	/**
	 * 道具具体类别
	 * 类别划分规则一般是一个功能（使用时的逻辑）对应一个类别，
	 * 同一个类别的道具的差别（除道具一般的差别之外，例如限制等级、性别等）就是功能的参数、属性有所不同<br/>
	 * 
	 */
	public static enum Type implements IndexedEnum {
		/** 空类型 */
		NULL(0, null),
		
		/** 武器 */
		WEAPON(101, IdentityType.EQUIP), 
		/** 头饰 */
		CAP(102, IdentityType.EQUIP),
		/** 衣服 */
		CLOTHES(103, IdentityType.EQUIP),  
		/** 鞋子 */
		SHOES(104, IdentityType.EQUIP),
		/** 披风 */
		SHOULDER(105, IdentityType.EQUIP),  
		/** 配饰*/
		RING(106, IdentityType.EQUIP),
		

		
		/** 一般主包道具 */
		COMMOM_ITEM(111, IdentityType.CONSUMABLE),
		BUFF_ITEM(113, IdentityType.CONSUMABLE),
		/** 血包 **/
		HP_ITEM(119, IdentityType.CONSUMABLE),
		
		/** 武器卷轴 **/
		REEL_EQUIP(121, IdentityType.REEL),
		/** 丹药卷轴  **/
		REEL_ITEM(122, IdentityType.REEL),
		/** 一般材料 */
		COMMON_MATERIAL(131, IdentityType.MATERIAL),
//		/** 一般任务道具 */
//		COMMON_QUEST(52, Catalogue.COMMON_QUEST),
		
		

		;
	
		public final int index;
		/** 所属大类 */
		public final IdentityType identityType;

		private Type(int index, IdentityType identityType) {
			this.index = index;
			this.identityType = identityType;
		}

		@Override
		public int getIndex() {
			return index;
		}

		public IdentityType getIdentityType() {
			return identityType;
		}

		/**
		 * 是否是装备类型
		 * 
		 * @return
		 */
		public boolean isEquipment() {
			return identityType == IdentityType.EQUIP;
		}

		private static final List<Type> values = IndexedEnumUtil.toIndexes(Type
				.values());

		public static Type valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}

	/**
	 * 装备放置位置定义
	 * 此位置的index值一旦确定不可以更改,因为与道具表格中的数据相关
	 */
	public static enum Position implements IndexedEnum {
		//武器、头盔、衣服、鞋子、披风，配饰。1,2,3,4,5,6
		/** 空 */
		NULL(0, false),
		/** 武器 */
		WEAPON(1, false),
		/** 头盔 */
		CAP(2, true),
		/** 衣服 */
		CLOTHES(3, true),  
		/** 鞋子 */
		SHOES(4, false),
		/** 肩甲 */
		SHOULDER(5, true),  
		/** 配饰 */
		RING(6, false),
		
		;

		public final int index;
		/** 此位置在换装时是否需要更换avatar */
		private boolean needSwitchAvatar;

		private Position(int index, boolean needSwitchAvatar) {
			this.index = index;
			this.needSwitchAvatar = needSwitchAvatar;
		}

		public boolean isNeedSwitchAvatar() {
			return needSwitchAvatar;
		}

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<Position> values = IndexedEnumUtil
				.toIndexes(Position.values());

		public static Position valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}

	/**
	 * 消耗品函数功能
	 */
	public static enum ConsumableFunc implements IndexedEnum {
		NULL(0),
		/** 使用后自身获得buff */
		ADD_HP(1), 
		/** 添加buff **/
		ADD_BUFF(2),
		/** 增加体力 **/
		ADD_VIT(3),
		/** 变身 **/
		CHANGE_BODY(4),
		/** 添加vip体验效果 **/
		ADD_VIP_BUFF(5),
		;

		public final short index;

		/** 属性功能的动作类型，需要静态初始化 */
		private ConsumableFunc(int index) {
			this.index = (short) index;
		}

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<ConsumableFunc> values = IndexedEnumUtil
				.toIndexes(ConsumableFunc.values());

		public static ConsumableFunc valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}

	/**
	 * 道具的稀有程度
	 */
	public static enum Rarity implements IndexedEnum {
		/** 白色 */
		WHITE(1,"#FFFFFF"),
		/** 绿色#1eff00 */
		GREEN(2,"#1EFF00"),
		/** 蓝色#0070dd */
		BLUE(3,"#0070DD"),
		/** 紫色#a335ee */
		PURPLE(4,"#A335EE"),
		/** 黄色#ffd100 */
		YELLOW(5,"#FFD100"), 
		
		;

		private Rarity(int index,String color) {
			this.index = index;
			this.color = color;
		}

		public final int index;
		
		@NotTranslate
		public final String color;

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<Rarity> values = IndexedEnumUtil
				.toIndexes(Rarity.values());

		public static Rarity valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
		
	}

	
	/**
	 * 道具属性分组
	 */
	public static enum AttrGroup implements IndexedEnum {
		/** 基础属性 */
		BASE(1),
		/** 随机附加 */
		ENHANCE(2),
		/** 绑定状态 */
		FREEZESTATE(3),
		/** 取消绑定的时间 */
		CANCELTIME(4), 
		;

		private AttrGroup(int index) {
			this.index = index;
		}

		public final int index;

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<AttrGroup> values = IndexedEnumUtil
				.toIndexes(AttrGroup.values());

		public static AttrGroup valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}

	}
	
	public static enum FreezeState implements IndexedEnum {
		/** 普通状态 */
		NOMAL(0),
		/** 绑定状态 */
		FREEZE(1),
		/** 取消绑定状态 */
		CANCELFREEZE(2)
		;

		private FreezeState(int index) {
			this.index = index;
		}

		public final int index;

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<FreezeState> values = IndexedEnumUtil
				.toIndexes(FreezeState.values());

		public static FreezeState valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}

	/**
	 * 装备属性名称
	 * @author xf
	 */
	public static enum EquipProps{
		ATK("atk"),
		DEF("def"),
		SPD("spd"),
		CRI("cri"),
		MAXHP("maxhp"),
		FANJI("fanji"),
		MINGZHONG("mingzhong"),
		SHANBI("shanbi"),
		LIANJI("lianji"),
		SHANGHAI("shanghai"),
		MIANSHANG("mianshang"),
		ZHANDOULI("zhandouli")
		;
		
		private String value;
		private EquipProps(String value){
			this.value = value;
			
		}
		public static EquipProps valueBy(String value){
			for(EquipProps e :  values()){
				if(e.getValue().equals(value))return e;
			}
			return null;
		}
		public String getValue(){
			return this.value;
		}
		public String toString(){
			return this.value;
		}
		
	}
	
	/**
	 * 装备属性名称
	 * @author xf
	 */
	public static enum XinghunProps{
		ATK("atk"),
		DEF("def"),
//		SKILLATK("skillatk"),
//		SKILLDEF("skilldef"),
		HP("hp"),
		SHANBI("shanbi"),
		MINGZHONG("mingzhong"),
		CRI("cri"),
		RENXING("renxing"),
		BISHA("bisha")
//		,
//		ADDJITUI("addjitui"),
//		REDJITUI("redjitui")
		;
		
		private String value;
		private XinghunProps(String value){
			this.value = value;
			
		}
		public static XinghunProps valueBy(String value){
			for(XinghunProps e :  values()){
				if(e.getValue().equals(value))return e;
			}
			return null;
		}
		public String getValue(){
			return this.value;
		}
		public String toString(){
			return this.value;
		}
		
	}
}
