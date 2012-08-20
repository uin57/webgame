package com.pwrd.war.gameserver.quest;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.FinishedQuestEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 完成任务数据库管理器
 * 
 * 
 */
public class FinishedQuestUpdater implements POUpdater {

	@Override
	public void delete(PersistanceObject<?,?> obj) {

	}

	@Override
	public void save(PersistanceObject<?,?> obj) {
		FinishedQuest quest = (FinishedQuest) obj;		
		IIoOperation _oper = new SaveObjectOperation<FinishedQuestEntity, FinishedQuest>(
				quest, Globals.getDaoService().getFinishedQuestDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
