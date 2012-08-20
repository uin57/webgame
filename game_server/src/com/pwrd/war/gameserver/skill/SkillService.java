package com.pwrd.war.gameserver.skill;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.skill.enums.SkillTypeEnum;
import com.pwrd.war.gameserver.skill.msg.CGSkillOrder;
import com.pwrd.war.gameserver.skill.msg.CGSkillUpgrade;
import com.pwrd.war.gameserver.skill.template.SkillBuffTemplate;
import com.pwrd.war.gameserver.skill.template.SkillPasvTemplate;
import com.pwrd.war.gameserver.skill.template.SkillSpecTemplate;
import com.pwrd.war.gameserver.skill.template.SkillTemplate;

public class SkillService {

	/** 职业普通技能，按照职业保存 */
	private Map<VocationType, SkillTemplate> vocationSkills = new HashMap<VocationType, SkillTemplate>();
	
	/** 武将技，按照"技能编号_等级"保存，包括普通武将技和锁屏武将技 */
	private Map<String, SkillTemplate> specialSkills = new HashMap<String, SkillTemplate>();
	
	/** 被动技能，按照技能编号_等级"保存 */
	private Map<String, SkillPasvTemplate> pasvSkills = new HashMap<String, SkillPasvTemplate>();
	
	/** 职业特性技能，按照职业保存 */
	private Map<VocationType, SkillSpecTemplate> featureSkills = new HashMap<VocationType, SkillSpecTemplate>();
	
	/** 技能buff效果，按照编号保存 */
	private Map<Integer, SkillBuffTemplate> buffs = new HashMap<Integer, SkillBuffTemplate>();
	
	/**
	 * 加载技能配置模板，根据技能类型和职业分别保存到相应的结构中
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public SkillService() throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		//加载全部buff效果
		for (SkillBuffTemplate template : Globals.getTemplateService().getAll(SkillBuffTemplate.class).values()) {
			buffs.put(template.getBuffId(), template);
		}
		
		//加载全部主动技能
		for (SkillTemplate template : Globals.getTemplateService().getAll(SkillTemplate.class).values()) {
			//根据技能类型将技能保存在相应的结构中，1为非锁屏武将技，2为职业普通攻击，3为锁屏武将技
			String key = new StringBuilder().append(template.getSkillSn()).append("_").append(template.getSkillLevel()).toString();
			if (template.getSkillType() == SkillTypeEnum.VOCATION_NORMAL.getId()) {
				//普通职业技能
				vocationSkills.put(VocationType.getByCode(template.getVocationType()), template);
			} else {
				//武将技
				specialSkills.put(key, template);
			}
			template.checkEffect(buffs);
		}
		
		//加载全部被动技能
		for (SkillPasvTemplate template : Globals.getTemplateService().getAll(SkillPasvTemplate.class).values()) {
			String key = new StringBuilder().append(template.getSkillSn()).append("_").append(template.getSkillLevel()).toString();
			pasvSkills.put(key, template);
			template.checkEffect(buffs);
		}
		
		//加载全部职业特性技能
		for (SkillSpecTemplate template : Globals.getTemplateService().getAll(SkillSpecTemplate.class).values()) {
			featureSkills.put(VocationType.getByCode(template.getVocationType()), template);
			template.checkEffect(buffs);
		}
	}
	
	/**
	 * 获取全部buff效果配置
	 * @return
	 */
	public Map<Integer, SkillBuffTemplate> getAllBuffs() {
		return buffs;
	}
	
	/**
	 * 获取指定职业的职业普通技能
	 * @param vocation
	 * @return
	 */
	public SkillTemplate getVocationSkill(VocationType vocation) {
		return vocationSkills.get(vocation);
	}
	
	/**
	 * 获取指定编号和等级的武将技
	 * @param sn
	 * @param level
	 * @return
	 */
	public SkillTemplate getSpecialSkill(String sn, int level) {
		String key = new StringBuilder().append(sn).append("_").append(level).toString();
		return specialSkills.get(key);
	}
	
