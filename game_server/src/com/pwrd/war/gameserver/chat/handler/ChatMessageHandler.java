package com.pwrd.war.gameserver.chat.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import com.pwrd.war.common.LogReasons;
import com.pwrd.war.common.ModuleMessageHandler;
import com.pwrd.war.common.constants.GameConstants;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.command.ICommand;
import com.pwrd.war.core.command.impl.SysCmd;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.chat.ChatConstants;
import com.pwrd.war.gameserver.chat.WordFilterService;
import com.pwrd.war.gameserver.chat.msg.CGChatMsg;
import com.pwrd.war.gameserver.command.ClientAdminCmdProcessor;
import com.pwrd.war.gameserver.command.impl.ActivityCmd;
import com.pwrd.war.gameserver.command.impl.AddChargeCmd;
import com.pwrd.war.gameserver.command.impl.AddGrowCmd;
import com.pwrd.war.gameserver.command.impl.AddItemCmd;
import com.pwrd.war.gameserver.command.impl.AddLevelCmd;
import com.pwrd.war.gameserver.command.impl.AddMoneyCmd;
import com.pwrd.war.gameserver.command.impl.AddPetCmd;
import com.pwrd.war.gameserver.command.impl.AddPetLevelCmd;
import com.pwrd.war.gameserver.command.impl.AddPetToForm;
import com.pwrd.war.gameserver.command.impl.AddSkillCmd;
import com.pwrd.war.gameserver.command.impl.AddXinghunCmd;
import com.pwrd.war.gameserver.command.impl.BuffCmd;
import com.pwrd.war.gameserver.command.impl.ClearDailyQuestCountCmd;
import com.pwrd.war.gameserver.command.impl.ClearFoodCmd;
import com.pwrd.war.gameserver.command.impl.ClearItemCmd;
import com.pwrd.war.gameserver.command.impl.ClearMoneyCmd;
import com.pwrd.war.gameserver.command.impl.EditPlayerCmd;
import com.pwrd.war.gameserver.command.impl.FinishDailyQuestDestCmd;
import com.pwrd.war.gameserver.command.impl.FinishQuestDestCmd;
import com.pwrd.war.gameserver.command.impl.GiftBagCmd;
import com.pwrd.war.gameserver.command.impl.GiveDailyQuestCmd;
import com.pwrd.war.gameserver.command.impl.GiveMoneyCmd;
import com.pwrd.war.gameserver.command.impl.KillMonsterCmd;
import com.pwrd.war.gameserver.command.impl.MoveCmd;
import com.pwrd.war.gameserver.command.impl.NoticeCmd;
import com.pwrd.war.gameserver.command.impl.OpenFunctionCmd;
import com.pwrd.war.gameserver.command.impl.PromptButtonCmd;
import com.pwrd.war.gameserver.command.impl.ReloadCmd;
import com.pwrd.war.gameserver.command.impl.RemovePetCmd;
import com.pwrd.war.gameserver.command.impl.SendNoticeCmd;
import com.pwrd.war.gameserver.command.impl.SetItemFreezeTimeCmd;
import com.pwrd.war.gameserver.command.impl.SetTreeExpCmd;
import com.pwrd.war.gameserver.command.impl.TeamCmd;
import com.pwrd.war.gameserver.command.impl.TreeShakeCmd;
import com.pwrd.war.gameserver.command.impl.UseItemCmd;
import com.pwrd.war.gameserver.command.impl.WarcraftCmd;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.PlayerLangConstants_30000;
import com.pwrd.war.gameserver.player.Player;

/**
 * 聊天消息处理器
 * 
 */
public class ChatMessageHandler implements ModuleMessageHandler {

	private WordFilterService chatInfoFilter;
	private ClientAdminCmdProcessor<ISession> cmdProcessor;
	/** 聊天信息中的链接信息模式 */
	private static final Pattern LINK_PATTERN = Pattern
			.compile("\\[l [0-9a-z]+|\\[.*?\\]|[0-9]|[0-9]+ \\]/g");

	private Logger logger = Loggers.chatLogger;

