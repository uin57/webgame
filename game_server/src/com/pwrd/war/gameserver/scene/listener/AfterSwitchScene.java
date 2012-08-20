package com.pwrd.war.gameserver.scene.listener;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.scene.PlayerLeaveSceneCallback;
import com.pwrd.war.gameserver.scene.msg.GCSwitchScene;

public class AfterSwitchScene implements PlayerLeaveSceneCallback {

	protected int x,y;
	protected int lineNo;
	protected String nowSwitchId;
	protected String nextSceneId, nextId;
	public AfterSwitchScene(int x, int y, String nowSwitchId, String nextSceneId, String nextId, int lineNo){
		this.x = x;
		this.y = y;
		this.nowSwitchId = nowSwitchId;
		this.nextSceneId = nextSceneId;
		this.nextId = nextId; 
		this.lineNo = lineNo;
	}
	public AfterSwitchScene(int x, int y,  String nextSceneId, int lineNo){
		this.x = x;
		this.y = y;
		this.nextSceneId = nextSceneId;
		this.lineNo = lineNo;
		this.nextId = "";
	}
	public AfterSwitchScene(int x, int y,String nextSceneId){
		this.x = x;
		this.y = y;
		this.nextSceneId = nextSceneId;
		this.nextId = "";
	}
	@Override
	public void afterLeaveScene(Player player) { 
		//停止修炼
		Globals.getHumanService().getHumanXiulianService().endXiulian(player.getHuman());
		//设置新位置
		player.getHuman().setX(x);
		player.getHuman().setY(y);
		player.setToX(x);
		player.setToY(y);
		player.setTargetSceneId(nextSceneId);//目标的切换点id
		if(lineNo <= 0){
			lineNo = Globals.getSceneService().getAvailableLine(nextSceneId);
		}
		
		Loggers.msgLogger.info("成功切换到地图："+nextSceneId+"，分线号："+lineNo);
		GCSwitchScene msg = new GCSwitchScene();
		msg.setSceneId(nextSceneId);
		msg.setSwitchId(nextId);
		msg.setLineNo(lineNo);
		player.sendMessage(msg);
		//TODO
//		if(player.getHuman().isInTeam()){
//			TeamInfo t = player.getHuman().getTeamInfo();
//			if(t.isLeader(player.getHuman())){
//				//TODO 告诉队员开始切换场景到目标线
//				//同时队员开始离开当前场景
//				for(Human h : t.getTeamer()){
//					//设置为切换状态，同时离开当前场景
//					Player p = h.getPlayer(); 
//					Globals.getSceneService().onPlayerSwitchScene(p, nowSwitchId, lineNo);
//				}
//			}
//		}
	} 

}
