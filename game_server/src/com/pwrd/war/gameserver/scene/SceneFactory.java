package com.pwrd.war.gameserver.scene;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.gameserver.activity.boss.AfterEnterBossSceneListener;
import com.pwrd.war.gameserver.activity.boss.BossActivity;
import com.pwrd.war.gameserver.activity.ggzj.AfterEnterGgzjSceneListener;
import com.pwrd.war.gameserver.activity.ggzj.GGZJActivity;
import com.pwrd.war.gameserver.monster.MonsterService;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.scene.vo.SceneInfoVO;
import com.pwrd.war.gameserver.scene.vo.SceneInfoVO.SceneType;

/**
 * 场景工厂类
 * 
 * @author haijiang.jin
 *
 */
public final class SceneFactory {
	/** 类默认构造器 */
	private SceneFactory() {
	}

	/**
	 * 创建场景
	 * 
	 * @param tpl 
	 * @param serv
	 * @return
	 * 
	 */
	public static Scene createScene(SceneService service, SceneInfoVO tpl, OnlinePlayerService serv, MonsterService monsterService) {		
		//匿名类
		Scene scene =  new Scene(tpl,serv, monsterService){		
		};
		scene.init();
		//初始化这个场景获取分线号
		Integer line = service.getSceneLine().get(scene.getSceneId());
		if(line == null){
			//1开始
			service.getSceneLine().put(scene.getSceneId(), 1);
			service.getSceneLock().put(scene.getSceneId(), new ReentrantLock());
			scene.setLine(1);
			Loggers.msgLogger.info("地图"+scene.getSceneId()+"创建第一条线...");
		}else{			
			//最大场景的线编号
			scene.setLine(line);
			Loggers.msgLogger.info("地图"+scene.getSceneId()+"增加一条线到"+line);
		}
		if(tpl.getSceneType() == SceneType.ACTIVITY){
			if(StringUtils.equals(tpl.getSceneId(), BossActivity.getBossSceneId())){
				scene.getMonsterManager().setAlive(false);//活动地图的怪都是先不心跳
				List<VisibleMonster> ms = scene.getMonsterManager().getAnyVisibleMonster();
				for(VisibleMonster vm : ms){
					vm.setSharedLevel(Player.getPlayerMaxLevel());
				}
				scene.registerListener(new AfterEnterBossSceneListener());
			}else if(StringUtils.equals(tpl.getSceneId(), GGZJActivity.getSceneId())){
				scene.getMonsterManager().setAlive(false);//活动地图的怪都是先不心跳
				scene.registerListener(new AfterEnterGgzjSceneListener());
			}
		}
		return scene;
		
	}
}
