package com.pwrd.war.gameserver.tree.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.friend.FriendInfo;
import com.pwrd.war.gameserver.friend.FriendManager;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.human.wealth.Bindable.Oper;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.tree.HumanTreeInfo;
import com.pwrd.war.gameserver.tree.HumanTreeWaterInfo;
import com.pwrd.war.gameserver.tree.TreeInfoManager;
import com.pwrd.war.gameserver.tree.TreeWaterManager;
import com.pwrd.war.gameserver.tree.msg.CGFriendWaterInfo;
import com.pwrd.war.gameserver.tree.msg.CGGetFriendWaterRecord;
import com.pwrd.war.gameserver.tree.msg.CGGetFruit;
import com.pwrd.war.gameserver.tree.msg.CGGetWaterRecord;
import com.pwrd.war.gameserver.tree.msg.CGTreeInfo;
import com.pwrd.war.gameserver.tree.msg.CGTreeShake;
import com.pwrd.war.gameserver.tree.msg.CGTreeShakeBatch;
import com.pwrd.war.gameserver.tree.msg.CGWaterFriend;
import com.pwrd.war.gameserver.tree.msg.CGWaterFriendBatch;
import com.pwrd.war.gameserver.tree.msg.CGWaterInfo;
import com.pwrd.war.gameserver.tree.msg.CGWaterMyself;
import com.pwrd.war.gameserver.tree.msg.GCGetFruit;
import com.pwrd.war.gameserver.tree.msg.GCGetWaterRecord;
import com.pwrd.war.gameserver.tree.msg.GCTreeInfo;
import com.pwrd.war.gameserver.tree.msg.GCTreeShake;
import com.pwrd.war.gameserver.tree.msg.GCWaterInfo;
import com.pwrd.war.gameserver.tree.msg.GCWaterResult;
import com.pwrd.war.gameserver.tree.template.TreeCostTemplate;
import com.pwrd.war.gameserver.tree.template.TreeFruitTemplate;
import com.pwrd.war.gameserver.tree.template.TreeTemplate;

public class TreeMessageHandler {

	private static int VIP_LEVEL = 4;
	private static int BATCH_TIMES = 10;
	
	public void handleTreeInfo(Player player, CGTreeInfo cgTreeInfo) {
		// TODO Auto-generated method stub
//		GCTreeInfo msg = new GCTreeInfo();
//		Human human = player.getHuman();
//		
//		int level = human.getLevel();	
//		if(level < 14){
//			return;
//		}
//		
//		TreeInfoManager tim = human.getTreeInfoManager();
//		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
//				
//		if(info == null){
//			//如果没有数据 首先加入数据
//			info = tim.creatHumanTreeInfo();		
//			msg.setShakeTimes(0);
//		}else{
//			//清零
//			long todayBegin = TimeUtils.getTodayBegin(Globals.getTimeService());
//			if(todayBegin > info.getLastShakeTime()){
//				info.setShakeTimes(0);
//				info.setModified();
//			}
//			msg.setShakeTimes(info.getShakeTimes());
//		}
//		
//		int vipLevel = human.getVipLevel();
//		int maxShakeTimes = Globals.getVitService().getTimesMap().get(vipLevel).getShakeTimes();
//		int costGold = 0;
//		if(info.getShakeTimes() < maxShakeTimes){
//			costGold = Globals.getTreeService().getTreeCostTemplateMap().get(info.getShakeTimes() + 1).getCostGold();
//		}
//		boolean batchFlag = false;
//		int batchCost = 0;
//		if(vipLevel >= VIP_LEVEL && maxShakeTimes - info.getShakeTimes() >= 10){
//			batchFlag = true;
//			for(int i = 1; i <= BATCH_TIMES; i++){
//				batchCost += Globals.getTreeService().getTreeCostTemplateMap().get(info.getShakeTimes() + i).getCostGold();
//			}
//		}
//		msg.setBatchFlag(batchFlag);
//		msg.setCostBatch(batchCost);
//		msg.setCostGold(costGold);
//		
//		TreeTemplate tmp = Globals.getTreeService().getTreeTemplateMap().get(human.getLevel());
//		if(tmp != null){
//			msg.setGetCoin(Globals.getTreeService().getTreeTemplateMap().get(human.getLevel()).getGetCoins());
//		}else{
//			msg.setGetCoin(0);
//		}
//		
//		msg.setTotalTimes(maxShakeTimes);
//		player.sendMessage(msg);
		
		Globals.getTreeService().getTreeInfo(player);
		
	}

