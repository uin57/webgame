package com.pwrd.war.gameserver.fight.field;

import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.fight.domain.FightAction;
import com.pwrd.war.gameserver.fight.domain.FightLostItem;
import com.pwrd.war.gameserver.fight.domain.FightRoleInfo;
import com.pwrd.war.gameserver.fight.field.unit.FightTeam;
import com.pwrd.war.gameserver.fight.msg.GCBattleActionMessage;
import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.form.template.FormSnTemplate;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.role.Role;
import com.pwrd.war.gameserver.role.properties.HumanNormalProperty;

/**
 * 玩家与玩家战斗的战场基类
 * @author zy
 *
 */
public abstract class PvPField extends BaseField {
	protected Human attHuman;
	protected Human defHuman;
	protected boolean sendAttMsg;
	protected boolean sendDefMsg;

	public PvPField(Human attHuman, Human defHuman, boolean sendAttMsg, boolean sendDefMsg, boolean attIsFullHp, boolean defIsFullHp) {
		//构造进攻方
		super(attHuman, attIsFullHp);
		this.attHuman = attHuman;
		this.sendAttMsg = sendAttMsg;
		
		//构造防守方
		this.defHuman = defHuman;
		this.sendDefMsg = sendDefMsg;
		BattleForm form = defHuman.getDefaultForm();
		defTeam = new FightTeam(defHuman.getUUID(), String.valueOf(form.getFormSn()));
		
		//根据用户阵型依次创建战斗单位
		String[] positions = form.getPositions();
		for (int i = 0; i < positions.length; i ++) {
			String petSn = positions[i];
			if (!StringUtils.isEmpty(petSn)) {
				Role role = defHuman.getRole(petSn);
				if (role != null) {
					//获取当前位置对应的阵型效果配置
					int position = i + 1;
					FormSnTemplate snTemplate = Globals.getFormService().getFormSnTemplate(position);
					
					//创建战斗单位并加入列表
					addFightUnit(role, snTemplate.getOrder(), position, false, attIsFullHp, role.getRoleType(), true);
				}
			}
		}
	}

	@Override
	protected void endImpl(boolean attWin, long timeCost) {
		//将技能和buff资源转换为消息格式
		int[] skillRes = new int[skillResources.size()];
		int index = 0;
		for (Integer res : skillResources.values()) {
			skillRes[index] = res;
			index ++;
		}
		int[] buffRes = new int[buffResources.size()];
		index = 0;
		for (Integer res : buffResources.values()) {
			buffRes[index] = res;
			index ++;
		}
		
		//构造并发送消息给双方
		//TODO 实现battleSn
		if (sendAttMsg || sendDefMsg) {
			GCBattleActionMessage msg = new GCBattleActionMessage(sceneId, attTeam.getTeamSn(), attTeam.getFormSn(),
					defTeam.getTeamSn(), defTeam.getFormSn(), roleInfos.values().toArray(new FightRoleInfo[0]),
					fightActions.toArray(new FightAction[0]), timeCost, attWin,
					new FightLostItem[0], attTeam.getAliveRate(), defTeam.getAliveRate(),
					"", skillRes, buffRes, npcId, bgId);
			if (sendAttMsg) {
				attHuman.sendMessage(msg);
			}
			if (sendDefMsg) {
				//TODO 处理发给防守方的信息
				defHuman.sendMessage(msg);
			}
		}
		
		//设置双方战斗冷却
		HumanNormalProperty attProp = attHuman.getPropertyManager().getPropertyNormal();
		attProp.setLastFightTime(Globals.getTimeService().now() + fightCost);
		HumanNormalProperty defProp = defHuman.getPropertyManager().getPropertyNormal();
		defProp.setLastFightTime(Globals.getTimeService().now() + fightCost);
		
		//调用子类实现
		pvmEndImpl(attWin, timeCost);
	}
	
	/**
	 * 子类战斗结束的实现
	 * @param attWin
	 * @param timeCost
	 */
	protected abstract void pvmEndImpl(boolean attWin, long timeCost);
	
}
