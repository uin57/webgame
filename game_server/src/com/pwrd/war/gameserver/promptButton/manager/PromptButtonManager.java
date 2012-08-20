package com.pwrd.war.gameserver.promptButton.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.db.model.PromptButtonEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.promptButton.model.PromptButton;
import com.pwrd.war.gameserver.promptButton.msg.GCPromptButton;

/**
 * 
 * 角色提示按钮管理器
 * @author dengdan
 *
 */
public class PromptButtonManager {

	private Human human;
	private Map<String,PromptButton> promptButtons;
	
	public PromptButtonManager(Human human){
		this.human = human;
		promptButtons = new HashMap<String,PromptButton>();
	}
	
	/**
	 * 初始加载提示按钮消息发送到前端，然后清除
	 */
	public void init(){
		List<PromptButtonEntity> list = Globals.getDaoService().getPromptButtonDao().queryButtonMessageByCharId(human.getCharId());
		if(list == null || list.size() == 0){
			return;
		}
		for(PromptButtonEntity entity : list){
			PromptButton promptButton = new PromptButton();
			promptButton.fromEntity(entity);
			GCPromptButton msg = new GCPromptButton();
			msg.setContent(promptButton.getContent());
			msg.setFunctionId(promptButton.getFunctionId());
			msg.setId(promptButton.getDbId());
			human.getPlayer().sendMessage(msg);
			promptButtons.put(promptButton.getDbId(), promptButton);
		}
	}
	
	/**
	 * 获取指定的提示按钮
	 * @param id
	 * @return
	 */
	public PromptButton getPromptButton(String id){
		if(!this.promptButtons.containsKey(id)){
			return null;
		}
		return this.promptButtons.get(id);
	}
	
	/**
	 * 增加1个提示按钮
	 * @param promptButton
	 */
	public void addPromptButton(PromptButton promptButton){
		this.promptButtons.put(promptButton.getDbId(), promptButton);
	}
	
	/**
	 * 删除1个提示按钮
	 * @param promptButtonId
	 */
	public void removePromptButton(String promptButtonId){
		if(!this.promptButtons.containsKey(promptButtonId)){
			return;
		}
		this.promptButtons.remove(promptButtonId);
	}
}
