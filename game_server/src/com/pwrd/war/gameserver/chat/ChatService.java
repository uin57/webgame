package com.pwrd.war.gameserver.chat;

import java.util.Collection;

import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.gameserver.chat.msg.CGChatMsg;
import com.pwrd.war.gameserver.chat.msg.GCChatMsg;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.family.model.Family;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.scene.Scene;

/**
 * 处理聊天
 * 
 */
public class ChatService implements InitializeRequired {

	
	@SuppressWarnings("unused")
	private TemplateService templateService;

	public ChatService(TemplateService templateService) {
		this.templateService = templateService;
	}

	@Override
	public void init() {

	}

	/**
	 * 处理私聊
	 * 
	 * @param player
	 *            发送聊天信息的玩家
	 * @param msg
	 */
	public void handlePrivateChat(Player player, CGChatMsg msg) {
		/* 消息接收的玩家ID */
		String _destRoleName = msg.getDestRoleName();
		if ((_destRoleName == null) || 
			(_destRoleName = _destRoleName.trim()).isEmpty()) {
			return;
		}
		if (_destRoleName.equals(player.getHuman().getName())) {
			return;
		}

		// 发送给玩家本身
		GCChatMsg gcChatMsg = new GCChatMsg();
		gcChatMsg.setScope(SharedConstants.CHAT_SCOPE_PRIVATE);
		gcChatMsg.setContent(msg.getContent());
		gcChatMsg.setFromRoleUUID(player.getHuman().getUUID());
		gcChatMsg.setFromRoleName(player.getHuman().getName());
		gcChatMsg.setToRoleName(msg.getDestRoleName());
		gcChatMsg.setToRoleUUID(msg.getDestRoleUUID());
		gcChatMsg.setColor(msg.getColor());
		gcChatMsg.setFromRoleCamp(player.getHuman().getCamp().getCode());
		player.sendMessage(gcChatMsg);

		// 获取目标玩家
		Player destPlayer = Globals.getOnlinePlayerService().getPlayer(_destRoleName);
		if (destPlayer != null) {
			if(destPlayer.getHuman().getFriendManager().isBlack(player.getHuman().getUUID())){
				//黑名单中
				return;
			}
			destPlayer.sendMessage(gcChatMsg);
			//接受到信息
			destPlayer.getHuman().getFriendManager().onChat(player.getRoleUUID(), msg.getContent(), false);
		}
		//发送消息
		player.getHuman().getFriendManager().onChat(msg.getDestRoleUUID(), msg.getContent(), true);
	}

	/**
	 * 处理世界聊天
	 * 
	 * @param player
	 * @param msg
	 */
	public void handleWorldChat(Player player, CGChatMsg msg) {		
		Human human = player.getHuman();
		
		if(human == null) {
			return;
		}
		
//		// 获取世界聊天最小级别
//		int worldChatMinLevel = Globals
//			.getGameConstants().getWorldChatMinLevel();
//		
//		// 玩家小于世界聊天最小允许级别
//		if (human.getLevel() < worldChatMinLevel) {
//			player.sendSystemMessage(
//				langManager.readSysLang(LangConstants.CHAT_WORLD_MIN_LEVEL, 
//				worldChatMinLevel));
//			return;
//		}

//		// 获取世界聊天所需钻石数量
//		int worldChatNeedDiamond = Globals
//			.getServerConfig().getWorldChatNeedDiamond();
//
//		if (!human.hasEnoughMoney(
//			worldChatNeedDiamond, Currency.GOLD, null, true)) {
//			// 如果所需钻石不足, 
//			// 则直接退出
//			return;
//		}
//		
//		// 扣除玩家钻石
//		human.costMoney(worldChatNeedDiamond, Currency.GOLD, null, false, -1, MoneyLogReason.WORLD_CHAT, "", 0);
//		
		// 向玩家所在的GS发送消息
		GCChatMsg gcChatMsg = new GCChatMsg();
		gcChatMsg.setScope(SharedConstants.CHAT_SCOPE_WORLD);
		gcChatMsg.setContent(msg.getContent());
		gcChatMsg.setFromRoleName(human.getName());
		gcChatMsg.setFromRoleUUID(human.getUUID());
		gcChatMsg.setColor(msg.getColor());
		gcChatMsg.setFromRoleCamp(player.getHuman().getCamp().getCode());
		// 获取本 GS 的所有在线玩家
		OnlinePlayerService playerService = Globals.getOnlinePlayerService();
		playerService.broadcastMessage(gcChatMsg);
		
//		human.getQuestDiary().getListener().onChatInWorldChannel(human);
	}

