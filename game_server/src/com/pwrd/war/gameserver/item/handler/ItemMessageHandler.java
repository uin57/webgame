package com.pwrd.war.gameserver.item.handler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.ItemLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.common.LogReasons.XinghunLogReason;
import com.pwrd.war.common.ModuleMessageHandler;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.model.xinghun.XinghunXilianInfo;
import com.pwrd.war.core.enums.QueryInfoType;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.common.event.DayTaskEvent;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.i18n.constants.ItemLangConstants_20000;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.dayTask.DayTaskType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.OpenFunction;
import com.pwrd.war.gameserver.item.EquipmentFeature;
import com.pwrd.war.gameserver.item.Inventory;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef;
import com.pwrd.war.gameserver.item.ItemDef.FreezeState;
import com.pwrd.war.gameserver.item.ItemDef.XinghunProps;
import com.pwrd.war.gameserver.item.ItemFeature;
import com.pwrd.war.gameserver.item.XinghunFeature;
import com.pwrd.war.gameserver.item.container.AbstractItemBag;
import com.pwrd.war.gameserver.item.container.ShoulderBag;
import com.pwrd.war.gameserver.item.msg.CGDropItem;
import com.pwrd.war.gameserver.item.msg.CGEnhanceEquip;
import com.pwrd.war.gameserver.item.msg.CGEnhanceEquipInfo;
import com.pwrd.war.gameserver.item.msg.CGGetReelInfo;
import com.pwrd.war.gameserver.item.msg.CGItemCancelFreeze;
import com.pwrd.war.gameserver.item.msg.CGItemFreeze;
import com.pwrd.war.gameserver.item.msg.CGMoveItem;
import com.pwrd.war.gameserver.item.msg.CGOpenBagPageCost;
import com.pwrd.war.gameserver.item.msg.CGReqBaginfo;
import com.pwrd.war.gameserver.item.msg.CGReqItemInfo;
import com.pwrd.war.gameserver.item.msg.CGSellItem;
import com.pwrd.war.gameserver.item.msg.CGSpandBag;
import com.pwrd.war.gameserver.item.msg.CGSpandBagGold;
import com.pwrd.war.gameserver.item.msg.CGSplitItem;
import com.pwrd.war.gameserver.item.msg.CGTidyBag;
import com.pwrd.war.gameserver.item.msg.CGUseItem;
import com.pwrd.war.gameserver.item.msg.CGXinghunActivate;
import com.pwrd.war.gameserver.item.msg.CGXinghunAdd;
import com.pwrd.war.gameserver.item.msg.CGXinghunDestruct;
import com.pwrd.war.gameserver.item.msg.CGXinghunPiliangXilian;
import com.pwrd.war.gameserver.item.msg.CGXinghunPiliangXilianRemain;
import com.pwrd.war.gameserver.item.msg.CGXinghunRed;
import com.pwrd.war.gameserver.item.msg.CGXinghunXilian;
import com.pwrd.war.gameserver.item.msg.CommonItemBuilder;
import com.pwrd.war.gameserver.item.msg.GCBagUpdate;
import com.pwrd.war.gameserver.item.msg.GCGetReelInfo;
import com.pwrd.war.gameserver.item.msg.GCItemUpdate;
import com.pwrd.war.gameserver.item.msg.GCOpenBagPageCost;
import com.pwrd.war.gameserver.item.msg.GCRemoveItem;
import com.pwrd.war.gameserver.item.msg.GCResItemInfo;
import com.pwrd.war.gameserver.item.msg.GCSpandBagGold;
import com.pwrd.war.gameserver.item.msg.GCXinghunActivate;
import com.pwrd.war.gameserver.item.msg.GCXinghunPiliangXilian;
import com.pwrd.war.gameserver.item.msg.GCXinghunPiliangXilianRemain;
import com.pwrd.war.gameserver.item.msg.GCXinghunXilian;
import com.pwrd.war.gameserver.item.msg.ItemMessageBuilder;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.item.template.OpenBagTemplate;
import com.pwrd.war.gameserver.item.template.ReelTemplate;
import com.pwrd.war.gameserver.item.template.XinghunParaTemplate;
import com.pwrd.war.gameserver.item.template.XinghunXilianTemplate;
import com.pwrd.war.gameserver.item.xinghun.XinghunAttributeVo;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.tower.HumanTowerInfo;
import com.pwrd.war.gameserver.tower.TowerInfoManager;
import com.pwrd.war.gameserver.tower.template.TowerTemplate;

/**
 * 
 * 物品消息处理器
 * 
 * 
 */
public class ItemMessageHandler implements ModuleMessageHandler {
	
	private static int JIHUO = 1;
	private static int LOCK = 0;
	private static int WEIJIHUO = -1;
	private static int XILIAN_TIMES = 10;
	
	private Map<String, Map<Integer, XinghunXilianInfo>> xilianMap = new HashMap<String, Map<Integer,XinghunXilianInfo>>();

	public ItemMessageHandler() {
	}

	/**
	 * 使用道具
	 * 
	 * @param player
	 * @param msg
	 */
	public void handleUseItem(Player player, CGUseItem msg) {
//		this.handleUseItem(player, msg.getBagId(), msg.getIndex(), msg.getTargetUuid(), msg.getWearId(), msg.getParams());
		Human human = player.getHuman();
		Inventory inventory = human.getInventory();
		int bagId = msg.getBagId();
		BagType bagType = BagType.valueOf(bagId);
		if (bagType == null || bagType == BagType.NULL) {
			// 估计是作弊了
			player.sendErrorPromptMessage(ItemLangConstants_20000.BAG_TYPE_ERROR);
			return;
		}
		int index = msg.getIndex();
		Item item = null; 
		if(bagType == BagType.PRIM)
		{
			// 由于后面判断了必须是主背包,所以wearerId是0l
			item = inventory.getItemByIndex(bagType, "", index);
//			 
		}
		else if(bagType == BagType.HUMAN_EQUIP)
		{
			item = inventory.getItemByIndex(bagType, "", index); 
		}
		else if(bagType == BagType.PET_EQUIP)
		{
			item = inventory.getItemByIndex(bagType, msg.getWearId(), index); 
		}
		if (Item.isEmpty(item)) {
			player.sendErrorPromptMessage(ItemLangConstants_20000.ITEM_NOT_EXIST); 
			return;
		}
		item.use(msg.getTargetUuid(), msg.getParams());
	}

	
	/**
	 * 丢弃道具
	 * 
	 * @param player
	 * @param cgDropItem
	 */
	public void handleDropItem(Player player, CGDropItem cgDropItem) {
		BagType bagType = BagType.valueOf(cgDropItem.getBagId());
		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		int index = cgDropItem.getIndex();
		if (index < 0) {
			return;
		}
		player.getHuman().getInventory().dropItem(bagType, index);
	}

	/**
	 * 移动道具
	 * 
	 * @param player
	 * @param cgMoveItem
	 */
	public void handleMoveItem(Player player, CGMoveItem cgMoveItem) {
		int fromBagId = cgMoveItem.getFromBagId();
		int fromIndex = cgMoveItem.getFromIndex();
		int toBagId = cgMoveItem.getToBagId();
		int toIndex = cgMoveItem.getToIndex();
		String wearerId = cgMoveItem.getWearerId();
		if (fromIndex < 0 || toIndex < -1) {//-1为空位置
			return;
		}
		Human user = player.getHuman();
		BagType fromBag = BagType.valueOf(fromBagId);
		BagType toBag = BagType.valueOf(toBagId);
		if (fromBag == null || toBag == null) {
			return;
		}
		Pet wearer = null;
		if(fromBag == BagType.PET_EQUIP || toBag == BagType.PET_EQUIP)
		{
			// 必须有佩戴者,且是当前Player的Human
			wearer = user.getPetManager().getPetByUuid(wearerId);
			if(wearer == null)
			{
				return;
			}			
		}		
		else
		{
			wearerId = "";
		}
		
		user.getInventory().moveItem(fromBag, fromIndex, toBag, toIndex, wearer, wearerId);
	}

