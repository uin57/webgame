package com.pwrd.war.gameserver.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.db.dao.TreeInfoDao;
import com.pwrd.war.db.dao.TreeWaterDao;
import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.db.model.TreeInfoEntity;
import com.pwrd.war.db.model.TreeWaterEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.friend.FriendInfo;
import com.pwrd.war.gameserver.friend.FriendManager;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.tree.msg.GCFriendWaterInfo;
import com.pwrd.war.gameserver.tree.msg.GCGetFriendWaterRecord;
import com.pwrd.war.gameserver.tree.msg.GCTreeInfo;
import com.pwrd.war.gameserver.tree.msg.GCWaterInfo;
import com.pwrd.war.gameserver.tree.msg.GCWaterResult;
import com.pwrd.war.gameserver.tree.template.TreeCostTemplate;
import com.pwrd.war.gameserver.tree.template.TreeExpTemplate;
import com.pwrd.war.gameserver.tree.template.TreeFruitTemplate;
import com.pwrd.war.gameserver.tree.template.TreeTemplate;
import com.pwrd.war.gameserver.tree.template.TreeWaterTemplate;

public class TreeService {
	
	private static int VIP_LEVEL = 4;
	private static int BATCH_TIMES = 10;
	
	private Map<Integer, TreeTemplate> treeTemplateMap = new HashMap<Integer, TreeTemplate>();
	private Map<Integer, TreeCostTemplate> treeCostTemplateMap = new HashMap<Integer, TreeCostTemplate>();
	private Map<Integer, TreeExpTemplate> treeExpTemplateMap = new HashMap<Integer, TreeExpTemplate>();
	private Map<Integer, TreeFruitTemplate> treeFruitTemplateMap = new HashMap<Integer, TreeFruitTemplate>();
	private Map<Integer, TreeWaterTemplate> treeWaterTemplateMap = new HashMap<Integer, TreeWaterTemplate>();
	
	private TreeExpTemplate maxTreeTemplate = new TreeExpTemplate();
	private TreeFruitTemplate maxTreeFruitTemplate = new TreeFruitTemplate();
	
	public TreeService(){
		Map<Integer, TreeTemplate>  map = Globals.getTemplateService().getAll(TreeTemplate.class);
		Map<Integer, TreeCostTemplate>  map1 = Globals.getTemplateService().getAll(TreeCostTemplate.class);
		Map<Integer, TreeExpTemplate>  map2 = Globals.getTemplateService().getAll(TreeExpTemplate.class);
		Map<Integer, TreeFruitTemplate>  map3 = Globals.getTemplateService().getAll(TreeFruitTemplate.class);
		Map<Integer, TreeWaterTemplate>  map4 = Globals.getTemplateService().getAll(TreeWaterTemplate.class);
		for(Map.Entry<Integer, TreeTemplate> m : map.entrySet()){
			treeTemplateMap.put(m.getValue().getLevel(), m.getValue());
		}
		
		for(Map.Entry<Integer, TreeCostTemplate> m : map1.entrySet()){
			treeCostTemplateMap.put(m.getValue().getShakeTimes(), m.getValue());
		}
		
		for(Map.Entry<Integer, TreeExpTemplate> m : map2.entrySet()){
			treeExpTemplateMap.put(m.getValue().getLevel(), m.getValue());
		}
		
		for(Map.Entry<Integer, TreeFruitTemplate> m : map3.entrySet()){
			treeFruitTemplateMap.put(m.getValue().getLevel(), m.getValue());
		}
		
		for(Map.Entry<Integer, TreeWaterTemplate> m : map4.entrySet()){
			treeWaterTemplateMap.put(m.getValue().getLevel(), m.getValue());
		}
		
		maxTreeTemplate = this.getMaxExpTemplate();
		maxTreeFruitTemplate = this.getMaxFruitTemplate();
	}

	public Map<Integer, TreeTemplate> getTreeTemplateMap() {
		return treeTemplateMap;
	}

	public Map<Integer, TreeCostTemplate> getTreeCostTemplateMap() {
		return treeCostTemplateMap;
	}

	public Map<Integer, TreeExpTemplate> getTreeExpTemplateMap() {
		return treeExpTemplateMap;
	}

