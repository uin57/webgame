package com.pwrd.war.gameserver.command.impl;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.behavior.BehaviorTypeEnum;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class ClearDailyQuestCountCmd implements IAdminCommand<ISession> {
	@Override
	public void execute(ISession sess, String[] cmds) {
		if (!(sess instanceof GameClientSession)) {
			return;
		}

		Player player = ((GameClientSession)sess).getPlayer();
		
		if (player == null) {
			return;
		}

		// 获取当前玩家角色
		Human currHuman = player.getHuman();

		if (currHuman == null) {
			return;
		}

		// 清除日常任务次数
		currHuman.getBehaviorManager().gmClear(BehaviorTypeEnum.FINISH_DAILY_QUEST);
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_CLEAR_DAILY_QUEST_COUNT;
	}
}
