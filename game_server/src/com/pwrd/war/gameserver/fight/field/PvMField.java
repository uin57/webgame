package com.pwrd.war.gameserver.fight.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.common.LogReasons.RepDropLogReason;
import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.buff.enums.BufferType;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.QuestKillEvent;
import com.pwrd.war.gameserver.common.event.QuestKillEvent.KillInfo;
import com.pwrd.war.gameserver.common.event.StoryShowEvent;
import com.pwrd.war.gameserver.common.i18n.constants.ItemLangConstants_20000;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.fight.domain.FightAction;
import com.pwrd.war.gameserver.fight.domain.FightLostItem;
import com.pwrd.war.gameserver.fight.domain.FightRoleInfo;
import com.pwrd.war.gameserver.fight.field.unit.FightTeam;
import com.pwrd.war.gameserver.fight.field.unit.FightUnit;
import com.pwrd.war.gameserver.fight.msg.GCBattleActionMessage;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.human.wealth.Bindable.Oper;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.monster.Monster;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.monster.VisibleMonster.FightMonster;
import com.pwrd.war.gameserver.monster.template.MonsterTemplate;
import com.pwrd.war.gameserver.monster.template.VisibleMonsterTemplate;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.role.Role;
import com.pwrd.war.gameserver.role.RoleTypes;
import com.pwrd.war.gameserver.role.properties.HumanNormalProperty;
import com.pwrd.war.gameserver.story.StoryEventType;

/**
 * 玩家与怪物战斗的战场基类
 * @author zy
 *
 */
public abstract class PvMField extends BaseField {
	protected Player attPlayer;
	protected VisibleMonster visibleMonster;
	protected Map<String, Integer> kills = new HashMap<String, Integer>();
	
	public PvMField(Player player, VisibleMonster monster, boolean attIsFullHp, boolean defIsFullHp) {
		//构造进攻方
		super(player.getHuman(), attIsFullHp);
		attPlayer = player;
		
		//获取明雷配置的阵型，构造防守方
		visibleMonster = monster;
		VisibleMonsterTemplate template = monster.getTemplate();
		defTeam = new FightTeam(monster.getUUID(), template.getFormId());
		
		//根据阵型配置创建战斗对象，并计算奖励
		List<FightMonster> monsters = monster.getMonsters(attTeam.getUnits().size(), attTeam.getAvgLevel());
		expPrize = 0;
		moneyPrize = 0;
		for (FightMonster m : monsters) {
			addFightUnit(m, m.getInitOrder(), m.getFormPos(), false, defIsFullHp, RoleTypes.MONSTER, true);
			updatePrize(m);
		}
		
		//设置围观NPC编号
		npcId = visibleMonster.getViewNpcId();
		
		//设置战斗背景id
		bgId = Globals.getMapBattleBgService().getBattleBgByMapId(attPlayer.getSceneId());
	}
	
	/**
	 * 根据怪物对象计算经验金钱奖励和物品掉落
	 * @param monster
	 */
	private void updatePrize(Monster monster) {
		//获取经验
		MonsterTemplate template = monster.getMonsterTemplate();
		int levelRank = monster.getLevel() - template.getLevel();
		if (levelRank > 0) {
			expPrize = expPrize + template.getExp() + levelRank * template.getExpGrow();
		} else {
			expPrize += template.getExp();
		}
		
		//获取金钱
		moneyPrize += 10;
		
		//获取掉落
		String monsterSn = template.getMonsterSn();
		List<ItemTemplate> itemTemplates = Globals.getMonsterService().getDropBags(
				attPlayer.getHuman().getVipLevel(), monsterSn);
		for (ItemTemplate it : itemTemplates) {
			String itemSn = it.getItemSn();
			//将掉落加入掉落列表，新物品插入列表，存在的物品更新数量
			FightLostItem lostItem = lostItems.get(itemSn);
			if (lostItem == null) {
				lostItem = new FightLostItem();
				lostItem.setSn(itemSn);
				lostItem.setPerfect(it.getRarity().getIndex());
				lostItem.setNum(1);
				lostItem.setItemTemplate(it);
				lostItems.put(itemSn, lostItem);
			} else {
				lostItem.setNum(lostItem.getNum() + 1);
			}
		}
		
		//获取杀怪
		if (kills.containsKey(monsterSn)) {
			kills.put(monsterSn, Integer.valueOf(kills.get(monsterSn).intValue() + 1));
		} else {
			kills.put(monsterSn, Integer.valueOf(1));
		}
	}

