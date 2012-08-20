/**
 * 
 */
package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.promptButton.service.PromptButtonService;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 
 * 提示按钮GM命令
 * @author dengdan
 *
 */
public class PromptButtonCmd implements IAdminCommand<ISession> {

	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			int functionId = Integer.parseInt(commands[0]);
			String charId = commands[1];
			if("me".equals(charId)){
				charId = player.getHuman().getCharId();
			}
			String content = commands[2];
			if(charId == null || content == null){
				return;
			}
			Globals.getPromptButtonService().sendButtonMessageToHuman(content, charId,functionId);
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "promptButton";
	}
}
