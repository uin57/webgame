package com.pwrd.war.gameserver.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.google.common.collect.Maps;
import com.pwrd.war.common.HeartBeatListener;
import com.pwrd.war.common.LogReasons.GetItemLogReason;
import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.common.LogReasons.ItemLogReason;
import com.pwrd.war.common.LogReasons.ReasonDesc;
import com.pwrd.war.common.LogReasons.SnapLogReason;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.annotation.SyncIoOper;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.util.ArrayUtils;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.db.model.ItemEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.common.db.RoleDataHolder;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatTaskExecutor;
import com.pwrd.war.gameserver.common.heartbeat.HeartbeatTaskExecutorImpl;
import com.pwrd.war.gameserver.common.i18n.LangService;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.i18n.constants.ItemLangConstants_20000;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.ItemDef.FreezeState;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.ItemParam.MergeMode;
import com.pwrd.war.gameserver.item.container.AbstractItemBag;
import com.pwrd.war.gameserver.item.container.CommonBag;
import com.pwrd.war.gameserver.item.container.HumanEquipBag;
import com.pwrd.war.gameserver.item.container.PetBodyEquipBag;
import com.pwrd.war.gameserver.item.container.RedeemBag;
import com.pwrd.war.gameserver.item.container.ShoulderBag;
import com.pwrd.war.gameserver.item.msg.GCBagUpdate;
import com.pwrd.war.gameserver.item.msg.GCItemStartCd;
import com.pwrd.war.gameserver.item.msg.GCItemUpdate;
import com.pwrd.war.gameserver.item.msg.ItemMessageBuilder;
import com.pwrd.war.gameserver.item.operation.MoveItemOperation;
import com.pwrd.war.gameserver.item.operation.MoveItemServicePool;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.pet.Pet;

public class Inventory implements RoleDataHolder, HeartBeatListener{
	
	private static final Logger logger = Loggers.itemLogger;
	
	/** 道具所属的玩家 */
	private final Human owner;
	
	/** 玩家主背包 */
	private final ShoulderBag primBag;	
	
	/** 仓库包 **/
	private final ShoulderBag depotBag;
	
	/** 装备背包 **/
	private final HumanEquipBag equipBag;
	
	/** 所有玩家包的表 */
	private EnumMap<BagType, AbstractItemBag> bags;
	
	/** 所属玩家的所有武将背包的表 */
	private Map<String,PetBodyEquipBag> petBags;
	
	/** 加载后未通过校验的道具 */
	private List<Item> badItems;
	
	/** 心跳任务处理器 */
	private HeartbeatTaskExecutor hbTaskExecutor;
	
	private ItemService itemService;
	
	private LangService langManager;
	
	/** 回购物品背包*/
	private RedeemBag redeemBag;
	
	/** 将星云路背包*/
	private ShoulderBag xinghunBag;
	
	/** 星魂镶嵌后背包*/
	private ShoulderBag xiangqianBag;
	
	public Inventory(Human owner, ItemService itemService, LangService langManager) {
		this.owner = owner;
		this.itemService = itemService;
		this.langManager = langManager;
		primBag = new ShoulderBag(owner, BagType.PRIM, owner.getPropertyManager().getBagSize());
		depotBag = new ShoulderBag(owner, BagType.DEPOT, owner.getPropertyManager().getDepotSize());
		equipBag = new HumanEquipBag(owner);
		redeemBag = new RedeemBag(owner, BagType.TEMP);
		xinghunBag = new ShoulderBag(owner, BagType.XINGHUN, 20);
		xiangqianBag = new ShoulderBag(owner, BagType.XIANGQIAN, 600);
		
		bags = new EnumMap<BagType, AbstractItemBag>(BagType.class);
		bags.put(primBag.getBagType(), primBag);
		bags.put(equipBag.getBagType(), equipBag);
		bags.put(depotBag.getBagType(), depotBag);
		//TODO
		bags.put(redeemBag.getBagType(), redeemBag);
		bags.put(xinghunBag.getBagType(), xinghunBag);
		bags.put(xiangqianBag.getBagType(), xiangqianBag);
		
		petBags = Maps.newHashMap();
	
		hbTaskExecutor = new HeartbeatTaskExecutorImpl();
//		hbTaskExecutor.submit(new ItemExpireProcesser(this));
		// TODO: 因为本期不做绑定的功能,所以暂时先注释掉
//		hbTaskExecutor.submit(new ItemFreezeProcesser(this));
	}
	
	
	/**
	 * 加载完宠物后初始化宠物装备包
	 * 
	 */
	public void initPetBags()
	{		
		List<Pet> pets = owner.getPetManager().getPets();
		for(Pet owerPet : pets)
		{
			PetBodyEquipBag petEquipBag = new PetBodyEquipBag(this.owner, owerPet);
			petBags.put(owerPet.getUUID(), petEquipBag);
		}		
	}
	public HumanEquipBag getEquipBag() {
		return equipBag;
	}
 
	public ShoulderBag getDepotBag() {
		return depotBag;
	}

	/**
	 * 增加宠物装备包
	 * @param owerPet
	 */
	public void addPetBag(Pet owerPet) {
		PetBodyEquipBag petEquipBag = new PetBodyEquipBag(this.owner, owerPet);
		petBags.put(owerPet.getUUID(), petEquipBag);
	}
	
