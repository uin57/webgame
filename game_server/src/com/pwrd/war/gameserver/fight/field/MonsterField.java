package com.pwrd.war.gameserver.fight.field;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.monster.VisibleMonster.FightMonster;
import com.pwrd.war.gameserver.monster.template.VisibleMonsterTemplate;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.role.RoleTypes;

/**
 * 对怪物战斗战场
 * @author zy
 *
 */
public class MonsterField extends PvMField {
	/**
	 * 初始化怪物战斗战场
	 * @param player
	 * @param monster
	 */
	public MonsterField(Player player, VisibleMonster monster) {
		//构造进攻方
		super(player, monster, false, true);
		
		//构造玩家NPC
		VisibleMonsterTemplate template = monster.getTemplate();
		if (template.getUserNpcs().size() > 0) {
			for (FightMonster m : monster.getUserNpcs(attTeam.getAvgLevel())) {
				addFightUnit(m, m.getInitOrder(), m.getFormPos(), true, true, RoleTypes.MONSTER, true);
			}
		}
	}
	
	/**
	 * 战斗结束处理
	 */
	@Override
	public void pvmEndImpl(boolean attWin, long timeCost) {
		Loggers.battleLogger.info(new StringBuilder("++++++++++MonsterField.pvmEndImpl").toString());
		
		//设置玩家和怪物状态
		Human attHuman = attPlayer.getHuman();
		attHuman.setGamingState(GamingStateIndex.IN_NOMAL);
		visibleMonster.endAttack(attWin);
		
		//进攻失败设置复活位置
		if (!attWin) {
			//复活在明雷配置的复活位置上
			VisibleMonsterTemplate template = visibleMonster.getTemplate();
			String sceneId = template.getReliveSceneId();
			int sceneX = template.getReliveSceneX();
			int sceneY = template.getReliveSceneY();
			if (attHuman.isInRep()) {
				//副本中复活
				Globals.getRepService().onRelive(attHuman.getPlayer(), sceneId, sceneX, sceneY);
			} else {
				//大场景复活
				Globals.getSceneService().onRelive(attHuman.getPlayer(), sceneId, sceneX, sceneY);
			}
		}
		
	}
}
