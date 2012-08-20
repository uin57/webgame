package com.pwrd.war.gameserver.mail.handler;

import java.util.List;

import com.google.common.collect.Lists;
import com.pwrd.war.common.constants.ResultTypes;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.common.model.mail.MailInfo;
import com.pwrd.war.common.service.DirtFilterService.WordCheckType;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.mail.MailBox;
import com.pwrd.war.gameserver.mail.MailDef.BoxType;
import com.pwrd.war.gameserver.mail.MailDef.MailStatus;
import com.pwrd.war.gameserver.mail.MailInstance;
import com.pwrd.war.gameserver.mail.msg.CGDelAllMail;
import com.pwrd.war.gameserver.mail.msg.CGDelMail;
import com.pwrd.war.gameserver.mail.msg.CGMailList;
import com.pwrd.war.gameserver.mail.msg.CGReadMail;
import com.pwrd.war.gameserver.mail.msg.CGSaveMail;
import com.pwrd.war.gameserver.mail.msg.CGSendGuildMail;
import com.pwrd.war.gameserver.mail.msg.CGSendMail;
import com.pwrd.war.gameserver.mail.msg.GCMailList;
import com.pwrd.war.gameserver.mail.msg.GCMailUpdate;
import com.pwrd.war.gameserver.mail.msg.GCSendMail;
import com.pwrd.war.gameserver.mail.msg.MailMessageBuilder;
import com.pwrd.war.gameserver.player.Player;

public class MailMessageHandler {

	public void handleMailList(Player player, CGMailList cgMailList) {
		Human human = player.getHuman();
		if(human == null)
		{
			return;
		}
			
		MailBox mailbox = human.getMailbox();
		if(mailbox == null)
		{
			return;
		}
		
		int boxType = cgMailList.getBoxType();
		BoxType mailBoxType = BoxType.valueOf(boxType);
		if(mailBoxType == null)
		{
			return;
		}
		
		short index = cgMailList.getQueryIndex();
		
		List<MailInstance> mails = null;
	
		switch(mailBoxType)
		{
			case INBOX:
			{
				mails = mailbox.getInbox();
				break;
			}
			case SYSTEM:
			{
				mails = mailbox.getSysbox();
				break;
			}
			case PRIVATE:
			{
				mails = mailbox.getPrivatebox();
				break;
			}
			case BATTLE:
			{
				mails = mailbox.getBattleReportbox();
				break;
			}
			case STORY:
			{
				mails = mailbox.getStoryBox();
				break;
			}
			case SAVE:			
			{
				mails = Lists.newLinkedList(mailbox.getSavebox().iterator());
				break;
			}
			case DELETED:
			{
				mails = Lists.newLinkedList(mailbox.getDelbox().iterator());
				break;
			}
		}
		
		
		if(mails != null)
		{
			List<MailInfo> mailInfos = Lists.newArrayList();
			int _totalSize = mails.size();
			int _startPos = index * Globals.getGameConstants().getMailNumPerPage();
			// 开始位置检查
			if (_startPos < 0 || _startPos >= _totalSize) {
				// 此时返回的列表应该为空，更新客户端的版本
				_startPos = _totalSize;
			}
			int _endPos = (index + 1) *  Globals.getGameConstants().getMailNumPerPage();
			// 结束位置大于当前公会总数，则置为总数
			if (_endPos > _totalSize) {
				_endPos = _totalSize;
			}
			
			// 获取起始位置之间的公会信息
			for (int i = _startPos; i < _endPos; i++) {
				MailInstance _mail = mails.get(i);
				if (_mail == null) {
					continue;
				}
				MailInfo _mailInfo = MailMessageBuilder.createMailInfo(_mail);
				mailInfos.add(_mailInfo);
			}
			
			GCMailList gcMailList = new GCMailList();
			gcMailList.setTotalNums((short)_totalSize);
			gcMailList.setBoxType((short)boxType);
			gcMailList.setQueryIndex(index);
			gcMailList.setMailInfos(mailInfos.toArray(new MailInfo[0]));
			
			player.sendMessage(gcMailList);
		}		
	}

