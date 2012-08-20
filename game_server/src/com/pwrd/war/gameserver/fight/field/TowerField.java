package com.pwrd.war.gameserver.fight.field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.common.LogReasons.TowerLogReason;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.map.MapBattleBgService;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.tower.HumanTowerInfo;
import com.pwrd.war.gameserver.tower.TowerInfoManager;
import com.pwrd.war.gameserver.tower.msg.CGTowerBattle;
import com.pwrd.war.gameserver.tower.msg.GCTowerBag;
import com.pwrd.war.gameserver.tower.msg.GCTowerBattleResult;
import com.pwrd.war.gameserver.tower.msg.GCTowerInfo;

/**
 * 对怪物战斗战场
 * @author zy
 *
 */
public class TowerField extends PvMField {
	/**
	 * 初始化怪物战斗战场
	 * @param player
	 * @param monster
	 */
	private CGTowerBattle cgTowerBattle;
	
	public TowerField(Player player, VisibleMonster monster, CGTowerBattle cgTowerBattle) {
		//构造进攻方
		super(player, monster, false, true);
		this.cgTowerBattle = cgTowerBattle;
		
		//设置战斗背景
		bgId = Globals.getMapBattleBgService().getBattleBgByFuncId(MapBattleBgService.FUNC_JXYL);
		npcId = Globals.getMapBattleBgService().getNpcIdByFuncId(MapBattleBgService.FUNC_JXYL);
	}
	
	/**
	 * 战斗结束处理
	 */
	@Override
	public void pvmEndImpl(boolean attWin, long timeCost) {
		//设置玩家和怪物状态
		Human attHuman = attPlayer.getHuman();
		attHuman.setGamingState(GamingStateIndex.IN_NOMAL);
		visibleMonster.endAttack(!attWin);
		
		int towerNum = cgTowerBattle.getConstellationId();
		boolean secretFlag = cgTowerBattle.getSecretFlag();
//		TowerInfoManager dtim = new TowerInfoManager(attHuman);TODO
		TowerInfoManager dtim = attHuman.getTowerInfoManager();
		
		Map<Integer, HumanTowerInfo> towerInfos = dtim.getTowerInfos();
		HumanTowerInfo towerInfo = towerInfos.get(towerNum);
		
		GCTowerBattleResult msg = new GCTowerBattleResult();
		
		List<ItemTemplate> items = new ArrayList<ItemTemplate>();
		
		//进攻失败设置复活位置
		if (!attWin) {
			//爬塔失败
			msg.setResult(false);
		
		}else{
			msg.setResult(true);
			
			//初始化消息
			GCTowerInfo msgInfo = new GCTowerInfo();
			if(secretFlag){
				//如果为神秘星
				towerInfo.setSecretFlag(false);
				towerInfo.setModified();
//				dtim.updateSecretFlag(towerInfo);TODO
				
				items = Globals.getTowerService().getXinghunItem(towerNum, towerInfo.getCurNum()+1, true);
				
				//TODO
				msgInfo.setConstellationId(towerNum);
				msgInfo.setMaxStarNumber(towerInfo.getMaxNum());
				msgInfo.setStarNumber(towerInfo.getCurNum());
				msgInfo.setRefreshTimes(towerInfo.getRefreshNum());
				msgInfo.setSecretFlag(towerInfo.isSecretFlag());
				attHuman.sendMessage(msgInfo);
			}else{
				items = Globals.getTowerService().getXinghunItem(towerNum, towerInfo.getCurNum()+1, false);
				
				
				towerInfo.setCurNum(towerInfo.getCurNum()+1);
				if(towerInfo.getCurNum() > towerInfo.getMaxNum()){
					towerInfo.setMaxNum(towerInfo.getCurNum());
				}
				towerInfo.setModified();
//				dtim.updateTowerInfo(towerInfo);TODO
								
				//如果本层通过 开启下一个
				if(towerInfo.getMaxNum() == Globals.getTowerService().getSizeByConstellationId(towerNum)){
					HumanTowerInfo i = new HumanTowerInfo(attHuman);
					i.setCharId(attHuman.getCharId());
					i.setConstellationId(towerNum + 1);
					i.setCurNum(0);
					i.setLastRefreshTime(0);
					i.setMaxNum(0);
					i.setOwner(attHuman);
					i.setRefreshNum(0);
					i.setDbId(KeyUtil.UUIDKey());
					i.setInDb(false);
					i.getLifeCycle().activate();
					i.setModified();
					dtim.addTowerInfo(i);
//					dtim.addTowerInfoToDb(i);TODO
					
					
					msgInfo.setConstellationId(towerNum + 1);
					msgInfo.setMaxStarNumber(0);
					msgInfo.setStarNumber(0);
					msgInfo.setRefreshTimes(0);
					msgInfo.setSecretFlag(false);
					attHuman.sendMessage(msgInfo);
				}else{
					msgInfo.setConstellationId(towerNum);
					msgInfo.setMaxStarNumber(towerInfo.getMaxNum());
					msgInfo.setStarNumber(towerInfo.getCurNum());
					msgInfo.setRefreshTimes(towerInfo.getRefreshNum());
					msgInfo.setSecretFlag(towerInfo.isSecretFlag());
					attHuman.sendMessage(msgInfo);
				}
			}
			
//			//初始化消息
//			GCTowerInfo msgInfo = new GCTowerInfo();
//			msgInfo.setConstellationId(towerNum);
//			msgInfo.setMaxStarNumber(towerInfo.getMaxNum());
//			msgInfo.setStarNumber(towerInfo.getCurNum());
//			msgInfo.setRefreshTimes(towerInfo.getRefreshNum());
//			msgInfo.setSecretFlag(towerInfo.isSecretFlag());
//			attHuman.sendMessage(msgInfo);
			
			//发送星魂背包消息
			GCTowerBag msgBag = new GCTowerBag();
			msgBag.setCapacity(attHuman.getInventory().getXinghunBag().getEmptySlotCount());
			
			//添加星魂 TODO
			if(items != null && !items.isEmpty() ){
				for(ItemTemplate tmp : items){
					attHuman.getInventory().addItem(tmp.getItemSn(), 1, BindStatus.BIND_YET, ItemGenLogReason.ITEM_GIFT_GET, "星魂获得", true);
					msg.setStarSoul(tmp.getItemSn());
				}
			}
			
//			attHuman.getInventory().addItem("14013001", 1, BindStatus.BIND_YET, ItemGenLogReason.ITEM_GIFT_GET, "星魂获得", true);
			attHuman.sendMessage(msg);
		}
		int secretFlagInt = (secretFlag == true) ? 1 : 0;
		int attWinInt = (attWin == true) ? 1 : 0;
		Globals.getLogService().sendTowerLog(attHuman, TowerLogReason.TOWER, null, towerNum, towerInfo.getCurNum(), attHuman.getLevel(), Globals.getTimeService().now(), secretFlagInt, attWinInt);
		
	}
}
