package com.pwrd.war.gameserver.story;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.FinishedStoryEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 完成任务数据库管理器
 * 
 * 
 */
public class FinishedStoryUpdater implements POUpdater {

	@Override
	public void delete(PersistanceObject<?,?> obj) {

	}

	@Override
	public void save(PersistanceObject<?,?> obj) {
		FinishedStory story = (FinishedStory) obj;		
		IIoOperation _oper = new SaveObjectOperation<FinishedStoryEntity, FinishedStory>(
				story, Globals.getDaoService().getFinishedStoryDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
