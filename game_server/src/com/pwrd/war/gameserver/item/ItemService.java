package com.pwrd.war.gameserver.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.LogReasons;
import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.common.i18n.constants.ItemLangConstants_20000;
import com.pwrd.war.gameserver.common.msg.GCMessage;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.currency.CurrencyCostType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.enums.CoolType;
import com.pwrd.war.gameserver.human.template.CoolDownCostTemplate;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.enhance.EquipEnhanceService;
import com.pwrd.war.gameserver.item.generate.EquipGenerator;
import com.pwrd.war.gameserver.item.msg.GCEnhanceEquip;
import com.pwrd.war.gameserver.item.template.BagExpandTemplateTemplate;
import com.pwrd.war.gameserver.item.template.EquipEnhanceLevel;
import com.pwrd.war.gameserver.item.template.EquipEnhanceLevelRate;
import com.pwrd.war.gameserver.item.template.EquipEnhancePosPrice;
import com.pwrd.war.gameserver.item.template.EquipEnhancePosRate;
import com.pwrd.war.gameserver.item.template.EquipEnhanceSuccesRateTemplate;
import com.pwrd.war.gameserver.item.template.EquipEnhanceSuccesRateTemplate.EquipEnhanceSuccesRate;
import com.pwrd.war.gameserver.item.template.EquipItemAttribute;
import com.pwrd.war.gameserver.item.template.EquipItemTemplate;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.role.properties.PropertyType;
import com.pwrd.war.gameserver.role.properties.amend.Amend;
import com.pwrd.war.gameserver.role.properties.amend.AmendTriple;

public class ItemService implements InitializeRequired{
	
	/** 物品绑定状态按照默认方式 */
	public static final int BIND_DEFAULT_MODE = 2;
	
	/** 物品模板map */
	private Map<String, ItemTemplate> itemTempMap;
	
	/** 装备强化等级属性模版 **/
	private Map<Integer, EquipEnhanceLevel> equipEnhanceLevelMap;
	
	/** 装备强化部位属性 key为部位id_等级**/
	private Map<String, EquipEnhancePosRate> equipEnhancePosRateMap;
	
	/** 强化成功率，1级list表示有几组数据，2级list表示每组内的成功率信息 **/
	private List<List<EquipEnhanceSuccesRate>> equipEnhanceSuccessRate;
	
	/** 装备等级对应的系数和价格系数 **/
	private Map<Integer, EquipEnhanceLevelRate> equipEnhanceLevelRate;
	
	/** 装备等级对应的部位强化价格 **/
	private Map<Integer, EquipEnhancePosPrice> equipEnhancePosPrice;
	
	/** 背包扩充模版  **/
	private Map<BagType, Map<Integer, Integer>> bagExpandGoldTemplate;
	
	/** 物品模板管理器,负责从模板中取得物品 */
	private TemplateService itemTmplService;
	
	/** 装备生成器 */
	private EquipGenerator equipGenerator;

	/** 装备强化概率生成服务**/
	private EquipEnhanceService equipEnhanceService;
	
	public ItemService(TemplateService templateService) {
		this.itemTmplService = templateService;
		this.equipGenerator = new EquipGenerator();
		this.equipEnhanceService = new EquipEnhanceService(this);
		
	}

