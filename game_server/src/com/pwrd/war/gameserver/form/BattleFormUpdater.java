package com.pwrd.war.gameserver.form;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.FormEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.DeleteEntityOperation;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

public class BattleFormUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?,?> obj) {
		BattleForm battleForm = (BattleForm) obj;		
		IIoOperation _oper = new SaveObjectOperation<FormEntity, BattleForm>(battleForm, Globals.getDaoService().getFormDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
	
	@Override
	public void delete(PersistanceObject<?,?> obj) {
		BattleForm battleForm = (BattleForm) obj;
		final String _charId = battleForm.getOwner().getUUID();
		final FormEntity formEntity = new FormEntity();
		formEntity.setId(battleForm.getDbId());
		IIoOperation _update = new DeleteEntityOperation<FormEntity>(formEntity, _charId, Globals.getDaoService().getFormDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_update);		
	}
}
