package com.pwrd.war.gameserver.player;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.scene.PlayerLeaveSceneCallback;

/**
 * 玩家离开场景后设置其在之前的场景，只是设置数据，适用于离线时恢复到之前场景
 * @author xf
 */
public class LetPlayerGotoBeforeSceneCallBack implements
		PlayerLeaveSceneCallback {

	@Override
	public void afterLeaveScene(Player player) {
		Human human = player.getHuman();
		
		String sceneId = human.getPropertyManager().getBeforeSceneId();
		if(StringUtils.isEmpty(sceneId)){
			return;
		}
		int x = human.getPropertyManager().getBeforeX();
		int y = human.getPropertyManager().getBeforeY();
	 
		human.setX(x);
		human.setY(y);
		human.setSceneId(sceneId);
		
		human.getPropertyManager().setBeforeSceneId("");
		human.getPropertyManager().setBeforeX(0);
		human.getPropertyManager().setBeforeY(0);
		human.setModified();

	}

}
