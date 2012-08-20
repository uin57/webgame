package com.pwrd.war.gameserver.player.persistance;

import java.util.LinkedHashMap;
import java.util.Map;

import com.pwrd.war.core.annotation.NotThreadSafe;
import com.pwrd.war.core.object.LifeCycle;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.core.persistance.AbstractDataUpdater;
import com.pwrd.war.gameserver.arena.ArenaAchievement;
import com.pwrd.war.gameserver.arena.ArenaAchievementUpdater;
import com.pwrd.war.gameserver.arena.ArenaHistoryUpdater;
import com.pwrd.war.gameserver.arena.ArenaRanking;
import com.pwrd.war.gameserver.arena.ArenaUpdater;
import com.pwrd.war.gameserver.arena.ChallengeHistory;
import com.pwrd.war.gameserver.buff.BufferUpdate;
import com.pwrd.war.gameserver.buff.domain.AttackBuffer;
import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.buff.domain.ChangeBuffer;
import com.pwrd.war.gameserver.buff.domain.DefenseBuffer;
import com.pwrd.war.gameserver.buff.domain.ExperienceBuffer;
import com.pwrd.war.gameserver.buff.domain.LifeBuffer;
import com.pwrd.war.gameserver.buff.domain.MedicinalBuffer;
import com.pwrd.war.gameserver.buff.domain.MoneyBuffer;
import com.pwrd.war.gameserver.buff.domain.VitalityBuffer;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.form.BattleFormUpdater;
import com.pwrd.war.gameserver.friend.FriendInfo;
import com.pwrd.war.gameserver.friend.FriendUpdater;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.HumanUpdater;
import com.pwrd.war.gameserver.human.cooldown.Cooldown;
import com.pwrd.war.gameserver.human.cooldown.CooldownUpdater;
import com.pwrd.war.gameserver.human.domain.ProbInfo;
import com.pwrd.war.gameserver.human.manager.ProbInfoUpdater;
import com.pwrd.war.gameserver.human.xiulian.XiulianInfo;
import com.pwrd.war.gameserver.human.xiulian.XiulianUpdater;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemUpdater;
import com.pwrd.war.gameserver.mail.MailInstance;
import com.pwrd.war.gameserver.mail.MailUpdater;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.pet.PetUpdater;
import com.pwrd.war.gameserver.quest.DoingQuest;
import com.pwrd.war.gameserver.quest.DoingQuestUpdater;
import com.pwrd.war.gameserver.quest.FinishedQuest;
import com.pwrd.war.gameserver.quest.FinishedQuestUpdater;
import com.pwrd.war.gameserver.robbery.HumanRobberyInfo;
import com.pwrd.war.gameserver.robbery.HumanRobberyUpdater;
import com.pwrd.war.gameserver.robbery.RobberyInfo;
import com.pwrd.war.gameserver.robbery.RobberyUpdater;
import com.pwrd.war.gameserver.story.FinishedStory;
import com.pwrd.war.gameserver.story.FinishedStoryUpdater;
import com.pwrd.war.gameserver.tower.HumanTowerInfo;
import com.pwrd.war.gameserver.tower.HumanTowerInfoUpdater;
import com.pwrd.war.gameserver.tree.HumanTreeInfo;
import com.pwrd.war.gameserver.tree.HumanTreeInfoUpdater;
import com.pwrd.war.gameserver.tree.HumanTreeWaterInfo;
import com.pwrd.war.gameserver.tree.HumanTreeWaterInfoUpdater;
import com.pwrd.war.gameserver.vocation.SkillGroup;
import com.pwrd.war.gameserver.vocation.SkillGroupUpdater;
import com.pwrd.war.gameserver.vocation.VocationSkill;
import com.pwrd.war.gameserver.vocation.VocationSkillUpdater;

/**
 * 
 * Player数据更新接口
 * 
 */
@NotThreadSafe
public class PlayerDataUpdater extends AbstractDataUpdater{

	private static Map<Class<? extends PersistanceObject<?, ?>>, POUpdater> operationDbMap = new LinkedHashMap<Class<? extends PersistanceObject<?, ?>>, POUpdater>();

	static {
		operationDbMap.put(Human.class, new HumanUpdater());
		operationDbMap.put(Pet.class, new PetUpdater());
		operationDbMap.put(Item.class, new ItemUpdater());
		operationDbMap.put(MailInstance.class, new MailUpdater());	
		operationDbMap.put(DoingQuest.class, new DoingQuestUpdater());
		operationDbMap.put(FinishedQuest.class, new FinishedQuestUpdater());
		operationDbMap.put(BattleForm.class, new BattleFormUpdater());
		operationDbMap.put(VocationSkill.class,new  VocationSkillUpdater());
		operationDbMap.put(SkillGroup.class,new  SkillGroupUpdater());
		operationDbMap.put(Cooldown.class,new  CooldownUpdater());
		operationDbMap.put(AttackBuffer.class,new  BufferUpdate());
		operationDbMap.put(Buffer.class,new  BufferUpdate());
		operationDbMap.put(ChangeBuffer.class,new  BufferUpdate());
		operationDbMap.put(DefenseBuffer.class,new  BufferUpdate());
		operationDbMap.put(ExperienceBuffer.class,new  BufferUpdate());
		operationDbMap.put(LifeBuffer.class,new  BufferUpdate());
		operationDbMap.put(MedicinalBuffer.class,new  BufferUpdate());
		operationDbMap.put(MoneyBuffer.class,new  BufferUpdate());
		operationDbMap.put(VitalityBuffer.class,new  BufferUpdate());
		operationDbMap.put(FinishedStory.class, new FinishedStoryUpdater());
		operationDbMap.put(FriendInfo.class, new FriendUpdater());
		operationDbMap.put(RobberyInfo.class, new RobberyUpdater());
		operationDbMap.put(ArenaRanking.class, new ArenaUpdater());
		operationDbMap.put(ChallengeHistory.class, new ArenaHistoryUpdater());
		operationDbMap.put(ArenaAchievement.class, new ArenaAchievementUpdater());
		operationDbMap.put(ProbInfo.class, new ProbInfoUpdater());
		operationDbMap.put(HumanTowerInfo.class, new HumanTowerInfoUpdater());
		operationDbMap.put(HumanTreeInfo.class, new HumanTreeInfoUpdater());
		operationDbMap.put(HumanTreeWaterInfo.class, new HumanTreeWaterInfoUpdater());
		operationDbMap.put(XiulianInfo.class, new XiulianUpdater());
		operationDbMap.put(HumanRobberyInfo.class, new HumanRobberyUpdater());
	}

	public PlayerDataUpdater() {
		super();
	}


	@Override
	protected void doUpdate(LifeCycle lc) {
		if (!lc.isActive()) {
			throw new IllegalStateException(
					"Only the live object can be updated.");

		}
		PersistanceObject<?, ?> po = lc.getPO();
		POUpdater poUpdater = operationDbMap.get(po.getClass());
		poUpdater.save(po);
	}

	@Override
	protected void doDel(LifeCycle lc) {
		if (!lc.isDestroyed()) {
			throw new IllegalStateException(
					"Only the destroyed object can be deleted.");
		}
		PersistanceObject<?, ?> po = lc.getPO();
		operationDbMap.get(po.getClass()).delete(po);
	}


}