	public ChatMessageHandler() {
		chatInfoFilter = Globals.getWordFilterService();
		cmdProcessor = new ClientAdminCmdProcessor<ISession>();
		registerCmd();
	}

	private void registerCmd() {
		cmdProcessor.registerCommand(new GiveMoneyCmd());
		cmdProcessor.registerCommand(new FinishQuestDestCmd());
		cmdProcessor.registerCommand(new FinishDailyQuestDestCmd());
		cmdProcessor.registerCommand(new NoticeCmd());
		cmdProcessor.registerCommand(new ClearItemCmd());
		cmdProcessor.registerCommand(new ClearMoneyCmd());
		cmdProcessor.registerCommand(new ClearFoodCmd());
		cmdProcessor.registerCommand(new ReloadCmd());
		cmdProcessor.registerCommand(new SetItemFreezeTimeCmd());
		cmdProcessor.registerCommand(new GiveDailyQuestCmd());
		cmdProcessor.registerCommand(new ClearDailyQuestCountCmd());
		cmdProcessor.registerCommand(new AddLevelCmd());
		cmdProcessor.registerCommand(new AddItemCmd());
		cmdProcessor.registerCommand(new AddSkillCmd());
		cmdProcessor.registerCommand(new AddPetCmd());
		cmdProcessor.registerCommand(new AddPetLevelCmd());
		cmdProcessor.registerCommand(new RemovePetCmd());
		cmdProcessor.registerCommand(new TeamCmd());
		cmdProcessor.registerCommand(new UseItemCmd());
		cmdProcessor.registerCommand(new AddMoneyCmd());
		cmdProcessor.registerCommand(new MoveCmd());
		cmdProcessor.registerCommand(new AddPetToForm());
		cmdProcessor.registerCommand(new AddChargeCmd());
		cmdProcessor.registerCommand(new ActivityCmd());
		cmdProcessor.registerCommand(new EditPlayerCmd());
		cmdProcessor.registerCommand((ICommand)(new SysCmd()));
		cmdProcessor.registerCommand(new GiftBagCmd());
		cmdProcessor.registerCommand(new BuffCmd());
		cmdProcessor.registerCommand(new SendNoticeCmd());
		cmdProcessor.registerCommand(new WarcraftCmd());
		cmdProcessor.registerCommand(new AddXinghunCmd());
		cmdProcessor.registerCommand(new AddGrowCmd());
		cmdProcessor.registerCommand(new OpenFunctionCmd());
		cmdProcessor.registerCommand(new TreeShakeCmd());
		cmdProcessor.registerCommand(new KillMonsterCmd());
		cmdProcessor.registerCommand(new PromptButtonCmd());
		cmdProcessor.registerCommand(new SetTreeExpCmd());
		
	}
	
	/**
	 * 处理聊天消息
	 * 
	 * @param player
	 * @param msg
	 */
	public void handleChatMsg(Player player, CGChatMsg msg) {
		String _msgContent = msg.getContent();
		if (null == _msgContent
				|| (_msgContent = _msgContent.trim()).length() == 0) {
			return;
		}

		// DEBUG命令
		if (player.getPermission() == SharedConstants.ACCOUNT_ROLE_GM) {
			if (_msgContent.startsWith(ICommand.CMD_PREFIX)) {// 命令方式
				if (logger.isDebugEnabled()) {
					logger.debug("Client message :" + _msgContent
							+ " is command!");
				}

				if (this.cmdProcessor != null) {
					this.cmdProcessor.execute(player.getSession(), _msgContent);

					// 获取空格字符索引
					int spaceCharIndex = _msgContent.indexOf(" ");

					String cmdName = "";
					String cmdParams = "";
					
					if (spaceCharIndex == -1) {
						cmdName = _msgContent;
					} else {
						// 命令名称和命令参数
						cmdName = _msgContent.substring(0, spaceCharIndex);
						cmdParams = _msgContent.substring(spaceCharIndex);
					}
					
					try {
						// 记录 GM 命令日志
						Globals.getLogService().sendGmCommandLog(
							player.getHuman(), 
							LogReasons.GmCommandLogReason.REASON_VALID_USE_GMCMD, 
							_msgContent, 
							player.getHuman().getName(), 
							player.getClientIp(), 
							cmdName, 
							null, 
							cmdParams, 
							null);
					} catch (Exception ex) {
						Loggers.gmcmdLogger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(), "记录角色基本日志时出错"), ex);
					}
				}
				return;
			}
		}
		
