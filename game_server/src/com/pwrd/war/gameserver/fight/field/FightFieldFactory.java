package com.pwrd.war.gameserver.fight.field;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.activity.campwar.CampWarActivity;
import com.pwrd.war.gameserver.activity.ggzj.GGZJActivity;
import com.pwrd.war.gameserver.activity.worldwar.WorldWarActivity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.StoryShowEvent;
import com.pwrd.war.gameserver.common.i18n.constants.BattleLangConstants_50000;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.role.properties.HumanNormalProperty;
import com.pwrd.war.gameserver.story.StoryEventType;
import com.pwrd.war.gameserver.tower.msg.CGTowerBattle;


public class FightFieldFactory {
	//
	//----------------PvE战斗----------------
	//
	/**
	 * 客户端主动攻击明雷接口
	 * @param player
	 * @param targetSn
	 */
	public static void doVisibleMonsterFight(Player player, String targetSn) {
		//获取玩家所在场景中的目标怪
		VisibleMonster visibleMonster = player.getHuman().getScene().getMonsterManager().getVisibleMonster(targetSn);
		doMonsterFight(player, visibleMonster);
	}
	
	/**
	 * 开始一场打怪战斗，非怪物主动攻击
	 * @param player
	 * @param monster
	 */
	public static void doMonsterFight(Player player, VisibleMonster monster) {
		doMonsterFight(player, monster, true);
	}
	
	/**
	 * 开始一场打怪战斗
	 * @param player
	 * @param targetSn
	 * @param userAttack
	 */
	public static void doMonsterFight(Player player, VisibleMonster monster, boolean userAttack) {	
		//检查怪物和玩家的状态，如果状态错误直接进入错误处理
		if (checkMonsterStatus(player, monster, userAttack)) {
			//发送剧情触发消息
			String para = monster.getTemplate().getVisibleMonsterSn();
			StoryShowEvent event = new StoryShowEvent(player.getHuman(), StoryEventType.BEFORE_KILL, para);
			Globals.getEventService().fireEvent(event);
			
			doAsyncFight(new MonsterField(player, monster));
		} else {
			if (monster != null) {
				monster.endAttack(false);
			}
		}
	}

	/**
	 * 开始一场怪物攻城战斗
	 * @param player
	 * @param targetSn
	 */
	public static void doBossField(Player player, VisibleMonster monster) {
		//检查怪物和玩家的状态，如果状态错误直接进入错误处理
		if (checkMonsterStatus(player, monster)) {
			doAsyncFight(new BossField(player, monster));
		} else {
			if (monster != null) {
				Globals.getActivityService().getBossActivity().onBattleError(player, monster);
			}
		}
	}

	/**
	 * 过关斩将战斗
	 */
	public static void doGgzjField(Player player, VisibleMonster monster) {
		//检查怪物和玩家的状态，如果状态错误直接进入错误处理
		if (checkMonsterStatus(player, monster)) {
			doAsyncFight(new GgzjField(player, monster));
		} else {
			if (monster != null) {
				Globals.getActivityService().getActivity(GGZJActivity.class)
					.onBattleError(player, monster);
			}
		}
	}
	
	/**
	 * 开始一场爬塔战斗
	 * @param player
	 * @param monster
	 */
	public static void doTowerFight(Player player, VisibleMonster monster, CGTowerBattle cgTowerBattle) {
		//检查怪物和玩家的状态，如果状态错误直接进入错误处理
		doAsyncFight(new TowerField(player, monster, cgTowerBattle));

	}
	
	/**
	 * PvE战斗前检查怪物和玩家状态，并提示错误消息
	 * @param player
	 * @param monster
	 * @return
	 */
	private static boolean checkMonsterStatus(Player player, VisibleMonster monster) {
		return checkMonsterStatus(player, monster, true);
	}
	
