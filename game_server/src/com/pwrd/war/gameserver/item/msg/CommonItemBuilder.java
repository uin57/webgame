package com.pwrd.war.gameserver.item.msg;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.common.model.item.AttrDescAddon;
import com.pwrd.war.common.model.item.AttrDescBase;
import com.pwrd.war.common.model.item.AttrDescSpec;
import com.pwrd.war.common.model.item.CommonItem;
import com.pwrd.war.core.util.JsonUtils;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindMode;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.human.wealth.Bindable.Oper;
import com.pwrd.war.gameserver.item.EquipmentFeature;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.role.properties.amend.Amend;
import com.pwrd.war.gameserver.role.properties.amend.AmendTriple;

/**
 * 
 * 单个道具信息，需要显示道具信息的地方公用的一个数据对象，提供了显示道具信息所需的所有get方法，各消息可根据需要引用其中的字段
 *
 */
public final class CommonItemBuilder {	
	
	/** 不绑定 */
	public static final short BIND_MODE_NULL = 0;
	/** 获取时绑定 */
	public static final short BIND_MODE_GET_BIND = 1;
	/** 使用时绑定 */
	public static final short BIND_MODE_USE_BIND = 2;
	/** 装备时绑定 */
	public static final short BIND_MODE_WEAR_BIND = 3;
	
	/** 道具个数 */
	private int count;
	/** 绑定的道具模板，如果绑定了Item此模板即为Item的模板，在显示任务奖励等只有模板的地方可直接绑定模板 */
	private ItemTemplate template;
	/** 绑定的道具实例 */
	private Item item;
	/** 需要填充内容的CommonItem，如果没提供这个，会new一个新的 */
	private CommonItem commonItem;
	/** 绑定状态 */
	private BindStatus bindStatus;
	
	public CommonItemBuilder() {
		reset();
	}
	
	/**
	 * 直接绑定模板和数量
	 * 
	 * @param template
	 * @param count
	 */
	public CommonItemBuilder(ItemTemplate template, int count) {
		bindItemTemplate(template, count);
	}
	
	/**
	 * 绑定一个道具实例
	 * 
	 * @param item
	 */
	public CommonItemBuilder(Item item) {
		bindItem(item);
	}
	
	public void bindItem(Item item) {
		reset();
		this.item = item;
		this.template = item.getTemplate();
		this.count = item.getOverlap();
	}

	
	public void bindItemTemplate(ItemTemplate template, int count) {
		reset();
		this.template = template;
		this.count = count;
	}
	
	
	/**
	 * 将该对象转换为CommonItem对象
	 * 
	 * @return
	 */
	public CommonItem buildCommonItem() {
		if (commonItem == null) {
			commonItem = new CommonItem();
		} 
		
		commonItem.setUuid(getUuid());
		commonItem.setItemSn(this.template.getItemSn());
		commonItem.setBind(this.item.getBindStatus().getIndex());
		commonItem.setBagId(this.item.getBagType().index);
		commonItem.setIndex(this.item.getIndex());
		commonItem.setNum(this.item.getOverlap());
		commonItem.setCreateTime((int) (this.item.getCreateTime().getTime()/TimeUtils.SECOND));
		commonItem.setIdentity(this.template.getIdentityType());
		
		commonItem.setFeature("");
		if(this.item.getFeature() != null){
//			commonItem.setNum(this.item.getFeature().getCurEndure());
			if(this.item.getFeature() instanceof EquipmentFeature){
				//装备需要把穿戴在身上的位置信息发过去
//				commonItem.setPosition(((EquipmentFeature)this.item.getFeature()).getPosition().getIndex());
				EquipmentFeature f = (EquipmentFeature) item.getFeature();
				commonItem.setWearerUuid(item.getWearerId());
				commonItem.setFeature(f.toFeatureString());
				commonItem.setBattleProps(JsonUtils.toJsonString(f.getBattleProps()));
			}else{
				commonItem.setFeature(item.getFeature().toFeatureString());
			}
		}

		return commonItem;
	}
	
	private boolean getCanEnhance(){
		if(item == null){
			return false;
		}
		// 非装备可强化应该是配错了,所以先控制是装备
		if(item.isEquipment()
				/*&& item.getTemplate().isCanEnhance()*/
				){
			return true;
		}
		else{
			return false;
		}
	}
	