	/**
	 * 请求背包数据
	 * @param player
	 * @param cgReqBaginfo
	 */
	public void handleReqBaginfo(Player player, CGReqBaginfo cgReqBaginfo) {
		int bagId = cgReqBaginfo.getBagId();
		String wearerId = cgReqBaginfo.getWearerId();
		
		Human human = player.getHuman();
		if(human == null)
		{
			return;
		}
		
		BagType bagType = BagType.valueOf(bagId);
		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		
		if(bagType == BagType.PRIM)
		{
			GCBagUpdate updateMsg = human.getInventory().getPrimBagInfoMsg();
			updateMsg.setWearerId("");
//			updateMsg.setWearerId(0l);
			player.sendMessage(updateMsg);
			
		}
		else if(bagType == BagType.PET_EQUIP)
		{
			Pet pet = human.getPetManager().getPetByUuid(wearerId);
			if(pet == null)
			{
				return;
			}
			GCBagUpdate updateMsg = human.getInventory().getPetEquipBagInfoMsg(wearerId);
			updateMsg.setWearerId(wearerId);
			player.sendMessage(updateMsg);
		}
		
	}

	/**
	 * 拆分道具
	 * 
	 * @param player
	 * @param cgSplitItem
	 */
	public void handleSplitItem(Player player, CGSplitItem cgSplitItem) {
		int count = cgSplitItem.getCount();
		if (count <= 0) {
			return;
		}
		BagType bagType = BagType.valueOf(cgSplitItem.getBagId());
		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		int index = cgSplitItem.getIndex();
		if (index < 0) {
			return;
		}
		player.getHuman().getInventory().splitItem(bagType, index, count);
	}

	/**
	 * 整理背包
	 * 
	 * @param player
	 * @param cgTidyBag
	 */
	public void handleTidyBag(Player player, CGTidyBag cgTidyBag) {
		BagType bagType = BagType.valueOf(cgTidyBag.getBagId());
		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		player.getHuman().getInventory().tidyBag(bagType);
	}
	
	/**
	 * 请求物品信息
	 * 
	 * @param player
	 * @param cgReqItemInfo
	 */
	public void handleReqItemInfo(Player player, CGReqItemInfo cgReqItemInfo) {
		if (player == null) {
			return;
		}

		// 查询类型
		int type = cgReqItemInfo.getQueryType();
		QueryInfoType queryType = QueryInfoType.valueOf(type);

		// 物品所属者的UUID
		String ownerUUID = cgReqItemInfo.getOwnerUUID();

		// 检查物品ID是否为空
		String targetUUID = cgReqItemInfo.getTargetUUID();
		if ((queryType == QueryInfoType.PET || queryType == QueryInfoType.ITEM)
			&& (cgReqItemInfo.getTargetUUID() == null || cgReqItemInfo.getTargetUUID().equals(""))) 
		{
			return;
		}

		// 模板物品link直接使用模板创建消息
		if (queryType == QueryInfoType.TMPL_ITEM) {
			// TODO 从模板加载信息数据 ，并执行模板信息的发送
			return;
		}

		// 检查玩家否在当前GS上，如果没有则进行跨线查询
		Player itemOwner = Globals.getOnlinePlayerService()
				.getPlayer(ownerUUID);
		if (itemOwner == null) {
			// 提示玩家已下线
			player.sendErrorMessage(CommonLangConstants_10000.PLAYER_OFFLINE);
			return;
		}

		// 玩家在当前GS上
		switch (queryType) {
			case BASE_INFO_AND_EQUIPS:
				queryOtherHumanInfo(player, itemOwner);
				break;
			case PET_LIST:
				queryOtherPetList(player, itemOwner);
				break;
			case ITEM:
				queryItemInfo(player, itemOwner, targetUUID);
				break;
			case PET:
				queryPetInfo(player, itemOwner, targetUUID);
			break;
		}
	}

	/**
	 * 查询玩家信息
	 * 
	 * @param player
	 * @param other
	 */
	private void queryOtherHumanInfo(Player player, Player other) {
		
	}

	/**
	 * 查看其它玩家的宠物列表
	 * 
	 * @param player
	 * @param other
	 */
	private void queryOtherPetList(Player player, Player other) {
		
	}

	/**
	 * 查询物品信息
	 * 
	 * @param player
	 * @param owner
	 * @param targetUUID
	 */
	private void queryItemInfo(Player player, Player owner, String targetUUID) {
		// 从包裹中查找是否有该物品
		Item item = owner.getHuman().getInventory().getItemByUUID(targetUUID);
		if (null == item) {
			player.sendErrorMessage(ItemLangConstants_20000.ITEM_NOT_EXIST);
			return;
		}

		// 向客户端发送消息
		CommonItemBuilder info = new CommonItemBuilder(item);
		GCResItemInfo gcResItemInfo = new GCResItemInfo();
		gcResItemInfo.setItem(info.buildCommonItem());
		player.sendMessage(gcResItemInfo);
	}

	/**
	 * @param player
	 * @param owner
	 * @param targetUUID
	 */
	private void queryPetInfo(Player player, Player owner, String targetUUID) {

	}
 

	public void handleOpenBagPageCost(Player player, CGOpenBagPageCost cgOpenBagPageCost) 
	{
		Human human  = player.getHuman();
		if(human != null)
		{
			int page2cost = Globals.getTemplateService().get(2, OpenBagTemplate.class).getGoldCost();
			int page3cost = Globals.getTemplateService().get(3, OpenBagTemplate.class).getGoldCost();
			int page4cost = Globals.getTemplateService().get(4, OpenBagTemplate.class).getGoldCost();
			
			GCOpenBagPageCost gcOpenBagPageCost = new GCOpenBagPageCost();
			gcOpenBagPageCost.setPage2goldcost(page2cost);
			gcOpenBagPageCost.setPage3goldcost(page3cost);
			gcOpenBagPageCost.setPage4goldcost(page4cost);
			
			human.sendMessage(gcOpenBagPageCost);
		}	
	}