	/**
	 * 删除宠物装备包
	 * @param petUuid
	 */
	public void removePetBag(String petUuid) {
		petBags.remove(petUuid);
	}
	
	/**
	 * 初始化玩家的背包,并加载玩家的道具
	 */
	@SyncIoOper
	public void load() {
		loadOffline();
		Globals.getPlayerLogService().sendInventorySnapLog(owner,SnapLogReason.REASON_SNAP_LOGIN_ITEM);
	}
	
	@SyncIoOper
	public void loadOffline() {
		// 从数据库加载玩家道具,同步io操作
		loadAllItemsFromDB(this);
		//TODO 遍历装备和星魂的对应关系
		Item[] weapons = this.getAllWeapon();
		for(int i=0; i< weapons.length; i++){
			this.setXinghunForWeapon(weapons[i]);
		}
		this.redeemBag = new RedeemBag(this.owner, BagType.TEMP);
	}
	
	
	/**
	 * 从数据库中加载玩家的所有物品
	 * 
	 * @param taskInfo
	 */
	@SyncIoOper
	public void loadAllItemsFromDB(Inventory inventory) {
		Assert.notNull(inventory);
		String _charId = inventory.getOwner().getUUID();
		List<ItemEntity> items = null;
		try {
			items = Globals.getDaoService().getItemDao().getItemsByCharId(_charId);
			for (ItemEntity entity : items) {
				Item item = Item.buildFromItemEntity(entity, inventory.getOwner());
				item.setInDb(true); // 从db中读出来的都在db中
				if (item != null) {
					item.getLifeCycle().deactivate();
					if (item.validateOnLoaded(entity)) {
						inventory.putItem(item);
					} else {
						inventory.putBadItem(item);
					}
				}
			}
		} catch (DataAccessException e) {
			if (Loggers.itemLogger.isErrorEnabled()) {
				Loggers.itemLogger.error(LogUtils.buildLogInfoStr(inventory
						.getOwner().getUUID(), "从数据库中加载物品信息出错"), e);
			}
			return;
		}
	}
	
	
	public Human getOwner() {
		return owner;
	}
	
	@Override
	public void checkAfterRoleLoad() {
		// 将所有包裹里的道具设为active
		activeAllItems();
	}
	
	private void activeAllItems() {
		for (AbstractItemBag bag : bags.values()) {
			bag.activeAll();
		}
		for (PetBodyEquipBag bag : petBags.values()) {
			bag.activeAll();
		}
	}

	@Override
	public void checkBeforeRoleEnter() {
		
	}

	@Override
	public void onHeartBeat() {
		this.hbTaskExecutor.onHeartBeat();
	}
	
	/**
	 * 增加某类型的背包
	 * 
	 * @param bag
	 */
	public void putBag(AbstractItemBag bag) {
		bags.put(bag.getBagType(), bag);
	}
	
	
	/**
	 * 获得背包,包含搜索宠物背包
	 * @param bagType
	 * @param petId
	 * @return
	 */
	public AbstractItemBag getBagByType(BagType bagType,String wearerId)
	{
		AbstractItemBag bag = null;
		if(bagType == BagType.PET_EQUIP)
		{
			bag = this.petBags.get(wearerId);			
		}
		else
		{
			bag = bags.get(bagType);
		}
		return bag;
	}
	
	
	/**
	 * 根据PetId取bag
	 *  
	 * @param petUuid
	 * @return
	 */
	public PetBodyEquipBag getBagByPet(String petUuid)
	{
		return petBags.get(petUuid);
	}
	
		
	/**
	 * 增加一个道具实例，一般用于在数据加载时相背包中添加数据
	 * 
	 * @param item
	 */
	public void putItem(Item item) {
		BagType bagType = item.getBagType();
		if (bags.containsKey(bagType)) {
			bags.get(bagType).putItem(item);
			if(bagType == BagType.HUMAN_EQUIP){
				equipBag.initEquip(item);
			}
		} else if(bagType == BagType.PET_EQUIP) {
			PetBodyEquipBag petBag = petBags.get(item.getWearerId());
			if(petBag != null)
			{
				petBag.putItem(item);
				petBag.initEquip(item);
			}
			else
			{
				logger.error("武将背包没有找到wearerId:" + item.getWearerId() + " bag:" + item.getBagType() + " item:"	+ item.getTemplateId());
			}
		}
		else
		{
			logger.error("不可以直接往此包中添加道具 bag:" + item.getBagType() + " item:"	+ item.getTemplateId());
		}
	}
	
	/**
	 * 在加载数据时，将未通过校验的item，使用此方法加入inventory
	 * 
	 * @param item
	 */
	public void putBadItem(Item item) {
		if (this.badItems == null) {
			badItems = new ArrayList<Item>();
		}
		badItems.add(item);
	}
	
	/**
	 * 获取所有主背包中的非空道具，其他背包的对应操作可以先得到背包再取得其中的物品
	 * 
	 * @return
	 */
	public Collection<Item> getAllPrimBagItems() {
		return primBag.getAll();
	}
	


