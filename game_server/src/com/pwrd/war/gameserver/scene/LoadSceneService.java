package com.pwrd.war.gameserver.scene;

import java.util.Map;

import com.pwrd.war.gameserver.scene.vo.SceneInfoVO;

/**
 * 从地图文件夹加载地图
 * @author xf
 */
public interface LoadSceneService {

	
	/**
	 * 获取所有场景地图信息
	 * @author xf
	 */
	public Map<String, SceneInfoVO>  getAllScenes();
	
	/**
	 * 根据场景id取模版
	 * @author xf
	 */
	public SceneInfoVO getSceneById(String sceneId);
	
}
