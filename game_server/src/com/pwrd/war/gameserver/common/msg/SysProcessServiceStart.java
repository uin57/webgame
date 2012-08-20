package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.gameserver.common.Globals;

public class SysProcessServiceStart extends SysInternalMessage{

	@Override
	public void execute() {
		Globals.getProcessService().startService();
	}
	
	@Override
	public String getTypeName() {
		return "SysProcessServiceStart";
	}

}
