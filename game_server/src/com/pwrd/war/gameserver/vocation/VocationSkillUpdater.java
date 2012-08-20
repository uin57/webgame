package com.pwrd.war.gameserver.vocation;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.VocationSkillEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.DeleteEntityOperation;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

public class VocationSkillUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?,?> obj) {
		VocationSkill vocationSkill = (VocationSkill) obj;		
		IIoOperation _oper = new SaveObjectOperation<VocationSkillEntity, VocationSkill>(vocationSkill, Globals.getDaoService().getVacationSkillDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
	
	@Override
	public void delete(PersistanceObject<?,?> obj) {
		VocationSkill vocationSkill = (VocationSkill) obj;	
		final String _charId = vocationSkill.getOwner().getUUID();
		final VocationSkillEntity _dailyQuestEntity = new VocationSkillEntity();
		_dailyQuestEntity.setId(vocationSkill.getDbId());
		IIoOperation _update = new DeleteEntityOperation<VocationSkillEntity>(
				_dailyQuestEntity, _charId, Globals.getDaoService().getVacationSkillDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_update);		
	}
}