	private int getCancelTime(){
		int interval = 0;
		if(item !=null && item.isEquipment()){
			EquipmentFeature equipmentFeature = (EquipmentFeature)item.getFeature();
			
			interval = (int)(equipmentFeature.getCancelTime() - Globals.getTimeService().now() );
			if(interval < 0){
				interval = 0;
			}
		}
		return interval;
	}
	
	private int getFreezeState(){
		int freezeState = ItemDef.FreezeState.NOMAL.index;
		if(item !=null && item.isEquipment()){
			EquipmentFeature equipmentFeature = (EquipmentFeature)item.getFeature();
			
			freezeState = equipmentFeature.getFreezeState();
		}
		return freezeState;
	}
	
	private void reset() {
		this.count = 0;
		this.item = null;
		this.template = null;
		this.commonItem = null;
	}
	
	public Item getItem() {
		return item;
	}
	
	/**
	 * 设置common item 方便builder子类
	 * 
	 * @param commenItem
	 */
	public void setCommonItem(CommonItem commenItem) {
		this.commonItem = commenItem;
	}
	
	/**
	 * 道具实例的UUID
	 * 
	 * @return
	 */
	public String getUuid() {
		if (item == null) {
			return "";
		} else {
			return item.getUUID();
		}
	}

	public String getName() {
		return template.getName();
	}

	public int getCount() {
		return count;
	}

//	public String getIconId() {
//		return template.getIconId();
//	}

	public String getDesc() {
		return template.getDesc();
	}
	
	public ItemTemplate getTemplate() {
		return template;
	}
	
	public int getTemplateId() {
		return template.getId();
	}
	
	/**
	 * 出售给商店的价格
	 * 
	 * @return
	 */
	public int getPrice() {
		int tmplSellPrice = template.getSellPrice();
		return count > 0 ? tmplSellPrice * count : tmplSellPrice;
	}

	/**
	 * 出售给商店的货币类型
	 * 
	 * @return
	 */
	public int getCurrency() {
		return template.getSellCurrency().index;
	}

	
	public int getTemplateBagId() {
		if (template != null) {
			return template.getBagType().index;
		} else {
			return 0;
		}
	}

	public int getBagId() {
		if (item != null) {
			return item.getBagType().index;
		} else {
			return 0;
		}
	}

	public int getIndex() {
		if (item != null) {
			return item.getIndex();
		} else {
			return 0;
		}
	}

	public int getBind() {
		return getBindStatus().index;
	}

	public BindStatus getBindStatus() {
		if (bindStatus != null) {
			return bindStatus;
		}

		if (item != null) {
			return item.getBindStatus();
		}

		return template.getBindStatusAfterOper(BindStatus.NOT_BIND, Oper.GET);
	}
	
	public short getBindMode() {
		if (item == null || item.getBindStatus() == BindStatus.NOT_BIND) {
			// 只有未创建实例的和未绑定的道具需要显示这个
			return getTemplateBindMode();
		} else {
			return BIND_MODE_NULL;
		}
	}

	public void setBindStatus(BindStatus bindStatus) {
		this.bindStatus = bindStatus;
	}
	

	 

	public short getItmeType() {
		return (short) template.getItemType().index;
	}


//	public String getIcon() {
//		return template.getIconId();
//	}

	public int getCooldown() {
		return template.getCoolDown();
	}

	public int getLeftCD() {
		return (int) (item == null ? 0 : item.getLeftCD());
	}

	/**
	 * 等级，装备和非装备显示格式不一样
	 * 
	 * @return
	 */
//	public int getLevel() {
//		return template.getLevel();
//	}

	/**
	 * @return
	 */
//	public int getAlliance() {
//		return template.getAlliance();
//	}

	/**
	 * @return
	 */
	public int getSexLimit() {
		return template.getSexLimit();
	}

	/**
	 * 耐久上限，装备需要显示，为0时不需要显示耐久信息
	 * 
	 * @return
	 */
//	public int getMaxEndure() {
////		if (template.isEquipment()) {
////			return ((EquipItemTemplate) template).getMaxEndure();
////		} else {
//			int endure = template.getMaxEndure();
//			return endure <= 0 ? -1 : endure;
////		}
//	}

