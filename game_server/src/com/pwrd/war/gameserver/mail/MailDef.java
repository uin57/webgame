package com.pwrd.war.gameserver.mail;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;

public interface MailDef {
	
	/**
	 * 系统邮件,发件人id为10000
	 */
	public static String SYSTEM_MAIL_SEND_ID = "10000";
	
	/**
	 * 军团邮件,发件人id为20000
	 */
	public static String GUILD_MAIL_SEND_ID = "20000";
	
	/**
	 * 史实邮件，发件人id为30000
	 */
	public static String STORY_MAIL_SEND_ID = "30000";
	
	
	/**
	 * 系统邮件触发原因
	 * 
	 */
	public enum SysMailReasonType implements IndexedEnum{
		
		/** 农田 */
		FARM(101,1,2,3),
		/** 银矿 */
		OIL(102,1,2,3),
		/** 攻击 */
		ATTACK(103,1,2),
		/** 征服 */
		OCCUPY(104,1,2),
		/** 区战 */
		REGION(105,1),
		/** 军团战 */
		GUILDBATTLE(106,1),
		;
		
		public final int reasonId;
		
		public final int[] behaviorIds;
		
		private SysMailReasonType(int reasonId,int... behaviorIds) {
			this.reasonId = reasonId;
			this.behaviorIds = behaviorIds;
		}


		@Override
		public int getIndex() {
			return reasonId;
		}
		
		private static final List<SysMailReasonType> values = IndexedEnumUtil.toIndexes(SysMailReasonType.values());

		public static SysMailReasonType valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
				
	}
	
	
	
	
	
	public enum BoxType implements IndexedEnum{
		
		/** 普通邮件 */
		INBOX(1),
		/** 系统邮件 */
		SYSTEM(2),
		/** 玩家邮件 */
		PRIVATE(3),
		/** 战报邮件 */
		BATTLE(4),
		/** 已保存 */
		SAVE(5),
		/** 已删除 */
		DELETED(6),		
		/** 史实邮件 */
		STORY(7),
		;
		
		private final int index;
		
		private BoxType(int index) {
			this.index = index;
		}


		@Override
		public int getIndex() {
			return index;
		}
		
		
		private static final List<BoxType> values = IndexedEnumUtil.toIndexes(BoxType.values());

		public static BoxType valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
		
	}
	
	
	
	/**
	 * 邮件类型
	 * @author jiliang.lu
	 *
	 */
	public enum MailType implements IndexedEnum{
		
		/** 未知类型 */
		NONE(0, CommonLangConstants_10000.MAIL_TYPE_NONE),
		/** 普通邮件 */
		NORMAL(1, CommonLangConstants_10000.MAIL_TYPE_PRIVATE),
		/** 群体邮件 */
		GROUP(2, CommonLangConstants_10000.MAIL_TYPE_GUILD),
		/** 系统邮件 */
		SYSTEM(4, CommonLangConstants_10000.MAIL_TYPE_SYSTEM),
		/** 战报邮件 */
		BATTLE(8, CommonLangConstants_10000.MAIL_TYPE_BATTLE),
		/** 史实邮件 */
		STORY(16, CommonLangConstants_10000.MAIL_TYPE_STORY),
		;
		
		private final int index;	
		
		private final Integer nameLangKey;
		
		private MailType(int index, Integer nameLangKey) {
			this.index = index;
			this.nameLangKey = nameLangKey;
		}


		@Override
		public int getIndex() {
			return index;
		}
		
		public Integer getNameLangKey() {
			return nameLangKey;
		}
		
		private static final List<MailType> values = IndexedEnumUtil.toIndexes(MailType.values());

		public static MailType valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}

	}
	
	/**
	 * 邮件状态
	 * @author jiliang.lu
	 *
	 */
	public enum MailStatus implements IndexedEnum{
		
		/** 未读 */
		UNREAD(1, CommonLangConstants_10000.MAIL_STATUS_UNREAD),
		/** 已读 */
		READED(2, CommonLangConstants_10000.MAIL_STATUS_READED),
		/** 已保存 */
		SAVED(4, CommonLangConstants_10000.MAIL_STATUS_SAVED),
		/** 已删除 */
		DELETED(8, CommonLangConstants_10000.MAIL_STATUS_DELETED),
		
		;
		
		public static final int MAX_MAIL_MASK = UNREAD.mark | READED.mark | SAVED.mark | DELETED.mark;
		
		public final int mark;
		
		private final Integer nameLangKey;
		
		private MailStatus(int mark, Integer nameLangKey) {
			this.mark = mark;
			this.nameLangKey = nameLangKey;
		}
		
		@Override
		public int getIndex() {
			return mark;
		}
		
		public Integer getNameLangKey() {
			return nameLangKey;
		}
		
		private static final List<MailStatus> values = IndexedEnumUtil.toIndexes(MailStatus.values());

		public static MailStatus valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
		
		/**
		 * 状态是否正常
		 * 
		 * @param mailStatus
		 * @return
		 */
		public static boolean isValidStatus(int mailStatus)
		{
			boolean result = true;
			do
			{			
				if((mailStatus & MAX_MAIL_MASK) == 0)
				{
					result = false;
					break;
				}
							
				if((mailStatus & SAVED.mark) != 0 && (mailStatus & DELETED.mark) != 0)
				{
					result = false;
					break;
				}			
				
			}while(false);
			
			return result;
		}

	}

}
