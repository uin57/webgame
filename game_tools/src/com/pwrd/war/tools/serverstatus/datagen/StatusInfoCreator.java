package com.pwrd.war.tools.serverstatus.datagen;

import com.pwrd.war.common.constants.ServerTypes;
import com.pwrd.war.tools.serverstatus.AgentServerStatus;
import com.pwrd.war.tools.serverstatus.DbsServerStatus;
import com.pwrd.war.tools.serverstatus.GameServerStatus;
import com.pwrd.war.tools.serverstatus.LoginServerStatus;
import com.pwrd.war.tools.serverstatus.StatusInfo;
import com.pwrd.war.tools.serverstatus.WorldServerStatus;

/**
 *状态信息创建器
 * 
 * 
 */
public class StatusInfoCreator {

	/**
	 * 根据服务器类型创建服务器状态
	 * 
	 * @param serverType
	 * @return
	 */
	public static StatusInfo createStatusInfo(String serverType) {
		int serverIntType = Integer.parseInt(serverType);
		switch (serverIntType) {
		case ServerTypes.DBS:
			return new DbsServerStatus();
		case ServerTypes.GAME:
			return new GameServerStatus();
		case ServerTypes.LOGIN:
			return new LoginServerStatus();
		case ServerTypes.WORLD:
			return new WorldServerStatus();
		case ServerTypes.AGENT:
			return new AgentServerStatus();
		default:
			return null;

		}
	}
}
