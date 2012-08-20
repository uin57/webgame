package com.pwrd.war.gameserver.item.container;

import static com.pwrd.war.gameserver.item.ItemDef.Position.WEAPON;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.LogReasons.EquipmentLogReason;
import com.pwrd.war.common.constants.RoleConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.Sex;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.i18n.constants.ItemLangConstants_20000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindMode;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.EquipmentFeature;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.msg.GCRemoveItem;
import com.pwrd.war.gameserver.item.operation.AbstractUseItemOperation;
import com.pwrd.war.gameserver.item.template.EquipItemTemplate;
import com.pwrd.war.gameserver.pet.Pet;


/**
 * 武将身上的装备包
 * 
 */
public class PetBodyEquipBag extends CommonBag {
	/** 佩戴者 */
	private final Pet wearer;
	public PetBodyEquipBag(Human owner,Pet pet) {
		super(owner, BagType.PET_EQUIP, RoleConstants.PET_EQUIP_BAG_CAPACITY);
		this.wearer = pet;		
	}
	/** 部位与穿戴装备的对应关系 */
	private final  EnumMap<Position, Item> equips = new EnumMap<Position, Item>(Position.class);
	/**
	 * 获取身上全部装备
	 * @author xf
	 */
	public EnumMap<Position, Item> getAllEquips(){
		return this.equips;
	}
	
	/**
	 * 初始化时调用 
	 */
	public void initEquip(Item item){
		equips.put(item.getPosition(), item);
	}
	
