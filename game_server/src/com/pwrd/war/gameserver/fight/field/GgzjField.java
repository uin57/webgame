package com.pwrd.war.gameserver.fight.field;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.gameserver.activity.ggzj.GGZJActivity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.fight.field.unit.FightUnit;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.monster.Monster;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.monster.VisibleMonster.FightMonster;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;

public class GgzjField extends PvMField {
	private int oHp;//之前的hp
	
	public GgzjField(Player player, VisibleMonster monster) {
		//构造进攻方
		super(player, monster, true, false);
		
		//设置oldHp
		oHp = 0;
		for(FightUnit u : defTeam.getUnits()){
			oHp += u.getCurHp();
		}
	}

	@Override
	public void pvmEndImpl(boolean attWin, long timeCost) {
		int nowHp = 0;
		//修改血量
		for(FightUnit u : defTeam.getUnits()){
			nowHp += u.getCurHp();
			u.getUnitSn();
			List<FightMonster> lm = visibleMonster.getSharedMonsterInfo().getSharedMonster();
			for(Monster m : lm){
				if(StringUtils.equals(m.getUUID(), u.getUnitSn())){
					m.setCurHp(u.getCurHp());
				}
			}
		}
		
		//设置玩家状态
		Human attHuman = attPlayer.getHuman();
		attHuman.setGamingState(GamingStateIndex.IN_NOMAL);
		visibleMonster.endAttack(!attWin);
		
		if(nowHp < 0) nowHp = 0;
		//告诉boss系统战斗结束
		int lossHp = oHp - nowHp;
		Globals.getActivityService().getActivity(GGZJActivity.class).playerEndAttack(attWin, attPlayer, visibleMonster, 
				lossHp);
	}

}
