package com.pwrd.war.gameserver.tree;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.TreeInfoEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 将星云路数据库管理器
 * 
 * 
 */
public class HumanTreeInfoUpdater implements POUpdater {

	@Override
	public void delete(PersistanceObject<?,?> obj) {

	}

	@Override
	public void save(PersistanceObject<?,?> obj) {
		HumanTreeInfo info = (HumanTreeInfo) obj;		
		IIoOperation _oper = new SaveObjectOperation<TreeInfoEntity, HumanTreeInfo>(
				info, Globals.getDaoService().getTreeInfoDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
