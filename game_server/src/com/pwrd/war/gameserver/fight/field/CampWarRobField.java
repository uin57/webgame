package com.pwrd.war.gameserver.fight.field;

import com.pwrd.war.gameserver.activity.campwar.CampWarActivity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;

/**
 * 守军争夺战抢劫战斗
 */
public class CampWarRobField extends PvPField {
	private String targetUUID;
	private Player attacker;
	/**
	 * 构造方法，直接调用父级构造方法
	 * @param battleSn
	 */
	public CampWarRobField(Player player, Human targetHuman, boolean sendDefMessage) {
		//构造双方对象
		super(player.getHuman(), targetHuman, true, sendDefMessage, true, true);
		this.targetUUID = targetHuman.getUUID();
		this.attacker = player;
	}

	@Override
	protected void pvmEndImpl(boolean attWin, long timeCost) {
		Globals.getActivityService().getActivity(CampWarActivity.class).onRobEnd(attWin, attacker, targetUUID);
	}

}
