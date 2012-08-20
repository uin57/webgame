package com.pwrd.war.logserver;

import org.apache.mina.common.ByteBuffer;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.recognizer.IMessageRecognizer;
import com.pwrd.war.core.msg.MessageParseException;
	import com.pwrd.war.logserver.model.ItemLog;
	import com.pwrd.war.logserver.model.CheatLog;
	import com.pwrd.war.logserver.model.TaskLog;
	import com.pwrd.war.logserver.model.ChatLog;
	import com.pwrd.war.logserver.model.SnapLog;
	import com.pwrd.war.logserver.model.ItemGenLog;
	import com.pwrd.war.logserver.model.OnlineTimeLog;
	import com.pwrd.war.logserver.model.ChargeLog;
	import com.pwrd.war.logserver.model.LevelLog;
	import com.pwrd.war.logserver.model.GmCommandLog;
	import com.pwrd.war.logserver.model.BasicPlayerLog;
	import com.pwrd.war.logserver.model.PetLog;
	import com.pwrd.war.logserver.model.MailLog;
	import com.pwrd.war.logserver.model.GuildLog;
	import com.pwrd.war.logserver.model.PrizeLog;
	import com.pwrd.war.logserver.model.BattleLog;
	import com.pwrd.war.logserver.model.PetExpLog;
	import com.pwrd.war.logserver.model.PetLevelLog;
	import com.pwrd.war.logserver.model.ActivityLog;
	import com.pwrd.war.logserver.model.CurrencyLog;
	import com.pwrd.war.logserver.model.EquipmentLog;
	import com.pwrd.war.logserver.model.FamilyLog;
	import com.pwrd.war.logserver.model.FriendLog;
	import com.pwrd.war.logserver.model.GetItemLog;
	import com.pwrd.war.logserver.model.LevelUpLog;
	import com.pwrd.war.logserver.model.LoginLog;
	import com.pwrd.war.logserver.model.OfficialLog;
	import com.pwrd.war.logserver.model.QuestLog;
	import com.pwrd.war.logserver.model.RepDropLog;
	import com.pwrd.war.logserver.model.RepLog;
	import com.pwrd.war.logserver.model.TowerLog;
	import com.pwrd.war.logserver.model.VipLog;
	import com.pwrd.war.logserver.model.WarcraftLog;
	import com.pwrd.war.logserver.model.XinghunLog;

/**
 * This is an auto generated source,please don't modify it.
 */
public class LogMessageRecognizer implements IMessageRecognizer {


	@Override
	public int recognizePacketLen(final ByteBuffer buff) {
		// 消息头还未读到,返回null
		if (buff.remaining() < IMessage.MIN_MESSAGE_LENGTH) {
			return -1;
		}
		return IMessage.Packet.peekPacketLength(buff);
	}


	public IMessage recognize(ByteBuffer buf) throws MessageParseException {
		/* 长度尚不足 */
		if (buf.remaining() < IMessage.MIN_MESSAGE_LENGTH) {
			return null;
		}

		/* 长度不足实际命令 */
		int len = buf.getShort(); // 预期长度
		if (buf.remaining() < len - 2) {
			return null;
		}

		short type = buf.getShort();
		return createMessage(type);
	}

	public static IMessage createMessage(int type)
			throws MessageParseException {

		switch (type) {
			case MessageType.LOG_ITEM_RECORD: {
				return new ItemLog();
			}
			case MessageType.LOG_CHEAT_RECORD: {
				return new CheatLog();
			}
			case MessageType.LOG_TASK_RECORD: {
				return new TaskLog();
			}
			case MessageType.LOG_CHAT_RECORD: {
				return new ChatLog();
			}
			case MessageType.LOG_SNAP_RECORD: {
				return new SnapLog();
			}
			case MessageType.LOG_ITEMGEN_RECORD: {
				return new ItemGenLog();
			}
			case MessageType.LOG_ONLINETIME_RECORD: {
				return new OnlineTimeLog();
			}
			case MessageType.LOG_CHARGE_RECORD: {
				return new ChargeLog();
			}
			case MessageType.LOG_LEVEL_RECORD: {
				return new LevelLog();
			}
			case MessageType.LOG_GMCOMMAND_RECORD: {
				return new GmCommandLog();
			}
			case MessageType.LOG_BASICPLAYER_RECORD: {
				return new BasicPlayerLog();
			}
			case MessageType.LOG_PET_RECORD: {
				return new PetLog();
			}
			case MessageType.LOG_MAIL_RECORD: {
				return new MailLog();
			}
			case MessageType.LOG_GUILD_RECORD: {
				return new GuildLog();
			}
			case MessageType.LOG_PRIZE_RECORD: {
				return new PrizeLog();
			}
			case MessageType.LOG_BATTLE_RECORD: {
				return new BattleLog();
			}
			case MessageType.LOG_PETEXP_RECORD: {
				return new PetExpLog();
			}
			case MessageType.LOG_PETLEVEL_RECORD: {
				return new PetLevelLog();
			}
			case MessageType.LOG_ACTIVITY_RECORD: {
				return new ActivityLog();
			}
			case MessageType.LOG_CURRENCY_RECORD: {
				return new CurrencyLog();
			}
			case MessageType.LOG_EQUIPMENT_RECORD: {
				return new EquipmentLog();
			}
			case MessageType.LOG_FAMILY_RECORD: {
				return new FamilyLog();
			}
			case MessageType.LOG_FRIEND_RECORD: {
				return new FriendLog();
			}
			case MessageType.LOG_GETITEM_RECORD: {
				return new GetItemLog();
			}
			case MessageType.LOG_LEVELUP_RECORD: {
				return new LevelUpLog();
			}
			case MessageType.LOG_LOGIN_RECORD: {
				return new LoginLog();
			}
			case MessageType.LOG_OFFICIAL_RECORD: {
				return new OfficialLog();
			}
			case MessageType.LOG_QUEST_RECORD: {
				return new QuestLog();
			}
			case MessageType.LOG_REPDROP_RECORD: {
				return new RepDropLog();
			}
			case MessageType.LOG_REP_RECORD: {
				return new RepLog();
			}
			case MessageType.LOG_TOWER_RECORD: {
				return new TowerLog();
			}
			case MessageType.LOG_VIP_RECORD: {
				return new VipLog();
			}
			case MessageType.LOG_WARCRAFT_RECORD: {
				return new WarcraftLog();
			}
			case MessageType.LOG_XINGHUN_RECORD: {
				return new XinghunLog();
			}

		default: {
			// TODO::考虑不要死机，可能要特殊处理
			throw new MessageParseException("Unrecognized message :" + type);
		}
		}

	}

}