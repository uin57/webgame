package com.pwrd.war.gameserver.common.listener;

import java.util.List;
import java.util.Map;

import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.buff.enums.BufferType;
import com.pwrd.war.gameserver.common.event.HPReduceEvent;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

public class HPRedeceListener implements IEventListener<HPReduceEvent> {

	@Override
	public void fireEvent(HPReduceEvent event) {
		Role role = event.getInfo();
		Human human = null;
		if (role instanceof Human) {
			human = (Human) role;
		} else if (role instanceof Pet) {
			human = ((Pet) role).getOwner();
		} else {
			return;
		}
		
		List<Buffer> buffs = human.getBufferByType(BufferType.medicinal);
		for(Buffer b : buffs){
			b.modify(role, 0);
		}
	}

}
