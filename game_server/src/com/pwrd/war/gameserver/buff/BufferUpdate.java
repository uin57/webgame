package com.pwrd.war.gameserver.buff;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.model.BufferEntity;
import com.pwrd.war.gameserver.buff.domain.Buffer;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.DeleteEntityOperation;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

public class BufferUpdate  implements POUpdater {

	@Override
	public void save(PersistanceObject<?,?> obj) {
		Buffer buffer = (Buffer) obj;		
		IIoOperation _oper = new SaveObjectOperation<BufferEntity, Buffer>(buffer, Globals.getDaoService().getBufferDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
	
	@Override
	public void delete(PersistanceObject<?,?> obj) {
		Buffer buffer = (Buffer) obj;
		final String _charId = buffer.getOwner().getUUID();
		final BufferEntity bufferEntity = new BufferEntity();
		bufferEntity.setId(buffer.getDbId());
		IIoOperation _update = new DeleteEntityOperation<BufferEntity>(bufferEntity, _charId, Globals.getDaoService().getBufferDao());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_update);		
	}
}