	/**
	 * 向背包中增加指定的数量的道具,该方法给予道具空间不够则全部不给<br/> 
	 * 此方法是需要在已经计算好绑定状态后调用，此处不再计算绑定状态<br/> 
	 * 该接口是游戏中生成新道具的唯一接口，所有道具产生相关操作只能调用该接口进行处理
	 * 
	 * @param itemSN  物品SN
	 * @param count   添加的道具数
	 * @param reason   添加的原因
	 * @param detailReason  添加原因的详细说明（用于扩展reason，可无）
	 * @param needNotify  是否通知客户端"您或得了x个x"
	 * @return 操作后需要跟新的Item列表
	 */
	public Collection<Item> addItem(String itemSN, int count, BindStatus bind,
			ItemGenLogReason reason, String detailReason, boolean needNotify) {
		if (count <= 0) {
			return Collections.emptyList();
		}
		ItemTemplate tmpl = itemService.getTemplateByItemSn(itemSN);
		if (tmpl == null) {
			Loggers.itemLogger.error(String.format("找不到道具 id=%s, roleId=%s",
					itemSN, owner.getUUID()));
			return Collections.emptyList();
		}
		BagType bgType = tmpl.getBagType();
		// 找到应该放入的包
		ShoulderBag bag = (ShoulderBag) bags.get(bgType);
		// 检查是否有足够的空间存放，不做提示，提示需要请求者在调用此方法前作
		if (bag.getMaxCanAdd(tmpl, bind) < count) {
			return Collections.emptyList();
		}
		// 执行添加
		Collection<Item> updatedItems = bag.add(tmpl, count, bind, reason,
				detailReason);
		// 更新客户端背包
		GCItemUpdate msg = new GCItemUpdate();
		for (Item item : updatedItems) {
			GCItemUpdate msg1  = (GCItemUpdate) item.getUpdateMsgAndResetModify(); 
			msg.setItem(ArrayUtils.mergeArray(msg.getItem(), msg1.getItem()));
		}
		this.owner.sendMessage(msg);
		if (needNotify) {
			// 提示：您或得了x个x
//			owner.sendSystemMessage( CommonLangConstants_10000.GET_SOMETHING, count, tmpl
//					.getName());
		}
		
		Globals.getLogService().sendGetItemLog(owner, GetItemLogReason.ITEMGET, null, itemSN, Globals.getTimeService().now());
		return updatedItems;
	}
	
	/**
	 * 批量添加道具，这些道具需要时由于相同的原因添加的，要么全成功，要么全失败<br/>
	 * 此方法是需要在已经计算好绑定状态后调用，此处不再计算绑定状态
	 * 
	 * @param params   要新增道具参数，参数中的bindStatus是被忽略的，按照道具模板的bindMode规则进行绑定
	 * @param reason     添加的原因
	 * @param detailReason   添加原因的详细说明（用于扩展reason，可无）
	 * @param needNotify    是否通知客户端"您或得了x个x"
	 * @return 操作后需要跟新的Item列表
	 */
	public Collection<Item> addAllItems(Collection<ItemParam> params,
			ItemGenLogReason reason, String detailReason, boolean needNotify) {
		// 合并相同的参数，考虑绑定
		Collection<ItemParam> mergedParams = ItemParam.mergeByTmplId(params,
				MergeMode.CONSIDER_BIND);
		List<Item> updateList = new ArrayList<Item>();
		// 能全放下才放，这里只是做最后的校验，不提示，一般情况下在调用此方法前请求者已经检查过了空间
		if (checkSpace(mergedParams, false)) {
			for (ItemParam param : mergedParams) {
				updateList.addAll(addItem(param.getItemSN(), param
						.getCount(), param.getBind(), reason, detailReason,
						needNotify));
			}
		}
		return updateList;
	}