	/**
	 * PvE战斗前检查怪物和玩家的状态
	 * @param player
	 * @param monster
	 * @param sendMsg
	 * @return
	 */
	private static boolean checkMonsterStatus(Player player, VisibleMonster monster, boolean sendMsg) {
		//判断怪物对象是否存在
		if (monster == null) {
			if (sendMsg) {
				sendErrorMessage(player, BattleLangConstants_50000.MONSTER_NOT_FOUND);
			}
			return false;
		}
		
		//玩家状态处于正常和打坐状态时才能进入战斗
		Human human = player.getHuman();
		if (human.getGamingState() != GamingStateIndex.IN_NOMAL.getValue() &&
				human.getGamingState() != GamingStateIndex.IN_PRACTISE.getValue()) {
			if (sendMsg) {
				sendErrorMessage(player, BattleLangConstants_50000.PLAYER_STATUES_ERROE);
			}
			return false;
		}
		
		//判断战斗冷却时间
		HumanNormalProperty prop = human.getPropertyManager().getPropertyNormal();
		if (Globals.getTimeService().now() < prop.getLastFightTime()) {
			if (sendMsg) {
				sendErrorMessage(player, BattleLangConstants_50000.PLAYER_IN_COOLDOWN);
			}
			return false;
		}
		
		//判断玩家等级是否符合打怪
		if (human.getLevel() < monster.getTemplate().getWarMinLevel()) {
			if (sendMsg) {
				sendErrorMessage(player, BattleLangConstants_50000.PLAYER_LEVEL_LOWER);
			}
			return false;
		}
		if(human.getLevel() > monster.getTemplate().getWarMaxLevel()){
			if (sendMsg) {
				sendErrorMessage(player, BattleLangConstants_50000.PLAYER_LEVEL_HIGHT);
			}
			return false;
		}
		
		//判断怪物状态是否可以攻击
		if (!monster.beginAttack()) {
			if (sendMsg) {
				sendErrorMessage(player, BattleLangConstants_50000.MONSTER_IN_BATTLE);
			}
			return false;
		}
		
		return true;
	}
	
	//
	//----------------PvP战斗----------------
	//
	/**
	 * 开始一场切磋战斗
	 * @param player
	 * @param targetSn
	 */
	public static void doExchangeField(Player player, String targetSn) {
		//首先获取在线目标玩家
		Player targetPlayer = Globals.getOnlinePlayerService().getPlayerById(targetSn);
		
		//进行状态判断，如果状态错误则不能进入战斗
		if (checkPlayerStatus(player, targetPlayer, true)) {
			doAsyncFight(new ExchangeField(player.getHuman(), targetPlayer.getHuman(), true));
		}
	}
	
