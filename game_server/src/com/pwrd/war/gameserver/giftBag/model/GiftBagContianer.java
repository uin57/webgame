/**
 * 
 */
package com.pwrd.war.gameserver.giftBag.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.db.model.GiftBagEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;

/**
 * @author dendgan
 * 
 * 玩家礼包奖励存储容器
 *
 */
public class GiftBagContianer {

	private Human owner;
	/** 按礼包实例id存储的礼包 */
	private Map<String,GiftBag> giftMap;
	
	public GiftBagContianer(Human owner){
		this.owner = owner;
		giftMap = new ConcurrentHashMap<String,GiftBag>();
	}

	public Human getOwner() {
		return owner;
	}
	
	/**
	 * 添加礼包
	 */
	public void addGiftBag(GiftBag giftBag){
		giftMap.put(giftBag.getGiftBagTemplate().getGiftId(), giftBag);
	}
	
	/**
	 * 删除礼包
	 * @param giftBag
	 * @return
	 */
	public void removeGiftBag(GiftBag giftBag){
		giftMap.remove(giftBag.getGiftBagTemplate().getGiftId());
	}
	
	/**
	 * 根据礼包id获取礼包
	 * @param giftId
	 * @return
	 */
	public GiftBag getGiftBag(String giftId){
		if(StringUtils.isEmpty(giftId)){
			return null;
		}
		if(!giftMap.containsKey(giftId)){
			return null;
		}
		return giftMap.get(giftId);
	}
	
	/**
	 * 从数据库中加载玩家礼包信息
	 */
	public void load(){
		List<GiftBagEntity> giftBagList = Globals.getDaoService().getGiftBagDao().getAllGiftBagEntityByCharId(this.getOwner().getCharId());
		if(giftBagList == null || giftBagList.size() == 0){
			return;
		}
		for(GiftBagEntity entity : giftBagList){
			GiftBag giftBag = new GiftBag();
			giftBag.fromEntity(entity);
			long now = Globals.getTimeService().now();
			long period = giftBag.getGiftBagTemplate().getCycle() * 60 * 1000;
			if(period > 0 && (now - giftBag.getSendTime()) >= period){
				giftBag.delete();
			}else{
				/**
				 * 将同类礼包替换成最新的
				 */
				GiftBag oldGiftBag = this.getGiftBag(giftBag.getGiftBagTemplate().getGiftId());
				if(oldGiftBag != null){
					if(oldGiftBag.getSendTime() < giftBag.getSendTime()){
						oldGiftBag.delete();
						this.addGiftBag(giftBag);
					}
				}else{
					this.addGiftBag(giftBag);
				}
			}
		}
	}

	public Map<String, GiftBag> getGifts() {
		return giftMap;
	}
	
}
