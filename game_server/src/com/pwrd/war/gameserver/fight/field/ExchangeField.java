package com.pwrd.war.gameserver.fight.field;

import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.qiecuo.msg.GCQiecuoResultMessage;

public class ExchangeField extends PvPField {
	/**
	 * 构造方法，直接调用父级构造方法
	 * @param attHuman
	 * @param defHuman
	 * @param sendDefMsg
	 */
	public ExchangeField(Human attHuman, Human defHuman, boolean sendDefMsg) {
		//构造双方对象
		super(attHuman, defHuman, true, sendDefMsg, false, false);
	}

	@Override
	protected void pvmEndImpl(boolean attWin, long timeCost) {
		//创建切磋结果消息发送给进攻方
		GCQiecuoResultMessage gcQcMessage = new GCQiecuoResultMessage();
		gcQcMessage.setBattleSn("");
		gcQcMessage.setBearPlayerUUID(defHuman.getUUID());
		gcQcMessage.setBearPlayerName(defHuman.getName());
		gcQcMessage.setSponsorPlayerUUID(attHuman.getUUID());
		gcQcMessage.setSponsorPlayerName(attHuman.getName());
		gcQcMessage.setIsWin(attWin);
		attHuman.sendMessage(gcQcMessage);
		
		//TODO 发送消息给防守方
		if (sendDefMsg) {
			defHuman.sendMessage(gcQcMessage);
		}
	}

}
