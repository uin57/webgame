package com.pwrd.war.gameserver.item;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.dao.ItemDao;
import com.pwrd.war.db.model.ItemEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.DeleteEntityOperation;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 物品数据管理
 * 
 */
public class ItemUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?,?> obj) {
		ItemDao itemDao = Globals.getDaoService().getItemDao();
		IIoOperation _oper = new SaveObjectOperation<ItemEntity, Item>((Item) obj, itemDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?,?> obj) {
		ItemDao itemDao = Globals.getDaoService().getItemDao();
		ItemEntity item = (ItemEntity) obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<ItemEntity>(item, item
				.getCharId(), itemDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
