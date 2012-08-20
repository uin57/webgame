package com.pwrd.war.gameserver.common.listener;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.gameserver.common.event.FormChangeEvent;
import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.form.FormPosition;
import com.pwrd.war.gameserver.form.msg.GCForm;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

public class FormChangeListener implements IEventListener<FormChangeEvent> {

	@Override
	public void fireEvent(FormChangeEvent event) {
//		Role role=event.getInfo();
//		Human human =null;
//		if(role instanceof Human){
//			human=(Human) role;
//		}else if(role instanceof Pet){
//			human=((Pet) role).getOwner();
//		}else{
//			return;
//		}
//		for(BattleForm form:human.getBattleForms()){
//			List<String> petSns=new ArrayList<String>();
//			for(String petSn:form.getPetSns()){
//				if(StringUtils.isNotEmpty(petSn)){
//					petSns.add(petSn);
//				}
//			}
//			for(FormPosition position:form.getFormPositions()){
//				if(role.getUUID().equals(position.getPetSn())){
//					Role roleVal=human.getRole(position.getPetSn());
//					if(!petSns.contains(roleVal.getUUID())&&StringUtils.isNotEmpty(roleVal.getPetSkill())){
//						for(int i=0;i<form.getPetSns().length;i++){
//							if(StringUtils.isEmpty(form.getPetSns()[i])){
//								form.getPetSns()[i]=roleVal.getUUID();
//								petSns.add(roleVal.getUUID());
//								form.setModified();
//								break;
//							}
//						}
//					}
//				}
//			}
//		}
//		human.sendMessage(new GCForm(human.getPropertyManager().isOpenForm(),human.getBattleForms()));
	}

}
