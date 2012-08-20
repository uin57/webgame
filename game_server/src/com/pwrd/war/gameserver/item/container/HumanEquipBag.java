package com.pwrd.war.gameserver.item.container;

import java.util.EnumMap;

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


/**
 * 玩家的装备包
 * 
 */
public class HumanEquipBag extends CommonBag {
	
	
	public HumanEquipBag(Human owner  ) {
		super(owner, 
				com.pwrd.war.gameserver.common.container.Bag.BagType.HUMAN_EQUIP, 
				RoleConstants.EQUIP_MAX + 1);//多1个格子用来当临时替换装备用 
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
	 * 获取当前穿戴的武器的sn
	 * @author xf
	 */
	public Item getWeapon(){
		Item item = equips.get(Position.WEAPON);
		return item;
	}
	
	/**
	 * 初始化时调用
	 * @author xf
	 */
	public void initEquip(Item item){
		equips.put(item.getPosition(), item);
	}
	
	/**
	 * 穿装备
	 * @author xf
	 */
	public boolean equipItem(Item equipment, ShoulderBag userBag){
//		boolean result = false;
		//检查是否已经穿上
		if(!StringUtils.isEmpty(equipment.getWearerId())){
			this.owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.WEARED_THIS_EQUIP);
			return false;
		}
		//职业，性别，绑定等判断
		if(equipment.getTemplate().getSexLimit() != Sex.NONE.getCode() && 
				equipment.getTemplate().getSexLimit() != this.owner.getSex().getCode()){
			this.owner.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.SEX_ERROR);
			return false;
		}
		if(equipment.getTemplate().getVocationLimit() != VocationType.NONE.getCode() &&
				equipment.getTemplate().getVocationLimit() != this.owner.getVocationType().getCode()){
			this.owner.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.VOCATION_ERROR);
			return false;
		}
		if(equipment.getTemplate().getNeedLevel() > this.owner.getLevel()){
			this.owner.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.LEVEL_NOT_REACH);
			return false;
		}
		Item item = this.equips.get(equipment.getPosition());
		if(item != null){
			//脱下 
			boolean rs = this.swapEquip(item, equipment, userBag);
			if(rs){
				equipment.setWearerId(this.owner.getUUID());
				item.setWearerId("");
				//判断绑定类型是否绑定
				if(equipment.getBindMode() == BindMode.USE_BIND && equipment.getBindStatus() == BindStatus.NOT_BIND){
					equipment.setBindStatus(BindStatus.BIND_YET);
				}
				this.addOrRemovePosition(equipment.getPosition(), equipment);
				//告诉人物重新计算属性
				this.owner.calcProps(); 
				this.owner.sendMessage(equipment.getUpdateMsgAndResetModify());
				this.owner.sendMessage(item.getUpdateMsgAndResetModify());
				
				// LOG
				int enhanceLevel = ((EquipmentFeature) equipment.getFeature())
						.getEnhanceLevel();
				Globals.getLogService().sendEquipmentLog(owner,
						EquipmentLogReason.HUMAN_EQ, null,
						EquipmentLogReason.HUMAN_EQ.reason,
						equipment.getTemplate().getItemSn(), enhanceLevel, "",
						Globals.getTimeService().now());
				
				return true;
			}
			return false;
			
		}else{
			GCRemoveItem msg = new  GCRemoveItem((short) equipment.getBagType().index,
					(short) equipment.getIndex(), owner.getUUID());
			boolean rs = CommonBag.move(equipment, userBag, this);
			if(!rs){
				this.owner.sendErrorMessage(CommonLangConstants_10000.SYSTEM_ERROR);
				return false;
			}
			equipment.setWearerId(this.owner.getUUID());
			//判断绑定类型是否绑定
			if(equipment.getBindMode() == BindMode.USE_BIND && equipment.getBindStatus() == BindStatus.NOT_BIND){
				equipment.setBindStatus(BindStatus.BIND_YET);
			}
			this.addOrRemovePosition(equipment.getPosition(), equipment);
			//告诉人物重新计算属性
			this.owner.calcProps();
			//发消息
			this.owner.sendMessage(msg);
			this.owner.sendMessage(equipment.getUpdateMsgAndResetModify());
			
			// LOG
			int enhanceLevel = ((EquipmentFeature) equipment.getFeature())
					.getEnhanceLevel();
			Globals.getLogService().sendEquipmentLog(owner,
					EquipmentLogReason.HUMAN_EQ, null,
					EquipmentLogReason.HUMAN_EQ.reason,
					equipment.getTemplate().getItemSn(), enhanceLevel, "",
					Globals.getTimeService().now());
			
			return true;	
		} 
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
				EquipmentLogReason.HUMAN_SWAP, null,
				EquipmentLogReason.HUMAN_SWAP.reason,
				equipInHuman.getTemplate().getItemSn(), enhanceLevel, "",
				Globals.getTimeService().now());
		return true;
	}
	/**
	 * 脱去装备到指定格子，如果为空则按顺序找一个格子
	 * @author xf
	 */
	public boolean unEquipItem(Item equipment, ShoulderBag toBag, Item toItem){
		Item item = this.equips.get(equipment.getPosition());
		if(item == null){ 
			this.owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_NOT_EXIST);
			return false;
		}
		GCRemoveItem msg = new  GCRemoveItem((short) equipment.getBagType().index,
				(short) equipment.getPosition().index, owner.getUUID());
		boolean rs = false;
		if(toItem == null){
			rs = CommonBag.move(equipment, this, toBag);
		}else{
			rs = CommonBag.move(equipment, this, toBag, toItem.getIndex());
		}
		
		if(!rs){
			this.owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.BAG_FULL
					,Globals.getLangService().read(toBag.getBagType().getNameLangId()));
			return false;
		}
		equipment.setWearerId("");
		this.addOrRemovePosition(equipment.getPosition(), null);
		//告诉人物重新计算属性
		this.owner.calcProps();
		this.owner.sendMessage(msg);
		this.owner.sendMessage(equipment.getUpdateMsgAndResetModify());
		
		// LOG
		int enhanceLevel = ((EquipmentFeature) equipment.getFeature())
				.getEnhanceLevel();
		Globals.getLogService().sendEquipmentLog(owner,
				EquipmentLogReason.HUMAN_UNEQ, null,
				EquipmentLogReason.HUMAN_UNEQ.reason,
				equipment.getTemplate().getItemSn(), enhanceLevel, "",
				Globals.getTimeService().now());
		
		return true;
	}
	
	public void addOrRemovePosition(Position position, Item equipment){
		if(equipment != null){
			this.equips.put(position, equipment);
		}else{
			this.equips.remove(position);
		}
		if(position == Position.WEAPON){
			//武器别换了，告诉场景改变形象
			this.owner.getPlayer().sendInfoChangeMessageToScene();
		}
	}
	@Override
	public void onChanged() { 
		super.onChanged();
		
	}
	
 
}
