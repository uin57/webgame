package com.pwrd.war.gameserver.qiecuo.handler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.fight.field.FightFieldFactory;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.OpenFunction;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.qiecuo.msg.CGQiecuoAnswerMessage;
import com.pwrd.war.gameserver.qiecuo.msg.CGQiecuoRequestMessage;
import com.pwrd.war.gameserver.qiecuo.msg.GCQiecuoAnswerMessage;
import com.pwrd.war.gameserver.qiecuo.msg.GCQiecuoRequestMessage;

/**
 * 各模块通用消息处理器
 * 
 * @author jiliang.lu
 * @since 2010.12.16
 * 
 */
public class QieCuoMessageHandler {
	private static final int Level = 15;

	private ScheduledExecutorService schedule = Executors
			.newScheduledThreadPool(2);

	private ScheduledFuture future = null;

	/**
	 */
	public void handleQiecuoAnswerMessage(Player player,
			CGQiecuoAnswerMessage message) {
		if (future != null) {
			future.cancel(false);
		}
		Player sponsorPlayer = Globals.getOnlinePlayerService().getPlayerById(
				message.getSponsorPlayerUUID());
		if (sponsorPlayer != null) {
			if (sponsorPlayer.getHuman().getGamingState() == GamingStateIndex.IN_BATTLE
					.getValue()
					|| sponsorPlayer.getHuman().getGamingState() == GamingStateIndex.IS_DEAD
							.getValue()
					|| sponsorPlayer.getHuman().getGamingState() == GamingStateIndex.IN_PRACTISE
							.getValue()
					|| sponsorPlayer.getHuman().getScene() != player.getHuman()
							.getScene()) {
				player.sendSystemMessage("切磋邀请失效.");
				player.getHuman().setIsRequestQiecuo(new AtomicBoolean(false));
				player.getHuman().setQiecuoPlayerUUID(null);
			} else {
				if (message.getResult() == 0) {
					try {
						FightFieldFactory.doExchangeField(sponsorPlayer, player.getRoleUUID());
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (message.getResult() == 1) {
					sponsorPlayer.sendMessage(new GCQiecuoAnswerMessage(message
							.getSponsorPlayerUUID(), message
							.getSponsorPlayerName(), message
							.getBearPlayerUUID(), message.getBearPlayerName(),
							message.getResult()));
				}
				sponsorPlayer.getHuman().setIsRequestQiecuo(
						new AtomicBoolean(false));
				sponsorPlayer.getHuman().setQiecuoPlayerUUID(null);
				player.getHuman().setIsRequestQiecuo(new AtomicBoolean(false));
				player.getHuman().setQiecuoPlayerUUID(null);
			}
		} else {
			// player.sendMessage(new GCQiecuoAnswerMessage(message
			// .getSponsorPlayerUUID(),message.getSponsorPlayerName(),
			// message.getBearPlayerUUID(),
			// message.getBearPlayerName(),
			// 1));
			player.sendSystemMessage("切磋邀请失效.");
			player.getHuman().setIsRequestQiecuo(new AtomicBoolean(false));
			player.getHuman().setQiecuoPlayerUUID(null);
		}

	}

	public void handleQiecuoRequestMessage(final Player player,
			final CGQiecuoRequestMessage message) {
		if (player.getRoleUUID().equals(message.getSponsorPlayerUUID())) {
			final Human sponsorHuman = player.getHuman();

			if (sponsorHuman.isInTeam()) {
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getSponsorPlayerName()
						+ "在队伍中。");
				return;
			}
			if (sponsorHuman.getGamingState() == GamingStateIndex.IN_BATTLE
					.getValue()) {
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getSponsorPlayerName()
						+ "正在战斗中。");
				return;
			}
			if (sponsorHuman.getLevel() < Level) {
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getSponsorPlayerName()
						+ "等级小于" + Level + "级。");
				return;
			}
//			if (!OpenFunction.canQieCuo(sponsorHuman.getPropertyManager()
//					.getOpenFunction())) {
			if(!OpenFunction.isopen(sponsorHuman.getPropertyManager().getOpenFunc(), OpenFunction.qiecuo)){
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getSponsorPlayerName()
						+ "尚未开启此功能。");
				return;
			}
			Player bearPlayer = Globals.getOnlinePlayerService().getPlayerById(
					message.getBearPlayerUUID());
			if (bearPlayer == null) {
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getBearPlayerName()
						+ "已经下线。");
				return;
			}
			final Human bearHuman = bearPlayer.getHuman();
			if (sponsorHuman.getScene() != bearHuman.getScene()) {
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getBearPlayerName()
						+ "不在当前地图。");
				return;
			}
			if (bearHuman.getGamingState() == GamingStateIndex.IS_DEAD
					.getValue()) {
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getBearPlayerName()
						+ "已死亡。");
				return;
			}
			if (bearHuman.isInTeam()) {
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getBearPlayerName()
						+ "在队伍中。");
				return;
			}
			if (bearHuman.getLevel() < Level) {
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getBearPlayerName()
						+ "未达到" + Level + "级。");
				return;
			}
			if (!OpenFunction.isopen(bearHuman.getPropertyManager().getOpenFunc(), OpenFunction.qiecuo)) {
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getBearPlayerName()
						+ "尚未开启此功能。");
				return;
			}

			if (bearHuman.getIsRequestQiecuo().get()) {
				if (!sponsorHuman.getUUID().equals(
						bearHuman.getQiecuoPlayerUUID())) {
					sponsorHuman.getPlayer().sendRightMessage(message.getBearPlayerName()
							+ "对方已受到其他玩家的切磋邀请。");
				} else {
					sponsorHuman.getPlayer().sendRightMessage(message.getBearPlayerName()
							+ "已发送切磋邀请，等待对方回应");
				}
				return;
			}
			if (bearHuman.getGamingState() == GamingStateIndex.IN_BATTLE
					.getValue() ||
					bearHuman.getPropertyManager().getPropertyNormal().getLastFightTime() > Globals.getTimeService().now()) {
				sponsorHuman.getPlayer().sendErrorPromptMessage(message.getBearPlayerName()
						+ "正在战斗中。");
				return;
			}

			bearHuman.setIsRequestQiecuo(new AtomicBoolean(true));
			bearHuman.setQiecuoPlayerUUID(sponsorHuman.getUUID());
			sponsorHuman.setIsRequestQiecuo(new AtomicBoolean(true));
			sponsorHuman.setQiecuoPlayerUUID(bearHuman.getUUID());
			bearHuman.sendMessage(new GCQiecuoRequestMessage(message
					.getSponsorPlayerUUID(), message.getSponsorPlayerName(),
					message.getBearPlayerUUID(), message.getBearPlayerName()));

			future = schedule.schedule(new Runnable() {

				@Override
				public void run() {
					bearHuman.setIsRequestQiecuo(new AtomicBoolean(false));
					bearHuman.setQiecuoPlayerUUID(null);
					sponsorHuman.setIsRequestQiecuo(new AtomicBoolean(false));
					sponsorHuman.setQiecuoPlayerUUID(null);
					player.sendMessage(new GCQiecuoAnswerMessage(message
							.getSponsorPlayerUUID(), message
							.getSponsorPlayerName(), message
							.getBearPlayerUUID(), message.getBearPlayerName(),
							1));
				}
			}, 30, TimeUnit.SECONDS);
		}

	}

}