	/**
	 * 战斗结束实现
	 */
	protected void endImpl(boolean attWin, long timeCost) {
		//进攻方胜利，计算奖励
		Human attHuman = attPlayer.getHuman();
		if (attWin) {
			//根据玩家身上经验buff计算最终经验奖励
//			Map<BufferType, Buffer> buffers = attHuman.getBuffers();
			List<Buffer> experienceBuffer = attHuman.getBufferByType(BufferType.experience);
			if (experienceBuffer != null && experienceBuffer.size() > 0) {
				for(Buffer b : experienceBuffer){
					expPrize += b.getValue(expPrize);
				}
			}
			
			//根据玩家身上金钱buff计算最终金钱奖励
			List<Buffer> moneyBuffer = attHuman.getBufferByType(BufferType.money);
			if (moneyBuffer != null && moneyPrize > 0 && moneyBuffer.size() > 0) {
				for(Buffer b : moneyBuffer){
					moneyPrize += b.getValue(moneyPrize);
					b.modify(attHuman, 1);
				}
			}
			
			//触发任务更新，发送杀怪信息
			List<KillInfo> killInfos = new ArrayList<KillInfo>();
			for (Map.Entry<String, Integer> entry : kills.entrySet()) {
				killInfos.add(new KillInfo(entry.getKey(), entry.getValue().intValue()));
			}
			QuestKillEvent event = new QuestKillEvent(attHuman, killInfos);
			Globals.getEventService().fireEvent(event);
			
			// LOG
			String dropItems = lostItems.values().toString();
			Globals.getLogService().sendRepDropLog(attPlayer.getHuman(),
					RepDropLogReason.KILL, null, sceneId, "", dropItems, 1,
					Currency.COINS.getIndex(), moneyPrize,
					Globals.getTimeService().now(),
					visibleMonster.getTemplate().getVisibleMonsterSn());
			
		} else {
			//失败则清空各种奖励
			expPrize = 0;
			moneyPrize = 0;
			lostItems.clear();
		}
		
		//更新玩家及武将血量、经验数据
		for (FightUnit unit : attTeam.getUnits()) {
			Role role = attHuman.getRole(unit.getUnitSn());
			if (role != null) {
				FightRoleInfo roleInfo = roleInfos.get(unit.getUnitSn());
				if (roleInfo != null) {
					roleInfo.setExp(expPrize);
					roleInfo.setMoney(moneyPrize);
					roleInfo.setFinalHp(unit.getCurHp());
				}
				
				//设置玩家血量，如果死亡设置为1
//				if (!unit.isAlive()) {
//					role.addHP((int) (1 - role.getCurHp()));
//				} else {
//					role.addHP((int) (unit.getCurHp() - role.getCurHp()));
//				}
				if (expPrize > 0) {
					role.addExp(expPrize);
				}
				role.snapChangedProperty(true);
			}
		}
		
		//更新防守方血量
		for (FightUnit unit : defTeam.getUnits()) {
			FightRoleInfo roleInfo = roleInfos.get(unit.getUnitSn());
			if (roleInfo != null) {
				roleInfo.setFinalHp(unit.getCurHp());
			}
		}
		
		// 获取金钱
		if (moneyPrize > 0) {
			attHuman.giveMoney(moneyPrize, Currency.COINS, false,
					CurrencyLogReason.MONSTER,
					CurrencyLogReason.MONSTER.getReasonText());
		}
		
		//构造掉落物品信息，如果玩家背包空间足够将物品放入背包
		if (lostItems.size() > 0) {
			List<ItemParam> itemParams = new ArrayList<ItemParam>();
			for (FightLostItem lostItem : lostItems.values()) {
				itemParams.add(new ItemParam(lostItem.getSn(), lostItem.getNum(), lostItem.getItemTemplate().getBindStatusAfterOper(BindStatus.NOT_BIND, Oper.GET)));
			}
			if (attHuman.getInventory().checkSpace(itemParams, true)) {
				attHuman.getInventory().addAllItems(itemParams, ItemGenLogReason.MONSTER_DROP, Globals.getLangService().read(ItemLangConstants_20000.ITEM_NOT_ENOUGH_SPACE), true);
			} else {
				lostItems.clear();
			}
		}
		
		//将技能和buff资源转换为消息格式
		int[] skillRes = new int[skillResources.size()];
		int index = 0;
		for (Integer res : skillResources.values()) {
			skillRes[index] = res;
			index ++;
		}
		int[] buffRes = new int[buffResources.size()];
		index = 0;
		for (Integer res : buffResources.values()) {
			buffRes[index] = res;
			index ++;
		}
		
		//构造并发送消息
		//TODO 实现battleSn
		GCBattleActionMessage msg = new GCBattleActionMessage(sceneId, attTeam.getTeamSn(), attTeam.getFormSn(),
				defTeam.getTeamSn(), defTeam.getFormSn(), roleInfos.values().toArray(new FightRoleInfo[0]),
				fightActions.toArray(new FightAction[0]), timeCost, attWin,
				lostItems.values().toArray(new FightLostItem[0]), attTeam.getAliveRate(), defTeam.getAliveRate(),
				"", skillRes, buffRes, npcId, bgId);
		attPlayer.sendMessage(msg);
		
		//发送剧情 战斗成功后
		if (attWin){
			StoryShowEvent event = new StoryShowEvent(attPlayer.getHuman(), StoryEventType.AFTER_KILL, visibleMonster.getTemplate().getVisibleMonsterSn());
			Globals.getEventService().fireEvent(event);
		}
			
		//设置进攻方战斗冷却
		HumanNormalProperty prop = attHuman.getPropertyManager().getPropertyNormal();
		prop.setLastFightTime(Globals.getTimeService().now() + fightCost);
		
		//调用子类实现
		pvmEndImpl(attWin, timeCost);
	}
	
	public abstract void pvmEndImpl(boolean attWin, long timeCost);
}