	/**
	 * 获取指定编号和等级的被动技能
	 * @param sn
	 * @param level
	 * @return
	 */
	public SkillPasvTemplate getPassiveSkill(String sn, int level) {
		String key = new StringBuilder().append(sn).append("_").append(level).toString();
		return pasvSkills.get(key);
	}
	
	/**
	 * 获取指定职业的职业特性技能
	 * @param vocation
	 * @return
	 */
	public SkillSpecTemplate getFeatureSkill(VocationType vocation) {
		return featureSkills.get(vocation);
	}
	
	public void modifySkillOrder(Player player, CGSkillOrder message) {
//		int notifyCode = checkSkillOrder(player, message);
//		if (notifyCode == 0) {
//			for (VocationSkill vacationSkill : player.getHuman()
//					.getVocationSkills()) {
//				if (vacationSkill.getVocationType() == message
//						.getVocationType()) {
//					for (SkillGroup skillGroup : vacationSkill.getSkillGroups()) {
//						// 默认技能组直接返回
//						if (message.getSkillGroupOrder() == 0) {
//							return;
//						}
//						if (skillGroup.getNumber() == message
//								.getSkillGroupOrder()) {
//							if (!skillGroup.getName().equals(
//									message.getSkillGroupName())) {
//								skillGroup.setName(message.getSkillGroupName());
//								GCSkillGroupName gcSkillGroupName = new GCSkillGroupName();
//								gcSkillGroupName.setVocationType(vacationSkill
//										.getVocationType());
//								gcSkillGroupName.setSkillGroupOrder(skillGroup
//										.getNumber());
//								gcSkillGroupName.setSkillGroupName(skillGroup
//										.getName());
//								player.sendMessage(gcSkillGroupName);
//							} else {
//								Map<String, Integer> skillOrders = new HashMap<String, Integer>();
//								SkillUnit[] skillUnit = vacationSkill
//										.getSkillUnits();
//								skillOrders.put(skillUnit[0].getSkillSn(),
//										skillUnit[0].getSkillRank());
//								skillOrders.put(skillUnit[1].getSkillSn(),
//										skillUnit[1].getSkillRank());
//								skillOrders.put(skillUnit[2].getSkillSn(),
//										skillUnit[2].getSkillRank());
//								skillOrders.put(skillUnit[3].getSkillSn(),
//										skillUnit[3].getSkillRank());
//								String[] skillSns = message.getSkillSns();
//								if (StringUtils.isNotEmpty(skillSns[0])
//										&& skillOrders.get(skillSns[0]) != null) {
//									skillGroup.setSkillSn1(skillSns[0]);
//									skillGroup.setSkillRank1(skillOrders
//											.get(skillSns[0]));
//								}
//								if (StringUtils.isNotEmpty(skillSns[1])
//										&& skillOrders.get(skillSns[1]) != null) {
//									skillGroup.setSkillSn2(skillSns[1]);
//									skillGroup.setSkillRank2(skillOrders
//											.get(skillSns[1]));
//								}
//
//								if (StringUtils.isNotEmpty(skillSns[2])
//										&& skillOrders.get(skillSns[2]) != null) {
//									skillGroup.setSkillSn3(skillSns[2]);
//									skillGroup.setSkillRank3(skillOrders
//											.get(skillSns[2]));
//								}
//								if (StringUtils.isNotEmpty(skillSns[3])
//										&& skillOrders.get(skillSns[3]) != null) {
//									skillGroup.setSkillSn4(skillSns[3]);
//									skillGroup.setSkillRank4(skillOrders
//											.get(skillSns[3]));
//								}
//								GCSkillOrder gcSkillOrder = new GCSkillOrder();
//								gcSkillOrder.setSkillGroupOrder(skillGroup
//										.getNumber());
//								gcSkillOrder.setVocationType(vacationSkill
//										.getVocationType());
//								gcSkillOrder.setSkillSns(message.getSkillSns());
//								player.sendMessage(gcSkillOrder);
//							}
//							skillGroup.setModified();
//							break;
//						}
//					}
//					break;
//				}
//			}
//		} else {
//			GCNotifyException gcMessage = new GCNotifyException(
//					notifyCode, Globals.getLangService().read(notifyCode));
//			player.sendMessage(gcMessage);
//		}
	}

//	private int checkSkillOrder(Player player, CGSkillOrder message) {
//		List<String> skillSns = new ArrayList<String>();
//		for (String skillSn : message.getSkillSns()) {
//			if (StringUtils.isNotEmpty(skillSn) && skillSns.contains(skillSn)) {
//				return SkillLangConstants_80000.SKILL_INVALID_ORDER;
//			} else {
//				skillSns.add(skillSn);
//			}
//		}
//		return 0;
//	}

