package com.pwrd.war.gameserver.human;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.MultiKeyMap;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.CheckFunctionEvent;
import com.pwrd.war.gameserver.common.event.RoleLevelUpEvent;
import com.pwrd.war.gameserver.human.template.FirstSendTemplate;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.Inventory;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemService;

/**
 * 只是角色辅助的服务类,创建角色模版,初次登录奖励
 *
 */
public class HumanAssistantService implements InitializeRequired{
	
	
	private TemplateService tmplService;
	
	private ItemService itemService;
	
	/**
	 * <<国家,性别>,奖励模板>
	 */
	private MultiKeyMap firstSendTempMap = new MultiKeyMap();
	
	public HumanAssistantService(TemplateService templateService,ItemService itemService) {
		this.tmplService = templateService;
		this.itemService = itemService;
	}
	
	
	@Override
	public void init() {
		Map<Integer,FirstSendTemplate> firstGiveTemplate = this.tmplService.getAll(FirstSendTemplate.class);
		// 校正操作
		for (FirstSendTemplate sendTemplate : firstGiveTemplate.values()) {			
			sendTemplate.revise(itemService);		
			firstSendTempMap.put(sendTemplate.getAlliance(), sendTemplate.getSex(), sendTemplate);			
		}
	}



	/**
	 * 根据玩家性别，取得该FirstSand模板
	 * 
	 * @param sex
	 * @return FirstSand模板
	 */
	public FirstSendTemplate getItemTempByTempId(int alliance,int sex) {
		FirstSendTemplate tmpl = (FirstSendTemplate)firstSendTempMap.get(alliance,sex);
		return tmpl;
	}
	
	/**
	 * 查看首次登陆，并进行相应操作
	 * 
	 * @param human
	 */
	public void giveFirstLoginGifts(Human human) {
//		// 给物品
//		FirstSendTemplate firstSend = this.getItemTempByTempId(human.getAllianceId(),human.getSex());
//		if(firstSend != null)
//		{
//			Inventory inv = human.getInventory();
//			// 赠送物品
//			inv.addAllItems(firstSend.getFirstSendItems(),
//				ItemGenLogReason.FIRST_LOGIN, null, false);
//		}
		
		Inventory inv = human.getInventory();
		//10级物品包
		inv.addItem("15011001", 1, BindStatus.BIND_YET,
			ItemGenLogReason.FIRST_LOGIN, null, false);
		
		//根据职业发送初始武器，并装备到身上
		Collection<Item> results;
		Iterator<Item> itor;
		switch (human.getVocationType()) {
		case MENGJIANG:	//猛将
			results = inv.addItem("10012007", 1, BindStatus.BIND_YET,
					ItemGenLogReason.FIRST_LOGIN, null, false);
			itor = results.iterator();
			while (itor.hasNext()) {
				human.getInventory().getEquipBag().equipItem(itor.next(), human.getInventory().getPrimBag());
			}
			break;
			
		case HAOJIE:	//豪杰
			results = inv.addItem("10012013", 1, BindStatus.BIND_YET,
					ItemGenLogReason.FIRST_LOGIN, null, false);
			itor = results.iterator();
			while (itor.hasNext()) {
				human.getInventory().getEquipBag().equipItem(itor.next(), human.getInventory().getPrimBag());
			}
			break;
			
		case SHESHOU:	//射手
			results = inv.addItem("10012014", 1, BindStatus.BIND_YET,
					ItemGenLogReason.FIRST_LOGIN, null, false);
			itor = results.iterator();
			while (itor.hasNext()) {
				human.getInventory().getEquipBag().equipItem(itor.next(), human.getInventory().getPrimBag());
			}
			break;
			
		default:		//谋士
			results = inv.addItem("10012015", 1, BindStatus.BIND_YET,
					ItemGenLogReason.FIRST_LOGIN, null, false);
			itor = results.iterator();
			while (itor.hasNext()) {
				human.getInventory().getEquipBag().equipItem(itor.next(), human.getInventory().getPrimBag());
			}
			
		}
	}
	
	public void initHuman(Human human) {
		
		doInitConfig(human);
		
		// 等级为1级
//		human.setLevel(RoleConstants.HUMAN_INIT_LEVEL_NUM);

		// 主背包初始页数
//		human.setBagPageSize(RoleConstants.PRIM_BAG_INIT_PAGE);

 
		
		
		//异步保存一次战斗快照
//		SavePlayerRoleOperation saveTask = new SavePlayerRoleOperation(human.getPlayer(),
//				PlayerConstants.CHARACTER_INFO_MASK_BATTLE_SNAP, false);
//		Globals.getAsyncService().createOperationAndExecuteAtOnce(saveTask);
	}
	
	
	/**
	 * 第一次登陆时初始化的数据，如果这些数据在初始化钱被使用，千万不能放这里，要放注册初始化里
	 * @author xf
	 */
	private void doInitConfig(Human human)
	{
		human.setCurHp(human.getMaxHp());
//		switch(human.getVocationType().getCode()){
//			case 1:
//				human.setPetSkill(human.getPropertyManager().getPetSkill1());
//				break;
//			case 2:
//				human.setPetSkill(human.getPropertyManager().getPetSkill2());
//				break;
//			case 3:
//				human.setPetSkill(human.getPropertyManager().getPetSkill3());
//				break;
//			case 4:
//				human.setPetSkill(human.getPropertyManager().getPetSkill4());
//				break;
//		}
		human.getPropertyManager().setMuanBattle(false);
		human.getPropertyManager().setEquipEnhanceNum(1);//默认1个
		human.getPropertyManager().setIsOpenForm(true);
		human.getPropertyManager().setIsInBattle(true);
	
		human.getPropertyManager().getPropertyNormal().setNewPrize((short)1);
		human.getPropertyManager().getPropertyNormal().setNewPrizeSTime(0);
		
		//第一次登录，发送升到1级的消息
		RoleLevelUpEvent event = new RoleLevelUpEvent(human, 1);
		Globals.getEventService().fireEvent(event);
		
		CheckFunctionEvent e = new CheckFunctionEvent(human);
		Globals.getEventService().fireEvent(e);
		human.setAtkLevel(1);
		human.setShortAtkLevel(1);
		human.setRemoteAtkLevel(1);
		human.setDefLevel(1);
		human.setShortDefLevel(1);
		human.setRemoteDefLevel(1);
		human.setMaxHpLevel(1);
		//给点钱	
//		human.giveMoney(100000, Currency.GOLD, false, MoneyLogReason.MONSTER_DROP, "", CurrencyCostType.QITA	);
//		human.giveMoney(100000, Currency.COINS, false, MoneyLogReason.MONSTER_DROP, "", null	);


	}
	
}