	@Override
	public void init() {
		this.itemTempMap = new HashMap<String, ItemTemplate>();
		Map<Integer, ItemTemplate>  map0 = itemTmplService.getAll(ItemTemplate.class);
		for(Map.Entry<Integer, ItemTemplate> e : map0.entrySet()){
			itemTempMap.put(e.getValue().getItemSn(), e.getValue());
		}
		
		
		equipEnhanceLevelMap = new HashMap<Integer, EquipEnhanceLevel>();
		Map<Integer, EquipEnhanceLevel>  map1 = itemTmplService.getAll(EquipEnhanceLevel.class);
		for(Map.Entry<Integer, EquipEnhanceLevel> e : map1.entrySet()){
			equipEnhanceLevelMap.put(e.getValue().getLevel(), e.getValue());
		}
		
		equipEnhancePosRateMap = new HashMap<String, EquipEnhancePosRate>();
		Map<Integer, EquipEnhancePosRate>  map2 = itemTmplService.getAll(EquipEnhancePosRate.class);
		for(Map.Entry<Integer, EquipEnhancePosRate> e : map2.entrySet()){
			equipEnhancePosRateMap.put(e.getValue().getPositionId()+"_"+e.getValue().getLelve(), e.getValue());
		}
		equipEnhancePosPrice = new HashMap<Integer, EquipEnhancePosPrice>();
		for(Map.Entry<Integer, EquipEnhancePosPrice> e : itemTmplService.getAll(EquipEnhancePosPrice.class).entrySet()){
			equipEnhancePosPrice.put(e.getValue().getLelve(), e.getValue());
		}
		
		equipEnhanceSuccessRate = new ArrayList<List<EquipEnhanceSuccesRate>>();
		for(int i = 0; i< 4; i++){//先把4组list添加进去
			equipEnhanceSuccessRate.add(new ArrayList<EquipEnhanceSuccesRate>());
		}
		Map<Integer, EquipEnhanceSuccesRateTemplate> map3 = itemTmplService.getAll(EquipEnhanceSuccesRateTemplate.class);		
		for(Map.Entry<Integer, EquipEnhanceSuccesRateTemplate> e : map3.entrySet()){
			EquipEnhanceSuccesRateTemplate tmp = e.getValue();
			EquipEnhanceSuccesRate rate = new EquipEnhanceSuccesRate();
			rate.succesRate = tmp.getSuccesRate1();
			rate.weight = tmp.getWeaight1(); 
			equipEnhanceSuccessRate.get(0).add(rate);
			
			rate = new EquipEnhanceSuccesRate();
			rate.succesRate = tmp.getSuccesRate2();
			rate.weight = tmp.getWeaight2(); 
			equipEnhanceSuccessRate.get(1).add(rate);
			
			rate = new EquipEnhanceSuccesRate();
			rate.succesRate = tmp.getSuccesRate3();
			rate.weight = tmp.getWeaight3(); 
			equipEnhanceSuccessRate.get(2).add(rate);
			
			rate = new EquipEnhanceSuccesRate();
			rate.succesRate = tmp.getSuccesRate4();
			rate.weight = tmp.getWeaight4(); 
			equipEnhanceSuccessRate.get(3).add(rate); 
		}
		
		equipEnhanceLevelRate = new HashMap<Integer, EquipEnhanceLevelRate>();
		Map<Integer, EquipEnhanceLevelRate> map4 = itemTmplService.getAll(EquipEnhanceLevelRate.class);	
		for(Map.Entry<Integer, EquipEnhanceLevelRate> e : map4.entrySet()){
			equipEnhanceLevelRate.put(e.getValue().getEquipLevel(), e.getValue());
		}
		
		this.bagExpandGoldTemplate = new HashMap<BagType, Map<Integer, Integer>>();
		Map<Integer, BagExpandTemplateTemplate> map5 = itemTmplService.getAll(BagExpandTemplateTemplate.class);
		if(map5 != null){
			for(BagExpandTemplateTemplate tmp : map5.values()){ 
				if(!this.bagExpandGoldTemplate.containsKey(tmp.getBagTypeEnum())){
					this.bagExpandGoldTemplate.put(tmp.getBagTypeEnum() , new HashMap<Integer, Integer>());				
				}					
				this.bagExpandGoldTemplate.get(tmp.getBagTypeEnum()).put(tmp.getIndex(), tmp.getGold());
			}
		}
		
	}
	 
	public List<List<EquipEnhanceSuccesRate>> getEquipEnhanceSuccessRate() {
		return equipEnhanceSuccessRate;
	}
	public Map<Integer, EquipEnhanceLevel> getEquipEnhanceLevelMap() {
		return equipEnhanceLevelMap;
	}
	/**
	 * 启动装备强化率改变的服务
	 * @author xf
	 */
	public void startEnhanceService(){
		this.equipEnhanceService.start();
	}

