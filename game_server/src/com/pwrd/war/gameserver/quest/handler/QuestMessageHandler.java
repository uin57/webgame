package com.pwrd.war.gameserver.quest.handler;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.quest.msg.CGAcceptQuest;
import com.pwrd.war.gameserver.quest.msg.CGFinishQuest;
import com.pwrd.war.gameserver.quest.msg.CGGiveUpQuest;
import com.pwrd.war.gameserver.quest.msg.CGQuestList;
import com.pwrd.war.gameserver.story.StoryEventType;
import com.pwrd.war.gameserver.story.msg.GCStoryShow;

/**
 * 
 * 任务消息处理器
 * @author jiliang.lu
 *
 */
public class QuestMessageHandler {
	protected OnlinePlayerService onlinePlayerService;


	protected QuestMessageHandler(OnlinePlayerService playerManager) {
		this.onlinePlayerService = playerManager;
	}
	
	public void handleQuestList(Player player, CGQuestList cgQuestList) {
		player.getHuman().getQuestDiary().sendQuestListMsg();
	}

	public void handleAcceptQuest(Player player, CGAcceptQuest cgAcceptQuest) {
		int questId = cgAcceptQuest.getQuestId();
		player.getHuman().getQuestDiary().acceptQuest(questId);
//		int storyId = Globals.getStoryService().getStoryIdByEvent(StoryEventType.ACCEPT_QUEST, questId+"");
//		if(storyId > 0){
//			GCStoryShow msg = new GCStoryShow();
//			msg.setStoryId(storyId+"");
//			player.sendMessage(msg);
//		}
	}
	
	public void handleGiveUpQuest(Player player, CGGiveUpQuest cgGiveUpQuest) {
		int questId = cgGiveUpQuest.getQuestId();
		player.getHuman().getQuestDiary().giveUpQuest(questId);
	}
	
	public void handleFinishQuest(Player player, CGFinishQuest cgFinishQuest) {
		int questId = cgFinishQuest.getQuestId();
		player.getHuman().getQuestDiary().finishQuest(questId);
//		int storyId = Globals.getStoryService().getStoryIdByEvent(StoryEventType.FINISH_QUEST, questId+"");
//		if(storyId > 0){
//			GCStoryShow msg = new GCStoryShow();
//			msg.setStoryId(storyId+"");
//			player.sendMessage(msg);
//		}
	}

}
