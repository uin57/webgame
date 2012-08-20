package com.pwrd.war.gameserver.pet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.common.LogReasons.PetLogReason;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.model.pet.JingjiuInfo;
import com.pwrd.war.common.model.pet.PetHireInfo;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.currency.CurrencyCostType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.domain.Archive.ArchiveType;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.container.PetBodyEquipBag;
import com.pwrd.war.gameserver.item.container.ShoulderBag;
import com.pwrd.war.gameserver.pet.async.GivePetOperation;
import com.pwrd.war.gameserver.pet.msg.GCPetHireList;
import com.pwrd.war.gameserver.pet.msg.GCPetJingjiu;
import com.pwrd.war.gameserver.pet.msg.GCPetJingjiuList;
import com.pwrd.war.gameserver.pet.template.HirePetTemplate;
import com.pwrd.war.gameserver.pet.template.JingjiuPriceTemplate;
import com.pwrd.war.gameserver.pet.template.JingjiuTemplate;
import com.pwrd.war.gameserver.pet.template.NpcToHirePetPubTemplate;
import com.pwrd.war.gameserver.pet.template.PetTemplate;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.role.properties.HumanNormalProperty;
import com.pwrd.war.gameserver.role.template.RoleGrowTypeFactorTemplate;
import com.pwrd.war.gameserver.role.template.RoleToSkillTemplate;
import com.pwrd.war.gameserver.vitality.template.VipToVitTemplate;



public class PetService {

	
	//武将成长等级对应基础成长
	private Map<Integer, RoleGrowTypeFactorTemplate> growTypeFactorMap;
	private Map<String, PetTemplate> petTemplateMap;

	private Map<String, HirePetTemplate> petHireMap;
	
	private Map<String,RoleToSkillTemplate> skillTemplates=new HashMap<String,RoleToSkillTemplate>();
	
	private Map<Integer, Map<String, HirePetTemplate>> petPubMap = new HashMap<Integer, Map<String, HirePetTemplate>>();
	
	private Map<Integer, NpcToHirePetPubTemplate> npcToPubMap = new HashMap<Integer, NpcToHirePetPubTemplate>();
	
	private Map<Integer, JingjiuTemplate> jingjiuMap = new HashMap<Integer, JingjiuTemplate>();
	
	private Map<Integer, Integer> jingjiuPriceMap = new HashMap<Integer, Integer>();
	
//	private static final Logger logger = Loggers.petLogger;
//	
//	/** 模板服务 */
//	private TemplateService templateService;
//	/** 异步操作服务 */
//	private AsyncService asyncService;
	
	public PetService() {
//		if (templateService == null) {
//			throw new IllegalArgumentException("templateService is null");
//		}
//		if (asyncService == null) {
//			throw new IllegalArgumentException("asyncService is null");
//		}
//
//		this.templateService = templateService;
//		this.asyncService = asyncService;
		
		//初始化
		growTypeFactorMap = new HashMap<Integer, RoleGrowTypeFactorTemplate>();
		petTemplateMap = new HashMap<String, PetTemplate>();
		petHireMap = new HashMap<String, HirePetTemplate>();
		petPubMap = new HashMap<Integer, Map<String, HirePetTemplate>>();		
		
		//加载模板配置信息到map
		Map<Integer, RoleGrowTypeFactorTemplate>  growMap = Globals.getTemplateService().getAll(RoleGrowTypeFactorTemplate.class);
		for(Map.Entry<Integer, RoleGrowTypeFactorTemplate> e :growMap.entrySet()){
			growTypeFactorMap.put(e.getValue().getGrowType(), e.getValue());
		}		
		Map<Integer, PetTemplate>  petMap = Globals.getTemplateService().getAll(PetTemplate.class);
		for(Map.Entry<Integer, PetTemplate> e :petMap.entrySet()){
			petTemplateMap.put(e.getValue().getPetSn(), e.getValue());
		}
		Map<Integer, HirePetTemplate>  hiresMap = Globals.getTemplateService().getAll(HirePetTemplate.class);
		for(Map.Entry<Integer, HirePetTemplate> e :hiresMap.entrySet()){
			petHireMap.put(e.getValue().getPetSn(), e.getValue());
			if(!petPubMap.containsKey(e.getValue().getPub())){
				petPubMap.put(e.getValue().getPub(), new HashMap<String, HirePetTemplate>());
			}
			petPubMap.get(e.getValue().getPub()).put(e.getValue().getPetSn(), e.getValue());
		}
		
		for(RoleToSkillTemplate template :  Globals.getTemplateService().getAll(RoleToSkillTemplate.class).values()){
			if(VocationType.getByCode(Integer.parseInt(template.getRoleSn()))==VocationType.NONE){
				skillTemplates.put(template.getRoleSn(), template);
			}
		}
		
		npcToPubMap = Globals.getTemplateService().getAll(NpcToHirePetPubTemplate.class);
		
		jingjiuMap = Globals.getTemplateService().getAll(JingjiuTemplate.class);
		
		Map<Integer, JingjiuPriceTemplate> priceMap = Globals.getTemplateService().getAll(JingjiuPriceTemplate.class);
		for(Map.Entry<Integer, JingjiuPriceTemplate> m : priceMap.entrySet()){
			jingjiuPriceMap.put(m.getValue().getJingjiuTimes(), m.getValue().getCostGold());
		}
		
		
	}
	
