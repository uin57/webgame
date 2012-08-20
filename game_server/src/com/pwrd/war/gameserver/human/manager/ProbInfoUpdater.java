package com.pwrd.war.gameserver.human.manager;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.ProbEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;
import com.pwrd.war.gameserver.human.domain.ProbInfo;

public class ProbInfoUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?,?> obj) {
		ProbInfo skillGroup = (ProbInfo) obj;		
		IIoOperation _oper = new SaveObjectOperation<ProbEntity, ProbInfo>(skillGroup,
				Globals.getDaoService().getProbInfoDAO());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
	
	@Override
	public void delete(PersistanceObject<?,?> obj) {
		 	
	}

}
