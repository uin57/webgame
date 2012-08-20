/**
 * 
 */
package com.pwrd.war.gameserver.promptButton.service;

import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.promptButton.model.PromptButton;
import com.pwrd.war.gameserver.promptButton.msg.CGDeletePromptButton;
import com.pwrd.war.gameserver.promptButton.msg.GCPromptButton;

/**
 * 
 * 提示按钮服务类
 * @author dendgan
 *
 */
public class PromptButtonService {

	/** 发送提示按钮消息 */
	public void sendButtonMessageToHuman(String content,String charId,int functionId){
		Player player = Globals.getOnlinePlayerService().getPlayerById(charId);
		PromptButton promptButton = this.generateButtonMessage(content, charId, functionId);
		if(player != null){
			GCPromptButton msg = new GCPromptButton();
			msg.setContent(content);
			msg.setFunctionId(functionId);
			msg.setId(promptButton.getDbId());
			player.sendMessage(msg);
			player.getHuman().getPromptButtonManager().addPromptButton(promptButton);
		}
	}
	
	/**
	 * 生成提示按钮消息
	 * @param content
	 * @param charId
	 */
	public PromptButton generateButtonMessage(String content,String charId,int functionId){
		PromptButton promptButton = new PromptButton();
		promptButton.setDbId(KeyUtil.UUIDKey());
		promptButton.setCharId(charId);
		promptButton.setContent(content);
		promptButton.setFunctionId(functionId);
		promptButton.setTime(System.currentTimeMillis());
		promptButton.setModified();
		return promptButton;
	}
	
	/**
	 * 删除提示按钮
	 * @param player
	 * @param msg
	 */
	public void deletePromptButton(Player player,CGDeletePromptButton msg){
		if(!player.isOnline()){
			return;
		}
		String id = msg.getId();
		PromptButton promptButton = player.getHuman().getPromptButtonManager().getPromptButton(id);
		if(promptButton == null){
			return;
		}
		player.getHuman().getPromptButtonManager().removePromptButton(id);
		promptButton.delete();
	}
}
