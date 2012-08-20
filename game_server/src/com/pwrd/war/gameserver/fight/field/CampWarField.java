package com.pwrd.war.gameserver.fight.field;

import com.pwrd.war.gameserver.activity.campwar.CampWarActivity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;

public class CampWarField extends PvPField {
	private String targetUUID;
	private Player attacker;
	/**
	 * 构造方法，直接调用父级构造方法
	 * @param battleSn
	 */
	public CampWarField(Player player, Human targetHuman, boolean sendDefMessage, String targetUUID) {
		//构造双方对象
		super(player.getHuman(), targetHuman, true, sendDefMessage, false, true);
		this.targetUUID = targetUUID;
		this.attacker = player;
	}

	@Override
	protected void pvmEndImpl(boolean attWin, long timeCost) {
		Globals.getActivityService().getActivity(CampWarActivity.class).onBattleEnd(attWin, attacker, targetUUID);
	}

}
