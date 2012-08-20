/**
 * 
 */
package com.pwrd.war.gameserver.giftBag.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.giftBag.model.GiftBag;
import com.pwrd.war.gameserver.giftBag.model.vo.GiftBagInfo;
import com.pwrd.war.gameserver.giftBag.msg.GCGiftBagInfoList;
import com.pwrd.war.gameserver.giftBag.template.GiftBagTemplate;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;

/**
 * @author dengdan
 * 
 * 礼包奖励服务类
 *
 */
public class GiftBagService implements InitializeRequired {
	
	private Map<String,GiftBagTemplate> giftBagTemplateMap;
	
	public GiftBagService(){
		
	}

	/**
	 * 加载所有礼包奖励模版类
	 */
	@Override
	public void init() {
		giftBagTemplateMap = new HashMap<String,GiftBagTemplate>();
		Collection<GiftBagTemplate> temps = Globals.getTemplateService().getAll(GiftBagTemplate.class).values();
		for (GiftBagTemplate template : temps) {
			giftBagTemplateMap.put(template.getGiftId(), template);
		}
	}
	
	/**
	 * 根据礼包id获取对应的礼包模版
	 * @param giftBagId
	 * @return
	 */
	public GiftBagTemplate getGiftBagTemplate(String giftBagId){
		if(!giftBagTemplateMap.containsKey(giftBagId)){
			return null;
		}
		return giftBagTemplateMap.get(giftBagId);
	}
	
	/**
	 * 发放礼包
	 * @param giftBag
	 */
	public void sendGiftBag(String giftId){
		List<Player> playerList = Globals.getOnlinePlayerService().getOnlinePlayers();
		for(Player player:playerList){
			this.addGiftBag(player.getHuman().getCharId(), giftId);
		}
	}
	
	/**
	 * 发送玩家礼包列表
	 * @param player
	 */
	public void sendGiftList(Player player){
		if(!player.isOnline()){
			return;
		}
		Human human = player.getHuman();
		this.resetEffective(human);
		Map<String,GiftBag> gifts = human.getGiftBagContianer().getGifts();
		if(gifts == null){
			return;
		}
		GiftBagInfo[] giftsInfo = new GiftBagInfo[gifts.size()];
		int index = 0;
		for(GiftBag giftBag : gifts.values()){
			GiftBagInfo giftBagInfo = new GiftBagInfo();
			giftBagInfo.setId(giftBag.getDbId());
			giftBagInfo.setGiftId(giftBag.getGiftBagTemplate().getGiftId());
			giftBagInfo.setName(giftBag.getGiftBagTemplate().getGiftName());
			giftBagInfo.setImg(giftBag.getGiftBagTemplate().getImg());
			giftBagInfo.setDesc(giftBag.getGiftBagTemplate().getDesc());
			giftsInfo[index] =  giftBagInfo;
			index ++;
		}
		GCGiftBagInfoList msg = new GCGiftBagInfoList(giftsInfo);
		player.sendMessage(msg);
	}
	
	/**
	 * 添加礼包奖励
	 * @param player
	 * @param giftId
	 */
	public void addGiftBag(String charId,String giftId){
		GiftBagTemplate giftBagTemplate = this.getGiftBagTemplate(giftId);
		if(giftBagTemplate == null){
			return;
		}
		GiftBag giftBag = new GiftBag();
		giftBag.setDbId(KeyUtil.UUIDKey());
		giftBag.setGiftBagTemplate(giftBagTemplate);
		giftBag.setCharId(charId);
		giftBag.setSendTime(Globals.getTimeService().now());
		Player player = Globals.getOnlinePlayerService().getPlayerById(charId);
		/**
		 * 玩家在线的情况替换同类礼包,不在线的情况上线再替换,直接添加同类礼包
		 */
		if(player != null){
			Human human = player.getHuman();
			GiftBag oldGiftBag = human.getGiftBagContianer().getGiftBag(giftId);
			if(oldGiftBag != null){
				oldGiftBag.setSendTime(giftBag.getSendTime());
				oldGiftBag.setModified();
			}else{
				human.getGiftBagContianer().addGiftBag(giftBag);
				giftBag.setModified();
			}
			this.sendGiftList(player);
		}else{
			giftBag.setModified();
		}
	}
	
	
	/**
	 * 删除玩家的礼包奖励(包括在线玩家和离线玩家)
	 * @param human
	 * @param giftBag
	 */
	public void removeGiftBag(String charId,GiftBag giftBag){
		if(giftBag == null){
			return;
		}
		Player player = Globals.getOnlinePlayerService().getPlayerById(charId);
		if(player != null){
			Human human = player.getHuman();
			human.getGiftBagContianer().removeGiftBag(giftBag);
		}
		giftBag.delete();
	}
	
	/**
	 * 领取礼包奖励
	 * @param human
	 * @param giftBag
	 */
	public void giveGiftPrize(Player player,String giftDbId,String giftId){
		if(!player.isOnline()){
			return;
		}
		Human human = player.getHuman();
		GiftBag giftBag = human.getGiftBagContianer().getGiftBag(giftId);
		if(giftBag == null){
			return;
		}
		if(!giftBag.getDbId().equals(giftDbId)){
			return;
		}
		if(this.isEffective(giftBag)){
			String prizeId = giftBag.getGiftBagTemplate().getPrizeId();
			this.doPrize(player, prizeId);
		}
		this.removeGiftBag(human.getCharId(), giftBag);
		this.sendGiftList(player);
	}
	
	/**
	 * 结算奖励
	 * @param player
	 * @param prizeId
	 */
	public void doPrize(Player player,String prizeId){
		
	}
	
	/**
	 * 检查是否可以领取礼包奖励
	 * @param human
	 * @param giftBag
	 * @return
	 */
	public boolean isEffective(GiftBag giftBag){
		long now = Globals.getTimeService().now();
		long period = giftBag.getGiftBagTemplate().getCycle() * 60 * 1000;
		if(period <= 0){
			return true;
		}
		if((now - giftBag.getSendTime()) >= period){
			return false;
		}
		return true;
	}
	
	/**
	 * 重置有效的礼包,将失效礼包删除,每次发送礼包列表时调用
	 * @param player
	 */
	public void resetEffective(Human human){
		Map<String,GiftBag> gifts = human.getGiftBagContianer().getGifts();
		if(gifts == null){
			return;
		}
		List<GiftBag> inEffective = new ArrayList<GiftBag>();
		for(GiftBag giftBag : gifts.values()){
			if(!this.isEffective(giftBag)){
				inEffective.add(giftBag);
			}
		}
		for(GiftBag giftBag : inEffective){
			human.getGiftBagContianer().removeGiftBag(giftBag);
			giftBag.delete();
		}
	}

}
