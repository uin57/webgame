package com.pwrd.war.gameserver.interactive;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.pwrd.war.db.model.HumanEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.interactive.domain.PetInfomation;
import com.pwrd.war.gameserver.interactive.msg.CGEquipmentInfomationMessage;
import com.pwrd.war.gameserver.interactive.msg.CGInfomationMessage;
import com.pwrd.war.gameserver.interactive.msg.CGLookUpMessage;
import com.pwrd.war.gameserver.interactive.msg.CGRoleListMessage;
import com.pwrd.war.gameserver.interactive.msg.CGTransferVocationInformationMessage;
import com.pwrd.war.gameserver.interactive.msg.GCInfomationMessage;
import com.pwrd.war.gameserver.interactive.msg.GCLookUpMessage;
import com.pwrd.war.gameserver.interactive.msg.GCRoleListMessage;
import com.pwrd.war.gameserver.item.Inventory;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.role.Role;
import com.pwrd.war.gameserver.warcraft.container.PetWarcraftEquipBag;
import com.pwrd.war.gameserver.warcraft.container.WarcraftEquipBag;
import com.pwrd.war.gameserver.warcraft.manager.WarcraftInventory;
import com.pwrd.war.gameserver.warcraft.model.Warcraft;

public class InteractiveService {

	public void lookUp(Player player, CGLookUpMessage message) {
		Player lookUpedPlayer = Globals.getOnlinePlayerService().getPlayerById(
				message.getPlayUUID());
		
		if (lookUpedPlayer != null) {
			GCLookUpMessage msg = new GCLookUpMessage();
			Human lookUpedHuman = lookUpedPlayer.getHuman();
			msg.setName(lookUpedHuman.getName());
			msg.setLevel(lookUpedHuman.getLevel());
	//		msg.setOpenFunction(lookUpedHuman.getPropertyManager()
	//				.getOpenFunction());
			msg.setPlayUUID(lookUpedHuman.getUUID());
			msg.setSex(lookUpedHuman.getSex().getCode());
			msg.setVocation(lookUpedHuman.getVocationType().getCode());
			player.sendMessage(msg);
		} else {
			//玩家不在线，加载离线用户信息
			Human lookUpedHuman = loadOfflineHuman(message.getPlayUUID());
			if (lookUpedHuman != null) {
				GCLookUpMessage msg = new GCLookUpMessage();
				msg.setName(lookUpedHuman.getName());
				msg.setLevel(lookUpedHuman.getLevel());
		//		msg.setOpenFunction(lookUpedHuman.getPropertyManager()
		//				.getOpenFunction());
				msg.setPlayUUID(lookUpedHuman.getUUID());
				msg.setSex(lookUpedHuman.getSex().getCode());
				msg.setVocation(lookUpedHuman.getVocationType().getCode());
				player.sendMessage(msg);
			} else {
				player.sendErrorMessage(CommonLangConstants_10000.OBJECT_NOT_EXIST);
			}
		}
	}

	public void infomation(Player player, CGInfomationMessage message) {
		GCInfomationMessage msg = new GCInfomationMessage();
		msg.setPlayUUID(message.getPlayUUID());
		msg.setRoleSn(message.getRoleSn());
		player.sendMessage(msg);
	}

	public void roleList(Player player,
			CGRoleListMessage message) {
		GCRoleListMessage msg = new GCRoleListMessage();
		Player lookUpedPlayer = Globals.getOnlinePlayerService().getPlayerById(
				message.getPlayUUID());
		if (lookUpedPlayer != null) {
			List<PetInfomation> petInfomations = getPetInfomations(lookUpedPlayer.getHuman());
			msg.setPlayUUID(lookUpedPlayer.getRoleUUID());
			msg.setPetInformation(petInfomations.toArray(new PetInfomation[0]));
			player.sendMessage(msg);
		} else {
			//加载离线玩家信息
			Human lookUpedHuman = loadOfflinePet(message.getPlayUUID());
			if (lookUpedHuman != null) {
				List<PetInfomation> petInfomations = getPetInfomations(lookUpedHuman);
				msg.setPlayUUID(message.getPlayUUID());
				msg.setPetInformation(petInfomations.toArray(new PetInfomation[0]));
				player.sendMessage(msg);
			} else {
				player.sendErrorMessage(CommonLangConstants_10000.OBJECT_NOT_EXIST);
			}
		}
		
	}
	