	public void handleReadMail(Player player, CGReadMail cgReadMail) {
		String mailUUID = cgReadMail.getUuid();
		MailBox mailBox = player.getHuman().getMailbox();
		if(mailBox != null)
		{
			MailInstance mailInstance = mailBox.getMailByUUID(mailUUID);
			if(mailInstance != null)
			{
				if(mailInstance.isUnread())
				{
					mailInstance.setMailStatus(MailStatus.READED.mark);					
					GCMailUpdate gcMailUpdate = new GCMailUpdate();
					gcMailUpdate.setMail(MailMessageBuilder.createMailInfo(mailInstance));
					player.getHuman().sendMessage(gcMailUpdate);
				}
			}
		}
	}

	public void handleSaveMail(Player player, CGSaveMail cgSaveMail) {		
		String mailUUID = cgSaveMail.getUuid();
		MailBox mailBox = player.getHuman().getMailbox();
		if(mailBox != null)
		{
			MailInstance mailInstance = mailBox.getMailByUUID(mailUUID);
			if(mailInstance != null)
			{
				//邮件如果已进入删除箱,不能保存
				if(mailInstance.isDeleted())
				{
					return;
				}
				if(mailInstance.isSaved())
				{
					return;
				}
				// 邮件保存箱已满
				if (mailBox.getSavebox().size() >= Globals.getGameConstants().getMailInSaveboxMaxCount()) {
					player.getHuman().sendErrorMessage(Globals.getLangService().read(
							CommonLangConstants_10000.SAVE_MAIL_BOX_IS_FULL));
					return;
				}
				mailInstance.addMailStatus(MailStatus.SAVED.mark);
				mailBox.removeMailFromInbox(mailInstance);
				mailBox.addMailToSavebox(mailInstance);			
			}			
		}
	}	

	public void handleDelMail(Player player, CGDelMail cgDelMail) {
		String mailUUID = cgDelMail.getUuid();
		MailBox mailBox = player.getHuman().getMailbox();
		if(mailBox != null)
		{
			MailInstance mailInstance = mailBox.getMailByUUID(mailUUID);
			if(mailInstance != null)
			{
				if(mailInstance.isDeleted())
				{
					mailBox.removeMailFromDelbox(mailInstance);
					mailInstance.onDelete();
					return;
				}
				mailInstance.removeMailStatus(MailStatus.SAVED.mark);
				mailInstance.addMailStatus(MailStatus.DELETED.mark);
				mailBox.removeMailFromInbox(mailInstance);
				mailBox.removeMailFromSysbox(mailInstance);
				mailBox.removeMailFromPrivatebox(mailInstance);
				mailBox.removeMailFromBattleReportbox(mailInstance);
				mailBox.removeMailFromSavebox(mailInstance);
				mailBox.removeMailFromStorybox(mailInstance);
				mailBox.addMailToDelbox(mailInstance);			
			}			
		}	
	}
	
	public void handleDelAllMail(Player player, CGDelAllMail cgDelAllMail) {
		String[] uuids = cgDelAllMail.getUuidlist();
		MailBox mailBox = player.getHuman().getMailbox();
		if(mailBox != null)
		{
			for(String mailUUID : uuids)
			{
				MailInstance mailInstance = mailBox.getMailByUUID(mailUUID);
				if(mailInstance != null)
				{
					if(mailInstance.isDeleted())
					{
						mailBox.removeMailFromDelbox(mailInstance);
						mailInstance.onDelete();
						return;
					}
					mailInstance.removeMailStatus(MailStatus.SAVED.mark);
					mailInstance.addMailStatus(MailStatus.DELETED.mark);
					mailBox.removeMailFromInbox(mailInstance);
					mailBox.removeMailFromSysbox(mailInstance);
					mailBox.removeMailFromPrivatebox(mailInstance);
					mailBox.removeMailFromBattleReportbox(mailInstance);
					mailBox.removeMailFromSavebox(mailInstance);
					mailBox.removeMailFromStorybox(mailInstance);
					mailBox.addMailToDelbox(mailInstance);			
				}	
			}		
		}		
	}