	public Map<Integer, TreeFruitTemplate> getTreeFruitTemplateMap() {
		return treeFruitTemplateMap;
	}

	public Map<Integer, TreeWaterTemplate> getTreeWaterTemplateMap() {
		return treeWaterTemplateMap;
	}
	
	public TreeExpTemplate getMaxExpTemplate(){
		TreeExpTemplate tmp = new TreeExpTemplate();
		tmp.setExp(0);
		tmp.setLevel(0);

		for(Map.Entry<Integer, TreeExpTemplate> m : treeExpTemplateMap.entrySet()){
			if(m.getValue().getExp() > tmp.getExp()){
				tmp = m.getValue();
			}
		}
		
		return tmp;
	}
	
	public TreeFruitTemplate getMaxFruitTemplate(){
		TreeFruitTemplate tmp = new TreeFruitTemplate();
		tmp.setLevel(0);

		for(Map.Entry<Integer, TreeFruitTemplate> m : treeFruitTemplateMap.entrySet()){
			if(m.getValue().getLevel() > tmp.getLevel()){
				tmp = m.getValue();
			}
		}
		
		return tmp;
	}
	
	public GCWaterInfo getWaterInfoMsg(HumanTreeInfo info){
		GCWaterInfo msg = new GCWaterInfo();
		
		this.treeLevelUp(info);
		
		msg.setCurTreeExp(info.getCurTreeExp());
		
		if(TimeUtils.getTodayBegin(Globals.getTimeService()) > info.getLastFriendWaterTime()){
			info.setFriendWaterTimes(0);
			info.setModified();
		}
		msg.setFriendWaterTimes(info.getFriendWaterTimes());
		
		msg.setFruitLevel(info.getFruitLevel());
		msg.setTreeLevel(info.getTreeLevel());
		msg.setMaxTreeExp(info.getMaxTreeExp());
		
		//自己浇水冷却时间
		long lastWaterTime = info.getLastWaterTime();
		int waterTime = 0;
		if(Globals.getTimeService().now() - lastWaterTime < 60*5*1000){
			waterTime = 60*5 - (int)(Globals.getTimeService().now() - lastWaterTime)/1000;
		}
		msg.setWaterTime(waterTime);
		
		if(TimeUtils.getTodayBegin(Globals.getTimeService()) > lastWaterTime){
			msg.setWaterTimes(0);
			info.setWaterTimes(0);
			info.setModified();
		}else{
			msg.setWaterTimes(info.getWaterTimes());
		}
		
		return msg;
	}
	
	public void treeLevelUp(HumanTreeInfo info){
		int curExp = info.getCurTreeExp();
		int level = info.getTreeLevel();
		while(curExp >= treeExpTemplateMap.get(level).getExp() && level < maxTreeTemplate.getLevel()){
			curExp = curExp - treeExpTemplateMap.get(level).getExp();
			level = level + 1; 
		}
		
		info.setCurTreeExp(curExp);
		info.setTreeLevel(level);
		info.setMaxTreeExp(treeExpTemplateMap.get(level).getExp());
		
		info.setModified();
	}

	public TreeExpTemplate getMaxTreeTemplate() {
		return maxTreeTemplate;
	}

	public TreeFruitTemplate getMaxTreeFruitTemplate() {
		return maxTreeFruitTemplate;
	}
	
	public void getTreeInfo(Player player){
		GCTreeInfo msg = new GCTreeInfo();
		Human human = player.getHuman();
		
		int level = human.getLevel();	
		if(level < 14){
			return;
		}
		
		TreeInfoManager tim = human.getTreeInfoManager();
		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
				
		if(info == null){
			//如果没有数据 首先加入数据
			info = tim.creatHumanTreeInfo();		
			msg.setShakeTimes(0);
		}else{
			//清零
			long todayBegin = TimeUtils.getTodayBegin(Globals.getTimeService());
			if(todayBegin > info.getLastShakeTime()){
				info.setShakeTimes(0);
				info.setModified();
			}
			msg.setShakeTimes(info.getShakeTimes());
		}
		
		int vipLevel = human.getVipLevel();
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
		msg.setBatchFlag(batchFlag);
		msg.setCostBatch(batchCost);
		msg.setCostGold(costGold);
		
		TreeTemplate tmp = Globals.getTreeService().getTreeTemplateMap().get(human.getLevel());
		if(tmp != null){
			msg.setGetCoin(Globals.getTreeService().getTreeTemplateMap().get(human.getLevel()).getGetCoins());
		}else{
			msg.setGetCoin(0);
		}
		
		msg.setTotalTimes(maxShakeTimes);
		player.sendMessage(msg);
	}
	