	public void updateSkill(Player player, CGSkillUpgrade message) {
//		AbstractSkill skill = Globals.getSkillService().getSkill(message.getSkillSn(), message.getRank() + 1);
//		int notifyCode = checkUpdateSkill(player,skill, message);
//		if (notifyCode == 0) {
//			for (VocationSkill vocationSkill : player.getHuman()
//					.getVocationSkills()) {
//				for (SkillUnit skillUnit : vocationSkill.getSkillUnits()) {
//					if (skillUnit.getSkillSn().equals(message.getSkillSn())) {
//						if (skillUnit.getSkillRank() + 1 == message.getRank()) {
//							skillUnit.setSkillRank(skillUnit.getSkillRank() + 1);
//							List<String> skillSns = new ArrayList<String>();
//							for (SkillGroup skillGroup : vocationSkill
//									.getSkillGroups()) {
//								skillSns.addAll(dealwithSkillGroup(skillUnit,
//										skillGroup));
//							}
//							vocationSkill.setModified();
//
//							if (skillUnit.getSkillRank() == 1) {
//								GCSkillOrder gcSkillOrder = new GCSkillOrder();
//								gcSkillOrder.setSkillGroupOrder(0);
//								gcSkillOrder.setSkillSns(skillSns
//										.toArray(new String[skillSns.size()]));
//								gcSkillOrder.setVocationType(vocationSkill
//										.getVocationType());
//								player.sendMessage(gcSkillOrder);
//							}
//							GCSkillUpgrade gcMessage = new GCSkillUpgrade();
//							gcMessage.setSkillSn(skillUnit.getSkillSn());
//							gcMessage.setRank(skillUnit.getSkillRank());
//							player.sendMessage(gcMessage);
//							HumanPropertyManager propertyManager=player.getHuman().getPropertyManager();
//							propertyManager.setSliver(propertyManager.getSliver()-Integer.parseInt(skill.getConsume1()));
//							propertyManager.setSee(propertyManager.getSee()-Integer.parseInt(skill.getConsume2()));
//							player.getHuman().setModified();
//							break;
//						}
//					}
//				}
//			}
//		} else {
//			GCNotifyException gcMessage = new GCNotifyException(
//					notifyCode, Globals.getLangService().read(notifyCode));
//			player.sendMessage(gcMessage);
//		}

	}

