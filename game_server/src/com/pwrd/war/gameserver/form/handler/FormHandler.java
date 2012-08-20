package com.pwrd.war.gameserver.form.handler;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.form.msg.CGForm;
import com.pwrd.war.gameserver.form.msg.CGFormPosition;
import com.pwrd.war.gameserver.player.Player;

public class FormHandler {

//	public void handlePetSkillOrder(Player player, CGPetSkillOrder message) {
//		Globals.getFormService().modifyPetSkillOrder(player, message);
//	}

	public void handleFormPosition(Player player, CGFormPosition message) {
		Globals.getFormService().modifyFormOrder(player, message);
	}
	
	public void handleForm(Player player, CGForm message) {
		Globals.getFormService().getForm(player);
	}

//	public void handleDefaultForm(Player player, CGDefaultForm message) {
//		BattleForm defaultBattleForm = player.getHuman().getDefaultForm();
//		Human human = player.getHuman();
//		for (FormPosition position : defaultBattleForm.getFormPositions()) {
//			if (StringUtils.isNotEmpty(position.getPetSn())) {
//				Role role = human.getRole(position.getPetSn());
//				if (role instanceof Pet) {
//					Pet pet = (Pet) role;
//					if (pet.getPropertyManager().getIsInBattle()) {
//						pet.getPropertyManager().setIsInBattle(false);
//						pet.snapChangedProperty(true);
//					}
//				}
//			}
//		}
//		for (BattleForm battleForm : player.getHuman().getBattleForms()) {
//			if (message.getFormSn().equals(battleForm.getFormSn())) {
//				for (FormPosition position : battleForm.getFormPositions()) {
//					if (StringUtils.isNotEmpty(position.getPetSn())) {
//						Role role = human.getRole(position.getPetSn());
//						if (role instanceof Pet) {
//							Pet pet = (Pet) role;
//							if (!pet.getPropertyManager().getIsInBattle()) {
//								pet.getPropertyManager().setIsInBattle(true);
//								pet.snapChangedProperty(true);
//							}
//						}
//					}
//				}
//				battleForm.setValid(true);
//				player.getHuman().setDefaultForm(battleForm);
//				player.sendMessage(new GCDefaultForm(battleForm.getFormSn()));
//			} else {
//				battleForm.setValid(false);
//			}
//			battleForm.setModified();
//		}
//
//	}
//
//	public void handleFormUpdate(Player player, CGFormUpdate message) {
//		//冷却时间
//		boolean rs =player.getHuman().getCooldownManager().hasEmptyCool(1, CoolType.DEVELOP);
//		if(!rs){
//			player.sendErrorMessage("队列冷却中");
//			return ;
//		}
//		int code = checkUpdate(player, message);
//		if (code == 0) {
//			for (BattleForm battleForm : player.getHuman().getBattleForms()) {
//				if (message.getFormSn().equals(battleForm.getFormSn())) {
//					if (battleForm.getFormLevel() + 1 == message.getFormLevel()) {
//						Human human = player.getHuman();
//						FormTemplate formTemplate = Globals.getFormService()
//								.getFormTemplate(message.getFormSn(),
//										battleForm.getFormLevel());
//						battleForm.setFormLevel(message.getFormLevel());
//						battleForm.setModified();
//						GCFormUpdate formUpdateMessage = new GCFormUpdate(
//								battleForm.getFormSn(),
//								battleForm.getFormLevel());
//						human.sendMessage(formUpdateMessage);
//						human.costCoins(formTemplate.getNeedMoney(),
//									true, null, null, null, 0);
//						human.getPropertyManager().setSee(
//								player.getHuman().getPropertyManager().getSee()
//										- formTemplate.getNeedSee());
//						human.snapChangedProperty(true);
//					}
//				}
//			}
//			CoolDownCostTemplate coolTmp = Globals.getHumanService().getCoolTemplateByCoolType(CoolType.DEVELOP, SubCoolType.SUB_TWO);
//			//添加冷却队列
//			player.getHuman().getCooldownManager().put(1, CoolType.DEVELOP, Globals.getTimeService().now(), 
//					Globals.getTimeService().now() + coolTmp.getNeedTime(message.getFormLevel()) * TimeUtils.MIN);
//		}else{
//			GCNotifyException gcMessage = new GCNotifyException(
//					code, Globals.getLangService().read(code));
//			player.sendMessage(gcMessage);
//		}
//	}
//
//	private int checkUpdate(Player player, CGFormUpdate message) {
//		FormTemplate formTemplate = Globals.getFormService().getFormTemplate(
//				message.getFormSn(), message.getFormLevel());
//		if (formTemplate == null) {
//			player.sendErrorMessage("阵型等级不正确  阵型：" + message.getFormSn() + "等级："
//					+ message.getFormLevel());
//			return FormLangContants_90000.NOT_SUCH_FORM;
//		}
//		HumanPropertyManager humanPropertyManager = player.getHuman()
//				.getPropertyManager();
//		if (humanPropertyManager.getSee() < formTemplate.getNeedSee()) {
//			player.sendErrorMessage("阅历不够");
//			return FormLangContants_90000.NOT_ENOUGH_SEE;
//		}
//		if (humanPropertyManager.getCoins() < formTemplate.getNeedMoney()) {
//			player.sendErrorMessage("金钱不够");
//			return FormLangContants_90000.NOT_ENOUGH_MONEY;
//		}
//		return 0;
//	}

}