	public void handleTreeShake(Player player, CGTreeShake cgTreeShake) {
		// TODO Auto-generated method stub
		GCTreeShake msg = new GCTreeShake();
		Human human = player.getHuman();
		
		int level = human.getLevel();
		
		if(level < 14){
			msg.setResult(false);
			msg.setDesc("玩家等级不够");
			player.sendMessage(msg);
			player.sendErrorPromptMessage(CommonLangConstants_10000.LEVEL_NOT_REACH);
			return;
		}
		
		
		int vipLevel = human.getVipLevel();
		int total = Globals.getVitService().getTimesMap().get(vipLevel).getShakeTimes();
		
		TreeInfoManager tim = human.getTreeInfoManager();
		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
		
		if(info == null){
			//如果没有数据 首先加入数据
			info = tim.creatHumanTreeInfo();	
		}else{
			//0点更新
			long todayBegin = TimeUtils.getTodayBegin(Globals.getTimeService());
			if(todayBegin > info.getLastShakeTime()){
				info.setShakeTimes(0);
				info.setModified();
			}
		}
		
		if(info.getShakeTimes() >= total){
			msg.setResult(false);
			msg.setDesc("CommonLangConstants_10000.SHAKE_TIMES_NOT_ENOUGH");
			player.sendMessage(msg);
//			player.sendImportPromptMessage(CommonLangConstants_10000.SHAKE_TIMES_NOT_ENOUGH);
			player.sendErrorPromptMessage(CommonLangConstants_10000.SHAKE_TIMES_NOT_ENOUGH);
			return;
		}
		
		int cost = Globals.getTreeService().getTreeCostTemplateMap().get(info.getShakeTimes() + 1).getCostGold();
		int money = 0;
		TreeTemplate tmp = Globals.getTreeService().getTreeTemplateMap().get(human.getLevel());
		if(tmp != null){
			money = Globals.getTreeService().getTreeTemplateMap().get(human.getLevel()).getGetCoins();
		}
		
		
		if(human.hasEnoughGold(cost, true)){
			human.costGold(cost, true, 0, CurrencyLogReason.TREE,
					CurrencyLogReason.TREE.getReasonText(), 0);
			human.giveMoney(money, Currency.COINS, true,
					CurrencyLogReason.TREE,
					CurrencyLogReason.TREE.TREE.getReasonText());
			info.setShakeTimes(info.getShakeTimes() + 1);
			info.setLastShakeTime(Globals.getTimeService().now());
			info.setModified();
			msg.setResult(true);
			
			//重要信息提示
			String content = Globals.getLangService().read(CommonLangConstants_10000.ADDPROP, money,
					Globals.getLangService().read(Currency.COINS.getNameKey()));
			player.sendImportPromptMessage(content);
		}else{
			msg.setResult(false);
			msg.setDesc("用户金钱不足");
//			player.sendImportPromptMessage(CommonLangConstants_10000.COMMON_NOT_ENOUGH, 
//								CommonLangConstants_10000.CURRENCY_NAME_GOLD);
			player.sendMessage(msg);
			return;
		}
		player.sendMessage(msg);
		
		
		//为前端推算摇钱信息
		GCTreeInfo msgInfo = new GCTreeInfo();
		int maxShakeTimes = Globals.getVitService().getTimesMap().get(vipLevel).getShakeTimes();
		int costGold = 0;
		if(info.getShakeTimes() < maxShakeTimes){
			costGold = Globals.getTreeService().getTreeCostTemplateMap().get(info.getShakeTimes() + 1).getCostGold();
		}
		if(info.getShakeTimes() == maxShakeTimes){
			costGold = Globals.getTreeService().getTreeCostTemplateMap().get(maxShakeTimes).getCostGold();
		}
		
		boolean batchFlag = false;
		int batchCost = 0;
		if(vipLevel >= VIP_LEVEL && maxShakeTimes - info.getShakeTimes() >= 10){
			batchFlag = true;
			for(int i = 1; i <= BATCH_TIMES; i++){
				batchCost += Globals.getTreeService().getTreeCostTemplateMap().get(info.getShakeTimes() + i).getCostGold();
			}
		}
		msgInfo.setBatchFlag(batchFlag);
		msgInfo.setCostBatch(batchCost);
		msgInfo.setCostGold(costGold);
		msgInfo.setGetCoin(Globals.getTreeService().getTreeTemplateMap().get(human.getLevel()).getGetCoins());
		msgInfo.setTotalTimes(maxShakeTimes);
		msgInfo.setShakeTimes(info.getShakeTimes());
		player.sendMessage(msgInfo);
		
		return;	
	}

