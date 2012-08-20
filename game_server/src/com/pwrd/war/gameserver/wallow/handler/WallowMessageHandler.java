package com.pwrd.war.gameserver.wallow.handler;

import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.wallow.msg.CGWallowAddInfo;

public class WallowMessageHandler {

	public void handleWallowAddInfo(Player player,
			CGWallowAddInfo cgWallowAddInfo) {
		
		// 真实姓名
		String name = cgWallowAddInfo.getName();
		// 身份证号
		String idCard = cgWallowAddInfo.getIdCard();
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(idCard)) {
			return;
		}
		if (!Globals.getServerConfig().isTurnOnLocalInterface()) {
			player.sendSystemMessage(CommonLangConstants_10000.LOCAL_TURN_OFF);
			return;
		}
		
		
		
	}

}
