package com.pwrd.war.gameserver.command.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.form.FormPosition;
import com.pwrd.war.gameserver.form.template.FormTemplate;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * GM命令：将一个武将增加到阵型中，顺序排列在阵型下一个位置上
 * @author zy
 *
 */
public class AddPetToForm implements IAdminCommand<ISession> {

	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		
		
//		Player player = ((GameClientSession) playerSession).getPlayer();
//		System.out.println(Arrays.toString(commands));
//		BattleForm form = player.getHuman().getDefaultForm();
//		form.setFormLevel(10);
//		
//		List<Pet> pets = player.getHuman().getPetManager().getPets();
//		FormTemplate template = Globals.getFormService().getFormTemplate(
//				form.getFormSn(), form.getFormLevel());
//		List<FormPosition> formPositions = new ArrayList<FormPosition>();
//		List<String> petSns=new ArrayList<String>();
//		FormPosition position = new FormPosition();
//		position.setPetSn(player.getHuman().getUUID());
//		position.setFormId(form.getDbId());
//		position.setPosition(template.getPosition1());
//		position.setOwner(player.getHuman());
//		Globals.getDaoService().getFormPositionDao().save(position.toEntity());
//		petSns.add(player.getHuman().getUUID());
//		formPositions.add(position);
//		
//		for (FormPosition positionVal : form.getFormPositions()) {
//			Globals.getDaoService().getFormPositionDao()
//					.delete(positionVal.toEntity());
//		}
//		for (int i = 0; i < pets.size() && i < 5; i++) {
//			Pet pet = pets.get(i);
//			FormPosition petPosition = new FormPosition();
//			petPosition.setPetSn(pet.getUUID());
//			petSns.add(pet.getUUID());
//			petPosition.setFormId(form.getDbId());
//			if (i == 0&&template.getPosition2()!=0) {
//				petPosition.setPosition(template.getPosition2());
//			} else if (i == 1&&template.getPosition3()!=0) {
//				petPosition.setPosition(template.getPosition3());
//			} else if (i == 2&&template.getPosition4()!=0) {
//				petPosition.setPosition(template.getPosition4());
//			} else if (i == 3&&template.getPosition5()!=0) {
//				petPosition.setPosition(template.getPosition5());
//			}
//			petPosition.setOwner(player.getHuman());
//			Globals.getDaoService().getFormPositionDao()
//					.save(petPosition.toEntity());
//			formPositions.add(petPosition);
//		}
//		form.setFormPositions(formPositions.toArray(new FormPosition[0]));
//		form.setPetSns(petSns.toArray(new String[0]));
//		form.setModified();
	}

	@Override
	public String getCommandName() {
		return CommandConstants.ADD_PET_TO_FORM;
	}

}
