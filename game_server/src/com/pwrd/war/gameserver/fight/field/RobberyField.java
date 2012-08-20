package com.pwrd.war.gameserver.fight.field;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.map.MapBattleBgService;

public class RobberyField extends PvPField {
	private String targetUUID;
	private String protecterUUID;
	/**
	 * 构造方法，直接调用父级构造方法
	 * @param battleSn
	 */
	public RobberyField(Human attHuman, Human defHuman, String targetUUID, String protecterUUID) {
		//构造双方对象
		super(attHuman, defHuman, true, false, true, true);
		this.targetUUID = targetUUID;
		this.protecterUUID = protecterUUID;
		
		//设置战斗背景
		bgId = Globals.getMapBattleBgService().getBattleBgByFuncId(MapBattleBgService.FUNC_CCJJ);
		npcId = Globals.getMapBattleBgService().getNpcIdByFuncId(MapBattleBgService.FUNC_CCJJ);
	}

	@Override
	protected void pvmEndImpl(boolean attWin, long timeCost) {
		//实现运镖结束时的处理
		Globals.getRobberyService().onRobEnd(attHuman.getUUID(), targetUUID, protecterUUID, attWin);
	}

}