	/**
	 * 向附近玩家发送聊天消息
	 * 
	 * @param player
	 * @param msg
	 */
	public void handleNearChat(Player player, CGChatMsg msg) {
		if (player == null) {
			return;
		}

		if (msg == null) {
			return;
		}

		// 获取玩家角色
		Human human = player.getHuman();

		if (human == null) {
			return;
		}
		GCChatMsg gcChatMsg = new GCChatMsg();
		gcChatMsg.setScope(SharedConstants.CHAT_SCOPE_NEAR);
		gcChatMsg.setContent(msg.getContent());
		gcChatMsg.setFromRoleName(human.getName());
		gcChatMsg.setFromRoleUUID(human.getUUID());
		gcChatMsg.setColor(msg.getColor());
		gcChatMsg.setFromRoleCamp(player.getHuman().getCamp().getCode());
		//场景
		Scene scene = human.getScene();
		scene.getPlayerManager().sendGCMessage(gcChatMsg, null);
		
//		// 获取本 GS 的所有在线玩家
//		OnlinePlayerService olserv = Globals.getOnlinePlayerService();
//
//		// 向玩家所在的GS发送消息
//		GCChatMsg gcChatMsg = new GCChatMsg();
//		gcChatMsg.setScope(SharedConstants.CHAT_SCOPE_NEAR);
//		gcChatMsg.setContent(msg.getContent());
//		gcChatMsg.setFromRoleName(human.getName());
//		gcChatMsg.setFromRoleUUID(human.getUUID());
//
//		for (Long humanUUId : humanUUIdList) {
//			// 获取在线玩家
//			Player olplayer = olserv.getPlayer(humanUUId);
//
//			if (olplayer != null) {
//				olplayer.sendMessage(gcChatMsg);
//			}
//		}
	}



	/**
	 * 向区域内玩家发送聊天信息
	 * 
	 * @param player
	 * @param msg
	 * 
	 */
	public void handleRegionChat(Player player, CGChatMsg msg) {
		// 获取玩家角色
		Human human = player.getHuman();

		if (human == null) {
			return;
		}

		
	}
	
	/**
	 * 向家族内玩家发送聊天信息
	 * 
	 * @param player
	 * @param msg
	 */
	public void handleFamilyChat(Player player,CGChatMsg msg){
		if (player == null) {
			return;
		}
		
		if (msg == null) {
			return;
		}
		
		// 获取玩家角色
		Human human = player.getHuman();

		if (human == null) {
			return;
		}
		
		Family family = human.getFamily();
		if(family == null){
			player.sendSystemMessage(Globals.getLangService().read(CommonLangConstants_10000.NOT_HAVE_FAMILY));
			return;
		}
		GCChatMsg gcChatMsg = new GCChatMsg();
		gcChatMsg.setScope(SharedConstants.CHAT_SCOPE_FAMILY);
		gcChatMsg.setContent(msg.getContent());
		gcChatMsg.setFromRoleName(human.getName());
		gcChatMsg.setFromRoleUUID(human.getUUID());
		gcChatMsg.setColor(msg.getColor());
		gcChatMsg.setFromRoleCamp(player.getHuman().getCamp().getCode());
		family.broadcastMessage(gcChatMsg);
	}
	