	/**
	 * 根据武将成长等级返回武将的基础成长
	 * @param gradeType
	 * @return
	 */
	public int getBaseGrow(int growType){
		return growTypeFactorMap.get(growType).getBaseGrow();
	}
	
	public RoleToSkillTemplate getRoleToSkillTemplate(String petSn){
		return skillTemplates.get(petSn);
	}
	
	/**
	 * 根据武将id返回武将模板
	 * @param petId
	 * @return
	 */
	public PetTemplate getPetTemplate(String petId){
		return petTemplateMap.get(petId);
	}
	
 

	/**
	 * 解除武将
	 * @author xf
	 */
	public boolean removePet(final Player player, String name){
		Pet pet = player.getHuman().getPetManager().getPetByName(name);
		if(pet == null)
		{
			player.sendErrorMessage("武将不存在");
			Loggers.msgLogger.error(name+"武将不存在");
			return false;
		}
		PetBodyEquipBag bag = player.getHuman().getInventory().getBagByPet(pet.getUUID());
		if(bag.getAllItemsCount() > 0){
			player.sendErrorMessage("武将穿装备无法解雇");
			Loggers.msgLogger.error(name+"武将穿装备无法解雇");
			return false;
		} 
		//删除背包
		player.getHuman().getInventory().removePetBag(pet.getUUID());
		player.getHuman().getWarcraftInventory().removeWarcraftEquipBag(pet.getUUID());
		//删除武将
		player.getHuman().getPetManager().removePet(pet);
		pet.getLifeCycle().deactivate();
		pet.getLifeCycle().destroy();
		pet.setDeleted();
		
		player.sendMessage(player.getHuman().getPetManager().getPetDelMsg(pet));
		
		
		return true;
	}
	
	/**
	 * 取得所有可以看得见招募的武将列表
	 * 需要去除已经招募的武将
	 * @author xf
	 */
	public boolean getHirePetList(final Player player, int pub){
		List<PetHireInfo> list = new ArrayList<PetHireInfo>();
		
		if(petPubMap.get(pub).isEmpty()){
			return false;
		}
		for(HirePetTemplate tmp : petPubMap.get(pub).values()){
//			if(tmp.getSeeLevel() <= player.getHuman().getLevel()){
//				//等级够
//				//看看声望
//				if(tmp.getSeePopularity() <= player.getHuman().getPropertyManager().getPopularity()){
//					//看看任务
//					//if(player.getHuman().getMissions().contains(tmp.getSeeMessionId())){} 
//					if(player.getHuman().getPetManager().containsPetSn(tmp.getPetSn())){
//						//已经招募了
//						continue;
//					}
			
			//首先判断是否在可见等级内
			if (player.getHuman().getLevel() < tmp.getSeeMinLevel()
					|| player.getHuman().getLevel() > tmp.getSeeMaxLevel()) {
				continue;
			}
			
			String petId = tmp.getPetSn();
			PetTemplate petTmp = this.getPetTemplate(petId);
			PetHireInfo detail = new PetHireInfo();
			detail.setCanHire(this.canHirePet(player.getHuman(), tmp));
			detail.setPetSn(petTmp.getPetSn());

			detail.setHireCoin(tmp.getHireCoin());
			detail.setHireGold(tmp.getHireGold());
			detail.setHireItem1Num(tmp.getHireItem1Num());
			detail.setHireItem1Sn(tmp.getHireItem1Sn());
			detail.setHireItem2Num(tmp.getHireItem2Num());
			detail.setHireItem2Sn(tmp.getHireItem2Sn());
			detail.setHireLevel(tmp.getHireLevel());
			detail.setHirePopularity(tmp.getHirePopularity());

			list.add(detail);			
//				}
//			}
		}
		
		GCPetHireList msg = new GCPetHireList();
		msg.setPetInfo(list.toArray(new PetHireInfo[0]));
		player.sendMessage(msg);
		return true;
	}
	
