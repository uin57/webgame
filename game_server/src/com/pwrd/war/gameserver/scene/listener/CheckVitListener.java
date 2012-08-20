package com.pwrd.war.gameserver.scene.listener;

import com.pwrd.war.gameserver.common.listener.Listenable;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.scene.Scene;
import com.pwrd.war.gameserver.vitality.msg.GCVitZero;

/**
 * 切换场景检查体力的监听器
 * @author xf
 */
public class CheckVitListener implements SceneListener {

	@Override
	public void onRegisted(Listenable<?> listening) {

	}

	@Override
	public void onDeleted(Listenable<?> listening) {

	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public void afterHumanEnter(Scene scene, Human human) {
		if(human.getPropertyManager().getVitality() == 0){
			//发送体力为0的消息
			GCVitZero msg = new GCVitZero();
			human.sendMessage(msg);
		}
	}

	@Override
	public void beforeHumanLeave(Scene scene, Human human) {

	}

	@Override
	public void leaveOnLogoutSaving(Scene scene, Human human) {

	}

}