	public void waterFriend(Player player, String friendUuid, String friendName) {
		// TODO Auto-generated method stub
//		GCWaterInfo msgInfo = new GCWaterInfo();
		GCWaterInfo msgFriendInfo = new GCWaterInfo();
		GCWaterResult msg = new GCWaterResult();
		Human human = player.getHuman();
		boolean onLineFlag = true;

		Human friend = new Human();
		if (Globals.getOnlinePlayerService().getPlayerById(friendUuid) == null) {
			HumanEntity humanEntity = Globals.getDaoService().getHumanDao()
					.get(friendUuid);
			if (humanEntity != null) {
				friend.fromEntity(humanEntity);
				onLineFlag = false;
			} else {
				msg.setResult(false);
				msg.setDesc("好友不存在");
				player.sendMessage(msg);
				player.sendImportPromptMessage(CommonLangConstants_10000.NO_THIS_PLAYER);
				return;
			}

		} else {
			friend = Globals.getOnlinePlayerService().getPlayerById(friendUuid)
					.getHuman();
		}

		int level = human.getLevel();
		if (level < 20) {
			msg.setResult(false);
			msg.setDesc("玩家等级不够");
			player.sendMessage(msg);
			player.sendImportPromptMessage(CommonLangConstants_10000.LEVEL_NOT_REACH);
			return;
		}

		int frindLevel = friend.getLevel();
		if (frindLevel < 20) {
			msg.setResult(false);
			msg.setDesc("好友等级不够");
			player.sendMessage(msg);
			human.getPlayer().sendErrorPromptMessage(Globals.getLangService().read(CommonLangConstants_10000.FRIEND_FUNC_NOT_OPEN));
			return;
		}

		FriendManager fm = human.getFriendManager();
		FriendInfo humanFriend = new FriendInfo();
		if (!fm.getFriends().containsKey(friendUuid)) {
			msg.setResult(false);
			msg.setDesc("该用户不是你的好友");
			player.sendMessage(msg);
			player.sendImportPromptMessage(PlayerLangConstants_30000.NOT_YOUR_FRIEND);
			return;
		} else {
			humanFriend = fm.getFriends().get(friendUuid);
		}

		if (humanFriend.getWaterFlag() != 0) {
			msg.setResult(false);
			msg.setDesc("好友已经浇过水或者浇水系统未开放");
			player.sendMessage(msg);
			player.sendImportPromptMessage(CommonLangConstants_10000.WATER_OR_NOT_OPEN);
			return;
		}

		TreeInfoManager tim = human.getTreeInfoManager();
		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
		if (info == null) {
			// 如果没有首先加入数据
			info = tim.creatHumanTreeInfo();
		}

		TreeInfoEntity entity = new TreeInfoEntity();
		TreeInfoManager firendTim = friend.getTreeInfoManager();
		HumanTreeInfo friendInfo = firendTim.getTreeInfos().get(
				friend.getUUID());
		if (onLineFlag) {
			if (friendInfo == null) {
				// 如果没有首先加入数据
				friendInfo = firendTim.creatHumanTreeInfo();
			}
		} else {
			List<TreeInfoEntity> list = Globals.getDaoService()
					.getTreeInfoDao().getTreeInfosByHumanId(friendUuid);
			if (list.isEmpty()) {
				friendInfo = firendTim.creatOffLineHumanTreeInfo();
			} else {
				entity = list.get(0);
			}
		}

		// 首先处理浇水者
		if (TimeUtils.getBeginOfDay(Globals.getTimeService().now()) > info
				.getLastFriendWaterTime()) {
			info.setFriendWaterTimes(0);
			info.setModified();
		}

		if (info.getFriendWaterTimes() < 30) {
			int coins = Globals.getTreeService().getTreeWaterTemplateMap()
					.get(level).getGetCoins();
			human.giveMoney(coins, Currency.COINS, false,
					CurrencyLogReason.TREE, CurrencyLogReason.TREE.getReasonText());
			
			//重要信息提示
			String content = Globals.getLangService().read(
					CommonLangConstants_10000.WATER_FREND_BATCH,
					coins);
			player.sendImportPromptMessage(content);
		}

		info.setFriendWaterTimes(info.getFriendWaterTimes() + 1);

//		msgInfo = Globals.getTreeService().getWaterInfoMsg(info);

		// 然后处理被浇水者
		if (onLineFlag) {
			friendInfo.setCurTreeExp(friendInfo.getCurTreeExp() + 10);

			msgFriendInfo = Globals.getTreeService()
					.getWaterInfoMsg(friendInfo);
		} else {
			entity.setCurTreeExp(entity.getCurTreeExp() + 10);
			while (entity.getCurTreeExp() > entity.getMaxTreeExp()) {
				entity.setCurTreeExp(entity.getCurTreeExp()
						- entity.getMaxTreeExp());
				entity.setTreeLevel(entity.getTreeLevel() + 1);
				entity.setMaxTreeExp(Globals.getTreeService()
						.getTreeExpTemplateMap().get(entity.getTreeLevel())
						.getExp());
			}
			tim.updateOffLineHumanTreeInfo(entity);
		}

		// 设置好友浇水标志
		humanFriend.setWaterFlag(1);
		humanFriend.setLastWaterTime(Globals.getTimeService().now());
		humanFriend.setModified();

//		msg.setResult(true);
//		msg.setDesc("");
//
//		player.sendMessage(msg);
//		player.sendMessage(msgInfo);

		if (onLineFlag) {
			friend.sendMessage(msgFriendInfo);
		}

		// 添加浇水记录
		TreeWaterManager twm = human.getTreeWaterManager();
		twm.addTowerInfoToDb(human, friend);

		// 重新发送好友信息
		player.getHuman().getFriendManager().getHumanFriends();
		
		if(onLineFlag){
			this.getOnlineFriendWaterInfo(human, friend.getPlayer());
		}else{
			this.getOfflineFriendWaterInfo(human, friendUuid);
		}
	}
	
