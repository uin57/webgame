package com.pwrd.war.gameserver.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pwrd.war.gameserver.common.msg.GCMessage;

public class ChannelService {
	
	private Map<ChannelType, List<Player>> channels = new HashMap<ChannelType, List<Player>>();

	public ChannelService() {
	}
	public boolean isPlayerInChannel(final Player player, ChannelType channel){
		return channels.containsKey(channel); 
	}
	/**
	 * 添加一个玩家到指定频道
	 * @author xf
	 */
	public void addPlayerToChannel(final Player player, ChannelType channel){
		if(!channels.containsKey(channel)){
			channels.put(channel, new ArrayList<Player>());
		}
		channels.get(channel).add(player);
	}
	
	/**
	 * 删除一个玩家从指定频道
	 * @author xf
	 */
	public void removePlayerFromChannel(final Player player, ChannelType channel){
		if(channels.containsKey(channel)){
			channels.get(channel).remove(player);
		}
	}
	
	/**
	 * 给指定频道群发消息
	 * @author xf
	 */
	public void sendGCMessageToChannel(GCMessage msg, ChannelType channel){
		if(channels.containsKey(channel)){
			List<Player> list = channels.get(channel);
			Iterator<Player> it = list.iterator();
			while(it.hasNext()){
				Player p = it.next();
				if(p.isOnline()){
					p.sendMessage(msg);
				}else{
					it.remove();
				}
			}
		}
	}
	
	/**
	 * 频道类型
	 * @author xf
	 */
	public static enum ChannelType{
		CUSTOM,
		/** 强化装备 **/
		EQUIP_ENHANCE,
		/** 运镖 **/
		ROBBERY,
		
 
	}
}