	/** 修改职业技能等级 */
//	private List<String> dealwithSkillGroup(SkillUnit skillUnit,
//			SkillGroup skillGroup) {
//		List<String> skillSns = new ArrayList<String>();
//		if (skillGroup.getNumber() == 0) {
//			if (skillUnit.getSkillRank() == 1) {
//				if (skillGroup.getSkillSn1() != null) {
//					skillSns.add(skillGroup.getSkillSn1());
//					if (skillGroup.getSkillSn2() != null) {
//						skillSns.add(skillGroup.getSkillSn2());
//						if (skillGroup.getSkillSn3() != null) {
//							skillSns.add(skillGroup.getSkillSn3());
//							if (skillGroup.getSkillSn4() != null) {
//								skillSns.add(skillGroup.getSkillSn4());
//							}
//						}
//					}
//				}
//				if (skillGroup.getSkillSn1() == null) {
//					skillGroup.setSkillSn1(skillUnit.getSkillSn());
//					skillGroup.setSkillRank1(skillUnit.getSkillRank());
//					skillSns.add(skillUnit.getSkillSn());
//				} else if (skillGroup.getSkillSn2() == null) {
//					skillGroup.setSkillSn2(skillUnit.getSkillSn());
//					skillGroup.setSkillRank2(skillUnit.getSkillRank());
//					skillSns.add(skillUnit.getSkillSn());
//				} else if (skillGroup.getSkillSn3() == null) {
//					skillGroup.setSkillSn3(skillUnit.getSkillSn());
//					skillGroup.setSkillRank3(skillUnit.getSkillRank());
//					skillSns.add(skillUnit.getSkillSn());
//				} else if (skillGroup.getSkillSn4() == null) {
//					skillGroup.setSkillSn4(skillUnit.getSkillSn());
//					skillGroup.setSkillRank4(skillUnit.getSkillRank());
//					skillSns.add(skillUnit.getSkillSn());
//				}
//			} else {
//				modifySkillGroup(skillUnit, skillGroup);
//			}
//		} else {
//			modifySkillGroup(skillUnit, skillGroup);
//		}
//		skillGroup.setModified();
//		return skillSns;
//	}

//	private void modifySkillGroup(SkillUnit skillUnit, SkillGroup skillGroup) {
//		if (skillGroup.getSkillSn1() != null
//				&& skillGroup.getSkillSn1().equals(skillUnit.getSkillSn())) {
//			skillGroup.setSkillSn1(skillUnit.getSkillSn());
//			skillGroup.setSkillRank1(skillUnit.getSkillRank());
//		} else if (skillGroup.getSkillSn2() != null
//				&& skillGroup.getSkillSn2().equals(skillUnit.getSkillSn())) {
//			skillGroup.setSkillSn2(skillUnit.getSkillSn());
//			skillGroup.setSkillRank2(skillUnit.getSkillRank());
//		} else if (skillGroup.getSkillSn3() != null
//				&& skillGroup.getSkillSn3().equals(skillUnit.getSkillSn())) {
//			skillGroup.setSkillSn3(skillUnit.getSkillSn());
//			skillGroup.setSkillRank3(skillUnit.getSkillRank());
//		} else if (skillGroup.getSkillSn4() != null
//				&& skillGroup.getSkillSn4().equals(skillUnit.getSkillSn())) {
//			skillGroup.setSkillSn4(skillUnit.getSkillSn());
//			skillGroup.setSkillRank4(skillUnit.getSkillRank());
//		}
//	}

//	private int checkUpdateSkill(Player player,AbstractSkill skill, CGSkillUpgrade message) {
//		if (skill==null||!skillSns.contains(message.getSkillSn())) {
//			return SkillLangConstants_80000.NO_SUCH_SKILL_IN_UPGRADE_SKILL;
//		}
//		if (player.getHuman().getLevel() < skill.getNeedRank()) {
//			return SkillLangConstants_80000.NO_ENOUGH_LEVEL_IN_UPGRADE_SKILL;
//		}
//		if (StringUtils.isNotEmpty(skill.getConsume1())) {
//			if (player.getHuman().getPropertyManager().getSliver() < Integer
//					.parseInt(skill.getConsume1())) {
//				return SkillLangConstants_80000.NO_ENOUGH_MONEY_IN_UPGRADE_SKILL;
//			}
//		}
//		if (StringUtils.isNotEmpty(skill.getConsume2())) {
//			if (player.getHuman().getPropertyManager().getSee() < Integer
//					.parseInt(skill.getConsume2())) {
//				return SkillLangConstants_80000.NO_ENOUGH_SEE_IN_UPGRADE_SKILL;
//			}
//		}
//		return 0;
//	}

}
