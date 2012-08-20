/**
 * 
 */
package com.pwrd.war.gameserver.promptButton.handler;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.promptButton.msg.CGDeletePromptButton;

/**
 * @author DevUser
 *
 */
public class PromptButtonHandler {

	public void handleDeletePromptButton(Player player,CGDeletePromptButton msg){
		if(!player.isOnline()){
			return;
		}
		Globals.getPromptButtonService().deletePromptButton(player, msg);
	}
}
