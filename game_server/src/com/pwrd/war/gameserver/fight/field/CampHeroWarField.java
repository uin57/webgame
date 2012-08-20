package com.pwrd.war.gameserver.fight.field;

import com.pwrd.war.gameserver.activity.campwar.CampWarActivity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.Camp;
import com.pwrd.war.gameserver.human.Human;

/**
 * 守军争夺战，主将战
 */
public class CampHeroWarField extends PvPField {

	private Camp tagetCamp;
	private Human attacker;
	/**
	 * 构造方法，直接调用父级构造方法
	 * @param battleSn
	 */
	public CampHeroWarField(Human attacker, Human targetHuman) {
		//构造双方对象
		super(attacker, targetHuman, false, false, true, true);
	 
		this.attacker = attacker;
		this.tagetCamp = targetHuman.getCamp();
	}

	@Override
	protected void pvmEndImpl(boolean attWin, long timeCost) {
		Globals.getActivityService().getActivity(CampWarActivity.class).onHeroWarEnd(attWin, 
				attacker.getUUID(),
				attacker.getCamp(), tagetCamp);
	}

}
