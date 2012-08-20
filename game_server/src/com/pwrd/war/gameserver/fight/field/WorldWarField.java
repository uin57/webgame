package com.pwrd.war.gameserver.fight.field;

import com.pwrd.war.gameserver.activity.worldwar.WorldWarActivity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;

public class WorldWarField extends PvPField {
	private String targetUUID;
	private Player attacker;
	/**
	 * 构造方法，直接调用父级构造方法
	 * @param battleSn
	 */
	public WorldWarField(Player player, Human targetHuman, boolean attFullHp, boolean defFullHp) {
		//构造双方对象
		super(player.getHuman(), targetHuman, true, true, attFullHp, defFullHp);
		this.targetUUID = targetHuman.getUUID();
		this.attacker = player;
	}

	@Override
	protected void pvmEndImpl(boolean attWin, long timeCost) {
		Globals.getActivityService().getActivity(WorldWarActivity.class).onBattleEnd(attacker.getRoleUUID(),
				targetUUID, attWin);
	}

}
