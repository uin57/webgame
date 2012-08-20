package com.pwrd.war.gameserver.human.cooldown;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.CooldownEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.DeleteEntityOperation;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

public class CooldownUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?,?> obj) {
		Cooldown skillGroup = (Cooldown) obj;		
		IIoOperation _oper = new SaveObjectOperation<CooldownEntity, Cooldown>(skillGroup, Globals.getDaoService().getCooldownDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
	
	@Override
	public void delete(PersistanceObject<?,?> obj) {
		Cooldown cooldown = (Cooldown) obj;	
		final String _charId = cooldown.getOwner().getUUID();
		final CooldownEntity cooldownEntity = new CooldownEntity();
		cooldownEntity.setId(cooldown.getDbId());
		cooldownEntity.setCoolType(cooldown.getCoolType());
		cooldownEntity.setIndex(cooldown.getIndex());
		IIoOperation _update = new DeleteEntityOperation<CooldownEntity>(
				cooldownEntity, _charId, Globals.getDaoService().getCooldownDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_update);		
	}

}
