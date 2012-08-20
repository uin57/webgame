package com.pwrd.war.gameserver.buff.domain;

import java.util.Map;

import com.pwrd.war.db.model.BufferEntity;
import com.pwrd.war.gameserver.buff.enums.BuffBigType;
import com.pwrd.war.gameserver.buff.enums.ModifyType;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.ItemLangConstants_20000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.template.ConsumeItemTemplate;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

public class ChangeBuffer extends Buffer {
 

	@Override
	public boolean addBuffer(Human human) { 
		Map<BuffBigType, Buffer> buffers = human.getBuffers(); 
		if (isContain(buffers)) { 
			//直接删除
			human.sendMessage(generatorMessage(human, ModifyType.delete));
			 
		} 
		BufferEntity entity = this.toEntity();
		Globals.getDaoService().getBufferDao().save(entity);
		this.setId(entity.getId());
		this.setInDb(true);
		buffers.put(this.getBuffBigType(), this);
		human.sendMessage(generatorMessage(human, ModifyType.add));
		return true;
	}
	@Override
	public double modify(Role role, int number) {
		Human human=null;
		if(role instanceof Human){
			human=(Human) role;
		}else if(role instanceof Pet){
			human=((Pet)role).getOwner();
		}else{
			return number;
		}
		if (getEndTime() < System.currentTimeMillis()) {
			human.sendMessage(generatorMessage(human,ModifyType.delete));
			//广播消息
			human.getPlayer().sendInfoChangeMessageToScene();
		}
		return 0;
	}
	public boolean addBuffer(Human human, ConsumeItemTemplate itemTemplate){
	 
		 
		super.setBufferName(
				Globals.getLangService().read(ItemLangConstants_20000.BUFF_CHANGE_NAME, itemTemplate.getParam3()));
		super.setBufferDescription( 
				Globals.getLangService().read(ItemLangConstants_20000.BUFF_CHANGE_DESC,itemTemplate.getParam3(), itemTemplate.getParam2() ));
		super.setExt(itemTemplate.getParam1());
		return this.addBuffer(human);
	}
	@Override
	public void onDelete() { 
		super.onDelete();
		getOwner().getPlayer().sendInfoChangeMessageToScene();
	}
 
	
}