	public void handleSellItem(Player player, CGSellItem cgSellItem) {
		BagType bagType = BagType.valueOf(cgSellItem.getBagId());
		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		if (!AbstractItemBag.isPrimBag(bagType))
		{
			return;			
		}
		int index = cgSellItem.getIndex();
		if (index < 0) {
			return;
		}
		
		Human human  = player.getHuman();
		if(human == null)
		{
			return;
		}
		
		Item toSellItem = human.getInventory().getItemByIndex(bagType, "", index);
//		Item toSellItem = human.getInventory().getItemByIndex(bagType, 0l, index);
		if(toSellItem == null )
		{
			return;
		}

		if(toSellItem.isEquipment())
		{
			EquipmentFeature feature = (EquipmentFeature) toSellItem.getFeature();
			if(feature.getFreezeState() != ItemDef.FreezeState.NOMAL.index)
			{
				human.sendErrorMessage(ItemLangConstants_20000.CAN_NOT_SELL_FREEZE);
				return;
			}
		}
		
		ItemTemplate itemTmpl = toSellItem.getTemplate();
		if(itemTmpl == null)
		{
			return;
		}
		
		Currency currency = itemTmpl.getSellCurrency();
		if(currency == Currency.GOLD)
		{			
			return;
		}
		
		// 物品能卖多少钱应该是物品的单价*物品的数量
		int sellPrice = itemTmpl.getSellPrice()*toSellItem.getOverlap();
		if(sellPrice <= 0)
		{
			return;
		}
		
		if(toSellItem.isEquipment())
		{
			EquipmentFeature feature = (EquipmentFeature) toSellItem.getFeature();
			if(feature.getEnhanceLevel() > ItemDef.INIT_ENHANCE_LEVEL)
			{
				human.sendErrorMessage(ItemLangConstants_20000.CAN_NOT_SELL_ENHANCE_LEVEL,feature.getEnhanceLevel());				
				return;
			}
		}

		String detailReason;
		
		detailReason = ItemLogReason.SELL_COST.getReasonText();
		detailReason = MessageFormat.format(
			detailReason, 
			itemTmpl.getId(), 
			itemTmpl.getSellPrice());

		if(human.getInventory().removeItemByIndex(bagType, index, toSellItem.getOverlap(), ItemLogReason.SELL_COST, detailReason))
		{
			// 道具模版Id={0}|道具名称={1}
			detailReason = MoneyLogReason.SELL_TO_SHOP.getReasonText();
			detailReason = MessageFormat.format(
				detailReason, 
				itemTmpl.getId(), 
				itemTmpl.getName());

			boolean giveMoneySucc = human.giveMoney(sellPrice, currency, false,
					CurrencyLogReason.SELL, detailReason);

			if (!giveMoneySucc) {
				// 如果给予失败。正常情况不会发生
				Loggers.playerLogger.error(LogUtils.buildLogInfoStr(player
						.getRoleUUID(), String.format(
						"出售物品(id=%d)时给钱失败",
						itemTmpl.getId())));
				player.sendErrorMessage(ItemLangConstants_20000.SHOP_SELL_FAIL);
				return;
			}

		}
		
	}
	
	public void handleItemFreeze(Player player,CGItemFreeze cgItemFreeze) {
		String itemUUID = cgItemFreeze.getItemUUID();		
		Human human = player.getHuman();
		if(human == null)
		{
			return;
		}		
		
		Item item = human.getInventory().getItemByUUID(itemUUID);
		if(item == null)
		{
			return;
		}
		
		if(item.isEquipment()){
			EquipmentFeature equipmentFeature = (EquipmentFeature)item.getFeature();
			if(equipmentFeature.getFreezeState() != FreezeState.FREEZE.index){
				equipmentFeature.setFreezeState(FreezeState.FREEZE.index);
				equipmentFeature.setCancelTime(0l);
				
				GCItemUpdate gcItemUpdate = ItemMessageBuilder.buildGCItemInfo(item);
				human.sendMessage(gcItemUpdate);
			}
		}
	}
	
	public void handleItemCancelFreeze(Player player,CGItemCancelFreeze agItemCancelFreeze) {
		String itemUUID = agItemCancelFreeze.getItemUUID();
		handleItemCancelFreeze(player,itemUUID,TimeUtils.getAfterToday(3));
	}
	
	public void handleItemCancelFreeze(Player player,String itemUUID,Date date) {
		if(player == null || itemUUID.isEmpty()){
			return;
		}
		Human human = player.getHuman();
		if(human == null)
		{
			return;
		}		
		
		Item item = human.getInventory().getItemByUUID(itemUUID);
		if(item == null)
		{
			return;
		}
		
		if(item.isEquipment()){
			EquipmentFeature equipmentFeature = (EquipmentFeature)item.getFeature();
			if(equipmentFeature.getFreezeState() == FreezeState.FREEZE.index){
				equipmentFeature.setFreezeState(FreezeState.CANCELFREEZE.index);
				equipmentFeature.setCancelTime(date.getTime());
				
				GCItemUpdate gcItemUpdate = ItemMessageBuilder.buildGCItemInfo(item);
				human.sendMessage(gcItemUpdate);
			}
		}
	}
	
	/**
	 * 取得强化装备信息
	 */
	public void handleEnhanceEquipInfo(Player player, CGEnhanceEquipInfo msg){
	 
		player.sendMessage(Globals.getItemService().getEquipEnhanceService().getEquipInfoMsg());
	}
	/**
	 * 强化装备
	 * @author xf
	 */
	public void handleEnhanceEquip(Player player, CGEnhanceEquip msg){
		if(!OpenFunction.isopen(player.getHuman(), OpenFunction.qianghua, true)){
			return;
		}
		Globals.getItemService().enhanceEquipment(player,
				msg.getWearerId(), msg.getBagId(), msg.getIndex(),
				msg.getIsFree(), msg.getIsDouble());
		
		//发送每日任务的事件
		DayTaskEvent event = new DayTaskEvent(player.getHuman(), DayTaskType.ENHANCE.getValue());
		Globals.getEventService().fireEvent(event);
	}
//	/**
//	 * 取得当前装备强化成功概率
//	 * @author xf
//	 */
//	public void handleEnhanceEquipRate(final Player player, CGEnhanceEquipRate msg){
//		player.sendMessage(Globals.getItemService().getEquipEnhanceService().getRateMsg());
//		
//	}
 
	/**
	 * 取得扩充背包需要元宝
	 * @author xf
	 */
	public void handleSpandBagGold(final Player player, CGSpandBagGold msg){
		int num = msg.getNumber();
		BagType type = BagType.valueOf(msg.getBagType());
		Human human = player.getHuman();
		int nowSize = 0;
		if(type == BagType.PRIM){
			nowSize = human.getPropertyManager().getBagSize();
		}else if(type == BagType.DEPOT){
			nowSize = human.getPropertyManager().getDepotSize();
		}
		if(nowSize + num > 216){
			player.sendErrorMessage(ItemLangConstants_20000.BAG_EXPAND_ALL);
			return;
		}
		Map<Integer, Integer>  map = Globals.getItemService().getBagExpandGoldTemplate().get(type);
		int gold = 0;
		for(int i = nowSize; i< nowSize + num; i++){
			gold += map.get(i);
		}
		GCSpandBagGold rmsg = new GCSpandBagGold(num, gold, msg.getBagType());
		player.sendMessage(rmsg);
		
	}
	 
	/**
	 * 扩充背包
	 * @author xf
	 */
	public void handleSpandBag(final Player player, CGSpandBag msg){
		int num = msg.getNumber();
		BagType type = BagType.valueOf(msg.getBagType());
		Human human = player.getHuman();
		int nowSize = 0;
		if(type == BagType.PRIM){
			nowSize = human.getPropertyManager().getBagSize();
		}else if(type == BagType.DEPOT){
			nowSize = human.getPropertyManager().getDepotSize();
		}
		if(nowSize + num > 216){
			player.sendErrorMessage(ItemLangConstants_20000.BAG_EXPAND_ALL);
			return;
		}
		Map<Integer, Integer>  map = Globals.getItemService().getBagExpandGoldTemplate().get(type);
		int gold = 0;
		for(int i = nowSize; i< nowSize + num; i++){
			gold += map.get(i);
		}
		if(gold == 0){
			return;
		}
		boolean rs = human.hasEnoughGold(gold, true);
		if(!rs){
			return;
		}
		rs = human.costGold(gold, true, 0, CurrencyLogReason.GGZJ,
				CurrencyLogReason.GGZJ.getReasonText(), 0);
		if (!rs) {
			return;
		}
		
		int newCapacity = nowSize + num;
		
		if(type == BagType.PRIM){
			ShoulderBag primbag = human.getInventory().getPrimBag();
			primbag.resetCapacity(newCapacity);
			human.getPropertyManager().setBagSize(newCapacity);
		}else if(type == BagType.DEPOT){
			ShoulderBag depot = human.getInventory().getDepotBag();
			depot.resetCapacity(newCapacity);
			human.getPropertyManager().setDepotSize(newCapacity);
		}
		human.getPlayer().sendRightMessage(ItemLangConstants_20000.BAG_EXPAND_OK,
				Globals.getLangService().read(type.getNameLangId()),
				num);
		
	}
	
