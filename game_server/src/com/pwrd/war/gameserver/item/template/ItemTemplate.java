package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.common.enums.Sex;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.human.wealth.Bindable;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindMode;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef;
import com.pwrd.war.gameserver.item.ItemDef.IdentityType;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.ItemDef.Rarity;
import com.pwrd.war.gameserver.item.ItemDef.Type;
import com.pwrd.war.gameserver.item.ItemFeature;


/**
 * 道具模板基类，所有道具类型都继承这一个模板
 * 
 * 
 */
@ExcelRowBinding
public abstract class ItemTemplate extends ItemTemplateVO{

	public static final String SHEET_NAME = "items";
	
	protected final static int START_INDEX = 20;
	
	/** 道具身份类型 */
	private ItemDef.IdentityType idendityType;
	
	/** 道具类型 */
	private ItemDef.Type itemType;
	
	/** 道具将被放在哪个包中 */
	private BagType bagType;
	
	/** 稀有程度 */
	private ItemDef.Rarity rarity;
	
	/** 出售给商店的货币类型 */
	private Currency sellCurrency;
	
	/** 绑定模式 */
	private BindMode bindMode;
	
	private Sex sexLimit;
	
	/** 物品模板的附加属性 */
	private ItemTemplateFeature templateFeature;
	
	public ItemTemplate() {
		bagType = BagType.PRIM;
	}
 
	
	@Override
	public void setIdentityType(int typeId) {
		super.setIdentityType(typeId);
		idendityType = ItemDef.IdentityType.valueOf(this.identityType);
		if (idendityType == null || idendityType == IdentityType.NULL) {
			throw new TemplateConfigException(this.getSheetName(), getId(), String
					.format("道具类型Id配置错误 typeId=%d", typeId));
		}
	}

	public ItemDef.IdentityType getIdendityType() {
		return idendityType;
	}
	
	/* 
	 * 稀有度
	 */
	@Override
	public void setQuantity(int quantity) { 
		super.setQuantity(quantity);
		this.rarity = Rarity.valueOf(this.quantity);
		if (rarity == null) {
			throw new TemplateConfigException(this.getSheetName(), getId(), String
					.format("道具稀有程度配置错误 稀有程度=%d", quantity));
		}
	} 
	/* 
	 * 稀有度
	 */
	public Rarity getRarity() {
		return rarity;
	}
 
 

	@Override
	public void setSexLimit(int sex) {
		super.setSexLimit(sex);
		if (super.sexLimit == Sex.NONE.getCode()) {
			return;
		} else {
			if (super.sexLimit != Sex.MALE.getCode()
					&& super.sexLimit != Sex.FEMALE.getCode()) {
				throw new TemplateConfigException(this.getSheetName(), getId(),
						"道具性别限制错误 性别限制=" + this.sexLimit);
			}
			sexLimit = Sex.getByCode(super.sexLimit);
		}
	}
	
	@Override
	public void setMaxOverlap(int maxOverlap) {
		super.setMaxOverlap(maxOverlap);
		if (this.maxOverlap < 1) {
			throw new TemplateConfigException(this.getSheetName(), getId(),
					"叠加上限不可以小于1 叠加上限=" + maxOverlap);
		}
	}

	/**
	 * 是否能够叠加，我们把最大叠加数超过1的都叫做可叠加
	 * 
	 * @return
	 */
	public boolean canOverlap() {
		return maxOverlap > 1;
	}
	 
	public void setTypeId(int typeId){
		this.itemType = Type.valueOf(typeId + this.getIdentityType() * 10);
	}/*{
		super.setTypeId(typeId);
		itemType = ItemDef.Type.valueOf(this.typeId);
		if (itemType == null || itemType == ItemDef.Type.NULL) {
			throw new TemplateConfigException(this.getSheetName(), getId(), String
					.format("道具类型Id配置错误 类型=%d", typeId));
		}
	}
	*/
	/**
	 * 取得道具或者装备具体类别
	 * @author xf
	 */
	public ItemDef.Type getItemType() {
		return itemType;
	}

	
	@Override
	public void check() throws TemplateConfigException {
		// 叠加和道具的有效时间不能兼容，设置有效时间叠加个数就不能>1
//		if (    maxOverlap > 1) {
//			throw new TemplateConfigException(this.getSheetName(), getId(),
//					"如果设置了道具有效时间，这个道具堆叠一个格子数量就不能大于1，因为一个格子中的道具只能有一个计时");
//		}
	}
	
	@Override
	public void patchUp() {
		
		
		if (Loggers.itemLogger.isDebugEnabled()) {
			Loggers.itemLogger.debug(this.toString());
		}
	}
	
	public ItemTemplateFeature getTemplateFeature() {
		return templateFeature;
	}

	/**
	 * 装备位置，非装备为Position.NULL
	 * 
	 * @return
	 */
	public abstract Position getPosition();
	
	/**
	 * 是否为装备，这种结构下很常用
	 * 
	 * @return
	 */
	public abstract boolean isEquipment();
	
	/**
	 * 是否能够被使用
	 * @return
	 */
	public abstract boolean getCanBeUsed();
	
	/**
	 * 根据模板初始化ItemFeature
	 * 
	 * @return
	 */
	public abstract ItemFeature initItemFeature(Item item);


	@Override
	public void setBindModeId(int bindModeId) { 
		super.setBindModeId(bindModeId); 
		this.bindMode = BindMode.valueOf(this.bindModeId);
		if (bindMode == null) {
			throw new TemplateConfigException(this.getSheetName(), getId(), String
					.format("道具绑定模式配置错误 绑定模式=%d", this.bindModeId));
		}
	}


	public BindMode getBindMode() {
		return bindMode;
	}
	/**
	 * 得到当前绑定状态经过oper操作后的绑定状态
	 * 
	 * @param oper
	 * @return
	 */
	public BindStatus getBindStatusAfterOper(BindStatus curBind,
			Bindable.Oper oper) {
		return getBindMode().getBindStatusAfterOper(curBind, oper);
	}


	public BagType getBagType() {
		return bagType;
	}


	public Currency getSellCurrency() {
		return sellCurrency;
	}

	



	
	
	
}