//		// 判断是不是私聊
//		String[] privateChat = null;
//		if (_msgContent.startsWith(ChatConstants.PRIVATE_CHAT_PRFIX)) {
//			privateChat = parsePrivateChat(_msgContent);
//			if (null == privateChat) {
//				return;
//			}
//			_msgContent = privateChat[1];
//		}
//
//		int scope = msg.getScope();
//		if (privateChat != null) {
//			scope = SharedConstants.CHAT_SCOPE_PRIVATE;
//		}

//		// 检查聊天的时间间隔
//		if (Globals.getTimeService().now() - player.getLastChatTime(scope) < getSnapTime(scope)) {
//			if (scope == SharedConstants.CHAT_SCOPE_WORLD) {
//				String channel = Globals.getLangService().readSysLang(
//						LangConstants.CHAT_WORLD_CHANNEL);
//				player.getHuman().sendErrorMessage(
//						LangConstants.CHAT_WORLD_TOO_FAST, channel,
//						Globals.getGameConstants().getWorldChat());
//			} else {
//				player.getHuman().sendErrorMessage(LangConstants.CHAT_TOO_FAST);
//			}
//			return;
//		}

		/** 聊天信息长度需要控制在允许范围内 */
		int _permitLen = getMaxContentLen(_msgContent);
		if (_permitLen < _msgContent.length()) {
			if (logger.isDebugEnabled()) {
				logger.debug("[Chat message length is ： "
						+ _msgContent.length() + ", more than " + _permitLen
						+ " characters , delete over info!]");
			}
			_msgContent = _msgContent.substring(0, _permitLen);
		}

//		// 记录过滤之前的话
//		String _srcContent = _msgContent;

//		/* 过滤聊天中的不良信息 */
//		_msgContent = chatInfoFilter.filterHtmlTag(_msgContent);
//		boolean hasDirtyWords = chatInfoFilter.containKeywords(_msgContent);
//		if (hasDirtyWords) {
//			// 记录玩家发表不良信息的日志
//			try {
//				Globals.getLogService().sendChatLog(player.getHuman(),
//						LogReasons.ChatLogReason.REASON_CHAT_DIRTY_WORD,
//						privateChat == null ? "" : privateChat[0], scope, privateChat == null ? "" : privateChat[0],
//						_srcContent);
//			} catch (Exception ex) {
//				logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
//						String
//								.format("记录玩家聊天时发送不良信息的日志时出现错误！scope = %d",
//										scope)), ex);
//			}
//			_msgContent = chatInfoFilter.filter(_msgContent);
//		}

		if (_msgContent.isEmpty()) {
			return;
		}
		msg.setContent(_msgContent);

//		if (privateChat != null) {
//			msg.setScope(SharedConstants.CHAT_SCOPE_PRIVATE);
//			msg.setDestRoleName(privateChat[0]);
//		}

//		// 如果是私聊, 
//		// 需要判断目标玩家名称是否含有不良信息
//		if (msg.getScope() == SharedConstants.CHAT_SCOPE_PRIVATE) {
//			// 是否为含有不良信息的玩家名称?
//			boolean isDirtyName = chatInfoFilter.containKeywords(msg.getDestRoleName());
//
//			if (isDirtyName) {
//				return;
//			}
//		}

		sendChatMsg(player, msg);
//		player.setLastChatTime(scope, Globals.getTimeService().now());