	public void handleGetReelInfo(final Player player, CGGetReelInfo msg){
		String reelSN = msg.getReelSN();
		BagType bagType = BagType.valueOf(msg.getBagId());
		ItemTemplate tmp = Globals.getItemService().getItemTempByTempId(reelSN);
		if(tmp instanceof ReelTemplate){
			ReelTemplate rtp = (ReelTemplate) tmp;
			String newequipt = rtp.getGetItemSn();
			ItemTemplate newequip = Globals.getItemService().getItemTempByTempId(newequipt);
			if(newequip != null && newequip.isEquipment()){
				Item oitem = player.getHuman().getInventory().getItemByIndex(bagType, msg.getUuid(),
						msg.getIndex());
				Item nitem = Item.newDeactivedInstance(player.getHuman(), 
						newequip, bagType,	1);
				ItemFeature of = null;
				if(oitem != null){
					of = oitem.getFeature();
				}
				ItemFeature nf = nitem.getFeature();
				if(of != null && nf != null){
					//复制feature
					of.cloneTo(nf);
				}
				Globals.getItemService().buildPropsByEnhanceLevel(nitem, 
						((EquipmentFeature)of).getEnhanceLevel());
				GCGetReelInfo rmsg = new GCGetReelInfo();
				rmsg.setItem(ItemMessageBuilder.buildGCItemInfo(nitem).getItem()[0]);
				player.sendMessage(rmsg);
			}
		}
	}
	
	/**
	 * 魂石属性激活
	 * @param player
	 * @param cgXinghunActivate
	 */
	public void handleXinghunActivate(Player player,
			CGXinghunActivate cgXinghunActivate) {
		GCXinghunActivate gcMsg = new GCXinghunActivate();
		
		int bagId = cgXinghunActivate.getBagId();
		int index = cgXinghunActivate.getIndex();

		BagType bagType = BagType.valueOf(bagId);

		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		if (bagType != BagType.XINGHUN && bagType != BagType.XIANGQIAN) {
			return;
		}

		if (index < 0) {
			return;
		}

		Human human = player.getHuman();
		if (human == null) {
			return;
		}

		Item toActItem = human.getInventory()
				.getItemByIndex(bagType, "", index);
		// Item toSellItem = human.getInventory().getItemByIndex(bagType, 0l,
		// index);
		if (toActItem == null) {
			return;
		}

		XinghunFeature feature = (XinghunFeature) toActItem.getFeature();
		
		if(feature == null){
			return;
		}
		if(feature.getPropNum() >= 3){
			return;
		}
		ItemTemplate itemTmpl = toActItem.getTemplate();
		if (itemTmpl == null) {
			return;
		}
		
		ItemDef.XinghunProps prop = feature.getAttribute();
		// 获得星魂属性模板
		XinghunParaTemplate paraTemplate = Globals.getXinghunParaService()
				.getXinghunParaMap().get(itemTmpl.getItemSn());
		XinghunAttributeVo value = feature.getPara(paraTemplate, prop);
		feature.setAddOnProps(feature.getPropNum()+1, prop, value);
		feature.setPropNum(feature.getPropNum() + 1);
		GCItemUpdate msg = toActItem.getItemInfoMsg();
		player.sendMessage(msg);
		
		// 相关背包更新
		// 同步更新装备的feature信息
		if (bagType == BagType.XIANGQIAN) {
			int eqBagId = cgXinghunActivate.getEqbagId();
			BagType eqBagType = BagType.valueOf(eqBagId);
			int eqIndex = cgXinghunActivate.getEqbagIndex();
			String wearId = cgXinghunActivate.getUuid();

			if (eqIndex < 0) {
				return;
			}

			Item weapon;
			if(eqBagType == BagType.HUMAN_EQUIP){
				weapon = human.getInventory().getEquipBag().getWeapon();
//				getItemByIndex(eqBagType, "", eqIndex);
			}else if(eqBagType == BagType.PET_EQUIP){
				weapon = human.getInventory().getBagByPet(wearId).getByIndex(eqIndex);
			}else{
				weapon = human.getInventory().getItemByIndex(eqBagType, "", eqIndex);
			}

			if (weapon == null) {
				return;
			}

			ItemTemplate eqitemTmpl = weapon.getTemplate();
			if (eqitemTmpl == null) {
				return;
			}

			EquipmentFeature eqfeature = (EquipmentFeature) weapon.getFeature();
			eqfeature.updateXinghunIndex();
			player.sendMessage(eqfeature.getItem().getItemInfoMsg());
		}

		// 涉及到的背包更新
		// human.sendMessage(human.getInventory().getXinghunBagInfoMsg());
		// human.sendMessage(human.getInventory().getXiangqianBagInfoMsg());
		human.sendMessage(human.getInventory().getEquipBagInfoMsg());
		int eqBagId = cgXinghunActivate.getEqbagId();
		BagType eqBagType = BagType.valueOf(eqBagId);
		if (StringUtils.isNotEmpty(cgXinghunActivate.getUuid()) && (eqBagType == BagType.PET_EQUIP)) {
			human.sendMessage(human.getInventory().getPetEquipBagInfoMsg(
					cgXinghunActivate.getUuid()));
		}
		human.sendMessage(human.getInventory().getPrimBagInfoMsg());
		
		gcMsg.setResult(true);
		gcMsg.setDesc("");
		player.sendMessage(gcMsg);

	}
	
