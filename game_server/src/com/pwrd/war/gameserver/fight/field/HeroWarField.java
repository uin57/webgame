package com.pwrd.war.gameserver.fight.field;

import com.pwrd.war.gameserver.activity.campwar.HeroWarActivity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;

public class HeroWarField extends PvPField {
	private String targetUUID;
	private Player attacker;
	/**
	 * 构造方法，直接调用父级构造方法
	 * @param battleSn
	 */
	public HeroWarField(Player player, Human targetHuman, boolean sendDefMsg, String targetUUID) {
		//构造双方对象
		super(player.getHuman(), targetHuman, true, sendDefMsg, true, true);
		this.targetUUID = targetUUID;
		this.attacker = player;
	}

	@Override
	protected void pvmEndImpl(boolean attWin, long timeCost) {
		Globals.getActivityService().getActivity(HeroWarActivity.class).onBattleEnd(attWin, attacker, targetUUID);
	}

}