	public void handleTreeShakeBatch(Player player,
			CGTreeShakeBatch cgTreeShakeBatch) {
		// TODO Auto-generated method stub
		
		GCTreeShake msg = new GCTreeShake();
		
		Human human = player.getHuman();
		
		int level = human.getLevel();
		
		if(level < 14){
			msg.setResult(false);
			msg.setDesc("玩家等级不够");
			player.sendErrorPromptMessage(CommonLangConstants_10000.LEVEL_NOT_REACH);
			player.sendMessage(msg);
			return;
		}
		
		int vipLevel = human.getVipLevel();
		
		//vip4级开放批量摇钱
		if(vipLevel < VIP_LEVEL){
			msg.setResult(false);
			msg.setDesc("vip等级不够");
			player.sendMessage(msg);
			player.sendErrorPromptMessage(CommonLangConstants_10000.VIP_LEVEL_NOT_ENOUGH);
			return;
		}
		
		int total = Globals.getVitService().getTimesMap().get(vipLevel).getShakeTimes();
		TreeInfoManager tim = human.getTreeInfoManager();
		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
		
		if(info == null){
			//如果没有数据 首先加入数据
			info = tim.creatHumanTreeInfo();
		}else{
			//0点更新
			long todayBegin = TimeUtils.getTodayBegin(Globals.getTimeService());
			if(todayBegin > info.getLastShakeTime()){
				info.setShakeTimes(0);
				info.setModified();
			}
		}
		
		if(info.getShakeTimes() + BATCH_TIMES > total){
			msg.setResult(false);
			msg.setDesc("摇钱次数不足");
			player.sendMessage(msg);
			player.sendErrorPromptMessage(CommonLangConstants_10000.SHAKE_TIMES_NOT_ENOUGH);
			return;
		}
		
		//计算10次的花费
		int cost = 0;
		Map<Integer, TreeCostTemplate> map =  Globals.getTreeService().getTreeCostTemplateMap();
		for(int i = 1; i <= BATCH_TIMES; i++){
			cost = cost + map.get(info.getShakeTimes() + i).getCostGold();
		}
		
		int money = 0;
		TreeTemplate tmp = Globals.getTreeService().getTreeTemplateMap().get(human.getLevel());
		if(tmp != null){
			money = Globals.getTreeService().getTreeTemplateMap().get(human.getLevel()).getGetCoins() * BATCH_TIMES;
		}
		if(human.hasEnoughGold(cost, true)){
			human.giveMoney(money, Currency.COINS, true, CurrencyLogReason.TREE, CurrencyLogReason.TREE.getReasonText());
			info.setShakeTimes(info.getShakeTimes() + BATCH_TIMES);
			info.setLastShakeTime(Globals.getTimeService().now());
			info.setModified();
			msg.setResult(true);
			
			//重要信息提示
			String content = Globals.getLangService().read(CommonLangConstants_10000.ADDPROP, money,
					Globals.getLangService().read(Currency.COINS.getNameKey()));
			player.sendImportPromptMessage(content);
		}else{
			msg.setResult(false);
			msg.setDesc("用户金钱不足");
			player.sendMessage(msg);
			player.sendImportPromptMessage(CommonLangConstants_10000.COMMON_NOT_ENOUGH, 
					CommonLangConstants_10000.CURRENCY_NAME_GOLD);
			return;
		}
		player.sendMessage(msg);
		
		// 为前端推算摇钱信息
		GCTreeInfo msgInfo = new GCTreeInfo();
		int maxShakeTimes = Globals.getVitService().getTimesMap().get(vipLevel)
				.getShakeTimes();
		int costGold = 0;
		if (info.getShakeTimes() < maxShakeTimes) {
			costGold = Globals.getTreeService().getTreeCostTemplateMap()
					.get(info.getShakeTimes() + 1).getCostGold();
		}
		boolean batchFlag = false;
		int batchCost = 0;
		if (vipLevel >= VIP_LEVEL && maxShakeTimes - info.getShakeTimes() >= BATCH_TIMES) {
			batchFlag = true;
			for (int i = 1; i <= BATCH_TIMES; i++) {
				batchCost += Globals.getTreeService().getTreeCostTemplateMap()
						.get(info.getShakeTimes() + i).getCostGold();
			}
		}
		msgInfo.setBatchFlag(batchFlag);
		msgInfo.setCostBatch(batchCost);
		msgInfo.setCostGold(costGold);
		msgInfo.setGetCoin(Globals.getTreeService().getTreeTemplateMap()
				.get(human.getLevel()).getGetCoins());
		msgInfo.setTotalTimes(maxShakeTimes);
		msgInfo.setShakeTimes(info.getShakeTimes());
		player.sendMessage(msgInfo);
		
		return;	
	}

