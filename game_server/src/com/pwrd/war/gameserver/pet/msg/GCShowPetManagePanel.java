package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 显示武将管理面板
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCShowPetManagePanel extends GCMessage{
	
	/** 面板的全局信息 */
	private com.pwrd.war.common.model.pet.PetManagePanelInfo panelInfo;
	/** 面板的全局信息 */
	private com.pwrd.war.common.model.pet.PetListInfo[] petList;

	public GCShowPetManagePanel (){
	}
	
	public GCShowPetManagePanel (
			com.pwrd.war.common.model.pet.PetManagePanelInfo panelInfo,
			com.pwrd.war.common.model.pet.PetListInfo[] petList ){
			this.panelInfo = panelInfo;
			this.petList = petList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		panelInfo = new com.pwrd.war.common.model.pet.PetManagePanelInfo();
					panelInfo.setPetsCount(readInteger());
							panelInfo.setMaxPetsCount(readInteger());
				count = readShort();
		count = count < 0 ? 0 : count;
		petList = new com.pwrd.war.common.model.pet.PetListInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.common.model.pet.PetListInfo obj = new com.pwrd.war.common.model.pet.PetListInfo();
			obj.setId(readInteger());
			obj.setName(readString());
			obj.setPhoto(readInteger());
			obj.setDesc(readString());
			obj.setHireStatus(readInteger());
			obj.setGroup(readInteger());
			petList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(panelInfo.getPetsCount());
		writeInteger(panelInfo.getMaxPetsCount());
		writeShort(petList.length);
		for(int i=0; i<petList.length; i++){
			writeInteger(petList[i].getId());
			writeString(petList[i].getName());
			writeInteger(petList[i].getPhoto());
			writeString(petList[i].getDesc());
			writeInteger(petList[i].getHireStatus());
			writeInteger(petList[i].getGroup());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_PET_MANAGE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_PET_MANAGE_PANEL";
	}

	public com.pwrd.war.common.model.pet.PetManagePanelInfo getPanelInfo(){
		return panelInfo;
	}
		
	public void setPanelInfo(com.pwrd.war.common.model.pet.PetManagePanelInfo panelInfo){
		this.panelInfo = panelInfo;
	}

	public com.pwrd.war.common.model.pet.PetListInfo[] getPetList(){
		return petList;
	}

	public void setPetList(com.pwrd.war.common.model.pet.PetListInfo[] petList){
		this.petList = petList;
	}	
}