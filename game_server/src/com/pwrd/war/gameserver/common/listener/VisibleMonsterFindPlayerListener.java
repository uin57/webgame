package com.pwrd.war.gameserver.common.listener;

import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.gameserver.activity.ggzj.GGZJActivity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.VisibleMonsterFindPlayerEvent;
import com.pwrd.war.gameserver.fight.field.FightFieldFactory;

/**
 * 明雷发现怪物
 * @author xf
 */
public class VisibleMonsterFindPlayerListener implements IEventListener<VisibleMonsterFindPlayerEvent> {

	@Override
	public void fireEvent(VisibleMonsterFindPlayerEvent event) {
		if(event.getMonster().getTemplate().isShare()){
			//共享怪
			Globals.getActivityService().getBossActivity().playerBeginAttack(event.getPlayer(), event.getMonster());
			Globals.getActivityService().getActivity(GGZJActivity.class).playerBeginAttack(event.getPlayer(), event.getMonster());
		}else{
			//直接战斗
			FightFieldFactory.doMonsterFight(event.getPlayer(), event.getMonster(), false);
		}
	}

}