	public EquipEnhanceService getEquipEnhanceService() {
		return equipEnhanceService;
	}
	/**
	 * 根据{@link EquipItemAttribute}生成对应的{@link AmendTriple}
	 * 
	 * @param attrs
	 * @return
	 */
	public static List<AmendTriple> convertAmendTuples(List<EquipItemAttribute> attrs) {
		if (attrs == null) {
			return Collections.emptyList();
		}
		ArrayList<AmendTriple> tuples = new ArrayList<AmendTriple>();
		for (EquipItemAttribute attr : attrs) {
			if (attr == null || StringUtils.isEmpty(attr.getPropKey())) {
				continue;
			}
		
						
			AmendTriple tuple = Globals.getAmendService().createAmendTriple(attr.getPropKey(), attr.getPropValue(), attr.getEnhancePerLevel());
			tuples.add(tuple);
		}
		return tuples;
	}
	
	
	/**
	 * 判断模板id为templateId的绑定状态为bind的物品是否可以叠加到baseItem上<br/>
	 * 此方法不考虑叠加上限的问题
	 * 
	 * @param baseItem
	 * @param templateId
	 * @param bind
	 * @return
	 */
	public static boolean canOverlapOn(Item baseItem, int templateId,
			BindStatus bindStatus) {
		// 锁定的物品不可以再往上面叠加
		return Item.isSameTemplateId(templateId, baseItem)
				&& baseItem.getBindStatus() == bindStatus
				/*&& !baseItem.isLocked()*/;
	}
	
	
	
	
	public void reload()
	{
		init();
	}
	
	/**
	 * 创建一个已经激活的道具，即创建之后将直接出现在游戏世界中，初始叠加数为0
	 * 
	 * @param owner
	 *            所有者
	 * @param template
	 *            道具模板
	 * @param bagId
	 *            所在包id
	 * @param _index
	 *            在包中的索引
	 * @return
	 */
	public Item newActivatedInstance(Human owner, ItemTemplate template,
			BagType bagType, int bagIndex) {
		Item item = null;
		if (template.isEquipment()) {
			item = equipGenerator.generateActivedEquip(owner,
					(EquipItemTemplate) template, bagType, bagIndex);
		} else {
			item = Item
					.newActivatedInstance(owner, template, bagType, bagIndex);
		}
		return item;
	}

	/**
	 * 创建一个未激活的道具，初始叠加数为0
	 * 
	 * @param owner
	 *            所有者
	 * @param template
	 *            道具模板
	 * @param bagId
	 *            所在包id
	 * @param _index
	 *            在包中的索引
	 * @return
	 */
	public Item newDeactiveInstance(Human owner, ItemTemplate template,
			BagType bagType, int bagIndex) {
		if (template.isEquipment()) {
			Item item = equipGenerator.generateDeactivedEquip(owner,
					(EquipItemTemplate) template, bagType, bagIndex);
			return item;
		} else {
			return Item.newDeactivedInstance(owner, template, bagType, bagIndex);
		}
	}

	/**
	 * 根据item模板id，取得该item模板
	 * 
	 * @param templateId
	 * @return item模板
	 */
	public ItemTemplate getItemTempByTempId(String itemSN) {
//		for(ItemTemplate tmpl : itemTempMap.values()){
//			if(tmpl.getId() == templateId)return tmpl;
//		}
//		return null;
		return itemTempMap.get(itemSN);
	}

	/**
	 * 获取所有模板的集合，此集合不可修改
	 * 
	 * @return
	 */
	public Collection<ItemTemplate> getAll() {
		return Collections.unmodifiableCollection(itemTempMap.values());
	}
	
 
	
	private static void accumulateAmend(List<AmendTriple> tuples, Map<Integer, Float> propMap, PropertyType propType) {
		if (tuples == null || tuples.isEmpty()) {
			return;
		}
		for (AmendTriple tuple : tuples) {
			// 
			Amend amd = tuple.getAmend();
			float value = tuple.getAmendValue();
			
			// 如果修正属性类型与被提取类型一致,累加对应类型index的值
			if (amd.getPropertyType() == propType) {
				accumulate(propMap, new int[] { amd.getProperytIndex() }, value);
			}
		}
	}
	
	private static void accumulate(Map<Integer, Float> propMap, int[] keys,	float value) {
		for (int key : keys) {
			Float sum = propMap.get(key);
			if (sum == null) {
				sum = 0.0f;
			}
			propMap.put(key, value + sum);
		}
	}
	