	public void handleGetFruit(Player player, CGGetFruit cgGetFruit) {
		// TODO Auto-generated method stub
		GCGetFruit msg = new GCGetFruit();
		
		Human human = player.getHuman();
		int level = human.getLevel();	
		if(level < 20){
			msg.setResult(false);
			msg.setDesc("玩家等级不够");
			player.sendMessage(msg);
			player.sendErrorPromptMessage(CommonLangConstants_10000.LEVEL_NOT_REACH);
			return;
		}
		
		TreeInfoManager tim = human.getTreeInfoManager();
		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());	
		if(info == null){
			//如果没有首先加入数据
			info = tim.creatHumanTreeInfo();	
		}
		
		int fruitLevel = cgGetFruit.getFruitLevel();
		int getFruitLevel = info.getFruitLevel();
		if (fruitLevel <= 0
				|| fruitLevel != getFruitLevel + 1
				|| fruitLevel > Globals.getTreeService().getMaxFruitTemplate()
						.getLevel() || fruitLevel > info.getTreeLevel()) {
			msg.setResult(false);
			msg.setDesc("果实等级不符");
			player.sendMessage(msg);
			player.sendErrorPromptMessage(CommonLangConstants_10000.FRUIT_ERROR);
			return;
		}
		
		TreeFruitTemplate tmp = Globals.getTreeService().getTreeFruitTemplateMap().get(fruitLevel);
		if(tmp == null){
			msg.setResult(false);
			msg.setDesc("果实不存在");
			player.sendMessage(msg);
			player.sendErrorPromptMessage(CommonLangConstants_10000.FRUIT_ERROR);
			return;
		}
		
		//处理vip用户和普通用户
		int getCoins = 0;
		int getGold = 0;
		String itemSn = "";
		if(human.getVipLevel() >= 1){
			getCoins = tmp.getVipCoins();
			getGold = tmp.getVipGlod();
			itemSn = tmp.getVipItem();
		}else{
			getCoins = tmp.getCoins();
			getGold = tmp.getGlod();
			itemSn = tmp.getItem();
		}
		
