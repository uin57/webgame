package com.pwrd.war.gameserver.common.log;

import java.util.Collection;
import java.util.List;

import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.manager.HumanPetManager;
import com.pwrd.war.gameserver.human.manager.HumanRepInfoManager;
import com.pwrd.war.gameserver.item.EquipmentFeature;
import com.pwrd.war.gameserver.item.Inventory;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.container.AbstractItemBag;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.rep.HumanRepInfo;

/**
 * 为LogService.sendSnapLog提供服务，产生玩家的各种对象的快照字符串
 * 
 * 
 */
public class SnapStringBuilder {
	
	public static String buildInventorySnap(Inventory inventory) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<font color=\"#FF0000\">主背包 【</font>");
		buildBag(inventory.getPrimBag(), sb);
		sb.append("<font color=\"#FF0000\">】</font><br>");
		
		// 角色武将列表
		List<Pet> pets = inventory.getOwner().getPetManager().getPets();
		
		if (pets != null) {
			for (Pet pet : pets) {
				if (pet == null) {
					continue;
				}
				
				sb.append("<font color=\"#00FF00\">武将背包 ").append("(").append(pet.getName()).append(")").append("【</font>");
				buildBag(inventory.getBagByPet(pet.getUUID()), sb);
				sb.append("<font color=\"#00FF00\">】</font><br>");
			}
		}
		
		return sb.toString();		
	}
		
	private static void buildBag(AbstractItemBag bag, StringBuilder sb) {
		Collection<Item> items = bag.getAll();
		int i = 1;
		
		for (Item item : items) {	
			if (i != 1) {
				sb.append(", &nbsp;<br/>");
			}

			buildItem(item, sb);
			i++;
		}
	}

	/**
	 * 构造物品快照
	 * 
	 * @param item
	 * @param sb
	 */
	private static void buildItem(Item item, StringBuilder sb) {
		sb.append(item.getName()).append("(").append(item.getTemplateId())
				.append(")X").append(item.getOverlap());
		sb.append("@").append(item.getBagType().index).append("[").append(
				item.getIndex()).append("]");
		// 是装备，记录属性信息
		if (item.isEquipment()) {
			sb.append("强化等级:").append(((EquipmentFeature)item.getFeature()).getEnhanceLevel());
		}
	}

	
	public static String buildHumanSnap(Human human) {
		StringBuilder _sb = new StringBuilder();
		//TODO
		_sb.append("<font color=\"FF0000\">角色 【</font>");
//		appendElement(_sb, "阵营", human.getCountry().toString());
		appendElement(_sb, "等级", human.getLevel());
//		appendElement(_sb, "官职", human.getRank());
//		appendElement(_sb, "vip等级", human.getVipLevel());
//		appendElement(_sb, "军团UUID", human.getGuildId());
//		appendElement(_sb, "城市ID", human.getSceneId());
		
		_sb.append("<font color=\"#FF0000\">】</font>");		
		return _sb.toString();
	}
	
	
	private static void appendElement(StringBuilder sb, String key, Object value) {
		sb.append(key).append("(").append(value).append("),");
	}
	
	/**
	 * 构造全部宠物快照字符串
	 * 
	 * @param petManager
	 * @return
	 */
	public static String buildPetSnap(HumanPetManager petManager) {
		List<Pet> pets = petManager.getPets();
		StringBuilder sb = new StringBuilder();
		sb.append("<font color=\"#FF00FF\">宠物【");
		for (Pet pet : pets) {
			sb.append(buildPetSnap(pet)).append(", &nbsp;<br/>");
		}
		sb.append("<font color=\"#FF00FF\">】</font>");
		return sb.toString();
	}
	
	/**
	 * 构造宠物快照
	 * 
	 * @param pet
	 *            宠物
	 * @return 快照string
	 */
	public static String buildPetSnap(Pet pet) {
		StringBuilder sb = new StringBuilder();
//		sb.append("<font color=\"#FF0000\">").append(pet.getUUID()).append("</font>,");
//		sb.append("<font color=\"#00FF00\">").append(pet.getName()).append("</font>,");		
//		sb.append("<font color=\"#0000FF\">").append(pet.getTemplateId()).append("</font>");
//		
//		int tmplId = pet.getTemplateId();		
//		sb.append(tmplId).append(",");
//		sb.append(",性别(").append(pet.getSex()).append(")");
//		sb.append(",等级(").append(pet.getLevel()).append(")");
//		sb.append(",经验(").append(pet.getCurExp()).append(")");
////		sb.append(",兵种类型(").append(pet.getSoldierType()).append(")");
////		sb.append(",兵种兵阶(").append(pet.getSoldierGrade()).append(")");
////		sb.append(",兵种等级(").append(pet.getSoldierLevel()).append(")");
////		sb.append(",兵种数量(").append(pet.getSoldierAmount()).append(")");
//		sb.append(",统(").append(pet.getPropertyManager().getLeaderShip()).append(")");
//		sb.append(",勇(").append(pet.getPropertyManager().getMight()).append(")");
//		sb.append(",智(").append(pet.getPropertyManager().getIntellect()).append(")");

		return sb.toString();
	}

	/**
	 * 构造副本信息快照字符串
	 * @param petManager
	 * @return
	 */
	public static String buildHumanRepInfoSnap(HumanRepInfoManager humanRepInfoManager) {
//		List<HumanRepInfo> reps = humanRepInfoManager.getHumanRepInfos();
		StringBuilder sb = new StringBuilder();
//		sb.append("<font color=\"#FF00FF\">副本【");
//		for (HumanRepInfo rep : reps) {
//			sb.append(buildHumanRepInfoSnap(rep)).append(", &nbsp;<br/>");
//		}
//		sb.append("<font color=\"#FF00FF\">】</font>");
		return sb.toString();
	}
	
	public static String buildHumanRepInfoSnap(HumanRepInfo rep) {
		StringBuilder sb = new StringBuilder();

		return sb.toString();
	}
}