	public ItemTemplate getTemplateByItemSn(String itemSn){		
		return itemTempMap.get(itemSn);
	}

	
	/**
	 * 强化装备
	 * @author xf
	 */
	public void enhanceEquipment(final Player player, 
			String wearerId, int bagId, int index,
			boolean bFree, boolean bDouble){
	 
		
		BagType bagType = BagType.valueOf(bagId);
		if (bagType == null || bagType == BagType.NULL) { 
			return;
		}
		Human human = player.getHuman();
		Item item = human.getInventory().getItemByIndex(bagType, wearerId, index);
		if(Item.isEmpty(item)){
			return;
		}
		if(!item.isEquipment()){
			return;
		}
		EquipmentFeature feature = (EquipmentFeature) item.getFeature();
		int enhanceLevel = feature.getEnhanceLevel();
		int nextLevel = enhanceLevel + 1;
		if(nextLevel > EquipEnhanceService.MAX_LEVEL){
			player.sendErrorPromptMessage(ItemLangConstants_20000.ENHANCE_MAX_LEVEL);
			return ;
		}
		boolean bEmpty = human.getCooldownManager().hasEmptyCool(human.getPropertyManager().getEquipEnhanceNum(),
				CoolType.ENHANCE_EQUIP);
		 
		if(!bEmpty){
			player.sendErrorPromptMessage(ItemLangConstants_20000.ENHANCE_QUEUE_FULL);
			return;
		}
		//判断玩家当前等级够否
		if(nextLevel > human.getLevel()){
			player.sendErrorPromptMessage(ItemLangConstants_20000.ENHANCE_LEVEL_GREAT_HUMAN);
			return;
		}
		
		EquipEnhanceLevel level = this.equipEnhanceLevelMap.get(nextLevel);	
		
//		ItemDef.Rarity rarity = feature.getItem().getTemplate().getRarity();
		int needMoney = equipEnhancePosPrice.get(feature.getEnhanceLevel()).
				getPosPrice().get(item.getTemplate().getPosition().getIndex() - 1);
//		switch(rarity){
//			case WHITE:
//				needMoney = level.getWhiteCurrencyNum();
//				break;
//			case GREEN:
//				needMoney = level.getGreenCurrencyNum();
//				break;
//			case BLUE:
//				needMoney = level.getBlueCurrencyNum();
//				break;
//			case PURPLE:
//				needMoney = level.getPurpleCurrencyNum();
//				break;
//			case YELLOW:
//				needMoney = level.getYellowCurrencyNum();
//				break;
//		}
		GCEnhanceEquip rmsg = new GCEnhanceEquip(false, false);
		
		boolean rs = false;//是否扣铜钱
		if(human.getLevel() >= 15){
			if(this.equipEnhanceService.isFree()){
				//特惠时间内
				rs = this.equipEnhanceService.isFreeJustRate();
				if(rs){ 
					GCMessage msg = player.getSystemMessage(human.getName()+"强化时获得了免费效果");
					Globals.getOnlinePlayerService().broadcastMessage(msg);
				}
			}else if(bFree){
				//判断是不是vip,且元宝是否足够
				if(human.getVipTemplate().getVipLevel() > 0 ){			
					//不在优惠时间内扣元宝
					if (human
							.hasEnoughGold(EquipEnhanceService.FREE_GOLD, true)) {
						// 扣元宝
						rs = human.costGold(EquipEnhanceService.FREE_GOLD,
								true, 0, CurrencyLogReason.QIANGHUA,
								CurrencyLogReason.QIANGHUA.getReasonText(), 0);
					} else {
						rs = false;
					}
					//扣除元宝成功
					if(rs){  
						rs = this.equipEnhanceService.isFreeJustRate();
					}else{
						human.getPlayer().sendErrorPromptMessage("元宝不足，直接用铜钱强化");
					}
					
				}else{
					rs = false;
				}
			}
		}
		rmsg.setIsFree(rs);
		if(!rs){
			//判断钱够否
			rs = human.hasEnoughMoney(needMoney, Currency.valueOf(level.getCurrencyId()), null, true);
			if(!rs){ 
				return;
			}		
			// 扣钱
			rs = human.costMoney(needMoney,
					Currency.valueOf(level.getCurrencyId()), null, true, 0,
					CurrencyLogReason.QIANGHUA,
					CurrencyLogReason.QIANGHUA.getReasonText(), 0);
			if (!rs) {
				return;
			}
		}
		
		//取模版计算需要冷却的时间
		//取得模版
		CoolDownCostTemplate tmp = Globals.getHumanService().getCoolTemplateByCoolType(CoolType.ENHANCE_EQUIP);


		int needt = tmp.getNeedTime(nextLevel);
		
		//先保存冷却cd序列
		human.getCooldownManager().put(human.getPropertyManager().getEquipEnhanceNum(),
				CoolType.ENHANCE_EQUIP, 
				Globals.getTimeService().now(),
				Globals.getTimeService().now() + needt * TimeUtils.MIN);		
//				Globals.getTimeService().now() + 5 * TimeUtils.MIN);		
		
		//失败了，返还金钱
//		if(!rs){
//			human.giveMoney(failReturnMoney, Currency.valueOf(level.getCurrencyId()),
//					true, null, "");
//			
//			human.snapChangedProperty(true);
//			human.sendSystemMessage(ItemLangConstants_20000.ENHANCE_FAIL, failReturnMoney);
//			return;
//		}
//		human.getPlayer().sendRightMessage(ItemLangConstants_20000.ENHANCE_SUCCESS);
		if(this.equipEnhanceService.isDouble()){
			//双倍时间内
			boolean rd = this.equipEnhanceService.isFreeJustRate();
			if(rd){
				//连升2及
				this.buildPropsByEnhanceLevel(item,  nextLevel);
				if(nextLevel + 1 <= EquipEnhanceService.MAX_LEVEL){
					this.buildPropsByEnhanceLevel(item,  nextLevel + 1);
				}
				GCMessage msg = player.getSystemMessage(human.getName()+"强化时获得了双倍效果");
				Globals.getOnlinePlayerService().broadcastMessage(msg);
			}else{
				//升1级
				this.buildPropsByEnhanceLevel(item,  nextLevel);
			}
			rmsg.setIsDouble(rd);
		}else if(bDouble ){
			boolean rd = false;//是否双倍了
			if(human.getVipTemplate().getVipLevel() > 0 ){					
				if(human.hasEnoughGold(EquipEnhanceService.DOUBLE_GOLD,false)){
					rd = human.costGold(EquipEnhanceService.DOUBLE_GOLD, true,
							0, CurrencyLogReason.QIANGHUA,
							CurrencyLogReason.QIANGHUA.getReasonText(), 0);
				}
				if(rd){
					rd = this.equipEnhanceService.isFreeJustRate();
				}else{
					human.getPlayer().sendErrorPromptMessage("元宝不足，无法产生双倍效果");
				}
			}else{
				rd = false;
			}
			if(rd){
				//连升2及
				this.buildPropsByEnhanceLevel(item,  nextLevel);
				if(nextLevel + 1 <= EquipEnhanceService.MAX_LEVEL){
					this.buildPropsByEnhanceLevel(item,  nextLevel + 1);
				}
				rmsg.setIsDouble(true);
			}else{
				//只升一级
				this.buildPropsByEnhanceLevel(item,  nextLevel);
			} 
		}else{
			//默认，成功了，加一级
			this.buildPropsByEnhanceLevel(item,  nextLevel);
		}
		
		 
		//更新装备消息
		human.sendMessage(item.getItemInfoMsg());
		//发送人物属性变化信息
		human.calcProps();
		human.sendMessage(rmsg);
		
	}