	/**
	 * 魂石销毁即卖出
	 * @param player
	 * @param cgXinghunDestruct
	 */
	public void handleXinghunDestruct(Player player,
			CGXinghunDestruct cgXinghunDestruct) {

		int bagId = cgXinghunDestruct.getBagId();
		int index = cgXinghunDestruct.getIndex();

		BagType bagType = BagType.valueOf(bagId);

		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		if ( (bagType != BagType.XINGHUN) && (bagType != BagType.XIANGQIAN)) {
			return;
		}

		if (index < 0) {
			return;
		}

		Human human = player.getHuman();
		if (human == null) {
			return;
		}

		Item toActItem = human.getInventory()
				.getItemByIndex(bagType, "", index);
		// Item toSellItem = human.getInventory().getItemByIndex(bagType, 0l,
		// index);
		if (toActItem == null) {
			return;
		}

//		XinghunFeature feature = (XinghunFeature) toActItem.getFeature();

		ItemTemplate itemTmpl = toActItem.getTemplate();
		if (itemTmpl == null) {
			return;
		}

		Currency currency = Currency.valueOf(itemTmpl.getCurrency());
		if (currency == Currency.GOLD) {
			return;
		}

		// 物品能卖多少钱应该是物品的单价*物品的数量
		int sellPrice = itemTmpl.getSellPrice();
		if (sellPrice <= 0) {
			return;
		}

		// 获得出售魂石的数量 首先根据星魂sn获得星魂模板
		String xinghunSn = itemTmpl.getItemSn();

		// 获得星魂属性模板
		XinghunParaTemplate paraTemplate = Globals.getXinghunParaService()
				.getXinghunParaMap().get(xinghunSn);

		String detailReason;

		detailReason = ItemLogReason.SELL_COST.getReasonText();
		detailReason = MessageFormat.format(detailReason, itemTmpl.getId(),
				itemTmpl.getSellPrice());
		
		if (human.getInventory().removeItemByIndex(bagType, index,
				toActItem.getOverlap(), ItemLogReason.SELL_COST, detailReason)) {
			// 道具模版Id={0}|道具名称={1}
			detailReason = MoneyLogReason.SELL_TO_SHOP.getReasonText();
			detailReason = MessageFormat.format(detailReason, itemTmpl.getId(),
					itemTmpl.getName());

			boolean giveMoneySucc = human.giveMoney(sellPrice, currency, false,
					CurrencyLogReason.SELL, detailReason);

			if (!giveMoneySucc) {
				// 如果给予失败。正常情况不会发生
				Loggers.playerLogger.error(LogUtils.buildLogInfoStr(
						player.getRoleUUID(),
						String.format("出售物品(id=%d)时给钱失败", itemTmpl.getId())));
				player.sendErrorMessage(ItemLangConstants_20000.SHOP_SELL_FAIL);
				return;
			}
			human.getPropertyManager().setHunshiNum(
					human.getPropertyManager().getHunshiNum()
							+ paraTemplate.getHunshi());
			human.snapChangedProperty(true);
//			int hunshi = human.getPropertyManager().getHunshiNum();
			//
			int xingzuoId = paraTemplate.getXingzuoId();
			//相关背包更新
			//同步更新装备的feature信息
			if(bagType == BagType.XIANGQIAN){
				int eqBagId = cgXinghunDestruct.getEqbagId();
				BagType eqBagType = BagType.valueOf(eqBagId);		
				int eqIndex = cgXinghunDestruct.getEqbagIndex();	
				String wearId = cgXinghunDestruct.getUuid();
				
				if(eqIndex < 0){
					return;
				}
				
				Item weapon;
				if(eqBagType == BagType.HUMAN_EQUIP){
					weapon = human.getInventory().getEquipBag().getWeapon();
//					getItemByIndex(eqBagType, "", eqIndex);
				}else if(eqBagType == BagType.PET_EQUIP){
					weapon = human.getInventory().getBagByPet(wearId).getByIndex(eqIndex);
				}else{
					weapon = human.getInventory().getItemByIndex(eqBagType, "", eqIndex);
				}
				
				if (weapon == null) {
					return;
				}

				ItemTemplate eqitemTmpl = weapon.getTemplate();
				if (eqitemTmpl == null) {
					return;
				}
				
				EquipmentFeature eqfeature = (EquipmentFeature) weapon.getFeature();
				eqfeature.setXinghunIndex(xingzuoId, -1);
				
				player.sendMessage(eqfeature.getItem().getItemInfoMsg());
			}
		}
		
		//涉及到的背包更新 
		human.sendMessage(human.getInventory().getXinghunBagInfoMsg());
		human.sendMessage(human.getInventory().getXiangqianBagInfoMsg());
		human.sendMessage(human.getInventory().getEquipBagInfoMsg());
		int eqBagId = cgXinghunDestruct.getEqbagId();
		BagType eqBagType = BagType.valueOf(eqBagId);	
		if(StringUtils.isNotEmpty(cgXinghunDestruct.getUuid()) && eqBagType == BagType.PET_EQUIP){
			human.sendMessage(human.getInventory().getPetEquipBagInfoMsg(cgXinghunDestruct.getUuid()));
		}		
		human.sendMessage(human.getInventory().getPrimBagInfoMsg());
		
	}

	/**
	 *  星魂洗练
	 * @param player
	 * @param cgXinghunXilian
	 */
	public void handleXinghunXilian(Player player,
			CGXinghunXilian cgXinghunXilian) {
		// TODO Auto-generated method stub
		
		GCXinghunXilian gcMsg = new GCXinghunXilian();
		
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		int bagId = cgXinghunXilian.getBagId();
		BagType bagType = BagType.valueOf(bagId);

		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		if (bagType != BagType.XINGHUN && bagType != BagType.XIANGQIAN) {
			return;
		}
		
		int bagIndex = cgXinghunXilian.getIndex();
		if(bagIndex < 0){
			return;
		}
		
		Item toActItem = human.getInventory().getItemByIndex(bagType, "",
				bagIndex);

		if (toActItem == null) {
			return;
		}

		XinghunFeature feature = (XinghunFeature) toActItem.getFeature();

		if (feature == null) {
			return;
		}
		if (feature.getPropNum() > 3) {
			return;
		}
		ItemTemplate itemTmpl = toActItem.getTemplate();
		if (itemTmpl == null) {
			return;
		}

		String xinghunSn = itemTmpl.getItemSn();

		// 获得星魂属性模板
		XinghunParaTemplate paraTemplate = Globals.getXinghunParaService()
				.getXinghunParaMap().get(xinghunSn);

		int prop1 = cgXinghunXilian.getProp1();
		int prop2 = cgXinghunXilian.getProp2();
		int prop3 = cgXinghunXilian.getProp3();
		
		//对激活权限进行简单判断，写固定代码
		if(feature.getPropNum() == 1){
			if(prop2 != -1 || prop3 != -1){
				return;
			}
		}
		if(feature.getPropNum() == 2){
			if(prop3 != -1){
				return;
			}
		}
		
		//判断金钱和魂石是够足够
		XinghunXilianTemplate xilianTemp = Globals.getXinghunParaService().getXilianTemp(prop1, prop2, prop3, paraTemplate.getQualityNum());
//		if(human.getPropertyManager().getHunshiNum() < xilianTemp.getHunshi()){
//			return;
//		}
//		boolean isEnough = human.hasEnoughMoney(xilianTemp.getPrice(),
//				Currency.COINS, null, true);
//		if(!isEnough){
//			return;
//		}
//		
//		human.getPropertyManager().setHunshiNum(
//				human.getPropertyManager().getHunshiNum()
//						- xilianTemp.getHunshi());
		//TODO
//		human.costMoney(xilianTemp.getPrice(), Currency.COINS, null, true,
//				0, MoneyLogReason.XINGHUN_XILIAN, xinghunSn,
//				itemTmpl.getId());
		
		//重新生成可随机的属性 先进行预处理
		if(prop1 == JIHUO){
			feature.changeProp(feature.getProp1());
		}
		if(prop2 == JIHUO){
			feature.changeProp(feature.getProp2());
		}
		if(prop3 == JIHUO){
			feature.changeProp(feature.getProp3());
		}
		
		//随机属性
		if(prop1 == JIHUO){
			ItemDef.XinghunProps prop = feature.getAttribute();
			XinghunAttributeVo value = feature.getPara(paraTemplate, prop);
			feature.setAddOnProps(1, prop, value);
		}
		if(prop2 == JIHUO){
			ItemDef.XinghunProps prop = feature.getAttribute();
			XinghunAttributeVo value = feature.getPara(paraTemplate, prop);
			feature.setAddOnProps(2, prop, value);
		}
		if(prop3 == JIHUO){
			ItemDef.XinghunProps prop = feature.getAttribute();
			XinghunAttributeVo value = feature.getPara(paraTemplate, prop);
			feature.setAddOnProps(3, prop, value);
		}
		
		GCItemUpdate msg = toActItem.getItemInfoMsg();
		player.sendMessage(msg);
		
		//同步更新装备的feature信息
		if(bagType == BagType.XIANGQIAN){
			int eqBagId = cgXinghunXilian.getEqbagId();
			BagType eqBagType = BagType.valueOf(eqBagId);		
			int eqIndex = cgXinghunXilian.getEqbagIndex();	
			String wearId = cgXinghunXilian.getUuid();
			
			if(eqIndex < 0){
				return;
			}
			
			Item weapon;
			if(eqBagType == BagType.HUMAN_EQUIP){
				weapon = human.getInventory().getEquipBag().getWeapon();
//				getItemByIndex(eqBagType, "", eqIndex);
			}else if(eqBagType == BagType.PET_EQUIP){
				weapon = human.getInventory().getBagByPet(wearId).getByIndex(eqIndex);
			}else{
				weapon = human.getInventory().getItemByIndex(eqBagType, "", eqIndex);
			}
			
			if (weapon == null) {
				return;
			}

			ItemTemplate eqitemTmpl = weapon.getTemplate();
			if (eqitemTmpl == null) {
				return;
			}
			
			EquipmentFeature eqfeature = (EquipmentFeature) weapon.getFeature();
			eqfeature.updateXinghunIndex();
			player.sendMessage(eqfeature.getItem().getItemInfoMsg());
			human.sendMessage(human.getInventory().getXiangqianBagInfoMsg());
			human.sendMessage(human.getInventory().getEquipBagInfoMsg());
//			int eqBagId = cgXinghunXilian.getEqbagId();
//			BagType eqBagType = BagType.valueOf(eqBagId);	
			if(StringUtils.isNotEmpty(cgXinghunXilian.getUuid()) && eqBagType == BagType.PET_EQUIP){
					human.sendMessage(human.getInventory().getPetEquipBagInfoMsg(cgXinghunXilian.getUuid()));
			}		                                                                          
			human.sendMessage(human.getInventory().getPrimBagInfoMsg());
		}else{
			//涉及到的背包更新 
			human.sendMessage(human.getInventory().getXinghunBagInfoMsg());
		}
			
		gcMsg.setResult(true);
		gcMsg.setDesc("");
		player.sendMessage(gcMsg);
		
		// LOG
		Globals.getLogService().sendXinghunLog(human, XinghunLogReason.XILIAN,
				null, xinghunSn, itemTmpl.getName(), feature.toFeatureString(),
				Globals.getTimeService().now());
	}