		if(itemSn != null){
			List<ItemParam> params = new ArrayList<ItemParam>();
			ItemTemplate itemTemplate = Globals.getItemService().getTemplateByItemSn(itemSn);
			BindStatus bind = itemTemplate.getBindStatusAfterOper(
					BindStatus.NOT_BIND, Oper.BUY);
			params.add(new ItemParam(itemTemplate.getItemSn(), 1, bind));
			if(!human.getInventory().checkSpace(params, true)){
				msg.setResult(false);
				msg.setDesc("物品栏已满");
				player.sendMessage(msg);
//				player.sendErrorPromptMessage(CommonLangConstants_10000.BAG_IS_FULL);
				return;
			}
		}
		
		human.giveMoney(getCoins, Currency.COINS, false,
				CurrencyLogReason.TREE, CurrencyLogReason.TREE.getReasonText());
		human.giveMoney(getGold, Currency.COUPON, true, CurrencyLogReason.TREE,
				CurrencyLogReason.TREE.getReasonText());

		// 重要信息提示
		String content1 = Globals.getLangService().read(
				CommonLangConstants_10000.ADDPROP, getCoins,
				Globals.getLangService().read(Currency.COINS.getNameKey()));
		player.sendImportPromptMessage(content1);

		// 重要信息提示
		String content2 = Globals.getLangService().read(
				CommonLangConstants_10000.ADDPROP, getGold,
				Globals.getLangService().read(Currency.COUPON.getNameKey()));
		player.sendImportPromptMessage(content2);
		
				
		if(itemSn != null){
			human.getInventory().addItem(itemSn, 1, BindStatus.BIND_YET, ItemGenLogReason.TREE_FRUIT, null, true);
		}
		
		//更新数据库
		info.setFruitLevel(info.getFruitLevel() + 1);
		info.setModified();
		
		msg.setResult(true);
		msg.setDesc("");
		
		player.sendMessage(msg);
		