	public void equipmentInfomation(Player player,
			CGEquipmentInfomationMessage message){
		Player lookUpedPlayer = Globals.getOnlinePlayerService().getPlayerById(message.getPlayUUID());
		Human lookUpedHuman = null;
		if(lookUpedPlayer != null){
			lookUpedHuman = lookUpedPlayer.getHuman();
		} else {
			lookUpedHuman = loadOfflineAll(message.getPlayUUID());
		}
		
		if (lookUpedHuman != null) {
			Role role = lookUpedHuman.getRole(message.getRoleSn());
			Inventory inv = lookUpedHuman.getInventory();
			WarcraftInventory winv = lookUpedHuman.getWarcraftInventory();
			if (role instanceof Human) {
				//计算兵法属性
				WarcraftEquipBag warcraftEquipBag = winv.getWarcraftEquipBag();
				List<Warcraft> warcrafts = warcraftEquipBag.getAllWarcraft();
				JSONArray warcraftProps = new JSONArray();
				for(Warcraft warcraft : warcrafts){
					warcraftProps.add(warcraft.toProp());
				}
				if(warcraftProps.size() == 0){
					role.setWarcraftProps("");
				}else{
					role.setWarcraftProps(warcraftProps.toString());
				}
				role.sendInfomationMessage(player);
				player.sendMessage(inv.getEquipBagInfoMsg());
				//发送兵法装备信息
				if(winv != null){
					player.sendMessage(winv.sendWarcraftEquipBagInfo());
				}
			} else if (role instanceof Pet) {
				//计算兵法属性
				PetWarcraftEquipBag warcraftEquipBag = (PetWarcraftEquipBag)winv.getWarcraftBagByType(
						BagType.PET_WARCRAFT_EQUIP, role.getUUID());
				List<Warcraft> warcrafts = warcraftEquipBag.getAllWarcraft();
				JSONArray warcraftProps = new JSONArray();
				for(Warcraft warcraft : warcrafts){
					warcraftProps.add(warcraft.toProp());
				}
				if(warcraftProps.size() == 0){
					role.setWarcraftProps("");
				}else{
					role.setWarcraftProps(warcraftProps.toString());
				}
				role.sendInfomationMessage(player);
				player.sendMessage(inv.getPetEquipBagInfoMsg(role.getUUID()));
			}
		}else{
			player.sendErrorMessage(CommonLangConstants_10000.OBJECT_NOT_EXIST);
		}
	}

	private List<PetInfomation> getPetInfomations(Human lookUpedHuman) {
		List<PetInfomation> petInfomations = new ArrayList<PetInfomation>();
		for (Pet pet : lookUpedHuman.getPetManager().getPets()) {
			PetInfomation petInfomation = new PetInfomation();
			petInfomation.setInBattle(pet.getPropertyManager().getIsInBattle());
			petInfomation.setLevel(pet.getLevel());
			petInfomation.setName(pet.getName());
			petInfomation.setPetSn(pet.getPetSn());
			petInfomation.setVocation(pet.getVocationType().getCode());
			petInfomation.setSex(pet.getSex().getCode());
			petInfomation.setSkeltonId(pet.getSkeletonId());
			petInfomation.setUuid(pet.getUUID());
			petInfomations.add(petInfomation);
		}
		return petInfomations;
	}

	public void transferVocationInformation(Player player,
			CGTransferVocationInformationMessage message) {
		Player lookUpedPlayer = Globals.getOnlinePlayerService().getPlayerById(
				message.getPlayUUID());
		if(lookUpedPlayer!=null){
			Role role =lookUpedPlayer.getHuman().getRole(message.getRoleSn());
			role.sendSkillMessage(player);
		}else{
			player.sendErrorMessage(CommonLangConstants_10000.OBJECT_NOT_EXIST);
		}
		
       
	}

	/**
	 * 加载离线用户信息，仅Human表信息
	 * @param humanSn
	 * @return
	 */
	private Human loadOfflineHuman(String humanSn) {
		HumanEntity entity = Globals.getDaoService().getHumanDao().get(humanSn);
		if (entity != null) {
			//构造目标Human对象
			Human targetHuman = new Human();
			targetHuman.fromEntity(entity);
			return targetHuman;
		} else {
			return null;
		}
	}
	
	/**
	 * 加载离线用户信息，包括Human表和Pet表信息
	 * @param humanSn
	 * @return
	 */
	private Human loadOfflinePet(String humanSn) {
		Human human = loadOfflineHuman(humanSn);
		if (human != null) {
			human.getPetManager().loadOffline();
			return human;
		} else {
			return null;
		}
	}
	
	/**
	 * 加载离线用户全部需要信息，包括Human、Pet、Inventory、兵法
	 * @param humanSn
	 * @return
	 */
	private Human loadOfflineAll(String humanSn) {
		HumanEntity entity = Globals.getDaoService().getHumanDao().get(humanSn);
		if (entity != null) {
			//构造目标Human对象
			Human targetHuman = new Human();
			targetHuman.loadOffline(entity);
			targetHuman.getWarcraftInventory().load();
			return targetHuman;
		} else {
			return null;
		}
	}
}