	/**
	 * 向队伍发送聊天消息
	 * @param player
	 * @param msg
	 */
	/**
	public void handleTeamChat(Player player,CGChatMsg msg){
		if (player == null) {
			return;
		}
		
		if (msg == null) {
			return;
		}
		
		// 获取玩家角色
		Human human = player.getHuman();

		if (human == null) {
			return;
		}
		TeamInfo teamInfo = human.getTeamInfo();
		if(teamInfo == null){
			return;
		}
		GCChatMsg gcChatMsg = new GCChatMsg();
		gcChatMsg.setScope(SharedConstants.CHAT_SCOPE_TEAM);
		gcChatMsg.setContent(msg.getContent());
		gcChatMsg.setFromRoleName(human.getName());
		gcChatMsg.setFromRoleUUID(human.getUUID());
		gcChatMsg.setColor(msg.getColor());
		teamInfo.broadcastMessage(gcChatMsg);
	}
	**/

	public void handleGuildChat(Player player, CGChatMsg msg) {
		if (player == null) {
			return;
		}
		
		if (msg == null) {
			return;
		}
		
		// 获取玩家角色
		Human human = player.getHuman();

		if (human == null) {
			return;
		}
		
//		if(!human.getGuildManager().isGuildMember())
//		{
//			return ;				
//		}
		
//		// 获取玩家的公会
//		Guild _guild = Globals.getGuildService().getCreatedGuild(human.getGuildId());
//		if (_guild == null) {
//			return;
//		}
//
//		// 向玩家所在的GS发送消息
//		GCChatMsg gcChatMsg = new GCChatMsg();
//		gcChatMsg.setScope(SharedConstants.CHAT_SCOPE_GUILD);
//		gcChatMsg.setContent(msg.getContent());
//		gcChatMsg.setFromRoleName(human.getName());
//		gcChatMsg.setFromRoleUUID(human.getUUID());
//		
//		// 向所有公会成员发送消息
//		for (GuildMember member : _guild.getMemberList()) {
//			if (member == null) {
//				continue;
//			}
//
//			Player _player = Globals.getOnlinePlayerService().getPlayer(member.getRoleId());
//			if (_player != null) {				
//				Human _human = _player.getHuman();
//				if(_human != null)
//				{
//					msg.setDestRoleName(_human.getName());
//					msg.setDestRoleUUID(_human.getUUID());
//					_player.sendMessage(gcChatMsg);
//				}
//			}
//		}
	}

	/**
	 * 喇叭广播聊天消息
	 * 
	 * @param player
	 * @param msg
	 */
	public void handleTrumpetChat(Player player, CGChatMsg msg) {
	}

	/**
	 * 向同一阵营的玩家发送聊天消息
	 * 
	 * @param player
	 * @param msg
	 */
	public void handleCampChat(Player player, CGChatMsg msg) {
		if (player == null) {
			return;
		}

		if (msg == null) {
			return;
		}

		// 获取玩家角色
		Human human = player.getHuman();

		if (human == null) {
			return;
		}
		// 向玩家所在的GS发送消息
		GCChatMsg gcChatMsg = new GCChatMsg();
		gcChatMsg.setScope(SharedConstants.CHAT_SCOPE_CAMP);
		gcChatMsg.setContent(msg.getContent());
		gcChatMsg.setFromRoleName(human.getName());
		gcChatMsg.setFromRoleUUID(human.getUUID());
		gcChatMsg.setColor(msg.getColor());
		gcChatMsg.setFromRoleCamp(human.getCamp().getCode());
		// 获取本 GS 的所有在线玩家
		OnlinePlayerService olserv = Globals.getOnlinePlayerService();
		// 获取附近的玩家角色 Id 列表
		Collection<String> humanUUIdList = olserv.getCampOnlinePlayers(player.getHuman().getCamp());
//		Collection<String> humanUUIdList = olserv.getAllOnlinePlayerRoleUUIDs();

		

		for (String humanUUId : humanUUIdList) {
			olserv.getPlayerById(humanUUId).sendMessage(gcChatMsg);
//			// 获取在线玩家
//			Player olplayer = olserv.getPlayerById(humanUUId);
//
//			if (olplayer == null) {
//				continue;
//			}
//
//			// 获取在线角色
//			Human olhuman = olplayer.getHuman();
//
//			if (olhuman == null) {
//				continue;
//			}
//
//			if (olhuman.getPropertyManager().getCamp()!= human.getPropertyManager().getCamp()) {
//				continue;
//			}

//			olhuman.sendMessage(gcChatMsg);
		}
	}
}
