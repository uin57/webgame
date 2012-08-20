/**
 * 
 */
package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 发送公告命令
 * @author dengdan
 *
 */
public class SendNoticeCmd implements IAdminCommand<ISession> {

	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		/**
		 * 格式:!notice send arg1 arg2
		 * arg1:内容
		 * arg2 ：公告类型,为1~8的数字(参见NoticeTypes)多个公告以逗号分隔
		 */
		try {
			String cmd = commands[0];
			String content = commands[1];
			String[] noticeType = commands[2].split(",");
			Short[] noticeTypes = new Short[noticeType.length];
			for(int i = 0;i<noticeType.length;i++){
				noticeTypes[i] = Short.parseShort(noticeType[i]);
			}
			if("send".equals(cmd)){
				Globals.getNoticeService().sendMultiNotice(content, noticeTypes);
			}
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "notice";
	}
}
