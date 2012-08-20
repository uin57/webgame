package com.pwrd.war.gameserver.map;

import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.map.template.MapBattleBgTemplate;
import com.pwrd.war.gameserver.map.template.MapTemplate;

public class MapBattleBgService {
	public static final int FUNC_ARENA = 1;		//竞技场功能
	public static final int FUNC_JXYL = 2;		//将星云路功能
	public static final int FUNC_CCJJ = 3;		//草船借箭功能
	public static final int FUNC_DEFAULT = 4;	//默认背景
	
	private Map<String, String> mapBattleBg = new HashMap<String, String>();	//地图对应的战斗背景
	private Map<Integer, String> funcBattleBg = new HashMap<Integer, String>();	//功能对应的战斗背景
	private Map<Integer, Integer> funcNpcId = new HashMap<Integer, Integer>();	//功能对应的围观npc编号
	
	public MapBattleBgService() {
		//加载地图对应的战斗背景
		for (MapTemplate template : Globals.getTemplateService().getAll(MapTemplate.class).values()) {
			mapBattleBg.put(template.getMapId(), template.getBgId());
		}
		
		//加载功能对应的战斗背景
		for (MapBattleBgTemplate template : Globals.getTemplateService().getAll(MapBattleBgTemplate.class).values()) {
			funcBattleBg.put(template.getFuncId(), template.getBgId());
			funcNpcId.put(template.getFuncId(), template.getNpcId());
		}
	}
	
	/**
	 * 根据地图id获取战斗背景
	 * @param mapId
	 * @return
	 */
	public String getBattleBgByMapId(String mapId) {
		if (mapBattleBg.containsKey(mapId)) {
			return mapBattleBg.get(mapId);
		} else {
			return funcBattleBg.get(FUNC_DEFAULT);
		}
	}
	
	/**
	 * 根据功能id获取战斗背景
	 * @param funcId
	 * @return
	 */
	public String getBattleBgByFuncId(int funcId) {
		if (funcBattleBg.containsKey(funcId)) {
			return funcBattleBg.get(funcId);
		} else {
			return funcBattleBg.get(FUNC_DEFAULT);
		}
	}
	
	/**
	 * 根据功能id获取npc编号
	 * @param funcId
	 * @return
	 */
	public int getNpcIdByFuncId(int funcId) {
		if (funcNpcId.containsKey(funcId)) {
			return funcNpcId.get(funcId);
		} else {
			return funcNpcId.get(FUNC_DEFAULT);
		}
	}
}
