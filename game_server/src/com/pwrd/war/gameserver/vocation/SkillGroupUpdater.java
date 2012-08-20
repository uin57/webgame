package com.pwrd.war.gameserver.vocation;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.SkillGroupEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.DeleteEntityOperation;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

public class SkillGroupUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?,?> obj) {
		SkillGroup skillGroup = (SkillGroup) obj;		
		IIoOperation _oper = new SaveObjectOperation<SkillGroupEntity, SkillGroup>(skillGroup, Globals.getDaoService().getSkillGroupDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
	
	@Override
	public void delete(PersistanceObject<?,?> obj) {
		SkillGroup skillGroup = (SkillGroup) obj;	
		final String _charId = skillGroup.getOwner().getUUID();
		final SkillGroupEntity _dailyQuestEntity = new SkillGroupEntity();
		_dailyQuestEntity.setId(skillGroup.getDbId());
		IIoOperation _update = new DeleteEntityOperation<SkillGroupEntity>(
				_dailyQuestEntity, _charId, Globals.getDaoService().getSkillGroupDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_update);		
	}
}