	/**
	 * 检查背包是否能够放下params指定的所有道具，注意锁定了的道具不在考虑范围内
	 * 
	 * @param params        道具参数列表
	 * @param needNotice      是否需要提示，如果为true，在空间不足时会提示背包空间不足，同时提示每个包需要几个空位
	 * @return 全都能放下返回true，否则返回fasle
	 */
	public boolean checkSpace(Collection<ItemParam> params, boolean needNotice) {
		Collection<ItemParam> merged = ItemParam.mergeByTmplId(params,
				MergeMode.CONSIDER_BIND);
		// 计算每个包需要几个槽位 key:格子类型，value:需要的槽位
		EnumMap<BagType, Integer> needSlotCounter = countNeedSlot(merged);
		boolean isSuccess = true;
		StringBuilder msgSb = new StringBuilder();
		for (Map.Entry<BagType, Integer> entry : needSlotCounter.entrySet()) {
			BagType bagType = entry.getKey();
			int needSlot = entry.getValue();
			ShoulderBag bag = (ShoulderBag) bags.get(bagType);
			if (bag.getEmptySlotCount() < needSlot) {
				// 空槽位比需要的少，放不下了
				isSuccess = false;
				if (needNotice) {
					String bagName = langManager.read( bagType.getNameLangId());
					// {0}中需要{1}个空位
					msgSb.append(
							langManager.read( ItemLangConstants_20000.ITEM_MAKE_SPACE, bagName,
									needSlot)).append(" ");
				} else {
					// 如果不需要通知，直接返回结果，如果需要通知，需要将所有的道具都检查一遍，统计出那个包需要多少个格子
					return isSuccess;
				}
			}
		}
		// 如果不能全放下，并且需要提示，提示空间不足，并指出哪个包需要几个空格
		if (!isSuccess && needNotice) {
			// 您的背包没有足够的空间
//			owner.sendErrorMessage( );
			owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_NOT_ENOUGH_SPACE);
			// 通知每个包需要多少个空位
//			owner.sendSystemMessage(msgSb.toString());
		}
		return isSuccess;
	}
	
	/**
	 * 移动道具
	 * 
	 * @param fromBag
	 *            源包
	 * @param fromIndex
	 *            在源包中的索引
	 * @param toBag
	 *            目标包
	 * @param toIndex
	 *            在目标包中的索引
	 */
	public void moveItem(BagType fromBag, int fromIndex, BagType toBag, int toIndex, Pet wearer, String wearerId) {
		// TODO 检测状态冲突

		// 没有移动直接返回
		if (fromBag == toBag && fromIndex == toIndex) {
			return;
		}

		Item fromItem = getItemByIndex(fromBag, wearerId, fromIndex);
		Item toItem = null;
		if(toIndex == -1){
			AbstractItemBag bag = getBagByType(toBag, wearerId);
			if(bag != null){
				//需要判断目标背包有无可以叠加的
				BindStatus bind = fromItem.getBindStatus();
				List<Item> list = null;
				list = bag.getAllConsiderBind(fromItem.getTemplate().getItemSn(), bind);
				for(Item it : list){
					if(it.getOverlap() + fromItem.getOverlap() <= it.getMaxOverlap()){
						toItem = it;
						break;
					}
				}
				if(toItem == null){
					toItem = bag.getEmptySlot();
				}
				if(toItem == null){
					owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.BAG_FULL,
							Globals.getLangService().read(bag.getBagType().getNameLangId()));
					return;
				}
			}
		}else{
			toItem = getItemByIndex(toBag, wearerId, toIndex);
		}
		
		if (Item.isEmpty(fromItem)) {
			// 源道具不可以是空格，目标道具可以是空格
			return;
		}
		if (toItem == null) {
			// 这种toItem,肯定是有问题的,没有找到相应的bag,或者index
			if (Loggers.itemLogger.isWarnEnabled()) {
				Loggers.itemLogger
						.error(LogUtils.buildLogInfoStr(
								getOwner().getCharId(),
								String.format("移动道具,目标不合法,fromBag=%s,fromIndex=%s,toBag=%s,toIndex=%s,index=%s",
										fromBag.index,fromIndex,toBag.index,toIndex,wearerId)));
			}
			return;
		}


		MoveItemOperation service = MoveItemServicePool.instance.get(fromBag,toBag);
		if (service == null) {
			// 没有合适的处理器，说明不允许这样移动
			owner.sendSystemMessage( ItemLangConstants_20000.MOVE_ITEM_FAIL);
			return;
		}
		if(wearer == null)
		{	
			//不涉及武将装备移动
			service.move(owner, fromItem, toItem);
		}
		else
		{			
			service.move(owner, wearer, fromItem, toItem);
		}
	}
	
	
	
	
 
	
	/**
	 * 查询某一道具在包里数量，不区分绑定状态，只在主背包、材料、任务三个包里面找，而不搜索身上的装备、仓库等
	 * 
	 * @param templateId
	 * @return
	 */
	public int getItemCountIgnoreBind(String itemSN) {
		ShoulderBag bag = this.getBagByTemplateId(itemSN);
		if (bag != null) {
			return bag.getCountIgnoreBind(itemSN);
		} 
		else {
			// 不在那三个包中视为没有
			return 0;
		}
	}
	
	 
 
	
	/**
	 * 查询是否有itemSN指定的道具，忽略绑定状态<br/>
	 * 只在主背包、材料、任务三个包里面找，而不搜索身上的装备、 仓库等
	 * 
	 * @param templateId
	 * @return
	 */
	public boolean hasItemIgnoreBind(String itemSN, int count) {
		int gotCount = getItemCountIgnoreBind(itemSN);
		return gotCount >= count;
	}
	
	/**
	 * 查询在指定索引上是否有templateId指定的道具，区别绑定状态<br/>
	 * 只在主背包、材料、任务三个包里面找，而不搜索身上的装备、 仓库等
	 * 
	 * @param bag
	 * @param index
	 * @param templateId
	 * @param count
	 * @param bind
	 * @return
	 */
	public boolean hasItemConsiderBindByIndex(BagType bag, int index,
			int templateId, int count, BindStatus bind) {
		return hasItemByIndex(bag, index, templateId, count, true, bind);
	}

	/**
	 * 查询在指定索引上是否有templateId指定的道具，忽略绑定状态<br/>
	 * 只在主背包、材料、任务三个包里面找，而不搜索身上的装备、 仓库等
	 * 
	 * @param bag
	 * @param index
	 * @param templateId
	 * @param count
	 * @return
	 */
	public boolean hasItemIgnoreBindByIndex(BagType bag, int index,
			int templateId, int count) {
		return hasItemByIndex(bag, index, templateId, count, false, null);
	}
	
	/**
	 * 查询在指定索引上是否有templateId指定的道具<br/>
	 * 只在主背包、材料、任务三个包里面找，而不搜索身上的装备、 仓库等
	 * 
	 * @param bag
	 * @param index
	 * @param templateId
	 * @param count
	 * @param considerBind
	 * @param bind
	 * @return
	 */
	private boolean hasItemByIndex(BagType bag, int index, int templateId,
			int count, boolean considerBind, BindStatus bind) {
		if (bag == null || bag == BagType.NULL) {
			return false;
		}
		if (!AbstractItemBag.isPrimBag(bag)) {
			return false;
		}
		if (considerBind && bind == null) {
			return false;
		}
		// 检测包格中物品数量是否够
		AbstractItemBag aib = bags.get(bag);
		Item item = aib.getByIndex(index);
		if (Item.isEmpty(item)) {
			return false;
		}
		if (item.getTemplateId() != templateId) {
			return false;
		}
		if (considerBind && item.getBindStatus() != bind) {
			return false;
		}
		if (item.getOverlap() < count) {
			return false;
		}
		return true;
	}
	
	/**
	 * 移除一定数量的某种道具，要么全删，要么不删，只能移除主背包、材料包、任务道具包中的道具，区分绑定状态
	 * 
	 * @param <T>
	 * @param templateId
	 * @param count
	 * @param reason
	 * @return 移除了的Item
	 */
	public Collection<Item> removeItemConsiderBind(String itemSN, int count,
			BindStatus bind, ItemLogReason reason, String detailReason) {
		ShoulderBag bag = this.getBagByTemplateId(itemSN);
		if (bag == null) {
			return Collections.emptyList();
		}
		Collection<Item> updateList = bag.removeItemConsiderBind(itemSN,
				count, bind, reason, detailReason);
		// 通知客户端
		noticeClient(updateList);
		return updateList;
	}

	 
	
	/**
	 * 移除count个templateId指定的所有道具，要么全删，要么不删，只能移除主背包、材料包、任务道具包中的道具，不区分绑定状态
	 * 
	 * @param <T>
	 * @param templateId
	 * @param count
	 * @param reason
	 * @return
	 */
	public Collection<Item> removeItemIgnoreBind(String itemSN, int count,
			ItemLogReason reason, String detailReason) {
		ShoulderBag bag = this.getBagByTemplateId(itemSN);
		if (bag == null) {
			return Collections.emptyList();
		}
		Collection<Item> updateList = bag.removeItemIgnoreBind(itemSN,
				count, reason, detailReason);
		// 通知客户端
		noticeClient(updateList);
		return updateList;
	}
	
 
	
	/**
	 * 拆分道具
	 * 
	 * @param bagType
	 * @param index
	 * @param count
	 */
	public void splitItem(BagType bagType, int index, int count) {
		// 状态冲突检查

		// 只有主背包、材料包、任务道具包三个背包有拆分功能
		if (!AbstractItemBag.isPrimBag(bagType)) {
			return;
		}
		ShoulderBag bag = (ShoulderBag) bags.get(bagType);
		// 检查道具状态是否为空，是否锁定等
		Item itemToSplit = bag.getByIndex(index);
		if (Item.isEmpty(itemToSplit)) {
			owner.sendErrorMessage(ItemLangConstants_20000.ITEM_CANNOT_SLIT);
			return;
		}
//		if (itemToSplit.isLocked()) {
//			return;
//		}

		Item emptyItem = bag.getEmptySlot();
		// 没有地方拆分
		if (emptyItem == null) {
			// 您的背包没有足够的空间
			owner.sendErrorMessage( ItemLangConstants_20000.ITEM_NOT_ENOUGH_SPACE);
		}
		// 执行拆分
		List<Item> updateList = bag.split(index, count);
		// 更新客户端背包
		for (Item item : updateList) {
			owner.sendMessage(item.getUpdateMsgAndResetModify());
		}
	}
	
	/**
	 * 整理背包
	 * 
	 * @param bagType
	 */
	public void tidyBag(BagType bagType) {
		// 状态冲突检查

		// 主背包、材料包、任务道具包三个背包有整理功能
		// 仓库也有整理功能
		if (!AbstractItemBag.isPrimBag(bagType) && bagType != BagType.DEPOT) {
			return;
		}
		
		CommonBag bag = (CommonBag) bags.get(bagType);
		
		bag.tidyUp();
		// 刷新背包信息
		GCMessage msg = null;
		switch (bagType) {
			case PRIM:
				msg = getPrimBagInfoMsg();
				break;
			case DEPOT:
				msg = getDepotBagInfoMsg();
				break;
		}

		owner.sendMessage(msg);
	}
	

	/**
	 * 当指定位置的物品被删除后,重置对应位置的ItemInstance对象
	 * 
	 * @param bagType
	 *            包的类型
	 * @param index
	 *            包的索引位置
	 */
	public void resetAfterDel(BagType bagType, String wearerId, int index) {
		final AbstractItemBag bag = getBagByType(bagType, wearerId);
		final Item item = getItemByIndex(bagType,wearerId, index);
		if (item == null) {
			logger.error(String.format("背包中出现null的Item引用 bag=%s index=%d",
					bagType.name(), index));
			return;
		}
		Item resetItem = Item.newEmptyOwneredInstance(owner, bagType, index);
		bag.putItem(resetItem);
	}
		
	/**
	 * 丢弃道具
	 * 
	 * @param bagType
	 * @param index
	 */
	public void dropItem(BagType bagType, int index) {
		// 状态冲突检查

		Item droped = null;

		switch (bagType) {
		case PRIM:
			ShoulderBag bag = (ShoulderBag) bags.get(bagType);
			droped = bag.drop(index);
			bag.onChanged();
			break;
		}

		if (droped != null) {
			owner.sendMessage(droped.getUpdateMsgAndResetModify());
		}
	}
	
	/**
	 * 直接拾取道具1个实例，一般是有实例属性的最大叠加数未1的
	 * 
	 * @param item
	 *            道具实例，这里提供的实例时从ItemService中生成出来的，overlap为0，并且需要激活才可以用
	 * @param reason
	 *            原因
	 * @param detailReason
	 *            详细原因（可无）
	 * @param needNotify
	 *            是否通知客户端"您或得了x个x"
	 * @return 如果道具不存在或者放不下返回false，否则返回true
	 */
	public boolean giveOneItemInstance(Item item, ItemGenLogReason reason,
			String detailReason, BindStatus bind, boolean needNotify) {
		ItemTemplate template = item.getTemplate();
		BagType bagType = template.getBagType();
		// 找到应该放入的包
		ShoulderBag bag = (ShoulderBag) bags.get(bagType);
		Item emptySlot = bag.getEmptySlot();
		if (emptySlot == null) {
			if (needNotify) {
				StringBuilder msgSb = new StringBuilder();
				String bagName = langManager.read(bagType.getNameLangId());
				// {0}中需要{1}个空位
				msgSb.append(langManager.read(ItemLangConstants_20000.ITEM_MAKE_SPACE, bagName, 1));
				// 您的背包没有足够的空间
				owner.sendErrorMessage( ItemLangConstants_20000.ITEM_NOT_ENOUGH_SPACE);
				// 通知每个包需要多少个空位
				owner.sendErrorMessage(msgSb.toString());
			}
			return false;
		}

		item.setBagType(emptySlot.getBagType());
		item.setIndex(emptySlot.getIndex());
		item.setBindStatus(bind);
		item.setOwner(owner);
		// 记录道具产生日志
		String genKey = KeyUtil.UUIDKey();
		try {
			// 增加物品增加原因到reasonDetail
			String countChangeReason = " [genReason:" + reason.getClass().getField(reason.name()).getAnnotation(ReasonDesc.class).value() + "] ";
			detailReason = detailReason == null ? countChangeReason : detailReason
					+ countChangeReason;
			
			Globals.getLogService().sendItemGenLog(owner, reason, detailReason,
					template.getId(), template.getName(), 1, bind.index, 0, item.toEntity().getProperties(), genKey);
		} catch (Exception e) {
			Loggers.itemLogger.error(LogUtils.buildLogInfoStr(owner.getUUID(),
					"记录直接拾取道具1个实例日志时出错"), e);
		}
		item.changeOverlap(1, ItemLogReason.COUNT_ADD, detailReason, genKey,
				true);
		item.getLifeCycle().activate();
		item.setModified();
		bag.putItem(item);
		owner.sendMessage(item.getUpdateMsgAndResetModify());
		if (needNotify) {
			// 提示：您或得了x个x
			owner.sendSystemMessage(CommonLangConstants_10000.GET_SOMETHING, 1, item.getName());
		}
		return true;
	}

	/**
	 * 开始道具使用冷却，只有主道具包和任务包中的道具需要冷却
	 * 
	 * @param needStartPubCD
	 *            是否开始公共cd
	 */
	public void startCoolDown(Item item,int cooldown) {
		if(item != null)
		{
			item.startCoolDown();
			// 给客户端发送GCItemStartCd消息
			GCItemStartCd msg = new GCItemStartCd(item.getUUID(),cooldown);
			owner.sendMessage(msg);
		}
	}

	
	/**
	 * 物品数量变化时通知监听器
	 * 此处调用是较频繁的
	 * 
	 * @param templateId
	 */
	public void onItemCountChanged(final int templateId) {
		//通知对包中道具数量变化感兴趣的监听器

	}
	
	/**
	 * 通过UUID查找一个道具，查找范围按顺序：主背包、身上、仓库、材料、任务。 此方法开销比较大，慎用
	 * 
	 * @param bag
	 * @param UUID
	 * @return
	 */
	public Item getItemByUUID(String UUID) {
		Item result = primBag.getByUUID(UUID);
		if (result == null) {  
			result = this.equipBag.getByUUID(UUID);
			if(result == null)
			{
				result = this.depotBag.getByUUID(UUID);				
			}
			if(result == null){	 
				for(PetBodyEquipBag petBag : petBags.values()) {
					result = petBag.getByUUID(UUID);
					if(result != null)
					{
						break;
					}
				}	
			}
		} else {
			return result;
		}
		return result;
	}
	
	/**
	 * 通过索引取道具对象
	 * 
	 * @param bag
	 *            包id
	 * @param index
	 *            在包中的索引
	 * @return 如果包id或index不存在，返回null
	 */
	public Item getItemByIndex(BagType bagType, String wearerId, int index) {
		AbstractItemBag bag = getBagByType(bagType, wearerId);
		if(bag != null)
		{
			return bag.getByIndex(index);			
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 按索引移除物品
	 * 
	 * @param bag
	 * @param index
	 * @param count
	 * @param reason 
	 * @param detailReason
	 * @return
	 */
	public boolean removeItemByIndex(BagType bag, int index, int count,
			ItemLogReason reason, String detailReason) {
		return removeItemByIndex(bag, new int[] { index }, new int[] { count },
				reason, detailReason);
	}

	/**
	 * 按指定索引数组移除所有的物品。 使用该方法时要注意：物品的堆叠数若小于要移除的物品数，则最多扣除堆叠的个数。
	 * <ul>
	 * <li>注：此处只能移除主背包、材料包和任务包的物品</li>
	 * </ul>
	 * 
	 * @param bag
	 * @param index
	 * @param count
	 * @param reason
	 * @param detailReason 
	 * @return
	 */
	public boolean removeItemByIndex(BagType bag, int index[], int[] count,
			ItemLogReason reason, String detailReason) {
		if (bag == null || bag == BagType.NULL) {
			return false;
		}
		if (!AbstractItemBag.isPrimBag(bag) && (bag != BagType.XINGHUN) && (bag != BagType.XIANGQIAN)) {
			return false;
		}

		// 检测各个索引对应的包格中物品数量是否够
		AbstractItemBag aib = bags.get(bag);
		for (int i = 0; i < index.length; i++) {
			Item item = aib.getByIndex(index[i]);
			if (Item.isEmpty(item)) {
				if (Loggers.itemLogger.isWarnEnabled()) {
					Loggers.itemLogger.warn(String.format(
							"Can't remove %d item,item is empty!", count[i]));
				}
				continue;
			}

			if (item.getOverlap() < count[i]) {
				count[i] = item.getOverlap();
				if (Loggers.itemLogger.isWarnEnabled()) {
					Loggers.itemLogger
							.warn(String
									.format(
											"Can't remove %d item(%d),there are only %d items!",
											count[i], item.getTemplateId(),
											item.getOverlap()));
				}
			}
		}

		// 扣除物品
		for (int i = 0; i < index.length; i++) {
			Item item = aib.getByIndex(index[i]);
			if (!Item.isEmpty(item)) {
				item.changeOverlap(item.getOverlap() - count[i], reason, "",
						"", true);
				owner.sendMessage(item.getUpdateMsgAndResetModify());
			}
		}
		return true;
	}


	/**
	 * 将所有需要存库的包裹中的item全都转成ItemEntity
	 * 
	 * @return
	 */
	public List<ItemEntity> toItemEntities() {
		List<ItemEntity> entities = new ArrayList<ItemEntity>();
		entities.addAll(primBag.toItemEntitys());
		return entities;
	}
		
	public List<Item> getBadItems() {
		return badItems;
	}
	
	public ShoulderBag getPrimBag() {
		return primBag;
	}


	public ShoulderBag getXinghunBag() {
		return xinghunBag;
	}


	public void setXinghunBag(ShoulderBag xinghunBag) {
		this.xinghunBag = xinghunBag;
	}


	public ShoulderBag getXiangqianBag() {
		return xiangqianBag;
	}


	public void setXiangqianBag(ShoulderBag xiangqianBag) {
		this.xiangqianBag = xiangqianBag;
	}


	/**
	 * 生成主背包消息对象
	 * 
	 * @return
	 */
	public GCBagUpdate getPrimBagInfoMsg() {
		return ItemMessageBuilder.buildGCBagUpdate(primBag);
	}
	/**
	 * 生成装备消息对象
	 * @author xf
	 */
	public GCBagUpdate getEquipBagInfoMsg() {
		return ItemMessageBuilder.buildGCBagUpdate(equipBag);
	}
	/**
	 * 生成仓库消息对象
	 * @author xf
	 */
	public GCBagUpdate getDepotBagInfoMsg() {
		return ItemMessageBuilder.buildGCBagUpdate(depotBag);
	}
	
	public GCBagUpdate getXinghunBagInfoMsg() {
		return ItemMessageBuilder.buildGCBagUpdate(xinghunBag);
	}
	
	public GCBagUpdate getXiangqianBagInfoMsg() {
		return ItemMessageBuilder.buildGCBagUpdate(xiangqianBag);
	}
	
	/**
	 * 生成宠物的背包消息
	 * @param petId
	 * @return
	 */
	public GCBagUpdate getPetEquipBagInfoMsg(String petId) {
		PetBodyEquipBag petBag = this.getBagByPet(petId);
		GCBagUpdate gcBagUpdate = ItemMessageBuilder.buildGCBagUpdate(petBag);
		gcBagUpdate.setWearerId(petId);
		return gcBagUpdate;
	}
	
	
	
	
	private EnumMap<BagType, Integer> countNeedSlot(Collection<ItemParam> params) {
		EnumMap<BagType, Integer> needSlotCounter = new EnumMap<BagType, Integer>(
				BagType.class);
		// 遍历所有请求，统计每个包需要多少个空位才能放下
		for (ItemParam param : params) {
			String itemSN = param.getItemSN();
			ItemTemplate template = itemService.getTemplateByItemSn(itemSN);
			if(template == null){
				//TODO 传入参数错误
				Loggers.itemLogger.error("template = null, ignot... "+param);			
				continue;
			}
			// 找到此道具应该放入的包
//			ShoulderBag bag = getBagByTemplateId(templateId);
			ShoulderBag bag = this.primBag;
			int count = param.getCount();
			BindStatus bind = param.getBind();
			// 此包需要的空位
			int needSlot = bag.getNeedSlot(template, count, bind);
			BagType bagType = bag.getBagType();
			if (needSlotCounter.containsKey(bagType)) {
				needSlotCounter.put(bagType, needSlotCounter.get(bagType)
						+ needSlot);
			} else {
				needSlotCounter.put(bagType, needSlot);
			}
		}
		return needSlotCounter;
	}

	/**
	 * 根据模板id得到所属的背包，得到的包为主背包、材料包、任务道具包之一
	 * 
	 * @param templateId
	 * @return 如果所属背包不是三个包之一返回null
	 */
	private ShoulderBag getBagByTemplateId(String itemSN) {
		ItemTemplate tmpl = itemService.getItemTempByTempId(itemSN);
		if (tmpl == null) {
			return null;
		}
		AbstractItemBag bag = bags.get(tmpl.getBagType());
		// 只能移除主背包、材料包、任务道具包中的道具
		if (!AbstractItemBag.isPrimBag(bag.getBagType())) {
			// 走到这里很有可能是填表填错了
			return null;
		}
		// 三个背包都是这种类型
		return (ShoulderBag) bag;
	}
	
	/**
	 * 通知客户端更新物品信息
	 * @param updateList
	 */
	private void noticeClient(Collection<Item> updateList) {
		if (!updateList.isEmpty()) {
			// 通知客户端更新背包
			for (Item item : updateList) {
				owner.sendMessage(item.getUpdateMsgAndResetModify());
			}
		}
	}
	
	public void synBagFreezeItem(AbstractItemBag bag) {
		Collection<Item> items = bag.getAll();
		EquipmentFeature equipmentFeature = null;
		
		for (Item item : items) {
			//判断取消绑定物品的绑定时间是否已经过期
			if(item.isEquipment()){
				equipmentFeature = (EquipmentFeature)item.getFeature();
				if(equipmentFeature.getFreezeState() == FreezeState.CANCELFREEZE.index
						&& Globals.getTimeService().now() >= equipmentFeature.getCancelTime()){
					equipmentFeature.setFreezeState(FreezeState.NOMAL.index);
					equipmentFeature.setCancelTime(0);
					
					GCItemUpdate gcItemUpdate = ItemMessageBuilder.buildGCItemInfo(item);
					owner.sendMessage(gcItemUpdate);
				}
			}
		}
	}

	/**
	 * 取得所有的血包
	 * @author xf
	 */
	public Item[] getAllHpBuff(){
		Collection<Item> cols = this.primBag.getAll();
		List<Item> list = new ArrayList<Item>();
		for(Item item : cols){
			if(item.getTemplate().getItemType() == ItemDef.Type.HP_ITEM){
				list.add(item);
			}
		}
		return list.toArray(new Item[0]);
	}
	
	/**
	 * 获取所有武器
	 * @return
	 */
	public Item[] getAllWeapon(){
		
		Collection<Item> cols1 = this.primBag.getAll();
		List<Item> list = new ArrayList<Item>();
		for(Item item : cols1){
			if(item.getTemplate().getItemType() == ItemDef.Type.WEAPON){
				list.add(item);
			}
		}
		
		Collection<Item> cols2 = this.depotBag.getAll();
		for(Item item : cols2){
			if(item.getTemplate().getItemType() == ItemDef.Type.WEAPON){
				list.add(item);
			}
		}
		
		Item item = this.equipBag.getWeapon();
		if(item != null){
			list.add(item);
		}
		
		for(Map.Entry<String, PetBodyEquipBag> pb : this.petBags.entrySet()){
			if(pb.getValue().getByPosition(Position.WEAPON)!=null){
				list.add(pb.getValue().getByPosition(Position.WEAPON));
			}
		}
		return list.toArray(new Item[0]);
	}
	
	public void setXinghunForWeapon(Item weapon){
		Collection<Item> cols = this.xiangqianBag.getAll();
		for(Item item : cols){
			XinghunFeature feature = (XinghunFeature)item.getFeature();
			if(feature != null){
				if(feature.getEquipmentUuid().equals(weapon.getUUID())){
					int id = feature.getXingzuoId();
					int index = item.getIndex();
					EquipmentFeature eqfeature = (EquipmentFeature)weapon.getFeature();
					eqfeature.setXinghunIndex(id, index);
				}			
			}
		}
	}

}
