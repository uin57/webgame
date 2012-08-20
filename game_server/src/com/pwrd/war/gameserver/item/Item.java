package com.pwrd.war.gameserver.item;

import java.sql.Timestamp;

import com.pwrd.war.common.LogReasons.ItemLogReason;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.msg.DataType;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.LifeCycleImpl;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.db.model.ItemEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.container.Bag;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.common.container.Containable;
import com.pwrd.war.gameserver.common.i18n.constants.ItemLangConstants_20000;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.ItemDef.Type;
import com.pwrd.war.gameserver.item.msg.GCItemUpdate;
import com.pwrd.war.gameserver.item.msg.GCRemoveItem;
import com.pwrd.war.gameserver.item.msg.ItemMessageBuilder;
import com.pwrd.war.gameserver.item.operation.UseItemOperPool;
import com.pwrd.war.gameserver.item.operation.UseItemOperation;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.role.Role;

/**
 * 
 * 一个道具，每个非空（count>0）的物品对应数据库中一条记录
 * 
 * 
 */
public class Item implements Containable,
		PersistanceObject<String, ItemEntity>, Bindable  {

	/** 道具的实例UUID */
	private String UUID;
	/** 道具模板 */
	private ItemTemplate template;
	/** 堆叠个数 */
	private int overlap;
	/** 所在包裹 */
	private BagType bagType;
	/** 所在格子的位置 */
	private int index;
	/** 是否已经变更了 */
	private boolean modified;
	/** 冷却的绝对时间点，存库时需要存相对时间，即还需要多长时间可以冷却 */
	private long coolDownTimePoint;
	/** 物品的生命期的状态 */
	private final LifeCycle lifeCycle;
	/** 此实例是否在db中 */
	private boolean isInDb;
	/** 道具实例属性，消耗品此属性为null */
	private ItemFeature feature;
	/** 所有者 */
	private Human owner;
	/** 佩戴者uuid (宠物or武将) 0表示没有佩带者*/
	private String wearerId;
	/** 创建时间 */
	private Timestamp createTime;
	/** 绑定状态 */
	private BindStatus bindStatus;
	/** 是否已经被锁定 */
	private boolean isLocked;

	
	/**
	 * 判断一个item是否为空，不考虑锁定，过期，销毁等特殊情况 <br/>
	 * 注意，检测一个item是否为可以放东西的空格不可以用此方法这种格子的条件为(item != null && item.isEmpty())
	 * 
	 * @param item
	 * @return
	 */
	public static boolean isEmpty(Item item) {
		return item == null || item.isEmpty();
	}

	/**
	 * 判断是否属于同一个道具模板
	 * 
	 * @param templateId
	 * @param item
	 * @return
	 */
	public static boolean isSameTemplateId(int templateId, Item item) {
		return item != null && !item.isEmpty()
				&& item.getTemplateId() == templateId;
	}

	public static boolean isSameTemplate(String itemSN, Item item) {
		return item != null && !item.isEmpty()
				&& StringUtils.isEquals(item.getTemplate().getItemSn(), itemSN);
	}
	/**
	 * 判断是否为同一种道具，即他们的道具模板是同一个，不考虑绑定状态，不考虑锁定，过期，销毁等特殊情况
	 * 
	 * 
	 * @param itemA
	 * @param itemB
	 * @return 
	 *         如果itemA,itemB任意一个为null或为空，有模板但不同时返回false，否则返回true。当两个都为空时同样返回false
	 */
	public static boolean isSameItem(Item itemA, Item itemB) {
		if (Item.isEmpty(itemA) || Item.isEmpty(itemB)) {
			return false;
		}
		if (itemA.getTemplateId() == itemB.getTemplateId()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断两个道具能否叠加在一起，规则为，两个非空item如果模板相同，绑定状态相同，就可以叠加。不考虑锁定，过期，销毁等特殊情况
	 * 
	 * 
	 * @param itemA
	 * @param itemB
	 * @return
	 */
	public static boolean canMerge(Item itemA, Item itemB) {
		if (Item.isEmpty(itemA) || Item.isEmpty(itemB)) {
			return false;
		}
		return isSameItem(itemA, itemB)
				&& itemA.getBindStatus() == itemB.getBindStatus();
	}

	/**
	 * @param owner
	 *            所有者
	 * @param template
	 *            道具模板
	 * @param bagId
	 *            所在包的id 例如主背包的id为{@link Bag#PRIM_BAG_ID}
	 * @param index
	 *            在包中的索引
	 * @param overlap
	 *            堆叠数量
	 */
	private Item(Human owner, String wearerId, ItemTemplate template, BagType bagType, int index, int overlap) {
		lifeCycle = new LifeCycleImpl(this);
		this.owner = owner;
		this.wearerId = wearerId;
		this.template = template;
		if (this.template != null) {
			this.feature = template.initItemFeature(this);
		}
		this.bindStatus = BindStatus.NOT_BIND;
		this.bagType = bagType == null ? BagType.NULL : bagType;
		this.overlap = overlap;
		this.index = index;
	}

	/**
	 * 由一个已存在物品来构造一个物品，相当于复制
	 * <p>
	 * <strong>注意：</strong> <br>
	 * 1.该对象的生命周期并没有进行复制，处于未激活状态， 需要使用者根据相关业务逻辑，适时进行激活。 <br>
	 * 2.该对象与被复制对象的{@link #feature}用的是同一个引用， 如果这样实现有什么不合适请修改，改为深度复制。
	 * 
	 * @param item 已存在的物品
	 * 
	 */
	private Item(Item item) {
		this.UUID = KeyUtil.UUIDKey();
		this.template = item.template;
		this.overlap = item.overlap;
		this.bagType = item.bagType;
		this.index = item.index;
		this.coolDownTimePoint = item.coolDownTimePoint;
		this.lifeCycle = new LifeCycleImpl(this);
		this.feature = item.feature;
		if(this.feature != null){
			feature.bindItem(this);
		}
		this.owner = item.owner;
		this.wearerId = item.wearerId;
		this.createTime = item.createTime;
		this.bindStatus = item.bindStatus;
		this.isLocked = item.isLocked;
	}
	
	/**
	 * 物品数据加载完成后进行数据的校验,只能校验通过的物品才可以在加载后加入到{@link Inventory}当中.
	 * 
	 * @return true,数据正常,校验通过;false,数据异常,校验未通过
	 */
	public boolean validateOnLoaded(ItemEntity itemInfo) {
		if (this.isEmpty()) {
			try {
				onDelete();
				// 记录删除日志
				Human owner = getOwner();
				
				Globals.getLogService().sendItemLog(owner, ItemLogReason.LOAD_VALID_ERR,
						"加载时发现道具模板不存在，删除道具", bagType.index, index, itemInfo
								.getTemplateId(), UUID, 0, overlap, "",
								DataType.obj2byte(itemInfo));
				if (Loggers.itemLogger.isWarnEnabled()) {
					Loggers.itemLogger.warn(LogUtils.buildLogInfoStr(
							getCharId(), String.format(
									"[Found empty item from db item=%s",
									itemInfo.toString())));
				}
			} catch (Exception e) {
				Loggers.itemLogger.error(LogUtils.buildLogInfoStr(getOwner()
						.getUUID(), "记录道具数量变化日志时出错"), e);
			}
			return false;
		} else {
			try {
				// 查找相同位置是否已经有物品了
				Item item = this.getOwner().getInventory().getItemByIndex(
						getBagType(),getWearerId(), index);
				if (!Item.isEmpty(item)) {
					// 相同位置的物品重复,删除自身,记录删除日志
					onDelete();
					Human owner = getOwner();
					Globals.getLogService().sendItemLog(owner, ItemLogReason.LOAD_VALID_ERR,
							"加载时发现此道具所在的位置已经有道具了，删除自身", bagType.index, index,
							itemInfo.getTemplateId(), UUID, 0, overlap, "",
							DataType.obj2byte(itemInfo));
					if (Loggers.itemLogger.isWarnEnabled()) {
						Loggers.itemLogger
								.error(LogUtils.buildLogInfoStr(
										getCharId(),
										String.format("[Found duplicate item from db item=%s",
												itemInfo.toString())));
					}
					return false;
				}
			} catch (Exception e) {
				Loggers.itemLogger.error(LogUtils.buildLogInfoStr(getOwner()
						.getUUID(), "记录道具数量变化日志时出错"), e);
			}
		}
		return true;
	}

	
	/**
	 * 创建一个已经激活的道具，即创建之后将直接出现在游戏世界中，初始叠加数为0
	 * 
	 * 创建道具,都必须首先创建到主背包中,不能直接穿到身上
	 * 
	 * @param owner
	 *            所有者
	 * @param template
	 *            道具模板
	 * @param bagId
	 *            所在包id
	 * @param index
	 *            在包中的索引
	 * @return
	 */
	public static Item newActivatedInstance(Human owner, ItemTemplate template,	BagType bagType, int index) {
		Assert.notNull(owner);
		Assert.notNull(template);
		Item item = new Item(owner, "", template, bagType, index, 0);
		item.setUUID(KeyUtil.UUIDKey());
		item.setCreateTime(new Timestamp(Globals.getTimeService().now()));
		item.lifeCycle.activate();
		return item;
	}


	/**
	 * 创建若干个绑定了模板但不绑定所有者的道具，创建时默认是未激活状态，即不会出现在游戏世界中，也不会对应数据库中的记录<br />
	 * 此方法只用于创建Item对象，而不关心任何业务逻辑
	 * 
	 * 未激活的Item,自然不需要设置佩戴者
	 * 
	 * @param template
	 *            道具模板
	 * @param bagId
	 *            道具所在的包ID
	 * @param index
	 *            道具在包中的索引
	 * @return
	 * @throws IllegalArgumentException
	 *             如果template为null
	 */
	public static Item newDeactivedInstance(Human owner, ItemTemplate template,
			BagType bagType, int index) {
		Assert.notNull(template);
		Assert.notNull(bagType);
		Item item = new Item(owner, "", template, bagType, index, 0);
		item.lifeCycle.deactivate();
		item.setUUID(KeyUtil.UUIDKey());
		item.setCreateTime(new Timestamp(Globals.getTimeService().now()));
		return item;
	}

	/**
	 * 创建一个空的item实例，背包中的道具格子初始化时需要用空道具初始化
	 * 
	 * @param owner
	 * @param bagType
	 * @param index
	 * @return
	 */
	public static Item newEmptyOwneredInstance(Human owner, BagType bagType,
			int index) {
		Assert.notNull(owner);
		return new Item(owner, "", null, bagType, index, 0);
	}

	/**
	 * 根据一个item创建一个item实例，复制
	 * <p>
	 * <strong>注意：</strong> <br>
	 * 1.该对象的生命周期并没有进行复制，处于未激活状态， 需要使用者根据相关业务逻辑，适时进行激活。 <br>
	 * 2.该对象与被复制对象的{@link #feature}用的是同一个引用， 如果这样实现有什么不合适请修改，改为深度复制。
	 * 
	 * @param item
	 *            被复制的item
	 * @return 新创建的item实例
	 */
	public static Item newCloneInstance(Item item) {
		Assert.notNull(item);
		return new Item(item);
	}
	
	/**
	 * 根据模板Id和绑定状态生成一个唯一的long型的key
	 * 
	 * @param templateId
	 * @param bind
	 * @return
	 */
	public static long genKeyByTmplIdAndBind(int templateId, BindStatus bind) {
		long key = bind.getIndex();
		return key << Integer.SIZE | templateId;
	}


	/**
	 * 从ItemEntity生成一个item实例
	 * 
	 * @param entity
	 * @return
	 */
	public static Item buildFromItemEntity(ItemEntity entity, Human owner) {
		int templateId = entity.getTemplateId();
		ItemTemplate template = Globals.getTemplateService().get(templateId,
				ItemTemplate.class);
		BagType bagType = BagType.valueOf(entity.getBagId());
		Item item = new Item(owner, entity.getWearerId(), template, bagType, entity.getBagIndex(), entity.getOverlap());
		item.fromEntity(entity);
		return item;
	}

	
	@Override
	public BagType getBagType() {
		return bagType;
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	@Override
	public int getOverlap() {
		return overlap;
	}
	
	@Override
	public int getMaxOverlap() {
		return template != null ? template.getMaxOverlap() : 0;
	}
	
	@Override
	public BindMode getBindMode() {
//		return template != null ? template.getBindMode() : BindMode.NO_BIND;
//		
		return null;
	}

	@Override
	public String getCharId() {
		Human owner = getOwner();
		return owner == null ? "" : owner.getCharId();
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}
	
 

	
	@Override
	public String getDbId() {
		return UUID;
	}
	
	@Override
	public void setDbId(String id) {
		this.UUID = id;
	}

	@Override
	public boolean isInDb() {
		return isInDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.isInDb = inDb;
	}

	@Override
	public void setModified() {
		modified = true;
		// 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive() && !this.isEmpty()) {
			// 物品的生命期处于活动状态,并且该物品不是空的,则执行通知更新机制进行
			this.onUpdate();
		}
	}
	
	/**
	 * 将修改状态还原
	 */
	public void resetModified() {
		this.modified = false;
	}
	
	 
 
	
	@Override
	public void setBindStatus(BindStatus bindStatus) {
		this.bindStatus = bindStatus;
		setModified();
	}
	

	@Override
	public BindStatus getBindStatus() {
		return bindStatus;
	}
 
	
	@Override
	public void fromEntity(ItemEntity entity) {
		// 这里是加载的时候调用的，尽量不调用自己的set方法，因为set方法会通知更新，这是没有必要的
		BagType bagType = BagType.valueOf(entity.getBagId());
		this.bagType = bagType;
		this.index = entity.getBagIndex();
		this.overlap = entity.getOverlap();
		this.UUID = entity.getId();
		BindStatus bind = BindStatus.valueOf(entity.getBind());
		this.bindStatus = bind;
		this.createTime = entity.getCreateTime();
		if (this.feature != null) {
			// 这个有可能为null
//			this.feature.setCurEndure(entity.getCurEndure());
			this.feature.fromPropertyString(entity.getProperties());
		}
		this.coolDownTimePoint = entity.getCoolDownTimePoint();
//		buildFeature(entity);
	}
	
	@Override
	public ItemEntity toEntity() {
		ItemEntity entity = new ItemEntity();
		entity.setId(this.getUUID());
		entity.setTemplateId(this.getTemplateId());
		entity.setBagId(this.getBagType().index);
		entity.setBagIndex(this.getIndex());
		entity.setOverlap(this.getOverlap());
		entity.setBind(this.getBindStatus().index);
		entity.setCharId(this.getOwner().getUUID());
		entity.setWearerId(this.getWearerId());
		entity.setCreateTime(this.getCreateTime());
//		if (this.feature != null) { // 道具被删除时可能feature为null
////			entity.setCurEndure(this.feature.getCurEndure()); 
//		}
		entity.setCoolDownTimePoint(coolDownTimePoint);
		if(feature != null){
			entity.setProperties(feature.toPropertyString());
		}

		return entity;
	}
	
	/* ==============  业务方法BEGIN  ==============  */
	
	/**
	 * 修改物品实例的数量
	 * 
	 * @param newOverlap
	 *            设置物品的目标数量,当物品的数量设置为0时,将触发物品的删除事件
	 * @param reason
	 *            物品数量改变的原因
	 * @param detailReason
	 *            改变原因详细说明（可无）
	 * @param genKey
	 *            物品产生的key(物品删除时可无)
	 * @param needSendLog
	 *            是否需要向logserver发送日志
	 */
	public void changeOverlap(final int newOverlap, ItemLogReason reason,
			String detailReason, String genKey, boolean needSendLog) {
		if (newOverlap == this.overlap) {
			return;
		}
		int oldOverlap = this.overlap;
		// 规范化，防止出现非法值
		this.overlap = normalizeOverlap(newOverlap);

		//通知inventory该 实例的数量被修改了
		getOwner().getInventory().onItemCountChanged(getTemplateId());

		// 转换转化为数据对象,用于记录详细的道具属性,接到detailReason后面,作为log的其他参数
		ItemEntity entity = toEntity();
//		detailReason = detailReason == null ? entity.toString() : detailReason
//				+ " " + entity.toString();
		if (this.overlap > 0) {
			try {
				// 记录数量变化日志
				Human owner = getOwner();
				Globals.getLogService().sendItemLog(owner, reason, detailReason,
						getBagType().index, getIndex(), getTemplateId(), UUID,
						overlap - oldOverlap, overlap, genKey, DataType.obj2byte(entity));
			} catch (Exception e) {
				Loggers.itemLogger.error(LogUtils.buildLogInfoStr(getOwner()
						.getUUID(), "记录道具数量变化日志时出错"), e);
			}
			setModified();
		} else if (this.overlap == 0) {
			try {
				// 记录物品删除日志 ： 数量变成0
				Human owner = getOwner();
				Globals.getLogService().sendItemLog(owner, reason, detailReason, 
						getBagType().index, getIndex(), getTemplateId(), UUID,
						overlap - oldOverlap, overlap, genKey, DataType.obj2byte(entity));
			} catch (Exception e) {
				Loggers.itemLogger.error(LogUtils.buildLogInfoStr(getOwner()
						.getUUID(), "记录道具数量变化日志时出错"), e);
			}
			// 物品已经被删除
			onDelete();
		} else {
			throw new IllegalStateException("The overlap must not be <0 ");
		}
	}

	/**
	 * 删除这个道具
	 * 
	 * @param reason
	 * @param detailReason
	 */
	public void delete(ItemLogReason reason, String detailReason) {
		changeOverlap(0, reason, detailReason, "", true);
	}
	
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	protected void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String getGUID() {
		return  this.UUID;
	}

	public String getUUID() {
		return this.UUID;
	}

	private void setUUID(String uuid) {
		this.UUID = uuid;
	}
	
	public void setIndex(int index) {
		this.index = index;
		setModified();
	}
	
	public void setBagType(BagType bagType) {
		this.bagType = bagType;
		setModified();
	}
	
 

	public void setOwner(Human owner) {
		this.owner = owner;
		setModified();
	}

	public Human getOwner() {
		return owner;
	}
	
	public void setWearerId(String wearerId) {
		this.wearerId = wearerId;
		setModified();
	}
	
	public String getWearerId()
	{
		return this.wearerId;
	}
	
	public ItemFeature getFeature() {
		return feature;
	}

	/**
	 * 是否是空物品
	 * @return
	 */
	public boolean isEmpty() {
		return getTemplate() == null || getOverlap() == 0;
	}
	
	
	/**
	 * 开始自身冷却
	 */
	public void startCoolDown() {
		if (!isCoolingDown()) {
			coolDownTimePoint = Globals.getTimeService().now() + getCoolDown();
		}
	}
	
	/**
	 * 获取还没转完的冷却时间
	 * 
	 * @return
	 */
	public long getLeftCD() {
		long leftCD = coolDownTimePoint - Globals.getTimeService().now();
		return leftCD > 0 ? leftCD : 0;
	}


	public long getCoolDownTimePoint() {
		return coolDownTimePoint;
	}
	
	/**
	 * 初始化冷却时间点
	 * 
	 * @param coolDownTimePoint
	 */
	public void initCoolDownTimePoint(long coolDownTimePoint) {
		this.coolDownTimePoint = coolDownTimePoint;
	}

	/**
	 * 检查是否出于cd状态
	 */
	public boolean isCoolingDown() {
		return !Globals.getTimeService().timeUp(coolDownTimePoint);
	}

 

	/**
	 * 检测是否处于可用状态
	 * 
	 * @return 如此物未激活、已过期、已销毁返回false 否则返回true
	 */
	public boolean checkAvailable() {
		if (!lifeCycle.isActive() || lifeCycle.isDestroyed()) {
			return false;
		} else {
			return true;
		}
	}

 
  
		
	
	/**
	 * 生成一个道具信息消息
	 * 
	 * @return
	 */
	public GCItemUpdate getItemInfoMsg() {
		return ItemMessageBuilder.buildGCItemInfo(this);
	}
	
	/**
	 * 返回物品变化信息，并重置变化的状态，此方法需要在调用之前有对此Item的修改操作，否则将返回一个删除0号包（默认的NULL包）0号索引的删除消息
	 * 
	 * @return 更新道具信息的消息，可能是删除，也可能是更新个数等
	 */
	public GCMessage getUpdateMsgAndResetModify() {
		// 判断是需要更新还是删除物品
		if (needUpdate()) {
			// 如果是更新: 重置修改状态
			resetModified();
			return getItemInfoMsg();
		} else if (needDelete()) {
			// 如果是删除
			resetModified();
			return new GCRemoveItem((short) getBagType().index,	(short) getIndex(), owner.getUUID());
		} else {
			try {
				throw new RuntimeException();
			} catch (RuntimeException e) {
				Loggers.itemLogger.error("不需要更新时调用了此方法，请检查调用者的逻辑", e);
			}
			// 返回一个默认的操作
			return new GCRemoveItem((short) BagType.NULL.index, (short) 0, "");
		}
	}
	
 
	
	
	/* ==============  业务方法END  ==============  */
	
	/* --------------  私有方法  ---------------- */
	
	 
	
	
	private int normalizeOverlap(int overlap) {
		int normalized = 0;
		boolean ok = true;
		if (overlap < 0) {
			normalized = 0;
			ok = false;
		} else if (overlap > getMaxOverlap()) {
			normalized = getMaxOverlap();
			ok = false;
		} else {
			normalized = overlap;
		}
		if (!ok) {
			Loggers.itemLogger.error(String.format(
					"非法的叠加数  overlap=%d itemUUID=%s", overlap, UUID));
		}
		return normalized;
	}

	
	/**
	 * 是否需要通知客户端更新了
	 * 
	 * @return
	 */
	private boolean needUpdate() {
		if (modified && overlap > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否需要通知客户端删除了
	 * 
	 * @return
	 */
	private boolean needDelete() {
		if (modified && overlap == 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 物品实例被修改(新增加或者属性更新)时调用,触发更新机制
	 */
	private void onUpdate() {
		if (Loggers.itemLogger.isDebugEnabled()) {
			Loggers.itemLogger.debug(String.format(
					"update item=%s bag=%s index=%s", this.toString(),
					getBagType(), getIndex()));
		}
		this.getOwner().getPlayer().getDataUpdater().addUpdate(
				this.getLifeCycle());
	}
	
	/**
	 * 物品被删除时调用,恢复默认值,并触发删除机制
	 * 
	 */
	private void onDelete() {
		modified = true;
		template = null;
		feature = null;
		overlap = 0;
		setBindStatus(BindStatus.NOT_BIND);
		this.lifeCycle.destroy();
		getOwner().getInventory().resetAfterDel(getBagType(),getWearerId(), getIndex());
		if (Loggers.itemLogger.isDebugEnabled()) {
			Loggers.itemLogger.debug(String.format(
					"delete item=%s bag=%s index=%s", this.toString(),
					getBagType(), getIndex()));
		}
		this.getOwner().getPlayer().getDataUpdater().addDelete(
				this.getLifeCycle());
	}

 
 
	
	/* --------------  委托给template完成 ---------------- */
	
	/**
	 * 获得道具模版ID
	 * @return
	 */
	public ItemTemplate getTemplate() {
		return template;
	}
	
	/**
	 * 道具模板ID
	 * 
	 * @return 如果此道具已经绑定了模板，返回模板id，否则返回-1
	 */
	public int getTemplateId() {
		return template != null ? template.getId() : -1;
	}

	/**
	 * 此道具的位置，当此道具会被放在一个固定的位置上时有效
	 * 
	 * @return
	 */
	public Position getPosition() {
		return template != null ? template.getPosition() : Position.NULL;
	}

	/**
	 * 得到当前绑定状态经过oper操作后的绑定状态
	 * 
	 * @param oper
	 * @return
	 */
	public BindStatus getBindStatusAfterOper(Bindable.Oper oper) {
		return template != null ? template.getBindStatusAfterOper(
				getBindStatus(), oper) : BindStatus.NOT_BIND;
	}
	
	public String getName() {
		return template == null ? "" : template.getName();
	}
	
	public Type getItemType() {
		return template != null ? template.getItemType() : Type.NULL;
	}
	
	 
	public int getSex() {
		return template != null ? template.getSexLimit() : -1;
	}
	
	public int getCoolDown() {
		return template == null ? 0 : template.getCoolDown();
	}

	/**
	 * 判断一个道具是否为装备，装备是指武器和防具，在添加新的装备大类时需要修改此方法，此方法应该定义装备这个概念的唯一方法。
	 * 
	 * @return
	 */
	public boolean isEquipment() {
		return template == null ? false : template.isEquipment();
	}
	
	/**
	 * 是否可以叠加
	 * 
	 * @return
	 */
	public boolean canOverlap() {
		if (template != null) {
			return template.canOverlap();
		} else {
			return false;
		}
	}

	/**
	 * 使用这个道具
	 * @author xf
	 */
	public boolean use(String targetUuid ,String params){
		Human human = this.owner;
		BagType bagType = this.bagType;
		if (bagType == null || bagType == BagType.NULL) {
			// 估计是作弊了
			owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_CANNOT_USE);
			return false;
		} 
		Role target = null;
		if(bagType == BagType.PRIM)
		{  
			if(StringUtils.isEquals(targetUuid, human.getUUID())){
				target = human;
			}else{
				target = human.getPetManager().getPetByUuid(targetUuid); 
			}
		}
		else if(bagType == BagType.HUMAN_EQUIP)
		{ 
			target = human;
		}
		else if(bagType == BagType.PET_EQUIP)
		{ 
			//target是武将，因为后面要根据target类型决定是要调用武将的穿装备还是人的
			target = human.getPetManager().getPetByUuid(this.wearerId); 
		}
		// 不是作弊就是客户端与服务器数据不同步了，比如延迟大，连点了两次使用
		if (Item.isEmpty(this)) {
			owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_NOT_EXIST);
			return false;
		}

		if (!this.getTemplate().getCanBeUsed()) {
			owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_CANNOT_USE);
			return false;
		}

		// 只可以使用主背包、身上、任务包中的道具，否则是作弊
		if (bagType != BagType.PRIM && bagType != BagType.PET_EQUIP && bagType != BagType.HUMAN_EQUIP) {
			try {
//				Globals.getLogService().sendCheatLog(
//								human,
//								CheatLogReason.USE_ITEM,
//								String.format("使用的道具在不可以执行使用操作的包里: bagId=%d bagIndex=%d passportId=%d roleId=%d",
//												bagId, index, player.getPassportId(), human.getUUID()),
//								"");
			} catch (Exception e) {
				Loggers.itemLogger.error(LogUtils.buildLogInfoStr(human
						.getUUID(), "记录使用道具作弊日志时出错"), e);
			}
			return false;
		}
		

		Human user = this.owner;
		
		UseItemOperation<Human> operation = UseItemOperPool.instance.getSuitableOperation(user, this, target);		
		if (operation == null) {
			owner.getPlayer().sendErrorPromptMessage(ItemLangConstants_20000.ITEM_CANNOT_USE);
			return false;
		}
		
		// 如果不是消息驱动，则目标为玩家自己
		target = operation.getTarget(user, targetUuid, target);
	
		// 是否可以使用
		if (!operation.canUse(user, this, target)) {
			return false;
		}
		
		ItemUseInfo itemUseInfo = operation.collectItemUseInfo(this);
		itemUseInfo.setParams(params);

		// 执行使用
		return operation.useOnce(user, itemUseInfo, target);
//		return true;
	}

	@Override
	public String toString() {
		return "Item [template=" + template + ", UUID=" + UUID + ", overlap="
				+ overlap + ", bagType=" + bagType + ", index=" + index
				+ ", modified=" + modified + ", coolDownTimePoint="
				+ coolDownTimePoint + ", lifeCycle=" + lifeCycle + ", isInDb="
				+ isInDb + ", feature=" + feature + ", owner=" + owner
				+ ", createTime=" + createTime + ", bindStatus=" + bindStatus
				+ ", isLocked=" + isLocked + "]";
	}

}