	/**
	 * 根据强化等级重置强化附加属性
	 * @author xf
	 */
	public EquipmentFeature buildPropsByEnhanceLevel(Item item,   int level){
		EquipmentFeature feature = (EquipmentFeature)item.getFeature();
		if(level == 0){
			feature.setEnhanceLevel(level);
			feature.setEnhanceName("");
			for(ItemDef.EquipProps prop : ItemDef.EquipProps.values()){
				feature.setAddOnProps(prop, 0);	
			}
			//计算下一级属性值
			this.buildNextPropsByEnhanceLevel(item,  level + 1);
			return feature;
		}
		EquipEnhanceLevel levelProps = this.equipEnhanceLevelMap.get(level);	
		
		ItemDef.Rarity rarity = feature.getItem().getTemplate().getRarity(); 
		double rate = 0;
		switch(rarity){
			case WHITE: 
				rate = 1;
				break;
			case GREEN: 
				rate = levelProps.getGreenRate();
				break;
			case BLUE: 
				rate = levelProps.getBlueRate();
				break;
			case PURPLE: 
				rate = levelProps.getPurpleRate();
				break;
			case YELLOW: 
				rate = levelProps.getYellowRate();
				break;
		} 
		//成功了，加属性
		feature.setEnhanceLevel(level);
		feature.setEnhanceName(levelProps.getName());
		ItemDef.Position pos = item.getPosition();
		EquipEnhancePosRate  posRate = equipEnhancePosRateMap.get(pos.getIndex()+"_"+item.getTemplate().getNeedLevel());
		if(posRate.getAtkRate() > 0){
			//改变攻击
			double atkAddon = levelProps.getAddAtk() * rate * posRate.getAtkRate() * equipEnhanceLevelRate.get(level).getLevelRate();
			feature.setAddOnProps(ItemDef.EquipProps.ATK, atkAddon);
		}
		
		if(posRate.getDefRate() > 0){
			//防御
			double defAddon = levelProps.getAddDef() * rate * posRate.getDefRate() * equipEnhanceLevelRate.get(level).getLevelRate();
			feature.setAddOnProps(ItemDef.EquipProps.DEF, defAddon);
		}
		
		if(posRate.getHpRate() > 0){
			//最大生命
			double hpAddon = levelProps.getAddHp() * rate * posRate.getHpRate() * equipEnhanceLevelRate.get(level).getLevelRate();
			feature.setAddOnProps(ItemDef.EquipProps.MAXHP, hpAddon);
		}
		//计算下一级属性值
		this.buildNextPropsByEnhanceLevel(item,  level + 1);
		return feature;
	}

