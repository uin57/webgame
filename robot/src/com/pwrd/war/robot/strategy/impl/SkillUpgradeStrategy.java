package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.skill.msg.CGSkillUpgrade;
import com.pwrd.war.gameserver.vocation.SkillUnit;
import com.pwrd.war.gameserver.vocation.msg.GCVocation;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class SkillUpgradeStrategy  extends OnceExecuteStrategy{

	public SkillUpgradeStrategy(Robot robot, int delay) {
		super(robot,delay);
	}

	@Override
	public void doAction() {
		CGSkillUpgrade  skillUpgrade =new CGSkillUpgrade();
		skillUpgrade.setSkillSn("10002");
		skillUpgrade.setRank(0);
		this.getRobot().sendMessage(skillUpgrade);
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCVocation){
			GCVocation vocation = (GCVocation) message;
			for (com.pwrd.war.gameserver.vocation.VocationSkill  vocationSkill : vocation.getVocationSkills()) {
				for (SkillUnit skillUnit : vocationSkill.getSkillUnits()) {
					CGSkillUpgrade  skillUpgrade =new CGSkillUpgrade();
					skillUpgrade.setSkillSn(skillUnit.getSkillSn());
					skillUpgrade.setRank(skillUnit.getSkillRank());
					this.getRobot().sendMessage(skillUpgrade);
				}
			};
			
		}
		
	}

}
