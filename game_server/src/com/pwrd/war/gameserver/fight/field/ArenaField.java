package com.pwrd.war.gameserver.fight.field;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.map.MapBattleBgService;

public class ArenaField extends PvPField {
	private boolean defOnline;
	
	/**
	 * 构造方法，直接调用父级构造方法
	 * @param player
	 * @param targetHuman
	 * @param targetIsOnline
	 */
	public ArenaField(Human attHuman, Human defHuman, boolean defOnline) {
		//构造双方对象
		super(attHuman, defHuman, true, false, true, true);
		this.defOnline = defOnline;
		
		//设置战斗背景
		bgId = Globals.getMapBattleBgService().getBattleBgByFuncId(MapBattleBgService.FUNC_ARENA);
		npcId = Globals.getMapBattleBgService().getNpcIdByFuncId(MapBattleBgService.FUNC_ARENA);
	}

	@Override
	protected void pvmEndImpl(boolean attWin, long timeCost) {
		//实现竞技场结束处理
		Globals.getArenaService().finishFight(attHuman, defHuman, attWin, defOnline);
	}

}
