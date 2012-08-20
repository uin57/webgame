package com.pwrd.war.gameserver.quest;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.DoingQuestEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.DeleteEntityOperation;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 正在做的任务数据库管理器
 * 
 * 
 */
public class DoingQuestUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?,?> obj) {
		DoingQuest quest = (DoingQuest) obj;		
		IIoOperation _oper = new SaveObjectOperation<DoingQuestEntity, DoingQuest>(quest, Globals.getDaoService().getDoingQuestDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
	
	@Override
	public void delete(PersistanceObject<?,?> obj) {
		DoingQuest quest = (DoingQuest) obj;
		final String _charId = quest.getOwner().getUUID();
		final DoingQuestEntity _doingQuestEntity = new DoingQuestEntity();
		_doingQuestEntity.setId(quest.getDbId());
		_doingQuestEntity.setCharId(quest.getCharId());
		IIoOperation _update = new DeleteEntityOperation<DoingQuestEntity>(
				_doingQuestEntity, _charId, Globals.getDaoService().getDoingQuestDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_update);
		
	}
}