	/**
	 * 穿装备 
	 */
	public boolean equipItem(Item equipment, ShoulderBag userBag){
		//检查是否已经穿上
		if(!StringUtils.isEmpty(equipment.getWearerId())){
			this.owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.WEARED_THIS_EQUIP);
			return false;
		}
		//职业，性别，绑定等判断
		if(equipment.getTemplate().getSexLimit() != Sex.NONE.getCode() &&
				equipment.getTemplate().getSexLimit() != this.wearer.getSex().getCode()){
			this.owner.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.SEX_ERROR);
			return false;
		}
		//有职业限制
		if( equipment.getTemplate().getVocationLimit() != VocationType.NONE.getCode()){
			//看看有没有id限制
			EquipItemTemplate eq = (EquipItemTemplate) equipment.getTemplate();
			if(eq.getFigureLimit() != 0){//限制
				if(!StringUtils.equals(""+eq.getFigureLimit() ,wearer.getTemplate().getPetSn())){
					this.owner.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.PETTYPE_ERROR);
					return false;
				}
			}else{//不限制
				if(equipment.getTemplate().getVocationLimit() != this.wearer.getVocationType().getCode()){
					this.owner.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.VOCATION_ERROR);
					return false;
				}
			} 
			
		}
		if(equipment.getTemplate().getNeedLevel() > this.wearer.getLevel()){
//			this.owner.sendErrorMessage("等级不够！");
//			return false;
		}
		Item item = this.equips.get(equipment.getPosition());
		if(item != null){
			//脱下
			boolean rs = this.swapEquip(item, equipment, userBag);
			if(rs){
				equipment.setWearerId(this.wearer.getUUID());
				item.setWearerId("");
				//判断绑定类型是否绑定
				if(equipment.getBindMode() == BindMode.USE_BIND && equipment.getBindStatus() == BindStatus.NOT_BIND){
					equipment.setBindStatus(BindStatus.BIND_YET);
				}
				this.equips.put(equipment.getPosition(), equipment);

				this.wearer.calcProps(); 
				this.owner.sendMessage(equipment.getUpdateMsgAndResetModify());
				this.owner.sendMessage(item.getUpdateMsgAndResetModify());
				
				// LOG
				int enhanceLevel = ((EquipmentFeature) equipment.getFeature())
						.getEnhanceLevel();
				Globals.getLogService().sendEquipmentLog(owner,
						EquipmentLogReason.PET_EQ, null,
						EquipmentLogReason.PET_EQ.reason,
						equipment.getTemplate().getItemSn(), enhanceLevel, wearer.getTemplate().getName(),
						Globals.getTimeService().now());
				
				return true;
			}
			return false;
		}
		GCRemoveItem msg = new  GCRemoveItem((short) equipment.getBagType().index,
				(short) equipment.getIndex(), wearer.getUUID());
		boolean rs = CommonBag.move(equipment, userBag, this);
		if(!rs){
			this.owner.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.SYSTEM_ERROR);
			return false;
		}
		equipment.setWearerId(this.wearer.getUUID());
		//判断绑定类型是否绑定
		if(equipment.getBindMode() == BindMode.USE_BIND && equipment.getBindStatus() == BindStatus.NOT_BIND){
			equipment.setBindStatus(BindStatus.BIND_YET);
		}
		this.equips.put(equipment.getPosition(), equipment);
		//告诉人物重新计算属性
		this.wearer.calcProps();
		//发消息
		this.owner.sendMessage(msg);
		this.owner.sendMessage(equipment.getUpdateMsgAndResetModify());
		
		
		// LOG
		int enhanceLevel = ((EquipmentFeature) equipment.getFeature())
				.getEnhanceLevel();
		Globals.getLogService().sendEquipmentLog(owner,
				EquipmentLogReason.PET_EQ, null,
				EquipmentLogReason.PET_EQ.reason,
				equipment.getTemplate().getItemSn(), enhanceLevel, wearer.getTemplate().getName(),
				Globals.getTimeService().now());
		return true;		 
	} 
	/**
	 * 脱去装备 
	 */
	public boolean unEquipItem(Item equipment, ShoulderBag toBag, Item toItem){
		Item item = this.equips.get(equipment.getPosition());
		if(item == null){ 
			this.owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_NOT_EXIST);
			return false;
		}
		GCRemoveItem msg = new  GCRemoveItem((short) equipment.getBagType().index,
				(short) equipment.getPosition().index, wearer.getUUID());
		boolean rs = false;
		if(toItem == null){
			rs = CommonBag.move(equipment, this, toBag);
		}else{
			rs = CommonBag.move(equipment, this, toBag, toItem.getIndex());
		}
		 
		if(!rs){
			this.owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.BAG_FULL,
					Globals.getLangService().read(toBag.getBagType().getNameLangId()));
			return false;
		}
		equipment.setWearerId("");
		this.equips.remove(equipment.getPosition());
		//告诉人物重新计算属性
		this.wearer.calcProps();
		this.owner.sendMessage(msg);
		this.owner.sendMessage(equipment.getUpdateMsgAndResetModify());
		
		// LOG
		int enhanceLevel = ((EquipmentFeature) equipment.getFeature())
				.getEnhanceLevel();
		Globals.getLogService().sendEquipmentLog(owner,
				EquipmentLogReason.PET_UNEQ, null,
				EquipmentLogReason.PET_UNEQ.reason,
				equipment.getTemplate().getItemSn(), enhanceLevel, wearer.getTemplate().getName(),
				Globals.getTimeService().now());
		return true;
	}
	
	/**
	 * 将身上的装备和背包的装备互换，index不变
	 * @author xf
	 */
	public boolean  swapEquip(Item equipInHuman, Item equipInBag, ShoulderBag userBag){
		//先将背包 的移动到装备包，然后将身上的移动到身上
		int index = equipInBag.getIndex();
		boolean rs = CommonBag.move(equipInBag, userBag, this);
		if(!rs){
			this.owner.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.SYSTEM_ERROR);
			return false;
		}
		rs = CommonBag.move(equipInHuman, this, userBag, index);
		if(!rs){
			this.owner.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.SYSTEM_ERROR);
			return false;
		}
		
		// LOG
		int enhanceLevel = ((EquipmentFeature) equipInHuman.getFeature())
				.getEnhanceLevel();
		Globals.getLogService().sendEquipmentLog(owner,
				EquipmentLogReason.PET_SWAP, null,
				EquipmentLogReason.PET_SWAP.reason,
				equipInHuman.getTemplate().getItemSn(), enhanceLevel, wearer.getTemplate().getName(),
				Globals.getTimeService().now());
		
		return true;
	}
	
	/**
	 * 检测装备是否有效，即穿上之后装备的各种属性是否能够对装备者起作用
	 * 
	 * @param equip
	 * @return
	 */
	public boolean isEffective(Item equip) {
		if (Item.isEmpty(equip) || !equip.isEquipment()) {
			return false;
		}
		// 检查耐久
//		if (equip.getCurEndure() <= 0) {
//			return false;
//		}
		// 重新验证一般使用道具条件
		if (!AbstractUseItemOperation.checkCommonConditions(wearer, equip)) {
			return false;
		}
		
		if(equip.getSex() != RoleConstants.NOSEX)
		{
			//TODO
//			if(equip.getSex() == RoleConstants.MALE && wearer.getSex() != RoleConstants.MALE)
//			{
//				wearer.getOwner().sendErrorMessage(LangConstants.ITEM_USEFAIL_SEX);
//				return false;
//			}
//			if(equip.getSex() == RoleConstants.FEMALE && wearer.getSex() != RoleConstants.FEMALE)
//			{
//				wearer.getOwner().sendErrorMessage(LangConstants.ITEM_USEFAIL_SEX);
//				return false;
//			}	
		}
		
		return true;
	}

	
	
	@Override
	public void onLoad() {
	}
	
	
	/**
	 * 玩家身体上的组件变化了,重新计算玩家身上穿的东西
	 */
	@Override
	public void onChanged() {
		updateEquipEffect();
	}
	
	/**
	 * 刷新装备修正效果
	 */
	private void updateEquipEffect() {
//		wearer.getPropertyManager().updateProperty(RolePropertyManager.PROP_FROM_MARK_EQUIP);
//		wearer.snapChangedProperty(true);
	}
	
	
	/**
	 * 根据装备的位置获取在玩家身上对应的装备
	 * 
	 * @param pos
	 *            装备位置
	 * @return
	 */
	public Item getByPosition(Position pos) {
		List<Item> list = new ArrayList<Item>();
		boolean hasEmpty = false;
		int emptyIndex = 0;
//		for (int i = 0; i < INDEX2POS.length; i++) {
//			if (INDEX2POS[i] != pos) {
//				continue;
//			}
//			Item item = getByIndex(i);
//			if (item == null) {
//				// 这种情况不应该发生
//				continue;
//			}
//			list.add(item);
//			// 看看有没有空位
//			if (item.isEmpty()) {
//				hasEmpty = true;
//				emptyIndex = i;
//				break;
//			}
//		}
		if (list.isEmpty()) {
			return null;
		}
		if (hasEmpty) {
			// 有空位尽量返回空位
			return items[emptyIndex];
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 根据装备的索引获取其对应的装备位置
	 * 
	 * @param index
	 * @return
	 */
//	public ItemDef.Position getPosByIndex(int index) {
//		if (index >= INDEX2POS.length || index < 0) {
//			return Position.NULL;
//		}
//		return INDEX2POS[index];
//	}
	
	/**
	 * 是否装备了武器
	 * 
	 * @return
	 */
	public boolean isHoldingWeapon() {
		Item weapon = getByPosition(WEAPON);
		if (!Item.isEmpty(weapon) && isEffective(weapon)) {
			return true;
		} else {
			return false;
		}
	}
	

	/**
	 * 返回当前身上所有生效的装备
	 * 
	 * @return
	 */
	public List<Item> getEffectiveItems() {
		// 查找有效的装备
		List<Item> effectives = new ArrayList<Item>(RoleConstants.PET_EQUIP_BAG_CAPACITY);
		for (Item equip : items) {
			if (!Item.isEmpty(equip) && isEffective(equip)) {
				effectives.add(equip);
			}
		}
		return effectives;
	}
	
	/**
	 * 返回当前身上所有装备的总数量
	 * @return
	 */
	public int getAllItemsCount() {
		int count = 0;
		for (Item equip : items) {
			if(!Item.isEmpty(equip)) count++;
		}
		return count;
	}
	
	/**
	 * 返回所有装备列表
	 * @return
	 */
	public List<Item> getAllItems() {
		List<Item> items = new ArrayList<Item>(RoleConstants.PET_EQUIP_BAG_CAPACITY);
		for (Item equip : items) {
			if(!Item.isEmpty(equip)) {
				items.add(equip);
			}
		}
		return items;
	}
	
 
 
	
	public void addOrRemovePosition(Position position, Item equipment){
		if(equipment != null){
			this.equips.put(position, equipment);
		}else{
			this.equips.remove(position);
		} 
	}

}
