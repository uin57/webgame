package com.pwrd.war.gameserver.fight.handler;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.StoryShowEvent;
import com.pwrd.war.gameserver.fight.field.FightFieldFactory;
import com.pwrd.war.gameserver.fight.msg.CGBattleBeginMessage;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.story.StoryEventType;

public class FightMessageHandler {
	public void handleBattleBeginMessage(final Player player, CGBattleBeginMessage msg) {
		//进行指定类型的战斗
		FightFieldFactory.doVisibleMonsterFight(player, msg.getTargetSn());
	}	
}