	public void handleXinghunAdd(Player player, CGXinghunAdd cgXinghunAdd) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		
		int xhBagId = cgXinghunAdd.getXhbagId();
		BagType xhBagType = BagType.valueOf(xhBagId);
		if (AbstractItemBag.isNullBag(xhBagType)) {
			return;
		}
		if (xhBagType != BagType.XINGHUN) {
			return;
		}
		
		int eqBagId = cgXinghunAdd.getEqbagId();
		BagType eqBagType = BagType.valueOf(eqBagId);
		
		int xhIndex = cgXinghunAdd.getXhIndex();
		int eqIndex = cgXinghunAdd.getEqbagIndex();
		
		String wearId = cgXinghunAdd.getUuid();
		
		if(xhIndex < 0 || eqIndex < 0){
			return;
		}
		
		Item toActItem = human.getInventory()
				.getItemByIndex(xhBagType, "", xhIndex);
		if (toActItem == null) {
			return;
		}

		ItemTemplate itemTmpl = toActItem.getTemplate();
		if (itemTmpl == null) {
			return;
		}
		
		Item weapon;
		if(eqBagType == BagType.HUMAN_EQUIP){
			weapon = human.getInventory().getEquipBag().getWeapon();
//			getItemByIndex(eqBagType, "", eqIndex);
		}else if(eqBagType == BagType.PET_EQUIP){
			weapon = human.getInventory().getBagByPet(wearId).getByIndex(eqIndex);
		}else{
			weapon = human.getInventory().getItemByIndex(eqBagType, "", eqIndex);
		}
		
		if (weapon == null) {
			return;
		}

		ItemTemplate eqitemTmpl = weapon.getTemplate();
		if (eqitemTmpl == null) {
			return;
		}
		
		XinghunFeature feature = (XinghunFeature) toActItem.getFeature();
		EquipmentFeature eqfeature = (EquipmentFeature) weapon.getFeature();
		

		// 获得星魂属性模板
		String xinghunSn = itemTmpl.getItemSn();
		XinghunParaTemplate paraTemplate = Globals.getXinghunParaService()
				.getXinghunParaMap().get(xinghunSn);
		int xingzuoId = paraTemplate.getXingzuoId();
		
		//判断是否开启
		TowerInfoManager dtim = new TowerInfoManager(human);
		Map<Integer, HumanTowerInfo> towerMap =  dtim.getTowerInfos();
		HumanTowerInfo towerInfo = towerMap.get(xingzuoId);
		
		List<TowerTemplate> towerList = new ArrayList<TowerTemplate>();
		towerList =	Globals.getTowerService().getTowerListMap().get(xingzuoId);
		if(towerList == null){
			return;
		}
		int size = towerList.size();
		if(xingzuoId >= 2 && (towerInfo == null || towerInfo.getMaxNum() < size)){
			//不能镶嵌
			return;
		}
		
		feature.setEquipmentUuid(weapon.getUUID());
		
		ShoulderBag xqBag = human.getInventory().getXiangqianBag();
		ShoulderBag xhBag = human.getInventory().getXinghunBag();
		
		
		List<Item> items = (List<Item>) xqBag.getAll();
		
		int toIndex = -1;
		for(Item t :items){
			XinghunFeature f = (XinghunFeature)t.getFeature();
			if(f.getXingzuoId() == feature.getXingzuoId() && f.getEquipmentUuid() == weapon.getUUID()){
				toIndex = t.getIndex();
			}
		}
		
		GCRemoveItem reMoveMsg = new  GCRemoveItem((short) xhBagType.index,
				(short) xhIndex, human.getUUID());
		
		//判断是否以前有镶嵌
		if(toIndex == -1){
			human.getInventory().getXinghunBag().move(toActItem, xhBag, xqBag);
			int index = toActItem.getIndex();
			eqfeature.setXinghunIndex(xingzuoId, index);
			
		}else{
			Item xqItem = human.getInventory()
					.getItemByIndex(BagType.XIANGQIAN, "", toIndex);
			XinghunFeature xqFeature = (XinghunFeature) xqItem.getFeature();
			xqFeature.setEquipmentUuid(null);
			human.getInventory().getXiangqianBag().move(xqItem, xqBag, xhBag);
			human.getInventory().getXinghunBag().move(toActItem, xhBag, xqBag);
			int index = toActItem.getIndex();
			eqfeature.setXinghunIndex(xingzuoId, index);
		}
		//物品更新消息
		GCItemUpdate msg = toActItem.getItemInfoMsg();
		player.sendMessage(msg);
		player.sendMessage(eqfeature.getItem().getItemInfoMsg());
		
		player.sendMessage(reMoveMsg);
				
		//涉及到的背包更新 
		human.sendMessage(human.getInventory().getXinghunBagInfoMsg());
		human.sendMessage(human.getInventory().getXiangqianBagInfoMsg());
		human.sendMessage(human.getInventory().getEquipBagInfoMsg());
		if(StringUtils.isNotEmpty(wearId)){
			human.sendMessage(human.getInventory().getPetEquipBagInfoMsg(wearId));
		}		
		human.sendMessage(human.getInventory().getPrimBagInfoMsg());
	}

	public void handleXinghunRed(Player player, CGXinghunRed cgXinghunRed) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		
		int bagId = cgXinghunRed.getBagId();
		int index = cgXinghunRed.getIndex();