	/**
	 * 好友一键浇水
	 * @param player
	 * @param friendUuid
	 * @param friendName
	 * @return
	 */
	public boolean waterFriendBatch(Player player, String friendUuid, String friendName) {
		// TODO Auto-generated method stub
//		GCWaterInfo msgInfo = new GCWaterInfo();
		GCWaterInfo msgFriendInfo = new GCWaterInfo();
//		GCWaterResult msg = new GCWaterResult();
		Human human = player.getHuman();
		boolean onLineFlag = true;

		Human friend = new Human();
		if (Globals.getOnlinePlayerService().getPlayerById(friendUuid) == null) {
			HumanEntity humanEntity = Globals.getDaoService().getHumanDao()
					.get(friendUuid);
			if (humanEntity != null) {
				friend.fromEntity(humanEntity);
				onLineFlag = false;
			} else {
				return false;
			}

		} else {
			friend = Globals.getOnlinePlayerService().getPlayerById(friendUuid)
					.getHuman();
		}

		int level = human.getLevel();
		if (level < 20) {
			human.getPlayer().sendErrorPromptMessage(Globals.getLangService().read(CommonLangConstants_10000.FRIEND_FUNC_NOT_OPEN));
			return false;
		}

		int frindLevel = friend.getLevel();
		if (frindLevel < 20) {
			
			return false;
		}

		FriendManager fm = human.getFriendManager();
		FriendInfo humanFriend = new FriendInfo();
		if (!fm.getFriends().containsKey(friendUuid)) {
			return false;
		} else {
			humanFriend = fm.getFriends().get(friendUuid);
		}

		if (humanFriend.getWaterFlag() != 0) {
			return false;
		}

		TreeInfoManager tim = human.getTreeInfoManager();
		HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
		if (info == null) {
			// 如果没有首先加入数据
			info = tim.creatHumanTreeInfo();
		}

		TreeInfoEntity entity = new TreeInfoEntity();
		TreeInfoManager firendTim = friend.getTreeInfoManager();
		HumanTreeInfo friendInfo = firendTim.getTreeInfos().get(
				friend.getUUID());
		if (onLineFlag) {
			if (friendInfo == null) {
				// 如果没有首先加入数据
				friendInfo = firendTim.creatHumanTreeInfo();
			}
		} else {
			List<TreeInfoEntity> list = Globals.getDaoService()
					.getTreeInfoDao().getTreeInfosByHumanId(friendUuid);
			if (list.isEmpty()) {
				friendInfo = firendTim.creatOffLineHumanTreeInfo();
			} else {
				entity = list.get(0);
			}
		}

		// 首先处理浇水者
		if (TimeUtils.getBeginOfDay(Globals.getTimeService().now()) > info
				.getLastFriendWaterTime()) {
			info.setFriendWaterTimes(0);
			info.setModified();
		}

		info.setFriendWaterTimes(info.getFriendWaterTimes() + 1);

		// 然后处理被浇水者
		if (onLineFlag) {
			friendInfo.setCurTreeExp(friendInfo.getCurTreeExp() + 10);

			msgFriendInfo = Globals.getTreeService()
					.getWaterInfoMsg(friendInfo);
		} else {
			entity.setCurTreeExp(entity.getCurTreeExp() + 10);
			while (entity.getCurTreeExp() > entity.getMaxTreeExp()) {
				entity.setCurTreeExp(entity.getCurTreeExp()
						- entity.getMaxTreeExp());
				entity.setTreeLevel(entity.getTreeLevel() + 1);
				entity.setMaxTreeExp(Globals.getTreeService()
						.getTreeExpTemplateMap().get(entity.getTreeLevel())
						.getExp());
			}
			tim.updateOffLineHumanTreeInfo(entity);
		}

		// 设置好友浇水标志
		humanFriend.setWaterFlag(1);
		humanFriend.setLastWaterTime(Globals.getTimeService().now());
		humanFriend.setModified();

		if (onLineFlag) {
			friend.sendMessage(msgFriendInfo);
		}

		// 添加浇水记录
		TreeWaterManager twm = human.getTreeWaterManager();
		twm.addTowerInfoToDb(human, friend);

		// 重新发送好友信息
		player.getHuman().getFriendManager().getHumanFriends();
		
		return true;
	}
	
