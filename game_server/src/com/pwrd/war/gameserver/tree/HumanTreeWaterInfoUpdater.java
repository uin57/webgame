package com.pwrd.war.gameserver.tree;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.TreeWaterEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 将星云路数据库管理器
 * 
 * 
 */
public class HumanTreeWaterInfoUpdater implements POUpdater {

	@Override
	public void delete(PersistanceObject<?,?> obj) {

	}

	@Override
	public void save(PersistanceObject<?,?> obj) {
		HumanTreeWaterInfo info = (HumanTreeWaterInfo) obj;		
		IIoOperation _oper = new SaveObjectOperation<TreeWaterEntity, HumanTreeWaterInfo>(
				info, Globals.getDaoService().getTreeWaterDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
