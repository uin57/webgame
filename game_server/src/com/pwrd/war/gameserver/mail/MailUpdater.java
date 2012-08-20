package com.pwrd.war.gameserver.mail;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.object.PersistanceObject;
import com.pwrd.war.db.dao.MailDao;
import com.pwrd.war.db.model.MailEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.POUpdater;
import com.pwrd.war.gameserver.common.db.operation.DeleteEntityOperation;
import com.pwrd.war.gameserver.common.db.operation.SaveObjectOperation;

public class MailUpdater implements POUpdater  {
	
	@Override
	public void save(PersistanceObject<?, ?> obj) {
		MailDao mailDao = Globals.getDaoService().getMailDao();
		IIoOperation _oper = new SaveObjectOperation<MailEntity, MailInstance>((MailInstance) obj, mailDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);	
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		MailDao mailDao = Globals.getDaoService().getMailDao();
		MailEntity mail = (MailEntity) obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<MailEntity>(mail, mail
				.getCharId(), mailDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