	/**
	 * 当前耐久，装备需要显示
	 * 
	 * @return
	 */
//	public int getCurEndure() {
//		return item == null ? template.getMaxEndure() : item.getCurEndure();
//	}
	
	/**
	 * 生成道具剩余使用时限，显示“还可以使用xx天”或“还可以使用不足一天”或“该道具已经过期”
	 * 
	 * @return
	 */
//	public String getExpireDesc() {
//		int leftDaysOfValidity = countLeftDaysOfValidity();
//		int leftDaysOfExpireDate = countLeftDaysOfExpireDate();
//		int leftDays = leftDaysOfValidity < leftDaysOfExpireDate ? leftDaysOfValidity
//				: leftDaysOfExpireDate;
//		String desc = "";
//		LangService ls = Globals.getLangService();
//		if (leftDays == Integer.MAX_VALUE) {
//			return "";
//		} else if (leftDays > 0) {
//			desc = ls.readSysLang(LangConstants.ITEM_LEFT_DAYS, leftDays);
//		} else if (leftDays == 0) {
//			desc = ls.readSysLang(LangConstants.ITEM_LESS_THAN_ONE_DAY);
//		} else if (leftDays < 0) {
//			desc = ls.readSysLang(LangConstants.ITEM_EXPIRED);
//		}
//		return desc;
//	}

//	private int countLeftDaysOfValidity() {
//		int validity = template.getValidity();
//		if (validity <= 0) {
//			return Integer.MAX_VALUE;
//		} else {
//			return validity / 24;
//		}
//	}
//
//	private int countLeftDaysOfExpireDate() {
//		long expireTm = template.getExpiredTime();
//		if (expireTm >= Expireable.CAN_USE_FOREVER) {
//			return Integer.MAX_VALUE;
//		} else {
//			long leftDays = (expireTm - Globals.getTimeService().now())
//					/ TimeUtils.DAY;
//			return (int) leftDays;
//		}
//	}


	public int getRarity() {
		return template.getRarity().index;
	}
	

	
	private short getTemplateBindMode() {
		BindMode bindMode = template.getBindMode();
		if (bindMode == BindMode.GET_BIND) {
			return BIND_MODE_GET_BIND;
		} else if (bindMode == BindMode.USE_BIND) {
			if (template.isEquipment()) {
				return BIND_MODE_WEAR_BIND;
			} else {
				return BIND_MODE_USE_BIND;
			}
		} else {
			return BIND_MODE_NULL;
		}
	}

	private AttrDescBase[] getBaseAttrs() {
		if (!template.isEquipment()) {
			return new AttrDescBase[0];
		}
		List<AttrDescBase> baseList = new ArrayList<AttrDescBase>();
		List<AmendTriple> baseTuples = null;
		
//		if (item == null) {
//			EquipItemTemplate equipTmpl = (EquipItemTemplate) template;
//			baseTuples = equipTmpl.getBaseAmends();
//		} else {
//			// 从道具实例上取经过修正的基础属性
//			baseTuples = item.getBaseAmends();
//			
//		}
		for (AmendTriple tuple : baseTuples) {
			AttrDescBase desc = new AttrDescBase();
			Amend amend = tuple.getAmend();
			short key = (short) amend.getPropertyType().genPropertyKey(amend
					.getProperytIndex());
			desc.setKey(key);
			String descValue = tuple.getMethod().formatDesc(
					tuple.getVariationValue());
			desc.setMainValue(descValue);
			baseList.add(desc);
		}
		return baseList.toArray(new AttrDescBase[baseList.size()]);
	}

	private AttrDescAddon[] getAddonAttrs() {
		return new AttrDescAddon[0];
	}

	private AttrDescSpec[] getSpecAttrs() {
		return new AttrDescSpec[0];
	}
	
	private int getEnhanceLevel() {
		if (item == null) {
			return ItemDef.INIT_ENHANCE_LEVEL;
		} else if (item.isEquipment()) {
			EquipmentFeature equipFeature = (EquipmentFeature) item.getFeature();
			return equipFeature.getEnhanceLevel();
		} else {
			return ItemDef.INIT_ENHANCE_LEVEL;
		}
	}
	
	
	@Override
	public String toString() {
		return "CommonItemBuilder [count=" + count + ", template=" + template
				+ ", item=" + item + ", commonItem=" + commonItem + "]";
	}
	
}