	private boolean canHirePet(Human human, HirePetTemplate tmp){
		//判断条件是否足够
		if(tmp.getHireLevel() > human.getLevel()){ 
			return false;
		}
		//等级够
		//看看声望
		if(tmp.getHirePopularity() > human.getPropertyManager().getPopularity()){ 
			return false;
		} 
		//看看任务
		//if(player.getHuman().getMissions().contains(tmp.getSeeMessionId())){} 
		
		//道具数量
		if(StringUtils.isEmpty(tmp.getHireItem1Sn())){
			ShoulderBag bag = human.getInventory().getPrimBag();
			Item[] items = bag.getBySn(tmp.getHireItem1Sn());
			int count = 0;
			for(Item item : items){
				count += item.getOverlap();
			}
			if(count < tmp.getHireItem1Num()){ 
				return false;
			}
		}
		if(StringUtils.isEmpty(tmp.getHireItem2Sn())){
			ShoulderBag bag = human.getInventory().getPrimBag();
			Item[] items = bag.getBySn(tmp.getHireItem2Sn());
			int count = 0;
			for(Item item : items){
				count += item.getOverlap();
			}
			if(count < tmp.getHireItem2Num()){ 
				return false;
			}
		}
		
		//看钱
		if(tmp.getHireCoin() > 0){
			if(! human.hasEnoughMoney(tmp.getHireCoin(), Currency.COINS,
				null, true)){
				return false;
			}
		}
		
		// 看钱
		if (tmp.getHireGold() > 0) {
			if (!human.hasEnoughMoney(tmp.getHireGold(), Currency.GOLD, null,
					true)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 雇佣武将
	 * @author xf
	 */
	public boolean hirePet(final Player player, String petId, int pub){
//		HirePetTemplate tmp = this.petHireMap.get(petId);
		HirePetTemplate tmp = this.petPubMap.get(pub).get(petId);
		if(tmp == null){
			Loggers.msgLogger.info("没有这个武将"+petId);
			player.sendErrorMessage("没有这个武将");
			return false;
		}
		Human human = player.getHuman();
		//判断条件是否足够
		boolean rs = this.canHirePet(human, tmp);
		if(!rs){
			Loggers.msgLogger.info("招募条件不够"+petId);
			player.sendErrorMessage("招募条件不够");
			return false;
		}
		//是否已经招募
		if(human.getPetManager().containsPetSn(tmp.getPetSn())){
			Loggers.msgLogger.info("已经招募"+petId);
			player.sendErrorMessage("已经招募");
			return false;
		}
		
		//扣钱
		if(tmp.getHireCoin() > 0){
			rs = human.costMoney(tmp.getHireCoin(), Currency.COINS,null, true,
					0, CurrencyLogReason.WUJIANG, "招募"+tmp.getName(), 0);
		}
		
		if(!rs){
			Loggers.msgLogger.info("招募条件不够"+petId);
			player.sendErrorMessage("招募条件不够");
			return false;
		}
		
		if(tmp.getHireGold() > 0){
			rs = human.costMoney(tmp.getHireGold(), Currency.GOLD,null, true,
					null, null, null, 0);
		}
		
		if(!rs){
			Loggers.msgLogger.info("招募条件不够"+petId);
			player.sendErrorMessage("招募条件不够");
			return false;
		}
		
		givePetToHuman(human, petId);
		
		//扣除声望
		human.addPopularity(-tmp.getHirePopularity());
		human.snapChangedProperty(true);
		player.sendErrorMessage("招募成功！");
		
		//刷新列表
		this.getHirePetList(player, pub);
		
		//LOG
		Globals.getLogService().sendPetLog(human, PetLogReason.HIRE_PET, null, petId, tmp.getName(), Globals.getTimeService().now());
		return true;
	}
	
	/**
	 * 将武将发给玩家，并更新成就
	 * @param human
	 * @param petSn
	 */
	public void givePetToHuman(Human human, String petSn) {
		Pet pet = new Pet(petSn);  
		GivePetOperation op = new GivePetOperation(human, pet);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(op);		
		
		human.getArchiveManager().addArchiveNumber(ArchiveType.PET_HIRE_NUM, 1);
		human.getArchiveManager().addArchiveNumber(ArchiveType.PET_HIRE_STAR_VALUE, pet.getTemplate().getStar());
		if(pet.getTemplate().getStar() == 5){//紫色武将
			human.getArchiveManager().addArchiveNumber(ArchiveType.PET_HIRE_PURPLE_NUM, 1);
		}
	}
	
	public int getPubFromNpc(String npcId){
		for(Map.Entry<Integer, NpcToHirePetPubTemplate> n : npcToPubMap.entrySet()){
			if(n.getValue().getNpcId().equals(npcId)){
				return n.getValue().getPubId();
			}
		}
		return -1;
	}
	
	/**
	 * 发送敬酒信息
	 * @param human
	 */
	public void sendJingjiuMsg(Human human){
		
		// 获得用户上次敬酒的时间
		long lastJingjiuTime = human.getPropertyManager().getPropertyNormal()
				.getLastJingjiuTime();

		// 取得当天的0点时间 判断是否到刷新时间
		long nowTime = Globals.getTimeService().now();
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeInMillis(nowTime);
		_calendar.set(Calendar.HOUR_OF_DAY, 0);
		_calendar.set(Calendar.MINUTE, 0);
		_calendar.set(Calendar.SECOND, 0);
		_calendar.set(Calendar.MILLISECOND, 0);
		long dayBegin = _calendar.getTimeInMillis();
		
		GCPetJingjiuList msg = new GCPetJingjiuList();
		List<JingjiuInfo> jingjiuList = new ArrayList<JingjiuInfo>();
		for (Map.Entry<Integer, JingjiuTemplate> m : jingjiuMap.entrySet()) {
			JingjiuInfo jingjiuInfo  = new JingjiuInfo();
			jingjiuInfo.setId(m.getValue().getId());
			jingjiuInfo.setCoin(m.getValue().getCoin());
			jingjiuInfo.setGold(m.getValue().getGold());
			jingjiuInfo.setName(m.getValue().getName());
			jingjiuInfo.setPopularity(m.getValue().getPopularity());
			jingjiuInfo.setVipLevel(m.getValue().getVip());
			
			if(dayBegin >= lastJingjiuTime){
				//清零
				jingjiuInfo.setTimes(0);
				human.getPropertyManager().getPropertyNormal().setJingjiu1(0);
				human.getPropertyManager().getPropertyNormal().setJingjiu2(0);
				human.getPropertyManager().getPropertyNormal().setJingjiu3(0);
				
			}else{
				switch(m.getValue().getId()){
				case 1:
					jingjiuInfo.setTimes(human.getPropertyManager().getPropertyNormal().getJingjiu1());
					break;
				case 2:
					jingjiuInfo.setTimes(human.getPropertyManager().getPropertyNormal().getJingjiu2());
					break;
				case 3:
					jingjiuInfo.setTimes(human.getPropertyManager().getPropertyNormal().getJingjiu3());
					break;		
				}
			}
			
			//高级敬酒因为花费元宝数跟次数相关 所以做特殊处理
			if(m.getValue().getId() ==3){
				jingjiuInfo.setGold(this.getCostColdByJingjiuTimes(jingjiuInfo.getTimes() + 1));
			}
			jingjiuList.add(jingjiuInfo);
		}
		msg.setJingjiuInfo(jingjiuList.toArray(new JingjiuInfo[0]));
		
		human.sendMessage(msg);
	}
	
	public void jingjiu(Player player, int id){
		
		Human human = player.getHuman();
		GCPetJingjiu msg = new GCPetJingjiu();
		JingjiuTemplate tmp = jingjiuMap.get(id);
		if(tmp == null){
			msg.setResult(false);
			msg.setDesc("id不存在");
			human.sendMessage(msg);
			return;
			
		}
		
		// 获得vip相关判断敬酒次数
		VipToVitTemplate vipTmp = player.getHuman().getVipTemplate();

		HumanNormalProperty prop = player.getHuman().getPropertyManager()
				.getPropertyNormal();
		
		// 获得用户上次敬酒的时间
		long lastJingjiuTime = human.getPropertyManager().getPropertyNormal()
				.getLastJingjiuTime();

		// 取得当天的0点时间 判断是否到刷新时间
		long nowTime = Globals.getTimeService().now();
		Calendar _calendar = Calendar.getInstance();
		_calendar.setTimeInMillis(nowTime);
		_calendar.set(Calendar.HOUR_OF_DAY, 0);
		_calendar.set(Calendar.MINUTE, 0);
		_calendar.set(Calendar.SECOND, 0);
		_calendar.set(Calendar.MILLISECOND, 0);
		long dayBegin = _calendar.getTimeInMillis();
		
		//清0
		if(dayBegin >= lastJingjiuTime){
			prop.setJingjiu1(0);
			prop.setJingjiu2(0);
			prop.setJingjiu3(0);
		}
		
		switch (id) {
		case 1:
			if (prop.getJingjiu1() >= vipTmp.getJingjiu1()) {
				msg.setResult(false);
				msg.setDesc("敬酒次数已满");
				human.sendMessage(msg);
				return;
			}
			break;
		case 2:
			if (prop.getJingjiu2() >= vipTmp.getJingjiu2()) {
				msg.setResult(false);
				msg.setDesc("敬酒次数已满");
				human.sendMessage(msg);
				return;
			}
			break;
		case 3:
			if (prop.getJingjiu3() >= vipTmp.getJingjiu3()) {
				msg.setResult(false);
				msg.setDesc("敬酒次数已满");
				human.sendMessage(msg);
				return;
			}
			break;
		}
		
//		int vipLevel = human.getPropertyManager().getVip();
//		if(vipLevel < tmp.getVip()){
//			msg.setResult(false);
//			msg.setDesc("vip等级不够");
//			human.sendMessage(msg);
//			return;
//		}
		
		int coin = tmp.getCoin();
		if(coin > 0){
			if(!human.hasEnoughCoins(coin, false)){
				msg.setResult(false);
				msg.setDesc("铜钱不够");
				human.sendMessage(msg);
				String content = Globals.getLangService().read(CommonLangConstants_10000.COMMON_NOT_ENOUGH, 
						Globals.getLangService().read(Currency.COINS.getNameKey()));
				human.getPlayer().sendErrorPromptMessage(content);
				return;
			}	
		}
		
		int gold = 0;
		
		//高级敬酒因为花费元宝数跟次数相关 所以做特殊处理
		if(id == 3){
			gold = this.getCostColdByJingjiuTimes(prop.getJingjiu3() + 1);
		}else{
			gold = tmp.getGold();
		}
		if(gold > 0){
			if(!human.hasEnoughGold(gold, false)){
				msg.setResult(false);
				msg.setDesc("元宝不够");
				human.sendMessage(msg);
				String content = Globals.getLangService().read(CommonLangConstants_10000.COMMON_NOT_ENOUGH, 
						Globals.getLangService().read(Currency.GOLD.getNameKey()));
				human.getPlayer().sendErrorPromptMessage(content);
				return;
			}		
		}
		
		boolean rs1 = true;
		boolean rs2 = true;
		
		if (coin > 0) {
			rs1 = human.costCoins(coin, true, 0, CurrencyLogReason.WUJIANG,
					CurrencyLogReason.WUJIANG.getReasonText(), 0);
		}
		
		if (gold > 0) {
			rs2 = human.costGold(gold, true, 0, CurrencyLogReason.WUJIANG,
					CurrencyLogReason.WUJIANG.getReasonText(), 0);
		}

		if(rs1 && rs2){
			human.addPopularity(tmp.getPopularity());
			human.snapChangedProperty(true);
			msg.setResult(true);
			msg.setDesc("");
			
			switch(id){
			case 1:
				prop.setJingjiu1(prop.getJingjiu1() + 1);
				break;
			case 2:
				prop.setJingjiu2(prop.getJingjiu2() + 1);
				break;
			case 3:
				prop.setJingjiu3(prop.getJingjiu3() + 1);
				break;
			}
			
			prop.setLastJingjiuTime(Globals.getTimeService().now());
		}
		
		
		human.sendMessage(msg);
		
		this.sendJingjiuMsg(human);
	}
	
	public int getCostColdByJingjiuTimes(int times){
		int result = -1;
		if(times <= 0){
			return result;
		}
		if(!jingjiuPriceMap.containsKey(times)){
			return result;
		}else{
			return jingjiuPriceMap.get(times);
		}
	}
}