//		// 记录玩家所说的话，如果包含脏话就不要记了，前面已经记过了
//		try {
//			if (!hasDirtyWords) {
//				Globals.getLogService().sendChatLog(player.getHuman(),
//						LogReasons.ChatLogReason.REASON_CHAT_COMMON,
//						privateChat == null ? "" : privateChat[0], scope, privateChat == null ? "" : privateChat[0],
//						_srcContent);
//			}
//		} catch (Exception ex) {
//			logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(), String
//					.format("记录玩家聊天时日志出现错误！scope = %d", scope)), ex);
//		}

	}

	/**
	 * 向玩家发送消息
	 * 
	 * @param player
	 *            发送消息的玩家
	 * @param msg
	 *            消息
	 */
	private void sendChatMsg(Player player, CGChatMsg msg) {
		final int _chatScope = msg.getScope();
		switch (_chatScope) {
		case SharedConstants.CHAT_SCOPE_PRIVATE:
			Globals.getChatService().handlePrivateChat(player, msg);
			break;
		case SharedConstants.CHAT_SCOPE_NEAR:
			Globals.getChatService().handleNearChat(player, msg);
			break;
		case SharedConstants.CHAT_SCOPE_WORLD:
			if(!player.isCanChat()){
				player.sendErrorPromptMessage(PlayerLangConstants_30000.CANNOT_CHAT);
				return;
			}
			Globals.getChatService().handleWorldChat(player, msg);
			break;
		case SharedConstants.CHAT_SCOPE_TRUMPET:
			Globals.getChatService().handleTrumpetChat(player, msg);
			break;
		case SharedConstants.CHAT_SCOPE_CAMP:
			Globals.getChatService().handleCampChat(player, msg);
			break;
		case SharedConstants.CHAT_SCOPE_FAMILY:
			Globals.getChatService().handleFamilyChat(player, msg);
			break;
		default:
			break;
		}
	}

	private long getSnapTime(int scope) {
		GameConstants gameConstants = Globals.getGameConstants();
		switch (scope) {
			case SharedConstants.CHAT_SCOPE_PRIVATE:
				return gameConstants.getPrivateChat() * TimeUtils.SECOND;
			case SharedConstants.CHAT_SCOPE_NEAR:
				return gameConstants.getNearChat() * TimeUtils.SECOND;
			case SharedConstants.CHAT_SCOPE_WORLD:
				return gameConstants.getWorldChat() * TimeUtils.SECOND;
			case SharedConstants.CHAT_SCOPE_TRUMPET:
				return gameConstants.getTrumpetChat() * TimeUtils.SECOND;
			default:
				return gameConstants.getGeneralChat() * TimeUtils.SECOND;
		}
	}

	/**
	 * 得到聊天允许的最大长度
	 * 
	 * @param content
	 * @return
	 */
	public static int getMaxContentLen(String content) {
		Matcher _matcher = LINK_PATTERN.matcher(content);
		if (_matcher.find()) {
			return ChatConstants.CHAT_LINK_MSG_MAX_LENGTH;
		}
		return ChatConstants.CHAT_NOLINK_MSG_MAX_LENGTH;
	}

	/**
	 * 从私聊的内容中解析出聊天的目标和内容
	 * 
	 * @param chatContent
	 * @return null,格式错误;String[0] 聊天的目标,String[1] 聊天的内容
	 */
	private static String[] parsePrivateChat(final String chatContent) {
		// 检查命令的前缀
		final String _content = chatContent
				.startsWith(ChatConstants.PRIVATE_CHAT_PRFIX) ? chatContent
				.substring(ChatConstants.PRIVATE_CHAT_PRFIX.length()) : null;
		if (_content == null) {
			return null;
		}

		final int _size = _content.length();
		int _index = -1;
		for (int i = 0; i < _size; i++) {
			final char _c = _content.charAt(i);
			if (_c == ' ' || _c == '　') {
				// 当遇到半角空格或者全角空格时,私聊的第一段已经解析到了
				_index = i;
				break;
			}
		}

		if (_index < 0) {
			// 未找到私聊的目标名称
			return null;
		}

		final String _toName = _content.substring(0, _index);

		// 继续忽略所有的前导空格
		for (int i = _index + 1; i < _size; i++) {
			final char _c = _content.charAt(i);
			if (_c != ' ' && _c != '　') {
				_index = i;
				break;
			}
		}

		final String _toContent = _content.substring(
				Math.min(_index, _content.length())).trim();

		if (_toName.isEmpty()) {
			return null;
		}
		if (_toContent.isEmpty()) {
			return null;
		}
		return new String[] { _toName, _toContent };
	}


}

