package com.pwrd.war.gameserver.telnet.command;

import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.mina.common.IoSession;

import com.pwrd.war.common.model.ServerStatus;
import com.pwrd.war.gameserver.common.Globals;

/**
 * 取得 Game Server 的状态
 * 
 * @author <a href="mailto:dongyong.wang@opi-corp.com">wang dong yong<a>
 * 
 */
public class GSStatusCommand extends LoginedTelnetCommand {
	public GSStatusCommand() {
		super("GS_STATUS");
	}

	@Override
	protected void doExec(String command, Map<String, String> params, IoSession session) {
		JSONArray _arrays = new JSONArray();

		ServerStatus _status = Globals.getServerStatus();

		if (_status != null) {
			_arrays.add(_status.getStatusJSON());
		}

		this.sendMessage(session, _arrays.toString());
	}
}