	public void getOnlineFriendWaterInfo(Human human, Player friendPlayer){
		Human friend = friendPlayer.getHuman();
		
		int frindLevel = friend.getLevel();
		if (frindLevel < 20) {
			human.getPlayer().sendErrorPromptMessage(Globals.getLangService().read(CommonLangConstants_10000.FRIEND_FUNC_NOT_OPEN));
			return;
		}
		
		TreeInfoManager firendTim = friend.getTreeInfoManager();
		HumanTreeInfo friendInfo = firendTim.getTreeInfos().get(
				friend.getUUID());
		if (friendInfo == null) {
			// 如果没有首先加入数据
			friendInfo = firendTim.creatHumanTreeInfo();
		}
		
		GCFriendWaterInfo msg = new GCFriendWaterInfo();
		
		msg.setVocation(friendPlayer.getHuman().getVocationType().getCode());
		msg.setCurTreeExp(friendInfo.getCurTreeExp());
		msg.setMaxTreeExp(friendInfo.getMaxTreeExp());
		msg.setTreeLevel(friendInfo.getTreeLevel());
		
		human.sendMessage(msg);
	}
	
	public void getOfflineFriendWaterInfo(Human human, String friendUuid){
		FriendManager fm = human.getFriendManager();
		
		FriendInfo friendInfo = fm.getFriends().get(friendUuid);
		if(friendInfo == null){
			return;
		}
		if(friendInfo.getFriendLevel() < 20){
			human.getPlayer().sendErrorPromptMessage(Globals.getLangService().read(CommonLangConstants_10000.FRIEND_FUNC_NOT_OPEN));
			return;
		}

		TreeInfoDao treeInfoDao = Globals.getDaoService().getTreeInfoDao();
		
		List<TreeInfoEntity> list = treeInfoDao.getTreeInfosByHumanId(friendUuid);
		
		TreeInfoEntity treeInfoEntity = new TreeInfoEntity();
		
		//如果没有 生成一个初始的
		if(list.isEmpty()){
			treeInfoEntity.setId(KeyUtil.UUIDKey());
			treeInfoEntity.setCharId(friendUuid);
			treeInfoEntity.setShakeTimes(0);		
			treeInfoEntity.setLastShakeTime(0);			
			treeInfoEntity.setCurTreeExp(9);
			treeInfoEntity.setFriendWaterTimes(0);
			treeInfoEntity.setFruitLevel(0);
			treeInfoEntity.setLastWaterTime(0);
			treeInfoEntity.setTreeLevel(1);
			treeInfoEntity.setWaterTimes(0);
			treeInfoEntity.setLastFriendWaterTime(0);
			treeInfoEntity.setMaxTreeExp(Globals.getTreeService().getTreeExpTemplateMap().get(1).getExp());
			treeInfoDao.save(treeInfoEntity);;
		}else{
			treeInfoEntity = list.get(0);
		}
	
		GCFriendWaterInfo msg = new GCFriendWaterInfo();
		
		HumanEntity humanEntity = Globals.getDaoService().getHumanDao()
				.get(friendUuid);
		if (humanEntity != null) {
			msg.setVocation(humanEntity.getVocation());
		}
		msg.setCurTreeExp(treeInfoEntity.getCurTreeExp());
		msg.setMaxTreeExp(treeInfoEntity.getMaxTreeExp());
		msg.setTreeLevel(treeInfoEntity.getTreeLevel());
		
		human.sendMessage(msg);
	}
	
