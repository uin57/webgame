package com.pwrd.war.gameserver.player.async;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;

/**
 * 向local平台汇报用户退出
 *
 *
 */
public class ReportLogout2Local implements IIoOperation {

	/** 退出的用户 */
	private Player player;
	
	public ReportLogout2Local(Player player) {
		this.player = player;
	}
	
	@Override
	public int doIo() {
//		Globals.getSynLocalService().logout(
//				player.getPassportId(), player.getClientIp());
		return IIoOperation.STAGE_IO_DONE;
	}

	@Override
	public int doStart() {
		return IIoOperation.STAGE_START_DONE;
	}

	@Override
	public int doStop() {
		return IIoOperation.STAGE_STOP_DONE;
	}

}