		player.sendRightMessage(CommonLangConstants_10000.SUCCESS_GET);
	}

	public void handleWaterFriend(Player player, CGWaterFriend cgWaterFriend) {
//		// TODO Auto-generated method stub
//		GCWaterInfo msgInfo = new GCWaterInfo();
//		GCWaterInfo msgFriendInfo = new GCWaterInfo();
//		GCWaterResult msg = new GCWaterResult();
//		Human human = player.getHuman();
//		boolean onLineFlag = true;
//		
//		Human friend = new Human();
//		if(Globals.getOnlinePlayerService().getPlayerById(cgWaterFriend.getFriendUUID()) == null){
//			HumanEntity humanEntity = Globals.getDaoService().getHumanDao().get(cgWaterFriend.getFriendUUID());
//			if(humanEntity != null){
//				friend.fromEntity(humanEntity);
//				onLineFlag = false;
//			}else{
//				msg.setResult(false);
//				msg.setDesc("好友不存在");
//				player.sendMessage(msg);
//				player.sendImportPromptMessage(CommonLangConstants_10000.NO_THIS_PLAYER);
//				return;
//			}
//			
//		}else{
//			friend = Globals.getOnlinePlayerService().getPlayerById(cgWaterFriend.getFriendUUID()).getHuman();
//		}
//		
//		int level = human.getLevel();	
//		if(level < 20){
//			msg.setResult(false);
//			msg.setDesc("玩家等级不够");
//			player.sendMessage(msg);
//			player.sendImportPromptMessage(CommonLangConstants_10000.LEVEL_NOT_REACH);
//			return;
//		}
//		
//		int frindLevel = friend.getLevel();
//		if(frindLevel < 20){
//			msg.setResult(false);
//			msg.setDesc("好友等级不够");
//			player.sendMessage(msg);
//			player.sendImportPromptMessage(CommonLangConstants_10000.LEVEL_NOT_REACH);
//			return;
//		}
//		
//		FriendManager fm = human.getFriendManager();
//		FriendInfo humanFriend = new FriendInfo();
//		if(!fm.getFriends().containsKey(cgWaterFriend.getFriendUUID())){
//			msg.setResult(false);
//			msg.setDesc("该用户不是你的好友");
//			player.sendMessage(msg);
//			player.sendImportPromptMessage(PlayerLangConstants_30000.NOT_YOUR_FRIEND);
//			return;
//		}else{
//			humanFriend = fm.getFriends().get(cgWaterFriend.getFriendUUID());
//		}
//		
//		if(humanFriend.getWaterFlag() != 0){
//			msg.setResult(false);
//			msg.setDesc("好友已经浇过水或者浇水系统未开放");
//			player.sendMessage(msg);
//			player.sendImportPromptMessage(CommonLangConstants_10000.WATER_OR_NOT_OPEN);
//			return;
//		}
//		
//		TreeInfoManager tim = human.getTreeInfoManager();
//		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());		
//		if(info == null){
//			//如果没有首先加入数据
//			info = tim.creatHumanTreeInfo();		
//		}
//		
//		TreeInfoEntity entity = new TreeInfoEntity();
//		TreeInfoManager firendTim = friend.getTreeInfoManager();
//		HumanTreeInfo friendInfo = firendTim.getTreeInfos().get(friend.getUUID());
//		if(onLineFlag){		
//			if(friendInfo == null){
//				//如果没有首先加入数据
//				friendInfo = firendTim.creatHumanTreeInfo();					
//			}
//		}else{
//			List<TreeInfoEntity> list = Globals.getDaoService().getTreeInfoDao().getTreeInfosByHumanId(cgWaterFriend.getFriendUUID()); 
//			if(list.isEmpty()){
//				friendInfo = firendTim.creatOffLineHumanTreeInfo();
//			}else{
//				entity = list.get(0);
//			}
//		}
//		
//		
//		//首先处理浇水者
//		if(TimeUtils.getBeginOfDay(Globals.getTimeService().now()) > info.getLastFriendWaterTime()){
//			info.setFriendWaterTimes(0);
//			info.setModified();
//		}
//		
//		if(info.getFriendWaterTimes() < 30){
//			int coins = Globals.getTreeService().getTreeWaterTemplateMap().get(level).getGetCoins();
//			human.giveMoney(coins, Currency.COINS, false, MoneyLogReason.TREE_FRUIT, null, CurrencyCostType.NULL, CurrencyLogReason.TREE);
//		}
//		
//		info.setFriendWaterTimes(info.getFriendWaterTimes() + 1);
//		
//		msgInfo = Globals.getTreeService().getWaterInfoMsg(info);
//		
//		//然后处理被浇水者
//		if(onLineFlag){
//			friendInfo.setCurTreeExp(friendInfo.getCurTreeExp() + 10);
//			
//			
//			msgFriendInfo = Globals.getTreeService().getWaterInfoMsg(friendInfo);
//		}else{
//			entity.setCurTreeExp(entity.getCurTreeExp() + 10);
//			while(entity.getCurTreeExp() > entity.getMaxTreeExp()){
//				entity.setCurTreeExp(entity.getCurTreeExp() - entity.getMaxTreeExp());
//				entity.setTreeLevel(entity.getTreeLevel() + 1);
//				entity.setMaxTreeExp(Globals.getTreeService().getTreeExpTemplateMap().get(entity.getTreeLevel()).getExp());
//			}
//			tim.updateOffLineHumanTreeInfo(entity);
//		}
//		
//		//设置好友浇水标志
//		humanFriend.setWaterFlag(1);
//		humanFriend.setLastWaterTime(Globals.getTimeService().now());
//		humanFriend.setModified();
//		
//		msg.setResult(true);
//		msg.setDesc("");
//		
//		player.sendMessage(msg);
//		player.sendMessage(msgInfo);
//		
//		if(onLineFlag){
//			friend.sendMessage(msgFriendInfo);
//		}
//		
//		//添加浇水记录
//		TreeWaterManager twm = human.getTreeWaterManager();
//		twm.addTowerInfoToDb(human, friend);
//		
//		//重新发送好友信息
//		player.getHuman().getFriendManager().getHumanFriends();
		
		Human human = player.getHuman();
		String friendUuid = cgWaterFriend.getFriendUUID();
		String friendName = cgWaterFriend.getFriendName();
		
		Globals.getTreeService().waterFriend(player, friendUuid, friendName);
		
		GCWaterInfo msgInfo = new GCWaterInfo();
		GCWaterResult msg = new GCWaterResult();
		
		TreeInfoManager tim = human.getTreeInfoManager();
		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
		msgInfo = Globals.getTreeService().getWaterInfoMsg(info);
		
		msg.setResult(true);
		msg.setDesc("");
		
		player.sendMessage(msg);
		player.sendMessage(msgInfo);
	}

	public void handleWaterInfo(Player player, CGWaterInfo cgWaterInfo) {
		// TODO Auto-generated method stub
		GCWaterInfo msg = new GCWaterInfo();
		Human human = player.getHuman();
		
		int level = human.getLevel();
		
		if(level < 20){
			return;
		}
		
		
		TreeInfoManager tim = human.getTreeInfoManager();
		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
		
		if(info == null){
			//如果没有首先加入数据
			info = tim.creatHumanTreeInfo();	
		}
		
		msg = Globals.getTreeService().getWaterInfoMsg(info);
		player.sendMessage(msg);
	}

	public void handleWaterMyself(Player player, CGWaterMyself cgWaterMyself) {
		// TODO Auto-generated method stub
		
		GCWaterInfo msgInfo = new GCWaterInfo();
		GCWaterResult msg = new GCWaterResult();
		
		Human human = player.getHuman();
		
		int level = human.getLevel();
		
		if(level < 20){
			msg.setResult(false);
			msg.setDesc("玩家等级不够");
			player.sendMessage(msg);
			player.sendErrorPromptMessage(CommonLangConstants_10000.LEVEL_NOT_REACH);
			return;
		}
		
		TreeInfoManager tim = human.getTreeInfoManager();
		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
		
		if(info == null){
			//如果没有首先加入数据
			info = tim.creatHumanTreeInfo();	
		}
		
		if(TimeUtils.getTodayBegin(Globals.getTimeService()) < info.getLastWaterTime() && info.getWaterTimes() >=3){
			msg.setResult(false);
			msg.setDesc("浇水次数已满");
			player.sendMessage(msg);
			player.sendErrorPromptMessage(CommonLangConstants_10000.WATER_TIMES_ENOUGH);
			return;
		}
		
		if(Globals.getTimeService().now() - info.getLastWaterTime() < 5*60*1000){
			msg.setResult(false);
			msg.setDesc("冷却时间未到");
			player.sendMessage(msg);
//			player.sendImportPromptMessage(CommonLangConstants_10000.COMMON_COOLINGDOWN);
			
			player.sendErrorPromptMessage(CommonLangConstants_10000.COMMON_COOLINGDOWN);
			return;
		}
		
		int coins = Globals.getTreeService().getTreeWaterTemplateMap().get(level).getGetCoins();
		
		human.giveMoney(coins, Currency.COINS, false, CurrencyLogReason.TREE,
				CurrencyLogReason.TREE.getReasonText());
		msg.setResult(true);
		msg.setDesc("");
		
		//重要信息提示
		String content = Globals.getLangService().read(CommonLangConstants_10000.ADDPROP, coins,
				Globals.getLangService().read(Currency.COINS.getNameKey()));
		player.sendImportPromptMessage(content);
		
		info.setCurTreeExp(info.getCurTreeExp() + 10);
		info.setLastWaterTime(Globals.getTimeService().now());
		info.setWaterTimes(info.getWaterTimes() + 1);
		info.setModified();
		
		msgInfo = Globals.getTreeService().getWaterInfoMsg(info);
		
		player.sendMessage(msg);
		player.sendMessage(msgInfo);
		
		//添加浇水记录
		TreeWaterManager twm = human.getTreeWaterManager();
		twm.addTowerInfoToDb(human, human);
	}

	public void handleGetWaterRecord(Player player,
			CGGetWaterRecord cgGetWaterRecord) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		
		GCGetWaterRecord msg  = new GCGetWaterRecord();
		msg.setHumanTreeWaterInfo(human.getTreeWaterManager()
				.getTreeWaterInfos().toArray(new HumanTreeWaterInfo[0]));
		player.sendMessage(msg);
	}

	public void handleWaterFriendBatch(Player player,
			CGWaterFriendBatch cgWaterFriendBatch) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		FriendManager fm = human.getFriendManager();
		
		GCWaterInfo msgInfo = new GCWaterInfo();
		GCWaterResult msg = new GCWaterResult();
		
		TreeInfoManager tim = human.getTreeInfoManager();
		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
		
		Map<String, FriendInfo> friends = fm.getFriends();
		
		int waterTimes = 0;
		for(Map.Entry<String, FriendInfo> f : friends.entrySet()){
			if(f.getValue().getWaterFlag() == 0){
				if(Globals.getTreeService().waterFriendBatch(player, f.getValue().getFriendUUID(), f.getValue().getFriendName())){
					if(info.getFriendWaterTimes() <= 30){
						waterTimes ++;		
					}
				}
			}		
		}
		
		int coins = Globals.getTreeService().getTreeWaterTemplateMap()
				.get(human.getLevel()).getGetCoins();
		if(coins * waterTimes > 0){
			human.giveMoney(coins * waterTimes, Currency.COINS, false,
					CurrencyLogReason.TREE,
					CurrencyLogReason.TREE.getReasonText());
			// 重要信息提示
			String content = Globals.getLangService().read(
					CommonLangConstants_10000.WATER_FREND_BATCH,
					coins * waterTimes);
			player.sendImportPromptMessage(content);
			msg.setResult(true);
			msg.setDesc("");
		}else{
			msg.setResult(false);
			msg.setDesc("你太勤劳了，都还不需要浇水！");
			
			// 重要信息提示
			String content = Globals.getLangService().read(
					CommonLangConstants_10000.WATER_FREND_BATCH_NONE_FRIEND);
			player.sendImportPromptMessage(content);
		}
			
		msgInfo = Globals.getTreeService().getWaterInfoMsg(info);
		
		player.sendMessage(msg);
		player.sendMessage(msgInfo);
		
	}

	public void handleFriendWaterInfo(Player player,
			CGFriendWaterInfo cgFriendWaterInfo) {
		Human human = player.getHuman();
		String friendUuid = cgFriendWaterInfo.getFriendUuid();
		Player friendPlayer = Globals.getOnlinePlayerService().getPlayerById(friendUuid);
		
		//分为离线和在线两种方式处理
		if(friendPlayer == null){
			Globals.getTreeService().getOfflineFriendWaterInfo(human, friendUuid);
		}else{
			Globals.getTreeService().getOnlineFriendWaterInfo(human, friendPlayer);
		}
	}

	public void handleGetFriendWaterRecord(Player player,
			CGGetFriendWaterRecord cgGetFriendWaterRecord) {
		Human human = player.getHuman();
		String friendUuid = cgGetFriendWaterRecord.getFriendUuid();
		Player friendPlayer = Globals.getOnlinePlayerService().getPlayerById(
				friendUuid);

		// 分为离线和在线两种方式处理
		if (friendPlayer == null) {
			Globals.getTreeService().getOfflineFriendWaterRecord(human,
					friendUuid);
		} else {
			Globals.getTreeService().getOnlineFriendWaterRecord(human,
					friendPlayer);
		}
		
	}
}