	public void handleSendMail(final Player player, CGSendMail cgSendMail) {
//		final String recName = cgSendMail.getRecName();
		final String title = cgSendMail.getTitle();
		final String content = cgSendMail.getContent();		
		
		Human human = player.getHuman();
		if(human == null)
		{
			return;
		}
		
//		String _checkInputError = Globals.getDirtFilterService().checkInput(
//				WordCheckType.CHAT_ANNOUNCE_DESC, title,
//				CommonLangConstants_10000.GAME_INPUT_TYPE_MAIL_TITLE,
//				SharedConstants.MIN_MAIL_TITLE_LENGTH_ENG,
//				SharedConstants.MAX_MAIL_TITLE_LENGTH_ENG, false);
//
//		if (_checkInputError != null) {				
//			GCSendMail gcSendMailMsg = new GCSendMail();
//			gcSendMailMsg.setResultCode(ResultTypes.FAIL.val);
//			gcSendMailMsg.setResultMsg(_checkInputError);
//			human.sendMessage(gcSendMailMsg);
//			return;
//		}
//		
//		_checkInputError = Globals.getDirtFilterService().checkInput(
//				WordCheckType.CHAT_ANNOUNCE_DESC, content,
//				CommonLangConstants_10000.GAME_INPUT_TYPE_MAIL_CONTENT,
//				SharedConstants.MIN_MAIL_CONTENT_LENGTH_ENG,
//				SharedConstants.MAX_MAIL_CONTENT_LENGTH_ENG, false);
//		
//		if (_checkInputError != null) {				
//			GCSendMail gcSendMailMsg = new GCSendMail();
//			gcSendMailMsg.setResultCode(ResultTypes.FAIL.val);
//			gcSendMailMsg.setResultMsg(_checkInputError);
//			human.sendMessage(gcSendMailMsg);
//			return;
//		}
		
		if(cgSendMail.getIsGuildMail())
		{
//			if(!human.getGuildManager().isGuildMember())
//			{
//				return ;				
//			}
//			
//			// 军团是否还存在
//			Guild _guild = Globals.getGuildService().getCreatedGuild(human.getGuildId());
//			if (_guild == null || _guild.getState() != GuildState.NORMAL) {
//				return;
//			}
//			
//			// 判断是否有权限
//			GuildMember _self = _guild.getMember(human.getUUID());
//			if (!Globals.getGuildService().checkRankRule(_self.getGuildRank(), GuildPurviewType.SEND_GUILD_MAIL)) {
//				return;
//			}
//			
//			Globals.getMailService().sendGuildMail(_guild, title, content);
//			GCSendMail gcSendMail = new GCSendMail();
//			gcSendMail.setResultCode(ResultTypes.SUCCESS.val);								
//			player.sendMessage(gcSendMail);
			
		}
		else
		{
//			Globals.getHumanService().doQueryRoleUUID(recName, new QueryRoleIdCallback() {
//				
//				@Override
//				public void afterQueryComplete(String roleName, Long roleUUID) {
//					
//					if(roleUUID == null)
//					{
//						GCSendMail gcSendMail = new GCSendMail();
//						gcSendMail.setResultCode(ResultTypes.FAIL.val);
//						gcSendMail.setResultMsg(Globals.getLangService().getSysLangSerivce().read(
//								LangConstants.MAIL_SEND_ERROR_RECID_NOEXIST));
//						player.sendMessage(gcSendMail);
//					}
//					else
//					{
//						Globals.getHumanService().doQueryHumanInfo(roleUUID, new QueryHumanInfoCallback() {
//							
//							@Override
//							public void afterQueryComplete(HumanInfo humanInfo) {
//								Human sender = player.getHuman();
//								int senderAlliance = sender.getAllianceId();
//								int recAlliance = humanInfo.getAllianceId();
//								if(senderAlliance != recAlliance)
//								{
//	
//									GCSendMail gcSendMail = new GCSendMail();
//									gcSendMail.setResultCode(ResultTypes.FAIL.val);
//									gcSendMail.setResultMsg(Globals.getLangService().getSysLangSerivce().read(
//											LangConstants.MAIL_SEND_ERROR_ALLIANCE));
//									player.sendMessage(gcSendMail);								
//								}
//								else
//								{
//									Globals.getMailService().sendMail(sender.getUUID(), sender.getName(), humanInfo.getRoleUUID(), humanInfo.getName(), title, content);
//									GCSendMail gcSendMail = new GCSendMail();
//									gcSendMail.setResultCode(ResultTypes.SUCCESS.val);								
//									player.sendMessage(gcSendMail);	
//									player.sendSystemMessage(LangConstants.MAIL_SEND_SUCCESS_INFO);
//									sender.getQuestDiary().getListener().onSendToOtherMail(sender);
//								}
//							}
//						});
//					}
//				}
//			});
		}
		
	}

	public void handleSendGuildMail(Player player,
			CGSendGuildMail cgSendGuildMail) {
		// TODO Auto-generated method stub
		
	}

}