//		String uuid = cgXinghunRed.getUuid();
		BagType bagType = BagType.valueOf(bagId);
		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		if (bagType != BagType.XIANGQIAN) {
			return;
		}
		
		Item toActItem = human.getInventory()
				.getItemByIndex(bagType, "", index);
		
		if (toActItem == null) {
			return;
		}

		ItemTemplate itemTmpl = toActItem.getTemplate();
		if (itemTmpl == null) {
			return;
		}
		
		int eqBagId = cgXinghunRed.getEqbagId();
		int eqIndex = cgXinghunRed.getEqbagIndex();
		BagType eqBagType = BagType.valueOf(eqBagId);
		String wearId = cgXinghunRed.getUuid();
		
		if(eqIndex < 0){
			return;
		}
		
		Item weapon;
		if(eqBagType == BagType.HUMAN_EQUIP){
			weapon = human.getInventory().getEquipBag().getWeapon();
//			getItemByIndex(eqBagType, "", eqIndex);
		}else if(eqBagType == BagType.PET_EQUIP){
			weapon = human.getInventory().getBagByPet(wearId).getByIndex(eqIndex);
		}else{
			weapon = human.getInventory().getItemByIndex(eqBagType, "", eqIndex);
		}
		
		if (weapon == null) {
			return;
		}

		ItemTemplate eqitemTmpl = weapon.getTemplate();
		if (eqitemTmpl == null) {
			return;
		}
		EquipmentFeature eqfeature = (EquipmentFeature) weapon.getFeature();
		
		
		XinghunFeature feature = (XinghunFeature) toActItem.getFeature();
		feature.setEquipmentUuid(null);
		human.getInventory().getXiangqianBag().move(toActItem, human.getInventory().getXiangqianBag(), human.getInventory().getXinghunBag());
		eqfeature.setXinghunIndex(feature.getXingzuoId(), -1);
		
		
		//物品更新消息
		GCItemUpdate msg = toActItem.getItemInfoMsg();
		player.sendMessage(msg);
		player.sendMessage(eqfeature.getItem().getItemInfoMsg());
				
				
		//涉及到的背包更新
		human.sendMessage(human.getInventory().getXinghunBagInfoMsg());
		human.sendMessage(human.getInventory().getXiangqianBagInfoMsg());
		human.sendMessage(human.getInventory().getEquipBagInfoMsg());
		if(StringUtils.isNotEmpty(cgXinghunRed.getUuid())){
				human.sendMessage(human.getInventory().getPetEquipBagInfoMsg(cgXinghunRed.getUuid()));
		}		
		human.sendMessage(human.getInventory().getPrimBagInfoMsg());
	}

	
	public void handleXinghunPiliangXilian(Player player,
			CGXinghunPiliangXilian cgXinghunPiliangXilian) {
		// TODO Auto-generated method stub
		
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		
		//清除map里关于该用户的洗练数据
		xilianMap.remove(human.getCharId());
		
		int bagId = cgXinghunPiliangXilian.getBagId();
		int index = cgXinghunPiliangXilian.getIndex();
//		String uuid = cgXinghunRed.getUuid();
		BagType bagType = BagType.valueOf(bagId);
		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		if (bagType != BagType.XIANGQIAN && bagType != BagType.XINGHUN) {
			return;
		}
		
		Item toActItem = human.getInventory()
				.getItemByIndex(bagType, "", index);
		
		if (toActItem == null) {
			return;
		}

		ItemTemplate itemTmpl = toActItem.getTemplate();
		if (itemTmpl == null) {
			return;
		}

		XinghunFeature feature = (XinghunFeature) toActItem.getFeature();

		if (feature == null) {
			return;
		}
		if (feature.getPropNum() > 3) {
			return;
		}

		String xinghunSn = itemTmpl.getItemSn();

		// 获得星魂属性模板
		XinghunParaTemplate paraTemplate = Globals.getXinghunParaService()
				.getXinghunParaMap().get(xinghunSn);

		int prop1 = cgXinghunPiliangXilian.getProp1();
		int prop2 = cgXinghunPiliangXilian.getProp2();
		int prop3 = cgXinghunPiliangXilian.getProp3();
		
		//对激活权限进行简单判断，写固定代码
		if(feature.getPropNum() == 1){
			if(prop2 != -1 || prop3 != -1){
				return;
			}
		}
		if(feature.getPropNum() == 2){
			if(prop3 != -1){
				return;
			}
		}
		
		// 判断金钱和魂石是够足够
//		XinghunXilianTemplate xilianTemp = Globals.getXinghunParaService()
//				.getXilianTemp(prop1, prop2, prop3, paraTemplate.getQuantity());
//		if (human.getPropertyManager().getHunshiNum() < xilianTemp.getHunshi() * XILIAN_TIMES) {
//			return;
//		}
//		boolean isEnough = human.hasEnoughMoney(xilianTemp.getPrice() * XILIAN_TIMES,
//				Currency.COINS, null, true);
//		if (!isEnough) {
//			return;
//		}
//
//		human.getPropertyManager().setHunshiNum(
//				human.getPropertyManager().getHunshiNum()
//						- xilianTemp.getHunshi() * XILIAN_TIMES);
//		human.costMoney(xilianTemp.getPrice() * XILIAN_TIMES, Currency.COINS, null, true, 0,
//				MoneyLogReason.XINGHUN_XILIAN, xinghunSn, itemTmpl.getId());
		
		//开始洗练
		XinghunXilianInfo[] xilianInfos = new XinghunXilianInfo[10];
		for(int i = 9; i >= 0; i--){
			
			//重新生成可随机的属性 先进行预处理
			if(prop1 == JIHUO){
				feature.changeProp(feature.getProp1());
			}
			if(prop2 == JIHUO){
				feature.changeProp(feature.getProp2());
			}
			if(prop3 == JIHUO){
				feature.changeProp(feature.getProp3());
			}
			
			//随机属性
			if(prop1 == JIHUO){
				ItemDef.XinghunProps prop = feature.getAttribute();
				XinghunAttributeVo value = feature.getPara(paraTemplate, prop);
				feature.setAddOnProps(1, prop, value);
			}
			if(prop2 == JIHUO){
				ItemDef.XinghunProps prop = feature.getAttribute();
				XinghunAttributeVo value = feature.getPara(paraTemplate, prop);
				feature.setAddOnProps(2, prop, value);
			}
			if(prop3 == JIHUO){
				ItemDef.XinghunProps prop = feature.getAttribute();
				XinghunAttributeVo value = feature.getPara(paraTemplate, prop);
				feature.setAddOnProps(3, prop, value);
			}
			
			//存储数据
			XinghunXilianInfo xilianInfo = new XinghunXilianInfo();
			xilianInfo.setResultId(i);
			if(prop1 == JIHUO){
				xilianInfo.setProp1(feature.getProp1());
				xilianInfo.setPropValue1(feature.getAddonProps().get(ItemDef.XinghunProps.valueBy(feature.getProp1())));
				xilianInfo.setPropQualtity1(feature.getPropQualtity1());
			}
			if(prop2 == JIHUO){
				xilianInfo.setProp2(feature.getProp2());
				xilianInfo.setPropValue2(feature.getAddonProps().get(ItemDef.XinghunProps.valueBy(feature.getProp2())));
				xilianInfo.setPropQualtity2(feature.getPropQualtity2());
			}
			if(prop3 == JIHUO){
				xilianInfo.setProp3(feature.getProp3());
				xilianInfo.setPropValue3(feature.getAddonProps().get(ItemDef.XinghunProps.valueBy(feature.getProp3())));
				xilianInfo.setPropQualtity3(feature.getPropQualtity3());
			}
			
			//放到map里
			if(xilianMap.containsKey(human.getCharId())){
				xilianMap.get(human.getCharId()).put(xilianInfo.getResultId(), xilianInfo);
			}else{
				Map<Integer, XinghunXilianInfo> map = new HashMap<Integer, XinghunXilianInfo>();
				map.put(xilianInfo.getResultId(), xilianInfo);
				xilianMap.put(human.getCharId(), map);
			}
			xilianInfos[i] = xilianInfo;
		}
		
		//物品更新消息
		GCItemUpdate msg = toActItem.getItemInfoMsg();
		player.sendMessage(msg);
		
		//向客户端返回信息
		GCXinghunPiliangXilian xilianMsg  = new GCXinghunPiliangXilian();
		xilianMsg.setXinghunXilianInfo(xilianInfos);
		player.sendMessage(xilianMsg);
		
		//相关背包更新
		//同步更新装备的feature信息
		if(bagType == BagType.XIANGQIAN){
			int eqBagId = cgXinghunPiliangXilian.getEqbagId();
			BagType eqBagType = BagType.valueOf(eqBagId);		
			int eqIndex = cgXinghunPiliangXilian.getEqbagIndex();	
			String wearId = cgXinghunPiliangXilian.getUuid();
			
			if(eqIndex < 0){
				return;
			}
			
			Item weapon;
			if(eqBagType == BagType.HUMAN_EQUIP){
				weapon = human.getInventory().getEquipBag().getWeapon();
//				getItemByIndex(eqBagType, "", eqIndex);
			}else if(eqBagType == BagType.PET_EQUIP){
				weapon = human.getInventory().getBagByPet(wearId).getByIndex(eqIndex);
			}else{
				weapon = human.getInventory().getItemByIndex(eqBagType, "", eqIndex);
			}
			
			if (weapon == null) {
				return;
			}

			ItemTemplate eqitemTmpl = weapon.getTemplate();
			if (eqitemTmpl == null) {
				return;
			}
			
			EquipmentFeature eqfeature = (EquipmentFeature) weapon.getFeature();
			eqfeature.updateXinghunIndex();
			//涉及到的背包更新 
			player.sendMessage(eqfeature.getItem().getItemInfoMsg());
			human.sendMessage(human.getInventory().getXiangqianBagInfoMsg());
			human.sendMessage(human.getInventory().getEquipBagInfoMsg());
//			int eqBagId = cgXinghunPiliangXilian.getEqbagId();
//			BagType eqBagType = BagType.valueOf(eqBagId);		
			if(StringUtils.isNotEmpty(cgXinghunPiliangXilian.getUuid())&& (eqBagType == BagType.PET_EQUIP)){
					human.sendMessage(human.getInventory().getPetEquipBagInfoMsg(cgXinghunPiliangXilian.getUuid()));
			}		
			human.sendMessage(human.getInventory().getPrimBagInfoMsg());
		}else{
			human.sendMessage(human.getInventory().getXinghunBagInfoMsg());
		}		
	}
	
	public void handleXinghunPiliangXilianRemain(Player player,
			CGXinghunPiliangXilianRemain cgXinghunPiliangXilianRemain) {
		
		GCXinghunPiliangXilianRemain gcMsg = new GCXinghunPiliangXilianRemain();
		
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		
		int bagId = cgXinghunPiliangXilianRemain.getBagId();
		int index = cgXinghunPiliangXilianRemain.getIndex();
		BagType bagType = BagType.valueOf(bagId);
		if (AbstractItemBag.isNullBag(bagType)) {
			return;
		}
		if (bagType != BagType.XIANGQIAN && bagType != BagType.XINGHUN) {
			return;
		}
		
		Item toActItem = human.getInventory()
				.getItemByIndex(bagType, "", index);
		
		if (toActItem == null) {
			return;
		}

		ItemTemplate itemTmpl = toActItem.getTemplate();
		if (itemTmpl == null) {
			return;
		}

		XinghunFeature feature = (XinghunFeature) toActItem.getFeature();
		
		//更新星魂属性
		int resultId = cgXinghunPiliangXilianRemain.getResultId();
		if(xilianMap.containsKey(human.getCharId())){
			XinghunXilianInfo info = xilianMap.get(human.getCharId()).get(resultId);
			if(info == null){
				return;
			}else{
				if(StringUtils.isNotBlank(info.getProp1())){
					/**
					 * changeProp 会出现类似bug
					 * 比如 3条属性 a = 1 ，b = 2， c = 3
					 * 洗出新三条属性 c = 2  d = 5 e = 6
					 * 在第三条changeprop时候会把 原来洗出的c=2也清掉，所以要加一个判断
					 */
					feature.changeProp(feature.getProp1());				
					feature.getProps().remove(XinghunProps.valueBy(info.getProp1()));
					feature.setAddOnProps(1, XinghunProps.valueBy(info.getProp1()), new XinghunAttributeVo(info.getPropValue1(), info.getPropQualtity1()));
				}
				if(StringUtils.isNotBlank(info.getProp2())){
					if (!info.getProp1().equalsIgnoreCase(feature.getProp2())) {
						feature.changeProp(feature.getProp2());
					}
					feature.getProps().remove(XinghunProps.valueBy(info.getProp2()));
					feature.setAddOnProps(2, XinghunProps.valueBy(info.getProp2()), new XinghunAttributeVo(info.getPropValue2(), info.getPropQualtity2()));
				}
				if(StringUtils.isNotBlank(info.getProp3())){
					if (!info.getProp1().equalsIgnoreCase(feature.getProp3())
							&& !info.getProp2().equalsIgnoreCase(
									feature.getProp3())) {
						feature.changeProp(feature.getProp3());
					}		
					feature.getProps().remove(XinghunProps.valueBy(info.getProp3()));
					feature.setAddOnProps(3, XinghunProps.valueBy(info.getProp3()), new XinghunAttributeVo(info.getPropValue3(), info.getPropQualtity3()));
				}
				
			}
		}
		
		//清除map里关于该用户的洗练数据
		xilianMap.remove(human.getCharId());
		
		// 相关背包更新
		// 同步更新装备的feature信息
		if (bagType == BagType.XIANGQIAN) {
			int eqBagId = cgXinghunPiliangXilianRemain.getEqbagId();
			BagType eqBagType = BagType.valueOf(eqBagId);
			int eqIndex = cgXinghunPiliangXilianRemain.getEqbagIndex();
			String wearId = cgXinghunPiliangXilianRemain.getUuid();

			if (eqIndex < 0) {
				return;
			}

			Item weapon;
			if(eqBagType == BagType.HUMAN_EQUIP){
				weapon = human.getInventory().getEquipBag().getWeapon();
//				getItemByIndex(eqBagType, "", eqIndex);
			}else if(eqBagType == BagType.PET_EQUIP){
				weapon = human.getInventory().getBagByPet(wearId).getByIndex(eqIndex);
			}else{
				weapon = human.getInventory().getItemByIndex(eqBagType, "", eqIndex);
			}

			if (weapon == null) {
				return;
			}

			ItemTemplate eqitemTmpl = weapon.getTemplate();
			if (eqitemTmpl == null) {
				return;
			}

			EquipmentFeature eqfeature = (EquipmentFeature) weapon.getFeature();
			eqfeature.updateXinghunIndex();
			player.sendMessage(eqfeature.getItem().getItemInfoMsg());
		}
		
		player.sendMessage(toActItem.getItemInfoMsg());
		// 涉及到的背包更新
		human.sendMessage(human.getInventory().getXinghunBagInfoMsg());
		human.sendMessage(human.getInventory().getXiangqianBagInfoMsg());
		human.sendMessage(human.getInventory().getEquipBagInfoMsg());
		
		int eqBagId = cgXinghunPiliangXilianRemain.getEqbagId();
		BagType eqBagType = BagType.valueOf(eqBagId);
		if (StringUtils.isNotEmpty(cgXinghunPiliangXilianRemain.getUuid()) && eqBagType == BagType.PET_EQUIP) {
			human.sendMessage(human.getInventory().getPetEquipBagInfoMsg(
					cgXinghunPiliangXilianRemain.getUuid()));
		}
		human.sendMessage(human.getInventory().getPrimBagInfoMsg());
		
		gcMsg.setResult(true);
		gcMsg.setDesc("");
		player.sendMessage(gcMsg);
		
	}

}