	/**
	 * 计算强化下一等级的属性值
	 * @author xf
	 */
	private EquipmentFeature buildNextPropsByEnhanceLevel(Item item,  int level){
		EquipmentFeature feature = (EquipmentFeature) item.getFeature();
		if(level < 1){ 
			return feature;
		} 
		EquipEnhanceLevel levelProps = this.equipEnhanceLevelMap.get(level);	
		
		ItemDef.Rarity rarity = feature.getItem().getTemplate().getRarity(); 
		double rate = 0;
		int needMoney = equipEnhancePosPrice.get(level-1).
				getPosPrice().get(item.getTemplate().getPosition().getIndex() - 1);;
		switch(rarity){
			case WHITE: 
				rate = 1;
//				needMoney = levelProps.getWhiteCurrencyNum();
				break;
			case GREEN: 
				rate = levelProps.getGreenRate();
//				needMoney = levelProps.getGreenCurrencyNum();
				break;
			case BLUE: 
				rate = levelProps.getBlueRate();
//				needMoney = levelProps.getBlueCurrencyNum();
				break;
			case PURPLE: 
				rate = levelProps.getPurpleRate();
//				needMoney = levelProps.getPurpleCurrencyNum();
				break;
			case YELLOW: 
				rate = levelProps.getYellowRate();
//				needMoney = levelProps.getYellowCurrencyNum();
				break;
		} 
		feature.setNextLevelMoney(needMoney);
		
		ItemDef.Position pos = item.getPosition();
		EquipEnhancePosRate  posRate = equipEnhancePosRateMap.get(pos.getIndex()+"_"+item.getTemplate().getNeedLevel());
		if(posRate.getAtkRate() > 0){
			//改变攻击
			double atkAddon = levelProps.getAddAtk() * rate * posRate.getAtkRate() * equipEnhanceLevelRate.get(level).getLevelRate();
			feature.setNextAddOnProps(ItemDef.EquipProps.ATK, atkAddon);
		} 
		if(posRate.getDefRate() > 0){
			//防御
			double defAddon = levelProps.getAddDef() * rate * posRate.getDefRate() * equipEnhanceLevelRate.get(level).getLevelRate();
			feature.setNextAddOnProps(ItemDef.EquipProps.DEF, defAddon);
		} 
		if(posRate.getHpRate() > 0){
			//最大生命
			double hpAddon = levelProps.getAddHp() * rate * posRate.getHpRate() * equipEnhanceLevelRate.get(level).getLevelRate();
			feature.setNextAddOnProps(ItemDef.EquipProps.MAXHP, hpAddon);
		}
		return feature;
	}

	public Map<BagType, Map<Integer, Integer>> getBagExpandGoldTemplate() {
		return bagExpandGoldTemplate;
	}

	 
	
	 
}