	public void getOnlineFriendWaterRecord(Human human, Player friendPlayer){
		Human friend = friendPlayer.getHuman();
		
		int frindLevel = friend.getLevel();
		if (frindLevel < 20) {
			human.getPlayer().sendErrorPromptMessage(Globals.getLangService().read(CommonLangConstants_10000.FRIEND_FUNC_NOT_OPEN));
			return;
		}
		
		TreeWaterManager firendTwm = friend.getTreeWaterManager();
		List<HumanTreeWaterInfo> friendWaterInfo = firendTwm.getTreeWaterInfos();
		
		GCGetFriendWaterRecord msg = new GCGetFriendWaterRecord();
		if (friendWaterInfo.isEmpty()) {
			// 如果没有首先加入数据
			msg.setHumanTreeWaterInfo(new HumanTreeWaterInfo[0]);
		}else{
			msg.setHumanTreeWaterInfo(friendWaterInfo.toArray(new HumanTreeWaterInfo[0]));
		}
		
		human.sendMessage(msg);
	}
	
	public void getOfflineFriendWaterRecord(Human human, String friendUuid){
		
		GCGetFriendWaterRecord msg = new GCGetFriendWaterRecord();
		
		FriendManager fm = human.getFriendManager();
		FriendInfo friendInfo = fm.getFriends().get(friendUuid);
		if(friendInfo == null){
			return;
		}
		if(friendInfo.getFriendLevel() < 20){
			human.getPlayer().sendErrorPromptMessage(Globals.getLangService().read(CommonLangConstants_10000.FRIEND_FUNC_NOT_OPEN));
			return;
		}
		
		List<HumanTreeWaterInfo> waterInfoList = new ArrayList<HumanTreeWaterInfo>();
		TreeWaterDao treeWaterDao = Globals.getDaoService().getTreeWaterDao();
		long time = TimeUtils.getTodayBegin(Globals.getTimeService());
		List<TreeWaterEntity> treeWaterList = treeWaterDao.getTreeWaterInfosByHumanId(friendUuid, time);			
		if(treeWaterList.isEmpty()){
			msg.setHumanTreeWaterInfo(new HumanTreeWaterInfo[0]);
		}else{
			for(TreeWaterEntity e : treeWaterList){
				HumanTreeWaterInfo info = new HumanTreeWaterInfo();
				info.fromEntity(e);
				waterInfoList.add(info);
			}
			msg.setHumanTreeWaterInfo(waterInfoList.toArray(new HumanTreeWaterInfo[0]));
		}
		
		human.sendMessage(msg);
	}
}