	/**
	 * 开始一场护送战斗
	 * @param player
	 * @param targetUUID
	 * @param protecterUUID
	 */
	public static void doRobberyField(Player player, String targetUUID, String protecterUUID) {
		//如果保护者存在则目标为保护者
		String defenderSn = StringUtils.isEmpty(protecterUUID) ? targetUUID : protecterUUID;
		
		//首先获取目标玩家，如果玩家不在线则获取离线信息
		Player targetPlayer = Globals.getOnlinePlayerService().getPlayerById(defenderSn);
		if (targetPlayer != null) {
			//目标玩家在线，进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetPlayer, false)) {
				doAsyncFight(new RobberyField(player.getHuman(), targetPlayer.getHuman(), targetUUID, protecterUUID));
			}
		} else {
			//目标玩家不在线，加载离线数据，进行状态判断，如果状态错误立刻进入错误处理
			Human targetHuman = loadOfflineHuman(defenderSn);
				
			//进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetHuman, false)) {
				doAsyncFight(new RobberyField(player.getHuman(), targetHuman, targetUUID, protecterUUID));
			}
		}
	}
	
	/**
	 * 开始一场竞技场战斗
	 * @param player
	 * @param targetSn
	 */
	public static void doArenaField(Player player, String targetSn) {
		//首先获取目标玩家，如果玩家不在线则获取离线信息
		Player targetPlayer = Globals.getOnlinePlayerService().getPlayerById(targetSn);
		if (targetPlayer != null) {
			//目标玩家在线，进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetPlayer, false)) {
				doAsyncFight(new ArenaField(player.getHuman(), targetPlayer.getHuman(), true));
			}
		} else {
			//目标玩家不在线，加载离线数据，进行状态判断，如果状态错误立刻进入错误处理
			Human targetHuman = loadOfflineHuman(targetSn);
				
			//进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetHuman, false)) {
				doAsyncFight(new ArenaField(player.getHuman(), targetHuman, false));
			}
		}
	}
	
	/**
	 * 开启主将争夺战
	 */
	public static void doHeroWarField(Player player, String targetUUID) {		
		//首先获取目标玩家，如果玩家不在线则获取离线信息
		Player targetPlayer = Globals.getOnlinePlayerService().getPlayerById(targetUUID);
		if (targetPlayer != null) {
			//目标玩家在线，进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetPlayer, false)) {
				doAsyncFight(new HeroWarField(player, targetPlayer.getHuman(), false, targetUUID));
			}
		} else {
			//目标玩家不在线，加载离线数据，进行状态判断，如果状态错误立刻进入错误处理
			Human targetHuman = loadOfflineHuman(targetUUID);
				
			//进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetHuman, false)) {
				doAsyncFight(new HeroWarField(player, targetHuman, false, targetUUID));
			}
		}
	}
	/**
	 * 守军争夺战战斗
	 */
	public static void doCampWarField(Player player, String targetSn) {
		//首先获取目标玩家，如果玩家不在线则获取离线信息
		Player targetPlayer = Globals.getOnlinePlayerService().getPlayerById(targetSn);
		boolean checked = false;
		if (targetPlayer != null) {
			//目标玩家在线，进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetPlayer, false)) {
				doAsyncFight(new CampWarField(player, targetPlayer.getHuman(), false, targetSn));
				checked = true;
			}
		} else {
			//目标玩家不在线，加载离线数据，进行状态判断，如果状态错误立刻进入错误处理
			Human targetHuman = loadOfflineHuman(targetSn);
				
			//进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetHuman, false)) {
				doAsyncFight(new CampWarField(player, targetHuman, false, targetSn));
				checked = true;
			}
		}
		
		//检查失败，进入错误处理
		if (!checked) {
			Globals.getActivityService().getActivity(CampWarActivity.class).onBattleError(player, targetSn);
		}
	}
	/**
	 * 守军争夺战主将战战斗
	 */
	public static void doCampHeroWarField(String attackerSN, String targetSn) {
		//首先获取目标玩家，如果玩家不在线则获取离线信息
		Player player =  Globals.getOnlinePlayerService().getPlayerById(attackerSN);
		Player targetPlayer = Globals.getOnlinePlayerService().getPlayerById(targetSn);
		Human att = null;
		Human def = null;
		 
		if(player == null){
			//目标玩家不在线，加载离线数据，进行状态判断，如果状态错误立刻进入错误处理
			att = loadOfflineHuman(attackerSN);
		}else{
			att = player.getHuman();
		}
		if(targetPlayer == null){
			//目标玩家不在线，加载离线数据，进行状态判断，如果状态错误立刻进入错误处理
			def = loadOfflineHuman(targetSn);
		}else{
			def = targetPlayer.getHuman();
		}
		doAsyncFight(new CampHeroWarField(att, def));
		 
	}
	/**
	 * 守军争夺战抢劫战斗
	 */
	public static void doCampWarRobField(Player player, String targetSn) {
		//首先获取目标玩家，如果玩家不在线则获取离线信息
		Player targetPlayer = Globals.getOnlinePlayerService().getPlayerById(targetSn);
		if (targetPlayer != null) {
			//目标玩家在线，进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetPlayer, false)) {
				doAsyncFight(new CampWarRobField(player, targetPlayer.getHuman(), false));
			}
		} else {
			//目标玩家不在线，加载离线数据，进行状态判断，如果状态错误立刻进入错误处理
			Human targetHuman = loadOfflineHuman(targetSn);
				
			//进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetHuman, false)) {
				doAsyncFight(new CampWarRobField(player, targetHuman, false));
			}
		} 
	}
	public static void doWorlddWarField(Player player, String targetSn, boolean attFullHp, boolean defFullHp) {
		//首先获取目标玩家，如果玩家不在线则获取离线信息
		Player targetPlayer = Globals.getOnlinePlayerService().getPlayerById(targetSn);
		boolean checked = false;
		if (targetPlayer != null) {
			//目标玩家在线，进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetPlayer, false)) {
				doAsyncFight(new WorldWarField(player, targetPlayer.getHuman(), attFullHp, defFullHp));
				checked = true;
			}
		} else {
			//目标玩家不在线，加载离线数据，进行状态判断，如果状态错误立刻进入错误处理
			Human targetHuman = loadOfflineHuman(targetSn);
				
			//进行状态判断，如果状态错误立刻进入错误处理
			if (checkPlayerStatus(player, targetHuman, false)) {
				doAsyncFight(new CampWarField(player, targetHuman, false, targetSn));
				checked = true;
			}
		}
		
		//检查失败，进入错误处理
		if (!checked) {
			Globals.getActivityService().getActivity(WorldWarActivity.class).onBattleError(player.getRoleUUID(),
					targetSn);
		}
	}
	/**
	 * 加载不在线玩家数据
	 * @param humanSn
	 * @return
	 */
	private static Human loadOfflineHuman(String humanSn) {
		HumanEntity entity = Globals.getDaoService().getHumanDao().get(humanSn);
		if (entity != null) {
			//构造目标Human对象
			Human targetHuman = new Human();
			targetHuman.loadOffline(entity);
			return targetHuman;
		} else {
			return null;
		}
	}
	
	/**
	 * PvP战斗检查双方情况
	 * @param player
	 * @param targetPlayer
	 * @param checkTargetStatus
	 * @return
	 */
	private static boolean checkPlayerStatus(Player player, Player targetPlayer, boolean checkTargetStatus) {
		//找不到目标玩家
		if (targetPlayer == null) {
			sendErrorMessage(player, CommonLangConstants_10000.NO_THIS_PLAYER);
			return false;
		}
		
		return checkPlayerStatus(player, targetPlayer.getHuman(), checkTargetStatus);
	}
	
	/**
	 * PvP战斗前检查双方情况
	 * @param player
	 * @param targetHuman
	 * @param checkTargetStatus
	 * @return
	 */
	private static boolean checkPlayerStatus(Player player, Human targetHuman, boolean checkTargetStatus) {
		//找不到目标玩家
		if (targetHuman == null) {
			sendErrorMessage(player, CommonLangConstants_10000.NO_THIS_PLAYER);
			return false;
		}
		
		//玩家状态处于正常和打坐状态时才能进入战斗
		Human attHuman = player.getHuman();
		if (attHuman.getGamingState() != GamingStateIndex.IN_NOMAL.getValue() &&
				attHuman.getGamingState() != GamingStateIndex.IN_PRACTISE.getValue()) {
			sendErrorMessage(player, BattleLangConstants_50000.PLAYER_STATUES_ERROE);
			return false;
		}
		
		//判断战斗冷却时间
		HumanNormalProperty attProp = attHuman.getPropertyManager().getPropertyNormal();
		if (Globals.getTimeService().now() < attProp.getLastFightTime()) {
			sendErrorMessage(player, BattleLangConstants_50000.PLAYER_IN_COOLDOWN);
			return false;
		}
		
		//判断对方状态
		if (checkTargetStatus) {
			if (targetHuman.getGamingState() != GamingStateIndex.IN_NOMAL.getValue() &&
					targetHuman.getGamingState() != GamingStateIndex.IN_PRACTISE.getValue()) {
				sendErrorMessage(player, BattleLangConstants_50000.PLAYER_STATUES_ERROE);
				return false;
			}
			
			//判断战斗冷却时间
			HumanNormalProperty targetProp = targetHuman.getPropertyManager().getPropertyNormal();
			if (Globals.getTimeService().now() < targetProp.getLastFightTime()) {
				sendErrorMessage(player, BattleLangConstants_50000.PLAYER_IN_COOLDOWN);
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 开启异步战斗处理
	 * @param field
	 */
	private static void doAsyncFight(BaseField field) {
		try {
			if (field != null) {
				Globals.getAsyncService().createOperationAndExecuteAtOnce(field);
			}
		} catch (Exception e) {
			Loggers.battleLogger.warn("Can not fight:" + field.getClass().getName());
		}
	}
	
	/**
	 * 发送错误消息
	 * @param player
	 * @param errorCode
	 */
	private static void sendErrorMessage(Player player, int errorCode) {
		player.sendErrorPromptMessage(errorCode);
	}
	
}
